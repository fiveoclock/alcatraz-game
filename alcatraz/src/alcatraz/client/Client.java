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

import javax.swing.JOptionPane;
import javax.swing.JTextField;

import at.falb.games.alcatraz.api.Alcatraz;
import at.falb.games.alcatraz.api.MoveListener;
import at.falb.games.alcatraz.api.Player;
import at.falb.games.alcatraz.api.Prisoner;


public class Client extends UnicastRemoteObject implements IClient, MoveListener {

	private static final long serialVersionUID = 1L;
		
	private String name;
    private String server;

    private int myId;
    private Player playerList[];
    private int numPlayer;
    private Alcatraz a = new Alcatraz();
    

	// ================================================================================
	// ================================================================================
	// CONSTRUCTOR

	public Client() throws RemoteException {

	}

	// ================================================================================
	// ================================================================================
	// MAIN

	public static void main(String[] args) throws RemoteException {

		// TODO create a GUI class and create the object here instead of the
		// console input - downloadlink for gui designer (eclipse luna):
		// http://download.eclipse.org/windowbuilder/WB/release/R201406251200/4.4/
		
		Client c = new Client();
		Player p = new Player(0);
		
		if (args.length > 0)
			p.setName(args[0]);
		if (args.length > 1)
			c.server = args[1];
		if (args.length > 2)
			c.numPlayer = Integer.valueOf(args[2]);
		
		JTextField fName = new JTextField(p.getName());
		JTextField fServer = new JTextField(c.server);
		JTextField fNumPlayer = new JTextField(c.numPlayer);
		
		Object[] message = {
		  "Name", fName,
		  "Server Host", fServer,
		  "Number of Players", fNumPlayer,
		};


		JOptionPane pane = new JOptionPane( message, 
		  JOptionPane.PLAIN_MESSAGE, 
		  JOptionPane.OK_CANCEL_OPTION);
		pane.createDialog(null, "Alcatraz").setVisible(true);
		
		// use input from dialog
		p.setName(fName.getText());
		c.server = fServer.getText();
		c.numPlayer = Integer.valueOf(fNumPlayer.getText());
		
		// register
		registerPlayer(c.server, p, c.numPlayer);
		
		c.startGame();
		
		// wait - just for testing
		try {
			Thread.sleep(20000); // show wait message
		}
		catch (Exception e) {
			System.out.println("Trouble!");
			e.printStackTrace();
		}
		
		// unregister again
		unregisterPlayer(c.server, p);
		
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
	private static void registerPlayer(String serverIP, Player p, int numPlayer) {
		try {
			boolean messageRegister;
			IServer IS = (IServer) Naming.lookup("rmi://" + serverIP
					+ ":1099/RegistrationService");
			System.out.print("Registration proceed...");
			messageRegister = IS.register(p, numPlayer);
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
	// test
	//TODO remove this later if not needed anymore 
	public boolean TEST(int spielerID, String spielerName)
			throws IServerException, RemoteException {
		return true;
	}

	// ================================================================================
	// ================================================================================
	// GAME STUFF
	
	
	public void startGame() {
		a.init(2, this.myId);	// a2.init(2, 1);	// a1.init(3, 0);
//		t1.setOther(0, a2);
//		t1.setOther(1, a3);
		a.showWindow();
		a.addMoveListener(this);
		a.start();
	}
	
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


	
	public void moveDone(Player player, Prisoner prisoner, int rowOrCol, int row, int col) {
		System.out.println("moving " + prisoner + " to " + (rowOrCol == Alcatraz.ROW ? "row" : "col") + " " + (rowOrCol == Alcatraz.ROW ? row : col));
		for (int i = 0; i < getNumPlayer() - 1; i++) {
			//sendMessage("p:"+prisoner+" > "+rowOrCol+":"+row+":"+col);
			Move m = new Move(player, prisoner, rowOrCol, row, col);
			//sendObject(m);
		}
	}
	
	public void gameWon(Player player) {
		System.out.println("Player " + player.getId() + " wins.");
	}
	
	public int getNumPlayer() {
	    return numPlayer;
	}



}
