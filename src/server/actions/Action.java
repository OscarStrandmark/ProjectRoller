package server.actions;

import java.io.Serializable;

/**
 * Base class for all actions to extend. This is used to send what has happened between the server and the client
 *
 * @author Oscar Strandmark
 */
public class Action implements Serializable {

	private static final long serialVersionUID = -704853619854051998L;

	private String username;

	public Action(String username) {
		this.username = username;
	}

	/**
	 * Get the username of the user that is the source of the action.
	 * @return String - The username of the user.
	 */
	public String getUsername() {
		return username;
	}
}
