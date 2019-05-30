package server.actions;

/**
 * Action for moving an icon on the board.
 * 
 * @author Oscar Strandmark
 */
public class BoardIconMoveAction extends Action {

	private static final long serialVersionUID = -1506696520874842346L;

	private int x;
	private int y;
	private int index;
	
	public BoardIconMoveAction(String username, int index, int x, int y) {
		super(username);
		this.index = index;
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getIndex() {
		return index;
	}

}