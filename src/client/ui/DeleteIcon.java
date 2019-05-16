package client.ui;

import java.awt.Component;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

import client.BoardModel;

public class DeleteIcon extends AbstractAction {

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
