package pwr.tp.domain;

import pwr.tp.movement.Move;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public interface Board {
    String getBoardName();
    void receiveMove(Move Move);
    List<Integer> getPossibleNoPlayers();
}
