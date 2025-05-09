package pwr.tp.domain.StarBoard;

import pwr.tp.domain.Field;
import pwr.tp.utilityClases.Pair;

import java.util.List;

/**
 * Manages the stripes on the star board.
 * This class is responsible for organizing fields into horizontal, left-skew, and right-skew stripes.
 */
public class StripeManager {
    List<Field> fields;
    List<Stripe> stripeList;

    /**
     * Constructs a StripeManager with the given fields and stripe list.
     *
     * @param fields the list of fields on the board
     * @param stripeList the list of stripes on the board
     */
    public StripeManager(List<Field> fields, List<Stripe> stripeList) {
        this.fields = fields;
        this.stripeList = stripeList;
        fillStripes();
    }

    /**
     * Finds a field by its coordinates.
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
     * Fills the stripes on the board.
     */
    public void fillStripes() {
        fillHorizontalStripes();
        fillLeftSkewStripes();
        fillRightSkewStripes();
    }

    /**
     * Fills the horizontal stripes on the board.
     */
    public void fillHorizontalStripes() {
        Stripe stripe = new Stripe();
        for(Field field: fields) {
            Pair<Integer, Integer> pair = field.getCoordinates();
            if(pair.getSecond() == 1) {
                stripe = new Stripe();
                stripeList.add(stripe);
            }
            stripe.addField(field);
        }
    }

    /**
     * Fills the left-skew stripes on the board.
     */
    public void fillLeftSkewStripes() {
        Stripe stripe;
        for(int i = 1; i < 5; i++) {
            int k = i;
            stripe = new Stripe();
            for(int j = 5; k > 0; j++) {
                stripe.addField(findFieldByCoordinates(new Pair<>(j,k)));
                k--;
            }
            stripeList.add(stripe);
        }

        for(int i = 1; i < 6; i++) {
            stripe = new Stripe();
            int k = i;
            for(int j = i; j < 14; j ++) {
                if(j == 5) {
                    k = i + 4;
                } else if(k > i) {
                    k--;
                }
                stripe.addField(findFieldByCoordinates(new Pair<>(j,k)));
            }
            stripeList.add(stripe);
        }

        for(int i = 1; i < 5; i++) {
            stripe = new Stripe();
            for(int j = 5; j < 10; j++) {
                int k = 9 + i - j + 5;
                stripe.addField(findFieldByCoordinates(new Pair<>(j,k)));
            }
            for(int j = 10; j < 14; j++) {
                int k = 5 + i;
                stripe.addField(findFieldByCoordinates(new Pair<>(j,k)));
            }
            for(int j = 1; j <= i; j++){
                int k = i + 1 - j;
                stripe.addField(findFieldByCoordinates(new Pair<>(j + 13,k)));
            }
            stripeList.add(stripe);
        }

        for(int i = 1; i < 5; i++) {
            stripe = new Stripe();
            int k = 9 + i;
            for(int j = 9 + i; j < 14; j++) {
                stripe.addField(findFieldByCoordinates(new Pair<>(j,k)));
            }
            stripeList.add(stripe);
        }
    }

    /**
     * Fills the right-skew stripes on the board.
     */
    public void fillRightSkewStripes() {
        Stripe stripe;
        for(int i = 1; i < 5; i++) {
            int k = 14 - i;
            stripe = new Stripe();
            for(int j = 1; j <= i; j++) {
                stripe.addField(findFieldByCoordinates(new Pair<>(j + 4,k)));
            }
            stripeList.add(stripe);
        }

        for(int i = 1; i < 6; i++) {
            stripe = new Stripe();
            int k = 0;
            for(int j = 1 + i - 1; j <= 4; j++) {
                k++;
                stripe.addField(findFieldByCoordinates(new Pair<>(j,k)));
            }

            k = 9 - i + 1;
            for(int j = 5; j < 10; j++) {
                stripe.addField(findFieldByCoordinates(new Pair<>(j,k)));
            }

            for(int j = 10; j < 14; j++) {
                k = j - i + 1;
                stripe.addField(findFieldByCoordinates(new Pair<>(j,k)));
            }

            stripeList.add(stripe);
        }

        for(int i = 1; i < 5; i++) {
            stripe = new Stripe();
            for(int j = 5; j < 10; j++) {
                int k = 4 - i + 1;
                stripe.addField(findFieldByCoordinates(new Pair<>(j,k)));
            }
            for(int j = 10; j < 14; j++) {
                int k = j - i - 4;
                stripe.addField(findFieldByCoordinates(new Pair<>(j,k)));
            }
            for(int j = 14; j <= 14 + i - 1; j++) {
                int k = 5 - i;
                stripe.addField(findFieldByCoordinates(new Pair<>(j,k)));
            }

            stripeList.add(stripe);
        }

        for(int i = 1; i < 5; i++) {
            stripe = new Stripe();
            int k = 0;
            for(int j = 10 + i - 1; j < 14; j ++) {
                k++;
                stripe.addField(findFieldByCoordinates(new Pair<>(j,k)));
            }
            stripeList.add(stripe);
        }
    }
}
