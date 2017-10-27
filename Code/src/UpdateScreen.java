import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

/**
 * This class implements the Update Screen frame with all components.
 * @author Group 11 CSCE 361
 * @date Fall 2017
 */
public class UpdateScreen extends JFrame implements ActionListener {

	//Action commands
	private static final String CHAT 	= "chat";
	private static final String REPORT 	= "report";
	private static final String UPDATE 	= "update";
	
	//Instances of other screens
	private ReportScreen 		reportScr;
	private ChatScreen 			chatScr;
	
	//Java GUI components
	private JTextArea 			questionBox;
	private JTextArea 			answerBox;
	private JButton 			chatButton;
	private JButton 			updateButton;
	private JButton 			reportButton;
	private JLabel 				message;
	
	/**
	 * This constructor creates Update Screen Frame
	 * @throws IOException 
	 */
	public UpdateScreen() throws IOException {
		
		//Set frame
		setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
		setTitle("CSE CHATBOT - UPDATE");
		setResizable(false);
		Container contentPane = getContentPane();
		
		//Create panel
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.setPreferredSize (new Dimension(600, 400));
		panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

		//CSE image
		BufferedImage csePicture = ImageIO.read(getClass().getResource("CSE_logo.png"));
		JLabel picLabel = new JLabel(new ImageIcon(csePicture));
		picLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel.add(picLabel, BorderLayout.WEST);
		
		//Update panel
		JPanel updatePanel = new JPanel();
		updatePanel.setLayout(new BorderLayout());
		updatePanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));
		
		//Chat button
		chatButton = new JButton("RETURN TO CHAT");
		chatButton.setActionCommand(CHAT);
        chatButton.addActionListener(this);
        
        //Update button
        updateButton = new JButton("UPDATE");
        updateButton.setActionCommand(UPDATE);
        updateButton.addActionListener(this);
        
        //Report button
		reportButton = new JButton("REPORT");
		reportButton.setActionCommand(REPORT);
		reportButton.addActionListener(this);
        
		//Question box label
		JLabel questionLabel = new JLabel("QUESTION", JLabel.TRAILING);
		questionLabel.setLabelFor(questionBox);
		
        //Question box
        questionBox = new JTextArea();
        questionBox.setEditable(true);
        questionBox.setWrapStyleWord(true);
        questionBox.setFont(new Font("Courier", Font.PLAIN, 14));
        questionBox.setLineWrap(true);
        questionBox.setMargin(new Insets(10, 10, 10, 10));
        
	    //Answer box label
	    JLabel answerLabel = new JLabel("ANSWER", JLabel.TRAILING);
		answerLabel.setLabelFor(answerBox);
	    
	    //Answer box
	    answerBox = new JTextArea();
	    answerBox.setEditable(true);
	    answerBox.setWrapStyleWord(true);
	    answerBox.setFont(new Font("Courier", Font.PLAIN, 14));
	    answerBox.setLineWrap(true);
	    answerBox.setMargin(new Insets(10, 10, 10, 10));
        
        //input panel
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new SpringLayout());
        inputPanel.add(questionLabel);
        inputPanel.add(new JScrollPane(questionBox));
        inputPanel.add(answerLabel);
        inputPanel.add(new JScrollPane(answerBox));
        SpringUtilities.makeCompactGrid(inputPanel, 2, 2, 6, 6, 20, 10);
        updatePanel.add(inputPanel, BorderLayout.CENTER);
        
        //Message Label
        message = new JLabel(" ");
        message.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        //Action panel
        JPanel actionPanel = new JPanel();
        actionPanel.setLayout(new BoxLayout(actionPanel, BoxLayout.Y_AXIS));
        actionPanel.add(message);
        
        //button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.add(updateButton);
        buttonPanel.add(chatButton);
        buttonPanel.add(reportButton);
        buttonPanel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        actionPanel.add(buttonPanel);
        
        updatePanel.add(actionPanel, BorderLayout.SOUTH);
        panel.add(updatePanel, BorderLayout.CENTER);
        
		contentPane.add(panel);
		pack();
		setLocationRelativeTo(null);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		
		if (UPDATE.equals(cmd)) {
			message.setText(" ");
			String question = questionBox.getText();
			String answer = answerBox.getText();
			if (question.length() < 1) {
				message.setText("Question cannot be empty.");
			}
			else if (answer.length() < 1) {
				message.setText("Answer cannot be empty.");
			}
			else {
				DataProcessor.insertQA(question, answer);
				chatScr.getQuestionList();
				message.setText("Successfully updated.");
				questionBox.setText("");
				answerBox.setText("");
			}
		}
		else if (CHAT.equals(cmd)) {
			message.setText(" ");
			questionBox.setText("");
			answerBox.setText("");
			this.setVisible(false);
			chatScr.setVisible(true);
		}
		else {//Report
			message.setText(" ");
			questionBox.setText("");
			answerBox.setText("");
			this.setVisible(false);
			reportScr.appearFrom(this);
		}
	}

	/**
	 * @param reportScr the reportScr to set
	 */
	public void setReportScr(ReportScreen reportScr) {
		this.reportScr = reportScr;
	}

	/**
	 * @param chatScr the chatScr to set
	 */
	public void setChatScr(ChatScreen chatScr) {
		this.chatScr = chatScr;
	}
	
}
