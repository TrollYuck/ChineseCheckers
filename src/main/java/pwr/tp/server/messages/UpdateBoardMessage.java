package pwr.tp.server.messages;

import java.util.List;

public class UpdateBoardMessage implements Message {
  private final MessageType type = MessageType.UPDATE_BOARD;
  private List<List<String>> playersPawnPositions;
  private int numberOfPlayers;

  public void update(List<List<String>> playersPawnPositions, int numberOfPlayers) {
      this.playersPawnPositions = playersPawnPositions;
      this.numberOfPlayers = numberOfPlayers;
  }

  public List<List<String>> getPlayersPawnPositions() {
      return playersPawnPositions;
  }

  public int getNumberOfPlayers() {
      return numberOfPlayers;
  }

  @Override
  public MessageType getType() {
    return type;
  }
}
