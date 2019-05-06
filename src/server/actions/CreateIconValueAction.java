package server.actions;

import shared.Value;

public class CreateIconValueAction extends Action
{

	private int iconIndex;
	private Value value;
	
	public CreateIconValueAction(String username, int iconIndex, int valueIndex, Value value) {
		super(username);
		this.iconIndex = iconIndex;
		this.value = value;
		
	}

	public int getIconIndex() {
		return iconIndex;
	}


	public Value getValue() {
		return value;
	}
}
