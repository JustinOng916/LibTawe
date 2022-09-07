/**
 * Book.java
 *
 * @author Alex Jones and Zak Chambers Hale
 *
 * No copyright infringements intended, holds no copyright.
 *
 * This class is a subclass of Resource, that will hold all of the base
 * data. This class is for instantiation of Book objects of type
 * Resource and it adds the new fields that aren't pertinent in other
 * Resource-type classes.
 *
 * @version 1.0
 * @since 19/11/2018
 */

public class Book extends Resource {

	protected String author; // Author of the book
	protected String publisher; // Publisher of the book
	protected String genre; // Genre of the book
	protected String ISBN; // International Standard Book Number
	protected String language; // Language of the book

	/**
	 * This is a constructor for Book type objects, and will build a Book and assign
	 * respective fields.
	 *
	 * @param numberID  ID of the Book.
	 * @param title     Title of the Book.
	 * @param relYear   Release year of the Book.
	 * @param resCount  Number of copies of the Book.
	 * @param author    The author of the Book.
	 * @param publisher The publisher of the Book.
	 * @param genre     The genre of the Book.
	 * @param ISBN      The ISBN number of the Book.
	 * @param language  The language that the book is written in.
	 */
	public Book(String numberID, String title, String relYear, int resCount, String author, String publisher,
			String genre, String ISBN) {
			super("Book", numberID, title, relYear, resCount);
		
		this.author = author;
		this.publisher = publisher;
		this.genre = genre;
		this.ISBN = ISBN;
		this.language = "English";
		
		super.setType("Book");
	}

	/**
	 * @param numID
	 */
	public void setID(String numID) {
		this.numberID = 'b' + numID;
	}

	/**
	 * @param UID
	 */
	public void setUniqueID(String UID) {
		super.UID = 'b' + UID;
	}

	/**
	 * Sets Author.
	 *
	 * @param author The author of the Book.
	 */
	public void setAuthor(String author) {
		this.author = author;
	}

	/**
	 * Gets Author.
	 *
	 * @return The author's name.
	 */
	public String getAuthor() {
		return author;
	}

	/**
	 * Sets Publisher.
	 *
	 * @param publisher The publisher of the Book.
	 */
	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	/**
	 * Gets publisher.
	 *
	 * @return The publisher of the book.
	 */
	public String getPublisher() {
		return publisher;
	}

	/**
	 * Sets the genre.
	 *
	 * @param genre The genre of the Book.
	 */
	public void setGenre(String genre) {
		this.genre = genre;
	}

	/**
	 * Gets the genre.
	 *
	 * @return The genre of the Book.
	 */
	public String getGenre() {
		return genre;
	}

	/**
	 * Sets the ISBN.
	 *
	 * @param ISBN The ISBN number of the Book.
	 */
	public void setISBN(String ISBN) {
		this.ISBN = ISBN;
	}

	/**
	 * Gets the ISBN.
	 *
	 * @return The ISBN number of the Book.
	 */
	public String getISBN() {
		return ISBN;
	}

	/**
	 * Sets the language.
	 *
	 * @param language The language of the Book.
	 */
	public void setLanguage(String language) {
		this.language = language;
	}

	/**
	 * Gets the language.
	 *
	 * @return The language of the Book.
	 */
	public String getLanguage() {
		return language;
	}

	/**
	 * Prints the Book, with all relevant information formatted nicely for easy reading.
	 *
	 * @return The reformatted string of Book to be output nicely, combined with the
	 * Resource toString() to complete the resources information.
	 */
	public String toString() {
		String result = "This is a Book\n";
		result += "Author: " + this.author + "\n";
		result += "Publisher: " + this.publisher + "\n";
		result += "Genre: " + this.genre + "\n";
		result += "ISBN: " + this.ISBN + "\n";
		result += "Language: " + this.language + "\n";
		result += super.toString();
		return result;
	}
}
