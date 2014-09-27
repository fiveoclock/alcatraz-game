package alcatraz;

import java.net.InetAddress; 

import java.rmi.Naming;

public class Server {
	
	/* ######################################################### 
	 * -------------------- Constructor ------------------------
	 * ######################################################### 
	 */
	public Server(){
		
	
	}
	
	/* ######################################################### 
	 * ----------------------- Main ----------------------------
	 * ######################################################### 
	 */
	public static void main(String[] args) {
	 try {
		InetAddress address = InetAddress.getLocalHost();
		String ipAddress = address.getHostAddress();
		System.out.println(ipAddress);
		int ID = (int ) (Math.random() * 99 + 1);
		System.out.println(ID);
		System.out.println("Server is starting");
		System.out.println("Server Parameter is now setting...");
		//Server S = new Server();
		
		System.out.println("Serverparameters are set!");
		
		Communicator IS = new IServer();
		Naming.rebind("rmi://localhost:1099/RegistrationService", IS);
		System.out.println("RegistrationServer is up and running.");
	
	/* ######################################################### 
	 * ---------------------- Methods --------------------------
	 * ######################################################### 
	 */
	 }catch (Exception e){
		System.out.println("Error!");
		e.printStackTrace();
	 	}
	}
	

}
