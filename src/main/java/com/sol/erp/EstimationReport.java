package com.sol.erp;

import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.text.DecimalFormat;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.sol.erp.util.DBConnectionUtil;

public class EstimationReport implements java.awt.event.ActionListener {
	

	Connection con;

	java.sql.Statement stat;
	ResultSet rs;
	String[] columnNames = { "Item_Code", "Item Qty", "Sheet Size", "Sheet Qty", "Dtl Time", "Chk Time",
			"PerSheet Time", "Total Time", "Complexity" };
	Object[][] data = new Object[0][];

	DecimalFormat df = new DecimalFormat("00.00");
	DecimalFormat df2 = new DecimalFormat("00");
	DecimalFormat df3 = new DecimalFormat("000");

	DefaultTableModel model = new DefaultTableModel(data, columnNames);
	JTable tb = new JTable(model);
	javax.swing.JScrollPane sp = new javax.swing.JScrollPane(tb);

	javax.swing.table.TableColumnModel tcm = tb.getColumnModel();
	javax.swing.table.TableColumn tc = tcm.getColumn(2);

	JInternalFrame f = new JInternalFrame("TL ESTIMATION REPORT", true, true, true, true);
	JLabel l1 = new JLabel("Project No");
	JLabel l2 = new JLabel("Project Name");
	JLabel l3 = new JLabel("Client Name");

	JComboBox tf1 = new JComboBox();
	JTextField tf2 = new JTextField(15);
	JTextField tf3 = new JTextField(15);

	JButton showbut = new JButton("Show Details");
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
	JLabel title = new JLabel("TL Estimation Details");

	java.awt.Font bigfo = new java.awt.Font("Courier New", 1, 26);
	java.awt.Font fo = new java.awt.Font("arial", 1, 11);
	javax.swing.border.Border line = javax.swing.BorderFactory.createLineBorder(Color.darkGray);
	javax.swing.border.Border bor1 = javax.swing.BorderFactory.createTitledBorder(line, "Project No", 0, 0,
			fo, Color.darkGray);
	javax.swing.border.Border bor2 = javax.swing.BorderFactory.createTitledBorder(line,
			"Project Estimation Details", 0, 0, fo, Color.darkGray);

	java.awt.Toolkit tk = java.awt.Toolkit.getDefaultToolkit();
	java.awt.Dimension ss = tk.getScreenSize();
	java.awt.Dimension fs = f.getSize();

	JTextField tbtf = new JTextField();
	Container c;

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

		titlepanel.setBackground(new Color(60, 130, 130));
		titlepanel.add(title, "West");
		titlepanel.setPreferredSize(new java.awt.Dimension(0, 60));
		title.setFont(bigfo);
		title.setForeground(Color.white);

		tbtf.setBackground(Color.red);
		tbtf.setFont(fo);

		tb.setCellEditor(new javax.swing.DefaultCellEditor(tbtf));
		tc.setCellRenderer(new javax.swing.table.DefaultTableCellRenderer());

		tb.setSelectionMode(2);

		northpanel.setBorder(bor1);
		sp.setBorder(bor2);

		tb.setSelectionBackground(new Color(60, 130, 130));
		tb.setSelectionForeground(Color.white);
		tb.getTableHeader().setFont(fo);
		tb.getTableHeader().setPreferredSize(new java.awt.Dimension(30, 25));
		tb.setRowHeight(20);
		tb.setFont(fo);

		northp1.add(l1);
		northp1.add(tf1);
		tf1.setFont(fo);
		northp1.add(l2);
		northp1.add(tf2);
		tf2.setEditable(false);
		tf2.setFont(fo);
		northp1.add(l3);
		northp1.add(tf3);
		tf3.setEditable(false);
		tf3.setFont(fo);

		butpanel2.add(printbut);
		printbut.setMnemonic(80);
		butpanel2.add(closebut);
		closebut.setMnemonic(67);

		northp1.add(showbut);
		showbut.setMnemonic(83);
		northpanel.add(northp1, "North");

		centerpanel.add(northpanel, "North");
		centerpanel.add(sp, "Center");
		centerpanel.add(butpanel2, "South");

		c.add(titlepanel, "North");
		c.add(centerpanel, "Center");

		showbut.addActionListener(this);
		closebut.addActionListener(this);
		printbut.addActionListener(this);
		tf1.addActionListener(this);

		f.setSize(1024, 580);
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

	float sumsl = 0.0F;
	float sumitemqty = 0.0F;
	float sumsheetqty = 0.0F;
	float sumdtltime = 0.0F;
	float sumchktime = 0.0F;
	float sumhrs = 0.0F;
	float complexity = 0.0F;
	float flstr4;
	float flstr6;
	float flstr7;
	float flstr8;
	float flstr10;

	public void details() {
		model.setNumRows(0);
		try {
			stat = con.createStatement();
			rs = stat.executeQuery(
					"select Project_name,Client_name,Item_code,Item_Qty,Sheet_Size,Sheet_Qty,Dtl_Time,Chk_Time,PerSheet_Time,Total_Time,Complexity from OLD_TL_Estimation where Project_no='"
							+ tf1.getSelectedItem() + "' ");
			rs.getMetaData().getColumnCount();
			while (rs.next()) {
				String str1 = new String(rs.getString(1));
				String str2 = new String(rs.getString(2));
				String str3 = new String(rs.getString(3));
				flstr4 = Float.parseFloat(rs.getString(4));
				String str4 = new String(rs.getString(5));
				flstr6 = Float.parseFloat(rs.getString(6));
				flstr7 = Float.parseFloat(rs.getString(7));
				flstr8 = Float.parseFloat(rs.getString(8));
				String str5 = new String(rs.getString(9));
				flstr10 = Float.parseFloat(rs.getString(10));
				flstr11 = Float.parseFloat(rs.getString(11));

				tf2.setText(str1);
				tf3.setText(str2);
				model.addRow(new Object[] { str3, String.valueOf(df3.format(flstr4)), str4,
						String.valueOf(df3.format(flstr6)), String.valueOf(flstr7),
						String.valueOf(flstr8), str5, String.valueOf(flstr10),	
						String.valueOf(flstr11) });

				sumitemqty += flstr4;
				sumsheetqty += flstr6;
				sumdtltime += flstr7;
				sumchktime += flstr8;
				sumhrs += flstr10;
				complexity = (sumhrs / sumsheetqty);

				model.getRowCount();

				String str6 = String.valueOf(df.format(sumchktime));
				System.out.println(str6);

				int k = str6.indexOf('.');
				int m = str6.length();
				String str7 = str6.substring(0, k);
				String str8 = str6.substring(k + 1, m);

				int n = Integer.parseInt(str7);
				int i1 = Integer.parseInt(str8);
				int i2 = i1 / 60 + n;
				int i3 = i1 % 60;

				ttlchk = (String.valueOf(df2.format(i2)) + "." + String.valueOf(df2.format(i3)));

				String str9 = String.valueOf(df.format(sumdtltime));
				System.out.println(str6);

				int i4 = str9.indexOf('.');
				int i5 = str9.length();
				String str10 = str9.substring(0, i4);
				String str11 = str9.substring(i4 + 1, i5);

				int i6 = Integer.parseInt(str10);
				int i7 = Integer.parseInt(str11);
				int i8 = i7 / 60 + i6;
				int i9 = i7 % 60;

				ttldtl = (String.valueOf(df2.format(i8)) + "." + String.valueOf(df2.format(i9)));

				String str12 = String.valueOf(df.format(sumhrs));
				System.out.println(str12);

				int i10 = str12.indexOf('.');
				int i11 = str12.length();
				String str13 = str12.substring(0, i10);
				String str14 = str12.substring(i10 + 1, i11);

				int i12 = Integer.parseInt(str13);
				int i13 = Integer.parseInt(str14);
				int i14 = i13 / 60 + i12;
				int i15 = i13 % 60;

				ttlhrs = (String.valueOf(df2.format(i14)) + "." + String.valueOf(df2.format(i15)));

				String str15 = String.valueOf(df.format(complexity));
				System.out.println(str15);

				int i16 = str15.indexOf('.');
				int i17 = str15.length();
				String str16 = str15.substring(0, i16);
				String str17 = str15.substring(i16 + 1, i17);

				int i18 = Integer.parseInt(str16);
				int i19 = Integer.parseInt(str17);
				int i20 = i19 / 60 + i18;
				int i21 = i19 % 60;

				ttlcom = (String.valueOf(df2.format(i20)) + "." + String.valueOf(df2.format(i21)));
			}

			model.addRow(new Object[] { "", String.valueOf(df3.format(sumitemqty)), "",
					String.valueOf(df3.format(sumsheetqty)), ttldtl, ttlchk, "", ttlhrs,
					ttlcom });

			sumsl = 0.0F;
			sumitemqty = 0.0F;
			sumsheetqty = 0.0F;
			sumdtltime = 0.0F;
			sumchktime = 0.0F;
			sumhrs = 0.0F;
			complexity = 0.0F;
			flstr4 = 0.0F;
			flstr6 = 0.0F;
			flstr7 = 0.0F;
			flstr8 = 0.0F;
			flstr10 = 0.0F;
			flstr11 = 0.0F;
		} catch (Exception localException) {
		}
	}

	float flstr11;
	String ttldtl;
	String ttlchk;
	String ttlhrs;
	String ttlcom;

	public void summary() {
		try {
			stat = con.createStatement();
			rs = stat.executeQuery(
					"select Item_Qty,Sheet_Qty,Dtl_Time,Chk_Time,Total_Time,C_Level from Estimation_Summary where Project_no='"
							+ tf1.getSelectedItem() + "' ");
			rs.getMetaData().getColumnCount();
			while (rs.next()) {
				String str1 = new String(rs.getString(1));
				String str2 = new String(rs.getString(2));
				float f1 = Float.parseFloat(rs.getString(3));
				float f2 = Float.parseFloat(rs.getString(4));
				float f3 = Float.parseFloat(rs.getString(5));
				float f4 = Float.parseFloat(rs.getString(6));

				model.addRow(new Object[] { "", str1, "", str2, String.valueOf(df.format(f1)),
						String.valueOf(df.format(f2)), "", String.valueOf(df.format(f3)),
						String.valueOf(df.format(f4)) });
			}
		} catch (Exception localException) {
			System.out.println(localException);
		}
	}

	public void actionPerformed(ActionEvent paramActionEvent) {
		if ((paramActionEvent.getSource() == tf1) || (paramActionEvent.getSource() == showbut)) {
			details();
		}

		if (paramActionEvent.getSource() == printbut) {

			int i = tb.getRowCount();
			for (int j = 0;

			j < i; j++) {
				if (j == 2) {
					tb.setBackground(Color.gray);
				}
				if (j == 4) {
					tb.setBackground(Color.gray);
				}
			}
		}
		if (paramActionEvent.getSource() == closebut) {
			f.setVisible(false);
		}
	}
}
