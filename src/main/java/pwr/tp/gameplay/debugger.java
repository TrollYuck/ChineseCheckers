package pwr.tp.gameplay;

import pwr.tp.domain.Field;
import pwr.tp.domain.StarBoard.StarBoard;
import pwr.tp.domain.StarBoard.Stripe;
import pwr.tp.game.Lobby;
import pwr.tp.movement.Move;
import pwr.tp.utilityClases.Pair;

public class debugger {
    public static void main(String[] args) {
        StarBoard starBoard = new StarBoard();
        Lobby lobby = new Lobby(6, starBoard);
        lobby.startGame();
        for(Field field: starBoard.getFields()) {
            System.out.println(field + " " + field.isEmpty());
        }
        try{
            lobby.receiveMove(new Move(new Pair<>(3, 1), new Pair<>(5,5)), 0);
            System.out.println(lobby.getPawnsFromPlayer(0));
        } catch (IllegalMoveException e) {
            System.out.println("illegal move");
        }

    }
}
