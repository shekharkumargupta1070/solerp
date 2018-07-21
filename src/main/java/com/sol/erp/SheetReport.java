package com.sol.erp;

import java.awt.Color;
import java.awt.Dimension;
import java.sql.ResultSet;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.sol.erp.util.DBConnectionUtil;

public class SheetReport implements java.awt.event.ActionListener, java.awt.event.ItemListener {
	

	java.sql.Connection con;

	java.sql.Statement stat;
	ResultSet rs;
	String[] columnNames = { "Sl_No", "Item_Code", "Sheet/Drg No", "Dtl Time", "Chk Time", "Total Time" };
	Object[][] data = new Object[0][];

	java.text.DecimalFormat df = new java.text.DecimalFormat("0.00");

	DefaultTableModel model = new DefaultTableModel(data, columnNames);
	JTable tb = new JTable(model);
	javax.swing.JScrollPane sp = new javax.swing.JScrollPane(tb);

	JInternalFrame f = new JInternalFrame("Sheet Details", true, true, true, true);
	JLabel l1 = new JLabel("Project No");
	JLabel l2 = new JLabel("Item Wise");
	JLabel l3 = new JLabel("Project Name");

	JComboBox tf1 = new JComboBox();
	JComboBox tf2 = new JComboBox();
	JTextField tf3 = new JTextField(15);

	JButton closebut = new JButton("Close");
	JButton printbut = new JButton("Print Report");

	JPanel northp1 = new JPanel();
	JPanel northp2 = new JPanel();
	JPanel northp3 = new JPanel();

	JPanel butpanel1 = new JPanel();
	JPanel butpanel2 = new JPanel();

	JPanel centerpanel = new JPanel();
	JPanel northpanel = new JPanel();
	JPanel titlepanel = new JPanel();
	JLabel title = new JLabel(new javax.swing.ImageIcon("image/sheet.gif"));

	java.awt.Font bigfo = new java.awt.Font("Tahoma", 1, 22);
	java.awt.Font fo = new java.awt.Font("Tahoma", 1, 11);
	javax.swing.border.Border line = javax.swing.BorderFactory.createLineBorder(Color.darkGray);
	javax.swing.border.Border bor1 = javax.swing.BorderFactory.createTitledBorder(line, "Project No", 0, 0,
			fo, Color.darkGray);
	javax.swing.border.Border bor2 = javax.swing.BorderFactory.createTitledBorder(line, "Sheet Report", 0, 0,
			fo, Color.darkGray);

	java.awt.Toolkit tk = java.awt.Toolkit.getDefaultToolkit();
	Dimension ss = tk.getScreenSize();
	Dimension fs = f.getSize();
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
		titlepanel.setLayout(new java.awt.BorderLayout());

		titlepanel.setBackground(Color.white);
		titlepanel.setForeground(Color.blue);
		titlepanel.add(title, "West");
		titlepanel.setPreferredSize(new Dimension(0, 60));
		title.setFont(bigfo);

		northpanel.setBorder(bor1);
		sp.setBorder(bor2);

		tb.setSelectionBackground(new Color(60, 130, 100));
		tb.setSelectionForeground(Color.white);
		tb.setGridColor(new Color(100, 150, 100));
		tb.getTableHeader().setFont(fo);
		tb.getTableHeader().setPreferredSize(new Dimension(30, 25));
		tb.setRowHeight(20);
		tb.setFont(fo);

		tf2.addItem("%");
		tf2.addItem("AAA");

		northp1.add(l1);
		northp1.add(tf1);
		tf1.setFont(fo);
		northp1.add(l2);
		northp1.add(tf2);
		northp1.add(l3);
		northp1.add(tf3);
		tf3.setEditable(false);
		tf3.setFont(fo);

		butpanel2.add(printbut);
		printbut.setMnemonic(80);
		butpanel2.add(closebut);
		closebut.setMnemonic(67);

		northpanel.add(northp1, "North");

		centerpanel.add(northpanel, "North");
		centerpanel.add(sp, "Center");
		centerpanel.add(butpanel2, "South");

		c.add(titlepanel, "North");
		c.add(centerpanel, "Center");

		closebut.addActionListener(this);
		tf1.addItemListener(this);
		tf2.addItemListener(this);

		f.setLocation((ss.width - fs.width) / 8, (ss.height - fs.height) / 35);

		f.setSize(800, 600);
		f.setVisible(true);
	}

	public void projectNo() {
		model.setNumRows(0);
		tf1.addItem("SELECT");

		try {
			con = DBConnectionUtil.getConnection();
			stat = con.createStatement();
			rs = stat.executeQuery("select Distinct(Project_No) from Estimation");
			while (rs.next()) {
				String str = new String(rs.getString(1));
				tf1.addItem(str);
				System.out.println(str);
			}
		} catch (Exception localException) {
			System.out.println(localException);
		}
	}

	public void details() {
		model.setNumRows(0);
		int i = 0;
		try {
			con = DBConnectionUtil.getConnection();
			stat = con.createStatement();
			rs = stat.executeQuery(
					"select Project_name,Client_name,Item_code,drawing_no,dtl_Time,Chk_Time,total_Time from drawingno where Project_no='"
							+ tf1.getSelectedItem() + "' and item_code LIKE '" + tf2.getSelectedItem()
							+ "'   ");
			rs.getMetaData().getColumnCount();
			while (rs.next()) {
				String str1 = new String(rs.getString(1));
				new String(rs.getString(2));
				String str3 = new String(rs.getString(3));
				String str4 = new String(rs.getString(4));
				String str5 = new String(rs.getString(5));
				String str6 = new String(rs.getString(6));
				String str7 = new String(rs.getString(7));
				tf3.setText(str1);
				i += 1;

				model.addRow(new Object[] { String.valueOf(i), str3, str4, str5, str6, str7 });
			}
		} catch (Exception localException) {
			System.out.println(localException);
		}
	}

	public void itemList() {
		tf2.removeAllItems();
		tf2.addItem("%");
		try {
			con = DBConnectionUtil.getConnection();
			stat = con.createStatement();
			rs = stat.executeQuery(
					"select distinct(item_code) from drawingno where project_no='" + tf1.getSelectedItem() + "' ");
			rs.getMetaData().getColumnCount();
			while (rs.next()) {
				String str = new String(rs.getString(1));
				tf2.addItem(String.valueOf(str));
			}
		} catch (Exception localException) {
			System.out.println(localException);
		}
	}

	public void actionPerformed(java.awt.event.ActionEvent paramActionEvent) {
		if (paramActionEvent.getSource() == tf1) {
			itemList();
			details();
		}
		if (paramActionEvent.getSource() == closebut) {
			f.setVisible(false);
		}
	}

	public void itemStateChanged(java.awt.event.ItemEvent paramItemEvent) {
		if (paramItemEvent.getSource() == tf1) {
			itemList();
			details();
			tb.selectAll();
		}
		if (paramItemEvent.getSource() == tf2) {
			details();
			tb.selectAll();
		}
	}

}
