package pwr.tp.domain;

import pwr.tp.movement.Move;
import pwr.tp.utilityClases.Pair;

import java.util.*;
import java.util.stream.Collectors;

public class StarBoard implements Board{
    Set<Field> fieldSet;
    public StarBoard() {
        fieldSet = new HashSet<>();
        fillBoardWithFields();
        printFields();
    }

    @Override
    public void receiveMove(Move Move) {

    }

    private void fillBoardWithFields() {
        for (int i = 1; i < 5; i++) {
            for (int j = 1; j < i + 1; j++) {
                fieldSet.add(new Field(new Pair<>(i, j)));
            }
        }
        for (int i = 5; i < 10; i++) {
            for (int j = 1; j < 19 - i; j++) {
                fieldSet.add(new Field(new Pair<>(i, j)));
            }
        }
        for (int i = 10; i < 14; i++) {
            for(int j = 1; j < i +1; j++) {
                fieldSet.add(new Field(new Pair<>(i, j)));
            }

        }
        for (int i = 14; i < 18; i++) {
            for (int j = 1; j < 19 - i; j ++) {
                fieldSet.add(new Field(new Pair<>(i, j)));
            }
        }
    }

    private void printFields() {
        List<Field> arr = fieldSet.stream().sorted().collect(Collectors.toList());
        System.out.println(arr);
    }

}
