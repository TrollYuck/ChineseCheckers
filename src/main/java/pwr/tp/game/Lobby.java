package pwr.tp.game;

import pwr.tp.domain.Board;
import pwr.tp.domain.StarBoard.StarBoard;
import pwr.tp.movement.Move;

public class Lobby {
    private static int numOfPlayers;
    private static Board board;
    private static Lobby instance;

    private Lobby(){

    }

    public static Board getBoard() {
        return board;
    }

    public static int getNumOfPlayers() {
        return numOfPlayers;
    }

    public static void setBoard(Board board) {
        Lobby.board = board;
    }

    public static void setNumOfPlayers(int numOfPlayers) {
        Lobby.numOfPlayers = numOfPlayers;
    }

    public static void receiveMove(Move move) {

    }

    public static Lobby getInstance() {
        if(instance == null) {
            instance = new Lobby();
        }
        return instance;
    }


}
