package pwr.tp.server.messages;

/**
 * Represents a message to join a game.
 */
public class JoinMessage implements Message {
  /**
   * The type of the message.
   */
  private final MessageType type = MessageType.JOIN;

  /**
   * The unique lobby number.
   */
  private final int uniqueLobbyNumber;

  /**
   * Indicates if the join request is accepted.
   */
  private boolean isAccepted = false;

  /**
   * The index of the player.
   */
  private int playerIndex;

  private String gameType;

  /**
   * Constructs a JoinMessage with the specified unique lobby number.
   *
   * @param lobbyUniqueNumber the unique lobby number
   */
  public JoinMessage(int lobbyUniqueNumber) {
    this.uniqueLobbyNumber = lobbyUniqueNumber;
  }

  /**
   * Returns the unique lobby number.
   *
   * @return the unique lobby number
   */
  public int getUniqueLobbyNumber() {
    return uniqueLobbyNumber;
  }

  /**
   * Sets whether the join request is accepted.
   *
   * @param succeeded true if the join request is accepted, false otherwise
   */
  public void setAccepted(boolean succeeded) {
      this.isAccepted = succeeded;
  }

  /**
   * Returns whether the join request is accepted.
   *
   * @return true if the join request is accepted, false otherwise
   */
  public boolean isAccepted() {
      return isAccepted;
  }

  /**
   * Sets the player index.
   *
   * @param playerIndex the index of the player
   */
  public void setPlayerIndex(int playerIndex) {
    this.playerIndex = playerIndex;
  }

  /**
   * Returns the player index.
   *
   * @return the player index
   */
  public int getPlayerIndex() {
      return playerIndex;
  }

  public String getGameType() {
    return gameType;
  }

  public void setGameType(String gameType) {
    this.gameType = gameType;
  }

  /**
   * Returns the type of the message.
   *
   * @return the message type
   */
  public MessageType getType() {
    return type;
  }

}
