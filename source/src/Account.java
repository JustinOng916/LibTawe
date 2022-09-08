/**
 * Account.java
 * @version 1.0
 * @author Zak Chambers Hale & Alex Jones
 * Superclass for User and Librarian
 */
abstract class Account {
    protected String username;
    protected String firstName;
    protected String surname;
    protected String profileImgPath;
    protected String phoneNum;
    protected String addLn1;
    protected String addLn2;
    protected String city;
    protected String postcode;
    
    /**
     * @param username; 
     * @param firstName;
     * @param surname;
     * @param profileImg;
     * @param phoneNum;
     * @param addLn1;
     * @param addLn2;
     * @param city;
     * @param postcode;
     */

    
    //Same constructor, no image
    public Account (String username, String firstName, String surname, String phoneNum, String addLn1, String addLn2, String city, String postcode) {
        this.username = username;
        this.firstName = firstName;
        this.surname = surname;
        this.phoneNum = phoneNum;
        this.addLn1 = addLn1;
        this.addLn2 = addLn2;
        this.city = city; 
        this.postcode = postcode;
        
        setProfileImg("Images/" + username + ".png");
        
    }
        
    /**
     * Gets username
     * @return
     */
    public String getUsername () {
        return username;
    }
    
    /**
     * Sets username
     * @param username
     */
    public void setUsername (String username) {
        this.username = username;
    }
    
    /**
     * Gets first name
     * @return
     */
    public String getFirstName () {
        return firstName;
    }
    
    /**
     * Sets first name
     * @param firstName
     */
    public void setFirstName (String firstName) {
        this.firstName = firstName;
    }
    
    /**
     * Gets surname
     * @return
     */
    public String getSurname () {
        return surname;
    }
    
    /**
     * Sets surname
     * @param surname
     */
    public void setSurname (String surname) {
        this.surname = surname;
    }
    
    /**
     * Gets profile image
     * @return
     */
    public String getProfileImg () {
        return profileImgPath;
    }
    
    /**
     * Sets profile image
     * @param profileImg
     */
    public void setProfileImg (String path) {
        this.profileImgPath = path;
    }
    
    /**
     * Gets phone number
     * @return
     */
    public String getPhoneNum () {
        return phoneNum;
    }
    
    /**
     * Sets phone number
     * @param phoneNum
     */
    public void setPhoneNum (String phoneNum) {
        this.phoneNum = phoneNum;
    }
    
    /**
     * Gets address line 1
     * @return
     */
    public String getAddLn1 () {
        return addLn1;
    }
    
    /**
     * Sets address line 1
     * @param addLn1
     */
    public void setAddLn1 (String addLn1) {
        this.addLn1 = addLn1;
    }
    
    /**
     * Gets address line 2
     * @return
     */
    public String getAddLn2 () {
        return addLn2;
    }
    
    /**
     * Sets address line 2
     * @param addLn2
     */
    public void setAddLn2 (String addLn2) {
        this.addLn2 = addLn2;
    }
    
    /**
     * Gets city name
     * @return
     */
    public String getCity () {
        return city;
    }
    
    /** 
     * Sets city name
     * @param city
     */
    public void setCity (String city) {
        this.city = city;
    }
    
    /**
     * Gets postcode
     * @return
     */
    public String getPostcode () {
        return postcode;
    }
    
    /**
     * Sets postcode
     * @param postcode
     */
    public void setPostcode () {
        this.postcode = postcode;
    }
    
    /**
	 * Prints the Account, with all relevant information formatted nicely.
	 * 
	 * @return The reformatted string of Account to be output nicely.
	 */
    public String toString() {
    	String res = "";
    	res += "Username: " + username + "\n";
    	res += "Full name: " + firstName + " " + surname + "\n";
    	res += "Phone no.: " + phoneNum + "\n";
    	res += "---Address----" + "\n";
    	res += addLn1 + "\n";
    	res += addLn2 + "\n";
    	res += city + "\n";
    	res += postcode;
    	return res;
    }
}