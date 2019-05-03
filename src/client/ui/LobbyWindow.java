package client.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import client.Controller;
import server.Session;
import server.actions.ConnectToSessionAction;
import server.actions.CreateSessionAction;
import server.actions.RefreshAction;

/* A window that shows all active sessions
 * @author Andreas Jönsson den 8/4-2019  
 * 
 **/
public class LobbyWindow extends JFrame {

	// --------------------------------------------------------------------------------
	// ----------------------------Create Window---------------------------------------
	// --------------------------------------------------------------------------------

	private JFrame frameCreateSession;

	JTextField tfName;
	JTextField tfPassword;
	JTextField tfMaxPlayers;

	private JButton btnCreate;
	private JButton btnCancel;

	// --------------------------------------------------------------------------------
	// ----------------------------MainWindow-----------------------------------------
	// --------------------------------------------------------------------------------

	// panels
	private JSplitPane pnlLobby;
	private JPanel pnlLobbyCreate;
	private JScrollPane spnSessionList;

	// Buttons
	private JButton btnJoinSession;
	private JButton btnCreateSession;
	private JButton btnRefresh;

	// --------------------------------------------------------------------------------
	// ----------------------------Data------------------------------------------------
	// --------------------------------------------------------------------------------

	private Controller controller;
	// TODO implementera controller-klassen

	// List for sessions
	private ArrayList<Session> sessionList;
	private JTable sessionTable;
	private TableModel sessionTableModel;

	private ArrayList<String> sessionData;
	// TODO change sessionList to sessionData to match with the server

	/**
	 * Constuctor for the lobby window
	 */
	public LobbyWindow() {

		// initilize the lobbywindow
		init();
		setVisible(true);
	}

	/**
	 * Constuctor for the lobby window
	 */
	public LobbyWindow(Controller controller) {

		// initilize the lobbywindow
		init();
		setVisible(true);
		this.controller = controller;
	}

	/**
	 * Initializes the components and data in the lobby window
	 */
	public void init() {

		// --------------------------------------------------------------------------------
		// ----------------------------initFrame------------------------------------------
		// --------------------------------------------------------------------------------


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

		// --------------------------------------------------------------------------------
		// ----------------------------initButtons----------------------------------------
		// --------------------------------------------------------------------------------
		this.btnJoinSession = new JButton("Join Session");
		this.btnCreateSession = new JButton("Create Session");
		this.btnRefresh = new JButton("Refresh");
		this.btnJoinSession.addActionListener(new ButtonListener());
		this.btnCreateSession.addActionListener(new ButtonListener());
		this.btnRefresh.addActionListener(new ButtonListener());

		// --------------------------------------------------------------------------------
		// ----------------------------panel components init-------------------------------
		// --------------------------------------------------------------------------------
		this.pnlLobbyCreate.add(this.btnJoinSession);
		this.pnlLobbyCreate.add(this.btnCreateSession);
		this.pnlLobbyCreate.add(this.btnRefresh);

		// --------------------------------------------------------------------------------
		// ----------------------------Lobby Data-----------------------------------------
		// --------------------------------------------------------------------------------
		sessionList = new ArrayList<Session>();

		sessionData = new ArrayList<String>();
		// TODO change sessionList to sessionData to match with the server

		// --------------------------------------------------------------------------------
		// ----------------------------JTable----------------------------------------------
		// --------------------------------------------------------------------------------

		this.sessionTableModel = new SessionTableModel();

		// initializing the JTable
		this.sessionTable = new JTable(this.sessionTableModel);
		this.spnSessionList = new JScrollPane(this.sessionTable);

		// -----------------------------------------------------------------------------------------
		// ------------------------------------------Main panels components-------------------------
		// -----------------------------------------------------------------------------------------

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
	 * 
	 * @param sessionList ArrayList<String>
	 */
	public void updateSessionList(ArrayList<String> sessionList) {

		// TODO change sessionList to sessionData to match with the server

		// Clears the old table
		((DefaultTableModel) this.sessionTableModel).setRowCount(0);

		for (String s : sessionList) {

			String[] bits = s.split(":");
			((DefaultTableModel) this.sessionTableModel).addRow(bits);
			
		}

		((DefaultTableModel) this.sessionTableModel).fireTableDataChanged();

		this.sessionTable = new JTable(this.sessionTableModel);
		this.sessionTable.revalidate();

		this.spnSessionList = new JScrollPane(this.sessionTable);
	}

	/**
	 * Creates a window to create a session
	 */
	private void createSession() {
		// initializing the components in the create session window
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

		btnCreate = new JButton("Create");
		btnCancel = new JButton("Cancel");

		// adding buttonlisteners to the buttons
		btnCreate.addActionListener(new ButtonListener());
		btnCancel.addActionListener(new ButtonListener());

		// adding the components to the create session window
		pnlCreateSession.add(pnlTextField, BorderLayout.NORTH);
		pnlCreateSession.add(pnlButtons, BorderLayout.SOUTH);

		pnlTextField.add(lblName);
		pnlTextField.add(tfName);
		pnlTextField.add(lblPassword);
		pnlTextField.add(tfPassword);
		pnlTextField.add(lblMaxPlayers);
		pnlTextField.add(tfMaxPlayers);

		pnlButtons.add(btnCancel);
		pnlButtons.add(btnCreate);

		// initializing the frame
		frameCreateSession.setTitle("Create a Session");
		frameCreateSession.setVisible(true);
		frameCreateSession.add(pnlCreateSession);
		Dimension windowSize = Toolkit.getDefaultToolkit().getScreenSize();
		windowSize.setSize(windowSize.getWidth() / 3, windowSize.getHeight() / 3);
		frameCreateSession.setSize(windowSize);
		frameCreateSession.setLocation((int) (windowSize.getWidth() / 2) + (frameCreateSession.getWidth() / 2),
				(int) (windowSize.getHeight() / 2) + (frameCreateSession.getHeight() / 2));
		frameCreateSession.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

	}

	/*
	 * The class sessionTableModel handles information to be used in the JTable
	 * 
	 * @author Andreas Jönsson den 10/04-2019
	 * 
	 **/
	private static class SessionTableModel extends DefaultTableModel {

		// The names of the columns
		private static final String[] columnNames = { "Session Name", "Number Of Players", "Max Players" };

		public SessionTableModel() {
			super(columnNames, 0);
		}

		public String[] getColumnNames() {
			return columnNames;
		}

	}

	/*
	 * A Listener that listens to buttons
	 * 
	 * @author Andreas Jönsson den 8/04-2019
	 * 
	 **/
	private class ButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

			// opens up the window to create a new session
			if (e.getSource() == btnCreateSession) {
				createSession();
			}

			// the user joins the server
			// TODO link with the server
			else if (e.getSource() == btnJoinSession) {
				int index = sessionTable.getSelectedRow();
				
				if(index != -1) {
					String sessionName = (String) sessionTable.getValueAt(index, 0);
					new ConnectToSessionAction(controller.username, sessionName);
				}
			}

			// when the button is pressed a new session is created
			// TODO link with server
			else if (e.getSource() == btnCreate) {
				
				boolean numeric;
				
				try {
					int maxPlayers = Integer.parseInt(tfMaxPlayers.getText());
					numeric = true;
				} catch (Exception e2) {
					numeric = false;
				}
				
				if(numeric && tfMaxPlayers.getText().length() > 0 && tfName.getText().length() > 0) {
					CreateSessionAction act;
					
					if(tfPassword.getText().length() > 0) {
						act = new CreateSessionAction(controller.username, tfName.getText(), Integer.parseInt(tfMaxPlayers.getText()), tfPassword.getText());
					} else {
						act = new CreateSessionAction(controller.username, tfName.getText(), Integer.parseInt(tfMaxPlayers.getText()));
					}
					controller.pushActionToServer(act);
				} else {
					JOptionPane.showMessageDialog(null, "Entered text for maximum players must be numeric, must exist and name must exist", "ERROR", JOptionPane.ERROR_MESSAGE);
				}

				frameCreateSession.setVisible(false);
			}

			// cancels creating a new session
			else if (e.getSource() == btnCancel) {
				frameCreateSession.setVisible(false);
			}

			// if the button refresh is pressed the session table will update
			else if (e.getSource() == btnRefresh) {
				controller.pushActionToServer(new RefreshAction(controller.username));

			}
		}

	}

	public static void main(String[] args) {
		new LobbyWindow();
	}
}
