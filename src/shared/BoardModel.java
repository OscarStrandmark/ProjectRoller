package shared;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import client.Controller;
import server.Session;
import server.actions.SynchAction;


/**
 * Class that represents the board.
 * 
 * @author Oscar Strandmark
 */
public class BoardModel implements Serializable {

	private HashMap<ImageIcon,CharacterIcon> iconMap;
	private ImageIcon background;
	private JLabel backgroundReference;
	
	private JPanel board;
	
	private Session session;
	private Controller controller;

	/**
	 * Constructor used for creating a BoardModel on the client.
	 * 
	 * @param controller The controller-class of the client.
	 */
	public BoardModel(Controller controller) {
		this.controller = controller;
		this.iconMap = new HashMap<ImageIcon,CharacterIcon>();
	}

	/**
	 * Constructor used for creating a BoardModel on the server.
	 * 
	 * @param session - The session the BoardModel belongs to.
	 */
	public BoardModel(Session session) {
		this.session = session;
		this.iconMap = new HashMap<ImageIcon,CharacterIcon>();
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
			board.remove(backgroundReference);
		}
		backgroundReference = img;
		this.background = (ImageIcon)img.getIcon();
		board.setComponentZOrder(img, board.getComponentCount()-1);
		//synchToServer();
	}
	
	public void addIcon(JLabel icon) {
		CharacterIcon cIcon = new CharacterIcon(icon);
		ImageIcon imgIcon = (ImageIcon)icon.getIcon();
		iconMap.put(imgIcon, cIcon);
		board.setComponentZOrder(icon, 0);
		board.repaint();
		//synchToServer();
	}
	
	public void removeIcon(JLabel icon) {
		iconMap.remove((ImageIcon)icon.getIcon());
		board.remove(icon);
		board.repaint();
		//synchToServer();
		
	}
	
	public CharacterIcon lookup(JLabel lbl) {
		return iconMap.get((ImageIcon)lbl.getIcon());
	}
	
	private void setThisBackground(ImageIcon background) {
		this.background = background;
	}

	//TODO: FIXA SYNK
	public void synchClient(HashMap<ImageIcon,CharacterIcon> map, ImageIcon background) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				board.removeAll();
				board.revalidate();
				board.repaint();
				
				iconMap = map;
				
				setThisBackground(background);
				JLabel lblBackground = new JLabel(background);
				board.add(lblBackground);
				
				Iterator<ImageIcon> iter =  map.keySet().iterator();
				
				while(iter.hasNext()) {
					ImageIcon img = iter.next();
					JLabel lblNew = new JLabel(img);
					board.add(lblNew);
					int x = map.get(img).getX();
					int y = map.get(img).getY();
					lblNew.setLocation(x, y);
				}
				
				board.revalidate();
				board.repaint();
			}
		});
	}
	
	public void synchServer(HashMap<ImageIcon,CharacterIcon> map, ImageIcon background) {
		iconMap = map;
		this.background = background;
	}
	
	public void synchToServer() {
		if(background == null) {
			background = new ImageIcon();
		}
		controller.pushActionToServer(new SynchAction(controller.username, iconMap, background));
	}
}
