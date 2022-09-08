import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class CreateBookController {
	private CurrentSessionData temp;
	private String username;
	private String userType;
	@FXML
	private TextField title;
	@FXML
	private TextField yor;
	@FXML
	private TextField ca;
	@FXML
	private TextField author;
	@FXML
	private TextField publisher;
	@FXML
	private TextField genre;
	@FXML
	private TextField isbn;
	@FXML
	private TextField language;
	
	@FXML
	private void handleCreateResourceEvent() {
		Librarian lib = new Librarian("","","","","","","","","","");
		String title = this.title.getText();
		String yor = this.yor.getText();
		int ca = Integer.valueOf(this.ca.getText());
		String author = this.author.getText();
		String publisher = this.publisher.getText();
		String genre = this.genre.getText();
		String isbn = this.isbn.getText();
		String language = this.language.getText();
		lib.createBook("000", title, yor, ca, author, publisher, genre, isbn);
	}
	
	public void initialize() {
		temp = new CurrentSessionData();
		username = temp.getUsername();
	}
}
