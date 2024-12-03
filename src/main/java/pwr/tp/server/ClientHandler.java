package pwr.tp.server;

import java.net.Socket;

public class ClientHandler implements Runnable {

  private Socket clientSocket;
  private GameHostServer gameHostServer;

    public ClientHandler(Socket clientSocket, GameHostServer gameHostServer) {
        this.clientSocket = clientSocket;
        this.gameHostServer = gameHostServer;
    }

  @Override
  public void run() {

  }
}
