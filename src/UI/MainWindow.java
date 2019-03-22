package UI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
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
	
	private JTextArea chatJTA;
	private JTextField chatBox;
	private JButton chatBtnSend;
	
	public MainWindow(Controller controller) {
		this.controller = controller;
		init();
		repaint();
	}
	
	private void init() {
		setTitle("Project Roller");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		setVisible(true);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		
		addComponentListener(new ComponentAdapter() { //Sidepanel is always 20% of window width.
			public void componentResized(ComponentEvent e) {
				Rectangle r = getBounds();
				int w = (int)(r.width * 0.8);
				try {
					contentPane.setDividerLocation(w);

				} catch (Exception e2) {}
			}
		});
		
		/*
		 * ==============================================================
		 * =======================TAB PANELS=============================
		 * ==============================================================
		 */
		
		// ---------- CHAT ---------
		JPanel chatPanel = new JPanel(new BorderLayout());
		
		JScrollPane scrollPane1 = new JScrollPane();
		chatJTA = new JTextArea();
		chatJTA.setEditable(false);
		scrollPane1.setViewportView(chatJTA);
		chatPanel.add(scrollPane1, BorderLayout.CENTER);
		
		JPanel textBoxPane = new JPanel(new BorderLayout());
		chatBox = new JTextField();
		chatBtnSend = new JButton("SEND");
		chatBtnSend.addActionListener(new ButtonListener());
		textBoxPane.add(chatBox, BorderLayout.CENTER);
		textBoxPane.add(chatBtnSend, BorderLayout.EAST);
		textBoxPane.setMinimumSize(new Dimension(0, 150));
		chatPanel.add(textBoxPane,BorderLayout.SOUTH);
		
		// ---------- NOTES ----------
		
		JScrollPane notePanel = new JScrollPane();
		JTextArea noteJTA = new JTextArea();
		notePanel.setViewportView(noteJTA);
		
		// ---------- IMPORT ----------
		
		
		
		// ---------- SETTINGS ----------
		
		
		/*
		 * ==============================================================
		 * ======================MAIN PANELS=============================
		 * ==============================================================
		 */
		
		contentPane = new JSplitPane();	
		boardPanel = new JScrollPane();
		sidePanel = new JTabbedPane();
		
		sidePanel.addTab("Chat", chatPanel);
		sidePanel.addTab("Notes", notePanel);
		sidePanel.addTab("Import", new JPanel());
		sidePanel.addTab("Settings", new JPanel());
		sidePanel.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
		sidePanel.setMinimumSize(new Dimension(800, 800));
		
		sidePanel.addChangeListener(new TabListener());
		
		contentPane.setLeftComponent(boardPanel);
		contentPane.setRightComponent(sidePanel);
		contentPane.setEnabled(false);
		add(contentPane,BorderLayout.CENTER);
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
	
	private class ButtonListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == chatBtnSend) {
				controller.newChatMessage(chatBox.getText());
				chatBox.setText("");
			}
		}
	}
}
