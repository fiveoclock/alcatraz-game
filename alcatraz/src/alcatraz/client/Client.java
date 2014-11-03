package alcatraz.client;

import java.util.*;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

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

	private String name;
	private String serverAdr;
	private int numPlayer;
	
	private int myId;

	private Alcatraz a = new Alcatraz();

	// ================================================================================
	// ================================================================================
	// MAIN

	public static void main(String[] args) throws RemoteException {
		
		// a new client creates the gui in his constructor
		Client c = new Client();
		

		/*
		 * 
		 * //TODO publish ClientObject //TODO bind other client objects to pass
		 * the moves //TODO first client (or server) starts new game and does
		 * the first move //TODO first client passes move to other players and
		 * the next clients turn begins
		 */
	}

	// ================================================================================
	// ================================================================================
	// CONSTRUCTOR

	public Client() throws RemoteException {

		ClientGUI frame = new ClientGUI(this);
		frame.setVisible(true);

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
	public static void registerPlayer(String serverIP, RemotePlayer p) {
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

	/**
	 * Unregister the passed Player
	 *
	 * @author max
	 * @param serverIP
	 * @param p
	 */
	public static void unregisterPlayer(String serverIP, RemotePlayer p) {

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
	// ================================================================================
	// GAME STUFF

	public void startGame() {
		a.init(2, this.myId); // a2.init(2, 1); // a1.init(3, 0);
		// t1.setOther(0, a2);
		// t1.setOther(1, a3);
		a.showWindow();
		a.addMoveListener(this);
		a.start();
	}

	/**
	 * @see alcatraz.IClient#startGame(java.util.ArrayList)
	 */
	@Override
	public void startGame(ArrayList<Player> playerList)
			throws IClientException, RemoteException {
		// TODO bind the other clients from the list here
		// TODO start alcatraz here
		// TODO do game stuff
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

	public void moveDone(Player player, Prisoner prisoner, int rowOrCol,
			int row, int col) {
		System.out.println("moving " + prisoner + " to "
				+ (rowOrCol == Alcatraz.ROW ? "row" : "col") + " "
				+ (rowOrCol == Alcatraz.ROW ? row : col));
		for (int i = 0; i < getNumPlayer() - 1; i++) {
			// sendMessage("p:"+prisoner+" > "+rowOrCol+":"+row+":"+col);
			Move m = new Move(player, prisoner, rowOrCol, row, col);
			// sendObject(m);
		}
	}

	public void gameWon(Player player) {
		System.out.println("Player " + player.getId() + " wins.");
	}

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
