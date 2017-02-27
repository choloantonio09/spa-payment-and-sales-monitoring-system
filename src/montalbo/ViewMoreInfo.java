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

public class ViewMoreInfo extends JFrame {

	private JPanel contentPane;
	private JTextField id;
	private JTextField name;
	private JTextField price;
	private JTextField voucherPrice;
	
	ConnectDB c;
	private JLabel lblNewLabel;
	private JButton btnBack;
	private JTextField category;

	public ViewMoreInfo(Object service_id) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 600, 573);
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
		name.setEditable(false);
		name.setFont(new Font("Tahoma", Font.PLAIN, 14));
		name.setBounds(234, 193, 304, 25);
		contentPane.add(name);
		name.setColumns(10);
		
		price = new JTextField();
		price.setEditable(false);
		price.setFont(new Font("Tahoma", Font.PLAIN, 14));
		price.setColumns(10);
		price.setBounds(136, 387, 107, 25);
		contentPane.add(price);
		
		JTextArea description = new JTextArea();
		description.setEditable(false);
		description.setFont(new Font("Tahoma", Font.PLAIN, 14));
		description.setBounds(53, 263, 487, 113);
		contentPane.add(description);
		
		JLabel label = new JLabel("CATEGORY:");
		label.setForeground(new Color(139, 69, 19));
		label.setFont(new Font("Tahoma", Font.BOLD, 23));
		label.setBounds(53, 423, 144, 40);
		contentPane.add(label);
		
		JLabel label_1 = new JLabel("VOUCHER PRICE:");
		label_1.setForeground(new Color(139, 69, 19));
		label_1.setFont(new Font("Tahoma", Font.BOLD, 22));
		label_1.setBounds(264, 384, 194, 32);
		contentPane.add(label_1);
		
		voucherPrice = new JTextField();
		voucherPrice.setEditable(false);
		voucherPrice.setFont(new Font("Tahoma", Font.PLAIN, 14));
		voucherPrice.setColumns(10);
		voucherPrice.setBounds(468, 387, 106, 25);
		contentPane.add(voucherPrice);
		
		lblNewLabel = new JLabel("SERVICE INFORMATION");
		lblNewLabel.setIcon(new ImageIcon(main.class.getResource("/img/serviceInfoLabel.JPG")));
		lblNewLabel.setBackground(new Color(255, 255, 255));
		lblNewLabel.setBounds(10, 11, 564, 75);
		contentPane.add(lblNewLabel);
		
		btnBack = new JButton("BACK");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnBack.setForeground(new Color(160, 82, 45));
		btnBack.setBackground(new Color(154, 205, 50));
		btnBack.setFont(new Font("Tahoma", Font.BOLD, 18));
		btnBack.setBounds(234, 484, 120, 40);
		contentPane.add(btnBack);
		
		category = new JTextField();
		category.setEditable(false);
		category.setFont(new Font("Tahoma", Font.PLAIN, 14));
		category.setColumns(10);
		category.setBounds(207, 429, 107, 25);
		contentPane.add(category);
		
		
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
				category.setText(c.rs.getString("service_type"));
				price.setText(c.rs.getString("service_price"));
				voucherPrice.setText(c.rs.getString("voucher_price"));
				//generateVoucher.setValue(c.rs.getObject("voucher_count"));
				
				origVoucherCount = (int) c.rs.getObject("voucher_count");
				
			}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
		
		JLabel servicesEdit_lblBg = new JLabel("");
		servicesEdit_lblBg.setIcon(new ImageIcon(main.class.getResource("/img/serviceinfobg.png")));
		servicesEdit_lblBg.setBounds(0, -160, 619, 864);
		contentPane.add(servicesEdit_lblBg);
	}
}
