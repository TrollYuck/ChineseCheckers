package pwr.tp.game;

import pwr.tp.domain.Board;
import pwr.tp.domain.GamaData;
import pwr.tp.domain.IllegalFieldOnBoard;
import pwr.tp.domain.IllegalMoveException;
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

    public static void receiveMove(Move move) throws IllegalMoveException, IllegalFieldOnBoard {

    }

    public static String printBoardTypes() {
        return GamaData.printBoardTypes();
    }

    public static Lobby getInstance() {
        if(instance == null) {
            instance = new Lobby();
        }
        return instance;
    }


}
