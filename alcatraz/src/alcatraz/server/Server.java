//TODO implement self created exceptions for register and unregister and all the methods yet to come

package alcatraz.server;

import java.net.InetAddress;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import alcatraz.IServerException;
import alcatraz.IServer;
import at.falb.games.alcatraz.api.Player;

import spread.*;
import java.io.*;

public class Server extends UnicastRemoteObject implements IServer, AdvancedMessageListener {

	private static final long serialVersionUID = 1L;
	
	private int id = 0;
	private String spreadHost = "localhost";
	private String spreadGroup = "maulwurf";
	private int numMembers;
	private SpreadConnection spread = new SpreadConnection();

	// ================================================================================
	// ================================================================================
	// GLOBAL VARIABLES

	static ArrayList<Player> playerList = new ArrayList<Player>();
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
		
		Server s = new Server();
		
		s.id = (int) (Math.random() * 99 + 1);
		System.out.println("My ID: " + s.id);

		// if spread group is successful
		if (s.joinGroup()) {
			// check if master server
			if (true) {
				// publish the server object to the RMIregistry
				s.publishObject();
			}
		}
		
	}
	
	// ================================================================================
	// ================================================================================
	// Spread
	
	public boolean joinGroup() {
		try {
			System.out.println("Connecting to spread deamon...");
			this.spread.connect(InetAddress.getByName(this.spreadHost), 0, this.id+"", false, true);
			this.spread.add(this);
			
			System.out.println("Joining group...");
			SpreadGroup group = new SpreadGroup();
			group.join(this.spread, this.spreadGroup);
			return true;
		} 
		catch (Exception e) {
			System.out.println("Trouble!");
			e.printStackTrace();
			return false;
		}
	}

	// called when group membership changed
	public void membershipMessageReceived(SpreadMessage msg) {
		MembershipInfo info = msg.getMembershipInfo();
		int num = info.getMembers().length;
		
		if (info.isCausedByJoin())  {
			System.out.println("Spread: " + info.getJoined() + " joined group / " + num + " connected / current members:");
			
			// is it me who joined the group?
			if (this.spread.getPrivateGroup().equals(info.getJoined())) {
				System.out.println("I joined");
			}
			// wenn num == 1 -> become master
			// else
				// wenn num > 1
					// höchste id wird master - master id speichern
		}
		if (info.isCausedByDisconnect())  {
			System.out.println("Spread: " + info.getDisconnected() + " got disconnected from group / " + num + " connected / current members:");
			// wenn backup disconnected
				// nur nachricht
			// wenn master disconnected
				// höchste id wird master - master id speichern
		}
		if (info.isCausedByLeave())  {
			System.out.println("Spread: " + info.getLeft() + " left group / " + num + " connected / current members:");
		}
		
		for (SpreadGroup g : info.getMembers() ) {
			System.out.println("  member: "+ g.toString());
		}
	}

	public void regularMessageReceived(SpreadMessage message) {
		String s = new String(message.getData());
		System.out.println("New message from " + message.getSender() + ": "+ s);
	}

	public void sendMessage(String msg) {
		SpreadMessage message = new SpreadMessage();
		message.setReliable();
		message.addGroup(this.spreadGroup);
		message.setData(msg.getBytes());
		try {
			this.spread.multicast(message);
		}
		catch (Exception e) {
			System.out.println("Trouble!");
			e.printStackTrace();
		}
	}

	public void sendObject(Object obj) {
		SpreadMessage message = new SpreadMessage();
		message.setReliable();
		message.addGroup(this.spreadGroup);

		try {
			message.setData(serialize(obj));
			this.spread.multicast(message);
		}
		catch (Exception e) {
			System.out.println("Trouble!");
			e.printStackTrace();
		}
	}
	
	public static byte[] serialize(Object obj) {
		try {
			ByteArrayOutputStream b = new ByteArrayOutputStream();
			ObjectOutputStream o = new ObjectOutputStream(b);
			o.writeObject(obj);
			return b.toByteArray();
		}
		catch (Exception e) {
			System.out.println("Trouble!");
			e.printStackTrace();
		}
		return null;
    }

	public static Object deserialize(byte[] bytes) {
		try {
			ByteArrayInputStream b = new ByteArrayInputStream(bytes);
			ObjectInputStream o = new ObjectInputStream(b);
			return o.readObject();
		}
		catch (Exception e) {
			System.out.println("Trouble!");
			e.printStackTrace();
		}
		return null;
	}
	
	// ================================================================================
	// ================================================================================
	// REGISTRY STUFF

	private void publishObject() {
		try {

			InetAddress address = InetAddress.getLocalHost();
			String ipAddress = address.getHostAddress();
			System.out.println(ipAddress);



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
	//@Override
	public boolean register(Player p) throws IServerException, RemoteException {
		
		if (playerList.toString().contains(p.getName())) {
			System.out.println("That name(" + p.getName()
					+ ") is already taken.");
			return false;
		} else {
			if (playerList.size() < 4) {
				playerList.add(p);
				System.out.println("\"" + p.getName()
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
	//@Override
	public boolean unregister(Player p) throws IServerException, RemoteException {

		// when the player is not in the list you cannot unregister him
		if (playerList.remove(p) == true) {
			System.out.println("You have been successfully unregistered");
			return true;
		}
		else {
			System.out.println("There is no player called \"" + p.getName()
					+ "\". No unregister possible.");
			return false;
		}
	}

	@Override
	public boolean startNow() throws RemoteException {
		// TODO Auto-generated method stub
		return false;
	}



}
