package pwr.tp.server.messages;

import pwr.tp.game.Lobby;

import java.util.List;

/**
 * Represents a message containing a list of game lobbies.
 */
public class ListGamesMessage implements Message{
  /**
   * The type of this message.
   */
  private final MessageType type = MessageType.LIST_GAMES;

  /**
   * The list of lobbies.
   */
  private List<String> lobbies;

  /**
   * Constructs a new ListGamesMessage with the specified list of lobbies.
   *
   * @param lobbies the list of lobbies
   */
  public ListGamesMessage(List<String> lobbies) {
      this.lobbies = lobbies;
  }

  /**
   * Returns the type of this message.
   *
   * @return the message type
   */
  @Override
  public MessageType getType() {
    return type;
  }

  /**
   * Returns the list of lobbies.
   *
   * @return the list of lobbies
   */
  public List<String> getLobbies() {
    return lobbies;
  }
}
