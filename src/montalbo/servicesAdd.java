package montalbo;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JSpinner;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JButton;
import java.awt.Color;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import javax.swing.SpinnerNumberModel;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class servicesAdd extends JFrame {

	private JPanel contentPane;
	private JTextField name;
	private JTextField originalPrice;
	
	ConnectDB c;
	private JTextField voucherPrice;
	
	public servicesAdd() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 600, 529);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(10, 87, 564, 10);
		contentPane.add(separator);
		
		name = new JTextField();
		name.setFont(new Font("Tahoma", Font.PLAIN, 14));
		name.setBounds(236, 111, 275, 25);
		contentPane.add(name);
		name.setColumns(10);
		
		JTextArea description = new JTextArea();
		description.setFont(new Font("Tahoma", Font.PLAIN, 14));
		description.setBounds(53, 188, 458, 113);
		contentPane.add(description);
		
		originalPrice = new JTextField();
		originalPrice.setFont(new Font("Tahoma", Font.PLAIN, 14));
		originalPrice.setColumns(10);
		originalPrice.setBounds(136, 311, 85, 25);
		contentPane.add(originalPrice);
		
		JSpinner generateVoucher = new JSpinner();
		generateVoucher.setModel(new SpinnerNumberModel(new Integer(0), new Integer(0), null, new Integer(1)));
		generateVoucher.setFont(new Font("Tahoma", Font.PLAIN, 14));
		generateVoucher.setBounds(322, 347, 60, 25);
		contentPane.add(generateVoucher);
		
		JLabel lblCategory = new JLabel("CATEGORY:");
		lblCategory.setForeground(new Color(139, 69, 19));
		lblCategory.setFont(new Font("Tahoma", Font.BOLD, 22));
		lblCategory.setBounds(53, 389, 136, 32);
		contentPane.add(lblCategory);
		
		JComboBox category = new JComboBox();
		category.setModel(new DefaultComboBoxModel(new String[] {"MASSAGE", "SCRUB"}));
		category.setBounds(199, 395, 107, 22);
		contentPane.add(category);
		
		JLabel lblVoucherPrice = new JLabel("VOUCHER PRICE:");
		lblVoucherPrice.setForeground(new Color(139, 69, 19));
		lblVoucherPrice.setFont(new Font("Tahoma", Font.BOLD, 22));
		lblVoucherPrice.setBounds(236, 308, 194, 32);
		contentPane.add(lblVoucherPrice);
		
		voucherPrice = new JTextField();
		voucherPrice.setFont(new Font("Tahoma", Font.PLAIN, 14));
		voucherPrice.setColumns(10);
		voucherPrice.setBounds(440, 312, 85, 25);
		contentPane.add(voucherPrice);
		
		JButton submit = new JButton("");
		submit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					
					c = new ConnectDB();//Database Connection
					int insertCtr = 0;
					
					
					
					//=++++++++++++++++++++++++++++++++++ START OF VALIDATION +++++++++++++++++++++++++++++++=
					
					
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
						String un ="SELECT service_name FROM service";
						c.smt = c.con.createStatement();
						c.smt.executeQuery(un);
						c.rs = c.smt.getResultSet();
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
								Matcher opriceMatcher = opricePattern.matcher(originalPrice.getText());
								if(originalPrice.getText().equals(""))
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
												insertCtr++;
											}
										}
										else
										{
											Pattern vpricePattern = Pattern.compile("[^0-9^\\.]", Pattern.CASE_INSENSITIVE);
											Matcher vpriceMatcher = vpricePattern.matcher(voucherPrice.getText());
											if(voucherPrice.getText().equals(""))
											{
												voucherPrice.setText("0");
												insertCtr++;
											}
											else if(vpriceMatcher.find())
											{
												JOptionPane.showMessageDialog(null, "VOUCHER PRICE: Only numbers are allowed.");

											}
											else
											{ 
												insertCtr++;
											}
										}
									}
									
									
								}
							}
						}
					}
					
					
					
					//================================== END OF VALIDATION ===================================
					if(insertCtr>0)
					{
						String sname = name.getText();
						String stype = (String) category.getSelectedItem();
						String sdescription = description.getText();
						String sprice = originalPrice.getText();
						String svoucherPrice = voucherPrice.getText();
						int gVoucher = (int) generateVoucher.getValue();
						int savailability = 1;
						
						//+++++++++++++++ INSERTING OF SERVICE DATA FIELDS ++++++++++++++
						
						String insert = "INSERT INTO service "
								+ "(service_name, service_type, description, service_price, voucher_price, availability) "
								+ "VALUES (?,?,?,?,?,?);";
						
						
						c.pst = c.con.prepareStatement(insert);
						
						c.pst.setString(1, sname);
						c.pst.setString(2, stype);
						c.pst.setString(3, sdescription);
						c.pst.setString(4, sprice);
						c.pst.setString(5, svoucherPrice);
						c.pst.setInt(6, savailability);
						
						c.pst.execute();
						//++++++++++++++++ GENERATE VOUCHERS ++++++++++++++++++
						
						int currentServiceId = 0; //GET CURRENT SERVICE ID
						String getServiceId = "SELECT service_id FROM service WHERE service_name = ?";
						c.pst = c.con.prepareStatement(getServiceId);
						c.pst.setString(1, sname);
						c.pst.execute();
						c.rs = c.pst.getResultSet();
						while(c.rs.next())
						{
							currentServiceId = c.rs.getInt("service_id");
							//System.out.println(currentServiceId);
						}
						
						String generateVoucherCount = "INSERT INTO voucher (service_id)"
								+ "VALUES (?)";
						c.pst = c.con.prepareStatement(generateVoucherCount);
						c.pst.setInt(1, currentServiceId);
						
						
						int x = gVoucher;
						int y;
						
						for(y = 0; y < x; y++)
						{
							c.pst.execute();
						}
						
						//++++++++++++++++++++++ UPDATE SERVICE INFO (VOUCHER COUNT) ++++++++++++++++++++
						
						String getVoucherCount = "SELECT * FROM voucher WHERE service_id = ?";
						c.pst = c.con.prepareStatement(getVoucherCount);
						c.pst.setInt(1, currentServiceId);
						c.pst.execute();
						c.rs = c.pst.getResultSet();
						
						int sVoucherCount = 0;
						while(c.rs.next())
						{
							sVoucherCount++;
						}
						
						String update = "UPDATE service SET voucher_count = ? WHERE service_id = ?";
						c.pst = c.con.prepareStatement(update);
						c.pst.setInt(1, sVoucherCount);
						c.pst.setInt(2, currentServiceId);
						c.pst.execute();
						
											
						dispose();
						JOptionPane.showMessageDialog(null, "Service successfully added!");
					}
					
					
					
					
					
					
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
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
		submit.setBounds(262, 440, 120, 40);
		contentPane.add(submit);
		
		JButton clear = new JButton("");
		clear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				name.setText(null);
				category.setSelectedItem(null);
				description.setText(null);
				originalPrice.setText(null);
				voucherPrice.setText(null);
				generateVoucher.setValue(null);
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
		clear.setBounds(391, 440, 120, 40);
		contentPane.add(clear);
		
		
		
		JLabel servicesAdd_lblBg = new JLabel("");
		servicesAdd_lblBg.setIcon(new ImageIcon(main.class.getResource("/img/addService_bg1.png")));
		servicesAdd_lblBg.setBounds(0, -51, 584, 586);
		contentPane.add(servicesAdd_lblBg);
	}
}
