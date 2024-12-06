package pwr.tp.game;

import pwr.tp.domain.Board;
import pwr.tp.movement.Move;

public class Lobby {
    int numOfPlayers;
    int currentNumOfPlayers;
    Board board;
    public Lobby(int numOfPlayers, Board board){
        this.board = board;
        this.numOfPlayers = numOfPlayers;
    }

    public void receiveMove(Move move) {

    }



}
