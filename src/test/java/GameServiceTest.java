import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class GameServiceTest {
  @Autowired
  private GameService gameService;

  @Test
  public void testStartNewGame() {
    SavedGame savedGame = gameService.startNewGame("TestGameType", 4);
    assertNotNull(savedGame.getGameId());
    assertEquals("TestGameType", savedGame.getGameType());
    assertEquals(4, savedGame.getPlayerCount());
  }
}