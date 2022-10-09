import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;
import java.awt.Font;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.JTabbedPane;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;
import javax.swing.JOptionPane;

import java.text.DecimalFormat;

public class CustomerPage extends JFrame {
	private JPanel contentPane;
	private static JTable productTable;
	private static JTable tblBasket;
	private JTable tblSearch;
	public static float totalCost;
	public static int totalItems;
	
	public static void outputTable(String table) throws FileNotFoundException, IOException {
		FileFunctions FileFn = new FileFunctions();
		List<Product> productList = FileFn.readProduct();
		
		// In order to reuse this function, I pass a string to decide what table I want to output
		if (table.equals("shopping")) {
			try {
				String[] firstRow = {"Barcode", "Product", "Type", "Brand", "Color", "Connectivity", "Quantity", "Price", "Extra Info"};
				DefaultTableModel model = (DefaultTableModel)productTable.getModel();
				model.setRowCount(0);
				model.setColumnIdentifiers(firstRow);	// Sets the table header

				// Loops through each product and adds it to the table
				for (Product item : productList) {
					if (item instanceof Mouse) {
						Mouse m = (Mouse) item;
						Object[] dataLine = new Object[] {
								m.getBarcode(), "mouse", m.getPType(), m.getBrand(), m.getColour(),
								m.getConn(), m.getQuantity(), m.getRetailPrice(), m.getButtons()
						};
						model.addRow(dataLine);
					} else {
						Keyboard k = (Keyboard) item;
						Object[] dataLine = new Object[] {
								k.getBarcode(), "keyboard", k.getPType(), k.getBrand(), k.getColour(),
								k.getConn(), k.getQuantity(), k.getRetailPrice(), k.getLang()
						};
						model.addRow(dataLine);
					}
				}
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		} else {
			String userBasket = MainFrame.userLogIn;
			
			String[] firstRow = {"Barcode", "Product", "Type", "Brand", "Color", "Connectivity", "Quantity Available", "Price", "Extra Info"};
			DefaultTableModel model = (DefaultTableModel)tblBasket.getModel();
			model.setRowCount(0);
			model.setColumnIdentifiers(firstRow);	// Sets the table header
			
			FileFn = new FileFunctions();
			List<Basket> basketList = FileFn.readBasket();

			totalCost = 0;
			totalItems = 0;
			
			// In order to reuse this function, I also pass a string to decide what want to do to the table
			if (table.equals("basket")) {
				for (Basket b : basketList) {	// Here we loop through the basket list to output to the table
					String user = b.getUser().toString();
					String item = b.getMouseKey().toString();
					
					if (user.equals(userBasket)) {
						if (item.equals("mouse")) {
							Object[] dataLine = new Object[] {
									b.getBarcode(), "mouse", b.getPType(), b.getBrand(), b.getColour(),
									b.getConn(), b.getQuantity(), b.getRetailPrice(), b.getExtraInfo()
							};
							model.addRow(dataLine);
						} else {
							Object[] dataLine = new Object[] {
									b.getBarcode(), "keyboard", b.getPType(), b.getBrand(), b.getColour(),
									b.getConn(), b.getQuantity(), b.getRetailPrice(), b.getExtraInfo()
							};
							model.addRow(dataLine);
						}
						// We then add the total cost and number of items for all of the item in the basket
						totalCost += Float.parseFloat(Double.toString(b.getRetailPrice()));
						totalItems += 1;
					}
				}

			} else if (table.equals("clear")) { // Here we clear the basket table
				FileFn = new FileFunctions();
				FileFn.writeShoppingBasket("", false);
				String inputString = "";
				for (Basket b : basketList) {
					String user = b.getUser().toString();
					String item = b.getMouseKey().toString();
					if (!user.equals(userBasket)) { 	// We only remove the items from the basket where the user is the one logged in
						if (item.equals("mouse")) {
							inputString = b.getUser()+", "+b.getBarcode()+", mouse, "+b.getPType()+", "+b.getBrand()+", "+
									b.getColour()+", "+b.getConn()+", "+b.getQuantity()+", "+b.getRetailPrice()+", "+
									b.getExtraInfo()+"\n";
						} else {
							inputString = b.getUser()+", "+b.getBarcode()+", keyboard, "+b.getPType()+", "+b.getBrand()+", "+
									b.getColour()+", "+b.getConn()+", "+b.getQuantity()+", "+b.getRetailPrice()+", "+
									b.getExtraInfo()+"\n";
						}
						FileFn.writeShoppingBasket(inputString, true);
					}
				}
				FileFn.readBasket();
			}
		}
	}
	
	public static void checkOut(String typeOfPay) throws FileNotFoundException, IOException {
		FileFunctions FileFn = new FileFunctions();
		List<Product> productList = FileFn.readProduct();
		FileFn = new FileFunctions();
		List<Basket> basketList = FileFn.readBasket();
		int countOfSingleItem = 0;
		
		// Re-writing the basket with the new quantity
		FileFn.writeProduct("", false);
		for (Product p : productList) {
			countOfSingleItem = 0;
			for (Basket b : basketList) {
				if (p.getBarcode() == b.getBarcode()) {		// Adding through the basket to see how many duplicate items there are
					countOfSingleItem += 1;
				}
			}
			for (Basket b : basketList) {
				if (p.getBarcode() == b.getBarcode()) {		// Now looping through the basket list to change the quantity
					p.decreaseQuantity(countOfSingleItem);	// Decreasing the quantity using the setter method
					if (p instanceof Mouse) {
						Mouse m1 = new Mouse(p.getBarcode(), p.getBrand(), p.getPType(), p.getColour(), p.getConn(),
								p.getQuantity(), p.getOriginalCost(), p.getRetailPrice(), Integer.parseInt(b.getExtraInfo()));
						FileFn.writeProduct(m1.toString()+"\n", true);
					} else {
						Keyboard k1 = new Keyboard(p.getBarcode(), p.getBrand(), p.getPType(), p.getColour(), p.getConn(),
								p.getQuantity(), p.getOriginalCost(), p.getRetailPrice(), KeyboardLang.valueOf(b.getExtraInfo()));
						FileFn.writeProduct(k1.toString()+"\n", true);
					}
					break;
				} 
			}
		}

		// Adding the rest of the items to the basket
		for (Product p : productList) {
			if (FileFn.productInList(p.getBarcode()) == false) {
				if (p instanceof Mouse) {
					Mouse m = (Mouse) p;
					FileFn.writeProduct(m.toString()+"\n", true);
				} else {
					Keyboard k = (Keyboard) p;
					FileFn.writeProduct(k.toString()+"\n", true);
				}
			}
		}
		
		String user = MainFrame.userLogIn;
		FileFn = new FileFunctions();
		List<User> userList = FileFn.readUser();
		DecimalFormat rounded = new DecimalFormat("###.##");
		
		if (typeOfPay == "PayPal") {	// As mentioned in the previous function, I pass this string to reuse this function with the loops above
			String address = "";
			for (User c1 : userList) {
				if (user.equals(c1.getUsername())) {
					address = c1.getAddress().toString();	// Getting the toString() getter from the Address function
				}
			}
			// Returning required output
			String outputString = "£" + rounded.format(totalCost) + " paid using PayPal, and the address is " + address;
			JOptionPane.showMessageDialog(null, outputString, "Success:", JOptionPane.INFORMATION_MESSAGE);
		} else {
			String address = "";
			for (User c1 : userList) {
				if (user.equals(c1.getUsername())) {
					address = c1.getAddress().toString();
				}
			}
			String outputString = "£" + rounded.format(totalCost) + " paid using Credit Card, and the address is " + address;
			JOptionPane.showMessageDialog(null, outputString, "Success:", JOptionPane.INFORMATION_MESSAGE);
		}
	}

	

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CustomerPage frame = new CustomerPage();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public CustomerPage() throws FileNotFoundException {
		
		FileFunctions FileFn = new FileFunctions();
		List<User> userList = FileFn.readUser();
		FileFn = new FileFunctions();
		List<Product> productList = FileFn.readProduct();
		DecimalFormat rounded = new DecimalFormat("###.##"); // Format needed for rounding the total cost of the basket
		
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 950, 520);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNewLabel = new JLabel("Computer Accessories Shop : Customer Access");
		lblNewLabel.setBounds(5, 3, 432, 24);
		lblNewLabel.setFont(new Font("Trebuchet MS", Font.PLAIN, 20));
		contentPane.add(lblNewLabel);
		
		JPanel panel = new JPanel();
		panel.setBounds(5, 29, 926, 449);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(0, 0, 926, 449);
		panel.add(tabbedPane);
		
		JPanel panel_1 = new JPanel();
		tabbedPane.addTab("View Products", null, panel_1, null);
		panel_1.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 11, 901, 365);
		panel_1.add(scrollPane);
		
		productTable = new JTable();
		scrollPane.setViewportView(productTable);
		
		JPanel panel_3 = new JPanel();
		tabbedPane.addTab("Search Items", null, panel_3, null);
		panel_3.setLayout(null);
		
		JPanel pnFilterItems_1 = new JPanel();
		pnFilterItems_1.setLayout(null);
		pnFilterItems_1.setBounds(10, 0, 901, 44);
		panel_3.add(pnFilterItems_1);
		
		JLabel lblFilterBy_1 = new JLabel("Filter by:");
		lblFilterBy_1.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblFilterBy_1.setBounds(20, 7, 58, 31);
		pnFilterItems_1.add(lblFilterBy_1);
		
		JLabel lblFilterBrand_1 = new JLabel("Brand:");
		lblFilterBrand_1.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblFilterBrand_1.setBounds(88, 7, 58, 31);
		pnFilterItems_1.add(lblFilterBrand_1);
		
		JLabel lblFilterButt_1 = new JLabel("Mouse button quantity:");
		lblFilterButt_1.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblFilterButt_1.setBounds(289, 7, 145, 31);
		pnFilterItems_1.add(lblFilterButt_1);
		
		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(10, 55, 901, 355);
		panel_3.add(scrollPane_2);
		
		String[] firstRow = {"Barcode", "Product", "Type", "Brand", "Color", "Connectivity", "Quantity Available", "Price", "Extra Info"};
		tblSearch = new JTable();
		scrollPane_2.setViewportView(tblSearch);
		DefaultTableModel model = (DefaultTableModel)tblSearch.getModel();
		model.setColumnIdentifiers(firstRow);
		
		String[] brandFirstRow = {"Select Brand"};
		JComboBox cmboxFilterBrand = new JComboBox(brandFirstRow);
		String[] buttonFirstRow = {"Select No Of Buttons"};
		JComboBox cmboxFilterButt = new JComboBox(buttonFirstRow);
		cmboxFilterBrand.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String brandSelected = cmboxFilterBrand.getSelectedItem().toString();

				model.setRowCount(0);	// Clearing the table row for the next filtering to be done
				cmboxFilterButt.setSelectedIndex(0);	// Clearing the button filter to not cause errors later on
				for (Product item : productList) {
					if (item.getBrand().equals(brandSelected)) {	// Filtering the selected brands
						if (item instanceof Mouse) {
							Mouse m = (Mouse) item;
							int buttons = m.getButtons();
							String brand = m.getBrand();
							Object[] dataLine = new Object[] {
									m.getBarcode(), "mouse", m.getPType(), brand, m.getColour(),
									m.getConn(), m.getQuantity(), m.getRetailPrice(), buttons
							};
							model.addRow(dataLine);
						} else {
							Keyboard k = (Keyboard) item;
							Object[] dataLine = new Object[] {
									k.getBarcode(), "keyboard", k.getPType(), k.getBrand(), k.getColour(),
									k.getConn(), k.getQuantity(), k.getRetailPrice(), k.getLang()
							};
							model.addRow(dataLine);
						}
					}		
				}
			}
		});
		cmboxFilterBrand.setBounds(130, 12, 135, 22);
		pnFilterItems_1.add(cmboxFilterBrand);
		
		ArrayList<String> brandList = new ArrayList<String>(); // Creating an array list where I add the non repeated brands to it
		for (Product item : productList) {
			if (!brandList.contains(item.getBrand())) {
				brandList.add(item.getBrand());
			}
		} 
		for (String brand : brandList) {
			cmboxFilterBrand.addItem(brand);	// Adding the array to the combobox
		}
		
		cmboxFilterButt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String brandSelected = cmboxFilterBrand.getSelectedItem().toString();
				String buttonSelected = cmboxFilterButt.getSelectedItem().toString();

				model.setRowCount(0);
				for (Product item : productList) {
					if (item.getBrand().equals(brandSelected)) {	// Filtering through the Mice buttons
						if (item instanceof Mouse) {
							Mouse m = (Mouse) item;
							int buttons = m.getButtons();
							String brand = m.getBrand();
							Object[] dataLine = null;
							if (buttonSelected.equals(Integer.toString(buttons)) && brandSelected.equals(brand)) {
								m = (Mouse) item;
								dataLine = new Object[] {
										m.getBarcode(), "mouse", m.getPType(), brand, m.getColour(),
										m.getConn(), m.getQuantity(), m.getRetailPrice(), buttons
								};
								model.addRow(dataLine);
							}					
						}
					}		
				}
			}
		});
		ArrayList<String> buttonList = new ArrayList<String>(); 
		for (Product item : productList) {	
			if (item instanceof Mouse) {
				Mouse mouse = (Mouse) item;
				if (!buttonList.contains(mouse.getButtons())) {	// Creating an array list where I add the non repeated buttons to it
					buttonList.add(Integer.toString(mouse.getButtons()));
				}
			}
		} 
		for (String button : buttonList) {
			cmboxFilterButt.addItem(button);
		}
		cmboxFilterButt.setBounds(430, 12, 135, 22);
		pnFilterItems_1.add(cmboxFilterButt);
		
		JPanel panel_2 = new JPanel();
		tabbedPane.addTab("View Shopping Basket", null, panel_2, null);
		panel_2.setLayout(null);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(10, 11, 901, 227);
		panel_2.add(scrollPane_1);
		
		tblBasket = new JTable();
		scrollPane_1.setViewportView(tblBasket);
		
		JLabel lblNewLabel_1 = new JLabel("Total Cost:");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNewLabel_1.setBounds(20, 254, 85, 14);
		panel_2.add(lblNewLabel_1);
		
		JLabel lblTotalCost = new JLabel("\u00A3");
		lblTotalCost.setFont(new Font("Tahoma", Font.PLAIN, 24));
		lblTotalCost.setBounds(30, 271, 101, 35);
		panel_2.add(lblTotalCost);
		
		JLabel lblNewLabel_1_1 = new JLabel("Number Of Items:");
		lblNewLabel_1_1.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNewLabel_1_1.setBounds(158, 254, 115, 14);
		panel_2.add(lblNewLabel_1_1);
		
		JLabel lblTotalItems = new JLabel("0");
		lblTotalItems.setFont(new Font("Tahoma", Font.PLAIN, 24));
		lblTotalItems.setBounds(168, 271, 85, 35);
		panel_2.add(lblTotalItems);
		
		JButton btnNewButton_2 = new JButton("Add item to Basket");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int tableRow = productTable.getSelectedRow();
				int itemCount = 0;
				
				if (tableRow > -1) {	// Making sure the user selected an item to add to the basket
					if(Integer.parseInt(productTable.getModel().getValueAt(tableRow, 6).toString()) <= 0) {	// Making sure the product has more than 0 items in stock
						JOptionPane.showMessageDialog(null, "Item out of stock", "Error", JOptionPane.ERROR_MESSAGE);
					} else {
						int barcode = Integer.parseInt(productTable.getModel().getValueAt(tableRow, 0).toString());
						int quantity = Integer.parseInt(productTable.getModel().getValueAt(tableRow, 6).toString());
						File inputFile = new File("src/Basket");
						try {
							Scanner fileScanner = new Scanner(inputFile);
							String[] product;
							while (fileScanner.hasNextLine()) {	
								String line = fileScanner.nextLine();
								product = line.split(",");
								
								int readBarcode = Integer.parseInt(product[1].trim());
								if (readBarcode == barcode) {
									itemCount += 1;	// Counting how many items of the same product there are in the basket
								}
							}
							fileScanner.close();
						} catch(Exception e1) {e1.printStackTrace();
						}
						/* If the quantity of the item is higher than the number of items of the same barcode in that basket
						 * then we can add it to the basket, otherwise the stock wont be sufficient so we dont add it*/
						if (itemCount < quantity) {	
							String tableColm = "";
							tableColm += MainFrame.userLogIn.toString().trim();
							for (int i=0; i < 9; i++) {
								tableColm += ", " + productTable.getModel().getValueAt(tableRow, i).toString();
							}
							FileFunctions fileFunctions = new FileFunctions();

							try {
								fileFunctions.writeShoppingBasket(tableColm + "\n", true);
								outputTable("basket");
							} catch (IOException e1) {
								e1.printStackTrace();
							}
							
							lblTotalCost.setText("£" + rounded.format(totalCost));
							lblTotalItems.setText(Integer.toString(totalItems));
						} else {
							JOptionPane.showMessageDialog(null, "Item Cannot Be Added To Basket", "Error", JOptionPane.ERROR_MESSAGE);
						}
					}
				} else {
					JOptionPane.showMessageDialog(null, "Please select an item.", "Warning:", JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		btnNewButton_2.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnNewButton_2.setBounds(749, 387, 162, 23);
		panel_1.add(btnNewButton_2);
		
		JButton btnClear = new JButton("Clear Basket");
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int tableRow = productTable.getSelectedRow();
				
				if (tableRow > -1) { // Cannot empty an empty basket
					lblTotalCost.setText("£0");
					lblTotalItems.setText("0");
					try {
						outputTable("clear");
					} catch (IOException e1) {e1.printStackTrace();}
				} else {
					JOptionPane.showMessageDialog(null, "Basket already empty.", "Information:", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
		btnClear.setBounds(10, 387, 167, 23);
		panel_2.add(btnClear);
		
		JTabbedPane tabbedPane_1 = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane_1.setBounds(527, 249, 384, 161);
		panel_2.add(tabbedPane_1);
		
		JPanel panel_4 = new JPanel();
		tabbedPane_1.addTab("Pay-Pal", null, panel_4, null);
		panel_4.setLayout(null);
		
		JLabel lblNewLabel_3 = new JLabel("Enter Pay-Pal email address:");
		lblNewLabel_3.setBounds(25, 45, 186, 14);
		panel_4.add(lblNewLabel_3);
		
		JTextField tfPayPal = new JTextField();
		tfPayPal.setBounds(25, 70, 186, 20);
		panel_4.add(tfPayPal);
		tfPayPal.setColumns(10);
		
		JButton btnPayPal = new JButton("Check Out");
		btnPayPal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {				
				String input = tfPayPal.getText();
				
				boolean paypalFormat = input.matches("[a-zA-Z0-9@.]+"); // Email format

				// Error checking so that the user doesnt input an incorrect format 
				if (tblBasket.getRowCount() == 0) {
					JOptionPane.showMessageDialog(null, "Your basket is empty", "Warning:", JOptionPane.WARNING_MESSAGE);
				} else if (input.equals("")) {
					JOptionPane.showMessageDialog(null, "Please enter your PayPal email.", "Warning:", JOptionPane.WARNING_MESSAGE);
				} else if (paypalFormat == false || !input.contains("@") || !input.contains(".")) {
					JOptionPane.showMessageDialog(null, "Enter your PayPal email in the correct format.\nCorrect format is 'xxx@xxx.xx'", "Warning:", JOptionPane.WARNING_MESSAGE);
				} else {
					try {
						checkOut("PayPal"); // Calling the checkout function
					} catch (FileNotFoundException e3) {e3.printStackTrace();
					} catch (IOException e3) {e3.printStackTrace();
					}
					lblTotalCost.setText("£0");
					lblTotalItems.setText("0");
					tfPayPal.setText("");
					try {
						outputTable("clear");	// Clearing the basket
						outputTable("shopping");	// Updating the basket
					} catch (IOException e1) {e1.printStackTrace();}
				}
			}
		});
		btnPayPal.setBounds(257, 36, 112, 64);
		panel_4.add(btnPayPal);
		
		JPanel panel_5 = new JPanel();
		tabbedPane_1.addTab("Credit Card", null, panel_5, null);
		panel_5.setLayout(null);
		
		JLabel lblNewLabel_4 = new JLabel("Enter your 6-digit card number:");
		lblNewLabel_4.setBounds(26, 11, 193, 14);
		panel_5.add(lblNewLabel_4);
		
		JTextField tfSixDigit = new JTextField();
		tfSixDigit.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char ch = e.getKeyChar();
				if ((ch < '0' || ch > '9') && (ch != KeyEvent.VK_BACK_SPACE)) {
					e.consume();
				}
			} // Only allows numbers to be inputed into the text field
		});
		tfSixDigit.setBounds(26, 36, 162, 20);
		panel_5.add(tfSixDigit);
		tfSixDigit.setColumns(10);
		
		JLabel lblNewLabel_4_1 = new JLabel("Enter your 3-digit security code:");
		lblNewLabel_4_1.setBounds(26, 67, 193, 14);
		panel_5.add(lblNewLabel_4_1);
		
		JTextField tfThreeDigit = new JPasswordField();
		tfThreeDigit.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char ch = e.getKeyChar();
				if ((ch < '0' || ch > '9') && (ch != KeyEvent.VK_BACK_SPACE)) {
					e.consume();
				}
			} // Only allows numbers to be inputed into the text field
		});
		tfThreeDigit.setColumns(10);
		tfThreeDigit.setBounds(26, 92, 162, 20);
		panel_5.add(tfThreeDigit);
		
		JButton btnCreditCard = new JButton("Check Out");
		btnCreditCard.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String sixDigitInput = tfSixDigit.getText();
				String threeDigitInput = tfThreeDigit.getText();
				
				// Error checking so that the user doesnt input an incorrect format 
				if (sixDigitInput.equals("") || threeDigitInput.equals("")) {
					JOptionPane.showMessageDialog(null, "Enter your card details", "Warning:", JOptionPane.WARNING_MESSAGE);
				} else if (sixDigitInput.length() != 6 	|| threeDigitInput.length() != 3) {
					JOptionPane.showMessageDialog(null, "Enter your PayPal email in the correct format.\nCorrect format is 6-digit card number & 3-digit security code", "Warning:", JOptionPane.WARNING_MESSAGE);
				} else {
					try {
						checkOut("Card");
					} catch (FileNotFoundException e3) {e3.printStackTrace();
					} catch (IOException e3) {e3.printStackTrace();
					}
					lblTotalCost.setText("£0");
					lblTotalItems.setText("0");
					tfSixDigit.setText("");
					tfThreeDigit.setText("");
					try {
						outputTable("clear");	// Clearing the basket
						outputTable("shopping");	// Updating the basket
					} catch (IOException e1) {e1.printStackTrace();}
				}
			}
		});
		btnCreditCard.setBounds(258, 36, 111, 64);
		panel_5.add(btnCreditCard);
		
		JLabel lblNewLabel_2 = new JLabel("Pay through Pay-Pal or Credit Card ->");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblNewLabel_2.setBounds(339, 255, 192, 14);
		panel_2.add(lblNewLabel_2);
		
		JButton btnNewButton = new JButton("Return to Log In Page");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnNewButton.setBounds(746, 6, 180, 23);
		contentPane.add(btnNewButton);
		
		String user = MainFrame.userLogIn;
		JLabel lblNewLabel_5 = new JLabel("(" + user + ")");
		lblNewLabel_5.setFont(new Font("Trebuchet MS", Font.PLAIN, 20));
		lblNewLabel_5.setBounds(434, 3, 186, 24);
		contentPane.add(lblNewLabel_5);
		
				
		try {
			outputTable("shopping");	
			outputTable("basket");
			lblTotalCost.setText("£" + rounded.format(totalCost)); 
			lblTotalItems.setText(Integer.toString(totalItems));
		} catch (IOException e1) {
			e1.printStackTrace();
		} // At the start of the window opening, we set the tables and the total cost for the user logged in
	}
}
