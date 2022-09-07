import java.util.ArrayList;
import java.util.Scanner;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * UserTransactionHistoryController.java
 *
 * @author Alex Jones
 *
 * No copyright infringements intended, holds no copyright.
 *
 * This controlls the GUI for UserTransactionHistory.fxml. This
 * shows the fines that have been payed by the user as well as
 * the date and time that they were processed. This class is
 * purely for showing the user the fines that they have processed
 * and gives no functionality for how they pay the fines.
 *
 * @version 1.0
 * @since 28/11/2018
 */

public class UserTransactionHistoryController {
	
	private CurrentSessionData temp;
	private String username;
	private String userType;
	private ArrayList<Label> fineList = new ArrayList<Label>();
	private ArrayList<Label> DTlist = new ArrayList<Label>();
	
	// Fields for TransactionHistory
	@FXML
	VBox paymentList;
	@FXML
	VBox dateAndTimeList;
	@FXML
	Button backTransactionButton;

	/**
	 * Initializes the GUI for the user to use, including creating an
	 * instance of CurrentSessionData to get the username, usertype
	 * and later the fines that they have payed.
	 */
	public void initialize() {
		temp = new CurrentSessionData();
		username = temp.getUsername();
		userType = temp.getUserType();
		fillTransactionAndDateList();
	}

	/**
	 * This class collects all information from the userTH.txt
	 * file and outputs it to the user for easy reading.
	 */
	private void fillTransactionAndDateList() {
		Scanner in = temp.getFile("userTH");
		int i = 0;
		while (in.hasNextLine()) {
			if (in.next().equals(username)) {
				String curLine = in.nextLine();
				String tempLine = curLine.replaceAll(" : ", "");
				
				System.out.println(curLine);
				System.out.println(tempLine);
				
				System.out.println("------" + " '" + username + "' + ' : '" + " was removed------");
				
				Scanner line = new Scanner(tempLine);
				line.useDelimiter(";");
				
				//System.out.println(line.next());
				Double thisFine = line.nextDouble();
				String thisDT = line.next();
				
				System.out.println("This line's fine is : " + thisFine);
				System.out.println("This line's date and time is : " + thisDT);
				
				fineList.add(new Label("�" + (String.format("%.2f", thisFine))));
				DTlist.add(new Label(thisDT));
				
				paymentList.getChildren().add(fineList.get(i));
				dateAndTimeList.getChildren().add(DTlist.get(i));
				line.close();
				i++;
			} else {
				in.nextLine();
			}
			
		}
		in.close();
	}

	/**
	 * This closes the current stage
	 *
	 * @param e The 'Return' button is clicked.
	 */
	@FXML
	private void handleReturnTransactionEvent(ActionEvent e) {
		Stage stage = (Stage) backTransactionButton.getScene().getWindow();
		stage.close();
	}
}
