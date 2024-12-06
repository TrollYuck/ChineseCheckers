package pwr.tp.server.messages;

public class MoveMessage implements Message {
  private final MessageType type = MessageType.MOVE;
  private final int x;
  private final int y;

  public MoveMessage(int x, int y) {
    this.x = x;
    this.y = y;
  }

  public MessageType getType() {
    return type;
  }

  public int getX() {
    return x;
  }

  public int getY() {
    return y;
  }

  @Override
  public String toString() {
    return "MoveMessage: x=" + x + ", y=" + y;
  }
}
