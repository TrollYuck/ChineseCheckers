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
        Lobby lobby = new Lobby(2, starBoard);
        lobby.startGame();

//        for(Field field: starBoard.getFields()) {
//            System.out.println(field + " " + field.isEmpty());
//        }
//        for(Stripe stripe: starBoard.getStripes()) {
//            System.out.println(stripe.getFieldsInRow());
//        }

        try{
//            lobby.receiveMove(new Move(new Pair<>(4, 4), new Pair<>(5,9)), 0);
//            lobby.receiveMove(new Move(new Pair<>(15, 1), new Pair<>(13,5)), 1);
//            lobby.receiveMove(new Move(new Pair<>(2, 2), new Pair<>(6,9)), 0);

            lobby.receiveMove(new Move(new Pair<>(4, 3), new Pair<>(5,7)), 0);
            lobby.receiveMove(new Move(new Pair<>(15, 1), new Pair<>(13,5)), 1);
            lobby.receiveMove(new Move(new Pair<>(2, 1), new Pair<>(6,6)), 0);


            System.out.println(lobby.getPawnsFromPlayer(0));
            System.out.println(lobby.getPawnsFromPlayer(1));
        } catch (IllegalMoveException e) {
            System.out.println("illegal move");
        }

    }
}
