package server;

import java.util.ArrayList;

import javax.swing.ImageIcon;

import shared.CharacterIcon;
import shared.Value;

/**
 * Class that represents the board and its content on the server.
 * Two diffrent classes are used for the model since nothing needs to be drawn on the board serverside.
 * @author Oscar Strandmark
 */
public class ServerBoardModel {
	
	private ImageIcon background;	
	private ArrayList<ImageIcon> iconList;
	private ArrayList<CharacterIcon> charList;

	
	public ServerBoardModel() {
		this.charList = new ArrayList<CharacterIcon>();
		this.iconList = new ArrayList<ImageIcon>();
	}
	
	public void setBackground(ImageIcon img) {
		background = img;
	}
	
	public ImageIcon getBackground(ImageIcon img) {
		return background;
	}
	
	public void addIcon(ImageIcon img) {
		iconList.add(img);
		charList.add(new CharacterIcon(img));
	}
	
	public void moveIcon(int index, int x, int y) {
		charList.get(index).setPosition(x, y);
		
	}
	
	public void removeIcon(int index) {
		iconList.remove(index);
		charList.remove(index);
	}
	
	public void setValue(int index, ArrayList<Value> list) {
		CharacterIcon icon = charList.get(index);
		icon.setValues(list);
	}
	
	public CharacterIcon lookup(int index) {
		return charList.get(index);
	}
	
	public ImageIcon getBackground() {
		return background;
	}
	
	public ArrayList<ImageIcon> getIcons() {
		return iconList;
	}
	
	public ArrayList<CharacterIcon> getChars() {
		return charList;
	}
}
