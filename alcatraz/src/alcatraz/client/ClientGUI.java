package alcatraz.client;

import java.awt.Label;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import alcatraz.RemotePlayer;

public class ClientGUI extends JFrame {

	private static final long serialVersionUID = 1L;

	private JPanel contentPane;
	private JTextField tfName;
	private JTextField tfServerAdr;
	private JTextArea outputArea;
	private JRadioButton rb2players;
	private JRadioButton rb3players;
	private JRadioButton rb4players;
	private JButton btnRegister;
	private JButton btnUnregister;

	/**
	 * Create the frame.
	 */
	public ClientGUI(final RemotePlayer p) {
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

		btnUnregister = new JButton("unregister");
		btnUnregister.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(Client.unregisterPlayer(p) == true) {
					btnUnregister.setEnabled(false);
					btnRegister.setEnabled(true);
					outputArea.append("You successfully unregistered!\n");
				}
				else {
					outputArea.append("Could not unregister!\n");
				}
				
				
			}
		});
		btnUnregister.setEnabled(false);
		btnUnregister.setBounds(30, 158, 95, 23);
		contentPane.add(btnUnregister);

		btnRegister = new JButton("register");
		btnRegister.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				if (gatherFieldInformation(p) == true) {
					if(Client.registerPlayer(p) == true) {
						btnRegister.setEnabled(false);
						btnUnregister.setEnabled(true);
						outputArea.append("You successfully registered:\n");
						outputArea.append("Name: " + p.getName() + "\n");
						outputArea.append("Server: " + p.getServerAdr() + "\n");
						outputArea.append("Game for: " + p.getDesiredNumPlayers() + "\n");
					}
					else {
						outputArea.append("Username already taken or Server not found!");
					}
				} else {
					outputArea.append("Please fill out all the fields.\n");
				}

			}
		});

		btnRegister.setBounds(141, 158, 89, 23);
		contentPane.add(btnRegister);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(253, 28, 251, 153);
		contentPane.add(scrollPane);

		outputArea = new JTextArea();
		outputArea.setEnabled(false);
		outputArea.setEditable(false);
		scrollPane.setViewportView(outputArea);

	}

	// ================================================================================
	// ================================================================================
	// METHODS

	/**
	 * Checks if there is any input - does not check if the input is valid.
	 * 
	 * @author max
	 * @param p
	 * @return Returns false if there is an empty field or no radiobutton is
	 *         selected.<br>
	 *         Returns true if there is input in each field and one radiobutton
	 *         is selected
	 */
	private boolean gatherFieldInformation(RemotePlayer p) {

		boolean allClear = true;
		boolean nameClear = true;
		boolean serverAdrClear = true;
		boolean numberClear = true;

		// Player name
		if (!(tfName.getText().equals(""))) {
			p.setName(tfName.getText());
		} else {
			nameClear = false;
		}

		// Server address
		if (!(tfServerAdr.getText().equals(""))) {
			p.setServerAdr(tfServerAdr.getText());
		} else {
			serverAdrClear = false;
		}

		// Number of Players
		if (rb2players.isSelected()) {
			p.setDesiredNumPlayers(2);
		} else if (rb3players.isSelected()) {
			p.setDesiredNumPlayers(3);
		} else if (rb4players.isSelected()) {
			p.setDesiredNumPlayers(4);
		} else {
			numberClear = false;
		}

		// if something was not filled out properly
		if (nameClear == false | serverAdrClear == false | numberClear == false) {
			allClear = false;
		}

		return allClear;
	}
}
