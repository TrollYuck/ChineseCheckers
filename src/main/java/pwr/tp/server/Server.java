package pwr.tp.server;

import pwr.tp.client.Client;
import pwr.tp.game.Lobby;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private ServerSocket serverSocket;

    public Server (ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
        Lobby.getInstance();
    }

    private void startServer() {
        try{
            while(!serverSocket.isClosed()) {
                Socket socket = serverSocket.accept();
                System.out.println("A client has connected!");
                ClientHandler clientHandler = new ClientHandler(socket);

                Thread thread = new Thread(clientHandler);
                thread.start();
            }

        } catch (IOException e) {
            closeSocketServer();
        }

    }

    private void closeSocketServer() {
        try{
            if(serverSocket != null) {
                serverSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(12345);
        Server server = new Server(serverSocket);
        server.startServer();
        ///pozwolić użytkownikom pisać jednocześnie i po prostu brać tylko te dane ktore na ten moment od konkretengo użytkownika potrzebuje
        ///w klient handlerze
    }

}
