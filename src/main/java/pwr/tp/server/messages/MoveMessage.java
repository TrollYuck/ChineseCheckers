package pwr.tp.server.messages;

public class MoveMessage implements Message {
  private final MessageType type = MessageType.MOVE;
  private final int start_a;
  private final int start_b;
  private final int final_a;
  private final int final_b;

  public MoveMessage(int start_a, int start_b, int final_a, int final_b) {
    this.start_a = start_a;
    this.start_b = start_b;
    this.final_a = final_a;
    this.final_b = final_b;
  }

  public MessageType getType() {
    return type;
  }

  public int getStart_a() {
    return start_a;
  }

  public int getStart_b() {
    return start_b;
  }

  public int getFinal_a() {
      return final_a;
  }

  public int getFinal_b() {
      return final_b;
  }

  public int[] getStartPoint() {
      return new int[] {start_a, start_b};
  }

    public int[] getFinalPoint() {
        return new int[] {final_a, final_b};
    }

  @Override
  public String toString() {
    return "Move from " + start_a + ", " + start_b + " to " + final_a + ", " + final_b;
  }
}
