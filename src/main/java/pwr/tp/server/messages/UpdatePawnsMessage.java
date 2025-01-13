package pwr.tp.server.messages;

import java.util.List;

public class UpdatePawnsMessage implements Message{
  private final MessageType type = MessageType.UPDATE_PAWNS;

  private List<String> pawns;

  public MessageType getType() {
    return type;
  }

  public void setPawns(List<String> pawns) {
    this.pawns = pawns;
  }

  public List<String> getPawns() {
      return pawns;
  }
}
