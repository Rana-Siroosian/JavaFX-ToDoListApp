package rana.todolist;

import java.io.IOException;
/**
 * 
 * @author >>RanaSiroosian<<
 */
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.util.Callback;
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
	@FXML
	private ContextMenu listContextMenu;
	@FXML
	private ToggleButton filterToggleButton;

	private FilteredList<ToDoItems> filteredList;

	private Predicate<ToDoItems> wantAllItems;
	private Predicate<ToDoItems> wantTodaysItems;

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
		listContextMenu = new ContextMenu();
		MenuItem deleteMenuItem = new MenuItem("Delete");
		deleteMenuItem.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				ToDoItems item = todoListView.getSelectionModel().getSelectedItem();
				deleteItem(item);

			}

		});
		listContextMenu.getItems().addAll(deleteMenuItem);
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
		wantAllItems = new Predicate<ToDoItems>() {

			@Override
			public boolean test(ToDoItems todoItem) {
				return true;
			}

		};
		wantTodaysItems = new Predicate<ToDoItems>() {

			@Override
			public boolean test(ToDoItems todoItem) {
				return (todoItem.getDeadline().equals(LocalDate.now()));
			}

		};
		filteredList = new FilteredList<ToDoItems>(TodoData.getInstance().getTodoItems(), wantAllItems);

		SortedList<ToDoItems> sortedList = new SortedList<ToDoItems>(filteredList, new Comparator<ToDoItems>() {
			@Override
			public int compare(ToDoItems o1, ToDoItems o2) {
				return o1.getDeadline().compareTo(o2.getDeadline());
			}

		});
//		todoListView.getItems().setAll(TodoData.getInstance().getTodoItems());
//		todoListView.setItems(TodoData.getInstance().getTodoItems());
		todoListView.setItems(sortedList);
		todoListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		todoListView.getSelectionModel().selectFirst();

		todoListView.setCellFactory(new Callback<ListView<ToDoItems>, ListCell<ToDoItems>>() {
			@Override
			public ListCell<ToDoItems> call(ListView<ToDoItems> param) {
				ListCell<ToDoItems> cell = new ListCell<ToDoItems>() {

					@Override
					protected void updateItem(ToDoItems item, boolean empty) {
						super.updateItem(item, empty);
						if (empty) {
							setText(null);
						} else {
							setText(item.getShortDescription());
							if (item.getDeadline().isBefore(LocalDate.now().plusDays(1))) {
								setTextFill(Color.RED);

							} else if (item.getDeadline().equals(LocalDate.now().plusDays(1))) {
								setTextFill(Color.BLUE);

							}
						}
					}

				};

				cell.emptyProperty().addListener((obs, wasEmpty, isNowEmpty) -> {
					if (isNowEmpty) {
						cell.setContextMenu(null);
					} else {
						cell.setContextMenu(listContextMenu);
					}
				});

				return cell;
			}
		});
	}

	@FXML
	public void showNewItemDialog() {
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
	public void handleKeyPressed(KeyEvent keyEvent) {
		ToDoItems selectedItem = todoListView.getSelectionModel().getSelectedItem();
		if (selectedItem != null) {
			if (keyEvent.getCode().equals(KeyCode.DELETE)) {
				deleteItem(selectedItem);
			}
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

	@FXML
	public void deleteItem(ToDoItems item) {
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.setTitle("Delete Todo Item");
		alert.setHeaderText("Delete item: " + item.getShortDescription());
		alert.setContentText("Are you sure? Press ok to confirm, or cancel to back out.");
		Optional<ButtonType> result = alert.showAndWait();

		if (result.isPresent() && result.get() == ButtonType.OK) {
			TodoData.getInstance().deleteTodoItem(item);
		}
	}

	@FXML
	public void handleFilterButton() {
		ToDoItems selectedItem = todoListView.getSelectionModel().getSelectedItem();
		if (filterToggleButton.isSelected()) {
			filteredList.setPredicate(wantTodaysItems);
			if (filteredList.isEmpty()) {
				itemDetailsTextArea.clear();
				deadlineLabel.setText("");

			} else if (filteredList.contains(selectedItem)) {
				todoListView.getSelectionModel().select(selectedItem);
			} else {
				todoListView.getSelectionModel().selectFirst();
			}
		} else {
			filteredList.setPredicate(wantAllItems);
			todoListView.getSelectionModel().select(selectedItem);
		}
	}

	@FXML
	public void handleExit() {
		Platform.exit();
	}

}
