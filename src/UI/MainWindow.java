package UI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import client.Controller;

public class MainWindow extends JFrame {

	private JScrollPane boardPanel;
	private JTabbedPane sidePanel;
	private JSplitPane contentPane;
	private Controller controller;
	
	private JPanel chatPanel;
	private JTextField chatJTF;
	private JComboBox<String> chatCharacter;
	private JTextArea chatBox;
	private JButton chatSendBtn;
	
	public MainWindow(Controller controller) {
		this.controller = controller;
		init();
		pack();
	}
	
	private void init() {
		setTitle("Project Roller");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		setVisible(true);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		addWindowListener(new wListener());
		
		/*
		 * ==============================================================
		 * =======================TAB PANELS=============================
		 * ==============================================================
		 */
		
		chatPanel = new JPanel(new BorderLayout());
		chatJTF = new JTextField();
		chatJTF.setEditable(false);
		chatPanel.add(chatJTF, BorderLayout.CENTER);
		
		JPanel textBoxPane = new JPanel();
		textBoxPane.add(new JLabel("Send message as:"));
		String[] strings = {"Text1","Text2","Text3"};
		chatCharacter = new JComboBox<String>(strings);
		textBoxPane.add(chatCharacter);
		chatBox = new JTextArea();
		textBoxPane.add(chatBox);
		chatSendBtn = new JButton("Send");
		textBoxPane.add(chatSendBtn);
		//TODO: ADD LISTENER TO BTN
		chatPanel.add(textBoxPane,BorderLayout.SOUTH);
		
		/*
		 * ==============================================================
		 * ======================MAIN PANELS=============================
		 * ==============================================================
		 */
		
		contentPane = new JSplitPane();	
		boardPanel = new JScrollPane();
		sidePanel = new JTabbedPane();
		
		sidePanel.addTab("TEST 0", chatPanel);
		sidePanel.addTab("TEST 1", new JPanel());
		sidePanel.addTab("TEST 2", new JPanel());
		sidePanel.addTab("TEST 3", new JPanel());
		sidePanel.addTab("TEST 4", new JPanel());
		sidePanel.addTab("TEST 5", new JPanel());
		sidePanel.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
		sidePanel.setMinimumSize(new Dimension(800, 800));
		
		sidePanel.addChangeListener(new TabListener());
		
		contentPane.setLeftComponent(boardPanel);
		contentPane.setRightComponent(sidePanel);
		contentPane.setEnabled(false);

		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int width = (int) (screenSize.width * 0.8);
		contentPane.setDividerLocation(width);
		add(contentPane,BorderLayout.CENTER);
		add(new JLabel("TEST"),BorderLayout.NORTH);	
	}
	
	private class TabListener implements ChangeListener {

		@Override
		public void stateChanged(ChangeEvent e) {
			int index = sidePanel.getSelectedIndex();
			System.out.println(index);
			switch (index) {
			case 0:
				break;
			case 1:
				break;
			case 2:
				break;
			case 3:
				break;
			case 4:
				break;
			case 5:
				break;
			default:
				break;
			}
		}		
	}
	
	private class wListener implements WindowListener {

		@Override
		public void windowActivated(WindowEvent arg0) {}

		@Override
		public void windowClosed(WindowEvent arg0) {}

		@Override
		public void windowClosing(WindowEvent arg0) {}

		@Override
		public void windowDeactivated(WindowEvent arg0) {}

		@Override
		public void windowDeiconified(WindowEvent arg0) {}

		@Override
		public void windowIconified(WindowEvent arg0) {}

		public void windowOpened(WindowEvent arg0) {}
		
	}
}
