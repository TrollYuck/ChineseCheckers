package pwr.tp.game;

import pwr.tp.domain.Board;
import pwr.tp.domain.StarBoard.StarBoard;

public class SetLobby {

    public static Lobby setLobby(String boardTypeString, String numOfPlayers) throws IllegalBoardTypeException, IllegalNumberOfPlayersException {
        if (boardTypeString.equalsIgnoreCase("star board")) {
            Board board = new StarBoard();
            int intNumOfPlayers = Integer.parseInt(numOfPlayers);
            if (board.getPossibleNoPlayers().contains(intNumOfPlayers)) {
                Lobby lobby = Lobby.getInstance();
                lobby.setBoard(board);
                lobby.setNumOfPlayers(intNumOfPlayers);
                return lobby;
            }

            throw new IllegalNumberOfPlayersException();
        }
        throw new IllegalBoardTypeException();
    }

}
