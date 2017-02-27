package montalbo;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import javax.swing.JTabbedPane;

import java.awt.FlowLayout;
import java.awt.Color;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import javax.swing.border.MatteBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;

import java.awt.Font;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.border.LineBorder;
import javax.swing.JButton;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.BevelBorder;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.JSpinner;
import javax.swing.JSeparator;

import net.proteanit.sql.DbUtils;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

import com.toedter.calendar.JDateChooser;
import javax.swing.SpinnerNumberModel;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.Toolkit;

public class admin_ui extends JFrame {
	
	

	private JPanel contentPane;
	private JTable accounts_tbl;
	private JTable services_tbl;
	private JTextField voucherCode;
	private JTable cart;
	private JTextField totalAmount;
	private JTable listService;
	private JTable sales_table;
	private JTextField sales_txtfieldTotal;
	private JPopupMenu popupMenu,popupOrder;
	Object account_id,account_type;
	Object service_id;
	Object yesdelete = 0;
	Object listServiceId,listServiceIdInfo,listId,cartServiceId,transactionIdSelected;
	ConnectDB c;
	DefaultTableModel model;
	private JTextField search;
	private JTable transactions;
	private JButton terminate;
	private JButton billout;
	private JButton backtocart;
	private JComboBox filter;
	private JButton confirm;
	private JButton delete_1;
	private JButton reset;
	
	public admin_ui(int acctype,int accid) {
		
		System.out.println(account_id);
		
		
		setTitle("Mont Albo's Payment and Sales System");
		setIconImage(Toolkit.getDefaultToolkit().getImage(main.class.getResource("/img/logo.png")));
		setResizable(false);

		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		setSize(1000, 667);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JTabbedPane tabAccounts = new JTabbedPane(JTabbedPane.TOP);
		tabAccounts.setBounds(0, 0, 994, 639);
		contentPane.add(tabAccounts);
		ChangeListener changeListener = new ChangeListener() {
		      public void stateChanged(ChangeEvent changeEvent) {
		        JTabbedPane sourceTabbedPane = (JTabbedPane) changeEvent.getSource();
		        int index = sourceTabbedPane.getSelectedIndex();
		        if(index==0)
	            {
	            	ViewTableAccounts();
	            }
	            else if(index==1)
	            {
	            	ViewTableServices();
	            }
	            else if(index==2)
	            {
	            	ViewTableServiceList();
	            	ViewTableCart();
	            	ViewTableTransactions();
	            }
		        //System.out.println("Tab changed to: " + sourceTabbedPane.getSelectedIndex());
		      }
		    };
		
		
		JPanel panelAccounts = new JPanel();
		panelAccounts.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		tabAccounts.addTab("", new ImageIcon(main.class.getResource("/img/navi_accounts.png")), panelAccounts, null);
		panelAccounts.setLayout(null);
		
		JButton accounts_btnLogout = new JButton("");
		accounts_btnLogout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				main login = new main();
				login.setVisible(true);
			}
		});
		accounts_btnLogout.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				accounts_btnLogout.setIcon(new ImageIcon(main.class.getResource("/img/button_logout_2_final.png")));
				accounts_btnLogout.revalidate();
			}
			public void mouseExited(MouseEvent e) {
				accounts_btnLogout.setIcon(new ImageIcon(main.class.getResource("/img/button_logout.png")));
				accounts_btnLogout.revalidate();
			}
		});
		accounts_btnLogout.setIcon(new ImageIcon(main.class.getResource("/img/button_logout.png")));
		accounts_btnLogout.setBorder(null);
		accounts_btnLogout.setBounds(782, 11, 169, 43);
		panelAccounts.add(accounts_btnLogout);
		
		JLabel accounts_lblHeading = new JLabel("");
		accounts_lblHeading.setIcon(new ImageIcon(main.class.getResource("/img/recordofaccts.png")));
		accounts_lblHeading.setBounds(53, 39, 409, 50);
		panelAccounts.add(accounts_lblHeading);
		
		JScrollPane accounts_scrollpane = new JScrollPane();
		accounts_scrollpane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		accounts_scrollpane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		accounts_scrollpane.setBounds(53, 100, 672, 395);
		panelAccounts.add(accounts_scrollpane);
		
	
		setAccounts_tbl(new JTable(){
		    @Override
		    public boolean isCellEditable(int row, int column) {
		        // This is how we disable editing:
		        return false;
		    }
		});
		getAccounts_tbl().setForeground(Color.BLACK);
		getAccounts_tbl().setFont(new Font("Tahoma", Font.PLAIN, 16));
		
		accounts_scrollpane.setViewportView(getAccounts_tbl());
		
		ViewTableAccounts();//function to view records on the table
		
		JButton accounts_btnDel = new JButton("");
		accounts_btnDel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				accountsDelete delete = new accountsDelete(account_id);
				delete.setVisible(true);
				delete.addWindowListener(new WindowAdapter() {
			         public void windowClosed(WindowEvent windowEvent){
			        	 ViewTableAccounts();
			          }        
			       }); 
			}
		});
		accounts_btnDel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				accounts_btnDel.setIcon(new ImageIcon(main.class.getResource("/img/accounts_button_removeaccount_2.png")));
				accounts_btnDel.revalidate();
			}
			public void mouseExited(MouseEvent e) {
				accounts_btnDel.setIcon(new ImageIcon(main.class.getResource("/img/accounts_button_removeaccount.png")));
				accounts_btnDel.revalidate();
			}
		});
		
				JButton accounts_btnAdd = new JButton("");
				accounts_btnAdd.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						
						//++++++++++++++++++++++++++++++++ ADD ACCOUNTS +++++++++++++++++++++++++++++++++++++++++++++++++
						accountsAdd add = new accountsAdd();
						add.setVisible(true);
						
						add.addWindowListener(new WindowAdapter() {
					         public void windowClosed(WindowEvent windowEvent){
					        	 ViewTableAccounts();
					          }        
					       }); 
						/*String add = "INSERT INTO `montalbo`.`account` VALUES (?,?,?,?,?,?,?,?);";*/
						
					}
				});
				accounts_btnAdd.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseEntered(MouseEvent e) {
						accounts_btnAdd.setIcon(new ImageIcon(main.class.getResource("/img/accounts_button_addaccount_2.png")));
						accounts_btnAdd.revalidate();
					}
					public void mouseExited(MouseEvent e) {
						accounts_btnAdd.setIcon(new ImageIcon(main.class.getResource("/img/accounts_button_addaccount.png")));
						accounts_btnAdd.revalidate();
					}
				});
				accounts_btnAdd.setIcon(new ImageIcon(main.class.getResource("/img/accounts_button_addaccount.png")));
				accounts_btnAdd.setBounds(755, 263, 175, 70);
				panelAccounts.add(accounts_btnAdd);
		
		JButton accounts_btnEdit = new JButton("");
		accounts_btnEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				accountsEdit edit = new accountsEdit(account_id);
				edit.setVisible(true);
				edit.addWindowListener(new WindowAdapter() {
			         public void windowClosed(WindowEvent windowEvent){
			        	 ViewTableAccounts();
			          }        
			       }); 
				
				
			}
		});
		accounts_btnEdit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				accounts_btnEdit.setIcon(new ImageIcon(main.class.getResource("/img/accounts_button_editaccount_2.png")));
				accounts_btnEdit.revalidate();
			}
			public void mouseExited(MouseEvent e) {
				accounts_btnEdit.setIcon(new ImageIcon(main.class.getResource("/img/accounts_button_editaccount.png")));
				accounts_btnEdit.revalidate();
			}
		});
		accounts_btnEdit.setIcon(new ImageIcon(main.class.getResource("/img/accounts_button_editaccount.png")));
		accounts_btnEdit.setBounds(755, 344, 175, 70);
		panelAccounts.add(accounts_btnEdit);
		accounts_btnDel.setIcon(new ImageIcon(main.class.getResource("/img/accounts_button_addaccount.png")));
		accounts_btnDel.setBounds(755, 425, 175, 70);
		panelAccounts.add(accounts_btnDel);
		accounts_btnDel.setIcon(new ImageIcon(main.class.getResource("/img/accounts_button_removeaccount.png")));
		accounts_btnDel.setBounds(755, 425, 175, 70);
		panelAccounts.add(accounts_btnDel);
		
		JLabel accounts_lblBg = new JLabel("");
		accounts_lblBg.setIcon(new ImageIcon(main.class.getResource("/img/bg_final.png")));
		accounts_lblBg.setBounds(0, 0, 979, 548);
		panelAccounts.add(accounts_lblBg);
		tabAccounts.setBackgroundAt(0, Color.WHITE);
		
		
		
		//------------------------------------SERVICES AREA-------------------------------------------
		
		
		
		
		JPanel panelServices = new JPanel();
		panelServices.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panelServices.setForeground(Color.WHITE);
		tabAccounts.addTab("", new ImageIcon(main.class.getResource("/img/navi_services.png")), panelServices, null);
		panelServices.setLayout(null);
		
		JButton services_btnLogout = new JButton("");
		services_btnLogout.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				services_btnLogout.setIcon(new ImageIcon(main.class.getResource("/img/button_logout_2_final.png")));
				services_btnLogout.revalidate();
			}
			public void mouseExited(MouseEvent e) {
				services_btnLogout.setIcon(new ImageIcon(main.class.getResource("/img/button_logout.png")));
				services_btnLogout.revalidate();
			}
		});
		services_btnLogout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				main login = new main();
				login.setVisible(true);
			}
		});
		services_btnLogout.setIcon(new ImageIcon(main.class.getResource("/img/button_logout.png")));
		services_btnLogout.setBorder(null);
		services_btnLogout.setBounds(782, 11, 169, 43);
		panelServices.add(services_btnLogout);
		
		JLabel services_lblHeading = new JLabel("");
		services_lblHeading.setIcon(new ImageIcon(main.class.getResource("/img/recordofserv.png")));
		services_lblHeading.setBounds(53, 39, 409, 50);
		panelServices.add(services_lblHeading);
		
		JScrollPane services_scrollpane = new JScrollPane();
		services_scrollpane.setBounds(53, 100, 672, 395);
		panelServices.add(services_scrollpane);
		
		services_tbl = new JTable(){
		    @Override
		    public boolean isCellEditable(int row, int column) {
		        // This is how we disable editing:
		        return false;
		    }
		};
		services_tbl.setForeground(Color.BLACK);
		services_tbl.setFont(new Font("Tahoma", Font.PLAIN, 16));
		services_tbl.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				int row = services_tbl.getSelectedRow();
				service_id = services_tbl.getValueAt(row, 0);
			}
		});
		services_scrollpane.setViewportView(services_tbl);
		ViewTableServices();
		
		JButton services_btnAdd = new JButton("");
		services_btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				servicesAdd add = new servicesAdd();
				add.setVisible(true); 
				add.addWindowListener(new WindowAdapter() {
			         public void windowClosed(WindowEvent windowEvent){
			        	 ViewTableServices();
			          }        
			       }); 
				
			}
		});
		 services_btnAdd.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				 services_btnAdd.setIcon(new ImageIcon(main.class.getResource("/img/services_button_addservice_2.png")));
				 services_btnAdd.revalidate();
			}
			public void mouseExited(MouseEvent e) {
				 services_btnAdd.setIcon(new ImageIcon(main.class.getResource("/img/services_button_addservice.png")));
				 services_btnAdd.revalidate();
			}
		});
		services_btnAdd.setIcon(new ImageIcon(main.class.getResource("/img/services_button_addservice.png")));
		services_btnAdd.setBounds(755, 265, 175, 70);
		panelServices.add(services_btnAdd);
		
		JButton services_btnEdit = new JButton("");
		services_btnEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				servicesEdit edit = new servicesEdit(service_id);
				edit.setVisible(true);
				edit.addWindowListener(new WindowAdapter() {
			         public void windowClosed(WindowEvent windowEvent){
			        	 ViewTableServices();
			          }        
			       }); 
			}
		});
		services_btnEdit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				services_btnEdit.setIcon(new ImageIcon(main.class.getResource("/img/services_button_editservice_2.png")));
				services_btnEdit.revalidate();
			}
			public void mouseExited(MouseEvent e) {
				services_btnEdit.setIcon(new ImageIcon(main.class.getResource("/img/services_button_editservice.png")));
				services_btnEdit.revalidate();
			}
		});
		services_btnEdit.setIcon(new ImageIcon(main.class.getResource("/img/services_button_editservice.png")));
		services_btnEdit.setBounds(755, 344, 175, 70);
		panelServices.add(services_btnEdit);
		
		JButton services_btnDel = new JButton("");
		services_btnDel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				servicesDelete delete = new servicesDelete(service_id);
				delete.setVisible(true);
				delete.addWindowListener(new WindowAdapter() {
			         public void windowClosed(WindowEvent windowEvent){
			        	 ViewTableServices();
			          }        
			       }); 
			}
		});
		services_btnDel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				services_btnDel.setIcon(new ImageIcon(main.class.getResource("/img/services_button_deleteservice_2.png")));
				services_btnDel.revalidate();
			}
			public void mouseExited(MouseEvent e) {
				services_btnDel.setIcon(new ImageIcon(main.class.getResource("/img/services_button_deleteservice.png")));
				services_btnDel.revalidate();
			}
		});
		
		JButton serviceRefresh = new JButton("REFRESH");
		serviceRefresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				ViewTableServices();
			}
		});
		serviceRefresh.setForeground(new Color(139, 69, 19));
		serviceRefresh.setFont(new Font("Tahoma", Font.BOLD, 22));
		serviceRefresh.setBackground(new Color(154, 205, 50));
		serviceRefresh.setBounds(755, 184, 175, 70);
		panelServices.add(serviceRefresh);
		services_btnDel.setIcon(new ImageIcon(main.class.getResource("/img/services_button_deleteservice.png")));
		services_btnDel.setBounds(755, 425, 175, 70);
		panelServices.add(services_btnDel);
		
		JLabel services_lblBg = new JLabel("");
		services_lblBg.setIcon(new ImageIcon(main.class.getResource("/img/bg_final.png")));
		services_lblBg.setBounds(0, 0, 979, 548);
		panelServices.add(services_lblBg);
		
		
		
		//-----------------------------------PAYMENT AREA----------------------------------
		
		
		
		JPanel panelPayment = new JPanel();
		panelPayment.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panelPayment.setForeground(Color.WHITE);
		tabAccounts.addTab("", new ImageIcon(main.class.getResource("/img/navi_payment_1.png")), panelPayment, null);
		panelPayment.setLayout(null);
		
		JButton payment_btnLogout = new JButton("");
		payment_btnLogout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
				main login = new main();
				login.setVisible(true);
			}
		});
		payment_btnLogout.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				payment_btnLogout.setIcon(new ImageIcon(main.class.getResource("/img/button_logout_2_final.png")));
				payment_btnLogout.revalidate();
			}
			public void mouseExited(MouseEvent e) {
				payment_btnLogout.setIcon(new ImageIcon(main.class.getResource("/img/button_logout.png")));
				payment_btnLogout.revalidate();
			}
		});
		payment_btnLogout.setIcon(new ImageIcon(main.class.getResource("/img/button_logout.png")));
		payment_btnLogout.setBorder(null);
		payment_btnLogout.setBounds(782, 11, 169, 43);
		panelPayment.add(payment_btnLogout);
		
		JLabel payment_lblCode = new JLabel("VOUCHER CODE");
		payment_lblCode.setForeground(new Color(139, 69, 19));
		payment_lblCode.setFont(new Font("Tahoma", Font.BOLD, 16));
		payment_lblCode.setIcon(null);
		payment_lblCode.setBounds(10, 37, 136, 21);
		panelPayment.add(payment_lblCode);
		
		voucherCode = new JTextField();
		voucherCode.setHorizontalAlignment(SwingConstants.RIGHT);
		voucherCode.setFont(new Font("Tahoma", Font.PLAIN, 18));
		voucherCode.setBounds(156, 37, 103, 21);
		panelPayment.add(voucherCode);
		voucherCode.setColumns(10);
		
		JButton useVoucher = new JButton("USE");
		useVoucher.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String voucherService = null;
				
				String currentVoucherCode = voucherCode.getText();
				try {
					if(voucherCode.getText().equals(""))
					{
						JOptionPane.showMessageDialog(null, "VOUCHER CODE: You did not enter a voucher code!");
					}
					else
					{
						int voucherCtr = 0;
						String selectVoucher = "SELECT * FROM voucher WHERE voucher_id = ?";
						c.pst = c.con.prepareStatement(selectVoucher);
						c.pst.setString(1, currentVoucherCode);
						c.pst.execute();
						c.rs = c.pst.getResultSet();
						while(c.rs.next())
						{
							
							if(c.rs.getString("voucher_id").equals(currentVoucherCode))
							{
								voucherCtr++;
								voucherService = c.rs.getString("service_id");
								
							}
						}
						if(voucherCtr>0)
						{
							String getService = "SELECT * FROM service WHERE service_id = ?";
							c.pst = c.con.prepareStatement(getService);
							c.pst.setString(1, voucherService);
							c.pst.execute();
							c.rs = c.pst.getResultSet();
							c.rs.next();
							String sname = c.rs.getString("service_name");
							int quantity = 1;
							String order_mode = "VOUCHER";
							float price = c.rs.getFloat("voucher_price");
							float subtotal = c.rs.getFloat("voucher_price");
							
							String sql = "INSERT INTO temp_cart (service_id,service_name,quantity,order_type,price,sub_total) VALUES (?,?,?,?,?,?);";
							c.pst = c.con.prepareStatement(sql);
							c.pst.setString(1, voucherService);
							c.pst.setString(2, sname);
							c.pst.setInt(3, quantity);
							c.pst.setString(4, order_mode);
							c.pst.setFloat(5, price);
							c.pst.setFloat(6, subtotal);
							c.pst.execute();
							ViewTableCart();
							ViewTotalAmountCart();
							
							String deleteVoucher = "DELETE FROM voucher WHERE voucher_id = ?";
							c.pst = c.con.prepareStatement(deleteVoucher);
							c.pst.setString(1, currentVoucherCode);
							c.pst.execute();
							
							JOptionPane.showMessageDialog(null, "VOUCHER CODE: Voucher code used! This cannot be undone.");
							CheckTempCart();
				
						}
						else
						{
							JOptionPane.showMessageDialog(null, "VOUCHER CODE: Voucher code does not exist / has already been used.");
						}
					}
					
					
				} catch (SQLException e1) {
					
					e1.printStackTrace();
				}
				
			}
		});
		useVoucher.setForeground(new Color(139, 69, 19));
		useVoucher.setBackground(new Color(154, 205, 50));
		useVoucher.setFont(new Font("Tahoma", Font.BOLD, 14));
		useVoucher.addMouseListener(new MouseAdapter() {
			
		});
		useVoucher.setIcon(null);
		useVoucher.setBounds(269, 37, 77, 21);
		panelPayment.add(useVoucher);
		
		JSpinner quantity = new JSpinner();
		quantity.setModel(new SpinnerNumberModel(new Integer(1), new Integer(1), null, new Integer(1)));
		quantity.setBounds(116, 241, 56, 21);
		panelPayment.add(quantity);
		

		JScrollPane payment_scrollpane1 = new JScrollPane();
		payment_scrollpane1.setBounds(10, 69, 605, 159);
		panelPayment.add(payment_scrollpane1);
		
		cart = new JTable(){
		    @Override
		    public boolean isCellEditable(int row, int column) {
		        // This is how we disable editing:
		        return false;
		    }
		};
		cart.addMouseListener(new MouseAdapter() {
			
			public void mouseClicked(MouseEvent e) {
				int cartrow = cart.getSelectedRow();
				cartServiceId = cart.getValueAt(cartrow, 0);
				//System.out.println(cartServiceId);
			
				try {
					
					
					String getQuantity = "SELECT * FROM temp_cart WHERE id = ?";
					c.pst = c.con.prepareStatement(getQuantity);
					c.pst.setInt(1, (int) cartServiceId);
					c.pst.execute();
					c.rs = c.pst.getResultSet();
					//System.out.println(c.rs.getInt("quantity"));
					while(c.rs.next())
					{
						int gotQuantity = c.rs.getInt("quantity");
						quantity.setValue(gotQuantity);
						
						
					}
					
					
				} catch (SQLException e1) {
					System.out.println(e1.getMessage());
					e1.printStackTrace();
				}
				
			}
		});
		ViewTableCart();
		
		payment_scrollpane1.setViewportView(cart);
		
		totalAmount = new JTextField();
		totalAmount.setEditable(false);
		totalAmount.setHorizontalAlignment(SwingConstants.RIGHT);
		totalAmount.setFont(new Font("Tahoma", Font.PLAIN, 19));
		totalAmount.setBounds(469, 241, 147, 21);
		panelPayment.add(totalAmount);
		totalAmount.setColumns(10);
		
		ViewTotalAmountCart();
		
		
		
		JLabel payment_lblServices = new JLabel("");
		payment_lblServices.setIcon(new ImageIcon(main.class.getResource("/img/payment_labelservices1.png")));
		payment_lblServices.setBounds(625, 70, 103, 21);
		panelPayment.add(payment_lblServices);
		
		JScrollPane payment_scrollpane2 = new JScrollPane();
		payment_scrollpane2.setBounds(625, 142, 344, 338);
		panelPayment.add(payment_scrollpane2);
		
		listService = new JTable(){
		    @Override
		    public boolean isCellEditable(int row, int column) {
		        // This is how we disable editing:
		        return false;
		    }
		    
		    @Override
	        public JPopupMenu getComponentPopupMenu() {
	            Point p = getMousePosition();
	            // mouse over table and valid row
	            if (p != null && rowAtPoint(p) >= 0) {
	                // condition for showing popup triggered by mouse
	                if (isRowSelected(rowAtPoint(p))) {
	                    return super.getComponentPopupMenu();
	                } else {
	                    return null;
	                }
	            }
	            return super.getComponentPopupMenu();
	        }

		};
		
		popupMenu = new JPopupMenu();
		
		JMenuItem viewMore = new JMenuItem("View More Info");
		viewMore.addActionListener(new ActionListener() {

	            @Override
	            public void actionPerformed(ActionEvent e) 
	            {
	               ViewMoreInfo viewIt = new ViewMoreInfo(listServiceId);
	               viewIt.setVisible(true);
	            }
	        });
		
		popupMenu.add(viewMore);
		listService.setComponentPopupMenu(popupMenu);
		
		listService.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent event) {
				
				int listrow = listService.getSelectedRow();
				
				listServiceId = listService.getValueAt(listrow, 0);
				
				if(event.getClickCount()==2)
				{
						
					
					CheckTempCart();
						
					try {
						
						int serviceCtr = 0;
						String getService = "SELECT * FROM service WHERE service_id = ?";
						c.pst = c.con.prepareStatement(getService);
						c.pst.setInt(1,(int)listServiceId);
						c.pst.execute();
						c.rs = c.pst.getResultSet();
						c.rs.next();
						String sname = c.rs.getString("service_name");
						int quantity = 1;
						String order_mode = "REGULAR";
						float price = c.rs.getFloat("service_price");
						float subtotal = c.rs.getFloat("service_price");
						
						String valid = "SELECT * FROM temp_cart";
						c.pst = c.con.prepareStatement(valid);
						c.pst.execute();
						c.rs = c.pst.getResultSet();
						while(c.rs.next())
						{
							if(c.rs.getObject("service_id").equals(listServiceId)&&c.rs.getObject("order_type").equals("REGULAR"))
							{
								serviceCtr++;
							}
						}
						
						if(serviceCtr>0)
						{
							String sql = "UPDATE temp_cart SET quantity = quantity+1, sub_total = price*quantity WHERE service_id = ? AND order_type = ?";
							c.pst = c.con.prepareStatement(sql);
							c.pst.setInt(1, (int) listServiceId);
							c.pst.setString(2, "REGULAR");
							c.pst.execute();
							ViewTableCart();
							ViewTotalAmountCart();
							
						}
						else
						{
							String sql = "INSERT INTO temp_cart (service_id,service_name,quantity,order_type,price,sub_total) VALUES (?,?,?,?,?,?);";
							c.pst = c.con.prepareStatement(sql);
							c.pst.setInt(1, (int) listServiceId);
							c.pst.setString(2, sname);
							c.pst.setInt(3, quantity);
							c.pst.setString(4, order_mode);
							c.pst.setFloat(5, price);
							c.pst.setFloat(6, subtotal);
							c.pst.execute();
							ViewTableCart();
							ViewTotalAmountCart();
						}	
							
							confirm.setVisible(true);
							reset.setVisible(true);	
							delete_1.setVisible(true);
													  
					} catch (SQLException e) {
						
						e.printStackTrace();
											
					}		
							
				}			
							
			}
		});
		listService.setFont(new Font("Tahoma", Font.BOLD, 11));
		
		ViewTableServiceList();
		
		payment_scrollpane2.setViewportView(listService);
		
		delete_1 = new JButton("");
		delete_1.setVisible(false);
		delete_1.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0)
			{
				
				try {
					
					String deleteCartItem = "DELETE FROM temp_cart WHERE id = ?";
					c.pst = c.con.prepareStatement(deleteCartItem);
					c.pst.setInt(1, (int) cartServiceId);
					
					c.pst.execute();
					CheckTempCart();
					JOptionPane.showMessageDialog(null, "CART: Item successfully removed.");
					ViewTableCart();
					ViewTotalAmountCart();
					
					
				} catch (SQLException e) {
					
					
				}
				
			}
		});
		delete_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				delete_1.setIcon(new ImageIcon(main.class.getResource("/img/payment_totalbutton_delete_2.png")));
				delete_1.revalidate();
			}
			public void mouseExited(MouseEvent e) {
				delete_1.setIcon(new ImageIcon(main.class.getResource("/img/payment_totalbutton_delete.png")));
				delete_1.revalidate();
			}
		});
		delete_1.setIcon(new ImageIcon(main.class.getResource("/img/payment_totalbutton_delete.png")));
		delete_1.setBounds(813, 491, 73, 49);
		panelPayment.add(delete_1);
		
		reset = new JButton("");
		reset.setVisible(false);
		reset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				DeleteCart();
				
				
			}
		});
		reset.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				reset.setIcon(new ImageIcon(main.class.getResource("/img/payment_totalbutton_reset_2.png")));
				reset.revalidate();
			}
			public void mouseExited(MouseEvent e) {
				reset.setIcon(new ImageIcon(main.class.getResource("/img/payment_totalbutton_reset.png")));
				reset.revalidate();
			}
		});
		reset.setIcon(new ImageIcon(main.class.getResource("/img/payment_totalbutton_reset.png")));
		reset.setBounds(896, 491, 73, 49);
		panelPayment.add(reset);
		
		JLabel lblQuantity = new JLabel("QUANTITY");
		lblQuantity.setForeground(new Color(139, 69, 19));
		lblQuantity.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblQuantity.setBounds(10, 241, 96, 21);
		panelPayment.add(lblQuantity);
		
		JLabel lblTotalAmount = new JLabel("TOTAL AMOUNT:");
		lblTotalAmount.setForeground(new Color(139, 69, 19));
		lblTotalAmount.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblTotalAmount.setBounds(312, 239, 147, 21);
		panelPayment.add(lblTotalAmount);
		
		JLabel lblSearch = new JLabel("SEARCH");
		lblSearch.setForeground(new Color(139, 69, 19));
		lblSearch.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblSearch.setBounds(625, 103, 77, 21);
		panelPayment.add(lblSearch);
		
		search = new JTextField();
		search.setHorizontalAlignment(SwingConstants.LEFT);
		search.setFont(new Font("Tahoma", Font.PLAIN, 18));
		search.setColumns(10);
		search.setBounds(705, 103, 181, 21);
		panelPayment.add(search);
		
		JButton submit = new JButton("OK");
		submit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String getSearch = "SELECT service_id,service_name,service_type,service_price FROM  service "
						+ "WHERE ("
						+ "service_id LIKE ? OR "
						+ "service_name LIKE ? OR "
						+ "service_type LIKE ? OR "
						+ "description LIKE ? OR "
						+ "service_price LIKE ?) AND availability != 0;";
				try {
					c.pst = c.con.prepareStatement(getSearch);
					c.pst.setString(1, "%" + search.getText() + "%");
					c.pst.setString(2, "%" + search.getText() + "%");
					c.pst.setString(3, "%" + search.getText() + "%");
					c.pst.setString(4, "%" + search.getText() + "%");
					c.pst.setString(5, "%" + search.getText() + "%");
					c.pst.execute();
					c.rs = c.pst.getResultSet();
					getlistService().setModel(DbUtils.resultSetToTableModel(c.rs));
					
					
				} catch (SQLException e1) {
					
					e1.printStackTrace();
				}
				
			}
		});
		submit.setForeground(new Color(139, 69, 19));
		submit.setFont(new Font("Tahoma", Font.BOLD, 14));
		submit.setBackground(new Color(154, 205, 50));
		submit.setBounds(903, 103, 66, 21);
		panelPayment.add(submit);
		
		
		confirm = new JButton("PURCHASE");
		confirm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				String currentDate = DateTimeFormatter.ISO_DATE.format(LocalDate.now());
				String currentTime = now();
				String paymentStatus = "UNPAID";
				
				CustomerInfo custinfo = new CustomerInfo(account_id, currentDate, currentTime,totalAmount.getText(), paymentStatus);
				custinfo.setVisible(true);
				custinfo.addWindowListener(new WindowAdapter() {
			         public void windowClosed(WindowEvent windowEvent){
			        	 
			  
			        	
				     		try {
				     			
				     			c = new ConnectDB();
					        	 
					        	 
					        	String sql = "SELECT transaction_id FROM customer_transaction ORDER BY transaction_id DESC LIMIT 1";
				     			
				     			int id = 0;
				     			c.pst = c.con.prepareStatement(sql);
				     			c.pst.execute();
				     			c.rs = c.pst.getResultSet();
				     			
				     			while (c.rs.next())
				     			{
				     				
				     				id = c.rs.getInt("transaction_id");
				     			}
				     			
				     			
				     			String cartCtr = "SELECT * FROM temp_cart;";
				     			
				     			c.pst = c.con.prepareStatement(cartCtr);
				     			c.pst.execute();
				     			c.rs = c.pst.getResultSet();
				     			String addItems = "INSERT INTO customer_order VALUES (?,?,?,?,?,?,?,?,?);";
				     			
				     			while(c.rs.next())
				     			{
				     				String ta = totalAmount.getText();
				     				String oid = c.rs.getString("id");
				     				String sid = c.rs.getString("service_id");
				     				String sn = c.rs.getString("service_name");
				     				String qty = c.rs.getString("quantity");
				     				String ot = c.rs.getString("order_type");
				     				String p = c.rs.getString("price");
				     				String st = c.rs.getString("sub_total");
				     				
				     				c.pst = c.con.prepareStatement(addItems);
				     				c.pst.setInt(1, id);
				     				c.pst.setString(2, oid);
				     				c.pst.setString(3, sid);
				     				c.pst.setString(4, sn);
				     				c.pst.setString(5, qty);
				     				c.pst.setString(6, ot);
				     				c.pst.setString(7, p);
				     				c.pst.setString(8, st);
				     				c.pst.setString(9, ta);
				     				c.pst.execute();
				     				
				     			}
				     			
				     			
				     				DeleteCart();

				     			ViewTableTransactions();
					        	ViewTableCart();
				     			
				     			
				     			
				     		} catch (SQLException e1) {
				     			
				     			//JOptionPane.showMessageDialog(null, "CANC");
				     		}
			          }        
			       }); 
				
				
			}
		});
		confirm.setForeground(new Color(139, 69, 19));
		confirm.setVisible(false);
		confirm.setFont(new Font("Tahoma", Font.BOLD, 16));
		confirm.setBackground(new Color(154, 205, 50));
		confirm.setBounds(625, 491, 178, 49);
		panelPayment.add(confirm);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 333, 605, 147);
		panelPayment.add(scrollPane);
		
		backtocart = new JButton("BACK TO CART");
		backtocart.addActionListener(new ActionListener() 
		{
		
			@Override
	        public void actionPerformed(ActionEvent e) 
			{
				ViewTableCart();
				ViewTotalAmountCart();

				backtocart.setVisible(false);
			}
		});
		backtocart.setVisible(false);
		backtocart.setForeground(new Color(139, 69, 19));
		backtocart.setFont(new Font("Tahoma", Font.BOLD, 16));
		backtocart.setBackground(new Color(154, 205, 50));
		backtocart.setBounds(10, 491, 212, 49);
		panelPayment.add(backtocart);
		
		billout = new JButton("BILL-OUT");
		billout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
					Cash cash = new Cash(transactionIdSelected,account_id);
					cash.setVisible(true);
					cash.addWindowListener(new WindowAdapter() {
				         public void windowClosed(WindowEvent windowEvent){
				        	 ViewTableTransactions();
				          }        
				       }); 
					
				}
				
			
		});
		
		
		billout.setVisible(false);
		billout.setForeground(new Color(139, 69, 19));
		billout.setFont(new Font("Tahoma", Font.BOLD, 16));
		billout.setBackground(new Color(154, 205, 50));
		billout.setBounds(469, 491, 147, 49);
		panelPayment.add(billout);
		
		terminate = new JButton("CANCEL TRANSACTION");
		terminate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				int dialog = JOptionPane.showConfirmDialog(null, "Do you really want to cancel this transaction?", "NOTICE!", JOptionPane.YES_NO_OPTION);
				if(dialog == JOptionPane.YES_OPTION)
				{
					try {
						c = new ConnectDB();
						String cancelTransact = "UPDATE customer_transaction SET payment_status = 'CANCELED' WHERE transaction_id = ?";
						
						c.pst = c.con.prepareStatement(cancelTransact);
						c.pst.setObject(1, transactionIdSelected);
						c.pst.execute();
						ViewTableTransactions();
						JOptionPane.showMessageDialog(null, "Transaction Canceled!");
						
					
					} catch (SQLException e1) {
					
						e1.printStackTrace();
					}
				}
				
				
			}
		});
		terminate.setVisible(false);
		terminate.setForeground(new Color(139, 69, 19));
		terminate.setFont(new Font("Tahoma", Font.BOLD, 16));
		terminate.setBackground(new Color(154, 205, 50));
		terminate.setBounds(232, 491, 227, 49);
		panelPayment.add(terminate);
		
		transactions = new JTable(){
		    @Override
		    public boolean isCellEditable(int row, int column) {
		        // This is how we disable editing:
		        return false;
		    }
		    
		    public JPopupMenu getComponentPopupMenu() {
	            Point p = getMousePosition();
	            // mouse over table and valid row
	            if (p != null && rowAtPoint(p) >= 0) {
	                // condition for showing popup triggered by mouse
	                if (isRowSelected(rowAtPoint(p))) {
	                    return super.getComponentPopupMenu();
	                } else {
	                	terminate.setVisible(false);
	                	billout.setVisible(false);
	                    return null;
	                }
	            }
	            return super.getComponentPopupMenu();
	        }
		};
		transactions.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				int transrow = transactions.getSelectedRow();
				transactionIdSelected = transactions.getValueAt(transrow, 0);
				
				try {
					c = new ConnectDB();
					
					String checkIfPaid = "SELECT * FROM customer_transaction WHERE transaction_id = ?";
					c.pst = c.con.prepareStatement(checkIfPaid);
					c.pst.setObject(1, transactionIdSelected);
					c.pst.execute();
					c.rs = c.pst.getResultSet();
					c.rs.next();
					 if(c.rs.getString("payment_status").equals("UNPAID"))
					 {
						 terminate.setVisible(true);
						billout.setVisible(true);
					 }
					 else
					 {
						 terminate.setVisible(false);
						 billout.setVisible(false);
					 }
					
				} catch (SQLException e) {
					// 
					e.printStackTrace();
				}
				
				
				
			}
		});
		
		popupOrder = new JPopupMenu();
				
		JMenuItem viewOrder = new JMenuItem("View Orders");
		viewOrder.addActionListener(new ActionListener() 
		{
		
			@Override
	        public void actionPerformed(ActionEvent e) 
			{
				try
				{
					c = new ConnectDB();
					
					
					String query = "Select transaction_id as 'ID', service_id as 'SERVICE ID',service_name as 'NAME',quantity as 'QTY',order_type as 'ORDER TYPE',price as 'PRICE',sub_total as 'SUB-TOTAL' from `montalbo`.`customer_order` WHERE transaction_id = ?;";
					
					c.pst = c.con.prepareStatement(query);
					c.pst.setObject(1, transactionIdSelected);
					c.pst.execute();
					c.rs = c.pst.getResultSet();
					
						
					getOrderCart().setModel(DbUtils.resultSetToTableModel(c.rs));
					
					String query2 = "SELECT * FROM customer_transaction WHERE transaction_id = ?";
					c.pst = c.con.prepareStatement(query2);
					c.pst.setObject(1, transactionIdSelected);
					c.pst.execute();
					c.rs = c.pst.getResultSet();
					c.rs.next();
					totalAmount.setText(c.rs.getString("amount"));
					
						
					
				}catch (SQLException e1) {
					e1.printStackTrace();
				}
				
				backtocart.setVisible(true);
				
            }
			
			
		});
				
		popupOrder.add(viewOrder);
		transactions.setComponentPopupMenu(popupOrder);
		
		
		
		
		scrollPane.setViewportView(transactions);
		
		JLabel lblTransactions = new JLabel("PENDING TRANSACTIONS:");
		lblTransactions.setForeground(new Color(139, 69, 19));
		lblTransactions.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblTransactions.setBounds(10, 277, 282, 21);
		panelPayment.add(lblTransactions);
		
		JLabel lblFilter = new JLabel("FILTER:");
		lblFilter.setForeground(new Color(139, 69, 19));
		lblFilter.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblFilter.setBounds(442, 301, 96, 21);
		panelPayment.add(lblFilter);
		
		filter = new JComboBox();
		filter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ViewTableTransactions();
			}
		});
		filter.setFont(new Font("Tahoma", Font.PLAIN, 13));
		filter.setModel(new DefaultComboBoxModel(new String[] {"UNPAID", "PAID", "CANCELED", "ALL"}));
		filter.setBounds(519, 301, 103, 22);
		panelPayment.add(filter);
		
		
		
		JButton setQuantity = new JButton("SET");
		setQuantity.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				
				try {
					String checkIfVoucher = "SELECT * FROM temp_cart WHERE id = ?";
					c.pst = c.con.prepareStatement(checkIfVoucher);
					c.pst.setInt(1, (int) cartServiceId);
					c.pst.execute();
					c.rs = c.pst.getResultSet();
					c.rs.next();
					if(c.rs.getString("order_type").equals("VOUCHER"))
					{
						JOptionPane.showMessageDialog(null, "A voucher was used for this item, quantity cannot be changed.");
					}
					else
					{
						String updateQuantity = "UPDATE temp_cart SET quantity = ? WHERE id = ?";
						c.pst = c.con.prepareStatement(updateQuantity);
						c.pst.setInt(1, (int) quantity.getValue()); 
						c.pst.setInt(2, (int) cartServiceId);
					
						c.pst.execute();
						
						String updatePurchase = "UPDATE temp_cart SET sub_total = price*quantity WHERE id = ?";
						c.pst = c.con.prepareStatement(updatePurchase);
						c.pst.setInt(1, (int) cartServiceId);
						
						c.pst.execute();
						
						ViewTableCart();
						ViewTotalAmountCart();
					}
					
					
					
					
				} catch (SQLException e) {
					// 
					e.printStackTrace();
				}
				
			}
		});
		setQuantity.setForeground(new Color(139, 69, 19));
		setQuantity.setFont(new Font("Tahoma", Font.BOLD, 14));
		setQuantity.setBackground(new Color(154, 205, 50));
		setQuantity.setBounds(182, 240, 77, 21);
		panelPayment.add(setQuantity);
		
		
		
		JLabel lblleftClick = new JLabel("(left click + right click to VIEW MORE)");
		lblleftClick.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblleftClick.setBounds(738, 74, 213, 14);
		panelPayment.add(lblleftClick);
		
		JLabel label = new JLabel("(left click + right click to VIEW MORE)");
		label.setFont(new Font("Tahoma", Font.BOLD, 11));
		label.setBounds(10, 309, 213, 14);
		panelPayment.add(label);
		
		
		
		JLabel payment_lblBg = new JLabel("");
		payment_lblBg.setFont(new Font("Tahoma", Font.PLAIN, 16));
		payment_lblBg.setIcon(new ImageIcon(main.class.getResource("/img/bg_final.png")));
		payment_lblBg.setBounds(0, 0, 989, 562);
		panelPayment.add(payment_lblBg);
		
		
		CheckTempCart();
		
		//++++++++++++++++++++++++++++++++++ SALES REPORT ++++++++++++++++++++++++++++++++++++++
		
		
		
		JPanel panelSales = new JPanel();
		tabAccounts.addTab("", new ImageIcon(main.class.getResource("/img/navi_sales_2.png")), panelSales, null);
		panelSales.setLayout(null);
		
		JButton sales_btnLogout = new JButton("");
		sales_btnLogout.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				 sales_btnLogout.setIcon(new ImageIcon(main.class.getResource("/img/button_logout_2_final.png")));
				 sales_btnLogout.revalidate();
			}
			public void mouseExited(MouseEvent e) {
				 sales_btnLogout.setIcon(new ImageIcon(main.class.getResource("/img/button_logout.png")));
				 sales_btnLogout.revalidate();
			}
		});
		
		JDateChooser dateChooser = new JDateChooser();
		dateChooser.setBounds(818, 252, 127, 32);
		panelSales.add(dateChooser);
		
		JDateChooser dateChooser_1 = new JDateChooser();
		dateChooser_1.setBounds(818, 322, 127, 32);
		panelSales.add(dateChooser_1);
		sales_btnLogout.setIcon(new ImageIcon(main.class.getResource("/img/button_logout.png")));
		sales_btnLogout.setBorder(null);
		sales_btnLogout.setBounds(782, 11, 169, 43);
		panelSales.add(sales_btnLogout);
		
		JScrollPane sales_scrollpane = new JScrollPane();
		sales_scrollpane.setBounds(53, 53, 613, 371);
		panelSales.add(sales_scrollpane);
		
		sales_table = new JTable(){
		    @Override
		    public boolean isCellEditable(int row, int column) {
		        // This is how we disable editing:
		        return false;
		    }
		};
		sales_table.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null, null, null, null, null},
				{null, null, null, null, null, null},
				{null, null, null, null, null, null},
				{null, null, null, null, null, null},
				{null, null, null, null, null, null},
				{null, null, null, null, null, null},
				{null, null, null, null, null, null},
				{null, null, null, null, null, null},
				{null, null, null, null, null, null},
				{null, null, null, null, null, null},
				{null, null, null, null, null, null},
				{null, null, null, null, null, null},
				{null, null, null, null, null, null},
				{null, null, null, null, null, null},
				{null, null, null, null, null, null},
				{null, null, null, null, null, null},
				{null, null, null, null, null, null},
				{null, null, null, null, null, null},
				{null, null, null, null, null, null},
				{null, null, null, null, null, null},
				{null, null, null, null, null, null},
				{null, null, null, null, null, null},
				{null, null, null, null, null, null},
				{null, null, null, null, null, null},
				{null, null, null, null, null, null},
			},
			new String[] {
				"Transaction No.", "Date", "Time", "Type of Payment", "Amount", "Emp.-in-charge"
			}
		));
		sales_table.getColumnModel().getColumn(0).setPreferredWidth(15);
		sales_table.getColumnModel().getColumn(0).setMinWidth(100);
		sales_table.getColumnModel().getColumn(2).setMinWidth(95);
		sales_table.getColumnModel().getColumn(3).setMinWidth(105);
		sales_table.getColumnModel().getColumn(5).setMinWidth(120);
		sales_scrollpane.setViewportView(sales_table);
		
		JLabel sales_lblTotal = new JLabel("");
		sales_lblTotal.setIcon(new ImageIcon(main.class.getResource("/img/sales_lbltotal.png")));
		sales_lblTotal.setBounds(53, 435, 316, 60);
		panelSales.add(sales_lblTotal);
		
		
		
		sales_txtfieldTotal = new JTextField();
		sales_txtfieldTotal.setFont(new Font("Tahoma", Font.PLAIN, 34));
		sales_txtfieldTotal.setHorizontalAlignment(SwingConstants.RIGHT);
		sales_txtfieldTotal.setBounds(348, 435, 316, 60);
		panelSales.add(sales_txtfieldTotal);
		sales_txtfieldTotal.setColumns(10);
		
		JLabel sales_lblSort = new JLabel("");
		sales_lblSort.setIcon(new ImageIcon(main.class.getResource("/img/payment_labelsort.png")));
		sales_lblSort.setBounds(795, 152, 93, 32);
		panelSales.add(sales_lblSort);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(714, 195, 231, 10);
		panelSales.add(separator);
		
		JLabel sales_lblFrom = new JLabel("");
		sales_lblFrom.setIcon(new ImageIcon(main.class.getResource("/img/payment_labelFrom.png")));
		sales_lblFrom.setBounds(714, 216, 93, 25);
		panelSales.add(sales_lblFrom);
		
		JLabel sales_lblTo = new JLabel("");
		sales_lblTo.setIcon(new ImageIcon(main.class.getResource("/img/payment_labelto.png")));
		sales_lblTo.setBounds(714, 292, 93, 25);
		panelSales.add(sales_lblTo);
		
		JButton sales_btnOk = new JButton("");
		sales_btnOk.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseEntered(MouseEvent e) {
					sales_btnOk.setIcon(new ImageIcon(main.class.getResource("/img/sales_buttonOk_2.png")));
					sales_btnOk.revalidate();
				}
				public void mouseExited(MouseEvent e) {
					sales_btnOk.setIcon(new ImageIcon(main.class.getResource("/img/sales_buttonOk.png")));
					sales_btnOk.revalidate();
				}
			});
		sales_btnOk.setIcon(new ImageIcon(main.class.getResource("/img/sales_buttonOk.png")));
		sales_btnOk.setBounds(855, 384, 90, 40);
		panelSales.add(sales_btnOk);
		
		JLabel sales_lblBg = new JLabel("");
		sales_lblBg.setIcon(new ImageIcon(main.class.getResource("/img/bg_final.png")));
		sales_lblBg.setBounds(0, 0, 979, 548);
		panelSales.add(sales_lblBg);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(main.class.getResource("/img/bg_final.png")));
		lblNewLabel.setBounds(0, 0, 984, 628);
		contentPane.add(lblNewLabel);
		
		tabAccounts.addChangeListener(changeListener);
		
		
		if(acctype == 0)
		{
			panelAccounts.setVisible(false);
			panelServices.setVisible(false);
			panelSales.setVisible(false);
		}
		
	}
	
	public JTable getAccounts_tbl() {
		return accounts_tbl;
	}

	public void setAccounts_tbl(JTable accounts_tbl) {
		this.accounts_tbl = accounts_tbl;
		accounts_tbl.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				int row = accounts_tbl.getSelectedRow();
				account_id = accounts_tbl.getValueAt(row, 0);
			}
		});
	}
	
	
	public void ViewTableAccounts(){
		// -----------------------INSERT VALUES TO JTABLE------------------------------
		try
		{
			c = new ConnectDB();
			c.smt = c.con.createStatement();
			
			String query = "Select account_id as 'ACCOUNT ID', account_name as 'ACCOUNT NAME', account_username as 'USERNAME', age as 'AGE', contact_no as 'CONTACT NO', email as 'EMAIL' from `montalbo`.`account` where account_id > 1;";
			
			c.smt.executeQuery(query);
			c.rs = c.smt.getResultSet();
				
				getAccounts_tbl().setModel(DbUtils.resultSetToTableModel(c.rs));
				
			
		}catch (SQLException e1) {
			e1.printStackTrace();
		}
	}
	
	public JTable getServices_tbl() {
		return services_tbl;
	}
	
	public void ViewTableServices(){
		// -----------------------INSERT VALUES TO JTABLE SERVICES------------------------------
		try
		{
			c = new ConnectDB();
			c.smt = c.con.createStatement();
			
			String query = "Select service_id as 'ID', service_name as 'NAME', service_type as 'TYPE', description as 'DESCRIPTION', service_price as 'O_PRICE', voucher_price as 'V_PRICE', availability as 'STATUS', voucher_count as 'VOUCHER CTR' from `montalbo`.`service`;";
			
			c.smt.executeQuery(query);
			c.rs = c.smt.getResultSet();
				
				getServices_tbl().setModel(DbUtils.resultSetToTableModel(c.rs));
				services_tbl.getColumnModel().getColumn(0).setPreferredWidth(7);
				services_tbl.getColumnModel().getColumn(1).setPreferredWidth(100);
				services_tbl.getColumnModel().getColumn(2).setPreferredWidth(50);
				services_tbl.getColumnModel().getColumn(3).setPreferredWidth(80);
				services_tbl.getColumnModel().getColumn(4).setPreferredWidth(15);
				services_tbl.getColumnModel().getColumn(5).setPreferredWidth(15);
				services_tbl.getColumnModel().getColumn(6).setPreferredWidth(10);
				services_tbl.getColumnModel().getColumn(7).setPreferredWidth(10);
				
				
			
		}catch (SQLException e1) {
			e1.printStackTrace();
		}
	}
	
	public JTable getlistService() {
		return listService;
	}
	
	public void ViewTableServiceList(){
		// -----------------------INSERT VALUES TO JTABLE SERVICES IN PAYMENT AREA------------------------------
		try
		{
			c = new ConnectDB();
			c.smt = c.con.createStatement();
			
			String query = "Select service_id,service_name,service_type,service_price from `montalbo`.`service` WHERE availability = 1;";
			
			c.smt.executeQuery(query);
			c.rs = c.smt.getResultSet();
				
				getlistService().setModel(DbUtils.resultSetToTableModel(c.rs));
				
			
		}catch (SQLException e1) {
			e1.printStackTrace();
		}
	}
	
	public JTable getOrderCart() {
		return cart;
	}
	
	public JTable getTransactions() {
		return transactions;
	}
	
	public void ViewTableTransactions(){
		// -----------------------INSERT VALUES TO JTABLE TRANSACTIONS------------------------------
		try
		{
			c = new ConnectDB();
			c.smt = c.con.createStatement();
			String query = null;
			if(filter.getSelectedItem().equals("UNPAID"))
			{

				query = "Select transaction_id as 'T.ID', account_id as 'EMPLOYEE IN-CHARGE', customer_name as 'CUSTOMER', contact_no as 'CONTACT NO.', date as 'DATE', time as 'TIME', amount as 'TOTAL', payment_status as 'STATUS' from customer_transaction where payment_status = 'UNPAID';";
			}
			else if(filter.getSelectedItem().equals("PAID"))
			{
				query = "Select transaction_id as 'T.ID', account_id as 'EMPLOYEE IN-CHARGE', customer_name as 'CUSTOMER', contact_no as 'CONTACT NO.', date as 'DATE', time as 'TIME', amount as 'TOTAL', payment_status as 'STATUS' from customer_transaction where payment_status = 'PAID';";
			
			}
			else if(filter.getSelectedItem().equals("CANCELED"))
			{

				query = "Select transaction_id as 'T.ID', account_id as 'EMPLOYEE IN-CHARGE', customer_name as 'CUSTOMER', contact_no as 'CONTACT NO.', date as 'DATE', time as 'TIME', amount as 'TOTAL', payment_status as 'STATUS' from customer_transaction where payment_status = 'CANCELED';";
				
			}
			else
			{
				query = "Select transaction_id as 'T.ID', account_id as 'EMPLOYEE IN-CHARGE', customer_name as 'CUSTOMER', contact_no as 'CONTACT NO.', date as 'DATE', time as 'TIME', amount as 'TOTAL', payment_status as 'STATUS' from customer_transaction;";
			}
			
			c.smt.executeQuery(query);
			c.rs = c.smt.getResultSet();
				
				getTransactions().setModel(DbUtils.resultSetToTableModel(c.rs));
				transactions.getColumnModel().getColumn(0).setPreferredWidth(4);
				transactions.getColumnModel().getColumn(1).setPreferredWidth(40);
				transactions.getColumnModel().getColumn(2).setPreferredWidth(40);
				transactions.getColumnModel().getColumn(3).setPreferredWidth(40);
				transactions.getColumnModel().getColumn(4).setPreferredWidth(30);
				transactions.getColumnModel().getColumn(5).setPreferredWidth(30);
				transactions.getColumnModel().getColumn(6).setPreferredWidth(30);
				transactions.getColumnModel().getColumn(7).setPreferredWidth(30);
			transactionIdSelected = null;
			billout.setVisible(false);
			terminate.setVisible(false);

		}catch (SQLException e1) {
			e1.printStackTrace();
		}
	}
	
	public void ViewTableCart(){
		// -----------------------INSERT VALUES TO JTABLE ORDER CART------------------------------
		try
		{
			c = new ConnectDB();
			c.smt = c.con.createStatement();
			
			String query = "Select id as 'OID', service_id as 'S.ID',service_name as 'NAME',quantity as 'QTY',order_type as 'ORDER TYPE',price as 'PRICE',sub_total as 'SUB-TOTAL'  from `montalbo`.`temp_cart`;";
			
			c.smt.executeQuery(query);
			c.rs = c.smt.getResultSet();
				
				getOrderCart().setModel(DbUtils.resultSetToTableModel(c.rs));
				cart.getColumnModel().getColumn(0).setPreferredWidth(4);
				cart.getColumnModel().getColumn(1).setPreferredWidth(4);
				cart.getColumnModel().getColumn(2).setPreferredWidth(190);
				cart.getColumnModel().getColumn(3).setPreferredWidth(4);
				cart.getColumnModel().getColumn(4).setPreferredWidth(50);
				cart.getColumnModel().getColumn(5).setPreferredWidth(20);
				cart.getColumnModel().getColumn(6).setPreferredWidth(20);
				
				
				
				
			
		}catch (SQLException e1) {
			e1.printStackTrace();
		}
	}
	
	public void ViewTotalAmountCart()
	{
		String getTotalAmount = "SELECT SUM(sub_total) FROM temp_cart WHERE order_type = 'REGULAR'";
		try {
			c.pst = c.con.prepareStatement(getTotalAmount);
			c.pst.execute();
			c.rs = c.pst.getResultSet();
			c.rs.next();
			
			String sum = c.rs.getString(1);
			if (sum == null)
			{
				totalAmount.setText("0.00");
			}
			else
			{
				totalAmount.setText(sum);
			}
			
			
			
		} catch (SQLException e1) {
			
			e1.printStackTrace();
		}
	}
	
	public static String now() {//get current time
		String DATE_FORMAT_NOW = "yyyy-MM-dd HH:mm:ss";
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
		return sdf.format(cal.getTime());

		}
	public void DeleteCart(){
		String deleteCart = "DELETE FROM temp_cart";
		try {
			confirm.setVisible(false);
    		reset.setVisible(false);
    		delete_1.setVisible(false);
			c.pst = c.con.prepareStatement(deleteCart);
			c.pst.execute();
			JOptionPane.showMessageDialog(null, "CART: Reset successfully applied.");
			totalAmount.setText("0.00");
			ViewTableCart();
			CheckTempCart();
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
	}
	public void CheckTempCart()
	{
		try {
			c = new ConnectDB();
			String checkCartEmpty = "SELECT * FROM temp_cart";
        	c.pst = c.con.prepareStatement(checkCartEmpty);
        	int x = 0;
        	c.pst.execute();
        	c.rs = c.pst.getResultSet();
        	while(c.rs.next())
        	{
        		x++;
        	}
        	if(x==0)
        	{
        		confirm.setVisible(false);
        		reset.setVisible(false);
        		delete_1.setVisible(false);
        		
        	}
        	else
        	{
        		confirm.setVisible(true);
        		reset.setVisible(true);
        		delete_1.setVisible(true);
        		
        	}
			
		} catch (SQLException e) {
			// 
			e.printStackTrace();
		}
	}
}
