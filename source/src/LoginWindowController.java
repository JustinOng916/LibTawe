import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;
import java.util.Scanner;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;

/**
 * LoginWindowController.java
 *
 * @author Alex Jones
 *
 * No copyright infringements intended, holds no copyright.
 *
 * This controls the GUI for LoginWindow.fxml. This allows the user to login
 * to the system. It checks through the UserList.txt file to see if the user
 * exists, and if the user is a normal user or a Librarian. The dashboard
 * associated with that user type is then opened. The login will fail if the
 * user does not exist. This is the first window that any user that wishes to
 * use this system will see.
 *
 * @version 1.0
 * @since 20/11/2018
 */
public class LoginWindowController {

	protected String date;
	protected String userType;
	protected String username;
	private Label label;
	private String imagePath;
	
	private static String path = "Data/userList.txt";
	
	@FXML private TextField userAttempt;
	@FXML private Button loginButton;
	@FXML private Label taweWelcome;
	@FXML private Label loginResult;


	/**
	 * Sets the user type.
	 *
	 * @param type The type of user to be logged in.
	 */
	public void setUserType(String type) {
		this.userType = type;
	}

	/**
	 * Sets the username.
	 *
	 * @param username The username of the user to be logged.
	 */
	public void setUsername(String username) {
		this.username = username;
	}


	/**
	 * This method is the main bulk of this class, and utilizes other methods
	 * within the class to search the userList.txt file to see if the exists
	 * before opening the dashboard associated with the user type that is being
	 * logged in.
	 *
	 * @param e This is the event upon clicking the login button.
	 */
	@FXML
	private void handleLoginButtonEvent(ActionEvent e) {
		if (searchUsers(userAttempt.getText())) {
			loginResult.setText("Succeeded");
			retrieveUserType(userAttempt.getText());
			setUsername(userAttempt.getText());
			userAttempt.clear();
			
			if (userType.equals("l")) {
			try {
				writeToFile(this.username, this.userType);
				FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("LibrarianDashboard.fxml"));		        
		        Parent root = (Parent) fxmlLoader.load();
		        showInputTextDialog();
		        
		        
		        Stage stage = new Stage();
		        
		        stage.setScene(new Scene(root));  
		        stage.show();
		        } catch(Exception i) {
		        	i.printStackTrace();
		        }
			} else {
				try {
					writeToFile(this.username, this.userType);
					FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("UserDashboard.fxml"));		        
			        Parent root = (Parent) fxmlLoader.load();
			           
			        Stage stage = new Stage();
			        
			        stage.setScene(new Scene(root));  
			        stage.show();
			        } catch(Exception i) {
			        	i.printStackTrace();
			        }				
			}
		} else {
			loginResult.setText("Failed");
		}
	}

	/**
	 * Gets and sets the date based on the date inputted by a Librarian. This
	 * will be used for calculating fines for resources.
	 */
	private void showInputTextDialog() {
		TextInputDialog dateDialog = new TextInputDialog("dd/mm/yyyy");
		 
        dateDialog.setTitle("Set Date");
        dateDialog.setHeaderText("Date in format 'dd/mm/yyyy'");
        dateDialog.setContentText("Date:");
 
        Optional<String> result = dateDialog.showAndWait();
 
        result.ifPresent(e -> {
            CurrentSessionData tempDate = new CurrentSessionData();
            tempDate.setDate(e);
        });
	}

	/**
	 * Parses line file to find the type of user that is associated with the
	 * entered username.
	 *
	 * @param username This specifies what user's type we need.
	 */
	public void retrieveUserType(String username) {
		File profileFile = new File(path); /* File initialised, with path */
		Scanner in = new Scanner(System.in); /* Scanner initialised */

		/*
		 * Try and scan a file by changing the Scanners type. If the file cannot be
		 * found, print an error message and close the program.
		 */
		try {
			in = new Scanner(profileFile);
		} catch (FileNotFoundException e) {
			System.out.println("Cannot open " + profileFile);
			System.exit(0);
		}
		// System.out.println("File found!"); //This was for initial testing
		
		/*
		 * Evaluates the data file line by line, and assigning required values for the
		 * profiles being parsed.
		 */
		while (in.hasNextLine()) {
			String curLine = in.nextLine();
			Scanner line = new Scanner(curLine);
			
			line.useDelimiter(" : ");
			
			if (curLine.contains(username)) {
				System.out.println(username + " found!");
				username = line.next();
				setUserType(line.next());
				System.out.println("This user is of type: " + userType);
				imagePath = line.next();
			}
			
			line.close();
		}

		in.close();
	}

	/**
	 * Checks the user is in the system.
	 *
	 * @param username Username of the logged user.
	 * @return
	 */
	public static boolean searchUsers(String username) {
		System.out.println(readUsers(username));
		return readUsers(username);		
	}

	/**
	 * This method opens the userList.txt file for the searchUsers() to use.
	 *
	 * @param username Specifies what user we want.
	 * @return Returns true if the username is present.
	 */
	public static boolean readUsers(String username) {
		File profileFile = new File(path); /* File initialised, with path */
		Scanner in = new Scanner(System.in); /* Scanner initialised */

		/*
		 * Try and scan a file by changing the Scanners type. If the file cannot be
		 * found, print an error message and close the program.
		 */
		try {
			in = new Scanner(profileFile);
		} catch (FileNotFoundException e) {
			System.out.println("Cannot open " + profileFile);
			System.exit(0);
		}

		return validateUser(in, username);
	}

	/**
	 * This checks if the username is present in the userList.txt file.
	 *
	 * @param in       Scanner to go through file.
	 * @param username Specifies username;
	 * @return Returns true if username is present.
	 */
	public static boolean validateUser(Scanner in, String username) {

		boolean hasUser = false;
		in.useDelimiter("\n");
		
		/*
		 * Evaluates the data file line by line, and assigning required values for the
		 * profiles being parsed.
		 */
		while (in.hasNextLine()) {
			String curLine = in.nextLine();
			Scanner line = new Scanner(curLine);
			line.useDelimiter(" : ");
			
			while (line.hasNext()) {
				String parseField = line.next();
				if (parseField.equals(username)) {
					hasUser = true;
					System.out.println(username + " found");
				} 

			}
		}

		in.close();
		return hasUser;
	}

	/**
	 * Overwrites file.
	 *
	 * @param username Specifies username.
	 * @param userType Specifies user type.
	 */
	public void writeToFile(String username, String userType) {
		CurrentSessionData tempSesh = new CurrentSessionData(username, userType);
	  
	}
}
