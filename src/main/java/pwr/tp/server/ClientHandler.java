package pwr.tp.server;

import pwr.tp.domain.IllegalFieldOnBoard;
import pwr.tp.domain.IllegalMoveException;
import pwr.tp.game.SetLobby;
import pwr.tp.game.IllegalBoardTypeException;
import pwr.tp.game.IllegalNumberOfPlayersException;
import pwr.tp.game.Lobby;
import pwr.tp.movement.Move;
import pwr.tp.utilityClases.Pair;

import java.io.*;
import java.lang.reflect.ParameterizedType;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ClientHandler implements Runnable{
    public static ArrayList<ClientHandler> clientHandlers = new ArrayList<>();
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private String clientUsername;
    private int myIdx;
    private static int idx;


    public ClientHandler(Socket socket) {
        try {
            this.socket = socket;
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.clientUsername = bufferedReader.readLine();
            clientHandlers.add(this);
            myIdx();
            if(Lobby.getNumOfPlayers() < clientHandlers.size() && Lobby.getNumOfPlayers() != 0) {
                broadcastMessage("SERVER: " + clientUsername + " has joined the game (as observer)!");
                printToSocket("observer");
            } else{
                broadcastMessage("SERVER: " + clientUsername + " has joined the game!");
            }

            printSetBoardMessage();
            printSetNumberOfPlayersMessage();
            if(Lobby.getNumOfPlayers() == clientHandlers.size()){
                printStartGameMessage();
                printTypeMove(clientHandlers.get(idx));
            } else if(Lobby.getNumOfPlayers() != 0 && Lobby.getNumOfPlayers() > clientHandlers.size()) {
                printToAllWaitingForPlayersMessage();
            }


        } catch (IOException e) {
            closeEverything();
        }
    }

    private void myIdx() {
        for(ClientHandler clientHandler: clientHandlers) {
            if(clientHandler == this) {
                myIdx = clientHandlers.indexOf(clientHandler);
            }
        }
    }

    @Override
    public void run() {
        String messageFromClient;

        while(socket.isConnected()) {
            try{
//                messagesToPrint(this);
                messageFromClient = bufferedReader.readLine();
                analiseMessage(messageFromClient);
            } catch (IOException e) {
                closeEverything();
                break;
            }
        }
    }

    public void analiseMessage(String messageFromClient) {
        if(Lobby.getBoard() == null) {
            try{
                SetLobby.setBoard(messageFromClient);
                broadcastMessage("SERVER: The board has been chosen!");
                printSetNumberOfPlayersMessage();
            } catch(IllegalBoardTypeException e) {
                printToSocket(messageFromClient + " as board type doesn't exist");
            }
        } else if(Lobby.getNumOfPlayers() == 0) {
            try{
                int num = Integer.parseInt(messageFromClient);
                SetLobby.setNumberOfPlayers(Lobby.getBoard(), num);
                broadcastMessage("SERVER: Number of players has been set to " + Lobby.getNumOfPlayers());
                if(clientHandlers.size() >= Lobby.getNumOfPlayers()) {
                    for(int i = Lobby.getNumOfPlayers(); i < clientHandlers.size(); i++) {
                        printToSocket("observer", clientHandlers.get(i));
                    }
                    printStartGameMessage();
                    printTypeMove(clientHandlers.get(idx));
                } else {
                    printToAllWaitingForPlayersMessage();
                }
            } catch (NumberFormatException e) {
                printToSocket(messageFromClient + " is not a number");
            } catch (IllegalNumberOfPlayersException e ) {
                printToSocket(messageFromClient + " is not appropriate number of players for this board");
            }
        } else if(clientHandlers.size() < Lobby.getNumOfPlayers()) {
            printWaitForPlayersMessage();
        } else if(idx == clientHandlers.indexOf(this)) {
            try{
                String[] separated = messageFromClient.split(";");
                String[] initPos = separated[0].split(",");
                String[] finalPos = separated[1].split(",");
                Move move = new Move();
                move.setInitialPosition(new Pair<>(Integer.parseInt(initPos[0]), Integer.parseInt(initPos[1])));
                move.setFinalPosition(new Pair<>(Integer.parseInt(finalPos[0]), Integer.parseInt(finalPos[1])));
                Lobby.receiveMove(move);
                broadcastMessage("SERVER: " + clientUsername + " has moved " + move);
                idx++;
                idx %= Lobby.getNumOfPlayers();
            } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                printToSocket("bad move input");
            } catch (IllegalFieldOnBoard e) {
                printToSocket("bad initial or final position");
            } catch (IllegalMoveException e) {
                printToSocket("such move can't be made");
            }
            printTypeMove(clientHandlers.get(idx));
        } else {
            printWaitForYourTurnMessage();
        }
    }

    private void printSetBoardMessage() {
        if(Lobby.getBoard() == null) {
            printToSocket("Set the board type");
            printToSocket(Lobby.printBoardTypes());
        }
    }

    private void printSetNumberOfPlayersMessage() {
        if(Lobby.getNumOfPlayers() == 0) {
            broadcastMessage("Set the number of players for " + Lobby.getBoard().getBoardName());
        }
    }

    private void printTypeMove(ClientHandler clientHandler) {
        if(idx == clientHandler.myIdx) {
            printToSocket("Type your move in format a,b;c,d where all are integers e.g. '1,1;5,3'", clientHandler);
        }
    }

    private void printWaitForPlayersMessage() {
        printToSocket("Wait for players (" + clientHandlers.size() + "/" + Lobby.getNumOfPlayers() + ")");
    }

    private void printToAllWaitingForPlayersMessage() {
        broadcastMessage("Waiting for players (" + clientHandlers.size() + "/" + Lobby.getNumOfPlayers() + ")");
    }

    private void printWaitForYourTurnMessage() {
        printToSocket("Wait for your turn");
    }

    private void printStartGameMessage() {
        broadcastMessage("SERVER: All players have joined, game is starting right now!");
    }

    public void broadcastMessage(String messageToSend) {
        for(ClientHandler clientHandler: clientHandlers) {
            printToSocket(messageToSend, clientHandler);
        }
    }

    private void printToSocket(String messageToSend, ClientHandler clientHandler) {
        try{
            clientHandler.bufferedWriter.write(messageToSend);
            clientHandler.bufferedWriter.newLine();
            clientHandler.bufferedWriter.flush();
        } catch (IOException e) {
            closeEverything();
        }
    }

    private void printToSocket(String messageToSend) {
        try{
            bufferedWriter.write(messageToSend);
            bufferedWriter.newLine();
            bufferedWriter.flush();
        } catch (IOException e) {
            closeEverything();
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
