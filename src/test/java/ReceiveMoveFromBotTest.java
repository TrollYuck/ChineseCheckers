import org.junit.Test;
import pwr.tp.domain.StarBoard.StarBoard;
import pwr.tp.game.Lobby;
import pwr.tp.gameplay.StandardGameplay;
import pwr.tp.movement.Move;
import pwr.tp.utilityClases.Pair;

public class ReceiveMoveFromBotTest {
    StarBoard starBoard = new StarBoard();
    Lobby lobby = new Lobby(2, starBoard, false);

    @Test
    public void receiveMoveFromBotTest() {
        lobby.startGame();
        lobby.addBot();
        lobby.receiveMove(new Move(new Pair<>(3,3 ), new Pair<>(5,7)), 0);
        System.out.println(lobby.getAllPawnCoordinates());
    }
}
