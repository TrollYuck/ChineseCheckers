package pwr.tp.game;

/**
 * Exception thrown when an illegal number of players is encountered.
 */
public class IllegalNumberOfPlayersException extends Exception {
    /**
     * Constructs a new IllegalNumberOfPlayersException with {@code null} as its detail message.
     */
    public IllegalNumberOfPlayersException() {
        super();
    }

    /**
     * Constructs a new IllegalNumberOfPlayersException with the specified detail message.
     *
     * @param message the detail message
     */
    public IllegalNumberOfPlayersException(String message) {
        super(message);
    }
}
