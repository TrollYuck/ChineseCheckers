package pwr.tp.server.messages;

import pwr.tp.movement.Move;
import pwr.tp.utilityClases.Pair;

/**
 * Represents a message to move a piece in the game.
 */
public class MoveMessage implements Message {
  /**
   * The type of the message.
   */
  private final MessageType type = MessageType.MOVE;

  /**
   * The initial position A.
   */
  private final int initialPosA;

  /**
   * The initial position B.
   */
  private final int initialPosB;

  /**
   * The final position A.
   */
  private final int finalPosA;

  /**
   * The final position B.
   */
  private final int finalPosB;

  /**
   * Indicates if the move is accepted.
   */
  private boolean isAccepted;

  /**
   * The index of the player who is making the move.
   */
  private int playerIndex;

  /**
   * Constructs a MoveMessage with the specified initial and final positions.
   *
   * @param initialPosA the initial position A
   * @param initialPosB the initial position B
   * @param finalPosA the final position A
   * @param finalPosB the final position B
   */
  public MoveMessage(int initialPosA, int initialPosB, int finalPosA, int finalPosB) {
    this.initialPosA = initialPosA;
    this.initialPosB = initialPosB;
    this.finalPosA = finalPosA;
    this.finalPosB = finalPosB;
  }

  /**
   * Constructs a MoveMessage from a Move object.
   *
   * @param move the Move object
   */
  public MoveMessage(Move move) {
    this.initialPosA = move.getInitialPosition().getFirst();
    this.initialPosB = move.getInitialPosition().getSecond();
    this.finalPosA = move.getFinalPosition().getFirst();
    this.finalPosB = move.getFinalPosition().getSecond();
  }

  /**
   * Sets whether the move is accepted.
   *
   * @param isAccepted true if the move is accepted, false otherwise
   */
  public void setAccepted(boolean isAccepted) {
      this.isAccepted = isAccepted;
  }

  /**
   * Sets the player index.
   *
   * @param playerIndex the index of the player
   */
  public void setPlayerIndex(int playerIndex) {
      this.playerIndex = playerIndex;
  }

  /**
   * Returns the type of the message.
   *
   * @return the message type
   */
  public MessageType getType() {
    return type;
  }

  /**
   * Returns the initial position as a Pair.
   *
   * @return the initial position
   */
  public Pair<Integer, Integer> getInitialPoint() {
      return new Pair<>(initialPosA, initialPosB);
  }

  /**
   * Returns the final position as a Pair.
   *
   * @return the final position
   */
  public Pair<Integer, Integer> getFinalPoint() {
      return new Pair<>(finalPosA, finalPosB);
  }
  
  /**
   * Returns the move as a Move object.
   *
   * @return the move
   */
  public Move getMove() {
    return new Move(getInitialPoint(), getFinalPoint());
  }

  /**
   * Returns whether the move is accepted.
   *
   * @return true if the move is accepted, false otherwise
   */
  public boolean isAccepted() {
    return isAccepted;
  }

  /**
   * Returns the player index.
   *
   * @return the player index
   */
  public int getPlayerIndex() {
      return playerIndex;
  }

  /**
   * Returns a string representation of the move message.
   *
   * @return a string representation of the move message
   */
  @Override
  public String toString() {
    return "Move from: " + initialPosA + ", " + initialPosB +
            " to: " + finalPosA + ", " + finalPosB;
  }
}
