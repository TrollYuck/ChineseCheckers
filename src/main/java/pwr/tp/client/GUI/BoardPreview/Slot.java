package pwr.tp.client.GUI.BoardPreview;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

public class Slot extends StackPane {

  private Circle circle;
  private final Text SlotInfoText;
  private final int x;
  private final int y;

  public Slot(int x, int y) {
    setPrefSize(30, 30);
    circle = new Circle(15);
    circle.setFill(Color.web("#b1b1b1"));
    SlotInfoText = new Text(x + "," + y);
    SlotInfoText.setStyle("-fx-font-weight: bold;");
    this.x = x;
    this.y = y;
  }

  public int getX() {
    return x;
  }

  public int getY() {
      return y;
  }

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
}
