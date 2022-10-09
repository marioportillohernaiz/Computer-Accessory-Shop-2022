
public class Address {
	private int houseNumb;
	private String postcode;
	private String city;
	
	Address(int houseNumb, String postode, String city) {
		this.houseNumb = houseNumb;
		this.postcode = postode;
		this.city = city;
	}
	
	// Creating the Class getter method
	public int getHouseNumb() {
		return houseNumb;
	}
	public String getPostcode() {
		return postcode;
	}
	public String getCity() {
		return city;
	}
	
	// Creating a toString method to easily output the user's address
	public String toString() {
		return getHouseNumb() +", "+ getPostcode() +", "+ getCity();
	}
}
