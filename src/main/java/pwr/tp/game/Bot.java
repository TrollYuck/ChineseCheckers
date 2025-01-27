package pwr.tp.game;

import pwr.tp.domain.Field;
import pwr.tp.domain.Pawn;
import pwr.tp.domain.StarBoard.Stripe;
import pwr.tp.movement.Move;

import java.util.List;

public class Bot {

    public static Move generateMove(String gameType, List<Stripe> stripes, List<Pawn> pawns) {
        Move move = null;
        for(Pawn pawn: pawns) {
            if(gameType.equals("double base game")) {
                move = generateMoveForDoubleBaseGame(pawn.getLocation(), stripes);
            } else {
                move = generateMoveForStandardGame(pawn.getLocation(), stripes);
            }
            if(move.getFinalPosition() != null) {
                break;
            }
        }
        return move;
    }

    public static Move generateMoveForDoubleBaseGame(Field field, List<Stripe> stripes) {
        //TODO: think how to approach moving pawns in the right direction
        List <Stripe> stripeList = Move.findStripes(field.getCoordinates(), stripes);
        List<Field> fields = Move.checkingOutPossibleFieldsToMove(stripeList, field.getCoordinates(), field.getCoordinates());

        return new Move(field.getCoordinates(), fields.getFirst().getCoordinates());

    }

    public static Move generateMoveForStandardGame(Field field, List<Stripe> stripes) {
        //TODO: think how to approach moving pawns in the right direction
        List <Stripe> stripeList = Move.findStripes(field.getCoordinates(), stripes);
        List<Field> fields = Move.checkingOutPossibleFieldsToMove(stripeList, field.getCoordinates(), field.getCoordinates());

        return new Move(field.getCoordinates(), fields.getFirst().getCoordinates());
    }
}
