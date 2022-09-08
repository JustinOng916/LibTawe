/**
 * DVD.java
 * 
 * @version 1.0
 * @since 19/11/2018
 * @author Alex Jones and Zak Chambers Hale
 * 
 *         No copyright infringements intended, holds no copyright.
 * 
 *         This class is a subclass of Resource which will hold all of the base
 *         data. This class is for instantiation of DVD objects of type Resource
 *         and it adds the new fields that aren't pertinent in other
 *         Resource-type classes.
 */
public class DVD extends Resource {

	protected String director; // Director of DVD
	protected String runtime; // Runtime in minutes
	protected String language; // Language of DVD

	/**
	 * This is a constructor for DVD type objects, and will build a DVD and assign
	 * respective fields.
	 * 
	 * @param numberID
	 *            ID of the DVD
	 * @param title
	 *            Title of the DVD
	 * @param relYear
	 *            Release year of the DVD
	 * @param resCount
	 *            Number of copies of the DVD
	 * @param director
	 * @param runtime
	 * @param language
	 */
	public DVD(String numberID, String title, String relYear, int resCount, String director, String runtime,
			String language) {
		super("DVD", numberID, title, relYear, resCount);
		
		this.director = director;
		this.runtime = runtime;
		this.language = language;
		
		super.setType("DVD");
	}
	
	/**
	 * Change the director name.
	 * 
	 * @param director
	 *            The director of the film.
	 */
	public void setDirector(String director) {
		this.director = director;
	}

	/**
	 * Retrieves the director.
	 * 
	 * @return The director name.
	 */
	public String getDirector() {
		return director;
	}
	public void setID(String numID) {
		this.numberID = 'd' + numID;
	}
	/**
	 * Change the runtime length.
	 * 
	 * @param runtime
	 *            The runtime in minutes.
	 */
	public void setRuntime(String runtime) {
		this.runtime = runtime;
	}

	/**
	 * Retrieves the runtime
	 * 
	 * @return A string that shows the runtime in minutes.
	 */
	public String getRuntime() {
		return runtime + "minutes";
	}

	/**
	 * Change the language.
	 * 
	 * @param language
	 *            The string of whatever language.
	 */
	public void setLanguage(String language) {
		this.language = language;
	}

	/**
	 * Retrieves the language
	 * 
	 * @return The language
	 */
	public String getLanguage() {
		return language;
	}

	/**
	 * Prints the DVD, with all relevant information formatted nicely.
	 * 
	 * @return The reformatted string of DVD to be output nicely, combined with the
	 *         Resource toString().
	 */
	public String toString() {
		String result = "This is a DVD\n";
		result += "Director: " + this.director + "\n";
		result += "Runtime: " + this.runtime + "minutes" + "\n";
		result += "Language: " + this.language + "\n";
		result += super.toString();
		return result;
	}
}
