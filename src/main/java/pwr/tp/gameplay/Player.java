package pwr.tp.gameplay;

import pwr.tp.domain.Field;
import pwr.tp.domain.Pawn;

import java.util.ArrayList;
import java.util.List;

public class Player {
    List<Pawn> pawns;

    public Player() {
        pawns = new ArrayList<>();
    }

    public void receivePawn(Pawn pawn) {
        pawns.add(pawn);
    }

    public List<Pawn> getPawns() {
        return pawns;
    }

    public Pawn findPawnByLocation(Field field) {
        for(Pawn pawn: pawns) {
            if(pawn.getLocation().equals(field)) {
                return pawn;
            }
        }
        return null;
    }
}
