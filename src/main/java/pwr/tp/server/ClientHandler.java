package pwr.tp.server;

import pwr.tp.game.Lobby;
import pwr.tp.server.messages.CreateGameMessage;
import pwr.tp.server.messages.JoinMessage;
import pwr.tp.server.messages.Message;
import pwr.tp.server.messages.MoveMessage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;


public class ClientHandler implements Runnable {

  private Socket clientSocket;
  private GameHostServer gameHostServer;
  private ObjectOutputStream out;
  private ObjectInputStream in;
  private Lobby lobby;
  private final int playerIndex;

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
      default:
        send("Unknown message type");
        break;
    }
  }

  private void processCreateGameMessage(Message msg) {
    CreateGameMessage createGameMessage = (CreateGameMessage) msg;
    if (gameHostServer.createLobby()) {
      send(createGameMessage.getBoardType().toUpperCase() + " lobby created.");
    } else {
      send("Unable to create lobby for " + createGameMessage.getBoardType().toUpperCase());
    }
  }

  private void processJoinMessage(Message msg) {
    JoinMessage joinMessage = (JoinMessage) msg;
    if (gameHostServer.joinLobby(this, joinMessage.getUniqueLobbyNumber()) && this.lobby != null) {
      send("JOIN_SUCCESS");
      send("Connected to lobby number: " + joinMessage.getUniqueLobbyNumber());
    } else {
      send("JOIN_FAILURE");
      send("Unable to connect to lobby number: " + joinMessage.getUniqueLobbyNumber());
    }
  }

  private void processMoveMessage(Message msg) {
    MoveMessage moveMessage = (MoveMessage) msg;
    String move = moveMessage.toString();
    move = move + " from player number" + playerIndex;
    gameHostServer.sendToAllInLobby(move, this.lobby, this);
  }

  private void processQuitMessage(Message msg) {
    gameHostServer.removeClient(this);
    String notification = "Player " + playerIndex + " disconnected";
    if (this.lobby == null) {
      gameHostServer.broadcast(notification);
    } else {
      gameHostServer.sendToAllInLobby(notification, this.lobby, this);
    }
    close();
  }

  public void send(Object object) {
    try {
      out.writeObject(object);
      out.flush();
    } catch (IOException ioe) {
      ioe.printStackTrace();
    }
  }

  private void close() {
    try {
      in.close();
      out.close();
      clientSocket.close();
    } catch (IOException ioe) {
      ioe.printStackTrace();
    }
  }

  public int getPlayerIndex() {
      return playerIndex;
  }

  public Lobby getLobby() {
      return lobby;
  }

  public void addLobby(Lobby lobby) {
      this.lobby = lobby;
  }



}
