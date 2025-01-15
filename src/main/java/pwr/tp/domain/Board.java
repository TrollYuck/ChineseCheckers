package pwr.tp.domain;

import pwr.tp.domain.StarBoard.Stripe;
import pwr.tp.movement.Move;
import pwr.tp.utilityClases.Pair;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public interface Board {
    void receiveMove(Move Move);
    List<Integer> getPossibleNoPlayers();
    String getName();
    List<Stripe> getStripes();
    Field findFieldByCoordinates(Pair<Integer, Integer> coordinates);
    List<Field> getFields();
}
