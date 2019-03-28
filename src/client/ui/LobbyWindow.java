package client.ui;

import javax.swing.JFrame;

import client.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LobbyWindow extends JFrame
{


	private JSplitPane pnlLobby;
	private JPanel pnlLobbyCreate;
	
	
	private JButton joinButton;
	private JButton createButton;
	
	private JList<String> serverList;
	private DefaultListModel<String> listModel;
	
	
	public LobbyWindow()
	{
		init();
		
	}
	
	public void init()
	{
		setTitle("Project Roller");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		setVisible(true);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		//this.setResizable(false);
		

		this.pnlLobby = new JSplitPane();
				
		this.pnlLobbyCreate = new JPanel();

		this.joinButton = new JButton("Join Session");
		this.createButton = new JButton("Create Session");
		this.joinButton.addActionListener(new ButtonListener());
		this.createButton.addActionListener(new ButtonListener());

		
		this.pnlLobbyCreate.setLayout(new BoxLayout(this.pnlLobbyCreate, BoxLayout.PAGE_AXIS));
		this.pnlLobbyCreate.add(this.joinButton);
		this.pnlLobbyCreate.add(this.createButton);


		this.listModel = new DefaultListModel<String>();
		this.listModel.addElement("Server1");
		this.listModel.addElement("Server2");
		this.listModel.addElement("Server3");
		this.listModel.addElement("Server4");
		this.listModel.addElement("Server5");
		
		this.serverList = new JList<String>(listModel);

		
		
		
		
		
		
		
		this.pnlLobby.setLeftComponent(this.serverList);
		this.pnlLobby.setRightComponent(this.pnlLobbyCreate);
		this.pnlLobby.setEnabled(false);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int width = (int) (screenSize.width * 0.9);
		this.pnlLobby.setDividerLocation(width);

		
		add(this.pnlLobby, BorderLayout.CENTER);
		
	}
	
	
	private class ButtonListener implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent e) 
		{
			
			createServer();
		}
		
		private void createServer()
		{
			
		}
		
		private void joinServer()
		{
			
		}
		
	}
	
	public static void main(String[] args) {
		new LobbyWindow();
	}
}
