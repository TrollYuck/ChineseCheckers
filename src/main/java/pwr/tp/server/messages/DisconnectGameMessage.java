package pwr.tp.server.messages;

/**
 * Represents a message to disconnect from a game.
 */
public class DisconnectGameMessage implements Message {
  /**
   * The type of the message.
   */
  private final MessageType type = MessageType.DISCONNECT_GAME;
  private boolean disconnected = false;

  /**
   * Returns the type of the message.
   *
   * @return the message type
   */
  @Override
  public MessageType getType() {
    return type;
  }

  /**
   * Returns whether the game is disconnected.
   *
   * @return true if the game is disconnected, false otherwise
   */
  public boolean isDisconnected() {
      return disconnected;
  }

  /**
   * Sets the disconnected status of the game.
   *
   * @param disconnected true to set the game as disconnected, false otherwise
   */
  public void setDisconnected(boolean disconnected) {
      this.disconnected = disconnected;
  }
}
