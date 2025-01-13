package pwr.tp.game;

import pwr.tp.domain.Board;
import pwr.tp.domain.Pawn;
import pwr.tp.gameplay.Gameplay;
import pwr.tp.gameplay.IllegalMoveException;
import pwr.tp.movement.Move;

import java.io.Serializable;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Lobby  {
    private static final AtomicInteger COUNT = new AtomicInteger(0);
    private final int uniqueLobbyNumber = COUNT.getAndIncrement();
    int numOfPlayers;
    int currentNumOfPlayers;
    Gameplay currentGame;
    Board board;
    public Lobby(int numOfPlayers, Board board){
        this.board = board;
        this.numOfPlayers = numOfPlayers;
        System.out.println("uniqueLobbyNumber: " + uniqueLobbyNumber);
    }

    public int getNumOfPlayers() {
        return numOfPlayers;
    }

    public int getCurrentNumOfPlayers() {
        return currentNumOfPlayers;
    }

    public int getUniqueLobbyNumber() {
        return uniqueLobbyNumber;
    }

    public void addPlayer() {
        currentNumOfPlayers++;
    }

    public void removePlayer() {
        currentNumOfPlayers--;
    }

    public void startGame() {
        currentGame = new Gameplay(numOfPlayers, board);
    }

    public void receiveMove(Move move, int playerIndex) throws IllegalMoveException {
        currentGame.receiveMove(move, playerIndex);
    }

    public List<String> getPawnsFromPlayer(int playerIndex) {
        return currentGame.getPawnsFromPlayer(playerIndex);
    }

    public boolean isPlayerThresholdReached() {
        return currentNumOfPlayers >= numOfPlayers;
    }

    public boolean isGameStarted() {
        return currentGame != null;
    }

    @Override
    public String toString() {
        return "ID: " + uniqueLobbyNumber + ", board type: "+ board.getName() +
                " with " + currentNumOfPlayers + "/" + numOfPlayers + " players";
    }



}
