package pwr.tp.domain.StarBoard;

import pwr.tp.domain.Field;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a stripe on the star board.
 * A stripe consists of a list of fields in a row.
 */
public class Stripe {
    private List<Field> fieldsInRow;

    /**
     * Constructs an empty Stripe.
     */
    public Stripe() {
        fieldsInRow = new ArrayList<>();
    }

    /**
     * Returns the list of fields in the row.
     *
     * @return the list of fields in the row
     */
    public List<Field> getFieldsInRow() {
        return fieldsInRow;
    }

    /**
     * Adds a field to the stripe.
     *
     * @param field the field to add
     */
    public void addField(Field field) {
        fieldsInRow.add(field);
    }
}
