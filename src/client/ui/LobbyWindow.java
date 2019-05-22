package client.ui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import client.Controller;
import server.actions.RefreshAction;
import server.actions.SessionJoinRequestAction;

public class LobbyWindow extends JFrame {
	
	private JPanel pnlContent;
	private JScrollPane pnlScroll;
	
	private JTable table;
	
	private JButton btnJoin;
	private JButton btnCreate;
	private JButton btnRefresh;
	
	private Controller controller;
	
	public static void main(String[] args) {
		new LobbyWindow(null);
	}
	
	public LobbyWindow(Controller controller) {
		
		this.controller = controller;
		
		init();
	}

	
	private void init() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//Init components
		pnlContent = new JPanel(new BorderLayout());
		pnlScroll = new JScrollPane();
		
		btnJoin = new JButton("Join selected session");
		btnCreate = new JButton("Create new session");
		btnRefresh = new JButton("Refresh session list");
		
		table = new JTable(new SessionTableModel());
		
		//Config table.
		table.getTableHeader().setReorderingAllowed(false);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		//Add components to panels
		
		//Scrollpane + table
		pnlScroll.setViewportView(table);

		pnlContent.add(pnlScroll,BorderLayout.CENTER);
		
		//Panel for buttons
		JPanel pnlButtons = new JPanel(new GridLayout(3,1));
		pnlButtons.add(btnJoin);
		pnlButtons.add(btnCreate);
		pnlButtons.add(btnRefresh);
		
		pnlContent.add(pnlButtons,BorderLayout.EAST);
		
		//Button listeners
		ButtonListener listener = new ButtonListener();
		
		btnJoin.addActionListener(listener);
		btnCreate.addActionListener(listener);
		btnRefresh.addActionListener(listener);
		
		//Final calls
		setContentPane(pnlContent);
		pack();
		setTitle("Lobby");
		setVisible(true);
	}
	
	
	public void updateSessionList(ArrayList<String> sessionList) {
		SessionTableModel model = new SessionTableModel();
		
		for(String s : sessionList) {
			String[] sessionString = s.split(":");
			model.addRow(sessionString);
			
		}
		
		table.setModel(model);
	}

	/*
	 * @author Andreas Jönsson
	 **/
	private static class SessionTableModel extends DefaultTableModel {

		// The names of the columns
		private static final String[] columnNames = { "Session Name", "Current players", "Maximum allowed"};

		public SessionTableModel() {
			super(columnNames, 0);
		}

		public String[] getColumnNames() {
			return columnNames;
		}
		
		@Override
		public boolean isCellEditable(int row, int column) {
			return false;
		}
	}
	
	private class ButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

			if(e.getSource() == btnJoin) {
				int index = table.getSelectedRow();
				
				if(index != -1) {
					String name = (String) table.getModel().getValueAt(index, 0);
					controller.pushActionToServer(new SessionJoinRequestAction(controller.username, name));
				}
			}
			
			else
				
			if(e.getSource() == btnCreate) {
				new NewSessionWindow(controller);
			}
			
			else
				
			if(e.getSource() == btnRefresh) {
				controller.pushActionToServer(new RefreshAction(controller.username));
			}
		}
	}
	
	private class wListener implements WindowListener {
		
		public void windowOpened(WindowEvent e) {
			controller.pushActionToServer(new RefreshAction(controller.username));
		}

		public void windowClosing(WindowEvent e) {}
		public void windowClosed(WindowEvent e) {}
		public void windowIconified(WindowEvent e) {}
		public void windowDeiconified(WindowEvent e) {}
		public void windowActivated(WindowEvent e) {}
		public void windowDeactivated(WindowEvent e) {}
		
	}
}
