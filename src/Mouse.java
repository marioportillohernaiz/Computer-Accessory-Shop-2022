
public class Mouse extends Product {
	private int buttons;	
	
	Mouse(int barcode, String brand, PType type, String colour, Connectivity conn, int quantity, double originalCost, double retailPrice, int buttons) {
		super(barcode, brand, type, colour, conn, quantity, originalCost, retailPrice);
		this.buttons = buttons;
	}
	
	// Creating an extra getter method only a Mouse product can use
	public int getButtons() {
		return buttons;
	}
	
	// Creating a toString method to easily input this object's data into a text file
	public String toString() {
		return getBarcode()+", mouse, "+getPType()+", "+getBrand()+", "+getColour()+", "+getConn()+", "+getQuantity()+", "+getOriginalCost()+", "+getRetailPrice()+", "+getButtons();
	}
}
