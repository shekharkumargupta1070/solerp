package com.sol.erp;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.sol.erp.util.DBConnectionUtil;

public class StructIndex implements java.awt.event.ActionListener {
	java.sql.Connection con;
	java.sql.Statement stat;
	java.sql.ResultSet rs;
	

	String[] columnNames = { "Item Code", "Item Name" };
	Object[][] data = new Object[0][];

	DefaultTableModel model = new DefaultTableModel(this.data, this.columnNames);
	javax.swing.JTable tb = new javax.swing.JTable(this.model);
	javax.swing.JScrollPane sp = new javax.swing.JScrollPane(this.tb);

	JDialog f = new JDialog();
	JLabel l1 = new JLabel("Item Code");
	JLabel l2 = new JLabel("Item Name");

	JTextField tf1 = new JTextField();
	JTextField tf2 = new JTextField();

	JButton savebut = new JButton("Save Index");
	JButton updatebut = new JButton("Update");
	JButton showbut = new JButton("Show All");
	JButton nextbut = new JButton("Next >>");
	JButton removebut = new JButton("Remove");
	JButton closebut = new JButton("Close X");

	JPanel northp1 = new JPanel();
	JPanel northp2 = new JPanel();
	JPanel northp3 = new JPanel();

	JPanel butpanel1 = new JPanel();
	JPanel butpanel2 = new JPanel();

	JPanel centerpanel = new JPanel();
	JPanel northpanel = new JPanel();

	java.awt.Font fo = new java.awt.Font("Tahoma", 1, 11);
	javax.swing.border.Border line = javax.swing.BorderFactory.createLineBorder(Color.darkGray);
	javax.swing.border.Border bor1 = javax.swing.BorderFactory.createTitledBorder(this.line, "Structural Index", 0, 0,
			this.fo, Color.darkGray);
	javax.swing.border.Border bor2 = javax.swing.BorderFactory.createTitledBorder(this.line, "List of Structural Index",
			2, 2, this.fo, Color.darkGray);

	java.awt.Toolkit tk = java.awt.Toolkit.getDefaultToolkit();
	Dimension ss = this.tk.getScreenSize();
	Dimension fs = this.f.getSize();
	Container c;

	public void px() {
		this.c = this.f.getContentPane();
		this.c.setLayout(new java.awt.BorderLayout());
		this.northp1.setLayout(new java.awt.FlowLayout());
		this.northp2.setLayout(new java.awt.GridLayout(2, 2, 2, 2));
		this.northpanel.setLayout(new java.awt.BorderLayout());
		this.butpanel1.setLayout(new java.awt.FlowLayout());
		this.butpanel2.setLayout(new java.awt.FlowLayout());

		this.centerpanel.setLayout(new java.awt.BorderLayout());

		this.northpanel.setBorder(this.bor1);
		this.sp.setBorder(this.bor2);

		this.northp2.add(this.l1);
		this.northp2.add(this.tf1);
		this.northp2.add(this.l2);
		this.northp2.add(this.tf2);

		this.butpanel1.add(this.savebut);
		this.savebut.setMnemonic(83);
		this.butpanel1.add(this.updatebut);
		this.updatebut.setMnemonic(85);
		this.butpanel1.add(this.showbut);
		this.showbut.setMnemonic(87);
		this.butpanel1.add(this.nextbut);
		this.nextbut.setMnemonic(78);
		this.butpanel2.add(this.removebut);
		this.removebut.setMnemonic(82);
		this.butpanel2.add(this.closebut);
		this.closebut.setMnemonic(67);

		this.northpanel.add(this.northp2, "North");

		this.centerpanel.add(this.butpanel1, "North");
		this.centerpanel.add(this.sp, "Center");
		this.centerpanel.add(this.butpanel2, "South");

		this.c.add(this.northpanel, "North");
		this.c.add(this.centerpanel, "Center");

		this.savebut.addActionListener(this);
		this.updatebut.addActionListener(this);
		this.nextbut.addActionListener(this);
		this.removebut.addActionListener(this);
		this.closebut.addActionListener(this);

		this.tf1.addActionListener(this);
		this.tf2.addActionListener(this);

		this.f.setLocation((this.ss.width - this.fs.width) / 5, (this.ss.height - this.fs.height) / 10);
		this.f.setTitle("Structural Index");
		this.f.setSize(480, 500);
		this.f.setVisible(true);
	}

	public void actionPerformed(ActionEvent paramActionEvent) {
		if (paramActionEvent.getSource() == this.tf1) {
			this.tf2.requestFocus();
			this.tf2.selectAll();
		}
		if ((paramActionEvent.getSource() == this.tf2) || (paramActionEvent.getSource() == this.savebut)) {
			try {
				con = DBConnectionUtil.getConnection();
				this.stat = this.con.createStatement();
				this.stat.executeUpdate(
						"insert into StructIndex values('" + this.tf1.getText() + "','" + this.tf2.getText() + "')  ");

				this.model.addRow(new Object[] { this.tf1.getText(), this.tf2.getText() });
				this.tf1.setText("");
				this.tf2.setText("");
				this.tf1.requestFocus();
				this.tf1.selectAll();
			} catch (Exception localException) {
				javax.swing.JOptionPane.showMessageDialog(this.f, localException.getMessage().toString());
			}
		}
	}
}