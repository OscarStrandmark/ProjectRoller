package server.actions;

import shared.Diceroll;
/**
 * Action that represents the use of the /dmroll command.
 * 
 * @author Oscar Strandmark
 */
public class DiceRollHiddenAction extends Action {

	private static final long serialVersionUID = 4117310951945838243L;
	
	private Diceroll diceroll;
	
	public DiceRollHiddenAction(String username, Diceroll diceroll) {
		super(username);
		this.diceroll = diceroll;
	}

	public Diceroll getDiceroll() {
		return diceroll;
	}
}
