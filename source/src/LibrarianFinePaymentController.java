import java.util.ArrayList;
import java.util.Optional;
import java.util.Scanner;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * LibrarianFinePaymentController.java
 *
 * @author Alex Jones
 *
 * No copyright infringements intended, holds no copyright.
 *
 * This class controls the GUI for LibrarianFineRequests.fxml. This
 * displays all users that have outstanding fines and allows the
 * librarian that is logged in to accept all fines at once.
 *
 * @version 1.0
 * @since 02/12/2018
 */
public class LibrarianFinePaymentController {

	private ArrayList<Label> userList = new ArrayList<Label>();
	private ArrayList<Label> fineList = new ArrayList<Label>();
	
	private CurrentSessionData tempTime = new CurrentSessionData();
	private String username;
	private String userType;
	
	//FinePayment.fxml fields
	@FXML VBox fineToPayList;
	@FXML VBox userWithFineList;
	@FXML Button acceptFineButton;
	@FXML Button backFineButton;

	/**
	 * Initializes the GUI for the librarian to use. This includes creating
	 * an instance of CurrentSessionData to open and write to files and
	 * populating the GUI with data.
	 */
	public void initialize() {
		tempTime = new CurrentSessionData();
		username = tempTime.getUsername();
		userType = tempTime.getUserType();

		fillLists();
	}

	/**
	 * Closes this stage to return the user to the dashboard.
	 *
	 * @param e Clicks on the 'Return' button.
	 */
	@FXML
	private void handleFineReturnEvent(ActionEvent e) {
		Stage stage = (Stage) backFineButton.getScene().getWindow();
		stage.close();
	}

	/**
	 * Calls a method to set the date and time that the fine was payed and stores this
	 * in the transaction history database.
	 *
	 * @param e Clicks on the 'Accept' button.
	 */
	@FXML
	private void handleAcceptPaymentEvent(ActionEvent e) {
		showInputTextDialog();
		
		tempTime.libAcceptUserFinePayment(tempTime.getDate(), tempTime.getTime());
	}

	/**
	 * Displays a window that allows the librarian to set the date and time that
	 * for the user to see when their fine was accepted (transaction history).
	 *
	 * @return The time the fine was payed.
	 */
	private String showInputTextDialog() {
		TextInputDialog timeDialog = new TextInputDialog("hh:mm");
		 
        timeDialog.setTitle("Set time");
        timeDialog.setHeaderText("Time in format 'hh:mm' in 24h format");
        timeDialog.setContentText("Time:");
 
        Optional<String> result = timeDialog.showAndWait();        
        
        result.ifPresent(e -> {
            tempTime.setTime(e);
        });
        
        return tempTime.getTime();
        
	}

	/**
	 * Opens the Payment Request database and displays all of the fines that are
	 * currently stored in the system along with the user's name for reference.
	 */
	private void fillLists() {
		Scanner in = tempTime.getFile("libFPR");
		int i = 0;
		while (in.hasNextLine()) {
			String curLine = in.nextLine();
			Scanner line = new Scanner(curLine);
			line.useDelimiter(" : ");
			
			
			String thisUser = line.next();
			String oldFine = curLine.substring(curLine.lastIndexOf(":") + 2);
			Double thisFine = Double.parseDouble(oldFine);
			
			userList.add(new Label(thisUser));
			fineList.add(new Label("£" + (String.format("%.2f", thisFine))));
			
			userWithFineList.getChildren().add(userList.get(i));
			fineToPayList.getChildren().add(fineList.get(i));
			i++;
		}
	}
}
