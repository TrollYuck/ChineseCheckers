package pwr.tp.client.GUI;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Utility class for displaying error pop-ups.
 */
public class ErrorPopUpUtil {

  /**
   * Displays an error pop-up with the specified message.
   *
   * @param message the error message to display
   */
  public static void showErrorPopUp(String message) {
    try {
      FXMLLoader fxmlLoader = new FXMLLoader(ErrorPopUpUtil.class.getResource("/ErrorPopUp.fxml"));
      Parent root = fxmlLoader.load();
      ErrorPopUpController errorPopUpController = fxmlLoader.getController();
      if (message != null) {
        errorPopUpController.setErrorMessage(message);
      }
      Stage stage = new Stage();
      stage.setResizable(false);
      stage.setTitle("Error");
      stage.setScene(new Scene(root));
      stage.show();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
