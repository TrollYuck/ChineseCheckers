package pwr.tp.server.messages;

public class JoinMessage implements Message {
  private final MessageType type = MessageType.JOIN;
  private final String name;

  public JoinMessage(String name) {
    this.name = name;
  }

  public MessageType getType() {
    return type;
  }

  public String getName() {
    return name;
  }
}
