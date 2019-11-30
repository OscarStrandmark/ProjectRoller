package server.actions;
/**
 * Action that represents the change of username of a client.
 * 
 * @author Oscar Strandmark
 */
public class UsernameChangeAction extends Action {

	private static final long serialVersionUID = 8299247328693470128L;

	private String newUsername;
	
	public UsernameChangeAction(String username, String newUsername) {
		super(username);
		this.newUsername = newUsername;
	}

	public String getNewUsername() {
		return newUsername;
	}

}
