package pwr.tp.client.GUI;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pwr.tp.client.GUI.BoardPreview.BoardPreview;
import pwr.tp.server.messages.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.List;
import java.util.Scanner;

/**
 * The MIMGui class handles the communication between the client and the server.
 * It manages the connection, sending and receiving messages, and updating the client view.
 */
public class MIMGui {
  /**
   * The main view controller of the client.
   */
  private final ClientMainViewController clientMainViewController;

  /**
   * The in-game view controller.
   */
  private InGameViewController inGameViewController;

  private BoardPreview boardPreview;

  /**
   * The socket used for communication with the server.
   */
  private Socket socket;

  /**
   * The output stream for sending objects to the server.
   */
  private ObjectOutputStream objectOutputStream;

  /**
   * The input stream for receiving objects from the server.
   */
  private ObjectInputStream objectInputStream;

  /**
   * The scanner for reading input from the console.
   */
  private Scanner scannerIn;

  /**
   * The index of the player.
   */
  private int playerIndex;

  /**
   * Indicates whether the client is running.
   */
  private boolean running = false;

  /**
   * Indicates whether the client is in a game.
   */
  private boolean inGame = false;

  private String gameType;

  /**
   * Constructs a MIMGui object with the specified ClientMainViewController.
   *
   * @param clientMainViewController the main view controller of the client
   */
  public MIMGui(ClientMainViewController clientMainViewController) {
    this.clientMainViewController = clientMainViewController;
  }

  /**
   * Starts the connection to the server and begins receiving messages.
   *
   * @param host the server host
   * @param port the server port
   * @throws IOException if an I/O error occurs when connecting
   */
  public void start(String host, int port) throws IOException {
    connect(host, port);
    Thread receiveThread = new Thread(this::receiveMessages);
    receiveThread.start();
  }

  /**
   * Connects to the server with the specified host and port.
   *
   * @param host the server host
   * @param port the server port
   * @throws IOException if an I/O error occurs when connecting
   */
  private void connect(String host, int port) throws IOException {
    socket = new Socket(host, port);
    objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
    objectInputStream = new ObjectInputStream(socket.getInputStream());
    scannerIn = new Scanner(System.in);
    running = true;
  }

  /**
   * Closes the connection and all associated streams.
   *
   * @throws IOException if an I/O error occurs when closing
   */
  public synchronized void close() throws IOException {
    if (objectOutputStream != null) {
      objectOutputStream.close();
    }
    if (objectInputStream != null) {
      objectInputStream.close();
    }
    if (scannerIn != null) {
      scannerIn.close();
    }
    if (socket != null) {
      socket.close();
    }
  }

  /**
   * Receives messages from the server and processes them.
   */
  private void receiveMessages() {
    try {
      while (running) {
        Object message =  objectInputStream.readObject();
        if (message instanceof Message msg) {
          switch (msg.getType()) {
            case LIST_GAMES:
              ListGamesMessage listGamesMessage = (ListGamesMessage) msg;
              List<String> lobbies = listGamesMessage.getLobbies();
              clientMainViewController.setAvailableGames(lobbies);
              break;
            case MOVE:
              MoveMessage moveMessage = (MoveMessage) msg;
              processMoveMessage(moveMessage);
              break;
            case JOIN:
              JoinMessage joinMessage = (JoinMessage) msg;
              if (joinMessage.isAccepted()) {
                gameType = joinMessage.getGameType();
                playerIndex = joinMessage.getPlayerIndex();
                inGame = true;
              } else {
                ErrorPopUpUtil.showErrorPopUp("Failed to join game");
              }
              break;
            case UPDATE_PAWNS:
              UpdatePawnsMessage updatePawnsMessage = (UpdatePawnsMessage) msg;
              processUpdatePawnsMessage(updatePawnsMessage);
              break;
            case DISCONNECT_GAME:
              DisconnectGameMessage disconnectGameMessage = (DisconnectGameMessage) msg;
              if (disconnectGameMessage.isDisconnected()) {
                inGame = false;
              }
              break;
            case UPDATE_BOARD:
              UpdateBoardMessage updateBoardMessage = (UpdateBoardMessage) msg;
              List<List<String>> playersPawnPositions = updateBoardMessage.getPlayersPawnPositions();
              int numberOfPlayers = updateBoardMessage.getNumberOfPlayers();
              showMap(playersPawnPositions, numberOfPlayers);
              break;
            case ADD_BOT:
              AddBotMessage addBotMessage = (AddBotMessage) msg;
              processAddBotMessage(addBotMessage);
              break;
            case END_GAME:
              EndGameMessage endGameMessage = (EndGameMessage) msg;
              processEndGameMessage(endGameMessage);
              break;
            default:
              break;
          }
        } else if (message instanceof String) {
          if (inGame && inGameViewController != null) {
            inGameViewController.addLobbyInfo("Lobby: " + message);
          } else {
            clientMainViewController.addServerInfo("Server: " + message);
          }
        }
      }
    } catch (SocketException e) {
      clientMainViewController.addServerInfo("Disconnected");
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        close();
      } catch (IOException e) {
        ErrorPopUpUtil.showErrorPopUp(e.getMessage());
      }
    }
  }

  private void processAddBotMessage(AddBotMessage addBotMessage) {
    if (inGameViewController != null) {
      inGameViewController.addLobbyInfo("Bot added");
    }
  }

  /**
   * Sends a message to the server.
   *
   * @param message the message to send
   */
  private synchronized void send(Object message) {
    try {
      objectOutputStream.writeObject(message);
      objectOutputStream.flush();
    } catch (SocketException e) {
      System.out.println("Disconnected");
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Sends a request to the server to create a game.
   *
   * @param players  the number of players
   * @param gameType the type of the game
   */
  public void createGame(int players, String gameType) {
    this.gameType = gameType;
    send(new CreateGameMessage(players, gameType));
  }

  /**
   * Quits the game and closes the connection.
   *
   * @throws IOException if an I/O error occurs when closing
   */
  public void quitGame() throws IOException {
    running = false;
    if (socket != null && !socket.isClosed()) {
      send(new QuitMessage());
    }
    close();
  }

  /**
   * Sends a request to the server to list available games.
   */
  public void listGames() {
      send(new ListGamesMessage(null));
  }

  /**
   * Sends a request to the server to join a game.
   *
   * @param lobbyId the ID of the lobby to join
   */
  public void joinGame(int lobbyId) {
    send(new JoinMessage(lobbyId));
  }

  /**
   * Checks if the client is in a game.
   *
   * @return true if the client is in a game, false otherwise
   */
  public boolean isInGame() {
    return inGame;
  }

  /**
   * Sends a request to the server to update the pawns.
   */
  public void updateMyPawnsRequest() {
    send(new UpdatePawnsMessage());
  }

  /**
   * Processes the UpdatePawnsMessage received from the server.
   *
   * @param updatePawnsMessage the message containing the updated pawns
   */
  public void processUpdatePawnsMessage(UpdatePawnsMessage updatePawnsMessage) {
      if (inGameViewController != null) {
        Platform.runLater(() -> inGameViewController.clearPlayerPawns());
        for (String pawn : updatePawnsMessage.getPawns()) {
          Platform.runLater(() -> inGameViewController.addPlayerPawns(pawn));
        }
      }
  }

  /**
   * Sets the InGameViewController for this MIMGui.
   *
   * @param inGameViewController the in-game view controller
   */
  public void setInGameViewController(InGameViewController inGameViewController) {
    this.inGameViewController = inGameViewController;
  }

  /**
   * Sends a move message to the server.
   *
   * @param x1 the starting x-coordinate
   * @param y1 the starting y-coordinate
   * @param x2 the ending x-coordinate
   * @param y2 the ending y-coordinate
   */
  public void sendMove(int x1, int y1, int x2, int y2) {
    send(new MoveMessage(x1, y1, x2, y2));
  }

  /**
   * Processes the MoveMessage received from the server.
   *
   * @param moveMessage the message containing the move information
   */
  public void processMoveMessage(MoveMessage moveMessage) {
    if (inGameViewController != null) {
      if (moveMessage.isAccepted()) {
        updateMap(moveMessage.getInitialPoint().getFirst(), moveMessage.getInitialPoint().getSecond(), -1);
        if (moveMessage.getPlayerIndex() == playerIndex) {
          updateMap(moveMessage.getFinalPoint().getFirst(), moveMessage.getFinalPoint().getSecond(), playerIndex);
          inGameViewController.addLobbyInfo("You: " + moveMessage);
        } else {
          updateMap(moveMessage.getFinalPoint().getFirst(), moveMessage.getFinalPoint().getSecond(), moveMessage.getPlayerIndex());
          inGameViewController.addLobbyInfo("Player No. " + moveMessage.getPlayerIndex()+ ": " + moveMessage);
        }
      } else {
        inGameViewController.addLobbyInfo("Illegal move: " + moveMessage);
      }
    }
  }

  /**
   * Sends a request to the server to disconnect from the game.
   */
  public void disconnect() {
      send(new DisconnectGameMessage());
  }

  /**
   * Processes the EndGameMessage received from the server.
   *
   * @param endGameMessage the message containing the end game information
   */
  public void processEndGameMessage(EndGameMessage endGameMessage) {
      if (inGameViewController != null) {
      inGameViewController.addLobbyInfo("Game ended, the winner is Player: " + endGameMessage.getWinnerIndex() + "!");
      }
      inGame = false;
  }

  /**
   * Sends a request to the server to update the board.
   */
  public void showMap() {
    send(new UpdateBoardMessage());
  }


  /**
   * Displays the board with the given players' pawn positions and number of players.
   *
   * @param playersPawnPositions the positions of the players' pawns
   * @param numberOfPlayers the number of players in the game
   */
  public void showMap(List<List<String>> playersPawnPositions, int numberOfPlayers) {
    Platform.runLater(() -> {
      Stage stage = new Stage();
      boardPreview = new BoardPreview();
      boardPreview.initialize(playersPawnPositions, numberOfPlayers, gameType);
      Scene scene = new Scene(boardPreview.PreviewPane);
      stage.setScene(scene);
      stage.setTitle("Board");
      stage.show();
    });
  }

  /**
   * Updates the map with the given pawn position and player index.
   *
   * @param x the x-coordinate of the pawn
   * @param y the y-coordinate of the pawn
   * @param playerIndex the index of the player
   */
  public void updateMap(int x, int y, int playerIndex) {
    if (boardPreview != null) {
      Platform.runLater(() -> {
        boardPreview.updatePawnPosition(x, y, playerIndex);
      });
    }
  }

  public void addBot() {
    send(new AddBotMessage());
  }

}