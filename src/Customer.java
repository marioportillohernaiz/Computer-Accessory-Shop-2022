
public class Customer extends User {

	Customer(int userID, String username, String name, Address addr) {
		super(userID, username, name, addr);
	}
	
	public String toString() {
		return "Customer: " + getUserID() +", "+ getUsername() +", "+ getName() +", "+ getAddress().toString();
	}
}
