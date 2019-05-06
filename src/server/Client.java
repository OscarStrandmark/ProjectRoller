package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;

import server.actions.Action;
import server.actions.CheckPasswordAction;
import server.actions.CreateSessionAction;
import server.actions.JoinSessionRequestAction;
import server.actions.JoinedAction;
import server.actions.RefreshAction;
import server.actions.RequestPasswordAction;
import server.actions.WrongPasswordAction;
import shared.Buffer;

public class Client {

	private Socket socket;
	private Sender sender;
	private Reciever reciever;

	private String username; //Username of the user, can be changed in the settings menu.
	private Client thisClient = this;

	private Connection connection;
	private Session session; //The session the client is connected to. If null, client is in the lobby.

	public Client(Socket socket, Connection connection) {
		this.socket = socket;
		this.username = "New client";
		this.connection = connection;
		try {
			sender = new Sender();
			reciever = new Reciever();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getUsername() {
		return username;
	}

	public void setSession(Session session) {
		this.session = session;
	}

	public Session getSession() {
		return session;
	}

	public void sendAction(Action act) {
		sender.send(act);
	}

	private class Sender extends Thread {

		private ObjectOutputStream oos;

		private Buffer<Action> buffer;

		public Sender() throws IOException {
			oos = new ObjectOutputStream(socket.getOutputStream());
			buffer = new Buffer<Action>();
			start();
		}

		public void send(Action obj) {
			buffer.put(obj);
		}

		public void run() {
			while(true) {
				try {
					Action act = buffer.get();
					oos.writeObject(act);
					oos.flush();
				} catch (Exception e) {
					System.err.println("ERROR IN: CLIENT.SENDER");
					e.printStackTrace();
				}
			}
		}
	}

	private class Reciever extends Thread {

		private ObjectInputStream ois;

		public Reciever() throws IOException {
			ois = new ObjectInputStream(socket.getInputStream());
			start();
		}

		public void run() {
			while(true) {
				try {
					Action action = (Action)ois.readObject();

					if(action instanceof CreateSessionAction) { //User sent request to create a new session.
						CreateSessionAction createAction = (CreateSessionAction) action;
						connection.createNewSession(createAction);
						connection.joinSession(createAction.getSessionName(), thisClient);

						
						sender.send(new JoinedAction("SERVER", createAction.getSessionName()));

					}

					else

					if(action instanceof JoinSessionRequestAction) { //User sent request to connect to a session.
						JoinSessionRequestAction act = (JoinSessionRequestAction) action;
						String sessionName = act.getSessionName();
						ArrayList<Session> sessions = connection.getSessions();
						
						for(Session s : sessions) {
							if(s.getSessionName().equals(sessionName)) {
								if(s.isPassworded()) {
									sendAction(new RequestPasswordAction("SERVER",sessionName));
								} else {
									connection.joinSession(sessionName, thisClient);
									sendAction(new JoinedAction("SERVER", sessionName));
								}
							}
						}
					}


					else
						
					if(action instanceof RefreshAction	) { //User requested to refresh list of sessions.

						connection.refreshSessionList();
						
					}
					
					else
						
					if(action instanceof CheckPasswordAction) { //User requested to join a session that was passworded, check if password matches, then join session.
						CheckPasswordAction act = (CheckPasswordAction) action;
						String sessionName = act.getSessionName();
						String password =  act.getPassword();
						ArrayList<Session> sessions = connection.getSessions();
						
						for(Session s : sessions) {
							if(s.getSessionName().equals(sessionName)) {
								if(s.checkPassword(password)) { //Password ok
									connection.joinSession(sessionName, thisClient);
								} else { //Password not ok.
									sendAction(new WrongPasswordAction("SERVER"));
								}
							}
						}
						
					}
					//TODO: Implement what to do when recieving an action.
				} catch (SocketException se) {
					try { socket.close(); } catch (IOException e1) {}		
				} catch (Exception e) {
					System.err.println("ERROR IN: CLIENT.RECIEVER");
					

					e.printStackTrace();
				}
			}
		}
	}
}
