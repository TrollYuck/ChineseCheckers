package pwr.tp.movement;

import pwr.tp.domain.Board;
import pwr.tp.domain.Field;
import pwr.tp.domain.StarBoard.Stripe;
import pwr.tp.utilityClases.Pair;


import java.util.*;

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
        List<Field> visited = new ArrayList<>();
        List<Field> queue = new ArrayList<>();

        queue.add(new Field(move.initialPosition));
        while(!queue.isEmpty()) {

            Pair<Integer, Integer> coordinates = queue.getFirst().getCoordinates();
            queue.removeFirst();
            System.out.println(coordinates);
            if(coordinates.equals(move.finalPosition)) {
                return true;
            }
            if(visited.contains(new Field(coordinates))) {
                continue;
            } else {
                visited.add(new Field(coordinates));
            }
            List<Stripe> appropriateStripes = findStripes(coordinates, stripeList);
            queue.addAll(checkingOutPossibleFieldsToMove(appropriateStripes, coordinates, move, visited));
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
        for(Stripe stripe: result) {
            System.out.println(stripe.getFieldsInRow());
        }
        return result;
    }

    private static List<Field> checkingOutPossibleFieldsToMove(List<Stripe> stripeList, Pair<Integer, Integer> coordinates, Move move, List<Field> visited) {
        List<Field> fields = new ArrayList<>();
        for(Stripe stripe: stripeList) {
            int state = 1;
            int idx = stripe.getFieldsInRow().indexOf(new Field(coordinates));
            for(int i = idx + 1; i < stripe.getFieldsInRow().size(); i++) {
                Field field = stripe.getFieldsInRow().get(i);
                System.out.println(field);
                if(idx + 1 == i && coordinates.equals(move.initialPosition) && field.isEmpty()) {
                    if(move.finalPosition.equals(field.getCoordinates())) {
                        visited.add(field);
                        fields.add(field);
                        return fields;
                    }
                    System.out.println("weird state");
                    //System.out.println(field.isEmpty());
                } else if(field.isEmpty() && state == 0) {
                    System.out.println("state 0 and field empty" );
                    visited.add(field);
                    fields.add(field);
                    state = 1;
                } else if(field.isEmpty() && state == 1) {
                    System.out.println("state 1 and field empty");
                    break;
                } else if(!field.isEmpty() && state == 0) {
                    System.out.println("state 0 and field not empty");
                    break;
                } else if(!field.isEmpty() && state == 1) {
                    System.out.println("state 1 and field not empty");
                    state = 0;
                }
            }
            state = 1;
            for(int i = idx - 1; i >= 0; i--) {
                Field field = stripe.getFieldsInRow().get(i);
                System.out.println(field);
                if(idx - 1 == i && coordinates.equals(move.initialPosition) && field.isEmpty()) {
                    if(move.finalPosition.equals(field.getCoordinates())) {
                        visited.add(field);
                        fields.add(field);
                        return fields;
                    }
                    System.out.println("weird state");
                } else if(field.isEmpty() && state == 0) {
                    System.out.println("state 0 and field empty");
                    visited.add(field);
                    fields.add(field);
                    state = 1;
                } else if(field.isEmpty() && state == 1) {
                    System.out.println("state 1 and field empty");
                    break;
                } else if(!field.isEmpty() && state == 0) {
                    System.out.println("state 0 and field not empty");
                    break;
                } else if(!field.isEmpty() && state == 1) {
                    System.out.println("state 1 and field not empty");
                    state = 0;
                }
            }

        }
        //System.out.println("fields " + fields);
        return fields;
    }


}
