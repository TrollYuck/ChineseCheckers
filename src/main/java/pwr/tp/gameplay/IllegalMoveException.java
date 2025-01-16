package pwr.tp.gameplay;

/**
 * Exception thrown when an illegal move is made in the game.
 */
public class IllegalMoveException extends RuntimeException {
    /**
     * Constructs a new IllegalMoveException with the specified detail message.
     *
     * @param message the detail message
     */
  public IllegalMoveException(String message) {
    super(message);
  }
}
