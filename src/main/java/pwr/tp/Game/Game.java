package pwr.tp.Game;

import pwr.tp.domain.Board;
import pwr.tp.domain.BoardTypes;

public class Game {
    int numOfPlayers;
    Board board;
    public Game(int numOfPlayers, Board board){
        this.board = board;
        this.numOfPlayers = numOfPlayers;
    }

}
