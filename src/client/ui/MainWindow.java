package client.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.filechooser.FileNameExtensionFilter;

import client.BoardModel;
import client.Controller;
import server.actions.BoardBackgroundChangeAction;
import server.actions.BoardResyncRequestAction;
import server.actions.QuitAction;
import server.actions.SessionLeaveAction;
import server.actions.UsernameChangeAction;

public class MainWindow extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Controller controller;
	private BoardModel model;
	
	//Components used in main window.
	private JPanel boardPanel;
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
	
	private JLabel lblIconPreviewer;
	
	
	private JLabel lblBackgroundPreviewer;
	
	private JSlider sldrIconSize;


	private JTextField scaleIcon;

	private String imagePath;

	private JLabel backgroundIcon;

	private int iconWidth = 150;
	private int iconHeight = 150;

	//Components for settings-panel

	private JTextField settingsJTFUsername;

	private JButton settingsBtnSave;
	private JButton settingsBtnLeave;
	private JButton settingsBtnResync;

	public MainWindow(Controller controller, BoardModel model) {
		this.controller = controller;
		this.model = model;
		init();
		repaint();
		setVisible(true);
		settingsJTFUsername.setText(controller.username);
		model.setBoard(boardPanel);
	}

	private void init() {
		setTitle("Project Roller");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
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
		String welcomeMsg = "";
		welcomeMsg += "Welcome to ProjectRoller!" + "\n";
		welcomeMsg += "If you get de-synced use the resync-button in the settings" + "\n";
		welcomeMsg += "This chat-tab can be used to communicate with other players or excecute commands." + "\n";
		welcomeMsg += "Commands :" + "\n";
		welcomeMsg += "/roll xdy + n - Used to roll a dice." + "\n";
		welcomeMsg += "/dmroll xdy + n - same as /roll, but only you can see the result." + "\n";
		welcomeMsg += "/rp [name] [text] - Used to write a message as a character with the specified name." + "\n";
		welcomeMsg += "/w [name] [text] - Used to write a private message to another player."+ "\n";
		welcomeMsg += "-------------------------------------------" + "\n";
		welcomeMsg += "Dont forget to change your username before you start!" + "\n";
		welcomeMsg += "-------------------------------------------" + "\n";
		chatJTA.setText(welcomeMsg);
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
		JPanel importPanel = new JPanel(new GridLayout(2, 1));

		JPanel importIconPane = new JPanel();
		importIconPane.setLayout(new GridLayout(2,1));

		JPanel infoBGPane = new JPanel();
		infoBGPane.setLayout(new BoxLayout(infoBGPane, BoxLayout.Y_AXIS));
		JPanel buttonsBGPane = new JPanel(new GridLayout(1,2));
		JPanel importBGPane = new JPanel(new GridLayout(2,1)); //Panel for importing a background.
		importBGPane.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1)); //Creates a border for the panel
		JPanel importBGControllPane = new JPanel(new GridLayout(2,1));
		this.lblBackgroundPreviewer = new JLabel();
		this.lblBackgroundPreviewer.setSize(importBGControllPane.getWidth(), importBGControllPane.getHeight()/3);
		
		importJTFFilePath = new JTextField();
		importJTFFilePath.setEditable(false);
		importJTFFilePath.setText("File path ");

		importJFCBackground = new JFileChooser();
		importBtnBackgoundImport = new JButton("Load Background");
		importBtnBackgroundFileChooser = new JButton("Choose Background");


		importJFCBackground.setFileSelectionMode(JFileChooser.FILES_ONLY);
		importJFCBackground.setFileFilter(new FileNameExtensionFilter("Image files", ImageIO.getReaderFileSuffixes())); //Accept all image files supported on the system the program is ran on.

		infoBGPane.add(new JLabel("Background", SwingConstants.CENTER));
		infoBGPane.add(new JLabel("Import an image as a background", SwingConstants.CENTER));
		//infoBGPane.add(importJTFFilePath);
		
		//importBGPane.add(infoBGPane);
		buttonsBGPane.add(importBtnBackgroundFileChooser);
		buttonsBGPane.add(importBtnBackgoundImport);
		//importBGPane.add(buttonsBGPane);
		
		importBGControllPane.add(infoBGPane);
		importBGControllPane.add(buttonsBGPane);
		importBGPane.add(importBGControllPane);
		importBGPane.add(this.lblBackgroundPreviewer);
		this.lblBackgroundPreviewer.setBounds(this.getX(), this.getY(), importBGControllPane.getWidth(), importBGControllPane.getHeight());

		importPanel.add(importIconPane);
		importPanel.add(importBGPane);


		// ------- IMPORT ICON --------

		JPanel infoPane = new JPanel();
		infoPane.setLayout(new BoxLayout(infoPane, BoxLayout.Y_AXIS));
		scaleIcon = new JTextField();
		JLabel setSizeIcon = new JLabel("Set pixel size for icon", SwingConstants.CENTER);
		JPanel buttonsPanel = new JPanel(new GridLayout(1,2));
		JPanel sizePanel = new JPanel();
		sizePanel.setLayout(new BoxLayout( sizePanel, BoxLayout.Y_AXIS));
		JPanel importIconControllPane = new JPanel(new GridLayout(3, 1));

	
		this.lblIconPreviewer = new JLabel();
		
		this.sldrIconSize = new JSlider();
		this.sldrIconSize.setMajorTickSpacing(40);
		this.sldrIconSize.setMinorTickSpacing(10);
		this.sldrIconSize.setPaintTicks(true);
		this.sldrIconSize.setSnapToTicks(true);
		this.sldrIconSize.setMinimum(20);
		this.sldrIconSize.setMaximum(300);
		this.sldrIconSize.setPaintLabels(true);
		
		Border borderImport = BorderFactory.createLineBorder(Color.black);

		importBtnIconFileChooser = new JButton("Choose Icon");
		importBtnIconImport = new JButton("Load Icon");

		infoPane.add(new JLabel("Icon", SwingConstants.CENTER));
		infoPane.add(new JLabel("Import an image as a icon", SwingConstants.CENTER));
		importIconControllPane.add(infoPane);
		
		sizePanel.add(setSizeIcon);
		sizePanel.add(this.sldrIconSize);	
		importIconControllPane.add(sizePanel);
		
		buttonsPanel.add(importBtnIconFileChooser);
		buttonsPanel.add(importBtnIconImport);
		importIconControllPane.add(buttonsPanel);
		
		importIconPane.add(importIconControllPane);
		importIconPane.add(lblIconPreviewer);
		importIconPane.setBorder(borderImport);

		// ---------- SETTINGS ----------
		JPanel settingsPanel = new JPanel();
		JPanel settingsGrid = new JPanel(new GridLayout(1, 2)); //Grid is always 2 wide and 
		
		settingsBtnSave = new JButton("Save settings");
		settingsBtnLeave = new JButton("LEAVE SESSION");
		settingsBtnResync = new JButton("Resync");
		
		settingsJTFUsername = new JTextField();
		settingsJTFUsername.setColumns(15);
		
		settingsGrid.add(new JLabel("Username:"));
		settingsGrid.add(settingsJTFUsername);
				
		settingsPanel.add(settingsGrid);
		settingsPanel.add(settingsBtnSave);
		settingsPanel.add(settingsBtnLeave);
		settingsPanel.add(settingsBtnResync);
		/*
		 * ==============================================================
		 * ======================MAIN PANELS=============================
		 * ==============================================================
		 */

		contentPane = new JSplitPane();
		boardPanel = new JPanel();
		boardPanel.setLayout(null);
		sidePanel = new JTabbedPane();

		sidePanel.addTab("Chat", chatPanel);
		sidePanel.addTab("Notes", notePanel);
		sidePanel.addTab("Import / Create", importPanel);
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
		importBtnBackgoundImport.addActionListener(listener);

		importBtnIconFileChooser.addActionListener(listener);
		importBtnIconImport.addActionListener(listener);
		
		settingsBtnSave.addActionListener(listener);
		settingsBtnLeave.addActionListener(listener);
		settingsBtnResync.addActionListener(listener);
		
		this.addWindowListener(new wListener());
	}

	public void appendChatLine(String line) {
		String current = chatJTA.getText();
		chatJTA.setText(current + line + "\n");
	}

	/**
	 * Scales the chosen image to a set size.
	 * @param srcImg the image to be scaled
	 * @param w the width of the image
	 * @param h the height of the image
	 * @return return the new scaled image
	 */
	private Image getScaledImage(Image srcImg, int w, int h){
		BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = resizedImg.createGraphics();
		g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g2.drawImage(srcImg, 0, 0, w, h, null);
		g2.dispose();
		return resizedImg;
	}

	private class ButtonListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			//Send text written in chatbox
			if(e.getSource() == chatBtnSend) {
				controller.handleMessage(chatBox.getText());
				chatBox.setText("");
			}
			//Open File picker for background in import tab
			if(e.getSource() == importBtnBackgroundFileChooser) {
				importJFCBackground.showOpenDialog(null);

				File file = importJFCBackground.getSelectedFile();

				importJTFFilePath.setText(file.getPath());
				

			
				ImageIcon image = new ImageIcon(importJTFFilePath.getText());
				
				lblBackgroundPreviewer.setIcon(new ImageIcon(image.getImage().getScaledInstance(lblIconPreviewer.getWidth(), lblIconPreviewer.getHeight(), Image.SCALE_DEFAULT)));
				lblBackgroundPreviewer.setBounds(0, 0, image.getIconWidth(), image.getIconHeight());
				lblBackgroundPreviewer.repaint();
				
			}
			//Import selected file as a background
			if(e.getSource() == importBtnBackgoundImport) {
				ImageIcon background = new ImageIcon(importJTFFilePath.getText());
				Image newBackgroundImage = getScaledImage(background.getImage(), boardPanel.getWidth(), boardPanel.getHeight()); //mer parametrar i metoden
				ImageIcon finalBackground = new ImageIcon(newBackgroundImage);
				controller.pushActionToServer(new BoardBackgroundChangeAction(controller.username, finalBackground));
			} 
			
			//Open file picker for importing new icons.
			if(e.getSource() == importBtnIconFileChooser) {
				JFileChooser fileChooser = new JFileChooser();
				imagePath = null;

				//Filter for what files to show in FileChooser-window.
				FileNameExtensionFilter imageFilter = new FileNameExtensionFilter(
						"Image files", ImageIO.getReaderFileSuffixes());
				fileChooser.setFileFilter(imageFilter);
				fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

				int fileOk = fileChooser.showOpenDialog(null);
				if(fileOk == JFileChooser.APPROVE_OPTION) {
					imagePath = fileChooser.getSelectedFile().getPath();
				}
				
				ImageIcon image = new ImageIcon(imagePath);
				lblIconPreviewer.setIcon(new ImageIcon(image.getImage().getScaledInstance(lblIconPreviewer.getWidth(), lblIconPreviewer.getHeight(), Image.SCALE_DEFAULT)));
				lblIconPreviewer.repaint();
			}
			
			//Import the chosen file for icon import. 
			if(e.getSource() == importBtnIconImport) {
				
				boolean scaled = false;
				
				
					
						int scale = sldrIconSize.getValue();
						
						
						
							iconWidth = scale;
							iconHeight = scale;
							scaled = true;
						
				
				
				if(scaled) {
					
					Image image = new ImageIcon(imagePath).getImage();
					Image newIconImage = getScaledImage(image, iconWidth, iconHeight);
					
					model.sendIconNew(new ImageIcon(newIconImage));
				}
			}
			
			//Save changed settings in settings tab
			if(e.getSource() == settingsBtnSave) {
				String username = settingsJTFUsername.getText();
				if(username != controller.username) {
					controller.pushActionToServer(new UsernameChangeAction(controller.username, username));
					controller.username = username;
				}
			}
			
			//Leave the session
			if(e.getSource() == settingsBtnLeave) {
				controller.pushActionToServer(new SessionLeaveAction(controller.username));
				controller.sessionLeft();
			}
			
			//Resync request
			if(e.getSource() == settingsBtnResync) {
				controller.pushActionToServer(new BoardResyncRequestAction(controller.username));
			}
		}
	}
	
	private class wListener implements WindowListener {
		
		//Called when user exits program via the red X.
		public void windowClosing(WindowEvent e) {
			controller.pushActionToServer(new QuitAction(controller.username));
		}
		
		public void windowClosed(WindowEvent e) {}
		public void windowIconified(WindowEvent e) {}
		public void windowDeiconified(WindowEvent e) {}
		public void windowActivated(WindowEvent e) {}
		public void windowDeactivated(WindowEvent e) {}
		public void windowOpened(WindowEvent e) {}
	}
}