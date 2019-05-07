package client.ui;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import client.Controller;
import server.actions.SessionCreateAction;

/**
 * Class for the window where the user creates a new session.	
 * 
 * @author Oscar Strandmark
 */
public class NewSessionWindow extends JFrame {

	private static final long serialVersionUID = -8342709913608792277L;

	private JPanel pnlContent;
	
	private JTextField jtfName;
	private JTextField jtfPassword;
	private JNumberTextField jntfMaxPlayers;
	
	private JButton btnCreate;
	private JButton btnCancel;
	
	private Controller controller;
	
	public NewSessionWindow(Controller controller) {
		this.controller = controller;
		init();
	}
	
	private void init()	{
		//Set important values
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setResizable(false);
		setTitle("Create new session");
		
		//Create objects
		pnlContent = new JPanel(new GridLayout(4,2));
		jtfName = new JTextField();
		jtfPassword = new JTextField();
		jntfMaxPlayers = new JNumberTextField();
		btnCreate = new JButton("Create Session");
		btnCancel = new JButton("Cancel");
		
		//Configure objects
		jtfName.setColumns(15);
		jtfPassword.setColumns(15);
		
		//Add to panel
		pnlContent.add(new JLabel("SESSION NAME:"));
		pnlContent.add(jtfName);
		
		pnlContent.add(new JLabel("SESSION PASSWORD"));
		pnlContent.add(jtfPassword);
		
		pnlContent.add(new JLabel("MAX PLAYERS"));
		pnlContent.add(jntfMaxPlayers);
		
		pnlContent.add(btnCreate);
		pnlContent.add(btnCancel);
		
		//Button ActionListeners
		btnCreate.addActionListener(e -> create());
		btnCancel.addActionListener(e -> dispose());
		
		//Final calls
		setContentPane(pnlContent);
		pack();
		setVisible(true);
	}
	
	private void create() {
		
		if(jtfName.getText().length() > 0 && jntfMaxPlayers.getText().length() > 0) {
			String name = jtfName.getText();
			String pass = jtfPassword.getText();
			int max_players = Integer.parseInt(jntfMaxPlayers.getText());

			if(jtfPassword.getText().length() > 0) {
				controller.pushActionToServer(new SessionCreateAction(controller.username, name, max_players, pass));
			} else {
				controller.pushActionToServer(new SessionCreateAction(controller.username, name, max_players));
			}
			dispose();
		} else {
			JOptionPane.showMessageDialog(this, "Name & max players must exist", "ERROR", JOptionPane.ERROR_MESSAGE);
		}
	
	}
}
