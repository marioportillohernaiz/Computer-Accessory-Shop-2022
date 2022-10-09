import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.GridBagLayout;
import java.awt.HeadlessException;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.regex.Pattern;
import java.awt.event.ActionEvent;
import javax.swing.JOptionPane;

public class AdminPage extends JFrame {

	private JFrame fmAdmin;
	private JPanel contentPane;
	private JTextField tfBarcode;
	private JTextField tfBrand;
	private JTextField tfColor;
	private JTextField tfQuantity;
	private JTextField tfOCost;
	private JTextField tfRPrice;
	private JTextField tfExtraInfo;
	private static JTable tblAdmin;
	
	// Function called every time we want to update the JTable 
	public static void outputTable() throws FileNotFoundException, IOException {
		FileFunctions FileFn = new FileFunctions();
		List<Product> productList = FileFn.readProduct();
		
		try {
			String[] firstRow = {"Barcode", "Product", "Type", "Brand", "Color", "Connectivity", "Quantity", "Original Cost", "Retail Price", "Extra Info"};
			DefaultTableModel model = (DefaultTableModel)tblAdmin.getModel();
			model.setRowCount(0);
			model.setColumnIdentifiers(firstRow);
			
			// Looping through product list to display onto the table
			for (Product item : productList) {
				if (item instanceof Mouse) {	// if mouse then getButtons()
					Mouse m = (Mouse) item;
					Object[] dataLine = new Object[] {
							m.getBarcode(), "mouse", m.getPType(), m.getBrand(), m.getColour(),
							m.getConn(), m.getQuantity(), "£" + m.getOriginalCost(), "£" + m.getRetailPrice(), 
							m.getButtons()
					};
					model.addRow(dataLine);
				} else {						// if keyboard then getLang()
					Keyboard k = (Keyboard) item;
					Object[] dataLine = new Object[] {
							k.getBarcode(), "keyboard", k.getPType(), k.getBrand(), k.getColour(),
							k.getConn(), k.getQuantity(), "£" + k.getOriginalCost(),"£" + k.getRetailPrice(), 
							k.getLang()
					};
					model.addRow(dataLine);
				}
			}			
			
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	/* FOLLOWING FUNCTIONS validate the input for adding a new product to the list */
	public static boolean checkBarcode(String barcode) throws FileNotFoundException {
		boolean returnCheck = false;
		FileFunctions FileFn = new FileFunctions();
		if (String.valueOf(barcode).length() == 6) {
			if (FileFn.productInList(Integer.parseInt(barcode)) == false) {
				returnCheck = true;		
			} // if the length is 6 and it doesn't exist in the product file, return true
		}
		return returnCheck;
	}
	public static boolean checkQuantity(String quantity) {
		boolean returnCheck = false;
		if (Integer.parseInt(quantity) > 0 && Integer.parseInt(quantity) < 10000000) {
			returnCheck = true;		
		} // if the quantity is inside a real-life range, return true
		return returnCheck;
	}	
	public static boolean checkCost(String cost) {
		String numberFormat = "\\b\\d{1,5}\\.\\d{1,2}\\b";
		boolean returnCheck = false;
		if (Double.parseDouble(cost) > 0) {			
			if (Pattern.matches(numberFormat, cost) == true) {
				returnCheck = true;	
			} // if the either original or retail cost is bigger than 0 and a double with 2 decimal places, return true
		}
		return returnCheck;
	}
	public static boolean checkExtraInfo(String input, String ptype) {
		boolean returnCheck = false;
		if (ptype.equals("keyboard")) {
			if (input.equals("UK") || input.equals("US")) {
				returnCheck = true;
			}	// if a keyboard, it can only be "UK" or "US" so we check this
		} else {
			if (Integer.parseInt(input) > 0 && Integer.parseInt(input) < 1000) {
				returnCheck = true;
			}	// if a mouse, it can only be a number bigger than 0, so we check this
		}
		return returnCheck;
	}
	public static boolean checkType(String input, String ptype) {
		boolean returnCheck = false;
		if (ptype.equals("mouse") && input.equals("flexible")) {
			returnCheck = false;
		} else {
			returnCheck = true;
		}
		return returnCheck;
	}	// if a mouse, the type cannot be "flexible" so we return false if so
	


	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AdminPage fmAdmin = new AdminPage();
					fmAdmin.setVisible(true);
				} catch (Exception e) {e.printStackTrace();}
			}
		});
	}

	public AdminPage() {
		fmAdmin = new JFrame();
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 950, 520);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JLabel lblNewLabel = new JLabel("Computer Accessories Shop : Admin Access");
		lblNewLabel.setFont(new Font("Trebuchet MS", Font.PLAIN, 20));
		contentPane.add(lblNewLabel, BorderLayout.NORTH);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(10, 11, 621, 427);
		panel.add(panel_1);
		panel_1.setLayout(null);
		
		JScrollPane scrollPane_1 = new JScrollPane(tblAdmin);
		scrollPane_1.setBounds(0, 0, 622, 427);
		panel_1.add(scrollPane_1);
		
		tblAdmin = new JTable();
		scrollPane_1.setViewportView(tblAdmin);
		
		JLabel lblNewLabel_1 = new JLabel("ADD ITEM");
		lblNewLabel_1.setFont(new Font("Trebuchet MS", Font.PLAIN, 14));
		lblNewLabel_1.setBounds(739, 0, 74, 14);
		panel.add(lblNewLabel_1);
		
		JPanel pnAddItems = new JPanel();
		pnAddItems.setBounds(642, 21, 274, 392);
		panel.add(pnAddItems);
		GridBagLayout gbl_pnAddItems = new GridBagLayout();
		gbl_pnAddItems.columnWidths = new int[]{87, 0, 214, 0};
		gbl_pnAddItems.rowHeights = new int[]{17, 20, 20, 20, 20, 20, 20, 20, 20, 16, 14, 33, 0, 0, 0, 0, 0};
		gbl_pnAddItems.columnWeights = new double[]{0.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl_pnAddItems.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		pnAddItems.setLayout(gbl_pnAddItems);
		
		/* The following code creates the necessary input for a new product */
		String[] itemProduct = {"mouse", "keyboard"};
		
		JLabel lblproduct = new JLabel("Product:");
		lblproduct.setFont(new Font("Tahoma", Font.PLAIN, 13));
		GridBagConstraints gbc_lblproduct = new GridBagConstraints();
		gbc_lblproduct.anchor = GridBagConstraints.WEST;
		gbc_lblproduct.insets = new Insets(0, 0, 5, 5);
		gbc_lblproduct.gridx = 0;
		gbc_lblproduct.gridy = 1;
		pnAddItems.add(lblproduct, gbc_lblproduct);
		JComboBox cmbxProduct = new JComboBox(itemProduct);
		GridBagConstraints gbc_cmbxProduct = new GridBagConstraints();
		gbc_cmbxProduct.insets = new Insets(0, 0, 5, 5);
		gbc_cmbxProduct.fill = GridBagConstraints.HORIZONTAL;
		gbc_cmbxProduct.gridx = 1;
		gbc_cmbxProduct.gridy = 1;
		pnAddItems.add(cmbxProduct, gbc_cmbxProduct);
		
		JLabel lblNewLabel_2 = new JLabel("Barcode");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 13));
		GridBagConstraints gbc_lblNewLabel_2 = new GridBagConstraints();
		gbc_lblNewLabel_2.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_2.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_2.gridx = 0;
		gbc_lblNewLabel_2.gridy = 2;
		pnAddItems.add(lblNewLabel_2, gbc_lblNewLabel_2);
		
		tfBarcode = new JTextField();
		tfBarcode.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char ch = e.getKeyChar();
				if ((ch < '0' || ch > '9') && (ch != KeyEvent.VK_BACK_SPACE)) {
					e.consume();
				} // Only allows numbers to be inputed into the text field
			}
		});
		tfBarcode.setColumns(10);
		GridBagConstraints gbc_tfBarcode = new GridBagConstraints();
		gbc_tfBarcode.fill = GridBagConstraints.HORIZONTAL;
		gbc_tfBarcode.insets = new Insets(0, 0, 5, 5);
		gbc_tfBarcode.gridx = 1;
		gbc_tfBarcode.gridy = 2;
		pnAddItems.add(tfBarcode, gbc_tfBarcode);
		
		String[] itemType = {"standard", "flexible", "gaming"};
		
		JLabel lblbrand = new JLabel("Brand:");
		lblbrand.setFont(new Font("Tahoma", Font.PLAIN, 13));
		GridBagConstraints gbc_lblbrand = new GridBagConstraints();
		gbc_lblbrand.anchor = GridBagConstraints.WEST;
		gbc_lblbrand.insets = new Insets(0, 0, 5, 5);
		gbc_lblbrand.gridx = 0;
		gbc_lblbrand.gridy = 3;
		pnAddItems.add(lblbrand, gbc_lblbrand);
		
		tfBrand = new JTextField();
		tfBrand.setColumns(10);
		GridBagConstraints gbc_tfBrand = new GridBagConstraints();
		gbc_tfBrand.fill = GridBagConstraints.HORIZONTAL;
		gbc_tfBrand.anchor = GridBagConstraints.NORTH;
		gbc_tfBrand.insets = new Insets(0, 0, 5, 5);
		gbc_tfBrand.gridx = 1;
		gbc_tfBrand.gridy = 3;
		pnAddItems.add(tfBrand, gbc_tfBrand);
		
		JLabel lbltype = new JLabel("Type:");
		lbltype.setFont(new Font("Tahoma", Font.PLAIN, 13));
		GridBagConstraints gbc_lbltype = new GridBagConstraints();
		gbc_lbltype.anchor = GridBagConstraints.WEST;
		gbc_lbltype.insets = new Insets(0, 0, 5, 5);
		gbc_lbltype.gridx = 0;
		gbc_lbltype.gridy = 4;
		pnAddItems.add(lbltype, gbc_lbltype);
		JComboBox cmbxType = new JComboBox(itemType);
		GridBagConstraints gbc_cmbxType = new GridBagConstraints();
		gbc_cmbxType.insets = new Insets(0, 0, 5, 5);
		gbc_cmbxType.fill = GridBagConstraints.HORIZONTAL;
		gbc_cmbxType.gridx = 1;
		gbc_cmbxType.gridy = 4;
		pnAddItems.add(cmbxType, gbc_cmbxType);
		
		String[] itemConnectivity = {"wired", "wireless"};
		
		JLabel lblcolor = new JLabel("Color:");
		lblcolor.setFont(new Font("Tahoma", Font.PLAIN, 13));
		GridBagConstraints gbc_lblcolor = new GridBagConstraints();
		gbc_lblcolor.anchor = GridBagConstraints.WEST;
		gbc_lblcolor.insets = new Insets(0, 0, 5, 5);
		gbc_lblcolor.gridx = 0;
		gbc_lblcolor.gridy = 5;
		pnAddItems.add(lblcolor, gbc_lblcolor);
		
		tfColor = new JTextField();
		tfColor.setColumns(10);
		GridBagConstraints gbc_tfColor = new GridBagConstraints();
		gbc_tfColor.fill = GridBagConstraints.HORIZONTAL;
		gbc_tfColor.anchor = GridBagConstraints.NORTH;
		gbc_tfColor.insets = new Insets(0, 0, 5, 5);
		gbc_tfColor.gridx = 1;
		gbc_tfColor.gridy = 5;
		pnAddItems.add(tfColor, gbc_tfColor);
		
		JLabel lblconn = new JLabel("Connectivity:");
		lblconn.setFont(new Font("Tahoma", Font.PLAIN, 13));
		GridBagConstraints gbc_lblconn = new GridBagConstraints();
		gbc_lblconn.anchor = GridBagConstraints.WEST;
		gbc_lblconn.insets = new Insets(0, 0, 5, 5);
		gbc_lblconn.gridx = 0;
		gbc_lblconn.gridy = 6;
		pnAddItems.add(lblconn, gbc_lblconn);
		JComboBox cmbxConnectivity = new JComboBox(itemConnectivity);
		GridBagConstraints gbc_cmbxConnectivity = new GridBagConstraints();
		gbc_cmbxConnectivity.insets = new Insets(0, 0, 5, 5);
		gbc_cmbxConnectivity.fill = GridBagConstraints.HORIZONTAL;
		gbc_cmbxConnectivity.gridx = 1;
		gbc_cmbxConnectivity.gridy = 6;
		pnAddItems.add(cmbxConnectivity, gbc_cmbxConnectivity);
		
		JLabel lblquantity = new JLabel("Quantity:");
		lblquantity.setFont(new Font("Tahoma", Font.PLAIN, 13));
		GridBagConstraints gbc_lblquantity = new GridBagConstraints();
		gbc_lblquantity.anchor = GridBagConstraints.WEST;
		gbc_lblquantity.insets = new Insets(0, 0, 5, 5);
		gbc_lblquantity.gridx = 0;
		gbc_lblquantity.gridy = 7;
		pnAddItems.add(lblquantity, gbc_lblquantity);
		
		tfQuantity = new JTextField();
		tfQuantity.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char ch = e.getKeyChar();
				if ((ch < '0' || ch > '9') && (ch != KeyEvent.VK_BACK_SPACE)) {
					e.consume();
				} // Only allows numbers to be inputed into the text field
			}
		});
		tfQuantity.setColumns(10);
		GridBagConstraints gbc_tfQuantity = new GridBagConstraints();
		gbc_tfQuantity.fill = GridBagConstraints.HORIZONTAL;
		gbc_tfQuantity.anchor = GridBagConstraints.NORTH;
		gbc_tfQuantity.insets = new Insets(0, 0, 5, 5);
		gbc_tfQuantity.gridx = 1;
		gbc_tfQuantity.gridy = 7;
		pnAddItems.add(tfQuantity, gbc_tfQuantity);
		
		JButton btnNewButton_1 = new JButton("Log Out");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		
		JButton btnSave = new JButton("Save");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				// The following lines of code checks over all the possible validation for inputing a product 
				try {
					if (tfBarcode.getText().equals("") || tfBrand.getText().equals("") || tfColor.getText().equals("") || tfQuantity.getText().equals("") || tfOCost.getText().equals("") || tfRPrice.getText().equals("") || tfExtraInfo.getText().equals("")) {
						JOptionPane.showMessageDialog(null, "Cannot leave anything blank", "Warning", JOptionPane.WARNING_MESSAGE);
					} else if (checkBarcode(tfBarcode.getText()) == false) {
						JOptionPane.showMessageDialog(null, "Barcode is not in the correct format or it has already been used.\nCorrect format: 6-digit number", "Warning", JOptionPane.WARNING_MESSAGE);
					} else if (tfBrand.getText().equals("")) {
						JOptionPane.showMessageDialog(null, "Please enter a Brand.", "Warning", JOptionPane.WARNING_MESSAGE);
					} else if (checkType(cmbxType.getSelectedItem().toString(), cmbxProduct.getSelectedItem().toString()) == false) {
						JOptionPane.showMessageDialog(null, "Incorrect input for mouse.\nMouse can only be of standard or gaming type.", "Warning", JOptionPane.WARNING_MESSAGE);
					} else if (tfColor.getText().equals("")) {
						JOptionPane.showMessageDialog(null, "Please enter a Color.", "Warning", JOptionPane.WARNING_MESSAGE);
					} else if (checkQuantity(tfQuantity.getText()) == false) {
						JOptionPane.showMessageDialog(null, "Quantity must be between 0 and 1000000.", "Warning", JOptionPane.WARNING_MESSAGE);
					} else if (checkCost(tfOCost.getText()) == false) {
						JOptionPane.showMessageDialog(null, "Original Cost must be a double.\nExample: 12.50", "Warning", JOptionPane.WARNING_MESSAGE);
					} else if (checkCost(tfRPrice.getText()) == false) {
						JOptionPane.showMessageDialog(null, "Retail Price must be a double.\nExample: 12.50", "Warning", JOptionPane.WARNING_MESSAGE);
					} else if (checkExtraInfo(tfExtraInfo.getText(), cmbxProduct.getSelectedItem().toString()) == false) {
						JOptionPane.showMessageDialog(null, "If mouse, input must be numb > 0.\nIf keyboard, input must be 'UK' or 'US'", "Warning", JOptionPane.WARNING_MESSAGE);
					} else {
						
						// If the previous inputs are correct then the product is inputed into the file as an object
						int barcode = Integer.parseInt(tfBarcode.getText().trim());
						PType typeP = PType.valueOf(cmbxType.getSelectedItem().toString().trim());
						String brand = tfBrand.getText().trim();
						String colour = tfColor.getText().trim();
						Connectivity conn = Connectivity.valueOf(cmbxConnectivity.getSelectedItem().toString().trim());
						int quantity = Integer.parseInt(tfQuantity.getText().trim());
						double originalCost = Double.parseDouble(tfOCost.getText().trim());
						double retailPrice = Double.parseDouble(tfRPrice.getText().trim());
						
						FileFunctions fileFunctions = new FileFunctions();
						if (cmbxProduct.getSelectedItem().toString().equals("mouse")) {
							Mouse input = new Mouse (barcode, brand, typeP, colour, conn, quantity, originalCost, retailPrice, Integer.parseInt(tfExtraInfo.getText().trim()));
							try {
								fileFunctions.writeProduct(input.toString()+"\n", true);
							} catch (IOException e1) {e1.printStackTrace();
							}
						} else {
							KeyboardLang keyLang = KeyboardLang.valueOf(tfExtraInfo.getText().toString().trim());
							Keyboard input = new Keyboard (barcode, brand, typeP, colour, conn, quantity, originalCost, retailPrice, keyLang);
							try {
								fileFunctions.writeProduct(input.toString()+"\n", true);
							} catch (IOException e1) {e1.printStackTrace();
							}
						}
						
						try {
							outputTable();
						} catch (IOException e1) {
							e1.printStackTrace();
						}
						// Clearing the text entries
						tfBarcode.setText("");
						tfBrand.setText("");
						tfColor.setText("");
						tfQuantity.setText("");
						tfOCost.setText("");
						tfRPrice.setText("");
						tfExtraInfo.setText("");
					}
				} catch (NumberFormatException e1) {
					e1.printStackTrace();
				} catch (HeadlessException e1) {
					e1.printStackTrace();
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				}
			}
		});
		
		JLabel lblocost = new JLabel("Original Cost:");
		lblocost.setFont(new Font("Tahoma", Font.PLAIN, 13));
		GridBagConstraints gbc_lblocost = new GridBagConstraints();
		gbc_lblocost.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblocost.insets = new Insets(0, 0, 5, 5);
		gbc_lblocost.gridx = 0;
		gbc_lblocost.gridy = 8;
		pnAddItems.add(lblocost, gbc_lblocost);
		
		tfOCost = new JTextField();
		tfOCost.setColumns(10);
		GridBagConstraints gbc_tfOCost = new GridBagConstraints();
		gbc_tfOCost.fill = GridBagConstraints.HORIZONTAL;
		gbc_tfOCost.anchor = GridBagConstraints.NORTH;
		gbc_tfOCost.insets = new Insets(0, 0, 5, 5);
		gbc_tfOCost.gridx = 1;
		gbc_tfOCost.gridy = 8;
		pnAddItems.add(tfOCost, gbc_tfOCost);
		
		JLabel lblrprize = new JLabel("Retail Price:");
		lblrprize.setFont(new Font("Tahoma", Font.PLAIN, 13));
		GridBagConstraints gbc_lblrprize = new GridBagConstraints();
		gbc_lblrprize.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblrprize.insets = new Insets(0, 0, 5, 5);
		gbc_lblrprize.gridx = 0;
		gbc_lblrprize.gridy = 9;
		pnAddItems.add(lblrprize, gbc_lblrprize);
		
		tfRPrice = new JTextField();
		tfRPrice.setColumns(10);
		GridBagConstraints gbc_tfRPrice = new GridBagConstraints();
		gbc_tfRPrice.fill = GridBagConstraints.HORIZONTAL;
		gbc_tfRPrice.anchor = GridBagConstraints.NORTH;
		gbc_tfRPrice.insets = new Insets(0, 0, 5, 5);
		gbc_tfRPrice.gridx = 1;
		gbc_tfRPrice.gridy = 9;
		pnAddItems.add(tfRPrice, gbc_tfRPrice);
		
		JLabel lblextra = new JLabel("Extra Info:");
		lblextra.setFont(new Font("Tahoma", Font.PLAIN, 13));
		GridBagConstraints gbc_lblextra = new GridBagConstraints();
		gbc_lblextra.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblextra.anchor = GridBagConstraints.NORTH;
		gbc_lblextra.insets = new Insets(0, 0, 5, 5);
		gbc_lblextra.gridx = 0;
		gbc_lblextra.gridy = 10;
		pnAddItems.add(lblextra, gbc_lblextra);
		
		tfExtraInfo = new JTextField();
		tfExtraInfo.setColumns(10);
		GridBagConstraints gbc_tfExtraInfo = new GridBagConstraints();
		gbc_tfExtraInfo.fill = GridBagConstraints.HORIZONTAL;
		gbc_tfExtraInfo.anchor = GridBagConstraints.NORTH;
		gbc_tfExtraInfo.insets = new Insets(0, 0, 5, 5);
		gbc_tfExtraInfo.gridx = 1;
		gbc_tfExtraInfo.gridy = 10;
		pnAddItems.add(tfExtraInfo, gbc_tfExtraInfo);
		
		JLabel lblDesc9 = new JLabel("Mouse: No of buttons - Keyboard: UK/US");
		lblDesc9.setFont(new Font("Tahoma", Font.ITALIC, 10));
		GridBagConstraints gbc_lblDesc9 = new GridBagConstraints();
		gbc_lblDesc9.anchor = GridBagConstraints.NORTHWEST;
		gbc_lblDesc9.insets = new Insets(0, 0, 5, 5);
		gbc_lblDesc9.gridx = 1;
		gbc_lblDesc9.gridy = 11;
		pnAddItems.add(lblDesc9, gbc_lblDesc9);
		btnSave.setFont(new Font("Tahoma", Font.PLAIN, 13));
		GridBagConstraints gbc_btnSave = new GridBagConstraints();
		gbc_btnSave.fill = GridBagConstraints.BOTH;
		gbc_btnSave.insets = new Insets(0, 0, 5, 5);
		gbc_btnSave.gridx = 1;
		gbc_btnSave.gridy = 13;
		pnAddItems.add(btnSave, gbc_btnSave);
		btnNewButton_1.setFont(new Font("Tahoma", Font.PLAIN, 13));
		GridBagConstraints gbc_btnNewButton_1 = new GridBagConstraints();
		gbc_btnNewButton_1.insets = new Insets(0, 0, 5, 5);
		gbc_btnNewButton_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnNewButton_1.gridx = 1;
		gbc_btnNewButton_1.gridy = 14;
		pnAddItems.add(btnNewButton_1, gbc_btnNewButton_1);
		
		
		try {
			outputTable();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
}
