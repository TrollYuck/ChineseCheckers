package pwr.tp.movement;

import pwr.tp.utilityClases.Pair;

public class Move {
    Pair<Integer, Integer> initialPosition, finalPosition;

    public Move (Pair<Integer, Integer> initialPosition, Pair<Integer, Integer> finalPosition) {
        this.initialPosition = initialPosition;
        this.finalPosition = finalPosition;
    }

    public Pair<Integer, Integer> getInitialPosition() {
        return initialPosition;
    }

    public Pair<Integer, Integer> getFinalPosition() {
        return finalPosition;
    }

}
