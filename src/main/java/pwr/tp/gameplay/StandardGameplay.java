package pwr.tp.gameplay;

import pwr.tp.domain.Board;
import pwr.tp.domain.Field;
import pwr.tp.domain.Pawn;
import pwr.tp.domain.StarBoard.Stripe;
import pwr.tp.movement.Move;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the gameplay of the game.
 */
public class StandardGameplay implements Gameplay{
    /**
     * The number of players in the game.
     */
    private final int numberOfPlayers;

    /**
     * The list of players in the game.
     */
    private List<Player> players;

    /**
     * The list of stripes on the board.
     */
    private List<Stripe> stripes;

    /**
     * The game board.
     */
    private final Board board;

    /**
     * The index of the current player.
     */
    private int currentPlayerIndex;

    /**
     * The index of the winning player.
     */
    private int indexOfWinner;

    /**
     * The name of the game type.
     */
    private String gameType;

    /**
     * Constructs a Gameplay instance with the specified number of players and board.
     *
     * @param numberOfPlayers the number of players
     * @param board the game board
     */
    public StandardGameplay(int numberOfPlayers, Board board, String gameType) {
        this.numberOfPlayers = numberOfPlayers;
        stripes = board.getStripes();
        this.board = board;
        currentPlayerIndex = 0;
        indexOfWinner = -1;
        this.gameType = gameType;
        players = new ArrayList<>();
        setUpGame();
    }



    /**
     * Returns the index of the winner.
     *
     * @return the index of the winner
     */
    @Override
    public int getIndexOfWinner() {
        return indexOfWinner;
    }

    /**
     * Sets up the game by creating players and setting up pawns.
     */
    public void setUpGame() {
        createPlayers();
        setUpPawns();
    }

    /**
     * Checks for winning conditions.
     *
     * @return true if a player has won, false otherwise
     */
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

    /**
     * Sets up pawns for the players.
     */
    private void setUpPawns() {
        switch (gameType){
            case "double base game":
                setUpDoubleBasedGame();
                break;
            default:
                setUpStandardGame();
        }
    }

    private void setUpStandardGame() {
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

    private void setUpDoubleBasedGame() {
        if(numberOfPlayers == 2) {
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
                    players.getFirst().receivePawn(new Pawn(field));
                    field.setFull(0);
                }
            }

            for(int i = 17; i < 21; i++) {
                Stripe stripe = stripes.get(i);
                for(Field field: stripe.getFieldsInRow()) {
                    players.get(1).receivePawn(new Pawn(field));
                    field.setFull(1);
                }
            }

            for(int i = 30; i < 34; i++) {
                Stripe stripe = stripes.get(i);
                for(Field field: stripe.getFieldsInRow()) {
                    players.get(1).receivePawn(new Pawn(field));
                    field.setFull(1);
                }
            }
        } else {
            for(int i = 34; i < 38; i++) {
                Stripe stripe = stripes.get(i);
                for(Field field: stripe.getFieldsInRow()) {
                    players.get(2).receivePawn(new Pawn(field));
                    field.setFull(2);
                }
            }

            for(int i = 47; i < 51; i++) {
                Stripe stripe = stripes.get(i);
                for(Field field: stripe.getFieldsInRow()) {
                    players.get(2).receivePawn(new Pawn(field));
                    field.setFull(2);
                }
            }
        }
    }

    /**
     * Creates players for the game.
     */
    private void createPlayers() {
        for(int i = 0; i < numberOfPlayers; i++) {
            Player player = new Player();
            players.add(player);
        }
    }

    /**
     * Checks if a move is legal for a player.
     *
     * @param player the player making the move
     * @param move the move to be checked
     * @return true if the move is legal, false otherwise
     */
    private boolean isMoveLegal(Player player, Move move){
        Pawn pawn = new Pawn(new Field(move.getInitialPosition()));
        if(player.getPawns().contains(pawn)) {
            return Move.isMoveLegal(move, stripes);
        }
        return false;
    }

    /**
     * Receives a move from a player and processes it.
     *
     * @param move the move to be processed
     * @param playerIndex the index of the player making the move
     * @throws IllegalMoveException if the move is illegal
     */
    @Override
    public void receiveMove(Move move, int playerIndex) throws IllegalMoveException {
        checkForWinningConditions();
        if(playerIndex == currentPlayerIndex) {
            if(isMoveLegal(players.get(playerIndex), move)) {
                movePawn(move, playerIndex);
                currentPlayerIndex = (currentPlayerIndex + 1) % numberOfPlayers;
                checkForWinningConditions();
            } else {
                throw new IllegalMoveException("Illegal move: " + move);
            }
        } else {
            throw new IllegalMoveException("Illegal playerIndex: " + move);
        }
    }

    /**
     * Returns the coordinates of the pawns of a player.
     *
     * @param playerIndex the index of the player
     * @return a list of strings representing the coordinates of the pawns
     */
    @Override
    public List<String> getPawnsFromPlayer(int playerIndex) {
        List<Pawn> pawns = players.get(playerIndex).getPawns();
        List<String> result = new ArrayList<>();
        for(Pawn pawn: pawns) {
            result.add(pawn.toString());
        }
        return result;
    }

    /**
     * Returns the coordinates of all pawns of all players.
     *
     * @return a list of lists of strings representing the coordinates of the pawns
     */
    @Override
    public List<List<String>> getAllPawnsCoordinates() {
        List<List<String>> result = new ArrayList<>();
        for(Player player: players) {
            List<String> playerPawns = new ArrayList<>();
            for(Pawn pawn: player.getPawns()) {
                playerPawns.add(pawn.toString());
            }
            result.add(playerPawns);
        }
        return result;
    }


    /**
     * Moves a pawn according to the specified move.
     *
     * @param move the move to be executed
     * @param playerIndex the index of the player making the move
     */
    private void movePawn(Move move, int playerIndex) {
        Field field = board.findFieldByCoordinates(move.getInitialPosition());
        Pawn pawn = players.get(playerIndex).findPawnByLocation(field);
        field.setEmpty();

        field = board.findFieldByCoordinates(move.getFinalPosition());
        field.setFull(playerIndex);
        pawn.setLocation(field);
    }


}
