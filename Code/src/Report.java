/**
 * 
 */

/**
 * @author TRIEU HUNG
 *
 */
public class Report {

	private String report;
	private String username;
	private int id;
	
	public Report(int reportID, int userID, String report) {
		this.report = report;
		this.username = DataProcessor.getUsername(userID);
		this.id = reportID;
	}

	/**
	 * @return the report
	 */
	public String getReport() {
		return report;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	
}
