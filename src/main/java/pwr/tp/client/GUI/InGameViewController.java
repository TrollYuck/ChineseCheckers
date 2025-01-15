package pwr.tp.client.GUI;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

/**
 * Controller class for the in-game view.
 */
public class InGameViewController {

  private MIMGui mim;

  public BorderPane InGameBorderPane;

  /**
   * TextField for the starting x-coordinate of the move.
   */
  public TextField XFromTextField;

  /**
   * TextField for the starting y-coordinate of the move.
   */
  public TextField YFromTextField;

  /**
   * TextField for the ending x-coordinate of the move.
   */
  public TextField XToTextField;

  /**
   * TextField for the ending y-coordinate of the move.
   */
  public TextField YToTextField;

  /**
   * Button to confirm the move.
   */
  public Button ConfirmMoveButton;

  /**
   * Button to disconnect from the game.
   */
  public Button DisconnectButton;

  public Button RefreshButton;

  public Button ShowMapButton;

  /**
   * TextArea to display the player's pawns information.
   */
  public TextArea PlayerPawnsTextField;

  /**
   * TextArea to display the lobby information.
   */
  public TextArea LobbyInfoTextField;

  /**
   * Sets up the in-game view with the given MIMGui instance.
   *
   * @param mim the MIMGui instance
   */
  public void setupInGameView(MIMGui mim) {
    this.mim = mim;
    mim.setInGameViewController(this);
    mim.updateMyPawnsRequest();
  }

  /**
   * Adds information to the lobby info text area.
   *
   * @param info the information to add
   */
  public void addLobbyInfo(String info) {
    LobbyInfoTextField.appendText(info + "\n");
  }

  /**
   * Clears the lobby info text area.
   */
  public void clearLobbyInfo() {
      LobbyInfoTextField.clear();
  }

  /**
   * Adds player pawns information to the player pawns text area.
   *
   * @param pawns the player pawns information to add
   */
  public void addPlayerPawns(String pawns) {
      PlayerPawnsTextField.appendText(pawns + "\n");
  }

  /**
   * Clears the player pawns text area.
   */
  public void clearPlayerPawns() {
      PlayerPawnsTextField.clear();
  }

  /**
   * Confirms the move by sending the move coordinates to the server.
   */
  public void confirmMove() {
    try {
      int xFrom = Integer.parseInt(XFromTextField.getText());
      int yFrom = Integer.parseInt(YFromTextField.getText());
      int xTo = Integer.parseInt(XToTextField.getText());
      int yTo = Integer.parseInt(YToTextField.getText());
      mim.sendMove(xFrom, yFrom, xTo, yTo);
      mim.updateMyPawnsRequest();
    } catch (NumberFormatException e) {
      ErrorPopUpUtil.showErrorPopUp("Invalid input");
    }
  }

  /**
   * Disconnects from the game.
   */
  public void disconnect() {
    mim.disconnect();
  }

  public BorderPane getInGameBorderPane() {
    return InGameBorderPane;
  }

  public void refresh() {
      mim.updateMyPawnsRequest();
  }

  public void showMap() {
      mim.showMap();
  }
}
