import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

/**
 * 
 */

/**
 * @author trieu
 *
 */
public class ReportScreen extends JFrame implements ActionListener {
	
	private static final String SEND = "send";
	private static final String RETURN = "return";
	
	private LoginScreen loginScr;
	private JFrame previousScr;
	
	private JTextArea reportBox;
	private JButton returnButton;
	private JButton reportButton;
	private JLabel message;
	
	/**
	 * @throws IOException 
	 * 
	 */
	public ReportScreen() throws IOException {
		
		//Set frame
		setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
		setTitle("CSE CHATBOT - REPORT");
		setResizable(false);
		Container contentPane = getContentPane();
		
		//Create panel
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.setPreferredSize (new Dimension(600, 400));
		panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

		//CSE image
		BufferedImage csePicture = ImageIO.read(new File("Images/CSE_logo.png"));
		JLabel picLabel = new JLabel(new ImageIcon(csePicture));
		picLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel.add(picLabel, BorderLayout.WEST);
		
		//Report panel
		JPanel reportPanel = new JPanel();
		reportPanel.setLayout(new BorderLayout(10, 10));
		reportPanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));
        
        //Return button
        returnButton = new JButton("RETURN");
        returnButton.setPreferredSize(new Dimension(90, 40));
        returnButton.setActionCommand(RETURN);
        returnButton.addActionListener(this);
        
        //Report button
		reportButton = new JButton("SEND");
		reportButton.setPreferredSize(new Dimension(90, 40));
		reportButton.setActionCommand(SEND);
		reportButton.addActionListener(this);
        
		//Report box label
		JLabel reportLabel = new JLabel("REPORT:");
		reportLabel.setLabelFor(reportBox);
		reportPanel.add(reportLabel, BorderLayout.NORTH);
		
        //Report box
        reportBox = new JTextArea();
        reportBox.setEditable(true);
        reportBox.setWrapStyleWord(true);
        reportBox.setFont(new Font("Courier", Font.PLAIN, 14));
        reportBox.setLineWrap(true);
        reportBox.setMargin(new Insets(10, 10, 10, 10));
        reportPanel.add(new JScrollPane(reportBox), BorderLayout.CENTER);
        
        //Message Label
        message = new JLabel(" ");
        message.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        //Action panel
        JPanel actionPanel = new JPanel();
        actionPanel.setLayout(new BoxLayout(actionPanel, BoxLayout.Y_AXIS));
        actionPanel.add(message);
        
        //button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 50, 5));
        buttonPanel.add(reportButton);
        buttonPanel.add(returnButton);
        buttonPanel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        actionPanel.add(buttonPanel);
        
        reportPanel.add(actionPanel, BorderLayout.SOUTH);
        panel.add(reportPanel, BorderLayout.CENTER);
        
		contentPane.add(panel);
		pack();
		setLocationRelativeTo(null);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		
		if (SEND.equals(cmd)) {
			message.setText(" ");
			String report = reportBox.getText();
			if (report.length() < 1) {
				message.setText("Report cannot be empty.");
			}
			else {
				DataProcessor.insertReport(loginScr.getUsername(), report);
				message.setText("Successfully reported.");
				reportBox.setText("");
			}
		}
		else {
			message.setText(" ");
			reportBox.setText("");
			this.setVisible(false);
			previousScr.setVisible(true);
		}
	}

	/**
	 * @param loginScr the loginScr to set
	 */
	public void setLoginScr(LoginScreen loginScr) {
		this.loginScr = loginScr;
	}

	public void appearFrom(JFrame preScr) {
		this.setVisible(true);
		this.previousScr = preScr;
	}
	
}
