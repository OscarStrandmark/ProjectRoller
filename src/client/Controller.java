package client;

import client.ui.MainWindow;

public class Controller {
	
	private MainWindow window;
	
	public Controller() {
		window = new MainWindow(this);
	}

	public void newChatMessage(String text) {
		System.out.println("Chatmessage entered: " + text);
	}
}
