package server.actions;
/**
 * Action for displaying a user sent chat message in the chat.
 * 
 * @author Oscar Strandmark
 */
public class ChatMessageAction extends Action {

	private static final long serialVersionUID = -6816377779497793586L;
	
	private String message;
	
	public ChatMessageAction(String username, String message) {
		super(username);
		this.message = message;
	}

	public String getMessage() {
		return message;
	}
}
