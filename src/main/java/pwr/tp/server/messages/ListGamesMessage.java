package pwr.tp.server.messages;

import pwr.tp.game.Lobby;

import java.util.List;

public class ListGamesMessage implements Message{
  private final MessageType type = MessageType.LIST_GAMES;
  private List<String> lobbies;

  public ListGamesMessage(List<String> lobbies) {
      this.lobbies = lobbies;
  }

  @Override
  public MessageType getType() {
    return type;
  }

  public List<String> getLobbies() {
    return lobbies;
  }

}
