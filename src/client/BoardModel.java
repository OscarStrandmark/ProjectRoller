package client;

import java.io.Serializable;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import client.ui.IconMovement;
import server.actions.BoardBackgroundChangeAction;
import server.actions.BoardIconCreateAction;
import server.actions.BoardIconMoveAction;
import server.actions.BoardIconRemoveAction;
import server.actions.BoardIconValueUpdateAction;
import server.actions.BoardResyncRequestAction;
import shared.CharacterIcon;
import shared.Value;


/**
 * Class that represents the board on the client.
 * 
 * @author Oscar Strandmark
 */
public class BoardModel implements Serializable {
	
	private static final long serialVersionUID = -5609765582408967782L;
	
	private ArrayList<ImageIcon> listIcon;
	private ArrayList<CharacterIcon> listChar;
	private ArrayList<JLabel> listLabl;
	
	private ImageIcon background;
	private JLabel backgroundReference;
	
	private JPanel board;
	
	private Controller controller;

	/**
	 * Constructor used for creating a BoardModel on the client.
	 * 
	 * @param controller The controller-class of the client.
	 */
	public BoardModel(Controller controller) {
		this.controller = controller;
		
		this.listIcon = new ArrayList<ImageIcon>();
		this.listChar = new ArrayList<CharacterIcon>();
		this.listLabl = new ArrayList<JLabel>();
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
	
	public void addIcon(ImageIcon img) {
		listIcon.add(img);
		listChar.add(new CharacterIcon(img));
		
		JLabel icon = new JLabel(img);
		icon.setBounds(0,0,img.getIconWidth(),img.getIconHeight());
		
		board.add(icon);
		board.repaint();
		
		icon.addMouseListener(new IconMovement(this));
		icon.addMouseMotionListener(new IconMovement(this));
		listLabl.add(icon);
		if(backgroundReference != null) {
			board.setComponentZOrder(backgroundReference, board.getComponentCount()-1);
		}
	}
	
	public void moveIcon(int index, int x, int y) {
		listChar.get(index).setPosition(x, y);
		listLabl.get(index).setLocation(x, y);
	}
	
	public void removeIcon(int index) {
		listChar.remove(index);
		listIcon.remove(index);
		board.remove(listLabl.get(index));
		listLabl.remove(index);
		board.repaint();
	}
	
	public void setValues(int index, ArrayList<Value> list) {
		listChar.get(index).setValues(list);
		System.out.println("values updated");
	}
	
	public void resync(ImageIcon background, ArrayList<ImageIcon> iconList, ArrayList<CharacterIcon> charList) {
		
		this.listIcon = new ArrayList<ImageIcon>();
		this.listChar = new ArrayList<CharacterIcon>();
		this.listLabl = new ArrayList<JLabel>();
		
		board.removeAll();
		board.repaint();
		
		for (int i = 0; i < iconList.size(); i++) {
			this.listIcon.add(iconList.get(i));
			this.listChar.add(charList.get(i));
			
			JLabel icon = new JLabel(iconList.get(i));
			icon.setBounds(0,0,iconList.get(i).getIconWidth(),iconList.get(i).getIconHeight());
			
			board.add(icon);
			icon.setLocation(charList.get(i).getX(), charList.get(i).getY());
			board.repaint();
			
			icon.addMouseListener(new IconMovement(this));
			icon.addMouseMotionListener(new IconMovement(this));
			listLabl.add(icon);
		}
		
		if(background != null) {
			setBackground(background);
		}
	}
	
	/*
	 * All methods below using the prefix "send" are the methods called whenever the user modifies the board.
	 * The methods send the correct action-object to the server to use in synchronizing the clients.
	 */
	
	public void sendBackground(ImageIcon img) {
		controller.pushActionToServer(new BoardBackgroundChangeAction(controller.username, img));
	}
	
	public void sendIconNew(ImageIcon img) {
		controller.pushActionToServer(new BoardIconCreateAction(controller.username, img));
	}
	
	public void sendIconMove(ImageIcon img, int x, int y) {
		controller.pushActionToServer(new BoardIconMoveAction(controller.username, listIcon.indexOf(img), x, y));
	}
	
	public void sendIconRemove(ImageIcon img) {
		controller.pushActionToServer(new BoardIconRemoveAction(controller.username, listIcon.indexOf(img)));
	}
	
	public void sendValueUpdate(int index, ArrayList<Value> list) {
		controller.pushActionToServer(new BoardIconValueUpdateAction(controller.username, index, list));
	}
	
	public void sendResyncReq() {
		controller.pushActionToServer(new BoardResyncRequestAction(controller.username));
	}
	
	public int lookupIndex(ImageIcon img) {
		return listIcon.indexOf(img);
	}
	
	public CharacterIcon getChar(int index) {
		return listChar.get(index);
	}
}
