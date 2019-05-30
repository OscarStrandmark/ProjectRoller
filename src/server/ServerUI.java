package server;

import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 * Small UI-window for the server. The window displays the active sessions on the server.
 * 
 * @author Oscar Strandmark
 * @author Andreas Jönsson
 */
public class ServerUI extends JFrame {

	private static final long serialVersionUID = 8974573212414025888L;
	
	private JPanel pnlContent;
	private JScrollPane pnlScroll;
	
	private JList<String> listSessions;
	
	private JButton btnRefresh;
	
	private Connection connection;
	
	public ServerUI(Connection conn) {
		this.connection = conn;
		init();
		updateList();
	}
	
	private void init() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(333, 333);

		pnlContent = new JPanel();
		pnlScroll = new JScrollPane();
		listSessions = new JList<String>();
		btnRefresh = new JButton("REFRESH LIST");
		
		pnlScroll.setViewportView(listSessions);
		
		pnlContent.add(new JLabel("ACTIVE SESSIONS"));
		pnlContent.add(pnlScroll);
		pnlContent.add(btnRefresh);
		pnlContent.add(new JLabel("((Closing this window will kill the server))"));
		
		btnRefresh.addActionListener(e -> updateList());
		
		add(pnlContent);
		setVisible(true);
	}
	
	private void updateList() {
		DefaultListModel<String> model = new DefaultListModel<String>();
		ArrayList<String> list = connection.getSessionsStrings();
		
		for(String s : list) {
			model.addElement(s);
		}
		
		listSessions.setModel(model);
	}
}
