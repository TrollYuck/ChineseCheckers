package pwr.tp.gameplay;

import pwr.tp.domain.Board;
import pwr.tp.domain.Field;
import pwr.tp.domain.Pawn;
import pwr.tp.domain.StarBoard.StarBoard;
import pwr.tp.domain.StarBoard.Stripe;
import pwr.tp.movement.Move;
import pwr.tp.utilityClases.Pair;

import java.util.List;

import static pwr.tp.movement.Move.isMoveLegal;

public class Gameplay {
    int numberOfPlayers;
    String gameState;
    List<Player> players;
    List<Stripe> stripes;
    Board board;
    public Gameplay(int numberOfPlayers, Board board) {
        this.numberOfPlayers = numberOfPlayers;
        stripes = board.getStripes();
        this.board = board;
        gameState = "not started";
        notStarted();
    }

    public void notStarted() {
        createPlayers();
        setUpPawns();
        start();
    }

    public void start() {
        Player currentPlayer;
        int pointer = 0;
        while(true) {
            currentPlayer = players.get(pointer);
            pointer++;
            //receive Move (???) Move move =
            Move move = new Move(new Pair<>(1,1), new Pair<>(2,2));
            isMoveLegal(currentPlayer, move);
        }
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
}
