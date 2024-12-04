package pwr.tp.client;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class Client {
  private Socket socket;
  private ObjectOutputStream objectOutputStream;
  private ObjectInputStream objectInputStream;
  private Scanner scannerIn;
  private boolean running = false;

  public Client(String host, int port) throws Exception {
    try {
      connect(host, port);
      Thread receiveThread = new Thread(this::receiveMessages);
      receiveThread.start();
      Thread userInputThread = new Thread(this::handleUserInput);
      userInputThread.start();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void handleUserInput() {
    while (running) {
      String message = scannerIn.nextLine();
      try {
        objectOutputStream.writeObject(message);
        objectOutputStream.flush();
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

  private void receiveMessages() {
      try {
      while (running) {
          String message = (String) objectInputStream.readObject();
          System.out.println("Server: " + message);
      }
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

  public static void main(String[] args) {
      try {
          new Client("localhost", 12345);
      } catch (Exception e) {
          e.printStackTrace();
      }
  }


}
