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
	static ArrayList<Integer> spielerIDList = new ArrayList();
	static ArrayList<String> spielerNameList = new ArrayList();

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
			/*
			InetAddress address = InetAddress.getLocalHost();
			String ipAddress = address.getHostAddress();
			System.out.println(ipAddress);
			*/
			
			int ID = (int) (Math.random() * 99 + 1);
			System.out.println(ID);
			
			System.out.println("Server is starting");
			System.out.println("Server Parameter is now setting...");
			System.out.println("Serverparameters are set!");

			IServer IS = new Server();
			Naming.rebind("rmi://localhost:1099/RegistrationService", IS);
			System.out.println("RegistrationServer is up and running.");
		} catch (Exception e) {
			System.out.println("Error!");
			e.printStackTrace();
		}
	}
	

	
	// ================================================================================
	// ================================================================================
	// METHODS

	@Override
	public boolean register(int spielerID, String spielerName) throws IServerException, RemoteException {
		//if (checkPlayerList(spielerID, spielerName) == true) throw new IServerException("Name und/oder ID ist schon vergeben!");
		
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
		int i=0;
		for (i=0; i<= Server.spielerNameList.size();i++){
			if (Server.spielerIDList.get(i) == spielerID || Server.spielerNameList.get(i) == spielerName) {
				System.out.println("Name und/oder ID ist schon vergeben!");
				return false;
				

			}
		}
		return true;
	}

}
