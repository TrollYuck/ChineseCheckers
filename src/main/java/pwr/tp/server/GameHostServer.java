package pwr.tp.server;

import java.io.IOException;
import java.net.ServerSocket;

public class GameHostServer {

  private final int Port;

  public GameHostServer(int Port) {
    this.Port = Port;
    System.out.println("GameHostServer created on port " + Port);
  }

    public void start() {
        try (var serverSocket = new ServerSocket(Port)) {
            System.out.println("Server started on port " + Port);

            while (true) {
                var clientSocket = serverSocket.accept();
                System.out.println("Client connected: " + clientSocket.getInetAddress().getHostAddress());
                var clientThread = new Thread(new ClientHandler(clientSocket, this));
                clientThread.start();
            }
        } catch (IOException e) {
          e.printStackTrace();
        }
    }
}
