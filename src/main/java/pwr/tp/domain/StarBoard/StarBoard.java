package pwr.tp.domain.StarBoard;

import pwr.tp.domain.Board;
import pwr.tp.domain.Field;
import pwr.tp.movement.Move;
import pwr.tp.utilityClases.Pair;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Represents a Star Board in the game.
 * This class implements the Board interface and provides methods to manage the board's fields,
 * possible number of players, and stripes.
 */
public class StarBoard implements Board {
    List<Field> fields;
    List<Integer> possibleNoPlayers;
    List<Stripe> stripes;
    StripeManager stripeManager;

    /**
     * Constructs a StarBoard and initializes its fields, possible number of players, and stripes.
     */
    public StarBoard() {
        fields = new ArrayList<>();
        possibleNoPlayers = new ArrayList<>();
        fillBoardWithFields();
        fillPossibleNoPlayers();
        sortFields();

        stripes = new ArrayList<>();
        stripeManager = new StripeManager(fields, stripes);
    }

    /**
     * Returns the list of stripes on the board.
     *
     * @return the list of stripes
     */
    public List<Stripe> getStripes() {
        return stripes;
    }

    /**
     * Returns the list of fields on the board.
     *
     * @return the list of fields
     */
    public List<Field> getFields() {
        return fields;
    }

    /**
     * Receives a move and processes it.
     *
     * @param move the move to be processed
     */
    @Override
    public void receiveMove(Move move) {
        // Implementation to be added
    }

    /**
     * Returns the possible number of players for this board.
     *
     * @return the list of possible number of players
     */
    @Override
    public List<Integer> getPossibleNoPlayers() {
        return this.possibleNoPlayers;
    }

    /**
     * Returns the name of the board.
     *
     * @return the name of the board
     */
    @Override
    public String getName() {
        return "Star Board";
    }

    /**
     * Finds a field on the board by its coordinates.
     *
     * @param coordinates the coordinates of the field
     * @return the field at the given coordinates, or null if not found
     */
    public Field findFieldByCoordinates(Pair<Integer, Integer> coordinates) {
        for(Field field: fields) {
            if(field.getCoordinates().equals(coordinates)) {
                return field;
            }
        }
        return null;
    }

    /**
     * Fills the board with fields.
     */
    private void fillBoardWithFields() {
        for (int i = 1; i < 5; i++) {
            for (int j = 1; j < i + 1; j++) {
                 fields.add(new Field(new Pair<>(i, j)));
            }
        }
        for (int i = 5; i < 10; i++) {
            for (int j = 1; j < 19 - i; j++) {
                fields.add(new Field(new Pair<>(i, j)));
            }
        }
        for (int i = 10; i < 14; i++) {
            for(int j = 1; j < i + 1; j++) {
                fields.add(new Field(new Pair<>(i, j)));
            }
        }
        for (int i = 14; i < 18; i++) {
            for (int j = 1; j < 19 - i; j ++) {
                fields.add(new Field(new Pair<>(i, j)));
            }
        }
    }

    /**
     * Sorts the fields on the board.
     */
    private void sortFields() {
        fields.sort(null);
    }

    /**
     * Fills the list of possible number of players for this board.
     */
    private void fillPossibleNoPlayers(){
        possibleNoPlayers.add(2);
        possibleNoPlayers.add(3);
        possibleNoPlayers.add(4);
        possibleNoPlayers.add(6);
    }
}
