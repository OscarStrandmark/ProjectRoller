package server.actions;

import java.util.Vector;

public class MoveIconAction extends Action
{

	private int x;
	private int y;
	private int characterIconIndex;
	
	public MoveIconAction(String username, int x, int y, int characterIconIndex) {
		super(username);
		this.x = x;
		this.y = y;
		this.characterIconIndex = characterIconIndex;
		
	}
	
	public int getX()
	{
		return this.x;
	}
	
	public int getY()
	{
		return this.y;
	}
	
	public int getCharacterIconIndex()
	{
		return this.characterIconIndex;
	}

}
