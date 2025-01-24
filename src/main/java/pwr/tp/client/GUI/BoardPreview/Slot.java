package pwr.tp.client.GUI.BoardPreview;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

/**
 * The Slot class represents a slot on the game board.
 * It extends StackPane and contains a circle and text to display the slot's coordinates.
 */
public class Slot extends StackPane {

  /**
   * The circle representing the slot.
   */
  private final Circle circle;

  /**
   * The text displaying the slot's coordinates.
   */
  private final Text SlotInfoText;

  /**
   * The x-coordinate of the slot.
   */
  private final int x;

  /**
   * The y-coordinate of the slot.
   */
  private final int y;

    /**
     * Constructs a Slot with the specified coordinates.
     *
     * @param x The x-coordinate of the slot.
     * @param y The y-coordinate of the slot.
     */
  public Slot(int x, int y) {
    setPrefSize(30, 30);
    circle = new Circle(15);
    circle.setFill(Color.web("#b1b1b1"));
    circle.setStroke(Color.BLACK);
    SlotInfoText = new Text(x + "," + y);
    SlotInfoText.setStyle("-fx-font-weight: bold;");
    this.x = x;
    this.y = y;
    getChildren().addAll(circle, SlotInfoText);
  }

    /**
     * Gets the x-coordinate of the slot.
     *
     * @return The x-coordinate of the slot.
     */
  public int getX() {
    return x;
  }

    /**
     * Gets the y-coordinate of the slot.
     *
     * @return The y-coordinate of the slot.
     */
  public int getY() {
      return y;
  }

    /**
     * Sets the color of the slot based on the player's index.
     *
     * @param playerIndex The index of the player.
     */
  public void setColor(int playerIndex) {
    switch (playerIndex) {
      case 0:
        circle.setFill(Color.web("#ffffff"));
        break;
      case 1:
        circle.setFill(Color.web("#00cc00"));
        break;
      case 2:
        circle.setFill(Color.web("#ff0000"));
        break;
      case 3:
        circle.setFill(Color.web("#ffff00"));
        break;
      case 4:
        circle.setFill(Color.web("#ff00ff"));
        break;
      case 5:
        circle.setFill(Color.web("#00ffff"));
        break;
      default:
        circle.setFill(Color.web("#b1b1b1"));
        break;
    }
  }

    /**
     * Displays the slot information by adding the circle and text to the slot.
     */
  public void showSlotInfo() {
    getChildren().addAll(circle, SlotInfoText);
  }
}
