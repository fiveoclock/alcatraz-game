package alcatraz;

import java.io.Serializable;

/**
 * This class represents a player who can be passed 
 * from the client to the server for registration
 * 
 * @author max
 * 
 *
 */
public class Player implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	// ================================================================================
	
	public String name;
	
	// ================================================================================
	// CONSTRUCTOR
	public Player() {
		
	}
	
	// ================================================================================
	
	public String getPlayerName() {
		return name;
	}

	public void setPlayerName(String playerName) {
		this.name = playerName;
	}

	@Override
	public String toString() {
		return name;
	}

	
	
}
