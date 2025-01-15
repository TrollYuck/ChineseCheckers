package pwr.tp.domain;

public class Pawn {
    //to be implemented later on
    Field location;

    public Pawn(Field field) {
        this.location = field;
    }

    public String toString() {
        return location.toString();
    }
}
