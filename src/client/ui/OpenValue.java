package client.ui;

import java.awt.Component;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

import client.BoardModel;

public class OpenValue extends AbstractAction {

	private Component c;
	private BoardModel model;
	
	public OpenValue(Component c, BoardModel model) {
		super("Open Value-menu");
		this.model = model;
		this.c = c;
	}

	public void actionPerformed(ActionEvent e) {
		new ValueWindow(model,  (ImageIcon)((JLabel)c).getIcon() );
	}
}
