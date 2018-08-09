package com.sol.erp;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.sol.erp.ui.custom.SolCalendar;
import com.sol.erp.ui.custom.SolCalendar3;
import com.sol.erp.ui.custom.SolTableFinder;
import com.sol.erp.ui.custom.SolTableModel;
import com.sol.erp.ui.custom.SolTableColumnModel;
import com.sol.erp.ui.custom.SolTableColumnWork;
import com.sol.erp.util.DBConnectionUtil;
import com.sol.erp.util.DateCalculationUtil;
import com.sol.erp.util.SessionUtil;
import com.sol.erp.util.formater.SolDateFormatter;

public class SalarySheet implements java.awt.event.ActionListener, java.awt.event.ItemListener,
		java.awt.event.KeyListener, java.awt.event.MouseListener, java.awt.event.FocusListener {
	ArrayList heads = new ArrayList();
	Object[][] data = { new Object[0] };

	int benefiteHeadsSize = 0;
	int deductionHeadsSize = 0;
	int extraBenefiteHeadsSize = 0;


	SolCalendar cal1 = new SolCalendar();
	ReqModeList rqmode = new ReqModeList();
	
	PrevSalary prs = new PrevSalary();
	CompanyTable ct = new CompanyTable();
	SalarySlip1 sls = new SalarySlip1();


	SolTableColumnModel myModel = new SolTableColumnModel();
	SolTableColumnWork stw = new SolTableColumnWork();

	String coidstr = SessionUtil.getLoginInfo().getCompanyId();
	String brcodestr = SessionUtil.getLoginInfo().getBranchCode();

	DecimalFormat df = new DecimalFormat("0.00");
	DecimalFormat df1 = new DecimalFormat("0");
	DecimalFormat df2 = new DecimalFormat("00");

	javax.swing.JInternalFrame f = new javax.swing.JInternalFrame("Salary Sheet", true, true, true, true);
	javax.swing.JMenuBar mb = new javax.swing.JMenuBar();
	JMenu m1 = new JMenu("Salary Report");
	JMenu m2 = new JMenu("Salary DBM");

	JMenuItem mi1 = new JMenuItem("Print Report");
	JMenuItem mi2 = new JMenuItem("Exit");

	JMenuItem mb1 = new JMenuItem("Create Salary Report Structures");
	JMenuItem mb2 = new JMenuItem("Delete Salary Month Wise");
	JMenuItem mb3 = new JMenuItem("Database Management");
	JMenuItem mb4 = new JMenuItem("SQL Window");

	javax.swing.JPopupMenu jpm = new javax.swing.JPopupMenu();
	JMenuItem popm1 = new JMenuItem("Hide Column", new javax.swing.ImageIcon("image/close.gif"));
	JMenuItem popm2 = new JMenuItem("Refresh Column", new javax.swing.ImageIcon("image/check.gif"));
	javax.swing.JCheckBoxMenuItem checkboxmenuitem1 = new javax.swing.JCheckBoxMenuItem("Last Column", false);

	javax.swing.JTabbedPane tabbedpane = new javax.swing.JTabbedPane();

	DefaultTableModel model;

	JTable tb = new JTable() {

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
	javax.swing.JViewport jvp = new javax.swing.JViewport();
	javax.swing.JScrollPane sp = new javax.swing.JScrollPane(tb);

	JLabel monthlabel = new JLabel("Month");
	JLabel emplabel = new JLabel("Search Employee");
	JLabel statuslabel = new JLabel("Status : ");

	JLabel l1 = new JLabel("Total Number of Days: ");
	JLabel l2 = new JLabel("Official Leave : ");

	public static JTextField emptf = new JTextField(7);
	JComboBox cb1 = new JComboBox();
	SolCalendar3 datetf = new SolCalendar3();

	JButton createbut = new JButton("Extra Benefites");
	JButton reportbut = new JButton("Generate Salary");
	JButton netsalarybut = new JButton("Net Salary");
	JButton savebut = new JButton("Save Report");
	javax.swing.JCheckBox checkbox1 = new javax.swing.JCheckBox("Activate Minus Value", false);

	JLabel reqlabel = new JLabel("Salary Mode");
	JComboBox reqcb = new JComboBox();

	JProgressBar progress = new JProgressBar(0, 100);
	JButton hidebut = new JButton("Hide Column");

	JLabel rowheightLabel = new JLabel("Row Height");
	JLabel fontsizeLabel = new JLabel("Font Size");

	JTextField rowheighttf = new JTextField("40", 2);
	JTextField fontsizetf = new JTextField("9", 2);

	public JPanel mainPanel = new JPanel();
	JPanel tbpanel = new JPanel();
	JPanel northPanel = new JPanel();
	JPanel southpanel = new JPanel();
	JPanel tablepanel = new JPanel();

	JPanel southp1 = new JPanel();
	JPanel southp2 = new JPanel();

	JPanel northp1 = new JPanel();
	JPanel northp2 = new JPanel();

	JPanel currentpanel = new JPanel();
	JPanel previouspanel = new JPanel();
	JPanel salarypanel = new JPanel();

	Font fo = new Font("Verdana", 1, 9);

	java.util.Date dt = new java.util.Date();
	java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy");

	class ColoredTableCellRenderer extends javax.swing.table.DefaultTableCellRenderer {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		ColoredTableCellRenderer() {
		}

		public java.awt.Component getTableCellRendererComponent(JTable paramJTable, Object paramObject,
				boolean paramBoolean1, boolean paramBoolean2, int paramInt1, int paramInt2) {
			setEnabled((paramJTable == null) || (paramJTable.isEnabled()));

			String str = tb.getColumnName(paramInt2);

			if (paramInt2 >= 2) {
				setHorizontalAlignment(4);
			}
			if (str.equalsIgnoreCase("Name")) {
				setHorizontalAlignment(2);
			}
			if ((str.equalsIgnoreCase("Code")) || (str.equalsIgnoreCase("P_Card"))) {
				setHorizontalAlignment(0);
			}

			if (paramInt1 < 2) {
				setBackground(new Color(245, 245, 245));
				setForeground(Color.black);
			}
			if (paramInt1 == 2) {
				setHorizontalAlignment(0);
				setBackground(Color.darkGray);
				setForeground(Color.white);
				setFont(new Font("Verdana", 1, 25));
			}
			if (paramInt1 > 2) {
				setBackground(Color.white);
				setForeground(Color.darkGray);
			}

			tb.setRowHeight(30);
			tb.setRowHeight(2, 50);

			super.getTableCellRendererComponent(paramJTable, paramObject, paramBoolean1, paramBoolean2, paramInt1,
					paramInt2);

			int i = tb.getSelectedRow();
			int j = tb.getSelectedColumn();

			if (paramInt1 == i) {

				setFont(new Font("Currier", 1, 14));

				if (paramInt2 == j) {
					setBackground(Color.red);
					setForeground(Color.white);
					setHorizontalAlignment(0);
				}
			}

			return this;
		}
	}

	ColoredTableCellRenderer skr = new ColoredTableCellRenderer();

	java.awt.Container c;

	public JPanel DesignFrame() {
		c = f.getContentPane();
		c.setLayout(new BorderLayout());
		northPanel.setLayout(new BorderLayout());
		northp1.setLayout(new java.awt.FlowLayout(0));
		northp2.setLayout(new java.awt.FlowLayout(2));

		mainPanel.setLayout(new BorderLayout());
		currentpanel.setLayout(new BorderLayout());
		previouspanel.setLayout(new BorderLayout());
		salarypanel.setLayout(new BorderLayout());

		southpanel.setLayout(new BorderLayout());
		southp1.setLayout(new java.awt.FlowLayout(0));
		southp2.setLayout(new java.awt.FlowLayout(2));

		southpanel.add(southp1, "North");
		southpanel.add(southp2, "South");

		jpm.add(popm1);

		m1.add(mi1);
		m1.add(mi2);

		m2.add(mb1);
		m2.add(mb2);
		m2.add(mb3);
		m2.add(mb4);

		mb.add(m1);
		mb.add(m2);

		f.setJMenuBar(mb);

		for (int i = 0; i < rqmode.getModeList().size(); i++) {
			reqcb.addItem(rqmode.getModeList().get(i).toString().toUpperCase());
		}

		northp1.add(monthlabel);
		northp1.add(datetf.DesignFrame());
		northp1.add(ct.showContextFrame());
		northp1.add(l1);
		northp1.add(l2);

		southp1.add(reqlabel);
		southp1.add(reqcb);
		southp1.add(emplabel);
		southp1.add(emptf);
		southp1.add(reportbut);
		reportbut.setEnabled(false);
		southp1.add(savebut);
		savebut.setEnabled(false);

		emptf.setFont(new Font("Times New Roman", 1, 14));

		southpanel.setBorder(javax.swing.BorderFactory.createBevelBorder(1));
		southp2.add(progress);

		progress.setPreferredSize(new java.awt.Dimension(220, 16));
		progress.setForeground(Color.blue);

		southp2.add(statuslabel);

		tabbedpane.add("Current Salary", currentpanel);
		tabbedpane.add("Previous Salary", previouspanel);
		tabbedpane.add("Salary Slip", salarypanel);

		northPanel.add(northp1, "West");
		northPanel.add(northp2, "East");

		currentpanel.add(northPanel, "North");
		currentpanel.add(southpanel, "South");
		currentpanel.add(sp, "Center");

		previouspanel.add(prs.DesignFrame(), "Center");
		salarypanel.add(sls.DesignFrame(), "Center");

		mainPanel.add(tabbedpane);

		popm1.addActionListener(this);
		popm2.addActionListener(this);
		checkboxmenuitem1.addActionListener(this);

		tb.addKeyListener(this);
		tb.addMouseListener(this);

		hidebut.addActionListener(this);

		savebut.addActionListener(this);

		cb1.addItemListener(this);

		reqcb.addItemListener(this);
		createbut.addActionListener(this);
		reportbut.addActionListener(this);
		netsalarybut.addActionListener(this);
		emptf.addActionListener(this);
		rowheighttf.addActionListener(this);
		fontsizetf.addActionListener(this);

		mi1.addActionListener(this);
		mi2.addActionListener(this);

		mb1.addActionListener(this);
		mb2.addActionListener(this);
		mb3.addActionListener(this);
		mb4.addActionListener(this);

		emptf.addKeyListener(this);

		currentpanel.setBackground(Color.darkGray);
		previouspanel.setBackground(Color.darkGray);

		c.add(mainPanel, "Center");

		return mainPanel;
	}

	Object[] stHeads = { " ", "Extra Benefites" };
	Object[][] stData = new Object[0][];

	JDialog stf = new JDialog();
	DefaultTableModel stModel = new DefaultTableModel(stData, stHeads);
	JTable stTB = new JTable(stModel) {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@SuppressWarnings({ "unchecked", "rawtypes" })
		public Class getColumnClass(int paramAnonymousInt) {
			return getValueAt(0, paramAnonymousInt).getClass();
		}
	};
	javax.swing.JScrollPane stSP = new javax.swing.JScrollPane(stTB);

	JPanel stCenterPanel = new JPanel();
	JPanel stsouthpanel = new JPanel();
	JButton createSheetBut = new JButton("Create Sheet");

	public void structureFrame() {
		java.awt.Container localContainer = stf.getContentPane();
		localContainer.setLayout(new BorderLayout());
		stCenterPanel.setLayout(new BorderLayout());
		stsouthpanel.setLayout(new java.awt.FlowLayout(2));

		stTB.getColumnModel().getColumn(0).setPreferredWidth(10);
		stTB.getColumnModel().getColumn(1).setPreferredWidth(210);

		stCenterPanel.setBorder(javax.swing.BorderFactory.createBevelBorder(1));
		stCenterPanel.add(stSP, "Center");
		stsouthpanel.add(createSheetBut);

		stTB.getTableHeader().setPreferredSize(new java.awt.Dimension(30, 30));

		localContainer.add(stCenterPanel, "Center");
		localContainer.add(stsouthpanel, "South");

		createSheetBut.addActionListener(this);

		stf.getRootPane().setDefaultButton(createSheetBut);
		stf.setSize(300, 200);
		stf.setLocationRelativeTo(null);
		stf.setVisible(true);
		createSheetBut.requestFocus();
	}

	public void px() {
		DesignFrame();
		f.setSize(800, 600);

		f.setVisible(true);
	}

	public JTable getMainTable() {
		return tb;
	}

	public DefaultTableModel getMainTableModel() {
		return model;
	}

	public void refresh() {
		callBenefites();
		callDeductions();
		addExtraBenefites();
		makeTB();
		EmpList();
		showOT();
	}

	public class myPrint extends Thread implements Runnable {
		public myPrint() {
		}

		public void run() {
			String str1 = "Salary Report";

			java.util.Date localDate = new java.util.Date();
			java.text.SimpleDateFormat localSimpleDateFormat = new java.text.SimpleDateFormat("dd/MM/yyyy");
			String str2 = null;
			str2 = localSimpleDateFormat.format(localDate);

			try {
				java.text.MessageFormat localMessageFormat1 = new java.text.MessageFormat(str1);
				java.text.MessageFormat localMessageFormat2 = new java.text.MessageFormat(str2 + " " + "Page{0}");
				tb.print(javax.swing.JTable.PrintMode.FIT_WIDTH, localMessageFormat1,
						localMessageFormat2);
			} catch (java.awt.print.PrinterException localPrinterException) {
				System.err.println("Error printing: " + localPrinterException.getMessage());
			}
		}
	}

	public void callBenefites() {
		heads.clear();
		heads.add("T_Deduct");
		heads.add("Name");
		heads.add("Code");
		heads.add("P_Card");
		heads.add("InHand1");
		heads.add("Basic1");
		heads.add("L_D");
		heads.add("T_L");
		heads.add("Total1");

		try {
			Connection con = DBConnectionUtil.getConnection();
			Statement stat = con.createStatement();
			ResultSet localResultSet = stat.executeQuery(
					"select head, percent, Base_pref from HRSALARY_STRUCT where type ='Benefite' and Req_Mode='"
							+ reqcb.getSelectedItem() + "' and company_id='" + coidstr + "' and Branch_code='"
							+ brcodestr + "' ");
			System.out.println("Table Used : HRSALARY_STRUCT");

			while (localResultSet.next()) {
				String str1 = new String(localResultSet.getString(1));
				new String(localResultSet.getString(2));
				String str3 = new String(localResultSet.getString(3));

				if (str3.equalsIgnoreCase("Inhand")) {
					str3 = "H";
				}
				if (str3.equalsIgnoreCase("Basic")) {
					str3 = "B";
				}
				heads.add(str1 + "(+)");
			}

			benefiteHeadsSize = heads.size();
		} catch (Exception localException) {
			System.out.println(localException);
		}
	}

	public void callDeductions() {
		try {
			Connection con = DBConnectionUtil.getConnection();
			Statement stat = con.createStatement();
			ResultSet localResultSet = stat.executeQuery(
					"select head,percent,Base_pref from HRSALARY_STRUCT where type ='Deduct' and Req_Mode='"
							+ reqcb.getSelectedItem() + "'  and company_id='" + coidstr
							+ "' and Branch_code='" + brcodestr + "'");
			System.out.println("Table Used : HRSALARY_STRUCT");
			while (localResultSet.next()) {
				String str1 = new String(localResultSet.getString(1));
				new String(localResultSet.getString(2));
				String str3 = new String(localResultSet.getString(3));

				if (str3.equalsIgnoreCase("Inhand")) {
					str3 = "H";
				}
				if (str3.equalsIgnoreCase("Basic")) {
					str3 = "B";
				}

				model.setValueAt("<HTML><Center>" + str1 + "(-)", 0, heads.size());
				heads.add(str1 + "(-)");
			}
			deductionHeadsSize = heads.size();
		} catch (Exception localException) {
			System.out.println(localException);
		}
	}

	public void callExtraBenefites() {
		stModel.setNumRows(0);
		try {
			Connection con = DBConnectionUtil.getConnection();
			Statement stat = con.createStatement();
			ResultSet localResultSet = stat
					.executeQuery("select EXTRA_HEADS,RATE from HRCOMPANY_EXTRA_BENEFITES where req_mode='"
							+ reqcb.getSelectedItem() + "'  and company_id='" + coidstr
							+ "' and Branch_code='" + brcodestr + "'");
			System.out.println("Table Used : HRCOMPANY_EXTRA_BENEFITES");
			while (localResultSet.next()) {
				String str1 = new String(localResultSet.getString(1));
				new String(localResultSet.getString(2));
				stModel.addRow(new Object[] { new Boolean(false), str1 });
			}
		} catch (Exception localException) {
			System.out.println(localException);
		}
	}

	public void addExtraBenefites() {
		for (int i = 0; i < stModel.getRowCount(); i++) {
			String str1 = String.valueOf(stModel.getValueAt(i, 0));
			String str2 = String.valueOf(stModel.getValueAt(i, 1));
			if (str1.equalsIgnoreCase("true")) {
				heads.add(str2);
			}
		}

		heads.add("TDS1(-)");
		heads.add("Advance1(-)");
		heads.add("Net_Salary");
		heads.add("OT_HRS");
		heads.add("OT_Rate");
		heads.add("OT_APP");

		extraBenefiteHeadsSize = (heads.size() - 5);
	}

	public void makeTB() {
		String[] arrayOfString = new String[heads.size()];

		for (int i = 0; i < heads.size(); i++) {
			arrayOfString[i] = String.valueOf(heads.get(i));
		}

		tb.setModel(model = new DefaultTableModel(data, arrayOfString));

		SolTableModel.decorateTable(tb);
		tb.setAutoResizeMode(0);

		tb.setFont(new Font("Verdana", 0, 11));
		tb.getTableHeader().setFont(new Font("Verdana", 0, 10));
		tb.setGridColor(new Color(240, 240, 240));

		for (int i = 0; i < tb.getColumnCount() - 1; i++) {
			tb.getColumnModel().getColumn(i).setCellRenderer(skr);
		}

		tb.getColumnModel().getColumn(0).setPreferredWidth(60);
		tb.getColumnModel().getColumn(1).setPreferredWidth(160);
		tb.getColumnModel().getColumn(2).setPreferredWidth(80);
		tb.getColumnModel().getColumn(3).setPreferredWidth(60);
		tb.getColumnModel().getColumn(4).setPreferredWidth(90);
		tb.getColumnModel().getColumn(5).setPreferredWidth(90);
		tb.getColumnModel().getColumn(6).setPreferredWidth(60);
		tb.getColumnModel().getColumn(7).setPreferredWidth(60);
		tb.getColumnModel().getColumn(tb.getColumnCount() - 5).setPreferredWidth(70);
		tb.getColumnModel().getColumn(tb.getColumnCount() - 4).setPreferredWidth(110);
		tb.getColumnModel().getColumn(tb.getColumnCount() - 3).setPreferredWidth(60);
		tb.getColumnModel().getColumn(tb.getColumnCount() - 2).setPreferredWidth(60);
		tb.getColumnModel().getColumn(tb.getColumnCount() - 1).setPreferredWidth(30);

		currentpanel.add(northPanel, "North");
		currentpanel.add(southpanel, "South");
		currentpanel.add(sp, "Center");
		sp.setColumnHeader(null);
		northPanel.revalidate();
		southpanel.revalidate();
		currentpanel.revalidate();
	}

	public void EmpSalaryMode() {
		try {
			Connection localConnection = DBConnectionUtil.getConnection();
			;
			Statement localStatement = localConnection.createStatement();
			ResultSet localResultSet = localStatement
					.executeQuery("select req_mode from HREmp_join where Emp_Code = '" + emptf.getText() + "' ");
			System.out.println("Table Used : HREMP_JOIN");
			while (localResultSet.next()) {
				String str = new String(localResultSet.getString(1));
				reqcb.setSelectedItem(str);
			}
		} catch (Exception localException) {
			System.out.println(localException);
		}
	}

	public void EmpList() {
		String str1 = "%" + emptf.getText() + "%";
		model.setNumRows(0);

		java.sql.Date localDate = SolDateFormatter.DDMMYYtoSQL(datetf.getText());

		model.addRow(new Object[] { " ", "Total Working Days : " });
		model.setValueAt(new Boolean(true), 0, tb.getColumnCount() - 1);

		model.addRow(new Object[] { " ", "Official Leaves    : " });
		model.setValueAt(new Boolean(false), 1, tb.getColumnCount() - 1);

		model.addRow(new Object[] { " ", "Name", "EMP Code", "P. Card", "InHand", "Basic",
				"<HTML><Center>Leave<BR>Deduction", "<HTML><Center>Leave<BR>Taken",
				"<HTML><Center>Salary<BR>Entitlement", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0",
				new Boolean(false) });

		model.setValueAt(new Boolean(false), 2, tb.getColumnCount() - 1);
		Object localObject;
		try {
			Connection localConnection = DBConnectionUtil.getConnection();
			localObject = localConnection.prepareStatement(
					"select punchcard_no, emp_code, full_name, Basic_Salary, OT  from HREMP_JOIN where EMP_CODE LIKE '"
							+ str1 + "' and Req_Mode='" + String.valueOf(reqcb.getSelectedItem())
							+ "' and status NOT LIKE ? and doj2 <= ? AND emp_code not in(select emp_code from HR_EX_EMP where releave_date < ?) ORDER BY PUNCHCARD_NO");
			((PreparedStatement) localObject).setString(1, "1");
			((PreparedStatement) localObject).setDate(2, localDate);
			((PreparedStatement) localObject).setDate(3, localDate);
			ResultSet localResultSet = ((PreparedStatement) localObject).executeQuery();

			while (localResultSet.next()) {
				String str2 = new String(localResultSet.getString(1));
				String str3 = new String(localResultSet.getString(2));
				String str4 = new String(localResultSet.getString(3));
				String str5 = new String(localResultSet.getString(4));
				new String(localResultSet.getString(5));

				model.addRow(new Object[] { "", str4.toUpperCase(), str3.toUpperCase(), str2.toUpperCase(), str5,
						"0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0" });
			}
		} catch (Exception localException) {
			System.out.println(localException);
		}

		for (int i = 2; i < tb.getRowCount(); i++) {
			localObject = String.valueOf(model.getValueAt(i, 2));
			model.setValueAt(new Boolean(EmpTB.getOTPermission((String) localObject)), i,
					tb.getColumnCount() - 1);
		}

		empBasicSalary();
		empTDS();
		addTableHeaderText();
	}

	public void addTableHeaderText() {
		for (int i = 9; i < tb.getColumnCount() - 4; i++) {
			tb.getColumnModel().getColumn(i).setPreferredWidth(100);

			String str1 = tb.getColumnName(i);
			int j = str1.indexOf("_");

			if (j >= 0) {
				String str2 = str1.substring(0, j);
				String str3 = str1.substring(j + 1, str1.length());

				model.setValueAt("<HTML><Center>" + str2 + "<BR>" + str3, 2, i);
			} else {
				model.setValueAt("<HTML><Center>" + str1, 2, i);
			}
		}

		model.setValueAt("<HTML><Center>Net<BR>Salary", 2, tb.getColumnCount() - 4);
		model.setValueAt("<HTML><Center>OT Hrs", 2, tb.getColumnCount() - 3);
		model.setValueAt("<HTML><Center>OT Rate", 2, tb.getColumnCount() - 2);
	}

	public void empBasicSalary() {
		for (int i = 3; i < tb.getRowCount(); i++) {
			String str1 = String.valueOf(model.getValueAt(i, 2));

			try {
				Connection con = DBConnectionUtil.getConnection();
				Statement stat = con.createStatement();
				ResultSet localResultSet = stat.executeQuery(
						"select Amount from HR_EMP_SALARY where EMP_CODE='" + str1 + "' and Heads = 'BASIC'  ");
				System.out.println("Table Used : HR_EMP_SALARY");
				while (localResultSet.next()) {
					String str2 = new String(localResultSet.getString(1));

					model.setValueAt(str2, i, 5);
				}
			} catch (Exception localException) {
				System.out.println(localException);
			}
		}
	}

	public void empTDS() {
		for (int i = 3; i < tb.getRowCount(); i++) {
			String str = String.valueOf(model.getValueAt(i, 2));

			try {
				model.setValueAt(String.valueOf("0"), i, tb.getColumnCount() - 5);
				Connection con = DBConnectionUtil.getConnection();
				Statement stat = con.createStatement();
				ResultSet localResultSet = stat
						.executeQuery("select ADVANCE_DEDUCT, TDS from HR_TDS where EMP_CODE='" + str + "'  ");
				System.out.println("Table Used : HR_TDS");
				while (localResultSet.next()) {
					float f1 = localResultSet.getFloat(1);
					float f2 = localResultSet.getFloat(2);

					model.setValueAt(String.valueOf(df1.format(f1)), i, tb.getColumnCount() - 5);
					model.setValueAt(String.valueOf(df1.format(f2)), i, tb.getColumnCount() - 6);
				}
			} catch (Exception localException) {
				System.out.println(localException);
			}
		}
		cess();
	}

	public void cess() {
		for (int i = 3; i < tb.getRowCount(); i++) {
			String str1 = String.valueOf(heads.get(tb.getColumnCount() - 7));
			String str2 = str1.substring(0, str1.length() - 3);

			if (str2.equalsIgnoreCase("Cess")) {
				float f1 = Float.parseFloat(String.valueOf(model.getValueAt(i, tb.getColumnCount() - 6)));
				float f2 = f1 * 3.0F / 100.0F;
				model.setValueAt(String.valueOf(df.format(f2)), i, tb.getColumnCount() - 7);
				System.out.println("Tax : " + f2);
			}
		}
	}

	public void showOT() {
		int i = DateCalculationUtil.getMonth(datetf.getText());
		int j = DateCalculationUtil.getYear(datetf.getText());

		for (int k = 3; k < tb.getRowCount(); k++) {

			String str = String.valueOf(model.getValueAt(k, 2));
			model.setValueAt("0", k, tb.getColumnCount() - 3);
			model.setValueAt("0", k, tb.getColumnCount() - 2);
			try {
				Connection con = DBConnectionUtil.getConnection();
				PreparedStatement localPreparedStatement = con
						.prepareStatement("select value1, rate from HR_EMP_Extra where emp_code ='" + str
								+ "' and heads = ? and MONTH = ? and YEAR = ?  ");
				System.out.println("Table Used : HR_EMP_EXTRA");
				localPreparedStatement.setString(1, "OT");
				localPreparedStatement.setInt(2, i);
				localPreparedStatement.setInt(3, j);
				ResultSet localResultSet = localPreparedStatement.executeQuery();

				while (localResultSet.next()) {
					float f1 = localResultSet.getFloat(1);
					float f2 = localResultSet.getFloat(2);
					model.setValueAt(String.valueOf(f1), k, tb.getColumnCount() - 3);
					model.setValueAt(String.valueOf(f2), k, tb.getColumnCount() - 2);
				}
			} catch (Exception localException) {
				System.out.println(localException);
			}
			progress.setValue(k);
		}
	}

	public void showLWP() {
		int i = DateCalculationUtil.getMonth(datetf.getText());
		int j = DateCalculationUtil.getYear(datetf.getText());

		@SuppressWarnings("deprecation")
		java.sql.Date localDate1 = new java.sql.Date(j - 1900, i - 1, 1);

		java.sql.Date localDate2 = SolDateFormatter.DDMMYYtoSQL(datetf.getText());

		for (int k = 3; k < tb.getRowCount(); k++) {
			String str1 = String.valueOf(model.getValueAt(k, 2));
			try {
				Connection con = DBConnectionUtil.getConnection();
				PreparedStatement localPreparedStatement = con
						.prepareStatement("select count(CATEGORY) from HREMP_LEAVES where emp_code ='" + str1
								+ "' and Category='LWP' and Company_Id='" + coidstr + "' and Branch_Code='"
								+ brcodestr + "' and DATE between ? and ? ");
				System.out.println("Table Used : HREMP_LEAVES");
				localPreparedStatement.setDate(1, localDate1);
				localPreparedStatement.setDate(2, localDate2);
				ResultSet localResultSet = localPreparedStatement.executeQuery();
				while (localResultSet.next()) {
					String str2 = new String(localResultSet.getString(1));

					model.setValueAt(str2, k, 6);
				}
			} catch (Exception localException) {
				System.out.println(localException);
			}
			progress.setValue(k);
		}
	}

	public void showTotalLeave() {
		int i = DateCalculationUtil.getMonth(datetf.getText());
		int j = DateCalculationUtil.getYear(datetf.getText());

		@SuppressWarnings("deprecation")
		java.sql.Date localDate1 = new java.sql.Date(j - 1900, i - 1, 1);

		java.sql.Date localDate2 = SolDateFormatter.DDMMYYtoSQL(datetf.getText());

		for (int k = 3; k < tb.getRowCount(); k++) {
			String str1 = String.valueOf(model.getValueAt(k, 2));
			try {
				Connection con = DBConnectionUtil.getConnection();
				PreparedStatement localPreparedStatement = con
						.prepareStatement("select count(CATEGORY) from HREMP_LEAVES where emp_code ='" + str1
								+ "' and Company_Id='" + coidstr + "' and Branch_Code='" + brcodestr
								+ "' and DATE between ? and ? ");
				System.out.println("Table Used : HREMP_LEAVES");
				localPreparedStatement.setDate(1, localDate1);
				localPreparedStatement.setDate(2, localDate2);
				ResultSet localResultSet = localPreparedStatement.executeQuery();
				while (localResultSet.next()) {
					String str2 = new String(localResultSet.getString(1));

					model.setValueAt(str2, k, 7);
				}
			} catch (Exception localException) {
				System.out.println(localException);
			}
			progress.setValue(k);
		}
	}

	public void benefiteData() {
		int i = deductionHeadsSize;
		for (int j = 3; j < tb.getRowCount(); j++) {
			for (int k = 9; k < i; k++) {
				String str1 = String.valueOf(heads.get(k));
				String str2 = str1.substring(0, str1.length() - 3);

				String str3 = String.valueOf(model.getValueAt(j, 2));
				model.setValueAt(" ", j, k);
				try {
					Connection con = DBConnectionUtil.getConnection();
					Statement stat = con.createStatement();
					ResultSet localResultSet = stat.executeQuery("select PERCENT from HR_emp_salary where emp_code ='"
							+ str3 + "' and heads='" + str2 + "' ");
					System.out.println("Table Used : HREMP_SALARY");
					while (localResultSet.next()) {
						String str4 = new String(localResultSet.getString(1));
						model.setValueAt(str4, j, k);
					}
				} catch (Exception localException) {
					System.out.println(localException);
				}
			}
			progress.setValue(j);
		}
	}

	public void extraBenefiteData() {
		int i = DateCalculationUtil.getMonth(datetf.getText());
		int j = DateCalculationUtil.getYear(datetf.getText());

		for (int k = 3; k < tb.getRowCount(); k++) {
			for (int m = deductionHeadsSize; m < extraBenefiteHeadsSize; m++) {
				String str1 = String.valueOf(model.getValueAt(k, tb.getColumnCount() - 1));

				String str2 = String.valueOf(heads.get(m));
				String str3 = str2.substring(0, str2.length() - 3);
				String str4 = String.valueOf(model.getValueAt(k, 2));
				if (!str3.equalsIgnoreCase("Cess")) {

					try {
						Connection con = DBConnectionUtil.getConnection();
						PreparedStatement localPreparedStatement = con.prepareStatement(
								"select (RATE)*(VALUE1) FROM HR_EMP_EXTRA where EMP_CODE = ? and HEADS = ? and MONTH =? and YEAR=? ");
						System.out.println("Table Used : HR_EMP_EXTRA");
						localPreparedStatement.setString(1, str4);
						localPreparedStatement.setString(2, str3);
						localPreparedStatement.setInt(3, i);
						localPreparedStatement.setInt(4, j);
						ResultSet localResultSet = localPreparedStatement.executeQuery();
						while (localResultSet.next()) {
							String str5 = new String(localResultSet.getString(1));
							float f1 = Float.parseFloat(str5);
							if (f1 < 0.0F) {
								str5 = "0";
							}
							if ((!str3.equalsIgnoreCase("OT")) || (!str1.equalsIgnoreCase("false"))) {

								model.setValueAt(String.valueOf(df.format(Float.parseFloat(str5))), k, m);
							}
						}
					} catch (Exception localException) {
						System.out.println(localException);
					}
				}
			}
			progress.setValue(k);
		}
	}

	public void newSalaryData() {
		int j = DateCalculationUtil.getNumberOfDays(datetf.getText());
		int k = DateCalculationUtil.getDate(datetf.getText());

		for (int m = 3; m < tb.getRowCount(); m++) {
			int n = Integer.parseInt(String.valueOf(model.getValueAt(m, 6)));
			String str = String.valueOf(model.getValueAt(m, 3));

			if ((str.length() >= 2) && (!str.equalsIgnoreCase("null"))) {

				float f1 = Float.parseFloat(String.valueOf(model.getValueAt(m, 4)));
				float f2 = f1 / j;
				float f3 = f2 * k;
				f3 -= f1 / j * n;

				model.setValueAt(String.valueOf(df.format(f3)), m, 8);
			}
			n = 0;
			progress.setValue(m);
		}
	}

	public void totalDeduction(int paramInt1, int paramInt2) {
		int i = benefiteHeadsSize;

		for (int j = paramInt1; j < paramInt2; j++) {
			float f1 = 0.0F;
			float f2 = Float.parseFloat(String.valueOf(model.getValueAt(j, 8)));

			for (int k = i; k < deductionHeadsSize; k++) {
				String str = String.valueOf(model.getValueAt(j, k));
				if ((str.length() >= 2) && (!str.equalsIgnoreCase("null"))) {

					float f3 = Float.parseFloat(String.valueOf(model.getValueAt(j, k)));
					float f4 = f2 * f3 / 100.0F;

					f1 += f4;

					model.setValueAt(String.valueOf(df.format(f4)), j, k);
				}
			}
			model.setValueAt(String.valueOf(df.format(f2 - f1)), j, 0);
		}
	}

	public void newCalculatedData2() {
		int i = deductionHeadsSize;
		for (int j = 3; j < tb.getRowCount(); j++) {
			float f1 = Float.parseFloat(String.valueOf(model.getValueAt(j, 8)));
			for (int k = 9; k < i; k++) {
				float f2 = 0.0F;
				String str = String.valueOf(model.getValueAt(j, 3));
				if ((str.length() >= 2) && (!str.equalsIgnoreCase("null"))) {

					float f3 = Float.parseFloat(String.valueOf(model.getValueAt(j, k)));
					f2 = f1 * f3 / 100.0F;
					model.setValueAt(String.valueOf(df.format(f2)), j, k);
				}
				progress.setValue(j);
			}
		}
	}

	public void totalData(int paramInt1, int paramInt2) {
		int i = benefiteHeadsSize;

		for (int j = paramInt1; j < paramInt2; j++) {
			float f1 = 0.0F;

			for (int k = 9; k < i; k++) {
				String str = String.valueOf(model.getValueAt(j, k));
				if ((str.length() >= 2) && (!str.equalsIgnoreCase("null"))) {

					float f2 = Float.parseFloat(String.valueOf(model.getValueAt(j, k)));
					f1 += f2;
				}
				model.setValueAt(String.valueOf(df.format(f1)), j, 8);
				model.setValueAt("0", j, tb.getColumnCount() - 5);
				model.setValueAt("0", j, tb.getColumnCount() - 4);
				progress.setValue(j);
			}
		}
	}

	public void netSalary(int paramInt1, int paramInt2) {
		int i = tb.getColumnCount();
		for (int j = paramInt1; j < paramInt2; j++) {
			float f1 = 0.0F;
			for (int k = 8; k < tb.getColumnCount() - 4; k++) {
				String str1 = String.valueOf(heads.get(k));
				String str2 = str1.substring(str1.length() - 3, str1.length());

				float f2 = Float.parseFloat(String.valueOf(model.getValueAt(j, k)));
				if (str2.equalsIgnoreCase("(+)")) {
					f1 += f2;
				}
				if (str2.equalsIgnoreCase("(-)")) {
					f1 -= f2;
				}

				model.setValueAt(String.valueOf(f1), j, i - 4);
			}
		}
	}

	public void changeDecimalFormat() {
		for (int i = 4; i < tb.getColumnCount() - 3; i++) {
			for (int j = 3; j < tb.getRowCount() - 1; j++) {
				float f1 = Float.parseFloat(String.valueOf(model.getValueAt(j, i)));
				String str1 = String.valueOf(df1.format(f1));
				model.setValueAt(str1, j, i);

				float f2 = Float.parseFloat(String.valueOf(model.getValueAt(j, 0)));
				String str2 = String.valueOf(df1.format(f2));
				model.setValueAt(str2, j, 0);
			}
		}
	}

	public class Report extends Thread {
		public Report() {
		}

		public void run() {
			int i = tb.getRowCount() - 2;
			progress.setMinimum(1);
			progress.setMaximum(i);

			for (int j = 3; j < tb.getRowCount(); j++) {
				try {
					Thread.sleep(100L);
				} catch (InterruptedException localInterruptedException) {
					System.out.println(localInterruptedException);
				}
			}
			showLWP();
			showTotalLeave();
			newSalaryData();
			benefiteData();
			extraBenefiteData();

			newCalculatedData2();
			totalDeduction(3, tb.getRowCount());

			netSalary(3, tb.getRowCount());

			totalSalary();
			changeDecimalFormat();
			removeColumn();

			savebut.setEnabled(true);
		}
	}

	String[] days = { "January", "February", "March", "April", "May", "June", "July", "August", "September", "October",
			"November", "December" };

	public String createTableString() {
		String str1 = SessionUtil.getLoginInfo().getCompanyId();
		int i = DateCalculationUtil.getYear(datetf.getText());
		int j = DateCalculationUtil.getMonth(datetf.getText());
		String str2 = DateCalculationUtil.getNameofMonth(j - 1);

		String str3 = "111_" + str1 + "_" + brcodestr + "_" + String.valueOf(reqcb.getSelectedItem()) + "_"
				+ String.valueOf(i) + "_" + str2;

		String str4 = "Create Table " + str3 + " ( ";

		for (int k = 0; k < tb.getColumnCount(); k++) {
			String str5 = String.valueOf(tb.getColumnName(k));
			String str6 = str5.substring(0, str5.length() - 3);

			if (k <= 6) {
				str4 = str4 + str5 + " VARCHAR(50),";
			}
			if ((k > 6) && (k < tb.getColumnCount() - 4)) {
				str4 = str4 + str6 + " VARCHAR(50),";
			}
			if (k >= tb.getColumnCount() - 4) {
				str4 = str4 + str5 + " VARCHAR(50),";
			}
		}

		str4 = str4 + "Last_Col date)";

		System.out.println("\n\n\n");
		System.out.println(str4);
		System.out.println("\n\n\n");

		return str4;
	}

	public String insertRecordString(int paramInt) {
		java.sql.Date localDate = SolDateFormatter.DDMMYYtoSQL(String.valueOf(datetf.getText()));

		String str1 = SessionUtil.getLoginInfo().getCompanyId();
		int i = DateCalculationUtil.getYear(datetf.getText());
		int j = DateCalculationUtil.getMonth(datetf.getText());
		String str2 = DateCalculationUtil.getNameofMonth(j - 1);

		String str3 = "111_" + str1 + "_" + brcodestr + "_" + String.valueOf(reqcb.getSelectedItem()) + "_"
				+ String.valueOf(i) + "_" + str2;

		String str4 = "Insert into " + str3 + " values ( ";

		for (int k = 0; k < tb.getColumnCount(); k++) {
			String str5 = String.valueOf(tb.getValueAt(paramInt, k));

			str4 = str4 + "'" + str5 + "', ";
		}
		str4 = str4 + " '" + localDate + "' )";

		return str4;
	}

	public void dropTable() {
		String str1 = SessionUtil.getLoginInfo().getCompanyId();
		String str2 = SessionUtil.getLoginInfo().getBranchCode();
		int i = DateCalculationUtil.getYear(datetf.getText());
		int j = DateCalculationUtil.getMonth(datetf.getText());
		String str3 = DateCalculationUtil.getNameofMonth(j - 1);

		String str4 = "111_" + str1 + "_" + str2 + "_" + String.valueOf(reqcb.getSelectedItem()) + "_"
				+ String.valueOf(i) + "_" + str3;

		try {
			Connection con = DBConnectionUtil.getConnection();
			Statement localStatement = con.createStatement();
			localStatement.executeUpdate("Drop Table " + str4);
		} catch (Exception localException) {
			System.out.println("Drop Table :" + localException);
		}
	}

	public void createTable() {
		try {
			Connection con = DBConnectionUtil.getConnection();
			Statement localStatement = con.createStatement();
			localStatement.executeUpdate(createTableString());

			javax.swing.JOptionPane.showMessageDialog(f, "Report Properlly Generated and Saved.");
		} catch (Exception localException) {
			javax.swing.JOptionPane.showMessageDialog(f, localException.getMessage().toString());
		}
	}

	public void insertRecord() {
		for (int i = 3; i < tb.getRowCount(); i++) {
			try {
				Connection con = DBConnectionUtil.getConnection();
				Statement localStatement = con.createStatement();
				localStatement.executeUpdate(insertRecordString(i));
			} catch (Exception localException) {
				System.out.println(localException);
			}
		}
	}

	public void saveMonthlyReport() {
		dropTable();
		createTable();
		insertRecord();
	}

	public void removeColumn() {
		tb.removeColumn(tb.getColumnModel().getColumn(0));
		tb.removeColumn(tb.getColumnModel().getColumn(4));
	}

	public void changeDateFormat() {
		int i = DateCalculationUtil.getNumberOfDays(datetf.getText());
		int j = DateCalculationUtil.getMonth(datetf.getText());
		int k = DateCalculationUtil.getYear(datetf.getText());

		String str = String.valueOf(df1.format(i)) + "/" + String.valueOf(df2.format(j)) + "/"
				+ String.valueOf(k);
		datetf.setText(str);
	}

	public void officialLeave() {
		String str1 = "C01";
		String str2 = "0";

		String.valueOf(datetf.getText());
		int i = DateCalculationUtil.getDate(datetf.getText());
		int j = DateCalculationUtil.getMonth(datetf.getText());
		int k = DateCalculationUtil.getYear(datetf.getText());

		@SuppressWarnings("deprecation")
		java.sql.Date localDate1 = new java.sql.Date(k - 1900, j, 1);

		java.sql.Date localDate2 = SolDateFormatter.DDMMYYtoSQL(datetf.getText());

		int m = 0;
		int n = 0;
		ResultSet localResultSet;
		try {
			Connection con = DBConnectionUtil.getConnection();
			PreparedStatement localPreparedStatement1 = con
					.prepareStatement("select count(OCCASION) from HR_HOLIDAY_LIST where Company_ID='" + str1
							+ "' and BRANCH_CODE='" + str2 + "' and DATE between ? and ? ");
			localPreparedStatement1.setDate(1, localDate1);
			localPreparedStatement1.setDate(2, localDate2);
			localResultSet = localPreparedStatement1.executeQuery();

			while (localResultSet.next()) {
				String str4 = new String(localResultSet.getString(1));
				m = Integer.parseInt(str4);
			}
		} catch (Exception localException1) {
			System.out.println(localException1);
		}
		try {
			Connection con = DBConnectionUtil.getConnection();
			PreparedStatement localPreparedStatement2 = con
					.prepareStatement("select count(DATE) from HR_OT_URGENT where Company_ID='" + str1
							+ "' and BRANCH_CODE='" + str2 + "' and DATE between ? and ? ");
			localPreparedStatement2.setDate(1, localDate1);
			localPreparedStatement2.setDate(2, localDate2);
			localResultSet = localPreparedStatement2.executeQuery();

			while (localResultSet.next()) {
				n = localResultSet.getInt(1);
			}
		} catch (Exception localException2) {
			System.out.println(localException2);
		}

		int i1 = DateCalculationUtil.countDayTillDate(i, j, k, "SUN");
		int i2 = DateCalculationUtil.countDayTillDate(i, j, k, "SAT");

		int i3 = i1 + i2 + m - n;

		int i4 = DateCalculationUtil.getDate(datetf.getText());
		int i5 = i4 - i3;

		l1.setText("Total Working Days : " + String.valueOf(i5) + "/" + String.valueOf(i4));
		l2.setText("Offcial Leaves : " + String.valueOf(i3));

		String str5 = DateCalculationUtil.getNameofMonth(j - 1);

		model.setValueAt(str5.toUpperCase() + " " + String.valueOf(k), 0, tb.getColumnCount() - 4);
		model.setValueAt(ct.getMyCompanyName(), 1, tb.getColumnCount() - 4);
		model.setValueAt(String.valueOf(reqcb.getSelectedItem()) + ",", 1, tb.getColumnCount() - 5);

		model.setValueAt(l1.getText(), 0, 1);
		model.setValueAt(l2.getText(), 1, 1);
	}

	public void totalSalary() {
		model.addRow(new Object[] { " ", "GRAND Total" });
		for (int i = 4; i < tb.getColumnCount() - 3; i++) {
			float f1 = 0.0F;
			for (int j = 3; j < tb.getRowCount() - 1; j++) {
				float f2 = Float.parseFloat(String.valueOf(model.getValueAt(j, i)));
				f1 += f2;
			}
			model.setValueAt(String.valueOf(df1.format(f1)), tb.getRowCount() - 1, i);
		}
	}

	public void focusGained(java.awt.event.FocusEvent paramFocusEvent) {
	}

	public void focusLost(java.awt.event.FocusEvent paramFocusEvent) {
		if (paramFocusEvent.getSource() == datetf) {
		}
	}

	public void itemStateChanged(java.awt.event.ItemEvent paramItemEvent) {
		if ((paramItemEvent.getSource() != cb1) ||

				(paramItemEvent.getSource() == reqcb)) {
			callExtraBenefites();
			structureFrame();
		}
	}

	public void actionPerformed(ActionEvent paramActionEvent) {
		if (paramActionEvent.getSource() == popm1) {
			for (int i = 0; i < tb.getColumnCount(); i++) {
				tb.getColumnModel().getColumn(tb.getSelectedColumn()).setMinWidth(0);
			}
			tb.getColumnModel().getColumn(tb.getSelectedColumn()).setPreferredWidth(0);
		}

		if ((paramActionEvent.getSource() != popm2) ||

				(paramActionEvent.getSource() == hidebut)) {
			stw.DesignFrame(tb, model);
			stw.showFrame();
		}
		if (paramActionEvent.getSource() == datetf) {
			changeDateFormat();
			EmpSalaryMode();
			callExtraBenefites();
			structureFrame();
		}

		if (paramActionEvent.getSource() == createbut) {
			callExtraBenefites();
			structureFrame();
			stw.setColumnListSize(0);
		}
		if (paramActionEvent.getSource() == createSheetBut) {
			refresh();
			officialLeave();
			stf.setVisible(false);
			reportbut.setEnabled(true);
			savebut.setEnabled(false);
			statuslabel.setText("Mode : " + String.valueOf(reqcb.getSelectedItem()) + ", Number Of Emp : "
					+ String.valueOf(tb.getRowCount()));
		}
		if (paramActionEvent.getSource() == emptf) {
			EmpSalaryMode();
			callExtraBenefites();
			structureFrame();
		}
		Object localObject;
		if (paramActionEvent.getSource() == reportbut) {
			localObject = new Report();
			((Report) localObject).start();
			reportbut.setEnabled(false);
		}
		if (paramActionEvent.getSource() == netsalarybut) {
			netSalary(0, tb.getRowCount());
			saveMonthlyReport();
		}
		if (paramActionEvent.getSource() == savebut) {
			saveMonthlyReport();
		}
		if (paramActionEvent.getSource() == mi1) {

			localObject = new myPrint();
			((myPrint) localObject).start();
		}
		if (paramActionEvent.getSource() == mi2) {
			f.setVisible(false);
		}
		if (paramActionEvent.getSource() == mb1) {
			createTable();
		}
		if (paramActionEvent.getSource() == mb2) {
			dropTable();
		}

		if ((paramActionEvent.getSource() != rowheighttf) ||

				(paramActionEvent.getSource() == fontsizetf)) {
			int j = Integer.parseInt(fontsizetf.getText());
			tb.setFont(new Font("Tahoma", 1, j));
		}
	}

	public void keyPressed(KeyEvent paramKeyEvent) {
	}

	public void keyReleased(KeyEvent paramKeyEvent) {
		int i;
		Object localObject;
		if (paramKeyEvent.getSource() == tb) {
			i = paramKeyEvent.getKeyCode();
			if ((paramKeyEvent.getSource() == tb) && (i == 112)) {
				localObject = new SolTableFinder();
				((SolTableFinder) localObject).showFinder();
				((SolTableFinder) localObject).setAutoSelectedText(tb, model);
				((SolTableFinder) localObject).SearchInThisTable(tb, model);
			}
			if (((paramKeyEvent.getSource() != tb) || (i != 120)) || (

			(i != 116) ||

					(i == 116))) {
				stw.DesignFrame(tb, model);
				stw.showFrame();
			}
		}

		if (paramKeyEvent.getSource() == emptf) {
			i = paramKeyEvent.getKeyCode();

			if (i == 112) {
				localObject = new HelpTable();
				((HelpTable) localObject).showFrame();
				((HelpTable) localObject).getEmpCode(emptf);
			}
		}
	}

	public void keyTyped(KeyEvent paramKeyEvent) {
	}

	public void mousePressed(MouseEvent paramMouseEvent) {
		if (((paramMouseEvent.getSource() != datetf) ||

				(!javax.swing.SwingUtilities.isRightMouseButton(paramMouseEvent))) ||

				(paramMouseEvent.getSource() == tb)) {
			if (javax.swing.SwingUtilities.isRightMouseButton(paramMouseEvent)) {
				jpm.show((javax.swing.JComponent) paramMouseEvent.getSource(), paramMouseEvent.getX(),
						paramMouseEvent.getY());
			}
		}
	}

	public void mouseReleased(MouseEvent paramMouseEvent) {
	}

	public void mouseClicked(MouseEvent paramMouseEvent) {
	}

	public void mouseEntered(MouseEvent paramMouseEvent) {
	}

	public void mouseExited(MouseEvent paramMouseEvent) {
	}

}
