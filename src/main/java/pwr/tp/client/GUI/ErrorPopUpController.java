package pwr.tp.client.GUI;

import javafx.scene.control.Label;

public class ErrorPopUpController {
  public Label ErrorMessageLabel;
  public void setErrorMessage(String message) {
    ErrorMessageLabel.setText(message);
  }

}
