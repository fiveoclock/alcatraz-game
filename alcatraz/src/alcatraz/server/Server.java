//TODO implement self created exceptions for register and unregister and all the methods yet to come

package alcatraz.server;

import java.net.InetAddress;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import alcatraz.IServerException;
import alcatraz.IServer;
import alcatraz.RemotePlayer;


import spread.*;
import java.io.*;

public class Server extends UnicastRemoteObject implements IServer, AdvancedMessageListener {

	private static final long serialVersionUID = 1L;
	
	private static Server server;
	
	private int id = 0;
	private String spreadHost = "localhost";
	private String spreadGroup = "maulwurf";
	private SpreadGroup spreadSelf;
	private SpreadConnection spread = new SpreadConnection();

	// ================================================================================
	// ================================================================================
	// GLOBAL VARIABLES

	static ArrayList<RemotePlayer> playerList = new ArrayList<RemotePlayer>();

	// ================================================================================
	// ================================================================================
	// CONSTRUCTOR

	public Server() throws RemoteException {

	}

	// ================================================================================
	// ================================================================================
	// MAIN
	public static void main(String[] args) throws RemoteException {
		server = new Server();
		
		server.id = (int) (Math.random() * 999 + 1);
		System.out.println("My ID: " + server.id);

		// if spread group is successful
		server.joinGroup();
	}
	
	// ================================================================================
	// ================================================================================
	// Spread membership handling
	
	public boolean joinGroup() {
		try {
			// connecting to spread
			System.out.print("Connecting to spread deamon ("+this.spreadHost+") ... ");
			this.spread.connect(InetAddress.getByName(this.spreadHost), 0, this.id+"", false, true);
			System.out.print("connected ... ");
			
			// adding this class as listener
			this.spread.add(this);
			
			// joining spread group
			SpreadGroup group = new SpreadGroup();
			System.out.println("joining group ");
			group.join(this.spread, this.spreadGroup);
			
			
			// remembering myself for later use
			spreadSelf = this.spread.getPrivateGroup();
			return true;
		} 
		catch (Exception e) {
			System.out.println("Spread error\n" +
					"  Please make sure that the Spread Daemon is running" +
					"  This error can also be caused by non-unique server IDs");
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
			printMemberList(info);
			
			// is it me who joined the group?
			if (info.getJoined().equals(this.spreadSelf) ) {
				System.out.println("  > Message caused by me");
				
				// to master or not to master?
				// start server if I'm the only member
				if (num == 1) {
					server.publishObject();
				}
			}
		}
		if (info.isCausedByDisconnect())  {
			System.out.println("Spread: " + info.getDisconnected() + " got disconnected from group / " + num + " connected / current members:");
			printMemberList(info);
			
			// to master or not to master?
			// start server if I'm the only remaining member
			if (num == 1) {
				server.publishObject();
			}
			// if there are more than one
			else if (num >= 2) {
				// start server if I have the lowest id
				if (server.id <= getLowestId(info)) {
					System.out.println("Becomming master server because of lowest ID");
					server.publishObject();
				}
			}
		}
		if (info.isCausedByLeave())  {
			System.out.println("Spread: " + info.getLeft() + " left group / " + num + " connected / current members:");
			printMemberList(info);
			
			// to master or not to master?
			// start server if I'm the only remaining member
			if (num == 1) {
				server.publishObject();
			}
			// if there are more than one
			else if (num >= 2) {
				// start server if I have the lowest id
				if (server.id <= getLowestId(info)) {
					System.out.println("Becomming master server because of lowest ID");
					server.publishObject();
				}
			}
		}
	}
	
	private void printMemberList(MembershipInfo info) {
		for (SpreadGroup g : info.getMembers() ) {
			System.out.print("  member: "+ g.toString());
			if (g.equals(this.spreadSelf) ) {
				System.out.print("  (me)");
			}
			System.out.println();
		}
	}
	
	private int getLowestId(MembershipInfo info) {
		int lowestId = Integer.MAX_VALUE; 
		for (SpreadGroup g : info.getMembers() ) {
			int memberID = Integer.valueOf(g.toString().split("#")[1]);
			
			//System.out.print("\n" + lowestId + " <= " + memberID);
			if (memberID <= lowestId) {
				//System.out.print(" -- true");
				lowestId = memberID;
			}
		}
		return lowestId;
	}
	
	// ================================================================================
	// ================================================================================
	// Spread message handling
	
	public void regularMessageReceived(SpreadMessage message) {
		// check if message was sent by myself
		if ( ! message.getSender().equals(this.spreadSelf) ) {
			System.out.println("Received updated PlayerList from" + message.getSender());
			playerList = (ArrayList<RemotePlayer>) deserialize(message.getData());
		}
	}

	public void sendMessage(String msg) {
		SpreadMessage message = new SpreadMessage();
		message.setReliable();
		message.addGroup(this.spreadGroup);
		message.setData(msg.getBytes());
		try {
			server.spread.multicast(message);
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
			server.spread.multicast(message);
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

			System.out.println("Server is starting");
			//System.out.println("Server Parameter is now setting...");
			//System.out.println("Serverparameters are set!");
			System.out.println("ServerIP: " + ipAddress + "\n");

			IServer IS = new Server();
			//Naming.rebind("rmi://" + ipAddress + ":1099/RegistrationService", IS);
			Naming.rebind("rmi://127.0.0.1" + ":1099/RegistrationService", IS);
			
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
	public boolean register(RemotePlayer p) throws IServerException, RemoteException {
		if (playerList.toString().contains(p.getName())) {
			System.out.println("A player by the name of " + p.getName() + " is already registered.");
			return false;
		}
		playerList.add(p);
		System.out.println("\"" + p.getName() + "\" has registered.");
		
		// count registered players
		int count = 0;
		for (RemotePlayer s : playerList ) {
			if (s.getDesiredNumPlayers() == p.getDesiredNumPlayers()) {
				count++;
			}
		}
		// if there are enough players start a game now
		if (count == p.getDesiredNumPlayers()) {
			System.out.println("Enough players registered to start a game - starting now.");
			//startNow(p.getDesiredNumPlayers());
		}
		// synchronize list
		sendObject(playerList);
		return true;
	}

	/**
	 * Unregisters a player from the server.
	 * 
	 * @see alcatraz.IServer#unregister(alcatraz.Player)
	 * @author max
	 */
	//@Override
	public boolean unregister(RemotePlayer p) throws IServerException, RemoteException {
		// when the player is not in the list you cannot unregister him
		if (playerList.remove(p) == true) {
			// send updated playerlist to backup servers
			sendObject(playerList);
			
			System.out.println(p.getName() + " has un-registered");
			return true;
		}
		else {
			System.out.println("There is no player called \"" + p.getName() );
			return false;
		}
	}

	@Override
	public boolean startNow(int numPlayers) throws RemoteException {
		ArrayList<RemotePlayer> gameList = new ArrayList<RemotePlayer>();
		
		// add Players to a gameList
		int count = 0;
		for (RemotePlayer p : playerList ) {
			if (numPlayers == p.getDesiredNumPlayers()) {
				gameList.add(p);
			}
			if (gameList.size() == numPlayers) {
				break;
			}
		}
		// invoke startNow on Client RMIs
		// remove clients from playerList
		for (RemotePlayer p : gameList ) {
			System.out.print("Invoking start on \"" + p.getName() +" ... ");
			// TODO: uncomment later
			//if (p.startNow(gameList)) {
			if (true) {  // just for debugging
				System.out.print("success");
				playerList.remove(p);
			}
			else {
				playerList.remove(p);
				return false;
			}
		}
		return true;
	}
}
