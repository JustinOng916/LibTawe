import java.io.File;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * @author Alex Jones
 *
 * No copyright infringements intended, holds no copyright.
 *
 * This opens the first window of the application (LoginWindow.fxml) in
 * order to access the rest of the program.
 *
 * @version 1.0
 * @since 20/11/2018
 */
public class Main extends Application {

	/**
	 * @param primaryStage The first GUI window that the user sees(LoginWindow.fxml).
	 */
	@Override
	public void start(Stage primaryStage) {
		try {
			Pane root = (Pane)FXMLLoader.load(getClass().getResource("LoginWindow.fxml"));
			Scene scene = new Scene(root);
			
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {		
		launch(args);
	}
		
}
