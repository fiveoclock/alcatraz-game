package alcatraz;
import java.rmi.Naming;

public class client {

	public static void main(String[] args) {
		try {
		      Communicator c = (Communicator)Naming.lookup("rmi://localhost:1099/RegistrationService");
		      System.out.print("4-3="); System.out.println(c.sub(4, 3));
		      System.out.print("4+5="); System.out.println(c.add(4, 5));
		      System.out.print("3*6="); System.out.println(c.mul(3, 6));
		      System.out.print("9/3="); System.out.println(c.div(9, 3));
		      System.out.print("5/0="); System.out.println(c.div(5, 0));
		    } catch (CommunicationException ce) {
		      System.err.println("Calculator threw Exception: "+ce.getMessage());
		    } catch (Exception e) {
		      System.err.println("Something did not work, see stack trace.");
		      e.printStackTrace();
		    }
		  }	// TODO Auto-generated method stub
		
	

}
