package rana.todolist.datamodel;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
/**
 * 
 * @author >>RanaSiroosian<<
 */
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class TodoData {

	private static TodoData instance = new TodoData();
	private static String filename = "TodoListItems.txt";

	private ObservableList<ToDoItems> todoItems;
	private DateTimeFormatter formatter;

	public static TodoData getInstance() {
		return instance;
	}

	private TodoData() {
		formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
	}

	public ObservableList<ToDoItems> getTodoItems() {
		return todoItems;
	}

//	public void setTodoItems(List<ToDoItems> todoItems) {
//		this.todoItems = todoItems;
//	}

	public void loadTodoItems() throws IOException {
		todoItems = FXCollections.observableArrayList();
		Path path = Paths.get(filename);
		BufferedReader br = Files.newBufferedReader(path);

		String input;

		try {
			while ((input = br.readLine()) != null) {
				String[] itemPieces = input.split("\t");
				String shortDescription = itemPieces[0];
				String details = itemPieces[1];
				String dateString = itemPieces[2];

				LocalDate date = LocalDate.parse(dateString, formatter);
				ToDoItems todoItem = new ToDoItems(shortDescription, details, date);
				todoItems.add(todoItem);

			}
		} finally {
			if (br != null) {
				br.close();
			}
		}
	}

	public void storeTodoItems() throws IOException {
		Path path = Paths.get(filename);
		BufferedWriter bw = Files.newBufferedWriter(path);
		try {
			Iterator<ToDoItems> iter = todoItems.iterator();
			while (iter.hasNext()) {
				ToDoItems item = iter.next();
				bw.write(String.format("%s\t%s\t%s", item.getShortDescription(), item.getDetails(),
						item.getDeadline().format(formatter)));
				bw.newLine();
			}
		} finally {
			if (bw != null) {
				bw.close();
			}
		}
	}

	public void addTodoItem(ToDoItems item) {
		todoItems.add(item);
	}

	public void deleteTodoItem(ToDoItems item) {
		todoItems.remove(item);
	}

}
