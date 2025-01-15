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
      case DISCONNECT_GAME:
        processDisconnectGameMessage();
        break;
      default:
        send("Unknown message type");
        break;
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
      updatePawnsMessage.setPawns(lobby.getPawnsFromPlayer(playerIndex));
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
      joinMessage.setPlayerIndex(playerIndex);
      joinMessage.setAccepted(true);
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
    moveMessage.setPlayerIndex(playerIndex);
    if (lobby.isGameStarted()) {
      if (lobby.receiveMove(moveMessage.getMove(), playerIndex)) {
        moveMessage.setAccepted(true);
        send(moveMessage);
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
  private void processDisconnectGameMessage() {
    gameHostServer.quitLobby(this);
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
}
