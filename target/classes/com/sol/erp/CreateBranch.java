package com.sol.erp;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.sol.erp.ui.custom.SolTableModel;
import com.sol.erp.util.DBConnectionUtil;

public class CreateBranch implements java.awt.event.ActionListener {

	Connection con;
	Statement stat;
	ResultSet rs;
	int affected = 0;

	String[] head = { "Company Name", "Address (Head Branch)", "Id" };
	Object[][] data = new Object[0][];

	java.text.DecimalFormat df = new java.text.DecimalFormat("00");

	JFrame f = new JFrame("Create Branch");
	DefaultTableModel model = new DefaultTableModel(data, head);
	JTable tb = new JTable(model);
	javax.swing.JScrollPane sp = new javax.swing.JScrollPane(tb);

	JLabel l1 = new JLabel("Name");
	JLabel l2 = new JLabel("Address");
	JLabel l3 = new JLabel("Id");

	public static JTextField tf1 = new JTextField(12);
	public static JTextField tf2 = new JTextField(20);
	public static JTextField tf3 = new JTextField(4);

	JButton b1 = new JButton("Create", new javax.swing.ImageIcon("image/create.gif"));
	JButton b2 = new JButton("Remove", new javax.swing.ImageIcon("image/close.gif"));
	JButton b3 = new JButton("Show All", new javax.swing.ImageIcon("image/play.gif"));
	JButton b4 = new JButton("Close");

	JPanel p1 = new JPanel();
	JPanel p2 = new JPanel();

	public void px() {
		tf1.setText(CreateCompany.tf1.getText());
		tf2.setText(CreateCompany.tf2.getText());
		tf3.setText(CreateCompany.tf3.getText());

		Container localContainer = f.getContentPane();
		localContainer.setLayout(new java.awt.BorderLayout());

		p1.setLayout(new java.awt.FlowLayout(0));
		p2.setLayout(new java.awt.FlowLayout(2));

		SolTableModel.decorateTable(tb);
		tb.getColumnModel().getColumn(0).setPreferredWidth(150);
		tb.getColumnModel().getColumn(1).setPreferredWidth(360);
		tb.getColumnModel().getColumn(2).setPreferredWidth(30);

		p1.add(l1);
		p1.add(tf1);
		tf1.setEditable(false);
		p1.add(l2);
		p1.add(tf2);
		tf2.setEditable(false);
		p1.add(l3);
		p1.add(tf3);
		tf3.setEditable(false);
		p1.add(b1);

		p2.add(b3);
		p2.add(b2);
		p2.add(b4);

		localContainer.add(p1, "North");
		localContainer.add(sp, "Center");
		localContainer.add(p2, "South");

		tf1.addActionListener(this);
		tf2.addActionListener(this);
		b1.addActionListener(this);
		b2.addActionListener(this);
		b3.addActionListener(this);
		b4.addActionListener(this);

		f.setLocation(200, 150);
		f.setSize(730, 400);
		f.setResizable(false);
		f.setVisible(true);
		maxID();
	}

	String maxid = tf3.getText();
	int maxno = 0;
	String initialstr = "C";

	public void maxID() {
		try {
			con =  DBConnectionUtil.getConnection();
			stat = con.createStatement();
			rs = stat
					.executeQuery("select max(BR_CODE) from HRCOMPANY_ID where COMPANY_ID ='" + tf3.getText() + "' ");
			while (rs.next()) {
				String str = new String(rs.getString(1));
				maxno = (Integer.parseInt(str) + 1);

				model.addRow(new Object[] { tf1.getText(), "", String.valueOf(maxno) });
			}
		} catch (Exception localException) {
			System.out.println("Max : " + localException);
		}
	}

	public void create() {
		int i = tb.getSelectedRow();
		String str1 = String.valueOf(model.getValueAt(i, 0));
		String str2 = String.valueOf(model.getValueAt(i, 1));
		String str3 = String.valueOf(model.getValueAt(i, 2));

		try {
			con =  DBConnectionUtil.getConnection();
			stat = con.createStatement();
			affected = stat.executeUpdate("insert into HRCOMPANY_ID values('" + str1 + "','" + str2 + "','"
					+ str3 + "','0','" + tf3.getText() + "') ");

			if (affected > 0) {
				javax.swing.JOptionPane.showMessageDialog(f, "Branch Created");
			}
		} catch (Exception localException) {
			javax.swing.JOptionPane.showMessageDialog(f, localException.getMessage().toString());
		}
	}

	public void remove() {
		String str1 = tf3.getText();
		String str2 = String.valueOf(model.getValueAt(tb.getSelectedRow(), 2));
		try {
			con =  DBConnectionUtil.getConnection();
			stat = con.createStatement();
			affected = stat.executeUpdate(
					"delete from HRCOMPANY_ID where COMPANY_ID ='" + str1 + "' and  br_code='" + str2 + "' ");
			if (affected > 0) {
				model.removeRow(tb.getSelectedRow());
			}
		} catch (Exception localException) {
			javax.swing.JOptionPane.showMessageDialog(f, localException.getMessage().toString());
		}
		maxID();
	}

	public void show() {
		model.setNumRows(0);
		try {
			con =  DBConnectionUtil.getConnection();
			stat = con.createStatement();
			rs = stat.executeQuery("select * from HRCOMPANY_ID where company_id ='" + tf3.getText() + "' ");
			while (rs.next()) {
				String str1 = new String(rs.getString(1));
				String str2 = new String(rs.getString(2));
				String str3 = new String(rs.getString(3));

				model.addRow(new Object[] { str1, str2, str3 });
			}
		} catch (Exception localException) {
			System.out.println("Show : " + localException);
		}
	}

	public void actionPerformed(ActionEvent paramActionEvent) {
		if ((paramActionEvent.getSource() == b1) || (paramActionEvent.getSource() == tf2)) {
			create();
		}
		if (paramActionEvent.getSource() == b2) {
			remove();
		}
		if (paramActionEvent.getSource() == b3) {
			show();
		}
		if (paramActionEvent.getSource() == b4) {
			f.setVisible(false);
		}
	}
}
