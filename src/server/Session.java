package server;

public class Session 
{
	
	private String name;
	private int nbrPlayers;
	private int maxPlayers;

	public Session(String name, int nbrPlayers, int maxPlayers)
	{
		this.name = name;
		this.nbrPlayers = nbrPlayers;
		this.maxPlayers = maxPlayers;
	}
	
	public void setName(String setName)
	{
		this.name = name;
	}
	
	public String getName()
	{
		return this.name;
	}
	
	public int getnbrPlayers()
	{
		return this.nbrPlayers;
	}
	
	public int getMaxPlayers()
	{
		return this.maxPlayers;
	}

}
