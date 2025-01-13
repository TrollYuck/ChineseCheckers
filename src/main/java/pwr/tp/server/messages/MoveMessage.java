package pwr.tp.server.messages;

import pwr.tp.movement.Move;
import pwr.tp.utilityClases.Pair;

public class MoveMessage implements Message {
  private final MessageType type = MessageType.MOVE;

  private final int initialPosA;
  private final int initialPosB;
  private final int finalPosA;
  private final int finalPosB;

  private boolean isAccepted;
  private int playerIndex;

  public MoveMessage(int initialPosA, int initialPosB, int finalPosA, int finalPosB) {
    this.initialPosA = initialPosA;
    this.initialPosB = initialPosB;
    this.finalPosA = finalPosA;
    this.finalPosB = finalPosB;
  }

  public MoveMessage(Move move) {
    this.initialPosA = move.getInitialPosition().getFirst();
    this.initialPosB = move.getInitialPosition().getSecond();
    this.finalPosA = move.getFinalPosition().getFirst();
    this.finalPosB = move.getFinalPosition().getSecond();
  }

  public void setAccepted(boolean isAccepted) {
      this.isAccepted = isAccepted;
  }

  public void setPlayerIndex(int playerIndex) {
      this.playerIndex = playerIndex;
  }

  public MessageType getType() {
    return type;
  }

  public Pair<Integer, Integer> getInitialPoint() {
      return new Pair<>(initialPosA, initialPosB);
  }

  public Pair<Integer, Integer> getFinalPoint() {
      return new Pair<>(finalPosA, finalPosB);
  }
  
  public Move getMove() {
    return new Move(getInitialPoint(), getFinalPoint());
  }

  public boolean isAccepted() {
    return isAccepted;
  }

  public int getPlayerIndex() {
      return playerIndex;
  }

  @Override
  public String toString() {
    return "Move from: " + initialPosA + ", " + initialPosB +
            " to: " + finalPosA + ", " + finalPosB;
  }
}
