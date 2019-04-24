package server.actions;

import shared.Value;

public class ChangeIconValueAction extends Action {

	private static final long serialVersionUID = 3929434214095509270L;

	private int iconIndex;
	private int valueIndex;
	private Value value;

	public ChangeIconValueAction(String username, int iconIndex, int valueIndex, Value value) {
		super(username);
		this.iconIndex = iconIndex;
		this.valueIndex = valueIndex;
		this.value = value;
	}

	public int getIconIndex() {
		return iconIndex;
	}

	public int getValueIndex() {
		return valueIndex;
	}

	public Value getValue() {
		return value;
	}

}
