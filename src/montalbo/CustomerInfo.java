package montalbo;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class CustomerInfo extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField textField;
	private JTextField name;
	private JTextField phone;
	ConnectDB c;

	
	public CustomerInfo(Object account_id, String date, String time, String amount, String payment_status) {
		setTitle("ENTER CUSTOMER INFO");
		setResizable(false);
		
		/*Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);*/
		
		setModal(true);
		
		//setBounds(100, 100, 450, 219);
		setSize(450,219);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setForeground(new Color(160, 82, 45));
		contentPanel.setBackground(new Color(255, 255, 153));
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JLabel lblCustomerName = new JLabel("Customer Name:");
		lblCustomerName.setForeground(new Color(160, 82, 45));
		lblCustomerName.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblCustomerName.setBounds(41, 87, 130, 25);
		contentPanel.add(lblCustomerName);
		{
			JLabel lblCustomerInformation = new JLabel("CUSTOMER INFORMATION");
			lblCustomerInformation.setForeground(new Color(160, 82, 45));
			lblCustomerInformation.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 20));
			lblCustomerInformation.setBounds(10, 11, 301, 14);
			contentPanel.add(lblCustomerInformation);
		}
		{
			JLabel lblCustomerContactNo = new JLabel("Contact Number:");
			lblCustomerContactNo.setForeground(new Color(160, 82, 45));
			lblCustomerContactNo.setFont(new Font("Tahoma", Font.BOLD, 15));
			lblCustomerContactNo.setBounds(41, 123, 130, 25);
			contentPanel.add(lblCustomerContactNo);
		}
		{
			JLabel lblTransactionId = new JLabel("Transaction ID:");
			lblTransactionId.setForeground(new Color(160, 82, 45));
			lblTransactionId.setFont(new Font("Tahoma", Font.BOLD, 15));
			lblTransactionId.setBounds(41, 51, 130, 25);
			contentPanel.add(lblTransactionId);
		}
		
		String id = "0001";
		String sql = "SELECT transaction_id FROM customer_transaction ORDER BY transaction_id DESC LIMIT 1";
		try {
			c = new ConnectDB();
			
			c.pst = c.con.prepareStatement(sql);
			c.pst.execute();
			c.rs = c.pst.getResultSet();
			
			while (c.rs.next())
			{
				int i = c.rs.getInt("transaction_id") + 1;
				id = id.substring(0,id.length()-1) + i;
			}
			
			
			
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		
		
		
		textField = new JTextField(id);
		textField.setEditable(false);
		textField.setHorizontalAlignment(SwingConstants.RIGHT);
		textField.setBounds(181, 55, 130, 20);
		contentPanel.add(textField);
		textField.setColumns(10);
		
		name = new JTextField();
		name.setColumns(10);
		name.setBounds(181, 91, 243, 20);
		contentPanel.add(name);
		
		phone = new JTextField();
		phone.setColumns(10);
		phone.setBounds(181, 127, 130, 20);
		contentPanel.add(phone);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBackground(new Color(255, 255, 153));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			buttonPane.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
			{
				JButton okButton = new JButton("OK");
				okButton.setFont(new Font("Tahoma", Font.BOLD, 11));
				okButton.setForeground(new Color(160, 82, 45));
				okButton.setBackground(new Color(154,205,50));
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						try 
						{
							c = new ConnectDB();
							String insert = "INSERT INTO customer_transaction (account_id,customer_name,contact_no,date,time,amount,payment_status) VALUES (?,?,?,?,?,?,?);";
							c.pst = c.con.prepareStatement(insert);
							c.pst.setObject(1, account_id);
							c.pst.setString(2, name.getText());
							c.pst.setString(3, phone.getText());
							c.pst.setString(4, date);
							c.pst.setString(5, time);
							c.pst.setString(6, amount);
							c.pst.setString(7, payment_status);
							
							//--------------VALidATIONS---------------------
							Pattern namePattern = Pattern.compile("[0-9[^\\w]&&[^' ]]", Pattern.CASE_INSENSITIVE);
							Matcher nameMatcher = namePattern.matcher(name.getText());
							
							if(name.getText().equals("")) // Check is employee name is empty
							{
								JOptionPane.showMessageDialog(null, "You must enter the name of the employee!");
							}
							else if(nameMatcher.find())// Check if the are numbers / symbols in employee's name
							{
								JOptionPane.showMessageDialog(null, "CUSTOMER NAME: NUMBERS and SYMBOLS are NOT allowed!");
							}
							else
							{
								if(phone.getText().equals(""))// CHeck if a contact number is empty
								{
									JOptionPane.showMessageDialog(null, "A phone number is required!");
								}
								else
								{
									Pattern phonePattern = Pattern.compile("[^0-9]", Pattern.CASE_INSENSITIVE);
									Matcher phoneMatcher = phonePattern.matcher(phone.getText());
									if(phoneMatcher.find()) // CHeck if the phone number entered has symbols,whitespaces or letters
									{
										JOptionPane.showMessageDialog(null, "CONTACT NUMBER: LETTERS, WHITESPACES, AND SYMBOLS are NOT allowed! Use '0' number prefix instead of '+63'.");
									}
									else
									{
										if(phone.getText().length()<7)
										{
											JOptionPane.showMessageDialog(null, "CONTACT NUMBER: Enter a minimum of 7 digit contact number.");

										}
										else
										{
											c.pst.execute();
											JOptionPane.showMessageDialog(null, "New transaction has been inserted!");
											
											dispose();
										}
									}
								}
							
							
							}
					} 
						catch (SQLException e1) 
						{
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						
						
					}
				});
				getRootPane().setDefaultButton(okButton);
			}
		}
	}
	
	public void DeleteCart(){
		String deleteCart = "DELETE FROM temp_cart";
		try {
			c.pst = c.con.prepareStatement(deleteCart);
			c.pst.execute();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
