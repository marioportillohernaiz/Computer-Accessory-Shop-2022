import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class FileFunctions {

	public ArrayList<Product> readProduct()  throws FileNotFoundException {
		ArrayList<Product> productList = new ArrayList<Product>();
		File inputFile = new File("src/Stock");
		Scanner fileScanner = new Scanner(inputFile);
		String[] product;
		
		try {
			while (fileScanner.hasNextLine()) {
				String line = fileScanner.nextLine();
				product = line.split(",");
				
				int barcode = Integer.parseInt(product[0].trim());
				String brand = product[3].trim();
				PType type = PType.valueOf(product[2].trim());
				String colour = product[4].trim();
				Connectivity conn = Connectivity.valueOf(product[5].trim());
				int quantity = Integer.parseInt(product[6].trim());
				double originalC = Double.parseDouble(product[7].trim());
				double retailP = Double.parseDouble(product[8].trim());
				if (product[1].trim().equals("mouse")) {
					Mouse m1 = new Mouse(barcode, brand, type, colour, conn, quantity, originalC, retailP, Integer.parseInt(product[9].trim()));
					productList.add(m1);
				} else {
					Keyboard k1 = new Keyboard(barcode, brand, type, colour, conn, quantity, originalC, retailP, KeyboardLang.valueOf(product[9].trim()));
					productList.add(k1);
				}
			}
			fileScanner.close();
			
		} catch(Exception e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		} finally {
			if(fileScanner != null) {				
				fileScanner.close();
			}
		}

		priceComparator comp = new priceComparator(); //change name
		Collections.sort(productList, comp);
		
		return(productList);
	} // Reading Stock.txt. I created this to be able to call it multiple times

	public ArrayList<User> readUser()  throws FileNotFoundException {
		ArrayList<User> userList = new ArrayList<User>();
		File inputFile = new File("src/UserAccounts");
		Scanner fileScanner = new Scanner(inputFile);
		String[] user;
		
		try {
			while (fileScanner.hasNextLine()) {
				String line = fileScanner.nextLine();
				user = line.split(",");
				
				int userID = Integer.parseInt(user[0].trim());
				String userName = user[1].trim();
				String name = user[2].trim();
				Address addr = new Address(Integer.parseInt(user[3].trim()), user[4].trim(), user[5].trim());
				if (user[6].trim().equals("admin")) {
					Admin a1 = new Admin(userID, userName, name, addr);
					userList.add(a1);
				} else {
					Customer c1 = new Customer(userID, userName, name, addr);
					userList.add(c1);
				}
			}
			fileScanner.close();
			
		} catch(Exception e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		} finally {
			if(fileScanner != null) {				
				fileScanner.close();
			}
		}
		return(userList);
	} // Reading UserAccounts.txt. I created this to be able to call it multiple times
	
	public ArrayList<Basket> readBasket()  throws FileNotFoundException {
		ArrayList<Basket> basketList = new ArrayList<Basket>();
		File inputFile = new File("src/Basket");
		Scanner fileScanner = new Scanner(inputFile);
		String[] product;
		
		try {
			while (fileScanner.hasNextLine()) {
				String line = fileScanner.nextLine();
				product = line.split(",");
				
				String user = product[0].trim();
				int barcode = Integer.parseInt(product[1].trim());
				String mouseKey = product[2].trim();
				PType type = PType.valueOf(product[3].trim());
				String brand = product[4].trim();
				String colour = product[5].trim();
				Connectivity conn = Connectivity.valueOf(product[6].trim());
				int quantity = Integer.parseInt(product[7].trim());
				double retailP = Double.parseDouble(product[8].trim());
				String extraInfo = product[9].trim();
				
				Basket b1 = new Basket(user, barcode, mouseKey, brand, type, colour, conn, quantity, retailP, extraInfo);
				basketList.add(b1);
				
			}
			fileScanner.close();
			
		} catch(Exception e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		} finally {
			if(fileScanner != null) {				
				fileScanner.close();
			}
		}
		return(basketList);
	} // Reading Basket.txt. I created this to be able to call it multiple times
	
	
	public void writeProduct(String inputItem, boolean typeOfInput) throws IOException {
		FileWriter fileW;
		try {		
			fileW = new FileWriter("src/Stock", typeOfInput);
			fileW.write(inputItem);
			fileW.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	} // Writing into the Stock.txt

	public void writeShoppingBasket(String inputItem, boolean typeOfInput) throws IOException {
		FileWriter fileW;
		try { 
			fileW = new FileWriter("src/Basket", typeOfInput);
			fileW.write(inputItem);
			fileW.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	} // Writing into the Basket.txt
	

	public boolean productInList(int barcode) throws FileNotFoundException {
		boolean returnTF = false;
		File inputFile = new File("src/Stock");
		Scanner fileScanner = new Scanner(inputFile);
		String[] product;
		
		try {
			while (fileScanner.hasNextLine()) {
				String line = fileScanner.nextLine();
				product = line.split(",");
				
				int readBarcode = Integer.parseInt(product[0].trim());
				if (readBarcode == barcode) {
					returnTF = true;
					break;
				}
			}
			fileScanner.close();
		} catch(Exception e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
		return returnTF;
	} // Searches for an existing barcode in Stock.txt

}
