package server.actions;

import javax.swing.ImageIcon;

public class BoardBackgroundChangeAction extends Action {

	private static final long serialVersionUID = -4957981042216254767L;
	
	private ImageIcon img;
	
	public BoardBackgroundChangeAction(String username, ImageIcon img) {
		super(username);
		this.img = img;
	}

	public ImageIcon getImage() {
		return img;
	}
}
