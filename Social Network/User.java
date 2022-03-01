import java.util.*;

public class User {

	private String name;
	private String email;
	private int respond;
	private boolean alreadyExecuted = false;
	private List<FriendRequest> friendRequestList = new ArrayList<FriendRequest>();
	private Map<Integer, FriendRequest> friendRequestMap = new HashMap<Integer, FriendRequest>();
	private Network net = Network.getInstance();
	
	public User(String name) {
		this.name = name;
	}

	public void sendRequest(User sender,User receiver) {
		if(net.areFriends(sender, receiver)) {
			System.out.println("You are already friends with this user");
			return;
		}
		List<String> temp = new ArrayList<String>();
		for(FriendRequest x:receiver.friendRequestList){
			temp.add(x.getSender().getName());
		}
		if(!temp.contains(sender.getName())) {
			FriendRequest fr = new FriendRequest();
			fr.setSender(sender);
			fr.setReceiver(receiver);
			fr.setFriendRequestTimestamp();
			receiver.friendRequestList.add(fr);
			System.out.println("Friend Request sent!");
			return;
		}
		System.out.println("Your request is still pending");
		return;
	}
	
	public FriendRequest selectFriendRequest(User user) {
		Scanner sc = new Scanner(System.in);
		if(!user.friendRequestList.isEmpty()) {
			int temp = 0;
			for(FriendRequest x: friendRequestList) {
				user.friendRequestMap.put(temp++, x);
			}
			System.out.println("\tFRIEND REQUEST LIST\n");
			for(Map.Entry<Integer, FriendRequest> x: user.friendRequestMap.entrySet()) {
				System.out.println(1+x.getKey()+". "+x.getValue().toString());
			}
			System.out.println("Which request would you like to respond to?");
			respond = sc.nextInt()-1;
			return user.friendRequestMap.get(respond);
		}
		return null;
	}
	
	public void like(Message msg) {
		if(!alreadyExecuted) {
			msg.setLikes();
			alreadyExecuted = true;
			return;
		}
		System.out.println("You cannot like a message twice!");
	}
	
	public void deleteFriend(User user1, User user2) {
		net.getFriendMap().get(user1).remove(user2);
	}
	
	public  String toString() {
		return "User [name=" + name + ", email=" + email + "]";
	}

	
	String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
}
