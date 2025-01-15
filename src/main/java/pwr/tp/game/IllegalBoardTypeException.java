package pwr.tp.game;

/**
 * Exception thrown when an illegal board type is encountered.
 */
public class IllegalBoardTypeException extends Exception {
    /**
     * Constructs a new IllegalBoardTypeException with {@code null} as its detail message.
     */
    public IllegalBoardTypeException() {
        super();
    }

    /**
     * Constructs a new IllegalBoardTypeException with the specified detail message.
     *
     * @param message the detail message
     */
    public IllegalBoardTypeException(String message) {
        super(message);
    }
}
