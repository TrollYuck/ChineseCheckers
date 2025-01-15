package pwr.tp.client.GUI;

import javafx.scene.control.Label;

/**
 * Controller class for the error pop-up.
 */
public class ErrorPopUpController {

  /**
   * Label to display the error message.
   */
  public Label ErrorMessageLabel;

  /**
   * Sets the error message to be displayed in the error pop-up.
   *
   * @param message the error message to display
   */
  public void setErrorMessage(String message) {
    ErrorMessageLabel.setText(message);
  }
}
