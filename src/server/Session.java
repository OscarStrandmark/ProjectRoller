package server;

import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.ImageIcon;

import server.actions.ChatDisplayTextAction;
import server.actions.JoinedAction;
import server.actions.SynchAction;
import shared.BoardModel;
import shared.CharacterIcon;

 public class Session {

 	private boolean isPassworded;
	private String password;
	private String sessionName;
	private int maxPlayers;
	private int currentPlayers;
	private ArrayList<Client> connectedClients;
	private Connection connection;
	private BoardModel model;

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
		model = new BoardModel(this);
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
		if(this.password.equals(password)) {
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
		client.sendAction(new JoinedAction("SERVERMESSAGE",sessionName));
	}

 	/**
	 * Called by a client when it wants to leave the server. If it is the last client on the session, the session is deleted
	 * @param client The client that is leaving.
	 */
	public void leave(Client client) {
		if(connectedClients.size() == 1) {
			connectedClients.remove(client);
			connection.killSession(this);
		} else {
			connectedClients.remove(client);
		}
	}

	public void pushChatText(String text) {
		for(Client c : connectedClients) {
			c.sendAction(new ChatDisplayTextAction("SERVER", text));
		}
	}
	
	public void sendWhisper(String sender ,String receiver ,String text) {
		for(Client c : connectedClients) {
			if(c.getUsername().equals(receiver)) {
				text = "Whisper from " + sender + ": " + text;
				c.sendAction(new ChatDisplayTextAction(sender, text));
			}
		}
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
	
	public synchronized void synchBoard(HashMap<ImageIcon,CharacterIcon> map, ImageIcon background) {
		model.synchServer(map, background);
		for(Client c : connectedClients) {
			System.out.println("Sent synch from server to " + c.getUsername());
			c.sendAction(new SynchAction("SERVER", map, background));
		}
	}
}
