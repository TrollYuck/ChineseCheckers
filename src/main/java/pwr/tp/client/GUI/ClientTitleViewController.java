package pwr.tp.client.GUI;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class ClientTitleViewController {
  public TextField ServerPortTextField;
  public Button ServerConnectButton;
  public Button QuitGameButton;

  public void QuitGame() {
    Platform.exit();
  }

  @FXML
  private void setupServerPortTextField() {
    ServerPortTextField.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
      if (event.getCode() == KeyCode.ENTER) {
        ServerConnectButton.fire();
        event.consume();
      }
    });
  }

  public void connectToServer() {
    String portText = ServerPortTextField.getText();
    if (portText.isEmpty()) {
      ErrorPopUpUtil.showErrorPopUp("Port cannot be empty");
      return;
    }

    try {
      int port = Integer.parseInt(portText);
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/ClientMainView.fxml"));
      Parent root = loader.load();

      ClientMainViewController controller = loader.getController();
      controller.startMIM(port);

      Stage stage = (Stage) ServerConnectButton.getScene().getWindow();
      stage.setScene(new Scene(root));
    } catch (NumberFormatException e) {
      ErrorPopUpUtil.showErrorPopUp("Port must be a number");
    } catch (IOException e) {
      ErrorPopUpUtil.showErrorPopUp("Failed to connect to: " + portText);
    } catch (Exception e) {
      ErrorPopUpUtil.showErrorPopUp("An error occurred: " + e.getMessage());
    }
  }
}
