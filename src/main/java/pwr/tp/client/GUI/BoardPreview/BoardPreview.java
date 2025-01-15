package pwr.tp.client.GUI.BoardPreview;

import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import pwr.tp.domain.Field;
import pwr.tp.domain.StarBoard.StarBoard;
import pwr.tp.domain.StarBoard.Stripe;
import pwr.tp.game.Lobby;

import java.util.ArrayList;
import java.util.List;

public class BoardPreview {
  public VBox PreviewPane = new VBox();
  public ArrayList<Slot> slots = new ArrayList<>();

  private ArrayList<Stripe> stripeArrayList = new ArrayList<>();

    public void initialize(List<List<String>> playerPawnPositions, int numberOfPlayers) {
      PreviewPane.setPrefWidth(600);
      PreviewPane.setPrefHeight(600);
      Lobby lobby = new Lobby(numberOfPlayers, new StarBoard(), true);
      setStripes(lobby.getBoard().getStripes());
      fillTheVoid();
      lobby.startGame();
      updateAllPlayersColors(playerPawnPositions);
    }

    public void setSlotColor(int x, int y, int playerIndex) {
        for (Slot slot : slots) {
            if (slot.getX() == x && slot.getY() == y) {
                slot.setColor(playerIndex);
                break;
            }
        }
    }

    private void setStripes(List<Stripe> stripes) {
        for (int i = 0; i < 17; i++) {
            stripeArrayList.add(stripes.get(i));
        }
    }

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

    public void updateAllPlayersColors(List<List<String>> playersPawnPositions) {
      int index = 0;
      for (List<String> player : playersPawnPositions) {
        updateOnePlayersColors(player, index);
        index++;
      }
    }

    public void updateOnePlayersColors(List<String> playerPawnPositions, int playerIndex) {
      for (String position : playerPawnPositions) {
        String[] coordinates = position.split(",");
        for (String s : coordinates) {
          String[] split = s.split(" ");
          setSlotColor(Integer.parseInt(split[0]), Integer.parseInt(split[1]), playerIndex);
        }
      }
    }

    public void updatePawnPosition(int x, int y, int playerIndex) {
        setSlotColor(x, y, playerIndex);
    }


}
