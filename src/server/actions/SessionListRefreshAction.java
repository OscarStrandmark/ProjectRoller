package server.actions;

import java.util.ArrayList;
/**
 * Action that represents the list sent to the client upon a refresh request.
 * 
 * @author Oscar Strandmark
 */
public class SessionListRefreshAction extends Action {

	private static final long serialVersionUID = -8798234840940738022L;
	private ArrayList<String> sessionStrings;
	
	public SessionListRefreshAction(String username, ArrayList<String> sessionStrings) {
		super(username);
		this.sessionStrings = sessionStrings;
	}
	
	public ArrayList<String> getSessionList(){
		return sessionStrings;
	}

}
