package pwr.tp.game;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pwr.tp.domain.Board;
import pwr.tp.domain.StarBoard.StarBoard;
import pwr.tp.gameplay.StandardGameplay;
import pwr.tp.gameplay.IllegalMoveException;
import pwr.tp.movement.Move;
import pwr.tp.server.DB.DataOperator;
import pwr.tp.server.DB.DatabaseManager;
import pwr.tp.utilityClases.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Represents a game lobby where players can join, start a game, and make moves.
 */
public class Lobby  {
  /**
   * A counter to generate unique lobby numbers.
   */
  private static final AtomicInteger COUNT = new AtomicInteger(0);

  /**
   * The unique number assigned to this lobby.
   */
  private int uniqueLobbyNumber;

  private List<Integer> botIndexes = new ArrayList<Integer>();

  /**
   * The number of players required to start the game.
   */
  int numOfPlayers;

  /**
   * The current number of players in the lobby.
   */
  int currentNumOfPlayers;

  String gameType;

  private DatabaseManager databaseManager;

  private boolean loadedGame = false;

  /**
   * The current game being played in the lobby.
   */
  StandardGameplay currentGame;

  /**
   * The game board used in the lobby.
   */
  Board board;

  /**
   * Constructs a new Lobby with the specified number of players and board.
   *
   * @param numOfPlayers the number of players for the lobby
   * @param board the game board
   */
  public Lobby(int numOfPlayers, Board board, Boolean ghost) {
    this.board = board;
    this.numOfPlayers = numOfPlayers;
    if (ghost) {
      uniqueLobbyNumber = -1;
    } else {
      uniqueLobbyNumber = COUNT.incrementAndGet();
      System.out.println("uniqueLobbyNumber: " + uniqueLobbyNumber);
    }
  }

  public Lobby (int loadGameIndex) {
    databaseManager = new DatabaseManager(DataOperator.jdbcTemplate());
    databaseManager.setGameId(loadGameIndex);
    List<String> gameData = databaseManager.getGamesInProgress(false);
    String loadedGameInfo = gameData.get(loadGameIndex);
    String[] loadedList = loadedGameInfo.split(",");
    this.numOfPlayers = Integer.parseInt(loadedList[1]);
    this.gameType = loadedList[4];
    this.board = new StarBoard();
    this.uniqueLobbyNumber = COUNT.getAndIncrement();
    this.loadedGame = true;
  }

  private void loadMoves() {
    this.currentGame = new StandardGameplay(numOfPlayers, board, gameType);
    List<Pair<Integer, Pair<String, String>>> moves = databaseManager.getMoves();
    for (Pair<Integer, Pair<String, String>> move : moves) {
      try {
        int playerIndex = move.getFirst();
        int x1 = Integer.parseInt(move.getSecond().getFirst().split(",")[0]);
        int y1 = Integer.parseInt(move.getSecond().getFirst().split(",")[1]);
        int x2 = Integer.parseInt(move.getSecond().getSecond().split(",")[0]);
        int y2 = Integer.parseInt(move.getSecond().getSecond().split(",")[1]);
        Move loadedMove = new Move(new Pair<>(x1, y1), new Pair<>(x2, y2));
        currentGame.receiveMove(loadedMove, playerIndex);
      } catch (Exception e) {
        System.out.println("Error loading move: " + e);
      }
    }
  }



  public void setUpLobby(int numOfPlayers, Board board, Boolean ghost) {
    this.board = board;
    this.numOfPlayers = numOfPlayers;
    if (ghost) {
      uniqueLobbyNumber = -1;
    } else {
      uniqueLobbyNumber = COUNT.incrementAndGet();
      System.out.println("uniqueLobbyNumber: " + uniqueLobbyNumber);
    }
  }

  public void setGameType(String gameType) {
    this.gameType = gameType;
  }

  /**
   * Returns the number of players for the lobby.
   *
   * @return the number of players
   */
  public int getNumOfPlayers() {
    return numOfPlayers;
  }

  /**
   * Returns the current number of players in the lobby.
   *
   * @return the current number of players
   */
  public int getCurrentNumOfPlayers() {
    return currentNumOfPlayers;
  }

  /**
   * Returns the unique lobby number.
   *
   * @return the unique lobby number
   */
  public int getUniqueLobbyNumber() {
    return uniqueLobbyNumber;
  }

  /**
   * Adds a player to the lobby.
   */
  public void addPlayer() {
    currentNumOfPlayers++;
  }

  /**
   * Removes a player from the lobby.
   */
  public void removePlayer() {
    currentNumOfPlayers--;
  }

  /**
   * Starts the game in the lobby.
   */
  public void startGame() {
    if (loadedGame) {
      loadMoves();
    } else {
      currentGame = new StandardGameplay(numOfPlayers, board, gameType);
      databaseManager = new DatabaseManager(DataOperator.jdbcTemplate());
      if (botIndexes != null) {
        int i = 0;
        for (Integer botIndex : botIndexes) {
          currentGame.addBot(botIndex);
          i++;
        }
        databaseManager.createGame(numOfPlayers, i, gameType);
      } else {
        databaseManager.createGame(numOfPlayers, 0, gameType);
      }
    }
  }

  /**
   * Receives a move from a player and processes it.
   *
   * @param move the move to be processed
   * @param playerIndex the index of the player making the move
   * @return true if the move is valid, false otherwise
   */
  public boolean receiveMove(Move move, int playerIndex) {
    try {
      currentGame.receiveMove(move, playerIndex);
      databaseManager.recordMove(playerIndex, move.getInitialPosition().toString(),
              move.getFinalPosition().toString());
      return true;
    } catch (IllegalMoveException e) {
      return false;
    }
  }

  /**
   * Returns the list of pawns from a player.
   *
   * @param playerIndex the index of the player
   * @return the list of pawns
   */
  public List<String> getPawnsFromPlayer(int playerIndex) {
    return currentGame.getPawnsFromPlayer(playerIndex);
  }

  /**
   * Checks if the player threshold is reached.
   *
   * @return true if the player threshold is reached, false otherwise
   */
  public boolean isPlayerThresholdReached() {
    return currentNumOfPlayers >= numOfPlayers;
  }

  /**
   * Checks if the game has started.
   *
   * @return true if the game has started, false otherwise
   */
  public boolean isGameStarted() {
    return currentGame != null;
  }

  /**
   * Returns a string representation of the lobby.
   *
   * @return a string representation of the lobby
   */
  @Override
  public String toString() {
    return "ID: " + uniqueLobbyNumber + ", board type: "+ board.getName()
            + ", game type: " + gameType +
            " with " + currentNumOfPlayers + "/" + numOfPlayers + " players";
  }

  /**
   * Returns the index of the winner.
   * If the game winner hasn't been decided yet, the index will be -1.
   *
   * @return the index of the winner, or -1 if not decided
   */
  public int getIndexOfWinner() {
    //(if game winner hasn't been decided yet, indexOfWinner = -1)
    return currentGame.getIndexOfWinner();
  }

  /**
   * Returns the game board used in the lobby.
   *
   * @return the game board
   */
  public Board getBoard() {
    return board;
  }

  /**
   * Returns the coordinates of all pawns in the game.
   *
   * @return a list of lists containing the coordinates of all pawns
   */
  public List<List<String>> getAllPawnCoordinates() {
    return currentGame.getAllPawnsCoordinates();
  }

  public void addBot() {
    //currentGame.addBot(currentNumOfPlayers);
    botIndexes.add(currentNumOfPlayers);
    addPlayer();
  }

  public String getGameType() {
    return gameType;
  }

  public void endGame() {
    databaseManager.endGame(currentGame.getIndexOfWinner());
  }
}
