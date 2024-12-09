package pwr.tp.game;

import pwr.tp.domain.Board;
import pwr.tp.domain.StarBoard.NoPlayersStarBoard;
import pwr.tp.domain.StarBoard.StarBoard;

public class CreateGame {

    public static Lobby createGame(int numOfPlayers, String boardTypeString) throws IllegalBoardTypeException, IllegalNumberOfPlayersException{
        if(boardTypeString.equalsIgnoreCase("star board")) {
            for(NoPlayersStarBoard noPlayersStarBoard: NoPlayersStarBoard.values()) {
                Board board = new StarBoard();

                if(board.getPossibleNoPlayers().contains(numOfPlayers)) {
                    return new Lobby(numOfPlayers, new StarBoard());
                }
            }
            throw new IllegalNumberOfPlayersException();
        }
        throw new IllegalBoardTypeException();
    }

}