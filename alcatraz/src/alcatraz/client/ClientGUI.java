package alcatraz.client;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JPopupMenu;

import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JRadioButtonMenuItem;

import java.awt.Choice;
import java.awt.Label;

import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JScrollBar;

public class ClientGUI extends JFrame {

	private JPanel contentPane;
	private JTextField tfName;
	private JTextField tfServerAdr;
	private JTextArea outputArea;
	private JRadioButton rb2players;
	private JRadioButton rb3players;
	private JRadioButton rb4players;

	/**
	 * Create the frame.
	 */
	public ClientGUI(final Client c) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 530, 230);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
				
		tfName = new JTextField();
		tfName.setBounds(30, 28, 200, 20);
		contentPane.add(tfName);
		tfName.setColumns(10);
		
		tfServerAdr = new JTextField();
		tfServerAdr.setBounds(30, 70, 200, 20);
		contentPane.add(tfServerAdr);
		tfServerAdr.setColumns(10);
		
		JLabel lblUsername = new JLabel("Username");
		lblUsername.setBounds(32, 10, 64, 14);
		contentPane.add(lblUsername);
		
		JLabel lblServerIpAddress = new JLabel("Server IP Address");
		lblServerIpAddress.setBounds(32, 52, 108, 14);
		contentPane.add(lblServerIpAddress);
		
		Label lblNumberOfPlayers = new Label("Number Of Players");
		lblNumberOfPlayers.setBounds(32, 98, 110, 22);
		contentPane.add(lblNumberOfPlayers);
		
		rb2players = new JRadioButton("2");
		rb2players.setBounds(30, 120, 35, 20);
		contentPane.add(rb2players);
		
		rb3players = new JRadioButton("3");
		rb3players.setBounds(70, 120, 35, 20);
		contentPane.add(rb3players);
		
		rb4players = new JRadioButton("4");
		rb4players.setBounds(110, 120, 35, 20);
		contentPane.add(rb4players);
		
		ButtonGroup group = new ButtonGroup();
		group.add(rb2players);
		group.add(rb3players);
		group.add(rb4players);
		
	JButton btnRegister = new JButton("register");
	btnRegister.addMouseListener(new MouseAdapter() {
		@Override
		public void mouseClicked(MouseEvent e) {
			
			
			
			if(gatherFieldInformation(c) == true){
				//c.registerPlayer(c.getServerAdr(), p, c.getNumPlayer());
			}
			
					
		}
	});
	
	btnRegister.setBounds(141, 158, 89, 23);
	contentPane.add(btnRegister);
	
	JScrollPane scrollPane = new JScrollPane();
	scrollPane.setBounds(253, 28, 251, 153);
	contentPane.add(scrollPane);
	
	outputArea = new JTextArea();
	scrollPane.setViewportView(outputArea);
	}
	
	// ================================================================================
	// ================================================================================
	// METHODS
	private boolean gatherFieldInformation(Client c) {
		
		boolean allClear = true;
		boolean nameClear = true;
		boolean serverAdrClear = true;
		boolean numberClear = true;
		
		// Player name
		if(!(tfName.getText().equals(""))) {
			c.setName(tfName.getText());
			outputArea.append("Name: " + c.getName() + "\n");
		}
		else{
			outputArea.append("ERROR: Please enter a name!\n");
			nameClear = false;
		}
		
		// Server address
		if(!(tfServerAdr.getText().equals(""))) {
			c.setServerAdr(tfServerAdr.getText());
			outputArea.append("Server: " + c.getServerAdr() + "\n");
			
		}
		else{
			outputArea.append("ERROR: Please enter a server address!\n");
			serverAdrClear = false;
		}
		
		// Number of Players
		if(rb2players.isSelected()) {
			c.setNumPlayer(2);
			outputArea.append("A game for " + c.getNumPlayer() + " players\n");
		}
		else if(rb3players.isSelected()) {
			c.setNumPlayer(3);
			outputArea.append("A game for " + c.getNumPlayer() + " players\n");
		}
		else if(rb4players.isSelected()) {
			c.setNumPlayer(4);
			outputArea.append("A game for " + c.getNumPlayer() + " players\n");
		}
		else{
			outputArea.append("ERROR: Please select the number of players!\n");
			numberClear = false;
		}
		
		// if something was not filled out properly
		if(nameClear == false | serverAdrClear == false | numberClear == false) {
			allClear = false;
		}
		
		return allClear;
	}

	//TODO: delete later 
	/*
	private static void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}

			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}

			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}
	*/
}
