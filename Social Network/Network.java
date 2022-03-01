import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Scanner;
import java.util.Random;

public class Network {
	
	private Menu menu = new Menu();
	private Map<String, User> allUsers = new HashMap<String, User>();
	private Map<User, Wall> wallMap = new HashMap<User, Wall>();
	private Map<User, List<User>> friendMap = new HashMap<User, List<User>>();
	private Map<Integer, Message> messageMap = new HashMap<Integer, Message>();
	
	private Network() {}
	
	private static final Network _instance;
	
	static {
		_instance = new Network();
	}
	
	public static Network getInstance() {
		return _instance;
	}
	
	public void addUser(User user) {
		allUsers.put(user.getName(), user);
		friendMap.put(user, null);
		Wall wall = new Wall();
		wallMap.put(user, wall);
	}
	
	public void deleteUser(User user) {
		allUsers.remove(user.getName());
		friendMap.remove(user);
		wallMap.remove(user);
	}
	
	public void userSign() {
		Scanner sc1 = new Scanner(System.in);
		System.out.println("\tWelcome to papaNet :D");
		System.out.println("Type \"Log In\" to log in or \"Sign Up\" to sign up.");
		String temp = sc1.nextLine();
		if(temp.equalsIgnoreCase("Log In")) {
			signIn();
		}
		if(temp.equalsIgnoreCase("Sign Up")) {
			signUp();
		}
		if(!temp.equalsIgnoreCase("Log In") || temp.equalsIgnoreCase("Sign Up")) {
			System.out.println("Invalid Option!\n");
			userSign();
		}
	}
	
	public void signIn() {
		Scanner sc2 = new Scanner(System.in);
		System.out.println("\tLOG IN");
		System.out.println("Please type your username: ");
		String username = sc2.nextLine();
		System.out.println("Please type your email: ");
		String email;
		email = sc2.nextLine();
		for(Map.Entry<String, User> x: allUsers.entrySet()) {
			if(username.equalsIgnoreCase(x.getKey()) && email.equalsIgnoreCase(x.getValue().getEmail())) {
				System.out.println("Welcome "+x.getKey()+", we are at your disposal.");
				System.out.println("Please select an option by typing an integer from 1 to 8.");
				menuFunctionalities(x.getValue());
				return;
			}
		}
		for(Map.Entry<String, User> x: allUsers.entrySet()) {
			if(!(username.equalsIgnoreCase(x.getKey()) && email.equalsIgnoreCase(x.getValue().getEmail()))) {
				System.out.println("Invalid username or email!");
				userSign();
			}
		}
	}
	
	public void signUp() {
		Scanner sc3 = new Scanner(System.in);
		System.out.println("\tSIGN UP");
		System.out.println("Enter a username: ");
		String temp;
		temp = sc3.nextLine();
		for(Map.Entry<String, User> x: allUsers.entrySet()) {
			if(temp.equalsIgnoreCase(x.getKey())) {
				System.out.println("Username already taken, try something else");
				signUp();
			}
		}
		for(Map.Entry<String, User> x: allUsers.entrySet()) {
			if(!temp.equalsIgnoreCase(x.getKey())) {
				User user = new User(temp);
				addUser(user);
				System.out.println("Enter your email: ");
				String email;
				email = sc3.nextLine();
				user.setEmail(email);
				System.out.println("Sign up completed");
				signIn();
			}
		}
	}
	
	public void respondFriendRequest(User user) {
		Scanner sc4 = new Scanner(System.in);
		FriendRequest fr = user.selectFriendRequest(user);
		if(fr==null) {
			System.out.println("You have no friend requests :D");
			return;
		}
		System.out.println("Type \"Accept\" or \"Reject\" to respond. Type anything else to cancel");
		String temp = sc4.nextLine();
		if(temp.equalsIgnoreCase("Reject")) {
			fr.setFriendRequestStatus("Rejected");
			System.out.println("User Rejected");
			return;
		}
		if(!(temp.equalsIgnoreCase("Accept") || temp.equalsIgnoreCase("Reject"))) {
			System.out.println("User Pending");
			return;
		}
		if(friendMap.get(user)==null) {
			List<User> sender = new ArrayList<User>();
			sender.add(fr.getSender());
			friendMap.put(user, sender);
			List<User> receiver = new ArrayList<User>();
			receiver.add(user);
			friendMap.put(fr.getSender(), receiver);
			fr.setFriendRequestStatus("Accepted");
			System.out.println("User Accepted");
			return;
		}
		if(!friendMap.get(user).contains(fr.getSender())) {
			friendMap.get(user).add(fr.getSender());
			friendMap.get(fr.getSender()).add(user);
			fr.setFriendRequestStatus("Accepted");
			System.out.println("User Accepted");
			return;
		}
		System.out.println("User already accepted!");
	}
	
	public void suggestions(User user) {
		Scanner sc5 = new Scanner(System.in);
		List<User> notFriends = new ArrayList<User>();
		for(Map.Entry<String, User> x: allUsers.entrySet()) {
			notFriends.add(x.getValue());	
		}
		notFriends.remove(user);
		if(friendMap.get(user)!=null) {
			notFriends.removeAll(friendMap.get(user));			
		}
		Map<Integer, User> suggestedFriends = new HashMap<Integer, User>();
		for(int x=1;x<=notFriends.size();x++) {
			suggestedFriends.put(x, notFriends.get(x-1));
		}
		System.out.println("Select a user, to add him to your friend list");
		for(Map.Entry<Integer, User> entry : suggestedFriends.entrySet()) {
			System.out.println(entry.getKey()+". "+entry.getValue().getName());	
		}
		try {
			int input = sc5.nextInt();			
			user.sendRequest(user, suggestedFriends.get(input));
		}catch(InputMismatchException e) {
			System.out.println("Please type an integer!");
			suggestions(user);
		}
	}
	
	public void userFriendList(User user) {
		List<String> temp = new ArrayList<String>();
		System.out.println("\tFRIEND LIST");
		if(friendMap.get(user)==null) {
			System.out.println("You have no friends");
			return;
		}
		if(friendMap.get(user)!=null) {
			for(int x=0;x<friendMap.get(user).size();x++) {
				temp.add(x, friendMap.get(user).get(x).getName());
			}			
			for(String x: temp)
				System.out.println(x);
			return;
		}
	}
	
	public void friendWallView(User user) {
		Scanner sc6 = new Scanner(System.in);
		System.out.println("Type the user you want to view his wall: ");
		String temp = sc6.nextLine();
		for(Map.Entry<String, User> x: allUsers.entrySet()) {
			if(temp.equalsIgnoreCase(x.getKey())) {
				User user1 = allUsers.get(temp);
				wallMap.get(user1).wallView(user1);
				friendWallActions(user, user1);
				return;
			}
		}
		System.out.println("User not found, try again");
		friendWallView(user);
	}
	
	public void friendWallActions(User user, User user1) {
		Scanner sc7 = new Scanner(System.in);
		System.out.println("Type \"a\" to post a message, \"b\" to reply to a message or \"c\" to like a message");
		String input = sc7.nextLine();
		switch(input){
		case "a":
			postMessage(user, user1);
			break;
		case "b":
			replyMessage(user, user1);
			break;
		case "c":
			likeMessage(user);
			break;
		default:
			System.out.println("Invalid Option!");
			friendWallActions(user, user1);
		}
	}
	
	public void postMessage(User user, User user1) {
		Wall wall = wallMap.get(user1);
		if(wall.canPost(user, user1)) {
			Message msg = new Message();
			Scanner sc = new Scanner(System.in);
			System.out.println("You can now write at "+user1.getName()+" wall");
			String messageText = sc.nextLine();
			msg.setMessageUser(user);
			msg.setMessageText(messageText);
			msg.setMessageTimestamp();
			wall.setMessage(msg);
			return;
		}
		System.out.println("You cannot post a message here");
	}
	
	public void replyMessage(User user, User user1) {
		Wall wall = wallMap.get(user1);
		if(wall.canPost(user, user1) && !wall.getMessage().isEmpty()) {
			int numb = selectMessage(user);
			System.out.println("You are now replying at message #"+numb);
			Message message = messageMap.get(--numb);
			ReplyMessage replyMsg = new ReplyMessage();
			message.getReplyMessage().add(replyMsg);
			Scanner sc8 = new Scanner(System.in);
			String replyText = sc8.nextLine();
			replyMsg.setMessageUser(user);
			replyMsg.setMessageText(replyText);
			replyMsg.setMessageTimestamp();
			wall.setReplyMessage(replyMsg);
			return;
		}
		System.out.println("You cannot post a reply");
	}
	
	public void likeMessage(User user) {
		int numb = selectMessage(user);
		Message msg = messageMap.get(--numb);
		user.like(msg);
	}
	
	public int selectMessage(User user) {
		Scanner sc8 = new Scanner(System.in);
		int counter = 0;
		for(Message x: wallMap.get(user).getMessage()) {
			messageMap.put(counter++, x);
		}
		System.out.println("Select a message: ");
		for(Map.Entry<Integer, Message> x: messageMap.entrySet()) {
			System.out.println(1+x.getKey()+". "+x.getValue().toString());
		}
		try {
			int temp = sc8.nextInt();
			return temp;			
		}catch(InputMismatchException e) {
			System.out.println("Please enter an interger!");
			selectMessage(user);
		}
		return 0;
	}
	
	public boolean areFriends(User user1, User user2) {
		if(friendMap.get(user1)==null) {
			return false;
		}
		Set<User> unionValues = new HashSet<User>();
		unionValues.addAll(friendMap.get(user1));
		unionValues.add(user2);
		unionValues.removeAll(friendMap.get(user1));
		return unionValues.isEmpty();
	}
	
	public String mutualFriends(User user1, User user2) {
		List<User> user1Values = new ArrayList<User>();
		List<User> user2Values = new ArrayList<User>();
		user1Values.addAll(friendMap.get(user1));
		user2Values.addAll(friendMap.get(user2));
		user1Values.retainAll(user2Values);
		return user1Values.toString();
	}


	public void menuFunctionalities(User user) {
		Scanner scan = new Scanner(System.in);
		menu.Options();
		String input = scan.nextLine();
		switch(input) {
		case "1":
			wallMap.get(user).wallView(user);;
			menuFunctionalities(user);
			break;
		case "2":
			friendWallView(user);
			menuFunctionalities(user);
			break;
		case "3":
			suggestions(user);
			menuFunctionalities(user);
			break;
		case "4":
			respondFriendRequest(user);
			menuFunctionalities(user);
			break;
		case "5":
			userFriendList(user);
			menuFunctionalities(user);
			break;
		case "6":
			System.out.println("Just a decorative functionality :D");
			menuFunctionalities(user);
			break;
		case "7":
			user = null;
			System.out.println("Logging out...");
			signIn();
			break;
		case "8":
			System.out.println("Exiting...");
			break;
		/*easterEggStart*/
		case "311298":
			Random rand = new Random();
			int randNumb = rand.nextInt(11);
			System.out.println("Your grade: " + randNumb + "/10");
			break;
		/*easterEggEnd*/
		default:
			System.out.println("Invalid Option");
			menuFunctionalities(user);
		}
	}
	
	public Map<User, List<User>> getFriendMap() {
		return friendMap;
	}
	
	public void setFriendMap(Map<User, List<User>> friendMap) {
		this.friendMap = friendMap;
	}

	public Map<User, Wall> getWallMap() {
		return wallMap;
	}

	public void setWallMap(Map<User, Wall> wallMap) {
		this.wallMap = wallMap;
	}

	public Map<Integer, Message> getMessageMap() {
		return messageMap;
	}

}
