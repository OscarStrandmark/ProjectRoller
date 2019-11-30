package server.actions;

/**
 * Action for deleting an icon off the board.
 * 
 * @author Oscar Strandmark
 */
public class BoardIconRemoveAction extends Action {

	private static final long serialVersionUID = -8790683634812865198L;

	private int index;
	
	public BoardIconRemoveAction(String username, int index) {
		super(username);
		this.index = index;
	}

	public int getIndex() {
		return index;
	}

}