package com.sol.erp;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class MasterReport implements java.awt.event.ActionListener {
	JFrame f = new JFrame("Shekhar");

	JPanel p1 = new JPanel();
	JComboBox cb = new JComboBox();
	JButton b1 = new JButton("+ Add Row");
	JButton b2 = new JButton("X Remove Row");
	JScrollPane sp;
	JTable tab;
	Container c;

	public void show() {
		this.c = this.f.getContentPane();
		this.c.setLayout(new java.awt.BorderLayout());

		this.cb.addItem("HREmp_JOIN");
		this.cb.addItem("Punchcard");
		this.cb.addItem("HRTIMEMASTER");

		this.p1.add(this.cb);
		this.p1.add(this.b1);
		this.b1.addActionListener(this);
		this.p1.add(this.b2);
		this.b2.addActionListener(this);

		this.cb.addActionListener(this);

		this.c.add(this.p1, "North");

		this.f.setSize(800, 580);
		this.f.setVisible(true);
	}

	public void actionPerformed(ActionEvent paramActionEvent) {
		if (paramActionEvent.getSource() == this.cb) {
			if (this.sp != null)
				this.c.remove(this.sp);
			String str1 = (String) this.cb.getSelectedItem();

			String str2 = "jdbc:mysql://192.168.1.144:3306/ERPDB";
			String str3 = "root";
			String str4 = "myadmin";

			try {
				Class.forName("com.mysql.jdbc.Driver");
				Connection localConnection = java.sql.DriverManager.getConnection(str2, str3, str4);
				Statement localStatement = localConnection.createStatement();
				ResultSet localResultSet = localStatement.executeQuery("select * from " + str1);
				int i = 0;
				int j = 0;
				while (localResultSet.next()) {
					i++;
				}
				localResultSet = localStatement.executeQuery("select * from " + str1);
				j = localResultSet.getMetaData().getColumnCount();
				Object[][] arrayOfObject = new Object[i][j];
				localResultSet = localStatement.executeQuery("select * from " + str1);
				for (int k = 0; localResultSet.next(); k++) {
					for (int m = 0; m < j; m++) {
						arrayOfObject[k][m] = localResultSet.getString(m + 1);
					}
				}
				String[] arrayOfString = new String[j];
				localResultSet = localStatement.executeQuery("select * from " + str1);
				for (int m = 0; m < j; m++) {
					arrayOfString[m] = localResultSet.getMetaData().getColumnLabel(m + 1);
				}

				this.tab = new JTable(arrayOfObject, arrayOfString);
				this.sp = new JScrollPane(this.tab);

				this.c.add(this.sp, "Center");
				this.f.pack();
				this.tab.setBackground(java.awt.Color.pink);
			} catch (Exception localException) {
				javax.swing.JOptionPane.showMessageDialog(this.f, localException.getMessage().toString());
			}
		}
	}

}