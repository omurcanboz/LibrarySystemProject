package ui.controller;

import controller.SystemController;
import dataaccess.Auth;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;
import ui.Start;
import utility.HelperUtility;

public class MainScreenController extends Stage {

  @FXML
  private TabPane mainTabPane;
  @FXML
  private Button logout;
  @FXML
  private Label welcomeLabel;
  @FXML
  private Tab addNewMemberTab;
  @FXML
  private Tab addNewBookTab;
  @FXML
  private Tab checkoutBookTab;
  @FXML
  private Tab addBookCopyTab;
  @FXML
  private Tab printCheckoutTab;
  @FXML
  private Tab checkOverdueTab;

  Alert alert = new Alert(AlertType.INFORMATION);

  public void showMainScreen() {
    try {
      FXMLLoader loader = new FXMLLoader(getClass().getResource("../MainScreen.fxml"));
      loader.setController(this);
      Parent root = loader.load();
      Scene scene = new Scene(root);
      setScene(scene);
      setTitle("Library System");
      welcomeLabel.setText("Welcome " + SystemController.userId);

      show();
    } catch (Exception e1) {
      e1.printStackTrace();
    }

    Auth auth = SystemController.currentAuth;
    switch (auth) {
      case ADMIN:
        mainTabPane.getTabs().remove(checkoutBookTab);
        mainTabPane.getTabs().remove(printCheckoutTab);
        mainTabPane.getTabs().remove(checkOverdueTab);
        break;
      case LIBRARIAN:
        mainTabPane.getTabs().remove(addBookCopyTab);
        mainTabPane.getTabs().remove(addNewBookTab);
        mainTabPane.getTabs().remove(addNewMemberTab);
        break;
      case BOTH:
        break;
      default:
        throw new IllegalArgumentException("Unexpected value: " + auth);
    }

  }
  
  public void backToMain(ActionEvent event) {
    try {
      Node node = (Node) event.getSource();
      Stage thisStage = (Stage) node.getScene().getWindow();
      thisStage.close();
      showMainScreen();
    } catch (Exception e1) {
      e1.printStackTrace();
    }
  }

  public void backToLogin(ActionEvent event) {
    try {
      LoginController.showLoginScreen(event);
    } catch (Exception e1) {
      e1.printStackTrace();
    }
  }

}


