package pwr.tp.server.messages;

public class ListGamesMessage implements Message{
  private final MessageType type = MessageType.LIST_GAMES;

  @Override
  public MessageType getType() {
    return type;
  }
}
