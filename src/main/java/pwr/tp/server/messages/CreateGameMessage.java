package pwr.tp.server.messages;

/**
 * Represents a message to create a game.
 */
public class CreateGameMessage implements Message {
  private final MessageType type = MessageType.CREATE_GAME;
  private final int numOfPlayers;
  private final String boardType;

  /**
   * Constructs a CreateGameMessage with the specified number of players and board type.
   *
   * @param numOfPlayers the number of players for the game
   * @param boardType the type of the board for the game
   */
  public CreateGameMessage(int numOfPlayers, String boardType){
    this.numOfPlayers = numOfPlayers;
    this.boardType = boardType;
  }

  /**
   * Returns the number of players for the game.
   *
   * @return the number of players
   */
  public int getNumOfPlayers() {
    return numOfPlayers;
  }

  /**
   * Returns the type of the board for the game.
   *
   * @return the board type
   */
  public String getBoardType() {
    return boardType;
  }

  /**
   * Returns the type of the message.
   *
   * @return the message type
   */
  @Override
  public MessageType getType() {
    return type;
  }
}
