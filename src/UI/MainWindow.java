package UI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;

import client.Controller;

public class MainWindow extends JFrame {

	private JScrollPane boardPanel;
	private JTabbedPane sidePanel;
	private JSplitPane contentPane;
	private Controller controller;
	
	public MainWindow(Controller controller) {
		this.controller = controller;
		init();
	}
	
	private void init() {
		setTitle("Project Roller");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		setVisible(true);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		pack();
		contentPane = new JSplitPane();	
		boardPanel = new JScrollPane();
		sidePanel = new JTabbedPane();
		
		sidePanel.addTab("TEST 0", new JPanel());
		sidePanel.addTab("TEST 1", new JPanel());
		sidePanel.addTab("TEST 2", new JPanel());
		sidePanel.addTab("TEST 3", new JPanel());
		sidePanel.addTab("TEST 4", new JPanel());
		sidePanel.addTab("TEST 5", new JPanel());
		sidePanel.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
		sidePanel.setMinimumSize(new Dimension(800, 800));
		
		contentPane.setLeftComponent(boardPanel);
		contentPane.setRightComponent(sidePanel);
		contentPane.setEnabled(false);

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int width = (int) (screenSize.width * 0.8);
		contentPane.setDividerLocation(width);
		add(contentPane,BorderLayout.CENTER);
		add(new JLabel("TEST"),BorderLayout.NORTH);	
	}
}
