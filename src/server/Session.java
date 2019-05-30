package server;

import java.util.ArrayList;

import javax.swing.ImageIcon;

import server.actions.BoardBackgroundChangeAction;
import server.actions.BoardIconCreateAction;
import server.actions.BoardIconMoveAction;
import server.actions.BoardIconRemoveAction;
import server.actions.BoardIconValueUpdateAction;
import server.actions.BoardResyncAction;
import server.actions.ChatDisplayTextAction;
import server.actions.JoinedAction;
import shared.Value;
/**
 * Class that represents a session on the server. Whenever a client creates a new session, 
 * a corresponding session-object is created on the server to handle synchronization of board and chat.
 * 
 * @author Oscar Strandmark
 * @author Patrik Skuza
 */
public class Session {

	private boolean isPassworded;
	private String password;
	private String sessionName;
	private int maxPlayers;
	private ArrayList<Client> connectedClients;
	private Connection connection;
	private ServerBoardModel model;
	private Log LOGGER;

	/**
	 * Constructor to use when creating a session without a password.
	 *
	 * @param sessionName Name of the session.
	 * @param maxPlayers  Maximum amount of allowed players in the session.
	 */
	public Session(String sessionName, int maxPlayers, Connection connection) {
		this.isPassworded = false;
		this.sessionName = sessionName;
		this.maxPlayers = maxPlayers;
		this.connection = connection;
		connectedClients = new ArrayList<Client>();
		model = new ServerBoardModel();
		this.LOGGER = new Log(sessionName + ".txt");
	}

	/**
	 * Constructor to use when creating a session <b>with</b> a password.
	 *
	 * @param password    The password for the session.
	 * @param sessionName Name of the session.
	 * @param maxPlayers  Maximum amount of allowed players in the session.
	 */
	public Session(String password, String sessionName, int maxPlayers, Connection connection) {
		this.isPassworded = true;
		this.password = password;
		this.sessionName = sessionName;
		this.maxPlayers = maxPlayers;
		this.connection = connection;
		connectedClients = new ArrayList<Client>();
		this.LOGGER = new Log(sessionName + ".txt");
	}

	/**
	 * Check if the session is passworded.
	 * 
	 * @return True if session is passworded, false if it isn't.
	 */
	public boolean isPassworded() {
		return isPassworded;
	}

	/**
	 * Check if entered password is the same as the password stored in the session.
	 * 
	 * @param password
	 * @return True if password is correct, false if it isn't.
	 */
	public boolean checkPassword(String password) {
		if (this.password.equals(password)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Get the name of the session.
	 * 
	 * @return The name of the session.
	 */
	public String getSessionName() {
		return sessionName;
	}

	/**
	 * Called by a client when it wants to join the server.
	 * 
	 * @param client The client that is joining.
	 */
	public void join(Client client) {
		connectedClients.add(client);
		client.sendAction(new JoinedAction("SERVERMESSAGE", sessionName));
		LOGGER.logMessage("[" + client.getUsername() + "] " + "has joined the session.");
	}

	/**
	 * Called by a client when it wants to leave the server. If it is the last
	 * client on the session, the session is deleted
	 * 
	 * @param client The client that is leaving.
	 */
	public void leave(Client client) {
		if (connectedClients.size() == 1) {
			connectedClients.remove(client);
			connection.killSession(this);
		} else {
			connectedClients.remove(client);
		}
	}

	public void pushChatText(String text) {
		for (Client c : connectedClients) {
			c.sendAction(new ChatDisplayTextAction("SERVER", text));
		}
		LOGGER.logMessage(text);
	}

	public void sendWhisper(String sender, String receiver, String text) {
		for (Client c : connectedClients) {
			if (c.getUsername().equals(receiver)) {
				text = "Whisper from " + sender + ": " + text;
				c.sendAction(new ChatDisplayTextAction(sender, text));
				LOGGER.logMessage(text);
			}
		}
	}

	/**
	 * Get the maximum amount of allowed connections.
	 * 
	 * @return The amount.
	 */
	public int getMaximumConnections() {
		return maxPlayers;
	}

	/**
	 * Get the current amount of connections to the session.
	 * 
	 * @return The amount of sessions.
	 */
	public int getCurrentConnections() {
		return connectedClients.size();
	}

	public void setBackground(ImageIcon img) {
		model.setBackground(img);
		for (Client c : connectedClients) {
			c.sendAction(new BoardBackgroundChangeAction("SERVER", img));
		}
		LOGGER.logMessage("New background has been set: " + img.toString());
	}

	public void createIcon(ImageIcon img) {
		model.addIcon(img);
		for (Client c : connectedClients) {
			c.sendAction(new BoardIconCreateAction("SERVER", img));
		}
		LOGGER.logMessage("New icon has been created: " + img.toString());
	}

	public void moveIcon(int index, int x, int y) {
		model.moveIcon(index, x, y);
		for (Client c : connectedClients) {
			c.sendAction(new BoardIconMoveAction("SERVER", index, x, y));
		}
		LOGGER.logMessage("Icon : " + index + " was moved to: " + "X: " + x + " Y:" + y);
	}

	public void removeIcon(int index) {
		model.removeIcon(index);
		for (Client c : connectedClients) {
			c.sendAction(new BoardIconRemoveAction("SERVER", index));
		}
		LOGGER.logMessage("Icon : " + index + " has been removed.");
	}

	public void updateValue(int index, ArrayList<Value> list) {
		model.setValue(index, list);
		for (Client c : connectedClients) {
			c.sendAction(new BoardIconValueUpdateAction("SERVER", index, list));
		}
		LOGGER.logMessage("Value : " + index + " has been updated. " + "TEST - List: " + list.toString());
	}

	public BoardResyncAction getSyncAction() {
		return new BoardResyncAction("SERVER", model.getBackground(), model.getChars(), model.getIcons());
	}
}
