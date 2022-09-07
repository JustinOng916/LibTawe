import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import javafx.scene.image.Image;

/**
 * Librarian.java
 *
 * @author Zak Chambers Hale & Alex Jones
 *
 * Subclass of Account that has administrative privileges on the system
 * for procedures such as adding resources and users among others, that
 * normal users cannot access. Librarians hold extra information to
 * normal users(staff number and employment date).
 *
 * @version 1.0
 * @since 19/11/2018
 */
public class Librarian extends Account {
	private CurrentSessionData temp;
	private String staffNum;
	private String emplDate;

	/**
	 * @param username;     The username of the Librarian.
	 * @param firstName;    The first name of the Librarian.
	 * @param surname;      The surname of the Librarian.
	 * @param phoneNum;     The phone number of the Librarian.
	 * @param addLn1;       The first address line of the Librarian.
	 * @param addLn2;       The second address line of the Librarian.
	 * @param city;         The residing city of the Librarian.
	 * @param postcode;     The postcode of the Librarian.
	 * @param staffNum;     The unique staff number of the Librarian.
	 * @param emplDate;     The date that the Librarian was employed.
	 */
	public Librarian(String username, String firstName, String surname, String phoneNum, String addLn1, String addLn2,
			String city, String postcode, String staffNum, String emplDate) {

		super(username, firstName, surname, phoneNum, addLn1, addLn2, city, postcode);
		this.staffNum = staffNum;
		this.emplDate = emplDate;
	}

	/**
	 * Creates a new user based on information given.
	 */
	public void createUser() {
		User newUser = new User(username, firstName, surname, phoneNum, addLn1, addLn2, city, postcode);
	}

	/**
	 * Adds a new Book to ResourceDatabase.txt and ResourceDatabase2 using
	 * the given information.
	 * @param numberID  The Unique ID of the Book.
	 * @param title     The title of the Book.
	 * @param relYear   The year the book was released.
	 * @param resCount  The amount of copies being stored of this Book.
	 * @param author    The author of the Book.
	 * @param publisher The publisher of the Book.
	 * @param genre     The genre type of the Book.
	 * @param ISBN      The ISBN number of the Book.
	 */
	public void createBook(String numberID, String title, String relYear, int resCount, String author, String publisher,
			String genre, String ISBN) {
		String avail = "!a!";
		Book book = new Book(numberID,title,relYear,resCount,author,publisher,genre,ISBN);
		char sep = ';';
		for(int i = 1; i<= book.getResCount();i++) {
		try {
				FileWriter fstream = new FileWriter("Data/ResourceDatabase.txt",true);
				BufferedWriter out = new BufferedWriter(fstream);
				int size = countResources("Data/ResourceDatabase.txt");
				book.setID(String.valueOf(size));				
				out.write("book"+ sep + book.numberID + sep + book.title + sep + book.relYear + sep + resCount +sep +"book.png" + sep
					+ book.author + sep +book.genre + sep + book.ISBN+ sep + avail);
				out.newLine();
				out.close();
				}
			
		
			catch(IOException e) {
			System.out.println(e.getMessage());
			}
		}
		try {
			FileWriter fstream = new FileWriter("Data/ResourceDatabase2.txt",true);
			BufferedWriter out = new BufferedWriter(fstream);
			out.write("book"+ sep + book.title + sep + book.relYear + sep + resCount +sep + "book.png" + sep
					+ book.author + sep +book.genre + sep + book.ISBN);
			out.newLine();
			out.close();
		}		
		catch(IOException e) {
			System.out.println(e.getMessage());
		}
		
	}

	/**
	 * Adds a new DVD to ResourceDatabase.txt and ResourceDatabase2 using
	 * the given information.
	 * @param numberID  The Unique ID of the DVD.
	 * @param title     The title of the DVD.
	 * @param relYear   The year the book was released.
	 * @param resCount  The amount of copies being stored of this DVD.
	 * @param director  The director of the DVD.
	 * @param language  The language of DVD.
	 * @param runtime   The runtime of the DVD.
	 */
	public void createDVD(String numberID, String title, String relYear, int resCount, String director, String runtime,
			String language) {
		DVD dvd = new DVD(numberID,title,relYear,resCount,director,runtime,language);
		String avail = "!a!";
		char sep = ';';
		for(int i = 1; i<= dvd.getResCount();i++) {
		try {
			FileWriter fstream = new FileWriter("Data/ResourceDatabase.txt",true);
			BufferedWriter out = new BufferedWriter(fstream);
			int size = countResources("Data/ResourceDatabase.txt");
			dvd.setID(String.valueOf(size));			
			out.write("dvd"+ sep + dvd.numberID + sep + dvd.title + sep + dvd.relYear + sep + resCount + sep + "dvd.png" + sep
					+ dvd.director + sep +dvd.runtime + sep + dvd.language + sep + avail);
			out.newLine();
			out.close();
			}
			
		catch(IOException e) {
			System.out.println(e.getMessage());
		}
		}
		try {
			FileWriter fstream = new FileWriter("Data/ResourceDatabase2.txt",true);
			BufferedWriter out = new BufferedWriter(fstream);
			out.write('\n'+"dvd"+ sep + dvd.title + sep + dvd.relYear + sep + resCount + sep +  "dvd.png" + sep
					+ dvd.director + sep +dvd.runtime + sep + dvd.language);
			//out.newLine();
			out.close();
		}		
		catch(IOException e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Adds a new Laptop to ResourceDatabase.txt and ResourceDatabase2 using
	 * the given information.
	 * @param numberID      The Unique ID of the Book.
	 * @param title         The title of the Book.
	 * @param relYear       The year the book was released.
	 * @param resCount      The amount of copies being stored of this Book.
	 * @param manufacturer  The manufacturer of the Laptop.
	 * @param model         The model of the Laptop.
	 * @param OS            The operating system of the Laptop.
	 */
	public void createLaptop(String numberID, String title, String relYear, int resCount, String manufacturer, String OS,
			String model) {
		Laptop laptop = new Laptop(numberID,title,relYear,resCount,manufacturer,OS,model);
		char sep = ';';
		String avail = "!a!";
		int size;
		for(int i = 1; i<= laptop.getResCount();i++) {
		try {
				FileWriter fstream = new FileWriter("Data/ResourceDatabase.txt",true);
				BufferedWriter out = new BufferedWriter(fstream);
				size = countResources("Data/ResourceDatabase.txt");								
				laptop.setID(String.valueOf(size));				
				out.write("laptop"+ sep + laptop.numberID + sep + laptop.title + sep + laptop.relYear + sep + resCount + sep +  "laptopicon.png" + sep
						+ laptop.manufacturer + sep +laptop.OS + sep + laptop.model + sep + avail);	
				out.newLine();
				out.close();
			}		
		catch(IOException e) {
			System.out.println(e.getMessage());
		}
		
		}
		try {
			FileWriter fstream = new FileWriter("Data/ResourceDatabase2.txt",true);
			BufferedWriter out = new BufferedWriter(fstream);
			//as number Id so user can view "ID"  + laptop.numberID + sep 
			out.write("laptop"+ sep + laptop.title + sep + laptop.relYear + sep + resCount + sep + "laptopicon.png" + sep
					+ laptop.manufacturer + sep +laptop.OS + sep + laptop.model );	
			out.newLine();
			out.close();
		}		
		catch(IOException e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Counts the amount of copies of a specific resource in a specified textfile.
	 * @param filename The name of the file to be scanned for copies.
	 * @return The amount of copies in the specified file.
	 */
	public int countResources(String filename) {
		File file = new File(filename);
		Scanner in = null;
		try {
			in = new Scanner(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		int counter = 1;
		while (in.hasNextLine()) {
			if(in.nextLine()!=null) {
				counter +=1;
			}
			
		}
		return counter;
	}

	/**
	 * Gets staff number.
	 *
	 * @return
	 */
	public String getStaffNum() {
		return staffNum;
	}

	/**
	 * Sets staff number.
	 *
	 * @param staffNum
	 */
	public void setStaffNum(String staffNum) {
		this.staffNum = staffNum;
	}

	/**
	 * Gets employment date.
	 *
	 * @return
	 */
	public String getEmplDate() {
		return emplDate;
	}

	/**
	 * Sets employment date.
	 *
	 * @param emplDate
	 */
	public void setEmplDate(String emplDate) {
		this.emplDate = emplDate;
	}

	/**
	 * Prints the Librarian, with all relevant information formatted nicely for easy reading.
	 *
	 * @return The reformatted string of Librarian to be output nicely, combined with the
	 * Account toString() to complete the users information.
	 */
	public String toString() {
		String res = "This is a Librarian" + "\n";
		res += super.toString();
		return res;
	}
}