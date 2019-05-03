package client;

import java.util.ArrayList;

import client.ui.LobbyWindow;
import client.ui.MainWindow;
import server.actions.Action;
import shared.BoardModel;

public class Controller {


	private LobbyWindow lobbyWindow;
	private Connection connection;
	private MainWindow window;
	public String username;
	private BoardModel boardModel;

	public Controller() {
		lobbyWindow = new LobbyWindow(this);
		window = new MainWindow(this);
		window.setVisible(false);
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
	
	public BoardModel getBoardModel()
	{
		return this.boardModel;
	}
}
