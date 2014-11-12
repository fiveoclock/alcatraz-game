package alcatraz.client;

import java.util.*;
import java.net.InetAddress;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import javax.annotation.processing.Messager;

import alcatraz.IServerException;
import alcatraz.IServer;
import alcatraz.IClientException;
import alcatraz.IClient;
//import alcatraz.Player; // use provided Alcatraz player class

import alcatraz.RemotePlayer;
import at.falb.games.alcatraz.api.Alcatraz;
import at.falb.games.alcatraz.api.MoveListener;
import at.falb.games.alcatraz.api.Player;
import at.falb.games.alcatraz.api.Prisoner;

public class Client extends UnicastRemoteObject implements IClient,
		MoveListener {

	private static final long serialVersionUID = 1L;

	// TODO remove the next 4 variables out of the system - these things are all
	// accessible from the RemotePlayer object
	private String name;
	private String serverAdr;
	private int numPlayer;
	private int myId;
	private ArrayList<RemotePlayer> playerList = new ArrayList<RemotePlayer>();
	private Alcatraz a = new Alcatraz();

	// ================================================================================
	// ================================================================================
	// MAIN

	public static void main(String[] args) throws RemoteException {

		// A new Client opens the GUI in its constructor
		IClient IC = new Client();

	}

	// ================================================================================
	// ================================================================================
	// CONSTRUCTOR

	public Client() throws RemoteException {

		RemotePlayer p = new RemotePlayer(this);
		ClientGUI frame = new ClientGUI(p);
		frame.setVisible(true);

	}
	

	// ================================================================================
	// ================================================================================
	// METHODS

	// ================================================================================
	// server lookup and register player at server
	/**
	 * @author max
	 * @param p
	 * @return Returns <b>true</b> if the RemotePlayer was successfully registered. <br>
	 * Returns <b>false</b> if the registration failed.
	 */
	public static boolean registerPlayer(RemotePlayer p) {
		
		boolean registerSuccess = false;
		try {
			IServer IS = (IServer) Naming.lookup("rmi://" + p.getServerAdr() + ":1099/RegistrationService");
			System.out.print("Registration proceed...\n");
			registerSuccess = IS.register(p);		

		} catch (IServerException ISe) {
			System.err.println("Registration threw Exception: " + ISe.getMessage());
			ISe.printStackTrace();
		} catch (Exception e) {
			System.err.println("Something did not work, see stack trace.");
			e.printStackTrace();
		}
		
		return registerSuccess;
	}

	/**
	 * @author max
	 * @param p
	 * @return Returns <b>true</b> if the RemotePlayer was successfully unregistered. <br>
	 * Returns <b>false</b> if no such registered RemotePlayer exists.
	 */
	public static boolean unregisterPlayer(RemotePlayer p) {

		boolean unregistrationSuccess = false;
		
		try {
			
			IServer IS = (IServer) Naming.lookup("rmi://" + p.getServerAdr()
					+ ":1099/RegistrationService");
			System.out.print("Registration proceed...");
			unregistrationSuccess = IS.unregister(p);

		} catch (IServerException ISe) {
			System.err.println("Registration throw Exception: "
					+ ISe.getMessage());
			ISe.printStackTrace();
		} catch (Exception e) {
			System.err.println("Something did not work, see stack trace.");
			e.printStackTrace();
		}
		
		return unregistrationSuccess;
	}
	
	/**
	 * publishes the Client-Remote-Objects so the moves of the game can be <br>
	 * passed to the other Players.
	 *
	 * @author max
	 */
	public static String publishObject(RemotePlayer p) {
		try {
			InetAddress address = InetAddress.getLocalHost();
			String ipAddress = address.getHostAddress();

			System.out.println("Client is being published");
			System.out.println("ClientIP: " + ipAddress);

			String rmiUri = new String("rmi://" + ipAddress + ":1099/" + p.getName());
			Naming.rebind(rmiUri, p.getIC());
			
			System.out.println("Client Services are up and running.\n");
			
			return rmiUri;
			
		} catch (Exception e) {
			System.out.println("Error!");
			e.printStackTrace();
		}
		
		return null;
	}
	

	// ================================================================================
	// ================================================================================
	// GAME STUFF
    
	public void startAndInitGame() {
		a.init(2, this.myId); // a2.init(2, 1); // a1.init(3, 0);
		//t1.setOther(0, a2);
		// t1.setOther(1, a3);
		a.showWindow();
		a.addMoveListener(this);
		a.start();
	}
	

	/**
	 * @see alcatraz.IClient#startGame(java.util.ArrayList)
	 */
	@Override
	public boolean startGame(ArrayList<RemotePlayer> playerList, RemotePlayer me) throws IClientException, RemoteException {
		//startAndInitGame();
		this.myId = me.getId();
		this.playerList = playerList;
		// setup the game
		a.init(playerList.size(), this.myId);
		a.showWindow();
		a.addMoveListener(this);
		a.start();
		
		return true;
	}

	// ================================================================================
	@Override
	// Original w�re: doMoveRemote(Player player, Prisoner prisoner, int
	// rowOrCol, int row, int col)
	// macht aber nur einen Error; Manuel
	public boolean doMoveRemote(Player player, Prisoner prisoner, int rowOrCol, int row, int col) throws IClientException, RemoteException {
		a.doMove(player, prisoner, rowOrCol, row, col);
		return true;
	}

	public void moveDone(Player player, Prisoner prisoner, int rowOrCol, int row, int col) {
		System.out.println("moving " + prisoner + " to "
				+ (rowOrCol == Alcatraz.ROW ? "row" : "col") + " "
				+ (rowOrCol == Alcatraz.ROW ? row : col));
		
		for (RemotePlayer p : this.playerList) {
			if (p.getId() == this.myId) {
			}
			else {
				boolean status = false;
				while (! status ) {
					try {
						p.getIC().doMoveRemote(player, prisoner, rowOrCol, row, col);
						status = true;
					} catch (Exception e) {
						System.out.println("Sending move to " + player.getName() + " failed, retrying...");
						try{
							Thread.sleep(1000);
						}
						catch(InterruptedException ie){ }
					}
				}
			}
		}
	}

	public void gameWon(Player player) {
		System.out.println("Player " + player.getId() + " wins.");
	}

	// ================================================================================
	// ================================================================================
	// GETTERS AND SETTERS

	public int getNumPlayer() {
		return numPlayer;
	}

	public void setNumPlayer(int numPlayer) {
		this.numPlayer = numPlayer;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getServerAdr() {
		return serverAdr;
	}

	public void setServerAdr(String serverAdr) {
		this.serverAdr = serverAdr;
	}

}
