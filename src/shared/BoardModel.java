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
import server.actions.BoardBackgroundChangeAction;
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
	public void setBackground(ImageIcon img) {
		JLabel newBG = new JLabel(img);
		newBG.setBounds(0, 0, board.getWidth(), board.getHeight());
		
		if(background != null) {
			board.remove(backgroundReference);
		}
		
		backgroundReference = newBG;
		this.background = img;
		
		board.add(newBG);
		board.setComponentZOrder(newBG, board.getComponentCount()-1);
		board.repaint();
	}
	
	public void addIcon(JLabel icon) {
		CharacterIcon cIcon = new CharacterIcon(icon);
		ImageIcon imgIcon = (ImageIcon)icon.getIcon();
		iconMap.put(imgIcon, cIcon);
		board.setComponentZOrder(icon, 0);
		board.repaint();
	}
	
	public void removeIcon(JLabel icon) {
		iconMap.remove((ImageIcon)icon.getIcon());
		board.remove(icon);
		board.repaint();
		
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
}
