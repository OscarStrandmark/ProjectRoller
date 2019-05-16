package server.actions;

/**
 * @author Oscar Strandmark
 */
public class BoardIconRemoveAction extends Action {

	private int index;
	
	public BoardIconRemoveAction(String username, int index) {
		super(username);
		this.index = index;
	}

	public int getIndex() {
		return index;
	}

}