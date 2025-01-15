package pwr.tp.server;

import pwr.tp.game.Lobby;
import pwr.tp.gameplay.IllegalMoveException;
import pwr.tp.server.messages.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;


public class  ClientHandler implements Runnable {

  private final Socket clientSocket;
  private final GameHostServer gameHostServer;
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

  private void processUpdatePawnsMessage(Message msg) {
    if (lobby.isGameStarted()) {
      UpdatePawnsMessage updatePawnsMessage = (UpdatePawnsMessage) msg;
      updatePawnsMessage.setPawns(lobby.getPawnsFromPlayer(playerIndex));
      send(updatePawnsMessage);
    } else {
      send("Game not started, cannot update pawns");
    }
  }

  private void processCreateGameMessage(Message msg) {
    CreateGameMessage createGameMessage = (CreateGameMessage) msg;
    if (gameHostServer.createLobby(createGameMessage.getNumOfPlayers(), createGameMessage.getBoardType(), this)) {
      send(createGameMessage.getBoardType().toUpperCase() + " lobby created.");
    } else {
      send("Unable to create lobby");
    }
  }

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

  private void processQuitMessage(Message msg) {
    gameHostServer.removeClient(this);
    close();
  }

  private void processDisconnectGameMessage() {
    gameHostServer.quitLobby(this);
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
