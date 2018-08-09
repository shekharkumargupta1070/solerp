package com.sol.erp;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DecimalFormat;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.sol.erp.ui.custom.SolCalendar;
import com.sol.erp.ui.custom.SolTableModel;
import com.sol.erp.util.DBConnectionUtil;
import com.sol.erp.util.DateDifferencesUtil;

public class EmpPerform implements java.awt.event.ActionListener, java.awt.event.MouseListener {
	SolCalendar skl = new SolCalendar();

	Connection con;
	Statement stat;
	ResultSet rs;
	ResultSet rs1;
	float sheet = 0.0F;
	float alloted = 0.0F;
	float completed = 0.0F;
	float taken = 0.0F;
	float bfa = 0.0F;
	float rev = 0.0F;
	float wasted = 0.0F;
	float used = 0.0F;
	float eff = 0.0F;

	String[] columnNames = { "Project", "Alloted Drg", "CMPTLD Drg", "Estd Hrs", "Taken Hrs", "BFA Time", "Rev Time",
			"Wst Time", "Used Hrs", "Effeciency" };
	Object[][] data = new Object[0][];

	String[] columnNames1 = { "Emp Code", "Desig" };
	Object[][] data1 = new Object[0][];

	String[] prjcolumn1 = { "DTLed", "TL" };
	Object[][] prjdata1 = new Object[0][];

	String[] prjcolumn2 = { "CHKed", "TL" };
	Object[][] prjdata2 = new Object[0][];

	String empcodestr = "Shekhar";
	String itemcodestr = "AAA";
	String projectnostr = "001";

	DecimalFormat df = new DecimalFormat("00.00");
	DecimalFormat df1 = new DecimalFormat("0");
	DecimalFormat df2 = new DecimalFormat(".0");

	DefaultTableModel model = new DefaultTableModel(data, columnNames);
	JTable tb = new JTable(model);
	javax.swing.JScrollPane sp = new javax.swing.JScrollPane(tb);

	DefaultTableModel model1 = new DefaultTableModel(data1, columnNames1);
	JTable tb1 = new JTable(model1);
	javax.swing.JScrollPane sp1 = new javax.swing.JScrollPane(tb1);

	DefaultTableModel prjmodel1 = new DefaultTableModel(prjdata1, prjcolumn1);
	JTable prjtb1 = new JTable(prjmodel1);
	javax.swing.JScrollPane prjsp1 = new javax.swing.JScrollPane(prjtb1);

	DefaultTableModel prjmodel2 = new DefaultTableModel(prjdata2, prjcolumn2);
	JTable prjtb2 = new JTable(prjmodel2);
	javax.swing.JScrollPane prjsp2 = new javax.swing.JScrollPane(prjtb2);

	javax.swing.JSplitPane spl1 = new javax.swing.JSplitPane(1, sp1, sp);
	javax.swing.JSplitPane spl2 = new javax.swing.JSplitPane(1, prjsp1, prjsp2);

	javax.swing.JInternalFrame f = new javax.swing.JInternalFrame("EMPLOYEE Performance", true, true, true, true);
	javax.swing.JFrame f1 = new javax.swing.JFrame();

	javax.swing.JMenuBar mb = new javax.swing.JMenuBar();
	javax.swing.JMenu menu1 = new javax.swing.JMenu("Report");
	JMenuItem mi1 = new JMenuItem("Make HTML");
	JMenuItem mi2 = new JMenuItem("Make XLS");
	JMenuItem mi3 = new JMenuItem("Print");
	JMenuItem mi4 = new JMenuItem("Exit");

	JLabel fromlabel = new JLabel("From");
	JLabel tolabel = new JLabel("To");
	JLabel southl1 = new JLabel("Project Wise");
	JLabel southl2 = new JLabel("Company Wise");

	JTextField fromtf = new JTextField("01/08/2007", 8);
	JTextField totf = new JTextField("30/08/2007", 8);
	JTextField southtf1 = new JTextField(8);
	JComboBox southtf2 = new JComboBox();

	JLabel l1 = new JLabel("Project No");
	JTextField tf1 = new JTextField("07001", 10);

	javax.swing.JButton dtlbut = new javax.swing.JButton("DTL Report");
	javax.swing.JButton chkbut = new javax.swing.JButton("CHK Report");
	javax.swing.JButton closebut = new javax.swing.JButton("Close", new javax.swing.ImageIcon("image/close.gif"));

	JPanel centerpanel = new JPanel();
	JPanel northpanel = new JPanel();
	JPanel southpanel = new JPanel();

	JPanel southpanel2 = new JPanel();

	java.awt.Font fo = new java.awt.Font("Tahoma", 1, 11);
	javax.swing.border.Border line = javax.swing.BorderFactory.createLineBorder(Color.darkGray);
	javax.swing.border.Border bor1 = javax.swing.BorderFactory.createTitledBorder(line, "Date Range", 0, 0, fo,
			Color.darkGray);
	javax.swing.border.Border bor2 = javax.swing.BorderFactory.createTitledBorder(line, "Employee Performance Report",
			0, 0, fo, Color.darkGray);

	java.awt.Toolkit tk = java.awt.Toolkit.getDefaultToolkit();
	java.awt.Dimension ss = tk.getScreenSize();
	java.awt.Dimension fs = f.getSize();

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

			if (paramInt1 % 2 == 0) {
				setBackground(new Color(240, 240, 240));
				setForeground(Color.darkGray);

			} else {
				setBackground(new Color(230, 230, 230));
				setForeground(Color.darkGray);
			}

			if (paramInt2 >= 0) {
				setHorizontalAlignment(0);
			}

			String str = String.valueOf(model1.getValueAt(paramInt1, 1));
			setToolTipText(str);

			if (str.equalsIgnoreCase("Detailer")) {
				setBackground(new Color(240, 240, 200));
				setForeground(Color.darkGray);
			}

			if (str.equalsIgnoreCase("Checker")) {
				setBackground(new Color(200, 240, 230));
				setForeground(Color.darkGray);
			}
			if (str.equalsIgnoreCase("Team Leader")) {
				setBackground(Color.darkGray);
				setForeground(Color.white);
			}
			if (str.equalsIgnoreCase("Project Manager")) {
				setBackground(Color.red);
				setForeground(Color.white);
			}

			super.getTableCellRendererComponent(paramJTable, paramObject, paramBoolean1, paramBoolean2, paramInt1,
					paramInt2);

			return this;
		}
	}

	class ColoredTableCellRenderer1 extends javax.swing.table.DefaultTableCellRenderer {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		ColoredTableCellRenderer1() {
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

			if (paramInt2 > 0) {
				setHorizontalAlignment(4);
			}

			String str = String.valueOf(model.getValueAt(paramInt1, 9));
			float f = Float.parseFloat(str);

			if (paramInt2 == 9) {
				if (f <= 0.6D) {
					setBackground(Color.red);
					setForeground(Color.white);
				}
				if ((f > 0.6D) && (f < 0.8D)) {
					setBackground(Color.green);
					setForeground(Color.black);
				}
				if (f >= 0.8D) {
					setBackground(Color.blue);
					setForeground(Color.white);
				}
			}

			super.getTableCellRendererComponent(paramJTable, paramObject, paramBoolean1, paramBoolean2, paramInt1,
					paramInt2);

			return this;
		}
	}

	EmpPerform.ColoredTableCellRenderer skr = new EmpPerform.ColoredTableCellRenderer();
	EmpPerform.ColoredTableCellRenderer1 skr1 = new EmpPerform.ColoredTableCellRenderer1();


	DateDifferencesUtil skd = new DateDifferencesUtil();
	java.awt.Container c;
	java.awt.Container c1;

	public void px() {
		c = f.getContentPane();
		c.setLayout(new java.awt.BorderLayout());
		northpanel.setLayout(new java.awt.FlowLayout(0));
		southpanel.setLayout(new java.awt.FlowLayout(0));
		centerpanel.setLayout(new java.awt.BorderLayout());

		northpanel.add(fromlabel);
		northpanel.add(fromtf);
		northpanel.add(tolabel);
		northpanel.add(totf);

		southtf2.addItem("STRUCTURES Online");
		southtf2.addItem("Atecture");
		southtf2.addItem("Roark");

		southpanel.add(southl1);
		southpanel.add(southtf1);
		southpanel.add(southl2);
		southpanel.add(southtf2);

		totf.setFont(fo);
		fromtf.setFont(fo);
		southtf1.setFont(fo);

		tb1.getColumnModel().getColumn(0).setCellRenderer(skr);

		for (int i = 0; i < tb.getColumnCount(); i++) {
			tb.getColumnModel().getColumn(i).setCellRenderer(skr1);
		}

		SolTableModel.decorateTable(tb);
		SolTableModel.decorateTable(tb1);
		SolTableModel.decorateTable(prjtb1);
		SolTableModel.decorateTable(prjtb2);

		tb1.setShowGrid(false);
		tb.setShowGrid(false);

		tb1.setShowVerticalLines(false);
		tb.setShowVerticalLines(false);

		tb1.setIntercellSpacing(new java.awt.Dimension(0, 0));
		tb.setIntercellSpacing(new java.awt.Dimension(0, 0));

		northpanel.setBorder(bor1);

		mb.add(menu1);
		menu1.add(mi1);
		menu1.add(mi2);
		menu1.addSeparator();
		menu1.add(mi3);
		menu1.addSeparator();
		menu1.add(mi4);

		spl1.setOneTouchExpandable(true);
		spl1.setDividerLocation(100);

		c.add(northpanel, "North");
		c.add(spl1, "Center");
		c.add(southpanel, "South");

		dtlbut.addActionListener(this);
		chkbut.addActionListener(this);
		closebut.addActionListener(this);

		tf1.addActionListener(this);
		southtf1.addActionListener(this);

		fromtf.addActionListener(this);
		fromtf.addMouseListener(this);
		totf.addActionListener(this);
		totf.addMouseListener(this);

		mi1.addActionListener(this);
		mi2.addActionListener(this);
		mi3.addActionListener(this);
		mi4.addActionListener(this);
		tb1.addMouseListener(this);

		f.setJMenuBar(mb);
		f.setSize(865, 550);
		f.setVisible(true);

		c1 = f1.getContentPane();
		southpanel2.setLayout(new java.awt.FlowLayout());
		c1.setLayout(new java.awt.BorderLayout());
		southpanel2.setBackground(new Color(120, 240, 250));

		southpanel2.add(dtlbut);
		southpanel2.add(chkbut);

		c1.add(spl2, "Center");
		c1.add(southpanel2, "South");

		spl2.setDividerLocation(190);
		f1.setSize(400, 200);
	}

	public void empList() {
		tb1.removeColumn(tb1.getColumnModel().getColumn(1));
		model1.setNumRows(0);
		try {
			con = DBConnectionUtil.getConnection();
			stat = con.createStatement();
			rs = stat.executeQuery(
					"select EMP_CODE,DESIGNATION from emp_status where DESIGNATION ='Detailer' or DESIGNATION ='Checker' or DESIGNATION ='Team Leader' ORDER BY EMP_CODE ");
			rs.getMetaData().getColumnCount();
			while (rs.next()) {
				String str1 = new String(rs.getString(1));
				String str2 = new String(rs.getString(2));
				model1.addRow(new Object[] { str1, str2 });
			}
		} catch (Exception localException) {
			System.out.println(localException);
		}
	}

	public void detailedList() {
		String str1 = String.valueOf(model1.getValueAt(tb1.getSelectedRow(), 0));
		prjmodel1.setNumRows(0);
		try {
			stat = con.createStatement();
			rs = stat.executeQuery("select DISTINCT(PROJECT_NO), MAX(PROJECT_CO) from projectmanpower WHERE EMP_CODE='"
					+ str1 + "' AND DTL_CHK='Detailing' and from_date between '" + fromtf.getText() + "' and '"
					+ totf.getText() + "' GROUP BY PROJECT_NO ");
			rs.getMetaData().getColumnCount();

			while (rs.next()) {
				String str2 = new String(rs.getString(1));
				String str3 = new String(rs.getString(2));

				prjmodel1.addRow(new Object[] { str2, str3 });
			}
		} catch (Exception localException) {
			System.out.println(localException);
		}
	}

	public void checkedList() {
		String str1 = String.valueOf(model1.getValueAt(tb1.getSelectedRow(), 0));
		prjmodel2.setNumRows(0);

		try {
			stat = con.createStatement();
			rs = stat.executeQuery("select DISTINCT(PROJECT_NO), MAX(PROJECT_CO) from projectmanpower WHERE EMP_CODE='"
					+ str1 + "' AND DTL_CHK='Checking' and from_date between '" + fromtf.getText() + "' and '"
					+ totf.getText() + "' GROUP BY PROJECT_NO ");
			rs.getMetaData().getColumnCount();

			while (rs.next()) {
				String str2 = new String(rs.getString(1));
				String str3 = new String(rs.getString(2));

				prjmodel2.addRow(new Object[] { str2, str3 });
			}
		} catch (Exception localException) {
			System.out.println(localException);
		}
	}

	public void report1() {
		model.setNumRows(0);
		int[] arrayOfInt = prjtb1.getSelectedRows();

		String str1 = String.valueOf(model1.getValueAt(tb1.getSelectedRow(), 0));
		System.out.println(str1);

		for (int i = 0; i < arrayOfInt.length; i++) {
			String str2 = String.valueOf(prjmodel1.getValueAt(arrayOfInt[i], 0));
			System.out.println("Project No : " + str2);

			int j = 0;
			float f2 = 0.0F;
			String str4;
			try {
				stat = con.createStatement();
				rs = stat.executeQuery("SELECT distinct(drawing_no), e_time  from detailing where project_no='" + str2
						+ "' and emp_code='" + str1 + "' ");

				j = rs.getRow();

				while (rs.next()) {
					j += 1;

					new String(rs.getString(1));
					str4 = new String(rs.getString(2));
					float f3 = Float.parseFloat(str4);

					f2 += f3;
					f3 = 0.0F;
				}
			} catch (Exception localException1) {
				System.out.println("**************************");
				System.out.println("Message From Report1 (A)");
				System.out.println(localException1);
				System.out.println("**************************");
			}

			try {
				stat = con.createStatement();
				rs = stat.executeQuery(
						"SELECT COUNT(DRAWING_NO), (SELECT COUNT(DRG_NO) FROM TIMESHEET WHERE PROJECT_NO='" + str2
								+ "' and emp_code='" + str1
								+ "' and drgstatus NOT like '0' ),(select average_hrs from emp_status where emp_code='"
								+ str1 + "'),(select SUM(TOTAL_TIME) from HRTIMEMASTER where project_no='" + str2
								+ "' and emp_code='" + str1
								+ "' and D_C = 'D' GROUP BY PROJECT_NO)/100 from DETAILING where emp_code='" + str1
								+ "' and project_no='" + str2 + "' ");

				rs.getMetaData().getColumnCount();

				while (rs.next()) {
					str4 = new String(rs.getString(1));
					String str5 = new String(rs.getString(2));
					new String(rs.getString(3));
					String str7 = new String(rs.getString(4));

					sheet = j;

					completed = Float.parseFloat(str5);

					alloted = f2;

					taken = Float.parseFloat(str7);

					System.out.println("DB Row " + j);

					model.addRow(new Object[] { str2, String.valueOf(df1.format(sheet)),
							String.valueOf(df1.format(completed)), String.valueOf(df1.format(alloted)),
							String.valueOf(df1.format(taken)) });
				}
			} catch (Exception localException2) {
				System.out.println("**************************");
				System.out.println("Message From Report1 (B)");
				System.out.println(localException2);
				System.out.println("**************************");
			}
		}

		f1.setVisible(false);
	}

	public void report2() {
		model.setNumRows(0);
		int[] arrayOfInt = prjtb2.getSelectedRows();

		String str1 = String.valueOf(model1.getValueAt(tb1.getSelectedRow(), 0));
		System.out.println(str1);

		for (int i = 0; i < arrayOfInt.length; i++) {
			String str2 = String.valueOf(prjmodel2.getValueAt(arrayOfInt[i], 0));
			System.out.println("Project No : " + str2);

			float f2 = 0.0F;
			String str4;
			try {
				stat = con.createStatement();
				rs = stat.executeQuery("SELECT distinct(drawing_no),e_time  from Checking where project_no='" + str2
						+ "' and emp_code='" + str1 + "' ");

				rs.getRow();

				while (rs.next()) {
					new String(rs.getString(1));
					str4 = new String(rs.getString(2));
					float f3 = Float.parseFloat(str4);

					f2 += f3;
					f3 = 0.0F;
				}
			} catch (Exception localException1) {
				System.out.println("**************************");
				System.out.println("Message From Report2 (A)");
				System.out.println(localException1);
				System.out.println("**************************");
			}

			try {
				stat = con.createStatement();
				rs = stat.executeQuery(
						"SELECT COUNT(DRAWING_NO), (SELECT COUNT(DRG_NO) FROM TIMESHEET WHERE PROJECT_NO='" + str2
								+ "' and emp_code='" + str1
								+ "' and drgstatus1 NOT like '0' ),(select average_hrs from emp_status where emp_code='"
								+ str1 + "'),(select SUM(TOTAL_TIME) from HRTIMEMASTER where project_no='" + str2
								+ "' and emp_code='" + str1
								+ "' and D_C = 'C' GROUP BY PROJECT_NO)/100 from CHECKING where emp_code='" + str1
								+ "' and project_no='" + str2 + "' ");
				rs.getMetaData().getColumnCount();

				while (rs.next()) {
					str4 = new String(rs.getString(1));
					String str5 = new String(rs.getString(2));
					new String(rs.getString(3));
					String str7 = new String(rs.getString(4));

					sheet = Float.parseFloat(str4);

					completed = Float.parseFloat(str5);

					alloted = f2;

					taken = Float.parseFloat(str7);

					model.addRow(new Object[] { str2, String.valueOf(df1.format(sheet)),
							String.valueOf(df1.format(completed)), String.valueOf(df1.format(alloted)),
							String.valueOf(df1.format(taken)) });
				}
			} catch (Exception localException2) {
				System.out.println("**************************");
				System.out.println("Message From Report2 (B)");
				System.out.println(localException2);
				System.out.println("**************************");
			}
		}

		f1.setVisible(false);
	}

	public void BFA() {
		int i = tb.getRowCount();

		String str1 = String.valueOf(model1.getValueAt(tb1.getSelectedRow(), 0));

		for (int j = 0; j < i; j++) {
			String str2 = String.valueOf(model.getValueAt(j, 0));
			model.setValueAt("0", j, 5);
			try {
				stat = con.createStatement();
				rs = stat.executeQuery("select sum(total_time)/100 from BFA where emp_code='" + str1
						+ "' and project_no='" + str2 + "' and status='Approved' ");
				while (rs.next()) {
					String str3 = new String(rs.getString(1));
					bfa = Float.parseFloat(str3);

					model.setValueAt(df1.format(bfa), j, 5);
				}
			} catch (Exception localException) {
				System.out.println(localException);
			}
		}
	}

	public void REV() {
		int i = tb.getRowCount();

		String str1 = String.valueOf(model1.getValueAt(tb1.getSelectedRow(), 0));

		for (int j = 0; j < i; j++) {
			String str2 = String.valueOf(model.getValueAt(j, 0));
			model.setValueAt("0", j, 6);
			try {
				stat = con.createStatement();
				rs = stat.executeQuery("select sum(total_time)/100 from REVISION_TIME where emp_code='" + str1
						+ "' and project_no='" + str2 + "' and REMARKS ='Approved' ");
				while (rs.next()) {
					String str3 = new String(rs.getString(1));
					rev = Float.parseFloat(str3);

					model.setValueAt(df1.format(rev), j, 6);
				}
			} catch (Exception localException) {
				System.out.println(localException);
			}
		}
	}

	public void WASTED() {
		int i = tb.getRowCount();

		String str1 = String.valueOf(model1.getValueAt(tb1.getSelectedRow(), 0));

		for (int j = 0; j < i; j++) {
			String str2 = String.valueOf(model.getValueAt(j, 0));
			model.setValueAt("0", j, 7);

			try {
				stat = con.createStatement();
				rs = stat.executeQuery("select sum(total_time) from WASTED where emp_code='" + str1
						+ "' and project_no='" + str2 + "' and REMARKS ='Approved' ");
				while (rs.next()) {
					String str3 = new String(rs.getString(1));
					wasted = Float.parseFloat(str3);

					model.setValueAt(df1.format(wasted), j, 7);
				}
			} catch (Exception localException) {
				System.out.println(localException);
			}
		}
	}

	public void USED() {
		int i = tb.getRowCount();

		for (int j = 0; j < i; j++) {
			sheet = Float.parseFloat(String.valueOf(model.getValueAt(j, 1)));
			completed = Float.parseFloat(String.valueOf(model.getValueAt(j, 2)));
			alloted = Float.parseFloat(String.valueOf(model.getValueAt(j, 3)));
			taken = Float.parseFloat(String.valueOf(model.getValueAt(j, 4)));
			bfa = Float.parseFloat(String.valueOf(model.getValueAt(j, 5)));
			rev = Float.parseFloat(String.valueOf(model.getValueAt(j, 6)));
			wasted = Float.parseFloat(String.valueOf(model.getValueAt(j, 7)));

			used = (taken - (rev + wasted));

			float f2 = alloted / sheet;
			float f3 = used / completed;

			eff = (f2 / f3);

			model.setValueAt(String.valueOf(df1.format(used)), j, 8);

			model.setValueAt(String.valueOf(df2.format(eff)), j, 9);
		}

		sheet = 0.0F;
		completed = 0.0F;
		alloted = 0.0F;
		taken = 0.0F;
		bfa = 0.0F;
		rev = 0.0F;
		wasted = 0.0F;
		used = 0.0F;
		eff = 0.0F;
	}

	public void effectiveAVG() {
		float f2 = 0.0F;
		float f3 = 0.0F;
		float f4 = 0.0F;
		float f5 = 0.0F;
		float f6 = 0.0F;
		float f7 = 0.0F;
		float f8 = 0.0F;
		float f9 = 0.0F;
		float f10 = 0.0F;
		int i = tb.getRowCount();

		String.valueOf(model1.getValueAt(tb1.getSelectedRow(), 0));

		for (int j = 0; j < i; j++) {

			float f11 = Float.parseFloat(String.valueOf(model.getValueAt(j, 1)));
			float f12 = Float.parseFloat(String.valueOf(model.getValueAt(j, 2)));
			float f13 = Float.parseFloat(String.valueOf(model.getValueAt(j, 3)));
			float f14 = Float.parseFloat(String.valueOf(model.getValueAt(j, 4)));
			float f15 = Float.parseFloat(String.valueOf(model.getValueAt(j, 5)));
			float f16 = Float.parseFloat(String.valueOf(model.getValueAt(j, 6)));
			float f17 = Float.parseFloat(String.valueOf(model.getValueAt(j, 7)));
			float f18 = Float.parseFloat(String.valueOf(model.getValueAt(j, 8)));
			Float.parseFloat(String.valueOf(model.getValueAt(j, 9)));

			f2 += f11;
			f3 += f12;
			f4 += f13;
			f5 += f14;
			f6 += f15;
			f7 += f16;
			f8 += f17;
			f9 += f18;

			float f20 = f4 / f2;
			float f21 = f9 / f3;

			f10 = f20 / f21;
		}

		model.addRow(new Object[] { "", String.valueOf(df1.format(f2)), String.valueOf(df1.format(f3)),
				String.valueOf(df1.format(f4)), String.valueOf(df1.format(f5)), String.valueOf(df1.format(f6)),
				String.valueOf(df1.format(f7)), String.valueOf(df1.format(f8)), String.valueOf(df1.format(f9)),
				String.valueOf(df2.format(f10)) });
	}

	public void empList2() {
		model1.setNumRows(0);
		try {
			con = DBConnectionUtil.getConnection();
			stat = con.createStatement();
			rs = stat.executeQuery(
					"SELECT distinct(P.Emp_code), MAX(E.Designation) from PROJECTMANPOWER p, emp_status e where P.PROJECT_NO='"
							+ southtf1.getText() + "' GROUP BY P.EMP_CODE");
			while (rs.next()) {
				String str1 = new String(rs.getString(1));
				String str2 = new String(rs.getString(2));

				System.out.println(str1 + "\t" + str2);

				model1.addRow(new Object[] { str1.toUpperCase(), str2 });
			}
		} catch (Exception localException) {
			System.out.println(localException);
		}
	}

	public void actionPerformed(java.awt.event.ActionEvent paramActionEvent) {
		if ((paramActionEvent.getSource() != totf) ||

		(paramActionEvent.getSource() == southtf1)) {
			empList2();
		}
		if (paramActionEvent.getSource() == dtlbut) {
			report1();
			BFA();
			REV();
			WASTED();
			USED();
			effectiveAVG();
		}
		if (paramActionEvent.getSource() == chkbut) {
			report2();
			BFA();
			REV();
			WASTED();
			USED();
			effectiveAVG();
		}

		if (paramActionEvent.getSource() == mi4) {
			f.setVisible(false);
		}
	}

	@SuppressWarnings("deprecation")
	public void mousePressed(MouseEvent paramMouseEvent) {
		if (paramMouseEvent.getSource() == tb1) {
			if (javax.swing.SwingUtilities.isRightMouseButton(paramMouseEvent)) {
				detailedList();
				checkedList();

				f1.setLocation(paramMouseEvent.getX() + 50, paramMouseEvent.getY());
				if (paramMouseEvent.getY() > 600) {
					f1.setLocation(paramMouseEvent.getX() + 40, paramMouseEvent.getY() / paramMouseEvent.getY() + 30);
				}
				f1.show();
			}
			if (javax.swing.SwingUtilities.isLeftMouseButton(paramMouseEvent)) {
				f1.setVisible(false);
			}
		}

		if (paramMouseEvent.getSource() == fromtf) {
			if (javax.swing.SwingUtilities.isRightMouseButton(paramMouseEvent)) {
				skl.showCalendar();
				skl.getDate(fromtf);
			}
		}
		if (paramMouseEvent.getSource() == totf) {
			if (javax.swing.SwingUtilities.isRightMouseButton(paramMouseEvent)) {
				skl.showCalendar();
				skl.getDate(totf);
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
