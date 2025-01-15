package pwr.tp.server.messages;

/**
 * Represents a message to disconnect from a game.
 */
public class DisconnectGameMessage implements Message {
  /**
   * The type of the message.
   */
  private final MessageType type = MessageType.DISCONNECT_GAME;

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
