package pwr.tp.server.messages;

public class JoinMessage implements Message {
  private final MessageType type = MessageType.JOIN;
  private final int uniqueLobbyNumber;

  public JoinMessage(int lobbyUniqueNumber) {
    this.uniqueLobbyNumber = lobbyUniqueNumber;
  }

  public int getUniqueLobbyNumber() {
    return uniqueLobbyNumber;
  }

  public MessageType getType() {
    return type;
  }

}
