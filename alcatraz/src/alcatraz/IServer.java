package alcatraz;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IServer extends Remote {
  
  public long add(long a, long b) throws RemoteException;
  
  public long sub(long a, long b) throws RemoteException;
  
  public long mul(long a, long b) throws RemoteException;
  
  public long div(long a, long b) throws IServerException, RemoteException;
  
  public char registry(int spielerID, char spielerName) throws IServerException, RemoteException;
}
