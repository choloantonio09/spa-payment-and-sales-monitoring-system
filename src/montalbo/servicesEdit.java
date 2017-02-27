package montalbo;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JSpinner;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JButton;
import java.awt.Color;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.SpinnerNumberModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class servicesEdit extends JFrame {

	private JPanel contentPane;
	private JTextField id;
	private JTextField name;
	private JTextField price;
	private JSpinner generateVoucher;
	private JButton clear;
	private JButton submit;
	private JTextField voucherPrice;
	
	ConnectDB c;
	private JLabel lblAvailability;
	private JComboBox availability;

	public servicesEdit(Object service_id) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 600, 669);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 0));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(10, 87, 564, 10);
		contentPane.add(separator);
		
		id = new JTextField(service_id.toString());
		id.setEditable(false);
		id.setFont(new Font("Tahoma", Font.PLAIN, 18));
		id.setBounds(264, 145, 165, 35);
		contentPane.add(id);
		id.setColumns(10);
		
		name = new JTextField();
		name.setFont(new Font("Tahoma", Font.PLAIN, 14));
		name.setBounds(234, 193, 304, 25);
		contentPane.add(name);
		name.setColumns(10);
		
		price = new JTextField();
		price.setFont(new Font("Tahoma", Font.PLAIN, 14));
		price.setColumns(10);
		price.setBounds(136, 387, 107, 25);
		contentPane.add(price);
		
		JTextArea description = new JTextArea();
		description.setFont(new Font("Tahoma", Font.PLAIN, 14));
		description.setBounds(53, 263, 487, 113);
		contentPane.add(description);
		
		generateVoucher = new JSpinner();
		generateVoucher.setModel(new SpinnerNumberModel(new Integer(0), new Integer(0), null, new Integer(1)));
		generateVoucher.setFont(new Font("Tahoma", Font.PLAIN, 14));
		generateVoucher.setBounds(316, 427, 90, 25);
		contentPane.add(generateVoucher);
		
		JLabel label = new JLabel("CATEGORY:");
		label.setForeground(new Color(139, 69, 19));
		label.setFont(new Font("Tahoma", Font.BOLD, 23));
		label.setBounds(53, 462, 144, 40);
		contentPane.add(label);
		
		JComboBox category = new JComboBox();
		category.setModel(new DefaultComboBoxModel(new String[] {"MASSAGE", "SCRUB"}));
		category.setBounds(196, 471, 107, 25);
		contentPane.add(category);
		
		JLabel label_1 = new JLabel("VOUCHER PRICE:");
		label_1.setForeground(new Color(139, 69, 19));
		label_1.setFont(new Font("Tahoma", Font.BOLD, 22));
		label_1.setBounds(264, 384, 194, 32);
		contentPane.add(label_1);
		
		voucherPrice = new JTextField();
		voucherPrice.setFont(new Font("Tahoma", Font.PLAIN, 14));
		voucherPrice.setColumns(10);
		voucherPrice.setBounds(468, 387, 106, 25);
		contentPane.add(voucherPrice);
		

		lblAvailability = new JLabel("AVAILABILITY:");
		lblAvailability.setForeground(new Color(139, 69, 19));
		lblAvailability.setFont(new Font("Tahoma", Font.BOLD, 23));
		lblAvailability.setBounds(53, 510, 190, 40);
		contentPane.add(lblAvailability);
		
		availability = new JComboBox();
		availability.setModel(new DefaultComboBoxModel(new String[] {"AVAILABLE", "UNAVAILABLE"}));
		availability.setBounds(236, 518, 114, 25);
		contentPane.add(availability);
		
		int origVoucherCount = 0;
		
		try {
			c = new ConnectDB();
			
			String select = "SELECT * from service WHERE service_id = ?";
			c.pst = c.con.prepareStatement(select);
			c.pst.setString(1, id.getText());
			
			c.pst.execute();
			c.rs = c.pst.getResultSet();
			
			while(c.rs.next())
			{
				name.setText(c.rs.getString("service_name"));
				description.setText(c.rs.getString("description"));
				category.setSelectedItem(c.rs.getObject("service_type"));
				price.setText(c.rs.getString("service_price"));
				voucherPrice.setText(c.rs.getString("voucher_price"));
				//generateVoucher.setValue(c.rs.getObject("voucher_count"));
				
				origVoucherCount = (int) c.rs.getObject("voucher_count");
				
				//intAvailability = c.rs.getByte("availability");
				
				if(c.rs.getByte("availability")==1)
				{
					availability.setSelectedItem("AVAILABLE");
				}
				else
				{
					availability.setSelectedItem("UNAVAILABLE");

				}
				
				
			}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		submit = new JButton("");
		submit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				// ++++++++++ EDIT OF SERVICE ++++++++++++++++++++++++++++++++++++++++
				try 
				{
					//=++++++++++++++++++++++++++++++++++ START OF VALIDATION +++++++++++++++++++++++++++++++=
					int updateCtr = 0;
					
					Pattern namePattern = Pattern.compile("[0-9[^\\w]&&[^'\\- ]]", Pattern.CASE_INSENSITIVE);
					Matcher nameMatcher = namePattern.matcher(name.getText());
					
					if(name.getText().equals(""))
					{
						JOptionPane.showMessageDialog(null, "SERVICE NAME: You must enter the name of the service!");
					}
					else if(nameMatcher.find())
					{
						JOptionPane.showMessageDialog(null, "SERVICE NAME: Numbers are not allowed. Only letters, '- symbols, and whitespaces are accepted.");
					}
					else
					{
						String un ="SELECT service_name FROM service WHERE service_id != ?";
						c.pst = c.con.prepareStatement(un);
						c.pst.setString(1, id.getText());
						c.pst.execute();
						c.rs = c.pst.getResultSet();
						int snctr = 0;
						while(c.rs.next())
						{
							if(name.getText().equalsIgnoreCase(c.rs.getString("service_name")))
							{
								snctr++;
							}
						}
						//System.out.println(snctr);
						if(snctr!=0)
						{
							JOptionPane.showMessageDialog(null, "SERVICE NAME: That service already exists.");
						}
						else
						{
							if(description.getText().equals(""))
							{
								JOptionPane.showMessageDialog(null, "DESCRIPTION: Service description is required.");
							}
							else
							{
								Pattern opricePattern = Pattern.compile("[^0-9^\\.]", Pattern.CASE_INSENSITIVE);
								Matcher opriceMatcher = opricePattern.matcher(price.getText());
								if(price.getText().equals(""))
								{
									JOptionPane.showMessageDialog(null, "SERVICE PRICE: Missing Field! The price of the service is required.");
								}
								else if(opriceMatcher.find())
								{
									JOptionPane.showMessageDialog(null, "SERVICE PRICE: Only numbers are allowed.");
									
								}
								else
								{
									
									if(category.getSelectedItem().equals(null))
									{
										JOptionPane.showMessageDialog(null, "SERVICE CATEGORY: You must select a category!");
									}
									else
									{
										if(availability.getSelectedItem().equals(null))
										{
											JOptionPane.showMessageDialog(null, "SERVICE AVAILABILITY: You must select an availability!");
										}
										else
										{
											if(!generateVoucher.getValue().equals(0))
											{
												Pattern vpricePattern = Pattern.compile("[^0-9^\\.]", Pattern.CASE_INSENSITIVE);
												Matcher vpriceMatcher = vpricePattern.matcher(voucherPrice.getText());
												if(voucherPrice.getText().equals(""))
												{
													JOptionPane.showMessageDialog(null, "VOUCHER PRICE: You're trying to generate vouchers on this service, therefore the voucher price is required!.");

												}
												else if(vpriceMatcher.find())
												{
													JOptionPane.showMessageDialog(null, "VOUCHER PRICE: Only numbers are allowed.");

												}
												else
												{ 
													updateCtr++;
												}
											}
											else
											{
												
												Pattern vpricePattern = Pattern.compile("[^0-9^\\.]", Pattern.CASE_INSENSITIVE);
												Matcher vpriceMatcher = vpricePattern.matcher(voucherPrice.getText());
												if(voucherPrice.getText().equals(""))
												{
													voucherPrice.setText("0");
													updateCtr++;
												}
												else if(vpriceMatcher.find())
												{
													JOptionPane.showMessageDialog(null, "VOUCHER PRICE: Only numbers are allowed.");

												}
												else
												{ 
													updateCtr++;
												}
											}
										}
										
									}
									
								}
							}
						}
					}
					
					
					
					//================================== END OF VALIDATION ===================================
					
					
					
					if(updateCtr>0)
					{
						String sname = name.getText();
						String stype = (String) category.getSelectedItem();
						String sdescription = description.getText();
						String sprice = price.getText();
						String svoucherPrice = voucherPrice.getText();
						int gVoucher = (int) generateVoucher.getValue();
						String sid = id.getText();
						int savailability = 0;
						
						if(availability.getSelectedItem().equals("AVAILABLE"))
						{
							savailability = 1;
						}
						else
						{
							savailability = 0;
						}
						
						String update = "UPDATE service "
								+ "SET service_name = ?, service_type = ?, description = ?, service_price = ?, voucher_price = ?, availability = ?"
								+ " WHERE service_id = ?";
						
						c.pst = c.con.prepareStatement(update);
						c.pst.setString(1, sname);
						c.pst.setString(2, stype);
						c.pst.setString(3, sdescription);
						c.pst.setString(4, sprice);
						c.pst.setString(5, svoucherPrice);
						c.pst.setInt(6, savailability);
						c.pst.setString(7, sid);
						c.pst.execute();
						
						//++++++++++++++++ GENERATE VOUCHERS ++++++++++++++++++
						
						String currentServiceId = id.getText(); //GET CURRENT SERVICE ID
						
						String generateVoucherCount = "INSERT INTO voucher (service_id)"
								+ "VALUES (?)";
						c.pst = c.con.prepareStatement(generateVoucherCount);
						c.pst.setString(1, currentServiceId);
						
						
						int x = gVoucher;
						int y;
						
						for(y = 0; y < x; y++)
						{
							c.pst.execute();
						}
						
						//++++++++++++++++++++++ UPDATE SERVICE INFO (VOUCHER COUNT) ++++++++++++++++++++
						
						String getVoucherCount = "SELECT * FROM voucher WHERE service_id = ?";
						c.pst = c.con.prepareStatement(getVoucherCount);
						c.pst.setString(1, currentServiceId);
						c.pst.execute();
						c.rs = c.pst.getResultSet();
						
						int sVoucherCount = 0;
						while(c.rs.next())
						{
							sVoucherCount++;
						}
						
						String update1 = "UPDATE service SET voucher_count = ? WHERE service_id = ?";
						c.pst = c.con.prepareStatement(update1);
						c.pst.setInt(1, sVoucherCount);
						c.pst.setString(2, currentServiceId);
						
						c.pst.execute();
						
						dispose();
						JOptionPane.showMessageDialog(null, "SERVICE EDIT: Service successfully updated!");
					}
					
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		});
		submit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				submit.setIcon(new ImageIcon(main.class.getResource("/img/addAccount_button_submit_2.png")));
				submit.revalidate();
			}
			public void mouseExited(MouseEvent e) {
				submit.setIcon(new ImageIcon(main.class.getResource("/img/addAccount_button_submit.png")));
				submit.revalidate();
			}
		});
		submit.setIcon(new ImageIcon(main.class.getResource("/img/addAccount_button_submit.png")));
		submit.setBounds(286, 566, 120, 40);
		contentPane.add(submit);
		
		clear = new JButton("");
		clear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				name.setText(null);
				category.setSelectedItem(null);
				description.setText(null);
				price.setText(null);
				voucherPrice.setText(null);
				generateVoucher.setValue(0);
				availability.setSelectedItem(null);
			}
		});
		clear.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				clear.setIcon(new ImageIcon(main.class.getResource("/img/addAccount_button_clear_2.png")));
				clear.revalidate();
			}
			public void mouseExited(MouseEvent e) {
				clear.setIcon(new ImageIcon(main.class.getResource("/img/addAccount_button_clear.png")));
				clear.revalidate();
			}
		});
		clear.setIcon(new ImageIcon(main.class.getResource("/img/addAccount_button_clear.png")));
		clear.setBounds(416, 566, 120, 40);
		contentPane.add(clear);
		
		JButton btnDeleteVouchers = new JButton("DELETE VOUCHERS");
		btnDeleteVouchers.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				try {
					String deleteVoucher = "DELETE FROM voucher WHERE service_id = ?";
					c.pst = c.con.prepareStatement(deleteVoucher);
					c.pst.setString(1, id.getText());
					c.pst.execute();
					
					String getVoucherCount = "SELECT * FROM voucher WHERE service_id = ?";
					c.pst = c.con.prepareStatement(getVoucherCount);
					c.pst.setString(1, id.getText());
					c.pst.execute();
					c.rs = c.pst.getResultSet();
					
					int sVoucherCount = 0;
					while(c.rs.next())
					{
						sVoucherCount++;
					}
					
					String update1 = "UPDATE service SET voucher_count = ? WHERE service_id = ?";
					c.pst = c.con.prepareStatement(update1);
					c.pst.setInt(1, sVoucherCount);
					c.pst.setString(2, id.getText());
					
					c.pst.execute();
					
					JOptionPane.showMessageDialog(null, "ALL vouchers associated with this service have been successfully deleted.");
					
					
					
				} catch (SQLException e) {
					e.printStackTrace();
				}
				
			}
		});
		btnDeleteVouchers.setToolTipText("(NOTE! This will delete ALL vouchers associated with this service.)");
		btnDeleteVouchers.setForeground(new Color(139, 69, 19));
		btnDeleteVouchers.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnDeleteVouchers.setBackground(new Color(154, 205, 50));
		btnDeleteVouchers.setBounds(416, 428, 158, 25);
		contentPane.add(btnDeleteVouchers);
		
		
		
		
		JLabel servicesEdit_lblBg = new JLabel("");
		servicesEdit_lblBg.setIcon(new ImageIcon(main.class.getResource("/img/editServices_bg1.png")));
		servicesEdit_lblBg.setBounds(0, -160, 619, 864);
		contentPane.add(servicesEdit_lblBg);
	}
}
