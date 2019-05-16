package server.actions;

import java.util.ArrayList;

import javax.swing.ImageIcon;

import shared.CharacterIcon;

/**
 * @author Oscar Strandmark
 */
public class BoardResyncAction extends Action {
	
	private ImageIcon background;
	private ArrayList<CharacterIcon> charList;
	private ArrayList<ImageIcon> iconList;
	
	public BoardResyncAction(String username, ImageIcon background, ArrayList<CharacterIcon> charList, ArrayList<ImageIcon> iconList) {
		super(username);
		this.background = background;
		this.charList = charList;
		this.iconList = iconList;
	}

	public ImageIcon getBackground() {
		return background;
	}

	public ArrayList<CharacterIcon> getCharList() {
		return charList;
	}

	public ArrayList<ImageIcon> getIconList() {
		return iconList;
	}

}
