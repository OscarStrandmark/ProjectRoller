package server.actions;

import java.util.ArrayList;

/**
 * Class that is used in the object streams between server and client.
 * An object of this class is sent when a client first connects to the server so that it recieves all sessions in the session list.
 * @author Oscar Strandmark
 */
public class ConnectedToServerAction extends Action {

	private static final long serialVersionUID = 6856725180165279994L;

	private ArrayList<String> sessions;

	public ConnectedToServerAction(String username, ArrayList<String> sessions) {
		super(username);
		this.sessions = sessions;
	}

	public ArrayList<String> getSessionList() {
		return sessions;
	}

}
