package client.ui;

import java.awt.BorderLayout;
import java.awt.Color;
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

	// Components used in main window.
	private JScrollPane boardPanel;
	private JTabbedPane sidePanel;
	private JSplitPane contentPane;

	// Components for chat-panel
	private JTextArea chatJTA;
	private JTextField chatBox;
	private JButton chatBtnSend;

	// Components for import-panel
	private JTextField importJTFFilePath;

	private JFileChooser importJFCBackground;

	private JButton importBtnBackgroundFileChooser;
	private JButton importBtnBackgoundImport;
	private JButton importBtnIconFileChooser;
	private JButton importBtnIconImport;

	private JTextField scaleIcon;

	private String imagePath;

	private JLabel iconicon;

	private int iconWidth = 150;
	private int iconHeight = 150;

	// Components for settings-panel
	private JLabel settingsLblUsername;
	private JTextField settingsJTFUsername;
	private JButton settingsBtnSave;

	public MainWindow(Controller controller) {
		this.controller = controller;
		init();
		repaint();
		setVisible(true);
	}

	private void init() {
		setTitle("Project Roller");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		setVisible(true);
		setExtendedState(JFrame.MAXIMIZED_BOTH);

		// Code below sets the board-view to always be 80% of the screen.
		addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent e) {
				Rectangle r = getBounds();
				int w = (int) (r.width * 0.8);
				try {
					contentPane.setDividerLocation(w);

				} catch (Exception e2) {
				}
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
		chatPanel.add(textBoxPane, BorderLayout.SOUTH);

		// ---------- NOTES ----------
		JScrollPane notePanel = new JScrollPane();

		JTextArea noteJTA = new JTextArea();
		notePanel.setViewportView(noteJTA);

		// ---------- IMPORT ----------
		JPanel importPanel = new JPanel(new GridLayout(3, 1));

		// Panel for importing an icon
		JPanel importIconPane = new JPanel();
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

		// Panel for importing a background.
		JPanel importBGPane = new JPanel(new GridLayout(4, 1)); // Panel for importing a background.
		importBGPane.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1)); // Creates a border for the panel

		importJTFFilePath = new JTextField();
		importJTFFilePath.setEditable(false);
		importJTFFilePath.setText("File path ");

		importJFCBackground = new JFileChooser();
		importBtnBackgoundImport = new JButton("Import");
		importBtnBackgroundFileChooser = new JButton("Pick file");

		importJFCBackground.setFileSelectionMode(JFileChooser.FILES_ONLY);
		importJFCBackground.setFileFilter(new FileNameExtensionFilter("Image files", ImageIO.getReaderFileSuffixes())); // Accept
																														// all
																														// image
																														// files
																														// supported
																														// on
																														// the
																														// system
																														// the
																														// program
																														// is
																														// ran
																														// on.

		importBGPane.add(new JLabel("Import an image as a background", SwingConstants.CENTER));
		importBGPane.add(importBtnBackgroundFileChooser);
		importBGPane.add(importJTFFilePath);
		importBGPane.add(importBtnBackgoundImport);

		JPanel importSaveLoadSessionPane = new JPanel(); // For future implementation of importing saved session data.

		importPanel.add(importIconPane);
		importPanel.add(importBGPane);
		importPanel.add(importSaveLoadSessionPane);

		// ---------- SETTINGS ----------
		JPanel settingsPanel = new JPanel(new GridLayout(15, 1));
		JPanel usernamePanel = new JPanel(new GridLayout(1, 2));
		usernamePanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

		settingsLblUsername = new JLabel();
		settingsLblUsername.setText("Username: ");

		settingsJTFUsername = new JTextField();

		settingsBtnSave = new JButton("Save");

		settingsPanel.add(usernamePanel);

		usernamePanel.add(settingsLblUsername);
		usernamePanel.add(settingsJTFUsername);
		settingsPanel.add(settingsBtnSave);

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
		add(contentPane, BorderLayout.CENTER);

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
	}

	private void setBoardBackground(Image img) {
		Graphics g = boardPanel.getGraphics();
		g.drawImage(img, 0, 0, null);
	}

	/**
	 * Scales the image by input from user. If no input is made, make the icon the
	 * standard size of 150 by 150 pixels.
	 */
	private void getScaleInput() {
		if (scaleIcon.getText().length() == 0) {
		} else if (Integer.parseInt(scaleIcon.getText()) < 0 || Integer.parseInt(scaleIcon.getText()) > 15) {
			JOptionPane.showMessageDialog(null,
					"Enter a valid number between 0-15 or leave the field empty for a standard size of 150 by 150 pixels icon.");
		} else if (Integer.parseInt(scaleIcon.getText()) >= 1 && Integer.parseInt(scaleIcon.getText()) <= 15) {
			int amountOfScale = Integer.parseInt(scaleIcon.getText());
			iconWidth = amountOfScale * 20;
			iconHeight = amountOfScale * 20;
		} else {
			JOptionPane.showMessageDialog(null,
					"Enter a valid number between 0-15, or leave the field empty for a standard size of 150 by 150 pixels icon.");
		}
	}

	/**
	 * Scales the chosen image to a set size.
	 *
	 * @param srcImg the image to be scaled
	 * @param w      the width of the image
	 * @param h      the height of the image
	 * @return return the new scaled image
	 */
	private Image getScaledImage(Image srcImg, int w, int h) {
		BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = resizedImg.createGraphics();
		g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g2.drawImage(srcImg, 0, 0, w, h, null);
		g2.dispose();
		return resizedImg;
	}

	private class ButtonListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == chatBtnSend) {
				controller.newChatMessage(chatBox.getText());
				chatBox.setText("");
			}

			if (e.getSource() == importBtnBackgroundFileChooser) {
				importJFCBackground.showOpenDialog(null);

				File file = importJFCBackground.getSelectedFile();

				importJTFFilePath.setText(file.getPath());
			}

			if (e.getSource() == importBtnBackgoundImport) {
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

			if (e.getSource() == importBtnIconFileChooser) {
				JFileChooser fileChooser = new JFileChooser();
				imagePath = null;

				// Filter for what files to show in FileChooser-window.
				FileNameExtensionFilter imageFilter = new FileNameExtensionFilter("Image files",
						ImageIO.getReaderFileSuffixes());
				fileChooser.setFileFilter(imageFilter);
				fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

				int fileOk = fileChooser.showOpenDialog(null);
				if (fileOk == JFileChooser.APPROVE_OPTION) {
					imagePath = fileChooser.getSelectedFile().getPath();
				}

				if (e.getSource() == importBtnIconImport) {
					getScaleInput();
					ImageIcon icon = new ImageIcon(imagePath);
					Image image = icon.getImage();
					Image newIconImage = getScaledImage(image, iconWidth, iconHeight);
					ImageIcon finalIcon = new ImageIcon(newIconImage);
					JLabel iconicon = new JLabel(finalIcon);
					iconicon.setBounds(0, 0, iconWidth, iconHeight);
					boardPanel.add(iconicon);
					boardPanel.repaint();
					iconicon.addMouseListener(new IconMovement());
					iconicon.addMouseMotionListener(new IconMovement());
				}

				if (e.getSource() == settingsBtnSave) {
					// TODO
				}
			}
		}
	}

	private class IconMovement implements MouseListener, MouseMotionListener {
		private int x, y;

		// public IconMovement(Component... pns) {
		// for(Component iconicon : pns) {
		// iconicon.addMouseListener(this);
		// iconicon.addMouseMotionListener(this);
		// }
		// }

		@Override
		public void mouseDragged(MouseEvent event) {
			event.getComponent().setLocation((event.getX() + event.getComponent().getX()) - x,
					(event.getY() + event.getComponent().getX()) - y);
		}

		@Override
		public void mouseMoved(MouseEvent event) {
		}

		@Override
		public void mouseClicked(MouseEvent event) {
		}

		@Override
		public void mousePressed(MouseEvent event) {
			x = event.getX();
			y = event.getY();
		}

		@Override
		public void mouseReleased(MouseEvent event) {
		}

		@Override
		public void mouseEntered(MouseEvent e) {
		}

		@Override
		public void mouseExited(MouseEvent e) {
		}
	}
}
