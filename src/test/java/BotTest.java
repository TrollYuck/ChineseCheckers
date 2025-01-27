import org.junit.Test;
import pwr.tp.domain.Board;
import pwr.tp.domain.Pawn;
import pwr.tp.domain.StarBoard.StarBoard;
import pwr.tp.domain.StarBoard.Stripe;
import pwr.tp.game.Bot;
import pwr.tp.gameplay.StandardGameplay;
import pwr.tp.movement.Move;

import java.util.List;

import static org.junit.Assert.assertTrue;

public class BotTest {
    Board board = new StarBoard();
    List<Stripe> stripeList = board.getStripes();
    StandardGameplay gameplay = new StandardGameplay(6, board, "standard game");

    private void standardGameplaySetter() {
        gameplay = new StandardGameplay(6, board, "standard game");
    }

    private void DoubleBaseGameplaySetter() {
        gameplay = new StandardGameplay(3, board, "double base game");
    }

    @Test
    public void checkStandardPlayer1() {
        standardGameplaySetter();
        Move move = Bot.generateMove(stripeList, gameplay.getPlayers().getFirst().getPawns());
        System.out.println("player1 moves: " + move.getInitialPosition() + " " + move.getFinalPosition());
        assertTrue(Move.isMoveLegal(move, stripeList));
    }

    @Test
    public void checkStandardPlayer2() {
//        standardGameplaySetter();
        Move move = Bot.generateMove(stripeList, gameplay.getPlayers().get(1).getPawns());
        System.out.println("player2 moves: " + move.getInitialPosition() + " " + move.getFinalPosition());
        assertTrue(Move.isMoveLegal(move, stripeList));
    }

    @Test
    public void checkStandardPlayer3() {
//        standardGameplaySetter();
        Move move = Bot.generateMove(stripeList, gameplay.getPlayers().get(2).getPawns());
        System.out.println("player3 moves: " + move.getInitialPosition() + " " + move.getFinalPosition());
        assertTrue(Move.isMoveLegal(move, stripeList));
    }

    @Test
    public void checkStandardPlayer4() {
        standardGameplaySetter();
        Move move = Bot.generateMove(stripeList, gameplay.getPlayers().get(3).getPawns());
        System.out.println("player4 moves: " + move.getInitialPosition() + " " + move.getFinalPosition());
        assertTrue(Move.isMoveLegal(move, stripeList));
    }

    @Test
    public void checkStandardPlayer5() {
        standardGameplaySetter();
        Move move = Bot.generateMove(stripeList, gameplay.getPlayers().get(4).getPawns());
        System.out.println("player5 moves: " + move.getInitialPosition() + " " + move.getFinalPosition());
        assertTrue(Move.isMoveLegal(move, stripeList));
    }

    @Test
    public void checkStandardPlayer6() {
        standardGameplaySetter();
        Move move = Bot.generateMove(stripeList, gameplay.getPlayers().get(5).getPawns());
        System.out.println("player6 moves: " + move.getInitialPosition() + " " + move.getFinalPosition());
        assertTrue(Move.isMoveLegal(move, stripeList));
    }


}
