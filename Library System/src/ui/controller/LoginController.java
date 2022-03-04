package ui.controller;

import business.ControllerInterface;
import controller.SystemController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import ui.Start;

public class LoginController extends Stage {

  @FXML
  private TextField username;

  @FXML
  private PasswordField password;

  Alert alertError = new Alert(AlertType.ERROR);

  public static void showLoginScreen(ActionEvent event) {
    try {
      Node node = (Node) event.getSource();
      Stage thisStage = (Stage) node.getScene().getWindow();
      thisStage.close();
      Start.primStage().show();
    } catch (Exception e1) {
      e1.printStackTrace();
    }
  }

  public void login(ActionEvent event) {
    try {
      ControllerInterface c = new SystemController();
      c.login(username.getText().trim(), password.getText().trim());
      Node node = (Node) event.getSource();
      Stage thisStage = (Stage) node.getScene().getWindow();
      thisStage.close();
      MainScreenController mainScreenController = new MainScreenController();
      mainScreenController.showMainScreen();

    } catch (Exception e) {
      alertError.setContentText(e.getMessage());
      alertError.show();
    }
  }

}
