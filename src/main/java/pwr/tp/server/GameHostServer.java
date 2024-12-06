package pwr.tp.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GameHostServer {

  private final int Port;
  private final int maxPlayers;
  private ServerSocket serverSocket;
  private int playerCount = 0;
  private List<ClientHandler> clients = new ArrayList<>();
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
          var clientHandler = new ClientHandler(clientSocket, this);
          clients.add(clientHandler);
          pool.execute(clientHandler);
          playerCount++;
          System.out.println("Player " + playerCount + " connected");
          broadcast(playerCount + "/" + maxPlayers + " players connected");
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

  public synchronized int getPlayerIndex(ClientHandler clientHandler) {
    return (clients.indexOf(clientHandler) + 1);
  }

  public static void main(String[] args) {
      var server = new GameHostServer(12345, 2);
      server.start();
  }

}
