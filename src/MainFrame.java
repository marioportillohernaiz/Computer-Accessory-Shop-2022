import java.awt.EventQueue;
import javax.swing.JFrame;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import java.awt.Font;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MainFrame extends JFrame {
	private JFrame fmLogIn;	
	public static String userLogIn;
	FileWriter fileW;

	/*Launch the application.*/
	public static void main(String[] args) throws FileNotFoundException, IOException {	
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame window = new MainFrame();
					window.fmLogIn.setVisible(true);
				} catch (Exception e) {e.printStackTrace();}
			}
		});
	}
	
	/*Create the application.*/
	public MainFrame() throws FileNotFoundException {
		initialize();
	}
		
	
	/*Initialize the contents of the frame.*/
	private void initialize() throws FileNotFoundException {
		fmLogIn = new JFrame();
		fmLogIn.setBounds(100, 100, 500, 300);
		fmLogIn.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		FileFunctions FileFn = new FileFunctions();
		List<User> userList = FileFn.readUser();
		
		JLabel lblComputerAccessoriesShop = new JLabel("Computer Accessories Shop : Log In");
		lblComputerAccessoriesShop.setFont(new Font("Trebuchet MS", Font.PLAIN, 20));
		fmLogIn.getContentPane().add(lblComputerAccessoriesShop, BorderLayout.NORTH);
		
		JPanel panel = new JPanel();
		fmLogIn.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Welcome, log in to your account");
		lblNewLabel.setBounds(138, 75, 191, 16);
		panel.add(lblNewLabel);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));

		String [] firstUser = {"Select User"};		// Adds the first non-select item of the log in combobox
		JComboBox cmbxLogIn = new JComboBox(firstUser);
		cmbxLogIn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String userSelected = (String) cmbxLogIn.getSelectedItem();
				userLogIn = userSelected;	// Saves the user logged in as a global variable to be passed on
				
				for (User person : userList) {
					if (userSelected.equals(person.getUsername()) &&  person instanceof Admin) {
						AdminPage showadmin = new AdminPage();
						showadmin.setVisible(true);		// Displaying the Admin's page if the user selected is Admin
						break;
					} else {
						if (userSelected.equals(person.getUsername()) &&  person instanceof Customer) {
							CustomerPage showcustomer;
							try {
								showcustomer = new CustomerPage();
								showcustomer.setVisible(true);	// Displaying the Customer's page if the user selected is a Customer
							} catch (FileNotFoundException e1) {
								e1.printStackTrace();
							}
							break;
						}
					}
				}
			}
		});
		for (User person : userList) {
			cmbxLogIn.addItem(person.getUsername().toString());	// Fills the combobox with the user's username
		}
		cmbxLogIn.setBounds(157, 118, 143, 22);
		panel.add(cmbxLogIn);
	}
}
