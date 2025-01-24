package pwr.tp.server.messages;

/**
 * Represents a message indicating the end of a game.
 */
public class EndGameMessage implements Message {
  /**
   * The type of the message.
   */
  private final MessageType type = MessageType.END_GAME;

  /**
   * The index of the winning player.
   */
  private final int winnerIndex;

  /**
   * Constructs an EndGameMessage with the specified winner index.
   *
   * @param winnerIndex the index of the winning player
   */
  public EndGameMessage(int winnerIndex) {
    this.winnerIndex = winnerIndex;
  }

  /**
   * Returns the index of the winning player.
   *
   * @return the index of the winning player
   */
  public int getWinnerIndex() {
    return winnerIndex;
  }

  /**
   * Returns the type of the message.
   *
   * @return the message type
   */
  @Override
  public MessageType getType() {
    return type;
  }
}
