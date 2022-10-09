
public class Basket {
	private String user;
	private final int barcode;
	private String mouseKey;
	private String brand;
	private PType type;
	private String colour;
	private Connectivity conn;
	private int quantity;
	private double retailPrice;
	private String extraInfo;
	
	Basket(String user, int barcode, String mouseKey, String brand, PType type, String colour, Connectivity conn, int quantity, double retailPrice, String extraInfo) {
		this.user = user;
		this.barcode = barcode;
		this.mouseKey = mouseKey;
		this.brand = brand;
		this.type = type;
		this.colour = colour;
		this.conn = conn;
		this.quantity = quantity;
		this.retailPrice = retailPrice;
		this.extraInfo = extraInfo;
	}
	
	// Creating the Class getter method
	public String getUser() {
		return user;
	}
	public int getBarcode() {
		return barcode;
	}
	public String getMouseKey() {
		return mouseKey;
	}
	public String getBrand() {
		return brand;
	}
	public PType getPType() {
		return type;
	}
	public String getColour() {
		return colour;
	}
	public Connectivity getConn() {
		return conn;
	}
	public int getQuantity() {
		return quantity;
	}
	public double getRetailPrice() {
		return retailPrice;
	}
	public String getExtraInfo() {
		return extraInfo;
	}
}
