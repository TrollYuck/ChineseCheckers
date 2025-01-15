package pwr.tp.server.messages;

/**
 * Represents a message to quit the game.
 */
public class QuitMessage implements Message {
  /**
   * The type of the message.
   */
  private final MessageType type = MessageType.QUIT;

  /**
   * Returns the type of the message.
   *
   * @return the message type
   */
  public MessageType getType() {
    return type;
  }
}
