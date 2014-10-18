package alcatraz.server;

import java.net.InetAddress;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import alcatraz.IServerException;
import alcatraz.IServer;

public class Server extends UnicastRemoteObject implements IServer {
		
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// ================================================================================
	// ================================================================================
	// GLOBAL VARIABLES
	static ArrayList<Integer> spielerIDList = new ArrayList<Integer>();
	static ArrayList<String> spielerNameList = new ArrayList<String>();

	// ================================================================================
	// ================================================================================
	// CONSTRUCTOR

	public Server() throws RemoteException {

	}

	// ================================================================================
	// ================================================================================
	// MAIN
	public static void main(String[] args) throws RemoteException {
		
		
		// publish the server object to the RMIregistry
		Server s = new Server();
		s.publishObject();
	}

	// ================================================================================
	// ================================================================================
	// REGISTRY STUFF
	
	private void publishObject() {
		try {
			
			InetAddress address = InetAddress.getLocalHost();
			String ipAddress = address.getHostAddress();
			System.out.println(ipAddress);
			
			
			int ID = (int) (Math.random() * 99 + 1);
			System.out.println(ID);
			
			System.out.println("Server is starting");
			System.out.println("Server Parameter is now setting...");
			System.out.println("Serverparameters are set!");
			System.out.println("ServerIP: "+ipAddress);
			
			IServer IS = new Server();
			Naming.rebind("rmi://"+ipAddress+":1099/RegistrationService", IS);
			System.out.println("RegistrationServer is up and running.");
		} catch (Exception e) {
			System.out.println("Error!");
			e.printStackTrace();
		}
	}
	

	
	// ================================================================================
	// ================================================================================
	// METHODS
	
	//Obwohl mit der Methode checkPlayerList überprüft wird, ob es den Spielernamen und/oder ID schon gibt, wird
	// kein Exception ausgelöst und einfach hinzugefügt, Manuel
	@Override
	public boolean register(int spielerID, String spielerName) throws IServerException, RemoteException {
		if (checkPlayerList(spielerID, spielerName) == false) throw new IServerException("Name und/oder ID ist schon vergeben!");
		
		if (Server.spielerIDList.size()<4) {
			Server.spielerIDList.add(spielerID);
			Server.spielerNameList.add(spielerName);
			System.out.println(spielerIDList);
			System.out.println(spielerNameList);
			return true;
			
		}
		else{
			System.out.println("Keine zusätzlichen Spieler mehr möglich!");
			return false;
		}
	}

	public boolean checkPlayerList(int spielerID, String spielerName){
		int i;
		if (Server.spielerIDList == null || Server.spielerIDList.size() == 0 || Server.spielerNameList == null || Server.spielerNameList.size() == 0 ) {
			return true;
			}
		else{
			for (i=0; i< Server.spielerNameList.size();i++){
				
				if (Server.spielerNameList.get(i) == spielerName || Server.spielerIDList.get(i) == spielerID) {
					System.out.println("Name und/oder ID ist schon vergeben!");
					return false;
				}

			}
		}
		return true;
	}

	@Override
	public boolean startNow() throws RemoteException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean unregister(int spielerID, String spielerName) throws IServerException, RemoteException {
		int i;
		for(i=0; i< Server.spielerIDList.size(); i++)
			if(Server.spielerIDList.get(i) == spielerID && Server.spielerNameList.get(i) == spielerName){
				Server.spielerIDList.remove(i);
				Server.spielerNameList.remove(i);
				System.out.println(spielerIDList);
				System.out.println(spielerNameList);
				return true;
			}
		
		return false;
	}


}
