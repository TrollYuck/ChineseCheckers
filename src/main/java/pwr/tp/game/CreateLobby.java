package pwr.tp.game;

import pwr.tp.domain.Board;
import pwr.tp.domain.StarBoard.StarBoard;

public class CreateLobby {

    public static Lobby createLobby(String boardTypeString, String numOfPlayers) throws IllegalBoardTypeException, IllegalNumberOfPlayersException {
        if (boardTypeString.equalsIgnoreCase("star board")) {
            Board board = new StarBoard();
            int intNumOfPlayers = Integer.parseInt(numOfPlayers);
            if (board.getPossibleNoPlayers().contains(intNumOfPlayers)) {
                return new Lobby(intNumOfPlayers, new StarBoard());
            }

            throw new IllegalNumberOfPlayersException();
        }
        throw new IllegalBoardTypeException();
    }

}
