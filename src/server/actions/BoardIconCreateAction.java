package server.actions;

import javax.swing.ImageIcon;

/**
 * @author Oscar Strandmark
 */
public class BoardIconCreateAction extends Action {
	
	private ImageIcon img;
	
	public BoardIconCreateAction(String username,ImageIcon img) {
		super(username);
		this.img = img;
	}

	public ImageIcon getImage() {
		return img;
	}
}
