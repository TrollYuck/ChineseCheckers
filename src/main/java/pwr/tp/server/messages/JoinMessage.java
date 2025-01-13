package pwr.tp.server.messages;

public class JoinMessage implements Message {
  private final MessageType type = MessageType.JOIN;
  private final int uniqueLobbyNumber;

  private boolean isAccepted = false;
  private int playerIndex;

  public JoinMessage(int lobbyUniqueNumber) {
    this.uniqueLobbyNumber = lobbyUniqueNumber;
  }

  public int getUniqueLobbyNumber() {
    return uniqueLobbyNumber;
  }

  public void setAccepted(boolean succeeded) {
      this.isAccepted = succeeded;
  }

  public boolean isAccepted() {
      return isAccepted;
  }

  public void setPlayerIndex(int playerIndex) {
    this.playerIndex = playerIndex;
  }

  public int getPlayerIndex() {
      return playerIndex;
  }

  public MessageType getType() {
    return type;
  }

}
