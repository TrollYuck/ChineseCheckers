package pwr.tp;

import pwr.tp.client.GUI.MainGui;

@org.springframework.boot.autoconfigure.SpringBootApplication
public class SpringBootApplication {

  public static void main(String[] args) {
      javafx.application.Application.launch(MainGui.class, args);
  }
}
