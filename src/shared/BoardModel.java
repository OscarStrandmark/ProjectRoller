package shared;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import client.Controller;
import server.Session;
import server.actions.SynchAction;


/**
 * Class that represents the board.
 * 
 * @author Oscar Strandmark
 */
public class BoardModel implements Serializable {

	private long lastUpdatedEpoch;

	private HashMap<JLabel,CharacterIcon> iconMap;
	
	private JPanel board;
	private JLabel background;
	
	private Session session;
	private Controller controller;

	/**
	 * Constructor used for creating a BoardModel on the client.
	 * 
	 * @param controller The controller-class of the client.
	 */
	public BoardModel(Controller controller) {
		this.controller = controller;
		this.iconMap = new HashMap<JLabel,CharacterIcon>();
	}

	/**
	 * Constructor used for creating a BoardModel on the server.
	 * 
	 * @param session - The session the BoardModel belongs to.
	 */
	public BoardModel(Session session) {
		this.session = session;
		this.iconMap = new HashMap<JLabel,CharacterIcon>();
	}

	public void setBoard(JPanel board) {
		this.board = board;
	}
	
	/**
	 * Set the background image.
	 * 
	 * @param img An {@link ImageIcon} of the background image.
	 */
	public void setBackground(JLabel img) {
		if(background != null) {
			board.remove(background);
		}
		this.background = img;
		board.setComponentZOrder(img, board.getComponentCount()-1);
		synchToServer();
	}
	
	public void addIcon(JLabel icon) {
		CharacterIcon cIcon = new CharacterIcon(icon);
		iconMap.put(icon, cIcon);
		board.setComponentZOrder(icon, 0);
		board.repaint();
		synchToServer();
	}
	
	public void removeIcon(JLabel icon) {
		iconMap.remove(icon);
		board.remove(icon);
		board.repaint();
		synchToServer();
		
	}
	
	public CharacterIcon lookup(JLabel lbl) {
		return iconMap.get(lbl);
	}

	public void synchClient(HashMap<JLabel,CharacterIcon> map, JLabel background) {
		board.removeAll();
		iconMap = map;
		this.background = background;

		board.add(background);
		
		Set<JLabel> set = map.keySet();
		Iterator<JLabel> iter = set.iterator();
		
		while(iter.hasNext()) {
			JLabel lbl = iter.next();
			board.add(lbl);
			int x = lookup(lbl).getX();
			int y = lookup(lbl).getY();
			lbl.setLocation(x, y);
		}
	}
	
	public void synchServer(HashMap<JLabel,CharacterIcon> map, JLabel background) {
		iconMap = map;
		this.background = background;
	}
	
	public void synchToServer() {
		controller.pushActionToServer(new SynchAction(controller.username, iconMap, background));
	}
}
