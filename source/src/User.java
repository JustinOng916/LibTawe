import java.util.ArrayList;

/**
 * User.java
 *
 * @author Zak Chambers Hale & Alex Jones
 *
 * Subclass of Account that does not have administrative privileges. This
 * subclass of account can accumulate fines as well as pay them off if
 * they wish.
 *
 * @version 1.0
 * @since 19/11/2018
 */
public class User extends Account {
    protected double fine;
    protected double totalFine = 0;
    protected final double BOOK_OR_DVD_FINE = 2.00;
    protected final double BOOK_OR_DVD_FINE_MAX = 25.00;
    protected final double LAPTOP_FINE = 10.00;
    protected final double LAPTOP_FINE_MAX = 100.00;
    private final double NULL_FINE = 0;
    
    private ArrayList<Resource> withdrawnResources = new ArrayList<Resource>();;

	/**
	 * @param username;     The username of the User.
	 * @param firstName;    The first name of the User.
	 * @param surname;      The surname of the User.
	 * @param phoneNum;     The phone number of the User.
	 * @param addLn1;       The first address line of the User.
	 * @param addLn2;       The second address line of the User.
	 * @param city;         The residing city of the User.
	 * @param postcode;     The postcode of the User.
	 */
    public User (String username, String firstName, String surname,  String phoneNum, String addLn1, String addLn2, String city, String postcode) {
        super(username, firstName, surname, phoneNum, addLn1, addLn2, city, postcode);
    }

	/**
	 * Sets fine amount for user
	 *
	 * @param daysLate  The amount of days late after the due date
	 * @param r         The resource that is late being returned
	 */
    public void setFine (int daysLate, Resource r) {
        switch(r.getType()) {
        	case "Book":
        	case "DVD":
        		if (daysLate <= 0) {
        			this.fine = 0;        			
        		} else if (daysLate >= 13) {
        			this.fine = BOOK_OR_DVD_FINE_MAX;
        		} else {
        			this.fine = BOOK_OR_DVD_FINE * daysLate;
        		}
        		break;
        	case "Laptop":
        		if (daysLate < 0) {
        			this.fine = 0;
        		} else if (daysLate >= 10) {
        			this.fine = LAPTOP_FINE_MAX;
        		} else {
        			this.fine = LAPTOP_FINE * daysLate;
        		}
        		break;
        		
        	}
        totalFine += this.fine;
    }

	/**
	 * Allows the user to pay either a fraction or all of a fine that has
	 * been set upon the account.
	 *
	 * @param amount The account that wishes to pay the fine.
	 */
    public void payFine(double amount) {
    	if (amount < 0.01) {
    		System.out.println("Must pay an amount between �0.01 and " + "�" + totalFine);
    	} else if (amount <= totalFine) {
    		totalFine -= amount;
    	} else {
    		System.out.println("Must pay an amount between �" + totalFine + " and �0.01");
    	}
    }

	/**
	 * Returns the amount of fines that are currently on the account.
	 *
	 * @return The total amount of fines that are set on the account.
	 */
    public String getFine () {
    	String result = String.format("%.2f", totalFine);
        return "�" + result;
    }

	/**
	 * Resets user's fine to 0.
	 */
    public void resetFines () {
        this.fine = NULL_FINE;
    }
  
    public String getWithdrawnResources() {
    	String withdrawnRes = "";
    	for (int i = 0; i < withdrawnResources.size(); i++) {
    		withdrawnRes += "\n" + (withdrawnResources.get(i)).getTitle();
    	}
    	return withdrawnRes;
    }

    public void addWithdrawnResource(String resourceName) {
    	//withdrawnResources.add(e); was of type Resource as parameter

    }

	/**
	 * Removes a withdrawn resource from the users account.
	 * @param e
	 */
    public void removeWithdrawnResource(Resource e) {
    	for (int i = 0; i <= withdrawnResources.size(); i++) {
    		Resource temp = withdrawnResources.get(i);
    		if (withdrawnResources.contains(e)) {
    			if (temp.getType().equalsIgnoreCase(e.getType())) {
        			withdrawnResources.remove(i);
        		}
    		} else {
    			System.out.println("The Resource: " + e.getTitle() + " isn't withdrawn by User: " + this.username);
    		}
    	}
    }

	/**
	 * Prints the User, with all relevant information formatted nicely for easy
	 * reading.
	 *
	 * @return The reformatted string of User to be output nicely, combined with the
	 * Account toString().
	 */
    public String toString() {
    	String res = "This account is a User" + "\n";
    	res += super.toString();
    	return res;
    }
}