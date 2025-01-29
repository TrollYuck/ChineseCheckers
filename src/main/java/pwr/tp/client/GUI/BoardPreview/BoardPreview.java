package pwr.tp.client.GUI.BoardPreview;

import javafx.scene.layout.VBox;

import pwr.tp.domain.Field;
import pwr.tp.domain.StarBoard.StarBoard;
import pwr.tp.domain.StarBoard.Stripe;
import pwr.tp.game.Lobby;

import java.util.ArrayList;
import java.util.List;

/**
 * The BoardPreview class is responsible for displaying a preview of the game board.
 */
public class BoardPreview {
  /**
   * The VBox that contains the preview of the game board.
   */
  public VBox PreviewPane = new VBox();

  /**
   * The list of slots on the game board.
   */
  public ArrayList<Slot> slots = new ArrayList<>();

  /**
   * The list of stripes on the game board.
   */
  private final ArrayList<Stripe> stripeArrayList = new ArrayList<>();

    /**
     * Initializes the board preview with player pawn positions and the number of players.
     *
     * @param playerPawnPositions List of player pawn positions.
     * @param numberOfPlayers     Number of players in the game.
     */
    public void initialize(List<List<String>> playerPawnPositions, int numberOfPlayers, String gameType) {
      PreviewPane.setPrefWidth(600);
      PreviewPane.setPrefHeight(600);
      Lobby lobby = new Lobby(numberOfPlayers, new StarBoard(), true);
      lobby.setGameType(gameType);
      setStripes(lobby.getBoard().getStripes());
      fillTheVoid();
      lobby.startGame();
      updateAllPlayersColors(playerPawnPositions);
    }

    /**
     * Sets the color of a slot based on the player's index.
     *
     * @param x           The x-coordinate of the slot.
     * @param y           The y-coordinate of the slot.
     * @param playerIndex The index of the player.
     */
    public void setSlotColor(int x, int y, int playerIndex) {
        for (Slot slot : slots) {
            if (slot.getX() == x && slot.getY() == y) {
                slot.setColor(playerIndex);
                break;
            }
        }
    }

    /**
     * Sets the stripes for the board preview.
     *
     * @param stripes List of stripes to be set.
     */
    private void setStripes(List<Stripe> stripes) {
        for (int i = 0; i < 17; i++) {
            stripeArrayList.add(stripes.get(i));
        }
    }

    /**
     * Fills the board preview with slots based on the stripes.
     */
    private void fillTheVoid() {
      for (Stripe stripe : stripeArrayList) {
        CustomHbox hbox = new CustomHbox();
        for (Field field : stripe.getFieldsInRow()) {
          Slot slot = new Slot(field.getX(), field.getY());
          hbox.getChildren().add(slot);
          slots.add(slot);
        }
        PreviewPane.getChildren().add(hbox);
      }
    }

    /**
     * Updates the colors of all players' pawns.
     *
     * @param playersPawnPositions List of all players' pawn positions.
     */
    public void updateAllPlayersColors(List<List<String>> playersPawnPositions) {
      int index = 0;
      for (List<String> player : playersPawnPositions) {
        updateOnePlayersColors(player, index);
        index++;
      }
    }

    /**
     * Updates the colors of a single player's pawns.
     *
     * @param playerPawnPositions List of the player's pawn positions.
     * @param playerIndex         The index of the player.
     */
    public void updateOnePlayersColors(List<String> playerPawnPositions, int playerIndex) {
      for (String position : playerPawnPositions) {
        String[] coordinates = position.split(",");
        for (String s : coordinates) {
          String[] split = s.split(" ");
          setSlotColor(Integer.parseInt(split[0]), Integer.parseInt(split[1]), playerIndex);
        }
      }
    }

    /**
     * Updates the position of a pawn for a specific player.
     *
     * @param x           The x-coordinate of the pawn.
     * @param y           The y-coordinate of the pawn.
     * @param playerIndex The index of the player.
     */
    public void updatePawnPosition(int x, int y, int playerIndex) {
        setSlotColor(x, y, playerIndex);
    }


}
