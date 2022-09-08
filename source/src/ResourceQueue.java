import java.util.ArrayList;

/**
 * ResourceQueue.java
 * 
 * @version 1.0
 * @since 19/11/2018
 * @author Alex Jones and Zak Chambers Hale
 * 
 *         No copyright infringements intended, holds no copyright.
 * 
 *         This class is to hold queues of objects of type resource once passed
 *         through the IssueDesk. Based on their due date (see IssueDesk) the
 *         queue will be filled in ascending order. Dequeues will be based on
 *         due dates being met (see IssueDesk).
 *
 * @param <Account>
 *            The type of the queue is Account.
 */
public class ResourceQueue<Account> {

	CurrentSessionData temp = new CurrentSessionData();
	private ArrayList<User> queueData;

	private int length; // counter for the length of the queue
	private int arrayListLimit; // limit of the arrayList
	private final int TAKE_ONE = 1;
	private final int ADD_ONE = 1;
	private String date = temp.getDate();
	private String nameOfResource;

	/**
	 * Upon initialisation of this queue object, the size of it is set to 0
	 * (counter)
	 * 
	 * @param copiesAvailable
	 *            This is the number of the copies of the resource
	 */
	public ResourceQueue(String nameOfResource) {
		length = 0;
		arrayListLimit = temp.countNumResources(nameOfResource);
		queueData = new ArrayList<User>(arrayListLimit);
		//this.listType = e;
		System.out.println("The current date is " + temp.getDate());
		this.nameOfResource = nameOfResource;
	}

	/**
	 * This method is to add objects to the queue.
	 * 
	 * @param elem
	 *            is the element being added.
	 */
	public void enqueue(User u) {
		if (length < arrayListLimit) {
			queueData.add(length, u);	
			length++;
			
			// listType.setResCount(listType.getResCount() - TAKE_ONE);
			// u.addWithdrawnResource(listType);
		} else {
			System.out.println("The queue is full, you can't add more...Requesting the latest copy to be brought back");
		}
	}

	/**
	 * This method removes the item at the front of the queue, a.k.a when requested
	 * and due date is met.
	 */
	public void dequeue(User u) {
		if (length == 0) {
			System.out.println("Impossible to dequeue! Queue is empty...");
		} else {
			queueData.remove(u);
			length--;
			System.out.println("Dequeued");
			
			// listType.setResCount(listType.getResCount() + ADD_ONE);
			// u.removeWithdrawnResource(listType);
		}
	}

	/**
	 * This method looks at the top item in the queue, basically the first thing
	 * added.
	 * 
	 * @return A generic object that will compile to the specific resource type when
	 *         needed.
	 */
	public String peek() {
		if (length == 0) {
			return null;
		} else {
			return queueData.get(0).getUsername() + " has been renting " + nameOfResource + " for the longest";
		}
	}

	/**
	 * Method to check if the queue is empty or not.
	 * 
	 * @return True if the queue is empty, else False.
	 */
	public boolean isEmpty() {
		return length == 0;
	}
	
	/**
	 * Method to check if the queue is full or not.
	 * 
	 * @return True if the queue is full, else False.
	 */
	public boolean isFull() {
		return length == arrayListLimit;
	}
	
	// /**
	// * Method to return the type of Resource that the queue is.
	// *
	// * @return The Resource.
	// */
	// public Resource getListType() {
	// return listType;
	// }
}

