package pwr.tp.gameplay;

import pwr.tp.domain.Pawn;

import java.util.List;

public class Player {
    List<Pawn> pawns;

    public Player() {

    }

    public void receivePawn(Pawn pawn) {
        pawns.add(pawn);
    }

    public List<Pawn> getPawns() {
        return pawns;
    }
}
