//TODO implement self created exceptions for register and unregister and all the methods yet to come

package alcatraz.server;

import java.net.InetAddress;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import alcatraz.IServerException;
import alcatraz.IServer;
import alcatraz.Player;

public class Server extends UnicastRemoteObject implements IServer {

	private static final long serialVersionUID = 1L;

	// ================================================================================
	// ================================================================================
	// GLOBAL VARIABLES

	static ArrayList<String> playerNameList = new ArrayList<String>();

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
			System.out.println("ServerIP: " + ipAddress);

			IServer IS = new Server();
			Naming.rebind("rmi://" + ipAddress + ":1099/RegistrationService",
					IS);
			System.out.println("RegistrationServer is up and running.");
		} catch (Exception e) {
			System.out.println("Error!");
			e.printStackTrace();
		}
	}

	// ================================================================================
	// ================================================================================
	// METHODS

	/**
	 * Registers a player on the Server.
	 * 
	 * @see alcatraz.IServer#register(alcatraz.Player)
	 * @author max
	 */
	@Override
	public boolean register(Player p) throws IServerException, RemoteException {

		if (playerNameList.contains(p.getPlayerName())) {
			System.out.println("That name(" + p.getPlayerName()
					+ ") is already taken.");
			return false;
		} else {
			if (playerNameList.size() < 4) {
				playerNameList.add(p.getPlayerName());
				System.out.println("\"" + p.getPlayerName()
						+ "\" has been successfully registered.");
				return true;
			} else {
				System.out
						.println("There cannot be more than 4 players registered.");
				return false;
			}

		}
	}

	/**
	 * Unregisters a player from the server.
	 * 
	 * @see alcatraz.IServer#unregister(alcatraz.Player)
	 * @author max
	 */
	@Override
	public boolean unregister(Player p) throws IServerException,
			RemoteException {

		// when the player is not in the list you cannot unregister him
		if (!(playerNameList.contains(p.getPlayerName()))) {
			System.out.println("There is no player with that name registered.");
			return false;
		} else {
			playerNameList.remove(p.getPlayerName());
			System.out.println("You have been successfully unregistered");
			return true;
		}

	}

	@Override
	public boolean startNow() throws RemoteException {
		// TODO Auto-generated method stub
		return false;
	}

}
