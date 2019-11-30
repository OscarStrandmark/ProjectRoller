package server.actions;
/**
 * Action for requesting a re-synchronization of the board.
 * @author Oscar Strandmark
 */
public class BoardResyncRequestAction extends Action {

	private static final long serialVersionUID = 8002692167311129568L;

	public BoardResyncRequestAction(String username) {
		super(username);
	}

}