package client;

import java.util.ArrayList;

import client.ui.LobbyWindow;
import client.ui.MainWindow;
import server.actions.Action;

public class Controller {


	private LobbyWindow lobbyWindow;
	private Connection connection;
	private MainWindow window;
	public String username;

	public Controller() {
		lobbyWindow = new LobbyWindow(this);
		window = new MainWindow(this);
		window.setVisible(false);
		connection = new Connection(this);
	}

	public void pushActionToServer(Action act) {
		connection.send(act);
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void updateSessionList(ArrayList<String> sessionList) {
		lobbyWindow.updateSessionList(sessionList);
	}
	
	public void sessionEntered() {
		lobbyWindow.setVisible(true);
		window.setVisible(true);
	}

	public void pushActionToServre(Action act) {
		//TODO: add action-handling.
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUsername() {
		return username;
	}
}
