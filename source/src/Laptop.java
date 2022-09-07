/**
 * Laptop.java
 *
 * @author Alex Jones and Zak Chambers Hale
 *
 * No copyright infringements intended, holds no copyright.
 *
 * This class is a subclass of Resource, that will hold all of the base
 * data. This class is for instantiation of Laptop objects of type
 * Resource and it adds the new fields that aren't pertinent in other
 * Resource-type classes.
 *
 * @version 1.0
 * @since 19/11/2018
 */
public class Laptop extends Resource {

	protected String manufacturer; // The manufacture
	protected String OS; // The operating system
	protected String model; // The model of laptop

	/**
	 * This is a constructor for Laptop type objects, and will build a Laptop and
	 * assign respective fields.
	 *
	 * @param numberID     ID of the Laptop
	 * @param title        Title of the Laptop
	 * @param relYear      Release year of the Laptop
	 * @param resCount     Number of copies of the Laptop
	 * @param manufacturer Manufacturer of the Laptop
	 * @param OS           Operating System of the laptop
	 * @param model        Model of the laptop
	 */
	public Laptop(String numberID, String title, String relYear, int resCount, String manufacturer, String OS,
			String model) {
		super("Laptop", numberID,title,relYear,resCount);
		
		this.manufacturer = manufacturer;
		this.OS = OS;
		this.model = model;
		
		super.setType("Laptop");
	}

	/**
	 * Sets ID.
	 *
	 * @param numID The ID of the Laptop.
	 */
	public void setID(String numID) {
		this.numberID = 'l' + numID;
	}

	/**
	 * Sets manufacturer.
	 *
	 * @param manufacturerName The manufacturer of the name.
	 */
	public void setManufacturer(String manufacturerName) {
		this.manufacturer = manufacturerName;
	}

	/**
	 * Gets manufacturer.
	 *
	 * @return The manufacturer name.
	 */
	public String getManufacturer() {
		return manufacturer;
	}

	/**
	 * Sets OS.
	 *
	 * @param OS The OS of the Laptop.
	 */
	public void setOS(String OS) {
		this.OS = OS;
	}

	/**
	 * Gets OS.
	 *
	 * @return The operating system.
	 */
	public String getOS() {
		return OS;
	}

	/**
	 * Sets model.
	 *
	 * @param model The model of the Laptop.
	 */
	public void setModel(String model) {
		this.model = model;
	}

	/**
	 * Gets model.
	 *
	 * @return The model.
	 */
	public String getModel() {
		return model;
	}

	/**
	 * Prints the Laptop, with all relevant information formatted nicely.
	 *
	 * @return The reformatted string of Laptop to be output nicely, combined with
	 * the Resource toString() to complete the resources information.
	 */
	public String toString() {

		String result = "This is a Laptop\n";
		result += "Manufacturer: " + this.manufacturer + "\n";
		result += "OS: " + this.OS + "\n";
		result += "Model: " + this.model + "\n";
		;
		result += super.toString();
		return result;
	}
}