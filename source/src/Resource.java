/**
 * Resource.java
 *
 * @author Alex Jones and Zak Chambers Hale
 *
 * No copyright infringements intended, holds no copyright.
 *
 * This class is the superclass of: Book, DVD, and Laptop. It holds all
 * of the base data for these to avoid duplication. This class is can't
 * be instantiated but helps organise the base of data and future proof
 * the program.
 *
 * @version 1.0
 * @since 19/11/2018
 */

abstract class Resource {
	
	protected String UID;
	protected String numberID; // ID of the Resource.
	protected String title; // Title of the Resource.
	protected String relYear; // Year of release of the Resource.
	protected int resCount; // Number of copies of the Resource.
	protected int dueDate;
	protected String type;
	protected int loanDuration = 1;
	// protected Avatar image;

	/**
	 * This is a constructor for Resource type objects, and will get used by
	 * subclasses to assign basic values common between all Resources.
	 *
	 * @param numberID ID of the Resource
	 * @param title    Title of the Resource
	 * @param relYear  Release year of the Resource
	 * @param resCount Number of copies of the Resource
	 */
	public Resource(String type, String numberID, String title, String relYear, int resCount) {
		this.numberID = numberID;
		this.title = title;
		this.relYear = relYear;
		this.resCount = resCount;
		dueDate = 0;	
	}

	/**
	 * Gets ID.
	 *
	 * @return The ID.
	 */
	public String getID() {
		return numberID;
	}

	/**
	 * Set ID.
	 *
	 * @param numID The new Resource ID.
	 */
	public void setID(String numID) {
		this.numberID = numID;
	}
	
	public void setID() {
		//subclasses override this method
	}

	/**
	 * Gets title.
	 *
	 * @return The title.
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Set the title of the Resource.
	 *
	 * @param title The title of the Resource.
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Gets year.
	 *
	 * @return The release year.
	 */
	public String getYear() {
		return relYear;
	}

	/**
	 * Sets year.
	 *
	 * @param year Sets the release year of the resource.
	 */
	public void setYear(String year) {
		this.relYear = year;
	}

	/**
	 * Gets resCount
	 *
	 * @return The number copies available for the resource.
	 */
	public int getResCount() {
		return resCount;
	}

	/**
	 * Set resCount
	 *
	 * @param stock Sets the number of available resources.
	 */
	public void setResCount(int stock) {
		this.resCount = stock;
	}

	/**
	 * Set the type of the Resource as a String.
	 *
	 * @param type The type of resource(Book, DVD, or Laptop).
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * Gets type.
	 *
	 * @return Returns the string of the type of Resource.
	 */
	public String getType() {
		return this.type;
	}

	/**
	 * Prints the Resource, with all relevant information formatted nicely for easy
	 * reading.
	 *
	 * @return The reformatted string of Resource to be output nicely.
	 */
	public String toString() {
		String result = "";
		result += "ID: " + numberID + "\n";
		result += "Title: " + title + "\n";
		result += "Year of release: " + relYear + "\n";
		result += "StockNo.: " + resCount + "\n";
		return result;
	}
}
