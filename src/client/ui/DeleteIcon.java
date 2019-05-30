package client.ui;

import java.awt.Component;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

import client.BoardModel;

/**
 * Class for one of the buttons in the context-menu for icons on the playing field.
 * 
 * @author Patrik Skuza
 */
public class DeleteIcon extends AbstractAction {

	private static final long serialVersionUID = 6735193266973169483L;

	private Component c;
	private BoardModel model;
	
	public DeleteIcon(Component c, BoardModel model) {
		super("Delete Icon");
		this.model = model;
		this.c = c;
	}

	public void actionPerformed(ActionEvent e) {
		model.sendIconRemove((ImageIcon)((JLabel)c).getIcon());
	}
}
