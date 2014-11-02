package alcatraz;

import at.falb.games.alcatraz.api.Player;

public class RemotePlayer extends Player {
	private static final long serialVersionUID = -1887483427951420040L;
	private int desiredNumPlayers;
	private String rmiUri;
	
	public RemotePlayer(Player player, int numPlayers) {
		super(player);
		setDesiredNumPlayers(numPlayers);
	}
	public RemotePlayer(int id, int numPlayers) {
		super(id);
		setDesiredNumPlayers(numPlayers);
	}
	
	public boolean setDesiredNumPlayers(int i) {
		if (i>=2 && i<=4) {
			desiredNumPlayers = i;
			return true;
		}
		return false;
	}
	public int getDesiredNumPlayers() {
		return 	desiredNumPlayers;
	}

	public void setRmiUri(String uri) {
		rmiUri = uri;
	}
	public String getRmiUri() {
		return rmiUri;
	}
}
