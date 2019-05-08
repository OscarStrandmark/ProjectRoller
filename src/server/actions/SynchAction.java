package server.actions;

import java.util.HashMap;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import shared.CharacterIcon;

public class SynchAction extends Action {

	private static final long serialVersionUID = -3002835788021675436L;
	
	private HashMap<ImageIcon,CharacterIcon> map;
	private ImageIcon background;
	
	public SynchAction(String username,HashMap<ImageIcon,CharacterIcon> map, ImageIcon background) {
		super(username);
		this.map = map;
		this.background = background;
	}

	public HashMap<ImageIcon, CharacterIcon> getMap() {
		return map;
	}

	public ImageIcon getBackground() {
		return background;
	}

	public void setMap(HashMap<ImageIcon, CharacterIcon> map) {
		this.map = map;
	}

	public void setBackground(ImageIcon background) {
		this.background = background;
	}
}
