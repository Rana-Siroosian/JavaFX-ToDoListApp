package rana.todolist;
/**
 * 
 * @author >>RanaSiroosian<<
 */
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import rana.todolist.datamodel.ToDoItems;

public class Controller {

	private List<ToDoItems> toDo;
	
	@FXML
	private ListView todoListView;
	
	public void initialize() {
		ToDoItems item1 = new ToDoItems("Mail birthday card", "Buy 45th birthday card for Lucy",
				LocalDate.of(2020, 6, 23));
		ToDoItems item2 = new ToDoItems("Doctor's appointment",
				"See Dr. Smith in Ann Arbor, don't forget the paperwork", LocalDate.of(2020, 6, 28));
		ToDoItems item3 = new ToDoItems("Mail graduation card", "Buy 26 graduation party card",
				LocalDate.of(2020, 9, 10));
		ToDoItems item4 = new ToDoItems("Finish Android Studio project", "Work on details in the project",
				LocalDate.of(2020, 5, 15));
		ToDoItems item5 = new ToDoItems("Reserve a restaurant", "Invite friends for birthday dinner",
				LocalDate.of(2020, 7, 2));

		toDo = new ArrayList<ToDoItems>();
		toDo.add(item1);
		toDo.add(item2);
		toDo.add(item3);
		toDo.add(item4);
		toDo.add(item5);
		
		todoListView.getItems().setAll(toDo);
		todoListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		

	}
}
