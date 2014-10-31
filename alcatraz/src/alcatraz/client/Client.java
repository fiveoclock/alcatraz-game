package alcatraz.client;

import java.util.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import alcatraz.IServerException;
import alcatraz.IServer;
import alcatraz.IClientException;
import alcatraz.IClient;
//import alcatraz.Player; // use provided Alcatraz player class

import at.falb.games.alcatraz.api.Alcatraz;
import at.falb.games.alcatraz.api.MoveListener;
import at.falb.games.alcatraz.api.Player;
import at.falb.games.alcatraz.api.Prisoner;


public class Client extends UnicastRemoteObject implements IClient {

	private static final long serialVersionUID = 1L;
	
	private String username;
    private String serverHost;

    private int myId;
    private Player playerList[];

    private Alcatraz a = new Alcatraz();


	// ================================================================================
	// ================================================================================
	// CONSTRUCTOR

	public Client() throws RemoteException {

	}

	// ================================================================================
	// ================================================================================
	// MAIN

	public static void main(String[] args) {

		// TODO create a GUI class and create the object here instead of the
		// console input - downloadlink for gui designer (eclipse luna):
		// http://download.eclipse.org/windowbuilder/WB/release/R201406251200/4.4/

		// Create new Player that will be registered at the server
		Player p = new Player(0);
		
		String serverIP = inputServerIP();
		p.setName(inputName());
		
		// register
		registerPlayer(serverIP, p);
		
		// wait - just for testing
		try {
			Thread.sleep(10000); // show wait message
		}
		catch (Exception e) {
			System.out.println("Trouble!");
			e.printStackTrace();
		}
		
		// unregister again
		unregisterPlayer(serverIP, p);
		
		//TODO publish ClientObject
		//TODO bind other client objects to pass the moves
		//TODO first client (or server) starts new game and does the first move
		//TODO first client passes move to other players and the next clients turn begins

	}

	// ================================================================================
	// ================================================================================
	// METHODS

	// ================================================================================
	// server lookup and register player at server
	/**
	 * Does the server lookup and registers the passed Player.
	 * 
	 * @param serverIP
	 *            ip of the server
	 * @param p
	 *            player to register
	 * @author manuel
	 */
	private static void registerPlayer(String serverIP, Player p) {
		
		try {

			boolean messageRegister;
			IServer IS = (IServer) Naming.lookup("rmi://" + serverIP
					+ ":1099/RegistrationService");
			System.out.print("Registration proceed...");
			messageRegister = IS.register(p);
			System.out.println(messageRegister);

			if (messageRegister == true) {
				System.out.println("Registration OK!");
			} else {
				System.out.println("Registration False! Program closed!");
			}

		} catch (IServerException ISe) {
			System.err.println("Registration throw Exception: "
					+ ISe.getMessage());
			ISe.printStackTrace();
		} catch (Exception e) {
			System.err.println("Something did not work, see stack trace.");
			e.printStackTrace();
		}
	}
	
	private static void unregisterPlayer(String serverIP, Player p) {
		try {
			boolean messageRegister;
			IServer IS = (IServer) Naming.lookup("rmi://" + serverIP
					+ ":1099/RegistrationService");
			System.out.print("Registration proceed...");
			messageRegister = IS.unregister(p);
			System.out.println(messageRegister);

			if (messageRegister == true) {
				System.out.println("Unregistration OK!");
			} else {
				System.out.println("Unregister failed!");
			}

		} catch (IServerException ISe) {
			System.err.println("Registration throw Exception: "
					+ ISe.getMessage());
			ISe.printStackTrace();
		} catch (Exception e) {
			System.err.println("Something did not work, see stack trace.");
			e.printStackTrace();
		}
	}

	// ================================================================================
	// console stuff
	/**
	 * 
	 * @return serverIP
	 */
	private static String inputServerIP() {
		// Eingabe der IP-Adresse des Servers
		Scanner sr = new Scanner(System.in);
		System.out.print("IP-Adresse des Servers: ");
		String serverIP = sr.next();
		System.out.println("ServerIP: " + serverIP);

		return serverIP;
	}

	/**
	 * 
	 * @return playerName
	 */
	private static String inputName() {
		// Eingabe des Spielernames
		Scanner sc = new Scanner(System.in);
		System.out.print("Spielername: ");
		String input = sc.next();
		System.out.println("SpielerName: " + input);

		return input;
	}

	// ================================================================================
	// test
	//TODO remove this later if not needed anymore 
	public boolean TEST(int spielerID, String spielerName)
			throws IServerException, RemoteException {
		return true;
	}

	// ================================================================================
	// ================================================================================
	// GAME STUFF
	
	/**
	 * @see alcatraz.IClient#startGame(java.util.ArrayList)
	 */
	@Override
	public void startGame(ArrayList<Player> playerList) throws IClientException, RemoteException {
		//TODO bind the other clients from the list here
		//TODO start alcatraz here
		//TODO do game stuff
		System.out.println("kj");
	}
	
	// ================================================================================
	@Override
	// Original w�re: doMoveRemote(Player player, Prisoner prisoner, int
	// rowOrCol, int row, int col)
	// macht aber nur einen Error; Manuel
	public void doMoveRemote(String player, int prisoner, int roworCol,
			int row, int col) throws IClientException, RemoteException {
		

	}




}
