package alcatraz;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

import at.falb.games.alcatraz.api.*;


public interface IClient extends Remote {
	
	/**
	 * This must be called by the server when either there are 4 players registered or
	 * if a player issues a startNow() to the server.
	 * 
	 * @param playerList
	 * @throws IClientException
	 * @throws RemoteException
	 * @author max
	 */
	public boolean startGame(ArrayList<RemotePlayer> playerList, RemotePlayer player) throws IClientException, RemoteException;
	
	/**
	 * This function is to propagate a move to the other players.
	 * @param player
	 * @param prisoner
	 * @param roworCol
	 * @param row
	 * @param col
	 * @throws IClientException
	 * @throws RemoteException
	 */
	public boolean doMoveRemote(Player player, Prisoner prisoner, int rowOrCol, int row, int col) throws IClientException, RemoteException;


}
