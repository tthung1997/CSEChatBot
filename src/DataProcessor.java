import java.util.*;
import java.sql.*;

/**
 * 
 */

/**
 * @author trieu
 *
 */


public class DataProcessor {
	static final String connectionUrl="jdbc:mysql://cse.unl.edu:3306/ziyunw";
	static final String DB_URL = "com.mysql.jdbc.Driver";
	static String username="ziyunw";
	static String password="I_Uv4Z";
	public static void main(String [] args) throws SQLException {
		Statement stmt = null;
	//declare the JDBC objects.
	
	try {
		Class.forName("com.mysql.jdbc.Driver");
		
		Connection con= DriverManager.getConnection(connectionUrl, username, password);
		
		stmt= con.createStatement();
		String sql;
		sql= "SELECT Question FROM QA";
		ResultSet rs= stmt.executeQuery(sql);
		while ( rs.next()) {
			String question= rs.getString("Question");
			System.out.println("Question"+question);
		}
		rs.close();
		stmt.close();
		con.close();
	}catch(SQLException se) {
		se.printStackTrace();
	}catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}finally {
		
	}
	}
	
	
	/**
	 * @return
	 */
	public static ArrayList<String> getQuestions() {
		ArrayList<String> questions = new ArrayList<String>();
		return questions;
	}
	
	/**
	 * @param questionID
	 * @return
	 */
	public static String getAnswer(int questionID) {
		String answer = "";
		return answer;
	}
	
	/**
	 * @param question
	 * @param answer
	 * @return
	 */
	public static boolean insertQA(String question, String answer) {
		return true;
	}
	
	/**
	 * @param userID
	 * @param report
	 * @return
	 */
	public static boolean insertReport(String username, String report) {
		return true;
	}
	
	/**
	 * @param username
	 * @return
	 */
	public static String getPassword(String username) {
		String password = "";
		return password;
	}
	
	/**
	 * @param username
	 * @return
	 */
	public static String getRole(String username) {
		String role = "";
		return role;
	}
	
}

