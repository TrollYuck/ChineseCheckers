package pwr.tp.domain.StarBoard;

import pwr.tp.domain.Board;
import pwr.tp.domain.Field;
import pwr.tp.movement.Move;
import pwr.tp.utilityClases.Pair;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

public class StarBoard implements Board {
    List<Field> fields;
    List<Integer> possibleNoPlayers;
    List<Stripe> stripes;
    StripeManager stripeManager;

    public StarBoard() {
        fields = new ArrayList<>();
        possibleNoPlayers = new ArrayList<>();
        fillBoardWithFields();
        fillPossibleNoPlayers();
        sortFields();

        stripes = new ArrayList<>();
        stripeManager = new StripeManager(fields, stripes);
    }

    public List<Stripe> getStripes() {
        return stripes;
    }
    @Override
    public void receiveMove(Move Move) {

    }
    @Override
    public List<Integer> getPossibleNoPlayers() {
        return this.possibleNoPlayers;
    }

    @Override
    public String getName() {
        return "Star Board";
    }

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

    private void sortFields() {
        fields.sort(null);
    }

    private void fillPossibleNoPlayers(){
        possibleNoPlayers.add(2);
        possibleNoPlayers.add(3);
        possibleNoPlayers.add(4);
        possibleNoPlayers.add(6);
    }

}
