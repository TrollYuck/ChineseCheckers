package pwr.tp.client;

import pwr.tp.game.Lobby;
import pwr.tp.server.ClientHandler;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private Socket socket;
    private BufferedWriter bufferedWriter;
    private BufferedReader bufferedReader;
    private String username;
    private Boolean observer;


    public Client(Socket socket, String username) {
        try{
            this.socket = socket;
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.username = username;
            this.observer = false;
        } catch (IOException e) {
            closeEverything();
        }

    }

    public void sendMessage() {
            try {
                bufferedWriter.write(username);
                bufferedWriter.newLine();
                bufferedWriter.flush();

                Scanner scanner = new Scanner(System.in);
                while(socket.isConnected()) {
                    String message = scanner.nextLine();
                    if(!observer) {
                        bufferedWriter.write(message);
                        bufferedWriter.newLine();
                        bufferedWriter.flush();
                    }

                }
            } catch (IOException e) {
                closeEverything();
            }

    }

    public void listenForMessage() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String messageGroupChat;

                while(socket.isConnected()) {
                    try {
                        messageGroupChat = bufferedReader.readLine();
                        if(messageGroupChat.equalsIgnoreCase("observer")) {
                            observer = true;
                            System.out.println("You've become an observer");
                        } else{
                            System.out.println(messageGroupChat);
                        }

                    } catch (IOException e) {
                        closeEverything();
                    }
                }
            }
        }).start();
    }


    public void closeEverything(){
        try{
            if(bufferedReader != null) {
                bufferedReader.close();
            }
            if(bufferedWriter != null) {
                bufferedWriter.close();
            }
            if(socket != null) {
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException{
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter your username for the game: ");
        String username = scanner.nextLine();
        Socket socket = new Socket("localhost", 12345);
        Client client = new Client(socket, username);
        client.listenForMessage();
        client.sendMessage();
    }
}
