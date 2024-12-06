package pwr.tp.server.messages;

public class QuitMessage implements Message {
  private final MessageType type = MessageType.QUIT;

  public MessageType getType() {
    return type;
  }
}
