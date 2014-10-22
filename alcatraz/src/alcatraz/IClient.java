package alcatraz;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;


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
	public void startGame(ArrayList<Player> playerList) throws IClientException, RemoteException;
	
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
	public void doMoveRemote(String player, int prisoner, int roworCol, int row, int col) throws IClientException, RemoteException;

  
  
}
