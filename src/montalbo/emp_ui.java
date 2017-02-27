package montalbo;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;

public class emp_ui extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField emp_txtfieldCode;
	private JTable emp_tbl1;
	private JTextField emp_txtfieldAmount;
	private JTable emp_tbl2_legend;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					emp_ui frame = new emp_ui();
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
	public emp_ui() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1000, 667);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(0, 0, 984, 628);
		contentPane.add(tabbedPane);
		
		JPanel panel_empPayment = new JPanel();
		tabbedPane.addTab("", new ImageIcon(main.class.getResource("/img/navi_payment_1.png")), panel_empPayment, null);
		panel_empPayment.setLayout(null);
		
		
		
		JButton emp_btnLogout = new JButton("");
		emp_btnLogout.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				emp_btnLogout.setIcon(new ImageIcon(main.class.getResource("/img/button_logout_2_final.png")));
				emp_btnLogout.revalidate();
			}
			public void mouseExited(MouseEvent e) {
				emp_btnLogout.setIcon(new ImageIcon(main.class.getResource("/img/button_logout.png")));
				emp_btnLogout.revalidate();
			}
		});
		emp_btnLogout.setBorder(null);
		emp_btnLogout.setIcon(new ImageIcon(main.class.getResource("/img/button_logout.png")));
		emp_btnLogout.setBounds(782, 11, 169, 43);
		panel_empPayment.add(emp_btnLogout);
		
		JLabel emp_lblCode = new JLabel("");
		emp_lblCode.setIcon(new ImageIcon(main.class.getResource("/img/payment_labelcode.png")));
		emp_lblCode.setBounds(51, 78, 63, 21);
		panel_empPayment.add(emp_lblCode);
		
		JButton emp_lblCodeOk = new JButton("");
		emp_lblCodeOk.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				emp_lblCodeOk.setIcon(new ImageIcon(main.class.getResource("/img/payment_codebutton_ok_2.png")));
				emp_lblCodeOk.revalidate();
			}
			public void mouseExited(MouseEvent e) {
				emp_lblCodeOk.setIcon(new ImageIcon(main.class.getResource("/img/payment_codebutton_ok.png")));
				emp_lblCodeOk.revalidate();
			}
		});
		
		emp_txtfieldCode = new JTextField();
		emp_txtfieldCode.setHorizontalAlignment(SwingConstants.RIGHT);
		emp_txtfieldCode.setFont(new Font("Tahoma", Font.PLAIN, 34));
		emp_txtfieldCode.setBounds(51, 110, 515, 65);
		panel_empPayment.add(emp_txtfieldCode);
		emp_txtfieldCode.setColumns(10);
		emp_lblCodeOk.setIcon(new ImageIcon(main.class.getResource("/img/payment_codebutton_ok.png")));
		emp_lblCodeOk.setBounds(574, 110, 67, 65);
		panel_empPayment.add(emp_lblCodeOk);
		
		JLabel emp_lblQty = new JLabel("");
		emp_lblQty.setIcon(new ImageIcon(main.class.getResource("/img/payment_labelqty.png")));
		emp_lblQty.setBounds(51, 190, 117, 21);
		panel_empPayment.add(emp_lblQty);
		
		JSpinner emp_spinner = new JSpinner();
		emp_spinner.setBounds(178, 186, 56, 30);
		panel_empPayment.add(emp_spinner);
		
		JScrollPane emp_scrollpane1 = new JScrollPane();
		emp_scrollpane1.setBounds(51, 222, 590, 198);
		panel_empPayment.add(emp_scrollpane1);
		
		emp_tbl1 = new JTable();
		emp_tbl1.setShowHorizontalLines(false);
		emp_tbl1.setShowGrid(false);
		emp_tbl1.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
			},
			new String[] {
					"Code", "Name", "Quantity", "Price", "Total"
			}
		));
		emp_tbl1.getColumnModel().getColumn(1).setPreferredWidth(100);
		emp_tbl1.getColumnModel().getColumn(1).setMinWidth(100);
		emp_tbl1.getColumnModel().getColumn(2).setPreferredWidth(60);
		emp_tbl1.getColumnModel().getColumn(2).setMinWidth(60);
		emp_scrollpane1.setViewportView(emp_tbl1);
		
		
		JLabel emp_lblAmount = new JLabel("");
		emp_lblAmount.setIcon(new ImageIcon(main.class.getResource("/img/payment_labelamt1.png")));
		emp_lblAmount.setBounds(51, 435, 295, 60);
		panel_empPayment.add(emp_lblAmount);
		
		emp_txtfieldAmount = new JTextField();
		emp_txtfieldAmount.setBounds(396, 435, 245, 60);
		panel_empPayment.add(emp_txtfieldAmount);
		emp_txtfieldAmount.setColumns(10);
		
		JLabel emp_lblServices = new JLabel("");
		emp_lblServices.setIcon(new ImageIcon(main.class.getResource("/img/payment_labelservices1.png")));
		emp_lblServices.setBounds(687, 78, 103, 21);
		panel_empPayment.add(emp_lblServices);
		
		JScrollPane emp_scrollpane2 = new JScrollPane();
		emp_scrollpane2.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		emp_scrollpane2.setBounds(687, 110, 245, 310);
		panel_empPayment.add(emp_scrollpane2);
		
		emp_tbl2_legend = new JTable();
		emp_tbl2_legend.setEnabled(true);
		emp_tbl2_legend.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
			},
			new String[] {
					"Code", "Name", "Description"
			}
		));
		emp_tbl2_legend.getColumnModel().getColumn(1).setMinWidth(75);
		emp_tbl2_legend.getColumnModel().getColumn(2).setPreferredWidth(100);
		emp_tbl2_legend.getColumnModel().getColumn(2).setMinWidth(100);
		emp_scrollpane2.setViewportView(emp_tbl2_legend);
		
		
		JButton emp_btnOk = new JButton("");
		emp_btnOk.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				emp_btnOk.setIcon(new ImageIcon(main.class.getResource("/img/payment_totalbutton_ok_2.png")));
				emp_btnOk.revalidate();
			}
			public void mouseExited(MouseEvent e) {
				emp_btnOk.setIcon(new ImageIcon(main.class.getResource("/img/payment_totalbutton_ok.png")));
				emp_btnOk.revalidate();
			}
		});
		emp_btnOk.setIcon(new ImageIcon(main.class.getResource("/img/payment_totalbutton_ok.png")));
		emp_btnOk.setBounds(687, 435, 73, 60);
		panel_empPayment.add(emp_btnOk);
		
		JButton emp_btnDel = new JButton("");
		emp_btnDel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				emp_btnDel.setIcon(new ImageIcon(main.class.getResource("/img/payment_totalbutton_delete_2.png")));
				emp_btnDel.revalidate();
			}
			public void mouseExited(MouseEvent e) {
				emp_btnDel.setIcon(new ImageIcon(main.class.getResource("/img/payment_totalbutton_delete.png")));
				emp_btnDel.revalidate();
			}
		});
		emp_btnDel.setIcon(new ImageIcon(main.class.getResource("/img/payment_totalbutton_delete.png")));
		emp_btnDel.setBounds(770, 435, 73, 60);
		panel_empPayment.add(emp_btnDel);
		
		JButton emp_btnRes = new JButton("");
		emp_btnRes.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				emp_btnRes.setIcon(new ImageIcon(main.class.getResource("/img/payment_totalbutton_reset_2.png")));
				emp_btnRes.revalidate();
			}
			public void mouseExited(MouseEvent e) {
				emp_btnRes.setIcon(new ImageIcon(main.class.getResource("/img/payment_totalbutton_reset.png")));
				emp_btnRes.revalidate();
			}
		});
		emp_btnRes.setIcon(new ImageIcon(main.class.getResource("/img/payment_totalbutton_reset.png")));
		emp_btnRes.setBounds(859, 435, 73, 60);
		panel_empPayment.add(emp_btnRes);
		
		textField = new JTextField();
		textField.setBounds(51, 110, 515, 65);
		panel_empPayment.add(textField);
		textField.setColumns(10);
		
		
		JLabel emp_lblBg = new JLabel("");
		emp_lblBg.setIcon(new ImageIcon(main.class.getResource("/img/bg_final.png")));
		emp_lblBg.setBounds(0, 0, 979, 548);
		panel_empPayment.add(emp_lblBg);
		
		JLabel label = new JLabel("");
		label.setIcon(new ImageIcon(main.class.getResource("/img/bg_final.png")));
		label.setBounds(0, 0, 984, 628);
		contentPane.add(label);
	}
}
