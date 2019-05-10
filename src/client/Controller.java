package client;

import java.util.ArrayList;

import javax.swing.JOptionPane;

import client.ui.LobbyWindow;
import client.ui.MainWindow;
import server.actions.Action;
import server.actions.ChatMessageAction;
import server.actions.ChatRPAction;
import server.actions.ChatWhisperAction;
import server.actions.DiceRollAction;
import server.actions.DiceRollHiddenAction;
import shared.BoardModel;
import shared.Dice;
import shared.Diceroll;

public class Controller {


	private LobbyWindow lobbyWindow;
	private Connection connection;
	private MainWindow mainWindow;
	public String username = "NewClient";
	private BoardModel boardModel;

	public Controller() {
		boardModel = new BoardModel(this);
		connection = new Connection(this);
		lobbyWindow = new LobbyWindow(this);
		mainWindow = new MainWindow(this,boardModel);
		mainWindow.setVisible(false);
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
		if(lobbyWindow != null) {
			lobbyWindow.dispose();
		}
		if(mainWindow != null) {
			mainWindow.dispose();
		}
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
			
			try {
				for(String r : arr) {
					System.out.print(s);
				}
				System.out.println();
				Diceroll roll = new Diceroll(Integer.parseInt(arr[arr.length-1]));
				
				for (int i = 0; i < arr.length; i++) {
					if(arr[i].contains("d")) {
						String[] diceString = arr[i].split("d"); //index 0 = amount of dice, index 1 = dice sides.
						for (int j = 0; j < Integer.parseInt(diceString[0]); j++) {
							roll.addDice(new Dice(Integer.parseInt(diceString[1])));
						}
					}
				}
				
				pushActionToServer(new DiceRollAction(username, roll));
			} catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(mainWindow, "Error in /roll syntax", "Syntax error!", JOptionPane.ERROR_MESSAGE);
			}
		}
		
		else 
			
		if(arr[0].equals("/dmroll")) {
			try {
				for(String r : arr) {
					System.out.print(s);
				}
				System.out.println();
				Diceroll roll = new Diceroll(Integer.parseInt(arr[arr.length-1]));
				
				for (int i = 0; i < arr.length; i++) {
					if(arr[i].contains("d")) {
						String[] diceString = arr[i].split("d"); //index 0 = amount of dice, index 1 = dice sides.
						for (int j = 0; j < Integer.parseInt(diceString[0]); j++) {
							roll.addDice(new Dice(Integer.parseInt(diceString[1])));
						}
					}
				}
				
				pushActionToServer(new DiceRollHiddenAction(username, roll));
			} catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(mainWindow, "Error in /dmroll syntax", "Syntax error!", JOptionPane.ERROR_MESSAGE);
			}
		}
			
		else
			
		if(arr[0].equals("/w")) { //Whisper msg
			String content = "";
			String receiver = arr[1];
			
			for (int i = 2; i < arr.length; i++) {
				content += arr[i] + " ";
			}
			
			pushActionToServer(new ChatWhisperAction(username, receiver, content));
		}
		
		else 
			
		if(arr[0].equals("/rp")) { //RP-message
			String content = "";
			String rpName = arr[1];
			
			for (int i = 2; i < arr.length; i++) {
				content += arr[i] + " ";
			}
			
			pushActionToServer(new ChatRPAction(username, rpName, content));
		}
		
		else if(s.length() != 0){ //Normal message
			pushActionToServer(new ChatMessageAction(username, s));
		}
	}
	
	public BoardModel getBoardModel() {
		return this.boardModel;
	}
}
