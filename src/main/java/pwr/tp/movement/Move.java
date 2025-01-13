package pwr.tp.movement;

import pwr.tp.domain.Field;
import pwr.tp.domain.StarBoard.Stripe;
import pwr.tp.utilityClases.Pair;

import java.lang.classfile.attribute.LineNumberInfo;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

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

    public static boolean isMoveLegal(Move move, List<Stripe> stripeList) {
        List<Field> queue = new ArrayList<>();
        queue.add(new Field(move.initialPosition));
        while(!queue.isEmpty()) {
            Pair<Integer, Integer> coordinates = queue.getFirst().getCoordinates();
            queue.removeFirst();
            if(coordinates.equals(move.finalPosition)) {
                return true;
            }
            List<Stripe> appropriateStripes = findStripes(coordinates, stripeList);
            queue.addAll(checkingOutPossibleFieldsToMove(appropriateStripes, coordinates));
        }
        return false;
    }

    private static List<Stripe> findStripes(Pair<Integer, Integer> coordinates, List<Stripe> stripeList) {
        List<Stripe> result = new ArrayList<>();
        for(Stripe stripe: stripeList) {
            for(Field field: stripe.getFieldsInRow()) {
                if(coordinates.equals(field.getCoordinates())) {
                    result.add(stripe);
                }
            }
        }
        return result;
    }

    private static List<Field> checkingOutPossibleFieldsToMove(List<Stripe> stripeList, Pair<Integer, Integer> coordinates) {
        List<Field> fields = new ArrayList<>();
        for(Stripe stripe: stripeList) {
            int state = -1;
            int idx = stripe.getFieldsInRow().indexOf(coordinates);
            for(int i = idx + 1; i < stripe.getFieldsInRow().size(); i++) {
                if(stripe.getFieldsInRow().get(idx).isEmpty() && state != 1) {
                    fields.add(stripe.getFieldsInRow().get(idx));
                    state = 1;
                } else if(stripe.getFieldsInRow().get(idx).isEmpty() && state == 1) {
                    break;
                } else if(!stripe.getFieldsInRow().get(idx).isEmpty() && state != 0) {
                    state = 0;
                } else if(!stripe.getFieldsInRow().get(idx).isEmpty() && state == 0) {
                    break;
                }
            }
        }

        return fields;
    }


}
