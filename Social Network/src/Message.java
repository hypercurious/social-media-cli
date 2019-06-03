import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Message {
	
	private User messageUser;
	private String messageText;
	private String messageTimestamp;
	private int likes = 0;
	private DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
	private LocalDateTime now = LocalDateTime.now();
	private List<ReplyMessage> replyMessage = new ArrayList<ReplyMessage>();

	public String getMessageTimestamp() {
		return messageTimestamp;
	}

	public String getMessageText() {
		return messageText;
	}

	public User getMessageUser() {
		return messageUser;
	}

	public int getLikes() {
		return likes;
	}
	
	public void setMessageTimestamp() {
		messageTimestamp = dtf.format(now);
	}

	public void setMessageText(String messageText) {
		this.messageText = messageText;
	}

	public void setMessageUser(User messageUser) {
		this.messageUser = messageUser;
	}
	
	public void setLikes() {
		likes++;
	}
	
	public List<ReplyMessage> getReplyMessage() {
		return replyMessage;
	}

	public String toString() {
			for(ReplyMessage x:replyMessage)
				return "> " + messageUser.getName() + "\n" + messageText + "\n" + likes + " likes" + "\t" + messageTimestamp + "\n\t" + x.toString();
		return "> " + messageUser.getName() + "\n" + messageText + "\n" + likes + " likes" + "\t" + messageTimestamp;
	}
	
}
