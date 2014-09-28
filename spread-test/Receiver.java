import spread.*; 

public class SpreadMember {

  public static void main(String args[]) {

      	System.out.println("Starting spread member...");
	SpreadConnection connection = new SpreadConnection();
	connection.connect(InetAddress.getByName("localhost"), 0, "privatename", false, true);

      	System.out.println("Joining group...");
	SpreadGroup group = new SpreadGroup();
	group.join(connection, "group");

	while (true) {
		SpreadMessage message = connection.receive(); 

		if(message.isRegular())
		    System.out.println("New message from " + message.getSender());
		else
		    System.out.println("New membership message from " + message.getMembershipInfo().getGroup());
	}
  }
}
