package pwr.tp.server.messages;

public class EndGameMessage implements Message {
  private final MessageType type = MessageType.END_GAME;
  private int winnerIndex;

  public EndGameMessage(int winnerIndex) {
    this.winnerIndex = winnerIndex;
  }

  public int getWinnerIndex() {
    return winnerIndex;
  }

  @Override
  public MessageType getType() {
    return type;
  }
}
