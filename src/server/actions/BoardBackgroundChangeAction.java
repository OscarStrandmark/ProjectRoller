package server.actions;

import javax.swing.ImageIcon;
/**
 * Action for changing the background on the board.
 * 
 * @author Oscar Strandmark
 */
public class BoardBackgroundChangeAction extends Action {
	
	private static final long serialVersionUID = -3999502429197137657L;
	
	private ImageIcon img;
	
	public BoardBackgroundChangeAction(String username, ImageIcon img) {
		super(username);
		this.img = img;
	}

	public ImageIcon getImage() {
		return img;
	}
}