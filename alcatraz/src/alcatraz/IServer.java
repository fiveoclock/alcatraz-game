package alcatraz;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IServer extends Remote {

  public boolean startNow() throws RemoteException;

  /**
   * 
   * @param p a player object
   * @return
   * @throws IServerException
   * @throws RemoteException
   */
  public boolean register(Player p) throws IServerException, RemoteException;
  
  /**
   * 
   * @param p a player object
   * @return
   * @throws IServerException
   * @throws RemoteException
   */
  public boolean unregister(Player p) throws IServerException, RemoteException;
  
  
}
