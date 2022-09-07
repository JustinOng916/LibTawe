import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.NoSuchElementException;
import java.util.Scanner;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

/**
 * LibrarianAcceptReturnsController.java
 *
 * @author Alex Jones
 *
 * Controlls the GUI for LibrarianAcceptReturns.fxml. This lists and
 * displays the users and the resources they are requesting to return
 * and allows them to accept the returns.
 *
 * @version 1.0
 * @since 09/12/2018
 */
public class LibrarianAcceptReturnsController {

	private CurrentSessionData temp = new CurrentSessionData();
	private ArrayList<String> wholeLibRRLine = new ArrayList<String>();
	private ArrayList<String> libRRLineToDisplay = new ArrayList<String>();
	@FXML
	ListView reqReturnList;
	@FXML
	Button backReturnsButton;
	@FXML
	Button acceptButton;

	/**
	 * Initializes the fxml file by listing the users that wish to
	 * return resources and the resources that they were once borrowing.
	 */
	public void initialize() {
		fillReqReturnList();
		for (int i = 0; i < wholeLibRRLine.size(); i++) {
			System.out.println(wholeLibRRLine.get(i) + " <- this line");
		}
		Scanner libRR = temp.getFile("libRR");
		libRR.useDelimiter("\n");
		Scanner in = temp.getFile("userBRDD");
		in.useDelimiter("\n");
		while (in.hasNext()) {
			String curLine = in.next();
			if (!curLine.contains("00/00/0000")) {
				String[] splitUsername = curLine.split(" : ");
				String[] splitData = splitUsername[1].split(";");
				String user = splitUsername[0];
				String resType = splitData[0];
				String copyTitle = splitData[2];
				String dueDate = splitData[10];
				int daysOverdue = temp.countDaysOverdue(user, dueDate);
				try {
					if (curLine.contains(user) && libRR.next().contains(user)) {
						temp.setFine(daysOverdue, resType, user);
					}
				} catch (NoSuchElementException eNSE) {
					System.out.println(eNSE.getMessage());
				}
			}
		}
		// Scanner in = temp.getFile("overdueCopies");

		ArrayList<String> overdueCopies = new ArrayList<String>();

		while (in.hasNextLine()) {
			overdueCopies.add(in.nextLine());
		}

	}

	/**
	 * Closes the current window, taking the Librarian back to the dashboard.
	 * @param e
	 */
	@FXML
	private void handleReturnsReturnEvent(ActionEvent e) {
		Stage stage = (Stage) backReturnsButton.getScene().getWindow();
		stage.close();
	}

	/**
	 * Accepting the borrowed resources takes user and resource out of the
	 * userBRDD.txt file. It will also remove the user ReservedQueue.txt file
	 * and ResourceQueue.txt file if necessary.
	 * @param e
	 */
	@FXML
	private void handleAcceptEvent(ActionEvent e) {
		int selectedIndex = reqReturnList.getSelectionModel().getSelectedIndex();

		if (selectedIndex < 0) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error: Cannot find selection");
			alert.setHeaderText(null);
			alert.setContentText(
					"Cannot view Resource as no Resource was selected. Please select a resource to return");
			alert.showAndWait();
		} else {
			String selectedLine = (String) reqReturnList.getSelectionModel().getSelectedItem();
			String[] lineSplit = selectedLine.split(" | ");
			for (int i = 0; i < wholeLibRRLine.size(); i++) {
				try {
					// System.out.println("Already in file");
					String currentRRLine = wholeLibRRLine.get(i);
					System.out.println(currentRRLine);

					String[] splitData = currentRRLine.split(" : ");
					String[] splitFields = splitData[1].split(";");
					String userToRemove = splitData[0];
					String resType = splitFields[0];
					String resID = splitFields[1];
					String resTitle = splitFields[2];
					String resDueDate = splitFields[10];
					String newLine = splitData[1].replace(";" + splitFields[10], "");

					System.out.println("Does it have a reserved queue? " + temp.hasReservedQueue(resTitle));
					System.out.println("Does it have a resource queue? " + temp.hasResourceQueue(resTitle));
					if (!(temp.hasResourceQueue(resTitle) && temp.hasReservedQueue(resTitle))) {
						newLine = newLine.replace(splitFields[9], "!a!");
						temp.overwriteLine("ResourceDatabase", resID, newLine);
						System.out.println("\n" + splitData[0] + "\n");
						temp.removeLineFromFileBRDD(resID);
						try {
							Writer fileWriter = new FileWriter("Data/libRR.txt");
							fileWriter.write("");
						} catch (Exception ex) {
							ex.printStackTrace();
						}
					} else {
						System.out.println("You are in the else block");

						String reservedQueue = temp.getFileLine("ReservedQueue", resTitle);
						String[] splitResReqQueue = reservedQueue.split(";");
						String updateReserveQueue = resTitle + " : ";

						for (int k = 1; k < splitResReqQueue.length; k++) {
							updateReserveQueue += splitResReqQueue[k] + ";";
						}
						temp.overwriteLine("ReservedQueue", resTitle, updateReserveQueue);
						System.out.println(updateReserveQueue);

						temp.editLine("userBRDD", resID, userToRemove, splitResReqQueue[0]);

						String resourceQueue = temp.getFileLine("ResourceQueue", resTitle);
						String[] splitResQueue = resourceQueue.split(";");
						String updateResourceQueue = resTitle + " : ";

						for (int k = 0; k < splitResQueue.length; k++) {
							updateResourceQueue += splitResQueue[k] + ";";
						}
						updateResourceQueue += splitResReqQueue[0];
						updateResourceQueue = updateResourceQueue.replace((userToRemove + ";"), "");
						temp.editLine("userBRDD", resID, splitFields[10], "00/00/0000");
						temp.overwriteLine("ResourceQueue", resTitle, updateResourceQueue);
						System.out.println(updateResourceQueue);

						temp.editLine("userResRes", splitResReqQueue[0], resTitle + " : " + splitResReqQueue[0], "");
						temp.overwriteLine("overdueCopies", (userToRemove + ";" + resTitle), "");
						try {
							Writer fileWriter = new FileWriter("Data/libRR.txt");
							fileWriter.write("");
						} catch (Exception ex) {
							ex.printStackTrace();
						}

						try {
							addOverdueDates(resTitle);
						} catch (IndexOutOfBoundsException ieEX) {
							System.out.println(ieEX.getMessage() + " out of bounds");
						}
					}
				} catch (NoSuchElementException a) {
					System.out.println(a.getMessage() + " error accepting return exception");
					System.out.println("Can't remove " + selectedLine + " from libRR.txt");
					try {
						Writer fileWriter = new FileWriter("Data/libRR.txt");
						fileWriter.write("");
					} catch (Exception exCatch) {
						exCatch.printStackTrace();
					}
				}
			}
		}
	}

	/**
	 * Adds overdue resources to the userBRDD.txt file if the resource is overdue
	 * @param title The title of the resource.
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
	 * Fills the GUI with the the user that is attempting to return a
	 * resource and the resource they wish to return.
	 */
	private void fillReqReturnList() {
		Scanner in = temp.getFile("libRR");
		int i = 0;
		while (in.hasNextLine()) {
			String curLine = in.nextLine();
			String[] splitLine = curLine.split(" : ");
			String[] splitData = splitLine[1].split(";");
			String user = splitLine[0];

			wholeLibRRLine.add(curLine);
			if (splitData[10].equals("00/00/0000")) {
				libRRLineToDisplay
						.add(user + " | " + splitData[1] + " | " + splitData[2] + " | " + "No due date set" + " | ");
			} else {
				libRRLineToDisplay
						.add(user + " | " + splitData[1] + " | " + splitData[2] + " | " + splitData[10] + " | ");
			}

			reqReturnList.getItems().add(libRRLineToDisplay.get(i));
			i++;
		}
	}
}
