package pwr.tp.game;

import pwr.tp.domain.Board;
import pwr.tp.movement.Move;

import java.util.concurrent.atomic.AtomicInteger;

public class Lobby {
    private static final AtomicInteger COUNT = new AtomicInteger(0);
    private final int uniqueLobbyNumber = COUNT.getAndIncrement();
    int numOfPlayers;
    int currentNumOfPlayers;
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

    public void receiveMove(Move move) {

    }

    @Override
    public String toString() {
        return "ID: " + uniqueLobbyNumber + ", board type: "+ board +
                " with " + currentNumOfPlayers + "/" + numOfPlayers + " players";
    }



}
