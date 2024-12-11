package pwr.tp.domain;

public class IllegalFieldOnBoard extends RuntimeException {
    public IllegalFieldOnBoard(String message) {
        super(message);
    }
}
