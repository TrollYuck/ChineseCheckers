package pwr.tp.domain;

import pwr.tp.movement.Move;

import java.util.HashSet;
import java.util.Set;

public interface Board {
    void receiveMove(Move Move);
}
