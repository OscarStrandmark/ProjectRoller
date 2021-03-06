package server.actions;
/**
 * Action for checking if the user input password matches the password for the session.
 * 
 * @author Oscar Strandmark
 */
public class CheckPasswordAction extends Action {

	private static final long serialVersionUID = 9050519898888366623L;
	
	private String sessionName;
	private String password;
	
	public CheckPasswordAction(String username, String sessionName, String password) {
		super(username);
		this.sessionName = sessionName;
		this.password = password;
	}

	public String getSessionName() {
		return sessionName;
	}

	public String getPassword() {
		return password;
	}
}
