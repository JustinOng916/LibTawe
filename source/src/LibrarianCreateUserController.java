import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.HBox;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * LibrariranCreateUserController.java
 *
 * @author Alex Jones
 *
 * No copyright infringements intended, holds no copyright.
 *
 * This class controls the GUI of LibrarianCreateUser.fxml. It allows
 * librarians, and librarians only, to create both Users and Librarians
 * and add them to the UserDatabase.txt file.
 *
 * @version 1.0
 * @since 02/12/2018
 */

public class LibrarianCreateUserController {
	private CurrentSessionData temp = new CurrentSessionData();

	// Creation of new user fields
	@FXML
	TextField usernameInput;
	@FXML
	TextField firstNameInput;
	@FXML
	TextField lastNameInput;
	@FXML
	TextField phoneNumberInput;
	@FXML
	TextField addLn1Input;
	@FXML
	TextField addLn2Input;
	@FXML
	TextField postcodeInput;
	@FXML
	TextField empDateInput;
	@FXML
	TextField staffNumInput;
	@FXML
	Button createUserButton;
	@FXML
	Button userFormReturnButton;

	/**
	 * Allows the user to add a user by pressing the 'CREATE' button. The method
	 * then checks through the TextFields to see if the Employment Date and Staff
	 * Number are empty then a normal user is made, otherwise a librarian is made.
	 * Both are made by adding variables to an array list with our delimiter (" : ")
	 * for the user to be searched through easily by other classes.
	 */
	@FXML
	private void handleCreateUserEvent() {		
		ArrayList<String> allFields;
		
		String username = usernameInput.getText();
		String userIMG = usernameInput.getText() + ".png";
		String fName = firstNameInput.getText();
		String lName = lastNameInput.getText();
		String pNum = phoneNumberInput.getText();
		String aL1 = addLn1Input.getText();
		String aL2 = addLn2Input.getText();
		String pCode = postcodeInput.getText();
		String empDate;
		String sNum;
	
		try {
			empDate = empDateInput.getText();
			sNum = staffNumInput.getText();
		} catch (NullPointerException e) {
			sNum = "null";
			empDate = "null";
			System.out.println("Null pointer exception in last two textfields, assuming user");
		}


		if ((!empDateInput.getText().trim().isEmpty()) && (!staffNumInput.getText().trim().isEmpty())) {
			createUserButton.setDisable(true);
			empDate = empDateInput.getText().trim();
			sNum = staffNumInput.getText().trim();
			allFields = new ArrayList<String>();
			
			allFields.add(username + " : ");
			allFields.add("l : ");
			allFields.add(username + ".png" + " : ");
			allFields.add(fName + " : ");
			allFields.add(lName + " : ");
			allFields.add(pNum + " : ");
			allFields.add(aL1 + " : ");
			allFields.add(aL2 + " : ");
			allFields.add(pCode + " : ");
			allFields.add(empDate + " : ");
			allFields.add(sNum);
			String newLibr = "";
			for (int i = 0; i < allFields.size(); i++) {
				newLibr += allFields.get(i);
			}
			System.out.println(newLibr);
			temp.addLineToFile("userList", newLibr);

		} else if ((empDateInput.getText().trim().isEmpty()) && !staffNumInput.getText().trim().isEmpty()
				|| (!empDateInput.getText().trim().isEmpty() && staffNumInput.getText().trim().isEmpty())) {

			if (empDate.trim().isEmpty()) {
				Alert fail = new Alert(AlertType.INFORMATION);
				fail.setHeaderText("Failure with employment start date");
				fail.setContentText(
						"You haven't entered an employment start date... Please enter in format dd/mm/yyyy");
				fail.showAndWait();
			} else if (sNum.trim().isEmpty()) {
				Alert fail = new Alert(AlertType.INFORMATION);
				fail.setHeaderText("Failure with staff number");
				fail.setContentText("You haven't entered a staff number... Please enter");
				fail.showAndWait();
			} 
		} else if  (empDateInput.getText().trim().isEmpty() && staffNumInput.getText().trim().isEmpty()) {
			createUserButton.setDisable(true);
			allFields  = new ArrayList<String>();
			
			allFields.add(username + " : ");
			allFields.add("u : ");
			allFields.add(username + ".png : ");
			allFields.add(fName + " : ");
			allFields.add(lName + " : ");
			allFields.add(pNum + " : ");
			allFields.add(aL1 + " : ");
			allFields.add(aL2 + " : ");
			allFields.add(pCode);
			String newUser = "";
			for (int i = 0; i < allFields.size(); i++) {
				newUser += allFields.get(i);
			}
			System.out.println(newUser);
			temp.addLineToFile("userList", newUser);
			temp.addLineToFile("userFTP", (username + " : " + "0.0"));
			
		} 
	}

	/**
	 * This closes the current window to take the librarian back to the dashboard.
	 */
	@FXML
	private void handleUserFormReturnEvent() {
		Stage stage = (Stage) userFormReturnButton.getScene().getWindow();
		stage.close();
	}

}
