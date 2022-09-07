import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.text.ParseException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * LibrarianResourceRequestController.java
 *
 * @author Alex Jones
 *
 * No copyright infringements intended, holds no copyright.
 *
 * This controls the GUI for LibrarianResourceRequestList.fxml. This allows
 * the Librarian to accept resource requests, putting the specified resources
 * in borrow list of the user requesting the borrow.
 *
 * @version 1.0
 * @since 02/12/2018
 */
public class LibrarianResourceRequestController {

	private ArrayList<Label> userList = new ArrayList<Label>();
	private ArrayList<Label> IDList = new ArrayList<Label>();
	private ArrayList<Label> resourceList = new ArrayList<Label>();
	private ArrayList<String> titleList = new ArrayList<String>();
	private ArrayList<String> reservedList = new ArrayList<String>();
	CurrentSessionData temp = new CurrentSessionData();
	// Users that have requested resources fields
	@FXML
	VBox resRequestedList;
	@FXML
	VBox resUserList;
	@FXML
	Button userResReqFilter;
	@FXML
	Button acceptReqButton;
	@FXML
	Button backReqListButton;

	/**
	 * Initializes the window by calling a method that fills the screen with
	 * the resource and the user wishing to borrow the item.
	 */
	public void initialize() {
		fillRequestedResourcesList();
	}

	/**
	 * This closes the window, returning the user to the dashboard.
	 *
	 * @param e This is the event upon clicking the return button.
	 */
	@FXML
	private void handleReturnRequestListEvent(ActionEvent e) {
		Stage stage = (Stage) backReqListButton.getScene().getWindow();
		stage.close();
	}

	@FXML
	private void handleResReqUserFilterEvent(ActionEvent e) {

	}

	/**
	 * Accepts the users attempt to borrow a resource, adding the user
	 * to the userBRDD.txt file as well as the date they borrowed it.
	 * @param e The user clicks the 'Accept' button.
	 */
	@FXML
	private void handleAcceptRequestEvent(ActionEvent e) {
		int j = 0;
		for (int i = 0; i < IDList.size(); i++) {
			temp.addToLine(titleList.get(i), userList.get(i).getText(), ";", "ResourceQueue");
			temp.editLine("ResourceDatabase", IDList.get(i).getText().trim(), "!ab!", "!b!");
			temp.addLineToFile("userBRDD", (userList.get(i).getText() + " : "
					+ temp.getWholeFileLine("ResourceDatabase", IDList.get(i).getText()) + ";00/00/0000"));
			j++;
		}

		while (j < resourceList.size()) {
			temp.addLineToFile("userResRes", (titleList.get(j) + " : " + userList.get(j).getText()));
			try {
				temp.getFileLine("ReservedQueue", titleList.get(j));
				temp.addToLine(titleList.get(j), userList.get(j).getText(), ";", "ReservedQueue");
			} catch (Exception exc) {
				temp.addLineToFile("ReservedQueue", titleList.get(j) + " : " + userList.get(j).getText() + ";");
			}
			j++;
		}
		for (int k = 0; k < reservedList.size(); k++) {
			addOverdueDates(titleList.get(k));
		}

		temp.libAcceptBorrowRequests();
	}

	/**
	 *
	 * @param title The title of the resource
	 */
	private void addOverdueDates(String title) {
		String date = temp.getDate();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/mm/yyyy");
		Calendar c = Calendar.getInstance();
		try {
			c.setTime(sdf.parse(date));
		} catch (java.text.ParseException pe) {
			pe.printStackTrace();
		}
		c.add(Calendar.DATE, 7);
		date = sdf.format(c.getTime());

		String blockOfBRDD = temp.showBRDD(title);
		System.out.println("The block is " + blockOfBRDD);
		Scanner BRDDFile = new Scanner(blockOfBRDD);
		BRDDFile.useDelimiter("\n");
		ArrayList<String> linesOfBRDD = new ArrayList<String>();
		while (BRDDFile.hasNext()) {
			linesOfBRDD.add(BRDDFile.next());
		}

		boolean dateIsChanged = false;

		for (int i = 0; i < linesOfBRDD.size(); i++) {
			if (linesOfBRDD.get(i).contains("00/00/0000")) {
				temp.editLine("userBRDD", "00/00/0000", "00/00/0000", date);
				
				dateIsChanged = true;
			}
		}

	}

	/**
	 * Loops through the userReqRes.txt file and displays all resources
	 * that have a have been requested and the users that have requested
	 * the resource.
	 */
	private void fillRequestedResourcesList() {
		Scanner in = temp.getFile("userReqRes");
		int i = 0;
		while (in.hasNextLine()) {
			String curLine = in.nextLine();
			Scanner line = new Scanner(curLine);
			line.useDelimiter(";");

			String thisUser = line.next();
			String title = line.next().trim();
			String copyID = "";
			String resReq = "";

			if (!curLine.contains("[RESERVE REQUEST")) {
				try {
					copyID = line.next();
					IDList.add(new Label(copyID));
				} catch (NoSuchElementException e) {
					System.out.println("----------- The element isn't here bro -----------");
				}
			} else {
				resReq = line.next();
				reservedList.add(resReq);
			}
			userList.add(new Label(thisUser));

			resourceList.add(new Label(copyID + " " + title));
			titleList.add(title);

			resUserList.getChildren().add(userList.get(i));
			resRequestedList.getChildren().add(resourceList.get(i));
			i++;

		}

	}
}
