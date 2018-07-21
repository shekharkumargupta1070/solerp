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
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import com.sol.erp.constants.ApplicationConstants;
import com.sol.erp.ui.custom.SolTableModel;
import com.sol.erp.util.DBConnectionUtil;

public class OTStatus implements ActionListener, MouseListener {

	Connection con;
	Statement stat;
	ResultSet rs;
	int affected;
	String[] columnNames;
	Object[][] data;
	String[] columnNames1;
	Object[][] data1;
	String empcodestr;
	String itemcodestr;
	String datestr;
	DecimalFormat df;
	DecimalFormat df1;
	DefaultTableModel model;
	JTable tb;
	JScrollPane sp;
	DefaultTableModel model1;
	JTable tb1;
	JScrollPane sp1;
	JInternalFrame f;
	JLabel l1;
	JLabel l2;
	JLabel l3;
	JLabel l4;
	JLabel l5;
	JLabel l6;
	JLabel l7;
	JTextField tf1;
	JTextField tf2;
	JTextField tf3;
	JTextField tf4;
	JTextField tf5;
	JTextField tf6;
	JTextField tf7;
	JButton b1;
	JButton b2;
	JButton b3;
	JButton b4;
	JButton b5;
	JPanel northp1;
	JPanel northp2;
	JPanel northp3;
	JPanel butpanel1;
	JPanel butpanel2;
	JPanel centerpanel;
	JPanel northpanel;
	Date dat;
	SimpleDateFormat formatter;
	String dateString;
	Font fo;
	Border line;
	Border bor1;
	Border bor2;
	Toolkit tk;
	Dimension ss;
	Dimension fs;
	Container c;
	OTStatus.ColoredTableCellRenderer skr;
	OTStatus.ColoredTableCellRenderer1 skr1;

	String remstr;

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

			if ((paramInt2 >= 0) && (paramInt2 <= 7)) {
				setHorizontalAlignment(0);
			}

			String str = String.valueOf(model.getValueAt(paramInt1, 2));

			if (str.equalsIgnoreCase("0")) {
				setBackground(Color.white);
				setForeground(Color.darkGray);
			}
			if (str.equalsIgnoreCase("Detailing")) {
				setBackground(new Color(230, 230, 230));
				setForeground(Color.darkGray);
			}
			if (str.equalsIgnoreCase("Checking")) {
				setBackground(new Color(200, 200, 200));
				setForeground(Color.darkGray);
			}

			super.getTableCellRendererComponent(paramJTable, paramObject, paramBoolean1, paramBoolean2, paramInt1,
					paramInt2);

			return this;
		}
	}

	class ColoredTableCellRenderer1 extends DefaultTableCellRenderer {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		ColoredTableCellRenderer1() {
		}

		public Component getTableCellRendererComponent(JTable paramJTable, Object paramObject, boolean paramBoolean1,
				boolean paramBoolean2, int paramInt1, int paramInt2) {
			setEnabled((paramJTable == null) || (paramJTable.isEnabled()));

			if ((paramInt2 >= 0) && (paramInt2 <= 7)) {
				setHorizontalAlignment(0);
			}

			String str1 = String.valueOf(model1.getValueAt(paramInt1, 1));
			String str2 = String.valueOf(model1.getValueAt(paramInt1, 2));

			if ((str1.equalsIgnoreCase("OK")) && (str2.equalsIgnoreCase("OK"))) {
				setBackground(Color.red);
				setForeground(Color.white);
			}
			if ((str1.equalsIgnoreCase("OK")) && (str2.equalsIgnoreCase("NO"))) {
				setBackground(new Color(120, 120, 180));
				setForeground(Color.white);
			}
			if ((str1.equalsIgnoreCase("0")) && (str2.equalsIgnoreCase("0"))) {
				setBackground(Color.white);
				setForeground(Color.darkGray);
			}

			super.getTableCellRendererComponent(paramJTable, paramObject, paramBoolean1, paramBoolean2, paramInt1,
					paramInt2);

			return this;
		}
	}

	public void px() {
		c = f.getContentPane();
		c.setLayout(new BorderLayout());
		northp1.setLayout(new FlowLayout());
		northp2.setLayout(new GridLayout(2, 2, 2, 2));
		northpanel.setLayout(new BorderLayout());
		butpanel1.setLayout(new FlowLayout());
		butpanel2.setLayout(new FlowLayout());
		centerpanel.setLayout(new BorderLayout());

		tb.getColumnModel().getColumn(3).setPreferredWidth(400);

		for (int i = 0; i < tb.getColumnCount(); i++) {
			tb.getColumnModel().getColumn(i).setCellRenderer(skr);
		}

		for (int i = 0; i < tb1.getColumnCount(); i++) {
			tb1.getColumnModel().getColumn(i).setCellRenderer(skr1);
		}

		SolTableModel.decorateTable(tb);
		SolTableModel.decorateTable(tb1);

		northpanel.setBorder(bor1);

		northp1.add(l1);
		northp1.add(tf1);
		tf1.setFont(fo);
		tf1.setEditable(false);
		northp1.add(l2);
		northp1.add(tf2);
		tf2.setFont(fo);
		tf2.setEditable(false);
		northp1.add(l3);
		northp1.add(tf3);
		tf3.setFont(fo);
		tf3.setEditable(false);
		northp1.add(l4);
		northp1.add(tf4);
		tf4.setFont(fo);
		tf4.setEditable(false);
		northp1.add(l5);
		northp1.add(tf5);
		tf5.setFont(fo);
		northp1.add(l6);
		northp1.add(tf6);
		tf6.setFont(fo);
		northp1.add(l7);
		northp1.add(tf7);
		tf7.setFont(fo);
		tf7.setEditable(false);

		butpanel2.add(b2);
		b2.setMnemonic(83);

		butpanel2.add(b4);
		b4.setMnemonic(68);
		butpanel2.add(b5);
		b5.setMnemonic(67);

		sp1.setPreferredSize(new Dimension(300, 300));

		northpanel.add(northp1, "North");
		centerpanel.add(sp, "Center");
		centerpanel.add(sp1, "East");
		centerpanel.add(butpanel2, "South");

		c.add(northpanel, "North");
		c.add(centerpanel, "Center");

		tb.addMouseListener(this);

		b1.addActionListener(this);
		b2.addActionListener(this);
		b3.addActionListener(this);
		b4.addActionListener(this);
		b5.addActionListener(this);

		tf1.addActionListener(this);
		tf5.addActionListener(this);
		tf6.addActionListener(this);

		f.setLocation((ss.width - fs.width) / 5, (ss.height - fs.height) / 9);
		f.setResizable(false);
		f.setTitle("OT Status");
		f.setSize(660, 420);
		f.setVisible(true);
	}

	public void paramUser() {
		tf1.setText(TechMainFrame.textFieldLoggedBy.getText());
	}

	public void desig() {
		try {
			con = DBConnectionUtil.getConnection();
			stat = con.createStatement();
			rs = stat.executeQuery(
					"select EMP_NAME,designation from emp_status where emp_code='" + tf1.getText() + "' ");
			while (rs.next()) {
				String str1 = new String(rs.getString(1));
				String str2 = new String(rs.getString(2));
				tf2.setText(str1);
				tf3.setText(str2);
			}
		} catch (Exception localException) {
			System.out.println(localException);
		}
	}

	public void empList() {
		model.setNumRows(0);
		try {
			con = DBConnectionUtil.getConnection();
			stat = con.createStatement();
			rs = stat
					.executeQuery("select distinct(emp_code),PROJECT_NO,DTL_CHK from projectmanpower where project_co='"
							+ tf1.getText()
							+ "' AND DTL_CHK='Detailing' or DTL_CHK='Checking' ORDER BY PROJECT_NO , DTL_CHK ");
			rs.getMetaData().getColumnCount();
			while (rs.next()) {
				String str1 = new String(rs.getString(1));
				String str2 = new String(rs.getString(2));
				String str3 = new String(rs.getString(3));

				model.addRow(new Object[] { str1.toUpperCase(), str2, str3, "0" });
			}
		} catch (Exception localException) {
			System.out.println(localException);
		}
	}

	public void dateRange() {
		int i = tb.getRowCount();
		String str1 = "0";
		String str2 = "0";
		String str3 = "0";

		for (int j = 0; j < i; j++) {
			String str4 = String.valueOf(model.getValueAt(j, 0));
			String str5 = String.valueOf(model.getValueAt(j, 1));
			String str6 = String.valueOf(model.getValueAt(j, 2));

			try {
				con = DBConnectionUtil.getConnection();
				stat = con.createStatement();
				rs = stat.executeQuery(
						"select count(from_date), min(from_date),MAX(FROM_DATE) from projectmanpower where emp_code='"
								+ str4 + "' AND PROJECT_NO='" + str5 + "' and DTL_CHK='" + str6 + "'  ");
				rs.getMetaData().getColumnCount();
				while (rs.next()) {
					str1 = new String(rs.getString(1));
					str2 = new String(rs.getString(2));
					str3 = new String(rs.getString(3));

					model.setValueAt("( " + str2 + " - " + str3 + ") " + str1, j, 3);
				}
			} catch (Exception localException) {
				System.out.println(localException);
			}
		}
	}

	public void dateRange2() {
		int i = tb.getSelectedRow();
		String str1 = "0";
		String str2 = "0";
		String str3 = "0";

		String str4 = String.valueOf(model.getValueAt(i, 0));
		String str5 = String.valueOf(model.getValueAt(i, 1));
		String str6 = String.valueOf(model.getValueAt(i, 2));

		try {
			con = DBConnectionUtil.getConnection();
			stat = con.createStatement();
			rs = stat.executeQuery(
					"select count(from_date), min(from_date),MAX(FROM_DATE) from projectmanpower where emp_code='"
							+ str4 + "' AND PROJECT_NO='" + str5 + "' and DTL_CHK='" + str6 + "'  ");
			rs.getMetaData().getColumnCount();
			while (rs.next()) {
				str1 = new String(rs.getString(1));
				str2 = new String(rs.getString(2));
				str3 = new String(rs.getString(3));

				tf4.setText(str4);
				tf5.setText(str2);
				tf6.setText(str3);
				tf7.setText(str1);
			}
		} catch (Exception localException) {
			System.out.println(localException);
		}
	}

	public void showOT() {
		int i = tb.getRowCount();

		for (int j = 0; j < i; j++) {
			String str1 = String.valueOf(model.getValueAt(j, 0));
			String str2 = String.valueOf(model.getValueAt(j, 1));
			try {
				stat = con.createStatement();
				rs = stat.executeQuery("select tl_remarks,remarks from OTSTATUS WHERE DATE='" + str1
						+ "' and emp_code='" + str2 + "' ");
				rs.getMetaData().getColumnCount();
				while (rs.next()) {
					String str3 = new String(rs.getString(1));
					String str4 = new String(rs.getString(2));

					model.setValueAt(str3, j, 3);
					model.setValueAt(str4, j, 4);
				}
			} catch (Exception localException) {
				System.out.println(localException);
			}
		}
	}

	public void showEmpOT() {
		model1.setNumRows(0);

		int i = tb.getSelectedRow();
		String str1 = String.valueOf(model.getValueAt(i, 0));
		String.valueOf(model.getValueAt(i, 1));

		try {
			con = DBConnectionUtil.getConnection();
			stat = con.createStatement();
			rs = stat.executeQuery("select DATE, TL_REMARKS, REMARKS from OTSTATUS where EMP_CODE='" + str1
					+ "' AND DATE BETWEEN '" + tf5.getText() + "' AND '" + tf6.getText() + "'  ");
			rs.getMetaData().getColumnCount();
			while (rs.next()) {
				String str3 = new String(rs.getString(1));
				String str4 = new String(rs.getString(2));
				String str5 = new String(rs.getString(3));

				model1.addRow(new Object[] { str3, str4, str5 });
			}
		} catch (Exception localException) {
			System.out.println(localException);
		}
	}

	public void sendOT() {
		int[] arrayOfInt = tb1.getSelectedRows();

		for (int i = 0; i < arrayOfInt.length; i++) {
			String str = String.valueOf(model1.getValueAt(arrayOfInt[i], 0));

			try {
				stat = con.createStatement();
				affected = stat.executeUpdate(
						"INSERT INTO OTSTATUS VALUES('" + str + "','" + tf4.getText() + "','0','OK','OK')  ");

				if (affected > 0) {
					model1.setValueAt("OK", arrayOfInt[i], 1);
					model1.setValueAt("OK", arrayOfInt[i], 2);
				}
			} catch (Exception localException) {
				JOptionPane.showMessageDialog(f, localException.getMessage().toString());
			}
		}
	}

	public OTStatus() {

		affected = 0;

		columnNames = new String[] { "Emp_Code", "Project No", "DTL_CHK", "Date Range" };
		data = new Object[0][];

		columnNames1 = new String[] { "Date", "TL Remarks", "Reply" };
		data1 = new Object[0][];

		empcodestr = "Shekhar";
		itemcodestr = "AAA";

		df = new DecimalFormat("00.00");
		df1 = new DecimalFormat("00");

		model = new DefaultTableModel(data, columnNames);
		tb = new JTable(model);
		sp = new JScrollPane(tb);

		model1 = new DefaultTableModel(data1, columnNames1);
		tb1 = new JTable(model1);
		sp1 = new JScrollPane(tb1);

		f = new JInternalFrame("OT Status", true, true, true, true);

		l1 = new JLabel("TL ");
		l2 = new JLabel("Name");
		l3 = new JLabel("Desig");

		l4 = new JLabel("Emp Code");
		l5 = new JLabel("From");
		l6 = new JLabel("To");
		l7 = new JLabel("#");

		tf1 = new JTextField(5);
		tf2 = new JTextField(15);
		tf3 = new JTextField(10);
		tf4 = new JTextField(4);
		tf5 = new JTextField(8);
		tf6 = new JTextField(8);
		tf7 = new JTextField(8);

		b1 = new JButton("Show Reply");
		b2 = new JButton("Send OT INFO");
		b3 = new JButton("OK");
		b4 = new JButton("DENY");
		b5 = new JButton("Close", new ImageIcon(ClassLoader.getSystemResource(ApplicationConstants.IMAGE_PATH + "close.gif")));

		northp1 = new JPanel();
		northp2 = new JPanel();
		northp3 = new JPanel();

		butpanel1 = new JPanel();
		butpanel2 = new JPanel();

		centerpanel = new JPanel();
		northpanel = new JPanel();

		dat = new Date();
		formatter = new SimpleDateFormat("dd/MM/yyyy");

		try {
			dateString = formatter.format(dat);
		} catch (IllegalArgumentException localIllegalArgumentException) {
			System.out.println(localIllegalArgumentException.getMessage());
		}

		fo = new Font("Tahoma", 1, 11);
		line = BorderFactory.createLineBorder(Color.darkGray);
		bor1 = BorderFactory.createTitledBorder(line, "Project No", 0, 0, fo, Color.darkGray);
		bor2 = BorderFactory.createTitledBorder(line, "Estimation Details", 0, 0, fo, Color.darkGray);

		tk = Toolkit.getDefaultToolkit();
		ss = tk.getScreenSize();
		fs = f.getSize();

		skr = new OTStatus.ColoredTableCellRenderer();
		skr1 = new OTStatus.ColoredTableCellRenderer1();

		remstr = "YES";
	}

	public void updateOT() {
		int i = tb.getSelectedRow();

		String str1 = String.valueOf(model.getValueAt(i, 0));
		String str2 = String.valueOf(model.getValueAt(i, 1));

		try {
			stat = con.createStatement();
			affected = stat.executeUpdate("update OTSTATUS SET REMARKS ='" + remstr + "' where emp_code='" + str2
					+ "' and date='" + str1 + "' ");

			if (affected > 0) {
				model.setValueAt(remstr, i, 4);
			}
		} catch (Exception localException) {
		}
	}

	public void checkDesig() {
		String str = tf3.getText();

		if (str.equalsIgnoreCase("Team Leader")) {
			b3.setEnabled(false);
			b4.setEnabled(false);
			empList();
			dateRange();
		} else {
			b2.setEnabled(false);
			showEmpOT();
		}
	}

	public void dateDiff() {
		String str1 = tf5.getText();
		String str2 = str1.substring(0, 2);
		String str3 = str1.substring(3, 5);
		String str4 = str1.substring(7, 10);

		int i = Integer.parseInt(str2);
		int j = Integer.parseInt(str3);
		int k = Integer.parseInt(str4);

		String str5 = tf6.getText();
		String str6 = str5.substring(0, 2);
		String str7 = str5.substring(3, 5);
		String str8 = str5.substring(7, 10);

		int m = Integer.parseInt(str6);
		int n = Integer.parseInt(str7);
		int i1 = Integer.parseInt(str8);

		int i2 = 0;
		GregorianCalendar localGregorianCalendar1 = new GregorianCalendar(k, j, i);
		GregorianCalendar localGregorianCalendar2 = new GregorianCalendar(i1, n, m);
		while (localGregorianCalendar2.get(1) != localGregorianCalendar1.get(1)) {
			i2 += 365;
			localGregorianCalendar1.add(6, 365);
		}
		i2 += localGregorianCalendar2.get(6) - localGregorianCalendar1.get(6);
		tf7.setText(String.valueOf(i2 + 1));
	}

	public void OTDetails() {
		Object localObject = "0";
		String str1 = "0";
		String str2 = "0";
		model1.setNumRows(0);

		int i = tb.getSelectedRow();
		String str4 = String.valueOf(model.getValueAt(i, 0));

		String str5 = tf5.getText();
		tf6.getText();

		String str7 = str5.substring(0, 2);

		int j = Integer.parseInt(str7);

		String str8 = str5.substring(3, 5);

		String str9 = str5.substring(6, 10);

		int k = Integer.parseInt(str8);

		int m = Integer.parseInt(str9);

		int n = 0;
		int i2 = m % 4;
		String str12 = "";

		String str13 = tf7.getText();
		int i4 = Integer.parseInt(str13);

		for (int i6 = 0; i6 <= i4; i6++) {
			String str10 = "/" + String.valueOf(df1.format(k)) + "/" + String.valueOf(m);
			if (k == 12) {
				k = 1;
				m += 1;
			}

			str12 = df1.format(j) + str10;
			tf5.getText();

			System.out.println(str12);

			try {
				stat = con.createStatement();
				rs = stat.executeQuery("select DATE, TL_REMARKS, REMARKS from OTSTATUS where EMP_CODE='" + str4
						+ "' AND DATE='" + str12 + "' ");
				rs.getMetaData().getColumnCount();

				while (rs.next()) {
					localObject = new String(rs.getString(1));
					str1 = new String(rs.getString(2));
					str2 = new String(rs.getString(3));
				}
			} catch (Exception localException) {
				System.out.println(localException);
			}
			if (((String) localObject).equalsIgnoreCase("0")) {
				localObject = str12;
			}
			model1.addRow(new Object[] { str12, str1, str2 });
			localObject = "0";
			str1 = "0";
			str2 = "0";

			if (k == 1) {
				n = 31;
			}
			if (k == 2) {
				if (k == 2) {
					if (i2 == 0) {
						n = 29;
					} else {
						n = 28;
					}

				} else {
					n = 30;
				}
			}
			if (k == 3) {
				n = 31;
			}
			if (k == 4) {
				n = 30;
			}
			if (k == 5) {
				n = 31;
			}
			if (k == 6) {
				n = 30;
			}
			if (k == 7) {
				n = 31;
			}
			if (k == 8) {
				n = 31;
			}
			if (k == 9) {
				n = 30;
			}
			if (k == 10) {
				n = 31;
			}
			if (k == 11) {
				n = 30;
			}
			if (k == 12) {
				n = 31;
			}

			if (j == n) {
				j = 0;
				k += 1;
				str10 = "/" + String.valueOf(df1.format(k)) + "/" + String.valueOf(m);
			}
			j += 1;
		}
	}

	public void actionPerformed(ActionEvent paramActionEvent) {
		if ((paramActionEvent.getSource() != tf1) || (

		(paramActionEvent.getSource() != b1) ||

				(paramActionEvent.getSource() == b2))) {
			sendOT();
		}
		if (paramActionEvent.getSource() == b3) {
			remstr = "YES";
			updateOT();
		}
		if (paramActionEvent.getSource() == b4) {
			remstr = "NO";
			updateOT();
		}
		if (paramActionEvent.getSource() == tf5) {
			tf6.requestFocus();
		}
		if (paramActionEvent.getSource() == tf6) {
			dateDiff();
			OTDetails();
		}
		if (paramActionEvent.getSource() == b5) {
			f.setVisible(false);
		}
	}

	public void mousePressed(MouseEvent paramMouseEvent) {
		if (paramMouseEvent.getSource() == tb) {
			dateRange2();
			OTDetails();
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
