package pwr.tp.server.messages;

import java.io.Serializable;

/**
 * Represents a message that can be serialized and has a type.
 */
public interface Message extends Serializable {
  /**
   * Returns the type of this message.
   *
   * @return the message type
   */
  public MessageType getType();
}
