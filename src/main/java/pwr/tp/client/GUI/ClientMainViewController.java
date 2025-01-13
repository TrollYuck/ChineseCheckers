package pwr.tp.client.GUI;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import pwr.tp.game.Lobby;

import java.io.IOException;
import java.util.List;

public class ClientMainViewController {

  private final MIMGui mim = new MIMGui(this); //man in the middle so this class isn't too big
  private List<String> lobbies;

  public Button CreateGameButton;
  public Button JoinGameButton;
  public Button RefreshButton;
  public TextField NumberOfPlayersTextField;
  public TextField GameTypeTextField;
  public TextArea ServerInfoTextArea;
  public ScrollPane AvailableGamesScrollPane;
  public BorderPane ClientMainView;

  @FXML
  public void initialize() {
    Platform.runLater(() -> {
      Stage stage = (Stage) ClientMainView.getScene().getWindow();
      stage.setOnCloseRequest((WindowEvent event) -> {
        try {
          mim.quitGame();
        } catch (IOException e) {
          e.printStackTrace();
        }
      });
    });
  }

  @FXML
  public void setupBorderPane() {
    ClientMainView.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
      if (event.getCode() == KeyCode.ENTER) {
        CreateGameButton.fire();
        event.consume();
      }
    });
  }

  public void startMIM(int port) throws IOException {
    mim.start("localhost", port);
  }

  public void createGame() {
    String numberOfPlayers = NumberOfPlayersTextField.getText();
    String gameType = GameTypeTextField.getText();
    if (numberOfPlayers.isEmpty() || gameType.isEmpty()) {
      ErrorPopUpUtil.showErrorPopUp("Please fill in all fields.");
    } else {
      try {
        int players = Integer.parseInt(numberOfPlayers);
        mim.createGame(players, gameType);
      } catch (NumberFormatException e) {
        ErrorPopUpUtil.showErrorPopUp("Number of players must be an integer.");
        clearUserInput();
      }
    }
  }

  public void clearServerInfo() {
    ServerInfoTextArea.clear();
  }

  public void clearUserInput() {
    NumberOfPlayersTextField.clear();
    GameTypeTextField.clear();
  }

  public void setAvailableGames(List<String> lobbies) {
      this.lobbies = lobbies;
  }

  public void clearAvailableGames() {
    AvailableGamesScrollPane.setContent(null);
  }

  public void addAvailableGames() {
    if (lobbies != null) {
      VBox vbox = new VBox();
      for (String lobby : lobbies) {
        TextArea lobbyInfo = new TextArea(lobby);
        lobbyInfo.setPrefHeight(30);
        lobbyInfo.setPrefWidth(390);
        lobbyInfo.setEditable(false);
        vbox.getChildren().add(lobbyInfo);
      }
      AvailableGamesScrollPane.setContent(vbox);
    }
  }

  public void addServerInfo(String info) {
    ServerInfoTextArea.appendText(info + "\n");
  }

  public void refreshButtonAction() {
    clearAvailableGames();
    mim.listGames();
    addAvailableGames();
  }
}
