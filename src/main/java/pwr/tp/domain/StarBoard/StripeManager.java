package pwr.tp.domain.StarBoard;

import pwr.tp.domain.Field;
import pwr.tp.utilityClases.Pair;

import javax.swing.text.html.FormView;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class StripeManager {
    List<Field> fields;
    List<Stripe> stripeList;

    public StripeManager(List<Field> fields, List<Stripe> stripeList) {
        this.fields = fields;
        this.stripeList = stripeList;
        fillStripes();
    }

    public void fillStripes() {
        fillHorizontalStripes();
        fillLeftSkewStripes();
        fillRightSkewStripes();

    }

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

    public void fillLeftSkewStripes() {
        //toDo: just go through all cases, it'll be similar to horizontal stripes case
        Stripe stripe = new Stripe();
        for(int i = 1; i < 5; i++) {
            int k = i;
            stripe = new Stripe();
            for(int j = 5; k > 0; j++) {
                stripe.addField(new Field(new Pair<>(j,k)));
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
                stripe.addField(new Field(new Pair<>(j, k)));
            }
            stripeList.add(stripe);
        }

    }

    public void fillRightSkewStripes() {
        //toDo: just go through all cases, it'll be similar to horizontal stripes case
    }
}
