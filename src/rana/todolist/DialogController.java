package rana.todolist;

import java.time.LocalDate;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import rana.todolist.datamodel.ToDoItems;
import rana.todolist.datamodel.TodoData;

public class DialogController {

	@FXML
	private TextField shortDescriptionField;

	@FXML
	private TextArea detailsArea;

	@FXML
	private DatePicker deadlinePicker;

	public ToDoItems processResults() {
		String shortDescription = shortDescriptionField.getText().trim();
		String details = detailsArea.getText().trim();
		LocalDate deadlineValue = deadlinePicker.getValue();
//		TodoData.getInstance().addTodoItem(new ToDoItems(shortDescription, details, deadlineValue));

		ToDoItems newItem = new ToDoItems(shortDescription, details, deadlineValue);
		TodoData.getInstance().addTodoItem(newItem);
		return newItem;
	}
}
