package montalbo;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.JSpinner;
import javax.swing.JRadioButton;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class accountsEdit extends JFrame {
	
	ConnectDB c;
	int valid = 0;
	private JPanel contentPane;
	private JTextField name;
	private JSpinner age;
	private JRadioButton male;
	private JRadioButton female;
	private JTextField contact;
	private JTextField email;
	private JTextField username;
	private JTextField password;
	private JTextField id;
	private JButton submit;
	private JButton clear;
	
	int iid;
	String iname;
	int iage;
	String igender;
	String icontactno;
	String iemail;
	String iun;
	String ipw;
	

	public accountsEdit(Object account_id) {
	
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 600, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		

		JSeparator separator = new JSeparator();
		separator.setBounds(10, 88, 564, 10);
		contentPane.add(separator);
		
		id = new JTextField(account_id.toString());
		id.setEditable(false);
		id.setFont(new Font("Tahoma", Font.PLAIN, 18));
		id.setBounds(280, 100, 165, 35);
		contentPane.add(id);
		id.setColumns(10);
		
		/*iid = id.getText();*/
		
		
		name = new JTextField();
		name.setToolTipText("Leave empty for no changes");
		name.setFont(new Font("Tahoma", Font.PLAIN, 14));
		name.setBounds(157, 155, 362, 25);
		contentPane.add(name);
		name.setColumns(10);
		
		age = new JSpinner();
		age.setToolTipText("Leave empty for no changes");
		age.setFont(new Font("Tahoma", Font.PLAIN, 14));
		age.setBounds(157, 193, 45, 25);
		contentPane.add(age);
		
		ButtonGroup group = new ButtonGroup();
		
		male = new JRadioButton("Male");
		male.setToolTipText("Leave empty for no changes");
		male.setOpaque(false);
		male.setHorizontalAlignment(SwingConstants.CENTER);
		male.setFont(new Font("Tahoma", Font.PLAIN, 14));
		male.setBounds(176, 233, 74, 23);
		contentPane.add(male);
		
		female = new JRadioButton("Female");
		female.setToolTipText("Leave empty for no changes");
		female.setOpaque(false);
		female.setHorizontalAlignment(SwingConstants.CENTER);
		female.setFont(new Font("Tahoma", Font.PLAIN, 14));
		female.setBounds(265, 233, 67, 23);
		contentPane.add(female);
		
		group.add(male);
		group.add(female);
		
		contact = new JTextField();
		contact.setToolTipText("Leave empty for no changes");
		contact.setFont(new Font("Tahoma", Font.PLAIN, 14));
		contact.setColumns(10);
		contact.setBounds(232, 263, 287, 25);
		contentPane.add(contact);
		
		email = new JTextField();
		email.setToolTipText("Leave empty for no changes");
		email.setFont(new Font("Tahoma", Font.PLAIN, 14));
		email.setColumns(10);
		email.setBounds(258, 299, 261, 25);
		contentPane.add(email);
		
		username = new JTextField();
		username.setToolTipText("Leave empty for no changes");
		username.setFont(new Font("Tahoma", Font.PLAIN, 14));
		username.setColumns(10);
		username.setBounds(209, 335, 310, 25);
		contentPane.add(username);
		
		password = new JTextField();
		password.setToolTipText("Leave empty for no changes");
		password.setFont(new Font("Tahoma", Font.PLAIN, 14));
		password.setColumns(10);
		password.setBounds(209, 371, 310, 25);
		contentPane.add(password);
		
		
		
		try {
			c = new ConnectDB();
			
			
			
			
			String validate = "SELECT * FROM account where account_id = ?";
			
			c.pst = c.con.prepareStatement(validate);
			
			c.pst.setString(1, id.getText());
			
			c.pst.execute();
			c.rs = c.pst.getResultSet();
			
			while(c.rs.next()){
				
				name.setText(c.rs.getString("account_name"));
				
				age.setValue(c.rs.getInt("age"));
				
				
				contact.setText(c.rs.getString("contact_no"));
				
				email.setText(c.rs.getString("email"));
			
				username.setText(c.rs.getString("account_username"));
		
				password.setText(c.rs.getString("account_password"));
				
				if(c.rs.getString("gender").equals("Male"))
				{
					group.setSelected(male.getModel(), true);
				}
				else
				{
					//System.out.println(c.rs.getString("gender"));
					group.setSelected(female.getModel(), true);
				}
			
				
			}
			
			
		} catch (SQLException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		
		
		
		submit = new JButton("");
		submit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				// ++++++++++ EDIT OF ACCOUNTS++++++++++++++++++++++++++++++++++++++++=
				
				
				String iid = id.getText();
				String iname = name.getText();
				int iage = (int) age.getValue();
				String igender = group.getSelection().getActionCommand();
				String icontactno = contact.getText();
				String iemail = email.getText();
				String iun = username.getText();
				String ipw = password.getText();
				
				try {
					c = new ConnectDB();
					
					String validate = "SELECT * FROM account where account_id = ?";
					
					c.pst = c.con.prepareStatement(validate);
					
					c.pst.setString(1, iid);
					c.pst.execute();
	
					c.rs = c.pst.getResultSet();
					
					while(c.rs.next())
					{
							
							String update = "UPDATE account SET account_name = ?, age = ?, gender = ?, contact_no = ?, email = ?, account_username = ?, account_password = ? WHERE account_id = ?; ";
							c.pst = c.con.prepareStatement(update);
							
							c.pst.setString(1,iname);
							c.pst.setInt(2,iage);
							c.pst.setString(3,igender);
							c.pst.setString(4,icontactno);
							c.pst.setString(5,iemail);
							c.pst.setString(6,iun);
							c.pst.setString(7,ipw);
							c.pst.setString(8, iid);
							
							//______________________________________ START OF VALIDATION ___________________________________
							
							
							
							
							Pattern namePattern = Pattern.compile("[0-9[^\\w]&&[^' ]]", Pattern.CASE_INSENSITIVE);
							Matcher nameMatcher = namePattern.matcher(name.getText());
							
							if(name.getText().equals("")) // Check is employee name is empty
							{
								JOptionPane.showMessageDialog(null, "You must enter the name of the employee!");
							}
							else if(nameMatcher.find())// Check if the are numbers / symbols in employee's name
							{
								JOptionPane.showMessageDialog(null, "EMPLOYEE NAME: NUMBERS and SYMBOLS are NOT allowed!");
							}
							else
							{
								
								if(iage==0)//Check if age is zero
								{
									JOptionPane.showMessageDialog(null, "You must enter the age of the employee!");
								}
								else if(iage < 18)// CHeck if the employee is younger than 18yrs old
								{
									JOptionPane.showMessageDialog(null, "EMPLOYEE AGE: The employee must be 18 years old and above!");
								}
								else
								{
									int genderCtr = 0;
									if(male.isSelected())
									{
										genderCtr++;
									}
									else if(female.isSelected())
									{
										genderCtr++;
									}
									else
									{
										genderCtr = 0;
									}
									
									if(genderCtr == 0)//CHeck if he didnt choose the age of employee
									{
										JOptionPane.showMessageDialog(null, "You must choose the gender of the employee!");
									}
									else
									{
										if(contact.getText().equals(""))// CHeck if a contact number is empty
										{
											JOptionPane.showMessageDialog(null, "A phone number is required!");
										}
										else
										{
											Pattern phonePattern = Pattern.compile("[^0-9]", Pattern.CASE_INSENSITIVE);
											Matcher phoneMatcher = phonePattern.matcher(contact.getText());
											if(phoneMatcher.find()) // CHeck if the phone number entered has symbols,whitespaces or letters
											{
												JOptionPane.showMessageDialog(null, "CONTACT NUMBER: LETTERS, WHITESPACES, AND SYMBOLS are NOT allowed! Use '0' number prefix instead of '+63'.");
											}
											else
											{
												if(contact.getText().length()<7)
												{
													JOptionPane.showMessageDialog(null, "CONTACT NUMBER: Enter a minimum of 7 digit contact number.");

												}
												else
												{
													if(email.getText().equals(""))
													{
														JOptionPane.showMessageDialog(null, "An email is required!");

													}
													else
													{
														Pattern emailPattern = Pattern.compile("[a-zA-Z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}$", Pattern.CASE_INSENSITIVE);
														Matcher emailMatcher = emailPattern.matcher(email.getText());
														if(emailMatcher.find())//CHECK if email is correct
														{
															String emailChk = "SELECT * FROM account where account_id != ?";
															c.pst = c.con.prepareStatement(emailChk);
															c.pst.setString(1, iid);
															c.pst.execute();
															c.rs = c.pst.getResultSet();
															int emailctr = 0;
															while(c.rs.next())
															{
																if(email.getText().equalsIgnoreCase(c.rs.getString("email")))
																{
																	emailctr++;
																}
															}
															
															if(emailctr!=0)
															{
																JOptionPane.showMessageDialog(null, "EMAIL: That email has already been used by a different account.");

															}
															else
															{
																if(username.getText().equals(""))//CHECK if username is empty
																{
																	JOptionPane.showMessageDialog(null, "USERNAME is Required!");
																}
																else
																{
																	Pattern unPattern = Pattern.compile("[ ]", Pattern.CASE_INSENSITIVE);
																	Matcher unMatcher = unPattern.matcher(username.getText());
																	if(unMatcher.find())
																	{
																		JOptionPane.showMessageDialog(null, "USERNAME: Whitespaces are not allowed");

																	}
																	else
																	{
																		if(password.getText().equals(""))//CHECK if username is empty
																		{
																			JOptionPane.showMessageDialog(null, "PASSWORD is Required!");
																		}
																		else
																		{
																			Pattern pwPattern = Pattern.compile("[ ]", Pattern.CASE_INSENSITIVE);
																			Matcher pwMatcher = pwPattern.matcher(password.getText());
																			if(pwMatcher.find())
																			{
																				JOptionPane.showMessageDialog(null, "PASSWORD: Whitespaces are not allowed");

																			}
																			else
																			{
																				String un ="SELECT account_username FROM account where account_id != ?";
																				c.pst = c.con.prepareStatement(un);
																				c.pst.setString(1, iid);
																				c.pst.execute();
																				c.rs = c.pst.getResultSet();
																				int unctr = 0;
																				while(c.rs.next())
																				{
																					if(username.getText().equalsIgnoreCase(c.rs.getString("account_username")))
																					{
																						unctr++;
																					}
																				}
																				
																				if(unctr!=0)
																				{
																					JOptionPane.showMessageDialog(null, "USERNAME: That username already exists.");

																				}
																				else
																				{
																					
																					valid++;
																					/*c.pst.execute();
																					dispose();
																					
																						JOptionPane.showMessageDialog(null, "ACCOUNT SUCCESSFULLY UPDATED!");*/
																				}
																				
																				
																			}
																		}
																	}
																}
															}
															
														}
														else
														{
															JOptionPane.showMessageDialog(null, "EMAIL: Use this format: yourEmail@gmail.com.ph");
														}
													}
												}
												
											}
										}
									}
								}
								
							}
							
							
							
							
							//______________________________________ END OF VALIDATION ___________________________________
							
							if(valid>0)
							{
								String update2 = "UPDATE account SET account_name = ?, age = ?, gender = ?, contact_no = ?, email = ?, account_username = ?, account_password = ? WHERE account_id = ?; ";
								c.pst = c.con.prepareStatement(update2);
								
								c.pst.setString(1,iname);
								c.pst.setInt(2,iage);
								c.pst.setString(3,igender);
								c.pst.setString(4,icontactno);
								c.pst.setString(5,iemail);
								c.pst.setString(6,iun);
								c.pst.setString(7,ipw);
								c.pst.setString(8, iid);
								c.pst.execute();
								dispose();
								
									JOptionPane.showMessageDialog(null, "ACCOUNT SUCCESSFULLY UPDATED!");
							}
							
							
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
				submit.setIcon(new ImageIcon("C:\\Users\\Cholo Miguel\\Desktop\\Desktop Folders\\DOCUMENTS\\Software Engineering\\montalbo\\img\\addAccount_button_submit_2.png"));
				submit.revalidate();
			}
			public void mouseExited(MouseEvent e) {
				submit.setIcon(new ImageIcon("C:\\Users\\Cholo Miguel\\Desktop\\Desktop Folders\\DOCUMENTS\\Software Engineering\\montalbo\\img\\addAccount_button_submit.png"));
				submit.revalidate();
			}
		});
		submit.setIcon(new ImageIcon("C:\\Users\\Cholo Miguel\\Desktop\\Desktop Folders\\DOCUMENTS\\Software Engineering\\montalbo\\img\\addAccount_button_submit.png"));
		submit.setBounds(269, 407, 120, 40);
		contentPane.add(submit);
		
		clear = new JButton("");
		clear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				//id.setText(null);
				name.setText(null);
				age.setValue(0);
				group.clearSelection();
				contact.setText(null);
				email.setText(null);
				username.setText(null);
				password.setText(null);
				
			}
		});
		clear.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				clear.setIcon(new ImageIcon("C:\\Users\\Cholo Miguel\\Desktop\\Desktop Folders\\DOCUMENTS\\Software Engineering\\montalbo\\img\\addAccount_button_clear_2.png"));
				clear.revalidate();
			}
			public void mouseExited(MouseEvent e) {
				clear.setIcon(new ImageIcon("C:\\Users\\Cholo Miguel\\Desktop\\Desktop Folders\\DOCUMENTS\\Software Engineering\\montalbo\\img\\addAccount_button_clear.png"));
				clear.revalidate();
			}
		});
		clear.setIcon(new ImageIcon("C:\\Users\\Cholo Miguel\\Desktop\\Desktop Folders\\DOCUMENTS\\Software Engineering\\montalbo\\img\\addAccount_button_clear.png"));
		clear.setBounds(399, 407, 120, 40);
		contentPane.add(clear);
		
		
		
		JLabel accountsEdit_lblBg = new JLabel("");
		accountsEdit_lblBg.setToolTipText("Leave empty for no changes");
		accountsEdit_lblBg.setFont(new Font("Tahoma", Font.PLAIN, 14));
		accountsEdit_lblBg.setIcon(new ImageIcon("C:\\Users\\Cholo Miguel\\Desktop\\Desktop Folders\\DOCUMENTS\\Software Engineering\\montalbo\\img\\editAccount_background1.png"));
		accountsEdit_lblBg.setBounds(0, -14, 584, 521);
		contentPane.add(accountsEdit_lblBg);
	}

}
