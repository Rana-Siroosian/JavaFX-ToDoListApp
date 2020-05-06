package rana.todolist;
/**
 * 
 * @author >>RanaSiroosian<<
 */
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("rana.todolist.fxml"));
		primaryStage.setTitle("To Do List");
		primaryStage.setScene(new Scene(root, 900, 500));
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
