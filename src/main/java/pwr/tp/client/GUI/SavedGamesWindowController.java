package pwr.tp.client.GUI;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import pwr.tp.server.DB.DataOperator;
import pwr.tp.server.DB.DatabaseManager;

import java.util.List;

public class SavedGamesWindowController {
  public ScrollPane savedScrollPane;
  public TextField gameIDTextField;
  public Button loadSavedGameButton;
  public Button replaySavedGameButton;
  public VBox savedGamesVBox;


  private MIMGui mim;

  public void setMim(MIMGui mim) {
    this.mim = mim;
  }

  public void show() {
    DatabaseManager manager=new DatabaseManager(DataOperator.jdbcTemplate());
    List<String> list=manager.getGamesInProgress(true);
    VBox vbox = new VBox();
    final String[] array=new String[list.size()];
    for(int i=0;i<list.size();i++)
      array[i]=list.get(i);
    for (String s : array) {
      TextArea textArea = new TextArea(s);
      textArea.setEditable(false);
      textArea.setPrefHeight(30);
      textArea.setPrefWidth(600);
      textArea.setWrapText(true);
      vbox.getChildren().add(textArea);
    }
    savedScrollPane.setContent(vbox);
  }

  public void loadSavedGame(ActionEvent actionEvent) {
    try {
      int gameID = Integer.parseInt(gameIDTextField.getText());
      mim.loadGame(gameID);
    } catch (NumberFormatException e) {
      ErrorPopUpUtil.showErrorPopUp("Invalid game ID\nPlease enter a valid game ID.");
    }
    ((Stage) savedGamesVBox.getScene().getWindow()).close();
  }

  public void replaySavedGame(ActionEvent actionEvent) {
  }
}
