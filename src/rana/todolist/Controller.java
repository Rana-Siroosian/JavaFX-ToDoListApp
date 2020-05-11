package rana.todolist;

import java.io.IOException;
/**
 * 
 * @author >>RanaSiroosian<<
 */
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import rana.todolist.datamodel.ToDoItems;
import rana.todolist.datamodel.TodoData;

public class Controller {

	private List<ToDoItems> toDo;

	@FXML
	private ListView<ToDoItems> todoListView;
	@FXML
	private TextArea itemDetailsTextArea;
	@FXML
	private Label deadlineLabel;
	@FXML
	private BorderPane mainBorderPane;

	public void initialize() {
//		ToDoItems item1 = new ToDoItems("Mail birthday card", "Buy 45th birthday card for Lucy",
//				LocalDate.of(2020, 6, 23));
//		ToDoItems item2 = new ToDoItems("Doctor's appointment",
//				"See Dr. Smith in Ann Arbor, don't forget the paperwork", LocalDate.of(2020, 6, 28));
//		ToDoItems item3 = new ToDoItems("Mail graduation card", "Buy 26 graduation party card",
//				LocalDate.of(2020, 9, 10));
//		ToDoItems item4 = new ToDoItems("Finish Android Studio project", "Work on details in the project",
//				LocalDate.of(2020, 5, 15));
//		ToDoItems item5 = new ToDoItems("Reserve a restaurant", "Invite friends for birthday dinner",
//				LocalDate.of(2020, 7, 2));
//
//		toDo = new ArrayList<ToDoItems>();
//		toDo.add(item1);
//		toDo.add(item2);
//		toDo.add(item3);
//		toDo.add(item4);
//		toDo.add(item5);
//
//		TodoData.getInstance().setTodoItems(toDo);

		todoListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<ToDoItems>() {

			@Override
			public void changed(ObservableValue<? extends ToDoItems> arg0, ToDoItems arg1, ToDoItems arg2) {
				// TODO Auto-generated method stub
				if (arg2 != null) {
					ToDoItems item = todoListView.getSelectionModel().getSelectedItem();
					itemDetailsTextArea.setText(item.getDetails());
					DateTimeFormatter df = DateTimeFormatter.ofPattern("MMMM d, yyyy");
					deadlineLabel.setText(df.format(item.getDeadline()));
				}

			}

		});
//		todoListView.getItems().setAll(TodoData.getInstance().getTodoItems());
		todoListView.setItems(TodoData.getInstance().getTodoItems());
		todoListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		todoListView.getSelectionModel().selectFirst();
	}

	@FXML
	public void showNewItemdialog() {
		Dialog<ButtonType> dialog = new Dialog<>();
		dialog.initOwner(mainBorderPane.getScene().getWindow());
		dialog.setTitle("Add New Todo Item");
		dialog.setHeaderText("Use this dialog to create a new todo item");
		FXMLLoader fxmlLoader = new FXMLLoader();
		fxmlLoader.setLocation(getClass().getResource("todoItemDialog.fxml"));
		try {
			dialog.getDialogPane().setContent(fxmlLoader.load());
		} catch (IOException e) {
			System.out.println("Couldn't load the dialog");
			e.printStackTrace();
			return;
		}

		dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
		dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

		Optional<ButtonType> result = dialog.showAndWait();
		if (result.isPresent() && result.get() == ButtonType.OK) {
			DialogController controller = fxmlLoader.getController();
			ToDoItems newItem = controller.processResults();
//			todoListView.getItems().setAll(TodoData.getInstance().getTodoItems());
			todoListView.getSelectionModel().select(newItem);
			System.out.println("Ok pressed");
		} else {
			System.out.println("Cancel pressed");
		}
	}

	@FXML
	public void handleClickListView() {
		ToDoItems item = (ToDoItems) todoListView.getSelectionModel().getSelectedItem();
		itemDetailsTextArea.setText(item.getDetails());
		deadlineLabel.setText(item.getDeadline().toString());
//		System.out.println("The selected item is: " + item.toString());
//		StringBuilder sb = new StringBuilder(item.getDetails());
//		sb.append("\n\n\n\n");
//		sb.append("Due: ");
//		sb.append(item.getDeadline().toString());
//		itemDetailsTextArea.setText(sb.toString());
	}

}
