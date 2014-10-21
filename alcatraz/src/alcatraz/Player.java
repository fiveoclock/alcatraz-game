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
	
	public String playerName;
	public int playerID;
	
	// ================================================================================
	
	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	public int getPlayerID() {
		return playerID;
	}

	public void setPlayerID(int playerID) {
		this.playerID = playerID;
	}

	@Override
	public String toString() {
		return playerName;
	}

	
	
}
