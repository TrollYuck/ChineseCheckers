package pwr.tp.server;

import pwr.tp.game.CreateLobby;
import pwr.tp.game.IllegalBoardTypeException;
import pwr.tp.game.IllegalNumberOfPlayersException;
import pwr.tp.game.Lobby;
import pwr.tp.movement.Move;
import pwr.tp.utilityClases.Pair;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ClientHandler implements Runnable{
    private Socket socket;
    private static List<ClientHandler> clientHandlers;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private String username;
    private static Lobby lobby;
    private static int idx;

    public ClientHandler (Socket socket) {
        try{
            this.socket = socket;
            clientHandlers = new ArrayList<>();
            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            idx = 1;
            username = bufferedReader.readLine();

            clientHandlers.add(this);
            broadcastMessage("SERVER: Client " + username + "has joined the lobby");
            if(clientHandlers.getFirst() == this) {
                initializeLobby();
                broadcastMessage("SERVER: Lobby has been created with " + lobby.getBoard().getBoardName() + "and " + lobby.getNumOfPlayers() + "players!");
            }

        } catch (IOException e) {
            closeEverything();
        }

    }

    public void broadcastMessage(String message) {
        for(ClientHandler clientHandler: clientHandlers) {
            try{
                if(clientHandler != this) {
                    bufferedWriter.write(message);
                    bufferedWriter.newLine();
                    bufferedWriter.flush();
                }
            } catch (IOException e) {
                closeEverything();
            }

        }
    }

    public void initializeLobby() {
        while(lobby != null) {
            try{
                Pair<String, String> pair = receiveGameSettingsData();
                lobby = CreateLobby.createLobby(pair.getFirst(), pair.getSecond());
            } catch (IllegalBoardTypeException e) {
                System.out.println("Such board type doesn't exist");
            } catch (IllegalNumberOfPlayersException e) {
                System.out.println("Game on this board can't be played with this many players");
            } catch (IOException e) {
                closeEverything();
            }
        }
    }

    private Pair<String, String> receiveGameSettingsData() throws IOException {
        String boardName = bufferedReader.readLine();
        String nOPlayers = bufferedReader.readLine();
        return new Pair<>(boardName, nOPlayers);
    }


    private void closeEverything() {
        removeClientHandler();
        try{
            if(socket != null) {
                socket.close();
            }
            if(bufferedReader != null) {
                bufferedReader.close();
            }
            if(bufferedWriter != null) {
                bufferedWriter.close();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void removeClientHandler() {
        clientHandlers.remove(this);
        broadcastMessage("SERVER: " + username + "has left the game!");
    }

    @Override
    public void run() {
        while(lobby.getNumOfPlayers() == clientHandlers.size()) {
            try{
                if(clientHandlers.get(idx) == this) {
                    String initialPos = bufferedReader.readLine();
                    String finalPos = bufferedReader.readLine();
                    broadcastMessage(username + ": moves from " + initialPos + " to " + finalPos);
                }
                idx ++;
            } catch (IOException e) {
                closeEverything();
            }
        }
    }
}
