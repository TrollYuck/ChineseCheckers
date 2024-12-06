package pwr.tp.server.messages;

import java.io.Serializable;

public interface Message extends Serializable {
  public MessageType getType();
}
