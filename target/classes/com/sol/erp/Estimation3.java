package com.sol.erp;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.sql.ResultSet;

import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.sol.erp.util.DBConnectionUtil;

public class Estimation3 implements java.awt.event.ActionListener {


	java.sql.Connection con;

	java.sql.Statement stat;
	ResultSet rs;
	String[] columnNames = { "Item Code", "MP Quoted Sheet", "MP Quoted Time", "TL Quoted Sheet", "TL Quoted Time",
			"Actual Sheet", "Actual Time" };
	Object[][] data = new Object[0][];

	String empcodestr = "Shekhar";
	String itemcodestr = "AAA";

	String datestr;

	java.text.DecimalFormat df = new java.text.DecimalFormat("00.00");

	DefaultTableModel model = new DefaultTableModel(data, columnNames);
	JTable tb = new JTable(model);
	javax.swing.JScrollPane sp = new javax.swing.JScrollPane(tb);

	JInternalFrame f = new JInternalFrame("Final Estimation", true, true, true, true);

	JLabel l1 = new JLabel("Project No");
	JLabel l2 = new JLabel("Name");
	JLabel l3 = new JLabel("Client");
	JLabel l4 = new JLabel("TL");

	JTextField tf1 = new JTextField(6);
	JTextField tf2 = new JTextField(12);
	JTextField tf3 = new JTextField(12);
	JTextField tf4 = new JTextField(4);

	JButton b1 = new JButton("Show Status");
	JButton b2 = new JButton("Save Estimation");
	JButton b3 = new JButton("Close", new javax.swing.ImageIcon("image/close.gif"));

	JPanel northp1 = new JPanel();
	JPanel northp2 = new JPanel();
	JPanel northp3 = new JPanel();

	JPanel butpanel1 = new JPanel();
	JPanel butpanel2 = new JPanel();

	JPanel centerpanel = new JPanel();
	JPanel northpanel = new JPanel();

	java.awt.Font fo = new java.awt.Font("Tahoma", 1, 11);
	javax.swing.border.Border line = javax.swing.BorderFactory.createLineBorder(Color.darkGray);
	javax.swing.border.Border bor1 = javax.swing.BorderFactory.createTitledBorder(line, "Project No", 0, 0, fo,
			Color.darkGray);
	javax.swing.border.Border bor2 = javax.swing.BorderFactory.createTitledBorder(line, "Estimation Details", 0, 0, fo,
			Color.darkGray);

	java.awt.Toolkit tk = java.awt.Toolkit.getDefaultToolkit();
	java.awt.Dimension ss = tk.getScreenSize();
	java.awt.Dimension fs = f.getSize();
	java.awt.Container c;

	public void px() {
		c = f.getContentPane();
		c.setLayout(new java.awt.BorderLayout());
		northp1.setLayout(new java.awt.FlowLayout());
		northp2.setLayout(new java.awt.GridLayout(2, 2, 2, 2));
		northpanel.setLayout(new java.awt.BorderLayout());
		butpanel1.setLayout(new java.awt.FlowLayout());
		butpanel2.setLayout(new java.awt.FlowLayout());
		centerpanel.setLayout(new java.awt.BorderLayout());

		tb.setFont(fo);
		tb.setBackground(Color.white);
		tb.setForeground(Color.black);
		tb.getTableHeader().setPreferredSize(new java.awt.Dimension(50, 20));
		tb.getTableHeader().setFont(fo);
		tb.setRowHeight(20);
		tb.setGridColor(new Color(100, 150, 100));
		tb.setSelectionBackground(new Color(60, 130, 100));
		tb.setSelectionForeground(Color.white);

		northpanel.setBorder(bor1);
		sp.setBorder(bor2);

		northp1.add(l1);
		northp1.add(tf1);
		tf1.setFont(fo);
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

		northp1.add(b1);
		b1.setMnemonic(87);

		butpanel2.add(b2);
		b2.setMnemonic(83);
		butpanel2.add(b3);
		b3.setMnemonic(67);

		northpanel.add(northp1, "North");
		centerpanel.add(sp, "Center");
		centerpanel.add(butpanel2, "South");

		c.add(northpanel, "North");
		c.add(centerpanel, "Center");

		b1.addActionListener(this);
		b2.addActionListener(this);
		b3.addActionListener(this);
		tf1.addActionListener(this);

		f.getRootPane().setDefaultButton(b1);

		f.setSize(800, 600);
		f.setVisible(true);
	}

	public void projectDetails() {
		model.setNumRows(0);
		try {
			con = DBConnectionUtil.getConnection();
			stat = con.createStatement();
			rs = stat.executeQuery("select PROJECT_NAME, CLIENT_NAME, CO_CODE from project_co");
			rs.getMetaData().getColumnCount();
			while (rs.next()) {
				String str1 = new String(rs.getString(1));
				String str2 = new String(rs.getString(2));
				String str3 = new String(rs.getString(3));

				tf2.setText(str1);
				tf3.setText(str2);
				tf4.setText(str3);
			}
		} catch (Exception localException) {
			System.out.println(localException);
		}
	}

	public void MPList() {
		model.setNumRows(0);

		try {
			stat = con.createStatement();
			rs = stat.executeQuery(
					"select M.item_code, M.sheet_qty, M.total_time, T.ITEM_CODE, T.SHEET_QTY, T.TOTAL_TIME from ESTIMATION_MP M,  OLD_TL_ESTIMATION T WHERE M.PROJECT_NO='"
							+ tf1.getText()
							+ "' group by M.ITEM_CODE, M.SHEET_QTY, M.TOTAL_TIME, T.ITEM_CODE, T.SHEET_QTY, T.TOTAL_TIME HAVING M.ITEM_CODE=T.ITEM_CODE ");
			rs.getMetaData().getColumnCount();
			while (rs.next()) {
				String str1 = new String(rs.getString(1));
				String str2 = new String(rs.getString(2));
				String str3 = new String(rs.getString(3));
				new String(rs.getString(4));
				String str5 = new String(rs.getString(5));
				String str6 = new String(rs.getString(6));

				model.addRow(new Object[] { str1, str2, str3, str5, str6 });
			}
		} catch (Exception localException) {
			System.out.println(localException);
		}
	}

	public void saveRecord() {
		int i = tb.getRowCount();
		System.out.println(i);

		for (int j = 0; j < i; j++) {

			String str1 = String.valueOf(model.getValueAt(j, 0));
			String str2 = String.valueOf(model.getValueAt(j, 5));
			String str3 = String.valueOf(model.getValueAt(j, 6));
			System.out.println(str2);

			try {
				stat = con.createStatement();
				stat.executeUpdate("INSERT INTO ESTIMATION3 VALUES('" + tf1.getText() + "','" + tf2.getText() + "','"
						+ tf3.getText() + "','" + tf4.getText() + "', '" + str1 + "','" + str2 + "', '" + str3 + "') ");
			} catch (Exception localException) {
				System.out.println(localException);
			}
		}
	}

	public void actionPerformed(ActionEvent paramActionEvent) {
		if ((paramActionEvent.getSource() == b1) || (paramActionEvent.getSource() == tf1)) {
			projectDetails();
			MPList();
		}

		if (paramActionEvent.getSource() == b2) {
			saveRecord();
		}

		if (paramActionEvent.getSource() == b3) {
			f.setVisible(false);
		}
	}

}
