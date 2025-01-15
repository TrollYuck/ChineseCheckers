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

import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Controller class for the client main view.
 */
public class ClientMainViewController {

  /**
   * Instance of MIMGui to handle game logic and communication.
   */
  private final MIMGui mim = new MIMGui(this); //man in the middle so this class isn't too big

  /**
   * List of available lobbies.
   */
  private List<String> lobbies;

  /**
   * ID of the selected lobby.
   */
  private int selectedLobbyId = -1;

  /**
   * Button to create a new game.
   */
  public Button CreateGameButton;

  /**
   * Button to join an existing game.
   */
  public Button JoinGameButton;

  /**
   * Button to refresh the list of available games.
   */
  public Button RefreshButton;

  /**
   * TextField for entering the number of players.
   */
  public TextField NumberOfPlayersTextField;

  /**
   * TextField for entering the game type.
   */
  public TextField GameTypeTextField;

  /**
   * TextArea for displaying server information.
   */
  public TextArea ServerInfoTextArea;

  /**
   * ScrollPane for displaying the list of available games.
   */
  public ScrollPane AvailableGamesScrollPane;

  /**
   * BorderPane for the client main view layout.
   */
  public BorderPane ClientMainView;

  /**
   * Initializes the controller class.
   */
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

  /**
   * Sets up the BorderPane to trigger the create game button when Enter is pressed.
   */
  @FXML
  public void setupBorderPane() {
    ClientMainView.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
      if (event.getCode() == KeyCode.ENTER) {
        CreateGameButton.fire();
        event.consume();
      }
    });
  }

  /**
   * Starts the MIM (Man In the Middle) with the specified port.
   *
   * @param port the port to connect to
   * @throws IOException if an I/O error occurs
   */
  public void startMIM(int port) throws IOException {
    mim.start("localhost", port);
  }

  /**
   * Creates a new game with the specified number of players and game type.
   * Displays an error pop-up if the input is invalid.
   */
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

  /**
   * Clears the server information text area.
   */
  public void clearServerInfo() {
    ServerInfoTextArea.clear();
  }

  /**
   * Clears the user input fields.
   */
  public void clearUserInput() {
    NumberOfPlayersTextField.clear();
    GameTypeTextField.clear();
  }

  /**
   * Sets the list of available games.
   *
   * @param lobbies the list of available games
   */
  public void setAvailableGames(List<String> lobbies) {
      this.lobbies = lobbies;
  }

  /**
   * Clears the available games from the scroll pane.
   */
  public void clearAvailableGames() {
    AvailableGamesScrollPane.setContent(null);
  }

  /**
   * Adds the available games to the scroll pane.
   */
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

  /**
   * Waits for the game to start.
   *
   * @throws InterruptedException if the thread is interrupted
   */
  private void waitForInGame() throws InterruptedException {
    int retries = 10;
    while (!mim.isInGame() && retries > 0) {
      Thread.sleep(500); // Wait for 500 milliseconds
      retries--;
    }
  }

  /**
   * Joins the selected game.
   * Displays an error pop-up if no game is selected or if an error occurs.
   */
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

  /**
   * Adds server information to the text area.
   *
   * @param info the server information to add
   */
  public void addServerInfo(String info) {
    ServerInfoTextArea.appendText(info + "\n");
  }

  /**
   * Refreshes the list of available games.
   */
  public void refreshButtonAction() {
    clearAvailableGames();
    mim.listGames();
    addAvailableGames();
  }
}
