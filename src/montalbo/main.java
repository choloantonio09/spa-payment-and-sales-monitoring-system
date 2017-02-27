package montalbo;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class main extends JFrame {

	private JPanel contentPane;
	private JTextField main_txtfieldUsername;
	private JTextField main_txtFieldPassword;
	ConnectDB c;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					main frame = new main();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public main() {
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage(main.class.getResource("/img/logo.png")));
		setTitle("Welcome!");
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		setSize(900,600);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		main_txtfieldUsername = new JTextField();
		main_txtfieldUsername.setFont(new Font("Tahoma", Font.PLAIN, 24));
		main_txtfieldUsername.setBounds(30, 143, 388, 55);
		contentPane.add(main_txtfieldUsername);
		main_txtfieldUsername.setColumns(10);
		
		main_txtFieldPassword = new JPasswordField();
		main_txtFieldPassword.setFont(new Font("Tahoma", Font.PLAIN, 24));
		main_txtFieldPassword.setColumns(10);
		main_txtFieldPassword.setBounds(30, 284, 388, 55);
		contentPane.add(main_txtFieldPassword);
		
		JButton main_btnLogin = new JButton("");
		getRootPane().setDefaultButton(main_btnLogin);
		main_btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				//-------------------------------------------ACCOUNT VALIDATION HERE--------------------------------------------
				
				try {
					c = new ConnectDB();
					c.pst = c.con.prepareStatement("SELECT * FROM account WHERE account_username = ?;");
					c.pst.setString(1, main_txtfieldUsername.getText());
					c.pst.execute();
					c.rs = c.pst.getResultSet();
					while(c.rs.next())
					{
						String username = c.rs.getString("account_username");
						String password = c.rs.getString("account_password");
						byte type = c.rs.getByte("account_type");
						String temp_un = main_txtfieldUsername.getText();
						String temp_pw = main_txtFieldPassword.getText();
						
						if(temp_un.equalsIgnoreCase(username) && temp_pw.equalsIgnoreCase(password))
						{
							if(type == 1)
							{
								dispose();
								admin_ui admin = new admin_ui(1,c.rs.getInt("account_id"));
								admin.account_id = c.rs.getObject("account_name");
								admin.account_type = 1;
								admin.setVisible(true);
								break;
							}
							else if(type == 0)
							{
								dispose();
								admin_ui employee = new admin_ui(0,c.rs.getInt("account_id"));
								employee.account_id = c.rs.getObject("account_name");
								employee.account_type = 1;
								employee.setVisible(true);
								break;
							}
						}
						else if(temp_un.equalsIgnoreCase(username) && !temp_pw.equalsIgnoreCase(password))
						{
							JOptionPane.showMessageDialog(null, "You entered a wrong password!");
						}
						else
						{
							JOptionPane.showMessageDialog(null, "User account does not exist!");
						}
					}	
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				
								
				
			}
		});
		main_btnLogin.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				main_btnLogin.setIcon(new ImageIcon(main.class.getResource("/img/main_button_login_2.png")));
				main_btnLogin.revalidate();
			}
			public void mouseExited(MouseEvent e) {
				main_btnLogin.setIcon(new ImageIcon(main.class.getResource("/img/main_button_login.png")));
				main_btnLogin.revalidate();
			}
		});
		main_btnLogin.setIcon(new ImageIcon(main.class.getResource("/img/main_button_login.png")));
		main_btnLogin.setBounds(139, 383, 128, 61);
		contentPane.add(main_btnLogin);
		
		JButton main_btnReset = new JButton("");
		main_btnReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				main_txtfieldUsername.setText(null);
				main_txtFieldPassword.setText(null);
			}
		});
		main_btnReset.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				main_btnReset.setIcon(new ImageIcon(main.class.getResource("/img/main_button_clear_2.png")));
				main_btnReset.revalidate();
			}
			public void mouseExited(MouseEvent e) {
				main_btnReset.setIcon(new ImageIcon(main.class.getResource("/img/main_button_clear.png")));
				main_btnReset.revalidate();
			}
		});
		main_btnReset.setIcon(new ImageIcon(main.class.getResource("/img/main_button_clear.png")));
		main_btnReset.setBounds(290, 383, 130, 60);
		contentPane.add(main_btnReset);
		
		JLabel main_lblBg = new JLabel("");
		main_lblBg.setIcon(new ImageIcon(main.class.getResource("/img/main1_v2.png")));
		main_lblBg.setBounds(0, 0, 900, 600);
		contentPane.add(main_lblBg);
	}

}
