package server.actions;

import java.util.ArrayList;

import shared.Value;
/**
 * @author Oscar Strandmark
 */
public class BoardIconValueUpdateAction extends Action {

	private static final long serialVersionUID = 835759847220153599L;

	private int index;
	private ArrayList<Value> list;
	
	public BoardIconValueUpdateAction(String username, int index, ArrayList<Value> list) {
		super(username);
		this.index = index;
		this.list = list;
	}

	public int getIndex() {
		return index;
	}
	
	public ArrayList<Value> getList() {
		return list;
	}
}
