package server.actions;

/**
 * Action that is sent when the user closes the program window. (Not leaving session by clicking leave).
 * 
 * @author Oscar Strandmark
 */
public class QuitAction extends Action {

	private static final long serialVersionUID = -7393888413161484320L;

	public QuitAction(String username) {
		super(username);
	}

}
