package pwr.tp.server.messages;

public class CreateGameMessage implements Message {
  private final MessageType type = MessageType.CREATE_GAME;
  private final int numOfPlayers;
  private final String boardType;

  public CreateGameMessage(int numOfPlayers, String boardType){
    this.numOfPlayers = numOfPlayers;
    this.boardType = boardType;
  }

  public int getNumOfPlayers() {
    return numOfPlayers;
  }

  public String getBoardType() {
    return boardType;
  }

  @Override
  public MessageType getType() {
    return type;
  }
}
