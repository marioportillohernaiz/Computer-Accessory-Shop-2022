 
public class Admin extends User {

	Admin(int userID, String username, String name, Address addr) {
		super(userID, username, name, addr);
	}
	
	public String toString() {
		return "Admin: " + getUserID() +", "+ getUsername() +", "+ getName() +", "+ getAddress().toString();
	}
	
}
