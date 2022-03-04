package utility;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ui.controller.MainController;

public class HelperUtility {

  public static Scene createScene(ActionEvent event, Class<?> clazz, String path) throws IOException {
    Node node = (Node) event.getSource();
    Stage thisStage = (Stage) node.getScene().getWindow();
    thisStage.close();
    Parent root = FXMLLoader.load(clazz.getResource(path));
    return new Scene(root);
  }
  
  public static void backToMain(ActionEvent event) {
    try {
      MainController mainScreenController = new MainController();
      mainScreenController.backToMain(event);
    } catch (Exception e1) {
      e1.printStackTrace();
    }
  }

}
