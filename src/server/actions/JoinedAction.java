package server.actions;

/**
 * Class that is used in the object streams between server and client.
 * When an object of this class is sent a user is joining named session.
 * @author Oscar Strandmark
 */
public class JoinedAction extends Action {

	private static final long serialVersionUID = 4093545819968327857L;
	private int sessionIndex;

	/**
	 * Create an object of this class.
	 * @param username Username of sender.
	 * @param sessionIndex Session index of the session to join.
	 */
	public JoinedAction(String username, int sessionIndex) {
		super(username);
		this.sessionIndex = sessionIndex;
	}

	public int getSessionIndex() {
		return sessionIndex;
	}
}
