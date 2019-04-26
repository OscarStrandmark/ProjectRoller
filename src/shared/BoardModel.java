package shared;

import java.util.ArrayList;

import javax.swing.ImageIcon;

import client.Controller;
import server.Session;
import server.actions.Action;
import server.actions.BackgroundChangeAction;
import server.actions.ChangeIconValueAction;
import server.actions.CreateIconAction;
import server.actions.RemoveIconAction;

/**
 * Class that represents the board.
 * @author Oscar Strandmark
 */
public class BoardModel {

	private ArrayList<CharacterIcon> icons;
	private ImageIcon background;
	private Session session;
	private Controller controller;
	/**
	 * Constructor used for creating a BoardModel on the client.
	 * @param controller The controller-class of the client.
	 */
	public BoardModel(Controller controller) {
		this.controller = controller;
		this.icons = new ArrayList<CharacterIcon>();
	}

	/**
	 * Constructor used for creating a BoardModel on the server.
	 * @param session - The session the BoardModel belongs to.
	 */
	public BoardModel(Session session) {
		this.session = session;
		this.icons = new ArrayList<CharacterIcon>();
	}

	/**
	 * Get the list of CharacterIcons on the board.
	 * @return An {@link ArrayList} of icons on the board.
	 */
	public ArrayList<CharacterIcon> getIcons() {
		return icons;
	}

	/**
	 * Get the background image.
	 * @return An {@link ImageIcon} of the background image.
	 */
	public ImageIcon getBackground() {
		return background;
	}

	/**
	 * Set the background image.
	 * @param img An {@link ImageIcon} of the background image.
	 */
	public void setBackground(ImageIcon img) {
		this.background = img;
		synch(new BackgroundChangeAction(controller.getUsername(),img));
	}

	/**
	 * Add an icon to the board.
	 * @param icon
	 */
	public void addIcon(CharacterIcon icon) {
		icons.add(icon);
		synch(new CreateIconAction(controller.getUsername(), icon));
	}

	public void changeIcon(CharacterIcon icon, int iconIndex, int valueIndex, Value value) {
		icons.get(iconIndex).changeValue(value, valueIndex);
		synch(new ChangeIconValueAction(controller.getUsername(), iconIndex, valueIndex, value));
	}

	public void removeIcon(CharacterIcon icon) {
		icons.remove(icon);
		synch(new RemoveIconAction(controller.getUsername(), icon));
	}

	private void synch(Action act) {
		if(session != null) {//If on server
			session.notifyPlayersForSynch();
		} else { //If on client
			controller.pushActionToServre(act);
		}
	}
}
