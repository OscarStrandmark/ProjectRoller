package server;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;

import server.actions.ConnectedToServerAction;
import server.actions.CreateSessionAction;

/**
 * Main connection class for the server. Handles all new incoming connections.
 *
 * @author Oscar Strandmark
 */
public class Connection {

    public static final int PORT = 48361; //Port the server will operate on.

	private ArrayList<Client> clientsInLobby;
	private ArrayList<Session> sessions;
	private Connection thisClass = this;

	public Connection() {
		clientsInLobby = new ArrayList<Client>();
		sessions = new ArrayList<Session>();

		new ConnectionAccepter();
	}

	/**
	 * Create a new session based of the information contained in the {@link CreateSessionAction}-object that is sent from a client to the server.
	 * @param action A {@link CreateSessionAction}-object sent from a client.
	 */
	public void createNewSession(CreateSessionAction action) {
		Session newSession;
		if(action.isPassworded()) {
			newSession = new Session(action.getPassword(), action.getSessionName(), action.getMaxPlayers(), this);
		} else {
			newSession = new Session(action.getSessionName(), action.getMaxPlayers(), this);
		}
		sessions.add(newSession);

	}

	/**
	 * Have a client join a session specified.
	 *
	 * @param sessionName The name of the session to join.
	 * @param client The client that will join
	 */
	public void joinSession(String sessionName, Client client) {
		for(Session s : sessions) {
			if(s.getSessionName().equals(sessionName)) {
				clientsInLobby.remove(client);
				s.join(client);
			}
		}
	}

	/**
	 * Get the index of the parameter session.
	 * @param session The session whose index to find.
	 * @return The index of the session.
	 */
	public int getSessionIndex(Session session) {
		return sessions.indexOf(session);
	}

	/**
	 * Nested class that handles all incoming connections and creates a new {@link Client} object for it and puts it into the client list.
	 *
	 * @author Oscar Strandmark
	 */
	private class ConnectionAccepter extends Thread {

		public ConnectionAccepter() {
			start();
		}

		public void run() {
			try(ServerSocket serverSocket = new ServerSocket(PORT)){ //Open the servers socket.
				while(true) {
					try {
						Socket socket = serverSocket.accept(); //Accept a connection.
						Client newClient = new Client(socket, thisClass); //Create a client for the incoming connection.
						clientsInLobby.add(newClient); //Add the Client object to the list containing all clients in the lobby.

						//Create a list for all session names.
						ArrayList<String> sessionNames = new ArrayList<String>();

						//Get all session names and put them in the list along with the current and maximum amount of connected users.
						for (int i = 0; i < sessions.size(); i++) {
							String sessionName = sessions.get(i).getSessionName() + sessions.get(i).getCurrentConnections() + sessions.get(i).getMaximumConnections();
							sessionNames.add(sessionName);
						}
						//Send an object to the client containing the list of all sessions.
						newClient.sendAction(new ConnectedToServerAction("SERVER", sessionNames));
					} catch (Exception e) {
						System.err.println("ERROR IN: CONNECTION.CONNECTIONACCEPTER: Accept connection.");
						e.printStackTrace();
					}
				}
			} catch (Exception e) {
				System.err.println("ERROR IN: CONNECTION.CONNECTIONACCEPTER: Create ServerSocket");
				e.printStackTrace();
			}
		}
	}
}
