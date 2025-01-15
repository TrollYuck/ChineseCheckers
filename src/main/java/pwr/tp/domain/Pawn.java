package pwr.tp.domain;

import java.util.Objects;

public class Pawn {
    //to be implemented later on
    Field location;

    public Pawn(Field field) {
        this.location = field;
    }

    public Field getLocation() {
        return this.location;
    }

    public void setLocation(Field location) {
        this.location = location;
    }

    public String toString() {
        return location.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Pawn pawn)) return false;
        return Objects.equals(location, pawn.location);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(location);
    }
}
