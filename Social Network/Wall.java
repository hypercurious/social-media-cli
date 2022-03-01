import java.util.*;

public class Wall {
	
	private List<Message> message = new ArrayList<Message>();
	private List<ReplyMessage> replyMessage = new ArrayList<ReplyMessage>();
	Network net = Network.getInstance();
	
	public boolean canPost(User user1, User user2) {
		if(!user1.equals(user2))
			return net.areFriends(user1, user2);			
		return true;
	}
	
	public void setMessage(Message msg) {
		message.add(msg);
	}

	public List<Message> getMessage() {
		return message;
	}
	
	public void setReplyMessage(ReplyMessage msg) {
		replyMessage.add(msg);
	}
	
	public List<ReplyMessage> getReplyMessage() {
		return replyMessage;
	}

	public void wallView(User wallUser) {
		System.out.println("\t" + wallUser.getName() + " WALL");
		for(Message x:message)
			System.out.println(x.toString());
	}

}
