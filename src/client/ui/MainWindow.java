package client.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;

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
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

import client.Controller;
import server.actions.QuitAction;
import server.actions.SessionLeaveAction;
import server.actions.UsernameChangeAction;
import shared.BoardModel;
import shared.CharacterIcon;

public class MainWindow extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Controller controller;

	private BoardModel boardModel;
	private CharacterIcon characterIcon;

	private int iconNumber = 0;

	private HashMap<String, Component> componentMap;

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

	JLabel iconicon;
	JLabel backgroundIcon;

	private int iconWidth = 150;
	private int iconHeight = 150;

	//Components for settings-panel

	private JTextField settingsJTFUsername;

	private JButton settingsBtnSave;
	private JButton settingsBtnLeave;

	public MainWindow(Controller controller) {
		this.controller = controller;
		init();
		repaint();
		setVisible(true);
		//		IconMovement iconMovement = new IconMovement(getComponents());
		settingsJTFUsername.setText(controller.username);
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
		
		//TODO:Settings n stuff
		
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
		importBtnBackgoundImport.addActionListener(listener);

		importBtnIconFileChooser.addActionListener(e -> openFileChooser());
		importBtnIconImport.addActionListener(e -> setIconImage());
		
		settingsBtnSave.addActionListener(listener);
		settingsBtnLeave.addActionListener(listener);
		
		this.addWindowListener(new wListener());
	}

	public void appendChatLine(String line) {
		System.out.println("append");
		String current = chatJTA.getText();
		chatJTA.setText(current + line + "\n");
	}
	
	/**
	 * When the "Choose Image"-button is clicked, show a FileChooser from which
	 * a user can choose an image for an icon
	 */
	public void openFileChooser() {
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

	/**
	 * When the "Import icon"-button is clicked, rescale the image
	 * and show it on the gameboard.
	 */
	public void setIconImage() {
		getScaleInput();
		ImageIcon icon = new ImageIcon(imagePath);
		Image image = icon.getImage();
		Image newIconImage = getScaledImage(image, iconWidth, iconHeight);
		ImageIcon finalIcon = new ImageIcon(newIconImage);
		iconicon = new JLabel(finalIcon);
		iconicon.setBounds(0,0,iconWidth,iconHeight);
		boardPanel.add(iconicon);
		//H�r kan man k�ra .setName och p� s� s�tt prioritera ikoner.
		boardPanel.setComponentZOrder(iconicon, 0);
		System.out.println("Icon: " + boardPanel.getComponentZOrder(iconicon));
		System.out.println("B: " + boardPanel.getComponentZOrder(backgroundIcon));
		boardPanel.repaint();
		iconicon.addMouseListener(new IconMovement());
		iconicon.addMouseMotionListener(new IconMovement());
		iconNumber++;
		System.out.println("Amount of icons: " + iconNumber);
	}

	/**
	 * Scales the image by input from user. If no input is made,
	 * make the icon the standard size of 150 by 150 pixels.
	 */
	public void getScaleInput() {
		if(scaleIcon.getText().length() == 0) {
		} else if(Integer.parseInt(scaleIcon.getText()) < 0 || Integer.parseInt(scaleIcon.getText()) > 15 ) {
			JOptionPane.showMessageDialog(null, "Enter a valid number between 0-15,"
					+ " or leave the field empty for a standard size of 150 by 150 "
					+ "pixels icon.");
		}else if(Integer.parseInt(scaleIcon.getText()) >= 1 && Integer.parseInt(scaleIcon.getText()) <= 15 ) {
			int amountOfScale = Integer.parseInt(scaleIcon.getText());
			iconWidth = amountOfScale * 20;
			iconHeight = amountOfScale * 20;
		} else {
			JOptionPane.showMessageDialog(null, "Enter a valid number between 0-15,"
					+ " or leave the field empty for a standard size of 150 by 150 "
					+ "pixels icon.");
		}
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

	private void setBoardBackground(Image img) {
		Graphics g = boardPanel.getGraphics();
		g.drawImage(img, 0, 0, null);
	}

	private void createComponentMap(JFrame frame) {
		componentMap = new HashMap<String,Component>();
		Component[] components = frame.getContentPane().getComponents();
		for (int i=0; i < components.length; i++) {
			componentMap.put(components[i].getName(), components[i]);
		}
	}

	private class ButtonListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == chatBtnSend) {
				controller.handleMessage(chatBox.getText());
				chatBox.setText("");
			}

			if(e.getSource() == importBtnBackgroundFileChooser) {
				importJFCBackground.showOpenDialog(null);

				File file = importJFCBackground.getSelectedFile();

				importJTFFilePath.setText(file.getPath());
			}

			if(e.getSource() == importBtnBackgoundImport) {
				ImageIcon background = new ImageIcon(importJTFFilePath.getText());
				Image backgroundImage = background.getImage();
				Image newBackgroundImage = getScaledImage(backgroundImage, boardPanel.getWidth(), boardPanel.getHeight()); //mer parametrar i metoden
				ImageIcon finalBackground = new ImageIcon(newBackgroundImage);
				backgroundIcon = new JLabel(finalBackground);
				backgroundIcon.setBounds(0,0,boardPanel.getWidth(), boardPanel.getHeight());
				boardPanel.add(backgroundIcon);
				backgroundPriorityLogic(backgroundIcon);
				boardPanel.repaint();
			} 
			
			if(e.getSource() == settingsBtnSave) {
				String username = settingsJTFUsername.getText();
				if(username != controller.username) {
					controller.pushActionToServer(new UsernameChangeAction(controller.username, username));
					controller.username = username;
				}
			}
			
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

	private void backgroundPriorityLogic(JLabel img) {
		int nr = 0;
		String backgroundName = "background" + String.valueOf(nr);
		img.setName(backgroundName);
		System.out.println("Aktuell ikon har: " + boardPanel.getComponentZOrder(iconicon));
		boardPanel.setComponentZOrder(img, nr + iconNumber);

		nr++;
	}

	private class IconMovement implements MouseListener, MouseMotionListener {
		private int x,y;

		@Override
		public void mouseDragged(MouseEvent event) {
			event.getComponent().setLocation((event.getX() + event.getComponent().getX()-x), (event.getY() + event.getComponent().getY()-y));
		}

		public void mouseMoved(MouseEvent event) {}
		public void mouseClicked(MouseEvent event) {}

		@Override
		public void mousePressed(MouseEvent event) {
			x = event.getX();
			y = event.getY();
		}

		@Override
		public void mouseReleased(MouseEvent event) {
			if(event.getButton() == 3) {
				PopAltMenu popMenu = new PopAltMenu();
				popMenu.show(event.getComponent(), event.getX(), event.getY());
			}
		}
		
		public void mouseEntered(MouseEvent e) {}
		public void mouseExited(MouseEvent e) {}
	}

	private class PopAltMenu extends JPopupMenu {
		private static final long serialVersionUID = 1L;

		public PopAltMenu() {
			JMenuItem altMenu = new JMenuItem(new ClickAltMenu());
			add(altMenu);

		}
	}

	private class ClickAltMenu extends AbstractAction{
		private static final long serialVersionUID = 1L;

		public ClickAltMenu() {
			super("Open Value-menu");
		}

		public void actionPerformed(ActionEvent e) {
			BuildAltMenu bam = new BuildAltMenu();
		}
	}

	private class BuildAltMenu {
		JFrame frame = new JFrame("Values for icon");
		JTable table = new JTable();

		JButton btnAdd = new JButton("Add");
		JButton btnDelete = new JButton("Delete");
		JButton btnUpdate = new JButton("Update"); 

		JTextField textId = new JTextField();
		JTextField textName = new JTextField();
		JTextField textValue = new JTextField();

		JLabel lblId = new JLabel("Insert Id:");
		JLabel lblName = new JLabel("Insert Name:");
		JLabel lblValue = new JLabel("Insert Value:");

		JScrollPane pane = new JScrollPane(table);

		public BuildAltMenu() {
			Object[] columns = {"Id","Name","Value"};
			DefaultTableModel model = new DefaultTableModel();
			model.setColumnIdentifiers(columns);

			table.setModel(model);
			table.setBackground(Color.LIGHT_GRAY);
			table.setForeground(Color.black);
			Font font = new Font("",1,22);
			table.setFont(font);
			table.setRowHeight(30);

			lblId.setBounds(20, 220, 80, 25);
			lblName.setBounds(20, 265, 80, 25);
			lblValue.setBounds(20, 310, 80, 25);

			textId.setBounds(120, 220, 100, 25);
			//med 30 p� Y
			textName.setBounds(120, 265, 100, 25);
			textValue.setBounds(120, 310, 100, 25);

			btnAdd.setBounds(250, 220, 100, 25);
			btnUpdate.setBounds(250, 265, 100, 25);
			btnDelete.setBounds(250, 310, 100, 25);

			pane.setBounds(0, 0, 880, 200);
			frame.setLayout(null);
			frame.add(pane);

			frame.add(lblId);
			frame.add(lblName);
			frame.add(lblValue);

			frame.add(textId);
			frame.add(textName);
			frame.add(textValue);

			frame.add(btnAdd);
			frame.add(btnDelete);
			frame.add(btnUpdate);

			Object[] row = new Object[3];

			btnAdd.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e) {

					row[0] = textId.getText();
					row[1] = textName.getText();
					row[2] = textValue.getText();

					// add row to the model
					model.addRow(row);
					System.out.println("Add!");
				}
			});

			btnDelete.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e) {
					// i = the index of the selected row
					int i = table.getSelectedRow();
					if(i >= 0){
						// remove a row from jtable
						model.removeRow(i);
					}
					else{
						System.out.println("Delete Error");
					}
				}
			});

			table.addMouseListener(new MouseAdapter(){
				@Override
				public void mouseClicked(MouseEvent e){
					// i = the index of the selected row
					int i = table.getSelectedRow();
					textId.setText(model.getValueAt(i, 0).toString());
					textName.setText(model.getValueAt(i, 1).toString());
					textValue.setText(model.getValueAt(i, 2).toString());
				}
			});

			btnUpdate.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent e) {           
					// i = the index of the selected row
					int i = table.getSelectedRow();
					if(i >= 0) 
					{
						model.setValueAt(textId.getText(), i, 0);
						model.setValueAt(textName.getText(), i, 1);
						model.setValueAt(textValue.getText(), i, 2);
					}
					else{
						System.out.println("Update Error");
					}
				}
			});

			frame.setSize(900,400);
			frame.setLocationRelativeTo(null);
			frame.setVisible(true);
		}
	}
}