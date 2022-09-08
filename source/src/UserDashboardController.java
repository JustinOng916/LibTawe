import java.io.File;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/**
 * UserDashboardController.java
 * 
 * @version 1.0
 * @since 02/12/2018
 * @author Alex Jones
 * 
 *         No copyright infringements intended, holds no copyright.
 * 
 *         This controls GUI for UserDashboard.fxml.
 */
public class UserDashboardController {
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
	private Button transactionButton;
	@FXML
	private Button reservedButton;
	@FXML
	private Button requestedButton;

	/**
	 * Opens window for user's requested resources.
	 * 
	 * @param e
	 *            This is the event upon clicking the requested resources button.
	 */
	@FXML
	private void handleRequestEvent(ActionEvent e) {
		loadNewWindow("UserRequestedResources");
	}

	/**
	 * Opens window for user's reserved resources.
	 * 
	 * @param e
	 *            This is the event upon clicking the reserved resources button.
	 */
	@FXML
	private void handleReservedEvent(ActionEvent e) {
		loadNewWindow("UserReservedResources");
	}

	/**
	 * Opens window for user's transaction history.
	 * 
	 * @param e
	 *            This is the event upon clicking the transaction history button.
	 */
	@FXML
	private void handleTransactionEvent(ActionEvent e) {
		loadNewWindow("UserTransactionHistory");
	}

	/**
	 * Opens window for user's fines to pay.
	 * 
	 * @param e
	 *            This is the event upon clicking the fines button.
	 */
	@FXML
	private void handleFinesEvent(ActionEvent e) {
		loadNewWindow("UserFinesBox");
	}

	/**
	 * Opens window for user's borrowed resources.
	 * 
	 * @param e
	 *            This is the event upon clicking the borrowed resources button.
	 */
	@FXML
	private void handleBorrowedEvent(ActionEvent e) {
		loadNewWindow("UserBorrowedResources");
	}

	/**
	 * Opens window for browser.
	 * 
	 * @param e
	 *            This is the event upon clicking the browser button.
	 */
	@FXML
	private void handleBrowserEvent(ActionEvent e) {
		loadNewWindow("Browser2");
	}

	/**
	 * Closes window.
	 * 
	 * @param e
	 *            This is the event upon clicking the logout button.
	 */
	@FXML
	private void handleLogOutEvent(ActionEvent e) {
		// Enter code for saving whatever else.
		Stage stage = (Stage) logoutButton.getScene().getWindow();
		stage.close();
	}

	/**
	 * Changes profile image.
	 * 
	 * @param e
	 *            This is the event upon clicking the profile picture.
	 */
	@FXML
	private void changeProfilePicture(MouseEvent e) {

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
	 * Opens a new window.
	 * 
	 * @param FXMLFile
	 *            Specifies what window to be opened.
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

	/**
	 * Initialises everything needed for a new window.
	 */
	public void initialize() {
		temp = new CurrentSessionData();
		username = temp.getUsername();
		userType = temp.getUserType();
		File file = new File("./Images/" + username + ".png");
		Image image = new Image(file.toURI().toString());
		avatarPicture.setImage(image);

//		try {
//			temp.getFileLine("userBRDD", this.username);
//		} catch (NoSuchElementException e) {
//			System.out.println(username + " not found in file " + );
//		}

	}
}
