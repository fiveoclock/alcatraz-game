// package at.falb.fh.vtsys;

import at.falb.games.alcatraz.api.Alcatraz;
import at.falb.games.alcatraz.api.MoveListener;
import at.falb.games.alcatraz.api.Player;
import at.falb.games.alcatraz.api.Prisoner;

import javax.swing.JOptionPane;
import javax.swing.JTextField;

import spread.*;
import java.net.*;

/**
 * A AGame class initializing a local Alcatraz game -- illustrating how
 * to use the Alcatraz API.
 */
public class AGame implements MoveListener {

    private Alcatraz other[] = new Alcatraz[4];
    private int numPlayer = 2;
    
    private String username;
    private String host;
    private String group;

    public AGame() {
    }
    
    public void setOther(int i, Alcatraz t) {
        this.other[i] = t;
    }
    
    public int getNumPlayer() {
        return numPlayer;
    }

    public void setNumPlayer(int numPlayer) {
        this.numPlayer = numPlayer;
    }

    public void moveDone(Player player, Prisoner prisoner, int rowOrCol, int row, int col) {
        System.out.println("moving " + prisoner + " to " + (rowOrCol == Alcatraz.ROW ? "row" : "col") + " " + (rowOrCol == Alcatraz.ROW ? row : col));
        for (int i = 0; i < getNumPlayer() - 1; i++) {
            other[i].doMove(other[i].getPlayer(player.getId()), other[i].getPrisoner(prisoner.getId()), rowOrCol, row, col);
        }
    }

    public void undoMove() {
         System.out.println("Undoing move");
    }
    
    public void gameWon(Player player) {
        System.out.println("Player " + player.getId() + " wins.");
    }

    public void startDialog(String[] args) {
		// Erstellung Array vom Datentyp Object, Hinzufügen der Komponenten		
		JTextField name = new JTextField();
		JTextField host = new JTextField();
		JTextField group = new JTextField();
		Object[] message = {"Name", name, 
        	"Spread Host", host,
        	"Group", group
        };
        
        if (args.length >= 3) {
			name.setText(args[0]);
			host.setText(args[1]);
			group.setText(args[2]);
		}

        JOptionPane pane = new JOptionPane( message, 
		  JOptionPane.PLAIN_MESSAGE, 
		  JOptionPane.OK_CANCEL_OPTION);
		pane.createDialog(null, "Alcatraz").setVisible(true);
		
		System.out.println("Name: " + name.getText() + ", Host: " + host.getText());
		this.username = name.getText();
		this.host = host.getText();
		this.group = group.getText();
	}
    
    public void joinGroup() {
		try {
			System.out.println("Connecting to spread deamon...");
			SpreadConnection connection = new SpreadConnection();
			connection.connect(InetAddress.getByName(this.host), 0, this.username, false, true);

			System.out.println("Joining group...");
			SpreadGroup group = new SpreadGroup();
			group.join(connection, this.group);
		} 
		catch (Exception e) {
			System.out.println("Trouble!");
			e.printStackTrace();
		}
	}
    
    /**
     * @param args Command line args
     */
    public static void main(String[] args) {
		
        AGame t1 = new AGame();

		t1.startDialog(args);
		t1.joinGroup();
		
		Alcatraz a1 = new Alcatraz();
		t1.setNumPlayer(2);

        a1.init(2, 0);
//        a2.init(2, 1);
//        a1.init(3, 0);
        
        a1.getPlayer(0).setName("Player 1");
        a1.getPlayer(1).setName("Player 2");
        
//        t1.setOther(0, a2);
//        t1.setOther(1, a3);
        
        a1.showWindow();
        a1.addMoveListener(t1);
        
        a1.start();
    }

}
