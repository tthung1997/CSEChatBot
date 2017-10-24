import java.util.*;

import org.apache.log4j.Logger;

import java.sql.*;

/**
 * 
 */

/**
 * @author trieu
 *
 */


public class DataProcessor {
	
	private static final String url = "jdbc:mysql://cse.unl.edu:3306/ziyunw";
	private static final String username = "ziyunw";
	private static final String password = "I_Uv4Z";
	private static org.apache.log4j.Logger log = Logger.getLogger(DataProcessor.class);
	
	static public Connection getConnection() {
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		} catch (InstantiationException e) {
			System.out.println("InstantiationException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			System.out.println("IllegalAccessException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		} catch (ClassNotFoundException e) {
			System.out.println("ClassNotFoundException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
		Connection conn = null;

		try {
			conn = DriverManager.getConnection(
					DataProcessor.url, DataProcessor.username, DataProcessor.password);
		} catch (SQLException e) {
			log.error("SQLException: ", e);
			throw new RuntimeException(e);
		}
		
		return conn;
	}
	
	/**
	 * @return
	 */
	public static ArrayList<String> getQuestions() {
		Connection conn = DataProcessor.getConnection();
		ArrayList<String> questions = new ArrayList<String>();
		PreparedStatement ps;
    	ResultSet rs;
    	try {
    		String query = "SELECT Question FROM QA";
    		ps = conn.prepareStatement(query);
    		rs = ps.executeQuery();
    		while (rs.next()) {
    			questions.add(rs.getString("Question"));
    		}
    		ps.close();
    		rs.close();
    	} catch (SQLException e) {
			log.error("SQLException: ", e);
			throw new RuntimeException(e);
		}
		try {
			if (conn != null)
				conn.close();
		} catch (SQLException e) {
			log.error("SQLException: ", e);
		}
		return questions;
	}
	
	/**
	 * @param questionID
	 * @return
	 */
	public static String getAnswer(String question) {
		Connection conn = DataProcessor.getConnection();
		String answer = "";
		PreparedStatement ps;
    	ResultSet rs;
    	try {
    		String query = "SELECT Answer FROM QA WHERE Question = ?";
    		ps = conn.prepareStatement(query);
    		ps.setString(1, question);
    		rs = ps.executeQuery();
    		if (rs.next()) {
    			answer = rs.getString("Answer");
    		}
    		ps.close();
    		rs.close();
    	} catch (SQLException e) {
			log.error("SQLException: ", e);
			throw new RuntimeException(e);
		}
		try {
			if (conn != null)
				conn.close();
		} catch (SQLException e) {
			log.error("SQLException: ", e);
		}
		return answer;
	}
	
	/**
	 * @param question
	 * @param answer
	 * @return
	 */
	public static void insertQA(String question, String answer) {
		Connection conn = DataProcessor.getConnection();
		PreparedStatement ps;
    	try {
    		String insertQA = "INSERT INTO QA (Question, Answer) VALUES (?, ?)";
    		ps = conn.prepareStatement(insertQA);
    		ps.setString(1, question);
    		ps.setString(2, answer);
    		ps.executeUpdate();
    		ps.close();
    	} catch (SQLException e) {
			log.error("SQLException: ", e);
			throw new RuntimeException(e);
		}
		try {
			if (conn != null)
				conn.close();
		} catch (SQLException e) {
			log.error("SQLException: ", e);
		}
	}
	
	/**
	 * @param userID
	 * @param report
	 * @return
	 */
	public static void insertReport(String username, String report) {
		Connection conn = DataProcessor.getConnection();
		PreparedStatement ps;
    	try {
    		String insertQA = "INSERT INTO Reports VALUES (?, ?)";
    		ps = conn.prepareStatement(insertQA);
    		ps.setInt(1, getUserID(username));
    		ps.setString(2, report);
    		ps.executeUpdate();
    		ps.close();
    	} catch (SQLException e) {
			log.error("SQLException: ", e);
			throw new RuntimeException(e);
		}
		try {
			if (conn != null)
				conn.close();
		} catch (SQLException e) {
			log.error("SQLException: ", e);
		}
	}
	
	public static int getUserID(String username) {
		Connection conn = DataProcessor.getConnection();
		int userID = -1;
		PreparedStatement ps;
    	ResultSet rs;
    	try {
    		String query = "SELECT UserID FROM Accounts WHERE Username = ?";
    		ps = conn.prepareStatement(query);
    		ps.setString(1, username);
    		rs = ps.executeQuery();
    		if (rs.next()) {
    			userID = rs.getInt("UserID");
    		}
    		ps.close();
    		rs.close();
    	} catch (SQLException e) {
			log.error("SQLException: ", e);
			throw new RuntimeException(e);
		}
		try {
			if (conn != null)
				conn.close();
		} catch (SQLException e) {
			log.error("SQLException: ", e);
		}
		return userID;
	}
	
	/**
	 * @param username
	 * @return
	 */
	public static String getPassword(String username) {
		Connection conn = DataProcessor.getConnection();
		String password = "";
		PreparedStatement ps;
    	ResultSet rs;
    	try {
    		String query = "SELECT Password FROM Accounts WHERE Username = ?";
    		ps = conn.prepareStatement(query);
    		ps.setString(1, username);
    		rs = ps.executeQuery();
    		if (rs.next()) {
    			password = rs.getString("Password");
    		}
    		else {
    			password = "Username not found";
    		}
    		ps.close();
    		rs.close();
    	} catch (SQLException e) {
			log.error("SQLException: ", e);
			throw new RuntimeException(e);
		}
		try {
			if (conn != null)
				conn.close();
		} catch (SQLException e) {
			log.error("SQLException: ", e);
		}
		return password;
	}
	
	/**
	 * @param username
	 * @return
	 */
	public static String getRole(String username) {
		Connection conn = DataProcessor.getConnection();
		String role = "";
		PreparedStatement ps;
    	ResultSet rs;
    	try {
    		String query = "SELECT Role FROM Accounts WHERE Username = ?";
    		ps = conn.prepareStatement(query);
    		ps.setString(1, username);
    		rs = ps.executeQuery();
    		if (rs.next()) {
    			role = rs.getString("Role");
    		}
    		ps.close();
    		rs.close();
    	} catch (SQLException e) {
			log.error("SQLException: ", e);
			throw new RuntimeException(e);
		}
		try {
			if (conn != null)
				conn.close();
		} catch (SQLException e) {
			log.error("SQLException: ", e);
		}
		return role;
	}
	
}

