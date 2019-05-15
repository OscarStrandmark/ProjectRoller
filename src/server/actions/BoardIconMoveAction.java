package server.actions;

/**
 * @author Oscar Strandmark
 */
public class BoardIconMoveAction extends Action {

	private static final long serialVersionUID = 8654230157182887770L;

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
