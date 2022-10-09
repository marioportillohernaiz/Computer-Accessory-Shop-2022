
public class Keyboard extends Product {
	private KeyboardLang lang;

	Keyboard(int barcode, String brand, PType type, String colour, Connectivity conn, int quantity, double originalCost, double retailPrice, KeyboardLang lang) {
		super(barcode, brand, type, colour, conn, quantity, originalCost, retailPrice);
		this.lang = lang;
	}
	
	public KeyboardLang getLang() {
		return lang;
	}
	
	// Creating a toString method to easily input this object's data into a text file
	public String toString() {
		return getBarcode()+", keyboard, "+getPType()+", "+getBrand()+", "+getColour()+", "+getConn()+", "+getQuantity()+", "+getOriginalCost()+", "+getRetailPrice()+", "+getLang();
	}
}
