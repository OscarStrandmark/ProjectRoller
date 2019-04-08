package client.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileNameExtensionFilter;

import client.Controller;

public class MainWindow extends JFrame {

	private Controller controller;

	//Components used in main window.
	private JScrollPane boardPanel;
	private JTabbedPane sidePanel;
	private JSplitPane contentPane;

	//Components for chat-panel
	private JTextArea chatJTA;
	private JTextField chatBox;
	private JButton chatBtnSend;

	//Components for import-panel
	private JTextField importJTFFilePath;

	private JFileChooser importJFCBackground;

	private JButton importBtnBackgroundFileChooser;
	private JButton importBtnBackgoundImport;
	private JButton importBtnIconFileChooser;
	private JButton importBtnIconImport;


	//Components for settings-panel


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

		//Code below sets the board-view to always be 80% of the screen.
		addComponentListener(new ComponentAdapter() {
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
		JPanel importPanel = new JPanel(new GridLayout(3, 1));

		JPanel importIconPane = new JPanel();

		JPanel importBGPane = new JPanel(new GridLayout(4,1)); //Panel for importing a background.
		importBGPane.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1)); //Creates a border for the panel

		importJTFFilePath = new JTextField();
		importJTFFilePath.setEditable(false);
		importJTFFilePath.setText("File path ");

		importJFCBackground = new JFileChooser();
		importBtnBackgoundImport = new JButton("Import");
		importBtnBackgroundFileChooser = new JButton("Pick file");


		importJFCBackground.setFileSelectionMode(JFileChooser.FILES_ONLY);
		importJFCBackground.setFileFilter(new FileNameExtensionFilter("Image files", ImageIO.getReaderFileSuffixes())); //Accept all image files supported on the system the program is ran on.

		importBGPane.add(new JLabel("Import an image as a background", SwingConstants.CENTER));
		importBGPane.add(importBtnBackgroundFileChooser);
		importBGPane.add(importJTFFilePath);
		importBGPane.add(importBtnBackgoundImport);

		JPanel importSaveLoadSessionPane = new JPanel(); //For future implementation of importing saved session data.


		importPanel.add(importIconPane);
		importPanel.add(importBGPane);
		importPanel.add(importSaveLoadSessionPane);


		// ---------- SETTINGS ----------
		JPanel settingsPanel = new JPanel();


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
		sidePanel.addTab("Import", importPanel);
		sidePanel.addTab("Settings", settingsPanel);
		sidePanel.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
		sidePanel.setMinimumSize(new Dimension(800, 800));

		contentPane.setLeftComponent(boardPanel);
		contentPane.setRightComponent(sidePanel);
		contentPane.setEnabled(false);
		add(contentPane,BorderLayout.CENTER);

		/*
		 * ==============================================================
		 * =======================LISTENERS==============================
		 * ==============================================================
		 */

		ButtonListener listener = new ButtonListener();

		importBtnBackgroundFileChooser.addActionListener(listener);
		importBtnBackgoundImport      .addActionListener(listener);
	}

	private void setBoardBackground(Image img) {
		Graphics g = boardPanel.getGraphics();
		g.drawImage(img, 0, 0, null);
	}

	private class ButtonListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == chatBtnSend) {
				controller.newChatMessage(chatBox.getText());
				chatBox.setText("");
			}

			if(e.getSource() == importBtnBackgroundFileChooser) {
				importJFCBackground.showOpenDialog(null);

				File file = importJFCBackground.getSelectedFile();

				importJTFFilePath.setText(file.getPath());
			}

			if(e.getSource() == importBtnBackgoundImport) {
				String filePath = importJTFFilePath.getText();
				Graphics g = boardPanel.getGraphics();

				Image img = null;
				try {
					img = ImageIO.read(new File(filePath));
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				g.drawImage(img, 0, 0, boardPanel.getWidth(), boardPanel.getHeight(), null);

			}
		}
	}
}
