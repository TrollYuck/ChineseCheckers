package pwr.tp.client.GUI;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class InGameViewController {

  private MIMGui mim;

  public TextField XFromTextField;
  public TextField YFromTextField;
  public TextField XToTextField;
  public TextField YToTextField;

  public Button ConfirmMoveButton;
  public Button DisconnectButton;

  public TextArea PlayerPawnsTextField;
  public TextArea LobbyInfoTextField;

//  @FXML
//  public void initialize() {
//      mim.updateMyPawnsRequest();
//  }

  public void setupInGameView(MIMGui mim) {
    this.mim = mim;
    mim.setInGameViewController(this);
    mim.updateMyPawnsRequest();
  }

  public void addLobbyInfo(String info) {
    LobbyInfoTextField.appendText(info + "\n");
  }

  public void clearLobbyInfo() {
      LobbyInfoTextField.clear();
  }

  public void addPlayerPawns(String pawns) {
      PlayerPawnsTextField.appendText(pawns + "\n");
  }

  public void clearPlayerPawns() {
      PlayerPawnsTextField.clear();
  }


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
}
