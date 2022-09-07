import java.awt.event.MouseEvent;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/**
 * LibrarianFinePaymentController.java
 *
 * @author Alex Jones
 *
 * No copyright infringements intended, holds no copyright.
 *
 * This class controls the GUI for UserFinesController.fxml. This
 * displays all the fines that the user has accumulated and allows
 * the user to pay an amount that the input that are stored in the
 * userFTP.txt file.
 *
 * @version 1.0
 * @since 02/12/2018
 */

public class UserFinesController {
	
	private CurrentSessionData temp;
	private String username;
	private String userType;
	private double totalFine = 0.0;
	
	
	@FXML private Label userFine;
	@FXML private HBox finePaymentSection;
	@FXML private TextField fineAmountToPay;
	@FXML private Button payFineButton;
	@FXML private Button finesBoxReturnButton;
	@FXML private Label helpMessage;

	/**
	 * Initializes the GUI for the user to use. An instance of CurrentSessionData
	 * is initialized so that the userFTP.txt file can be opened and output to
	 * this GUI for the user to see.
	 */
	public void initialize() {
		temp = new CurrentSessionData();
		username = temp.getUsername();
		userType = temp.getUserType();

		if (temp.isInFile("userFTP", username)) {
			String fineLine = temp.getFileLine("userFTP", this.username);
			totalFine += Double.parseDouble(fineLine.substring(fineLine.lastIndexOf(": ") + 1));
			System.out.println(username + " fine is: ï¿½" + totalFine);
			setUserFine();
		} else {
			userFine.setText(String.format("%.2f", 0));
		}

	}

	/**
	 * The input amount that the user wants to pay is saved into the Fine
	 * Payment Request database along with the user's name for reference.
	 *
	 * @param e The user clicks the 'Pay Fine' button.
	 */
	@FXML
	private void handlePayFineEvent(ActionEvent e) {
		String tempPayFine = fineAmountToPay.getText();
		Double tempFine = Double.parseDouble(tempPayFine);
		helpMessage.setText("Request filed for payment of £" + String.format("%.2f", tempFine));
		fineAmountToPay.clear();
		payFineButton.setDisable(true);
		temp.writeUserFinePayRequest(this.username, tempFine);
	}

	/**
	 * Closes the current window, taking the user back to the dashboard.
	 *
	 * @param e The user clicks on the 'Return' button.
	 */
	@FXML
	private void handleFinesBoxReturnEvent(ActionEvent e) {
		Stage stage = (Stage) finesBoxReturnButton.getScene().getWindow();
	    stage.close();
	}

	/**
	 * The total amount of fines that the user has accumulated is displayed
	 * here. If the fine is zero, then the pay fine button is disabled, but
	 * true otherwise.
	 */
	private void setUserFine() {
		String fine = String.format("%.2f", totalFine);
		
		userFine.setText(fine);
		
		if (Double.parseDouble(fine) <= 0) {
			payFineButton.setDisable(true);
		} else {
			payFineButton.setDisable(false);
		}
	}
	

}
