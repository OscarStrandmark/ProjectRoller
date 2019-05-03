package server.actions;

import java.util.ArrayList;

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
