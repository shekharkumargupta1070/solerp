package com.sol.erp;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DecimalFormat;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.sol.erp.ui.custom.SolCalendar;
import com.sol.erp.ui.custom.SolTableModel;
import com.sol.erp.util.DBConnectionUtil;
import com.sol.erp.util.formater.SolDateFormatter;
import com.sol.erp.util.DateDifferencesUtil;
import com.sol.erp.util.DateCalculationUtil;

public class ProjectManPower implements java.awt.event.ActionListener, java.awt.event.KeyListener,
		java.awt.event.MouseListener, java.awt.event.FocusListener, java.awt.event.ItemListener {
	
	SolCalendar skl = new SolCalendar();

	Connection con;

	java.sql.Statement stat;

	ResultSet rs;
	int affected = 0;

	DecimalFormat df = new DecimalFormat("00.00");
	DecimalFormat df1 = new DecimalFormat("00");

	String[] columnNames1 = { "Project No", "Project CO", "Emp Code", "Total Hrs", "Factor", "From", "To", "TotalDays",
			"Alloted Hrs", "Eff Hrs" };
	Object[][] data1 = new Object[0][];

	String[] columnNames2 = { "DAY", "DATE", "PROJECT NO.", "ITEM CODE", "DRG NO.", "JOB", "OFF" };
	Object[][] data2 = new Object[0][];

	String[] columnNames3 = { "EMP Code", "DTL Hrs", "CHK Hrs" };
	Object[][] data3 = new Object[0][];

	javax.swing.JTabbedPane tabbedPane = new javax.swing.JTabbedPane();

	JPanel planningPanel = new JPanel();
	JPanel availablityPanel = new JPanel();
	JPanel factorPanel = new JPanel();
	JPanel assignPanel = new JPanel();

	DefaultTableModel model1 = new DefaultTableModel(data1, columnNames1);
	JTable tb1 = new JTable(model1);
	javax.swing.JScrollPane sp1 = new javax.swing.JScrollPane(tb1);

	DefaultTableModel model2 = new DefaultTableModel(data2, columnNames2);
	JTable tb2 = new JTable(model2);
	javax.swing.JScrollPane sp2 = new javax.swing.JScrollPane(tb2);

	DefaultTableModel model3 = new DefaultTableModel(data3, columnNames3);
	JTable tb3 = new JTable(model3);
	javax.swing.JScrollPane sp3 = new javax.swing.JScrollPane(tb3);

	javax.swing.JInternalFrame f = new javax.swing.JInternalFrame("Manpower Planning", true, true, true, true);
	JLabel l1 = new JLabel("PROJECT NO.");
	JLabel l2 = new JLabel("TL CODE");
	JLabel l3 = new JLabel("EMP CODE");
	JLabel l4 = new JLabel("AVG. WORKING Hrs.");
	JLabel l5 = new JLabel("FROM DATE");
	JLabel l6 = new JLabel("TO DATE");
	JLabel l7 = new JLabel("TOTAL DAYS");
	JLabel l8 = new JLabel("TOTAL TIME");
	JLabel l9 = new JLabel("EFFECTIVE ALLOTED Hrs.");
	JLabel l10 = new JLabel("PROJECT NO.");

	JComboBox tf1 = new JComboBox();
	JTextField tf2 = new JTextField();
	JTextField tf3 = new JTextField();
	JTextField tf4 = new JTextField();
	JTextField tf5 = new JTextField();
	JTextField tf6 = new JTextField();
	JTextField tf7 = new JTextField();
	JTextField tf8 = new JTextField();
	JTextField tf9 = new JTextField();
	JTextField tf10 = new JTextField();

	JButton savebut = new JButton("ALLOT IT");
	JButton updatebut = new JButton("UPDATE");
	JButton removebut = new JButton("Remove");
	JButton closebut = new JButton("Close X");

	JLabel durl = new JLabel("PROJECT Hrs.");
	JTextField durtf = new JTextField(6);

	JLabel desigl = new JLabel("JOB");
	JComboBox desigtf = new JComboBox();

	JLabel factl = new JLabel("EMP FACTOR");
	JTextField facttf = new JTextField(4);

	JLabel dl = new JLabel("DETAILING Hrs.");
	JTextField dtf = new JTextField("0", 6);

	JLabel cl = new JLabel("CHECKING Hrs.");
	JTextField ctf = new JTextField("0", 6);

	JLabel tl = new JLabel("TL Hrs.");
	JTextField ttf = new JTextField("0", 6);

	JLabel dl1 = new JLabel("ASSIGNED DETAILING. Hrs");
	JTextField dtf1 = new JTextField("0", 6);

	JLabel cl1 = new JLabel("ASSIGNED CHECKING Hrs.");
	JTextField ctf1 = new JTextField("0", 6);

	JLabel tl1 = new JLabel("T Time");
	JTextField ttf1 = new JTextField("0", 6);

	JLabel selectedlabel = new JLabel("SELECTED DAYS");
	JTextField selectedtf = new JTextField("0", 6);

	JPanel butpanel1 = new JPanel();
	JPanel butpanel2 = new JPanel();

	JPanel centerpanel = new JPanel();
	JPanel centerp1 = new JPanel();
	JPanel southpanel = new JPanel();

	javax.swing.JSplitPane spl = new javax.swing.JSplitPane(1, centerp1, sp3);
	javax.swing.JSplitPane spl1 = new javax.swing.JSplitPane(1, spl, sp2);
	javax.swing.JSplitPane spl2 = new javax.swing.JSplitPane(0, centerpanel, southpanel);

	javax.swing.JPopupMenu jpm = new javax.swing.JPopupMenu();
	javax.swing.JMenuItem popm1 = new javax.swing.JMenuItem("Refresh", new javax.swing.ImageIcon("image/check.gif"));

	java.awt.Font fo = new java.awt.Font("Tahoma", 1, 9);
	java.awt.Font bigfo = new java.awt.Font("Tahoma", 1, 9);
	javax.swing.border.Border line = javax.swing.BorderFactory.createLineBorder(Color.black);
	javax.swing.border.Border bor1 = javax.swing.BorderFactory.createTitledBorder(line, "Project Info", 0, 0,
			fo, Color.black);
	javax.swing.border.Border bor2 = javax.swing.BorderFactory.createTitledBorder(line,
			"MANPOWER PLAING FOR PROJECT", 0, 0, fo, Color.red);

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

			if (paramInt2 == 0) {
				setHorizontalAlignment(2);
			}

			String str1 = String.valueOf(model2.getValueAt(paramInt1, 6));

			if ((paramInt2 >= 0) && (paramInt2 < 5)) {
				setHorizontalAlignment(0);
				String str2 = String.valueOf(model2.getValueAt(paramInt1, 2));
				String str3 = String.valueOf(model2.getValueAt(paramInt1, 3));
				String str4 = String.valueOf(model2.getValueAt(paramInt1, 5));

				if ((str2.length() >= 1) && (str3.length() == 1) && (str4.equalsIgnoreCase("Detailing"))) {
					setBackground(new Color(90, 120, 150));
					setForeground(Color.white);
				}

				if ((str2.length() >= 1) && (str3.length() == 1) && (str4.equalsIgnoreCase("Checking"))) {
					setBackground(Color.gray);
					setForeground(Color.white);
				}

				if ((str2.length() > 1) && (str3.length() > 1) && (str4.equalsIgnoreCase("Detailing"))) {
					setBackground(new Color(20, 130, 80));
					setForeground(Color.white);
				}
				if ((str2.length() > 1) && (str3.length() > 1) && (str4.equalsIgnoreCase("Checking"))) {
					setBackground(Color.pink);
					setForeground(Color.black);
				}
				if ((str2.length() == 1) && (str3.length() == 1)) {
					setBackground(new Color(240, 240, 240));
					setForeground(Color.darkGray);
				}
				if (str1.equalsIgnoreCase("O")) {
					setBackground(new Color(250, 255, 210));
					setForeground(Color.red);
				}
			}

			if ((str1.equalsIgnoreCase("N")) || (str1.equalsIgnoreCase("R")) || (str1.equalsIgnoreCase("?"))) {
				setBackground(new Color(240, 240, 240));
				setForeground(new Color(220, 220, 220));
				if (paramInt2 == 6) {
					setBackground(Color.red);
					setForeground(Color.white);
				}
			}
			if ((str1.equalsIgnoreCase("A")) || (str1.equalsIgnoreCase("E")) || (str1.equalsIgnoreCase("C"))) {
				setBackground(new Color(240, 240, 240));
				setForeground(new Color(220, 220, 220));
				if (paramInt2 == 6) {
					setBackground(new Color(60, 60, 160));
					setForeground(Color.white);
				}
			}
			if (str1.equalsIgnoreCase("W")) {
				setBackground(new Color(240, 240, 240));
				setForeground(Color.darkGray);
			}

			String str2 = String.valueOf(model2.getValueAt(paramInt1, 0));
			if (paramInt2 == 0) {
				if ((str2.equalsIgnoreCase("SUN")) || (str2.equalsIgnoreCase("SAT"))) {
					setBackground(Color.yellow);
					setForeground(Color.darkGray);
				} else {
					setBackground(new Color(240, 240, 240));
					setForeground(Color.darkGray);
				}
			}

			super.getTableCellRendererComponent(paramJTable, paramObject, paramBoolean1, paramBoolean2, paramInt1,
					paramInt2);

			return this;
		}
	}

	class ColoredTableCellRenderer2 extends javax.swing.table.DefaultTableCellRenderer {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		ColoredTableCellRenderer2() {
		}

		public java.awt.Component getTableCellRendererComponent(JTable paramJTable, Object paramObject,
				boolean paramBoolean1, boolean paramBoolean2, int paramInt1, int paramInt2) {
			setEnabled((paramJTable == null) || (paramJTable.isEnabled()));

			if (paramInt1 % 2 == 0) {
				setBackground(new Color(200, 220, 240));
				setForeground(Color.darkGray);

			} else {
				setBackground(Color.white);
				setForeground(Color.darkGray);
			}

			if (paramInt2 == 0) {
				setHorizontalAlignment(0);
			}
			if (paramInt2 == 1) {
				setHorizontalAlignment(4);
			}
			if (paramInt2 == 2) {
				setHorizontalAlignment(4);
			}

			super.getTableCellRendererComponent(paramJTable, paramObject, paramBoolean1, paramBoolean2, paramInt1,
					paramInt2);

			return this;
		}
	}

	ColoredTableCellRenderer skr = new ColoredTableCellRenderer();
	ColoredTableCellRenderer2 skr2 = new ColoredTableCellRenderer2();

	javax.swing.table.TableColumnModel tbcm = tb2.getColumnModel();
	javax.swing.table.TableColumn tbc1 = tbcm.getColumn(0);
	javax.swing.table.TableColumn tbc2 = tbcm.getColumn(1);
	javax.swing.table.TableColumn tbc3 = tbcm.getColumn(2);
	javax.swing.table.TableColumn tbc4 = tbcm.getColumn(3);
	javax.swing.table.TableColumn tbc5 = tbcm.getColumn(4);
	javax.swing.table.TableColumn tbc6 = tbcm.getColumn(5);

	java.awt.Container c;

	public void px() {
		c = f.getContentPane();
		c.setLayout(new java.awt.BorderLayout());

		planningPanel.setLayout(new java.awt.BorderLayout());
		availablityPanel.setLayout(new java.awt.BorderLayout());
		factorPanel.setLayout(new java.awt.BorderLayout());
		assignPanel.setLayout(new java.awt.BorderLayout());

		butpanel1.setLayout(new java.awt.GridLayout(2, 8, 5, 5));
		butpanel2.setLayout(new java.awt.GridLayout(1, 8, 10, 10));
		centerpanel.setLayout(new java.awt.BorderLayout());
		centerp1.setLayout(new java.awt.GridLayout(10, 2, 2, 2));
		southpanel.setLayout(new java.awt.BorderLayout());

		centerpanel.setBorder(bor2);

		tabbedPane.add("MANPOWER PLANNING", planningPanel);
		tabbedPane.add("MANPOWER AVAILABLITY", availablityPanel);

		tabbedPane.add("ASSIGNING PROJECT TO TEAM LEADER", assignPanel);

		tabbedPane.setFont(new java.awt.Font("Courier New", 1, 14));

		desigtf.addItem("DETAILING");
		desigtf.addItem("CHECKING");

		jpm.add(popm1);

		for (int i = 0; i < tb3.getColumnCount(); i++) {
			tb3.getColumnModel().getColumn(i).setCellRenderer(skr2);
		}

		tb1.getTableHeader().setPreferredSize(new java.awt.Dimension(50, 20));
		tb2.getTableHeader().setPreferredSize(new java.awt.Dimension(50, 20));

		SolTableModel.decorateTable(tb1);

		SolTableModel.decorateTable(tb3);

		tb1.getTableHeader().setPreferredSize(new java.awt.Dimension(30, 30));
		tb1.getTableHeader().setFont(new java.awt.Font("Verdana", 0, 9));
		tb1.setShowGrid(true);

		tb3.getTableHeader().setPreferredSize(new java.awt.Dimension(30, 30));
		tb3.getTableHeader().setFont(new java.awt.Font("Verdana", 0, 9));
		tb3.setShowGrid(false);

		tb2.setGridColor(Color.white);
		tb2.setBackground(new Color(230, 230, 230));
		tb2.setAutoResizeMode(0);
		tb2.setFont(new java.awt.Font("Tahoma", 1, 10));
		tb2.getTableHeader().setFont(new java.awt.Font("Verdana", 0, 9));
		tb2.getTableHeader().setPreferredSize(new java.awt.Dimension(30, 30));
		tb2.setRowHeight(23);
		tb2.setShowGrid(true);

		tb2.getColumnModel().getColumn(0).setPreferredWidth(60);
		tb2.getColumnModel().getColumn(1).setPreferredWidth(100);
		tb2.getColumnModel().getColumn(2).setPreferredWidth(90);
		tb2.getColumnModel().getColumn(6).setPreferredWidth(40);

		for (int i = 0; i < tb2.getColumnCount(); i++) {
			tb2.getColumnModel().getColumn(i).setCellRenderer(skr);
		}

		tf1.setBackground(Color.white);
		desigtf.setBackground(Color.white);
		savebut.setBackground(Color.white);
		updatebut.setBackground(Color.white);
		removebut.setBackground(Color.white);
		closebut.setBackground(Color.red);
		closebut.setForeground(Color.white);

		l1.setFont(fo);
		l2.setFont(fo);
		l3.setFont(fo);
		l4.setFont(fo);
		l5.setFont(fo);
		l6.setFont(fo);
		l7.setFont(fo);
		l8.setFont(fo);
		l9.setFont(fo);
		l10.setFont(fo);

		durl.setFont(fo);
		dl.setFont(fo);
		cl.setFont(fo);
		tl.setFont(fo);
		desigl.setFont(fo);
		factl.setFont(fo);

		selectedlabel.setFont(fo);
		dl1.setFont(fo);
		cl1.setFont(fo);
		tl1.setFont(fo);

		durtf.setFont(bigfo);
		dtf.setFont(fo);
		ctf.setFont(fo);
		ctf.setForeground(Color.red);
		ttf.setFont(fo);
		ttf.setForeground(Color.blue);

		durl.setHorizontalAlignment(4);
		dl.setHorizontalAlignment(4);
		cl.setHorizontalAlignment(4);
		tl.setHorizontalAlignment(4);
		desigl.setHorizontalAlignment(4);
		factl.setHorizontalAlignment(4);

		selectedlabel.setHorizontalAlignment(4);
		dl1.setHorizontalAlignment(4);
		cl1.setHorizontalAlignment(4);
		tl1.setHorizontalAlignment(4);

		durtf.setHorizontalAlignment(4);
		dtf.setHorizontalAlignment(4);
		ctf.setHorizontalAlignment(4);
		ttf.setHorizontalAlignment(4);
		facttf.setHorizontalAlignment(4);

		selectedtf.setHorizontalAlignment(4);
		dtf1.setHorizontalAlignment(4);
		ctf1.setHorizontalAlignment(4);
		ttf1.setHorizontalAlignment(4);

		centerp1.add(l1);
		centerp1.add(tf1);
		tf1.setFont(fo);
		tf1.setEditable(true);
		centerp1.add(l2);
		centerp1.add(tf2);
		tf2.setFont(fo);
		tf2.setEditable(false);
		centerp1.add(l3);
		centerp1.add(tf3);
		tf3.setFont(fo);
		centerp1.add(l4);
		centerp1.add(tf4);
		tf4.setFont(fo);
		centerp1.add(l5);
		centerp1.add(tf5);
		tf5.setFont(fo);
		centerp1.add(l6);
		centerp1.add(tf6);
		tf6.setFont(fo);
		centerp1.add(l7);
		centerp1.add(tf7);
		tf7.setFont(fo);
		centerp1.add(l8);
		centerp1.add(tf8);
		tf8.setFont(fo);
		tf8.setEditable(false);
		centerp1.add(l9);
		centerp1.add(tf9);
		tf9.setFont(fo);
		tf9.setEditable(false);
		centerp1.add(l10);
		centerp1.add(tf10);
		tf10.setFont(fo);
		tf10.setEditable(false);

		butpanel1.add(durl);
		butpanel1.add(durtf);
		butpanel1.add(dl);
		butpanel1.add(dtf);
		butpanel1.add(cl);
		butpanel1.add(ctf);
		butpanel1.add(tl);
		butpanel1.add(ttf);
		butpanel1.add(new JPanel());

		butpanel1.add(desigl);
		butpanel1.add(desigtf);
		desigtf.setFont(fo);
		butpanel1.add(factl);
		butpanel1.add(facttf);
		facttf.setFont(fo);

		butpanel1.add(savebut);
		savebut.setMnemonic(83);
		butpanel1.add(removebut);
		removebut.setMnemonic(82);
		butpanel1.add(new JPanel());
		butpanel1.add(new JPanel());
		butpanel1.add(new JPanel());

		durtf.setEditable(false);
		dtf.setEditable(false);
		ctf.setEditable(false);
		ttf.setEditable(false);
		desigtf.setEditable(false);
		facttf.setEditable(false);

		butpanel2.add(selectedlabel);
		butpanel2.add(selectedtf);
		selectedtf.setFont(bigfo);
		butpanel2.add(dl1);
		butpanel2.add(dtf1);
		dtf1.setFont(bigfo);
		butpanel2.add(cl1);
		butpanel2.add(ctf1);
		ctf1.setFont(bigfo);
		ctf1.setForeground(Color.red);
		butpanel2.add(tl1);
		butpanel2.add(ttf1);
		ttf1.setFont(bigfo);
		ttf1.setForeground(Color.blue);

		butpanel2.add(closebut);
		closebut.setMnemonic(67);

		selectedtf.setEditable(false);
		dtf1.setEditable(false);
		ctf1.setEditable(false);
		ttf1.setEditable(false);

		centerpanel.add(spl1, "Center");
		centerpanel.add(butpanel1, "South");

		southpanel.add(sp1, "Center");
		southpanel.add(butpanel2, "South");

		sp1.setPreferredSize(new java.awt.Dimension(100, 260));
		sp2.setPreferredSize(new java.awt.Dimension(100, 260));
		sp3.setPreferredSize(new java.awt.Dimension(230, 230));

		planningPanel.add(spl2, "Center");
		availablityPanel.add(new ManpowerAvail().DesignFrame(), "Center");

		assignPanel.add(new ProjectCo().DesignFrame(), "Center");

		c.add(tabbedPane, "Center");

		popm1.addActionListener(this);
		savebut.addActionListener(this);
		updatebut.addActionListener(this);
		removebut.addActionListener(this);
		closebut.addActionListener(this);

		tf1.addFocusListener(this);
		tf1.addActionListener(this);

		tf4.addFocusListener(this);
		tf3.addFocusListener(this);

		tf3.addKeyListener(this);
		tf4.addFocusListener(this);
		tf6.addFocusListener(this);

		tf7.addKeyListener(this);
		tf7.addFocusListener(this);
		tf1.addFocusListener(this);

		tb1.addMouseListener(this);
		tb1.addKeyListener(this);

		tb2.addMouseListener(this);
		tb2.addKeyListener(this);

		tb3.addMouseListener(this);
		tb3.addKeyListener(this);

		tf5.addMouseListener(this);
		tf6.addMouseListener(this);

		tabbedPane.addMouseListener(this);

		desigtf.addItemListener(this);

		spl.setDividerLocation(250);
		spl.setOneTouchExpandable(true);
		spl1.setDividerLocation(450);
		spl1.setOneTouchExpandable(true);
		spl2.setDividerLocation(340);
		spl2.setOneTouchExpandable(true);

		f.setLocation((ss.width - fs.width) / 30, (ss.height - fs.height) / 100);

		f.setSize(950, 600);
		f.setVisible(true);
	}

	public void dateDiff() {
		tf7.setText(String.valueOf(DateDifferencesUtil.getDayCount(tf5.getText(), tf6.getText()) - 1));
	}

	public void selectedDays() {
		int i = Integer.parseInt(tf4.getText());
		int j = Integer.parseInt(selectedtf.getText());
		int k = i * j;
		tf8.setText(String.valueOf(k));

		float f1 = Float.parseFloat(facttf.getText());
		float f2 = Float.parseFloat(tf8.getText());
		float f3 = f1 * f2;
		tf9.setText(String.valueOf(df.format(f3)));
	}

	public void splitTime() {
		float f1 = Float.parseFloat(durtf.getText());
		float f2 = f1 * 60.0F / 100.0F;
		float f3 = f1 * 30.0F / 100.0F;
		float f4 = f1 * 10.0F / 100.0F;

		dtf.setText(String.valueOf(df.format(f2)));
		ctf.setText(String.valueOf(df.format(f3)));
		ttf.setText(String.valueOf(df.format(f4)));
	}

	public void empAvg() {
		try {
			con = DBConnectionUtil.getConnection();
			stat = con.createStatement();
			rs = stat.executeQuery(
					"select average_hrs, job, factor from emp_status where emp_code='" + tf3.getText() + "' ");
			rs.getMetaData().getColumnCount();
			while (rs.next()) {
				String str1 = new String(rs.getString(1));
				String str2 = new String(rs.getString(2));
				String str3 = new String(rs.getString(3));

				tf4.setText(str1);
				facttf.setText(str3);

				desigtf.setSelectedItem(str2);
				tf5.requestFocus();
			}
		} catch (Exception localException) {
			System.out.println(localException);
		}
	}

	public void empList() {
		model3.setNumRows(0);
		try {
			con = DBConnectionUtil.getConnection();
			stat = con.createStatement();
			rs = stat.executeQuery("select emp_code from PROJECTMANPOWER where project_no='"
					+ tf1.getSelectedItem() + "' GROUP BY EMP_CODE ");
			rs.getMetaData().getColumnCount();
			while (rs.next()) {
				String str = new String(rs.getString(1));

				model3.addRow(new Object[] { str.toUpperCase(), "0", "0" });
			}
		} catch (Exception localException) {
			System.out.println(localException);
		}
		model3.addRow(new Object[] { "", "0", "0" });

		empList2();
		empList3();
	}

	public void empList2() {
		if (tb3.getRowCount() >= 0) {

			int i = tb3.getRowCount();
			float f1 = 0.0F;
			float f2 = 0.0F;

			for (int j = 0; j < i - 1; j++) {
				String str1 = String.valueOf(model3.getValueAt(j, 0));
				try {
					stat = con.createStatement();
					rs = stat
							.executeQuery("select (select sum(total_hrs) from PROJECTMANPOWER where project_no='"
									+ tf1.getSelectedItem() + "' and emp_code='" + str1
									+ "' and dtl_chk='detailing') from PROJECTMANPOWER where Project_no='"
									+ tf1.getSelectedItem() + "'  ");
					rs.getMetaData().getColumnCount();
					while (rs.next()) {
						String str2 = new String(rs.getString(1));

						f1 = Float.parseFloat(str2);
						model3.setValueAt(df1.format(f1), j, 1);
					}
				} catch (Exception localException) {
					System.out.println(localException);
				}
				f2 += f1;
				f1 = 0.0F;
			}

			model3.setValueAt(df1.format(f2), i - 1, 1);
		}
	}

	public void empList3() {
		if (tb3.getRowCount() >= 0) {

			float f1 = 0.0F;
			float f2 = 0.0F;
			int i = tb3.getRowCount();
			for (int j = 0; j < i - 1; j++) {
				String str1 = String.valueOf(model3.getValueAt(j, 0));
				try {
					stat = con.createStatement();
					rs = stat
							.executeQuery("select (select sum(total_hrs) from PROJECTMANPOWER where project_no='"
									+ tf1.getSelectedItem() + "' and emp_code='" + str1
									+ "' and dtl_chk='Checking') from PROJECTMANPOWER where Project_no='"
									+ tf1.getSelectedItem() + "'  ");
					rs.getMetaData().getColumnCount();
					while (rs.next()) {
						String str2 = new String(rs.getString(1));

						f1 = Float.parseFloat(str2);

						model3.setValueAt(df1.format(f1), j, 2);
					}
				} catch (Exception localException) {
					System.out.println(localException);
				}
				f2 += f1;
				f1 = 0.0F;
			}
			model3.setValueAt(df1.format(f2), i - 1, 2);
		}
	}

	public void projectList() {
		try {
			con = DBConnectionUtil.getConnection();
			stat = con.createStatement();
			rs = stat.executeQuery(
					"select distinct(project_no) from estimation_mp where project_no > '2007' order by project_no ");
			rs.getMetaData().getColumnCount();
			while (rs.next()) {
				String str = new String(rs.getString(1));
				tf1.addItem(str);
			}
		} catch (Exception localException) {
			System.out.println(localException);
		}
	}

	public void allotedTime() {
		dtf1.setText("0");

		try {
			stat = con.createStatement();
			rs = stat.executeQuery(
					"select sum(total_hrs) from projectmanpower where dtl_chk='Detailing' and project_no='"
							+ tf1.getSelectedItem() + "' ");
			rs.getMetaData().getColumnCount();

			while (rs.next()) {
				String str = new String(rs.getString(1));
				float f1 = Float.parseFloat(str);
				System.out.println(f1);
				dtf1.setText(String.valueOf(df.format(f1)));
			}
		} catch (Exception localException) {
			System.out.println(localException);
		}
	}

	public void allotedTime2() {
		ctf1.setText("0");

		try {
			stat = con.createStatement();
			rs = stat
					.executeQuery("select sum(total_hrs) from projectmanpower where dtl_chk='Checking' and project_no='"
							+ tf1.getSelectedItem() + "' ");
			rs.getMetaData().getColumnCount();

			while (rs.next()) {
				String str = new String(rs.getString(1));
				float f1 = Float.parseFloat(str);
				System.out.println(f1);
				ctf1.setText(String.valueOf(df.format(f1)));
			}
		} catch (Exception localException) {
			System.out.println(localException);
		}
	}

	int assigned = 0;
	String fromstr;
	String tostr;

	public void assigned() {
		try {
			stat = con.createStatement();
			rs = stat.executeQuery("select max(assigned) from PROJECTMANPOWER where EMP_code= '"
					+ tf3.getText() + "' and project_no='" + tf1.getSelectedItem() + "' AND DTL_CHK='"
					+ desigtf.getSelectedItem() + "'  ");
			rs.getMetaData().getColumnCount();
			while (rs.next()) {
				assigned = Integer.parseInt(rs.getString(1));
				assigned += 1;
			}
		} catch (Exception localException) {
			System.out.println(localException);
		}
	}

	public void dateRange() {
		int i = tb2.getSelectedRow();
		int[] arrayOfInt = tb2.getSelectedRows();
		int j = arrayOfInt.length;
		int k = i + j - 1;

		fromstr = String.valueOf(model2.getValueAt(i, 1));
		tostr = String.valueOf(model2.getValueAt(k, 1));
		System.out.println(fromstr + "\t" + tostr);
	}

	public void dynamicSave() {
		model1.setNumRows(0);

		int[] arrayOfInt = tb2.getSelectedRows();
		int i = arrayOfInt.length;

		dtime = Float.parseFloat(dtf.getText());
		ctime = Float.parseFloat(ctf.getText());

		float f1 = Float.parseFloat(tf9.getText());
		float f2 = f1 / arrayOfInt.length;

		for (int j = 0; j < arrayOfInt.length; j++) {
			String str1 = String.valueOf(model2.getValueAt(arrayOfInt[j], 1));
			String str2 = String.valueOf(model2.getValueAt(arrayOfInt[j], 6));

			String str3 = str1;
			String str4 = str3.substring(0, 2);
			String str5 = str3.substring(3, 5);
			String str6 = str3.substring(6, 10);

			@SuppressWarnings("deprecation")
			java.sql.Date localDate = new java.sql.Date(Integer.parseInt(str6) - 1900, Integer.parseInt(str5) - 1,
					Integer.parseInt(str4));

			if ((str2.equalsIgnoreCase("A")) || (str2.equalsIgnoreCase("N")) || (str2.equalsIgnoreCase("R"))
					|| (str2.equalsIgnoreCase("?")) || (str2.equalsIgnoreCase("C")) || (str2.equalsIgnoreCase("E"))) {
				javax.swing.JOptionPane.showMessageDialog(f,
						"Cannot Allot on " + str1 + ". Employee is on Leave.");
				break;
			}

			try {
				PreparedStatement localPreparedStatement = con
						.prepareStatement("insert into projectmanpower values('" + tf1.getSelectedItem() + "','"
								+ tf2.getText() + "','" + tf3.getText() + "','" + tf4.getText() + "','"
								+ str1 + "','0','" + facttf.getText() + "','" + f2 + "','0','"
								+ String.valueOf(desigtf.getSelectedItem()) + "', ? )");
				localPreparedStatement.setDate(1, localDate);
				affected = localPreparedStatement.executeUpdate();

				if (affected > 0) {
					model1.addRow(new Object[] { tf1.getSelectedItem(), tf2.getText(),
							tf3.getText(), tf4.getText(), facttf.getText(), str1, "0",
							tf7.getText(), tf4.getText(), String.valueOf(df.format(f2)) });

					model2.setValueAt(tf1.getSelectedItem(), arrayOfInt[j], 2);
					model2.setValueAt(desigtf.getSelectedItem(), arrayOfInt[j], 5);
				}

				if (String.valueOf(desigtf.getSelectedItem()).equalsIgnoreCase("Detailing")) {
					adtime = Float.parseFloat(dtf1.getText());
					actime = Float.parseFloat(ctf1.getText());
					float f3 = Float.parseFloat(durtf.getText());

					efftime = (Float.parseFloat(tf9.getText()) / i);
					adtime += efftime;
					dtf1.setText(String.valueOf(df.format(adtime)));

					float f4 = f3 - (adtime + actime);
					ttf1.setText(String.valueOf(df.format(f4)));

					if (adtime >= dtime) {
						javax.swing.JOptionPane.showMessageDialog(f, "Detailing Time is Being Over Now.");
					}
				}

				if (String.valueOf(desigtf.getSelectedItem()).equalsIgnoreCase("Checking")) {
					actime = Float.parseFloat(ctf1.getText());
					efftime1 = (Float.parseFloat(tf9.getText()) / i);
					actime += efftime1;
					ctf1.setText(String.valueOf(df.format(actime)));

					if (actime >= ctime) {
						javax.swing.JOptionPane.showMessageDialog(f, "Checking Time is Being Over Now.");
					}
				}
			} catch (Exception localException) {
				javax.swing.JOptionPane.showMessageDialog(f, localException.getMessage().toString());
			}
		}
	}

	float dtime;
	float ctime;

	public void remove() {
		int[] arrayOfInt = tb2.getSelectedRows();

		for (int i = 0; i < arrayOfInt.length; i++) {
			String.valueOf(desigtf.getSelectedItem());
			String str2 = String.valueOf(model2.getValueAt(arrayOfInt[i], 1));
			String str3 = String.valueOf(model2.getValueAt(arrayOfInt[i], 3));

			if (str3.length() > 1) {
				javax.swing.JOptionPane.showMessageDialog(f, "This Slot is not Empty, Cannot Remove.");
				break;
			}

			try {
				stat = con.createStatement();
				affected = stat
						.executeUpdate("delete from projectmanpower where from_date='" + str2 + "' and project_no='"
								+ tf1.getSelectedItem() + "' and emp_code='" + tf3.getText() + "' ");

				if (affected > 0) {
					model2.setValueAt("0", arrayOfInt[i], 2);
					model2.setValueAt("0", arrayOfInt[i], 5);
				}
			} catch (Exception localException) {
				javax.swing.JOptionPane.showMessageDialog(f, localException.getMessage().toString());
			}
		}
	}

	public void projectCo() {
		String str1 = "0";
		String str2 = "0";
		String str3 = "0";
		String str4 = "0";
		tf2.setText(str1);
		durtf.setText(str2);

		try {
			PreparedStatement localPreparedStatement = con.prepareStatement(
					"select CO_CODE, QTD_HRS, START, NEW_FINAL_DATE from PROJECT_CO where PROJECT_NO = ? ");
			localPreparedStatement.setInt(1, Integer.parseInt((String) tf1.getSelectedItem()));
			rs = localPreparedStatement.executeQuery();
			rs.getMetaData().getColumnCount();
			while (rs.next()) {
				str1 = new String(rs.getString(1));
				str2 = String.valueOf(df.format(rs.getFloat(2)));
				str3 = SolDateFormatter.SQLtoDDMMYY(rs.getDate(3));
				str4 = SolDateFormatter.SQLtoDDMMYY(rs.getDate(4));

				tf2.setText(str1);
				durtf.setText(str2);
				tf5.setText(str3);
				tf6.setText(str4);
				tf3.requestFocus();
			}
		} catch (Exception localException) {
			System.out.println(localException);
		}
		str1 = "0";
		str2 = "0";
	}

	float adtime = 0.0F;
	float actime = 0.0F;
	float efftime = 0.0F;
	float efftime1 = 0.0F;
	java.util.Calendar gcal;

	public void saveRecord() {
		assigned();

		dtime = Float.parseFloat(dtf.getText());
		ctime = Float.parseFloat(ctf.getText());

		try {
			stat = con.createStatement();
			affected = stat.executeUpdate("insert into projectmanpower values('" + tf1.getSelectedItem()
					+ "','" + tf2.getText() + "','" + tf3.getText() + "','" + tf4.getText() + "','"
					+ fromstr + "','" + tostr + "','" + tf7.getText() + "','" + tf8.getText()
					+ "','" + assigned + "','" + String.valueOf(desigtf.getSelectedItem()) + "' )");

			if (affected > 0) {
				model1.addRow(new Object[] { tf1.getSelectedItem(), tf2.getText(), tf3.getText(),
						tf4.getText(), facttf.getText(), fromstr, tostr, tf7.getText(),
						tf8.getText(), tf9.getText() });

				if (String.valueOf(desigtf.getSelectedItem()).equalsIgnoreCase("Detailing")) {
					adtime = Float.parseFloat(dtf1.getText());
					actime = Float.parseFloat(ctf1.getText());
					float f1 = Float.parseFloat(durtf.getText());

					efftime = Float.parseFloat(tf9.getText());
					adtime += efftime;
					dtf1.setText(String.valueOf(df.format(adtime)));

					float f2 = f1 - (adtime + actime);
					ttf1.setText(String.valueOf(df.format(f2)));

					if (adtime >= dtime) {
						javax.swing.JOptionPane.showMessageDialog(f, "Detailing Time is Being Over Now.");
					}
				}

				if (String.valueOf(desigtf.getSelectedItem()).equalsIgnoreCase("Checking")) {
					actime = Float.parseFloat(ctf1.getText());
					efftime1 = Float.parseFloat(tf9.getText());
					actime += efftime1;
					ctf1.setText(String.valueOf(df.format(actime)));
					if (actime >= ctime) {
						javax.swing.JOptionPane.showMessageDialog(f, "Checking Time is Being Over Now.");
					}
				}
				tf3.requestFocus();
			}
		} catch (Exception localException) {
			javax.swing.JOptionPane.showMessageDialog(f, localException.getMessage().toString());
		}
	}

	public void jobDetails() {
		for (int i = 0; i < tb2.getRowCount(); i++) {
			String str1 = String.valueOf(model2.getValueAt(i, 1));
			model2.setValueAt("0", i, 2);
			model2.setValueAt("0", i, 5);
			try {
				stat = con.createStatement();
				rs = stat.executeQuery("select project_no, dtl_chk from projectmanpower where emp_code='"
						+ tf3.getText() + "' and from_date='" + str1 + "' ");

				while (rs.next()) {
					String str2 = new String(rs.getString(1));
					String str3 = new String(rs.getString(2));
					model2.setValueAt(str2.toUpperCase(), i, 2);
					model2.setValueAt(str3.toUpperCase(), i, 5);
				}
			} catch (Exception localException) {
				System.out.println(localException);
			}
		}
	}

	public void refreshAlloted() {
		int i = tb2.getRowCount();
		for (int j = 0; j < i; j++) {
			String str1 = String.valueOf(model2.getValueAt(j, 1));
			String.valueOf(model2.getValueAt(j, 2));
			String str3 = String.valueOf(model2.getValueAt(j, 5));

			if (str3.equalsIgnoreCase("0")) {
				str3 = "Detailing";
			}

			model2.setValueAt("0", j, 3);
			model2.setValueAt("0", j, 4);

			try {
				PreparedStatement localPreparedStatement = con.prepareStatement(
						"select item_code, drawing_no from " + str3 + " where emp_code = ? and date_month = ? ");
				localPreparedStatement.setString(1, tf3.getText());
				localPreparedStatement.setString(2, str1);
				ResultSet localResultSet = localPreparedStatement.executeQuery();

				while (localResultSet.next()) {
					String str4 = new String(localResultSet.getString(1));
					String str5 = new String(localResultSet.getString(2));
					model2.setValueAt(str4.toUpperCase(), j, 3);
					model2.setValueAt(str5.toUpperCase(), j, 4);
				}
			} catch (Exception localException) {
				System.out.println(localException);
			}
		}
	}

	String[] days = { "SUN", "MON", "TUE", "WED", "THU", "FRI", "SAT" };

	java.util.ArrayList datelist = new java.util.ArrayList();

	public void workDetails() {
		model2.setNumRows(0);
		datelist.clear();
		int i = DateDifferencesUtil.getDayCount(tf5.getText(), tf6.getText());

		datelist = DateDifferencesUtil.getDate(tf5.getText(), tf6.getText(), i);

		for (int j = 0; j < datelist.size(); j++) {
			model2.addRow(new Object[] { "", datelist.get(j) });
		}

		for (int j = 0; j < tb2.getRowCount(); j++) {
			String str1 = String.valueOf(model2.getValueAt(j, 1));
			String str2 = DateCalculationUtil.NameOfDay(str1);
			model2.setValueAt(str2, j, 0);
		}

		officialLeave();
		empLeaveDetails();
	}

	public void officialLeave() {
		for (int i = 0; i < tb2.getRowCount(); i++) {
			String str1 = String.valueOf(model2.getValueAt(i, 1));
			String.valueOf(model2.getValueAt(i, 0));
			String str3 = "C01";
			String str4 = "0";

			java.sql.Date localDate = SolDateFormatter.DDMMYYtoSQL(str1);
			model2.setValueAt("W", i, 6);
			Object localObject;
			try {
				con = DBConnectionUtil.getConnection();
				PreparedStatement localPreparedStatement1 = con
						.prepareStatement("Select WORK_ARGS from HR_OT_URGENT where date= ? and  COMPANY_ID='" + str3
								+ "' and BRANCH_CODE='" + str4 + "'  ");
				localPreparedStatement1.setDate(1, localDate);
				localObject = localPreparedStatement1.executeQuery();
				while (((ResultSet) localObject).next()) {
					String str5 = new String(((ResultSet) localObject).getString(1));
					if (str5.equalsIgnoreCase("true")) {
						model2.setValueAt("W", i, 6);
					}
					if (str5.equalsIgnoreCase("false")) {
						model2.setValueAt("O", i, 6);
					}
				}
			} catch (Exception localException1) {
				System.out.println("Official Leave : " + localException1);
			}

			try {
				PreparedStatement localPreparedStatement2 = con
						.prepareStatement("Select HOLIDAY_TYPE from HR_HOLIDAY_LIST where date = ? and  COMPANY_ID='"
								+ str3 + "' and BRANCH_CODE='" + str4 + "'  ");
				localPreparedStatement2.setDate(1, localDate);
				rs = localPreparedStatement2.executeQuery();
				while (rs.next()) {
					localObject = new String(rs.getString(1));
					if (((String) localObject).equalsIgnoreCase("NH")) {
						model2.setValueAt("N", i, 6);
					}
					if (((String) localObject).equalsIgnoreCase("RH")) {
						model2.setValueAt("R", i, 6);
					}
					if (((String) localObject).equalsIgnoreCase("OH")) {
						model2.setValueAt("?", i, 6);
					}
				}
			} catch (Exception localException2) {
				System.out.println("Official Leave : " + localException2);
			}
		}
	}

	public void empLeaveDetails() {
		String str1 = String.valueOf(tf3.getText());

		for (int i = 0; i < tb2.getRowCount(); i++) {
			String str2 = String.valueOf(model2.getValueAt(i, 1));
			String.valueOf(model2.getValueAt(i, 0));
			java.sql.Date localDate = SolDateFormatter.DDMMYYtoSQL(str2);

			try {
				PreparedStatement localPreparedStatement = con
						.prepareStatement("Select category from HREMP_LEAVES where emp_code='" + str1
								+ "' and approve_tl='true' and approve_hr='true' AND date= ? ");
				localPreparedStatement.setDate(1, localDate);
				ResultSet localResultSet = localPreparedStatement.executeQuery();

				String str6 = null;

				while (localResultSet.next()) {
					str6 = new String(localResultSet.getString(1));

					if (str6.equalsIgnoreCase("LWP")) {
						model2.setValueAt("A", i, 6);
						str6 = null;
					}
					if (str6.equalsIgnoreCase("EL")) {
						model2.setValueAt("E", i, 6);
						str6 = null;
					}
					if (str6.equalsIgnoreCase("CL")) {
						model2.setValueAt("C", i, 6);
						str6 = null;
					}
				}
				localPreparedStatement.close();
			} catch (Exception localException) {
				System.out.println("Thread Run [HREMP_LEAVES]: " + localException);
			}
		}
	}

	public void actionPerformed(java.awt.event.ActionEvent paramActionEvent) {
		if (paramActionEvent.getSource() == tf1) {
			projectCo();
			splitTime();
			empList();

			allotedTime();
			allotedTime2();
		}

		if (paramActionEvent.getSource() == closebut) {
			f.setVisible(false);
		}
		if (paramActionEvent.getSource() == savebut) {

			dynamicSave();
		}
		if (paramActionEvent.getSource() == removebut) {
			dtf1.setText("0");
			ctf1.setText("0");
			remove();
			allotedTime();
			allotedTime2();
		}

		if (paramActionEvent.getSource() == popm1) {
			refreshAlloted();
		}
	}

	public void itemStateChanged(java.awt.event.ItemEvent paramItemEvent) {
		if (paramItemEvent.getSource() == desigtf) {
		}
	}

	public void focusGained(java.awt.event.FocusEvent paramFocusEvent) {
		if (paramFocusEvent.getSource() == tf4) {
			empAvg();
		}
	}

	public void focusLost(java.awt.event.FocusEvent paramFocusEvent) {
		if ((paramFocusEvent.getSource() != tf1) ||

		(paramFocusEvent.getSource() == tf6)) {
			dateDiff();
			workDetails();
			jobDetails();
			refreshAlloted();
		}
	}

	public void keyPressed(java.awt.event.KeyEvent paramKeyEvent) {
		int i = paramKeyEvent.getKeyCode();
		if (paramKeyEvent.getSource() == tb2) {
			if (i == 127) {
				remove();
				allotedTime();
				allotedTime2();
			}
		}
	}

	public void keyReleased(java.awt.event.KeyEvent paramKeyEvent) {
	}

	public void keyTyped(java.awt.event.KeyEvent paramKeyEvent) {
	}

	public void mousePressed(MouseEvent paramMouseEvent) {
		int i;
		Object localObject;
		if (paramMouseEvent.getSource() == tabbedPane) {
			i = tabbedPane.getSelectedIndex();
			if (i == 2) {
				localObject = new EmpList();
				((EmpList) localObject).getCode(tf3);
			}
			if (i == 1) {
				localObject = new ManpowerAvail();
				((ManpowerAvail) localObject).getCode(tf3);
			}
		}

		if (paramMouseEvent.getSource() == tb2) {
			if (javax.swing.SwingUtilities.isRightMouseButton(paramMouseEvent)) {
				jpm.show((javax.swing.JComponent) paramMouseEvent.getSource(), paramMouseEvent.getX(),
						paramMouseEvent.getY());
			}
		}

		if (paramMouseEvent.getSource() == tb3) {
			i = tb3.getSelectedRow();
			localObject = String.valueOf(model3.getValueAt(i, 0));
			tf3.setText((String) localObject);
			empAvg();
			dateDiff();
			workDetails();
			jobDetails();
			refreshAlloted();
		}
	}

	public void mouseReleased(MouseEvent paramMouseEvent) {
		if (paramMouseEvent.getSource() == tb2) {
			int[] arrayOfInt = tb2.getSelectedRows();
			int i = arrayOfInt.length;
			selectedtf.setText(String.valueOf(i));

			selectedDays();
			dateRange();
		}
	}

	public void mouseClicked(MouseEvent paramMouseEvent) {
	}

	public void mouseEntered(MouseEvent paramMouseEvent) {
	}

	public void mouseExited(MouseEvent paramMouseEvent) {
	}
}