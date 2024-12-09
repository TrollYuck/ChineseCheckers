package pwr.tp.domain.StarBoard;

import pwr.tp.domain.Board;
import pwr.tp.domain.Field;
import pwr.tp.movement.Move;
import pwr.tp.utilityClases.Pair;

import java.util.*;
import java.util.stream.Collectors;

public class StarBoard implements Board {
    Set<Field> fieldSet;
    List<Integer> possibleNoPlayers = new ArrayList<>();
    public StarBoard() {
        fieldSet = new HashSet<>();
        fillBoardWithFields();
        fillPossibleNoPlayers();
        printFields();
    }

    @Override
    public void receiveMove(Move Move) {

    }
    @Override
    public List<Integer> getPossibleNoPlayers() {
        return this.possibleNoPlayers;
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
            for(int j = 1; j < i + 1; j++) {
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

    private void fillPossibleNoPlayers(){
        possibleNoPlayers.add(2);
        possibleNoPlayers.add(3);
        possibleNoPlayers.add(4);
        possibleNoPlayers.add(6);
    }

}
