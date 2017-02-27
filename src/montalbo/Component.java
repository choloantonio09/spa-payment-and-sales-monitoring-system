package montalbo;

import java.awt.Font;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.awt.event.ActionEvent;

public class Component extends JFrame{
	
	private JTextArea txtPrintArea = new JTextArea();
	private JPanel contentPane;
	String heheemployee = null;
	ConnectDB c;
	

	public Component(Object transaction_id,Object cash, Object change)
	{
		setTitle("Official Receipt");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		setSize(612,667);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnPrint = new JButton("Print");
		btnPrint.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PrintSupport.printComponent(txtPrintArea);
			}
		});
		btnPrint.setFont(new Font("Tahoma", Font.BOLD, 25));
		btnPrint.setForeground(new Color(253, 245, 230));
		btnPrint.setBackground(new Color(107, 142, 35));
		btnPrint.setBounds(153, 545, 286, 73);
		contentPane.add(btnPrint);
		txtPrintArea.setEditable(false);
		
		int tid = (int) transaction_id;
		Calendar cal = new GregorianCalendar();
		int day = cal.get(Calendar.DAY_OF_MONTH);
		int month = cal.get(Calendar.MONTH);
		int year = cal.get(Calendar.YEAR);
		
		int second = cal.get(Calendar.SECOND);
		int minute = cal.get(Calendar.MINUTE);
		int hour = cal.get(Calendar.HOUR);
		month++;
		
		String employee_id = null;
		String customer = null;
		String phone = null;
		String sid = null;
		String sname = null;
		String qty = null;
		String subtotal = null;
		String total = null;
		String sample = null;
		
		
		try {
			c = new ConnectDB();
			String sql = "SELECT * FROM customer_transaction WHERE transaction_id = ?";
			c.pst = c.con.prepareStatement(sql);
			c.pst.setInt(1, tid);
			c.pst.execute();
			c.rs = c.pst.getResultSet();
			
			while(c.rs.next())
			{
				
				customer = c.rs.getString("customer_name");
				employee_id = c.rs.getString("account_id");
				System.out.println(employee_id);
				total = c.rs.getString("amount");
				
			}
			
			String sql2 = "SELECT account_name FROM account WHERE account_id = ?";
			c.pst = c.con.prepareStatement(sql2);
			c.pst.setString(1, employee_id);
			c.pst.execute();
			c.rs = c.pst.getResultSet();
			
			while(c.rs.next())
			{
				heheemployee = c.rs.getString(1);
				System.out.println(heheemployee);
				sample = heheemployee;
			}
			
			
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		
		txtPrintArea.setText("\r\n\t\t            Mont Albo's Spa\r\n\t\t      "
				+ "Sta. Mesa, Manila Branch\r\n\r\n "
				+ "Transaction No. "+tid+"\r\n "
				//+ "Employee In-Charge: "+sample+"\r\n "
				+ "Date: " +year+ "-" +month+ "-" +day+ "\t\t"
				+ "Time: " +hour+ ":" +minute+ ":" +second+"\r\n "
				+ "Client's name: "+customer+"\t"
				+"\n\n S.ID\t\tNAME\t\tQTY\tSUB-TOTAL\r\n ");
				
				try {
					c = new ConnectDB();
					String sql = "SELECT * FROM customer_order WHERE transaction_id = ?";
					c.pst = c.con.prepareStatement(sql);
					c.pst.setInt(1, tid);
					c.pst.execute();
					c.rs = c.pst.getResultSet();
					
					while(c.rs.next())
					{
						
						txtPrintArea.append(""+c.rs.getString("service_id")+"\t\t"
				+ ""+c.rs.getString("service_name")+"\t\t "
				+ ""+c.rs.getString("quantity")+"\t"
				+ ""+c.rs.getString("sub_total")+"\n ");
						
					}
					txtPrintArea.append("\r\n   ____________________________________________________________________\r\n\t\t\t\t"
				+ "TOTAL:\t "+total+"\r\n\t\t\t\t"
				+ "CASH:\t"+cash+"\r\n\t\t\t\t"
				+ "CHANGE: \t"+change+"\r\n\r\n\t                  THIS SERVES AS YOUR OFFICIAL RECEIPT.\r\n      \t\t                Thankyou! \r\n\t\t    We hope to see you again!\r\n\t\tVisit www.montalbomassage.com");
					

				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
		
		txtPrintArea.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtPrintArea.setBounds(10, 11, 575, 523);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportView(txtPrintArea);
		scrollPane.setBounds(10, 11, 575, 523);
		contentPane.add(scrollPane);
		
		
		
	}
}
