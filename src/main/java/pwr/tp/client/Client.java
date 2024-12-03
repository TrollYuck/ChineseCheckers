package pwr.tp.client;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class Client {
  private final Socket socket;
  private ObjectOutputStream objectOutputStream;
  private ObjectInputStream objectInputStream;
  private Scanner scannerIn;

    public Client(String host, int port) throws Exception {
        socket = new Socket(host, port);
        objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
        objectOutputStream.flush();
        objectInputStream = new ObjectInputStream(socket.getInputStream());
        scannerIn = new Scanner(System.in);
    }
}
