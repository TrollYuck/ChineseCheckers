package pwr.tp.server.messages;

/**
 * Enum representing the different types of messages that can be sent.
 */
public enum MessageType {
  /**
   * Message type for joining a game.
   */
  JOIN,

  /**
   * Message type for making a move in a game.
   */
  MOVE,

  /**
   * Message type for quitting a game.
   */
  QUIT,

  /**
   * Message type for listing available games.
   */
  LIST_GAMES,

  /**
   * Message type for creating a new game.
   */
  CREATE_GAME,

  /**
   * Message type for starting a game.
   */
  START_GAME,

  /**
   * Message type for ending a game.
   */
  END_GAME,

  /**
   * Message type for disconnecting from a game.
   */
  DISCONNECT_GAME,

  /**
   * Message type for updating pawns in a game.
   */
  UPDATE_PAWNS
}
