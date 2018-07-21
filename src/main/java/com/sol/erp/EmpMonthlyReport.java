package com.sol.erp;

import java.awt.Color;
import java.awt.Dimension;
import java.sql.ResultSet;
import java.text.DecimalFormat;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;

import com.sol.erp.util.DBConnectionUtil;

public class EmpMonthlyReport implements java.awt.event.ActionListener, java.awt.event.ItemListener {

	java.sql.Connection con;

	java.sql.Statement stat;
	ResultSet rs;
	String[] columnNames = { "Project No", "Item Code", "Drawing No", "E_Time", "Taken Time", "Factor" };
	Object[][] data = new Object[0][];

	String[] months = { "January", "February", "March", "April", "May", "June", "July", "August", "September",
			"October", "November", "December" };

	DecimalFormat df = new DecimalFormat("00.00");

	javax.swing.table.DefaultTableModel model = new javax.swing.table.DefaultTableModel(this.data, this.columnNames);
	JTable tb = new JTable(this.model);
	javax.swing.JScrollPane sp = new javax.swing.JScrollPane(this.tb);

	JFrame f = new JFrame();
	JLabel l1 = new JLabel("Emp Code");
	JLabel l2 = new JLabel("Password");
	JLabel l3 = new JLabel("Designation");
	JLabel l4 = new JLabel("Unit No");
	JLabel l5 = new JLabel("Factor");

	JLabel l6 = new JLabel("Factor Report");

	JComboBox monthcb = new JComboBox();
	JTextField tf1 = new JTextField(8);
	javax.swing.JPasswordField tf2 = new javax.swing.JPasswordField(8);
	JTextField tf3 = new JTextField(12);
	JTextField tf4 = new JTextField(5);
	JTextField tf5 = new JTextField(3);
	JTextField tf6 = new JTextField(8);

	JButton showbut = new JButton("Show Status");
	JButton updatebut = new JButton("Update Factor");
	JButton closebut = new JButton("Close", new javax.swing.ImageIcon("image/close.gif"));

	JPanel northp1 = new JPanel();
	JPanel northp2 = new JPanel();
	JPanel northp3 = new JPanel();

	JPanel butpanel1 = new JPanel();
	JPanel butpanel2 = new JPanel();

	JPanel centerpanel = new JPanel();
	JPanel northpanel = new JPanel();

	java.awt.Font fo = new java.awt.Font("Tahoma", 1, 11);
	javax.swing.border.Border line = javax.swing.BorderFactory.createLineBorder(Color.darkGray);
	javax.swing.border.Border bor1 = javax.swing.BorderFactory.createTitledBorder(this.line, "EMP Code", 0, 0, this.fo,
			Color.darkGray);
	javax.swing.border.Border bor2 = javax.swing.BorderFactory.createTitledBorder(this.line, "Work Allotment Details",
			0, 0, this.fo, Color.darkGray);

	java.awt.Toolkit tk = java.awt.Toolkit.getDefaultToolkit();
	Dimension ss = this.tk.getScreenSize();
	Dimension fs = this.f.getSize();
	java.awt.Container c;

	public void px() {
		this.c = this.f.getContentPane();
		this.c.setLayout(new java.awt.BorderLayout());
		this.northp1.setLayout(new java.awt.FlowLayout());
		this.northp2.setLayout(new java.awt.GridLayout(2, 2, 2, 2));
		this.northpanel.setLayout(new java.awt.BorderLayout());
		this.butpanel1.setLayout(new java.awt.FlowLayout());
		this.butpanel2.setLayout(new java.awt.FlowLayout());
		this.centerpanel.setLayout(new java.awt.BorderLayout());

		for (int i = 0; i < this.months.length; i++) {
			this.monthcb.addItem(this.months[i]);
		}

		this.tb.setFont(this.fo);
		this.tb.setBackground(Color.white);
		this.tb.setForeground(Color.black);
		this.tb.getTableHeader().setPreferredSize(new Dimension(50, 20));
		this.tb.getTableHeader().setFont(this.fo);
		this.tb.setRowHeight(20);
		this.tb.setSelectionBackground(new Color(100, 150, 100));
		this.tb.setSelectionForeground(Color.white);
		this.tb.setGridColor(new Color(100, 150, 100));

		this.northpanel.setBorder(this.bor1);
		this.sp.setBorder(this.bor2);

		this.northp1.add(this.monthcb);
		this.northp1.add(this.l1);
		this.northp1.add(this.tf1);
		this.tf1.setFont(this.fo);
		this.tf1.setEditable(false);

		this.northp1.add(this.l3);
		this.northp1.add(this.tf3);
		this.tf3.setFont(this.fo);
		this.northp1.add(this.l4);
		this.northp1.add(this.tf4);
		this.tf4.setFont(this.fo);
		this.northp1.add(this.l5);
		this.northp1.add(this.tf5);
		this.tf5.setFont(this.fo);

		this.tf3.setEditable(false);
		this.tf4.setEditable(false);
		this.tf5.setEditable(false);
		this.tf6.setEditable(false);

		this.butpanel2.add(this.l6);
		this.butpanel2.add(this.tf6);
		this.tf6.setFont(this.fo);

		this.butpanel2.add(this.closebut);
		this.closebut.setMnemonic(67);

		this.northpanel.add(this.northp1, "North");
		this.centerpanel.add(this.sp, "Center");
		this.centerpanel.add(this.butpanel2, "South");

		this.c.add(this.northpanel, "North");
		this.c.add(this.centerpanel, "Center");

		this.monthcb.addItemListener(this);
		this.showbut.addActionListener(this);
		this.updatebut.addActionListener(this);
		this.closebut.addActionListener(this);

		this.tf1.addActionListener(this);

		this.f.setLocation((this.ss.width - this.fs.width) / 800, (this.ss.height - this.fs.height) / 6);
		this.f.setTitle("EMP Monthly Report");
		this.f.setSize(800, 400);
		this.f.setVisible(true);

		this.tf1.setText(TechMainFrame.textFieldLoggedBy.getText());
		this.tf3.setText(TechMainFrame.textFieldPost.getText());
		this.tf4.setText(TechMainFrame.stf5.getText());
	}

	public void empDetails() {
		try {
			con = DBConnectionUtil.getConnection();
			this.stat = this.con.createStatement();
			this.rs = this.stat
					.executeQuery("select Factor from emp_status where EMP_Code='" + this.tf1.getText() + "' ");
			while (this.rs.next()) {
				String str = new String(this.rs.getString(1));

				this.tf5.setText(str);

				float f1 = Float.parseFloat(str);
				if (f1 < 75.0F) {
					this.tf5.setForeground(Color.red);
				}
				if (f1 >= 75.0F) {
					this.tf5.setForeground(Color.blue);
				}
			}
		} catch (Exception localException) {
			System.out.println(localException);
		}
	}

	public void report() {
		this.model.setNumRows(0);
		float f1 = 0.0F;
		try {
			this.stat = this.con.createStatement();
			this.rs = this.stat.executeQuery(
					"select Project_No,Item_Code,Drawing_No,E_Time,Dtl_Time,Dtl_Shoot from FINALDB where DETAILING = '"
							+ this.tf1.getText() + "'   ");
			while (this.rs.next()) {
				String str1 = new String(this.rs.getString(1));
				String str2 = new String(this.rs.getString(2));
				String str3 = new String(this.rs.getString(3));
				String str4 = new String(this.rs.getString(4));
				String str5 = new String(this.rs.getString(5));
				String str6 = new String(this.rs.getString(6));

				float f3 = Float.parseFloat(str5);
				float f4 = Float.parseFloat(str6);
				this.model.addRow(new Object[] { str1, str2, str3, str4, this.df.format(f3), this.df.format(f4) });
				f1 += f4;
			}
			int k = this.tb.getRowCount();
			float f2 = f1 / k;
			this.tf6.setText(String.valueOf(this.df.format(f2)));
		} catch (Exception localException) {
			System.out.println(localException);
		}
	}

	public void actionPerformed(java.awt.event.ActionEvent paramActionEvent) {
		if (paramActionEvent.getSource() == this.closebut) {
			this.f.setVisible(false);
		}
	}

	public void itemStateChanged(java.awt.event.ItemEvent paramItemEvent) {
		if (paramItemEvent.getSource() == this.monthcb) {
			empDetails();
			report();
		}
	}
}
