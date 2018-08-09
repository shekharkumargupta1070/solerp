package com.sol.erp;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.print.PrinterException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import com.sol.erp.ui.custom.SolCalendar;
import com.sol.erp.ui.custom.SolCellModel;
import com.sol.erp.ui.custom.SolProgressMonitor;
import com.sol.erp.ui.custom.SolTableModel;
import com.sol.erp.util.DBConnectionUtil;
import com.sol.erp.util.DateCalculationUtil;
import com.sol.erp.util.SessionUtil;
import com.sol.erp.util.formater.SolDateFormatter;

public class AttendanceReport
		implements ActionListener, ItemListener, KeyListener, java.awt.event.MouseListener, FocusListener {
    
	Connection con;
	PreparedStatement prsm;
	ResultSet rs;
	Calendar c;

	CompanyTable ct;
	Attendance atd;
	MonthlyLeaveDetails mld;
	SalarySheet sst;
	String coidstr;
	String brcodestr;
	Calendar gcal;
	String[] head;
	Object[][] data;
	List ar;
	DecimalFormat df;
	DecimalFormat df1;
	DecimalFormat df2;
	JFrame f;
	JTabbedPane tabbedpane;
	JPanel dailyPanel;
	JPanel attendancePanel;
	JPanel manpowerPanel;
	JPanel salaryPanel;
	JLabel l1;
	JLabel l2;
	JLabel l3;
	JLabel l4;
	JLabel l5;
	JLabel l6;
	JTextField datetf;
	JComboBox cb1;
	JComboBox cb2;
	JComboBox cb3;
	JTextField tf5;
	JComboBox cb4;
	DefaultListModel listmodel;
	JList list;
	JScrollPane listsp;
	DefaultTableModel model;
	JTable tb;
	JScrollPane sp;
	JSplitPane split;
	JProgressBar progressBar;
	JProgressBar subprogress;
	JLabel proglabel;
	JButton b1;
	JButton b2;
	JButton b3;
	JButton b4;
	JButton b5;
	JButton b6;
	JButton b7;
	JMenuBar menubar;
	JMenu ma;
	JMenuItem ma1;
	JMenuItem ma2;
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
	Toolkit tk;
	java.util.Date dat;
	SimpleDateFormat formatter;
	SimpleDateFormat formatter1;
	String dateString;
	String time;
	Font fo;
	SolCellModel skr;
	Renderer rend1;
	String standardhrs;
	Calendar cal;
	float totaltime;
	int workedsundays;
	int workedsats;
	int absent;
	int present;
	int lastDay;

	class Renderer extends DefaultTableCellRenderer {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		Renderer() {
		}

		public java.awt.Component getTableCellRendererComponent(JTable paramJTable, Object paramObject,
				boolean paramBoolean1, boolean paramBoolean2, int paramInt1, int paramInt2) {
			setEnabled((paramJTable == null) || (paramJTable.isEnabled()));

			if (paramInt1 % 2 == 0) {
				setBackground(new Color(240, 240, 240));
				setForeground(Color.darkGray);
			} else {
				setBackground(new Color(230, 230, 230));
				setForeground(Color.darkGray);
			}

			if (paramInt1 <= 1) {
				if (paramInt2 == 0) {
					setHorizontalAlignment(0);
				}
				if (paramInt2 == 1) {
					setHorizontalAlignment(2);
				}
				if (paramInt2 > 1) {
					setHorizontalAlignment(0);
				}
				setBackground(new Color(230, 230, 230));
				setForeground(Color.darkGray);
			}
			if (paramInt1 == 2) {

				if (paramInt2 == 0) {
					setHorizontalAlignment(0);
				}
				if (paramInt2 == 1) {
					setHorizontalAlignment(2);
				}
				if (paramInt2 > 1) {
					setHorizontalAlignment(0);
				}
				setBackground(Color.darkGray);
				setForeground(new Color(230, 230, 230));
			}

			if (paramInt1 > 2) {
				if (paramInt2 == 0) {
					setHorizontalAlignment(0);
				}
				if (paramInt2 == 1) {
					setHorizontalAlignment(2);
				}

				if ((paramInt2 > 1) && (paramInt2 < 33)) {
					setBackground(Color.white);
					setHorizontalAlignment(0);
					setForeground(Color.darkGray);

					if (String.valueOf(model.getValueAt(paramInt1, paramInt2)).equalsIgnoreCase("s")) {
						setBackground(new Color(220, 220, 220));
						setForeground(Color.darkGray);
					}
					if (String.valueOf(model.getValueAt(paramInt1, paramInt2)).equalsIgnoreCase("$")) {
						setBackground(new Color(245, 255, 240));
						setForeground(new Color(0, 190, 0));
					}
					if (String.valueOf(model.getValueAt(paramInt1, paramInt2)).equalsIgnoreCase("$$")) {
						setBackground(new Color(230, 230, 250));
						setForeground(Color.blue);
					}
					if (String.valueOf(model.getValueAt(paramInt1, paramInt2)).equalsIgnoreCase("L")) {
						setBackground(new Color(190, 110, 190));
						setForeground(Color.white);
					}
					if (String.valueOf(model.getValueAt(paramInt1, paramInt2)).equalsIgnoreCase("A")) {
						setForeground(Color.red);
						setBackground(Color.white);
					}
					if (String.valueOf(model.getValueAt(paramInt1, paramInt2)).equalsIgnoreCase("X")) {
						setForeground(Color.red);
						setBackground(Color.white);
					}
					if (String.valueOf(model.getValueAt(paramInt1, paramInt2)).equalsIgnoreCase("Y")) {
						setForeground(new Color(0, 140, 0));
						setBackground(Color.white);
					}
					if (String.valueOf(model.getValueAt(paramInt1, paramInt2)).equalsIgnoreCase("C")) {
						setBackground(new Color(250, 240, 240));
						setForeground(Color.red);
					}
					if (String.valueOf(model.getValueAt(paramInt1, paramInt2)).equalsIgnoreCase("E")) {
						setBackground(new Color(240, 240, 250));
						setForeground(Color.blue);
					}
					if (String.valueOf(model.getValueAt(paramInt1, paramInt2)).equalsIgnoreCase("?")) {
						setBackground(new Color(240, 240, 250));
						setForeground(Color.red);
					}
					if (String.valueOf(model.getValueAt(paramInt1, paramInt2)).equalsIgnoreCase("N")) {
						setBackground(new Color(240, 240, 250));
						setForeground(Color.red);
					}
					if (String.valueOf(model.getValueAt(paramInt1, paramInt2)).equalsIgnoreCase("R")) {
						setBackground(new Color(240, 240, 250));
						setForeground(Color.red);
					}
				}

				if (paramInt2 == 33) {
					setHorizontalAlignment(4);
					String str = String.valueOf(model.getValueAt(paramInt1, 33));
					float f = Float.parseFloat(str);

					if (f < 9.0F) {
						setBackground(new Color(190, 110, 190));
						setForeground(Color.white);
					}
					if (f >= 11.0F) {
						setBackground(new Color(110, 110, 180));
						setForeground(Color.white);
					}
				}
			}

			super.getTableCellRendererComponent(paramJTable, paramObject, paramBoolean1, paramBoolean2, paramInt1,
					paramInt2);

			return this;
		}

	}

	public void px() {
		Container localContainer = f.getContentPane();
		localContainer.setLayout(new BorderLayout());
		centerpanel.setLayout(new BorderLayout());
		northpanel.setLayout(new BorderLayout());
		northp1.setLayout(new FlowLayout(0));
		northp1.setLayout(new FlowLayout(2));
		bottompanel.setLayout(new BorderLayout());
		southpanel.setLayout(new BorderLayout());
		southpanel2.setLayout(new FlowLayout(0));
		southpanel3.setLayout(new FlowLayout(2));

		dailyPanel.setLayout(new BorderLayout());
		attendancePanel.setLayout(new BorderLayout());
		salaryPanel.setLayout(new BorderLayout());
		manpowerPanel.setLayout(new BorderLayout());

		tabbedpane.add("Monthly Leave Details", dailyPanel);
		tabbedpane.add("Monthly Attendance Report", attendancePanel);
		tabbedpane.add("Monthly Spent Hrs.", manpowerPanel);

		manpowerPanel.add(new EmpMonthlyHRS().DesignFrame(), "Center");

		SolTableModel.decorateTable(tb);
		tb.setAutoResizeMode(0);

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

		cb2.addItem("Structures Online");
		cb2.addItem("Atecture");
		cb2.addItem("Roark");

		cb3.addItem("ND01");

		cb2.setEnabled(false);
		cb3.setEnabled(false);

		sp.setViewportView(tb);

		tb.getColumnModel().getColumn(0).setPreferredWidth(40);
		tb.getColumnModel().getColumn(1).setPreferredWidth(140);
		tb.getColumnModel().getColumn(2).setPreferredWidth(100);
		tb.getColumnModel().getColumn(3).setPreferredWidth(120);
		tb.setBackground(new Color(220, 220, 220));

		for (int i = 0; i < tb.getColumnCount(); i++) {
			tb.getColumnModel().getColumn(i).setCellRenderer(rend1);
		}

		for (int i = 0; i < tb.getColumnCount() - 3; i++) {
			tb.getColumnModel().getColumn(i + 2).setPreferredWidth(20);
		}

		tb.getColumnModel().getColumn(33).setPreferredWidth(25);
		tb.getColumnModel().getColumn(34).setPreferredWidth(30);
		tb.getColumnModel().getColumn(35).setPreferredWidth(30);
		tb.getColumnModel().getColumn(36).setPreferredWidth(30);
		tb.getColumnModel().getColumn(37).setPreferredWidth(40);
		tb.getColumnModel().getColumn(38).setPreferredWidth(50);
		tb.getColumnModel().getColumn(39).setPreferredWidth(50);
		tb.getColumnModel().getColumn(40).setPreferredWidth(22);
		tb.getColumnModel().getColumn(41).setPreferredWidth(22);
		tb.getColumnModel().getColumn(42).setPreferredWidth(22);

		tb.removeColumn(tb.getColumnModel().getColumn(34));
		tb.removeColumn(tb.getColumnModel().getColumn(34));
		tb.removeColumn(tb.getColumnModel().getColumn(35));

		tf5.setFont(fo);
		tf5.setPreferredSize(new Dimension(150, 20));

		tb.setIntercellSpacing(new Dimension(1, 1));
		tb.setFont(fo);
		datetf.setFont(fo);

		northp1.add(datetf);
		northp1.add(b1);
		northp1.add(b7);

		northp2.add(ct.showContextFrame());

		northpanel.add(northp1, "East");
		northpanel.add(northp2, "West");

		split.setOneTouchExpandable(true);
		centerpanel.add(sp, "Center");

		progpanel1.add(proglabel);
		progpanel1.add(subprogress);
		subprogress.setPreferredSize(new Dimension(450, 13));
		progpanel2.add(progressBar);
		progressBar.setPreferredSize(new Dimension(250, 13));

		progressBar.setForeground(Color.red);
		subprogress.setForeground(Color.blue);

		progressBar.setFont(new Font("Verdana", 0, 9));
		subprogress.setFont(new Font("Verdana", 0, 9));

		southpanel.add(progpanel1, "West");
		southpanel.add(progpanel2, "East");

		southpanel2.add(l5);
		southpanel2.add(tf5);
		southpanel2.add(b6);
		southpanel2.add(b5);
		b5.setEnabled(false);

		bottompanel.add(southpanel2, "North");
		bottompanel.add(southpanel, "Center");

		ma.addSeparator();
		ma.add(ma2);
		menubar.add(ma);

		attendancePanel.add(northpanel, "North");
		attendancePanel.add(centerpanel, "Center");
		attendancePanel.add(bottompanel, "South");

		dailyPanel.add(mld.DesignFrame(), "Center");

		localContainer.add(tabbedpane, "Center");

		split.setDividerLocation(100);

		tb.addKeyListener(this);

		datetf.addMouseListener(this);
		datetf.addActionListener(this);
		datetf.addFocusListener(this);
		cb1.addActionListener(this);
		cb4.addItemListener(this);
		b1.addActionListener(this);
		b2.addActionListener(this);
		b3.addActionListener(this);
		b4.addActionListener(this);
		b5.addActionListener(this);
		b6.addActionListener(this);
		b7.addActionListener(this);
		tf5.addActionListener(this);
		tf5.addKeyListener(this);

		ma1.addActionListener(this);
		ma2.addActionListener(this);

		ct.coidtf1.addActionListener(this);
		ct.coidtf1.addFocusListener(this);
		ct.coidtf1.addKeyListener(this);

		f.setJMenuBar(menubar);

		f.setSize(tk.getScreenSize());
		f.setVisible(true);
		cb4.removeAllItems();
		deptList();
		deptList();
		empList();
	}

	public void empList() {
		model.setNumRows(0);

		model.addRow(new Object[] { "", "DAYS", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "",
				"", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "" });
		model.addRow(new Object[] { "", "OFFICIAL INFO", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "",
				"", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "" });
		model.addRow(new Object[] { "EMP", "EMP NAME", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12",
				"13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29",
				"30", "31", "H", "Cap", "P", "A", "TRG", "OT", "TOTAL", "SA", "SU", "P" });

		ct.getMyCompanyId();
		ct.getMyBranchCode();

		String str3 = "%" + tf5.getText() + "%";
		cb4.getSelectedItem();

		java.sql.Date localDate = SolDateFormatter.DDMMYYtoSQL(datetf.getText());

		try {
			con = DBConnectionUtil.getConnection();
			prsm = con.prepareStatement("Select Emp_code, EMP_NAME, AVERAGE_HRS from EMP_STATUS WHERE emp_code like '"
					+ str3 + "' AND emp_code not in(select emp_code from HR_EX_EMP where releave_date < ?) ");
			prsm.setDate(1, localDate);

			rs = prsm.executeQuery();

			while (rs.next()) {
				String str5 = new String(rs.getString(1));
				String str6 = new String(rs.getString(2));
				String str7 = new String(rs.getString(3));

				model.addRow(new Object[] { str5, str6.toUpperCase(), "", "", "", "", "", "", "", "", "", "", "", "",
						"", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", str7, "", "", "",
						"0", "", "0" });
			}
		} catch (Exception localException) {
			System.out.println(localException);
		}
		model.addRow(new Object[] { "", "TOTAL : " + String.valueOf(tb.getRowCount() - 1), "", "", "", "", "", "", "",
				"", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "0", "",
				"", "", "0", "", "0" });

		officialLeave();
	}

	public void deptList() {
		try {
			con = DBConnectionUtil.getConnection();
			prsm = con.prepareStatement("Select DEPTS from HRCOMPANY_DEPTS WHERE COMPANY_ID='" + coidstr
					+ "' and br_code='" + brcodestr + "' and ARGS='true' ");
			rs = prsm.executeQuery();

			while (rs.next()) {
				String str = new String(rs.getString(1));
				cb4.addItem(str);
			}
		} catch (Exception localException) {
			System.out.println(localException);
		}
	}

	public void standardWorkTime() {
		try {
			con = DBConnectionUtil.getConnection();
			prsm = con.prepareStatement("Select totaltime from HRshift where company_id='" + coidstr
					+ "' and branch_code='" + brcodestr + "' ");
			rs = prsm.executeQuery();

			while (rs.next()) {
				String str = new String(rs.getString(1));
				standardhrs = df.format(Float.parseFloat(str));
			}
		} catch (Exception localException) {
			System.out.println("Standard Work Time: " + localException);
		}
	}

	public int SOLOT(int paramInt1, int paramInt2) {
		return paramInt2 - paramInt1;
	}

	public class report extends Thread {
		public report() {
		}

		public void run() {
			lastDayOfMonth();

			int i = DateCalculationUtil.getDate(datetf.getText());
			int j = DateCalculationUtil.getMonth(datetf.getText());
			int k = DateCalculationUtil.getYear(datetf.getText());

			tb.getColumnCount();
			int n = tb.getRowCount() - 1;
			DateCalculationUtil.getNumberOfDays(datetf.getText());

			for (int i3 = 3; i3 < n; i3++) {
				OTHRSList(i3);
				String str1 = String.valueOf(model.getValueAt(i3, 0));

				proglabel.setText("Emp Code : " + str1);
				progressBar.setMinimum(0);
				progressBar.setMaximum(n - 1);
				progressBar.setValue(i3);
				progressBar.setStringPainted(true);

				int i5 = 0;

				for (int i4 = 2; i4 < i + 2; i4++) {
					try {
						Thread.sleep(0L);
					} catch (InterruptedException localInterruptedException) {
						System.out.println(localInterruptedException);
					}
					i5 += 1;
					String str2 = String.valueOf(df.format(i5));
					String str3 = String.valueOf(model.getValueAt(0, i4));
					String str4 = String.valueOf(model.getValueAt(1, i4));

					model.setValueAt("X", i3, i4);
					subprogress.setMinimum(2);
					subprogress.setMaximum(30);
					subprogress.setValue(i4);
					subprogress.setStringPainted(true);

					@SuppressWarnings("deprecation")
					java.sql.Date localDate = new java.sql.Date(k - 1900, j - 1, Integer.parseInt(str2));

					try {
						con = DBConnectionUtil.getConnection();
						prsm = con.prepareStatement("Select punchcard from HRTIMEMASTER where emp_code='" + str1
								+ "' and date =? and TOTAL_TIME NOT LIKE '0' ");
						prsm.setDate(1, localDate);
						rs = prsm.executeQuery();

						if (str4.equalsIgnoreCase("-")) {
							model.setValueAt("A", i3, i4);
						}
						if (str4.equalsIgnoreCase("O")) {
							model.setValueAt("O", i3, i4);
						}
						if (str4.equalsIgnoreCase("W")) {
							model.setValueAt("A", i3, i4);
						}
						if (str4.equalsIgnoreCase("N")) {
							model.setValueAt("N", i3, i4);
						}
						if (str4.equalsIgnoreCase("R")) {
							model.setValueAt("R", i3, i4);
						}
						if (str4.equalsIgnoreCase("?")) {
							model.setValueAt("?", i3, i4);
						}

						while (rs.next()) {
							new String(rs.getString(1));
							model.setValueAt("Y", i3, i4);

							if ((str3.equalsIgnoreCase("SU"))
									&& ((str4.equalsIgnoreCase("O")) || (str4.equalsIgnoreCase("N"))
											|| (str4.equalsIgnoreCase("R")) || (str4.equalsIgnoreCase("?")))) {
								model.setValueAt("$$", i3, i4);
							}
							if ((str3.equalsIgnoreCase("SA"))
									&& ((str4.equalsIgnoreCase("O")) || (str4.equalsIgnoreCase("N"))
											|| (str4.equalsIgnoreCase("R")) || (str4.equalsIgnoreCase("?")))) {
								model.setValueAt("$", i3, i4);
							}
						}
					} catch (Exception localException1) {
						System.out.println("Thread Run [Punchcard] : " + localException1);
					}

					try {
						con = DBConnectionUtil.getConnection();
						prsm = con.prepareStatement("Select category from HREMP_LEAVES where emp_code='" + str1
								+ "' and approve_tl='true' and approve_hr='true' and date= ? ");
						prsm.setDate(1, localDate);
						rs = prsm.executeQuery();

						String str6 = null;

						while (rs.next()) {
							str6 = new String(rs.getString(1));

							if (str6.equalsIgnoreCase("LWP")) {
								model.setValueAt("A", i3, i4);
								str6 = null;
							}
							if (str6.equalsIgnoreCase("EL")) {
								model.setValueAt("E", i3, i4);
								str6 = null;
							}
							if (str6.equalsIgnoreCase("CL")) {
								model.setValueAt("C", i3, i4);
								str6 = null;
							}
						}
					} catch (Exception localException2) {
						System.out.println("Thread Run [HREMP_LEAVES]: " + localException2);
					}

					if (String.valueOf(model.getValueAt(i3, i4)).equalsIgnoreCase("$$")) {
						workedsundays += 1;
						model.setValueAt(String.valueOf(workedsundays), i3, 41);
					}
					if (String.valueOf(model.getValueAt(i3, i4)).equalsIgnoreCase("$")) {
						workedsats += 1;
						model.setValueAt(String.valueOf(workedsats), i3, 40);
					}
					if ((String.valueOf(model.getValueAt(i3, i4)).equalsIgnoreCase("A"))
							|| (String.valueOf(model.getValueAt(i3, i4)).equalsIgnoreCase("X"))
							|| (String.valueOf(model.getValueAt(i3, i4)).equalsIgnoreCase(" "))
							|| (String.valueOf(model.getValueAt(i3, i4)).equalsIgnoreCase("L"))
							|| (String.valueOf(model.getValueAt(i3, i4)).equalsIgnoreCase("E"))
							|| (String.valueOf(model.getValueAt(i3, i4)).equalsIgnoreCase("C"))) {
						absent += 1;
						model.setValueAt(String.valueOf(absent), i3, 36);
					}
					if ((String.valueOf(model.getValueAt(i3, i4)).equalsIgnoreCase("Y"))
							|| (String.valueOf(model.getValueAt(i3, i4)).equalsIgnoreCase("$"))
							|| (String.valueOf(model.getValueAt(i3, i4)).equalsIgnoreCase("$$"))
							|| (String.valueOf(model.getValueAt(i3, i4)).equalsIgnoreCase("O"))
							|| (String.valueOf(model.getValueAt(i3, i4)).equalsIgnoreCase("R"))
							|| (String.valueOf(model.getValueAt(i3, i4)).equalsIgnoreCase("?"))) {
						present += 1;
						model.setValueAt(String.valueOf(present + 1), i3, 42);
					}
				}

				totaltime = 0.0F;
				workedsundays = 0;
				workedsats = 0;
				absent = 0;
				present = 0;
			}
			cb4.setEnabled(true);
			b1.setEnabled(true);
			b5.setEnabled(true);
			b6.setEnabled(true);
			b7.setEnabled(true);

		}
	}

	@SuppressWarnings("unchecked")
	public AttendanceReport() {
		

		ct = new CompanyTable();
		atd = new Attendance();
		mld = new MonthlyLeaveDetails();
		sst = new SalarySheet();

		coidstr = SessionUtil.getLoginInfo().getCompanyId();
		brcodestr = SessionUtil.getLoginInfo().getBranchCode();

		head = new String[] { "EMP", "Emp Name", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13",
				"14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30",
				"31", "H", "Cap", "P", "A", "TRG", "OT", "Total", "SA", "SU", "P" };

		data = new Object[][] { new Object[0] };

		ar = new ArrayList();
		df = new DecimalFormat("00");
		df1 = new DecimalFormat("00.00");
		df2 = new DecimalFormat("0");

		f = new JFrame("Monthly Attendance Report");
		tabbedpane = new JTabbedPane();

		dailyPanel = new JPanel();
		attendancePanel = new JPanel();
		manpowerPanel = new JPanel();
		salaryPanel = new JPanel();

		l1 = new JLabel("Year");
		l2 = new JLabel("Month");
		l3 = new JLabel("Company");
		l4 = new JLabel("Branch");
		l5 = new JLabel("Emp Code");
		l6 = new JLabel("Department");

		datetf = new JTextField(8);
		cb1 = new JComboBox();
		cb2 = new JComboBox();
		cb3 = new JComboBox();
		tf5 = new JTextField(25);
		cb4 = new JComboBox();

		listmodel = new DefaultListModel();
		list = new JList((ListModel) listmodel);
		listsp = new JScrollPane(list);

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
		sp = new JScrollPane();

		split = new JSplitPane(1, listsp, sp);

		progressBar = new JProgressBar(0, 100);
		subprogress = new JProgressBar(0, 100);
		proglabel = new JLabel("Emp Code : ");

		b1 = new JButton("Gen. Report");
		b2 = new JButton("Stop");
		b3 = new JButton("Start");
		b4 = new JButton("Close");
		b5 = new JButton("Calculate");
		b6 = new JButton("Save Figure");
		b7 = new JButton("Print Report");

		menubar = new JMenuBar();
		ma = new JMenu("File");
		ma1 = new JMenuItem("Print Report");
		ma2 = new JMenuItem("Exit");

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

		tk = Toolkit.getDefaultToolkit();

		dat = new java.util.Date();
		formatter = new SimpleDateFormat("yyyy");
		formatter1 = new SimpleDateFormat("dd/MM/yyyy");

		try {
			dateString = formatter.format(dat);
			datetf.setText(String.valueOf(formatter1.format(dat)));
		} catch (IllegalArgumentException localIllegalArgumentException) {
			System.out.println(localIllegalArgumentException.getMessage());
		}

		fo = new Font("Verdana", 1, 9);

		skr = new SolCellModel();

		rend1 = new Renderer();

		standardhrs = "0";

		totaltime = 0.0F;
		workedsundays = 0;
		workedsats = 0;
		absent = 0;
		present = 0;

		lastDay = 0;
	}

	public void lastDayOfMonth() {
		int i = cb1.getSelectedIndex() + 1;

		if ((i == 4) || (i == 6) || (i == 9) || (i == 11)) {
			lastDay = 30;
		}

		if ((i == 1) || (i == 3) || (i == 5) || (i == 7) || (i == 8) || (i == 10) || (i == 12)) {
			lastDay = 31;
		}

		int j = 2007;
		int k = j % 4;

		if (i == 2) {
			if (k == 0) {
				lastDay = 29;
			} else {
				lastDay = 28;
			}
		}
	}

	public void officialLeave() {
		clear();
		int i = DateCalculationUtil.getMonth(datetf.getText());
		int j = DateCalculationUtil.getYear(datetf.getText());
		for (int k = 0; k < 31; k++) {

			String str1 = String.valueOf(df.format(k + 1) + "/" + df.format(i) + "/" + df.format(j));
			String str2 = DateCalculationUtil.NameOfDay(str1);
			model.setValueAt(str2.substring(0, 2), 0, k + 2);

			try {
				con = DBConnectionUtil.getConnection();
				prsm = con.prepareStatement("Select " + str2 + " from HR_OFF_DAYS where COMPANY_ID='" + coidstr
						+ "' and BRANCH_CODE='" + brcodestr + "'  ");
				rs = prsm.executeQuery();
				while (rs.next()) {
					String str3 = new String(rs.getString(1));

					if (str3.equalsIgnoreCase(" ")) {
						model.setValueAt("Y", 1, k + 2);
					} else {
						model.setValueAt("-", 1, k + 2);
					}
				}
			} catch (Exception localException1) {
				System.out.println("Official Leave : " + localException1);
			}

			java.sql.Date localDate1 = SolDateFormatter.DDMMYYtoSQL(str1);

			try {
				con = DBConnectionUtil.getConnection();
				prsm = con.prepareStatement("Select WORK_ARGS from HR_OT_URGENT where date= ? and  COMPANY_ID='"
						+ coidstr + "' and BRANCH_CODE='" + brcodestr + "'  ");
				prsm.setDate(1, localDate1);
				rs = prsm.executeQuery();
				while (rs.next()) {
					String str4 = new String(rs.getString(1));
					if (str4.equalsIgnoreCase("true")) {
						model.setValueAt("W", 1, k + 2);
					}
					if (str4.equalsIgnoreCase("false")) {
						model.setValueAt("O", 1, k + 2);
					}
				}
			} catch (Exception localException2) {
				System.out.println("Official Leave : " + localException2);
			}

			java.sql.Date localDate2 = SolDateFormatter.DDMMYYtoSQL(str1);

			try {
				prsm = con.prepareStatement("Select HOLIDAY_TYPE from HR_HOLIDAY_LIST where date = ? and  COMPANY_ID='"
						+ coidstr + "' and BRANCH_CODE='" + brcodestr + "'  ");
				prsm.setDate(1, localDate2);
				rs = prsm.executeQuery();
				while (rs.next()) {
					String str5 = new String(rs.getString(1));
					if (str5.equalsIgnoreCase("NH")) {
						model.setValueAt("N", 1, k + 2);
					}
					if (str5.equalsIgnoreCase("RH")) {
						model.setValueAt("R", 1, k + 2);
					}
					if (str5.equalsIgnoreCase("OH")) {
						model.setValueAt("?", 1, k + 2);
					}
				}
			} catch (Exception localException3) {
				System.out.println("Official Leave : " + localException3);
			}
		}
		totalOfficialOFF();
	}

	public void totalOfficialOFF() {
		int i = DateCalculationUtil.getNumberOfDays(datetf.getText());
		int j = 0;
		for (int k = 2; k < i + 2; k++) {
			String str = String.valueOf(model.getValueAt(1, k));
			if ((str.equalsIgnoreCase("O")) || (str.equalsIgnoreCase("N")) || (str.equalsIgnoreCase("R"))
					|| (str.equalsIgnoreCase("?"))) {
				j += 1;
			}
		}

		model.setValueAt("OFFICIAL OFF : " + String.valueOf(j), 1, 1);
	}

	public void OTHRSList(int paramInt) {
		int i = DateCalculationUtil.getMonth(datetf.getText());
		int j = DateCalculationUtil.getYear(datetf.getText());

		java.sql.Date localDate1 = SolDateFormatter
				.DDMMYYtoSQL("01/" + String.valueOf(df.format(i)) + "/" + String.valueOf(j));
		int k = DateCalculationUtil.getNumberOfDays("01/" + String.valueOf(df.format(i)) + "/" + String.valueOf(j));
		java.sql.Date localDate2 = SolDateFormatter
				.DDMMYYtoSQL(String.valueOf(k) + "/" + String.valueOf(df.format(i)) + "/" + String.valueOf(j));

		int m = 0;
		int n = 0;
		int i1 = 0;
		int i2 = 0;
		int i3 = 0;

		String str = String.valueOf(model.getValueAt(paramInt, 0));
		try {
			prsm = con.prepareStatement(
					"Select OT_HRS, DEDUCT_OT, TOTAL_TIME, OT2, BREAK_HRS from HRTIMEMASTER where emp_code='" + str
							+ "' and Date between ? and ? and OT_REMARKS = ? and M_STATUS not Like ? and deduct_remarks= ? ");
			prsm.setDate(1, localDate1);
			prsm.setDate(2, localDate2);
			prsm.setString(3, "true");
			prsm.setString(4, "PM");
			prsm.setString(5, "true");

			rs = prsm.executeQuery();
			while (rs.next()) {
				float f1 = Float.parseFloat(rs.getString(1));
				float f2 = Float.parseFloat(rs.getString(2));
				float f3 = Float.parseFloat(rs.getString(3));
				float f4 = Float.parseFloat(rs.getString(4));
				float f5 = Float.parseFloat(rs.getString(5));

				int i5 = SolDateFormatter.convertHrsToMinute(String.valueOf(f1));
				int i6 = SolDateFormatter.convertHrsToMinute(String.valueOf(f2));
				int i7 = SolDateFormatter.convertHrsToMinute(String.valueOf(f3));
				int i8 = SolDateFormatter.convertHrsToMinute(String.valueOf(f4));
				int i9 = SolDateFormatter.convertHrsToMinute(String.valueOf(f5));

				m += i5;
				n += i6;
				i1 += i7;
				i3 += i9;
				i2 += i8;
			}

			int i4 = m + i2 - i3 - n;

			if (i4 >= 0) {
				model.setValueAt(SolDateFormatter.convertMinuteToHRS(i4), paramInt, 38);
				model.setValueAt(SolDateFormatter.convertMinuteToHRS(i1), paramInt, 39);
			} else {
				i4 -= i4 * 2;
				model.setValueAt("-" + SolDateFormatter.convertMinuteToHRS(i4), paramInt, 38);
				model.setValueAt(SolDateFormatter.convertMinuteToHRS(i1), paramInt, 39);
			}
		} catch (Exception localException) {
			System.out.println(localException);
		}
		m = 0;
		n = 0;
		i1 = 0;
		i2 = 0;
		i3 = 0;
	}

	public void calculate() {
		int i = 0;
		float f1 = 0.0F;
		float f2 = 0.0F;

		float f3 = 0.0F;
		float f4 = 0.0F;
		float f5 = 0.0F;
		float f6 = 0.0F;
		int j = 0;

		for (int k = 3; k < tb.getRowCount() - 1; k++) {
			float f7 = Float.parseFloat(String.valueOf(model.getValueAt(k, 33)));
			float f8 = Float.parseFloat(String.valueOf(model.getValueAt(k, 34)));

			for (int m = 2; m < lastDay + 2; m++) {
				String str = String.valueOf(model.getValueAt(k, m));

				if (str.equalsIgnoreCase("Y")) {
					i += 1;
				}
			}
			f1 = f7 * i;
			f2 = f8 - f1;
			model.setValueAt(String.valueOf(df2.format(f1)), k, 35);
			model.setValueAt(String.valueOf(df2.format(f2)), k, 36);
			i = 0;

			f3 += f7;
			f4 += f8;
			f5 += f1;
			f6 += f2;
			j = k;
		}

		model.setValueAt(String.valueOf(df2.format(f3)), j + 1, 33);
		model.setValueAt(String.valueOf(df2.format(f4)), j + 1, 34);
		model.setValueAt(String.valueOf(df2.format(f5)), j + 1, 35);
		model.setValueAt(String.valueOf(df2.format(f6)), j + 1, 36);
	}

	public void clear() {
		int i = 0;

		for (int j = 3; j < tb.getRowCount() - 1; j++) {
			for (i = 2; i <= 32; i++) {
				if (i != 33) {

					model.setValueAt(" ", j, i);
				}
			}

			model.setValueAt("0", j, 35);
			model.setValueAt("0", j, 36);
			model.setValueAt("0", j, 37);
			model.setValueAt("0", j, 38);
			model.setValueAt("0", j, 39);
			model.setValueAt("0", j, 40);
			model.setValueAt("0", j, 41);
			model.setValueAt("0", j, 42);
		}
	}

	public void clearLine(int paramInt) {
		for (int i = 2; i <= tb.getColumnCount(); i++) {
			if (i != 33) {

				model.setValueAt(" ", paramInt, i);
			}
		}
	}

	public class myPrint extends Thread implements Runnable {
		public myPrint() {
		}

		public void run() {
			try {
				MessageFormat localMessageFormat1 = new MessageFormat("Page {0}");
				MessageFormat localMessageFormat2 = new MessageFormat("- {0} -");
				tb.print(JTable.PrintMode.FIT_WIDTH, localMessageFormat1, localMessageFormat2);
			} catch (PrinterException localPrinterException) {
				System.err.println("Error printing: " + localPrinterException.getMessage());
			}
		}
	}

	public void saveFigure() {
	    SolProgressMonitor prmonitor = new SolProgressMonitor();
		prmonitor.setMIN(0);
		prmonitor.setMAX(tb.getRowCount() - 1);
		prmonitor.setProgressMessage("Saving OT Figures...");
		prmonitor.setStatusMessage("Status: Processing...");
		prmonitor.getProgressBar().setForeground(new Color(0, 0, 150));
		prmonitor.runProgressMonitor();
		prmonitor.showMonitor();

		for (int i = 3; i < tb.getRowCount() - 1; i++) {
			String str1 = String.valueOf(model.getValueAt(i, 0));
			String str2 = String.valueOf(model.getValueAt(i, 33));
			String.valueOf(model.getValueAt(i, 34));
			String.valueOf(model.getValueAt(i, 35));
			String str5 = String.valueOf(model.getValueAt(i, 36));
			String.valueOf(model.getValueAt(i, 37));
			String str7 = String.valueOf(model.getValueAt(i, 38));
			String str8 = String.valueOf(model.getValueAt(i, 39));
			String str9 = String.valueOf(model.getValueAt(i, 40));
			String str10 = String.valueOf(model.getValueAt(i, 41));
			String str11 = String.valueOf(model.getValueAt(i, 42));

			int j = DateCalculationUtil.getMonth(datetf.getText());
			int k = DateCalculationUtil.getYear(datetf.getText());

			cb1.getSelectedIndex();

			try {
				prsm = con.prepareStatement(
						"DELETE FROM HR_EMP_HRS_DETAILS where EMP_CODE = ? and year = ? and Month = ? ");
				prsm.setString(1, str1);
				prsm.setInt(2, k);
				prsm.setInt(3, j);
				prsm.executeUpdate();
			} catch (Exception localException1) {
				System.out.println("Deleting Record from HR_EMP_HRS_DETAILS \t: " + localException1);
			}

			try {
				prsm = con.prepareStatement("insert into HR_EMP_HRS_DETAILS values ('" + coidstr + "','" + brcodestr
						+ "','" + str1 + "',?, ?, ?, ?, ?, ?, ?, ?, ?)");
				prsm.setFloat(1, Float.parseFloat(str2));
				prsm.setFloat(2, Float.parseFloat(str5));
				prsm.setFloat(3, Float.parseFloat(str7));
				prsm.setFloat(4, Float.parseFloat(str8));
				prsm.setFloat(5, Float.parseFloat(str9));
				prsm.setInt(6, Integer.parseInt(str10));
				prsm.setInt(7, j);
				prsm.setInt(8, k);
				prsm.setInt(9, Integer.parseInt(str11));
				prsm.executeUpdate();
			} catch (Exception localException2) {
				System.out.println("Saving Record for HR_EMP_HRS_DETAILS \t\t: " + localException2);
			}
			try {
				prsm = con.prepareStatement("Update HR_EMP_EXTRA set VALUE1 ='" + str7 + "' where EMP_CODE ='" + str1
						+ "' and HEADS = 'OT' ");
				prsm.executeUpdate();
			} catch (Exception localException3) {
				System.out.println("Updating Record for HR_EMP_HRS_DETAILS \t\t: " + localException3);
			}
			try {
				prsm = con.prepareStatement("Update HR_EMP_EXTRA set Month=?, Year=?  where EMP_CODE ='" + str1 + "' ");
				prsm.setInt(1, j);
				prsm.setInt(2, k);
				prsm.executeUpdate();
			} catch (Exception localException4) {
				System.out.println("Updating Record for HR_EMP_HRS_DETAILS \t\t: " + localException4);
			}
		}
	}

	public void itemStateChanged(ItemEvent paramItemEvent) {
		if (paramItemEvent.getSource() == cb4) {
			empList();
		}
	}

	public void actionPerformed(ActionEvent paramActionEvent) {
		if (paramActionEvent.getSource() == datetf) {
			officialLeave();
		}

		Object localObject;

		if (paramActionEvent.getSource() == b1) {
			cb4.setEnabled(false);
			b1.setEnabled(false);
			b5.setEnabled(false);
			b6.setEnabled(false);
			b7.setEnabled(false);

			localObject = new report();
			((report) localObject).start();
		}

		if (paramActionEvent.getSource() == b6) {
			saveFigure();
		}
		if (paramActionEvent.getSource() == b2) {
			localObject = new report();
			try {
				localObject.wait();
			} catch (InterruptedException localInterruptedException) {
				System.out.println(localInterruptedException);
			}
		}
		if (paramActionEvent.getSource() == b3) {
			localObject = new report();
			localObject.notify();
		}

		if (paramActionEvent.getSource() == b4) {
			f.setVisible(false);
		}

		if (paramActionEvent.getSource() == b5) {
			calculate();
		}

		if (paramActionEvent.getSource() == b7) {
			localObject = new myPrint();
			((myPrint) localObject).start();
		}

		if ((paramActionEvent.getSource() == tf5) || (paramActionEvent.getSource() == cb4)) {
			empList();
		}

		if (paramActionEvent.getSource() == cb1) {
			clear();
			b1.setEnabled(true);
			b5.setEnabled(true);
			b6.setEnabled(true);
			b7.setEnabled(true);
		}

		if (paramActionEvent.getSource() == ma2) {
			f.setVisible(false);
		}
		if (paramActionEvent.getSource() == b4) {
			f.setVisible(false);
		}
	}

	public void focusLost(FocusEvent paramFocusEvent) {
		if (paramFocusEvent.getSource() == datetf) {
			officialLeave();
		}
	}

	public void keyPressed(KeyEvent paramKeyEvent) {
		int i = paramKeyEvent.getKeyCode();
		if (paramKeyEvent.getSource() == ct.coidtf1) {
			if (i == 9) {
				empList();
			}
		}
	}

	public void keyReleased(KeyEvent paramKeyEvent) {
		if (paramKeyEvent.getSource() == tf5) {
			int i = paramKeyEvent.getKeyCode();

			if (i == 112) {
				HelpTable localHelpTable = new HelpTable();
				localHelpTable.showFrame();
				localHelpTable.getEmpCode(tf5);
			}
		}
	}

	public void mousePressed(MouseEvent paramMouseEvent) {
		if (paramMouseEvent.getSource() == datetf) {
			if (javax.swing.SwingUtilities.isRightMouseButton(paramMouseEvent)) {
			    SolCalendar cal1 = new SolCalendar();
				cal1.showCalendar();
				cal1.getDate(datetf);
			}
		}
	}

	public void focusGained(FocusEvent paramFocusEvent) {
	}

	public void keyTyped(KeyEvent paramKeyEvent) {
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
