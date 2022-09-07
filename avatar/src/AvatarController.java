import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.WritableImage;
import javax.imageio.ImageIO;
import javafx.scene.input.*;

/**
 *
 * @author Damilohun
 */
public class AvatarController implements Initializable {
	private GraphicsContext gcB, gcF;
	private boolean drawline = false, drawoval = false, erase = false;
	double startX, startY, lastX, lastY, oldX, oldY;
	double hg;

	@FXML
	ColorPicker backGround;
	@FXML
	ColorPicker pen;
	@FXML
	Canvas canvas,tempCanvas;
	@FXML
	Button lineButton;
	@FXML
	Button circleButton;
	@FXML
	Button eraser;

	@FXML
	private void showHelpDialogue(ActionEvent event) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setResizable(true);
		alert.setWidth(600);
		alert.setWidth(600);
		alert.setTitle("Hello, you asked for help.");
		alert.setHeaderText("It's Very Easy");
		alert.setContentText("Careful with the next step!");
		alert.showAndWait();
	}

	@FXML
	private void setBackColor(ActionEvent e) {
		GraphicsContext gc = canvas.getGraphicsContext2D();
		gc.setFill(backGround.getValue());
		gc.fillRect(0, 0, 440, 440);
		System.out.println("Background Changed");
	}

	@FXML
	private void saveImage(ActionEvent e) {
		WritableImage image = new WritableImage(440, 440);
		canvas.snapshot(null, image);
		File file = new File("canvas.png");
		try {
			ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
			System.out.println("Image Saved");
		} catch (Exception s) {

		}
	}

	@FXML
	private void clearCanvas(ActionEvent e) {
		GraphicsContext gc = canvas.getGraphicsContext2D();
		gc.setFill(backGround.getValue());
		gc.fillRect(0, 0, 440, 440);
		System.out.println("Background Cleared");
	}

	@FXML
	private void onMousePressedListener(MouseEvent e) {
		this.startX = e.getX();
		this.startY = e.getY();
		this.oldX = e.getX();
		this.oldY = e.getY();
	}

	@FXML
	private void onMouseReleaseListener(MouseEvent e) {
		if (drawoval) {
			drawOval();
		} else if (drawline) {
			drawLine();
		} else if (erase) {
			erase();
		}
	}

	@FXML
	private void handleDragDetect() {
		/* drag was detected, start drag-and-drop gesture */
		System.out.println("Mouse Drag Detected");
	}

	@FXML
	private void onMouseDraggedListener(MouseEvent e) {
		this.lastX = e.getX();
		this.lastY = e.getY();

		if (drawoval) {
			drawOvalEffect();
		} else if (drawline) {
			drawLineEffect();
		} else if (erase) {
			erase();
		}
	}

	@FXML
	private void onMouseExitedListener(MouseEvent event) {
		System.out.println("Line Drawn");
	}

	private void drawOval() {
		hg = lastY - startY;

		// if(circleButton.isPressed()){
		gcB.setFill(pen.getValue());
		gcB.fillOval(startX, startY, hg, hg);
		// }
	}

	private void drawLine() {
		gcB.setLineWidth(3);
		gcB.setStroke(pen.getValue());
		gcB.strokeLine(startX, startY, lastX, lastY);
	}

	private void erase() {
		gcB.setLineWidth(10);
		gcB.setStroke(backGround.getValue());
		gcB.strokeLine(startX, startY, lastX, lastY);
	}

	private void drawOvalEffect() {
		hg = lastY - startY;

		// if(circleButton.isPressed()){
		gcF.clearRect(0, 0, tempCanvas.getWidth(), tempCanvas.getHeight());
		gcF.setFill(pen.getValue());
		gcF.fillOval(startX, startY, hg, hg);
		// }
	}

	private void drawLineEffect() {
		gcF.setLineWidth(3);
		gcF.setStroke(backGround.getValue());		
		gcF.clearRect(0, 0, tempCanvas.getWidth(), tempCanvas.getHeight());
		gcF.strokeLine(startX, startY, lastX, lastY);
	}

	@FXML
	private void setOvalAsCurrentShape() {
		drawline = false;
		drawoval = true;
		erase = false;
	}

	@FXML

	private void setLineAsCurrentShape() {
		drawline = true;
		drawoval = false;
		erase = false;
	}

	@FXML
	private void setErase() {
		drawline = false;
		drawoval = false;
		erase = true;
	}

	@FXML
	private void handleMouseDrop() {
		System.out.println("Mouse Dropped");
		System.out.println();
	}

	@FXML
	private void handleDrag() {
		/* drag was detected, start drag-and-drop gesture */
		System.out.println("Mouse Dragged");
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// throw new UnsupportedOperationException("Not supported yet.");
		gcB = canvas.getGraphicsContext2D();
		gcF = tempCanvas.getGraphicsContext2D();

	}
}
