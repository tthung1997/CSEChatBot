import java.io.IOException;
import java.util.ArrayList;

/**
 * 
 */

/**
 * Driver class contains the main method to initialize all screens
 * @author Group 11 CSCE 361
 * @date Fall 2017
 */
public class Driver {

	public static void main(String[] args) throws IOException {
		
		//Create all screens
		LoginScreen loginScr = new LoginScreen();
		ChatScreen chatScr = new ChatScreen();
		UpdateScreen updateScr = new UpdateScreen();
		ReportScreen reportScr = new ReportScreen();
		
		//Set connection to their related screens
		loginScr.setChatScr(chatScr);
		loginScr.setReportScr(reportScr);
		chatScr.setLoginScr(loginScr);
		chatScr.setUpdateScr(updateScr);
		chatScr.setReportScr(reportScr);
		updateScr.setChatScr(chatScr);
		updateScr.setReportScr(reportScr);
		reportScr.setLoginScr(loginScr);
		
		//Start the program by activate the login screen
		loginScr.setVisible(true);
		
	}

}
