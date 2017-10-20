import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * 
 */

/**
 * @author trieu
 *
 */
public class LoginScreen extends JFrame implements ActionListener {

	private String username;
	private String password;
	private String role;
	
	private JLabel usernameLabel;
	private JLabel passwordLabel;
	private JTextField usernameText;
	private JTextField passwordText;
	private JButton loginButton;
	private JButton exitButton;
	
	/**
	 * 
	 */
	public LoginScreen() {
		
		//Set frame
		setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
		setTitle("Login");
		setResizable(false);
		Container contentPane = getContentPane();
		
		//Create panel
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setPreferredSize (new Dimension(600, 400));

		//Labels
		usernameLabel = new JLabel("Username:");
		usernameLabel.setBounds(30, 25, 80, 20);
		panel.add(usernameLabel);
		passwordLabel = new JLabel("Password:");
		passwordLabel.setBounds(30, 55, 80, 20);
		panel.add(passwordLabel);

		//TextFields
		usernameText = new JTextField();
		usernameText.setBounds(120, 25, 180, 20);
		panel.add(usernameText);
		passwordText = new JPasswordField();
		passwordText.setBounds(120, 55, 180, 20);
		passwordText.addActionListener(this);
		panel.add(passwordText);

		//Buttons
		loginButton = new JButton("Login");
		loginButton.setFont(new Font("Arial", Font.PLAIN, 15));
		loginButton.setBounds(120, 85, 90, 45);
		panel.add(loginButton);
		loginButton.addActionListener(this);
		
		contentPane.add(panel);
		pack();
		setLocationRelativeTo(null);
		
	}
	
	/**
	 * @return
	 */
	private boolean verify() {
		String realPassword = DataProcessor.getPassword(this.username);
		if (realPassword.compareTo(this.password) != 0)
			return false;
		this.role = DataProcessor.getRole(this.username);
		return true;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO
	}
	
}
