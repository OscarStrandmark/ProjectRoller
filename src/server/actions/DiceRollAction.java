package server.actions;

import shared.Diceroll;
/**
 * Action that represents the use of the /roll command.
 * 
 * @author Oscar Strandmark
 */
public class DiceRollAction extends Action {

	private static final long serialVersionUID = 4117310951945838243L;
	
	private Diceroll diceroll;
	
	public DiceRollAction(String username, Diceroll diceroll) {
		super(username);
		this.diceroll = diceroll;
	}

	public Diceroll getDiceroll() {
		return diceroll;
	}
}
