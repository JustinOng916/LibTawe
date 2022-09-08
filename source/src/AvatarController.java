import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.image.WritableImage;
import javax.imageio.ImageIO;
import javafx.scene.input.*;
import javafx.stage.Stage;

/**
 * @author Damilohun Olatunji and Sevastiani
 * @version 1.0
 * @since 20/11/2018thor Damilohun and Sevastiani
 */
public class AvatarController {
	/**
	 * cb is the background canvas
	 */
	private GraphicsContext cb;
	/**
	 * cf is the background canvas
	 */
	private GraphicsContext cf;
	private boolean drawline = false;
	private boolean drawcircle = false;
	private boolean erase = false;
	private boolean pencil = false;
	private boolean square = false;
	private boolean rectangle = false;
	/**
	 * starX is the starting X coordinate of the shape when the mouse is pressed
	 */
	private double startX;
	/**
	 * starY is the starting Y coordinate of the shape when the mouse is pressed
	 */
	private double startY;
	/**
	 * lastX is the X coordinate of where the shape will end when the mouse is
	 * released
	 */
	private double lastX;
	/**
	 * lastY is the Y coordinate of where the shape will end when the mouse is
	 * released
	 */
	private double lastY;
	private double diameter;
	/**
	 * To parse the user details to the Avatar controller
	 */
	private CurrentSessionData temp;
	private String username;
	private String type;

	@FXML
	Button back;
	@FXML
	ColorPicker backGround;
	@FXML
	ColorPicker fill;
	@FXML
	ColorPicker border;
	@FXML
	Canvas tempCanvas;
	@FXML
	Canvas canvas;
	@FXML
	Button lineButton;
	@FXML
	Button circleButton;
	@FXML
	Button eraser;
	@FXML
	Slider borderSizeSlider;
	@FXML
	Slider lineSizeSlider;

	@FXML
	private void showHelpDialogue(ActionEvent event) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setResizable(true);
		alert.setWidth(600);
		alert.setWidth(600);
		alert.setTitle("Hello, you asked for help.");
		alert.setHeaderText("Instructions");
		alert.setContentText("1.Choose the fill color and background color you want." + "\n"
				+ "2.Press the Background button in order for the color to change." + "\n"
				+ "3.Choose the shape you want to draw." + "\n"
				+ "4.Press the Eraser button to erase parts of you drawing." + "\n"
				+ "5.Press the Clear button to start from scatch." + "\n"
				+ "6. Press the Change Profile Image to set the drawing as your profile picture");
		alert.showAndWait();
	}

	/**
	 * This method sets the canvas color by getting the color value from backGround
	 * colorpicker.
	 */
	@FXML
	private void setBackColor() {
		GraphicsContext gc = canvas.getGraphicsContext2D();
		gc.setFill(backGround.getValue());
		gc.fillRect(0, 0, 440, 440);
		System.out.println("Background Changed");
	}

	/**
	 * This method takes a screenshot of the canvas and the saves it as a png file
	 * which is named after the username of the use logged in.
	 */
	@FXML
	private void saveImage() {
		WritableImage image = new WritableImage(440, 440);
		canvas.snapshot(null, image);
		File file = new File("Images/" + username + ".png");
		try {
			ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
			System.out.println("Image Saved");
		} catch (IOException s) {
			System.out.println(s.getMessage());
		}
	}

	@FXML
	private void clearCanvas() {
		GraphicsContext gc = canvas.getGraphicsContext2D();
		gc.setFill(backGround.getValue());
		gc.fillRect(0, 0, 440, 440);
		System.out.println("Background Cleared");
	}

	/**
	 * This method gets last coordinates X and Y of the shape drawn when the event e
	 * occurs.
	 * 
	 * @param e is the event where the mouse is pressed.
	 */
	@FXML
	private void onMousePressedListener(MouseEvent e) {
		this.lastX = e.getX();
		this.lastY = e.getY();

	}

	/**
	 * This method draws the shape select when the event e occurs
	 * 
	 * @param e is the event where the mouse is released.
	 */
	@FXML
	private void onMouseReleasedListener() {
		if (drawcircle) {
			drawCircle();
		} else if (drawline) {
			drawLine();
		} else if (square) {
			drawSquare();
		} else if (rectangle) {
			drawRect();
		}
	}

	/**
	 * This method gets start coordinates X and Y of the shape drawn when the event
	 * e occurs.
	 * 
	 * @param e is the event where the mouse is dragged
	 */
	@FXML
	private void onMouseDraggedListener(MouseEvent e) {
		this.startX = e.getX();
		this.startY = e.getY();
		if (erase) {
			erase();
		} else if (pencil) {
			pencil();
		}
	}

	@FXML
	private void onMouseExitedListener() {
		System.out.println("Shape Drawn");
	}

	@FXML
	private void Back() {
		Stage stage = (Stage) back.getScene().getWindow();
		stage.close();

	}

	/**
	 * This method draws a circle with a border in the color of the border colorpicker
	 * and is filled in the color of the fill colorpicker.
	 */
	private void drawCircle() {
		diameter = startX - lastX;
		diameter = lastY - startY;
		cb.setLineWidth(borderSizeSlider.getValue());
		cf.clearRect(0, 0, tempCanvas.getWidth(), tempCanvas.getHeight());
		cb.setStroke(border.getValue());
		cb.strokeOval(startX, startY, diameter, diameter);
		cb.setFill(fill.getValue());
		cb.fillOval(startX, startY, diameter, diameter);
	}

	/**
	 * This method draws a square with a border in the color of the border colorpicker
	 * and is filled in the color of the fill colorpicker.	
	 */
	private void drawSquare() {
		double side = lastX - startX;
		cb.setLineWidth(borderSizeSlider.getValue());
		cb.setFill(fill.getValue());
		cb.setStroke(border.getValue());
		cb.strokeRect(startX, startY,side,side);
		cb.fillRect(startX, startY,side,side);
	}

	/**
	 * This method draws a line in the color of the fill colorpicker and of width
	 * equal to the value of the lineSizeSlider .
	 */
	private void drawLine() {
		cf.clearRect(0, 0, tempCanvas.getWidth(), tempCanvas.getHeight());
		cb.setStroke(fill.getValue());
		cb.setLineWidth(lineSizeSlider.getValue());
		cb.strokeLine(startX, startY, lastX, lastY);
	}

	/**
	 * This method draws a rectangle with a border in the color of the border colorpicker
	 * and is filled in the color of the fill colorpicker.
	 */
	private void drawRect() {
		double width = lastX - startX;
		double height = lastY - startY;
		cb.setLineWidth(borderSizeSlider.getValue());
		cb.setStroke(border.getValue());
		cb.setFill(fill.getValue());
		cb.strokeRect(startX, startY, width, height);
		cb.fillRect(startX, startY, width, height);
	}

	/**
	 * This method draws continuous rounded rectangles in the color of backGround
	 * and of same size as the lineSizeSlider
	 */
	private void erase() {
		double size = lineSizeSlider.getValue();
		cb.setFill(backGround.getValue());
		cb.fillRoundRect(startX, startY, size, size, size, size);
	}

	/**
	 * This method draws continuous rounded rectangles in the color of fill
	 * and of same size as the lineSizeSlider
	 */
	private void pencil() {
		double size =lineSizeSlider.getValue();
		cb.setFill(fill.getValue());
		cb.fillRoundRect(startX, startY, size, size, size, size);
	}

	/**
	 * Sets the current tool used as circle.
	 */
	@FXML
	private void setCircleAsCurrentShape() {
		drawline = false;
		drawcircle = true;
		erase = false;
		pencil = false;
		square = false;
		rectangle = false;
	}

	/**
	 * Sets the current tool used as line.
	 */
	@FXML
	private void setLineAsCurrentShape() {
		drawline = true;
		drawcircle = false;
		erase = false;
		pencil = false;
		square = false;
		rectangle = false;
	}

	/**
	 * Sets the current tool used as eraser.
	 */
	@FXML
	private void setErase() {
		drawline = false;
		drawcircle = false;
		erase = true;
		pencil = false;
		square = false;
		rectangle = false;
	}

	/**
	 * Sets the current tool used as pencil.
	 */
	@FXML
	private void setPencil() {
		drawline = false;
		drawcircle = false;
		erase = false;
		pencil = true;
		square = false;
		rectangle = false;
	}

	/**
	 * Sets the current tool used as square.
	 */
	@FXML
	private void setSquare() {
		drawline = false;
		drawcircle = false;
		erase = false;
		pencil = false;
		square = true;
		rectangle = false;
	}

	/**
	 * Sets the current tool used as rectangle.
	 */
	@FXML
	private void setRectangle() {
		drawline = false;
		drawcircle = false;
		erase = false;
		pencil = false;
		square = false;
		rectangle = true;
	}

	/**
	 * Initializes everything needed for a new window.
	 */
	public void initialize() {
		cb = canvas.getGraphicsContext2D();
		cf = tempCanvas.getGraphicsContext2D();
		GraphicsContext gc = canvas.getGraphicsContext2D();
		gc.setFill(backGround.getValue());
		gc.fillRect(0, 0, 440, 440);
		temp = new CurrentSessionData();
		this.username = temp.getUsername();
		this.type = temp.getUserType();

	}
}