import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * CreateResourceController.java
 *
 * @author Damilohun Olatunji and Sevastiani Pieri
 *
 * No copyright infringements intended, holds no copyright.
 *
 * This controls GUI for CreateResource.fxml. This is a gateway class
 * for adding particular resources to the ResourceDatabase.txt file.
 *
 * @version 1.0
 * @since 29/11/2018
 */

public class CreateResourceController {
	private CurrentSessionData temp;
	private String username;

	/**
	 * Initializes the class by creating a new instance of CurrentSessionData.
	 */
	public void initialize() {
		temp = new CurrentSessionData();
		username = temp.getUsername();
	}

	/**
	 * Calls loadNewWindow with the createDVD fxml file.
	 *
	 * @param e User clicks 'DVD' button.
	 */
	@FXML
	private void handleCreateDVD(ActionEvent e) {
		loadNewWindow("createDvD");
		System.out.println("DVD Created");
	}

	/**
	 * Calls loadNewWindow with the createLaptop fxml file.
	 *
	 * @param e The 'Laptop' button.
	 */
	@FXML
	private void handleCreateLaptop(ActionEvent e) {
		loadNewWindow("createLaptop");
		System.out.println("Laptop Created");
	}

	/**
	 * Calls loadNewWindow with the createBook fxml file
	 *
	 * @param e The 'Book' button.
	 */
	@FXML
	private void handleCreateBook(ActionEvent e) {
		loadNewWindow("creatBook");
		System.out.println("Book Created");
	}

	/**
	 * Loads the fxml file associated with the selected button to open
	 * the respective GUI for adding a resource.
	 *
	 * @param FXMLFile The name of the fxml file to be opened.
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
