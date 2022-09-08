import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class DashboardController extends LoginWindowController {

	
	@FXML private ImageView avatarPicture;
	@FXML private Button borrowedButton;
	@FXML private Button finesButton;
	@FXML private Button browserButton;
	@FXML private Button logoutButton;
	@FXML private Button transactionButton;
	@FXML private Button reservedButton;
	@FXML private Button requestedButton;
	@FXML private Button createUserButton;
	
	@FXML
	private void handleCreateUserEvent(ActionEvent e) {
		if (userType == "l") {
		} else {
			createUserButton.setDisable(true);
		}
	}
	
	@FXML
	private void handleRequestEvent(ActionEvent e) {
		
	}
	
	@FXML
	private void handleReservedEvent(ActionEvent e) {
		
	}
	
	@FXML
	private void handleTransactionEvent(ActionEvent e) {
		
	}
	
	@FXML
	private void handleFinesEvent(ActionEvent e) {
	
	}
	
	@FXML
	private void handleBorrowedEvent(ActionEvent e) throws Exception {
		loadNewWindow("WithdrawnResources");
	}
	
	@FXML
	private void handleBrowserEvent(ActionEvent e) {
		loadNewWindow("Browser");
	}
	
	@FXML
	private void handleLogOutEvent(ActionEvent e) {
		
	}
	
	@FXML
	private void changeProfilePicture(MouseEvent e) {
		loadNewWindow("Avatar");
			
	}
	
	private void loadNewWindow(String FXMLFile) {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(FXMLFile + ".fxml"));
	        Parent root = (Parent) fxmlLoader.load();
	        Stage stage = new Stage();
	        stage.setScene(new Scene(root));  
	        stage.show();
	        } catch(Exception i) {
	        	i.printStackTrace();
	        }
	}
	public void initialize() {		
		if (userType == "u") {
			System.out.println(username + " is of type: " + userType);
		} else if (userType == "l") {
			System.out.println(username + " is of type: " + userType);
		} else {
			System.out.println(username + " is of type: UNDEFINED");
		}
	}
}
