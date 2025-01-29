package pwr.tp.server;

import pwr.tp.game.Lobby;
import pwr.tp.server.messages.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


/**
 * Handles client connections and processes messages from clients.
 */
public class  ClientHandler implements Runnable {

  /**
   * The client socket for communication.
   */
  private final Socket clientSocket;

  /**
   * The game host server managing the game.
   */
  private final GameHostServer gameHostServer;

  /**
   * The output stream to send data to the client.
   */
  private ObjectOutputStream out;

  /**
   * The input stream to receive data from the client.
   */
  private ObjectInputStream in;

  /**
   * The lobby the client is part of.
   */
  private Lobby lobby;

  /**
   * The index of the player.
   */
  private final int playerIndex;

  /**
   * The index of the player in the lobby.
   */
  private int lobbyPlayerIndex;



    /**
     * Constructs a ClientHandler with the specified client socket, game host server, and player index.
     *
     * @param clientSocket the client socket
     * @param gameHostServer the game host server
     * @param playerIndex the index of the player
     */
    public ClientHandler(Socket clientSocket, GameHostServer gameHostServer, int playerIndex) {
        this.clientSocket = clientSocket;
        this.gameHostServer = gameHostServer;
        this.playerIndex = playerIndex;
        try {
            out = new ObjectOutputStream(clientSocket.getOutputStream());
            out.flush();
            in = new ObjectInputStream(clientSocket.getInputStream());
        } catch (IOException ioe) {
          ioe.printStackTrace();
        }
    }

    /**
     * Runs the client handler, continuously reading and processing messages from the client.
     */
  @Override
  public void run() {
    try {
      while (true) {
        Message msg = (Message) in.readObject();
        processMessage(msg);
      }
    } catch (SocketException e) {
      System.out.println("Client disconnected");
    } catch (IOException | ClassNotFoundException e) {
      System.out.println("Msg error");
    }
  }

    /**
     * Processes the received message based on its type.
     *
     * @param msg the message to process
     */
  private void processMessage(Message msg) {
    switch (msg.getType()) {
      case JOIN:
        processJoinMessage(msg);
        break;
      case MOVE:
        processMoveMessage(msg);
        break;
      case QUIT:
        processQuitMessage(msg);
        break;
      case CREATE_GAME:
        processCreateGameMessage(msg);
        break;
      case LOAD_GAME:
        processLoadGameMessage(msg);
        break;
      case LIST_GAMES:
        List<Lobby> activeLobbiesList = gameHostServer.getActiveLobbies();
        List<String> activeLobbiesString = new ArrayList<>();
        for (Lobby lobby : activeLobbiesList) {
          activeLobbiesString.add(lobby.toString());
        }
        send(new ListGamesMessage(activeLobbiesString));
        break;
      case UPDATE_PAWNS:
        processUpdatePawnsMessage(msg);
        break;
      case UPDATE_BOARD:
          processUpdateBoardMessage(msg);
          break;
      case ADD_BOT:
        processAddBotMessage(msg);
        break;
      case DISCONNECT_GAME:
        processDisconnectGameMessage(msg);
        break;
      default:
        send("Unknown message type");
        break;
    }
  }

  private void processLoadGameMessage(Message msg) {
    LoadGameMessage loadGameMessage = (LoadGameMessage) msg;
    if (gameHostServer.loadLobby(loadGameMessage.getGameID(), this)) {
      send("Game loaded");
    } else {
      send("Unable to load game");
    }
  }

  private void processAddBotMessage(Message msg) {
    AddBotMessage addBotMessage = (AddBotMessage) msg;
    if (lobby != null && !lobby.isGameStarted()) {
      lobby.addBot();
      send(addBotMessage);
      if (lobby.getCurrentNumOfPlayers() == lobby.getNumOfPlayers()) {
        gameHostServer.startGame(lobby);
      }
    } else {
      send("No lobby to add bot to or game already started");
    }
  }

  /**
     * Processes an UpdatePawnsMessage.
     *
     * @param msg the message to process
     */
  private void processUpdatePawnsMessage(Message msg) {
    if (lobby.isGameStarted()) {
      UpdatePawnsMessage updatePawnsMessage = (UpdatePawnsMessage) msg;
      updatePawnsMessage.setPawns(lobby.getPawnsFromPlayer(lobbyPlayerIndex));
      send(updatePawnsMessage);
    } else {
      send("Game not started, cannot update pawns");
    }
  }

    /**
     * Processes a CreateGameMessage.
     *
     * @param msg the message to process
     */
  private void processCreateGameMessage(Message msg) {
    CreateGameMessage createGameMessage = (CreateGameMessage) msg;
    if (gameHostServer.createLobby(createGameMessage.getNumOfPlayers(), createGameMessage.getBoardType(), this)) {
      send(createGameMessage.getBoardType().toUpperCase() + " lobby created.");
    } else {
      send("Unable to create lobby");
    }
  }

    /**
     * Processes a JoinMessage.
     *
     * @param msg the message to process
     */
  private void processJoinMessage(Message msg) {
    JoinMessage joinMessage = (JoinMessage) msg;
    if (gameHostServer.joinLobby(this, joinMessage.getUniqueLobbyNumber()) && this.lobby != null) {
      lobbyPlayerIndex = lobby.getCurrentNumOfPlayers() - 1;
      joinMessage.setPlayerIndex(lobbyPlayerIndex);
      joinMessage.setAccepted(true);
      joinMessage.setGameType(lobby.getGameType());
      send(joinMessage);
      send("Connected to lobby number: " + joinMessage.getUniqueLobbyNumber());
      if (lobby.getCurrentNumOfPlayers() == lobby.getNumOfPlayers()) {
        gameHostServer.startGame(lobby);
      }
    } else {
      send(joinMessage);
      send("Unable to connect to lobby number: " + joinMessage.getUniqueLobbyNumber());
    }
  }

    /**
     * Processes a MoveMessage.
     *
     * @param msg the message to process
     */
  private void processMoveMessage(Message msg) {
    MoveMessage moveMessage = (MoveMessage) msg;
    moveMessage.setPlayerIndex(lobbyPlayerIndex);
    if (lobby.isGameStarted()) {
      if (lobby.receiveMove(moveMessage.getMove(), lobbyPlayerIndex)) {
        moveMessage.setAccepted(true);
        send(moveMessage);
        checkWinner();
        gameHostServer.sendToAllInLobby(moveMessage, lobby, this);
      } else {
        moveMessage.setAccepted(false);
        send(moveMessage);
      }
    } else {
      send("Game not started");
    }
  }

    /**
     * Processes a QuitMessage.
     *
     * @param msg the message to process
     */
  private void processQuitMessage(Message msg) {
    gameHostServer.removeClient(this);
    close();
  }

    /**
     * Processes a DisconnectGameMessage.
     */
  private void processDisconnectGameMessage(Message msg) {
    gameHostServer.quitLobby(this);
    DisconnectGameMessage disconnectGameMessage = (DisconnectGameMessage) msg;
    disconnectGameMessage.setDisconnected(true);
    send(disconnectGameMessage);
  }

  /**
   * Processes an UpdateBoardMessage.
   *
   * @param msg the message to process
   */
  private void processUpdateBoardMessage(Message msg) {
    UpdateBoardMessage updateBoardMessage = (UpdateBoardMessage) msg;
    updateBoardMessage.update(lobby.getAllPawnCoordinates(), lobby.getNumOfPlayers() );
    send(updateBoardMessage);
  }

    /**
     * Sends an object to the client.
     *
     * @param object the object to send
     */
  public void send(Object object) {
    try {
      out.writeObject(object);
      out.flush();
    } catch (IOException ioe) {
      ioe.printStackTrace();
    }
  }

    /**
     * Closes the client connection.
     */
  private void close() {
    try {
      in.close();
      out.close();
      clientSocket.close();
    } catch (IOException ioe) {
      ioe.printStackTrace();
    }
  }

    /**
     * Returns the player index.
     *
     * @return the player index
     */
  public int getPlayerIndex() {
      return playerIndex;
  }

  public int getLobbyPlayerIndex() {
    return lobbyPlayerIndex;
  }

    /**
     * Returns the lobby.
     *
     * @return the lobby
     */
  public Lobby getLobby() {
      return lobby;
  }

    /**
     * Adds a lobby to the client handler.
     *
     * @param lobby the lobby to add
     */
  public void addLobby(Lobby lobby) {
      this.lobby = lobby;
  }

  /**
   * Checks if there is a winner in the lobby and sends an EndGameMessage if a winner is found.
   */
  private void checkWinner() {
    if (lobby.getIndexOfWinner() != -1) {
      gameHostServer.sendToAllInLobby(new EndGameMessage(lobby.getIndexOfWinner()), lobby, this);
      lobby.endGame();
    }
  }

}
