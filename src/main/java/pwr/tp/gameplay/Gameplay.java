package pwr.tp.gameplay;

import pwr.tp.movement.Move;

import java.util.List;

public interface Gameplay {
    List<List<String>> getAllPawnsCoordinates();
    int getIndexOfWinner();
    List<String> getPawnsFromPlayer(int playerIndex);
    void receiveMove(Move move, int playerIndex);
}
