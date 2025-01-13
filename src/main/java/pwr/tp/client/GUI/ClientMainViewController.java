package pwr.tp.client.GUI;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ClientMainViewController {

  private final MIMGui mim = new MIMGui(this); //man in the middle so this class isn't too big
  private List<String> lobbies;
  private int selectedLobbyId = -1;

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
      Pattern pattern = Pattern.compile("ID: (\\d+),");
      for (String lobby : lobbies) {

        TextArea lobbyInfo = new TextArea(lobby);
        lobbyInfo.setPrefHeight(30);
        lobbyInfo.setPrefWidth(390);
        lobbyInfo.setEditable(false);
        lobbyInfo.setOnMouseClicked(event -> {
          Matcher matcher = pattern.matcher(lobby);
          if (matcher.find()) {
            selectedLobbyId = Integer.parseInt(matcher.group(1));
          } else {
            selectedLobbyId = -1;
            System.out.println("No lobby selected");
          }
        });
        vbox.getChildren().add(lobbyInfo);
      }
      AvailableGamesScrollPane.setContent(vbox);
    }
  }

  private void waitForInGame() throws InterruptedException {
    int retries = 10;
    while (!mim.isInGame() && retries > 0) {
      Thread.sleep(500); // Wait for 500 milliseconds
      retries--;
    }
  }

  public void joinGame() {
    try {
      if (selectedLobbyId >= 0) {
        mim.joinGame(selectedLobbyId);
        //waitForInGame();
        Platform.runLater(() -> {
          try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/InGameView.fxml"));
            Parent root = loader.load();

            InGameViewController controller = loader.getController();
            controller.setupInGameView(mim);

            Stage stage = (Stage) ClientMainView.getScene().getWindow();
            stage.setScene(new Scene(root));
          } catch (IOException e) {
            ErrorPopUpUtil.showErrorPopUp("Failed to load InGameView.fxml: " + e.getMessage());
            e.printStackTrace();
          }
        });
      } else {
        ErrorPopUpUtil.showErrorPopUp("No lobby selected");
      }
    } catch (Exception e) {
      ErrorPopUpUtil.showErrorPopUp("An error occurred while joining the game");
      e.printStackTrace();
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
