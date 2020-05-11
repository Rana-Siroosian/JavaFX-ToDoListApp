package rana.todolist;
import java.io.IOException;

/**
 * 
 * @author >>RanaSiroosian<<
 */
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import rana.todolist.datamodel.TodoData;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("rana.todolist.fxml"));
		primaryStage.setTitle("ToDo List");
		primaryStage.setScene(new Scene(root, 900, 500));
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void stop() throws Exception{
		try {
			TodoData.getInstance().storeTodoItems();
		}catch(IOException e) {
			System.out.println(e.getMessage());
		}
	}
	
	@Override
	public void init() throws Exception{
		try {
			TodoData.getInstance().loadTodoItems();
		}catch(IOException e) {
			System.out.println(e.getMessage());
		}
	}
	
}
