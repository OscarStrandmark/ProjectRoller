package server.actions;

import javax.swing.ImageIcon;

public class BackgroundChangeAction extends Action {

	private static final long serialVersionUID = -2082787213471001222L;
	private ImageIcon background;

	public BackgroundChangeAction(String username, ImageIcon img) {
		super(username);
		this.background = img;
	}

	public ImageIcon getImage() {
		return background;
	}

}
