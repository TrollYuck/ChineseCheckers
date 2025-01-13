package pwr.tp.domain;

import pwr.tp.domain.StarBoard.Stripe;
import pwr.tp.movement.Move;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public interface Board {
    void receiveMove(Move Move);
    List<Integer> getPossibleNoPlayers();
    String getName();
    List<Stripe> getStripes();
}
