package com.sol.erp;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DecimalFormat;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import com.sol.erp.ui.custom.SolTableModel;
import com.sol.erp.util.DBConnectionUtil;
import com.sol.erp.util.DateCalculationUtil;
import com.sol.erp.util.formater.SolDateFormatter;

public class MonthlyLeaveDetails implements java.awt.event.ActionListener, java.awt.event.MouseListener,
		javax.swing.event.TableModelListener, java.awt.event.KeyListener {
	
	

	java.sql.Connection con;
	java.sql.Statement stat;
	ResultSet rs;
	public static String codestr;
	public static String[] head = { "Punchcard", "EMP Code", "Designation", "Name", "Avg." };
	public static Object[][] data = new Object[0][];

	DecimalFormat df;

	DecimalFormat df2;

	DecimalFormat monthformat;

	JFrame f;

	JLabel desiglabel;

	JLabel leavetypelabel;

	JLabel yearlabel;

	JLabel fromlabel;

	JLabel tolabel;

	JLabel datelabel;
	JComboBox desigcb;
	JComboBox leavetypecb;
	JComboBox yearcb;
	JComboBox fromcb;
	JComboBox tocb;
	JButton b4;
	JButton b5;
	JButton addbut;
	JButton savebut;
	JButton printbut;
	public static DefaultTableModel model = new DefaultTableModel(data, head);
	public static JTable tb = new JTable(model);

	javax.swing.JScrollPane sp;

	javax.swing.table.DefaultTableCellRenderer renderer;

	TableColumnModel tcm;

	TableColumn col;

	TableColumn col1;

	TableColumn col2;

	TableColumn col3;

	TableColumn col4;

	JPanel mainPanel;

	JPanel p1;

	JPanel p2;

	JPanel buttonpanel;
	JPanel southpanel;
	javax.swing.JTextField searchtf;
	JRadioButton rb1;
	JRadioButton rb2;
	JRadioButton rb3;
	javax.swing.ButtonGroup grp;
	javax.swing.border.Border bor1;
	Font fo;
	Font fo2;
	java.awt.Toolkit tk;
	Dimension ss;
	Dimension fs;
	java.util.Date dat;
	java.text.SimpleDateFormat formatter;
	java.text.SimpleDateFormat formatter1;
	String dateString;
	ColoredTableCellRenderer skr;
	ColoredTableCellRenderer2 skr2;

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
				setBackground(new Color(250, 250, 250));
				setForeground(Color.darkGray);

			} else {
				setBackground(new Color(240, 240, 240));
				setForeground(Color.darkGray);
			}

			if ((paramInt1 > 1) && (paramInt1 == tb.getRowCount() - 1)) {
				setBackground(Color.darkGray);
				setForeground(Color.white);
			}

			if ((paramInt2 == 0) || (paramInt2 == 1) || (paramInt2 == 4)) {
				setHorizontalAlignment(0);
			}
			if ((paramInt2 == 2) || (paramInt2 == 3)) {
				setHorizontalAlignment(2);
			}

			if (paramInt2 == 4) {
				String str = String.valueOf(model.getValueAt(paramInt1, 4));
				float f = Float.parseFloat(str);

				if (f > 10.0F) {
					setBackground(new Color(60, 160, 200));
					setForeground(Color.white);
				}
				if (f < 9.0F) {
					setBackground(new Color(250, 100, 100));
					setForeground(Color.white);
				}
			}

			if (paramInt2 == 0) {
				setBackground(new Color(200, 120, 200));
				setForeground(Color.white);
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

			setHorizontalAlignment(0);

			if ((paramInt1 > 1) && (paramInt1 == tb.getRowCount() - 1)) {
				setBackground(Color.darkGray);
				setForeground(Color.white);
			}

			if ((paramInt2 > 4) && (paramObject != null)) {
				String str1 = String.valueOf(leavetypecb.getSelectedItem());
				String str2 = String.valueOf(model.getValueAt(paramInt1, paramInt2));
				float f = Float.parseFloat(str2);

				setBackground(new Color(240, 240, 240));
				setForeground(Color.darkGray);
				if (((str1.equalsIgnoreCase("LWP")) && (f > 5.0F))
						|| ((str1.equalsIgnoreCase("ALL")) && (f >= 10.0F))) {
					setBackground(Color.red);
					setForeground(Color.white);
				}
			}
			super.getTableCellRendererComponent(paramJTable, paramObject, paramBoolean1, paramBoolean2, paramInt1,
					paramInt2);

			return this;
		}
	}

	public MonthlyLeaveDetails() {
		df = new DecimalFormat("0");
		df2 = new DecimalFormat("0.0");
		monthformat = new DecimalFormat("00");

		f = new JFrame();

		desiglabel = new JLabel("DESIGNATION");
		leavetypelabel = new JLabel("LEAVE TYPE");
		yearlabel = new JLabel("SELECT YEAR");
		fromlabel = new JLabel("FROM");
		tolabel = new JLabel("TO");
		datelabel = new JLabel("Today : ");

		desigcb = new JComboBox();
		leavetypecb = new JComboBox();
		yearcb = new JComboBox();
		fromcb = new JComboBox();
		tocb = new JComboBox();

		b4 = new JButton("Show All");
		b5 = new JButton("Search");

		addbut = new JButton("GENERATE >>");
		savebut = new JButton("Save Changes");
		printbut = new JButton("PRINT");

		sp = new javax.swing.JScrollPane(tb);

		renderer = new javax.swing.table.DefaultTableCellRenderer();

		tcm = tb.getColumnModel();
		col = tcm.getColumn(0);
		col1 = tcm.getColumn(1);
		col2 = tcm.getColumn(2);
		col3 = tcm.getColumn(3);
		col4 = tcm.getColumn(4);

		mainPanel = new JPanel();
		p1 = new JPanel();
		p2 = new JPanel();
		buttonpanel = new JPanel();
		southpanel = new JPanel();

		searchtf = new javax.swing.JTextField(15);
		rb1 = new JRadioButton("Emp Code", true);
		rb2 = new JRadioButton("Name");
		rb3 = new JRadioButton("Desig");
		grp = new javax.swing.ButtonGroup();

		bor1 = javax.swing.BorderFactory.createTitledBorder("Search Option");

		fo = new Font("Arial", 1, 11);
		fo2 = new Font("Verdana", 1, 10);

		tk = java.awt.Toolkit.getDefaultToolkit();
		ss = tk.getScreenSize();
		fs = f.getSize();

		dat = new java.util.Date();
		formatter = new java.text.SimpleDateFormat("yyyy");
		formatter1 = new java.text.SimpleDateFormat("dd/MM/yyyy");

		try {
			datelabel.setText(String.valueOf(formatter1.format(dat)));
			dateString = formatter.format(dat);
		} catch (IllegalArgumentException localIllegalArgumentException) {
			System.out.println(localIllegalArgumentException.getMessage());
		}

		skr = new ColoredTableCellRenderer();
		skr2 = new ColoredTableCellRenderer2();
	}

	public JPanel DesignFrame() {
		java.awt.Container localContainer = f.getContentPane();
		localContainer.setLayout(new java.awt.BorderLayout());
		mainPanel.setLayout(new java.awt.BorderLayout());
		p1.setLayout(new java.awt.GridLayout(4, 2, 2, 2));
		p2.setLayout(new java.awt.BorderLayout());
		buttonpanel.setLayout(new java.awt.FlowLayout(0));
		southpanel.setLayout(new java.awt.FlowLayout(0));

		yearcb.addItem("2007");
		yearcb.addItem("2008");
		yearcb.addItem("2009");
		yearcb.addItem("2010");
		yearcb.addItem("2011");
		yearcb.addItem("2012");
		yearcb.addItem("2013");
		yearcb.addItem("2014");
		yearcb.addItem("2015");
		yearcb.addItem("2016");
		yearcb.addItem("2017");
		yearcb.addItem("2018");
		yearcb.addItem("2019");
		yearcb.addItem("2020");
		yearcb.addItem("2021");
		yearcb.addItem("2022");
		yearcb.addItem("2023");
		yearcb.addItem("2024");
		yearcb.addItem("2025");
		yearcb.addItem("2026");
		yearcb.addItem("2027");
		yearcb.addItem("2028");
		yearcb.addItem("2029");
		yearcb.addItem("2030");
		yearcb.addItem("2031");
		yearcb.addItem("2032");
		yearcb.addItem("2033");
		yearcb.addItem("2034");
		yearcb.addItem("2035");
		yearcb.addItem("2036");
		yearcb.addItem("2037");
		yearcb.addItem("2038");
		yearcb.addItem("2039");
		yearcb.addItem("2040");
		

		yearcb.setSelectedItem(dateString);

		fromcb.addItem("January");
		fromcb.addItem("February");
		fromcb.addItem("March");
		fromcb.addItem("April");
		fromcb.addItem("May");
		fromcb.addItem("June");
		fromcb.addItem("July");
		fromcb.addItem("August");
		fromcb.addItem("September");
		fromcb.addItem("October");
		fromcb.addItem("November");
		fromcb.addItem("December");

		tocb.addItem("January");
		tocb.addItem("February");
		tocb.addItem("March");
		tocb.addItem("April");
		tocb.addItem("May");
		tocb.addItem("June");
		tocb.addItem("July");
		tocb.addItem("August");
		tocb.addItem("September");
		tocb.addItem("October");
		tocb.addItem("November");
		tocb.addItem("December");

		leavetypecb.addItem("ALL");
		leavetypecb.addItem("CL");
		leavetypecb.addItem("EL");
		leavetypecb.addItem("LWP");

		desiglabel.setFont(fo2);
		leavetypelabel.setFont(fo2);
		yearlabel.setFont(fo2);
		fromlabel.setFont(fo2);
		tolabel.setFont(fo2);

		desigcb.setFont(new Font("Tahoma", 1, 9));
		leavetypecb.setFont(new Font("Tahoma", 1, 9));
		yearcb.setFont(new Font("Tahoma", 1, 9));
		fromcb.setFont(fo2);
		tocb.setFont(fo2);

		buttonpanel.add(desiglabel);
		buttonpanel.add(desigcb);
		buttonpanel.add(leavetypelabel);
		buttonpanel.add(leavetypecb);
		buttonpanel.add(yearlabel);
		buttonpanel.add(yearcb);
		buttonpanel.add(fromlabel);
		buttonpanel.add(fromcb);
		buttonpanel.add(tolabel);
		buttonpanel.add(tocb);
		buttonpanel.add(addbut);
		buttonpanel.add(printbut);

		addbut.setBackground(Color.white);
		printbut.setBackground(new Color(120, 200, 120));
		printbut.setForeground(Color.white);
		desigcb.setBackground(Color.white);
		leavetypecb.setBackground(Color.white);
		yearcb.setBackground(Color.white);
		fromcb.setBackground(Color.white);
		tocb.setBackground(Color.white);

		addbut.setFont(fo2);
		printbut.setFont(fo2);

		SolTableModel.decorateTable(tb);
		tb.setAutoResizeMode(0);
		tb.setShowGrid(false);
		tb.setIntercellSpacing(new Dimension(1, 1));
		tb.getTableHeader().setPreferredSize(new Dimension(30, 30));
		tb.setFont(new Font("Tahoma", 1, 10));
		tb.setAutoCreateRowSorter(true);

		tb.setRowHeight(25);
		tb.getTableHeader().setBackground(new Color(200, 120, 200));
		tb.getTableHeader().setForeground(Color.white);

		col.setPreferredWidth(60);
		col1.setPreferredWidth(140);
		col2.setPreferredWidth(210);
		col3.setPreferredWidth(50);
		col4.setPreferredWidth(170);

		tb.getColumnModel().getColumn(0).setPreferredWidth(70);
		tb.getColumnModel().getColumn(1).setPreferredWidth(65);
		tb.getColumnModel().getColumn(2).setPreferredWidth(115);
		tb.getColumnModel().getColumn(3).setPreferredWidth(160);
		tb.getColumnModel().getColumn(4).setPreferredWidth(40);

		tb.getTableHeader().setToolTipText("<Html><Font Color='red' Size='13'>Click Me to Sort the Table");

		for (int i = 0; i < tb.getColumnCount(); i++) {
			tb.getColumnModel().getColumn(i).setCellRenderer(skr);
		}

		southpanel.add(datelabel);

		searchtf.addActionListener(this);
		b5.addActionListener(this);

		tb.addMouseListener(this);
		model.addTableModelListener(this);

		printbut.addActionListener(this);
		searchtf.addKeyListener(this);
		b4.addKeyListener(this);
		b5.addKeyListener(this);
		rb1.addKeyListener(this);
		rb2.addKeyListener(this);
		rb3.addKeyListener(this);
		tb.addKeyListener(this);

		addbut.addActionListener(this);
		savebut.addActionListener(this);

		p2.add(buttonpanel, "North");
		p2.add(sp, "Center");
		p2.add(southpanel, "South");

		mainPanel.add(p2, "Center");
		localContainer.add(mainPanel, "Center");

		buttonpanel.setBorder(bor1);

		f.setTitle("Employee List");
		desigList();
		empList();

		return mainPanel;
	}

	public void showEmpList() {
		DesignFrame();
		f.setLocation((ss.width - fs.width) / 10, (ss.height - fs.height) / 8);
		f.setSize(810, 530);
		f.setVisible(true);
	}

	public void desigList() {
		desigcb.removeAllItems();

		try {
			con = DBConnectionUtil.getConnection();
			stat = con.createStatement();
			rs = stat.executeQuery("select distinct(designation) from emp_status ");
			rs.getMetaData().getColumnCount();
			while (rs.next()) {
				String str = new String(rs.getString(1));
				desigcb.addItem(str.toUpperCase());
			}
			desigcb.addItem("ALL");
		} catch (Exception localException) {
			javax.swing.JOptionPane.showMessageDialog(f, localException.getMessage().toString());
		}
	}

	public void empList() {
		model.setNumRows(0);

		String str1 = searchtf.getText() + "%";
		String str3 = (String) desigcb.getSelectedItem();

		if (str3.equalsIgnoreCase("All")) {
			str3 = "%";
		} else {
			str3 = "%" + (String) desigcb.getSelectedItem();
		}

		java.sql.Date localDate = SolDateFormatter.DDMMYYtoSQL(datelabel.getText());

		try {
			con = DBConnectionUtil.getConnection();
			PreparedStatement localPreparedStatement = con.prepareStatement(
					"select p.punchcard_no, s.emp_code, s.designation, s.emp_name, s.average_hrs from emp_status s, punchcard p WHERE s.EMP_CODE like '"
							+ str1
							+ "' and s.emp_code not in(select emp_code from HR_EX_EMP where releave_date < ?) and s.DESIGNATION like '"
							+ str3 + "' and s.emp_code=p.emp_code group by s.emp_code order by p.punchcard_no");
			localPreparedStatement.setDate(1, localDate);
			ResultSet localResultSet = localPreparedStatement.executeQuery();
			localResultSet.getMetaData().getColumnCount();
			localResultSet.getRow();
			while (localResultSet.next()) {
				String str4 = new String(localResultSet.getString(1));
				String str5 = new String(localResultSet.getString(2));
				String str6 = new String(localResultSet.getString(3));
				String str7 = new String(localResultSet.getString(4));
				String str8 = new String(localResultSet.getString(5));

				model.addRow(new Object[] { str4, str5.toUpperCase(), str6.toUpperCase(), str7.toUpperCase(), str8 });
			}
			model.addRow(
					new Object[] { "TOTAL: " + String.valueOf(tb.getRowCount()), "", "", "COMPANY TOTAL & AVG.", "0" });
		} catch (Exception localException) {
			javax.swing.JOptionPane.showMessageDialog(f, localException.getMessage().toString());
		}
	}

	public void createSheet() {
		model.setColumnCount(0);
		tb.setModel(model = new DefaultTableModel(data, head));
		empList();

		int i = fromcb.getSelectedIndex();
		int j = tocb.getSelectedIndex();
		if (j < i) {
			javax.swing.JOptionPane.showMessageDialog(f, "ToMonth should always be greater than FromMonth.");
		} else {
			int k = j - i;
			int m = 4 + k;

			int n = fromcb.getSelectedIndex();

			for (int i1 = 4; i1 <= m; i1++) {
				model.addColumn(String.valueOf(n + i1 - 4 + 1) + ". ( " + fromcb.getItemAt(n + i1 - 4) + " )");
				tb.getColumnModel().getColumn(i1).setPreferredWidth(90);
			}
			model.addColumn("AVG.");
			tb.getColumnModel().getColumn(tb.getColumnCount() - 1).setPreferredWidth(60);
		}
	}

	public class Report extends Thread {
		public Report() {
		}

		public void run() {
			for (int i = 0; i < tb.getRowCount(); i++) {
				float f1 = 0.0F;
				float f2 = 0.0F;
				for (int j = 5; j < tb.getColumnCount() - 1; j++) {
					String str1 = tb.getColumnName(j);
					int m = Integer.parseInt(str1.substring(0, str1.indexOf(".")));

					String str2 = "01/" + String.valueOf(monthformat.format(m)) + "/"
							+ yearcb.getSelectedItem();
					int n = DateCalculationUtil.getNumberOfDays(str2);
					String str3 = String.valueOf(n) + "/"
							+ String.valueOf(monthformat.format(m)) + "/"
							+ yearcb.getSelectedItem();

					java.sql.Date localDate1 = SolDateFormatter.DDMMYYtoSQL(str2);
					java.sql.Date localDate2 = SolDateFormatter.DDMMYYtoSQL(str3);

					try {
						Thread.sleep(30L);
					} catch (InterruptedException localInterruptedException) {
						System.out.println(localInterruptedException);
					}
					String str4 = String.valueOf(model.getValueAt(i, 1));
					String str5 = "%" + String.valueOf(leavetypecb.getSelectedItem());

					if (str5.equalsIgnoreCase("%ALL")) {
						str5 = "%";
					}

					try {
						con = DBConnectionUtil.getConnection();
						PreparedStatement localPreparedStatement = con
								.prepareStatement("select COUNT(DATE) from HREMP_LEAVES WHERE emp_code='" + str4
										+ "' and date between ? and ? and CATEGORY Like ? ");
						localPreparedStatement.setDate(1, localDate1);
						localPreparedStatement.setDate(2, localDate2);
						localPreparedStatement.setString(3, str5);
						ResultSet localResultSet = localPreparedStatement.executeQuery();

						while (localResultSet.next()) {
							int i2 = localResultSet.getInt(1);
							model.setValueAt(df.format(i2), i, j);
						}
					} catch (java.sql.SQLException localSQLException) {
						System.out.println("Taken Leave : " + localSQLException);
					}
					int i1 = Integer.parseInt(String.valueOf(model.getValueAt(i, j)));
					f2 += i1;

					float f3 = tb.getColumnCount() - 6;
					f1 = f2 / f3;
					model.setValueAt(df2.format(f1), i,
							tb.getColumnCount() - 1);
				}
			}

			companyAVG();
		}
	}

	public void companyAVG() {
		for (int i = 4; i < tb.getColumnCount(); i++) {
			float f1 = 0.0F;
			for (int j = 0; j < tb.getRowCount(); j++) {
				float f2 = Float.parseFloat(String.valueOf(model.getValueAt(j, i)));
				f1 += f2;
			}
			int j = tb.getRowCount() - 1;
			float f2 = f1 / j;
			System.out.println("TOTAL OT : " + f1 + "\t Total Days :" + j + "\t AVG. :" + f2);

			model.setValueAt(String.valueOf(df2.format(f2)), tb.getRowCount() - 1, i);
		}
	}

	public void renameHeaderForMonth() {
		for (int i = 4; i < tb.getColumnCount() - 1; i++) {
		}
	}

	public class myPrint extends Thread implements Runnable {
		public myPrint() {
		}

		public void run() {
			String str = "Leave Application Chart";

			try {
				java.text.MessageFormat localMessageFormat1 = new java.text.MessageFormat(str);
				java.text.MessageFormat localMessageFormat2 = new java.text.MessageFormat(
						datelabel.getText());
				tb.print(javax.swing.JTable.PrintMode.FIT_WIDTH, localMessageFormat1,
						localMessageFormat2);
			} catch (java.awt.print.PrinterException localPrinterException) {
				System.err.println("Error printing: " + localPrinterException.getMessage());
			}
		}
	}

	public void actionPerformed(java.awt.event.ActionEvent paramActionEvent) {
		if (paramActionEvent.getSource() == printbut) {
			myPrint localmyPrint = new myPrint();
			localmyPrint.start();
		}
		if (paramActionEvent.getSource() == b4) {
			empList();
		}
		if (paramActionEvent.getSource() == addbut) {
			createSheet();
			for (int i = 0; i < tb.getColumnCount(); i++) {
				tb.getColumnModel().getColumn(i).setCellRenderer(skr);
			}
			for (int i = 5; i < tb.getColumnCount(); i++) {
				tb.getColumnModel().getColumn(i).setCellRenderer(skr2);
			}
			tb.getColumnModel().getColumn(0).setPreferredWidth(70);
			tb.getColumnModel().getColumn(1).setPreferredWidth(65);
			tb.getColumnModel().getColumn(2).setPreferredWidth(115);
			tb.getColumnModel().getColumn(3).setPreferredWidth(160);
			tb.getColumnModel().getColumn(4).setPreferredWidth(40);

			Report localReport = new Report();
			localReport.start();
		}

		if ((paramActionEvent.getSource() != savebut) || (

		(paramActionEvent.getSource() == b5) || (paramActionEvent.getSource() == searchtf))) {
			empList();
		}
	}

	public void keyPressed(java.awt.event.KeyEvent paramKeyEvent) {
		int i = paramKeyEvent.getKeyCode();

		if (i == 27) {
			f.setVisible(false);
		}
	}

	public void keyReleased(java.awt.event.KeyEvent paramKeyEvent) {
	}

	public void keyTyped(java.awt.event.KeyEvent paramKeyEvent) {
	}

	public void mousePressed(MouseEvent paramMouseEvent) {
	}

	public void mouseReleased(MouseEvent paramMouseEvent) {
	}

	public void mouseClicked(MouseEvent paramMouseEvent) {
		for (int i = 5; i < tb.getColumnCount(); i++) {
			tb.getColumnModel().getColumn(i).setCellRenderer(skr2);
		}
	}

	public void mouseExited(MouseEvent paramMouseEvent) {
	}

	public void mouseEntered(MouseEvent paramMouseEvent) {
	}

	public void tableChanged(javax.swing.event.TableModelEvent paramTableModelEvent) {
		tb.setForeground(Color.blue);
	}

}
