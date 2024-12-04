package pwr.tp.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;


public class ClientHandler implements Runnable {

  private Socket clientSocket;
  private GameHostServer gameHostServer;
  private ObjectOutputStream out;
  private ObjectInputStream in;

    public ClientHandler(Socket clientSocket, GameHostServer gameHostServer) {
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
        Object object = in.readObject();
        gameHostServer.broadcast(object);
      }
    } catch (IOException | ClassNotFoundException e) {
      gameHostServer.removeClient(this);
    }
  }

  public void send(Object object) {
    try {
      out.writeObject(object);
      out.flush();
    } catch (IOException ioe) {
      ioe.printStackTrace();
    }
  }
}
