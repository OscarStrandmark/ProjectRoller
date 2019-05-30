package client.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
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
import client.Controller.STATES;
import server.actions.BoardBackgroundChangeAction;
import server.actions.BoardResyncRequestAction;
import server.actions.QuitAction;
import server.actions.SessionLeaveAction;
import server.actions.UsernameChangeAction;

 * Class used for building the window that is used in session. This window contains the board-view and different tool-tabs.
 * 
 * @author Oscar Strandmark
 * @author Andreas Jönsson
 * @author Haris Obradovac
 * @author Patrik Skuza
 */

public class MainWindow extends JFrame {

	private static final long serialVersionUID = 1L;

	private Controller controller;
	private BoardModel model; //Model of the board.
	
	//Components used in main window.
	private JPanel boardPanel; //The jPanel of the board.
	private JTabbedPane sidePanel; //Sidepanels.
	private JSplitPane contentPane; //Split the board panel from the side tabs.

	//Components for chat-panel
	private JTextArea chatJTA; //JTextArea for chat window.
	private JTextField chatBox; //Box for chat-messages.
	private JButton chatBtnSend; //Button for sending messages.

	//Components for import-panel
	private JTextField importJTFFilePath; //Displays file path.

	private JFileChooser importJFCBackground; //FileChooser for background.

	//Buttons for all import-actions.
	private JButton importBtnBackgroundFileChooser;
	private JButton importBtnBackgoundImport;
	private JButton importBtnIconFileChooser;
	private JButton importBtnIconImport;
	
	//Preview for icons.
	private JLabel lblIconPreviewer;
	
	//Preview for backgrounds.
	private JLabel lblBackgroundPreviewer;
	
	//Slider for icon-size.
	private JSlider sldrIconSize;

	//The image path for icons.
	private String imagePath;

	//Width and Height for icons.
	private int iconWidth = 150;
	private int iconHeight = 150;

	//Components for settings-panel

	private JTextField settingsJTFUsername;

	private JButton settingsBtnSave;
	private JButton settingsBtnLeave;
	private JButton settingsBtnResync;
	
	//Components for help-panel
	private JTextArea helpJTA;
	

	/**
	 * Construtor for this class
	 * @param controller Reference to the Controller-class.
	 * @param model Reference to the BoardModel.
	 */
	public MainWindow(Controller controller, BoardModel model) {
		this.controller = controller;
		this.model = model;
		init();
		repaint();
		setVisible(true);
		settingsJTFUsername.setText(controller.username);
		model.setBoard(boardPanel);
	}

	/**
	 * Initialize the window and create bounds for different sections.
	 */
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
		welcomeMsg += "Don't forget to change your username before you start!" + "\n";
		welcomeMsg += "-------------------------------------------" + "\n";
		welcomeMsg += "For more information please go to the Help tab." + "\n";
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
		JPanel settingsGrid = new JPanel(new GridLayout(1, 2)); //Grid is always 2 wide. 
		
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
		
		// ---------- HELP ----------
		JPanel helpPanel = new JPanel(new BorderLayout());
		

		JScrollPane scrollPane12 = new JScrollPane();
		helpJTA = new JTextArea();
		helpJTA.setEditable(false);
		scrollPane12.setViewportView(helpJTA);
		String instructionMsg = "";
		instructionMsg += "Everything you need to know!" + "\n";
		
		instructionMsg += "\n" + "When using the chat:" + "\n";
		
		instructionMsg += "\n" + "* You can send messages by writing them in the text field" + "\n";
		instructionMsg += "  and then send them by pushing the SEND button." + "\n";
		
		instructionMsg += "\n" + "* Sending messages by pressing the return button on the" + "\n";
		instructionMsg += "  keyboard will not result in your message being sent." + "\n";
		
		instructionMsg += "\n" + "* /roll xdy + n - Is used to roll a dice:" + "\n";
		instructionMsg += "  x = number of dices" + "\n";
		instructionMsg += "  d = dice" + "\n";
		instructionMsg += "  y = amount of sides per dice" + "\n";
		instructionMsg += "  n = an amount that will add to the result of the dice throw" + "\n";
		instructionMsg += "  If you don't want to add a value to the result of" + "\n";
		instructionMsg += "  your dice throw you need to write 0 instead of n" + "\n";
		
		instructionMsg += "\n" + "* /dmroll xdy + n - Is used to roll a hidden dice:" + "\n";
		instructionMsg += "  x = number of dices" + "\n";
		instructionMsg += "  d = dice" + "\n";
		instructionMsg += "  y = amount of sides per dice" + "\n";
		instructionMsg += "  n = an amount that will add to the result of the dice throw" + "\n";
		
		instructionMsg += "\n" + "* /rp [name] [text] - Is used for writing message with " + "\n";
		instructionMsg += "  a specific name. This is useful when you want to write " + "\n";
		instructionMsg += "  something as your role playing character. You shouldn't " + "\n";
		instructionMsg += "  keep the square brackets when writing this command." + "\n";

		instructionMsg += "\n" + "* /w [name] [text] - Is used for writing in a private message. "+ "\n";
		instructionMsg += "  You shouldn't keep the square brackets" + "\n";
		instructionMsg += "  when writing this command." + "\n";

		
		instructionMsg += "\n" + "\n" + "When using notes:" + "\n";
		instructionMsg += "\n" + "* Notes are used for making any notations that you think will " + "\n";
		instructionMsg += "   be useful in the future or for writing down " + "\n";
		instructionMsg += "   anything you want to remember. It is basically a note pad." + "\n";
		
		
		instructionMsg += "\n" + "\n" + "When importing/creating:" + "\n";
		instructionMsg += "\n" + "* When importing a background you need to press the " + "\n";
		instructionMsg += "   Choose Background button and locate a picture of your " + "\n";
		instructionMsg += "   choice. You can see the picture in the preview section " + "\n";
		instructionMsg += "   below the buttons." + "\n";
		instructionMsg += "      Secondly you need to press the Load Background " + "\n";
		instructionMsg += "   button to put the picture as a background." + "\n";
		
		instructionMsg += "\n" + "* When importing an icon you need to press the " + "\n";
		instructionMsg += "   Choose Icon button and locate a picture of your choice. " + "\n";
		instructionMsg += "   You can see the picture of your choice in the preview " + "\n";
		instructionMsg += "   section below the buttons." + "\n";
		instructionMsg += "      Secondly you can scale the picture to decide how " + "\n";
		instructionMsg += "   big or small it should be. You are not able to scale " + "\n";
		instructionMsg += "   the picture after you have loaded it into the field." + "\n";
		instructionMsg += "      Finally you need to press the " + "\n";
		instructionMsg += "   Load Image button to put the picture on the field." + "\n";
		
		
		instructionMsg += "\n" + "\n" + "When using settings:" + "\n";
		instructionMsg += "\n" + "* When changing your username you need to write " + "\n";
		instructionMsg += "   your new username in the text field and then press the " + "\n";
		instructionMsg += "   Save settings button." + "\n";
		
		instructionMsg += "\n" + "* If you realize that the program isn't " + "\n";
		instructionMsg += "   synchronized correctly you can press the Resync " + "\n";
		instructionMsg += "   button to update the program according to" + "\n";
		instructionMsg += "   the current board and chat." + "\n";
		
		instructionMsg += "\n" + "* If you want to leave a session you can " + "\n";
		instructionMsg += "   either press the LEAVE SESSION button or " + "\n";
		instructionMsg += "   just close down the program. " + "\n";
		
		instructionMsg += "\n" + "\n" + "interacting with the field:" + "\n";
		instructionMsg += "\n" + "* You can move your icons freely across the " + "\n";
		instructionMsg += "   field but need to keep in mind that the icon that " + "\n";
		instructionMsg += "   was first loaded will always be in front of the " + "\n";
		instructionMsg += "   other icons. This will be the same for all icons as " + "\n";
		instructionMsg += "   in the first being in front of the second, " + "\n";
		instructionMsg += "   the second being in front of the third and so on." + "\n";
		
		instructionMsg += "\n" + "* You can remove icons by pressing  " + "\n";
		instructionMsg += "   Delete Icon button which appears by" + "\n";
		instructionMsg += "   right clicking on the icon." + "\n";

		
		instructionMsg += "\n" + "* You can create, edit, save and delete values " + "\n";
		instructionMsg += "   for each character by right clicking on an icon." + "\n";
		instructionMsg += "   To create a value for an icon you need to press the " + "\n";
		instructionMsg += "   Open Value-menu and then press Add new value." + "\n";
		instructionMsg += "   Then you need to edit the name of the value. " + "\n";
		instructionMsg += "   Important to remember is that you need to left " + "\n";
		instructionMsg += "   click on the other text box before pressing the " + "\n";
		instructionMsg += "   Save & Close button. This should be done after " + "\n";
		instructionMsg += "   filling out the value. You can remove a value by " + "\n";
		instructionMsg += "   selecting it, then pressing the Remove selected value " + "\n";
		instructionMsg += "   and then finally pressing the Save & Close button." + "\n";


		helpJTA.setText(instructionMsg);
		helpPanel.add(scrollPane12, BorderLayout.CENTER);
		
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
		sidePanel.addTab("Help", helpPanel);
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

		ButtonListener listener = new ButtonListener(); //Listener for buttons

		importBtnBackgroundFileChooser.addActionListener(listener);
		importBtnBackgoundImport.addActionListener(listener);

		importBtnIconFileChooser.addActionListener(listener);
		importBtnIconImport.addActionListener(listener);
		
		settingsBtnSave.addActionListener(listener);
		settingsBtnLeave.addActionListener(listener);
		settingsBtnResync.addActionListener(listener);
		
		this.addWindowListener(new wListener());
	}

	/**
	 * Method for appending chat line to the chat JTextArea.
	 * @param line The line to be appended.
	 */
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

	/**
	 * Inner-class for the ButtonListener which handles all button-functionalities.
	 * @author Patrik Skuza, Oscar Strandmark, Andreas JÃ¶nsson, Haris Obradovac
	 *
	 */
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
				controller.state = STATES.LOBBY;
			}
			
			//Resync request
			if(e.getSource() == settingsBtnResync) {
				controller.pushActionToServer(new BoardResyncRequestAction(controller.username));
			}
		}
	}
	
	/**
	 * Inner-class for quit-action of the program.
	 * @author Oscar Strandmark
	 *
	 */
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
