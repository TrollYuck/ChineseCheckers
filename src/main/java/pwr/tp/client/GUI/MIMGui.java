package pwr.tp.client.GUI;

import javafx.application.Platform;
import pwr.tp.game.Lobby;
import pwr.tp.server.messages.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.List;
import java.util.Scanner;

public class MIMGui {
  private ClientMainViewController clientMainViewController;

  private Socket socket;
  private ObjectOutputStream objectOutputStream;
  private ObjectInputStream objectInputStream;
  private Scanner scannerIn;
  private boolean running = false;
  private boolean inGame = false;

  public MIMGui(ClientMainViewController clientMainViewController) {
    this.clientMainViewController = clientMainViewController;
  }

  public void start(String host, int port) throws IOException {
    Runtime.getRuntime().addShutdownHook(new Thread(() -> {
      if (running) {
        //processQuitMessage();
      }
    }));
    connect(host, port);
    Thread receiveThread = new Thread(this::receiveMessages);
    receiveThread.start();
  }

  private void connect(String host, int port) throws IOException {
    socket = new Socket(host, port);
    objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
    objectInputStream = new ObjectInputStream(socket.getInputStream());
    scannerIn = new Scanner(System.in);
    running = true;
  }

  public synchronized void close() throws IOException {
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
  }

  private void receiveMessages() {
    try {
      while (running) {
        Object message =  objectInputStream.readObject();
        if (message instanceof Message) {
          Message msg = (Message) message;
          switch (msg.getType()) {
            case LIST_GAMES:
              ListGamesMessage listGamesMessage = (ListGamesMessage) msg;
              List<String> lobbies = listGamesMessage.getLobbies();
              clientMainViewController.setAvailableGames(lobbies);
              break;
            default:
              break;
          }
        } else if (message instanceof String) {
          if (message.equals("JOIN_SUCCESS")) {
            inGame = true;
          } else if (message.equals("JOIN_FAILURE")) {
            ErrorPopUpUtil.showErrorPopUp("Failed to join game");
          } else {
            clientMainViewController.addServerInfo("Server: " + message);
          }
        }
      }
    } catch (SocketException e) {
      clientMainViewController.addServerInfo("Disconnected");
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        close();
      } catch (IOException e) {
        ErrorPopUpUtil.showErrorPopUp(e.getMessage());
      }
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

  public void createGame(int players, String gameType) {
    send(new CreateGameMessage(players, gameType));
  }

  public void quitGame() throws IOException {
    running = false;
    if (socket != null && !socket.isClosed()) {
      send(new QuitMessage());
    }
    close();
  }

  public void listGames() {
      send(new ListGamesMessage(null));
  }


}
