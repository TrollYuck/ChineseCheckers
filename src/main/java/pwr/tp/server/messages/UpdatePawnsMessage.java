package pwr.tp.server.messages;

import java.util.List;

/**
 * Represents a message to update pawns in the game.
 */
public class UpdatePawnsMessage implements Message{
  /**
   * The type of the message.
   */
  private final MessageType type = MessageType.UPDATE_PAWNS;

  /**
   * The list of pawns to be updated.
   */
  private List<String> pawns;

  /**
   * Returns the type of the message.
   *
   * @return the message type
   */
  public MessageType getType() {
    return type;
  }

  /**
   * Sets the list of pawns to be updated.
   *
   * @param pawns the list of pawns
   */
  public void setPawns(List<String> pawns) {
    this.pawns = pawns;
  }

  /**
   * Returns the list of pawns to be updated.
   *
   * @return the list of pawns
   */
  public List<String> getPawns() {
      return pawns;
  }
}
