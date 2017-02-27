package montalbo;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import net.proteanit.sql.DbUtils;

import javax.swing.JLabel;
import javax.swing.ImageIcon;

import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ButtonGroup;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JSeparator;
import javax.swing.JSpinner;
import javax.swing.JRadioButton;

import java.awt.Color;

import javax.swing.JButton;
import javax.swing.SwingConstants;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class accountsAdd extends JFrame {

	private JPanel contentPane;
	private JTextField name;
	private JLabel accountsAdd_lblBg;
	private JRadioButton female;
	private JTextField contactno;
	private JTextField email;
	private JTextField username;
	private JTextField password;
	
	ConnectDB c;

	
	public accountsAdd() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 600, 485);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(10, 99, 564, 10);
		contentPane.add(separator);
		
		name = new JTextField();
		name.setFont(new Font("Tahoma", Font.PLAIN, 14));
		name.setBounds(139, 125, 362, 25);
		contentPane.add(name);
		name.setColumns(10);
		
		JSpinner age = new JSpinner();
		age.setFont(new Font("Tahoma", Font.PLAIN, 14));
		age.setBounds(139, 160, 45, 25);
		contentPane.add(age);
		
		ButtonGroup group = new ButtonGroup();
		
		JRadioButton male = new JRadioButton("Male");
		male.setActionCommand(male.getText());
		male.setHorizontalAlignment(SwingConstants.CENTER);
		male.setOpaque(false);
		male.setFont(new Font("Tahoma", Font.PLAIN, 14));
		male.setBackground(null);
		male.setBounds(162, 198, 74, 23);
		contentPane.add(male);
		
		female = new JRadioButton("Female");
		female.setActionCommand(female.getText());
		female.setHorizontalAlignment(SwingConstants.CENTER);
		female.setOpaque(false);
		female.setFont(new Font("Tahoma", Font.PLAIN, 14));
		female.setBackground((Color) null);
		female.setBounds(260, 198, 67, 23);
		contentPane.add(female);
		
		group.add(male);
		group.add(female);
		
		contactno = new JTextField();
		contactno.setFont(new Font("Tahoma", Font.PLAIN, 14));
		contactno.setColumns(10);
		contactno.setBounds(233, 230, 268, 25);
		contentPane.add(contactno);
		
		email = new JTextField();
		email.setFont(new Font("Tahoma", Font.PLAIN, 14));
		email.setColumns(10);
		email.setBounds(260, 270, 241, 25);
		contentPane.add(email);
		
		username = new JTextField();
		username.setFont(new Font("Tahoma", Font.PLAIN, 14));
		username.setColumns(10);
		username.setBounds(204, 305, 300, 25);
		contentPane.add(username);
		
		password = new JTextField();
		password.setFont(new Font("Tahoma", Font.PLAIN, 14));
		password.setColumns(10);
		password.setBounds(204, 341, 300, 25);
		contentPane.add(password);
		
		JButton submit = new JButton("");
		submit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				//++++++++++++++++++++++++++++++++ ADD ACCOUNTS +++++++++++++++++++++++++++++++++++++++++++++++++
				
				
				try 
				{
					
					
					String iname = name.getText();
					int iage = (int) age.getValue();
					String igender;
					//String igender = group.getSelection().getActionCommand();
					if(male.isSelected())
					{
						igender = "Male";
					}
					else
					{
						igender = "Female";
					}
					String icontactno = contactno.getText();
					String iemail = email.getText();
					String iun = username.getText();
					String ipw = password.getText();
					int itype = 0;
					
					/*VALIDATIONS*/

					String add = "INSERT INTO `montalbo`.`account` (account_name,age,gender,contact_no,email,account_username,account_password,account_type) VALUES (?,?,?,?,?,?,?,?);";
					
					c = new ConnectDB();
/*					c.pst = c.con.prepareCall("{call montalbo.select_all_accounts()}");*/
					c.pst = c.con.prepareStatement(add);
					
					c.pst.setString(1,iname);
					c.pst.setInt(2,iage);
					c.pst.setString(3,igender);
					c.pst.setString(4,icontactno);
					c.pst.setString(5,iemail);
					c.pst.setString(6,iun);
					c.pst.setString(7,ipw);
					c.pst.setInt(8, itype);
					
					
					
					
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
								if(contactno.getText().equals(""))// CHeck if a contact number is empty
								{
									JOptionPane.showMessageDialog(null, "A phone number is required!");
								}
								else
								{
									Pattern phonePattern = Pattern.compile("[^0-9]", Pattern.CASE_INSENSITIVE);
									Matcher phoneMatcher = phonePattern.matcher(contactno.getText());
									if(phoneMatcher.find()) // CHeck if the phone number entered has symbols,whitespaces or letters
									{
										JOptionPane.showMessageDialog(null, "CONTACT NUMBER: LETTERS, WHITESPACES, AND SYMBOLS are NOT allowed! Use '0' number prefix instead of '+63'.");
									}
									else
									{
										if(contactno.getText().length()<7)
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
																	String un ="SELECT account_username FROM account";
																	c.smt = c.con.createStatement();
																	c.smt.executeQuery(un);
																	c.rs = c.smt.getResultSet();
																	int unctr = 0;
																	while(c.rs.next())
																	{
																		if(username.getText().equalsIgnoreCase(c.rs.getString("account_username")))
																		{
																			unctr++;
																		}
																	}
																	System.out.println(unctr);
																	if(unctr!=0)
																	{
																		JOptionPane.showMessageDialog(null, "USERNAME: That username already exists.");

																	}
																	else
																	{
																		c.pst.execute();
																		
																		
																		
																			dispose();
																			JOptionPane.showMessageDialog(null, "ACCOUNT SUCCESSFULLY ADDED!");
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
					

					
					
					
				} catch (SQLException e1) {
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
		submit.setBounds(255, 382, 120, 40);
		contentPane.add(submit);
		
		JButton clear = new JButton("");
		clear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				name.setText(null);
				age.setValue(0);
				group.clearSelection();
				contactno.setText(null);
				email.setText(null);
				username.setText(null);
				password.setText(null);
				
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
		clear.setBounds(385, 382, 120, 40);
		contentPane.add(clear);
		
		accountsAdd_lblBg = new JLabel("");
		accountsAdd_lblBg.setForeground(Color.WHITE);
		accountsAdd_lblBg.setIcon(new ImageIcon(main.class.getResource("/img/addAccount_background.png")));
		accountsAdd_lblBg.setBounds(0, 0, 584, 455);
		contentPane.add(accountsAdd_lblBg);
	}
}
