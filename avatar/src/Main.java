import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;

/**
 *
 * @author Damilohun and Sebi
 */
public class Main extends Application {

	@Override
	public void start(Stage stage) throws Exception {
		stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("Avatar.fxml"))));
		stage.setTitle("Paint App");
		stage.show();

	}

	public static void main(String[] args) {
		launch(args);
	}
}
