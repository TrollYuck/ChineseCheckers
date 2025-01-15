package pwr.tp.gameplay;

import pwr.tp.domain.Board;
import pwr.tp.domain.Field;
import pwr.tp.domain.Pawn;
import pwr.tp.domain.StarBoard.StarBoard;
import pwr.tp.domain.StarBoard.Stripe;
import pwr.tp.movement.Move;
import pwr.tp.utilityClases.Pair;

import java.util.ArrayList;
import java.util.List;

import static pwr.tp.movement.Move.isMoveLegal;

public class Gameplay {
    private final int numberOfPlayers;
    private List<Player> players;
    private List<Stripe> stripes;
    private final Board board;
    private int currentPlayerIndex;
    private int indexOfWinner;


    public Gameplay(int numberOfPlayers, Board board) {
        this.numberOfPlayers = numberOfPlayers;
        stripes = board.getStripes();
        this.board = board;
        currentPlayerIndex = 0;
        indexOfWinner = -1;
        players = new ArrayList<>();
        setUpGame();
    }

    public int getIndexOfWinner() {
        return indexOfWinner;
    }

    public void setUpGame() {
        createPlayers();
        setUpPawns();
    }

    private boolean checkForWinningConditions() {
        boolean ok = true;
        for(int i = 0; i < 4; i ++) {
            Stripe stripe = stripes.get(i);
            for(Field field: stripe.getFieldsInRow()) {
                if(!players.get(1).getPawns().contains(new Pawn(field))) {
                    ok = false;
                    break;
                }
            }
        }
        if(ok) {
            indexOfWinner = 1;
            return true;
        }
        ok = true;

        for(int i = 13; i < 17; i ++) {
            Stripe stripe = stripes.get(i);
            for(Field field: stripe.getFieldsInRow()) {
                if(!players.getFirst().getPawns().contains(new Pawn(field))) {
                    ok = false;
                    break;
                }
            }
        }

        if(ok) {
            indexOfWinner = 0;
            return true;
        }
        ok = true;

        for(int i = 17; i < 21; i++) {
            Stripe stripe = stripes.get(i);
            for(Field field: stripe.getFieldsInRow()) {
                if(!players.get(3).getPawns().contains(new Pawn(field))) {
                    ok = false;
                    break;
                }
            }
        }

        if(ok) {
            indexOfWinner = 3;
            return true;
        }
        ok = true;


        for(int i = 30; i < 34; i++) {
            Stripe stripe = stripes.get(i);
            for(Field field: stripe.getFieldsInRow()) {
                if(!players.get(2).getPawns().contains(new Pawn(field))) {
                    ok = false;
                    break;
                }
            }
        }

        if(ok) {
            indexOfWinner = 2;
            return true;
        }
        ok = true;

        for(int i = 34; i < 38; i++) {
            Stripe stripe = stripes.get(i);
            for(Field field: stripe.getFieldsInRow()) {
                if(!players.get(5).getPawns().contains(new Pawn(field))) {
                    ok = false;
                    break;
                }
            }
        }

        if(ok) {
            indexOfWinner = 5;
            return true;
        }
        ok = true;

        for(int i = 47; i < 51; i++) {
            Stripe stripe = stripes.get(i);
            for(Field field: stripe.getFieldsInRow()) {
                if(!players.get(4).getPawns().contains(new Pawn(field))) {
                    ok = false;
                    break;
                }
            }
        }

        if(ok) {
            indexOfWinner = 4;
            return true;
        }
        return false;

    }

    private void setUpPawns() {
        if(numberOfPlayers >= 2) {
            for(int i = 0; i < 4; i ++) {
                Stripe stripe = stripes.get(i);
                for(Field field: stripe.getFieldsInRow()) {
                    players.getFirst().receivePawn(new Pawn(field));
                    field.setFull(0);
                }
            }
            for(int i = 13; i < 17; i ++) {
                Stripe stripe = stripes.get(i);
                for(Field field: stripe.getFieldsInRow()) {
                    players.get(1).receivePawn(new Pawn(field));
                    field.setFull(1);
                }
            }
        }
        if(numberOfPlayers >=3) {
            for(int i = 17; i < 21; i++) {
                Stripe stripe = stripes.get(i);
                for(Field field: stripe.getFieldsInRow()) {
                    players.get(2).receivePawn(new Pawn(field));
                    field.setFull(2);
                }
            }
        }
        if(numberOfPlayers >= 4) {
            for(int i = 30; i < 34; i++) {
                Stripe stripe = stripes.get(i);
                for(Field field: stripe.getFieldsInRow()) {
                    players.get(3).receivePawn(new Pawn(field));
                    field.setFull(3);
                }
            }
        }
        if(numberOfPlayers >= 5) {
            for(int i = 34; i < 38; i++) {
                Stripe stripe = stripes.get(i);
                for(Field field: stripe.getFieldsInRow()) {
                    players.get(4).receivePawn(new Pawn(field));
                    field.setFull(4);
                }
            }

            for(int i = 47; i < 51; i++) {
                Stripe stripe = stripes.get(i);
                for(Field field: stripe.getFieldsInRow()) {
                    players.get(5).receivePawn(new Pawn(field));
                    field.setFull(5);
                }
            }
        }
    }

    private void createPlayers() {
        for(int i = 0; i < numberOfPlayers; i++) {
            Player player = new Player();
            players.add(player);
        }
    }

    private boolean isMoveLegal(Player player, Move move){
        if(player.getPawns().contains(new Pawn(new Field(move.getInitialPosition())))) {
            Move.isMoveLegal(move, stripes);
        }
        return false;
    }

    public void receiveMove(Move move, int playerIndex) throws IllegalMoveException {
        if(playerIndex == currentPlayerIndex) {
            if(isMoveLegal(players.get(playerIndex), move)) {
                movePawn(move, playerIndex);
                currentPlayerIndex = (currentPlayerIndex + 1) % numberOfPlayers;
            } else {
                throw new IllegalMoveException("Illegal move: " + move);
            }
        }
    }

    public List<String> getPawnsFromPlayer(int playerIndex) {
        List<Pawn> pawns = players.get(playerIndex).getPawns();
        List<String> result = new ArrayList<>();
        for(Pawn pawn: pawns) {
            result.add(pawn.toString());
        }
        return result;
    }

    private void movePawn(Move move, int playerIndex) {
        Field field = board.findFieldByCoordinates(move.getInitialPosition());
        field.setEmpty();
        field = board.findFieldByCoordinates(move.getFinalPosition());
        field.setFull(playerIndex);
    }


}
