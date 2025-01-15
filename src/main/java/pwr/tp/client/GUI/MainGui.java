package pwr.tp.client.GUI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pwr.tp.client.GUI.BoardPreview.BoardPreview;

import java.util.Objects;

/**
 * Main class for launching the JavaFX application.
 */
public class MainGui extends Application {

  /**
   * Starts the JavaFX application.
   *
   * @param primaryStage the primary stage for this application
   * @throws Exception if an error occurs during loading the FXML resource
   */
  @Override
  public void start(Stage primaryStage) throws Exception {
    Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/ClientTitleView.fxml")));
    primaryStage.setTitle("Chinese Checkers");
    primaryStage.setScene(new Scene(root));
    primaryStage.setResizable(false);
    primaryStage.show();
  }

//  public void start(Stage primaryStage) throws Exception {
//    BoardPreview boardPreview = new BoardPreview();
//    boardPreview.initialize( 6);
//    primaryStage.setTitle("Chinese Checkers");
//    primaryStage.setScene(new Scene(boardPreview.PreviewPane));
//    primaryStage.show();
//
//  }

  /**
   * The main method to launch the JavaFX application.
   *
   * @param args the command line arguments
   */
  public static void main(String[] args) {
    launch(args);
  }
}
