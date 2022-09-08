
public class ResourceTest {

	public static void main(String[] args) {

		CurrentSessionData temp = new CurrentSessionData();
		
		String harryPotter = temp.getWholeFileLine("ResourceDatabase2", "Harry Potter");
		System.out.println(harryPotter);
		String fightClub = temp.getWholeFileLine("ResourceDatabase2", "Fight Club");
		System.out.println(fightClub);
		String RP1 = temp.getWholeFileLine("ResourceDatabase2", "Ernest Cline");
		System.out.println(RP1);
		
		
		ResourceQueue rqHP = new ResourceQueue("Harry Potter");
		ResourceQueue rqFC = new ResourceQueue("Fight Club");
		ResourceQueue rqRP1 = new ResourceQueue("Ready Player One");
		
		User ZappaZak = new User ("ZappaZak", "Zak", "Chambers",  "07", "Gwydr", "Uplands", "Swansea", "SA2");
		User JappaJak = new User("JappaJak", "Jak", "Hale",  "08", "Gwydr", "Uplands", "Swansea", "SA2");
		User ClappaClak = new User("ClappaClak", "Clap", "Onishles",  "09", "Gwydr", "Uplands", "Swansea", "SA2");
		User HappaHak = new User("HappaHak", "Nak", "Jameson",  "10", "Gwydr", "Uplands", "Swansea", "SA2");
		User LappaLak = new User("LappaLak", "Mac", "Wilson",  "11", "Gwydr", "Uplands", "Swansea", "SA2");
		User NappaNak = new User("NappaNak", "Mesh", "Jones",  "12", "Gwydr", "Uplands", "Swansea", "SA2");
		User MappaMak = new User("MappaMak", "Greece", "Jones",  "13", "Gwydr", "Uplands", "Swansea", "SA2");
		User Liam_O = new User("Liam_O", "Zak", "Chambers",  "14", "Gwydr", "Uplands", "Swansea", "SA2");
		
		rqHP.enqueue(ZappaZak);
		rqHP.enqueue(JappaJak);
		rqHP.enqueue(Liam_O);
		rqHP.enqueue(HappaHak);
		
		rqHP.dequeue(JappaJak);
		rqHP.dequeue(HappaHak);
		
		rqHP.enqueue(LappaLak);
		rqHP.enqueue(MappaMak);
		System.out.println(rqHP.peek());
		rqHP.dequeue(ZappaZak);
		System.out.println(rqHP.peek());
		
	}

}
