package pwr.tp.game;

public class IllegalNumberOfPlayersException extends Exception {
    public IllegalNumberOfPlayersException() {
        super();
    }

    public IllegalNumberOfPlayersException(String message) {
        super(message);
    }
}
