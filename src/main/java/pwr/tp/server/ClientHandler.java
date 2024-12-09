package pwr.tp.server;

import pwr.tp.game.SetLobby;
import pwr.tp.game.IllegalBoardTypeException;
import pwr.tp.game.IllegalNumberOfPlayersException;
import pwr.tp.game.Lobby;
import pwr.tp.utilityClases.Pair;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ClientHandler implements Runnable{
    public static ArrayList<ClientHandler> clientHandlers = new ArrayList<>();
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private String clientUsername;
    private static int idx = 0;

    public ClientHandler(Socket socket) {
        try {
            this.socket = socket;
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.clientUsername = bufferedReader.readLine();
            clientHandlers.add(this);
            broadcastMessage("SERVER: " + clientUsername + " has joined the game!");
        } catch (IOException e) {
            closeEverything();
        }
    }

    public static int getIdx() {
        return idx;
    }

    @Override
    public void run() {
        String messageFromClient;

        while(socket.isConnected()) {
            try{
                messageFromClient = bufferedReader.readLine();
                broadcastMessage(messageFromClient);
            } catch (IOException e) {
                closeEverything();
                break;
            }
        }
    }

    public void analiseMessage(String messageFromClient) {
        if(Lobby.getBoard() == null) {
            Lobby.setBoard();
            broadcastMessage("SERVER: The board has been chosen!");
        } else if(Lobby.getNumOfPlayers() == 0) {
            Lobby.setNumOfPlayers();
            broadcastMessage("SERVER: Number of players has been chosen!");
        } else if(idx == clientHandlers.indexOf(this)) {
            Lobby.receiveMove();
            broadcastMessage("SERVER: " + clientUsername + " has moved ");
            idx++;
        }
    }

    public void broadcastMessage(String messageToSend) {
        for(ClientHandler clientHandler: clientHandlers) {
            try{
                clientHandler.bufferedWriter.write(messageToSend);
                clientHandler.bufferedWriter.newLine();
                clientHandler.bufferedWriter.flush();
            } catch (IOException e) {
                closeEverything();
            }
        }
    }

    public void removeClientHandler() {
        clientHandlers.remove(this);
        broadcastMessage("SERVER: " + clientUsername + " has left the game!");
    }

    public void closeEverything() {
        removeClientHandler();
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
}   ///z klient handlera wysyłąm do clienta co ma podać, client obiera to na dodatkowym wątku metodą listenForMessage i ze
    ///standargowego wątku wysyła odpowiedź do clientHandlera
    ///wiadomości takie jak set board albo set number of players wysyłamy broadcastem do każdego a make a move socketem
    ///chcemy żeby to raczej większość kodu znajdowałą się z client handlerze, client i server powinny być czyste
    ///to na prawdę da się ładnie zaklepać nie piszać dużo kodu wystarczy troche pomyśleć
