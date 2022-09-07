import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.NoSuchElementException;
import java.util.Scanner;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

/**
 * ResourceQueue.java
 *
 * @author Samuel Matthews, Damilohun Olatunji, Sevastiani Pieri and Alex Jones.
 *
 * No copyright infringements intended, holds no copyright.
 *
 * This controls the GUI for ResourceView.fxml. All the data about the
 * specified resource is shown here, along with options to edit (if the
 * currently logged user is a Librarian) and request (if the currently
 * logged user is a User)
 *
 * @version 2.0
 * @since 10/12/2018
 */

public class ViewResourceController {
	String resourceToView;
	String resourceSplit[];

	private CurrentSessionData userPermissions = new CurrentSessionData();
	private String userName;
	private String userType;

	@FXML
	ImageView resourceImage;

	BufferedReader resourceBR = null; // The BufferedReader used to find the resource to edit.
	final String DATABASE_LOCATION = "Data/resourceDatabase.txt"; // Location of database.
	final String DATABASE_LOCATION2 = "Data/resourceDatabase2.txt"; // Location of database.
	File resourceDatabase = new File(DATABASE_LOCATION); // The file to be loaded into BufferedReader.
	File resourceDatabase2 = new File(DATABASE_LOCATION2); // The file to be loaded into BufferedReader.
	final String SELECTED_LOCATION = "Data/selectedResource.txt"; // Location of the selected resource.
	File selectedResource = new File(SELECTED_LOCATION); // The file to be loaded into BufferedReader.

	@FXML
	Label changeLabel1;
	@FXML
	Label changeLabel2;
	@FXML
	Label changeLabel3;
	@FXML
	Label changeLabel4;

	@FXML
	TextField typeField;
	@FXML
	TextField idField;
	@FXML
	TextField nameField;
	@FXML
	TextField yearField;
	@FXML
	TextField copiesField;
	@FXML
	TextField imgNameField;
	@FXML
	TextField changeField1;
	@FXML
	TextField changeField2;
	@FXML
	TextField changeField3;
	@FXML
	TextField changeField4;

	@FXML
	Button saveChangesButton;
	@FXML
	Button returnButton;
	@FXML
	Button requestButton;

	/**
	 * Initializes the GUI for use. This includes reading from the
	 * selectedResource.txt file to obtain the resource that the
	 * user selected from browser. It also checks what type of user
	 * is viewing the resource so the correct type of resource is
	 * being viewed. e.g. librarians will be able to see the ID of
	 * an individual item, while normal users cannot as they see the
	 * generic type with the amount of copies available.
	 */
	public void initialize() {
		try {
			BufferedReader readResource = new BufferedReader(new FileReader(selectedResource));
			resourceToView = readResource.readLine();
			System.out.println(resourceToView);
		} catch (Exception e) {
			e.printStackTrace();
		}

		copiesField.setEditable(false);
		setUserPermissions();
		setResourceData();
		setImageView();

		if (userType.equals("u")) {
			checkFinesToPay();
		}

		userName = userPermissions.getUsername();
		userType = userPermissions.getUserType();
	}

	/**
	 * Gets the user type from CSD and sets the permissions for the specific user.
	 * This will disable the 'Save' button among other things so that users cannot
	 * access procedures that they should not be able to access.
	 */
	private void setUserPermissions() {

		userName = userPermissions.getUsername();
		userType = userPermissions.getUserType();

		if (userType.equals("u")) {
			typeField.setEditable(false);
			idField.setEditable(false);
			nameField.setEditable(false);
			yearField.setEditable(false);
			imgNameField.setEditable(false);
			changeField1.setEditable(false);
			changeField2.setEditable(false);
			changeField3.setEditable(false);
			changeField4.setEditable(false);

			saveChangesButton.setDisable(true);
			requestButton.setDisable(false);
		} else if (userType.equals("l")) {
			typeField.setEditable(true);
			idField.setEditable(true);
			nameField.setEditable(true);
			yearField.setEditable(true);
			imgNameField.setEditable(true);
			copiesField.setEditable(true);
			changeField1.setEditable(true);
			changeField2.setEditable(true);
			changeField3.setEditable(true);
			changeField4.setEditable(true);

			saveChangesButton.setDisable(false);
			requestButton.setDisable(true);
		}
	}

	/**
	 * Checks the type of the selected resource and changes the labels
	 * accordingly.
	 */
	private void setResourceData() {
		System.out.println(resourceToView);
		String[] resourceSplit = resourceToView.split(";");
		System.out.println(resourceSplit[0]);

		if (resourceSplit[0].equals("book")) {
			changeLabel1.setText("Author:");
			changeLabel2.setText("Genre:");
			changeLabel3.setText("ISBN:");
			changeLabel4.setText(" ");
			changeField4.setVisible(false);
		} else if (resourceSplit[0].equals("laptop")) {
			changeLabel1.setText("Make:");
			changeLabel2.setText("Model:");
			changeLabel3.setText("OS:");
			changeLabel4.setText(" ");
			changeField4.setVisible(false);
		} else if (resourceSplit[0].equals("dvd")) {
			changeLabel1.setText("Director:");
			changeLabel2.setText("Runtime");
			changeLabel3.setText("Language:");
			changeLabel4.setText("Subtitle Languages:");
			changeField4.setText(resourceSplit[7]);
		}
		if (userType.equals("l")) {
			typeField.setText(resourceSplit[0]);
			idField.setText(resourceSplit[1]);
			nameField.setText(resourceSplit[2]);
			yearField.setText(resourceSplit[3]);
			copiesField.setText(resourceSplit[4]);
			imgNameField.setText(resourceSplit[5]);
			changeField1.setText(resourceSplit[6]);
			changeField2.setText(resourceSplit[7]);
			changeField3.setText(resourceSplit[8]);
		} else {
			typeField.setText(resourceSplit[0]);
			nameField.setText(resourceSplit[1]);
			yearField.setText(resourceSplit[2]);
			copiesField.setText(resourceSplit[3]);
			imgNameField.setText(resourceSplit[4]);
			changeField1.setText(resourceSplit[5]);
			changeField2.setText(resourceSplit[6]);
			changeField3.setText(resourceSplit[7]);
		}

	}

	/**
	 * Sets the image for the specified resource in the image view.
	 */
	@FXML
	private void setImageView() {
		String[] resourceSplit = resourceToView.split(";");
		File file = null;
		if (userType.equals("l")) {
			file = new File("./Images/" + resourceSplit[5]);
		} else {
			file = new File("./Images/" + resourceSplit[4]);
		}

		Image image = new Image(file.toURI().toString());
		resourceImage.setImage(image);
	}

	/**
	 * Saves changes to an item as long as all text fields in the GUI
	 * are filled in some way.
	 */
	@FXML
	private void handleSaveEvent() {
		Boolean completedFields = checkTextFields();
		String[] resourceSplit = resourceToView.split(";");

		if (completedFields == true) {
			String resourceReplacement;

			String type = typeField.getText();
			String id = idField.getText();
			String name = nameField.getText();
			String year = yearField.getText();
			String copies = copiesField.getText();
			String imageN = imgNameField.getText();
			String cF1 = changeField1.getText();
			String cF2 = changeField2.getText();
			String cF3 = changeField3.getText();

			if (resourceSplit[0] == "dvd") {
				String cF4 = changeField4.getText();
				resourceReplacement = type + ";" + id + ";" + name + ";" + year + ";" + copies + ";" + imageN + ";"
						+ cF1 + ";" + cF2 + ";" + cF3 + ";" + cF4;
						
			} else if (resourceSplit[0] == "laptop") {
				resourceReplacement = type + ";" + id + ";" + name + ";" + year + ";" + copies + ";" + imageN + ";"
						+ cF1 + ";" + cF2 + ";" + cF3 + ";";
			} else if (resourceSplit[0] == "book") {
				resourceReplacement = type + ";" + id + ";" + name + ";" + year + ";" + copies + ";" + imageN + ";"
						+ cF1 + ";" + cF2 + ";" + cF3 + ";";
			}

			try {
				if (userType.equals("l")) {
					resourceBR = new BufferedReader(new FileReader(resourceDatabase));
				} else {
					resourceBR = new BufferedReader(new FileReader(resourceDatabase2));
				}

				Alert alert = new Alert(Alert.AlertType.INFORMATION);
				alert.setTitle("SUCCESS");
				alert.setHeaderText(null);
				alert.setContentText("Changes have been saved to this resource.");
				alert.showAndWait();

				Stage stage = (Stage) returnButton.getScene().getWindow();
				stage.close();

			} catch (IOException e) {
				System.out.println("Cannot open ResourceDatabase");
				System.exit(0);
			}
		}
	}

	/**
	 * Checks that all fields are filled, based on a checksum that adds one
	 * if a text field is null.
	 *
	 * @return Boolean
	 */
	private boolean checkTextFields() {
		String[] resourceSplit = resourceToView.split(";");
		int textFieldCheck = 0;

		if (typeField.getText().equals(null)) {
			textFieldCheck = +1;
		} else if (idField.getText().equals(null)) {
			textFieldCheck = +1;
		} else if (nameField.getText().equals(null)) {
			textFieldCheck = +1;
		} else if (yearField.getText().equals(null)) {
			textFieldCheck = +1;
		} else if (imgNameField.getText().equals(null)) {
			textFieldCheck = +1;
		} else if (changeField1.getText().equals(null)) {
			textFieldCheck = +1;
		} else if (changeField2.getText().equals(null)) {
			textFieldCheck = +1;
		} else if (changeField3.getText().equals(null)) {
			textFieldCheck = +1;
		} else if (resourceSplit[0] == "dvd") {
			if (changeField4.getText().equals(null)) {
				textFieldCheck = +1;
			}
		}

		if (textFieldCheck > 1) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Error: Cannot save changes");
			alert.setHeaderText(null);
			alert.setContentText("Please fill out all content to save changes to resource.");
			alert.showAndWait();

			return false;
		} else {
			return true;
		}
	}

	/**
	 * Adds the selected resource to the logged users requested resources
	 * in the userReqRes.txt file if the resource is available or gives an
	 * error if there are no copies left for the user to borrow.
	 */
	@FXML
	private void handleRequestEvent() {
		String title = nameField.getText();
		System.out.println(title);
		String line2 = userPermissions.showNumResources(title);
		System.out.println(line2);
		Scanner in = new Scanner(line2);
		in.useDelimiter("\n");
		String lineToTake = in.next();
		String[] lineSplit = lineToTake.split(";");
		boolean availableFound = true;

		try {
			userPermissions.getFileLine("ResourceQueue", lineSplit[2]);
		} catch (NoSuchElementException e) {
			userPermissions.addLineToFile("ResourceQueue", lineSplit[2] + " : ");
		}
		if (!userPermissions.isResourceQueueFull(lineSplit[2], Integer.parseInt(lineSplit[4]))) {
			while (!lineSplit[9].equals("!a!") && availableFound) {
				if (in.hasNext()) {
					availableFound = true;
					lineToTake = in.next();
					System.out.println("\nLINES TO TAKE ------" + lineToTake + "-----------------\n");
				} else {
					availableFound = false;
					System.out.println("Was this resource available?" + availableFound);
				}
				lineSplit = lineToTake.split(";");
			}

			if (availableFound = true && lineSplit[9].equals("!a!")) {
				String line = userName + ";" + lineSplit[2] + ";" + lineSplit[1] ;
				userPermissions.editLine("ResourceDatabase", lineSplit[1], "!a!", "!ab!");
				userPermissions.addLineToFile("userReqRes", line);
			} else {
				String lineToAdd = userName + ";" + title + ";" + "[RESERVE REQUEST]";
				userPermissions.addLineToFile("userReqRes", lineToAdd);
			}
		} else {
			String lineToAdd = userName + ";" + title + ";" + "[RESERVE REQUEST]";
			userPermissions.addLineToFile("userReqRes", lineToAdd);
		}
	}

	/**
	 * Checks if the user has any fines to pay, and does not allow the user
	 * to request resources if there is any outstanding fines attached to their
	 * user in the userFTP.txt file.
	 */
	private void checkFinesToPay() {
		String line = userPermissions.getWholeFileLine("userFTP", userName);
		String[] lineSplit = line.split(" : ");
		String fineRemaining = lineSplit[1];
		double intFines = Double.valueOf(fineRemaining);
		System.out.println(intFines);
		if (intFines > 0.0) {
			requestButton.setDisable(true);
		}
	}

	/**
	 * Shows the transaction history of the selected resource through the
	 * ResourceBorrowHistory.txt file.
	 */
	@FXML
	private void viewCopyHistoryEvent() {
		userPermissions.setCopyID(idField.getText());
		System.out.println("This resource : " + nameField.getText() + " has ID " + idField.getText());
	}

	/**
	 * Closes the window taking the user back to the browser window.
	 */
	@FXML
	private void handleReturnEvent() {
		Stage stage = (Stage) returnButton.getScene().getWindow();
		stage.close();
	}

}