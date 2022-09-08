/** @author Damilohun Olatunji and Sevastiani
 * @version 1.0
 * @since 20/11/2018
 */
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class AvatarDefaultImageController {
	@FXML
	ImageView imgone;
	@FXML
	ImageView imgtwo;
	@FXML
	ImageView imgthree;
	@FXML
	ImageView imgfour;
	@FXML
	Button back;
	
	
	private CurrentSessionData temp;
	private String username;
	private String type;
	private Alert alert;
	private Optional<ButtonType> choice;
	
/**
 * This method is to alert the user that the profile image is about to change
 */
	private void showHelpDialogue() {
		alert = new Alert(AlertType.CONFIRMATION);
		alert.setResizable(true);
		alert.setWidth(600);
		alert.setWidth(600);
		alert.setContentText("You have now changed your avatar");
		choice = alert.showAndWait();	
	}
	
/**
 * This method saves the default image chosen as a png with the name of the user.
 * @param image is the default image that is going to be saved after the user has chosen it 
 */
	private void saveImage(Image image) {
		File file = new File("Images/" + username + ".png");
		try {
			ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
			System.out.println("Image Saved");
		} catch (IOException s) {
			System.out.println(s.getMessage());
		}
	}
	
	public void setImage(String imageName) {
		try {
			FileInputStream input = new FileInputStream(imageName);
			Image image = new Image(input);
			saveImage(image);
		}
		catch(FileNotFoundException i) {
			i.getMessage();
		}
	}
	
	@FXML
	public void setImageOne(MouseEvent e) {
		String image = "Images/LiamRocks.png";
		showHelpDialogue();
		if(choice.get() == ButtonType.OK) {
			setImage(image);				
			System.out.println("Image One Set");
		}
	}
	
	@FXML
	public void setImageTwo(MouseEvent e) {
		String image = "Images/Liam_O.png";
		showHelpDialogue();
		if(choice.get() == ButtonType.OK) {
			setImage(image);				
			System.out.println("Image Two Set");
		}	
	}
	
	@FXML
	public void setImageThree(MouseEvent e) {
		String image = "Images/LiamGroup.png";
		showHelpDialogue();
		if(choice.get() == ButtonType.OK) {
			setImage(image);				
			System.out.println("Image Three Set");
		}
	}

	@FXML
	public void setImageFour(MouseEvent e) {
		String image = "Images/LiamSolo.png";
		showHelpDialogue();
		if(choice.get() == ButtonType.OK) {
			setImage(image);				
			System.out.println("Image Four Set");
		}
	}
	
	@FXML 
	private void Back() {
		Stage stage = (Stage) back.getScene().getWindow();
		stage.close();

	}

/**
 * Initialises everything needed for a new window.
 */
	public void initialize() {
		try {
			FileInputStream input = new FileInputStream("Images/LiamRocks.png");
			FileInputStream input2 = new FileInputStream("Images/Liam_O.png");
			FileInputStream input3 = new FileInputStream("Images/LiamGroup.png");
			FileInputStream input4 = new FileInputStream("Images/LiamSolo.png");
			Image image = new Image(input);
			Image image2 = new Image(input2);
			Image image3 = new Image(input3);
			Image image4 = new Image(input4);
			imgone.setImage(image);
			imgtwo.setImage(image2);
			imgthree.setImage(image3);
			imgfour.setImage(image4);
		} catch (FileNotFoundException e) {
			e.getMessage();
		}
		
		temp = new CurrentSessionData();
		this.username = temp.getUsername();
		this.type = temp.getUserType();

	}

}