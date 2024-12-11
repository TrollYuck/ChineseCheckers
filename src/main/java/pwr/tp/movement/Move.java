package pwr.tp.movement;

import pwr.tp.utilityClases.Pair;

public class Move {
    Pair<Integer, Integer> initialPosition, finalPosition;

    public Move() {

    }
    public Move(Pair<Integer, Integer> initialPosition, Pair<Integer, Integer> finalPosition) {
        this.initialPosition = initialPosition;
        this.finalPosition = finalPosition;
    }

    public void setInitialPosition(Pair<Integer, Integer> initialPosition) {
        this.initialPosition = initialPosition;
    }

    public void setFinalPosition(Pair<Integer, Integer> finalPosition) {
        this.finalPosition = finalPosition;
    }

    public String toString() {
        return "from " + initialPosition.getFirst() + "," + initialPosition.getSecond() + " to " + finalPosition.getFirst() + "," + finalPosition.getSecond();
    }
}
