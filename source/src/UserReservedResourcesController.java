import java.util.ArrayList;
import java.util.Scanner;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * ResourceQueue.java
 *
 * @author Alex Jones
 *
 * No copyright infringements intended, holds no copyright.
 *
 * This controls the GUI for UserReservedResourcesController.fxml. This shows
 * the resources that the currently logged user has reserved for them from the
 * userResRes.txt file.
 *
 * @version 1.0
 * @since 02/12/2018
 */
public class UserReservedResourcesController {

	private CurrentSessionData temp;
	private String username;
	private String userType;
	private ArrayList<Label> userList = new ArrayList<Label>();

	// Fields for ReservedResources
	@FXML
	VBox resResList;
	@FXML
	Button withdrawResButton;
	@FXML
	Button backReservedButton;

	/**
	 * Initialises everything needed for a new window, including an instance
	 * of CurrentSessionData to read and display information from the
	 * userResRes.txt file.
	 */
	public void initialize() {
		temp = new CurrentSessionData();
		username = temp.getUsername();
		userType = temp.getUserType();
		fillReservedResourcesList();
	}

	/**
	 * Closes the window, taking the user back to the dashboard.
	 *
	 * @param e This is the event upon clicking the return button.
	 */
	@FXML
	private void handleReturnReservedEvent(ActionEvent e) {
		Stage stage = (Stage) backReservedButton.getScene().getWindow();
		stage.close();
	}

	/**
	 * 
	 * @param e
	 */
	@FXML
	private void handleWithdrawEvent(ActionEvent e) {

	}

	/**
	 * Fills the list with all the logged user's requested resources.
	 */
	@FXML
	private void fillReservedResourcesList() {
		Scanner in = temp.getFile("userResRes");
		int i = 0;
		while (in.hasNextLine()) {
			String curLine = in.nextLine();
			Scanner line = new Scanner(curLine);
			line.useDelimiter(" : ");

			if (curLine.contains(username)) {
				
				String title = line.next();
				String thisUser = line.next();

				userList.add(new Label(title));

				resResList.getChildren().add(userList.get(i));
				i++;
			}
		}
	}
}
