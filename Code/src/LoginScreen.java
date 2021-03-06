import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

/**
 * This class implements the Login Screen frame with all components.
 * @author Group 11 CSCE 361
 * @date Fall 2017
 */
public class LoginScreen extends JFrame implements ActionListener {

	//Action commands
	private static final String LOGIN 	= "login";
	private static final String REPORT 	= "report";
	
	//Data variables
	private String 				username = "anonymous";
	private String 				role;
	
	//Instances of other screens
	private ChatScreen 			chatScr;
	private ReportScreen 		reportScr;
	
	//Java GUI components
	private JLabel 				messageLabel;
	private JTextField 			usernameText;
	private JPasswordField 		passwordText;
	private JButton 			loginButton;
	private JButton 			reportButton;
	
	/**
	 * This constructor creates Login Screen Frame
	 * @throws IOException 
	 */
	public LoginScreen() throws IOException {
		
		//Set frame
		setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
		setTitle("CSE CHATBOT - LOGIN");
		setResizable(false);
		Container contentPane = getContentPane();
		
		//Create panel
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.setPreferredSize (new Dimension(600, 400));
		panel.setBorder(BorderFactory.createEmptyBorder(50, 80, 50, 80));

		//CSE image
		BufferedImage csePicture = ImageIO.read(getClass().getResource("cse.png"));
		JLabel picLabel = new JLabel(new ImageIcon(csePicture));
		picLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel.add(picLabel);
		panel.add(Box.createRigidArea(new Dimension(0, 40)));
		
		//Message Label
		messageLabel = new JLabel(" ");
		messageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel.add(messageLabel);
		
		//Text panel
		JPanel textPanel = new JPanel();
		textPanel.setLayout(new SpringLayout());
		textPanel.setBorder(BorderFactory.createEmptyBorder(10, 90, 0, 90));
		
		//Username label
		JLabel usernameLabel = new JLabel("USERNAME:", JLabel.TRAILING);
		textPanel.add(usernameLabel);
		
		//Username Field
		usernameText = new JTextField(10);
		usernameText.setHorizontalAlignment(JTextField.CENTER);
		usernameLabel.setLabelFor(usernameText);
		textPanel.add(usernameText);
		
		//Password label
		JLabel passwordLabel = new JLabel("PASSWORD:", JLabel.TRAILING);
		textPanel.add(passwordLabel);

		//Password Field
		passwordText = new JPasswordField(10);
		passwordText.setHorizontalAlignment(JTextField.CENTER);
		passwordText.addActionListener(this);
		passwordText.setActionCommand(LOGIN);
		passwordLabel.setLabelFor(passwordText);
		textPanel.add(passwordText);

		SpringUtilities.makeCompactGrid(textPanel, 2, 2, 6, 6, 20, 20);
		
		panel.add(textPanel);
		panel.add(Box.createRigidArea(new Dimension(0, 10)));
		
		//Buttons panel
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 0));
		buttonPanel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		panel.add(buttonPanel);
		
		//Login Button
		loginButton = new JButton("LOGIN");
		loginButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		loginButton.setPreferredSize(new Dimension(90, 40));
		loginButton.addActionListener(this);
		loginButton.setActionCommand(LOGIN);
		buttonPanel.add(loginButton);
//		buttonPanel.add(Box.createRigidArea(new Dimension(20, 0)));
		
		//Report Button
		reportButton = new JButton("REPORT");
		reportButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		reportButton.setPreferredSize(new Dimension(90, 40));
		reportButton.addActionListener(this);
		reportButton.setActionCommand(REPORT);
		buttonPanel.add(reportButton);
	
		contentPane.add(panel);
		pack();
		setLocationRelativeTo(null);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();

		if (LOGIN.equals(cmd)) {
			messageLabel.setText(" ");
			String inputUsername = usernameText.getText().trim();
			String inputPassword = new String(passwordText.getPassword());
			if (inputUsername.length() < 1) {
				messageLabel.setText("Username cannot be empty.");
				usernameText.setText("");
			}
			else if (inputPassword.length() < 1) {
				messageLabel.setText("Password cannot be empty.");
			}
			else if (isCorrectPassword(inputUsername, inputPassword)) {
				this.username = inputUsername;
				this.role = DataProcessor.getRole(this.username);
				this.setVisible(false);
				chatScr.appearFor(this.role);
			}
			else {
				messageLabel.setText("Invalid password. Try again.");
			}
		}
		else { //Report
			this.setVisible(false);
			reportScr.appearFrom(this);
		}
	}

	/**
	 * @param charScr the charScr to set
	 */
	public void setChatScr(ChatScreen chatScr) {
		this.chatScr = chatScr;
	}

	/**
	 * @param reportScr the reportScr to set
	 */
	public void setReportScr(ReportScreen reportScr) {
		this.reportScr = reportScr;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * This method checks the correctness of the password
	 * @param inputUsername
	 * @param inputPassword
	 * @return true if password if correct, false otherwise
	 */
	private boolean isCorrectPassword(String inputUsername, String inputPassword) {
		String realPassword = DataProcessor.getPassword(inputUsername);
		if (realPassword.equals(inputPassword))
			return true;
		else return false;
	}
	
}
