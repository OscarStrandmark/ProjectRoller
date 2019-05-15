package client.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
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
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.filechooser.FileNameExtensionFilter;

import client.Controller;
import server.actions.BoardBackgroundChangeAction;
import server.actions.QuitAction;
import server.actions.SessionLeaveAction;
import server.actions.UsernameChangeAction;
import shared.BoardModel;

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

	private JTextField scaleIcon;

	private String imagePath;

	private JLabel backgroundIcon;

	private int iconWidth = 150;
	private int iconHeight = 150;

	//Components for settings-panel

	private JTextField settingsJTFUsername;

	private JButton settingsBtnSave;
	private JButton settingsBtnLeave;

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
		JPanel importPanel = new JPanel(new GridLayout(3, 1));

		JPanel importIconPane = new JPanel();
		importIconPane.setLayout(new GridLayout(2,2));

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

		// ------- IMPORT ICON --------

		scaleIcon = new JTextField();
		JLabel setSizeIcon = new JLabel("Set size for Icon (1-15):", SwingConstants.CENTER);

		Border borderImport = BorderFactory.createLineBorder(Color.black);

		importBtnIconFileChooser = new JButton("Choose Image");
		importBtnIconImport = new JButton("Import Icon");

		importIconPane.add(importBtnIconFileChooser);
		importIconPane.add(importBtnIconImport);
		importIconPane.add(setSizeIcon);
		importIconPane.add(scaleIcon);
		importIconPane.setBorder(borderImport);

		// ---------- SETTINGS ----------
		JPanel settingsPanel = new JPanel();
		JPanel settingsGrid = new JPanel(new GridLayout(1, 2)); //Grid is always 2 wide and 
		
		settingsBtnSave = new JButton("Save settings");
		settingsBtnLeave = new JButton("LEAVE SESSION");

		settingsJTFUsername = new JTextField();
		settingsJTFUsername.setColumns(15);
		
		settingsGrid.add(new JLabel("Username:"));
		settingsGrid.add(settingsJTFUsername);
				
		settingsPanel.add(settingsGrid);
		settingsPanel.add(settingsBtnSave);
		settingsPanel.add(settingsBtnLeave);
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
			}
			
			//Import the chosen file for icon import. 
			if(e.getSource() == importBtnIconImport) {
				
				boolean scaled = false;
				
				if(scaleIcon.getText().length() != 0) {
					int scale = Integer.parseInt(scaleIcon.getText());
					
					if( scale < 0 || scale > 15 ) {
						JOptionPane.showMessageDialog(null, "Enter a valid number between 0-15, or leave the field empty for a standard size of 150 by 150 ");
					} else if(scale >= 1 && scale <= 15 ) {
						iconWidth = scale * 20;
						iconHeight = scale * 20;
						scaled = true;
					}
				} else {
					JOptionPane.showMessageDialog(null, "Enter a valid number between 0-15, or leave the field empty for a standard size of 150 by 150 ");
				}
				
				if(scaled) {
					
					Image image = new ImageIcon(imagePath).getImage();
					Image newIconImage = getScaledImage(image, iconWidth, iconHeight);
					
					JLabel icon = new JLabel(new ImageIcon(newIconImage));
					icon.setBounds(0,0,iconWidth,iconHeight);
					boardPanel.add(icon);
					model.addIcon(icon);
					boardPanel.repaint();
					icon.addMouseListener(new IconMovement());
					icon.addMouseMotionListener(new IconMovement());
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

	private class IconMovement implements MouseListener, MouseMotionListener {
		private int x,y;
		private JLabel c;
		
		public void mouseDragged(MouseEvent event) {
			event.getComponent().setLocation((event.getX() + event.getComponent().getX()-x), (event.getY() + event.getComponent().getY()-y));
		}

		public void mousePressed(MouseEvent event) {
			x = event.getX();
			y = event.getY();
			c = (JLabel) event.getComponent();
		}

		public void mouseReleased(MouseEvent event) {
			
			if(event.getButton() == 1 && c != null) {
				
				//TODO: push move action.
				c = null;
			}
			
			if(event.getButton() == 3) {
				PopAltMenu popMenu = new PopAltMenu(event.getComponent());
				popMenu.show(event.getComponent(), event.getX(), event.getY());
			}
		}
		
		public void mouseMoved(MouseEvent event) {}
		public void mouseClicked(MouseEvent event) {}
		public void mouseEntered(MouseEvent e) {}
		public void mouseExited(MouseEvent e) {}
	}

	private class PopAltMenu extends JPopupMenu {
		private static final long serialVersionUID = 1L;

		public PopAltMenu(Component c) {
			JMenuItem altMenuValue = new JMenuItem(new OpenValue(c));
			add(altMenuValue);
			
			JMenuItem altMenuDelete = new JMenuItem(new DeleteIcon(c));
			add(altMenuDelete);

		}
	}

	private class OpenValue extends AbstractAction {

		private Component c;

		public OpenValue(Component c) {
			super("Open Value-menu");
			this.c = c;
		}

		public void actionPerformed(ActionEvent e) {
			new ValueWindow(model, (JLabel)c);
		}
	}
	
	private class DeleteIcon extends AbstractAction {

		private Component c;
		
		public DeleteIcon(Component c) {
			super("Delete Icon");
			this.c = c;
		}

		public void actionPerformed(ActionEvent e) {
			model.removeIcon((JLabel)c);
		}
	}
}