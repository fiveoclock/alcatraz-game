package alcatraz;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IServer extends Remote {

  public boolean startNow(int numPlayers) throws RemoteException;

  /**
   * 
   * @param p a player object
   * @return
   * @throws IServerException
   * @throws RemoteException
   */
  public boolean register(RemotePlayer p) throws IServerException, RemoteException;
  
  /**
   * 
   * @param p a player object
   * @return
   * @throws IServerException
   * @throws RemoteException
   */
  public boolean unregister(RemotePlayer p) throws IServerException, RemoteException;
  
  
}
