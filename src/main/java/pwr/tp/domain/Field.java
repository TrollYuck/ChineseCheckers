package pwr.tp.domain;

import pwr.tp.utilityClases.Pair;

import java.util.Comparator;
import java.util.Objects;

public class Field implements Comparable {
    Pair<Integer, Integer> coordinates;
    private boolean isEmpty = true;
    private int whichPlayersPawn = -1;

    public Field(Pair<Integer, Integer> pair) {
        coordinates = pair;
        setEmpty();
    }

    public Pair<Integer, Integer> getCoordinates() {
        return coordinates;
    }

    public String toString(){
        return coordinates.getFirst() + " " + coordinates.getSecond();
    }

    public void setEmpty() {
        isEmpty = true;
        whichPlayersPawn = 0;
    }

    public void setFull(int num) {
        isEmpty = false;
        whichPlayersPawn = num;
    }

    public boolean isEmpty() {
        return isEmpty;
    }

    @Override
    public int compareTo(Object o) {
        Field object = (Field) o;
        return this.coordinates.compareTo(object.coordinates);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Field field)) return false;
        return Objects.equals(coordinates, field.coordinates);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(coordinates);
    }
}
