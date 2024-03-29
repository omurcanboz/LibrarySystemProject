package ui;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;


public class Start extends Application {
	public static void main(String[] args) {
		launch(args);
	}
	private static Stage primStage = null;
	public static Stage primStage() {
		return primStage;
	}
	
	public static class Colors {
		static Color green = Color.web("#034220");
		static Color red = Color.FIREBRICK;
	}
	
	private static Stage[] allWindows = { 

	};
	
	public static void hideAllWindows() {
		primStage.hide();
		for(Stage st: allWindows) {
			st.hide();
		}
	}
	
	@Override
	public void start(Stage primaryStage) throws IOException {
		primStage = primaryStage;
		primaryStage.setTitle("Library Application");
				
		VBox topContainer = new VBox();
		topContainer.setId("top-container");
		MenuBar mainMenu = new MenuBar();
		VBox imageHolder = new VBox();
        HBox splashBox = new HBox();
        Label splashLabel = new Label("Library Application");
        splashLabel.setFont(Font.font("Trajan Pro", FontWeight.BOLD, 20));
        splashBox.getChildren().add(splashLabel);
        splashBox.setAlignment(Pos.CENTER);
		// Button loginButton = new Button("Login");

		FXMLLoader loader = new FXMLLoader(getClass().getResource("NewLogin.fxml"));
		Parent root = loader.load();
		
		topContainer.getChildren().add(mainMenu);
		topContainer.getChildren().add(splashBox);
		topContainer.getChildren().add(imageHolder);
		topContainer.getChildren().add(root);

		
		

		
							
		Scene scene = new Scene(topContainer, 320, 250);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
}
