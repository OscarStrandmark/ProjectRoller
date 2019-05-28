package server.actions;
/**
 * Action for displaying a string of text in the chat.
 * 
 * @author Oscar Strandmark
 */
public class ChatDisplayTextAction extends Action {

	private static final long serialVersionUID = -727802040012650051L;
	
	private String text;
	
	public ChatDisplayTextAction(String username, String text) {
		super(username);
		this.text = text;
	}
	
	public String getText() {
		return text;
	}
}
