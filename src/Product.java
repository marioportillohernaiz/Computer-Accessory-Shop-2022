
public abstract class Product {
	private final int barcode;
	private String brand;
	private PType type;
	private String colour;
	private Connectivity conn;
	private int quantity;
	private double originalCost;
	private double retailPrice;
	
	
	Product(int barcode, String brand, PType type, String colour, Connectivity conn, int quantity, double originalCost, double retailPrice) {
		this.barcode = barcode;
		this.brand = brand;
		this.type = type;
		this.colour = colour;
		this.conn = conn;
		this.quantity = quantity;
		this.originalCost = originalCost;
		this.retailPrice = retailPrice;		
	}
	
	public abstract String toString();
	
	// Creating the Class getter methods
	public int getBarcode() {
		return barcode;
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
	public double getOriginalCost() {
		return originalCost;
	}
	public int getQuantity() {
		return quantity;
	}
	// Creating a Class setter to change the quantity of the product when checking out the product
	public void decreaseQuantity(int numb) {
		this.quantity = this.quantity - numb;
	}
	public double getRetailPrice() {
		return retailPrice;
	}
}
