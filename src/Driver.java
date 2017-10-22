import java.io.IOException;

/**
 * 
 */

/**
 * @author trieu
 *
 */
public class Driver {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		
		LoginScreen loginScr = new LoginScreen();
		ChatScreen chatScr = new ChatScreen();
		UpdateScreen updateScr = new UpdateScreen();
		ReportScreen reportScr = new ReportScreen();
		
		loginScr.setChatScr(chatScr);
		loginScr.setReportScr(reportScr);
		chatScr.setLoginScr(loginScr);
		chatScr.setUpdateScr(updateScr);
		chatScr.setReportScr(reportScr);
		updateScr.setChatScr(chatScr);
		updateScr.setReportScr(reportScr);
		reportScr.setLoginScr(loginScr);
		
		loginScr.setVisible(true);
		//chatScr.setVisible(true);
		//updateScr.setVisible(true);
		//reportScr.setVisible(true);
		
	}

}
