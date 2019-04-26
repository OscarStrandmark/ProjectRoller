package client;

import client.ui.MainWindow;
import server.actions.Action;

public class Controller {

	private MainWindow window;
	private String username;

	public Controller() {
		window = new MainWindow(this);
	}

	public void newChatMessage(String text) {
		System.out.println("Chatmessage entered: " + text);
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
