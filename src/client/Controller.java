package client;

import java.util.ArrayList;

import javax.swing.JOptionPane;

import client.ui.LobbyWindow;
import client.ui.MainWindow;
import server.actions.Action;
import shared.BoardModel;

public class Controller {


	private LobbyWindow lobbyWindow;
	private Connection connection;
	private MainWindow mainWindow;
	public String username;
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
	
	public BoardModel getBoardModel()
	{
		return this.boardModel;
	}
}
