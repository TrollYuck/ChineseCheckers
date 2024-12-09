package pwr.tp.game;

import pwr.tp.domain.Board;
import pwr.tp.movement.Move;

public class Lobby {
    private static int numOfPlayers;
    private static Board board;
    private static Lobby instance;

    private Lobby(){

    }

    public Board getBoard() {
        return board;
    }

    public int getNumOfPlayers() {
        return numOfPlayers;
    }

    public void setBoard(Board board) {
        Lobby.board = board;
    }

    public void setNumOfPlayers(int numOfPlayers) {
        Lobby.numOfPlayers = numOfPlayers;
    }

    public void receiveMove(Move move) {

    }

    public static Lobby getInstance() {
        if(instance == null) {
            instance = new Lobby();
        }
        return instance;
    }


}
