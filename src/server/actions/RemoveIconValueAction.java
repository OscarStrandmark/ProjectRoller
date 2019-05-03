package server.actions;

import shared.Value;

public class RemoveIconValueAction extends Action
{
	
	private int iconIndex;
	private int valueIndex;

	public RemoveIconValueAction(String username, int iconIndex, int valueIndex, Value value) {
		super(username);
		this.iconIndex = iconIndex;
		this.valueIndex = valueIndex;
	}
	
	public int getIconIndex() {
		return iconIndex;
	}

	public int getValueIndex() {
		return valueIndex;
	}



}
