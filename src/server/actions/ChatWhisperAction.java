package server.actions;

/**
 * Action for sending a private message.
 * @author Oscar Strandmark
 */
public class ChatWhisperAction extends Action {

	private static final long serialVersionUID = 4062993243082853196L;
	
	private String usernameTo;
	private String content;
	
	public ChatWhisperAction(String username, String usernameTo, String content) {
		super(username);
		this.usernameTo = usernameTo;
		this.content = content;
	}
	
	public String getReciever() {
		return usernameTo;
	}

	public String getContent() {
		return content;
	}
}
