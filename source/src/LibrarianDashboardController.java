import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/**
 * LibrarianDashboardController.java
 *
 * @author Alex Jones
 *
 * No copyright infringements intended, holds no copyright.
 *
 * This class controls the GUI for LibrarianDashboard.fxml. This is the
 * where the Librarian can gain access to all functions that the system
 * offers by clicking on the buttons that will open up other GUI windows
 * and controllers.
 *
 * @version 1.0
 * @since 02/12/2018
 */
public class LibrarianDashboardController {

	private CurrentSessionData temp;
	private String username;
	private String userType;

	@FXML
	private ImageView avatarPicture;
	@FXML
	private Button borrowedButton;
	@FXML
	private Button finesButton;
	@FXML
	private Button browserButton;
	@FXML
	private Button logoutButton;
	@FXML
	private Button createUserButton;
	@FXML
	private Button overdueButton;
	@FXML
	private Button returnsButton;

	/**
	 * This initialises everything needed for a new window, including an instance
	 * of CurrentSessionData to load the user's profile picture.
	 */
	public void initialize() {
		temp = new CurrentSessionData();
		username = temp.getUsername();
		userType = temp.getUserType();
		//System.out.println(username);
		File file = new File("./Images/" + username + ".png");
		Image image = new Image(file.toURI().toString());
		avatarPicture.setImage(image);
	}

	/**
	 * This method opens up options for the Librarian to change their profile.
	 * image.
	 *
	 * @param e The image box containing the current profile image is clicked.
	 */
	@FXML
	private void changeProfilePicture(ActionEvent e) {
		HBox root = new HBox();
        root.setPadding(new Insets(40));
        root.setSpacing(40);
 
        
        Button button1 = new Button("Default Image");
        Button button2 = new Button("Create Avatar");
 
        button1.setOnAction(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event) {
            	loadNewWindow("AvatarDefaultImageSelect");
            }
        });
 
        button2.setOnAction(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event) {
            	loadNewWindow("Avatar");
            }
        });
 
        root.getChildren().addAll(button1, button2);
        
        Stage stage = new Stage();
        Scene scene = new Scene(root, 300, 100);
        stage.setTitle("Avatar Image select.");
        stage.setScene(scene);
 
        stage.show();
	}

	/**
	 * Opens up the GUI associated with accepting user fines.
	 * @param e User clicks the 'Accept Returns' button.
	 */
	@FXML
	private void handleReturnRequestEvent(ActionEvent e) {
		loadNewWindow("LibrarianAcceptReturns");
	}

	/**
	 * Opens up the GUI associated with overdue copies.
	 * @param e User clicks the 'Overdue Copies' button.
	 */
	@FXML
	private void handleOverdueCopiesEvent(ActionEvent e) {
		loadNewWindow("LibrarianOverdueCopies");
	}

	/**
	 * Opens up the GUI associated with borrow requests.
	 * @param e User clicks the 'Borrow Request' button.
	 */
	@FXML
	private void handleBorrowRequestEvent(ActionEvent e) {
		loadNewWindow("LibrarianResourceRequestList");
	}

	/**
	 * Opens up the GUI associated with fine payments.
	 * @param e User clicks the 'Fine Payments' button.
	 */
	@FXML
	private void handleFinePaymentEvent(ActionEvent e) {
		loadNewWindow("LibrarianFineRequests");
	}

	/**
	 * Opens up the GUI associated with the browser. This opens up Browser,
	 * the Librarian version of browser that shows each individual copy of
	 * a resource rather than generic resource in Browser2.
	 * @param e User clicks the 'Browser' button.
	 */
	@FXML
	private void handleBrowserEvent(ActionEvent e) {
		loadNewWindow("Browser");
	}

	/**
	 * Opens up the GUI associated with creating a new user.
	 * @param e User clicks the 'Create User' button.
	 */
	@FXML
	private void handleCreateUserEvent(ActionEvent e) {
		loadNewWindow("LibrarianCreateUser");
		
	}

	/**
	 * Opens up the GUI associated with creating a new resource.
	 * @param e User clicks the 'Create Resource' button.
	 */
	@FXML
	private void handleCreateResourceEvent(ActionEvent e) {
		loadNewWindow("CreateResource");
	}

	/**
	 * This logs out Librarian, closing the window and taking the
	 * back to the login window.
	 * @param e User clicks the logout button.
	 */
	@FXML
	private void handleLogOutEvent(ActionEvent e) {
		Stage stage = (Stage) logoutButton.getScene().getWindow();
		stage.close();
	}

	/**
	 * The method that actually opens the GUI, using the path given
	 * from each button click event.
	 *
	 * @param FXMLFile The name of the FXML file.
	 */
	private void loadNewWindow(String FXMLFile) {
		System.out.println(username);
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(FXMLFile + ".fxml"));
			Parent root = (Parent) fxmlLoader.load();
			Stage stage = new Stage();
			stage.setScene(new Scene(root));
			stage.show();
		} catch (Exception i) {
			i.printStackTrace();
		}
	}
}
