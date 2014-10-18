package alcatraz;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IServer extends Remote {

  public boolean startNow() throws RemoteException;
	
  public boolean register(int spielerID, String spielerName) throws IServerException, RemoteException;
  
  public boolean unregister(int spielerID, String spielerName) throws IServerException, RemoteException;
  
  
}
