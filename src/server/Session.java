package server;

 import java.util.ArrayList;

 import server.actions.JoinedAction;
import server.actions.SynchAction;

 public class Session {

 	private boolean isPassworded;
	private String password;
	private String sessionName;
	private int maxPlayers;
	private int currentPlayers;
	private ArrayList<Client> connectedClients;
	private Connection connection;
	//private BoardModel model;
	//TODO: Complete model

 	/**
	 * Constructor to use when creating a session without a password.
	 *
	 * @param sessionName Name of the session.
	 * @param maxPlayers Maximum amount of allowed players in the session.
	 */
	public Session(String sessionName, int maxPlayers, Connection connection) {
		this.isPassworded = false;
		this.sessionName = sessionName;
		this.maxPlayers = maxPlayers;
		this.connection = connection;
		connectedClients = new ArrayList<Client>();
	}

 	/**
	 * Constructor to use when creating a session <b>with</b> a password.
	 *
	 * @param password The password for the session.
	 * @param sessionName Name of the session.
	 * @param maxPlayers Maximum amount of allowed players in the session.
	 */
	public Session(String password, String sessionName, int maxPlayers, Connection connection) {
		this.isPassworded = true;
		this.password = password;
		this.sessionName = sessionName;
		this.maxPlayers = maxPlayers;
		this.connection = connection;
		connectedClients = new ArrayList<Client>();
	}

 	/**
	 * Check if the session is passworded.
	 * @return True if session is passworded, false if it isn't.
	 */
	public boolean isPassworded() {
		return isPassworded;
	}

 	/**
	 * Check if entered password is the same as the password stored in the session.
	 * @param password
	 * @return True if password is correct, false if it isn't.
	 */
	public boolean checkPassword(String password) { //TODO: Hash & salt, handle password correctly?
		if(password == this.password) {
			return true;
		} else {
			return false;
		}
	}

 	/**
	 * Get the name of the session.
	 * @return The name of the session.
	 */
	public String getSessionName() {
		return sessionName;
	}

 	/**
	 * Called by a client when it wants to join the server.
	 * @param client The client that is joining.
	 */
	public void join(Client client) {
		connectedClients.add(client);
		int index = connection.getSessionIndex(this);
		client.sendAction(new JoinedAction("SERVERMESSAGE",index));
	}

 	/**
	 * Called by a client when it wants to leave the server.
	 * @param client The client that is leaving.
	 */
	public void leave(Client client) {
		connectedClients.remove(client);
	}

 	/**
	 * Get the maximum amount of allowed connections.
	 * @return The amount.
	 */
	public int getMaximumConnections() {
		return maxPlayers;
	}

 	/**
	 * Get the current amount of connections to the session.
	 * @return The amount of sessions.
	 */
	public int getCurrentConnections() {
		return currentPlayers;
	}

	/**
	 * Notifies all connected players that there has been a change in the BoardModel.
	 */
	public void notifyPlayersForSynch() {
		for(Client c : connectedClients) {
			c.sendAction(new SynchAction("SERVER"));
		}
	}
}
