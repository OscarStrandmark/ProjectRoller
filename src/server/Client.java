package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;

import server.actions.Action;
import server.actions.BoardBackgroundChangeAction;
import server.actions.BoardIconCreateAction;
import server.actions.BoardIconMoveAction;
import server.actions.BoardIconRemoveAction;
import server.actions.BoardIconValueUpdateAction;
import server.actions.ChatDisplayTextAction;
import server.actions.ChatMessageAction;
import server.actions.ChatRPAction;
import server.actions.ChatWhisperAction;
import server.actions.CheckPasswordAction;
import server.actions.DiceRollAction;
import server.actions.DiceRollHiddenAction;
import server.actions.JoinedAction;
import server.actions.QuitAction;
import server.actions.RefreshAction;
import server.actions.RequestPasswordAction;
import server.actions.SessionCreateAction;
import server.actions.SessionJoinRequestAction;
import server.actions.SessionLeaveAction;
import server.actions.SynchAction;
import server.actions.UsernameChangeAction;
import server.actions.WrongPasswordAction;
import shared.Buffer;
import shared.Diceroll;

public class Client {

	private Socket socket;
	
	private Sender sender;
	private Reciever reciever;

	private String username; //Username of the user, can be changed in the settings menu.
	private Client thisClient = this;

	private Connection connection;
	private Session session; //The session the client is connected to. If null, client is in the lobby.

	private volatile boolean alive = true;
	
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
	
	private void kill() {
		alive = false;
		sender = null;
		reciever = null;
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
			while(alive) {
				try {
					Action act = buffer.get();
					oos.writeObject(act);
					oos.flush();
					oos.reset();
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
			while(alive) {
				try {
					Action action = (Action)ois.readObject();

					if(action instanceof SessionCreateAction) { //User sent request to create a new session.
						SessionCreateAction createAction = (SessionCreateAction) action;
						connection.createNewSession(createAction);
						connection.joinSession(createAction.getSessionName(), thisClient);
						
						ArrayList<Session> sessions = connection.getSessions();
						
						for(Session s : sessions) {
							if(s.getSessionName() == createAction.getSessionName()) {
								setSession(s);
							}
						}
						
						sender.send(new JoinedAction("SERVER", createAction.getSessionName()));

					}

					else

					if(action instanceof SessionJoinRequestAction) { //User sent request to connect to a session.
						SessionJoinRequestAction act = (SessionJoinRequestAction) action;
						String sessionName = act.getSessionName();
						ArrayList<Session> sessions = connection.getSessions();
						
						for(Session s : sessions) {
							System.out.println(s.getSessionName());
							if(s.getSessionName().equals(sessionName)) {
								if(s.isPassworded()) {
									sendAction(new RequestPasswordAction("SERVER",sessionName));
								} else {
									connection.joinSession(sessionName, thisClient);
									setSession(s);
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
									setSession(s);
								} else { //Password not ok.
									sendAction(new WrongPasswordAction("SERVER"));
								}
							}
						}
						
					}
					
					else 
						
					if(action instanceof SessionLeaveAction) {
						session.pushChatText("USER LEFT: " + action.getUsername());
						session.leave(thisClient);
						connection.joinLobby(thisClient);
						setSession(null);
					}
					
					else
						
					if(action instanceof ChatMessageAction) {
						ChatMessageAction act = (ChatMessageAction) action;
						String s = act.getUsername() + ": " + act.getMessage();
						session.pushChatText(s);
					}
					
					else
						
					if(action instanceof UsernameChangeAction) {
						UsernameChangeAction act = (UsernameChangeAction) action;
						username = act.getNewUsername();
						session.pushChatText("NAME CHANGED: " + act.getUsername() + " -> " + act.getNewUsername());
					}
					
					else
						
					if(action instanceof QuitAction) {
						System.out.println("RECIEVED QUIT");
						session.leave(thisClient);
						kill();
						session.pushChatText("USER LEFT: " + action.getUsername());
					}
					
					else
						
					if(action instanceof DiceRollAction) {
						DiceRollAction act = (DiceRollAction) action;
						
						Diceroll dr = act.getDiceroll();
						
						String diceString = dr.getDiceString();
						
						int result = dr.roll();
						String printString = "User: " + act.getUsername() + " rolled dice: " + diceString + dr.getConstant() + " = " + result;
						session.pushChatText(printString);
						
					}
					
					else 
					
					if(action instanceof DiceRollHiddenAction) {
						DiceRollHiddenAction act = (DiceRollHiddenAction) action;
						
						Diceroll dr = act.getDiceroll();
						
						String diceString = dr.getDiceString();
						
						int result = dr.roll();
						String printString = "Hidden diceroll: " + diceString + dr.getConstant() + " = " + result;
						
						thisClient.sendAction(new ChatDisplayTextAction("SERVER", printString));
					}
					
					else
						
					if(action instanceof ChatWhisperAction) {
						ChatWhisperAction act = (ChatWhisperAction) action;
						session.sendWhisper(act.getUsername(), act.getReciever(), act.getContent());
					}
					
					else
						
					if(action instanceof ChatRPAction) {
						ChatRPAction act = (ChatRPAction) action;
						String contentString = "RP: " + act.getName() + ": " + act.getContent();
						session.pushChatText(contentString);
					}
					
					else
						
					if(action instanceof BoardBackgroundChangeAction) {
						BoardBackgroundChangeAction act = (BoardBackgroundChangeAction) action;
						session.setBackground(act.getImage());
					}
					
					else 
						
					if(action instanceof BoardIconCreateAction) {
						BoardIconCreateAction act = (BoardIconCreateAction) action;
						session.createIcon(act.getImage());
					}
					
					else
						
					if(action instanceof BoardIconMoveAction) {
						BoardIconMoveAction act = (BoardIconMoveAction) action;
						session.moveIcon(act.getIndex(), act.getX(), act.getY());
					}
					
					else
						
					if(action instanceof BoardIconRemoveAction) {
						BoardIconRemoveAction act = (BoardIconRemoveAction) action;
						session.removeIcon(act.getIndex());
					}
					
					else
						
					if(action instanceof BoardIconValueUpdateAction) {
						BoardIconValueUpdateAction act = (BoardIconValueUpdateAction) action;
						session.updateValue(act.getIndex(), act.getList());
					}
					
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
