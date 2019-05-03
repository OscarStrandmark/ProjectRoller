package client;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ConnectException;
import java.net.Socket;

import javax.swing.JOptionPane;

import server.actions.*;

import shared.Buffer;

public class Connection {

    private static final int PORT = 48361; //Port the server will operate on.
    public static final String ADDRESS = "localhost"; //Address the server will operate on.

    private Controller controller;
	private Socket socket;
	private Sender sender;

	public Connection(Controller controller) {
		this.controller = controller; 

		try {
			socket = new Socket(ADDRESS,PORT);
			sender = new Sender();
					 new Reciever();
		} catch (ConnectException ce) {
			int input = JOptionPane.showConfirmDialog(null, "Server not found", "ERROR", JOptionPane.OK_CANCEL_OPTION, JOptionPane.ERROR_MESSAGE);
			
			controller.disposeAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void send(Action action ) {
		sender.send(action);
	}

	private class Sender extends Thread {

		private Buffer<Action> buffer;

		private ObjectOutputStream oos;

		public Sender() {
			buffer = new Buffer<Action>();
			try {
				oos = new ObjectOutputStream(socket.getOutputStream());
			} catch (Exception e) {
				e.printStackTrace();
			}
			start();
		}

		public void send(Action action) {
			buffer.put(action);
		}

		public void run() {
			while(true) {
				try {
					Action act = buffer.get();
					oos.writeObject(act);
					oos.flush();
					oos.reset();

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	private class Reciever extends Thread {

		private ObjectInputStream ois;

		public Reciever() {
			try {
				ois = new ObjectInputStream(socket.getInputStream());
			} catch (Exception e) {
				e.printStackTrace();
			}
			start();
		}

		public void run() {
			while(true) {
				try {
					Action action = (Action) ois.readObject();


					if(action instanceof SessionListRefreshAction) {
						SessionListRefreshAction act = (SessionListRefreshAction)action;
						
						controller.updateSessionList(act.getSessionList());
						
					}
					
					else
					
					if(action instanceof JoinedAction) {
						controller.sessionEntered();
					}
					
					else

					if(action instanceof RequestPasswordAction) {
						RequestPasswordAction act = (RequestPasswordAction) action;
						String sessionName = act.getSessionName();
						String password = controller.requestPassword();
						controller.pushActionToServer(new CheckPasswordAction(controller.username, sessionName, password));
						
					}
					
					else 
						
					if(action instanceof WrongPasswordAction) {
						JOptionPane.showMessageDialog(null, "Password did not match", "Password mismatch", JOptionPane.INFORMATION_MESSAGE);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
}
