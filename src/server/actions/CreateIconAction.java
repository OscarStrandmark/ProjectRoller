package server.actions;

import shared.CharacterIcon;

public class CreateIconAction extends Action {

	private static final long serialVersionUID = -6675179254094731000L;
	private CharacterIcon icon;

	/**
	 * Create an action that symbolizes the creation of an icon.
	 * @param username
	 * @param icon
	 */
	public CreateIconAction(String username, CharacterIcon icon) {
		super(username);
		this.icon = icon;
	}

	public CharacterIcon getIcon() {
		return icon;
	}
}
