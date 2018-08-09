package com.sol.erp;

import java.awt.BorderLayout;
import java.awt.Container;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class SalarySlab {
	
	DeptList dep = new DeptList();
	DesigList des = new DesigList();
	ReqModeList res = new ReqModeList();

	Connection con;

	Statement stat;
	ResultSet rs;
	String[] head = { "Level" };
	Object[][] data = new Object[0][];

	String[] head1 = { "SubLevel", "Amount" };
	Object[][] data1 = new Object[0][];

	JFrame f = new JFrame("Salary Slab/Level");
	JLabel colabel = new JLabel("Company_Id");
	JLabel branchlabel = new JLabel("Branch_Code");

	JPanel northp1 = new JPanel();
	JPanel centerp1 = new JPanel();
	JPanel southp1 = new JPanel();

	DefaultTableModel model = new DefaultTableModel(this.data, this.head);
	JTable tb = new JTable(this.model);
	JScrollPane sp = new JScrollPane(this.tb);

	DefaultTableModel model1 = new DefaultTableModel(this.data1, this.head1);
	JTable tb1 = new JTable(this.model1);
	JScrollPane sp1 = new JScrollPane(this.tb1);

	JButton savebut = new JButton("Save");
	JButton updatebut = new JButton("Update");
	JButton closebut = new JButton("Close");

	public void px() {
		Container localContainer = this.f.getContentPane();
		localContainer.setLayout(new BorderLayout());
		this.centerp1.setLayout(new java.awt.GridLayout(1, 4, 5, 5));

		this.northp1.add(this.colabel);
		this.northp1.add(this.branchlabel);

		this.sp.setPreferredSize(new java.awt.Dimension(50, 50));

		this.centerp1.add(this.sp);
		this.centerp1.add(this.sp1);

		this.southp1.add(this.savebut);
		this.southp1.add(this.updatebut);
		this.southp1.add(this.closebut);

		localContainer.add(this.northp1, "North");
		localContainer.add(this.centerp1, "Center");
		localContainer.add(this.southp1, "South");

		this.f.setSize(300, 400);
		this.f.setVisible(true);
	}

}
	