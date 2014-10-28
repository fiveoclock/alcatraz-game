package alcatraz;

import java.io.Serializable;

/**
 * This class represents a player who can be passed 
 * from the client to the server for registration
 * @author max
 *
 * =======
 * alex: renamed Player class to Player2 because alcatraz-lib.jar already 
 * provides a class named Player that has the same methods + some others. 
 * Replaced all occurences of Player class with the provided one.
 *
 */
public class Player2 implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	// ================================================================================
	
	public String name;
	
	// ================================================================================
	// CONSTRUCTOR
	public Player2() {
		
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
