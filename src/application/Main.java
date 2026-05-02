package application;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;

public class Main extends Application {
	static LinkdList<User> userL = new LinkdList<>();

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		try {
			BorderPane root = new BorderPane();
			MainInterFace mainInterFace = new MainInterFace(primaryStage);
			Scene scene = new Scene(mainInterFace, 400, 400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setFullScreen(true);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

//--module-path "C:\Users\HP\Downloads\javafx-sdk-23.0.2\lib" --add-modules javafx.controls,javafx.fxml
