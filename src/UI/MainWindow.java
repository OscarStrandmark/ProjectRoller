package UI;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

import client.Controller;

public class MainWindow extends JFrame {

	private JScrollPane boardPanel;
	private JTabbedPane sidePanel;
	
	private Controller controller;
	
	public MainWindow(Controller controller) {
		this.controller = controller;
		init();
	}
	
	private void init() {
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		setVisible(true);
		
		boardPanel = new JScrollPane();
		add(boardPanel,BorderLayout.CENTER);
		
		sidePanel = new JTabbedPane();
		sidePanel.addTab("TEST 0", new JPanel());
		sidePanel.addTab("TEST 1", new JPanel());
		sidePanel.addTab("TEST 2", new JPanel());
		sidePanel.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
		add(sidePanel,BorderLayout.EAST);
		
	}
}
