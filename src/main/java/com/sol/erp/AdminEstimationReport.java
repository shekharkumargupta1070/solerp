package com.sol.erp;

import java.awt.Color;
import java.awt.Dimension;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.sol.erp.util.DBConnectionUtil;

public class AdminEstimationReport implements java.awt.event.ActionListener {

	
	ResultSet rs;
	String[] columnNames = { "Item_Code", "Item Qty", "Sheet Size", "Sheet Qty", "Dtl Time", "Chk Time",
			"PerSheet Time", "Total Time", "Complexity" };
	Object[][] data = new Object[0][];

	//java.text.DecimalFormat df = new java.text.DecimalFormat("0.00");

	DefaultTableModel model = new DefaultTableModel(data, columnNames);
	JTable tb = new JTable(model);
	javax.swing.JScrollPane sp = new javax.swing.JScrollPane(tb);

	JDialog f = new JDialog();
	JLabel labelProjectNumber = new JLabel("Project No");
	JLabel labelProjectName = new JLabel("Project Name");
	JLabel labelClientName = new JLabel("Client Name");

	JComboBox textFieldProjectNumber = new JComboBox();
	JTextField textFieldProjectName = new JTextField(15);
	JTextField textFieldClientName = new JTextField(15);

	JButton buttonShow = new JButton("Show Details");
	JButton buttonClose = new JButton("Close", new javax.swing.ImageIcon("image/close.gif"));
	JButton buttonPrint = new JButton("Print Report");

	JPanel northp1 = new JPanel();
	JPanel northp2 = new JPanel();
	JPanel northp3 = new JPanel();

	JPanel butpanel1 = new JPanel();
	JPanel butpanel2 = new JPanel();

	JPanel centerpanel = new JPanel();
	JPanel northpanel = new JPanel();
	JPanel titlepanel = new JPanel();
	JLabel title = new JLabel(new javax.swing.ImageIcon("image/admin.gif"));

	java.awt.Font bigfo = new java.awt.Font("Tahoma", 1, 22);
	java.awt.Font fo = new java.awt.Font("Tahoma", 1, 11);
	javax.swing.border.Border line = javax.swing.BorderFactory.createLineBorder(Color.darkGray);
	javax.swing.border.Border bor1 = javax.swing.BorderFactory.createTitledBorder(line, "Project No", 0, 0, fo,
			Color.darkGray);
	javax.swing.border.Border bor2 = javax.swing.BorderFactory.createTitledBorder(line, "Project Estimation Details", 0,
			0, fo, Color.darkGray);

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

		tb.setSelectionBackground(new Color(100, 150, 100));
		tb.setSelectionForeground(Color.white);
		tb.setGridColor(new Color(100, 150, 100));
		tb.getTableHeader().setFont(fo);
		tb.getTableHeader().setPreferredSize(new Dimension(30, 25));
		tb.setRowHeight(20);
		tb.setFont(fo);

		northp1.add(labelProjectNumber);
		northp1.add(textFieldProjectNumber);
		textFieldProjectNumber.setFont(fo);
		northp1.add(labelProjectName);
		northp1.add(textFieldProjectName);
		textFieldProjectName.setEditable(false);
		textFieldProjectName.setFont(fo);
		northp1.add(labelClientName);
		northp1.add(textFieldClientName);
		textFieldClientName.setEditable(false);
		textFieldClientName.setFont(fo);

		butpanel2.add(buttonPrint);
		buttonPrint.setMnemonic(80);
		butpanel2.add(buttonClose);
		buttonClose.setMnemonic(67);

		northp1.add(buttonShow);
		buttonShow.setMnemonic(83);
		northpanel.add(northp1, "North");

		centerpanel.add(northpanel, "North");
		centerpanel.add(sp, "Center");
		centerpanel.add(butpanel2, "South");

		c.add(titlepanel, "North");
		c.add(centerpanel, "Center");

		buttonShow.addActionListener(this);
		buttonClose.addActionListener(this);
		textFieldProjectNumber.addActionListener(this);

		f.setLocation((ss.width - fs.width) / 900, (ss.height - fs.height) / 18);
		f.setTitle("Estimation Details");

		f.setSize(800, 580);
		f.setVisible(true);
	}

	public void projectNo() {
		model.setNumRows(0);
		Connection con = null;
		Statement stat = null;
		ResultSet rs = null;
		try {
			con = DBConnectionUtil.getConnection();
			stat = con.createStatement();
			rs = stat.executeQuery("select Distinct(Project_No) from Estimation_Admin");

			while (rs.next()) {
				String str = new String(rs.getString(1));
				textFieldProjectNumber.addItem(str);
			}
		} catch (Exception localException) {
			System.out.println(localException);
		}
		finally{
		    DBConnectionUtil.closeConnection(rs, stat, con);
		}
	}

	public void details() {
		model.setNumRows(0);
		Connection con = null;
        Statement stat = null;
        ResultSet rs = null;
		try {
			con = DBConnectionUtil.getConnection();
			stat = con.createStatement();
			rs = stat.executeQuery(
					"select Project_name,Client_name,Item_code,Item_Qty,Sheet_Size,Sheet_Qty,Dtl_Time,Chk_Time,PerSheet_Time,Total_Time,Complexity from Estimation_Admin where Project_no='"
							+ textFieldProjectNumber.getSelectedItem() + "' ");
			rs.getMetaData().getColumnCount();
			while (rs.next()) {
				String str1 = new String(rs.getString(1));
				String str2 = new String(rs.getString(2));
				String str3 = new String(rs.getString(3));
				String str4 = new String(rs.getString(4));
				String str5 = new String(rs.getString(5));
				String str6 = new String(rs.getString(6));
				String str7 = new String(rs.getString(7));
				String str8 = new String(rs.getString(8));
				String str9 = new String(rs.getString(9));
				String str10 = new String(rs.getString(10));
				String str11 = new String(rs.getString(11));

				textFieldProjectName.setText(str1);
				textFieldClientName.setText(str2);

				model.addRow(new Object[] { str3, str4, str5, str6, str7, str8, str9, str10, str11 });
			}
		} catch (Exception localException) {
			System.out.println(localException);
		} finally{
		    DBConnectionUtil.closeConnection(rs, stat, con);
		}
	}

	public void summary() {
	    Connection con = null;
        Statement stat = null;
        ResultSet rs = null;
		try {
			con = DBConnectionUtil.getConnection();
			stat = con.createStatement();
			rs = stat.executeQuery(
					"select Item_Qty,Sheet_Qty,Dtl_Time,Chk_Time,Total_Time,C_Level from Estimation_Summary where Project_no='"
							+ textFieldProjectNumber.getSelectedItem() + "' ");
			rs.getMetaData().getColumnCount();
			while (rs.next()) {
				String str1 = new String(rs.getString(1));
				String str2 = new String(rs.getString(2));
				String str3 = new String(rs.getString(3));
				String str4 = new String(rs.getString(4));
				String str5 = new String(rs.getString(5));
				String str6 = new String(rs.getString(6));

				model.addRow(new Object[] { "", str1, "", str2, str3, str4, "", str5, str6 });
			}
		} catch (Exception localException) {
			System.out.println(localException);
		} finally{
		    DBConnectionUtil.closeConnection(rs, stat, con);
		}
	}

	public void actionPerformed(java.awt.event.ActionEvent paramActionEvent) {
		if ((paramActionEvent.getSource() == textFieldProjectNumber) || (paramActionEvent.getSource() == buttonShow)) {
			details();
			summary();
		}
		if (paramActionEvent.getSource() == buttonClose) {
			f.setVisible(false);
		}
	}
}