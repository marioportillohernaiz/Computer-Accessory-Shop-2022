
public abstract class User {
	private final int userID;
	private String username;
	private String name;	
	private Address addr;
	
	User(int userID, String username, String name, Address addr) {
		this.userID = userID;
		this.username = username;
		this.name = name;
		this.addr = addr;
	}
	
	public abstract String toString();
	
	// Creating the Class getter methods
	public Address getAddress() {
		return addr;
	}
	public String getUsername() {
		return username;
	}
	public String getName() {
		return name;
	}
	public int getUserID() {
		return userID;
	}
	
}
