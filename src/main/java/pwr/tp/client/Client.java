package pwr.tp.client;

import pwr.tp.server.messages.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.Scanner;

/**
 * The Client class represents a client that connects to the GameHostServer.
 * It handles user input, sends messages to the server, and processes messages from the server.
 */
public class Client {
  private Socket socket;
  private ObjectOutputStream objectOutputStream;
  private ObjectInputStream objectInputStream;
  private Scanner scannerIn;
  private boolean running = false;
  private boolean inGame = false;

  /**
   * Constructs a Client and connects to the specified host and port.
   *
   * @param host the server host
   * @param port the server port
   *
   */
  public Client(String host, int port) {
    try {
      Runtime.getRuntime().addShutdownHook(new Thread(() -> {
        if (running) {
          processQuitMessage();
        }
      }));
      connect(host, port);
      Thread receiveThread = new Thread(this::receiveMessages);
      receiveThread.start();
      handleUserInput();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Handles user input from the console.
   */
  private void handleUserInput() {
    label:
    while (running) {
      if (!inGame) {
        System.out.println("'create' to create game, 'join' to join existing game,\n" +
                " 'list' to list existing games or 'quit' to exit:");
        String input = scannerIn.nextLine();
        input = input.toLowerCase();
        input = input.trim();
        switch (input) {
          case "quit":
            break;
          case "create":
            System.out.println("Enter number of players and board type, separated by a semicolon (;): ");
            input = scannerIn.nextLine();
            processCreateGameMessage(input);
            break;
          case "join":
            System.out.println("Enter game id: ");
            input = scannerIn.nextLine();
            processJoinMessage(input);
            break;
          case "list":
            send(new ListGamesMessage(null));
            break;
          default:
            System.out.println("Invalid option");
            break;
        }
      } else {
        System.out.println("Enter action ('move' or 'disconnect'):");
        String input = scannerIn.nextLine();
        switch (input){
          case "move":
            System.out.println("Enter your move starting from x1,y1 ; destination to x2,y2 \n" +
                    " or 'quit' to exit:");
            input = scannerIn.nextLine();
            if (input.equals("quit")) {
              break;
            }
            processMoveMessage(input);
            break;
          case "disconnect":
            processDisconnectMessage();
            break;
          case "quit":
            break label;
          default:
            System.out.println("Invalid option");
            break;
        }
      }
    }
    processQuitMessage();
  }

  /**
   * Processes the join game message.
   *
   * @param input the game id input
   */
  private void processJoinMessage(String input) {
    try {
      int gameId = Integer.parseInt(input);
      send(new JoinMessage(gameId));
    } catch (NumberFormatException e) {
      System.out.println("Invalid input. Please enter a number.");
    }
  }

  /**
   * Processes the creates game message.
   *
   * @param input the number of players and board type input
   */
  private void processCreateGameMessage(String input) {
    try {
      String[] gameInfo = input.trim().split(";");
      for (int i = 0; i < gameInfo.length; i++) {
        gameInfo[i] = gameInfo[i].trim();
      }
      if (gameInfo.length != 2) {
          System.out.println("Invalid input. Please enter two values separated by a semicolon (;).");
          return;
      }
      int players = Integer.parseInt(gameInfo[0]);
      String boardType = gameInfo[1];
      send(new CreateGameMessage(players, boardType));
      } catch (NumberFormatException e) {
      System.out.println("Invalid input. Please enter two values separated by a semicolon (;).");
    }
  }

  /**
   * Processes the move message.
   *
   * @param message the move input
   */
  private void processMoveMessage(String message) {
    try {
      String[] coords = message.trim().split(";");
      for (int i = 0; i < coords.length; i++) {
        coords[i] = coords[i].trim();
      }
      if (coords.length != 2) {
        System.out.println("Invalid input. Please enter two points separated by a semicolon (;).");
        return;
      }
      String[] start = coords[0].split(",");
      String[] end = coords[1].split(",");
      for (int i = 0; i < start.length; i++) {
        start[i] = start[i].trim();
        end[i] = end[i].trim();
      }
      if (start.length != 2 || end.length != 2) {
          System.out.println("Invalid input. Please enter two points separated by a semicolon (;).");
          return;
      }
      int x1 = Integer.parseInt(start[0]);
      int y1 = Integer.parseInt(start[1]);
      int x2 = Integer.parseInt(end[0]);
      int y2 = Integer.parseInt(end[1]);
      send(new MoveMessage(x1, y1, x2, y2));
    } catch (NumberFormatException e) {
      System.out.println("Invalid input. Please enter two points separated by a semicolon (;).");
    }
  }

  /**
   * Processes the quit message.
   */
  private void processQuitMessage() {
    running = false;
    if (!socket.isClosed()) {
      send(new QuitMessage());
    }
    close();
  }

  /**
   * Receives messages from the server.
   */
  private void receiveMessages() {
    try {
    while (running) {
      String message = (String) objectInputStream.readObject();
      if (message.equals("JOIN_SUCCESS")) {
        inGame = true;
      } else if (message.equals("JOIN_FAILURE")) {
        System.out.println("Failed to join game");
      } else {
        System.out.println("Server: " + message);
      }
    }
    } catch (SocketException e) {
        System.out.println("Disconnected");
    } catch (Exception e) {
    e.printStackTrace();
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
   * Connects to the server.
   *
   * @param host the server host
   * @param port the server port
   */
  private void connect(String host, int port) {
      try {
          socket = new Socket(host, port);
          objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
          objectOutputStream.flush();
          objectInputStream = new ObjectInputStream(socket.getInputStream());
          scannerIn = new Scanner(System.in);
          System.out.println("Connected to server on port " + port);
          running = true;
      } catch (Exception e) {
          e.printStackTrace();
      }
  }

  /**
   * Processes the disconnect message.
   */
  private void processDisconnectMessage() {
    inGame = false;
    send(new DisconnectGameMessage());
  }

  /**
   * Closes the client connection and resources.
   */
  public synchronized void close() {
    try {
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
    } catch (SocketException SE) {
      System.out.println("Socket closed");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * The main method to start the client.
   *
   * @param args the command line arguments
   */
  public static void main(String[] args) {
      try {
          new Client("localhost", 12345);
      } catch (Exception e) {
        System.out.println("Server not found");
      }
  }
}
