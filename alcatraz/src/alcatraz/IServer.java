package alcatraz;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IServer extends Remote {

  public boolean startNow(int numPlayers) throws RemoteException;

  /**
   * 
   * @param p a player object
   * @return Returns <b>true</b> if the RemotePlayer was successfully registered. <br>
   * Returns <b>false</b> if the registration failed.
   * @throws IServerException
   * @throws RemoteException
   */
  public boolean register(RemotePlayer p) throws IServerException, RemoteException;
  
  /**
   * 
   * @param p a player object
   * @return Returns <b>true</b> if the RemotePlayer was successfully unregistered. <br>
   * Returns <b>false</b> if no such registered RemotePlayer exists.
   * @throws IServerException
   * @throws RemoteException
   */
  public boolean unregister(RemotePlayer p) throws IServerException, RemoteException;
  
  
}
