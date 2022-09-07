import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * LibrarianOverdueCopiesController.java
 *
 * This controlls the LibrarianOverdueCopues.fxml file. This shows all
 * overdue resources on the system, the user that is currently borrowing
 * them, and the amount of time that they have been overdue for.
 */
public class LibrarianOverdueCopiesController {
	@FXML
	VBox overdueCopiesList;
	@FXML
	Button backOverdueButton;

	CurrentSessionData temp = new CurrentSessionData();
	private ArrayList<Label> overdueCopies = new ArrayList<Label>();

	/**
	 * Initializes the GUI by calling a function to fill the overdueCopiesList.
	 */
	public void initialize() {
		try {
			fillOverdueCopiesList();
		} catch (IndexOutOfBoundsException e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Closes the current window, returning the user to the dashboard
	 * @param e
	 */
	@FXML
	private void handleOverdueReturnEvent(ActionEvent e) {
		Stage stage = (Stage) backOverdueButton.getScene().getWindow();
		stage.close();
	}

	/**
	 * Populates the overdueCopiesList with the user, the title of the resource
	 * and how overdue the copy is by reading from the userBRDD.txt file.
	 */
	@FXML
	private void fillOverdueCopiesList() {
		try {
			PrintWriter pw = new PrintWriter(new File("./Data/overdueCopies.txt"));
			pw.print("");
			pw.close();
		} catch (FileNotFoundException e) {
			System.out.println("overdueCopies.txt wasn't found");
		}

		Scanner in = temp.getFile("userBRDD");
		in.useDelimiter("\n");
		int i = 0;
		while (in.hasNext()) {
			String curLine = in.next();
			if (!curLine.contains("00/00/0000")){
				String[] splitUsername = curLine.split(" : ");
				String[] splitData = splitUsername[1].split(";");
				String user = splitUsername[0];
				String copyTitle = splitData[2];
				String dueDate = splitData[10];
				int daysOverdue = temp.countDaysOverdue(user, dueDate);
				System.out.println("\n" + user + " and days " + daysOverdue + "\n");
				if (daysOverdue > 0) {
					temp.addLineToFile("overdueCopies", user + ";" + copyTitle + ";" + daysOverdue);
					
					overdueCopies.add(new Label(user + " | " + copyTitle + " | " + daysOverdue));

					overdueCopiesList.getChildren().add(overdueCopies.get(i));
					i++;
				} else {
					System.out.println(user + " has the copy for " + -(daysOverdue) + " more days");
				}
				
			} else {
				System.out.println("not possible");
			}
			
		}

		in.close();
		
	}
}
