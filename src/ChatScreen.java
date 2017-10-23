import java.util.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
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
public class ChatScreen extends JFrame implements ActionListener {
	
	private static final String SEND = "send";
	private static final String REPORT = "report";
	private static final String UPDATE = "update";
	
	private ArrayList<String> badWords;
	
	private LoginScreen loginScr;
	private ReportScreen reportScr;
	private UpdateScreen updateScr;
	
	private JTextField messageBox;
	private JTextArea chatBox;
	private JButton sendButton;
	private JButton updateButton;
	private JButton reportButton;
	private JPanel buttonPanel;
	
	/**
	 * @throws IOException 
	 * 
	 */
	public ChatScreen() throws IOException {
		
		//Read bad words
		badWords = new ArrayList<String>();
		Scanner scnr = new Scanner(new File("Data/BadWords.txt"));
		while (scnr.hasNextLine()) {
			String line = scnr.nextLine();
			badWords.add(line);
		}
		scnr.close();
		
		//Set frame
		setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
		setTitle("CSE CHATBOT - CHAT");
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
		
		//Chat panel
		JPanel chatPanel = new JPanel();
		chatPanel.setLayout(new BorderLayout(0, 5));
		chatPanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));
		
		//Message Box
		messageBox = new JTextField(30);
		messageBox.requestFocusInWindow();
		messageBox.setActionCommand(SEND);
		messageBox.addActionListener(this);
		messageBox.setMargin(new Insets(10, 10, 10, 0));
		
		//Send button
		sendButton = new JButton("SEND");
		sendButton.setActionCommand(SEND);
        sendButton.addActionListener(this);
        sendButton.setPreferredSize(new Dimension(90, 40));
        
        //Update button
        updateButton = new JButton("UPDATE");
        updateButton.setActionCommand(UPDATE);
        updateButton.addActionListener(this);
        updateButton.setPreferredSize(new Dimension(90, 40));
        
        //Report button
		reportButton = new JButton("REPORT");
		reportButton.setActionCommand(REPORT);
		reportButton.addActionListener(this);
		reportButton.setPreferredSize(new Dimension(90, 40));
        
        //Chat box
        chatBox = new JTextArea();
        chatBox.setEditable(false);
        chatBox.setFont(new Font("Serif", Font.PLAIN, 15));
        chatBox.setLineWrap(true);
        chatBox.setMargin(new Insets(10, 10, 10, 10));
        chatPanel.add(new JScrollPane(chatBox), BorderLayout.CENTER);
        
        //input panel
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));
        inputPanel.add(messageBox);
        
        //button panel
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.add(sendButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(reportButton);
        buttonPanel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        inputPanel.add(buttonPanel);
        chatPanel.add(inputPanel, BorderLayout.SOUTH);
        
        panel.add(chatPanel, BorderLayout.CENTER);
        
		contentPane.add(panel);
		pack();
		setLocationRelativeTo(null);
		
	}
	
	/**
	 * @param word
	 * @return
	 */
	private String wordProcessing(String word) {
		String processedWord = new String(word.toLowerCase());
		if (badWords.contains(processedWord)) {
			processedWord = "";
		}
		else {
			int len = processedWord.length();
			if (len > 3 && processedWord.substring(len - 3, len).equals("ing")) {
				processedWord = processedWord.substring(0, len - 3);
			}
			else if (len > 2 && word.substring(len - 2, len).equals("ed")) {
				processedWord = processedWord.substring(0, len - 2);
			}
		}
		return processedWord;
	}
	
	/**
	 * @param text
	 * @return
	 */
	private ArrayList<String> textProcessing(String text) {
		//TODO
		String copiedText = new String(text);
		ArrayList<String> processedText = new ArrayList<String>();
		
		for(int i = 0; i < text.length(); i++) 
			if (Character.isLetter(text.charAt(i)) 
				|| Character.isDigit(text.charAt(i))
				|| text.charAt(i) == ' ') 
				copiedText += text.charAt(i);
		
		String[] words = copiedText.split(" ");
		
		
		return processedText;
	}
	
	/**
	 * @param question
	 * @return
	 */
	private boolean reportNewQuestion(String question) {
		return DataProcessor.insertReport(loginScr.getUsername(), "New question: " + question);
	}
	
	/**
	 * @param inputQuestion
	 * @param questionList
	 * @return
	 */
	private ArrayList<String> findMatch(String inputQuestion, ArrayList<String> questionList) {
		//TODO 
		ArrayList<String> matchedQuestions = new ArrayList<String>();
		return matchedQuestions;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		
		if (SEND.equals(cmd)) {
			if (messageBox.getText().length() < 1) {
                // do nothing
            } else {
                chatBox.append(String.format("%-10s : %s\n", loginScr.getUsername().toUpperCase(), messageBox.getText()));
                messageBox.setText("");
                // TODO
            } 
            messageBox.requestFocusInWindow();
		}
		else if (UPDATE.equals(cmd)) {
			messageBox.setText("");
			this.setVisible(false);
			updateScr.setVisible(true);
		}
		else {
			messageBox.setText("");
			this.setVisible(false);
			reportScr.appearFrom(this);
		}
	}

	public void appearFor(String role) {
		if (role.equals("Student")) 
			buttonPanel.remove(updateButton);
		this.setVisible(true);
	}

	/**
	 * @param loginScr the loginScr to set
	 */
	public void setLoginScr(LoginScreen loginScr) {
		this.loginScr = loginScr;
	}

	/**
	 * @param reportScr the reportScr to set
	 */
	public void setReportScr(ReportScreen reportScr) {
		this.reportScr = reportScr;
	}

	/**
	 * @param updateScr the updateScr to set
	 */
	public void setUpdateScr(UpdateScreen updateScr) {
		this.updateScr = updateScr;
	}
	
}
