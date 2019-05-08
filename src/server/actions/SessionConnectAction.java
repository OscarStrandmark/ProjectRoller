package server.actions;

/**
 * Class that is used in the object streams between server and client
 * When an object of this class is sent a client has made a request to connect to a session, named in the sessionID parameter.
 * @author Oscar Strandmark
 */
public class SessionConnectAction extends Action {

	private static final long serialVersionUID = -8725308533299212574L;

	private String sessionName;

	/**
	 * Constructor to create a 'Connect to session request'.
	 * This is sent from the client to the server.
	 * @param username - Username requesting to connect.
	 * @param sessionName - Name of the session.
	 */
	public SessionConnectAction(String username, String sessionName) {
		super(username);
		this.sessionName = sessionName;
	}

	public String getSessionID() {
		return sessionName;
	}
}
