package pwr.tp.game;

import pwr.tp.domain.Board;
import pwr.tp.gameplay.StandardGameplay;
import pwr.tp.gameplay.IllegalMoveException;
import pwr.tp.movement.Move;

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
    private final int uniqueLobbyNumber;

    /**
     * The number of players required to start the game.
     */
    int numOfPlayers;

    /**
     * The current number of players in the lobby.
     */
    int currentNumOfPlayers;

    /**
     * The current game being played in the lobby.
     */
    StandardGameplay currentGame;

    /**
     * The game board used in the lobby.
     */
    Board board;

    Boolean ghost = false;

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
        //TODO: implement asking players for game type, for standard game any string, for double base game as below
        currentGame = new StandardGameplay(numOfPlayers, board, "double base game");
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
        return "ID: " + uniqueLobbyNumber + ", board type: "+ board.getName() +
                " with " + currentNumOfPlayers + "/" + numOfPlayers + " players";
    }

    public int getIndexOfWinner() {
        //(if game winner hasn't been decided yet, indexOfWinner = -1)
        return currentGame.getIndexOfWinner();
    }

    public Board getBoard() {
        return board;
    }

    public List<List<String>> getAllPawnCoordinates() {
        return currentGame.getAllPawnsCoordinates();
    }

}
