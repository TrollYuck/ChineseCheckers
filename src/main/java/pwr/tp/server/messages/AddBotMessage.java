package pwr.tp.server.messages;

public class AddBotMessage implements Message {
  private final MessageType type = MessageType.ADD_BOT;
  @Override
  public MessageType getType() {
    return type;
  }
}
