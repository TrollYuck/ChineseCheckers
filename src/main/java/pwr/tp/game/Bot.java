package pwr.tp.game;

import pwr.tp.domain.Field;
import pwr.tp.domain.Pawn;
import pwr.tp.domain.StarBoard.Stripe;
import pwr.tp.movement.Move;
import pwr.tp.utilityClases.Pair;

import java.util.List;

public class Bot {

    public static Move generateMove(int idx, String gameType, List<Stripe> stripes, List<Pawn> pawns) {
        Move move;
        for(Pawn pawn: pawns) {
            if(gameType.equals("double base game")) {
                move = generateMoveForDoubleBaseGame(pawn.getLocation(), stripes);
            } else {
                move = generateMoveForStandardGame(idx, stripes);
            }
//            if(move != null) {
//                break;
//            }
        }
        return new Move(new Pair<>(1,1), new Pair<>(1,1));
    }

    public static Move generateMoveForDoubleBaseGame(Field field, List<Stripe> stripes) {
        //TODO: think how to approach moving pawns in the right direction
        List <Stripe> stripeList = Move.findStripes(field.getCoordinates(), stripes);
        //List<Field> fields = Move.checkingOutPossibleFieldsToMove(stripeList, field.getCoordinates(), )
        return new Move(new Pair<>(1,1), new Pair<>(1,1));

    }

    public static Move generateMoveForStandardGame(int idx, List<Stripe> stripes) {
        //TODO: think how to approach moving pawns in the right direction
        return new Move(new Pair<>(1,1), new Pair<>(1,1));
    }
}
