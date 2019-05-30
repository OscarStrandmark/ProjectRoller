package server.actions;

import javax.swing.ImageIcon;

/**
 * Action for creating an icon on the board.
 * 
 * @author Oscar Strandmark
 */
public class BoardIconCreateAction extends Action {
	
	private static final long serialVersionUID = -3340115659376244409L;
	
	private ImageIcon img;
	
	public BoardIconCreateAction(String username,ImageIcon img) {
		super(username);
		this.img = img;
	}

	public ImageIcon getImage() {
		return img;
	}
}