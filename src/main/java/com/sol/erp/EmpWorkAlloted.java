package com.sol.erp;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.sol.erp.dao.ProjectDAO;
import com.sol.erp.ui.custom.SolCalendar;
import com.sol.erp.ui.custom.SolTableModel;
import com.sol.erp.util.DBConnectionUtil;
import com.sol.erp.util.formater.SolDateFormatter;

public class EmpWorkAlloted
		implements java.awt.event.ActionListener, java.awt.event.MouseListener, java.awt.event.ItemListener {
	
	
	
	Connection con;
	Statement stat;
	ResultSet rs;
	static String[] columnNames = { "Day", "Date", "D/C", "Project No", "Drawing Nos", "OT Status" };
	static Object[][] data = new Object[0][];

	int countdays;

	DecimalFormat df;

	DecimalFormat df3;

	DecimalFormat df4;
	static DefaultTableModel model = new DefaultTableModel(data, columnNames);
	static JTable tb = new JTable(model);
	static javax.swing.JScrollPane sp = new javax.swing.JScrollPane(tb);

	JInternalFrame f;

	JLabel l;

	JLabel l1;
	JLabel l2;
	JLabel l3;
	JLabel l4;
	JComboBox tf;
	static JComboBox tf1 = new JComboBox();

	JTextField tf2;

	static JTextField tf3;

	static JTextField tf4;

	JLabel statusl1;
	JLabel statusl2;
	JLabel statusl3;
	JLabel statusl4;
	JLabel statusl5;
	JTextField statustf2;
	JTextField statustf3;
	JTextField statustf4;
	JTextField statustf5;
	JTextField statustf6;
	JTextField statustf7;
	JButton showbut;
	JButton closebut;
	static JButton insertbut = new JButton("Insert");

	javax.swing.JPopupMenu jpm;

	javax.swing.JMenuItem popm;
	javax.swing.JMenuItem popm1;
	JPanel northp1;
	JPanel northp2;
	JPanel northp3;
	JPanel butpanel1;
	JPanel butpanel2;
	JPanel statuspanel;
	JPanel centerpanel;
	JPanel northpanel;
	java.util.Date dat;
	java.text.SimpleDateFormat formatter;
	String dateString;
	java.awt.Font fo;
	javax.swing.border.Border line;
	javax.swing.border.Border bor1;
	javax.swing.border.Border bor2;
	java.awt.Toolkit tk;
	java.awt.Dimension ss;
	java.awt.Dimension fs;
	EmpWorkAlloted.ColoredTableCellRenderer ctr;
	EmpWorkAlloted.ColoredTableCellRenderer2 ctr2;
	EmpWorkAlloted.ColoredTableCellRenderer3 ctr3;

	java.awt.Container c;
	java.util.Calendar gcal;
	String[] days;
	String query;
	ArrayList arl1;
	ArrayList<?> arl2;
	ArrayList arl3;
	ArrayList<?> arl4;
	DailyTimeSheet dts;

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
				setBackground(Color.white);
			} else {
				setBackground(new Color(220, 220, 230));
			}

			if ((paramInt2 >= 0) && (paramInt2 < 4)) {
				setHorizontalAlignment(0);
			}

			if (paramInt2 == 4) {
				setHorizontalAlignment(2);
			}

			if (paramInt2 == 3) {
				setBackground(new Color(100, 100, 250));
				setForeground(Color.blue);
			}

			String str = String.valueOf(EmpWorkAlloted.model.getValueAt(paramInt1, 0));
			if (str.equalsIgnoreCase("SUN")) {
				setBackground(Color.orange);
				setForeground(Color.black);
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

			if ((paramInt2 >= 0) && (paramInt2 < 4)) {
				setHorizontalAlignment(0);
			}

			if (paramInt2 == 4) {
				setHorizontalAlignment(2);
			}

			String str2 = String.valueOf(EmpWorkAlloted.model.getValueAt(paramInt1, 2));
			if (str2.equalsIgnoreCase("0")) {
				setBackground(Color.white);
				setForeground(Color.darkGray);
			}
			if (str2.equalsIgnoreCase("Detailing")) {
				setBackground(new Color(230, 230, 230));
				setForeground(Color.darkGray);
			}
			if (str2.equalsIgnoreCase("Checking")) {
				setBackground(new Color(200, 200, 200));
				setForeground(Color.darkGray);
			}

			if (paramInt2 == 3) {
				setBackground(new Color(100, 100, 250));
				setForeground(Color.white);
			}

			super.getTableCellRendererComponent(paramJTable, paramObject, paramBoolean1, paramBoolean2, paramInt1,
					paramInt2);

			return this;
		}
	}

	class ColoredTableCellRenderer3 extends javax.swing.table.DefaultTableCellRenderer {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		ColoredTableCellRenderer3() {
		}

		public java.awt.Component getTableCellRendererComponent(JTable paramJTable, Object paramObject,
				boolean paramBoolean1, boolean paramBoolean2, int paramInt1, int paramInt2) {
			setEnabled((paramJTable == null) || (paramJTable.isEnabled()));

			if ((paramInt2 >= 0) && (paramInt2 < 4)) {
				setHorizontalAlignment(0);
			}

			if (paramInt2 == 4) {
				setHorizontalAlignment(2);
			}

			String str1 = String.valueOf(EmpWorkAlloted.model.getValueAt(paramInt1, 5));
			String str2 = String.valueOf(EmpWorkAlloted.model.getValueAt(paramInt1, 2));

			if (str2.equalsIgnoreCase("0")) {
				setBackground(Color.white);
				setForeground(Color.darkGray);
			}
			if (str2.equalsIgnoreCase("Detailing")) {
				setBackground(new Color(230, 230, 230));
				setForeground(Color.darkGray);
			}
			if (str2.equalsIgnoreCase("Checking")) {
				setBackground(new Color(200, 200, 200));
				setForeground(Color.darkGray);
			}

			if (str1.equalsIgnoreCase("OK")) {
				setBackground(Color.red);
				setForeground(Color.white);
			}
			if (str1.equalsIgnoreCase("NO")) {
				setBackground(new Color(120, 120, 180));
				setForeground(Color.white);
			}
			if (str1.equalsIgnoreCase("0")) {
				setBackground(Color.white);
				setForeground(Color.darkGray);
			}
			super.getTableCellRendererComponent(paramJTable, paramObject, paramBoolean1, paramBoolean2, paramInt1,
					paramInt2);

			return this;
		}
	}

	public void px(String paramString) {
		c = f.getContentPane();
		c.setLayout(new java.awt.BorderLayout());
		northp1.setLayout(new java.awt.FlowLayout());
		northp2.setLayout(new java.awt.GridLayout(2, 2, 2, 2));
		northpanel.setLayout(new java.awt.BorderLayout());
		butpanel1.setLayout(new java.awt.FlowLayout());
		butpanel2.setLayout(new java.awt.FlowLayout());
		centerpanel.setLayout(new java.awt.BorderLayout());

		SolTableModel.decorateTable(tb);

		tb.setIntercellSpacing(new java.awt.Dimension(1, 1));

		tb.getColumnModel().getColumn(0).setCellRenderer(ctr2);
		tb.getColumnModel().getColumn(1).setCellRenderer(ctr2);
		tb.getColumnModel().getColumn(2).setCellRenderer(ctr2);
		tb.getColumnModel().getColumn(3).setCellRenderer(ctr2);
		tb.getColumnModel().getColumn(4).setCellRenderer(ctr2);

		tb.getColumnModel().getColumn(0).setCellRenderer(ctr);
		tb.getColumnModel().getColumn(1).setCellRenderer(ctr3);
		tb.getColumnModel().getColumn(5).setCellRenderer(ctr3);

		tb.setAutoResizeMode(0);

		tb.getColumnModel().getColumn(1).setPreferredWidth(120);
		tb.getColumnModel().getColumn(4).setPreferredWidth(460);

		northpanel.setBorder(bor1);
		sp.setBorder(bor2);

		northp1.add(l);
		northp1.add(tf);
		tf1.setFont(fo);
		tf1.setPreferredSize(new java.awt.Dimension(60, 20));
		northp1.add(l1);
		northp1.add(tf1);
		tf1.setFont(fo);
		northp1.add(l2);
		northp1.add(tf2);
		tf2.setFont(fo);
		northp1.add(l3);
		northp1.add(tf3);
		tf3.setFont(fo);
		northp1.add(l4);
		northp1.add(tf4);
		tf4.setFont(fo);
		northp1.add(showbut);
		showbut.setMnemonic(83);

		northp1.add(insertbut);
		closebut.setMnemonic(73);
		insertbut.setEnabled(false);

		butpanel2.add(statusl1);
		statuspanel.add(statusl2);
		statuspanel.add(statustf2);
		statuspanel.add(statustf3);
		statuspanel.add(statusl3);
		statuspanel.add(statustf4);
		statuspanel.add(statustf5);
		statuspanel.add(statusl4);
		statuspanel.add(statustf6);
		statuspanel.add(statustf7);

		statusl1.setForeground(Color.darkGray);
		statusl1.setFont(fo);
		statusl2.setForeground(Color.darkGray);
		statusl2.setFont(fo);
		statusl3.setForeground(Color.darkGray);
		statusl3.setFont(fo);
		statusl4.setForeground(Color.darkGray);
		statusl4.setFont(fo);
		statusl5.setForeground(Color.darkGray);
		statusl5.setFont(fo);

		statustf2.setFont(fo);
		statustf2.setEditable(false);
		statustf3.setFont(fo);
		statustf3.setEditable(false);
		statustf4.setFont(fo);
		statustf4.setEditable(false);
		statustf5.setFont(fo);
		statustf5.setEditable(false);
		statustf6.setFont(fo);
		statustf6.setEditable(false);
		statustf7.setFont(fo);
		statustf7.setEditable(false);

		statustf2.setHorizontalAlignment(4);
		statustf3.setHorizontalAlignment(4);
		statustf4.setHorizontalAlignment(4);
		statustf5.setHorizontalAlignment(4);
		statustf6.setHorizontalAlignment(4);
		statustf7.setHorizontalAlignment(4);

		jpm.add(popm);
		jpm.add(popm1);

		tf.setEditable(false);
		tf1.setEditable(false);
		tf2.setEditable(false);

		tf.setEnabled(false);
		tf1.setEnabled(false);
		tf2.setEnabled(false);

		northpanel.add(northp1, "North");
		centerpanel.add(sp, "Center");
		centerpanel.add(butpanel2, "South");

		c.add(northpanel, "North");
		c.add(centerpanel, "Center");
		c.add(statuspanel, "South");
		statuspanel.setPreferredSize(new java.awt.Dimension(40, 40));

		popm.addActionListener(this);
		popm1.addActionListener(this);

		showbut.addActionListener(this);
		insertbut.addActionListener(this);
		closebut.addActionListener(this);
		tf.addItemListener(this);
		tf.addActionListener(this);
		tf1.addActionListener(this);
		tf2.addActionListener(this);
		tf3.addActionListener(this);
		tf4.addActionListener(this);

		tf3.addMouseListener(this);
		tf4.addMouseListener(this);

		tb.addMouseListener(this);

		f.setSize(930, 600);

		f.setVisible(true);
		tf4.requestFocus();
		tf4.selectAll();

		connect();
		paramUser(paramString);
	}

	public JComboBox getProjectComboBox() {
		return tf;
	}

	public JComboBox getEmpComboBox() {
		return tf1;
	}

	public JTextField getFromDateField() {
		return tf3;
	}

	public JTextField getToDateField() {
		return tf4;
	}

	public void paramUser(String paramString) {
		if (paramString.equalsIgnoreCase("true")) {
			tf1.setEditable(true);
			tf1.setSelectedItem(TechMainFrame.textFieldLoggedBy.getText());
			tf2.setText(TechMainFrame.textFieldPost.getText());
			if ((TechMainFrame.textFieldPost.getText().equalsIgnoreCase("Team Leader"))
					|| (TechMainFrame.textFieldPost.getText().equalsIgnoreCase("Project Manager"))
					|| (TechMainFrame.textFieldPost.getText().equalsIgnoreCase("CEO"))
					|| (TechMainFrame.textFieldPost.getText().equalsIgnoreCase("Admin"))) {
				tf.setEditable(true);

				tf.setEnabled(true);
				tf1.setEnabled(true);
				tf2.setEnabled(true);
			}
		}
		if (paramString.equalsIgnoreCase("false")) {
		}
	}

	public void dateDiff() {
		String str1 = tf3.getText();
		String str2 = str1.substring(0, 2);
		String str3 = str1.substring(3, 5);
		String str4 = str1.substring(7, 10);

		int i = Integer.parseInt(str2);
		int j = Integer.parseInt(str3);
		int k = Integer.parseInt(str4);

		String str5 = tf4.getText();
		String str6 = str5.substring(0, 2);
		String str7 = str5.substring(3, 5);
		String str8 = str5.substring(7, 10);

		int m = Integer.parseInt(str6);
		int n = Integer.parseInt(str7);
		int i1 = Integer.parseInt(str8);

		int i2 = 0;
		java.util.GregorianCalendar localGregorianCalendar1 = new java.util.GregorianCalendar(k, j, i);
		java.util.GregorianCalendar localGregorianCalendar2 = new java.util.GregorianCalendar(i1, n, m);
		while (localGregorianCalendar2.get(1) != localGregorianCalendar1.get(1)) {
			i2 += 360;
			localGregorianCalendar1.add(6, 360);
		}
		i2 += localGregorianCalendar2.get(6) - localGregorianCalendar1.get(6);
		countdays = (i2 + 1);
	}

	public void connect() {
		tf.removeAllItems();
		tf.addItem("Project No");
		try {
			con = DBConnectionUtil.getConnection();
			stat = con.createStatement();
			rs = stat.executeQuery("select distinct(project_no) from estimation where project_no > '2007' ");
			while (rs.next()) {
				String str = new String(rs.getString(1));
				tf.addItem(str);
			}
		} catch (Exception localException) {
			System.out.println(localException);
		}
	}

	public void getEmployee() {
		tf1.removeAllItems();
		try {
			con = DBConnectionUtil.getConnection();
			stat = con.createStatement();
			rs = stat.executeQuery("select distinct(emp_code)  from projectmanpower where project_no ='"
					+ tf.getSelectedItem() + "' ");
			while (rs.next()) {
				String str = new String(rs.getString(1));
				tf1.addItem(str.toUpperCase());
			}
		} catch (Exception localException) {
			System.out.println(localException);
		}
		getProjectDate();
	}

	public void getProjectDate() {
		try {
			PreparedStatement localPreparedStatement = con
					.prepareStatement("select START, NEW_FINAL_DATE from project_CO where project_no = ? ");
			localPreparedStatement.setInt(1, Integer.parseInt(String.valueOf(tf.getSelectedItem())));
			rs = localPreparedStatement.executeQuery();
			while (rs.next()) {
				String str1 = SolDateFormatter.SQLtoDDMMYY(rs.getDate(1));
				String str2 = SolDateFormatter.SQLtoDDMMYY(rs.getDate(2));
				tf3.setText(str1);
				tf4.setText(str2);
			}
		} catch (Exception localException) {
			System.out.println(localException);
		}
	}

	public void workDetails() {
		model.setNumRows(0);

		String str1 = tf3.getText();
		String str3 = str1.substring(0, 2);

		int i = Integer.parseInt(str3);

		String str4 = str1.substring(3, 5);

		String str5 = str1.substring(6, 10);

		int j = Integer.parseInt(str4);

		int k = Integer.parseInt(str5);

		int m = 0;
		int i1 = k % 4;

		String str6 = "/" + String.valueOf(df.format(j)) + "/" + String.valueOf(k);
		String str8 = " ";

		String str9 = "0";
		String str11 = str9;
		String str12 = "0";
		String str16 = "0";

		int i2 = countdays;

		for (int i3 = 0; i3 <= i2; i3++) {
			str8 = df.format(i) + str6;
			gcal = new java.util.GregorianCalendar(k, j - 1, i);
			String str17 = String.valueOf(days[(gcal.get(7) - 1)]);

			if (tf2.getText().equalsIgnoreCase("Checker")) {
				str9 = "Checking";
			}
			if (tf2.getText().equalsIgnoreCase("Detailer")) {
				str9 = "Detailing";
			}

			try {
				stat = con.createStatement();
				rs = stat
						.executeQuery("select DTL_CHK, PROJECT_NO, (SELECT REMARKS FROM OTSTATUS WHERE DATE='" + str8
								+ "' and emp_code='" + tf1.getSelectedItem()
								+ "')  FROM PROJECTMANPOWER  where emp_code='" + tf1.getSelectedItem()
								+ "' and FROM_date='" + str8 + "' ");
				rs.getMetaData().getColumnCount();
				while (rs.next()) {
					str11 = new String(rs.getString(1));
					str12 = new String(rs.getString(2));
					str16 = new String(rs.getString(3));
				}
			} catch (Exception localException) {
				System.out.println(localException);
			}
			model.addRow(new Object[] { str17, str8, str11, str12, "", str16 });
			str12 = "0";
			str9 = "0";
			str11 = "0";
			str16 = "0";

			if (j == 2) {
				if (i1 == 0) {
					m = 28;
				} else {
					m = 29;
				}
			}

			if ((j == 4) || (j == 6) || (j == 9) || (j == 11)) {
				m = 30;
			}
			if ((j == 1) || (j == 3) || (j == 5) || (j == 7) || (j == 8) || (j == 10) || (j == 12)) {
				m = 31;
			}

			if (i == m) {
				i = 0;
				if (j == 12) {
					j = 1;
					k += 1;
					str6 = "/" + String.valueOf(df.format(j)) + "/" + String.valueOf(k);
				} else {
					j += 1;
					str6 = "/" + String.valueOf(df.format(j)) + "/" + String.valueOf(k);
				}
			}
			i += 1;
		}
	}

	public void jobDetails() {
		String str2 = "0";
		String str3 = "0";
		Object localObject = "0";

		int i = tb.getRowCount();
		tb.getSelectedRow();

		System.out.println(i);

		int k = 0;
		String str4 = "<html><font color=red>";
		String str5 = "<html><font color=black>";

		for (int m = 0; m < i; m++) {
			String str6 = String.valueOf(model.getValueAt(m, 1));
			String str7 = String.valueOf(model.getValueAt(m, 2));
			String str8 = String.valueOf(model.getValueAt(m, 3));

			SolDateFormatter.DDMMYYtoSQL(str6);

			try {
				PreparedStatement localPreparedStatement = con.prepareStatement(
						"select Item_Code, Drawing_No  from " + str7 + " where project_no='" + str8 + "' and emp_code='"
								+ tf1.getSelectedItem() + "' and date_month= '" + str6 + "' ORDER BY ITEM_CODE ");

				ResultSet localResultSet1 = localPreparedStatement.executeQuery();

				String str9 = "";

				while (localResultSet1.next()) {
					k += 1;
					str9 = str2;
					str2 = new String(localResultSet1.getString(1));
					str3 = new String(localResultSet1.getString(2));

					System.out.println(str2 + "\t" + str3);

					try {
						Statement localStatement = con.createStatement();
						ResultSet localResultSet2 = localStatement.executeQuery(
								"select drg_no from timesheet where project_no='" + str8 + "' and item_code='" + str2
										+ "' and drg_no ='" + str3 + "' and DRGSTATUS1 LIKE 'CHECK' ");
						int n = 5;
						n = localResultSet2.getRow();
						System.out.println("Shekhar " + localResultSet2.getRow());

						while (localResultSet2.next()) {
							String str11 = new String(localResultSet2.getString(1));
							localObject = str11;
							System.out.println("SK : " + (String) localObject + "\tDBROW " + n);

							if (n == 0) {
								str3 = str4 + "" + str3 + "</FONT>";
							} else {
								str3 = str5 + "" + str3 + "</FONT></HTML>";
							}

						}

					} catch (Exception localException2) {
						System.out.println("SK Error : " + localException2);
					}

					String str10;

					if (k < 1) {
						str10 = String.valueOf(model.getValueAt(m, 4));
						model.setValueAt("<HTML>" + str2 + " = " + str3, m, 4);
						str2 = "";
					}
					if (k >= 1) {

						if (str2.equalsIgnoreCase(str9)) {
							str10 = String.valueOf(model.getValueAt(m, 4));
							model.setValueAt(str10 + ", " + str3, m, 4);
						} else {
							str10 = String.valueOf(model.getValueAt(m, 4));
							model.setValueAt(str10 + "<HTML>&nbsp &nbsp &nbsp &nbsp     " + str2 + " = " + str3 + "", m,
									4);
						}
					}
				}

				str3 = str5 + "" + str3 + "</FONT></HTML>";
			} catch (Exception localException1) {
				System.out.println("First For Loop " + localException1);
			}
			str3 = "0";
			str2 = "0";
			localObject = "0";
			k = 0;
		}
	}

	public void updateOT() {
		int i = tb.getSelectedRow();
		String.valueOf(model.getValueAt(i, 1));

		try {
			con = DBConnectionUtil.getConnection();
			stat = con.createStatement();
			stat.executeUpdate(query);
		} catch (Exception localException) {
			System.out.println(localException);
		}
	}

	public void status1() {
		int i = tb.getSelectedRow();
		String.valueOf(model.getValueAt(i, 1));
		String str2 = String.valueOf(model.getValueAt(i, 3));

		try {
			PreparedStatement localPreparedStatement = con
					.prepareStatement("Select project_name, Co_code from project_co where project_no= ? ");
			localPreparedStatement.setInt(1, Integer.parseInt(String.valueOf(tf.getSelectedItem())));
			rs = localPreparedStatement.executeQuery();
			while (rs.next()) {
				String str3 = new String(rs.getString(1));
				String str4 = new String(rs.getString(2));

				statusl1.setText(str2 + " (" + str3 + ", " + str4 + ")");
			}
		} catch (Exception localException) {
			System.out.println(localException);
		}
	}

	public void factorCalc() {
		int i = tb.getSelectedRow();
		String str1 = String.valueOf(tf1.getSelectedItem());
		String str2 = String.valueOf(model.getValueAt(i, 2));
		String str3 = String.valueOf(model.getValueAt(i, 3));

		statustf2.setText(String.valueOf(ProjectDAO.getCountAllotedDrg(str3, str1, str2)));
		statustf3.setText(String.valueOf(ProjectDAO.getAllotedHrs(str3, str1, str2)));
		statustf4.setText(String.valueOf(ProjectDAO.getCountCompletedDrg(str3, str1)));
		statustf5.setText(String.valueOf(ProjectDAO.getCompletedHrs(str3, str1, str2)));

		Float.parseFloat(EmpTB.getCurrentFactor(str1));
		float f2 = ProjectDAO.getUsedHrs(str1, str3, str2).floatValue();
		statustf6.setText(String.valueOf(df4.format(f2)));

		float f3 = Float.parseFloat(statustf5.getText()) / f2;

		statustf7.setText(String.valueOf(df4.format(f3)));
	}

	public void status2() {
		int i = tb.getSelectedRow();
		String.valueOf(model.getValueAt(i, 1));
		String str2 = String.valueOf(model.getValueAt(i, 2));
		String str3 = String.valueOf(model.getValueAt(i, 3));

		try {
			stat = con.createStatement();
			rs = stat.executeQuery("Select DISTINCT(DRAWING_NO) from " + str2 + " where project_no='" + str3
					+ "' and emp_code='" + tf1.getSelectedItem() + "'  ");
			while (rs.next()) {
				String str4 = new String(rs.getString(1));
				arl1.add(str4);
			}
			statustf2.setText(String.valueOf(arl1.size()));
			arl1.clear();
		} catch (Exception localException) {
			System.out.println(localException);
		}
	}

	public void status3() {
		int i = tb.getSelectedRow();
		String.valueOf(model.getValueAt(i, 1));
		String str2 = String.valueOf(model.getValueAt(i, 2));
		String str3 = String.valueOf(model.getValueAt(i, 3));

		try {
			stat = con.createStatement();
			rs = stat.executeQuery("Select DISTINCT(DATE_MONTH) from " + str2 + " where project_no='" + str3
					+ "' and emp_code='" + tf1.getSelectedItem() + "'  ");
			while (rs.next()) {
				String str4 = new String(rs.getString(1));
				arl3.add(str4);
			}
			statustf3.setText(String.valueOf(arl3.size()));
			arl3.clear();
		} catch (Exception localException) {
			System.out.println(localException);
		}
	}

	public void status4() {
		int i = tb.getSelectedRow();
		String.valueOf(model.getValueAt(i, 1));
		String str2 = String.valueOf(model.getValueAt(i, 2));
		String str3 = String.valueOf(model.getValueAt(i, 3));

		String str4 = "";
		if (str2.equalsIgnoreCase("Detailing")) {
			str4 = "drgstatus";
		}
		if (str2.equalsIgnoreCase("Checking")) {
			str4 = "drgstatus1";
		}

		statustf4.setText("");

		try {
			stat = con.createStatement();
			rs = stat.executeQuery("Select count(drg_no) from timesheet where project_no='" + str3
					+ "' and emp_code='" + tf1.getSelectedItem() + "' and " + str4 + " not like '0' ");
			while (rs.next()) {
				String str5 = new String(rs.getString(1));
				statustf4.setText(str5);
			}
		} catch (Exception localException) {
			System.out.println(localException);
		}

		float f1 = Float.parseFloat(statustf2.getText());
		float f2 = Float.parseFloat(statustf3.getText());
		float f3 = f1 / f2;

		statustf6.setText(String.valueOf(df3.format(f3)));
	}

	public void status5() {
		int i = tb.getSelectedRow();
		String.valueOf(model.getValueAt(i, 1));
		String.valueOf(model.getValueAt(i, 2));
		String str3 = String.valueOf(model.getValueAt(i, 3));

		statustf5.setText("");

		try {
			stat = con.createStatement();
			rs = stat.executeQuery("Select count(date) from HRTIMEMASTER where project_no='" + str3
					+ "' and emp_code='" + tf1.getSelectedItem() + "' ");
			while (rs.next()) {
				String str4 = new String(rs.getString(1));
				statustf5.setText(str4);
			}
		} catch (Exception localException) {
			System.out.println(localException);
		}

		float f1 = Float.parseFloat(statustf4.getText());
		float f2 = Float.parseFloat(statustf5.getText());
		float f3 = f1 / f2;

		float f4 = Float.parseFloat(statustf6.getText());

		if (f3 < f4) {
			statustf7.setForeground(Color.red);
			statustf7.setText(String.valueOf(df3.format(f3)));
		} else {
			statustf7.setForeground(Color.blue);
			statustf7.setText(String.valueOf(df3.format(f3)));
		}
	}

	public EmpWorkAlloted() {
		df = new DecimalFormat("00");
		df3 = new DecimalFormat("0.00");
		df4 = new DecimalFormat("00.00");

		f = new JInternalFrame("Employee Alloted Work/Job", true, true, true, true);
		l = new JLabel("Project No");
		l1 = new JLabel("Emp Code");
		l2 = new JLabel("Post");
		l3 = new JLabel("From");
		l4 = new JLabel("To");

		tf = new JComboBox();

		tf2 = new JTextField(10);

		statusl1 = new JLabel("ProjectNo/Name/TL");
		statusl2 = new JLabel("Alloted Drgs/Hrs");
		statusl3 = new JLabel("Completed Drgs/Hrs");
		statusl4 = new JLabel("Used Hrs/PerformedFactor");
		statusl5 = new JLabel("Current Factor/Performed Factor");

		statustf2 = new JTextField(4);
		statustf3 = new JTextField(4);
		statustf4 = new JTextField(4);
		statustf5 = new JTextField(4);
		statustf6 = new JTextField(4);
		statustf7 = new JTextField(4);

		showbut = new JButton("Show List", new javax.swing.ImageIcon("image/list.gif"));
		closebut = new JButton("Close", new javax.swing.ImageIcon("image/close.gif"));

		jpm = new javax.swing.JPopupMenu();
		popm = new javax.swing.JMenuItem("OK !", new javax.swing.ImageIcon("image/check.gif"));
		popm1 = new javax.swing.JMenuItem("Sorry !", new javax.swing.ImageIcon("image/close.gif"));

		northp1 = new JPanel();
		northp2 = new JPanel();
		northp3 = new JPanel();

		butpanel1 = new JPanel();
		butpanel2 = new JPanel();
		statuspanel = new JPanel();

		centerpanel = new JPanel();
		northpanel = new JPanel();

		dat = new java.util.Date();
		formatter = new java.text.SimpleDateFormat("dd/MM/yyyy");

		try {
			dateString = formatter.format(dat);
			tf3 = new JTextField(String.valueOf(dateString), 8);
			tf4 = new JTextField(String.valueOf(dateString), 8);
		} catch (IllegalArgumentException localIllegalArgumentException) {
			System.out.println(localIllegalArgumentException.getMessage());
		}

		fo = new java.awt.Font("Tahoma", 1, 11);
		line = javax.swing.BorderFactory.createLineBorder(Color.darkGray);
		bor1 = javax.swing.BorderFactory.createTitledBorder(line, "EMP Code", 0, 0, fo, Color.darkGray);
		bor2 = javax.swing.BorderFactory.createTitledBorder(line, "Work Allotment Details", 0, 0, fo,
				Color.darkGray);

		tk = java.awt.Toolkit.getDefaultToolkit();
		ss = tk.getScreenSize();
		fs = f.getSize();

		ctr = new EmpWorkAlloted.ColoredTableCellRenderer();
		ctr2 = new EmpWorkAlloted.ColoredTableCellRenderer2();
		ctr3 = new EmpWorkAlloted.ColoredTableCellRenderer3();

		days = new String[] { "SUN", "MON", "TUE", "WED", "THU", "FRI", "SAT" };

		query = "";

		arl1 = new ArrayList();
		arl2 = new ArrayList<Object>();
		arl3 = new ArrayList();
		arl4 = new ArrayList<Object>();

		dts = new DailyTimeSheet();
	}

	public void insertToDailyTimeSheet() {
		int i = tb.getSelectedRow();
		String str1 = String.valueOf(model.getValueAt(i, 1));
		String str2 = (String) tf1.getSelectedItem();

		System.out.println("Date : " + str1);

		DailyTimeSheet.tf.setText(" ");
		DailyTimeSheet.tf.setText(str1);
		DailyTimeSheet.tf1.setText(str2);
		f.setVisible(false);
		DailyTimeSheet.tf1.requestFocus();
	}

	public void actionPerformed(ActionEvent paramActionEvent) {
		if ((paramActionEvent.getSource() == tf4) || (paramActionEvent.getSource() == showbut)) {
			dateDiff();
			workDetails();
			jobDetails();
		}

		if (paramActionEvent.getSource() == tf) {
			getEmployee();
		}
		if (paramActionEvent.getSource() == closebut) {
			f.setVisible(false);
		}
		if (paramActionEvent.getSource() == insertbut) {
			insertToDailyTimeSheet();
		}
		int i;
		String str;
		if (paramActionEvent.getSource() == popm) {
			i = tb.getSelectedRow();
			str = String.valueOf(model.getValueAt(i, 1));
			query = ("update OTSTATUS SET REMARKS ='OK' where emp_code='" + tf1.getSelectedItem() + "' and date='"
					+ str + "' ");
			updateOT();
			model.setValueAt("OK", i, 5);
		}
		if (paramActionEvent.getSource() == popm1) {
			i = tb.getSelectedRow();
			str = String.valueOf(model.getValueAt(i, 1));
			query = ("update OTSTATUS SET REMARKS ='NO' where emp_code='" + tf1.getSelectedItem() + "' and date='"
					+ str + "' ");
			updateOT();
			model.setValueAt("NO", i, 5);
		}
	}

	public void mousePressed(MouseEvent paramMouseEvent) {
		if (paramMouseEvent.getSource() == tb) {
			int i = tb.getSelectedColumn();
			tb.getSelectedRow();
			if (i == 3) {
				status1();
				factorCalc();
			}

			if (javax.swing.SwingUtilities.isRightMouseButton(paramMouseEvent)) {
				jpm.show((javax.swing.JComponent) paramMouseEvent.getSource(), paramMouseEvent.getX(),
						paramMouseEvent.getY());
			}
		}
	}

	public void mouseReleased(MouseEvent paramMouseEvent) {
		if (paramMouseEvent.getSource() == tf3) {
			if (javax.swing.SwingUtilities.isRightMouseButton(paramMouseEvent)) {
			    SolCalendar skl = new SolCalendar();
				skl.showCalendar();
				skl.getDate(tf3);
			}
		}
		if (paramMouseEvent.getSource() == tf4) {
			if (javax.swing.SwingUtilities.isRightMouseButton(paramMouseEvent)) {
			    SolCalendar skl = new SolCalendar();
				skl.showCalendar();
				skl.getDate(tf4);
			}
		}
	}

	public void itemStateChanged(java.awt.event.ItemEvent paramItemEvent) {
		if (paramItemEvent.getSource() == tf) {
		}
	}

	public void mouseClicked(MouseEvent paramMouseEvent) {
	}

	public void mouseEntered(MouseEvent paramMouseEvent) {
	}

	public void mouseExited(MouseEvent paramMouseEvent) {
	}
}
