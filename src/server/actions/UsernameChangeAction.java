package server.actions;

public class UsernameChangeAction extends Action {

	private String newUsername;
	
	public UsernameChangeAction(String username, String newUsername) {
		super(username);
		this.newUsername = newUsername;
	}

	public String getNewUsername() {
		return newUsername;
	}

}
