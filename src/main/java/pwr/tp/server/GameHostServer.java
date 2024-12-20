package pwr.tp.server;

import pwr.tp.game.CreateLobby;
import pwr.tp.game.IllegalBoardTypeException;
import pwr.tp.game.IllegalNumberOfPlayersException;
import pwr.tp.game.Lobby;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GameHostServer {

  private final int Port;
  private final int maxPlayers;
  private int activeLobbiesCount = 0;
  private ServerSocket serverSocket;
  private int playerCount = 0;
  private List<ClientHandler> clients = new ArrayList<>();
  private List<Lobby> activeLobbies= new ArrayList<>();
  private ExecutorService pool;

  public GameHostServer(int Port, int maxPlayers) {
    this.Port = Port;
    this.maxPlayers = maxPlayers;
    this.pool = Executors.newFixedThreadPool(maxPlayers);
    System.out.println("GameHostServer created on port " + Port);
  }

  public void start() {
    try (var serverSocket = new ServerSocket(Port)) {
      this.serverSocket = serverSocket;
      System.out.println("Server started on port " + Port);
      acceptClients();
      broadcast("Game is starting");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void acceptClients() {
    while (true) {
      try {
        var clientSocket = serverSocket.accept();
        if (playerCount < maxPlayers) {
          playerCount++;
          var clientHandler = new ClientHandler(clientSocket, this, playerCount );
          clients.add(clientHandler);
          pool.execute(clientHandler);
          System.out.println("Player " + playerCount + " connected");
          //broadcast(playerCount + "/" + maxPlayers + " players connected");
          sendToAllInLobby(playerCount + "/" + maxPlayers + " players connected", null, clientHandler);
        } else {
          System.out.println("Server is full("+ playerCount + "/" + maxPlayers +") - rejecting client");
          clientSocket.close();
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  public synchronized void removeClient(ClientHandler clientHandler) {
    clients.remove(clientHandler);
    playerCount--;
    if (clientHandler.getLobby() != null) {
      for (var lobby : activeLobbies) {
        if (lobby == clientHandler.getLobby()) {
          lobby.removePlayer();
          String notification = "Player number " + clientHandler.getPlayerIndex() + " disconnected\n" +
                  "Lobby number " + lobby.getUniqueLobbyNumber() + " : " + lobby.getCurrentNumOfPlayers() +
                  "/" + lobby.getNumOfPlayers();
          sendToAllInLobby(notification, lobby, clientHandler);
          if (lobby.getCurrentNumOfPlayers() == 0) {
            activeLobbies.remove(lobby);
            activeLobbiesCount--;
          }
        }
      }
    }
    System.out.println("Player disconnected");
  }

  public synchronized void broadcast(Object object) {
    for (var client : clients) {
      client.send(object);
    }
  }

  public synchronized void sendToAllExcept(Object object, ClientHandler clientHandler) {
    for (var client : clients) {
      if (client != clientHandler) {
        client.send(object);
      }
    }
  }

  public synchronized void sendToAllInLobby(Object object, Lobby lobby, ClientHandler clientHandler) {
    for (var client : clients) {
      if (client.getLobby() == lobby && client != clientHandler) {
        client.send(object);
      }
    }
  }

  public synchronized Boolean joinLobby(ClientHandler client, int uniqueLobbyNumber) {
    if (activeLobbiesCount <= 0) {
      return false;
    }
    for (var lobby : activeLobbies) {
      if (lobby.getUniqueLobbyNumber() == uniqueLobbyNumber && lobby.getCurrentNumOfPlayers() < lobby.getNumOfPlayers()){
        client.addLobby(lobby);
        lobby.addPlayer();
        String notification = "Player number " + client.getPlayerIndex() + " connected\n" +
                "Lobby number " + uniqueLobbyNumber + " : " + lobby.getCurrentNumOfPlayers() +
                "/" + lobby.getNumOfPlayers();
        sendToAllInLobby(notification, lobby, client);
        return true;
      }
    }
    return false;
  }

  public Boolean createLobby(){
    try {
      activeLobbies.add(CreateLobby.createLobby(6, "star board"));
      activeLobbiesCount++;
      return true;
    } catch (IllegalBoardTypeException | IllegalNumberOfPlayersException e) {
      return false;
    }
  }



  public static void main(String[] args) {
      var server = new GameHostServer(12345, 24);
      server.start();
  }

}
