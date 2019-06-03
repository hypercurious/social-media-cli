import java.util.Scanner;

public class Menu {
	
	Scanner sc = new Scanner(System.in);
	Network net = Network.getInstance();
	
	public void Options(){
		System.out.println("\n\tMENU");
		System.out.println("1. See your wall");
		System.out.println("2. See friends wall\n\ta. Post a message\n\tb. Reply to a message\n\tc. Like");
		System.out.println("3. Send friend request");
		System.out.println("4. Accept/Reject friend Request");
		System.out.println("5. See my friends");
		System.out.println("6. Back");
		System.out.println("7. LogOut");
		System.out.println("8. Exit");
	}
	
}
