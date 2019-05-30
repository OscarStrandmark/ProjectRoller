package server.actions;
/**
 * Action that represents the act of leaving a session.
 * 
 * @author Oscar Strandmark
 */
public class SessionLeaveAction extends Action {

	private static final long serialVersionUID = -587175501789673351L;

	public SessionLeaveAction(String username) {
		super(username);
	}

}
