package alcatraz.client;
import java.util.*;
import java.rmi.Naming;

import alcatraz.IServerException;
import alcatraz.IServer;
import alcatraz.server.Server;


public class Client {

	private String spielerName;
	private int spielerID;
	
	// ================================================================================
	// ================================================================================
	// CONSTRUCTOR
	
	public Client() {
		setSpielerID(this.spielerID);
		setSpielerName(this.spielerName);
	}
	
	// ================================================================================
	// ================================================================================
	// MAIN
	
	public static void main(String[] args) {

		Client c = new Client();
		System.out.println("SpielerID: "+ c.spielerID);
		
		
		Scanner sc = new Scanner(System.in);
	    System.out.print("Spielername: ");
	    String eingabe = sc.next();
		c.setSpielerName(eingabe);
		System.out.println("SpielerName: "+ c.spielerName);
		boolean message;
		
		try { 
			  IServer IS = (IServer)Naming.lookup("rmi://localhost:1099/RegistrationService");
		      System.out.print("Registration proceed...");
		      message = IS.register(c.spielerID, c.spielerName);
		      System.out.println(message);
		      
		      if (message== true) {
		    	  System.out.println("Registration OK!");
		         }
		      else { 
		    	  System.out.println("Registration False! Program closed!");
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
		this.spielerID = (int ) (Math.random() * 10 + 1);
	}

}
