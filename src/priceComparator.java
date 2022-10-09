import java.util.Comparator;

public class priceComparator implements Comparator<Product> {
	// Function compares products to get the smallest retail price first and the largest last
	@Override
	public int compare(Product p1, Product p2) {
		if (p1.getRetailPrice() < p2.getRetailPrice()) {
			return -1;
		} else if (p1.getRetailPrice() > p2.getRetailPrice()) {
			return 1;
		} else {
			return 0;
		}
	}
}
