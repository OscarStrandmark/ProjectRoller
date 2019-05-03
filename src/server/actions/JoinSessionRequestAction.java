package server.actions;

public class JoinSessionRequestAction extends Action {

	private static final long serialVersionUID = -2728074350431203482L;
	
	private String sessionName;
	
	public JoinSessionRequestAction(String username, String sessionName) {
		super(username);
	
		this.sessionName = sessionName;
	}

	public String getSessionName() {
		return sessionName;
	}

}
