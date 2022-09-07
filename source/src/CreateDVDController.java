import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * CreateBookController.java
 *
 * @author Damilohun Olatunji and Sevastiani Pieri
 *
 * No copyright infringements intended, holds no copyright.
 *
 * This controls the GUI for createDVD.fxml. It allows for librarians
 * to add a book to the Resource Database with all its respective data.
 *
 * @version 1.0
 * @since 29/11/2018
 */

public class CreateDVDController {
	private CurrentSessionData temp;
	private String username;
	private String userType;
	@FXML
	private TextField title;
	@FXML
	private TextField yor;
	@FXML
	private TextField ca;
	@FXML
	private TextField director;
	@FXML
	private TextField runtime;
	@FXML
	private TextField language;
	@FXML
	private Button back;

	/**
	 * Initializes the class by creating a new instance of CurrentSessionData in
	 * order to write to the ResourceDatabase.txt file.
	 */
	public void initialize() {
		temp = new CurrentSessionData();
		username = temp.getUsername();
	}

	/**
	 * Creates a DVD based on the entered information in each respective text field.
	 *
	 * @param e The librarian 'Create Resource' button is clicked.
	 */
	@FXML
	private void handleCreateResourceEvent(ActionEvent e) {
		Librarian lib = new Librarian("","","","","","","","","","");
		String title = this.title.getText();
		String yor = this.yor.getText();
		int ca = Integer.valueOf(this.ca.getText());
		String director = this.director.getText();
		String runtime = this.runtime.getText();
		String language = this.language.getText();
		lib.createDVD("000", title, yor, ca, director, runtime, language);
	}

	@FXML
	private void handleBackEvent(ActionEvent e) {
		Stage stage = (Stage) back.getScene().getWindow();
		stage.close();
	}

}
