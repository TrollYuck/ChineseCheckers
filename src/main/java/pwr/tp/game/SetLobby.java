package pwr.tp.game;

import pwr.tp.domain.Board;
import pwr.tp.domain.StarBoard.StarBoard;

public class SetLobby {

    public static void setBoard(String board) throws IllegalBoardTypeException{
        if(board.equalsIgnoreCase("starboard")) {
            Lobby.setBoard(new StarBoard());
        } else {
            throw new IllegalBoardTypeException();
        }
    }

    public static void setNumberOfPlayers(Board board, int num) throws IllegalNumberOfPlayersException{
        if(board.getPossibleNoPlayers().contains(num)) {
            Lobby.setNumOfPlayers(num);
        } else {
            throw new IllegalNumberOfPlayersException();
        }
    }
}
