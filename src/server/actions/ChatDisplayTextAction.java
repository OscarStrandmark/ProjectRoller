package server.actions;

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
