package pwr.tp.domain.StarBoard;

import pwr.tp.domain.Field;

import java.util.ArrayList;
import java.util.List;

public class Stripe {
    private List<Field> fieldsInRow;

    public Stripe() {
        fieldsInRow = new ArrayList<>();
    }

    public List<Field> getFieldsInRow() {
        return fieldsInRow;
    }

    public void addField(Field field) {
        fieldsInRow.add(field);
    }
}
