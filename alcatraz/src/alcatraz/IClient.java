package alcatraz;

import java.rmi.Remote;
import java.rmi.RemoteException;


public interface IClient extends Remote {

  
  public void doMoveRemote(String player, int prisoner, int roworCol, int row, int col) throws IClientException, RemoteException;

  
  
}
