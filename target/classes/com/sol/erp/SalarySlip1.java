package com.sol.erp;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;

import com.sol.erp.ui.custom.SolCalendar;
import com.sol.erp.util.DBConnectionUtil;
import com.sol.erp.util.DateCalculationUtil;

import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JRViewer;

public class SalarySlip1 implements java.awt.event.ActionListener, java.awt.event.MouseListener, KeyListener {
	Connection con = null;
	Statement stat = null;
	ResultSet rs = null;

	Calendar c;

	
	SolCalendar skl = new SolCalendar();
	

	public static String[] head = { "   ", "  " };
	public static Object[][] data = { { "Name \t\t  \t  ", " " }, { "Address\t\t  ", " " },
			{ "EMP Code        \t  ", " " }, { "Designation          ", " " } };

	public static String[] head1 = { " ", "Particulars", "Amount", "Particulars", "Amount" };
	public static Object[][] data1 = { { new Boolean(true), "Total Working Days   ", " ", " ", " " },
			{ new Boolean(true), "Worked No. of Days   ", " ", " ", " " },
			{ new Boolean(true), "Official Leave       ", " ", " ", " " },
			{ new Boolean(true), "Leave Taken          ", " ", " ", " " },
			{ new Boolean(true), "Leave Entitlement    ", " ", " ", " " },
			{ new Boolean(true), "Leave Deduction      ", " ", " ", " " },
			{ new Boolean(true), "Pay Ent Days        ", " ", " ", " " },
			{ new Boolean(true), "Basic                ", " ", " ", " " }, { new Boolean(true), "HRA", " ", " ", " " },
			{ new Boolean(true), "Transport", " ", " ", " " },
			{ new Boolean(true), "Advance              ", " ", "Advance", " " },
			{ new Boolean(true), "MR", " ", "Income Tax Deduction", " " },
			{ new Boolean(false), "OT", " ", "Net Trf. in Bank", " " },
			{ new Boolean(false), "SPECIAL_ALLOWANCE", "00", " ", " " },
			{ new Boolean(false), "EL_ENCASHMENT", "00", " ", " " }, { new Boolean(false), "Bonus", "00", " ", " " } };

	JDialog f = new JDialog();

	public static DefaultTableModel model = new DefaultTableModel(data, head);
	public static JTable tb = new JTable(model) {
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
			return (i > 3) && ((i <= 12) || (i > 15)) && (i <= 16);
		}
	};
	JScrollPane sp = new JScrollPane(tb);

	public static DefaultTableModel model1 = new DefaultTableModel(data1, head1);
	public static JTable tb1 = new JTable(model1) {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@SuppressWarnings({ "unchecked", "rawtypes" })
		public Class getColumnClass(int paramAnonymousInt) {
			return getValueAt(0, paramAnonymousInt).getClass();
		}
	};

	JScrollPane sp1 = new JScrollPane(tb1);

	JTabbedPane tabbedpane = new JTabbedPane();
	JPanel application = new JPanel();
	JPanel preview = new JPanel();

	JLabel l1 = new JLabel("Emp Code");
	JLabel l2 = new JLabel("Co ID");
	JLabel l3 = new JLabel("Branch Code");
	JLabel l4 = new JLabel("Salary Mode");
	JLabel l5 = new JLabel("Year/Month");

	JTextField tf1 = new JTextField(4);
	JTextField tf2 = new JTextField(4);
	JTextField tf3 = new JTextField(4);
	JTextField tf4 = new JTextField(12);
	JTextField tf5 = new JTextField(6);

	JButton b1 = new JButton("Print");

	JPanel p1 = new JPanel();
	JPanel p2 = new JPanel();

	JPanel northtablepanel = new JPanel();
	JPanel centertablepanel = new JPanel();

	JPanel mainpanel = new JPanel();

	Border bor1 = javax.swing.BorderFactory.createTitledBorder("Search By");

	Font fo = new Font("Verdana", 1, 10);

	java.util.Date dt = new java.util.Date();
	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

	public JPanel DesignFrame() {
		tf5.setText(sdf.format(dt));
		con = DBConnectionUtil.getConnection();
		Container localContainer = f.getContentPane();
		localContainer.setLayout(new BorderLayout());

		application.setLayout(new BorderLayout());
		preview.setLayout(new BorderLayout());

		p1.setLayout(new FlowLayout(0));
		p2.setLayout(new FlowLayout(2));

		northtablepanel.setLayout(new BorderLayout());
		centertablepanel.setLayout(new BorderLayout());

		mainpanel.setLayout(new BorderLayout());

		tabbedpane.add(application, "Application");
		tabbedpane.add(preview, "Preview");

		p1.add(l5);
		p1.add(tf5);

		p1.add(l1);
		p1.add(tf1);
		p1.add(l2);
		p1.add(tf2);
		tf2.setEditable(false);
		p1.add(l3);
		p1.add(tf3);
		tf3.setEditable(false);
		p1.add(l4);
		p1.add(tf4);
		tf4.setEditable(false);

		p2.add(b1);

		tb.getColumnModel().getColumn(0).setPreferredWidth(180);
		tb.getColumnModel().getColumn(1).setPreferredWidth(450);
		tb.setRowHeight(18);
		tb.setFont(new Font("Verdana", 0, 10));
		tb.getTableHeader().setPreferredSize(new Dimension(50, 25));
		tb.getTableHeader().setFont(fo);

		tb1.getColumnModel().getColumn(0).setPreferredWidth(5);
		tb1.getColumnModel().getColumn(1).setPreferredWidth(210);
		tb1.getColumnModel().getColumn(2).setPreferredWidth(100);
		tb1.getColumnModel().getColumn(3).setPreferredWidth(210);
		tb1.getColumnModel().getColumn(4).setPreferredWidth(100);
		tb1.setRowHeight(18);
		tb1.setFont(new Font("Verdana", 0, 10));
		tb1.getTableHeader().setPreferredSize(new Dimension(50, 25));
		tb1.getTableHeader().setFont(fo);

		tf1.setFont(fo);
		tf2.setFont(fo);
		tf3.setFont(fo);
		tf4.setFont(fo);

		northtablepanel.add(sp, "Center");
		northtablepanel.setPreferredSize(new Dimension(100, 100));

		centertablepanel.add(northtablepanel, "North");
		centertablepanel.add(sp1, "Center");

		application.add(p1, "North");
		application.add(centertablepanel, "Center");
		application.add(p2, "South");

		mainpanel.add(tabbedpane, "Center");

		localContainer.add(mainpanel, "Center");

		tf1.addKeyListener(this);
		tf1.addActionListener(this);
		tf5.addMouseListener(this);

		tb1.addMouseListener(this);

		b1.addActionListener(this);

		f.setUndecorated(true);
		tf5.requestFocus();

		return mainpanel;
	}

	public void showFrame() {
		DesignFrame();
		f.setSize(700, 530);
		f.setLocationRelativeTo(null);
		f.setVisible(true);
	}

	public void salaryDetails() {
		model1.setValueAt(" ", 0, 2);
		model1.setValueAt(" ", 1, 2);
		model1.setValueAt(" ", 2, 2);
		model1.setValueAt(" ", 3, 2);
		model1.setValueAt(" ", 4, 2);
		model1.setValueAt(" ", 5, 2);
		model1.setValueAt(" ", 6, 2);
		model1.setValueAt(" ", 7, 2);
		model1.setValueAt(" ", 8, 2);
		model1.setValueAt(" ", 9, 2);

		model1.setValueAt(" ", 10, 2);
		model1.setValueAt(" ", 11, 2);
		model1.setValueAt(" ", 12, 2);

		model1.setValueAt(" ", 10, 4);
		model1.setValueAt(" ", 11, 4);
		model1.setValueAt(" ", 12, 4);

		model1.setValueAt(" ", 13, 2);
		model1.setValueAt(" ", 14, 2);
		model1.setValueAt(" ", 15, 2);

		String str1 = String.valueOf(DateCalculationUtil.getYear(tf5.getText()));
		String str2 = DateCalculationUtil.getNameofMonth(DateCalculationUtil.getMonth(tf5.getText()) - 1);

		String str3 = "111_" + tf2.getText() + "_" + tf3.getText() + "_" + tf4.getText() + "_" + str1
				+ "_" + str2;
		String str4 = String.valueOf(model.getValueAt(2, 1));
		Object localObject1;
		String str5;
		Object localObject2;
		Object localObject3;
		try {
			con = DBConnectionUtil.getConnection();
			Statement localStatement = con.createStatement();
			localObject1 = localStatement.executeQuery(
					"select T_L, L_D, ADVANCE1, TDS1, NET_SALARY from " + str3 + " where P_Card='" + str4 + "' ");

			while (((ResultSet) localObject1).next()) {
				str5 = new String(((ResultSet) localObject1).getString(1));
				localObject2 = new String(((ResultSet) localObject1).getString(2));
				localObject3 = new String(((ResultSet) localObject1).getString(3));
				String str6 = new String(((ResultSet) localObject1).getString(4));
				String str7 = new String(((ResultSet) localObject1).getString(5));

				model1.setValueAt("0", 0, 2);
				model1.setValueAt("0", 1, 2);
				model1.setValueAt("0", 2, 2);
				model1.setValueAt(str5, 3, 2);
				model1.setValueAt("0", 4, 2);
				model1.setValueAt(localObject2, 5, 2);
				model1.setValueAt("0", 6, 2);

				model1.setValueAt(localObject3, 10, 4);
				model1.setValueAt(str6, 11, 4);
				model1.setValueAt(str7, 12, 4);
			}

		} catch (Exception localException1) {

			System.out.println(localException1);
		}

		for (int i = 6; i < tb1.getRowCount(); i++) {
			localObject1 = String.valueOf(model1.getValueAt(i, 1));
			str5 = String.valueOf(model1.getValueAt(i, 0));

			if (str5.equalsIgnoreCase("true")) {
				try {
					con = DBConnectionUtil.getConnection();
					localObject2 = con.createStatement();
					localObject3 = ((Statement) localObject2).executeQuery(
							"select " + (String) localObject1 + " from " + str3 + " where P_Card='" + str4 + "' ");

					System.out.println("Column : " + (String) localObject1);

					while (((ResultSet) localObject3).next()) {
						model1.setValueAt(((ResultSet) localObject3).getString(1), i, 2);
					}
				} catch (Exception localException2) {
					System.out.println("----------------------------------------------------");
					System.out.println(localException2);
				}
			}
		}
	}


	public void salarySlipReport() {
		String str1 = "D:/SOLGROUPERP1/Erp/DataBase2/jrxml/SalarySlip.jrxml";

		String str2 = String.valueOf(DateCalculationUtil.getYear(tf5.getText()));
		String str3 = DateCalculationUtil.getNameofMonth(DateCalculationUtil.getMonth(tf5.getText()) - 1);
		String str4 = "SALARY SLIP FOR THE MONTH OF " + str3 + " " + str2;

		String str5 = String.valueOf(model.getValueAt(0, 1));
		String str6 = String.valueOf(model.getValueAt(1, 1));
		String str7 = String.valueOf(model.getValueAt(2, 1));
		String str8 = String.valueOf(model.getValueAt(3, 1));

		String str9 = model1.getValueAt(0, 2).toString();
		String str10 = model1.getValueAt(1, 2).toString();
		String str11 = model1.getValueAt(2, 2).toString();
		String str12 = model1.getValueAt(3, 2).toString();
		String str13 = model1.getValueAt(4, 2).toString();
		String str14 = model1.getValueAt(5, 2).toString();
		String str15 = model1.getValueAt(6, 2).toString();
		String str16 = model1.getValueAt(7, 2).toString();
		String str17 = model1.getValueAt(8, 2).toString();
		String str18 = model1.getValueAt(9, 2).toString();
		String str19 = model1.getValueAt(10, 2).toString();
		model1.getValueAt(11, 2).toString();
		model1.getValueAt(12, 2).toString();

		String str22 = model1.getValueAt(10, 4).toString();
		String str23 = model1.getValueAt(11, 4).toString();
		String str24 = model1.getValueAt(12, 4).toString();

		String str25 = "Extra Duties Remuneration                               "
				+ model1.getValueAt(12, 2).toString().trim();
		String str26 = "Special Allowance                                            "
				+ model1.getValueAt(13, 2).toString();

		String str27 = "Earn Leave                                                       "
				+ model1.getValueAt(14, 2).toString();
		String str28 = "Bonus                                                               "
				+ model1.getValueAt(15, 2).toString();

		try {
			HashMap<String, Object> localHashMap = new HashMap<String, Object>();

			localHashMap.put("Month", str4);

			localHashMap.put("EmpName", str5);
			localHashMap.put("Address", str6);
			localHashMap.put("EmpCode", str7);
			localHashMap.put("Designation", str8);

			localHashMap.put("WorkingDays", str9);
			localHashMap.put("WorkedDays", str10);
			localHashMap.put("OfficialLeave", str11);
			localHashMap.put("LeaveTaken", str12);
			localHashMap.put("LeaveEntitle", str13);
			localHashMap.put("LeaveDeduction", str14);
			localHashMap.put("PayEntitleDays", str15);
			localHashMap.put("Basic", str16);
			localHashMap.put("HRA", str17);
			localHashMap.put("Transport", str18);
			localHashMap.put("MR", str19);

			if (model1.getValueAt(12, 0).toString().equalsIgnoreCase("true")) {
				localHashMap.put("OT", str25);
			}

			if (model1.getValueAt(13, 0).toString().equalsIgnoreCase("true")) {
				localHashMap.put("SPECIAL_ALLOWANCE", str26);
			}

			if (model1.getValueAt(14, 0).toString().equalsIgnoreCase("true")) {
				localHashMap.put("EL", str27);
			}

			if (model1.getValueAt(15, 0).toString().equalsIgnoreCase("true")) {
				localHashMap.put("Bonus", str28);
			}

			localHashMap.put("Advance", str22);
			localHashMap.put("TDS", str23);
			localHashMap.put("Total", str24);

			JasperDesign localObject = JRXmlLoader.load(new File(str1));
			net.sf.jasperreports.engine.JasperReport localJasperReport = net.sf.jasperreports.engine.JasperCompileManager
					.compileReport((JasperDesign) localObject);

			con = DBConnectionUtil.getConnection();
			net.sf.jasperreports.engine.JasperPrint localJasperPrint = net.sf.jasperreports.engine.JasperFillManager
					.fillReport(localJasperReport, localHashMap, con);

			JRViewer localJRViewer = new JRViewer(localJasperPrint);

			preview.removeAll();
			preview.add(new JScrollPane(localJRViewer), "Center");
		} catch (Exception localException) {
			Object localObject = "Could not create the report " + localException.getMessage() + " "
					+ localException.getLocalizedMessage();
			System.out.println((String) localObject);
		}
	}

	public void empAddress() {
		model.setValueAt(" ", 1, 1);
		try {
			Statement localStatement = con.createStatement();
			ResultSet localResultSet = localStatement.executeQuery(
					"select Present_Add from HREMP_PERSONAL where EMP_CODE='" + tf1.getText() + "' ");

			while (localResultSet.next()) {
				String str = new String(localResultSet.getString(1));
				model.setValueAt(str, 1, 1);
			}
		} catch (Exception localException) {
			System.out.println("----------------------------------------------------");
			System.out.println(localException);
		}
	}

	public int absent() {
		String str1 = tf5.getText();
		String str2 = tf2.getText();
		String str3 = tf3.getText();

		int i = 0;

		int j = DateCalculationUtil.getMonth(str1);
		int k = DateCalculationUtil.getYear(str1);

		try {
			PreparedStatement localPreparedStatement = con.prepareStatement(
					"select absent from HR_EMP_HRS_DETAILS where Company_ID='" + str2 + "' and BRANCH_CODE='" + str3
							+ "' and Emp_Code='" + tf1.getText() + "' and MONTH=? and Year=? ");
			localPreparedStatement.setInt(1, j);
			localPreparedStatement.setInt(2, k);
			ResultSet localResultSet = localPreparedStatement.executeQuery();

			while (localResultSet.next()) {
				String str4 = new String(localResultSet.getString(1));
				i = Integer.parseInt(str4);
				System.out.println("Absent : " + i);
			}
		} catch (Exception localException) {
			System.out.println("----------------------------------------------------");
			System.out.println(localException);
		}
		return i;
	}

	public void officialLeave() {
		int i = absent();

		model1.setValueAt(" ", 2, 2);

		String str1 = tf2.getText();
		String str2 = tf3.getText();

		String str3 = String.valueOf(tf5.getText());
		str3.substring(0, 2);
		String str5 = str3.substring(3, 5);
		String str6 = str3.substring(6, 10);

		@SuppressWarnings("deprecation")
		java.sql.Date localDate1 = new java.sql.Date(Integer.parseInt(str6) - 1900, Integer.parseInt(str5) - 1, 1);
		@SuppressWarnings("deprecation")
		java.sql.Date localDate2 = new java.sql.Date(Integer.parseInt(str6) - 1900, Integer.parseInt(str5) - 1, 31);

		int j = 0;

		try {
			PreparedStatement localPreparedStatement = con
					.prepareStatement("select count(OCCASION) from HR_HOLIDAY_LIST where Company_ID='" + str1
							+ "' and BRANCH_CODE='" + str2 + "' and DATE between ? and ? ");
			localPreparedStatement.setDate(1, localDate1);
			localPreparedStatement.setDate(2, localDate2);
			ResultSet localResultSet = localPreparedStatement.executeQuery();

			while (localResultSet.next()) {
				String str7 = new String(localResultSet.getString(1));
				j = Integer.parseInt(str7);
			}
		} catch (Exception localException) {
			System.out.println("----------------------------------------------------");
			System.out.println(localException);
		}

		int k = DateCalculationUtil.countDay(Integer.parseInt(str5), Integer.parseInt(str6), "SUN");
		int m = DateCalculationUtil.countDay(Integer.parseInt(str5), Integer.parseInt(str6), "SAT");

		int n = 1;
		int i1 = k + m + j - n;
		System.out.println("Sundays : " + k + "\t Saturdays : " + m + "\t TotalLeave : " + i1 + "\t Holidays : " + j);

		model1.setValueAt(String.valueOf(i1), 2, 2);

		System.out.println(daysInMonth(tf5.getText()));

		int i2 = daysInMonth(tf5.getText()) - i1;
		int i3 = i2 - i;

		model1.setValueAt(String.valueOf(daysInMonth(tf5.getText())), 0, 2);

		model1.setValueAt(String.valueOf(i3), 1, 2);

		int i4 = Integer.parseInt(String.valueOf(model1.getValueAt(4, 2)));
		int i5 = Integer.parseInt(String.valueOf(model1.getValueAt(5, 2)));

		model1.setValueAt(String.valueOf(i3 + i1 + i4 - i5), 6, 2);
	}

	public int daysInMonth(String paramString) {
		c = new GregorianCalendar(DateCalculationUtil.getYear(paramString) - 1900, DateCalculationUtil.getMonth(paramString) - 1,
		        DateCalculationUtil.getDate(paramString));
		return c.getActualMaximum(5);
	}

	public void extraSalary() {
		String str1 = String.valueOf(DateCalculationUtil.getYear(tf5.getText()));
		String str2 = DateCalculationUtil.getNameofMonth(DateCalculationUtil.getMonth(tf5.getText()) - 1);

		String str3 = "111_" + tf2.getText() + "_" + tf3.getText() + "_" + tf4.getText() + "_" + str1
				+ "_" + str2;
		String str4 = String.valueOf(model.getValueAt(2, 1));

		String str5 = String.valueOf(model1.getValueAt(tb1.getSelectedRow(), 1));

		try {
			con = DBConnectionUtil.getConnection();
			Statement localStatement = con.createStatement();
			ResultSet localResultSet = localStatement
					.executeQuery("select " + str5 + " from " + str3 + " where P_Card='" + str4 + "' ");

			while (localResultSet.next()) {
				String str6 = new String(localResultSet.getString(1));
				System.out.println(str5 + " : " + str6);
				model1.setValueAt(str6, tb1.getSelectedRow(), 2);
			}
		} catch (Exception localException) {
			System.out.println("----------------------------------------------------");
			System.out.println(localException);
		}
	}

	public void actionPerformed(ActionEvent paramActionEvent) {
		if (paramActionEvent.getSource() == b1) {
			salarySlipReport();
			tabbedpane.setSelectedIndex(1);
		}

		if (paramActionEvent.getSource() == tf1) {
			tf2.setText(EmpTB.getCompanyID(tf1.getText().toUpperCase()));
			tf3.setText(EmpTB.getBranchCode(tf1.getText().toUpperCase()));
			tf4.setText(EmpTB.getReqMode(tf1.getText().toUpperCase()));

			if (tf4.getText().equalsIgnoreCase("Salaried")) {
				model1.setValueAt(new Boolean(true), 12, 0);
				model1.setValueAt(new Boolean(false), 13, 0);
			}
			if (tf4.getText().equalsIgnoreCase("Package")) {
				model1.setValueAt(new Boolean(false), 12, 0);
				model1.setValueAt(new Boolean(true), 13, 0);
			}

			model.setValueAt(EmpTB.getEmpName(tf1.getText().toUpperCase()), 0, 1);
			model.setValueAt(EmpTB.getPunchcardNo(tf1.getText().toUpperCase()), 2, 1);
			model.setValueAt(EmpTB.getDesig(tf1.getText().toUpperCase()), 3, 1);

			salaryDetails();
			empAddress();
			officialLeave();
		}
	}

	public void mousePressed(MouseEvent paramMouseEvent) {
		if (paramMouseEvent.getSource() == tf5) {
			if (javax.swing.SwingUtilities.isRightMouseButton(paramMouseEvent)) {
				skl.showCalendar();
				skl.getDate(tf5);
			}
		}
	}

	public void mouseReleased(MouseEvent paramMouseEvent) {
	}

	public void mouseClicked(MouseEvent paramMouseEvent) {
		int i = tb1.getSelectedColumn();
		int j = tb1.getSelectedRow();

		if ((j >= 12) && (i == 0)) {
			String str = model1.getValueAt(j, i).toString();
			if (str.equalsIgnoreCase("true")) {
				extraSalary();
			}
			if (str.equalsIgnoreCase("false")) {
				model1.setValueAt(" ", j, 2);
			}
		}
	}

	public void mouseEntered(MouseEvent paramMouseEvent) {
	}

	public void mouseExited(MouseEvent paramMouseEvent) {
	}

	public void keyPressed(KeyEvent paramKeyEvent) {
		if (paramKeyEvent.getSource() == tf1) {
			int i = paramKeyEvent.getKeyCode();

			if (i == 112) {
				HelpTable localHelpTable = new HelpTable();
				localHelpTable.showFrame();
				localHelpTable.getEmpCode(tf1);
			}
		}
	}

	public void keyReleased(KeyEvent paramKeyEvent) {
	}

	public void keyTyped(KeyEvent paramKeyEvent) {
	}

}
