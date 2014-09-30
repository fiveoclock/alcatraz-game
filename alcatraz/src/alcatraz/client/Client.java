package alcatraz.client;
import java.rmi.Naming;

import alcatraz.IServerException;
import alcatraz.IServer;


public class Client {

	private String spielerName = new String("mumphi");
	private int spielerID;
	
	// ================================================================================
	// ================================================================================
	// CONSTRUCTOR
	
	public Client() {
		setSpielerID(this.spielerID);

	}
	
	// ================================================================================
	// ================================================================================
	// MAIN
	
	public static void main(String[] args) {

		Client c = new Client();
		System.out.println("SpielerID: "+ c.spielerID);
		
		try { 
			  IServer IS = (IServer)Naming.lookup("rmi://localhost:1099/RegistrationService");
		      System.out.print("Registration proceed..."); System.out.print(IS.register(c.spielerID, c.spielerName));
		      
		      
		    } catch (Exception e) {
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
