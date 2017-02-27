package montalbo;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Font;

public class servicesDelete extends JFrame {
	ConnectDB c;
	private JPanel contentPane;
	private JTextField servicesDelete_txtfieldID;

	/**
	 * Create the frame.
	 */
	public servicesDelete(Object service_id) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 600, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(10, 97, 564, 10);
		contentPane.add(separator);
		
		servicesDelete_txtfieldID = new JTextField(service_id.toString());
		servicesDelete_txtfieldID.setEditable(false);
		servicesDelete_txtfieldID.setFont(new Font("Tahoma", Font.PLAIN, 30));
		servicesDelete_txtfieldID.setBounds(275, 134, 250, 46);
		contentPane.add(servicesDelete_txtfieldID);
		servicesDelete_txtfieldID.setColumns(10);
		
		JButton servicesDelete_btnDel = new JButton("");
		servicesDelete_btnDel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					c = new ConnectDB();
					
					String iid = servicesDelete_txtfieldID.getText();
					
					String delete = "DELETE FROM service WHERE service_id = ?";
					
					c.pst = c.con.prepareStatement(delete);
					
					c.pst.setString(1, iid);
					c.pst.execute();
					
					dispose();
					JOptionPane.showMessageDialog(null, "DELETE SERVICE: Service successfully deleted!");
					
					
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				
				
			}
		});
		servicesDelete_btnDel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				servicesDelete_btnDel.setIcon(new ImageIcon(main.class.getResource("/img/deleteAccount_button_delete_2.png")));
				servicesDelete_btnDel.revalidate();
			}
			public void mouseExited(MouseEvent e) {
				servicesDelete_btnDel.setIcon(new ImageIcon(main.class.getResource("/img/deleteAccount_button_delete.png")));
				servicesDelete_btnDel.revalidate();
			}
		});
		servicesDelete_btnDel.setIcon(new ImageIcon(main.class.getResource("/img/deleteAccount_button_delete.png")));
		servicesDelete_btnDel.setBounds(275, 198, 120, 40);
		contentPane.add(servicesDelete_btnDel);
		
		JButton servicesDelete_btnClear = new JButton("");
		servicesDelete_btnClear.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				servicesDelete_btnClear.setIcon(new ImageIcon(main.class.getResource("/img/addAccount_button_clear_2.png")));
				servicesDelete_btnClear.revalidate();
			}
			public void mouseExited(MouseEvent e) {
				servicesDelete_btnClear.setIcon(new ImageIcon(main.class.getResource("/img/addAccount_button_clear.png")));
				servicesDelete_btnClear.revalidate();
			}
		});
		servicesDelete_btnClear.setIcon(new ImageIcon(main.class.getResource("/img/addAccount_button_clear.png")));
		servicesDelete_btnClear.setBounds(406, 198, 120, 40);
		contentPane.add(servicesDelete_btnClear);
		
		JLabel servicesDelete_lblBg = new JLabel("");
		servicesDelete_lblBg.setIcon(new ImageIcon(main.class.getResource("/img/deleteService_bg1.png")));
		servicesDelete_lblBg.setBounds(0, -11, 594, 326);
		contentPane.add(servicesDelete_lblBg);
	}

}
