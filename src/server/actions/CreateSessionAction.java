package server.actions;

/**
 * Class that is used in the object streams between server and client.
 * When an object of this class is sent a client is making a request to create a new session.
 * @author Oscar Strandmark
 */
public class CreateSessionAction extends Action {

	private static final long serialVersionUID = 810392655728758390L;

	private boolean isPassworded;
	private String sessionName;
	private int maxPlayers;
	private String password;

	/**
	 * Create an object of this class to create a session with a password.
	 *
	 * @param username Username of the user creating the session.
	 * @param sessionName Name of the session.
	 * @param maxPlayers Maximum amount of users that is allowed to connect to this session.
	 * @param password The password this session will use.
	 */
	public CreateSessionAction(String username, String sessionName, int maxPlayers, String password) {
		super(username);
		this.sessionName = sessionName;
		this.maxPlayers = maxPlayers;
		this.isPassworded = true;
		this.password = password;
	}

	/**
	 * Create an object of this class to create a session <b>without</b> a password.
	 *
	 * @param username Username of the user creating the session.
	 * @param sessionName Name of the session.
	 * @param maxPlayers Maximum amount of users that is allowed to connect to this session.
	 */
	public CreateSessionAction(String username, String sessionName, int maxPlayers) {
		super(username);
		this.sessionName = sessionName;
		this.maxPlayers = maxPlayers;
		this.isPassworded = false;
	}

	/**
	 * Check if the session is passworded.
	 * @return True if session is passworded, false if it is not.
	 */
	public boolean isPassworded() {
		return isPassworded;
	}

	/**
	 * Get the name of the session.
	 * @return The session name.
	 */
	public String getSessionName() {
		return sessionName;
	}

	/**
	 * Get the maximum amount of connected users.
	 * @return The maximum amount of allowed connected users.
	 */
	public int getMaxPlayers() {
		return maxPlayers;
	}

	/**
	 * Get the password of the session.
	 * @return The password of the session.
	 */
	public String getPassword() {
		return password;
	}
}
