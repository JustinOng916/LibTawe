import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * UserBorrowedResourcesController.java
 *
 * @author Alex Jones
 *
 * No copyright infringements intended, holds no copyright.
 *
 * This class controls the GUI of UserBorrowedResources.fxml. It shows the
 * resources that are in the currently logged user's possession in a list
 * form.
 *
 * @version 1.0
 * @since 02/12/2018
 */

public class UserBorrowedResourcesController {

	private CurrentSessionData temp;
	private String username;
	private String userType;
	private ArrayList<String> listedResources = new ArrayList<String>();
	private ArrayList<String> listedDueDates = new ArrayList<String>();
	private ArrayList<String> wholeBRDDLine = new ArrayList<String>();
	// Fields for WithdrawnResources
	@FXML
	private ListView resourceList;
	@FXML
	private Button backWithdrawnButton;
	@FXML
	private Button returnResourceButton;
	@FXML
	private ListView dueDateList;

	/**
	 * This initialises everything needed for a new window, including an instance of
	 * CurrentSessionData to access the borrowed items that a user has in their
	 * possession.
	 */
	public void initialize() {
		temp = new CurrentSessionData();
		username = temp.getUsername();
		userType = temp.getUserType();
		fillWithdrawnResourceAndDateLists();
	}

	/**
	 * This closes the window, returning the user to the dashboard.
	 *
	 * @param e The user clicks the 'Return' button.
	 */
	@FXML
	private void handleReturnWithdrawnEvent(ActionEvent e) {
		Stage stage = (Stage) backWithdrawnButton.getScene().getWindow();
		stage.close();
	}

	/**
	 * Allows the user to select a resource they are currently borrowing and
	 * return it by first selecting an item in the list view and then pressing the
	 * 'Return' button. This then adds the item to the libRR.txt file for a
	 * librarian to accept the return.
	 * @param e
	 */
	@FXML
	private void handleReturnResourceEvent(ActionEvent e) {
		int selectedIndex = resourceList.getSelectionModel().getSelectedIndex();

		if (selectedIndex < 0) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error: Cannot find selection");
			alert.setHeaderText(null);
			alert.setContentText(
					"Cannot view Resource as no Resource was selected. Please select a resource to return");
			alert.showAndWait();
		} else {
			String selectedLine = (String) resourceList.getSelectionModel().getSelectedItem();
			String[] lineSplit = selectedLine.split(" ");
			for (int i = 0; i < wholeBRDDLine.size(); i++) {
				try {
					System.out.println("The line from BRDD is :" + wholeBRDDLine.get(i));
					System.out.println(lineSplit[lineSplit.length - 1]);
					if (wholeBRDDLine.get(i).contains(lineSplit[lineSplit.length - 1])) {
						System.out.println("You made it past the first check");
						try { 
							temp.getFileLine("libRR", username);
							System.out.println("Already in file");
						} catch (Exception bottomLevelException) {
							//This is adding the string to the libRR.txt file
							temp.addLineToFile("libRR", wholeBRDDLine.get(i));
						}
					} else {
						System.out.println("Can't add " + selectedLine + " to libRR.txt");
					}
				} catch (NoSuchElementException a) {
					System.out.println(a.getMessage() + " this is the exception");
				}

			}
		}
	}

	/**
	 * Fills the list view with all resources currently borrowed by the logged
	 * user by checking the userBRDD.txt file.
	 */
	private void fillWithdrawnResourceAndDateLists() {
		Scanner in = temp.getFile("userBRDD");
		int i = 0;
		while (in.hasNextLine()) {
			String curLine = in.nextLine();
			Scanner line = new Scanner(curLine);
			line.useDelimiter(";");

			if (curLine.contains(username)) {
				wholeBRDDLine.add(curLine);
				curLine.replace(username + " : ", "");
				String typeOfObject = line.next();
				String copyID = line.next();
				String title = line.next();

				String[] lineSplit = curLine.split(";");

				listedResources.add(title + " " + copyID);
				if (curLine.contains("00/00/0000")) {
					listedDueDates.add("No due date set");
				} else {
					listedDueDates.add(lineSplit[10]);
				}

				resourceList.getItems().add(listedResources.get(i));
				dueDateList.getItems().add(listedDueDates.get(i));
				i++;
			}

		}
	}
}