/**
 * 
 */

/**
 * @author trieu
 *
 */
public class LoginScreen {

	private String username;
	private String password;
	private String role;
	
	/**
	 * 
	 */
	public LoginScreen() {
		
	}
	
	/**
	 * @return
	 */
	private boolean verify() {
		String realPassword = DataProcessor.getPassword(this.username);
		if (realPassword.compareTo(this.password) != 0)
			return false;
		this.role = DataProcessor.getRole(this.username);
		return true;
	}
	
}
