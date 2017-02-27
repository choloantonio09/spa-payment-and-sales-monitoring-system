package montalbo;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;

public class accountsDelete extends JFrame {

	private JPanel contentPane;
	private JTextField accountsEdit_txtfieldID;
	
	ConnectDB c;

	public accountsDelete(Object account_id) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 600, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(10, 97, 564, 10);
		contentPane.add(separator);
		
		accountsEdit_txtfieldID = new JTextField(account_id.toString());
		accountsEdit_txtfieldID.setEditable(false);
		accountsEdit_txtfieldID.setFont(new Font("Tahoma", Font.PLAIN, 30));
		accountsEdit_txtfieldID.setBounds(307, 134, 250, 46);
		contentPane.add(accountsEdit_txtfieldID);
		accountsEdit_txtfieldID.setColumns(10);
		
		JButton accountsEdit_btnDel = new JButton("");
		accountsEdit_btnDel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					c = new ConnectDB();
					
					String iid = accountsEdit_txtfieldID.getText();
					
					String delete = "DELETE FROM account WHERE account_id = ?";
					
					c.pst = c.con.prepareStatement(delete);
					
					c.pst.setString(1, iid);
					c.pst.execute();
					dispose();
					JOptionPane.showMessageDialog(null, "ACCOUNT SUCCESSFULLY DELETED!");

					
					
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				
				
			}
		});
		accountsEdit_btnDel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				accountsEdit_btnDel.setIcon(new ImageIcon(main.class.getResource("/img/deleteAccount_button_delete_2.png")));
				accountsEdit_btnDel.revalidate();
			}
			public void mouseExited(MouseEvent e) {
				accountsEdit_btnDel.setIcon(new ImageIcon(main.class.getResource("/img/deleteAccount_button_delete.png")));
				accountsEdit_btnDel.revalidate();
			}
		});
		
		JButton btnNewButton = new JButton("CANCEL");
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 21));
		btnNewButton.setForeground(new Color(139, 69, 19));
		btnNewButton.setBackground(new Color(154, 205, 50));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				dispose();
			}
		});
		btnNewButton.setBounds(437, 198, 120, 40);
		contentPane.add(btnNewButton);
		accountsEdit_btnDel.setIcon(new ImageIcon(main.class.getResource("/img/deleteAccount_button_delete.png")));
		accountsEdit_btnDel.setBounds(307, 198, 120, 40);
		contentPane.add(accountsEdit_btnDel);
		
		JLabel accountsDelete_lblBg = new JLabel("");
		accountsDelete_lblBg.setIcon(new ImageIcon(main.class.getResource("/img/deleteAccount_bg.png")));
		accountsDelete_lblBg.setBounds(0, 0, 584, 300);
		contentPane.add(accountsDelete_lblBg);
	}
}
