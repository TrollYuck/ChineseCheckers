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
    int numberOfPlayers;
    String gameState;
    List<Player> players = new ArrayList<>();
    List<Stripe> stripes;
    Board board;
    int currentPlayerIndex;

    public Gameplay(int numberOfPlayers, Board board) {
        this.numberOfPlayers = numberOfPlayers;
        stripes = board.getStripes();
        this.board = board;
        gameState = "not started";
        currentPlayerIndex = 0;
        notStarted();
    }

    public void notStarted() {
        createPlayers();
        setUpPawns();
        //start();
    }

    //redundant method
    //TODO: remove,
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

    public boolean isMoveLegal(Player player, Move move){
        if(player.getPawns().contains(new Pawn(new Field(move.getInitialPosition())))) {
            Move.isMoveLegal(move, stripes);
        }
        return false;
    }

    public void receiveMove(Move move, int playerIndex) throws IllegalMoveException {
        if(playerIndex == currentPlayerIndex) {
            if(isMoveLegal(players.get(playerIndex), move)) {
                //movePawn(move);
                currentPlayerIndex = (currentPlayerIndex + 1) % numberOfPlayers;
            } else {
                throw new IllegalMoveException("Illegal move: " + move);
            }
        }
    }

    public List<String> getPawnsFromPlayer(int playerIndex) {
        List<Pawn> pawns = players.get(playerIndex).getPawns();
        List<String> result = new ArrayList<>();
        int i = 0; //TODO: remove and implement toString in Pawn
        for(Pawn pawn: pawns) {
            //result.add(pawn.getField().getCoordinates().toString());
            result.add("Pawn " + i + " is here :>");
        }
        return result;
    }
}
