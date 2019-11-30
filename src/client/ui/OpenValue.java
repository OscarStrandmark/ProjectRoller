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
public class OpenValue extends AbstractAction {

	private static final long serialVersionUID = -2181704034224328381L;

	private Component c;
	private BoardModel model;
	
	//Adds text to the alternative in the right-click context-menu for icons.
	public OpenValue(Component c, BoardModel model) {
		super("Open Value-menu");
		this.model = model;
		this.c = c;
	}

	public void actionPerformed(ActionEvent e) {
		new ValueWindow(model,  (ImageIcon)((JLabel)c).getIcon() );
	}
}
