package alcatraz.client;
import java.util.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import alcatraz.IServerException;
import alcatraz.IServer;
//import alcatraz.Player; KA was die machen
//import alcatraz.Prisoner; KA was die machen
import alcatraz.server.Server;
import alcatraz.IClientException;
import alcatraz.IClient;

public class Client extends UnicastRemoteObject implements IClient {
	private static final long serialVersionUID = 1L;
	private String spielerName;
	private int spielerID;

	// ================================================================================
	// ================================================================================
	// CONSTRUCTOR

	public Client() throws RemoteException {
		setSpielerID(this.spielerID);
		setSpielerName(this.spielerName);
	}

	// ================================================================================
	// ================================================================================
	// MAIN

	public static void main(String[] args) {

		try { 
			Client c = new Client();
			System.out.println("SpielerID: "+ c.spielerID);

			//Eingabe der IP-Adresse des Servers
			Scanner sr = new Scanner(System.in);
			System.out.print("IP-Adresse des Servers: ");
			String ServerIP = sr.next();
			System.out.println("ServerIP: "+ ServerIP );
			
			//Eingabe des Spielernames
			Scanner sc = new Scanner(System.in);
			System.out.print("Spielername: ");
			String eingabe = sc.next();
			c.setSpielerName(eingabe);
			//System.out.println("SpielerName: "+ c.spielerName);

			boolean messageRegister;
			IServer IS = (IServer)Naming.lookup("rmi://"+ServerIP+":1099/RegistrationService");
			System.out.print("Registration proceed...");
			messageRegister = IS.register(c.spielerID, c.spielerName);
			System.out.println(messageRegister);
			
			if (messageRegister == true) {
				System.out.println("Registration OK!");
			}
			
			else { 
				System.out.println("Registration False! Program closed!");
			}
			
			//Funktioniert noch ned; Manuel
			boolean messageUnregister;
			BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
			System.out.print("Wollen Sie sich abmelden? (y/n): ");
			String zeile = null;
			zeile= console.readLine();
			String yes = "y";
			if (zeile.contains(yes)== true){
				System.out.print("UnRegistration proceed...");
				messageUnregister = IS.unregister(c.spielerID, c.spielerName);
				System.out.println(messageUnregister);
			}
					


		} catch (IServerException ISe){
			System.err.println("Registration throw Exception: "+ISe.getMessage());
			ISe.printStackTrace();
		}
		catch (Exception e) {
			System.err.println("Something did not work, see stack trace.");
			e.printStackTrace();
		}
	}	


	// ================================================================================
	// ================================================================================
	// METHODS

	public String getSpielerName() {
		return spielerName;
	}

	public void setSpielerName(String spielerName) {
		this.spielerName = spielerName;
	}

	public int getSpielerID() {
		return spielerID;
	}

	public void setSpielerID(int spielerID) {
		this.spielerID = (int ) (Math.random() * 99 + 1);
	}

	public boolean TEST(int spielerID, String spielerName) throws IServerException, RemoteException {
		return true;
	}

	@Override
	// Original wäre: doMoveRemote(Player player, Prisoner prisoner, int rowOrCol, int row, int col)
	//		macht aber nur einen Error; Manuel
	public void doMoveRemote(String player, int prisoner, int roworCol,
			int row, int col) throws IClientException, RemoteException {
		// TODO Auto-generated method stub
		
	}


}
