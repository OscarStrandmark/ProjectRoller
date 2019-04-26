package server.actions;

import shared.CharacterIcon;

public class RemoveIconAction extends Action {

	private static final long serialVersionUID = -350659979316418070L;
	private CharacterIcon icon;

	/**
	 * Create an action symbolizing the removal of an icon on the board.
	 * @param username
	 */
	public RemoveIconAction(String username, CharacterIcon icon) {
		super(username);
		this.icon = icon;
	}

	public CharacterIcon getIcon() {
		return icon;
	}

}
