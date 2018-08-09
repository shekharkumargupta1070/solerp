package com.sol.erp;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
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
import java.awt.print.PrinterException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import com.sol.erp.ui.custom.SolCalendar;
import com.sol.erp.ui.custom.SolCellModel;
import com.sol.erp.ui.custom.SolProgressMonitor;
import com.sol.erp.ui.custom.SolSalaryCalc;
import com.sol.erp.ui.custom.SolTableModel;
import com.sol.erp.util.DBConnectionUtil;
import com.sol.erp.util.DateCalculationUtil;
import com.sol.erp.util.DateDifferencesUtil;
import com.sol.erp.util.SessionUtil;
import com.sol.erp.util.formater.SolDateFormatter;

public class EmpRegister implements ActionListener, MouseListener, FocusListener {
	
	ReqModeList res;
	
	SolSalaryCalc salCalc;
	SolProgressMonitor prmonitor;

	
	String coidstr;
	String brcodestr;
	Connection con;
	Statement stat;
	ResultSet rs;
	int affected;
	String coEmpId;
	String newNo;
	String[] columnNames;
	Object[][] data;
	String[] columnNames1;
	Object[][] data1;
	String[] columnNames2;
	Object[][] data2;
	String[] columnNames3;
	Object[][] data3;
	DecimalFormat df;
	DecimalFormat df1;
	JTabbedPane tabbedpane1;
	JTabbedPane tabbedpane2;
	JPanel salaryTab;
	JPanel extraTab;
	JPanel documentTab;
	JPanel selectedTab;
	JPanel EmpListTab;
	DefaultTableModel model;
	JTable tb;
	JScrollPane sp;
	DefaultTableModel model1;
	JTable tb1;
	JScrollPane sp1;
	DefaultTableModel model2;
	JTable tb2;
	JScrollPane sp2;
	DefaultTableModel model3;
	JTable tb3;
	JScrollPane sp3;
	JInternalFrame f;
	JLabel l1;
	JLabel l2;
	JLabel l3;
	JLabel l4;
	JLabel l5;
	JLabel jobtypelabel;
	JLabel usertypelabel;
	JLabel l6;
	JLabel coidlabel;
	JLabel brcodelabel;
	JLabel l7;
	JLabel l8;
	JLabel l9;
	JLabel l10;
	JLabel l11;
	JLabel l12;
	JLabel punchcardlabel;
	JLabel empcodelabel;
	JLabel searchLABEL;
	JLabel searchlabel;
	JLabel aplLABEL;
	JTextField punchcodetf;
	JTextField empcodetf;
	JLabel dateLABEL;
	JComboBox cb1;
	JTextField tf1;
	JTextField tf2;
	JTextField tf3;
	DeptList tf4;
	DesigList tf5;
	JTextField tf6;
	JComboBox jobtypecb;
	JComboBox usertypecb;
	JComboBox coidcb;
	JComboBox brcodecb;
	SalaryModeList tf7;
	public JTextField tf8;
	JTextField tf9;
	JTextField tf10;
	JTextField tf11;
	JTextField tf12;
	JTextField searchtf;
	JTextField aplTF;
	JTextField dateTF;
	java.util.Date dat;
	SimpleDateFormat formatter;
	String dateString;
	JButton newbut;
	JButton existcreatebut;
	JButton createbut;
	JButton updatebut;
	JButton deletebut;
	JButton activatebut;
	JButton removebut;
	JButton showbut;
	JButton showallbut;
	JButton printbut1;
	JButton printbut2;
	JButton closebut;
	JPanel northp1;
	JPanel northp2;
	JPanel northp3;
	JPanel registerbutpanel;
	JPanel registerpanel;
	JPanel butpanel1;
	JPanel butpanel2;
	JPanel centerpanel;
	JPanel northpanel;
	Font fo;
	Font fo2;
	Border line;
	Border bor1;
	Border bor2;
	Toolkit tk;
	Dimension ss;
	Dimension fs;

	SolCalendar skl;
	EmpRegister.ColoredTableCellRenderer skr;
	EmpRegister.ColoredTableCellRenderer2 skr2;
	JPanel mainpanel;
	Container c;
	JLabel rl1;
	JLabel rl2;
	JTextField rtf1;
	JTextField rtf2;
	JButton rb1;
	JButton rb2;
	JFrame rf;

	class ColoredTableCellRenderer extends DefaultTableCellRenderer {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		ColoredTableCellRenderer() {
		}

		public Component getTableCellRendererComponent(JTable paramJTable, Object paramObject, boolean paramBoolean1,
				boolean paramBoolean2, int paramInt1, int paramInt2) {
			setEnabled((paramJTable == null) || (paramJTable.isEnabled()));

			if (paramInt1 % 2 == 0) {
				setBackground(new Color(240, 240, 240));
				setForeground(Color.darkGray);
			} else {
				setBackground(new Color(250, 250, 250));
				setForeground(Color.darkGray);
			}

			if (paramInt2 == 0) {
				setBackground(new Color(120, 120, 200));
				setForeground(Color.white);
			}
			if (paramInt2 <= 2) {
				setHorizontalAlignment(2);
			}
			if (paramInt2 > 7) {
				setHorizontalAlignment(0);
				String str = String.valueOf(model.getValueAt(paramInt1, 10));

				if (str.equalsIgnoreCase("1")) {
					setBackground(new Color(120, 120, 200));
					setForeground(Color.white);
				}
				if (str.equalsIgnoreCase("2")) {
					setBackground(Color.orange);
					setForeground(Color.blue);
				}
			}

			if ((paramInt2 == 5) || (paramInt2 == 6)) {
				setHorizontalAlignment(4);
			}

			super.getTableCellRendererComponent(paramJTable, paramObject, paramBoolean1, paramBoolean2, paramInt1,
					paramInt2);

			return this;
		}
	}

	class ColoredTableCellRenderer2 extends DefaultTableCellRenderer {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		ColoredTableCellRenderer2() {
		}

		public Component getTableCellRendererComponent(JTable paramJTable, Object paramObject, boolean paramBoolean1,
				boolean paramBoolean2, int paramInt1, int paramInt2) {
			setEnabled((paramJTable == null) || (paramJTable.isEnabled()));

			if (paramInt1 % 2 == 0) {
				setBackground(new Color(240, 240, 240));
				setForeground(Color.darkGray);
			} else {
				setBackground(new Color(250, 250, 250));
				setForeground(Color.darkGray);
			}

			if ((paramInt2 >= 5) || (paramInt2 == 3)) {
				setHorizontalAlignment(4);
			}
			if ((paramInt2 == 0) || (paramInt2 == 2) || (paramInt2 == 4)) {
				setHorizontalAlignment(0);
			}
			if (paramInt2 == 1) {
				setHorizontalAlignment(2);
			}

			super.getTableCellRendererComponent(paramJTable, paramObject, paramBoolean1, paramBoolean2, paramInt1,
					paramInt2);

			return this;
		}
	}

	public JPanel DesignFrame() {
		c = f.getContentPane();
		c.setLayout(new BorderLayout());
		mainpanel.setLayout(new BorderLayout());
		northp1.setLayout(new GridLayout(10, 4, 2, 2));
		northp2.setLayout(new FlowLayout(0));
		northp3.setLayout(new FlowLayout(0));
		northpanel.setLayout(new BorderLayout());
		selectedTab.setLayout(new BorderLayout());
		EmpListTab.setLayout(new BorderLayout());
		salaryTab.setLayout(new BorderLayout());
		extraTab.setLayout(new BorderLayout());
		documentTab.setLayout(new BorderLayout());

		registerpanel.setLayout(new BorderLayout());
		registerbutpanel.setLayout(new FlowLayout(1));

		butpanel1.setLayout(new FlowLayout(0));
		butpanel2.setLayout(new FlowLayout(0));

		centerpanel.setLayout(new BorderLayout());

		northp1.setBorder(BorderFactory.createTitledBorder("Employee Joining Details"));

		tabbedpane1.setBackground(new Color(100, 100, 200));
		tabbedpane1.setForeground(Color.white);
		tabbedpane1.setFont(new Font("Verdana", 1, 11));
		tabbedpane1.setTabPlacement(1);
		tabbedpane1.setBorder(BorderFactory.createTitledBorder("Salary Structures"));
		registerbutpanel.setBorder(BorderFactory.createLineBorder(Color.gray));
		registerbutpanel.setPreferredSize(new Dimension(50, 50));
		registerpanel.setBackground(Color.white);

		cb1.addItem("Emp_Code");
		cb1.addItem("Punchcard_No");

		tabbedpane1.add("REGISTRATION", registerpanel);
		tabbedpane1.add("EMPLOYEE LIST", EmpListTab);

		tabbedpane2.add("EMPLOYEE DETAILS", tabbedpane1);
		tabbedpane2.add("SALARY STRUCTURE", salaryTab);
		tabbedpane2.add("EXTRA BENEFITES", extraTab);
		tabbedpane2.add("SELECTION REPORT", selectedTab);
		tabbedpane2.setTabPlacement(3);

		salaryTab.add(sp2, "Center");
		sp2.setPreferredSize(new Dimension(100, 100));
		extraTab.add(sp3, "Center");
		sp3.setPreferredSize(new Dimension(100, 100));

		jobtypecb.addItem("DETAILING");
		jobtypecb.addItem("CHECKING");
		jobtypecb.addItem("OTHER");

		usertypecb.addItem("USER");
		usertypecb.addItem("ADMIN");

		EmpListTab.add(sp, "Center");
		EmpListTab.add(butpanel2, "North");

		selectedTab.add(sp1, "Center");
		selectedTab.add(butpanel1, "North");

		northp1.add(coidlabel);
		northp1.add(coidcb);
		northp1.add(brcodelabel);
		northp1.add(brcodecb);
		northp1.add(new JPanel());
		northp1.add(new JPanel());
		northp1.add(new JPanel());
		northp1.add(new JPanel());
		northp1.add(new JPanel());
		northp1.add(new JPanel());
		northp1.add(new JPanel());
		northp1.add(new JPanel());
		northp1.add(l1);
		northp1.add(tf1);
		northp1.add(l2);
		northp1.add(tf2);
		northp1.add(l3);
		northp1.add(tf3);
		northp1.add(new JPanel());
		northp1.add(new JPanel());
		northp1.add(l4);
		northp1.add(tf4);
		northp1.add(l5);
		northp1.add(tf5);
		northp1.add(jobtypelabel);
		northp1.add(jobtypecb);
		northp1.add(usertypelabel);
		northp1.add(usertypecb);
		northp1.add(l7);
		northp1.add(tf7);
		northp1.add(l8);
		northp1.add(tf8);
		northp1.add(l6);
		northp1.add(tf6);
		northp1.add(l9);
		northp1.add(tf9);

		northp1.add(punchcardlabel);
		northp1.add(punchcodetf);
		northp1.add(empcodelabel);
		northp1.add(empcodetf);

		registerbutpanel.add(newbut);
		createbut.setMnemonic(78);
		registerbutpanel.add(createbut);
		createbut.setMnemonic(83);
		registerbutpanel.add(activatebut);
		activatebut.setMnemonic(65);
		registerbutpanel.add(updatebut);
		updatebut.setMnemonic(85);
		registerbutpanel.add(deletebut);
		deletebut.setMnemonic(68);
		registerbutpanel.add(removebut);
		removebut.setMnemonic(82);

		registerpanel.add(northp1, "North");
		registerpanel.add(registerbutpanel, "Center");

		butpanel1.add(aplLABEL);
		butpanel1.add(aplTF);
		butpanel1.add(showbut);
		showbut.setMnemonic(87);
		butpanel1.add(printbut2);
		printbut2.setMnemonic(80);

		butpanel2.add(searchlabel);
		butpanel2.add(searchtf);
		searchtf.setFont(fo);
		butpanel2.add(showallbut);
		butpanel2.add(printbut1);
		searchtf.setToolTipText(
				"<html><BODY BGCOLOR='WHITE'><Font color='red' face='verdana' size='3'><B>Type : EMPCODE, Initial of NAME, PUNCHCARD NO.");
		showallbut.setToolTipText(
				"<html><BODY BGCOLOR='WHITE'><Font color='red' face='verdana' size='3'><B>Type : EMPCODE, Initial of NAME, PUNCHCARD NO.");

		SolTableModel.decorateTable(tb);
		SolTableModel.decorateTable(tb1);
		SolTableModel.decorateTable(tb2);
		SolTableModel.decorateTable(tb3);

		tb.setFont(fo);
		tb1.setFont(fo);
		tb2.setFont(fo);
		tb2.setFont(fo);

		tb.setShowGrid(false);
		tb1.setShowGrid(false);
		tb2.setShowGrid(false);
		tb3.setShowGrid(false);

		tb.setIntercellSpacing(new Dimension(1, 1));
		tb1.setIntercellSpacing(new Dimension(1, 1));
		tb2.setIntercellSpacing(new Dimension(1, 1));
		tb3.setIntercellSpacing(new Dimension(1, 1));

		for (int i = 0; i < 7; i++) {
			tb.getColumnModel().getColumn(i).setCellRenderer(skr);
		}
		for (int i = 8; i < tb.getColumnCount(); i++) {
			tb.getColumnModel().getColumn(i).setCellRenderer(skr);
		}

		for (int i = 0; i < tb1.getColumnCount(); i++) {
			tb1.getColumnModel().getColumn(i).setCellRenderer(skr2);
		}

		tb.setRowHeight(22);
		tb.getTableHeader().setPreferredSize(new Dimension(30, 30));
		tb.getTableHeader().setBackground(new Color(200, 120, 200));
		tb.getTableHeader().setForeground(Color.white);

		tb.getColumnModel().getColumn(0).setPreferredWidth(140);
		tb.getColumnModel().getColumn(1).setPreferredWidth(80);
		tb.getColumnModel().getColumn(2).setPreferredWidth(120);
		tb.getColumnModel().getColumn(7).setPreferredWidth(20);
		tb.getColumnModel().getColumn(10).setPreferredWidth(30);

		tb1.setAutoResizeMode(0);
		tb1.setRowHeight(25);
		tb1.getTableHeader().setPreferredSize(new Dimension(30, 30));
		tb1.getTableHeader().setBackground(new Color(200, 120, 200));
		tb1.getTableHeader().setForeground(Color.white);

		tb1.getColumnModel().getColumn(0).setPreferredWidth(80);
		tb1.getColumnModel().getColumn(1).setPreferredWidth(220);
		tb1.getColumnModel().getColumn(2).setPreferredWidth(110);
		tb1.getColumnModel().getColumn(3).setPreferredWidth(80);
		tb1.getColumnModel().getColumn(4).setPreferredWidth(140);
		tb1.getColumnModel().getColumn(5).setPreferredWidth(40);
		tb1.getColumnModel().getColumn(6).setPreferredWidth(60);
		tb1.getColumnModel().getColumn(7).setPreferredWidth(60);
		tb1.getColumnModel().getColumn(8).setPreferredWidth(110);
		tb1.getColumnModel().getColumn(9).setPreferredWidth(100);

		centerpanel.add(tabbedpane2, "Center");

		mainpanel.add(centerpanel, "Center");
		c.add(mainpanel, "Center");

		existcreatebut.addActionListener(this);
		newbut.addActionListener(this);
		createbut.addActionListener(this);
		activatebut.addActionListener(this);
		showbut.addActionListener(this);
		showallbut.addActionListener(this);
		printbut1.addActionListener(this);
		printbut2.addActionListener(this);
		updatebut.addActionListener(this);
		deletebut.addActionListener(this);
		removebut.addActionListener(this);
		closebut.addActionListener(this);

		tf1.addActionListener(this);
		tf2.addActionListener(this);
		tf3.addActionListener(this);
		tf4.addActionListener(this);
		tf5.addActionListener(this);
		tf6.addActionListener(this);
		tf7.addActionListener(this);
		tf8.addActionListener(this);

		tf1.addMouseListener(this);
		tf2.addMouseListener(this);
		tf3.addMouseListener(this);
		tf4.addMouseListener(this);
		tf5.addMouseListener(this);
		tf6.addMouseListener(this);
		tf7.addMouseListener(this);
		tf8.addMouseListener(this);
		tf9.addMouseListener(this);
		tf10.addMouseListener(this);

		tf7.addFocusListener(this);
		tf8.addFocusListener(this);

		aplTF.addActionListener(this);
		dateTF.addMouseListener(this);
		searchtf.addActionListener(this);
		tb.addMouseListener(this);
		tb1.addMouseListener(this);
		coidcb.addActionListener(this);
		brcodecb.addActionListener(this);

		f.setSize(920, 660);
		f.setVisible(true);
		showCompanyList();
		probationDate();

		l1.setHorizontalAlignment(4);
		l1.setForeground(Color.gray);
		l2.setHorizontalAlignment(4);
		l2.setForeground(Color.gray);
		l3.setHorizontalAlignment(4);
		l3.setForeground(Color.gray);
		l4.setHorizontalAlignment(4);
		l4.setForeground(Color.gray);
		l5.setHorizontalAlignment(4);
		l5.setForeground(Color.gray);
		l6.setHorizontalAlignment(4);
		l6.setForeground(Color.gray);
		l7.setHorizontalAlignment(4);
		l7.setForeground(Color.gray);
		l8.setHorizontalAlignment(4);
		l8.setForeground(Color.gray);
		l9.setHorizontalAlignment(4);
		l9.setForeground(Color.gray);
		l10.setHorizontalAlignment(4);
		l10.setForeground(Color.gray);
		l11.setHorizontalAlignment(4);
		l11.setForeground(Color.gray);
		l12.setHorizontalAlignment(4);
		l12.setForeground(Color.gray);

		punchcardlabel.setHorizontalAlignment(4);
		empcodelabel.setHorizontalAlignment(4);

		jobtypelabel.setHorizontalAlignment(4);
		usertypelabel.setHorizontalAlignment(4);
		coidlabel.setHorizontalAlignment(4);
		brcodelabel.setHorizontalAlignment(4);
		searchLABEL.setHorizontalAlignment(4);
		searchlabel.setHorizontalAlignment(4);
		aplLABEL.setHorizontalAlignment(4);
		punchcodetf.setHorizontalAlignment(4);
		empcodetf.setHorizontalAlignment(4);
		dateLABEL.setHorizontalAlignment(4);

		jobtypelabel.setForeground(Color.gray);
		usertypelabel.setForeground(Color.gray);
		coidlabel.setForeground(Color.gray);
		brcodelabel.setForeground(Color.gray);
		searchLABEL.setForeground(Color.gray);
		searchlabel.setForeground(Color.gray);

		punchcardlabel.setForeground(Color.gray);
		empcodelabel.setForeground(Color.gray);
		dateLABEL.setForeground(Color.gray);

		newbut.setBackground(new Color(200, 120, 200));
		newbut.setForeground(Color.white);
		newbut.setFont(fo2);

		createbut.setBackground(new Color(200, 120, 200));
		createbut.setForeground(Color.white);
		createbut.setFont(fo2);

		updatebut.setBackground(new Color(200, 120, 200));
		updatebut.setForeground(Color.white);
		updatebut.setFont(fo2);

		deletebut.setEnabled(false);
		deletebut.setBackground(new Color(200, 120, 200));
		deletebut.setForeground(Color.white);
		deletebut.setFont(fo2);

		activatebut.setBackground(new Color(200, 120, 200));
		activatebut.setForeground(Color.white);
		activatebut.setFont(fo2);

		removebut.setBackground(new Color(200, 120, 200));
		removebut.setForeground(Color.white);
		removebut.setFont(fo2);

		closebut.setBackground(Color.red);
		closebut.setForeground(Color.white);
		closebut.setFont(fo2);

		showallbut.setBackground(new Color(200, 120, 200));
		showallbut.setForeground(Color.white);
		showallbut.setFont(fo2);

		printbut1.setBackground(new Color(200, 120, 200));
		printbut1.setForeground(Color.white);
		printbut1.setFont(fo2);

		showbut.setBackground(new Color(200, 120, 200));
		showbut.setForeground(Color.white);
		showbut.setFont(fo2);

		printbut2.setBackground(new Color(120, 190, 120));
		printbut2.setForeground(Color.white);
		printbut2.setFont(fo2);

		tf1.setFont(fo2);
		tf2.setFont(fo2);
		tf3.setFont(fo2);
		tf4.setFont(fo2);
		tf4.setEditable(false);
		tf5.setFont(fo2);
		tf5.setEditable(false);
		tf6.setFont(fo2);
		tf6.setEditable(false);
		tf7.setFont(fo2);
		tf7.setEditable(false);
		tf8.setFont(fo2);
		tf9.setFont(fo2);
		tf9.setEditable(false);
		tf10.setFont(fo2);
		tf11.setFont(fo2);
		tf12.setFont(fo2);
		jobtypecb.setFont(fo2);
		usertypecb.setFont(fo2);
		coidcb.setFont(fo2);
		brcodecb.setFont(fo2);
		searchtf.setFont(fo2);
		aplTF.setFont(fo2);
		dateTF.setFont(fo2);
		dateLABEL.setFont(fo);
		aplLABEL.setFont(fo);

		tf8.setHorizontalAlignment(4);
		tf8.setFont(new Font("Times New Roman", 1, 20));
		tf8.setForeground(Color.blue);
		empcodetf.setFont(new Font("Times New Roman", 1, 20));
		punchcodetf.setFont(new Font("Times New Roman", 1, 20));
		empcodetf.setForeground(new Color(200, 120, 200));
		empcodetf.setBackground(Color.white);
		punchcodetf.setForeground(new Color(200, 120, 200));
		jobtypecb.setBackground(Color.white);
		usertypecb.setBackground(Color.white);

		coidcb.setBackground(Color.white);
		brcodecb.setBackground(Color.white);

		empcodetf.setEditable(false);

		return mainpanel;
	}

	public EmpRegister() {
		res = new ReqModeList();
		salCalc = new SolSalaryCalc();
		prmonitor = new SolProgressMonitor();


		coidstr = SessionUtil.getLoginInfo().getCompanyId();
		brcodestr = SessionUtil.getLoginInfo().getBranchCode();

		affected = 0;

		coEmpId = "S";
		newNo = "";

		columnNames = new String[] { "NAME", "DEPARTMENT", "DESIGNATION", "DOJ", "SALARY MODE", "SALARY", "PROBATION",
				"OT", "Punch Card", "Emp Code", "Staus" };
		data = new Object[0][];

		columnNames1 = new String[] { "APLICANT NO.", "NAME", "POST", "SALARY", "SALARY MODEL", "OT", "PROB.", "FACTOR",
				"<HTML><CENTER>DATE OF<BR>INTERVIEW", "STATUS" };
		data1 = new Object[0][];

		columnNames2 = new String[] { "Heads", "Type", "Base", "Percent", "Amount" };
		data2 = new Object[0][];

		columnNames3 = new String[] { "Heads", "Rate", "Value", "Total" };
		data3 = new Object[0][];

		df = new DecimalFormat("0000");
		df1 = new DecimalFormat("00");

		tabbedpane1 = new JTabbedPane();
		tabbedpane2 = new JTabbedPane();

		salaryTab = new JPanel();
		extraTab = new JPanel();
		documentTab = new JPanel();

		selectedTab = new JPanel();
		EmpListTab = new JPanel();

		model = new DefaultTableModel(data, columnNames);
		tb = new JTable(model) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@SuppressWarnings({ "unchecked", "rawtypes" })
			public Class getColumnClass(int paramAnonymousInt) {
				return getValueAt(0, paramAnonymousInt).getClass();
			}

			public boolean isCellEditable(int paramAnonymousInt1, int paramAnonymousInt2) {
				int i = convertColumnIndexToModel(paramAnonymousInt2);
				return i < 0;
			}
		};
		sp = new JScrollPane(tb);

		model1 = new DefaultTableModel(data1, columnNames1);
		tb1 = new JTable(model1);
		sp1 = new JScrollPane(tb1);

		model2 = new DefaultTableModel(data2, columnNames2);
		tb2 = new JTable(model2);
		sp2 = new JScrollPane(tb2);

		model3 = new DefaultTableModel(data3, columnNames3);
		tb3 = new JTable(model3);
		sp3 = new JScrollPane(tb3);

		f = new JInternalFrame("Joining Details [Emp Register]", true, true, true, true);
		l1 = new JLabel("First Name");
		l2 = new JLabel("Middle Name");
		l3 = new JLabel("Last Name");
		l4 = new JLabel("Department");
		l5 = new JLabel("Designation");
		jobtypelabel = new JLabel("Technical Role");
		usertypelabel = new JLabel("Authentication Type");
		l6 = new JLabel("Date of Joining");
		coidlabel = new JLabel("Company ID");
		brcodelabel = new JLabel("Branch Code");
		l7 = new JLabel("Salary Mode");
		l8 = new JLabel("Salary");
		l9 = new JLabel("Prob Period");
		l10 = new JLabel("OT APL");
		l11 = new JLabel("Factor");
		l12 = new JLabel("AVG Hrs");

		punchcardlabel = new JLabel("Punchcard No");
		empcodelabel = new JLabel("Employee Code");
		searchLABEL = new JLabel("Search By");
		searchlabel = new JLabel("SEARCH");
		aplLABEL = new JLabel("APLICANT NO.");
		punchcodetf = new JTextField("S0000", 6);
		empcodetf = new JTextField("", 5);
		dateLABEL = new JLabel("DATE OF INTERVIEW");

		cb1 = new JComboBox();
		tf1 = new JTextField();
		tf2 = new JTextField();
		tf3 = new JTextField();
		tf4 = new DeptList("C01", "0");
		tf5 = new DesigList("C01", "0", String.valueOf(tf4.getSelectedItem()));
		tf6 = new JTextField();

		jobtypecb = new JComboBox();
		usertypecb = new JComboBox();
		coidcb = new JComboBox();
		brcodecb = new JComboBox();
		tf7 = new SalaryModeList("C01", "0");
		tf8 = new JTextField(8);
		tf9 = new JTextField(10);

		tf10 = new JTextField("false", 3);
		tf11 = new JTextField(".2", 3);
		tf12 = new JTextField("9", 3);

		searchtf = new JTextField(26);
		aplTF = new JTextField(16);
		dateTF = new JTextField(8);

		dat = new java.util.Date();
		formatter = new SimpleDateFormat("dd/MM/yyyy");

		try {
			dateString = formatter.format(dat);
			tf6 = new JTextField(String.valueOf(dateString));
		} catch (IllegalArgumentException localIllegalArgumentException) {
			System.out.println(localIllegalArgumentException.getMessage());
		}

		newbut = new JButton("NEW ENTRY");
		existcreatebut = new JButton("Save Existed Emp");
		createbut = new JButton("CREATE");
		updatebut = new JButton("UPDATE");
		deletebut = new JButton("DELETE");
		activatebut = new JButton("ACTIVATE");
		removebut = new JButton("RELEAVE");

		showbut = new JButton("SEARCH CANDIDATE");
		showallbut = new JButton("SEARCH");
		printbut1 = new JButton("PRINT");
		printbut2 = new JButton("PRINT");
		closebut = new JButton("CLOSE");

		northp1 = new JPanel();
		northp2 = new JPanel();
		northp3 = new JPanel();

		registerbutpanel = new JPanel();
		registerpanel = new JPanel();
		butpanel1 = new JPanel();
		butpanel2 = new JPanel();

		centerpanel = new JPanel();
		northpanel = new JPanel();

		fo = new Font("Tahoma", 1, 9);
		fo2 = new Font("Verdana", 1, 10);
		line = BorderFactory.createLineBorder(Color.darkGray);
		bor1 = BorderFactory.createTitledBorder(line, "Employee Details", 0, 0, fo, Color.darkGray);
		bor2 = BorderFactory.createTitledBorder(line, "List of New Emps", 0, 0, fo, Color.darkGray);

		tk = Toolkit.getDefaultToolkit();
		ss = tk.getScreenSize();
		fs = f.getSize();


		skl = new SolCalendar();

		skr = new EmpRegister.ColoredTableCellRenderer();
		skr2 = new EmpRegister.ColoredTableCellRenderer2();

		mainpanel = new JPanel();

		rl1 = new JLabel("Emp Code");
		rl2 = new JLabel("Date ");
		rtf1 = new JTextField(10);
		rtf2 = new JTextField(10);
		rb1 = new JButton("Releave");
		rb2 = new JButton("Cancel");
		rf = new JFrame("Releave Employee");
	}

	public void removeFrame() {
		Container localContainer = rf.getContentPane();
		localContainer.setLayout(new GridLayout(3, 2, 2, 2));
		localContainer.add(rl1);
		localContainer.add(rtf1);
		localContainer.add(rl2);
		localContainer.add(rtf2);
		localContainer.add(rb1);
		localContainer.add(rb2);

		rtf1.setEditable(false);

		rb1.addActionListener(this);
		rb2.addActionListener(this);

		rtf1.addActionListener(this);
		rtf2.addActionListener(this);
		rtf2.addMouseListener(this);

		rf.pack();
		rf.setResizable(false);
		rf.setLocationRelativeTo(null);
		rf.setVisible(true);
	}

	public void probationDate() {
		int i = DateCalculationUtil.getDate(tf6.getText());
		int j = DateCalculationUtil.getMonth(tf6.getText()) + 3;
		int k = DateCalculationUtil.getYear(tf6.getText());

		String str = String.valueOf(df1.format(i)) + "/" + String.valueOf(df1.format(j)) + "/" + String.valueOf(k);

		tf9.setText(str);
	}

	public void showAll() {
		String str1 = SessionUtil.getLoginInfo().getCompanyId();
		String str2 = SessionUtil.getLoginInfo().getBranchCode();
		model.setNumRows(0);
		String.valueOf(cb1.getSelectedItem());
		String str4 = "%" + searchtf.getText();

		try {
			con = DBConnectionUtil.getConnection(); 
			Statement localStatement = con.createStatement();
			ResultSet localResultSet = localStatement.executeQuery(
					"select full_name, Dept, Desig, Doj, Req_Mode, Basic_Salary, Prob, ot, PunchCard_no, Emp_Code, Status from HREMP_JOIN  where co_id ='"
							+ str1 + "' and br_code='" + str2 + "' and PUNCHCARD_NO like '" + str4
							+ "' or EMP_CODE like'" + str4 + "' or FIRST_NAME like '" + str4 + "' or DESIG like '"
							+ str4 + "' or REQ_MODE like '" + str4 + "' or CO_ID like '" + str4 + "' or DOJ like '"
							+ str4 + "' or BASIC_SALARY like '" + str4 + "' or OT like '" + str4 + "'");
			while (localResultSet.next()) {
				String str5 = new String(localResultSet.getString(1));
				String str6 = new String(localResultSet.getString(2));
				String str7 = new String(localResultSet.getString(3));
				String str8 = new String(localResultSet.getString(4));
				String str9 = new String(localResultSet.getString(5));
				String str10 = new String(localResultSet.getString(6));
				String str11 = new String(localResultSet.getString(7));
				String str12 = new String(localResultSet.getString(8));
				String str13 = new String(localResultSet.getString(9));
				String str14 = new String(localResultSet.getString(10));
				String str15 = new String(localResultSet.getString(11));

				model.addRow(new Object[] { str5.toUpperCase(), str6.toUpperCase(), str7, str8, str9, str10, str11,
						new Boolean(str12), str13, str14, str15 });
				System.out.println(str5 + "\t" + str13);
			}

			searchlabel.setText("Fetched Record : " + String.valueOf(tb.getRowCount()));
		} catch (Exception localException) {
			System.out.println(localException);
		}
	}

	public void showCompanyList() {
		String branchCode = SessionUtil.getLoginInfo().getBranchCode();
		model.setNumRows(0);
		String.valueOf(cb1.getSelectedItem());
		searchtf.getText();
		coidcb.removeAllItems();

		try {
			con = DBConnectionUtil.getConnection();
			Statement localStatement = con.createStatement();
			ResultSet localResultSet = localStatement
					.executeQuery("select company_id from HRCOMPANY_ID WHERE Br_code ='"+branchCode+"' ");
			while (localResultSet.next()) {
				String str5 = new String(localResultSet.getString(1));
				coidcb.addItem(str5);
			}
		} catch (Exception localException) {
			System.out.println(localException);
		}
	}

	public void showBranchList() {
		String str1 = String.valueOf(coidcb.getSelectedItem());
		brcodecb.removeAllItems();

		try {
			con = DBConnectionUtil.getConnection();
			Statement localStatement = con.createStatement();
			ResultSet localResultSet = localStatement
					.executeQuery("select Br_code from HRCOMPANY_ID where Company_id = '" + str1 + "' ");
			while (localResultSet.next()) {
				String str2 = new String(localResultSet.getString(1));
				brcodecb.addItem(str2);
			}
		} catch (Exception localException) {
			System.out.println(localException);
		}
	}

	public void singleShow() {
		String str3 = String.valueOf(empcodetf.getText());
		ResultSet localResultSet;
		String str4;
		String str5;
		try {
			con = DBConnectionUtil.getConnection();
			Statement localStatement1 = con.createStatement();
			localResultSet = localStatement1.executeQuery(
					"select first_name, middle_name, last_name, Dept, Desig, Doj, Req_Mode, Basic_Salary, Prob, ot, PunchCard_no, Emp_Code from HREMP_JOIN  where emp_code='"
							+ str3 + "' ");
			while (localResultSet.next()) {
				str4 = new String(localResultSet.getString(1));
				str5 = new String(localResultSet.getString(2));
				String str6 = new String(localResultSet.getString(3));
				String str7 = new String(localResultSet.getString(4));
				String str8 = new String(localResultSet.getString(5));
				String str9 = new String(localResultSet.getString(6));
				String str10 = new String(localResultSet.getString(7));
				String str11 = new String(localResultSet.getString(8));
				String str12 = new String(localResultSet.getString(9));
				String str13 = new String(localResultSet.getString(10));
				new String(localResultSet.getString(11));
				new String(localResultSet.getString(12));

				tf1.setText(str4);
				tf2.setText(str5);
				tf3.setText(str6);
				tf4.setText(str7);
				tf5.setText(str8);
				tf6.setText(str9);
				tf7.setText(str10);
				tf8.setText(str11);
				tf9.setText(str12);
				tf10.setText(str13);
			}
		} catch (Exception localException1) {
			System.out.println(localException1);
		}
		try {
			con = DBConnectionUtil.getConnection();
			Statement localStatement2 = con.createStatement();
			localResultSet = localStatement2
					.executeQuery("select factor, average_hrs from EMP_STATUS where emp_code='" + str3 + "' ");
			while (localResultSet.next()) {
				str4 = new String(localResultSet.getString(1));
				str5 = new String(localResultSet.getString(2));
				tf11.setText(str4);
				tf12.setText(str5);
			}
		} catch (Exception localException2) {
			System.out.println(localException2);
		}
	}

	public void getRecord() {
		try {
			con = DBConnectionUtil.getConnection();
			stat = con.createStatement();
			rs = stat.executeQuery("select * from HRINTERVIEW_CALL where APLICANT_No = '" + aplTF.getText() + "'   ");
			while (rs.next()) {
				new String(rs.getString(1));
				String str2 = new String(rs.getString(2));
				String str3 = new String(rs.getString(3));
				String str4 = new String(rs.getString(4));
				new String(rs.getString(5));
				new String(rs.getString(6));
				new String(rs.getString(7));
				new String(rs.getString(8));
				new String(rs.getString(9));
				new String(rs.getString(10));

				tf1.setText(str2);
				tf2.setText(str3);
				tf3.setText(str4);
			}
		} catch (Exception localException) {
			System.out.println(localException);
		}
	}

	public void punchCard() {
		try {
			con = DBConnectionUtil.getConnection();
			stat = con.createStatement();
			rs = stat.executeQuery("select max(punchcard_no) from punchcard where co_id='" + coidstr
					+ "' and branch_code='" + brcodestr + "' ");
			while (rs.next()) {
				String str1 = rs.getString(1);
				String str2 = str1.substring(1, str1.length());
				int i = Integer.parseInt(str2) + 1;
				newNo = (coEmpId + String.valueOf(df.format(i)));

				punchcodetf.setText(newNo);
			}
		} catch (Exception localException) {
			System.out.println(localException);
		}
	}

	public void makeCode() {
		model.setNumRows(0);
		String str1 = "";
		String str2 = "";
		String str3 = "";
		str1 = tf1.getText();
		str2 = tf2.getText();
		str3 = tf3.getText();
		String str4 = str1 + str2 + str3;
		String str5 = "";

		String str9 = String.valueOf(coidcb.getSelectedItem());
		String str10 = String.valueOf(brcodecb.getSelectedItem());

		java.sql.Date localDate = SolDateFormatter.DDMMYYtoSQL(tf6.getText());

		int i = str4.length();
		String str6 = str4.substring(0, 1);

		for (int j = 1; j < i; j++) {
			String str7 = str4.substring(j, j + 1);

			for (int k = 2; k < i; k++) {
				String str8 = str4.substring(k, k + 1);
				str5 = str6.toUpperCase() + str7.toUpperCase() + str8.toUpperCase();
				empcodetf.setText(str5);

				try {
					con = DBConnectionUtil.getConnection();
					PreparedStatement localPreparedStatement = con.prepareStatement("insert into HREMP_JOIN values('"
							+ tf1.getText() + "','" + tf2.getText() + "','" + tf3.getText() + "','" + tf4.getText()
							+ "','" + tf5.getText() + "','" + tf6.getText() + "','" + tf7.getText() + "','"
							+ tf8.getText() + "','" + tf9.getText() + "','" + tf10.getText() + "','"
							+ punchcodetf.getText() + "','" + str5 + "','" + str9 + "','" + str10 + "','" + str1 + " "
							+ str2 + " " + str3 + "','2',?  )  ");
					localPreparedStatement.setDate(1, localDate);
					affected = localPreparedStatement.executeUpdate();

					if (affected > 0) {
						empcodetf.setText(str5);
						model.addRow(new Object[] { tf1.getText() + " " + tf2.getText() + " " + tf3.getText(),
								tf4.getText(), tf5.getText(), tf6.getText(), tf7.getText(), tf8.getText(),
								tf9.getText(), new Boolean(false), punchcodetf.getText(), str5, "2" });
						JOptionPane.showMessageDialog(f, "Code Generated As : " + str5);
						j = i;
						break;
					}
				} catch (Exception localException) {
					System.out.println(localException);
				}
			}
		}
	}

	public void activate() {
		String str1 = String.valueOf(coidcb.getSelectedItem());
		String str2 = String.valueOf(brcodecb.getSelectedItem());
		String str3 = String.valueOf(jobtypecb.getSelectedItem());
		String str4 = tf4.getText();
		String str5 = tf5.getText();
		String str6 = SessionUtil.getLoginInfo().getCompanyId();
		String str7 = SessionUtil.getLoginInfo().getBranchCode();
		String str8 = String.valueOf(empcodetf.getText());
		java.sql.Date localDate = SolDateFormatter.DDMMYYtoSQL(tf6.getText());

		int i = tb.getSelectedRow();
		if (i == 0) {
			try {

				con = DBConnectionUtil.getConnection();
				PreparedStatement localPreparedStatement = con.prepareStatement(
						"insert into EMP_STATUS values('" + str8 + "','" + tf1.getText() + " " + tf2.getText() + " "
								+ tf3.getText() + "','" + tf11.getText() + "','" + str1 + "','" + str2
								+ "','9','0','0','" + tf5.getText() + "','" + str3 + "','" + tf4.getText() + "')  ");
				affected = localPreparedStatement.executeUpdate();
				affected = localPreparedStatement.executeUpdate("insert into PUNCHCARD values('" + str8 + "','"
						+ tf1.getText() + " " + tf2.getText() + " " + tf3.getText() + "','" + punchcodetf.getText()
						+ "','" + str1 + "','" + str7 + "', '1')  ");
				affected = localPreparedStatement
						.executeUpdate("insert into HR_OT_PERMISSION values('" + str1 + "','" + str7 + "', '" + str8
								+ "','" + tf4.getText() + "','" + tf5.getText() + "','" + localDate + "', 'false') ");
				affected = localPreparedStatement
						.executeUpdate("insert into PHONE values('" + str8 + "','','','2A', '000','','') ");
				affected = localPreparedStatement
						.executeUpdate("insert into PASSWORD values('" + str8 + "','Default','User','" + tf4.getText()
								+ "','" + tf5.getText() + "','001','" + str6 + "','" + str7 + "')  ");
				affected = localPreparedStatement.executeUpdate("insert into HR_OFF_DAYS values('" + str6 + "', '"
						+ str7 + "', '" + str4 + "', '" + str5 + "', '" + str8
						+ "','1',  '1',  '1',  '1',  '1', '1', '1', 'P',  'P',  'P',  'P',  'P', 'P', 'P', 'true')  ");

				if (tb1.getRowCount() > 0) {
					affected = localPreparedStatement
							.executeUpdate("UPDATE HR_CEO_ROUNDS set Employblity='Employed' where aplicant_no='"
									+ aplTF.getText() + "' ");
					model1.setValueAt("Employed", tb1.getSelectedRow(), 9);
				}
				tabbedpane2.setSelectedIndex(1);
			} catch (Exception localException) {
				JOptionPane.showMessageDialog(f, localException.getMessage().toString());

			}

		} else {
			JOptionPane.showMessageDialog(f, "No Record Selected to Activate.");
		}
	}

	public void redirectAttendance() {
		ArrayList localArrayList = new ArrayList();

		int i = DateCalculationUtil.getMonth(tf6.getText());
		int j = DateCalculationUtil.getYear(tf6.getText());
		String str1 = "01/" + String.valueOf(df1.format(i)) + "/" + String.valueOf(j);
		String str2 = String.valueOf(tf6.getText());

		int k = DateDifferencesUtil.getDayCount(str1, str2);

		localArrayList = DateDifferencesUtil.getDate(str1, str2, k);

		String str3 = String.valueOf(coidcb.getSelectedItem());
		String str4 = String.valueOf(brcodecb.getSelectedItem());
		String str5 = String.valueOf(empcodetf.getText());

		for (int m = 0; m < k; m++) {
			System.out.println("Date : " + localArrayList.get(m));

			String str6 = String.valueOf(localArrayList.get(m));
			java.sql.Date localDate = SolDateFormatter.DDMMYYtoSQL(str6);

			try {
				PreparedStatement localPreparedStatement = con
						.prepareStatement("insert into HREMP_LEAVES values('" + str3 + "','" + str4 + "', '" + str5
								+ "', ?,'LWP','ABSENT COUNT', '0','true','true','" + tf6.getText() + "') ");
				localPreparedStatement.setDate(1, localDate);
				localPreparedStatement.executeUpdate();
			} catch (Exception localException) {
				System.out.println("HREMP_LEAVES : " + localException);
			}
		}
	}

	public void getResult() {
		model1.setNumRows(0);
		String str1 = "%" + aplTF.getText();
		String str2 = "%" + dateTF.getText();

		try {
			con = DBConnectionUtil.getConnection();
			stat = con.createStatement();
			rs = stat.executeQuery("select * from HR_CEO_ROUNDS where aplicant_no like '" + str1 + "' and date like '"
					+ str2 + "' and employblity='Selected' ");

			while (rs.next()) {
				new String(rs.getString(1));
				new String(rs.getString(2));
				String str5 = new String(rs.getString(3));
				String str6 = new String(rs.getString(4));
				String str7 = new String(rs.getString(5));
				String str8 = new String(rs.getString(6));
				String str9 = new String(rs.getString(7));
				String str10 = new String(rs.getString(8));
				new String(rs.getString(9));
				String str12 = new String(rs.getString(10));
				String str13 = new String(rs.getString(11));
				String str14 = new String(rs.getString(12));
				String str15 = new String(rs.getString(13));
				new String(rs.getString(14));

				model1.addRow(new Object[] { str5, str6.toUpperCase(), str9.toUpperCase(), str8, str7, str13, str14,
						str15, str12, str10.toUpperCase() });
			}
		} catch (Exception localException) {
			System.out.println("getAplicantName : " + localException);
		}
	}

	public void salaryMatter() {
		salCalc.prepareSalary();

		salCalc.setSalaryMode(String.valueOf(tf7.getText()));
		salCalc.setInhand(String.valueOf(tf8.getText()));
		salCalc.salaryCalc();
		data2 = salCalc.getSalaryDetails();
		tb2.setModel(model2 = new DefaultTableModel(data2, columnNames2));

		data3 = salCalc.getExtraBenefites();
		tb3.setModel(model3 = new DefaultTableModel(data3, columnNames3));

		for (int i = 0; i < tb2.getColumnCount(); i++) {
			tb2.getColumnModel().getColumn(i).setCellRenderer(new SolCellModel());
		}

		for (int i = 0; i < tb3.getColumnCount(); i++) {
			tb3.getColumnModel().getColumn(i).setCellRenderer(new SolCellModel());
		}
	}

	public void saveEmpSalary() {
		String str1 = SessionUtil.getLoginInfo().getCompanyId();
		String str2 = SessionUtil.getLoginInfo().getBranchCode();
		String str3 = empcodetf.getText();

		for (int i = 0; i < tb2.getRowCount(); i++) {
			String str4 = String.valueOf(model2.getValueAt(i, 0));
			String str5 = String.valueOf(model2.getValueAt(i, 1));
			String str6 = String.valueOf(model2.getValueAt(i, 2));
			String str7 = String.valueOf(model2.getValueAt(i, 3));
			String str8 = String.valueOf(model2.getValueAt(i, 4));

			try {
				con = DBConnectionUtil.getConnection();
				stat = con.createStatement();
				stat.executeUpdate("insert into HR_EMP_SALARY values('" + str1 + "','" + str2 + "','" + str3 + "','"
						+ tf7.getText() + "', '" + str4 + "', '" + str5 + "', '" + str6 + "', '" + str7 + "','" + str8
						+ "')  ");
			} catch (Exception localException) {
				System.out.println("************************************");
				System.out.println("saveEmpSalary(HR_EMP_SALARY): " + localException);
				System.out.println("************************************");
			}
		}
	}

	public void saveExtraSalary() {
		String str1 = SessionUtil.getLoginInfo().getCompanyId();
		String str2 = SessionUtil.getLoginInfo().getBranchCode();
		String str3 = empcodetf.getText();

		for (int i = 0; i < tb3.getRowCount(); i++) {
			String str4 = String.valueOf(model3.getValueAt(i, 0));
			String str5 = String.valueOf(model3.getValueAt(i, 1));
			String str6 = String.valueOf(model3.getValueAt(i, 2));
			String str7 = String.valueOf(model3.getValueAt(i, 3));

			try {
				con = DBConnectionUtil.getConnection();
				stat = con.createStatement();
				stat.executeUpdate(
						"insert into HR_EMP_EXTRA values('" + str1 + "','" + str2 + "','" + str3 + "','" + tf7.getText()
								+ "', '" + str4 + "', '" + str5 + "', '" + str6 + "', '" + str7 + "','0','0')  ");
			} catch (Exception localException) {
				System.out.println("************************************");
				System.out.println("saveEmpSalary(HR_EMP_EXTRA): " + localException);
				System.out.println("************************************");
			}
		}
	}

	public void remove() {
		int i = tb.getSelectedRow();
		String str1 = String.valueOf(model.getValueAt(i, 9));
		String str2 = EmpTB.getCompanyID(str1);
		String str3 = EmpTB.getBranchCode(str1);

		try {
			con = DBConnectionUtil.getConnection();
			stat = con.createStatement();
			affected = stat.executeUpdate("update punchcard set status= '1' where emp_code='" + str1 + "'  ");
			if (affected > 0) {
				stat.executeUpdate("update HREMP_JOIN set status= '1' where emp_code='" + str1 + "'  ");
				java.sql.Date localDate = SolDateFormatter.DDMMYYtoSQL(rtf2.getText());

				PreparedStatement localPreparedStatement = con.prepareStatement(
						"insert into HR_EX_EMP values('" + str2 + "','" + str3 + "','" + str1 + "',?)");
				localPreparedStatement.setDate(1, localDate);
				affected = localPreparedStatement.executeUpdate();

				if (affected > 0) {
					JOptionPane.showMessageDialog(f, "Employblity Removed.");
					rf.setVisible(false);
				} else {
					JOptionPane.showMessageDialog(f, "Cannot Remove \nEmployblity Allready Removed.");
					rf.setVisible(false);
				}
			}
		} catch (Exception localException) {
			System.out.println(localException);
		}
	}

	public void updateSalary() {
		String str3 = empcodetf.getText();
		String str4 = String.valueOf(usertypecb.getSelectedItem());
		String str5 = tf4.getText();
		String str6 = tf5.getText();

		prmonitor.showMonitor();
		prmonitor.setMIN(0);
		prmonitor.setMAX(100);
		prmonitor.setProgressMessage("Updates Process for " + str3);
		prmonitor.setStatusMessage("Just Wait...");
		prmonitor.getProgressBar().setForeground(new Color(0, 0, 150));
		prmonitor.runProgressMonitor();

		java.sql.Date localDate = SolDateFormatter.DDMMYYtoSQL(tf6.getText());

		try {
			PreparedStatement localPreparedStatement = con
					.prepareStatement("delete from hr_emp_salary where company_id='" + coidstr + "' and branch_code='"
							+ brcodestr + "' and emp_code='" + str3 + "'  ");
			localPreparedStatement.executeUpdate();
			localPreparedStatement = con.prepareStatement("delete from HR_EMP_EXTRA where company_id='" + coidstr
					+ "' and branch_code='" + brcodestr + "' and emp_code='" + str3 + "'  ");
			localPreparedStatement.executeUpdate();
			localPreparedStatement = con.prepareStatement("update HREMP_JOIN set Dept='" + tf4.getText() + "', Desig='"
					+ tf5.getText() + "', DOj='" + tf6.getText() + "',doj2=?, req_mode='" + tf7.getText()
					+ "', Basic_Salary='" + tf8.getText() + "',prob='" + tf9.getText() + "', ot='" + tf10.getText()
					+ "', PUNCHCARD_NO='" + punchcodetf.getText() + "' , CO_ID='"
					+ String.valueOf(coidcb.getSelectedItem()) + "', br_code='"
					+ String.valueOf(brcodecb.getSelectedItem()) + "' where co_id='" + coidstr + "' and br_code='"
					+ brcodestr + "' and emp_code='" + str3 + "'  ");
			localPreparedStatement.setDate(1, localDate);
			localPreparedStatement.executeUpdate();

			String str7 = String.valueOf(jobtypecb.getSelectedItem());
			localPreparedStatement = con.prepareStatement("update EMP_STATUS set Dept='" + tf4.getText()
					+ "', Designation='" + tf5.getText() + "', CO_ID='" + String.valueOf(coidcb.getSelectedItem())
					+ "', branch_code='" + String.valueOf(brcodecb.getSelectedItem()) + "', job='" + str7
					+ "' where emp_code='" + str3 + "'  ");
			localPreparedStatement.executeUpdate();
			localPreparedStatement = con.prepareStatement("update punchcard set PUNCHCARD_NO='" + punchcodetf.getText()
					+ "' where emp_code='" + str3 + "'  ");
			localPreparedStatement.executeUpdate();

			localPreparedStatement = con.prepareStatement("update password set user_type='" + str4 + "', dept='" + str5
					+ "', post='" + str6 + "' where user='" + str3 + "' ");
			localPreparedStatement.executeUpdate();
		} catch (Exception localException) {
			System.out.println("EMP Updateion Exception :" + localException);
		}
		saveEmpSalary();
		saveExtraSalary();

		int i = tb.getSelectedRow();
		model.setValueAt(tf8.getText(), i, 3);
	}

	public void clearFields() {
		tf1.setText("");
		tf2.setText("");
		tf3.setText("");
		tf6.setText("");
		tf8.setText("");
		tf9.setText("");
		punchcodetf.setText("");
		empcodetf.setText("");
	}

	public class myPrint extends Thread implements Runnable {
		public myPrint() {
		}

		public void run() {
			String str = "Employee List";

			try {
				MessageFormat localMessageFormat1 = new MessageFormat(str);
				MessageFormat localMessageFormat2 = new MessageFormat("- {0} -");
				tb.print(JTable.PrintMode.FIT_WIDTH, localMessageFormat1, localMessageFormat2);
			} catch (PrinterException localPrinterException) {
				System.err.println("Error printing: " + localPrinterException.getMessage());
			}
		}
	}

	public class myPrint2 extends Thread implements Runnable {
		public myPrint2() {
		}

		public void run() {
			String str = "LIST OF SELECTED CANDIDATES";

			try {
				MessageFormat localMessageFormat1 = new MessageFormat(str);
				MessageFormat localMessageFormat2 = new MessageFormat("- {0} -");
				tb1.print(JTable.PrintMode.FIT_WIDTH, localMessageFormat1, localMessageFormat2);
			} catch (PrinterException localPrinterException) {
				System.err.println("Error printing: " + localPrinterException.getMessage());
			}
		}
	}

	public void actionPerformed(ActionEvent paramActionEvent) {
		if (paramActionEvent.getSource() == tf1) {
			tf2.requestFocus();
			tf2.selectAll();
		}
		if (paramActionEvent.getSource() == tf2) {
			tf3.requestFocus();
			tf3.selectAll();
		}
		if (paramActionEvent.getSource() == tf3) {
			tf4.requestFocus();
		}
		if (paramActionEvent.getSource() == tf4) {
			tf5.refreshList("C01", "0", String.valueOf(tf4.getSelectedItem()));
			tf7.refreshList("C01", "0");
		}
		if ((paramActionEvent.getSource() == showbut) || (paramActionEvent.getSource() == aplTF)) {
			getResult();
		}
		if ((paramActionEvent.getSource() == showallbut) || (paramActionEvent.getSource() == searchtf)) {
			showAll();
		}
		Object localObject;
		if (paramActionEvent.getSource() == printbut1) {
			localObject = new EmpRegister.myPrint();
			((EmpRegister.myPrint) localObject).start();
		}
		if (paramActionEvent.getSource() == printbut2) {
			localObject = new EmpRegister.myPrint2();
			((EmpRegister.myPrint2) localObject).start();
		}
		if (paramActionEvent.getSource() == removebut) {
			removeFrame();
		}
		if (paramActionEvent.getSource() == rb1) {
			remove();
		}
		if (paramActionEvent.getSource() == rb2) {
			rf.setVisible(false);
		}
		int i;
		if (paramActionEvent.getSource() == updatebut) {
			localObject = new Object[] { "Yes", "No! Not at All" };
			i = JOptionPane.showOptionDialog(f, "Do You Really Want to Update the Record of Employee", "Updation", 0, 3,
					null, (Object[]) localObject, localObject);
			if (i == 0) {
				updateSalary();
			}
		}

		if ((paramActionEvent.getSource() == tf6) || (paramActionEvent.getSource() == createbut)) {
			localObject = new Object[] { "Yes", "No! Not at All" };
			i = JOptionPane.showOptionDialog(f, "Do You Really Want to Register/Create Employee", "Employee Creation",
					0, 3, null, (Object[]) localObject, localObject);
			if (i == 0) {
				punchCard();
				makeCode();
				saveEmpSalary();
				saveExtraSalary();
				redirectAttendance();
			}
			if (i == 1) {
				JOptionPane.showMessageDialog(f, "Employee Creation Canceled.");
			}
		}
		if (paramActionEvent.getSource() == activatebut) {
			activate();
		}
		if (paramActionEvent.getSource() == newbut) {
			clearFields();
		}
		if (paramActionEvent.getSource() == existcreatebut) {
			saveEmpSalary();
			saveExtraSalary();
		}
		if (paramActionEvent.getSource() == tf8) {
			salaryMatter();
		}

		if (paramActionEvent.getSource() == closebut) {
			f.setVisible(false);
		}

		if (paramActionEvent.getSource() == aplTF) {
			getRecord();
			punchCard();
		}
		if (paramActionEvent.getSource() == coidcb) {
			showBranchList();
			tf7.refreshList(String.valueOf(coidcb.getSelectedItem()), String.valueOf(brcodecb.getSelectedItem()));
		}
		if (paramActionEvent.getSource() == brcodecb) {
			tf7.refreshList(String.valueOf(coidcb.getSelectedItem()), String.valueOf(brcodecb.getSelectedItem()));
		}
	}

	public void focusLost(FocusEvent paramFocusEvent) {
		if (paramFocusEvent.getSource() == tf8) {
			salaryMatter();
		}
	}

	public void selectTable2() {
		tf1.setText("");
		tf2.setText("");
		tf3.setText("");

		String str1 = String.valueOf(model1.getValueAt(tb1.getSelectedRow(), 1));
		String[] arrayOfString = null;
		arrayOfString = str1.split(" ");

		if (arrayOfString.length <= 1) {
			tf1.setText(arrayOfString[0]);
		}
		if ((arrayOfString.length > 1) && (arrayOfString.length < 3)) {
			tf1.setText(arrayOfString[0]);
			tf3.setText(arrayOfString[1]);
		}
		if (arrayOfString.length > 2) {
			tf1.setText(arrayOfString[0]);
			tf2.setText(arrayOfString[1]);
			tf3.setText(arrayOfString[2]);
		}

		String str2 = String.valueOf(model1.getValueAt(tb1.getSelectedRow(), 0));
		String str3 = String.valueOf(model1.getValueAt(tb1.getSelectedRow(), 2));
		String str4 = String.valueOf(model1.getValueAt(tb1.getSelectedRow(), 3));
		String str5 = String.valueOf(model1.getValueAt(tb1.getSelectedRow(), 4));
		String str6 = String.valueOf(model1.getValueAt(tb1.getSelectedRow(), 5));
		String str7 = String.valueOf(model1.getValueAt(tb1.getSelectedRow(), 6));
		String str8 = String.valueOf(model1.getValueAt(tb1.getSelectedRow(), 7));
		String str9 = String.valueOf(model1.getValueAt(tb1.getSelectedRow(), 8));
		String.valueOf(model1.getValueAt(tb1.getSelectedRow(), 9));

		aplTF.setText(str2);
		tf4.setText("Tech");
		tf5.setText(str3);
		tf7.setText(str5);
		if (tf7.getText().equalsIgnoreCase("Checker")) {
			jobtypecb.setSelectedItem("Checking");
		}
		if (tf7.getText().equalsIgnoreCase("Detailer")) {
			jobtypecb.setSelectedItem("Detailing");
		}
		tf8.setText(str4);
		tf9.setText(str7);
		tf10.setText(str6);
		tf11.setText(str8);
		tf12.setText(str9);
	}

	public void mousePressed(MouseEvent paramMouseEvent) {
		if (paramMouseEvent.getSource() == tb1) {
			selectTable2();
			salaryMatter();
		}
		if (paramMouseEvent.getSource() == tb) {
			String str1 = String.valueOf(model.getValueAt(tb.getSelectedRow(), 8));
			String str2 = String.valueOf(model.getValueAt(tb.getSelectedRow(), 9));

			rtf1.setText(str2);
			punchcodetf.setText(str1);
			empcodetf.setText(str2);

			singleShow();
			salaryMatter();
		}
	}

	public void mouseReleased(MouseEvent paramMouseEvent) {
		if (SwingUtilities.isRightMouseButton(paramMouseEvent)) {
			if ((paramMouseEvent.getSource() != tf4) || (

			(paramMouseEvent.getSource() != tf5) ||

			(paramMouseEvent.getSource() == tf6))) {
				skl.showCalendar();
				skl.getDate(tf6);
			}
			if (paramMouseEvent.getSource() == tf9) {
				skl.showCalendar();
				skl.getDate(tf9);
			}
			if ((paramMouseEvent.getSource() != tf7) ||

			(paramMouseEvent.getSource() == dateTF)) {
				skl.showCalendar();
				skl.getDate(dateTF);
			}
			if (paramMouseEvent.getSource() == rtf2) {
				skl.showCalendar();
				skl.getDate(rtf2);
			}
		}
	}

	public void focusGained(FocusEvent paramFocusEvent) {
	}

	public void mouseClicked(MouseEvent paramMouseEvent) {
	}

	public void mouseEntered(MouseEvent paramMouseEvent) {
	}

	public void mouseExited(MouseEvent paramMouseEvent) {
	}
}
