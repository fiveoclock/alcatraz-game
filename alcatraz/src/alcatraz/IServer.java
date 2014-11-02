package alcatraz;

import java.rmi.Remote;
import java.rmi.RemoteException;
import at.falb.games.alcatraz.api.Player;

public interface IServer extends Remote {

  public boolean startNow() throws RemoteException;

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
