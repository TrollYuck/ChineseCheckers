package pwr.tp.server.messages;

public class LoadGameMessage implements Message {
  private final MessageType type = MessageType.LOAD_GAME;
  private final int gameID;

  public LoadGameMessage(int gameID) {
      this.gameID = gameID;
  }

  public int getGameID() {
      return gameID;
  }
  @Override
  public MessageType getType() {
    return type;
  }
}
