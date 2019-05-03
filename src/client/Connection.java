package client;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;


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


					//Updates the sessions in the sessionlist
					if(action instanceof SessionListRefreshAction) {
						SessionListRefreshAction act = (SessionListRefreshAction)action;
						
						controller.updateSessionList(act.getSessionList());
						
					}
					
					else
					
					if(action instanceof JoinedAction) {
						controller.sessionEntered();
					}
					
					else
						
					if(action instanceof CreateSessionAction)
					{
						//TODO tills lobbyWindow är klar
					}
					
					else
						
					if(action instanceof MoveIconAction)
					{
					
						MoveIconAction act = (MoveIconAction)action;
						
						controller.getBoardModel().getIcons().get(act.getCharacterIconIndex()).setPosition(act.getX(), act.getY());
					}
					
					else
						
					if(action instanceof CreateIconAction)
					{
						CreateIconAction act = (CreateIconAction)action;
						
						controller.getBoardModel().addIcon(act.getIcon());
					}
					
					else
						
					if(action instanceof RemoveIconAction)
					{
						RemoveIconAction act = (RemoveIconAction)action;
						
						controller.getBoardModel().removeIcon(act.getIcon());
					}
					
					else 
						
					if(action instanceof CreateIconValueAction)
					{
						CreateIconValueAction act = (CreateIconValueAction)action;
						
						controller.getBoardModel().getIcons().get(act.getIconIndex()).addValue(act.getValue());
								
					}
					
					else
						
					if(action instanceof RemoveIconValueAction)
					{
						RemoveIconValueAction act = (RemoveIconValueAction)action;
						
						controller.getBoardModel().getIcons().get(act.getIconIndex()).removeValue(act.getValueIndex());
					}
						
					else
						
					if(action instanceof ChangeIconValueAction)
					{
						ChangeIconValueAction act = (ChangeIconValueAction)action;
						
						controller.getBoardModel().getIcons().get(act.getIconIndex()).changeValue(act.getValue(), act.getValueIndex());
					}
					
					else 
					
					if(action instanceof BackgroundChangeAction)
					{
						BackgroundChangeAction act = (BackgroundChangeAction)action;
						
						controller.getBoardModel().setBackground(act.getImage());
					}
					
					else
						
					if(action instanceof RefreshAction)
					{
						//TODO
					}
					
					else
						
					if(action instanceof ConnectToSessionAction)
					{
						//TODO
					}
					
					else
						
					if(action instanceof ConnectToSessionAction)
					{
						//TODO
					}
					
					//else

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
}
