import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class FriendRequest {
	

	private User sender;
	private User receiver;
	private String frt;
	private String frs = "Pending";
	private DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
	private LocalDateTime now = LocalDateTime.now();
	//private Date now = new Date();
	
	
	public String getFriendRequestTimestamp() {
		return frt;
	}
	
	public void setFriendRequestTimestamp() {
		frt = dtf.format(now);
	}
	
	public String getFriendRequestStatus() {
		return frs;
	}
	
	public void setFriendRequestStatus(String frs) {
		this.frs = frs;
	}
	
	public User getSender() {
		return sender;
	}
	
	public void setSender(User sender) {
		this.sender = sender;
	}
	
	public User getReceiver() {
		return receiver;
	}
	
	public void setReceiver(User receiver) {
		this.receiver = receiver;
	}

	public String toString() {
		return sender.getName()+"\t*"+getFriendRequestStatus()+"*" + "\n"
				+ getFriendRequestTimestamp();
	}
	
}
