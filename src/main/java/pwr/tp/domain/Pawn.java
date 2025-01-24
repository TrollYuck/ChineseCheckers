package pwr.tp.domain;

import java.util.Objects;

/**
 * Represents a pawn in the game.
 */
public class Pawn {
    /**
     * The current location of the pawn.
     */
    private Field location;

    /**
     * Constructs a Pawn with the given field location.
     *
     * @param field the initial location of the pawn
     */
    public Pawn(Field field) {
        this.location = field;
    }

    /**
     * Returns the current location of the pawn.
     *
     * @return the current location of the pawn
     */
    public Field getLocation() {
        return this.location;
    }

    /**
     * Sets the location of the pawn.
     *
     * @param location the new location of the pawn
     */
    public void setLocation(Field location) {
        this.location = location;
    }

    /**
     * Returns a string representation of the pawn.
     *
     * @return a string representation of the pawn
     */
    @Override
    public String toString() {
        return location.toString();
    }

    /**
     * Checks if this pawn is equal to another object.
     *
     * @param o the object to compare to
     * @return true if this pawn is equal to the specified object, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Pawn pawn)) return false;
        return Objects.equals(location, pawn.location);
    }

    /**
     * Returns the hash code of this pawn.
     *
     * @return the hash code of this pawn
     */
    @Override
    public int hashCode() {
        return Objects.hashCode(location);
    }
}
