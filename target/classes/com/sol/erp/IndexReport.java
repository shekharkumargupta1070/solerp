package com.sol.erp;

import java.awt.Choice;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ItemEvent;
import java.sql.ResultSet;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import com.sol.erp.util.DBConnectionUtil;

public class IndexReport implements java.awt.event.ActionListener, java.awt.event.ItemListener {
	

	java.sql.Connection con;

	java.sql.Statement stat;
	ResultSet rs;
	String[] columnNames = { "SL", "Item Code", "Drg No", "Format", "Remarks" };
	Object[][] data = new Object[0][];

	java.text.DecimalFormat df = new java.text.DecimalFormat("00.00");

	DefaultTableModel model = new DefaultTableModel(data, columnNames);
	JTable tb = new JTable(model);
	javax.swing.JScrollPane sp = new javax.swing.JScrollPane(tb);

	JDialog f = new JDialog();
	JLabel l1 = new JLabel("Project No");
	JLabel l2 = new JLabel("Project Name");

	Choice tf1 = new Choice();
	javax.swing.JTextField tf2 = new javax.swing.JTextField(15);

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

	TableColumn remarkColumn = tb.getColumnModel().getColumn(4);
	TableColumn slColumn = tb.getColumnModel().getColumn(0);

	java.awt.Font bigfo = new java.awt.Font("Tahoma", 1, 22);
	java.awt.Font fo = new java.awt.Font("Tahoma", 1, 11);
	javax.swing.border.Border line = javax.swing.BorderFactory.createLineBorder(Color.darkGray);
	javax.swing.border.Border bor1 = javax.swing.BorderFactory.createTitledBorder(line, "Project No", 0, 0,
			fo, Color.darkGray);
	javax.swing.border.Border bor2 = javax.swing.BorderFactory.createTitledBorder(line, "Indexing Details", 0, 0,
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

		northpanel.setBorder(bor1);
		sp.setBorder(bor2);

		tb.setSelectionBackground(new Color(60, 130, 100));
		tb.setSelectionForeground(Color.white);
		tb.setGridColor(new Color(100, 150, 100));
		tb.getTableHeader().setFont(fo);
		tb.getTableHeader().setPreferredSize(new Dimension(30, 20));
		tb.setRowHeight(18);
		tb.setFont(fo);

		northp1.add(l1);
		northp1.add(tf1);
		tf1.setFont(fo);

		butpanel2.add(printbut);
		printbut.setMnemonic(80);
		butpanel2.add(closebut);
		closebut.setMnemonic(67);

		northpanel.add(northp1, "North");

		centerpanel.add(northpanel, "North");
		centerpanel.add(sp, "Center");
		centerpanel.add(butpanel2, "South");

		remarkColumn.setPreferredWidth(460);
		slColumn.setPreferredWidth(6);

		c.add(centerpanel, "Center");

		closebut.addActionListener(this);
		tf1.addItemListener(this);

		f.setLocation((ss.width - fs.width) / 900, (ss.height - fs.height) / 18);
		f.setTitle("Index Details");

		f.setSize(1024, 650);
		f.setVisible(true);
	}

	public void projectNo() {
		model.setNumRows(0);
		try {
			con = DBConnectionUtil.getConnection();
			stat = con.createStatement();
			rs = stat.executeQuery("select Distinct(Project_No) from Estimation");
			while (rs.next()) {
				String str = new String(rs.getString(1));
				tf1.addItem(str);
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
			rs = stat
					.executeQuery("select Item_code,drg_no,drg_title,format,remarks from drg_index where Project_no='"
							+ tf1.getSelectedItem() + "'  ");
			while (rs.next()) {

				String str1 = new String(rs.getString(1));
				String str2 = new String(rs.getString(2));
				String str3 = new String(rs.getString(3));
				String str4 = new String(rs.getString(4));
				String str5 = new String(rs.getString(5));

				i += 1;

				model.addRow(new Object[] { String.valueOf(i), str1, str2, str3, str4, str5 });
			}
		} catch (Exception localException) {
			System.out.println(localException);
		}
	}

	public void actionPerformed(java.awt.event.ActionEvent paramActionEvent) {
		if (paramActionEvent.getSource() == tf1) {
			details();
		}
		if (paramActionEvent.getSource() == closebut) {
			f.setVisible(false);
		}
	}

	public void itemStateChanged(ItemEvent paramItemEvent) {
		if (paramItemEvent.getSource() == tf1) {
			details();
			tb.selectAll();
		}
		if (paramItemEvent.getSource() == tf2) {
			details();
			tb.selectAll();
		}
	}

}
