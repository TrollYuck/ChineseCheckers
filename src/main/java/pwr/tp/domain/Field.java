package pwr.tp.domain;

import pwr.tp.utilityClases.Pair;

import java.util.Comparator;

public class Field implements Comparable {
    Pair<Integer, Integer> coordinates;

    public Field(Pair<Integer, Integer> pair) {
        coordinates = pair;
    }

    public String toString(){
        return coordinates.getFirst() + " " + coordinates.getSecond();
    }

    @Override
    public int compareTo(Object o) {
        Field object = (Field) o;
        return this.coordinates.compareTo(object.coordinates);
    }
}
