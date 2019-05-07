package server.actions;

import java.util.HashMap;

import javax.swing.JLabel;

import shared.CharacterIcon;

public class SynchAction extends Action {

	private static final long serialVersionUID = -3002835788021675436L;
	
	private HashMap<JLabel,CharacterIcon> map;
	private JLabel background;
	
	public SynchAction(String username,HashMap<JLabel,CharacterIcon> map, JLabel background) {
		super(username);
		this.map = map;
		this.background = background;
	}

	public HashMap<JLabel, CharacterIcon> getMap() {
		return map;
	}

	public JLabel getBackground() {
		return background;
	}

	public void setMap(HashMap<JLabel, CharacterIcon> map) {
		this.map = map;
	}

	public void setBackground(JLabel background) {
		this.background = background;
	}
}
