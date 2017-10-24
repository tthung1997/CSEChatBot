import java.util.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

/**
 * This class implements the Chat Screen frame with all components.
 * @author Group 11 CSCE 361
 * @date Fall 2017
 */
public class ChatScreen extends JFrame implements ActionListener {
	
	//Action Commands
	private static final String 			SEND 	= "send";
	private static final String 			REPORT 	= "report";
	private static final String 			UPDATE 	= "update";
	
	//Data variables
	private ArrayList<String> 				badWords;
	private ArrayList<String> 				questionList;
	private ArrayList< ArrayList<String> > 	processedQuestionList;
	
	//Instances of other screens
	private LoginScreen 					loginScr;
	private ReportScreen 					reportScr;
	private UpdateScreen 					updateScr;
	
	//Java GUI components
	private JTextField 						messageBox;
	private JTextArea 						chatBox;
	private JButton 						sendButton;
	private JButton 						updateButton;
	private JButton 						reportButton;
	private JPanel 							buttonPanel;
	
	/**
	 * This constructor creates Chat Screen Frame
	 * @throws IOException 
	 */
	public ChatScreen() throws IOException {
		
		//Read bad words
		badWords = new ArrayList<String>();
		Scanner scnr = new Scanner(new File("Data/BadWords.txt"));
		while (scnr.hasNextLine()) {
			String line = scnr.nextLine().toLowerCase().trim();
			badWords.add(line);
		}
		scnr.close();
		
		//Process question list
		this.getQuestionList();
		
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
        chatBox.setWrapStyleWord(true);
        chatBox.setFont(new Font("Courier", Font.PLAIN, 12));
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
	 * This method retrieves the list of questions from the database
	 */
	public void getQuestionList() {
		questionList = DataProcessor.getQuestions();
		processedQuestionList = new ArrayList< ArrayList<String> >();
		for(String question : questionList) {
			processedQuestionList.add(textProcessing(question));
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		if (SEND.equals(cmd)) {
			if (messageBox.getText().length() < 1) {
                // do nothing because user inputs nothing
            } else {
            	String inputQuestion = messageBox.getText().trim();
                chatBox.append(String.format("<< %s >> %s\n", 
                		loginScr.getUsername().trim().toUpperCase(), inputQuestion));
                messageBox.setText("");
                chatBox.append(String.format("<< CSE-BOT >> %s\n\n", 
                		getAnswer(inputQuestion).trim()));                
            } 
            messageBox.requestFocusInWindow();
		}
		else if (UPDATE.equals(cmd)) {
			messageBox.setText("");
			this.setVisible(false);
			updateScr.setVisible(true);
		}
		else { //Report
			messageBox.setText("");
			this.setVisible(false);
			reportScr.appearFrom(this);
		}
	}

	/**
	 * This method activates the Chat Screen according to user's role
	 * @param role
	 */
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
	
	/**
	 * This method processes the input word by removing bad words and
	 * verb suffix
	 * @param word
	 * @return processedWord 
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
	 * This method processes the input sentence by breaking it into words
	 * and processing each word separately.
	 * @param text
	 * @return an ArrayList of important words
	 */
	private ArrayList<String> textProcessing(String text) {
		String copiedText = "";
		ArrayList<String> processedText = new ArrayList<String>();
		for(int i = 0; i < text.length(); i++) 
			if (Character.isLetter(text.charAt(i)) 
				|| Character.isDigit(text.charAt(i))
				|| text.charAt(i) == ' ') { 
				copiedText += text.charAt(i);
			}
		
		String[] words = copiedText.split(" ");
		for(int i = 0; i < words.length; i++) {
			words[i] = wordProcessing(words[i]);
			if (!words[i].equals("")) {
				processedText.add(words[i]);
			}
		}
		return processedText;
	}
	
	/**
	 * This method uses DataProcessor's method insertReport to
	 * inform system manager about new question.
	 * @param question
	 */
	private void reportNewQuestion(String question) {
		DataProcessor.insertReport(loginScr.getUsername(), "New question: " + question);
	}
	
	/**
	 * This method calculates the matching percentage between the input and a question
	 * from the questionList
	 * @param input
	 * @param question
	 * @return the matching percentage
	 */
	private double getPercentage(ArrayList<String> input, ArrayList<String> question) {
		double forwardPercentage = 0;
		double backwardPercentage = 0;
		for(String word : input) {
			if (question.contains(word)) {
				forwardPercentage += 1;
			}
		}
		for(String word : question) {
			if (input.contains(word)) {
				backwardPercentage += 1;
			}
		}
		return Math.max(forwardPercentage / input.size(), backwardPercentage / question.size());
	}
	
	/**
	 * This method returns a list of questions that match user's input question
	 * - A matched question is a question that has higher than 80% matching percentage
	 * - If no matched question is found, it will return a list of nearly matched 
	 * questions instead
	 * @param inputQuestion
	 * @return an array of matched questions
	 */
	private ArrayList<String> findMatch(String inputQuestion) {
		ArrayList<String> processedQuestion = textProcessing(inputQuestion);
		ArrayList<Integer> matchedID = new ArrayList<Integer>();
		ArrayList<Integer> nearlyMatchedID = new ArrayList<Integer>();
		ArrayList<String> matchedQuestions = new ArrayList<String>();
		for(int i = 0; i < processedQuestionList.size(); i++) {
			ArrayList<String> question = processedQuestionList.get(i);
			double percentage = getPercentage(processedQuestion, question); 
			if (percentage > 0.8) {
				matchedID.add(i);
			}
			else if (percentage > 0.6) {
				nearlyMatchedID.add(i);
			}
		}
		if (matchedID.size() == 0) {
			if (nearlyMatchedID.size() == 0) {
				matchedQuestions.add("Sorry, I cannot answer your question right now "
						+ "because it is not in the database!");
				reportNewQuestion(inputQuestion);
			}
			else if (nearlyMatchedID.size() <= 5) {
				for(Integer id : nearlyMatchedID) { 
					matchedQuestions.add(questionList.get(id));
				}
			}
			else {
				matchedQuestions.add("Sorry, your question is too broad, could you make "
						+ "it clearer?");
			}
		}
		else if (matchedID.size() <= 5) {
			for(Integer id : matchedID) { 
				matchedQuestions.add(questionList.get(id));
			}
		}	
		else {
			matchedQuestions.add("Sorry, your question is too broad, could you make "
					+ "it clearer?");
		}
		return matchedQuestions;
	}
	
	/**
	 * This method creates the answer for a specific inputQuestion
	 * @param inputQuestion
	 * @return answer
	 */
	private String getAnswer(String inputQuestion) {
		String answer = "";
		ArrayList<String> matchedQuestions = findMatch(inputQuestion);
		if (matchedQuestions.size() == 1) {
			if (matchedQuestions.get(0).length() >= 5 
					&& matchedQuestions.get(0).substring(0, 5).equals("Sorry")) {
				answer = matchedQuestions.get(0);
			}
			else {
				answer = DataProcessor.getAnswer(matchedQuestions.get(0));
			}
		}
		else {
			answer = "Do you mean one of these:\n";
			for(int i = 0; i < matchedQuestions.size(); i++) {
				answer += "" + (i + 1) + ". " + matchedQuestions.get(i) + "\n";
			}
			answer += "If yes, rewrite yours as it appears above; otherwise, either "
					+ "edit your question or report a new question using the button below.";
		} 
		return answer;
	}
	
}
