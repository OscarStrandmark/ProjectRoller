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
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.filechooser.FileNameExtensionFilter;

import client.Controller;

public class MainWindow extends JFrame {

	private Controller controller;

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

	private int iconWidth = 150;
	private int iconHeight = 150;

	//Components for settings-panel


	public MainWindow(Controller controller) {
		this.controller = controller;
		init();
		repaint();
		setVisible(true);
//		IconMovement iconMovement = new IconMovement(getComponents());
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
		JLabel setSizeIcon = new JLabel("Set size for Icon (1-15)", SwingConstants.CENTER);

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
		boardPanel.repaint();
		iconicon.addMouseListener(new IconMovement());
		iconicon.addMouseMotionListener(new IconMovement());
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

	private class IconMovement implements MouseListener, MouseMotionListener {
		private int x,y;

//		public IconMovement(Component... pns) {
//			for(Component iconicon : pns) {
//				iconicon.addMouseListener(this);
//				iconicon.addMouseMotionListener(this);
//			}
//		}

		@Override
		public void mouseDragged(MouseEvent event) {
				event.getComponent().setLocation((event.getX() + event.getComponent().getX())-x, (event.getY() + event.getComponent().getX())-y);
			}

		@Override
		public void mouseMoved(MouseEvent event) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseClicked(MouseEvent event) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mousePressed(MouseEvent event) {
				x = event.getX();
				y = event.getY();
			}

		@Override
		public void mouseReleased(MouseEvent event) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub

		}

	}
}