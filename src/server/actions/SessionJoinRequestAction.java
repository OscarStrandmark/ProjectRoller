package server.actions;

public class SessionJoinRequestAction extends Action {

	private static final long serialVersionUID = -2728074350431203482L;
	
	private String sessionName;
	
	public SessionJoinRequestAction(String username, String sessionName) {
		super(username);
	
		this.sessionName = sessionName;
	}

	public String getSessionName() {
		return sessionName;
	}

}
