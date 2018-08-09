package com.sol.erp;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
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
import com.sol.erp.util.ApplicationUtil;
import com.sol.erp.util.DBConnectionUtil;
import com.sol.erp.util.DateCalculationUtil;
import com.sol.erp.util.formater.SolDateFormatter;

public class EmpMonthlyHRS implements java.awt.event.ActionListener, java.awt.event.MouseListener,
		javax.swing.event.TableModelListener, java.awt.event.KeyListener {
    

	public static String codestr;
	public static String[] head = { "Punchcard", "EMP Code", "Designation", "Name", "Avg." };
	public static Object[][] data = new Object[0][];

	DecimalFormat df;

	DecimalFormat monthformat;

	JFrame f;

	JLabel desiglabel;

	JLabel yearlabel;

	JLabel fromlabel;

	JLabel tolabel;

	JLabel datelabel;

	JComboBox desigcb;

	JComboBox yearcb;
	JComboBox fromcb;
	JComboBox tocb;
	JButton b4;
	JButton b5;
	JButton addbut;
	JButton savebut;
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
	java.awt.Font fo;
	java.awt.Font fo2;
	java.awt.Toolkit tk;
	Dimension ss;
	Dimension fs;
	java.util.Date dat;
	java.text.SimpleDateFormat formatter;
	java.text.SimpleDateFormat formatter1;
	String dateString;
	EmpMonthlyHRS.ColoredTableCellRenderer skr;
	EmpMonthlyHRS.ColoredTableCellRenderer2 skr2;

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

			if ((paramInt1 > 1) && (paramInt1 == EmpMonthlyHRS.tb.getRowCount() - 1)) {
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
				String str = String.valueOf(EmpMonthlyHRS.model.getValueAt(paramInt1, 4));
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
				setBackground(Color.darkGray);
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

			if ((paramInt1 > 1) && (paramInt1 == EmpMonthlyHRS.tb.getRowCount() - 1)) {
				setBackground(Color.darkGray);
				setForeground(Color.white);
			}

			if ((paramInt2 > 4) && (paramObject != null)) {
				String valueString = String.valueOf(EmpMonthlyHRS.model.getValueAt(paramInt1, paramInt2));
				float cellValue = Float.parseFloat(valueString);

				setBackground(new Color(240, 240, 240));
				setForeground(Color.darkGray);
				if (cellValue <= 230.0F) {
					setBackground(new Color(250, 160, 200));
					setForeground(Color.black);
				}
				if (cellValue > 0.0F && cellValue < 190.0F) {
					setBackground(new Color(250, 160, 200));
					setForeground(Color.black);
				}
			}

			super.getTableCellRendererComponent(paramJTable, paramObject, paramBoolean1, paramBoolean2, paramInt1,
					paramInt2);

			return this;
		}
	}

	public EmpMonthlyHRS() {

		df = new DecimalFormat("0");
		monthformat = new DecimalFormat("00");

		f = new JFrame();

		desiglabel = new JLabel("DESIGNATION LIST");
		yearlabel = new JLabel("SELECT YEAR");
		fromlabel = new JLabel("FROM MONTH");
		tolabel = new JLabel("TO MONTH");
		datelabel = new JLabel("Today : ");

		desigcb = new JComboBox();
		yearcb = new JComboBox();
		fromcb = new JComboBox();
		tocb = new JComboBox();

		b4 = new JButton("Show All");
		b5 = new JButton("Search");

		addbut = new JButton("Generate Report >>");
		savebut = new JButton("Save Changes");

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

		fo = new java.awt.Font("Arial", 1, 11);
		fo2 = new java.awt.Font("Verdana", 1, 10);

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

		skr = new EmpMonthlyHRS.ColoredTableCellRenderer();
		skr2 = new EmpMonthlyHRS.ColoredTableCellRenderer2();

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

		fromcb.setMaximumRowCount(12);
		tocb.setMaximumRowCount(12);

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

		desiglabel.setFont(fo2);
		yearlabel.setFont(fo2);
		fromlabel.setFont(fo2);
		tolabel.setFont(fo2);

		desigcb.setFont(new java.awt.Font("Tahoma", 1, 9));
		yearcb.setFont(new java.awt.Font("Tahoma", 1, 9));
		fromcb.setFont(fo2);
		tocb.setFont(fo2);

		buttonpanel.add(desiglabel);
		buttonpanel.add(desigcb);
		buttonpanel.add(yearlabel);
		buttonpanel.add(yearcb);
		buttonpanel.add(fromlabel);
		buttonpanel.add(fromcb);
		buttonpanel.add(tolabel);
		buttonpanel.add(tocb);
		buttonpanel.add(addbut);

		addbut.setBackground(Color.white);
		desigcb.setBackground(Color.white);
		yearcb.setBackground(Color.white);
		fromcb.setBackground(Color.white);
		tocb.setBackground(Color.white);

		addbut.setFont(fo2);

		SolTableModel.decorateTable(tb);
		tb.setAutoResizeMode(0);
		tb.setShowGrid(false);
		tb.setIntercellSpacing(new Dimension(1, 1));
		tb.getTableHeader().setPreferredSize(new Dimension(30, 30));
		tb.setRowHeight(25);
		tb.setFont(new java.awt.Font("Tahoma", 1, 10));
		tb.setAutoCreateRowSorter(true);

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

		Connection con = null;
		Statement stat = null;
		ResultSet rs = null;
		
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
		} finally{
			DBConnectionUtil.closeConnection(rs, stat, con);
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

		Connection con = null;
		PreparedStatement stat = null;
		ResultSet rs = null;
		try {
			con = DBConnectionUtil.getConnection();
			stat = con.prepareStatement(
					"select p.punchcard_no, s.emp_code, s.designation, s.emp_name, s.average_hrs from emp_status s, punchcard p WHERE s.EMP_CODE like '"
							+ str1
							+ "' and s.emp_code not in(select emp_code from HR_EX_EMP where releave_date < ?) and s.DESIGNATION like '"
							+ str3 + "' and s.emp_code=p.emp_code group by s.emp_code order by p.punchcard_no");
			stat.setDate(1, localDate);
			rs = stat.executeQuery();
			rs.getMetaData().getColumnCount();
			rs.getRow();
			while (rs.next()) {
				String str4 = new String(rs.getString(1));
				String str5 = new String(rs.getString(2));
				String str6 = new String(rs.getString(3));
				String str7 = new String(rs.getString(4));
				String str8 = new String(rs.getString(5));

				model.addRow(new Object[] { str4, str5.toUpperCase(), str6.toUpperCase(), str7.toUpperCase(), str8 });
			}
			model.addRow(new Object[] { "TOTAL: " + String.valueOf(tb.getRowCount()), "---", "", "COMPANY TOTAL & AVG.",
					"0" });
		} catch (Exception localException) {
			javax.swing.JOptionPane.showMessageDialog(f, localException.getMessage().toString());
		} finally {
			DBConnectionUtil.closeConnection(rs, stat, con);
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
				model.addColumn(fromcb.getItemAt(n + i1 - 4));
				tb.getColumnModel().getColumn(i1).setPreferredWidth(90);
			}
			model.addColumn("AVG.");
			tb.getColumnModel().getColumn(tb.getColumnCount() - 1).setPreferredWidth(60);
		}
	}

	public class Report extends Thread {

		public Report() {
		}

		boolean isThreadCompleted = false;

		Connection con = null;
		PreparedStatement stat = null;
		ResultSet rs = null;
		
		public void run() {

			try {
				con = DBConnectionUtil.getConnection();
				
				Thread.sleep(1000);
				
				for (int i = 0; i < EmpMonthlyHRS.tb.getRowCount() - 1; i++) {
					float j = 0;
					float f1 = 0;

					String empCode = String.valueOf(model.getValueAt(i, 1));
					java.sql.Date localDate1 = SolDateFormatter.DDMMYYtoSQL(EmpTB.getDOJ(empCode));
					float f2 = 0;
					float f3 = 0;
					int m = 0;
					int n = tocb.getSelectedIndex() - fromcb.getSelectedIndex();
					int i1 = 0;
					for (; m <= n; m++) {
						int i2 = 0;
						String str2 = EmpMonthlyHRS.tb.getColumnName(i1 + 5);
						int monthIndex = ApplicationUtil.getMonthList().indexOf(str2) + 1;

						String str4 = "01/" + String.valueOf(monthformat.format(monthIndex)) + "/"
								+ yearcb.getSelectedItem();
						int i5 = DateCalculationUtil.getNumberOfDays(str4);
						String str5 = String.valueOf(i5) + "/" + String.valueOf(monthformat.format(monthIndex)) + "/"
								+ yearcb.getSelectedItem();

						java.sql.Date localDate2 = SolDateFormatter.DDMMYYtoSQL(str4);
						java.sql.Date localDate3 = SolDateFormatter.DDMMYYtoSQL(str5);

						System.out.println(empCode + " - " + str4 + "\t" + str5);

						
						stat = con
								.prepareStatement("select TOTAL_TIME from HRTIMEMASTER WHERE emp_code='" + empCode
										+ "' and date between ? and ? and OT_REMARKS = ? and M_STATUS not Like ? and deduct_remarks= ?  ");
						stat.setDate(1, localDate2);
						stat.setDate(2, localDate3);
						stat.setString(3, "true");
						stat.setString(4, "PM");
						stat.setString(5, "true");
						rs = stat.executeQuery();

						while (rs.next()) {
							f3 = Float.parseFloat(rs.getString(1));
							int i8 = SolDateFormatter.convertHrsToMinute(String.valueOf(f3));
							i2 += i8;
						}

						f3 = Float.parseFloat(SolDateFormatter.convertMinuteToHRS(i2));
						EmpMonthlyHRS.model.setValueAt(df.format(f3), i, i1 + 5);

						int i6 = localDate2.compareTo(localDate1);

						int i7 = Integer.parseInt(String.valueOf(EmpMonthlyHRS.model.getValueAt(i, i1 + 5)));

						if (i6 == 1) {
							f1 += i7;
							f2 += 1;
						}

						if (i6 == -1) {
							model.setValueAt("0", i, i1 + 5);
						}

						float f31 = f2;
						if (f31 > 0) {
							j = f1 / f31;
						}
						model.setValueAt(df.format(j), i, tb.getColumnCount() - 1);
						i1 += 1;
					}
					isThreadCompleted = true;
				}
			} catch (Exception ex) {
				System.out.println(ex);
			} finally{
				DBConnectionUtil.closeConnection(rs, stat, con);
			}

			if (isThreadCompleted) {
				companyAVG();
			}
		}
	}

	public void companyAVG() {
		for (int i = 4; i < tb.getColumnCount(); i++) {
			int j = 0;
			for (int k = 0; k < tb.getRowCount() - 1; k++) {
				int m = Integer.parseInt(String.valueOf(model.getValueAt(k, i)));
				j += m;
			}
			int k = tb.getRowCount() - 1;
			float f1 = j / k;

			model.setValueAt(String.valueOf(df.format(f1)), tb.getRowCount() - 1, i);
		}

		System.out.println("Process completed!");
	}

	public void actionPerformed(java.awt.event.ActionEvent paramActionEvent) {
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

			EmpMonthlyHRS.Report localReport = new EmpMonthlyHRS.Report();
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
