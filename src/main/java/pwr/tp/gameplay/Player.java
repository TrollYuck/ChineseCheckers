package pwr.tp.gameplay;

import pwr.tp.domain.Field;
import pwr.tp.domain.Pawn;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a player in the game.
 */
public class Player {
    private final List<Pawn> pawns;

    /**
     * Constructs a new Player with an empty list of pawns.
     */
    public Player() {
        pawns = new ArrayList<>();
    }

    /**
     * Adds a pawn to the player's list of pawns.
     *
     * @param pawn the pawn to be added
     */
    public void receivePawn(Pawn pawn) {
        pawns.add(pawn);
    }

    /**
     * Returns the list of pawns owned by the player.
     *
     * @return the list of pawns
     */
    public List<Pawn> getPawns() {
        return pawns;
    }

    /**
     * Finds a pawn by its location on the field.
     *
     * @param field the field where the pawn is located
     * @return the pawn at the specified location, or null if no pawn is found
     */
    public Pawn findPawnByLocation(Field field) {
        for(Pawn pawn: pawns) {
            if(pawn.getLocation().equals(field)) {
                return pawn;
            }
        }
        return null;
    }
}
