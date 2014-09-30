package alcatraz;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IServer extends Remote {

  
  public boolean register(int spielerID, String spielerName) throws IServerException, RemoteException;
  
  
}
