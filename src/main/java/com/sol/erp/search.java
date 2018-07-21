package com.sol.erp;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class search implements java.awt.event.ActionListener {
	JFrame f = new JFrame("Search From Multipule Type");
	javax.swing.JLabel l1 = new javax.swing.JLabel("Book Name");
	javax.swing.JLabel l2 = new javax.swing.JLabel("Book Code");
	javax.swing.JLabel l3 = new javax.swing.JLabel("Return Date");
	JTextField tf1 = new JTextField();
	JTextField tf2 = new JTextField();
	JTextField tf3 = new JTextField();
	JButton b1 = new JButton("Search");
	JButton b2 = new JButton("Reset");
	JButton b3 = new JButton("Status");
	JButton b4 = new JButton("Reset");
	JButton b5 = new JButton("About");
	JButton b6 = new JButton("Exit");

	JPanel p1 = new JPanel();
	JPanel p2 = new JPanel();
	JPanel p3 = new JPanel();

	javax.swing.JLabel mes1 = new javax.swing.JLabel("Select The Book Code &");
	javax.swing.JLabel mes2 = new javax.swing.JLabel("Type In Field Specified.");
	javax.swing.border.Border bor1 = javax.swing.BorderFactory.createTitledBorder("Search Book");
	javax.swing.border.Border bor2 = javax.swing.BorderFactory.createTitledBorder("Rules");
	javax.swing.border.Border bor3 = javax.swing.BorderFactory.createTitledBorder("Status Of Book");

	java.awt.Toolkit tk = java.awt.Toolkit.getDefaultToolkit();

	java.awt.Image ic = this.tk.getImage("r1.jpg");
	java.awt.Dimension ss = this.tk.getScreenSize();
	java.awt.Dimension fs = this.f.getSize();

	public void px() {
		java.awt.Container localContainer = this.f.getContentPane();
		localContainer.setLayout(null);
		this.p1.setLayout(new java.awt.GridLayout(2, 2));
		this.p2.setLayout(new java.awt.GridLayout(1, 2));
		this.p3.setLayout(new java.awt.GridLayout(3, 2));

		this.p1.add(this.l1);
		this.p1.add(this.tf1);
		this.p1.add(this.b1);
		this.b1.addActionListener(this);
		this.p1.add(this.b2);
		this.b2.addActionListener(this);
		this.p2.add(this.mes1);
		this.p2.add(this.mes2);

		this.p3.add(this.l2);
		this.p3.add(this.tf2);
		this.p3.add(this.l3);
		this.p3.add(this.tf3);
		this.tf3.setEditable(false);
		this.tf3.setForeground(java.awt.Color.blue);
		this.p3.add(this.b3);
		this.b3.addActionListener(this);
		this.p3.add(this.b4);
		this.b4.addActionListener(this);

		this.p1.setBounds(0, 0, 300, 90);
		this.p2.setBounds(0, 91, 300, 60);
		this.p3.setBounds(0, 152, 300, 140);
		localContainer.add(this.p1);
		this.p1.setBorder(this.bor1);
		localContainer.add(this.p2);
		this.p2.setBorder(this.bor2);
		localContainer.add(this.p3);
		this.p3.setBorder(this.bor3);
		this.f.setIconImage(this.ic);
		this.f.setLocation((this.ss.width - this.fs.width) / 3, (this.ss.height - this.fs.height) / 5);
		this.f.setSize(310, 330);
		this.f.setVisible(true);
		this.f.setResizable(false);
	}

	public void actionPerformed(java.awt.event.ActionEvent paramActionEvent) {
		java.sql.Statement localStatement;
		java.sql.ResultSet localResultSet;
		int i;
		if (paramActionEvent.getSource() == this.b1) {
			try {
				Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
				java.sql.Connection localConnection1 = java.sql.DriverManager.getConnection("jdbc:odbc:shekhar",
						"scott", "tiger");
				localStatement = localConnection1.createStatement();
				localResultSet = localStatement
						.executeQuery("select * from bo_record where edi='" + this.tf1.getText() + "' ");
				i = 0;
				int j = 0;
				while (localResultSet.next()) {
					i++;
				}
				localResultSet = localStatement
						.executeQuery("select * from bo_record where edi='" + this.tf1.getText() + "' ");
				j = localResultSet.getMetaData().getColumnCount();
				Object[][] arrayOfObject = new Object[i][j];
				localResultSet = localStatement
						.executeQuery("select * from bo_record where edi='" + this.tf1.getText() + "' ");
				for (int k = 0; localResultSet.next(); k++) {
					for (int m = 0; m < j; m++) {
						arrayOfObject[k][m] = localResultSet.getString(m + 1);
					}
				}
				String[] arrayOfString = new String[j];
				localResultSet = localStatement
						.executeQuery("select * from bo_record where edi='" + this.tf1.getText() + "'");
				for (int m = 0; m < 7; m++) {
					arrayOfString[m] = localResultSet.getMetaData().getColumnLabel(m + 1);
				}
				javax.swing.JTable localJTable = new javax.swing.JTable(arrayOfObject, arrayOfString);
				javax.swing.JScrollPane localJScrollPane = new javax.swing.JScrollPane(localJTable);
				JFrame localJFrame = new JFrame("Report");
				localJFrame.getContentPane().add(localJScrollPane, "Center");
				localJFrame.setSize(800, 250);
				localJFrame.setVisible(true);
				localJFrame.setResizable(false);
			} catch (Exception localException1) {
				javax.swing.JOptionPane.showMessageDialog(this.f, localException1.getMessage().toString());
			}
			this.tf2.setText("");
			this.tf3.setText("");
		}
		if (paramActionEvent.getSource() == this.b3) {
			try {
				Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
				java.sql.Connection localConnection2 = java.sql.DriverManager.getConnection("Jdbc:Odbc:shekhar",
						"scott", "tiger");
				localStatement = localConnection2.createStatement();
				localResultSet = localStatement
						.executeQuery("select * from stu_info where code = '" + this.tf2.getText() + "' ");
				i = localResultSet.getMetaData().getColumnCount();
				while (localResultSet.next()) {
					String str = new String(localResultSet.getString(8));
					this.tf3.setText(String.valueOf(str));
				}
			} catch (Exception localException2) {
				javax.swing.JOptionPane.showMessageDialog(this.f, localException2.getMessage().toString());
			}
			if (this.tf3.getText().equals("")) {
				this.tf3.setText("Availeble In Library");
			}
		}
	}

}
