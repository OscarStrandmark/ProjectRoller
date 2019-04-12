package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import server.actions.Action;
import server.actions.ConnectToSessionAction;
import server.actions.CreateSessionAction;
import shared.Buffer;

public class Client {

	private Socket socket;
	private Sender sender;
	private Reciever reciever;

	private String username; //Username of the user, can be changed in the settings menu.
	private Client thisClass = this;

	private boolean inLobby = true;
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
						connection.joinSession(createAction.getSessionName(), thisClass);
					}

					else

					if(action instanceof ConnectToSessionAction) { //User sent request to connect to a session.
						ConnectToSessionAction connectAction = (ConnectToSessionAction) action;
						connection.joinSession(connectAction.getSessionID(), thisClass);
					}

					//TODO: Implement what to do when recieving an action.
				} catch (Exception e) {
					System.err.println("ERROR IN: CLIENT.RECIEVER");
					e.printStackTrace();
				}
			}
		}
	}
}
