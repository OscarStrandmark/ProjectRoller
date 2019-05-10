package server;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;

import server.actions.ConnectedToServerAction;
import server.actions.SessionCreateAction;

import server.actions.SessionListRefreshAction;


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
		new ServerUI(this);
		new ConnectionAccepter();
	}

	/**
	 * Create a new session based of the information contained in the {@link SessionCreateAction}-object that is sent from a client to the server.
	 * @param action A {@link SessionCreateAction}-object sent from a client.
	 */
	public void createNewSession(SessionCreateAction action) {
		Session newSession;
		if(action.isPassworded()) {
			newSession = new Session(action.getPassword(), action.getSessionName(), action.getMaxPlayers(), this);
		} else {
			newSession = new Session(action.getSessionName(), action.getMaxPlayers(), this);
		}
		sessions.add(newSession);

	}

	/**
	 * Kills are removes a session. 
	 * 
	 * @param session The session to kill.
	 */
	public void killSession(Session session) {
		sessions.remove(session);
		
	}
	
	/**
	 * Force a client join the session specified.
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

	public ArrayList<String> getSessionsStrings() {

		ArrayList<String> list = new ArrayList<String>();
		
		for(Session s : sessions) {
			list.add(s.getSessionName());
		}
		
		return list;
	}
	
	public ArrayList<Session> getSessions(){
		return sessions;
	}
	
	public void joinLobby(Client c) {
		clientsInLobby.add(c);
	}
	
	/**
	 * Get the index of the parameter session.
	 * @param session The session whose index to find.
	 * @return The index of the session.
	 */
	public int getSessionIndex(Session session) {
		return sessions.indexOf(session);
	}
	
	public void refreshSessionList() {
		
		ArrayList<String> list = new ArrayList<String>();
		
		for(Session s : sessions) {
			String session = s.getSessionName() + ":" + s.getCurrentConnections() + ":" + s.getMaximumConnections();
			list.add(session);
		}
		
		for(Client c : clientsInLobby) {
			c.sendAction(new SessionListRefreshAction("SERVER", list));
		}
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
