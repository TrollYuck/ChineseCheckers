package pwr.tp.movement;

import pwr.tp.domain.Field;
import pwr.tp.domain.StarBoard.Stripe;
import pwr.tp.utilityClases.Pair;


import java.util.*;

/**
 * Represents a move in the game.
 */
public class Move {
    private final Pair<Integer, Integer> initialPosition;
    private final Pair<Integer, Integer> finalPosition;

    /**
     * Constructs a new Move with the specified initial and final positions.
     *
     * @param initialPosition the initial position of the move
     * @param finalPosition the final position of the move
     */
    public Move (Pair<Integer, Integer> initialPosition, Pair<Integer, Integer> finalPosition) {
        this.initialPosition = initialPosition;
        this.finalPosition = finalPosition;
    }

    /**
     * Returns the initial position of the move.
     *
     * @return the initial position
     */
    public Pair<Integer, Integer> getInitialPosition() {
        return initialPosition;
    }

    public Pair<Integer, Integer> getFinalPosition() {
        return finalPosition;
    }

    /**
     * Checks if a move is legal based on the current state of the board.
     *
     * @param move the move to be checked
     * @param stripeList the list of stripes on the board
     * @return true if the move is legal, false otherwise
     */
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

    /**
     * Finds the stripes that contain the specified coordinates.
     *
     * @param coordinates the coordinates to search for
     * @param stripeList the list of stripes to search within
     * @return a list of stripes that contain the specified coordinates
     */
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

    /**
     * Checks out possible fields to move to based on the current state of the board.
     *
     * @param stripeList the list of stripes on the board
     * @param coordinates the current coordinates of the pawn
     * @param move the move being considered
     * @param visited the list of fields that have already been visited
     * @return a list of fields that the pawn can move to
     */
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
