package pwr.tp.game;

import pwr.tp.domain.Board;
import pwr.tp.domain.StarBoard.StarBoard;

/**
 * This class provides a method to create a Lobby with a specified number of players and board type.
 */
public class CreateLobby {

    /**
     * Creates a Lobby with the specified number of players and board type.
     *
     * @param numOfPlayers the number of players for the lobby
     * @param boardTypeString the type of the board as a string
     * @return a new Lobby instance
     * @throws IllegalBoardTypeException if the board type is not recognized
     * @throws IllegalNumberOfPlayersException if the number of players is not allowed for the specified board type
     */
    public static Lobby createLobby(int numOfPlayers, String boardTypeString, String gameType) throws IllegalBoardTypeException, IllegalNumberOfPlayersException {
        if (boardTypeString.equalsIgnoreCase("star")) {
            Board board = new StarBoard();
            if (board.getPossibleNoPlayers().contains(numOfPlayers)) {
                Lobby lobby = new Lobby(numOfPlayers, board, false);
                lobby.setGameType(gameType);
                return lobby;
            }
            throw new IllegalNumberOfPlayersException();
        }
        throw new IllegalBoardTypeException();
    }
}