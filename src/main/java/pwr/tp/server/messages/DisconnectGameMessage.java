package pwr.tp.server.messages;

public class DisconnectGameMessage implements Message {
  private final MessageType type = MessageType.DISCONNECT_GAME;
  @Override
  public MessageType getType() {
    return type;
  }
}
