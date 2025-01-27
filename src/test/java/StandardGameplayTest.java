import org.junit.Test;
import pwr.tp.domain.Board;
import pwr.tp.domain.Pawn;
import pwr.tp.domain.StarBoard.StarBoard;
import pwr.tp.gameplay.StandardGameplay;

public class StandardGameplayTest {
    StarBoard starBoard = new StarBoard();
    StandardGameplay standardGameplay = new StandardGameplay(6, starBoard, "double base game");
    @Test
    public void checksPlayersPawns() {
        for(int i = 0; i < 3; i++) {
            for(Pawn pawn: standardGameplay.getPlayers().get(i).getPawns()) {
                System.out.println(i + " " + pawn);
            }
            System.out.println();
        }

    }
}
