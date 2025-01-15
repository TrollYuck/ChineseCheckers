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
        for(Stripe stripe: starBoard.getStripes()) {
            System.out.println(stripe.getFieldsInRow());

        }
        try{
            lobby.receiveMove(new Move(new Pair<>(3, 3), new Pair<>(5,9)), 0);
            lobby.receiveMove(new Move(new Pair<>(15, 1), new Pair<>(13,5)), 1);


            System.out.println(lobby.getPawnsFromPlayer(0));
            System.out.println(lobby.getPawnsFromPlayer(1));
        } catch (IllegalMoveException e) {
            System.out.println("illegal move");
        }

    }
}
