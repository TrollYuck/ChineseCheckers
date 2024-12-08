package pwr.tp.server;

import pwr.tp.server.messages.Message;
import pwr.tp.server.messages.MoveMessage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;


public class ClientHandler implements Runnable {

  private Socket clientSocket;
  private Server gameHostServer;
  private ObjectOutputStream out;
  private ObjectInputStream in;

    public ClientHandler(Socket clientSocket, Server gameHostServer) {
        this.clientSocket = clientSocket;
        this.gameHostServer = gameHostServer;
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
      default:
        send("Unknown message type");
        break;
    }
  }

  private void processJoinMessage(Message msg) {
      //TODO: implement when the game is ready (game state etc.)
  }

  private void processMoveMessage(Message msg) {
    MoveMessage moveMessage = (MoveMessage) msg;
    String move = moveMessage.toString();
    move = move + " from player " + gameHostServer.getPlayerIndex(this);
    gameHostServer.sendToAllExcept(move, this);
  }

  private void processQuitMessage(Message msg) {
    gameHostServer.removeClient(this);
    gameHostServer.broadcast("Player " + gameHostServer.getPlayerIndex(this) + " disconnected");
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


}
