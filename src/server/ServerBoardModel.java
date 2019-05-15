package server;

import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.ImageIcon;

import shared.CharacterIcon;
import shared.Value;

public class ServerBoardModel {

	private Session session;
	
	private ImageIcon background;	
	private ArrayList<ImageIcon> iconList;
	private ArrayList<CharacterIcon> charList;

	
	public ServerBoardModel(Session session) {
		this.charList = new ArrayList<CharacterIcon>();
		this.iconList = new ArrayList<ImageIcon>();
		this.session = session;
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
