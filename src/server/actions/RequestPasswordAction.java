package server.actions;

public class RequestPasswordAction extends Action {

	private static final long serialVersionUID = -7337701848255176850L;
	
	private String sessionName;
	
	public RequestPasswordAction(String username, String sessionName) {
		super(username);
		this.sessionName = sessionName;
	}

	public String getSessionName() {
		return sessionName;
	}
}
