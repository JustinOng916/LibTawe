import java.util.ArrayList;
import java.util.Scanner;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * UserRequestedResourcesController.java
 *
 * @author Alex Jones
 *
 * No copyright infringements intended, holds no copyright.
 *
 * This controls the GUI for UserRequestedResources.fxml. This shows the resources
 * that the user has currently requested, but have not yet been accepted by the
 * librarian.
 *
 * @version 1.0
 * @since 02/12/2018
 */
public class UserRequestedResourcesController {

	private CurrentSessionData temp;
	private String username;
	private String userType;
	private ArrayList<Label> userList = new ArrayList<Label>();

	// Fields for RequestedResources
	@FXML
	VBox reqResList;
	@FXML
	Button backRequestedButton;

	/**
	 * This initialises everything needed for a new window. This includes an
	 * instance of CurrentSessionData to read and output the currently requested
	 * resources on the currently logged account, from the userReqRes.txt file.
	 */
	public void initialize() {
		temp = new CurrentSessionData();
		username = temp.getUsername();
		userType = temp.getUserType();
		fillRequestedResourcesList();
	}

	/**
	 * Closes the window, taking the user back to the dashboard.
	 *
	 * @param e The user clicks the 'Return' button.
	 */
	@FXML
	private void handleReturnRequestedEvent(ActionEvent e) {
		Stage stage = (Stage) backRequestedButton.getScene().getWindow();
		stage.close();
	}

	/**
	 * Loops through the Requested Resources database and displays them
	 * all to the GUI, for the user to see.
	 */
	private void fillRequestedResourcesList() {
		Scanner in = temp.getFile("userReqRes");
		int i = 0;
		while (in.hasNextLine()) {
			String curLine = in.nextLine();
			Scanner line = new Scanner(curLine);
			line.useDelimiter(";");

			if (curLine.contains(username)) {

				String thisUser = line.next();
				String copyId = line.next();
				String title = line.next();

				userList.add(new Label(copyId + " " + title));

				reqResList.getChildren().add(userList.get(i));
				i++;
			}
		}
	}
}
