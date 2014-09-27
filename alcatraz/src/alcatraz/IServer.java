package alcatraz;

import alcatraz.CommunicationException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class IServer extends UnicastRemoteObject implements Communicator {

		  
		  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

		/**
	 * 
	 */

		// Implementations must have an
		  // explicit constructor
		  // in order to declare the
		  // RemoteException exception
		  public IServer() throws RemoteException {
		    super();
		  }
		  
		  public long add(long a, long b) throws RemoteException {
		    return a + b;
		  }
		  
		  public long sub(long a, long b) throws RemoteException {
		    return a - b;
		  }
		  
		  public long mul(long a, long b) throws RemoteException {
		    return a * b;
		  }
		  
		  public long div(long a, long b) throws CommunicationException, RemoteException {
		    if (b == 0) throw new CommunicationException("Div by zero.");
		    return a / b;
		  }
		}
