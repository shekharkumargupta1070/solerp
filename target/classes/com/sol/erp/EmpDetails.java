package com.sol.erp;

import java.awt.Color;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.sol.erp.ui.custom.SolCalendar3;
import com.sol.erp.ui.custom.SolCalendar4;
import com.sol.erp.ui.custom.SolTableModel;
import com.sol.erp.util.CityUtil;
import com.sol.erp.util.DBConnectionUtil;
import com.sol.erp.util.DateCalculationUtil;
import com.sol.erp.util.formater.SolDateFormatter;

public class EmpDetails extends javax.swing.JInternalFrame
		implements java.awt.event.ActionListener, java.awt.event.MouseListener, javax.swing.event.ChangeListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	JTextField calendar = new JTextField();
	SolCalendar4 cal4 = new SolCalendar4();

	java.sql.Connection con;
	java.sql.Statement stat;
	int affected = 0;

	JPanel joiningpanel = new JPanel();
	JPanel registerpanel = new JPanel();

	String[] head = { "", "<Html><Center>Name of <BR>Examination", " Marks(%)", "<Html><Center>Year of <BR>Passing",
			"<Html><Center>Board/University", "<HTML><Center>School/College<BR>Name", "<HTML><Center>Place of School",
			"<HTML><Center>Subjects" };

	Object[][] data = new Object[0][];

	String[] head1 = { "", "Company Name", "Post", "From", "To", "Job Profile", "Salary In Hand", "Salary Gross",
			"Reason of Leaving the Company" };
	Object[][] data1 = new Object[0][];

	java.awt.Font fo = new java.awt.Font("Tahoma", 1, 11);

	java.awt.Toolkit tk = java.awt.Toolkit.getDefaultToolkit();
	java.awt.Dimension ss = tk.getScreenSize();
	java.awt.Dimension fs = getSize();

	public EmpDetails() {
		initComponents();
	}

	public void initComponents() {
		con = DBConnectionUtil.getConnection();
		b1 = new JButton();
		tabbedpane = new JTabbedPane();
		p1 = new JPanel();
		p4 = new JPanel();
		subTabbed1 = new JTabbedPane();
		p6 = new JPanel();

		punchlabel = new JLabel();
		punchtf = new PunchCardList("C01", "0", "");

		emplabel = new JLabel();
		emptf = new EmpCodeList("C01", "0", "");

		l1 = new JLabel();
		tf1 = new JTextField();
		l2 = new JLabel();
		tf2 = new JTextField();
		l3 = new JLabel();
		tf3 = new JTextField();
		l4 = new JLabel();
		tf4 = new JTextField();
		l5 = new JLabel();
		tf5 = new SolCalendar3();
		l6 = new JLabel();
		tf6 = new JComboBox();
		l7 = new JLabel();
		tf7 = new JComboBox();
		citycb = new JComboBox();
		l8 = new JLabel();
		cb1 = new JComboBox();
		l9 = new JLabel();
		tf8 = new JTextField();
		p7 = new JPanel();
		l10 = new JLabel();
		sp1 = new javax.swing.JScrollPane();
		ta1 = new javax.swing.JTextArea();
		l12 = new JLabel();
		sp2 = new javax.swing.JScrollPane();
		ta2 = new javax.swing.JTextArea();
		l13 = new JLabel();
		tf9 = new JTextField();
		l14 = new JLabel();
		tf10 = new JTextField();
		l15 = new JLabel();
		tf11 = new JTextField();
		p8 = new JPanel();
		l16 = new JLabel();
		l17 = new JLabel();
		tf12 = new JTextField();
		l18 = new JLabel();
		tf13 = new JTextField();
		l19 = new JLabel();
		tf14 = new JTextField();
		l20 = new JLabel();
		cb3 = new JComboBox();
		l21 = new JLabel();
		tf15 = new JTextField();
		l22 = new JLabel();
		cb4 = new JComboBox();
		l23 = new JLabel();
		tf16 = new JTextField();
		l24 = new JLabel();
		tf17 = new JTextField();
		cb2 = new JComboBox();
		l25 = new JLabel();
		tf18 = new JTextField();
		l26 = new JLabel();
		cb5 = new JComboBox();
		p2 = new JPanel();
		sp3 = new javax.swing.JScrollPane();

		edumodel = new DefaultTableModel(data, head) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public Class<? extends Object> getColumnClass(int paramAnonymousInt) {
				return getValueAt(0, paramAnonymousInt).getClass();
			}
		};
		tb1 = new JTable(edumodel);
		p3 = new JPanel();
		sp4 = new javax.swing.JScrollPane();

		promodel = new DefaultTableModel(data1, head1);
		tb2 = new JTable(promodel) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public Class<? extends Object> getColumnClass(int paramAnonymousInt) {
				return getValueAt(0, paramAnonymousInt).getClass();
			}
		};
		butpanel = new JPanel();
		butpanel1 = new JPanel();

		b2 = new JButton();
		b3 = new JButton();
		b4 = new JButton();
		b5 = new JButton();
		b6 = new JButton();
		b7 = new JButton();

		b1.setText("SAVE");

		setClosable(true);
		setIconifiable(true);
		setMaximizable(true);
		setResizable(true);
		setTitle("Employee Details");
		p1.setLayout(new java.awt.BorderLayout());
		p1.setBackground(Color.red);

		p4.setLayout(null);

		p6.setLayout(null);
		p6.setBorder(new javax.swing.border.LineBorder(new Color(102, 102, 102), 1, true));

		punchlabel.setText("Punchcard No");
		punchlabel.setBounds(20, 40, 110, 15);
		punchtf.setBounds(180, 40, 80, 20);
		punchtf.setFont(fo);

		p6.add(punchlabel);
		p6.add(punchtf);

		emplabel.setText("Emp Code");
		emplabel.setBounds(280, 40, 60, 15);
		emptf.setBounds(350, 40, 230, 20);
		emptf.setFont(fo);

		p6.add(emplabel);
		p6.add(emptf);

		l1.setText("First Name");
		p6.add(l1);
		l1.setBounds(20, 70, 110, 15);

		p6.add(tf1);
		tf1.setBounds(180, 70, 290, 21);
		tf1.setFont(fo);

		l2.setText("Middle");
		p6.add(l2);
		l2.setBounds(20, 100, 100, 15);

		p6.add(tf2);
		tf2.setBounds(180, 100, 290, 21);
		tf2.setFont(fo);

		l3.setText("Last Name");
		p6.add(l3);
		l3.setBounds(20, 130, 110, 15);

		p6.add(tf3);
		tf3.setBounds(180, 130, 290, 21);
		tf3.setFont(fo);

		l4.setText("Father's Name");
		p6.add(l4);
		l4.setBounds(20, 160, 130, 15);

		p6.add(tf4);
		tf4.setBounds(180, 160, 290, 21);
		tf4.setFont(fo);

		l5.setText("Date of Birth");
		p6.add(l5);
		l5.setBounds(20, 190, 130, 15);

		p6.add(tf5.DesignFrame());
		tf5.DesignFrame().setBounds(180, 190, 130, 23);

		l6.setText("SEX");
		p6.add(l6);
		l6.setBounds(320, 190, 40, 15);

		p6.add(tf6);
		tf6.setBounds(350, 190, 40, 21);
		tf6.setEditable(false);
		tf6.setFont(fo);
		tf6.addItem("M");
		tf6.addItem("F");

		l7.setText("Place of Birth");
		p6.add(l7);
		l7.setBounds(20, 220, 120, 15);

		p6.add(tf7);
		tf7.setBounds(180, 220, 140, 21);
		tf7.setFont(fo);

		p6.add(citycb);
		citycb.setBounds(330, 220, 140, 21);

		l8.setText("Blood Group");
		p6.add(l8);
		l8.setBounds(20, 250, 120, 15);

		p6.add(cb1);
		cb1.setBounds(180, 250, 80, 21);
		cb1.setEditable(true);
		cb1.addItem("A");
		cb1.addItem("B");
		cb1.addItem("AB");
		cb1.addItem("O");

		l9.setText("BMI");
		p6.add(l9);
		l9.setBounds(20, 280, 80, 15);

		p6.add(tf8);
		tf8.setBounds(180, 280, 50, 21);
		tf8.setFont(fo);

		subTabbed1.addTab("Personal", p6);

		p7.setLayout(null);

		p7.setBackground(new Color(204, 204, 204));
		p7.setBorder(new javax.swing.border.LineBorder(new Color(102, 102, 102), 1, true));
		l10.setText("Present Address");
		p7.add(l10);
		l10.setBounds(20, 40, 140, 15);

		ta1.setColumns(20);
		ta1.setRows(5);
		sp1.setViewportView(ta1);
		ta1.setFont(fo);

		p7.add(sp1);
		sp1.setBounds(180, 40, 300, 100);

		l12.setText("Permanent Address");
		p7.add(l12);
		l12.setBounds(30, 150, 140, 20);

		ta2.setColumns(20);
		ta2.setRows(5);
		sp2.setViewportView(ta2);
		ta2.setFont(fo);

		p7.add(sp2);
		sp2.setBounds(180, 150, 300, 100);

		l13.setText("Phone (R)");
		p7.add(l13);
		l13.setBounds(40, 265, 70, 20);

		p7.add(tf9);
		tf9.setBounds(180, 260, 200, 21);
		tf9.setFont(fo);

		l14.setText("Mobile");
		p7.add(l14);
		l14.setBounds(40, 290, 70, 20);

		p7.add(tf10);
		tf10.setBounds(180, 290, 200, 21);
		tf10.setFont(fo);

		l15.setText("E-Mail");
		p7.add(l15);
		l15.setBounds(40, 320, 70, 20);

		p7.add(tf11);
		tf11.setBounds(180, 320, 300, 21);
		tf11.setFont(fo);

		subTabbed1.addTab("Residential", p7);

		p8.setLayout(null);

		p8.setBorder(new javax.swing.border.LineBorder(new Color(102, 102, 102), 1, true));
		l16.setText("Marital Status");
		p8.add(l16);
		l16.setBounds(40, 60, 90, 15);

		l17.setText("Spouse Profession");
		p8.add(l17);
		l17.setBounds(40, 90, 140, 15);

		tf12.setText("");
		p8.add(tf12);
		tf12.setBounds(210, 90, 230, 21);
		tf12.setFont(fo);
		tf12.setEditable(false);

		l18.setText("No of Children");
		p8.add(l18);
		l18.setBounds(40, 120, 100, 15);

		tf13.setText("");
		p8.add(tf13);
		tf13.setBounds(210, 120, 60, 21);
		tf13.setFont(fo);
		tf13.setEditable(false);

		l19.setText("PAN No");
		p8.add(l19);
		l19.setBounds(20, 190, 120, 15);

		tf14.setText("");
		p8.add(tf14);
		tf14.setBounds(150, 190, 180, 21);
		tf14.setFont(fo);

		l20.setText("Approval Auth.");
		p8.add(l20);
		l20.setBounds(340, 190, 120, 15);

		p8.add(cb3);
		cb3.setBounds(440, 190, 180, 21);
		cb3.setEditable(true);

		cb3.addItem("GOVT. OF INDIA");

		l21.setText("Driving Lcs.");
		p8.add(l21);
		l21.setBounds(20, 220, 150, 15);

		tf15.setText("");
		p8.add(tf15);
		tf15.setBounds(150, 220, 180, 21);
		tf15.setFont(fo);

		l22.setText("Approval Auth.");
		p8.add(l22);
		l22.setBounds(340, 220, 150, 15);

		p8.add(cb4);
		cb4.setBounds(440, 220, 180, 21);
		cb4.setEditable(true);

		l23.setText("Vehicle Name");
		p8.add(l23);
		l23.setBounds(20, 250, 150, 15);

		tf16.setText("");
		p8.add(tf16);
		tf16.setBounds(150, 250, 180, 21);
		tf16.setFont(fo);

		l24.setText("Number");
		p8.add(l24);
		l24.setBounds(340, 250, 150, 15);

		tf17.setText("");
		p8.add(tf17);
		tf17.setBounds(440, 250, 80, 21);
		tf17.setFont(fo);

		p8.add(cb2);
		cb2.setBounds(210, 60, 130, 21);
		cb2.addItem("Single");
		cb2.addItem("Married");

		l25.setText("Passport");
		p8.add(l25);
		l25.setBounds(20, 280, 150, 15);

		tf18.setText("");
		p8.add(tf18);
		tf18.setBounds(150, 280, 180, 21);
		tf18.setFont(fo);

		l26.setText("Approval Auth.");
		p8.add(l26);
		l26.setBounds(340, 280, 150, 15);

		p8.add(cb5);
		cb5.setBounds(440, 280, 180, 21);
		cb5.setEditable(true);
		cb5.addItem("GOVT. OF INDIA");

		subTabbed1.addTab("Marital Status", p8);
		subTabbed1.addTab("Joining Details", joiningpanel);

		p4.add(subTabbed1);
		subTabbed1.setBounds(50, 70, 780, 400);

		p1.add(p4, "Center");

		registerpanel.setLayout(new java.awt.BorderLayout());
		registerpanel.add(new EmpRegister().DesignFrame(), "Center");

		tabbedpane.addTab("Employee Creation", registerpanel);
		tabbedpane.addTab("Personal Details", p1);
		tabbedpane.addTab("Qualificational Details", p2);
		tabbedpane.addTab("Experience Details", p3);

		p2.setLayout(new java.awt.BorderLayout());
		p2.add(sp3, "Center");
		p2.add(butpanel1, "North");

		p3.setLayout(new java.awt.BorderLayout());
		p3.add(sp4, "Center");

		sp3.setViewportView(tb1);
		sp4.setViewportView(tb2);

		getContentPane().add(tabbedpane, "Center");

		b2.setText("SAVE");
		b3.setText("UPDATE");
		b4.setText("CREATE NEW");
		b5.setText("REMOVE");
		b6.setText("PRINT");
		b7.setText("CLOSE");

		butpanel.setLayout(new java.awt.FlowLayout(1));
		butpanel.add(b4);
		butpanel.add(b2);
		butpanel.add(b3);
		butpanel.add(b5);
		butpanel.add(b6);
		butpanel.add(b7);

		butpanel1.setLayout(new java.awt.FlowLayout(2));

		setBounds(0, 0, 800, 600);
		setLocation((ss.width - fs.width) / 10, (ss.height - fs.height) / 25);

		SolTableModel.decorateTable(tb1);
		SolTableModel.decorateTable(tb2);

		cb2.addActionListener(this);
		punchtf.addActionListener(this);
		emptf.addActionListener(this);
		tf1.addActionListener(this);
		tf7.addActionListener(this);

		b1.addActionListener(this);
		b2.addActionListener(this);
		b3.addActionListener(this);
		b4.addActionListener(this);
		b5.addActionListener(this);
		b6.addActionListener(this);
		b7.addActionListener(this);

		tabbedpane.addChangeListener(this);
		tabbedpane.addMouseListener(this);

		tb1.getTableHeader().setPreferredSize(new java.awt.Dimension(40, 40));
		tb1.setAutoResizeMode(0);
		tb1.setRowHeight(30);
		tb1.setIntercellSpacing(new java.awt.Dimension(1, 1));

		tb1.getColumnModel().getColumn(0).setPreferredWidth(30);
		tb1.getColumnModel().getColumn(1).setPreferredWidth(160);
		tb1.getColumnModel().getColumn(2).setPreferredWidth(75);
		tb1.getColumnModel().getColumn(3).setPreferredWidth(90);
		tb1.getColumnModel().getColumn(4).setPreferredWidth(130);
		tb1.getColumnModel().getColumn(5).setPreferredWidth(160);
		tb1.getColumnModel().getColumn(6).setPreferredWidth(130);
		tb1.getColumnModel().getColumn(7).setPreferredWidth(200);

		tb2.getTableHeader().setPreferredSize(new java.awt.Dimension(40, 40));
		tb2.setAutoResizeMode(0);
		tb2.setRowHeight(30);
		tb2.setIntercellSpacing(new java.awt.Dimension(1, 1));

		tb2.getColumnModel().getColumn(0).setPreferredWidth(30);
		tb2.getColumnModel().getColumn(1).setPreferredWidth(160);
		tb2.getColumnModel().getColumn(2).setPreferredWidth(100);
		tb2.getColumnModel().getColumn(3).setPreferredWidth(90);
		tb2.getColumnModel().getColumn(4).setPreferredWidth(90);
		tb2.getColumnModel().getColumn(5).setPreferredWidth(130);
		tb2.getColumnModel().getColumn(6).setPreferredWidth(100);
		tb2.getColumnModel().getColumn(7).setPreferredWidth(100);
		tb2.getColumnModel().getColumn(8).setPreferredWidth(200);

		calendar = cal4.DesignFrame();
		tb1.getColumnModel().getColumn(3).setCellEditor(new javax.swing.DefaultCellEditor(calendar));

		tb2.getColumnModel().getColumn(3).setCellEditor(new javax.swing.DefaultCellEditor(calendar));
		tb2.getColumnModel().getColumn(4).setCellEditor(new javax.swing.DefaultCellEditor(calendar));

		stateName();
		DesignJoiningPanel();
	}

	public void stateName() {
		tf7.addItem("Andhra Pradesh");
		tf7.addItem("Arunachal Pradesh");
		tf7.addItem("Assam");
		tf7.addItem("Bihar");
		tf7.addItem("Chhattisgarh");
		tf7.addItem("Goa");
		tf7.addItem("Gujarat");
		tf7.addItem("Haryana");
		tf7.addItem("Himachal Pradesh");
		tf7.addItem("Jammu & Kashmir");
		tf7.addItem("Jharkhand");
		tf7.addItem("Karnataka");
		tf7.addItem("Kerala");
		tf7.addItem("Madhya Pradesh");
		tf7.addItem("Maharashtra");
		tf7.addItem("Manipur");
		tf7.addItem("Meghalaya");
		tf7.addItem("Mizoram");
		tf7.addItem("Nagaland");
		tf7.addItem("Orissa");
		tf7.addItem("Punjab");
		tf7.addItem("Rajasthan");
		tf7.addItem("Sikkim");
		tf7.addItem("Tamilnadu");
		tf7.addItem("Tripura");
		tf7.addItem("Uttar Pradesh");
		tf7.addItem("Uttarakhand");
		tf7.addItem("West Bengal");
		tf7.addItem("----------------------");
		tf7.addItem("Andaman & Nicobar Island");
		tf7.addItem("Chandigarh");
		tf7.addItem("Dadra & Nagar Haveli");
		tf7.addItem("Daman & Diu");
		tf7.addItem("Lakshyadweep");
		tf7.addItem("Delh");
		tf7.addItem("Puducherry");

		tf7.setFont(new java.awt.Font("Verdana", 1, 11));
		tf7.setBackground(new Color(120, 120, 200));
		tf7.setForeground(Color.white);

		citycb.setFont(new java.awt.Font("Verdana", 1, 10));
		citycb.setBackground(new Color(120, 120, 200));
		citycb.setForeground(Color.white);
	}

	public void addCity(String paramString, JComboBox paramJComboBox) {
		paramJComboBox.removeAllItems();
		int i = CityUtil.getCityList(paramString).size();
		for (int j = 0; j < i; j++) {
			paramJComboBox.addItem(String.valueOf(CityUtil.getCityList(paramString).get(j)).toUpperCase());
		}
	}

	JLabel al1 = new JLabel("Department");
	JLabel al2 = new JLabel("Designation");
	JLabel al3 = new JLabel("Technical Role");
	JLabel al4 = new JLabel("Authenticaion Type");
	JLabel al5 = new JLabel("Date of Joining");
	JLabel al6 = new JLabel("Joining Salary");
	JLabel al7 = new JLabel("Salary Mode");
	JLabel al8 = new JLabel("Date of Probation");
	JLabel al9 = new JLabel("Date of Releaving");
	JLabel al10 = new JLabel("Company Code");
	JLabel al11 = new JLabel("Branch Code");

	JTextField atf1 = new JTextField();
	JTextField atf2 = new JTextField();
	JTextField atf3 = new JTextField();
	JTextField atf4 = new JTextField();
	JTextField atf5 = new JTextField();
	JTextField atf6 = new JTextField();
	JTextField atf7 = new JTextField();
	JTextField atf8 = new JTextField();
	JTextField atf9 = new JTextField();
	JTextField atf10 = new JTextField();
	JTextField atf11 = new JTextField();

	java.awt.Font myfont = new java.awt.Font("Verdana", 1, 11);
	private JButton b1;
	private JButton b2;

	public void DesignJoiningPanel() {
		joiningpanel.setLayout(new java.awt.GridLayout(14, 3, 5, 5));

		joiningpanel.add(new JPanel());
		joiningpanel.add(new JPanel());
		joiningpanel.add(new JPanel());

		joiningpanel.add(al1);
		joiningpanel.add(atf1);
		joiningpanel.add(new JPanel());
		joiningpanel.add(al2);
		joiningpanel.add(atf2);
		joiningpanel.add(new JPanel());

		joiningpanel.add(new JPanel());
		joiningpanel.add(new JPanel());
		joiningpanel.add(new JPanel());
		joiningpanel.add(al4);
		joiningpanel.add(atf4);
		joiningpanel.add(new JPanel());
		joiningpanel.add(al5);
		joiningpanel.add(atf5);
		joiningpanel.add(new JPanel());
		joiningpanel.add(al6);
		joiningpanel.add(atf6);
		joiningpanel.add(new JPanel());
		joiningpanel.add(al7);
		joiningpanel.add(atf7);
		joiningpanel.add(new JPanel());
		joiningpanel.add(al8);
		joiningpanel.add(atf8);
		joiningpanel.add(new JPanel());
		joiningpanel.add(al9);
		joiningpanel.add(atf9);
		joiningpanel.add(new JPanel());
		joiningpanel.add(al10);
		joiningpanel.add(atf10);
		joiningpanel.add(new JPanel());
		joiningpanel.add(al11);
		joiningpanel.add(atf11);
		joiningpanel.add(new JPanel());

		joiningpanel.add(new JPanel());
		joiningpanel.add(new JPanel());
		joiningpanel.add(new JPanel());
		joiningpanel.add(new JPanel());
		joiningpanel.add(new JPanel());
		joiningpanel.add(new JPanel());

		al1.setHorizontalAlignment(4);
		al2.setHorizontalAlignment(4);
		al3.setHorizontalAlignment(4);
		al4.setHorizontalAlignment(4);
		al5.setHorizontalAlignment(4);
		al6.setHorizontalAlignment(4);
		al7.setHorizontalAlignment(4);
		al8.setHorizontalAlignment(4);
		al9.setHorizontalAlignment(4);
		al10.setHorizontalAlignment(4);
		al11.setHorizontalAlignment(4);

		atf1.setBackground(new Color(120, 120, 200));
		atf1.setForeground(Color.white);
		atf2.setBackground(new Color(120, 120, 200));
		atf2.setForeground(Color.white);
		atf3.setBackground(new Color(120, 120, 200));
		atf3.setForeground(Color.white);
		atf4.setBackground(new Color(120, 120, 200));
		atf4.setForeground(Color.white);
		atf5.setBackground(new Color(120, 120, 200));
		atf5.setForeground(Color.white);
		atf6.setBackground(new Color(120, 120, 200));
		atf6.setForeground(Color.white);
		atf7.setBackground(new Color(120, 120, 200));
		atf7.setForeground(Color.white);
		atf8.setBackground(new Color(120, 120, 200));
		atf8.setForeground(Color.white);
		atf9.setBackground(new Color(120, 120, 200));
		atf9.setForeground(Color.white);
		atf10.setBackground(new Color(120, 120, 200));
		atf10.setForeground(Color.white);
		atf11.setBackground(new Color(120, 120, 200));
		atf11.setForeground(Color.white);

		atf1.setFont(myfont);
		atf1.setEditable(false);
		atf2.setFont(myfont);
		atf2.setEditable(false);
		atf3.setFont(myfont);
		atf3.setEditable(false);
		atf4.setFont(myfont);
		atf4.setEditable(false);
		atf5.setFont(myfont);
		atf5.setEditable(false);
		atf6.setFont(myfont);
		atf6.setEditable(false);
		atf7.setFont(myfont);
		atf7.setEditable(false);
		atf8.setFont(myfont);
		atf8.setEditable(false);
		atf9.setFont(myfont);
		atf9.setEditable(false);
		atf10.setFont(myfont);
		atf10.setEditable(false);
		atf11.setFont(myfont);
		atf11.setEditable(false);

		cb1.setBackground(new Color(120, 120, 200));
		cb1.setForeground(Color.white);
		cb1.setFont(myfont);

		cb2.setBackground(new Color(120, 120, 200));
		cb2.setForeground(Color.white);
		cb2.setFont(myfont);

		cb3.setBackground(new Color(120, 120, 200));
		cb3.setForeground(Color.white);
		cb3.setFont(myfont);

		cb4.setBackground(new Color(120, 120, 200));
		cb4.setForeground(Color.white);
		cb4.setFont(myfont);

		cb5.setBackground(new Color(120, 120, 200));
		cb5.setForeground(Color.white);
		cb5.setFont(myfont);

		tf6.setBackground(new Color(120, 120, 200));
		tf6.setForeground(Color.white);
		tf6.setFont(myfont);

		tf7.setBackground(new Color(120, 120, 200));
		tf7.setForeground(Color.white);
		tf7.setFont(myfont);

		b1.setBackground(new Color(220, 120, 120));
		b1.setForeground(Color.white);
		b1.setFont(myfont);

		b2.setBackground(new Color(200, 120, 200));
		b2.setForeground(Color.white);
		b2.setFont(myfont);

		b3.setBackground(new Color(200, 120, 200));
		b3.setForeground(Color.white);
		b3.setFont(myfont);

		b4.setBackground(new Color(200, 120, 200));
		b4.setForeground(Color.white);
		b4.setFont(myfont);

		b5.setBackground(new Color(200, 120, 200));
		b5.setForeground(Color.white);
		b5.setFont(myfont);

		b6.setBackground(new Color(120, 200, 120));
		b6.setForeground(Color.white);
		b6.setFont(myfont);

		b7.setBackground(Color.red);
		b7.setForeground(Color.white);
		b7.setFont(myfont);

		tabbedpane.setBackground(new Color(200, 100, 100));
		tabbedpane.setForeground(Color.white);
		tabbedpane.setFont(myfont);
		tabbedpane.setTabPlacement(1);

		subTabbed1.setBackground(new Color(100, 100, 200));
		subTabbed1.setForeground(Color.white);
		subTabbed1.setFont(myfont);
		subTabbed1.setTabPlacement(1);
	}

	private JButton b3;
	private JButton b4;
	private JButton b5;
	private JButton b6;
	private JButton b7;
	private JComboBox cb1;
	private JComboBox cb2;
	private JComboBox cb3;
	private JComboBox cb4;
	private JComboBox cb5;
	private JLabel punchlabel;
	private JLabel emplabel;
	private JLabel l1;

	public void insert() {
		try {
			con = DBConnectionUtil.getConnection();
			PreparedStatement localPreparedStatement = con.prepareStatement(
					"insert into HREMP_PERSONAL values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			localPreparedStatement.setString(1, punchtf.getText());
			localPreparedStatement.setString(2, emptf.getText());
			localPreparedStatement.setString(3, tf1.getText());
			localPreparedStatement.setString(4, tf2.getText());
			localPreparedStatement.setString(5, tf3.getText());
			localPreparedStatement.setString(6, tf4.getText());
			localPreparedStatement.setDate(7, SolDateFormatter.DDMMYYtoSQL(tf5.getText()));
			localPreparedStatement.setString(8, String.valueOf(tf6.getSelectedItem()));
			localPreparedStatement.setString(9, String.valueOf(citycb.getSelectedItem()));
			localPreparedStatement.setString(10, String.valueOf(cb1.getSelectedItem()));
			localPreparedStatement.setString(11, tf8.getText());
			localPreparedStatement.setString(12, ta1.getText());
			localPreparedStatement.setString(13, ta2.getText());
			localPreparedStatement.setString(14, tf9.getText());
			localPreparedStatement.setString(15, tf10.getText());
			localPreparedStatement.setString(16, tf11.getText());
			localPreparedStatement.setString(17, String.valueOf(cb2.getSelectedItem()));
			localPreparedStatement.setString(18, tf12.getText());
			localPreparedStatement.setString(19, tf13.getText());
			localPreparedStatement.setString(20, tf14.getText());
			localPreparedStatement.setString(21, String.valueOf(cb3.getSelectedItem()));
			localPreparedStatement.setString(22, tf15.getText());
			localPreparedStatement.setString(23, String.valueOf(cb4.getSelectedItem()));
			localPreparedStatement.setString(24, tf16.getText());
			localPreparedStatement.setString(25, tf17.getText());
			localPreparedStatement.setString(26, tf18.getText());
			localPreparedStatement.setString(27, String.valueOf(cb5.getSelectedItem()));
			localPreparedStatement.setString(28, "Remarks");
			localPreparedStatement.setString(29, atf10.getText());
			localPreparedStatement.setString(30, atf11.getText());
			localPreparedStatement.setString(31, atf1.getText());
			localPreparedStatement.setString(32, atf2.getText());
			localPreparedStatement.setString(33, atf4.getText());
			localPreparedStatement.setDate(34, SolDateFormatter.DDMMYYtoSQL(atf5.getText()));
			localPreparedStatement.setString(35, atf6.getText());
			localPreparedStatement.setString(36, atf7.getText());
			localPreparedStatement.setDate(37, SolDateFormatter.DDMMYYtoSQL(atf8.getText()));
			localPreparedStatement.setString(38, atf9.getText());
			localPreparedStatement.setTimestamp(39, DateCalculationUtil.getCurrentDateTime());
			localPreparedStatement.setString(40, "SHEKHAR");
			affected = localPreparedStatement.executeUpdate();
			if (affected > 0) {
				javax.swing.JOptionPane.showMessageDialog(this, "Record Saved.");
				tf1.setText("");
				tf2.setText("");
				tf3.setText("");
				tf4.setText("");
				tf5.setText("");
				tf8.setText("");
				tf9.setText("");

				tf10.setText("");
				tf11.setText("");
				tf12.setText("");
				tf13.setText("");
				tf14.setText("");
				tf15.setText("");
				tf16.setText("");
				tf17.setText("");
			}
		} catch (Exception localException) {
			javax.swing.JOptionPane.showMessageDialog(this, localException.getMessage().toString());
			System.out.println(localException);
		}
	}

	private JLabel l10;
	private JLabel l12;
	private JLabel l13;
	private JLabel l14;
	private JLabel l15;

	public void updatePersonal() {
		try {
			con = DBConnectionUtil.getConnection();
			PreparedStatement localPreparedStatement = con.prepareStatement(
					"update hremp_personal set F_NAME=?, M_NAME=?, L_NAME=?, FATHER=?, DOB=?, SEX=?, PLACE_OF_BIRTH=?, BLOOD_GROUP=?, BMI=?, PRESENT_ADD=?, PERMANENT_ADD=?, PHONE=?, MOBILE=?, EMAIL=?, MARITAL=?, SPOUSE_PROFF=?, NO_OF_CHILD=?, PAN_NO=?, PAN_APPROVED=?, DRIVING_LCS=?, LCS_APPROVED=?, VEHICLE_NAME=?, VEHICLE_NUMBER=?, PASSPORT=?, PASSPORT_APPROVED=?, REMARKS=? where EMP_CODE LIKE ? AND PUNCHCARD_NO LIKE ? ");
			localPreparedStatement.setString(1, tf1.getText());
			localPreparedStatement.setString(2, tf2.getText());
			localPreparedStatement.setString(3, tf3.getText());
			localPreparedStatement.setString(4, tf4.getText());
			localPreparedStatement.setDate(5, SolDateFormatter.DDMMYYtoSQL(tf5.getText()));
			localPreparedStatement.setString(6, String.valueOf(tf6.getSelectedItem()));
			localPreparedStatement.setString(7, String.valueOf(citycb.getSelectedItem()));
			localPreparedStatement.setString(8, String.valueOf(cb1.getSelectedItem()));
			localPreparedStatement.setString(9, tf8.getText());
			localPreparedStatement.setString(10, ta1.getText());
			localPreparedStatement.setString(11, ta2.getText());
			localPreparedStatement.setString(12, tf9.getText());
			localPreparedStatement.setString(13, tf10.getText());
			localPreparedStatement.setString(14, tf11.getText());
			localPreparedStatement.setString(15, String.valueOf(cb2.getSelectedItem()));
			localPreparedStatement.setString(16, tf12.getText());
			localPreparedStatement.setString(17, tf13.getText());
			localPreparedStatement.setString(18, tf14.getText());
			localPreparedStatement.setString(19, String.valueOf(cb3.getSelectedItem()));
			localPreparedStatement.setString(20, tf15.getText());
			localPreparedStatement.setString(21, String.valueOf(cb4.getSelectedItem()));
			localPreparedStatement.setString(22, tf16.getText());
			localPreparedStatement.setString(23, tf17.getText());
			localPreparedStatement.setString(24, tf18.getText());
			localPreparedStatement.setString(25, String.valueOf(cb5.getSelectedItem()));
			localPreparedStatement.setString(26, "Remarks");
			localPreparedStatement.setString(27, "%" + emptf.getText() + "%");
			localPreparedStatement.setString(28, "%" + punchtf.getText() + "%");

			affected = localPreparedStatement.executeUpdate();
			if (affected > 0) {
				javax.swing.JOptionPane.showMessageDialog(this, "Record Updated.");
			}
		} catch (Exception localException) {
			javax.swing.JOptionPane.showMessageDialog(this, localException.getMessage().toString());
			System.out.println(localException);
		}
	}

	private JLabel l16;
	private JLabel l17;
	private JLabel l18;
	private JLabel l19;
	private JLabel l2;

	public String saveEducational(int paramInt) {
		String str1 = String.valueOf(emptf.getText());
		String str2 = String.valueOf(edumodel.getValueAt(paramInt, 1));
		String str3 = String.valueOf(edumodel.getValueAt(paramInt, 2));
		String str4 = String.valueOf(edumodel.getValueAt(paramInt, 3));
		String str5 = String.valueOf(edumodel.getValueAt(paramInt, 4));
		String str6 = String.valueOf(edumodel.getValueAt(paramInt, 5));
		String str7 = String.valueOf(edumodel.getValueAt(paramInt, 6));
		String str8 = String.valueOf(edumodel.getValueAt(paramInt, 7));

		String str9 = "fail";
		int i = 0;

		try {
			con = DBConnectionUtil.getConnection();
			PreparedStatement localPreparedStatement = con
					.prepareStatement("insert into HREMP_EDU values(?,?,?,?,?,?,?,?,?,?)");
			localPreparedStatement.setString(1, str1);
			localPreparedStatement.setString(2, str2);
			localPreparedStatement.setString(3, str3);
			localPreparedStatement.setDate(4, SolDateFormatter.DDMMYYtoSQL(str4));
			localPreparedStatement.setString(5, str5);
			localPreparedStatement.setString(6, str6);
			localPreparedStatement.setString(7, str7);
			localPreparedStatement.setString(8, str8);
			localPreparedStatement.setTimestamp(9, DateCalculationUtil.getCurrentDateTime());
			localPreparedStatement.setString(10, "SHEKHAR");
			i = localPreparedStatement.executeUpdate();

			if (i > 0) {
				str9 = "OK";
			}
		} catch (Exception localException) {
			str9 = localException.getMessage().toString();
		}
		return str9;
	}

	public String saveProfessional(int paramInt) {
		String str1 = String.valueOf(emptf.getText());
		String str2 = String.valueOf(promodel.getValueAt(paramInt, 1));
		String str3 = String.valueOf(promodel.getValueAt(paramInt, 2));
		String str4 = String.valueOf(promodel.getValueAt(paramInt, 3));
		String str5 = String.valueOf(promodel.getValueAt(paramInt, 4));
		String str6 = String.valueOf(promodel.getValueAt(paramInt, 5));
		String str7 = String.valueOf(promodel.getValueAt(paramInt, 6));
		String str8 = String.valueOf(promodel.getValueAt(paramInt, 7));
		String str9 = String.valueOf(promodel.getValueAt(paramInt, 8));

		String str10 = "fail";
		int i = 0;

		try {
			con = DBConnectionUtil.getConnection();
			PreparedStatement localPreparedStatement = con
					.prepareStatement("insert into HREMP_PRO values(?,?,?,?,?,?,?,?,?,?,?)");
			localPreparedStatement.setString(1, str1);
			localPreparedStatement.setString(2, str2);
			localPreparedStatement.setString(3, str3);
			localPreparedStatement.setDate(4, SolDateFormatter.DDMMYYtoSQL(str4));
			localPreparedStatement.setDate(5, SolDateFormatter.DDMMYYtoSQL(str5));
			localPreparedStatement.setString(6, str6);
			localPreparedStatement.setFloat(7, Float.parseFloat(str7));
			localPreparedStatement.setFloat(8, Float.parseFloat(str8));
			localPreparedStatement.setString(9, str9);
			localPreparedStatement.setTimestamp(10, DateCalculationUtil.getCurrentDateTime());
			localPreparedStatement.setString(11, "SHEKHAR");
			i = localPreparedStatement.executeUpdate();

			if (i > 0) {
				str10 = "OK";
			}
		} catch (Exception localException) {
			str10 = localException.getMessage().toString();
		}
		return str10;
	}

	public void removeEducational(int paramInt) {
		String str1 = String.valueOf(emptf.getText());
		String str2 = String.valueOf(edumodel.getValueAt(paramInt, 1));

		try {
			con = DBConnectionUtil.getConnection();
			PreparedStatement localPreparedStatement = con
					.prepareStatement("delete from HREMP_EDU where emp_code=? and EXAM=? ");
			localPreparedStatement.setString(1, str1);
			localPreparedStatement.setString(2, str2);
			affected = localPreparedStatement.executeUpdate();

			System.out.println(str1 + "\t" + str2);
			if (affected > 0) {
				edumodel.removeRow(paramInt);
			}
		} catch (Exception localException) {
			javax.swing.JOptionPane.showMessageDialog(this, localException.getMessage().toString());
		}
	}

	public void removeProfessional(int paramInt) {
		String str1 = String.valueOf(emptf.getText());
		String str2 = String.valueOf(promodel.getValueAt(paramInt, 1));
		String str3 = String.valueOf(promodel.getValueAt(paramInt, 2));

		int i = 0;

		try {
			con = DBConnectionUtil.getConnection();
			PreparedStatement localPreparedStatement = con
					.prepareStatement("delete from HREMP_PRO where emp_code=? and company=? and post=? ");
			localPreparedStatement.setString(1, str1);
			localPreparedStatement.setString(2, str2);
			localPreparedStatement.setString(3, str3);
			i = localPreparedStatement.executeUpdate();

			if (i > 0) {
				promodel.removeRow(paramInt);
			}
		} catch (Exception localException) {
			javax.swing.JOptionPane.showMessageDialog(this, localException.getMessage().toString());
		}
	}

	public String updateEducational(int paramInt) {
		String str1 = String.valueOf(emptf.getText());
		String str2 = String.valueOf(edumodel.getValueAt(paramInt, 1));
		String str3 = String.valueOf(edumodel.getValueAt(paramInt, 2));
		String str4 = String.valueOf(edumodel.getValueAt(paramInt, 3));
		String str5 = String.valueOf(edumodel.getValueAt(paramInt, 4));
		String str6 = String.valueOf(edumodel.getValueAt(paramInt, 5));
		String str7 = String.valueOf(edumodel.getValueAt(paramInt, 6));
		String str8 = String.valueOf(edumodel.getValueAt(paramInt, 7));

		String str9 = "fail";
		int i = 0;

		try {
			con = DBConnectionUtil.getConnection();
			PreparedStatement localPreparedStatement = con.prepareStatement(
					"update HREMP_EDU set EXAM=?, PERCENT=?, YEAR_OF_PASSING=?, UNIVERSITY=?, NAME_OF_SCHOOL=?, PLACE=?, SUBJECT=? where EMP_CODE=? and EXAM=? ");
			localPreparedStatement.setString(1, str2);
			localPreparedStatement.setString(2, str3);
			localPreparedStatement.setDate(3, SolDateFormatter.DDMMYYtoSQL(str4));
			localPreparedStatement.setString(4, str5);
			localPreparedStatement.setString(5, str6);
			localPreparedStatement.setString(6, str7);
			localPreparedStatement.setString(7, str8);
			localPreparedStatement.setString(8, str1);
			localPreparedStatement.setString(9, str2);
			i = localPreparedStatement.executeUpdate();

			if (i > 0) {
				str9 = "OK";
			}
		} catch (Exception localException) {
			str9 = localException.getMessage().toString();
		}
		return str9;
	}

	public String updateProfessional(int paramInt) {
		String str1 = String.valueOf(emptf.getText());
		String str2 = String.valueOf(promodel.getValueAt(paramInt, 1));
		String str3 = String.valueOf(promodel.getValueAt(paramInt, 2));
		String str6 = String.valueOf(promodel.getValueAt(paramInt, 5));
		String str7 = String.valueOf(promodel.getValueAt(paramInt, 6));
		String str8 = String.valueOf(promodel.getValueAt(paramInt, 7));
		String str9 = String.valueOf(promodel.getValueAt(paramInt, 8));

		String str10 = "fail";
		int i = 0;

		try {
			con = DBConnectionUtil.getConnection();
			PreparedStatement localPreparedStatement = con.prepareStatement(
					"update HREMP_PRO set JOB_PROFILE=?, SALARY_HAND=?, SALARY_GROSS=?, REASON=? where EMP_CODE=? and COMPANY=? and POST=?");
			localPreparedStatement.setString(1, str6);
			localPreparedStatement.setFloat(2, Float.parseFloat(str7));
			localPreparedStatement.setFloat(3, Float.parseFloat(str8));
			localPreparedStatement.setString(4, str9);
			localPreparedStatement.setString(5, str1);
			localPreparedStatement.setString(6, str2);
			localPreparedStatement.setString(7, str3);
			i = localPreparedStatement.executeUpdate();

			if (i > 0) {
				str10 = "OK";
			}
		} catch (Exception localException) {
			str10 = localException.getMessage().toString();
		}
		return str10;
	}

	public void showRecord() {
		try {
			con = DBConnectionUtil.getConnection();
			stat = con.createStatement();
			ResultSet localResultSet = stat.executeQuery("Select * from HREMP_PERSONAL where punchcard_no like '"
					+ punchtf.getText() + "' or emp_code='" + emptf.getText() + "' or F_Name='" + tf1.getText() + "' ");

			while (localResultSet.next()) {
				punchtf.setText(localResultSet.getString(1));
				emptf.setText(localResultSet.getString(2));
				tf1.setText(localResultSet.getString(3));
				tf2.setText(localResultSet.getString(4));
				tf3.setText(localResultSet.getString(5));
				tf4.setText(localResultSet.getString(6));

				tf5.setText(SolDateFormatter.SQLtoDDMMYY(localResultSet.getDate(7)));
				tf6.setSelectedItem(localResultSet.getString(8));
				tf7.setSelectedItem(localResultSet.getString(9));
				cb1.setSelectedItem(localResultSet.getString(10));
				tf8.setText(localResultSet.getString(11));
				ta1.setText(localResultSet.getString(12));
				ta2.setText(localResultSet.getString(13));
				tf9.setText(localResultSet.getString(14));
				tf10.setText(localResultSet.getString(15));
				tf11.setText(localResultSet.getString(16));
				cb2.setSelectedItem(localResultSet.getString(17));
				tf12.setText(localResultSet.getString(18));
				tf13.setText(localResultSet.getString(19));

				tf14.setText(localResultSet.getString(20));
				cb3.setSelectedItem(localResultSet.getString(21));

				tf15.setText(localResultSet.getString(22));
				cb4.setSelectedItem(localResultSet.getString(23));

				tf16.setText(localResultSet.getString(24));
				tf17.setText(localResultSet.getString(25));

				tf18.setText(localResultSet.getString(26));
				cb5.setSelectedItem(localResultSet.getString(27));
			}
		} catch (Exception localException) {
			System.out.println(localException);
		}
	}

	public void showEducational() {
		edumodel.setNumRows(0);
		try {
			con = DBConnectionUtil.getConnection();
			stat = con.createStatement();
			ResultSet localResultSet = stat
					.executeQuery("Select * from HREMP_EDU where emp_code='" + emptf.getText() + "' ");

			while (localResultSet.next()) {
				String str2 = new String(localResultSet.getString(2));
				String str3 = new String(localResultSet.getString(3));
				String str4 = SolDateFormatter.SQLtoDDMMYY(localResultSet.getDate(4));
				String str5 = new String(localResultSet.getString(5));
				String str6 = new String(localResultSet.getString(6));
				String str7 = new String(localResultSet.getString(7));
				String str8 = new String(localResultSet.getString(8));
				edumodel.addRow(new Object[] { new Boolean(false), str2, str3, str4, str5, str6, str7, str8 });
			}
		} catch (Exception localException) {
			System.out.println(localException);
		}
	}

	public void showProfessional() {
		promodel.setNumRows(0);
		try {
			con = DBConnectionUtil.getConnection();
			stat = con.createStatement();
			ResultSet localResultSet = stat
					.executeQuery("Select * from HREMP_PRO where emp_code='" + emptf.getText() + "' ");

			while (localResultSet.next()) {
				String str2 = new String(localResultSet.getString(2));
				String str3 = new String(localResultSet.getString(3));
				String str4 = SolDateFormatter.SQLtoDDMMYY(localResultSet.getDate(4));
				String str5 = SolDateFormatter.SQLtoDDMMYY(localResultSet.getDate(5));
				String str6 = new String(localResultSet.getString(6));
				String str7 = new String(localResultSet.getString(7));
				String str8 = new String(localResultSet.getString(8));
				String str9 = new String(localResultSet.getString(9));

				promodel.addRow(new Object[] { new Boolean(false), str2, str3, str4, str5, str6, str7, str8, str9 });
			}
		} catch (Exception localException) {
			System.out.println(localException);
		}
	}

	private JLabel l20;
	private JLabel l21;
	private JLabel l22;

	public void actionPerformed(java.awt.event.ActionEvent paramActionEvent) {
		int i = tabbedpane.getSelectedIndex();
		String str1;
		int k;
		String str4;
		if (paramActionEvent.getSource() == b2) {

			if (i == 1) {
				insert();
			}
			if (i == 2) {
				str1 = null;
				for (k = 0; k < tb1.getRowCount(); k++) {
					str4 = String.valueOf(edumodel.getValueAt(k, 0));
					if (str4.equalsIgnoreCase("true")) {
						str1 = saveEducational(k);
						if (str1.equalsIgnoreCase("OK")) {
							str1 = "OK! Educational Details Saved.";
						} else {
							javax.swing.JOptionPane.showMessageDialog(this, str1);
						}
					}
				}
				javax.swing.JOptionPane.showMessageDialog(this, str1);
			}
			if (i == 3) {
				str1 = null;
				for (k = 0; k < tb2.getRowCount(); k++) {
					str4 = String.valueOf(promodel.getValueAt(k, 0));
					if (str4.equalsIgnoreCase("true")) {
						str1 = saveProfessional(k);
						if (str1.equalsIgnoreCase("OK")) {
							str1 = "OK! Professional Details Saved.";
						} else {
							javax.swing.JOptionPane.showMessageDialog(this, str1);
						}
					}
				}
				javax.swing.JOptionPane.showMessageDialog(this, str1);
			}
		}

		if (paramActionEvent.getSource() == b3) {

			if (i == 1) {
				updatePersonal();
			}
			if (i == 2) {
				str1 = null;
				for (k = 0; k < tb1.getRowCount(); k++) {
					str4 = String.valueOf(edumodel.getValueAt(k, 0));
					if (str4.equalsIgnoreCase("true")) {
						str1 = updateEducational(k);
						if (str1.equalsIgnoreCase("OK")) {
							str1 = "OK! Educational Details Updated.";
						} else {
							javax.swing.JOptionPane.showMessageDialog(this, str1);
						}
					}
				}
				javax.swing.JOptionPane.showMessageDialog(this, str1);
			}
			if (i == 3) {
				str1 = null;
				for (k = 0; k < tb2.getRowCount(); k++) {
					str4 = String.valueOf(promodel.getValueAt(k, 0));
					if (str4.equalsIgnoreCase("true")) {
						str1 = updateProfessional(k);
						if (str1.equalsIgnoreCase("OK")) {
							str1 = "OK! Professional Details Updated.";
						} else {
							javax.swing.JOptionPane.showMessageDialog(this, str1);
						}
					}
				}
				javax.swing.JOptionPane.showMessageDialog(this, str1);
			}
		}

		if (paramActionEvent.getSource() == b5) {
			int j;
			String str3;
			if (i == 2) {
				for (j = 0; j < tb1.getRowCount(); j++) {
					str3 = String.valueOf(edumodel.getValueAt(j, 0));
					if (str3.equalsIgnoreCase("true")) {
						removeEducational(j);
					}
				}
			}

			if (i == 3) {
				for (j = 0; j < tb2.getRowCount(); j++) {
					str3 = String.valueOf(promodel.getValueAt(j, 0));
					if (str3.equalsIgnoreCase("true")) {
						removeProfessional(j);
					}
				}
			}
		}

		if (paramActionEvent.getSource() == b7) {
			setVisible(false);
		}

		if (paramActionEvent.getSource() == b4) {
			b2.setEnabled(true);
			b3.setEnabled(true);
			b5.setEnabled(true);
			b6.setEnabled(true);

			if (i == 2) {

				edumodel.addRow(
						new Object[] { new Boolean(true), "", "", DateCalculationUtil.getCurrentDate("dd/MM/yyyy"), "", "", "", "" });
			}
			if (i == 3) {

				promodel.addRow(new Object[] { new Boolean(true), "", "", DateCalculationUtil.getCurrentDate("dd/MM/yyyy"),
				        DateCalculationUtil.getCurrentDate("dd/MM/yyyy"), "", "", "", "" });
			}
		}
		if (paramActionEvent.getSource() == cb2) {
			String str2 = (String) cb2.getSelectedItem();

			if (str2.equalsIgnoreCase("Married")) {
				tf12.setEditable(true);
				tf13.setEditable(true);
			}
			if (str2.equalsIgnoreCase("Single")) {
				tf12.setEditable(false);
				tf13.setEditable(false);
			}
		}
		if (paramActionEvent.getSource() == punchtf) {
			showRecord();
			showEducational();
			showProfessional();
		}
		if (paramActionEvent.getSource() == emptf) {

			punchtf.setText(EmpTB.getPunchcardNo(emptf.getText()));
			tf1.setText(EmpTB.getEmpName(emptf.getText()).toUpperCase());

			atf1.setText(EmpTB.getDept(emptf.getText()).toUpperCase());
			atf2.setText(EmpTB.getDesig(emptf.getText()).toUpperCase());
			atf4.setText(EmpTB.getUserType(emptf.getText()).toUpperCase());
			atf5.setText(EmpTB.getDOJ(emptf.getText()).toUpperCase());
			atf6.setText(EmpTB.getSalary(emptf.getText()).toUpperCase());
			atf7.setText(EmpTB.getReqMode(emptf.getText()).toUpperCase());
			atf8.setText(EmpTB.getProbationDate(emptf.getText()).toUpperCase());

			atf10.setText(EmpTB.getCompanyID(emptf.getText()).toUpperCase());
			atf11.setText(EmpTB.getBranchCode(emptf.getText()).toUpperCase());
		}
		if (paramActionEvent.getSource() == tf1) {
			showRecord();
		}
		if (paramActionEvent.getSource() == tf7) {
			addCity(String.valueOf(tf7.getSelectedItem()), citycb);
		}
	}

	private JLabel l23;

	private JLabel l24;
	private JLabel l25;
	private JLabel l26;
	private JLabel l3;
	private JLabel l4;
	private JLabel l5;
	private JLabel l6;
	private JLabel l7;

	public void mousePressed(java.awt.event.MouseEvent paramMouseEvent) {
		if (paramMouseEvent.getSource() == tabbedpane) {
			if (tabbedpane.getSelectedIndex() == 0) {
				getContentPane().remove(butpanel);
			} else {
				getContentPane().add(butpanel, "South");
				b1.setEnabled(true);
			}
		}
	}

	private JLabel l8;

	public void mouseReleased(java.awt.event.MouseEvent paramMouseEvent) {
	}

	public void mouseClicked(java.awt.event.MouseEvent paramMouseEvent) {
	}

	public void mouseEntered(java.awt.event.MouseEvent paramMouseEvent) {
	}

	public void mouseExited(java.awt.event.MouseEvent paramMouseEvent) {
	}

	public void stateChanged(javax.swing.event.ChangeEvent paramChangeEvent) {
		if (paramChangeEvent.getSource() == tabbedpane) {
			int i = tabbedpane.getSelectedIndex();
			if (i == 2) {
				p3.remove(butpanel1);
				p2.add(butpanel1, "North");
				p2.revalidate();
			}
			if (i == 3) {
				p2.remove(butpanel1);
				p3.add(butpanel1, "North");
				p4.revalidate();
			}
		}
	}

	private JLabel l9;
	private JPanel p1;
	private JPanel p2;
	private JPanel p3;
	private JPanel p4;
	private JPanel butpanel;
	private JPanel butpanel1;
	private JPanel p6;
	private JPanel p7;
	private JPanel p8;
	private javax.swing.JScrollPane sp1;
	private javax.swing.JScrollPane sp2;
	private javax.swing.JScrollPane sp3;
	private javax.swing.JScrollPane sp4;
	private JTabbedPane subTabbed1;
	private DefaultTableModel edumodel;
	private JTable tb1;
	private DefaultTableModel promodel;
	private JTable tb2;
	private javax.swing.JTextArea ta1;
	private javax.swing.JTextArea ta2;
	private PunchCardList punchtf;
	private EmpCodeList emptf;
	private JTextField tf1;
	private JTextField tf10;
	private JTextField tf11;
	private JTextField tf12;
	private JTextField tf13;
	private JTextField tf14;
	private JTextField tf15;
	private JTextField tf16;
	private JTextField tf17;
	private JTextField tf18;
	private JTextField tf2;
	private JTextField tf3;
	private JTextField tf4;
	private SolCalendar3 tf5;
	private JComboBox tf6;
	private JComboBox tf7;
	private JComboBox citycb;
	private JTextField tf8;
	private JTextField tf9;
	private JTabbedPane tabbedpane;
}
