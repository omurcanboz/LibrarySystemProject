package utility;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class HelperUtility {

  public static Scene createScene(ActionEvent event, Class<?> clazz, String path) throws IOException {
    Node node = (Node) event.getSource();
    Stage thisStage = (Stage) node.getScene().getWindow();
    thisStage.close();
    Parent root = FXMLLoader.load(clazz.getResource(path));
    return new Scene(root);
  }

}
