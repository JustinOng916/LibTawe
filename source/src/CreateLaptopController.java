import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class CreateLaptopController {
	
	
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
		private TextField manufacturer;
		@FXML
		private TextField OS;
		@FXML
		private TextField model;

		
		@FXML
		private void handleCreateResourceEvent() {
			Librarian lib = new Librarian("","","","","","","","","","");
			String title = this.title.getText();
			String yor = this.yor.getText();
			int ca = Integer.valueOf(this.ca.getText());
			String manufacturer = this.manufacturer.getText();
			String os = this.OS.getText();
			String model = this.model.getText();
			lib.createLaptop("000", title, yor, ca, manufacturer,os,model);
		}
		
		public void initialize() {
			temp = new CurrentSessionData();
			username = temp.getUsername();
		}
	
}
