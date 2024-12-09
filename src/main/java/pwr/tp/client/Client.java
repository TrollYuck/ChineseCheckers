package pwr.tp.client;

import pwr.tp.server.messages.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.Scanner;

public class Client {
  private Socket socket;
  private ObjectOutputStream objectOutputStream;
  private ObjectInputStream objectInputStream;
  private Scanner scannerIn;
  private Thread receiveThread;
  private boolean running = false;
  private boolean inGame = false;

  public Client(String host, int port) throws Exception {
    try {
      Runtime.getRuntime().addShutdownHook(new Thread(() -> {
        if (running) {
          processQuitMessage();
        }
      }));
      connect(host, port);
      receiveThread = new Thread(this::receiveMessages);
      receiveThread.start();
//      Thread userInputThread = new Thread(this::handleUserInput);
//      userInputThread.start();
      handleUserInput();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void handleUserInput() {
    while (running) {
      if (inGame) {
        System.out.println("Enter your move starting from x1,y1 ; destination to x2,y2 \n" +
                " or 'quit' to exit:");
        String input = scannerIn.nextLine();
        if (input.equals("quit")) {
          break;
        } else {
          processMoveMessage(input);
        }
      } else {
        System.out.println("'create' to create game, 'join' to join existing game\n" +
                " or 'quit' to exit:");
        String input = scannerIn.nextLine();
        if (input.equals("quit")) {
          break;
        } else if (input.equals("create")) {
          processCreateGameMessage(input);
        } else if (input.equals("join")) {
          processJoinMessage(input);
        } else {
          System.out.println("Invalid option");
        }
      }
    }
    running = false;
    processQuitMessage();
  }

  private void processJoinMessage(String input) {
    send(new JoinMessage(0));//TODO
  }

  private void processCreateGameMessage(String input) {
    send(new CreateGameMessage(6, "")); //TODO
    processJoinMessage(input);
  }

  private void processMoveMessage(String message) {
    try {
      String[] coords = message.trim().split(";");
      for (String s : coords) {
        s = s.trim();
      }
      if (coords.length != 2) {
        System.out.println("Invalid input. Please enter two points separated by a semicolon.");
        return;
      }
      String[] start = coords[0].split(",");
      String[] end = coords[1].split(",");
      for (int i = 0; i < start.length; i++) {
        start[i] = start[i].trim();
        end[i] = end[i].trim();
      }
      if (start.length != 2 || end.length != 2) {
          System.out.println("Invalid input. Please enter two points separated by a comma.");
          return;
      }
      int x1 = Integer.parseInt(start[0]);
      int y1 = Integer.parseInt(start[1]);
      int x2 = Integer.parseInt(end[0]);
      int y2 = Integer.parseInt(end[1]);
      send(new MoveMessage(x1, y1, x2, y2));
    } catch (NumberFormatException e) {
      e.printStackTrace();
    }
  }

  private void processQuitMessage() {
    running = false;
    //receiveThread.interrupt();
    if (!socket.isClosed()) {
      send(new QuitMessage());
    }
    close();
  }

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

  public static void main(String[] args) {
      try {
          new Client("localhost", 12345);
      } catch (Exception e) {
          e.printStackTrace();
      }
  }


}
