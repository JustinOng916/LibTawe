import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

/**
 * CurrentSession.java
 *
 * @author Alex Jones
 *
 * No copyright infringements intended, holds no copyright.
 *
 * This class is for many things: - Save the data of the current login
 * so that it can persist between files. - Write to any files - Check if
 * files contain certain names - Anything to do with reading or writing
 * to data files is done here.
 *
 * @version 1.0
 * @since 28/11/2018
 */

public class CurrentSessionData {
	private String loginPath = "Data/currentLogin.txt"; // Login path
	private String datePath = "Data/currentDate.txt"; // Date path
	private String timePath = "Data/currentTime.txt"; // Time path
	private String resourceQueuePath = "./Data/ResourceQueue.txt"; // ResourceQueue path
	private String userBRDDPath = "./Data/userBRDD.txt"; // userBRDD path
	private String userFTPPath = "./Data/userFTP.txt"; // userFTP path
	private String userReqResPath = "./Data/userReqRes.txt"; // userReqRes path
	private String userResResPath = "./Data/userResRes.txt"; // userResRes path
	private String userTHPath = "./Data/userTH.txt"; // userTH path
	private String libRRPath = "./Data/libRR.txt"; // libRR path
	private String libFPRPath = "./Data/libFPR.txt"; // libFPR path
	private String userListPath = "./Data/userList.txt"; // userList path
	private String resourceDatabasePath = "./Data/ResourceDatabase.txt";
	private String resourceDatabase2Path = "./Data/ResourceDatabase2.txt";
	private String copyIDPath = "./Data/currentCopyID.txt";
	private String requestQueuePath = "./Data/RequestQueue.txt";
	private String reservedQueuePath = "./Data/ReservedQueue.txt";
	private String overdueCopiesPath = "./Data/overdueCopies.txt";
	private String date; // Current date
	private String time = ""; // Current time
	private String username; // Current username
	private String userType; // Current userType

	private File overdueCopies = new File(overdueCopiesPath);
	private File userList = new File(userListPath);
	private File resourceQueue = new File(resourceQueuePath);
	private File userBorrowReqDueDate = new File(userBRDDPath);
	private File userFineToPay = new File(userFTPPath);
	private File userReqRes = new File(userReqResPath);
	private File userResRes = new File(userResResPath);
	private File userTransactHis = new File(userTHPath);
	private File libReturnReq = new File(libRRPath);
	private File libFinePayReq = new File(libFPRPath);
	private File resourceDatabase = new File(resourceDatabasePath);
	private File resourceDatabase2 = new File(resourceDatabase2Path);
	private File requestQueue = new File(requestQueuePath);
	private File reserveQueue = new File(reservedQueuePath);

	private String userBorrowRequestsDueDateLine;
	private String userFinesToPayLine;
	private String userTransactionHistoryLine;
	private String userResourceRequestsLine;
	private String userRequestedResourcesLine;
	private String librarianBorrowRequestsLine;
	private String librarianFinePaymentRequestsLine;
	private String resourceQueueLine;
	private String userListLine;

	private String copyID;

	/**
	 * Initialises the current session and runs getUsername(). This is for getting
	 * the username and type etc.
	 */
	public CurrentSessionData() {
		getUsername();
	}

	/**
	 * Upon login, this method writes to file the username and type.
	 * 
	 * @param user Username
	 * @param type Usertype
	 */
	public CurrentSessionData(String user, String type) {

		this.username = user;
		this.userType = type;

		try {
			PrintWriter fileWriter = new PrintWriter(loginPath);
			fileWriter.println(this.username);
			fileWriter.println(this.userType);
			fileWriter.close();
		} catch (IOException e) {
			System.out.println("Cannot open " + loginPath);
			System.exit(0);
		}
	}

	/**
	 * Sets a fine based on the resource and amount of days that the user
	 * has kept the resource for past it's due date, with a maximum fine
	 * set for each resource type (Book and DVD = 25, Laptop = 100).
	 * @param daysLate 	The amount of days past the due date.
	 * @param type		The resource type.
	 * @param user		The username of the fined user.
	 */
	public void setFine (int daysLate, String type, String user) {
		double totalFine = 0;
		int fine = 0;
        switch(type) {
        	case "book":
        		if (daysLate <= 0) {
        			fine = 0;        			
        		} else if (daysLate >= 13) {
        			fine = 25;
        		} else {
        			fine = (2*daysLate) - 2;
        		}
        		break;
        	case "dvd":
        		if (daysLate <= 0) {
        			fine = 0;        			
        		} else if (daysLate >= 13) {
        			fine = 25;
        		} else {
        			fine = (2*daysLate) - 2;
        		}
        		break;
        	case "laptop":
        		if (daysLate < 0) {
        			fine = 0;
        		} else if (daysLate >= 10) {
        			fine = 100;
        		} else {
        			fine = (10 * daysLate);
        		}
        		break;
        		
        	}
        totalFine = Double.parseDouble(getFileLine("userFTP", user));
        totalFine += fine;
        
        overwriteLine("userFTP", user, user + " : " + totalFine);
    }

	/**
	 *
	 * @param ID	ID of the resource.
	 */
	public void setCopyID(String ID) {
		this.copyID = ID;

		try {
			PrintWriter fileWriter = new PrintWriter(copyIDPath);
			fileWriter.println(copyID);
			fileWriter.close();
		} catch (IOException e) {
			System.out.println("Cannot open " + copyIDPath);
			System.exit(0);
		}
	}

	/**
	 * Gets the ID of a copy
	 * @return ID
	 */
	public String getCopyID() {
		File session = new File(copyIDPath);
		Scanner in = new Scanner(System.in);
		try {
			in = new Scanner(session);
		} catch (FileNotFoundException e) {
			System.out.println("File doesn't exist...");
		}
		this.copyID = in.nextLine();
		return this.copyID;
	}

	/**
	 * This method sets the date following style "DD/MM/YYYY" (preferred, but errors
	 * won't be caught if not handled well)
	 * 
	 * @param date The date to be written to the file.
	 */
	public void setDate(String date) {
		this.date = date;

		try {
			PrintWriter fileWriter = new PrintWriter(datePath);
			fileWriter.println(date);
			fileWriter.close();
		} catch (IOException e) {
			System.out.println("Cannot open " + datePath);
			System.exit(0);
		}
	}

	/**
	 * Returns the date from file.
	 * 
	 * @return The date as a String.
	 */
	public String getDate() {
		File session = new File(datePath);
		Scanner in = new Scanner(System.in);
		try {
			in = new Scanner(session);
		} catch (FileNotFoundException e) {
			System.out.println("File doesn't exist...");
		}
		this.date = in.nextLine();
		return this.date;
	}

	/**
	 * This method sets the time following style "HH:MM" (preferred, but errors
	 * won't be caught if not handled well)
	 * 
	 * @param time The time.
	 */
	public void setTime(String time) {
		File timeFile = new File(timePath);
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(timeFile, false));
			bw.append(time);
			bw.close();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Returns the time from file.
	 * 
	 * @return The time as a string.
	 */
	public String getTime() {
		File session = new File(timePath);
		Scanner in = new Scanner(System.in);
		try {
			in = new Scanner(session);
		} catch (FileNotFoundException e) {
			System.out.println("File doesn't exist...");
		}
		this.time = in.nextLine();
		return this.time;
	}

	/**
	 * Returns the username and type from file.
	 * 
	 * @return The username as a string.
	 */
	public String getUsername() {
		File session = new File(loginPath);
		Scanner in = new Scanner(System.in);
		try {
			in = new Scanner(session);
		} catch (FileNotFoundException e) {
			System.out.println("File doesn't exist...");
		}
		this.username = in.nextLine();
		this.userType = in.nextLine();

		// System.out.println("Username = " + this.username);
		// System.out.println("Usertype = " + this.userType);

		return this.username;
	}

	/**
	 * Retrieves the userType that is saved as a field.
	 * 
	 * @return The userType as a string.
	 */
	public String getUserType() {
		return userType;
	}

	/**
	 * Returns a file, the whole thing.
	 * 
	 * @param filename The name of the file (only needs to be the filename, don't
	 *                 include PATH, or ".txt")
	 * @return The file as a Scanner object, for parsing.
	 */
	public Scanner getFile(String filename) {
		File session = new File("./Data/" + filename + ".txt");
		Scanner in = new Scanner(System.in);

		// This sets the Scanner as whichever file has been parsed as parameter (if it
		// can be found)
		switch (filename) {
		case ("userBRDD"):
		case ("userFTP"):
		case ("userTH"):
		case ("userResRes"):
		case ("userReqRes"):
		case ("libRR"):
		case ("libFPR"):
		case ("ResourceQueue"):
		case ("userList"):
		case ("ResourceDatabase"):
		case ("ReservedQueue"):
		case ("overdueCopies"):
			try {
				in = new Scanner(session);
			} catch (FileNotFoundException e) {
				System.out.println("File doesn't exist...");
			}
			System.out.println(session + " was found!");
			break;
		}

		return in;
	}

	/**
	 * Retrieves a line from a file if the parameter 'name' is contained. It then
	 * removes username as when called this will be on specific user dashboards,
	 * therefore username isn't required.
	 * 
	 * @param filename The name of the file (exclude path and ".txt")
	 * @param name     The name being searched for in the file.
	 * @return The whole line (if 'name' is present) as a string.
	 */
	public String getFileLine(String filename, String name) {
		File session = new File("./Data/" + filename + ".txt");
		Scanner in = new Scanner(System.in);
		try {
			in = new Scanner(session);
		} catch (FileNotFoundException e) {
			System.out.println("File doesn't exist...");
		}

		String curLine = in.nextLine();

		/*
		 * This loop checks each line of the file and sees if name is present, if so it
		 * saves it as a string
		 */
		while (!curLine.contains(name)) {
			curLine = in.nextLine();
		}
		curLine = curLine.replace(name + " : ", "");
		System.out.println(name + " : " + curLine);
		return curLine;

	}

	/**
	 * Checks if a name is in a file.
	 * 
	 * @param filename      The filename (excluding path and ".txt")
	 * @param nameContained The name being searched for
	 * @return True if the name is present, false otherwise.
	 */
	public Boolean isInFile(String filename, String nameContained) {
		Boolean isContained = false;
		String contained = "";
		try {
			contained = getFileLine(filename, nameContained);
			isContained = true;
		} catch (NoSuchElementException e) {
			isContained = false;
			e.printStackTrace();
		}
		System.out.println(contained);
		return isContained;
	}

	/**
	 * Adds a line to a file, whether empty or not.
	 * 
	 * @param filename  The name of the file (excluding path and ".txt")
	 * @param lineToAdd The string to be added.
	 */
	public void addLineToFile(String filename, String lineToAdd) {
		Scanner in = null;
		File f = null;

		/*
		 * Based on the filename, sets a file as one of the fields at the start of the
		 * class.
		 */
		switch (filename) {
		case ("userList"):
			f = userList;
			break;
		case ("userBRDD"):
			f = userBorrowReqDueDate;
			break;
		case ("userFTP"):
			f = userFineToPay;
			break;
		case ("userTH"):
			f = userTransactHis;
			break;
		case ("userResRes"):
			f = userResRes;
			break;
		case ("userReqRes"):
			f = userReqRes;
			break;
		case ("libRR"):
			f = libReturnReq;
			break;
		case ("libFPR"):
			f = libFinePayReq;
			break;
		case ("ResourceQueue"):
			f = resourceQueue;
			break;
		case ("RequestQueue"):
			f = requestQueue;
			break;
		case ("ReservedQueue"):
			f = reserveQueue;
			break;
		case ("overdueCopies"):
			f = overdueCopies;
			break;
		default:
			System.out.println("This file : " + filename + " couldn't be found");
			break;
		}
		try {
			in = scanFile(f);
		} catch (Exception e) {
			e.printStackTrace();
		}

		PrintWriter pw = null;
		try {
			pw = new PrintWriter(new FileWriter(f, true));

			if (in.hasNextLine()) {
				in.nextLine();
				pw.println(lineToAdd);
				pw.close();
			} else {
				pw.print(lineToAdd + System.getProperty("line.separator"));
			}
			pw.close();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Writes the user's fine to pay request to the file that the librarian views.
	 * 
	 * @param username  The username.
	 * @param fineToPay The amount they're attempting to pay.
	 */
	public void writeUserFinePayRequest(String username, double fineToPay) {
		Scanner in = scanFile(libFinePayReq);
		String addFineReq = username + " : " + fineToPay;

		PrintWriter pw = null;
		try {
			pw = new PrintWriter(new FileWriter(libFinePayReq, true));

			if (in.hasNextLine()) {
				pw.write(System.getProperty("line.separator") + addFineReq);
				pw.close();
			} else {
				pw.append(addFineReq);
			}
			pw.close();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * This is a method solely used inside CurrentSessionData and although it does
	 * the same as getFile(String filename), it provides an effective shortcut by
	 * using the file.
	 */
	private Scanner scanFile(File f) {
		Scanner in = new Scanner(System.in);

		try {
			in = new Scanner(f);
		} catch (FileNotFoundException e) {
			System.out.println("File doesn't exist...");
		}

		return in;

	}

	/**
	 * This method is to accept all user fines as a librarian, then updates other
	 * data files using a private method.
	 * 
	 * @param date The current date.
	 * @param time The time, to be set.
	 */
	public void libAcceptUserFinePayment(String date, String time) {
		
		Scanner in = scanFile(libFinePayReq);
		PrintWriter pw = null;
		try {
			pw = new PrintWriter(new FileWriter(userTransactHis, true));

			while (in.hasNextLine()) {
				in.useDelimiter(" : ");
				String curLine = in.nextLine();
				String tempUser = curLine.substring(0, curLine.indexOf(":") - 1);
				String tempFine = curLine.substring(curLine.lastIndexOf(":") + 2);
				pw.println(tempUser + " : " + tempFine + ";" + date + " | " + time);
				updateUserFinesToPay();
			}
			pw.close();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}

		PrintWriter writer = null;
		try {
			writer = new PrintWriter(libFinePayReq);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		writer.print("");
		writer.close();
		pw.close();
	}

	/**
	 * This method updates all user fines to pay by doing simple arithmetic with
	 * however much they've tried to pay. If over the fine amount, then it sets fine
	 * to pay as zero.
	 */
	private void updateUserFinesToPay() {
		Scanner in = getFile("libFPR");
		ArrayList<String> userTransactions = new ArrayList<String>();
		while (in.hasNextLine()) {
			userTransactions.add(in.nextLine());
		}

		do {
			int i = 0;
			do {
				System.out.println("USER>>>>>>>>>" + userTransactions.get(i));
				Scanner line = getFile("userFTP");
				line.useDelimiter("\\n");
				String tempStr = userTransactions.get(i);
				String tempUser = tempStr.substring(0, tempStr.indexOf(" : "));

				String thisUser = "";
				while (line.hasNextLine()) {
					String thisLine = line.nextLine();
					if (thisLine.contains(tempUser)) {
						thisUser = thisLine.substring(0, tempStr.indexOf(" : "));
					}
				}

				// System.out.println(thisUser);
				// System.out.println(tempUser);
				// If the line in userFTP = the username from userTransactions(j)
				if (thisUser.equals(tempUser)) {
					String curUser = tempStr.substring(0, tempStr.indexOf(" : "));
					String fineToPay = tempStr.substring(tempStr.indexOf(":") + 2);

					// System.out.println("fine amount = " + fineToPay + " | " + curUser);
					// System.out.println(tempStr);
					replaceSelected(curUser, Double.parseDouble(fineToPay));
				}
				tempStr = userTransactions.get(i);
				line.close();
				i++;
			} while (i < userTransactions.size());
		} while (in.hasNextLine());
		in.close();

	}

	/**
	 * This method is to override the various user Fines To Pay.
	 * 
	 * @param user    The user
	 * @param newFine The updated fine to be written.
	 */
	private void replaceSelected(String user, Double newFine) {
		Scanner in = getFile("userFTP");
		String oldLine = getFileLine("userFTP", user);
		String userFTPLine = getFileLine("userFTP", user);

		String oldFine = userFTPLine;
		Double tempOldFine = Double.parseDouble(oldFine);

		System.out.println("Old line : " + oldLine);
		System.out.println("UserFTP line : " + userFTPLine + "\n");
		System.out.println(oldFine);
		System.out.println(tempOldFine);

		Double updatedFine = 0.0;
		if (newFine < tempOldFine) {
			updatedFine = tempOldFine - newFine;
		} else {
			updatedFine = 0.0;
		}
		String newFTPLine = userFTPLine.replaceAll(oldFine, Double.toString(updatedFine));

		System.out.println("New fine is " + newFTPLine);
		try {
			// Input all of userFTP to StringBuffer
			BufferedReader FTP = new BufferedReader(new FileReader(userFineToPay));
			String current;
			StringBuffer inputBuffer = new StringBuffer();

			while ((current = FTP.readLine()) != null) {
				inputBuffer.append(current);
				inputBuffer.append(System.getProperty("line.separator"));
			}
			String inputToString = inputBuffer.toString();
			FTP.close();

			System.out.println(inputToString); // double check input

			// To determine which line to be replaced by checking if username matches
			if (inputToString.contains(oldLine)) {
				inputToString = inputToString.replace(oldLine, newFTPLine);
			}

			System.out.println("------------------\n" + inputToString);

			// write the whole string with replaced line over same file
			FileOutputStream fileOutput = new FileOutputStream(userFTPPath);
			fileOutput.write(inputToString.getBytes());
			fileOutput.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * This method is to override a specific line in any given file.
	 * 
	 * @param filename           Name of the file
	 * @param uniqueStringInLine A unique identifier in the file that can identify
	 *                           the line.
	 * @param newLine            The new line to be written.
	 */
	public void overwriteLine(String filename, String uniqueStringInLine, String newLine) {
		Scanner in = getFile(filename);
		String oldLine = getWholeFileLine(filename, uniqueStringInLine);
		String updateLine = newLine;
		String path = "";

		System.out.println("Old line | " + oldLine);
		System.out.println("Update old line with | " + updateLine + "\n");

		System.out.println("New line is | " + updateLine);
		try {
			// Input all of the wanted file to StringBuffer
			BufferedReader fileReader = null;
			/*
			 * Based on the filename, sets a file as one of the fields at the start of the
			 * class.
			 */
			switch (filename) {
			case ("ResourceDatabase"):
				fileReader = new BufferedReader(new FileReader(resourceDatabase));
				path = resourceDatabasePath;
				break;
			case ("userList"):
				fileReader = new BufferedReader(new FileReader(userList));
				path = userListPath;
				break;
			case ("userBRDD"):
				fileReader = new BufferedReader(new FileReader(userBorrowReqDueDate));
				path = userBRDDPath;
				break;
			case ("userFTP"):
				fileReader = new BufferedReader(new FileReader(userFineToPay));
				path = userFTPPath;
				break;
			case ("userTH"):
				fileReader = new BufferedReader(new FileReader(userTransactHis));
				path = userTHPath;
				break;
			case ("userResRes"):
				fileReader = new BufferedReader(new FileReader(userResRes));
				path = userResResPath;
				break;
			case ("userReqRes"):
				fileReader = new BufferedReader(new FileReader(userReqRes));
				path = userReqResPath;
				break;
			case ("libRR"):
				fileReader = new BufferedReader(new FileReader(libReturnReq));
				path = libRRPath;
				break;
			case ("libFPR"):
				fileReader = new BufferedReader(new FileReader(libFinePayReq));
				path = libFPRPath;
				break;
			case ("ResourceQueue"):
				fileReader = new BufferedReader(new FileReader(resourceQueue));
				path = resourceQueuePath;
				break;
			case ("ReservedQueue"):
				fileReader = new BufferedReader(new FileReader(reserveQueue));
				path = reservedQueuePath;
				break;
			case ("overdueCopies"):
				fileReader = new BufferedReader(new FileReader(overdueCopies));
				path = overdueCopiesPath;
				break;
			default:
				System.out.println("This file : " + filename + " couldn't be found");
				break;
			}

			String current;
			StringBuffer inputBuffer = new StringBuffer();

			while ((current = fileReader.readLine()) != null) {
				inputBuffer.append(current);
				inputBuffer.append(System.getProperty("line.separator"));
			}
			String inputToString = inputBuffer.toString();
			fileReader.close();

			System.out.println(inputToString); // double check input

			// To determine which line to be replaced by checking if username matches
			if (inputToString.contains(oldLine)) {
				inputToString = inputToString.replace(oldLine, updateLine);
			}

			System.out.println("------------------\n" + inputToString);

			// write the whole string with replaced line over same file
			FileOutputStream fileOutput = new FileOutputStream(path);
			fileOutput.write(inputToString.getBytes());
			fileOutput.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * Retrieves the whole line from a file if the parameter 'name' is contained.
	 * 
	 * @param filename The name of the file (exclude path and ".txt")
	 * @param name     The name being searched for in the file.
	 * @return The whole line (if 'name' is present) as a string.
	 */
	public String getWholeFileLine(String filename, String name) {
		File session = new File("./Data/" + filename + ".txt");
		Scanner in = new Scanner(System.in);
		try {
			in = new Scanner(session);
		} catch (FileNotFoundException e) {
			System.out.println("File doesn't exist...");
		}

		String curLine = in.nextLine();

		/*
		 * This loop checks each line of the file and sees if name is present, if so it
		 * saves it as a string
		 */
		while (!curLine.contains(name)) {
			curLine = in.nextLine();
		}

		return curLine;

	}

	/**
	 * This method is to append data to the end of the line in a given file. This
	 * will be used for reserved resources, resource queue, requested resources etc.
	 * 
	 * @param identifierInLine Something that uniquely identifies the line to be
	 *                         edited (i.e. something that doesn't occur anywhere
	 *                         else e.g. a username/name of the resource queue)
	 * @param dataToAdd        Data to be added to the file
	 * @param delimiterInFile  The delimiter used in whichever text file
	 * @param filename         The name of the ".txt" file
	 */
	public void addToLine(String identifierInLine, String dataToAdd, String delimiterInFile, String filename) {
		String fileLine = getWholeFileLine(filename, identifierInLine);
		String path = "";
		String newFileLine = fileLine + dataToAdd + delimiterInFile;

		System.out.println("Old line | " + fileLine);
		System.out.println("Update old line with | " + dataToAdd + "\n");

		System.out.println("New line is | " + newFileLine);
		try {
			// Input all of the wanted file to StringBuffer
			BufferedReader fileReader = null;
			/*
			 * Based on the filename, sets a file as one of the fields at the start of the
			 * class.
			 */
			switch (filename) {
			case ("userList"):
				fileReader = new BufferedReader(new FileReader(userList));
				path = userListPath;
				break;
			case ("userBRDD"):
				fileReader = new BufferedReader(new FileReader(userBorrowReqDueDate));
				path = userBRDDPath;
				break;
			case ("userFTP"):
				fileReader = new BufferedReader(new FileReader(userFineToPay));
				path = userFTPPath;
				break;
			case ("userTH"):
				fileReader = new BufferedReader(new FileReader(userTransactHis));
				path = userTHPath;
				break;
			case ("userResRes"):
				fileReader = new BufferedReader(new FileReader(userResRes));
				path = userResResPath;
				break;
			case ("userReqRes"):
				fileReader = new BufferedReader(new FileReader(userReqRes));
				path = userReqResPath;
				break;
			case ("libRR"):
				fileReader = new BufferedReader(new FileReader(libReturnReq));
				path = libRRPath;
				break;
			case ("libFPR"):
				fileReader = new BufferedReader(new FileReader(libFinePayReq));
				path = libFPRPath;
				break;
			case ("ResourceQueue"):
				fileReader = new BufferedReader(new FileReader(resourceQueue));
				path = resourceQueuePath;
				break;
			case ("ReservedQueue"):
				fileReader = new BufferedReader(new FileReader(reserveQueue));
				path = reservedQueuePath;
				break;
			default:
				System.out.println("This file : " + filename + " couldn't be found");
				break;
			}

			String current;
			StringBuffer inputBuffer = new StringBuffer();

			while ((current = fileReader.readLine()) != null) {
				inputBuffer.append(current);
				inputBuffer.append(System.getProperty("line.separator"));
			}
			String inputToString = inputBuffer.toString();
			fileReader.close();

			System.out.println(inputToString); // double check input

			// To determine which line to be replaced by checking if username matches
			if (inputToString.contains(fileLine)) {
				inputToString = inputToString.replace(fileLine, newFileLine);
			}

			System.out.println("------------------\n" + inputToString);

			// write the whole string with replaced line over same file
			FileOutputStream fileOutput = new FileOutputStream(path);
			fileOutput.write(inputToString.getBytes());
			fileOutput.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Counts the number of resources based on perfect input and assuming the
	 * resource can be found.
	 * 
	 * @param uniqueIdentifier A unique identifier that persists between multiple
	 *                         copies (i.e. title)
	 * @return The number in the database
	 */
	public int countNumResources(String uniqueIdentifier) {
		int numberOfResources = 0;
		Scanner resScan = getFile("ResourceDatabase");
		while (resScan.hasNextLine()) {
			if (resScan.nextLine().contains(uniqueIdentifier)) {
				numberOfResources++;
			}
		}
		System.out.println(uniqueIdentifier + " has " + numberOfResources + " copies");
		return numberOfResources;

	}

	/**
	 * Edits a given line in a text file.
	 * @param filename			The name of the file to edit.
	 * @param identifierInLine	The unique identifier of a line.
	 * @param partToChange		The part to be changed in the line.
	 * @param updatedString		The part that needs to be replaced in the line.
	 */
	public void editLine(String filename, String identifierInLine, String partToChange, String updatedString) {
		String fileLine = getWholeFileLine(filename, identifierInLine);
		String path = "";
		String newFileLine = fileLine.replace(partToChange, updatedString);

		System.out.println("Old line | " + fileLine);
		System.out.println("Update old line with | " + updatedString + "\n");

		System.out.println("New line is | " + newFileLine);
		try {
			// Input all of the wanted file to StringBuffer
			BufferedReader fileReader = null;
			/*
			 * Based on the filename, sets a file as one of the fields at the start of the
			 * class.
			 */
			switch (filename) {
			case ("userList"):
				fileReader = new BufferedReader(new FileReader(userList));
				path = userListPath;
				break;
			case ("userBRDD"):
				fileReader = new BufferedReader(new FileReader(userBorrowReqDueDate));
				path = userBRDDPath;
				break;
			case ("userFTP"):
				fileReader = new BufferedReader(new FileReader(userFineToPay));
				path = userFTPPath;
				break;
			case ("userTH"):
				fileReader = new BufferedReader(new FileReader(userTransactHis));
				path = userTHPath;
				break;
			case ("userResRes"):
				fileReader = new BufferedReader(new FileReader(userResRes));
				path = userResResPath;
				break;
			case ("userReqRes"):
				fileReader = new BufferedReader(new FileReader(userReqRes));
				path = userReqResPath;
				break;
			case ("libRR"):
				fileReader = new BufferedReader(new FileReader(libReturnReq));
				path = libRRPath;
				break;
			case ("libFPR"):
				fileReader = new BufferedReader(new FileReader(libFinePayReq));
				path = libFPRPath;
				break;
			case ("ResourceQueue"):
				fileReader = new BufferedReader(new FileReader(resourceQueue));
				path = resourceQueuePath;
				break;
			case ("ReservedQueue"):
				fileReader = new BufferedReader(new FileReader(reserveQueue));
				path = reservedQueuePath;
				break;
			case ("ResourceDatabase"):
				fileReader = new BufferedReader(new FileReader(resourceDatabase));
				path = resourceDatabasePath;
				break;
			case ("ResourceDatabase2"):
				fileReader = new BufferedReader(new FileReader(resourceDatabase2));
				path = resourceDatabase2Path;
				break;
			case ("overdueCopies"):
				fileReader = new BufferedReader(new FileReader(overdueCopies));
				path = overdueCopiesPath;
				break;
			default:
				System.out.println("This file : " + filename + " couldn't be found");
				break;
			}

			String current;
			StringBuffer inputBuffer = new StringBuffer();

			while ((current = fileReader.readLine()) != null) {
				inputBuffer.append(current);
				inputBuffer.append(System.getProperty("line.separator"));
			}
			String inputToString = inputBuffer.toString();
			fileReader.close();

			if (inputToString.contains(fileLine)) {
				System.out.println("Line found!");
				inputToString = inputToString.replace(fileLine, newFileLine);
			}

			FileOutputStream fileOutput = new FileOutputStream(path);
			fileOutput.write(inputToString.getBytes());
			fileOutput.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * This returns a block of the same resources (i.e. to show all of the
	 * resources, this is mainly used for viewing availability)
	 * 
	 * @param uniqueIdentifier The identifier that is shared between multiple
	 *                         resources but separates them from others.
	 * @return A block of text with all resources.
	 */
	public String showNumResources(String uniqueIdentifier) { // newMethod
		String numberOfResources = "";
		Scanner resScan = getFile("ResourceDatabase");
		while (resScan.hasNextLine()) {
			String newLine = resScan.nextLine();
			if (newLine.contains(uniqueIdentifier)) {
				// System.out.println(newLine);
				numberOfResources += '\n' + newLine;
			}
		}
		return numberOfResources;

	}

	/**
	 * This returns a block of the same BRDD (i.e. to show all of the resources,
	 * this is mainly used for viewing availability)
	 * 
	 * @param uniqueIdentifier The identifier that is shared between multiple
	 *                         resources but separates them from others.
	 * @return A block of text with all resources.
	 */
	public String showBRDD(String uniqueIdentifier) { // newMethod
		String BRDDBlock = "";
		Scanner resScan = getFile("userBRDD");
		while (resScan.hasNextLine()) {
			String newLine = resScan.nextLine();
			if (newLine.contains(uniqueIdentifier)) {
				// System.out.println(newLine);
				BRDDBlock += '\n' + newLine;
			}
		}
		return BRDDBlock;

	}

	/**
	 * Checks if a resource is reserved through the userResRes.txt file.
	 * @param title
	 * @return Boolean of whether the resource is reserved
	 */
	public boolean checkIfReserved(String title) {
		boolean isReserved = false;
		Scanner resScan = getFile("userResRes");
		while (resScan.hasNextLine()) {
			if (resScan.nextLine().contains(title)) {
				isReserved = true;
			}
		}
		System.out.println(title + " has a full queue, next users added to reserved list.");
		return isReserved;
	}

	/**
	 * Checks if the queue for a given resource is full.
	 * @param title			Title of the resource.
	 * @param totalCopies	The amount of copies available for the resource.
	 * @return Boolean of whether the resource queue is full.
	 */
	public boolean isResourceQueueFull(String title, int totalCopies) {
		String resourceQueueLine = getFileLine("ResourceQueue", title);
		System.out.println(resourceQueueLine);
		int counter = 0;
		boolean isFull = false;

		Scanner in = new Scanner(resourceQueueLine);
		in.useDelimiter(";");
		while (in.hasNext()) {
			counter++;
			in.next();
		}
		if (counter < totalCopies) {
			isFull = false;
		} else if (counter == totalCopies) {
			isFull = true;
		}
		return isFull;
	}

	/**
	 * Allows librarians to accept borrow requests by the editing userReqRes.txt file.
	 */
	public void libAcceptBorrowRequests() {
		PrintWriter pw = null;
		try {
			pw = new PrintWriter(new FileWriter(userReqRes, false));
		} catch (Exception e) {
			e.printStackTrace();
		}
		pw.println("");
	}

	/**
	 * Gets the ID of a given resource copy from userBRDD.txt file.
	 * @param username	The username of the user borrowing a copy.
	 * @return
	 */
	public String getCopyID(String username) {
		String copyID = "";
		String resScan = getFileLine("userBRDD", username);
		String[] lineSplit = resScan.split(";");
		copyID = lineSplit[1];
		return copyID;
	}

	/**
	 * Counts the amount of days a resource has been overdue for.
	 * @param username 	The username of the user borrowing the copy for over the due date.
	 * @param date		The current date.
	 * @return The amount of days overdue.
	 */
	public int countDaysOverdue(String username, String date) {
		int daysOverdue;

		String today = getDate();
		String[] splitToday = today.split("/");
		int cYear = Integer.parseInt(splitToday[2].trim());
		int cMonth = Integer.parseInt(splitToday[1]);
		int cDays = Integer.parseInt(splitToday[0]);

		Calendar currentDate = Calendar.getInstance();
		currentDate.set(cYear, cMonth, cDays, 12, 0, 0);

		String dateToReturn = date;
		String[] splitReturn = dateToReturn.split("/");
		int dYear = Integer.parseInt(splitReturn[2].trim());
		int dMonth = Integer.parseInt(splitReturn[1]);
		int dDays = Integer.parseInt(splitReturn[0]);

		Calendar dueDate = Calendar.getInstance();
		dueDate.set(dYear, dMonth, dDays, 12, 0, 0);

		Date current = currentDate.getTime();
		Date due = dueDate.getTime();

		long diffInDays = getDateDiff(due, current, TimeUnit.DAYS);
		daysOverdue = (int) diffInDays;
		System.out.println(username + " has had the resource for " + daysOverdue + " days past the due date");
		return daysOverdue;
	}

	/**
	 * Gets the difference between the due date and the and the current
	 * date
	 * @param current	The current date.
	 * @param dueDate	The due date of the resource.
	 * @param timeUnit	The unit of time for the time to be calculated.
	 * @return The difference between the the two dates.
	 */
	private long getDateDiff(Date current, Date dueDate, TimeUnit timeUnit) {
		long diffInMillis = dueDate.getTime() - current.getTime();
		return timeUnit.convert(diffInMillis, TimeUnit.MILLISECONDS);
	}

	/**
	 * Removes a line from the userBRDD text file.
	 * @param identifierInFile	Unique ID that distinguishes the line from others(Resource ID).
	 */
	public void removeLineFromFileBRDD(String identifierInFile) {
		try {
			File f = new File(userBRDDPath);
			BufferedReader fileReader = new BufferedReader(new FileReader(f));
			File tempFile = new File("./Data/tempUserBRDD.txt");
			BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));
			String currentLine;
			while ((currentLine = fileReader.readLine()) != null) {
				// trim newLine when comparing to removeLineContaining
				if (!currentLine.contains(identifierInFile)) {
					writer.write(currentLine + System.getProperty("line.separator"));
				}
			}
			fileReader.close();
			writer.close();
			File inputFile = new File("./Data/userBRDD.txt");
			f.delete();
			System.out.println(f.toString());
			boolean success = tempFile.renameTo(inputFile);
			System.out.println("Removing line was a success in BRDD : " + success);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Checks if there are users in the reserved queue.
	 * @param title	Title at the head of the queue.
	 * @return True if usernames follow the title, else false.
	 */
	public boolean hasReservedQueue(String title) {
		boolean hasItems = false;
		try {
			String line = getWholeFileLine("ReservedQueue", title);
			line.replace(title + " : ", "");
			if (line.length() == 0) {
				hasItems = false;
			} else {
				hasItems = true;
			}
		} catch (Exception noItemsException) {
			System.out.println("The reserve queue doesn't have " + title);
		}
		return hasItems;
	}

	/**
	 * Checks if there are users in the resource queue.
	 * @param title	Title at the head of the queue.
	 * @return True if usernames follow the title, else false.
	 */
	public boolean hasResourceQueue(String title) {
		boolean hasItems = false;
		try {
			String line = getWholeFileLine("ResourceQueue", title);
			line.replace(title + " : ", "");
			if (line.length() == 0) {
				hasItems = false;
			} else {
				hasItems = true;
			}
		} catch (Exception noItemsException) {
			System.out.println("The resource queue doesn't have " + title);
		}
		return hasItems;
	}
}
