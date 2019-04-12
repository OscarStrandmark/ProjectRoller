package client.ui;

import javax.swing.JFrame;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import client.Controller;
import client.StartClient;
import server.Session;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Vector;



/* A window that shows all active sessions
 * @author Andreas Jönsson den 8/4-2019  
 * 
 **/
public class LobbyWindow extends JFrame
{

	//--------------------------------------------------------------------------------
	//----------------------------Create Window---------------------------------------
	//--------------------------------------------------------------------------------
	
	private JFrame frameCreateSession;

	JTextField tfName;
	JTextField tfPassword;
	JTextField tfMaxPlayers;
	
	private JButton btnCreate;
	private JButton btnCancel;
	
	//--------------------------------------------------------------------------------
	//----------------------------Main Window-----------------------------------------
	//--------------------------------------------------------------------------------
	
	//panels
	private JSplitPane pnlLobby;
	private JPanel pnlLobbyCreate;
	private JScrollPane spnSessionList;
	
	//Buttons
	private JButton btnJoinSession;
	private JButton btnCreateSession;
	private JButton btnRefreah;
	
	
	//--------------------------------------------------------------------------------
	//----------------------------Data------------------------------------------------
	//--------------------------------------------------------------------------------
	
	//List for sessions
	private ArrayList<Session> sessionList;
	private JTable sessionTable;
	private TableModel sessionTableModel;
	
	private ArrayList<String> sessionData;
	//TODO change sessionList to sessionData to match with the server
	
	
	/**
	    * Constuctor for the lobby window
	    */
	public LobbyWindow()
	{
		
		//initilize the lobbywindow
		init();
		setVisible(true);
	}
	
	
	/**
	    * Initilizes the components and data in the lobby window
	    */
	public void init()
	{
		
		//--------------------------------------------------------------------------------
		//----------------------------init Frame------------------------------------------
		//--------------------------------------------------------------------------------
		
		setTitle("Project Roller");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		setVisible(true);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.setResizable(false);
		

		//--------------------------------------------------------------------------------
		//----------------------------init Panels-----------------------------------------
		//--------------------------------------------------------------------------------
		
		this.pnlLobby = new JSplitPane();
				
		this.pnlLobbyCreate = new JPanel();
		this.pnlLobbyCreate.setLayout(new BoxLayout(this.pnlLobbyCreate, BoxLayout.PAGE_AXIS));

		
		//--------------------------------------------------------------------------------
		//----------------------------init Buttons----------------------------------------
		//--------------------------------------------------------------------------------
		this.btnJoinSession = new JButton("Join Session");
		this.btnCreateSession = new JButton("Create Session");
		this.btnRefreah = new JButton("Refresh");
		this.btnJoinSession.addActionListener(new ButtonListener());
		this.btnCreateSession.addActionListener(new ButtonListener());
		this.btnRefreah.addActionListener(new ButtonListener());
		
		//--------------------------------------------------------------------------------
		//----------------------------panel components init-------------------------------
		//--------------------------------------------------------------------------------
		this.pnlLobbyCreate.add(this.btnJoinSession);
		this.pnlLobbyCreate.add(this.btnCreateSession);
		this.pnlLobbyCreate.add(this.btnRefreah);


		//--------------------------------------------------------------------------------
		//----------------------------Lobby Data -----------------------------------------
		//--------------------------------------------------------------------------------
		sessionList = new ArrayList<Session>();
		
		sessionData = new ArrayList<String>();
		//TODO change sessionList to sessionData to match with the server
		
		//--------------------------------------------------------------------------------
		//----------------------------JTable----------------------------------------------
		//--------------------------------------------------------------------------------
		
		this.sessionTableModel = new SessionTableModel();
				
		
		
		for(Session session : sessionList)
		{
			this.addSession(session);
			//TODO change sessionList to sessionData to match with the server
		}


		//initilizing the JTable
		this.sessionTable = new JTable(this.sessionTableModel);
		this.spnSessionList = new JScrollPane(this.sessionTable);

		
		
		//-----------------------------------------------------------------------------------------
		//------------------------------------------Main panels components-------------------------
		//-----------------------------------------------------------------------------------------
		this.pnlLobby.setLeftComponent(this.spnSessionList);
		this.pnlLobby.setRightComponent(this.pnlLobbyCreate);
		this.pnlLobby.setEnabled(false);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int width = (int) (screenSize.width * 0.9);
		this.pnlLobby.setDividerLocation(width);

		
		add(this.pnlLobby, BorderLayout.CENTER);
		
	}
	
	
	/**
	    * Updates the session table in the lobby window
	    * @param sessionList ArrayList<Session>
	    */
	public void updateSessionList(ArrayList<Session> sessionList)
	{

		//TODO change sessionList to sessionData to match with the server
		
		//Clears the old table
		((DefaultTableModel) this.sessionTableModel).setRowCount(0);

		for(Session session : sessionList)
		{
			//TODO change sessionList to sessionData to match with the server

			this.addSession(session);
			
		}
		
		
		((DefaultTableModel) this.sessionTableModel).fireTableDataChanged();
		
		this.sessionTable = new JTable(this.sessionTableModel);
		this.sessionTable.revalidate();
		
		
		this.spnSessionList = new JScrollPane(this.sessionTable);
	}
	
	
	/**
	    * Adds a session the the tablemodel
	    * @param session Session
	    */
	public void addSession(Session session)
	{
		//TODO change sessionList to sessionData to match with the server
		Vector<Object> tableData = new Vector<>();
		tableData.add(session.getName());
		tableData.add(session.getnbrPlayers());
		tableData.add(session.getMaxPlayers());
		((DefaultTableModel) this.sessionTableModel).addRow(tableData);
	}
	
	
	/**
	    * Creates a window to create a  session
	    */
	private void createSession()
	{
		//initilizing the components in the create session window
		JPanel pnlCreateSession = new JPanel(new BorderLayout());
		JPanel pnlButtons = new JPanel(new FlowLayout());
		JPanel pnlTextField = new JPanel(new GridLayout(3, 2));
		frameCreateSession = new JFrame();
		
		JLabel lblName = new JLabel("Session Name:");
		JLabel lblPassword = new JLabel("Session Password:");
		JLabel lblMaxPlayers = new JLabel("Max Players");
		
		tfName = new JTextField();
		tfPassword = new JTextField();
		tfMaxPlayers = new JTextField();
		
		//adding the components to the create session window
		pnlCreateSession.add(pnlTextField, BorderLayout.NORTH);
		pnlCreateSession.add(pnlButtons, BorderLayout.SOUTH);
		
		;
		
		pnlTextField.add(lblName);
		pnlTextField.add(tfName);
		pnlTextField.add(lblPassword);
		pnlTextField.add(tfPassword);
		pnlTextField.add(lblMaxPlayers);
		pnlTextField.add(tfMaxPlayers);
		
		btnCreate = new JButton("Create");
		btnCancel = new JButton("Cancel");
		
		btnCreate.addActionListener(new ButtonListener());
		btnCancel.addActionListener(new ButtonListener());
												
		pnlButtons.add(btnCancel);
		pnlButtons.add(btnCreate);
		
		//init the frame
		frameCreateSession.setTitle("Create a Session");
		
		frameCreateSession.setVisible(true);
		frameCreateSession.add(pnlCreateSession);		
		Dimension windowSize = Toolkit.getDefaultToolkit().getScreenSize();
		windowSize.setSize(windowSize.getWidth()/3, windowSize.getHeight()/3);
		frameCreateSession.setSize(windowSize);
		frameCreateSession.setLocation((int)(windowSize.getWidth()/2) + (frameCreateSession.getWidth() /2), (int)(windowSize.getHeight()/2) + (frameCreateSession.getHeight()/2));
	
		frameCreateSession.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

	}
	
	/**
	    * Joining an active session
	    */
	private void joinServer()
	{
		//starts the main window
		new Controller();
		this.setVisible(false);
		
		//TODO connect to the chosen session & communicate with the server
	}
	

	
	
	/* The class sessionTableModel handles information to be used in the JTable
	 * @author Andreas Jönsson den 10/04-2019  
	 * 
	 **/
	private static class SessionTableModel extends DefaultTableModel
	{
		
		//The names of the columns
		private static final String[] columnNames = { "Session Name", "Number Of Players", "Max Players" };

		
		public SessionTableModel()
		{
			super(columnNames, 0);
		}
		
		public String[] getColumnNames() 
		{
			return columnNames;
		}
	
	}
	
	
	/* A Listener that listens to buttons
	 * @author Andreas Jönsson den 8/04-2019  
	 * 
	 **/
	private class ButtonListener implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent e){
			
			//opens up the window to create a new session
			if(e.getSource() == btnCreateSession){
				createSession();
			}
			
			//the user joins the server
			//TODO link with the server
			else if(e.getSource() == btnJoinSession){
				joinServer();
			}
			
			
			//when the button is pressed a new session is created
			//TODO link with server
			else if(e.getSource() == btnCreate){
				
				
				Session newSession = new Session(tfName.getText(), 0, Integer.parseInt(tfMaxPlayers.getText()));
				sessionList.add(newSession);
				updateSessionList(sessionList);
				frameCreateSession.setVisible(false);
			}
			
			
			//cancels creating a new session
			else if(e.getSource() == btnCancel){
				frameCreateSession.setVisible(false);
			}
			
			
			//if the button refresh is pressed the session table will update
			//TODO link with the server
			else if(e.getSource() == btnRefreah){
				updateSessionList(sessionList);
			}
		}
		
	}
	
	public static void main(String[] args) {
		new LobbyWindow();
	}
}
