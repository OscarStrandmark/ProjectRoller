package client;

import java.util.ArrayList;

import javax.swing.JOptionPane;

import client.ui.LobbyWindow;
import client.ui.MainWindow;
import server.actions.Action;
import server.actions.ChatMessageAction;
import shared.BoardModel;

public class Controller {


	private LobbyWindow lobbyWindow;
	private Connection connection;
	private MainWindow mainWindow;
	public String username = "NewClient";
	private BoardModel boardModel;

	public Controller() {
		lobbyWindow = new LobbyWindow(this);
		mainWindow = new MainWindow(this);
		mainWindow.setVisible(false);
		connection = new Connection(this);
		this.boardModel = new BoardModel(this);
	}

	public void pushActionToServer(Action act) {
		connection.send(act);
	}

	public void updateSessionList(ArrayList<String> sessionList) {
		lobbyWindow.updateSessionList(sessionList);
	}
	
	public void sessionEntered() {
		lobbyWindow.setVisible(false);
		mainWindow.setVisible(true);
	}
	
	public void sessionLeft() {
		lobbyWindow.setVisible(true);
		mainWindow.setVisible(false);
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String requestPassword() {
		String s = JOptionPane.showInputDialog("Enter password for session");
		return s;
	}
	
	public void disposeAll() {
		lobbyWindow.dispose();
		mainWindow.dispose();
	}
	
	public String getUsername() {
		return username;
	}
	
	public void appendChatLine(String line) {
		mainWindow.appendChatLine(line);
	}
	
	/**
	 * Method that handles all text written in the chat box and sends the appropiate action to the server. 
	 * 
	 * @param s
	 */
	public void handleMessage(String s) {
		String[] arr = s.split(" ");
		
		if(arr[0].equals("/roll")) { //Roll command
			//TODO
		}
		
		else 
			
		if(arr[0].equals("/w")) { //Whisper msg
			//TODO
		}
		
		else 
			
		if(arr[0].equals("/rp")) { //RP-message
			//TODO
		}
		
		else { //Normal message
			pushActionToServer(new ChatMessageAction(username, s));
		}
	}
	
	public BoardModel getBoardModel() {
		return this.boardModel;
	}
}
