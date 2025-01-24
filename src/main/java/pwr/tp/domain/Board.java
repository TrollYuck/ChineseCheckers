package pwr.tp.domain;

import pwr.tp.domain.StarBoard.Stripe;
import pwr.tp.movement.Move;
import pwr.tp.utilityClases.Pair;

import java.util.List;
/**
 * Represents a board in the game.
 */
public interface Board {
    /**
     * Receives a move and processes it.
     *
     * @param move the move to process
     */
    void receiveMove(Move move);

    /**
     * Returns a list of possible numbers of players.
     *
     * @return a list of possible numbers of players
     */
    List<Integer> getPossibleNoPlayers();

    /**
     * Returns the name of the board.
     *
     * @return the name of the board
     */
    String getName();

    /**
     * Returns the list of stripes on the board.
     *
     * @return the list of stripes on the board
     */
    List<Stripe> getStripes();

    /**
     * Finds a field by its coordinates.
     *
     * @param coordinates the coordinates of the field
     * @return the field at the given coordinates, or null if not found
     */
    Field findFieldByCoordinates(Pair<Integer, Integer> coordinates);

    /**
     * Returns the list of fields on the board.
     *
     * @return the list of fields on the board
     */
    List<Field> getFields();
}
