package server.actions;

/**
 * Action for sending a rp-message
 * @author Oscar Strandmark
 */
public class ChatRPAction extends Action {

	private static final long serialVersionUID = 5631309823276844776L;
	
	private String name;
	private String content;
	
	public ChatRPAction(String username, String name, String content) {
		super(username);
		this.name = name;
		this.content = content;
	}
	
	public String getName() {
		return name;
	}
	
	public String getContent() {
		return content;
	}
}
