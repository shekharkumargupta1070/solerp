package com.sol.erp;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.plaf.metal.MetalLookAndFeel;
//import javax.swing.plaf.nimbus.NimbusLookAndFeel;
import javax.swing.plaf.synth.SynthLookAndFeel;
import javax.swing.table.DefaultTableModel;

import com.sol.erp.constants.ApplicationConstants;
import com.sol.erp.dto.LoginDTO;
import com.sol.erp.ui.custom.SolTableModel;
import com.sol.erp.util.DBConnectionUtil;
import com.sol.erp.util.SessionUtil;

public class Login implements ActionListener, FocusListener, MouseListener {

	EmpLeaveTB leavetb = new EmpLeaveTB();
	Connection con;
	String idstr = "";
	String branchstr = "";
	String deptstr = "";

	//NimbusLookAndFeel nimbusLF = new NimbusLookAndFeel();
	MetalLookAndFeel metalLF = new MetalLookAndFeel();
	SynthLookAndFeel windowsLF = new SynthLookAndFeel();

	SuperAdminAccess sa = new SuperAdminAccess();
	JDesktopPane desk = new JDesktopPane();
	JFrame f = new JFrame();
	JInternalFrame inf = new JInternalFrame("Login Window");

	Icon ic = new ImageIcon(ClassLoader.getSystemResource(ApplicationConstants.IMAGE_PATH + "toplogo.gif"));
	JLabel imagelabel = new JLabel(ic);

	JLabel l1 = new JLabel("User Name");
	JLabel l2 = new JLabel("Password");
	JLabel l3 = new JLabel("User Type");
	JLabel l4 = new JLabel("Department");
	JLabel l5 = new JLabel("Post");

	JTextField tf1 = new JTextField(16);
	JPasswordField tf2 = new JPasswordField(16);
	JTextField tf3 = new JTextField(16);
	JTextField tf4 = new JTextField(16);
	JComboBox tf5 = new JComboBox();

	JButton b1 = new JButton("Login",
			new ImageIcon(ClassLoader.getSystemResource(ApplicationConstants.IMAGE_PATH + "sec.gif")));
	JButton b2 = new JButton("Close",
			new ImageIcon(ClassLoader.getSystemResource(ApplicationConstants.IMAGE_PATH + "close.gif")));

	JRadioButton rb1 = new JRadioButton("Java", true);
	JRadioButton rb2 = new JRadioButton("Windows", false);
	JRadioButton rb3 = new JRadioButton("Steel", false);
	ButtonGroup grp = new ButtonGroup();

	Border bor1 = BorderFactory.createTitledBorder("User Authentication");

	JPanel p1 = new JPanel();
	JPanel butpanel = new JPanel();
	JPanel titlepanel = new JPanel();
	JLabel title = new JLabel(
			new ImageIcon(ClassLoader.getSystemResource(ApplicationConstants.IMAGE_PATH + "login.gif")), 2);

	Font fo = new Font("Tahoma", Font.BOLD, 12);

	Toolkit tk = Toolkit.getDefaultToolkit();

	Dimension ss = tk.getScreenSize();
	Dimension fs = f.getSize();

	public void px() {
		Container c = inf.getContentPane();
		Container c1 = f.getContentPane();
		c.setLayout(new BorderLayout());
		p1.setLayout(new GridLayout(5, 2));
		p1.setSize(200, 80);
		titlepanel.setLayout(new BorderLayout());
		p1.setBorder(bor1);

		grp.add(rb1);
		grp.add(rb2);
		grp.add(rb3);

		/*
		 * labelProjectNumber.setBounds(50, 80, 70, 20); textFieldProjectNumber.setBounds(150, 80, 210, 20);
		 * 
		 * labelProjectName.setBounds(50, 104, 70, 20); textFieldProjectName.setBounds(150, 104, 210, 20);
		 * 
		 * labelClientName.setBounds(50, 128, 70, 20); textFieldClientName.setBounds(150, 128, 209, 19);
		 * 
		 * labelSerialNumber.setBounds(50, 152, 70, 20); textFieldSerialNumber.setBounds(150, 152, 209, 19);
		 * 
		 * labelItemCode.setBounds(50, 176, 70, 20); comboBoxItemCode.setBounds(150, 176, 209, 23);
		 */
		tf5.setEnabled(false);

		p1.add(l1);
		p1.add(tf1);
		tf1.addActionListener(this);
		tf1.addFocusListener(this);
		p1.add(l2);
		p1.add(tf2);
		tf2.addActionListener(this);
		p1.add(l3);
		p1.add(tf3);
		tf3.setEditable(false);
		p1.add(l4);
		p1.add(tf4);
		tf4.setEditable(false);
		p1.add(l5);
		p1.add(tf5);
		tf5.setEditable(false);

		tf1.setFont(fo);
		tf2.setFont(fo);
		tf3.setFont(fo);
		tf4.setFont(fo);
		tf5.setFont(fo);

		butpanel.add(rb1);
		rb1.addActionListener(this);
		// butpanel.add(radioButtonTrainee);
		rb2.addActionListener(this);
		butpanel.add(rb3);
		rb3.addActionListener(this);

		butpanel.add(b1);
		b1.addActionListener(this);
		b1.setMnemonic('L');
		butpanel.add(b2);
		b2.addActionListener(this);
		b2.setMnemonic('C');

		p1.setPreferredSize(new Dimension(440, 40));
		titlepanel.setBackground(Color.white);
		title.setFont(fo);
		titlepanel.add(title, "Center");

		c.add(imagelabel, "North");
		c.add(titlepanel, "West");
		c.add(p1, "Center");
		c.add(butpanel, "South");

		desk.setBackground(Color.darkGray);
		c1.add(desk, "Center");

		desk.add(inf);
		desk.moveToFront(inf);
		desk.setSize(tk.getScreenSize());

		f.setSize(tk.getScreenSize());
		//f.setUndecorated(true);

		inf.setLocation((ss.width - fs.width) / 4, (ss.height - fs.height) / 4);
		inf.setSize(600, 320);

		loadDefaultTheame();

		f.setVisible(true);
		inf.setVisible(true);

		tf1.requestFocus();
	}

	String head[] = { "Select", "ID", "Co Name" };
	Object data[][] = {};

	JInternalFrame f1 = new JInternalFrame("Access Details");
	JLabel actionLabel = new JLabel("Company");

	DefaultTableModel model = new DefaultTableModel(data, head);
	JTable tb = new JTable(model) {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public Class<? extends Object> getColumnClass(int column) {
			return getValueAt(0, column).getClass();
		}
	};

	JScrollPane sp = new JScrollPane(tb);

	JButton backbut = new JButton("Back to Login");
	JButton closebut = new JButton("close");

	JPanel centerpanel = new JPanel();
	JPanel northpanel = new JPanel();
	JPanel southpanel = new JPanel();

	
	public void px1() {
		Container c = f1.getContentPane();
		c.setLayout(new BorderLayout());
		centerpanel.setLayout(new GridLayout(4, 2, 2, 2));
		northpanel.setLayout(new FlowLayout(0));
		southpanel.setLayout(new FlowLayout(2));

		SolTableModel.decorateTable(tb);
		tb.setIntercellSpacing(new Dimension(1, 1));
		tb.setSelectionBackground(Color.pink);
		tb.setSelectionForeground(Color.darkGray);
		tb.getColumnModel().getColumn(2).setPreferredWidth(420);

		northpanel.add(actionLabel);

		southpanel.add(backbut);
		southpanel.add(closebut);

		c.add(northpanel, "North");
		c.add(sp, "Center");
		c.add(southpanel, "South");

		tb.addMouseListener(this);

		backbut.addActionListener(this);
		closebut.addActionListener(this);

		f1.setSize(500, 320);
		f1.setLocation((ss.width - fs.width) / 4, (ss.height - fs.height) / 4);
		f1.setVisible(true);
		desk.add(f1);
		desk.moveToFront(f1);

		companyList();
	}

	public void companyList() {
		model.setNumRows(0);
		try {
			con = DBConnectionUtil.getConnection();
			Statement stat = con.createStatement();
			ResultSet rs = stat.executeQuery("select company_id, name from HRCOMPANY_ID ");
			while (rs.next()) {
				String str1 = new String(rs.getString(1));
				String str2 = new String(rs.getString(2));
				model.addRow(new Object[] { new Boolean(false), str1, str2 });
			}
		} catch (Exception ex) {
			System.out.println(ex);
		}
	}

	public void branchList() {
		int row = tb.getSelectedRow();
		idstr = String.valueOf(model.getValueAt(row, 1));
		model.setNumRows(0);

		head[0] = "Select";
		head[1] = "Branch";
		head[2] = "Address";
		model = new DefaultTableModel(data, head);

		try {
			con = DBConnectionUtil.getConnection();
			Statement stat = con.createStatement();
			ResultSet rs = stat
					.executeQuery("select br_code, city from HRCOMPANY_ID where company_id='" + idstr + "' ");
			while (rs.next()) {
				String str1 = new String(rs.getString(1));
				String str2 = new String(rs.getString(2));
				model.addRow(new Object[] { new Boolean(false), str1, str2 });
				actionLabel.setText("Branch");
			}
			tb.setModel(model);
			tb.getColumnModel().getColumn(2).setPreferredWidth(420);

		} catch (Exception ex) {
			System.out.println(ex);
		}
	}

	public void deptList() {
		int row = tb.getSelectedRow();
		branchstr = String.valueOf(model.getValueAt(row, 1));
		model.setNumRows(0);

		head[0] = "Select";
		head[1] = "Dept";
		head[2] = " ## ";
		model = new DefaultTableModel(data, head);

		try {
			con = DBConnectionUtil.getConnection();
			Statement stat = con.createStatement();
			ResultSet rs = stat.executeQuery("select depts from HRCOMPANY_DEPTS where company_id='" + idstr
					+ "' and br_code='" + branchstr + "' ");
			while (rs.next()) {
				String str1 = new String(rs.getString(1));
				model.addRow(new Object[] { new Boolean(false), str1, "##" });
				actionLabel.setText("Depts");
			}
			tb.setModel(model);
			tb.getColumnModel().getColumn(1).setPreferredWidth(480);

		} catch (Exception ex) {
			System.out.println(ex);
		}
	}

	public void desigList() {
		tf5.removeAllItems();

		try {
			con = DBConnectionUtil.getConnection();
			Statement stat = con.createStatement();
			ResultSet rs = stat.executeQuery("select positions from HRCOMPANY_positions ");
			while (rs.next()) {
				String str1 = new String(rs.getString(1));
				tf5.addItem(str1);
				System.out.println(str1);
			}
		} catch (Exception ex) {
			System.out.println(ex);
		}
	}

	@SuppressWarnings("deprecation")
	public void loginCheck() {
		tf1.setText(tf1.getText());
		try {
			con = DBConnectionUtil.getConnection();
			Statement stat = con.createStatement();
			ResultSet rs = stat.executeQuery("select * from password where user = '" + tf1.getText() + "' ");

			while (rs.next()) {
				String duser = new String(rs.getString(1));
				String dpass = new String(rs.getString(2));
				String usertype = new String(rs.getString(3));
				String dept = new String(rs.getString(4));

				idstr = new String(rs.getString(7));
				branchstr = new String(rs.getString(8));

				// sa.l2.setText(idstr);
				// sa.l3.setText(branchstr);
				System.out.println(idstr + "\t" + branchstr);

				String tuser = new String(tf1.getText());
				String tpass = new String(tf2.getText());

				if (duser.equalsIgnoreCase(tuser) && dpass.equalsIgnoreCase(tpass)) {
					LoginDTO sessionLoginInfo = new LoginDTO();
					sessionLoginInfo.setId(tf1.getText().toUpperCase());
					sessionLoginInfo.setUserType(tf3.getText());
					sessionLoginInfo.setDept(tf4.getText());
					sessionLoginInfo.setPost(String.valueOf(tf5.getSelectedItem()));
					sessionLoginInfo.setCompanyId(idstr);
					sessionLoginInfo.setBranchCode(branchstr);
					SessionUtil.setLoginInfo(sessionLoginInfo);
					
					if (usertype.equalsIgnoreCase("User")) {
						if (dept.equalsIgnoreCase("Tech")) {
							TechMainFrame tmf = new TechMainFrame();
							tmf.paramUser();
							tmf.loginTime();
							tmf.designFrame();
							f.setVisible(false);
						}
						if (dept.equalsIgnoreCase("HR")) {
							HRMainFrame hmf = new HRMainFrame();
							hmf.show();
							f.setVisible(false);

						}
						if (dept.equalsIgnoreCase("IT")) {
							ITMainFrame imf = new ITMainFrame();
							imf.px();
						}
					}
					if (usertype.equalsIgnoreCase("Admin")) {
						SuperAdminAccess sa = new SuperAdminAccess();
						sa.px();
						inf.setVisible(false);
						f.setVisible(false);
					}
				} else {
					JOptionPane.showMessageDialog(inf, "Invalid UserName or Password");
				}
			}
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(inf, ex.getMessage().toString());
		}
	}

	@SuppressWarnings("deprecation")
	public void openDept() {
		int row = tb.getSelectedRow();
		//deptstr to open the department based module/screen
		String deptstr = String.valueOf(model.getValueAt(row, 1));
		if (deptstr.equalsIgnoreCase("Tech")) {
			TechMainFrame tmf = new TechMainFrame();
			/*
			TechMainFrame.textFieldLoggedBy.setText(textFieldProjectNumber.getText().toString().toUpperCase());
			TechMainFrame.textFieldUserType.setText(textFieldClientName.getText());
			TechMainFrame.textFieldDepartment.setText(textFieldSerialNumber.getText());
			TechMainFrame.textFieldPost.setText(String.valueOf(comboBoxItemCode.getSelectedItem()));
			TechMainFrame.textFieldCompanyId.setText(idstr);
			TechMainFrame.textFieldBranchCode.setText(branchstr);
			*/
						
			tmf.paramUser();
			tmf.loginTime();
			tmf.designFrame();
			f1.setVisible(false);
			f.setVisible(false);
		}
		if (deptstr.equalsIgnoreCase("HR")) {
			HRMainFrame hmf = new HRMainFrame();
			hmf.show();
			f1.setVisible(false);
			f.setVisible(false);
		}
		if (deptstr.equalsIgnoreCase("IT")) {
			ITMainFrame imf = new ITMainFrame();
			imf.px();
			f1.setVisible(false);
			f.setVisible(false);
		}
	}

	public void actionPerformed(ActionEvent evt) {
		if (evt.getSource() == tf1) {
			tf2.requestFocus();
		}
		if (evt.getSource() == b2) {
			System.exit(0);
		}
		if (evt.getSource() == b1 || evt.getSource() == tf2) {
			loginCheck();
		}
		if (evt.getSource() == backbut) {
			f1.setVisible(false);
			inf.setVisible(true);
		}
		if (evt.getSource() == closebut) {
			System.exit(0);
		}

		if (evt.getSource() == rb1) {
			try {
				//UIManager.setLookAndFeel(nimbusLF);
				SwingUtilities.updateComponentTreeUI(f);
			} catch (Exception ex) {
				System.out.println(ex);
			}

		}

		if (evt.getSource() == rb2) {
			try {
				UIManager.setLookAndFeel(windowsLF);
				SwingUtilities.updateComponentTreeUI(f);
			} catch (Exception ex) {
				System.out.println(ex);
			}

		}

		if (evt.getSource() == rb3) {
			try {
				UIManager.setLookAndFeel(metalLF);
				SwingUtilities.updateComponentTreeUI(f);
			} catch (Exception ex) {
				System.out.println(ex);
			}

		}
	}

	public void focusGained(FocusEvent fe) {
		if (fe.getSource() == tf1) {
			desigList();
			tf3.setText("");
			tf4.setText("");
			// comboBoxItemCode.setText("");
		}
	}

	public void focusLost(FocusEvent fe) {
		try {
			con = DBConnectionUtil.getConnection();
			Statement stat = con.createStatement();
			ResultSet rs = stat.executeQuery("select * from password where user = '" + tf1.getText() + "' ");

			while (rs.next()) {
				String usertype = new String(rs.getString(3));
				String dept = new String(rs.getString(4));
				String post = new String(rs.getString(5));
				tf3.setText(usertype);
				tf4.setText(dept);
				tf5.setSelectedItem(post.toUpperCase());
			}

		} catch (Exception ex) {
			System.out.println(ex);
		}

		if (String.valueOf(tf3.getText()).equalsIgnoreCase("Admin")) {
			tf5.setEnabled(true);
		}
		if (String.valueOf(tf3.getText()).equalsIgnoreCase("User")) {
			tf5.setEnabled(false);
		}
		if (String.valueOf(tf5.getSelectedItem()).equalsIgnoreCase("Team Leader")) {
			tf5.removeItem("Project Manager");
			tf5.removeItem("CEO");
			tf5.removeItem("System Manager");
			tf5.removeItem("Admin");
		}
		if (String.valueOf(tf5.getSelectedItem()).equalsIgnoreCase("Project Manager")) {
			tf5.removeItem("CEO");
			tf5.removeItem("System Manager");
			tf5.removeItem("Admin");
		}
		if (String.valueOf(tf5.getSelectedItem()).equalsIgnoreCase("CEO")) {
			tf5.removeItem("Admin");
		}
		if (String.valueOf(tf5.getSelectedItem()).equalsIgnoreCase("System Manager")) {
			tf5.removeItem("CEO");
			tf5.removeItem("Admin");
		}
	}

	public void mousePressed(MouseEvent me) {
	}

	public void mouseReleased(MouseEvent me) {
	}

	public void mouseClicked(MouseEvent me) {
		if (me.getSource() == tb) {
			if (tb.getSelectedColumn() == 0) {
				int row = tb.getSelectedRow();
				String val = String.valueOf(model.getValueAt(row, 0));
				if (val.equalsIgnoreCase("true")) {
					if (actionLabel.getText().equalsIgnoreCase("Company")) {
						branchList();
					}
					if (actionLabel.getText().equalsIgnoreCase("Branch")) {
						deptList();
					}
					if (actionLabel.getText().equalsIgnoreCase("Depts")) {
						openDept();
					}
				}
			}
		}
	}

	public void mouseEntered(MouseEvent me) {
	}

	public void mouseExited(MouseEvent me) {
	}

	private void loadDefaultTheame() {
		try {
			//UIManager.setLookAndFeel(nimbusLF);
			SwingUtilities.updateComponentTreeUI(f);
		} catch (Exception ex) {
			System.out.println(ex);
		}
	}
}
