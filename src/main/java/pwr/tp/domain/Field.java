package pwr.tp.domain;

import pwr.tp.utilityClases.Pair;

import java.util.Comparator;
import java.util.Objects;

/**
 * Represents a field on the board.
 */
public class Field implements Comparable {
    private Pair<Integer, Integer> coordinates;
    private boolean isEmpty = true;
    private int whichPlayersPawn = -1;

    /**
     * Constructs a Field with the given coordinates.
     *
     * @param pair the coordinates of the field
     */
    public Field(Pair<Integer, Integer> pair) {
        coordinates = pair;
        setEmpty();
    }

    /**
     * Returns the coordinates of the field.
     *
     * @return the coordinates of the field
     */
    public Pair<Integer, Integer> getCoordinates() {
        return coordinates;
    }

    /**
     * Returns the x-coordinate of the field.
     *
     * @return the x-coordinate of the field
     */
    public int getX() {
        return coordinates.getFirst();
    }

    /**
     * Returns the y-coordinate of the field.
     *
     * @return the y-coordinate of the field
     */
    public int getY() {
        return coordinates.getSecond();
    }

    /**
     * Returns a string representation of the field.
     *
     * @return a string representation of the field
     */
    public String toString(){
        return coordinates.getFirst() + " " + coordinates.getSecond();
    }

    /**
     * Sets the field to be empty.
     */
    public void setEmpty() {
        isEmpty = true;
        whichPlayersPawn = 0;
    }

    /**
     * Sets the field to be occupied by a player's pawn.
     *
     * @param num the player's number
     */
    public void setFull(int num) {
        isEmpty = false;
        whichPlayersPawn = num;
    }

    /**
     * Checks if the field is empty.
     *
     * @return true if the field is empty, false otherwise
     */
    public boolean isEmpty() {
        return isEmpty;
    }

    /**
     * Compares this field to another field based on coordinates.
     *
     * @param o the object to compare to
     * @return a negative integer, zero, or a positive integer as this field is less than, equal to, or greater than the specified field
     */
    @Override
    public int compareTo(Object o) {
        Field object = (Field) o;
        return this.coordinates.compareTo(object.coordinates);
    }

    /**
     * Checks if this field is equal to another object.
     *
     * @param o the object to compare to
     * @return true if this field is equal to the specified object, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Field field)) return false;
        return Objects.equals(coordinates, field.coordinates);
    }

    /**
     * Returns the hash code of this field.
     *
     * @return the hash code of this field
     */
    @Override
    public int hashCode() {
        return Objects.hashCode(coordinates);
    }
}
