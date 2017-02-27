package montalbo;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.awt.event.ActionEvent;

public class Cash extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	String employeeName = null;
	
	public Cash(Object transaction_id,Object account_id) {
		setUndecorated(true);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//setBounds(100, 100, 266, 194);
		setSize(266,194);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 153));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblCashAmount = new JLabel("CASH AMOUNT:");
		lblCashAmount.setForeground(new Color(102, 102, 0));
		lblCashAmount.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblCashAmount.setBounds(50, 26, 166, 41);
		contentPane.add(lblCashAmount);
		
		textField = new JTextField();
		textField.setFont(new Font("Tahoma", Font.BOLD, 16));
		textField.setHorizontalAlignment(SwingConstants.RIGHT);
		textField.setBounds(40, 78, 188, 41);
		contentPane.add(textField);
		textField.setColumns(10);
		
		
		
		JButton btnPay = new JButton("PAY");
		btnPay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				ConnectDB c;
				try {
					c = new ConnectDB();
					String sql2 = "SELECT account_name FROM account WHERE account_id = ?";
					c.pst = c.con.prepareStatement(sql2);
					c.pst.setObject(1, account_id);
					c.pst.execute();
					c.rs = c.pst.getResultSet();
					
					while(c.rs.next())
					{
						employeeName = c.rs.getString(1);
					
						
					}
				} catch (SQLException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				
				
				
				
				float change = 0;
				String cash = textField.getText();
				float realcash = Float.parseFloat(cash);
				
				Pattern opricePattern = Pattern.compile("[^0-9^\\.]", Pattern.CASE_INSENSITIVE);
				Matcher opriceMatcher = opricePattern.matcher(textField.getText());
				if(textField.getText().equals(""))
				{
					JOptionPane.showMessageDialog(null, "CASH: Missing Field! The cash for payment is required.");
				}
				else if(opriceMatcher.find())
				{
					JOptionPane.showMessageDialog(null, "CASH: Only numbers are allowed.");
					
				}
				else
				{
					String sql = "SELECT * FROM customer_transaction WHERE transaction_id = ?";
					try {
						c = new ConnectDB();
						c.pst = c.con.prepareStatement(sql);
						c.pst.setObject(1, transaction_id);
						c.rs = c.pst.executeQuery();
						c.rs.next();
						
						float total = c.rs.getFloat("amount");
						
						if(realcash<total)
						{
							JOptionPane.showMessageDialog(null, "CASH: Your cash is not enough.");
						}
						else
						{
							change = realcash - total;
							
							String changePaid = "UPDATE customer_transaction SET payment_status = ? WHERE transaction_id = ?";
							
							c.pst = c.con.prepareStatement(changePaid);
							c.pst.setString(1, "PAID");
							c.pst.setObject(2, transaction_id);
							c.pst.execute();
							Component print = new Component(transaction_id, realcash, change);
							print.setVisible(true);
							dispose();
						}
						
						
						
						
						
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				
				
				
				
			}
		});
		btnPay.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnPay.setForeground(new Color(255, 255, 255));
		btnPay.setBackground(new Color(102, 153, 51));
		btnPay.setBounds(140, 130, 76, 29);
		contentPane.add(btnPay);
		
		JButton btnCancel = new JButton("BACK");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		btnCancel.setForeground(Color.WHITE);
		btnCancel.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnCancel.setBackground(new Color(204, 51, 0));
		btnCancel.setBounds(50, 130, 76, 29);
		contentPane.add(btnCancel);
	}
}
