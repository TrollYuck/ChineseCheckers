package pwr.tp.game;

import pwr.tp.domain.Board;
import pwr.tp.domain.StarBoard.StarBoard;

public class CreateLobby {

    public static Lobby createLobby(int numOfPlayers, String boardTypeString) throws IllegalBoardTypeException, IllegalNumberOfPlayersException {
        if (boardTypeString.equalsIgnoreCase("star board")) {
            Board board = new StarBoard();
            if (board.getPossibleNoPlayers().contains(numOfPlayers)) {
                return new Lobby(numOfPlayers, new StarBoard());
            }

            throw new IllegalNumberOfPlayersException();
        }
        throw new IllegalBoardTypeException();
    }

}
