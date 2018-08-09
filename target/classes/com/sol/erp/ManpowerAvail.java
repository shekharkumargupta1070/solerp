package com.sol.erp;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
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
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JViewport;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import com.sol.erp.ui.custom.SolCalendar;
import com.sol.erp.ui.custom.SolCellModel;
import com.sol.erp.ui.custom.SolTableModel;
import com.sol.erp.util.DBConnectionUtil;
import com.sol.erp.util.DateCalculationUtil;
import com.sol.erp.util.DateDifferencesUtil;
import com.sol.erp.util.SessionUtil;
import com.sol.erp.util.formater.SolDateFormatter;

public class ManpowerAvail implements ActionListener, MouseListener, KeyListener, ItemListener {
	
	Connection con;
	Connection con1;
	Statement stat;
	ResultSet rs;
	PreparedStatement prsm;
	public static String codestr;
	EmpMonthlyHRS emh;
	
	
	CompanyTable ct;
	EmpWorkAlloted ew;
	String[] head;
	Object[][] data;
	String[] head1;
	Object[][] data1;
	String[] head2;
	Object[][] data2;
	String[] head3;
	Object[][] data3;
	ArrayList ar;
	DecimalFormat df;
	DecimalFormat df2;
	JInternalFrame internalFrame;
	JLabel datelabel;
	JLabel l1;
	JLabel l2;
	JLabel l3;
	JLabel l4;
	JLabel l5;
	JLabel l6;
	JTextField coidtf;
	JTextField conametf;
	JTextField brcodetf;
	JTextField datetf;
	JTextField datetf2;
	JComboBox cb1;
	JComboBox cb2;
	JComboBox cb3;
	JComboBox cb4;
	JComboBox cb5;
	DefaultTableModel model;
	JTable tb;
	DefaultTableModel model1;
	JTable tb1;
	JViewport maincenterview;
	JViewport centerview;
	JViewport rowheaderview;
	JViewport columnheaderview;
	JViewport rightview;
	JScrollPane sp;
	JScrollPane sp2;
	JScrollPane rowheadersp;
	JScrollPane mainsp;
	JProgressBar progressBar;
	JProgressBar subprogress;
	JLabel proglabel;
	JLabel projectlabel;
	JComboBox projecttf;
	JButton b1;
	JButton b2;
	JButton b3;
	JButton b4;
	JButton b5;
	JMenuBar menubar;
	JMenu ma;
	JMenu mb;
	JMenuItem ma1;
	JMenuItem ma2;
	JMenuItem ma3;
	JMenuItem mb1;
	JMenuItem mb2;
	JMenuItem mb3;
	JCheckBoxMenuItem mb4;
	JMenuItem mb5;
	JPanel mainPanel;
	JPanel northpanel;
	JPanel centerpanel;
	JPanel bottompanel;
	JPanel northp1;
	JPanel northp2;
	JPanel progpanel1;
	JPanel progpanel2;
	JPanel southpanel;
	JPanel southpanel2;
	JPanel southpanel3;
	JTabbedPane tabbedpane;
	JPanel detailsPanel;
	JPanel yearlyDetailsPanel;
	Toolkit tk;
	java.util.Date dat;
	SimpleDateFormat formatter;
	String dateString;
	String time;
	Font fo;
	SolTableModel skt;
	SolCellModel skr;
	Renderer rend1;
	Renderer2 rend2;
	Renderer3 rend3;
	Border bor1;
	TechMainFrame tmf;
	String coidstr;
	String brcodestr;
	ArrayList datelist;
	int totaldays;

	class Renderer extends DefaultTableCellRenderer {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		Renderer() {
		}

		public Component getTableCellRendererComponent(JTable paramJTable, Object paramObject, boolean paramBoolean1,
				boolean paramBoolean2, int paramInt1, int paramInt2) {
			setEnabled((paramJTable == null) || (paramJTable.isEnabled()));

			if (paramInt1 % 2 == 0) {
				setBackground(new Color(250, 250, 250));
				setForeground(Color.darkGray);

			} else {
				setBackground(new Color(230, 230, 230));
				setForeground(Color.darkGray);
			}

			if ((paramInt2 == 0) || (paramInt2 == 2)) {
				setHorizontalAlignment(0);
			}
			if (paramInt2 == 1) {
				setHorizontalAlignment(2);
			}

			if (paramInt2 >= 0) {
				setHorizontalAlignment(0);
				setBackground(new Color(230, 240, 250));

				String str1 = (String) projecttf.getSelectedItem();
				String str2 = str1.substring(0, str1.indexOf("|") - 1);

				String str3 = String.valueOf(model.getValueAt(paramInt1, paramInt2));

				if ((str3.length() > 1) && (str3.equalsIgnoreCase(str2))) {
					setBackground(Color.red);
					setForeground(Color.white);
				}

				String str4 = String.valueOf(model.getValueAt(1, paramInt2));

				if (str4.equalsIgnoreCase("SUN")) {
					setBackground(new Color(130, 180, 240));
					setForeground(Color.white);
				}

				if ((str4.equalsIgnoreCase("N")) || (str4.equalsIgnoreCase("R")) || (str4.equalsIgnoreCase("?"))) {
					setBackground(new Color(250, 140, 130));
					setForeground(Color.white);
				}

				if (String.valueOf(model.getValueAt(paramInt1, paramInt2)).equalsIgnoreCase("X")) {
					setForeground(Color.black);
				}
				if (String.valueOf(model.getValueAt(paramInt1, paramInt2)).equalsIgnoreCase("Y")) {
					setForeground(Color.blue);
				}
				if (String.valueOf(model.getValueAt(paramInt1, paramInt2)).equalsIgnoreCase("A")) {
					setForeground(Color.red);
				}
				if (String.valueOf(model.getValueAt(paramInt1, paramInt2)).equalsIgnoreCase("C")) {
					setBackground(Color.white);
					setBackground(new Color(240, 240, 255));
				}
				if (String.valueOf(model.getValueAt(paramInt1, paramInt2)).equalsIgnoreCase("E")) {
					setBackground(Color.white);
					setForeground(Color.blue);
				}

				if (str3.length() > 1) {
					String str5 = str3.substring(str3.length() - 1, str3.length());

					if (str5.equalsIgnoreCase("D")) {
						setBackground(Color.orange);
						setForeground(Color.darkGray);
					}
					if (str5.equalsIgnoreCase("C")) {
						setBackground(new Color(180, 240, 180));
						setForeground(Color.darkGray);
					}
				}
			}

			tb.setRowHeight(0, 30);
			tb.setRowHeight(1, 55);
			tb.setRowHeight(2, 22);

			tb1.setRowHeight(0, 30);
			tb1.setRowHeight(1, 55);
			tb1.setRowHeight(2, 22);

			if (paramInt1 == 1) {
				setHorizontalAlignment(0);
				setBackground(new Color(130, 180, 240));
				setForeground(Color.white);
			}

			super.getTableCellRendererComponent(paramJTable, paramObject, paramBoolean1, paramBoolean2, paramInt1,
					paramInt2);

			int i = tb.getSelectedRow();
			int j = tb.getSelectedColumn();

			if ((paramInt1 == i) && (paramInt2 == j)) {
				tb.repaint();
				tb.setSelectionBackground(Color.blue);
				tb.setSelectionForeground(Color.white);
				setFont(new Font("Currier", 1, 14));
				setHorizontalAlignment(0);
			}

			return this;
		}
	}

	class Renderer2 extends DefaultTableCellRenderer {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		Renderer2() {
		}

		public Component getTableCellRendererComponent(JTable paramJTable, Object paramObject, boolean paramBoolean1,
				boolean paramBoolean2, int paramInt1, int paramInt2) {
			setEnabled((paramJTable == null) || (paramJTable.isEnabled()));

			if (paramInt1 % 2 == 0) {
				setBackground(new Color(250, 250, 250));
				setForeground(Color.darkGray);

			} else {
				setBackground(new Color(230, 230, 230));
				setForeground(Color.darkGray);
			}

			if ((paramInt2 >= 0) && (paramInt1 == 1)) {
				setHorizontalAlignment(0);

				setBackground(Color.gray);
				setForeground(Color.white);
			}

			if ((paramInt1 > 1) && (paramInt2 == 1)) {
				setHorizontalAlignment(2);
			}
			if ((paramInt1 > 1) && (paramInt2 > 1)) {
				setHorizontalAlignment(0);
			}

			super.getTableCellRendererComponent(paramJTable, paramObject, paramBoolean1, paramBoolean2, paramInt1,
					paramInt2);

			return this;
		}
	}

	class Renderer3 extends DefaultTableCellRenderer {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		Renderer3() {
		}

		public Component getTableCellRendererComponent(JTable paramJTable, Object paramObject, boolean paramBoolean1,
				boolean paramBoolean2, int paramInt1, int paramInt2) {
			setEnabled((paramJTable == null) || (paramJTable.isEnabled()));

			if (paramInt1 % 2 == 0) {
				setBackground(new Color(250, 250, 250));
				setForeground(Color.darkGray);
			} else {
				setBackground(new Color(230, 230, 230));
				setForeground(Color.darkGray);
			}

			setHorizontalAlignment(4);

			if (paramInt1 == 1) {
				setHorizontalAlignment(0);

				setBackground(Color.gray);
				setForeground(Color.white);
			}
			super.getTableCellRendererComponent(paramJTable, paramObject, paramBoolean1, paramBoolean2, paramInt1,
					paramInt2);

			int i = tb.getColumnCount() - 5;
			if (paramInt2 >= i) {
				setFont(fo);
			}
			return this;
		}
	}

	public JPanel DesignFrame() {
		Container localContainer = internalFrame.getContentPane();
		localContainer.setLayout(new BorderLayout());
		mainPanel.setLayout(new BorderLayout());
		centerpanel.setLayout(new BorderLayout());
		northpanel.setLayout(new BorderLayout());
		northp1.setLayout(new FlowLayout(0));
		northp1.setLayout(new FlowLayout(2));
		bottompanel.setLayout(new BorderLayout());
		southpanel.setLayout(new BorderLayout());
		southpanel2.setLayout(new FlowLayout(2));
		southpanel3.setLayout(new FlowLayout(2));

		tabbedpane.add("Manpower Status", mainPanel);
		tabbedpane.add("Monthly Spent Hrs.", yearlyDetailsPanel.add(new EmpMonthlyHRS().DesignFrame()));
		tabbedpane.setBackground(Color.white);
		tabbedpane.setTabPlacement(1);

		cb1.addItem("January");
		cb1.addItem("February");
		cb1.addItem("March");
		cb1.addItem("April");
		cb1.addItem("May");
		cb1.addItem("June");
		cb1.addItem("July");
		cb1.addItem("August");
		cb1.addItem("September");
		cb1.addItem("October");
		cb1.addItem("November");
		cb1.addItem("December");

		cb2.setEnabled(false);
		cb3.setEnabled(false);

		for (int i = 0; i < tb.getColumnCount(); i++) {
			tb.getColumnModel().getColumn(i).setPreferredWidth(70);
		}

		tb.setSelectionBackground(new Color(60, 60, 160));
		tb.setSelectionForeground(Color.white);
		tb.getTableHeader().setPreferredSize(new Dimension(25, 25));
		tb.getTableHeader().setFont(fo);
		tb.setRowHeight(20);
		tb.setIntercellSpacing(new Dimension(1, 1));
		tb.setFont(new Font("Verdana", 1, 8));
		tb.setAutoResizeMode(0);
		tb.setShowGrid(false);

		for (int i = 0; i < tb.getColumnCount() - 5; i++) {
			tb.getColumnModel().getColumn(i).setCellRenderer(rend1);
		}

		tb1.getColumnModel().getColumn(0).setPreferredWidth(60);
		tb1.getColumnModel().getColumn(1).setPreferredWidth(160);
		tb1.getColumnModel().getColumn(2).setPreferredWidth(35);
		tb1.getColumnModel().getColumn(3).setPreferredWidth(35);

		tb1.setSelectionBackground(new Color(60, 60, 160));
		tb1.setSelectionForeground(Color.white);
		tb1.setRowHeight(20);
		tb1.setIntercellSpacing(new Dimension(1, 1));
		tb1.setFont(fo);
		tb1.setAutoResizeMode(0);
		tb1.setShowGrid(false);

		for (int i = 0; i < tb1.getColumnCount(); i++) {
			tb1.getColumnModel().getColumn(i).setCellRenderer(rend2);
		}

		datelabel.setForeground(Color.blue);
		datelabel.setFont(fo);
		l1.setFont(fo);
		l2.setFont(fo);
		l3.setFont(fo);

		coidtf.setFont(fo);
		conametf.setFont(fo);
		brcodetf.setFont(fo);
		cb4.setFont(fo);
		datetf.setFont(fo);
		datetf.setForeground(Color.darkGray);
		datetf2.setFont(fo);
		datetf2.setForeground(Color.darkGray);
		datetf.setHorizontalAlignment(4);
		datetf2.setHorizontalAlignment(4);

		northp2.add(datelabel);
		northp2.add(l1);
		northp2.add(coidtf);
		northp2.add(conametf);
		northp2.add(l2);
		northp2.add(brcodetf);

		conametf.setEditable(false);
		brcodetf.setEditable(false);

		northp1.add(l4);
		l4.setFont(fo);
		northp1.add(datetf);
		northp1.add(l5);
		l5.setFont(fo);
		northp1.add(datetf2);
		northp1.add(l3);
		northp1.add(cb4);
		northp1.add(l6);
		northp1.add(cb5);
		l6.setFont(fo);
		cb5.setFont(fo);

		northp1.add(b1);
		b1.setFont(fo);

		northpanel.add(northp1, "West");

		b1.setBackground(Color.white);
		projecttf.setBackground(Color.white);
		cb4.setBackground(Color.white);
		cb5.setBackground(Color.white);

		projecttf.setFont(fo);
		projectlabel.setFont(fo);
		progpanel1.add(projectlabel);
		progpanel1.add(projecttf);

		progressBar.setForeground(Color.blue);
		subprogress.setForeground(Color.blue);

		southpanel.add(progpanel1, "West");
		southpanel.add(progpanel2, "East");

		bottompanel.add(southpanel, "North");
		bottompanel.add(southpanel2, "Center");

		ma.add(ma2);
		ma.addSeparator();
		ma.add(ma3);

		mb.add(mb1);
		mb.addSeparator();
		mb.add(mb2);
		mb.add(mb3);
		mb.addSeparator();
		mb.add(mb4);
		mb.add(mb5);

		menubar.add(ma);
		menubar.add(mb);

		mainPanel.add(northpanel, "North");
		mainPanel.add(centerpanel, "Center");
		mainPanel.add(bottompanel, "South");

		localContainer.add(tabbedpane, "Center");

		mb4.addActionListener(this);

		projecttf.addActionListener(this);

		coidtf.addActionListener(this);
		coidtf.addKeyListener(this);

		cb1.addActionListener(this);
		b1.addActionListener(this);
		b2.addActionListener(this);
		b3.addActionListener(this);
		b4.addActionListener(this);
		b5.addActionListener(this);

		cb4.addActionListener(this);
		cb4.addMouseListener(this);

		cb5.addActionListener(this);

		mb1.addActionListener(this);
		ma2.addActionListener(this);
		ma3.addActionListener(this);

		tb.addMouseListener(this);
		tb.addKeyListener(this);

		tb1.addMouseListener(this);
		tb1.addKeyListener(this);

		datetf.addMouseListener(this);
		datetf2.addMouseListener(this);

		
		internalFrame.setJMenuBar(menubar);
		internalFrame.setSize(1024 ,600);
		internalFrame.setVisible(true);

		desigList();
		projectList();
		projectList();
		empList2();
		datetf.requestFocus();

		return mainPanel;
	}

	public void px() {
		DesignFrame();
		
	}

	public void makeTable() {
		datelist.clear();

		model.setColumnCount(0);
		model.setNumRows(0);
		model1.setNumRows(0);

		String str1 = DateCalculationUtil.getNameofMonth(DateCalculationUtil.getMonth(datetf.getText()) - 1).toUpperCase() + " "
				+ String.valueOf(DateCalculationUtil.getYear(datetf.getText()));

		totaldays = DateDifferencesUtil.getDayCount(datetf.getText(), datetf2.getText());
		datelist = DateDifferencesUtil.getDate(datetf.getText(), datetf2.getText(), totaldays);

		model.setColumnCount(totaldays + 5);
		model.addRow(new Object[0]);
		model.addRow(new Object[0]);

		for (int i = 0; i < totaldays; i++) {
			model.setValueAt(String.valueOf(datelist.get(i)), 0, i);
			model.setValueAt(String.valueOf(datelist.get(i)), 1, i);
			tb.getColumnModel().getColumn(i).setCellRenderer(rend1);
		}

		model.setValueAt("CAP.", 1, tb.getColumnCount() - 5);
		model.setValueAt("USED", 1, tb.getColumnCount() - 4);
		model.setValueAt("WASTED", 1, tb.getColumnCount() - 3);
		model.setValueAt("FREE", 1, tb.getColumnCount() - 2);
		model.setValueAt("E.FREE", 1, tb.getColumnCount() - 1);

		tb.getColumnModel().getColumn(tb.getColumnCount() - 5).setPreferredWidth(40);
		tb.getColumnModel().getColumn(tb.getColumnCount() - 4).setPreferredWidth(40);
		tb.getColumnModel().getColumn(tb.getColumnCount() - 3).setPreferredWidth(50);
		tb.getColumnModel().getColumn(tb.getColumnCount() - 2).setPreferredWidth(50);
		tb.getColumnModel().getColumn(tb.getColumnCount() - 1).setPreferredWidth(50);

		tb.getColumnModel().getColumn(tb.getColumnCount() - 5).setCellRenderer(rend3);
		tb.getColumnModel().getColumn(tb.getColumnCount() - 4).setCellRenderer(rend3);
		tb.getColumnModel().getColumn(tb.getColumnCount() - 3).setCellRenderer(rend3);
		tb.getColumnModel().getColumn(tb.getColumnCount() - 2).setCellRenderer(rend3);
		tb.getColumnModel().getColumn(tb.getColumnCount() - 1).setCellRenderer(rend3);

		model1.addRow(new Object[] { "", "", "" });
		model1.addRow(new Object[] { "", "<HTML><Center>( " + cb4.getSelectedItem() + " )<br>" + str1
				+ "<BR>TOTAL DAYS : " + String.valueOf(totaldays), " " });

		DateCalculationUtil.getMonth(datetf.getText());
		DateCalculationUtil.getYear(datetf.getText());

		for (int k = 0; k < totaldays; k++) {

			model.setValueAt("-", 1, k);
			String str2 = String.valueOf(model.getValueAt(0, k));
			String str3 = DateCalculationUtil.NameOfDay(str2);
			model.setValueAt(str3, 1, k);

			java.sql.Date localDate = SolDateFormatter.DDMMYYtoSQL(str2);

			try {
				prsm = con.prepareStatement("Select HOLIDAY_TYPE from HR_HOLIDAY_LIST where date = ? and  COMPANY_ID='"
						+ coidstr + "' and BRANCH_CODE='" + brcodestr + "'  ");
				prsm.setDate(1, localDate);
				rs = prsm.executeQuery();
				while (rs.next()) {
					String str4 = new String(rs.getString(1));
					if (str4.equalsIgnoreCase("NH")) {
						model.setValueAt("N", 1, k);
					}
					if (str4.equalsIgnoreCase("RH")) {
						model.setValueAt("R", 1, k);
					}
					if (str4.equalsIgnoreCase("OH")) {
						model.setValueAt("?", 1, k);
					}
				}
			} catch (Exception localException) {
				System.out.println("Official Leave : " + localException);
			}
		}
	}

	public void empList2() {
		makeTable();

		String str1 = (String) projecttf.getSelectedItem();
		String str2 = str1.substring(0, str1.indexOf("|"));

		try {
			con = DBConnectionUtil.getConnection();
			stat = con.createStatement();
			rs = stat.executeQuery(
					"SELECT distinct(p.emp_code), e.emp_name, e.factor, e.average_hrs from PROJECTMANPOWER p, EMP_STATUS e where p.PROJECT_NO='"
							+ str2 + "' and p.emp_code=e.emp_code group by p.emp_code");

			while (rs.next()) {
				String str3 = new String(rs.getString(1));
				String str4 = new String(rs.getString(2));
				String str5 = new String(rs.getString(3));
				String str6 = new String(rs.getString(4));

				model.addRow(new Object[] { "" });
				model1.addRow(new Object[] { str3.toUpperCase(), str4.toUpperCase(), str5, str6 });
			}
		} catch (Exception localException) {
			System.out.println(localException);
		}
		model.addRow(new Object[] { "" });
		model1.addRow(new Object[] { "", "TOTAL : " + String.valueOf(tb.getRowCount() - 1), "" });
		model1.setValueAt(
				"<html><Center>" + projecttf.getSelectedItem() + "<BR>TOTAL DAYS : " + String.valueOf(totaldays), 1, 1);
		model1.setValueAt("<html><Center>F", 1, 2);
		model1.setValueAt("<html><Center>H", 1, 3);

		capacity();
	}

	public void projectList() {
		try {
			con = DBConnectionUtil.getConnection();
			stat = con.createStatement();
			rs = stat.executeQuery(
					"SELECT project_no, project_name, co_code, Name from PROJECT_CO order by project_no DESC ");
			while (rs.next()) {
				String str1 = new String(rs.getString(1));
				String str2 = new String(rs.getString(2));
				new String(rs.getString(3));
				new String(rs.getString(4));

				projecttf.addItem(str1.trim() + " | " + str2.trim().toUpperCase());
			}
		} catch (Exception localException) {
			System.out.println(localException);
		}
	}

	public void projectDate() {
		String str1 = (String) projecttf.getSelectedItem();
		String str2 = str1.substring(0, str1.indexOf("|") - 1);

		try {
			con = DBConnectionUtil.getConnection();
			PreparedStatement localPreparedStatement = con
					.prepareStatement("SELECT start, new_final_date from PROJECT_CO where project_no =? ");
			localPreparedStatement.setInt(1, Integer.parseInt(str2));
			ResultSet localResultSet = localPreparedStatement.executeQuery();
			while (localResultSet.next()) {
				String str3 = SolDateFormatter.SQLtoDDMMYY(localResultSet.getDate(1));
				String str4 = SolDateFormatter.SQLtoDDMMYY(localResultSet.getDate(2));

				datetf.setText(str3);
				datetf2.setText(str4);
			}
		} catch (Exception localException) {
			System.out.println(localException);
		}
	}

	public void desigList() {
		CompanyTable localCompanyTable = new CompanyTable();

		String str1 = localCompanyTable.getMyCompanyId();
		String str2 = localCompanyTable.getMyBranchCode();

		cb4.removeAllItems();
		cb4.addItem("%");

		try {
			stat = con.createStatement();
			rs = stat.executeQuery(
					"Select distinct(JOB) from emp_STATUS where co_id='" + str1 + "' and branch_code='" + str2 + "'  ");

			while (rs.next()) {
				String str3 = new String(rs.getString(1));
				cb4.addItem(str3.toUpperCase());
			}
		} catch (Exception localException) {
			System.out.println(localException);
		}
		unitList();
	}

	public void unitList() {
		CompanyTable localCompanyTable = new CompanyTable();
		localCompanyTable.getMyCompanyId();
		localCompanyTable.getMyBranchCode();

		cb5.removeAllItems();
		cb5.addItem("%");
		try {
			stat = con.createStatement();
			rs = stat.executeQuery("Select distinct(UNIT) from PHONE order by UNIT");

			while (rs.next()) {
				String str3 = new String(rs.getString(1));
				cb5.addItem(str3.toUpperCase());
			}
		} catch (Exception localException) {
			System.out.println(localException);
		}
	}

	public void unitWiseEmpList() {
		makeTable();

		String str1 = "%" + String.valueOf(cb5.getSelectedItem()).trim() + "%";
		String str2 = (String) cb4.getSelectedItem();
		java.sql.Date localDate = SolDateFormatter.DDMMYYtoSQL(datetf.getText());

		try {
			con = DBConnectionUtil.getConnection();
			PreparedStatement localPreparedStatement = con.prepareStatement(
					"SELECT p.emp_code, e.emp_name, e.factor, e.average_hrs from PHONE p, EMP_STATUS e, HREMP_JOIN h where p.unit like ? and e.job like ? and e.emp_code not in(select emp_code from HR_EX_EMP where releave_date <= ?)and h.doj2 <= ? and p.emp_code=e.emp_code and p.emp_code=h.emp_code group by e.emp_code");
			localPreparedStatement.setString(1, str1);
			localPreparedStatement.setString(2, str2);
			localPreparedStatement.setDate(3, localDate);
			localPreparedStatement.setDate(4, localDate);
			rs = localPreparedStatement.executeQuery();
			while (rs.next()) {
				String str3 = new String(rs.getString(1));
				String str4 = new String(rs.getString(2));
				String str5 = new String(rs.getString(3));
				String str6 = new String(rs.getString(4));

				model.addRow(new Object[] { "" });
				model1.addRow(new Object[] { str3.toUpperCase(), str4.toUpperCase(), str5, str6 });
			}
		} catch (Exception localException) {
			System.out.println(localException);
		}
		model.addRow(new Object[] { "" });
		model1.addRow(new Object[] { "", "TOTAL : " + String.valueOf(tb.getRowCount() - 1), "" });
		model1.setValueAt(
				"<html><Center>" + projecttf.getSelectedItem() + "<BR>TOTAL DAYS : " + String.valueOf(totaldays), 1, 1);
		model1.setValueAt("<html><Center>F", 1, 2);
		model1.setValueAt("<html><Center>H", 1, 3);

		mainsp.setRowHeaderView(tb1);
		mainsp.getRowHeader().setPreferredSize(new Dimension(285, 5000));
		centerpanel.add(mainsp, "Center");
		tb.setTableHeader(null);

		sp.setCorner("UPPER_LEADING_CORNER", new JPanel());
		sp.revalidate();

		capacity();
	}

	public class report extends Thread {
		public report() {
		}

		public void run() {
			tb.getColumnCount();
			int j = tb.getRowCount() - 1;

			for (int k = 2; k < j; k++) {
				String.valueOf(df.format(DateCalculationUtil.getMonth(datetf.getText())));
				String.valueOf(DateCalculationUtil.getYear(datetf.getText()));

				String str3 = String.valueOf(model1.getValueAt(k, 0));

				proglabel.setText("Emp Code : " + str3);
				progressBar.setMinimum(0);
				progressBar.setMaximum(j - 2);
				progressBar.setValue(k);

				/*
				try {
					Thread.sleep(10L);
				} catch (InterruptedException localInterruptedException) {
					System.out.println(localInterruptedException);
				}
				*/

				DateCalculationUtil.getNumberOfDays(datetf.getText());

				for (int m = 0; m < tb.getColumnCount() - 5; m++) {

					String str4 = String.valueOf(model.getValueAt(0, m));

					subprogress.setMinimum(2);
					subprogress.setMaximum(30);
					subprogress.setValue(m);

					java.sql.Date localDate = SolDateFormatter.DDMMYYtoSQL(str4);
					Object localObject2;
					Object localObject3;
					try {
						PreparedStatement localPreparedStatement1 = con
								.prepareStatement("Select project_no from projectmanpower where emp_code='" + str3
										+ "' and from_date2=? ");
						localPreparedStatement1.setDate(1, localDate);
						ResultSet localObject1 = localPreparedStatement1.executeQuery();

						localObject2 = Boolean.valueOf(DateCalculationUtil.isCurrentDate(str4, datelabel.getText()));

						if (!((Boolean) localObject2).booleanValue()) {
							model.setValueAt("X", k, m);
						}

						while (((ResultSet) localObject1).next()) {
							localObject3 = new String(((ResultSet) localObject1).getString(1));
							model.setValueAt(localObject3, k, m);
						}
					} catch (Exception localException1) {
						System.out.println(localException1);
					}

					String str5 = (String) projecttf.getSelectedItem();
					Object localObject1 = str5.substring(0, str5.indexOf("|") - 1);
					String str6;
					try {
						localObject2 = con
								.prepareStatement("Select DRAWING_NO, Project_No from Detailing where emp_code='" + str3
										+ "' and DATE_MONTH = ? and project_no= ? ");
						((PreparedStatement) localObject2).setString(1, str4);
						((PreparedStatement) localObject2).setString(2, (String) localObject1);
						localObject3 = ((PreparedStatement) localObject2).executeQuery();

						str6 = String.valueOf(model.getValueAt(k, m));

						while (((ResultSet) localObject3).next()) {
							new String(((ResultSet) localObject3).getString(1));
							new String(((ResultSet) localObject3).getString(2));
							model.setValueAt(str6 + "-D", k, m);
						}
					} catch (Exception localException2) {
						System.out.println(localException2);
					}

					try {
						PreparedStatement localPreparedStatement2 = con
								.prepareStatement("Select DRAWING_NO, Project_No from CHECKING where emp_code='" + str3
										+ "' and DATE_MONTH = ? and project_no= ? ");
						localPreparedStatement2.setString(1, str4);
						localPreparedStatement2.setString(2, (String) localObject1);
						localObject3 = localPreparedStatement2.executeQuery();

						str6 = String.valueOf(model.getValueAt(k, m));

						while (((ResultSet) localObject3).next()) {
							new String(((ResultSet) localObject3).getString(1));
							new String(((ResultSet) localObject3).getString(2));
							model.setValueAt(str6 + "-C", k, m);
						}
					} catch (Exception localException3) {
						System.out.println(localException3);
					}

					try {
						PreparedStatement localPreparedStatement3 = con
								.prepareStatement("Select category from HREMP_LEAVES where emp_code='" + str3
										+ "' and approve_tl='true' and approve_hr='true' AND date= ? ");
						localPreparedStatement3.setDate(1, localDate);
						localObject3 = localPreparedStatement3.executeQuery();

						str6 = null;

						while (((ResultSet) localObject3).next()) {
							str6 = new String(((ResultSet) localObject3).getString(1));
							if (str6.equalsIgnoreCase("LWP")) {
								model.setValueAt("A", k, m);
								str6 = null;
							}
							if (str6.equalsIgnoreCase("EL")) {
								model.setValueAt("E", k, m);
								str6 = null;
							}
							if (str6.equalsIgnoreCase("CL")) {
								model.setValueAt("C", k, m);
								str6 = null;
							}
						}
						localPreparedStatement3.close();
					} catch (Exception localException4) {
						System.out.println("Thread Run [HREMP_LEAVES]: " + localException4);
					}
				}
			}
			b5.setEnabled(true);
			calculate();
		}
	}

	public void capacity() {
		int i = cb1.getSelectedIndex() + 1;
		if ((i == 4) || (i == 6) || (i == 9) || (i == 11)) {
		}

		if ((i == 1) || (i == 3) || (i == 5) || (i == 7) || (i == 8) || (i == 10) || (i == 12)) {
		}

		int k = DateCalculationUtil.getYear(datetf.getText());
		int m = k % 4;

		if (i == 2) {
			if (m == 0) {
			} else {
			}
		}

		int n = 0;

		for (int i1 = 0; i1 < tb.getColumnCount(); i1++) {
			String str = String.valueOf(model.getValueAt(1, i1));
			if ((str.equalsIgnoreCase("SUN")) || (str.equalsIgnoreCase("N")) || (str.equalsIgnoreCase("R"))
					|| (str.equalsIgnoreCase("O"))) {
				n += 1;
			}
		}

		DateCalculationUtil.getNumberOfDays(datetf.getText());
		DateCalculationUtil.countDay(DateCalculationUtil.getMonth(datetf.getText()), DateCalculationUtil.getYear(datetf.getText()), "SUN");

		int i3 = tb.getColumnCount() - n - 5;

		for (int i4 = 2; i4 < tb.getRowCount() - 1; i4++) {
			float f1 = Float.parseFloat(String.valueOf(model1.getValueAt(i4, 3)));
			float f2 = i3 * f1;
			model.setValueAt(String.valueOf(df.format(f2)), i4, tb.getColumnCount() - 5);
		}
	}

	public void calculate() {
		int i = 0;
		int j = 0;

		float f1 = 0.0F;
		float f2 = 0.0F;
		float f3 = 0.0F;
		float f4 = 0.0F;

		float f5 = 0.0F;
		float f6 = 0.0F;
		float f7 = 0.0F;
		float f8 = 0.0F;
		float f9 = 0.0F;
		float f10 = 0.0F;

		int k = 0;

		for (int m = 2; m < tb.getRowCount() - 1; m++) {
			float f11 = Float.parseFloat(String.valueOf(model1.getValueAt(m, 2)));
			float f12 = Float.parseFloat(String.valueOf(model1.getValueAt(m, 3)));
			float f13 = Float.parseFloat(String.valueOf(model.getValueAt(m, tb.getColumnCount() - 5)));

			for (int n = 0; n < tb.getColumnCount() - 5; n++) {
				String str1 = "0";
				str1 = String.valueOf(model.getValueAt(m, n));
				String str2 = String.valueOf(model.getValueAt(1, n));

				if ((str2.equalsIgnoreCase("MON")) || (str2.equalsIgnoreCase("TUE")) || (str2.equalsIgnoreCase("WED"))
						|| (str2.equalsIgnoreCase("THU")) || (str2.equalsIgnoreCase("FRI"))
						|| (str2.equalsIgnoreCase("SAT"))) {
					if (str1.length() > 4) {
						i += 1;
					}
					if ((str1.equalsIgnoreCase("X")) || (str1.equalsIgnoreCase("A")) || (str1.equalsIgnoreCase("C"))
							|| (str1.equalsIgnoreCase("E"))) {
						j += 1;
					}
				}
				str1 = "0";
			}

			System.out.println("No Value : " + j);

			f1 = f12 * i;
			f2 = f12 * j;

			f3 = f13 - (f1 + f2);
			f4 = f3 * f11;

			model.setValueAt(String.valueOf(df2.format(f1)), m, tb.getColumnCount() - 4);
			model.setValueAt(String.valueOf(df2.format(f2)), m, tb.getColumnCount() - 3);
			model.setValueAt(String.valueOf(df2.format(f3)), m, tb.getColumnCount() - 2);
			model.setValueAt(String.valueOf(df2.format(f4)), m, tb.getColumnCount() - 1);

			f5 += f12;
			f6 += f13;
			f7 += f1;
			f8 += f2;
			f9 += f3;
			f10 += f4;

			System.out.println("Yes Value : " + i);

			i = 0;
			j = 0;

			k = m;
		}

		model1.setValueAt(String.valueOf(df2.format(f5)), k + 1, 3);
		model.setValueAt(String.valueOf(df2.format(f6)), k + 1, tb.getColumnCount() - 5);
		model.setValueAt(String.valueOf(df2.format(f7)), k + 1, tb.getColumnCount() - 4);
		model.setValueAt(String.valueOf(df2.format(f8)), k + 1, tb.getColumnCount() - 3);
		model.setValueAt(String.valueOf(df2.format(f9)), k + 1, tb.getColumnCount() - 2);
		model.setValueAt(String.valueOf(df2.format(f10)), k + 1, tb.getColumnCount() - 1);
	}

	public void clear() {
		for (int i = 2; i < tb.getRowCount(); i++) {
			for (int j = 0; j < tb.getColumnCount(); j++) {
				if (j != 31) {

					model.setValueAt(" ", i, j);
				}
			}
		}
	}

	public class myPrint extends Thread implements Runnable {
		public myPrint() {
		}

		public void run() {
			try {
				MessageFormat localMessageFormat1;
				MessageFormat localMessageFormat2;
				if (tabbedpane.getSelectedIndex() == 0) {
					localMessageFormat1 = new MessageFormat("Manpower Status");
					localMessageFormat2 = new MessageFormat("- {0} -");
					tb.print(JTable.PrintMode.FIT_WIDTH, localMessageFormat1, localMessageFormat2);
				}
				if (tabbedpane.getSelectedIndex() == 1) {

					localMessageFormat1 = new MessageFormat("Employee Hrs. Status");
					localMessageFormat2 = new MessageFormat("- {0} -");
					EmpMonthlyHRS.tb.print(JTable.PrintMode.FIT_WIDTH, localMessageFormat1, localMessageFormat2);
				}

			} catch (PrinterException localPrinterException) {
				System.err.println("Error printing: " + localPrinterException.getMessage());
			}
		}
	}

	static JTextField sktf = new JTextField();
	int row;

	public ManpowerAvail() {
		emh = new EmpMonthlyHRS();

		ct = new CompanyTable();
		ew = new EmpWorkAlloted();

		head = new String[] { "" };
		data = new Object[][] { new Object[0] };

		head1 = new String[] { "EMP", "Emp Name", "F", "H" };
		data1 = new Object[][] { new Object[0] };

		head2 = new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16",
				"17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "H", "CAP.",
				"USED", "WASTED", "FREE", "E.FREE" };
		data2 = new Object[][] { new Object[0] };

		head3 = new String[] { "H", "CAP.", "USED", "WASTED", "FREE", "E.FREE" };
		data3 = new Object[][] { new Object[0] };

		ar = new ArrayList();
		df = new DecimalFormat("00");
		df2 = new DecimalFormat("0");

		internalFrame = new JInternalFrame("Manpower Status", true, true, true, true);

		datelabel = new JLabel("Today");
		l1 = new JLabel("Company ID");
		l2 = new JLabel("Branch");
		l3 = new JLabel("DESIGNATION");

		l4 = new JLabel("SELECT DATE FROM : ");
		l5 = new JLabel("TO : ");

		l6 = new JLabel("UNIT/POCKET");

		coidtf = new JTextField(3);
		conametf = new JTextField(16);
		brcodetf = new JTextField(3);
		datetf = new JTextField(9);
		datetf2 = new JTextField(9);

		cb1 = new JComboBox();
		cb2 = new JComboBox();
		cb3 = new JComboBox();
		cb4 = new JComboBox();
		cb5 = new JComboBox();

		model = new DefaultTableModel(data, head);
		tb = new JTable(model) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int paramAnonymousInt1, int paramAnonymousInt2) {
				int i = convertColumnIndexToModel(paramAnonymousInt2);
				return i < 0;

			}

		};
		model1 = new DefaultTableModel(data1, head1);
		tb1 = new JTable(model1) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int paramAnonymousInt1, int paramAnonymousInt2) {
				int i = convertColumnIndexToModel(paramAnonymousInt2);
				return i < 0;

			}

		};
		maincenterview = new JViewport();
		centerview = new JViewport();
		rowheaderview = new JViewport();
		columnheaderview = new JViewport();
		rightview = new JViewport();

		sp = new JScrollPane();
		sp2 = new JScrollPane();
		rowheadersp = new JScrollPane(tb1);
		mainsp = new JScrollPane(tb);

		progressBar = new JProgressBar(0, 100);
		subprogress = new JProgressBar(0, 100);
		proglabel = new JLabel("Emp Code : ");

		projectlabel = new JLabel("PROJECT LIST ");
		projecttf = new JComboBox();

		b1 = new JButton("GENERATE REPORT >>");
		b2 = new JButton("Stop");
		b3 = new JButton("Start");
		b4 = new JButton("Close");
		b5 = new JButton("Calculate");

		menubar = new JMenuBar();
		ma = new JMenu("File");
		mb = new JMenu("ReportUtilities");

		ma1 = new JMenuItem("New/Refresh Sheet");
		ma2 = new JMenuItem("Print Report");
		ma3 = new JMenuItem("Exit");

		mb1 = new JMenuItem("Change Company");
		mb2 = new JMenuItem("Find");
		mb3 = new JMenuItem("Highlight");
		mb4 = new JCheckBoxMenuItem("Show Report Sheet Scroller", false);
		mb5 = new JMenuItem("Column Summary");

		mainPanel = new JPanel();

		northpanel = new JPanel();
		centerpanel = new JPanel();
		bottompanel = new JPanel();

		northp1 = new JPanel();
		northp2 = new JPanel();

		progpanel1 = new JPanel();
		progpanel2 = new JPanel();
		southpanel = new JPanel();
		southpanel2 = new JPanel();
		southpanel3 = new JPanel();

		tabbedpane = new JTabbedPane();
		detailsPanel = new JPanel();
		yearlyDetailsPanel = new JPanel();

		tk = Toolkit.getDefaultToolkit();

		dat = new java.util.Date();
		formatter = new SimpleDateFormat("dd/MM/yyyy");

		try {
			dateString = formatter.format(dat);
			datetf.setText(dateString);
			datetf2.setText(dateString);
			datelabel.setText(dateString);
		} catch (IllegalArgumentException localIllegalArgumentException) {
			System.out.println(localIllegalArgumentException.getMessage());
		}

		fo = new Font("Tahoma", 1, 10);

		skt = new SolTableModel();
		skr = new SolCellModel();

		rend1 = new Renderer();
		rend2 = new Renderer2();
		rend3 = new Renderer3();

		bor1 = BorderFactory.createLineBorder(Color.blue);

		//tmf = new TechMainFrame();
		//coidstr = TechMainFrame.textFieldCompanyId.getText();
		//brcodestr = TechMainFrame.textFieldBranchCode.getText();
		
		coidstr = SessionUtil.getLoginInfo().getCompanyId();
		brcodestr = SessionUtil.getLoginInfo().getBranchCode();

		datelist = new ArrayList();
		totaldays = 0;

		row = 0;
	}

	public void getCode(JTextField paramJTextField) {
		row = tb.getSelectedRow();
		sktf = paramJTextField;
		if ((row != -1) ||

		(row >= 0)) {
			codestr = String.valueOf(model.getValueAt(row, 0));
			sktf.setText(codestr);
			paramJTextField = sktf;
		}
	}

	public void actionPerformed(ActionEvent paramActionEvent) {
		Object localObject2;

		if (paramActionEvent.getSource() == projecttf) {
			projectDate();
			empList2();
			Object[] arrayOfObject = { "Yes", "No" };
			int j = JOptionPane.showOptionDialog(internalFrame, "Do You Really Want to Proceed for Report?", "Confirm Report", 0, 3,
					null, arrayOfObject, arrayOfObject[1]);
			if (j == 0) {
				localObject2 = new report();
				((report) localObject2).start();
			}
			if (j != 1) {
			}
		}

		if ((paramActionEvent.getSource() != coidtf) ||

		(paramActionEvent.getSource() == cb1)) {
			int i = cb1.getSelectedIndex() + 1;
			String str = String.valueOf(df.format(i));
			localObject2 = "2007";
			System.out.println("Date : 01/" + str + "/" + (String) localObject2);
		}
		Object localObject1;
		if (paramActionEvent.getSource() == b1) {
			b5.setEnabled(false);
			unitWiseEmpList();

			localObject1 = new Object[] { "Yes", "No" };
			int k = JOptionPane.showOptionDialog(internalFrame, "Do You Really Want to Proceed for Report?", "Confirm Report", 0, 3,
					null, (Object[]) localObject1, localObject1);
			if (k == 0) {
				localObject2 = new report();
				((report) localObject2).start();
			}
			if (k != 1) {
			}
		}

		if (paramActionEvent.getSource() == b2) {
			localObject1 = new report();
			try {
				localObject1.wait();
			} catch (InterruptedException localInterruptedException) {
				System.out.println(localInterruptedException);
			}
		}
		if (paramActionEvent.getSource() == b3) {
			localObject1 = new report();
			localObject1.notify();
		}

		if (paramActionEvent.getSource() == b4) {
			internalFrame.setVisible(false);
		}

		if (paramActionEvent.getSource() == b5) {
			calculate();
		}

		if (paramActionEvent.getSource() == cb1) {
			clear();
			capacity();
			b5.setEnabled(false);
		}
		if (paramActionEvent.getSource() == cb4) {
			unitWiseEmpList();
			b5.setEnabled(false);
			clear();
			capacity();
		}
		if (paramActionEvent.getSource() == cb5) {
			unitWiseEmpList();
		}

		if (paramActionEvent.getSource() == mb1) {
			localObject1 = new JDialog();
			Container localContainer = ((JDialog) localObject1).getContentPane();
			localContainer.setLayout(new BorderLayout());
			localContainer.add(northp2);
			((JDialog) localObject1).pack();
			((JDialog) localObject1).setLocationRelativeTo(null);
			((JDialog) localObject1).setVisible(true);
		}

		if (paramActionEvent.getSource() == ma2) {
			localObject1 = new myPrint();
			((myPrint) localObject1).start();
		}
		if (paramActionEvent.getSource() == ma3) {
			internalFrame.setVisible(false);
		}

		if (paramActionEvent.getSource() == mb4) {
			if (mb4.getState() == new Boolean("true").booleanValue()) {
				mainsp.setHorizontalScrollBarPolicy(32);
				mainsp.setVerticalScrollBarPolicy(22);
			}
			if (mb4.getState() == new Boolean("false").booleanValue()) {
				mainsp.setHorizontalScrollBarPolicy(31);
				mainsp.setVerticalScrollBarPolicy(21);
			}
		}

		if (paramActionEvent.getSource() == b4) {
			internalFrame.setVisible(false);
		}
	}

	public void mousePressed(MouseEvent paramMouseEvent) {
		if (paramMouseEvent.getSource() == tb) {
			getCode(sktf);
			tb1.setRowSelectionInterval(tb.getSelectedRow(), tb.getSelectedRow());
		}
		if (paramMouseEvent.getSource() == tb1) {
			tb.setRowSelectionInterval(tb1.getSelectedRow(), tb1.getSelectedRow());
		}
	}

	public void mouseClicked(MouseEvent paramMouseEvent) {
		if (paramMouseEvent.getSource() == datetf) {
			if (SwingUtilities.isRightMouseButton(paramMouseEvent)) {
			    SolCalendar skl = new SolCalendar();
				skl.showCalendar();
				skl.getDate(datetf);
			}
		}
		if (paramMouseEvent.getSource() == datetf2) {
			if (SwingUtilities.isRightMouseButton(paramMouseEvent)) {
			    SolCalendar skl = new SolCalendar();
				skl.showCalendar();
				skl.getDate(datetf2);
			}
		}
	}

	public void keyPressed(KeyEvent paramKeyEvent) {
		int i = paramKeyEvent.getKeyCode();
		if (paramKeyEvent.getSource() == coidtf) {
			if (i == 112) {
				CompanyTable localCompanyTable = new CompanyTable();
				localCompanyTable.showFrame();
				localCompanyTable.getCompanyId(coidtf);
				localCompanyTable.getBranchCode(brcodetf);
				localCompanyTable.getCompanyName(conametf);
			}
		}

		if (paramKeyEvent.getSource() == CompanyTable.coidtf) {
			if (i == 9) {
				desigList();
			}
		}

		if (paramKeyEvent.getSource() == tb) {

			if (i == 123) {
				ew.px("false");
				int j = tb.getSelectedRow();
				String str1 = String.valueOf(model.getValueAt(j, 0));

				ew.getEmpComboBox().removeAllItems();
				ew.getEmpComboBox().addItem(str1);
				ew.getEmpComboBox().setEnabled(true);
				ew.getEmpComboBox().setEditable(false);

				String.valueOf(df.format(DateCalculationUtil.getDate(datetf.getText())));
				String str3 = String.valueOf(df.format(DateCalculationUtil.getMonth(datetf.getText())));
				String str4 = String.valueOf(df.format(DateCalculationUtil.getMonth(datetf.getText()) + 1));
				String str5 = String.valueOf(DateCalculationUtil.getYear(datetf.getText()));

				ew.getFromDateField().setText("01/" + str3 + "/" + str5);
				ew.getToDateField().setText("01/" + str4 + "/" + str5);
			}
		}
	}

	public void keyReleased(KeyEvent paramKeyEvent) {
		if (paramKeyEvent.getSource() == tb) {
			getCode(sktf);
			tb1.setRowSelectionInterval(tb.getSelectedRow(), tb.getSelectedRow());
		}
		if (paramKeyEvent.getSource() == tb1) {
			tb.setRowSelectionInterval(tb1.getSelectedRow(), tb1.getSelectedRow());
		}
	}

	public void itemStateChanged(ItemEvent paramItemEvent) {
	}

	public void mouseReleased(MouseEvent paramMouseEvent) {
	}

	public void mouseExited(MouseEvent paramMouseEvent) {
	}

	public void mouseEntered(MouseEvent paramMouseEvent) {
	}

	public void keyTyped(KeyEvent paramKeyEvent) {
	}
}
