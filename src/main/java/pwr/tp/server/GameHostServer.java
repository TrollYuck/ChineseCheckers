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

/**
 * The GameHostServer class manages the game host server, including client connections,
 * lobby creation, and game management.
 */
public class GameHostServer {

  /**
   * The port number for the server.
   */
  private final int Port;

  /**
   * The maximum number of players allowed on the server.
   */
  private final int maxPlayers;

  /**
   * The count of active lobbies.
   */
  private int activeLobbiesCount = 0;

  /**
   * The server socket for accepting client connections.
   */
  private ServerSocket serverSocket;

  /**
   * The current number of connected players.
   */
  private int playerCount = 0;

  /**
   * The list of connected clients.
   */
  private final List<ClientHandler> clients = new ArrayList<>();

  /**
   * The list of active lobbies.
   */
  private final List<Lobby> activeLobbies= new ArrayList<>();

  /**
   * The thread pool for handling client connections.
   */
  private final ExecutorService pool;

  /**
   * Constructs a GameHostServer with the specified port and maximum number of players.
   *
   * @param Port the port number for the server
   * @param maxPlayers the maximum number of players allowed on the server
   */
  public GameHostServer(int Port, int maxPlayers) {
    this.Port = Port;
    this.maxPlayers = maxPlayers;
    this.pool = Executors.newFixedThreadPool(maxPlayers);
    System.out.println("GameHostServer created on port " + Port);
  }

  /**
   * Starts the server and begins accepting client connections.
   */
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

  /**
   * Accepts client connections and assigns them to client handlers.
   */
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
          sendToAllInLobby(playerCount + "/" + maxPlayers + " players connected", null, null);
        } else {
          System.out.println("Server is full("+ playerCount + "/" + maxPlayers +") - rejecting client");
          clientSocket.close();
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  /**
   * Removes a client from the server and updates the player count.
   *
   * @param clientHandler the client handler to remove
   */
  public synchronized void removeClient(ClientHandler clientHandler) {
    clients.remove(clientHandler);
    playerCount--;
    quitLobby(clientHandler);
    System.out.println("Player disconnected");
  }

  /**
   * Broadcasts a message to all connected clients.
   *
   * @param object the message to broadcast
   */
  public synchronized void broadcast(Object object) {
    for (var client : clients) {
      client.send(object);
    }
  }

  /**
   * Sends a message to all clients except the specified client.
   *
   * @param object the message to send
   * @param clientHandler the client to exclude from the message
   */
  public synchronized void sendToAllExcept(Object object, ClientHandler clientHandler) {
    for (var client : clients) {
      if (client != clientHandler) {
        client.send(object);
      }
    }
  }

  /**
   * Sends a message to all clients in the specified lobby except the specified client.
   *
   * @param object the message to send
   * @param lobby the lobby to send the message to
   * @param clientHandler the client to exclude from the message
   */
  public synchronized void sendToAllInLobby(Object object, Lobby lobby, ClientHandler clientHandler) {
    for (var client : clients) {
      if (client.getLobby() == lobby && client != clientHandler) {
        client.send(object);
      }
    }
  }

  /**
   * Adds a client to a lobby based on the unique lobby number.
   *
   * @param client the client to add to the lobby
   * @param uniqueLobbyNumber the unique lobby number
   * @return true if the client was added to the lobby, false otherwise
   */
  public synchronized Boolean joinLobby(ClientHandler client, int uniqueLobbyNumber) {
    if (activeLobbiesCount <= 0) {
      return false;
    }
    for (var lobby : activeLobbies) {
      if (lobby.getUniqueLobbyNumber() == uniqueLobbyNumber && !lobby.isPlayerThresholdReached()) {
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

  /**
   * Removes a client from their current lobby.
   *
   * @param client the client to remove from the lobby
   */
  public synchronized void quitLobby(ClientHandler client) {
    if (client.getLobby() != null) {
      for (var lobby : activeLobbies) {
        if (lobby == client.getLobby()) {
          lobby.removePlayer();
          String notification = "Player number " + client.getPlayerIndex() + " disconnected\n" +
                  "Lobby number " + lobby.getUniqueLobbyNumber() + " : " + lobby.getCurrentNumOfPlayers() +
                  "/" + lobby.getNumOfPlayers();
          sendToAllInLobby(notification, lobby, client);
          client.send("Disconnected from lobby number: " + lobby.getUniqueLobbyNumber());
          if (lobby.getCurrentNumOfPlayers() == 0) {
            activeLobbies.remove(lobby);
            activeLobbiesCount--;
          }
          break;
        }
      }
    }
  }

  /**
   * Creates a new lobby with the specified number of players and board type.
   *
   * @param numOfPlayers the number of players for the lobby
   * @param boardType the type of board for the lobby
   * @param client the client creating the lobby
   * @return true if the lobby was created successfully, false otherwise
   */
  public Boolean createLobby(int numOfPlayers, String boardType, ClientHandler client) {
    try {
      Lobby lobby = CreateLobby.createLobby(numOfPlayers, boardType);
      activeLobbies.add(lobby);
      activeLobbiesCount++;
      return true;
    } catch (IllegalBoardTypeException IBE) {
      client.send("Invalid board type: " + boardType);
      return false;
    } catch (IllegalNumberOfPlayersException INP) {
      client.send("Invalid number of players: " + numOfPlayers);
      return false;
    }
  }

  /**
   * Returns the list of active lobbies.
   *
   * @return the list of active lobbies
   */
  public List<Lobby> getActiveLobbies() {
    return activeLobbies;
  }

  /**
   * The main method to start the game host server.
   *
   * @param args the command line arguments
   */
  public static void main(String[] args) {
      var server = new GameHostServer(12345, 24);
      server.start();
  }

  /**
   * Starts the game in the specified lobby.
   *
   * @param lobby the lobby to start the game in
   */
  public synchronized void startGame(Lobby lobby) {
      lobby.startGame();
  }
}
