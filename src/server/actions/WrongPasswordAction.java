package server.actions;
/**
 * Action that represents the input of a faulty password.
 * 
 * @author Oscar Strandmark
 */
public class WrongPasswordAction extends Action {

	private static final long serialVersionUID = -5819879239906730692L;

	public WrongPasswordAction(String username) {
		super(username);
	}
	
}
