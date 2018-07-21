package com.sol.erp;

import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DecimalFormat;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.sol.erp.util.DBConnectionUtil;

public class JobProcess implements java.awt.event.ActionListener, java.awt.event.KeyListener,
		java.awt.event.MouseListener, java.awt.event.ItemListener {
	

	Connection con;
	Statement stat;
	ResultSet rs;
	String projectcodestr = "";
	String itemcodestr = "";
	String draw_nostr = "";
	String emp_codestr = "";

	String[] columnNames1 = { "Project No", "Item Code", "Draw No", "Date", "E_Time", "Status", "Checker" };
	Object[][] data1 = new Object[0][];

	DecimalFormat df = new DecimalFormat("00.00");
	DecimalFormat df1 = new DecimalFormat("00");
	DecimalFormat timeformat = new DecimalFormat("0000");

	int v = 20;
	int h = 32;

	DefaultTableModel model1 = new DefaultTableModel(data1, columnNames1);
	JTable tb1 = new JTable(model1);
	javax.swing.JScrollPane sp1 = new javax.swing.JScrollPane(tb1);

	JFrame f = new JFrame();
	JLabel l5 = new JLabel("Search");
	JLabel l4 = new JLabel("Job");
	JLabel unitlabel = new JLabel("Unit No");
	JLabel emplabel = new JLabel("Emp Id");
	JLabel rowlabel = new JLabel("Rows : ");

	JTextField unittf = new JTextField(5);
	JComboBox empcb = new JComboBox();
	JComboBox dtlcb = new JComboBox();
	JComboBox statuscb = new JComboBox();

	JLabel northimagelabel = new JLabel(new javax.swing.ImageIcon("Image/top.gif"), 2);
	JLabel westimagelabel = new JLabel(new javax.swing.ImageIcon("Image/left1.gif"), 2);

	JButton showallbut = new JButton("Show All", new javax.swing.ImageIcon("image/LIST.gif"));
	JButton completebut = new JButton("Complete", new javax.swing.ImageIcon("image/complete1.gif"));
	JButton startbut = new JButton("Start", new javax.swing.ImageIcon("image/yes.gif"));
	JButton revisionbut = new JButton("Revise", new javax.swing.ImageIcon("image/revise.gif"));
	JButton finalbut = new JButton("Final", new javax.swing.ImageIcon("image/final.gif"));
	JButton closebut = new JButton("Close", new javax.swing.ImageIcon("image/close.gif"));

	java.util.GregorianCalendar gcal = new java.util.GregorianCalendar();
	int currentdt = gcal.get(5);
	int currentmn = gcal.get(2) + 1;
	int currentyr = gcal.get(1);

	String datestr = String.valueOf(df1.format(currentdt)) + "/"
			+ String.valueOf(df1.format(currentmn)) + "/" + String.valueOf(df1.format(currentyr));

	JPanel northp1 = new JPanel();
	JPanel northp2 = new JPanel();
	JPanel northp3 = new JPanel();

	JPanel butpanel1 = new JPanel();
	JPanel butpanel2 = new JPanel();

	JPanel centerpanel = new JPanel();
	JPanel northpanel = new JPanel();

	java.awt.Font fo = new java.awt.Font("Arial", 1, 11);
	javax.swing.border.Border line = javax.swing.BorderFactory.createLineBorder(java.awt.Color.black);
	javax.swing.border.Border bor1 = javax.swing.BorderFactory.createTitledBorder(line, "Project Info", 0, 0,
			fo, java.awt.Color.darkGray);
	javax.swing.border.Border bor2 = javax.swing.BorderFactory.createTitledBorder(line, "Drawing Updates", 0, 0,
			fo, java.awt.Color.darkGray);

	java.awt.Toolkit tk = java.awt.Toolkit.getDefaultToolkit();
	Dimension ss = tk.getScreenSize();
	Dimension fs = f.getSize();
	java.awt.Container c;
	String hrs;

	public void px() {
		tb1.setPreferredScrollableViewportSize(new Dimension(100, 50));
		c = f.getContentPane();
		c.setLayout(new java.awt.BorderLayout());

		northp1.setLayout(new java.awt.FlowLayout());
		northp2.setLayout(new java.awt.GridLayout(4, 6, 2, 2));
		northpanel.setLayout(new java.awt.BorderLayout());
		butpanel1.setLayout(new java.awt.FlowLayout());
		butpanel2.setLayout(new java.awt.FlowLayout());
		centerpanel.setLayout(new java.awt.BorderLayout());

		tb1.setFont(fo);
		tb1.getTableHeader().setPreferredSize(new Dimension(50, 20));
		tb1.getTableHeader().setFont(fo);
		tb1.setRowHeight(18);

		northpanel.setBorder(bor1);
		centerpanel.setBorder(bor2);

		statuscb.addItem("Ready");
		statuscb.addItem("On Going");
		statuscb.addItem("Completed");
		statuscb.addItem("Revision");

		empcb.setEditable(false);
		dtlcb.setEditable(false);

		unittf.setFont(fo);

		butpanel1.add(emplabel);
		butpanel1.add(empcb);
		butpanel1.add(l4);
		butpanel1.add(dtlcb);
		butpanel1.add(l4);
		butpanel1.add(dtlcb);
		butpanel1.add(l5);
		butpanel1.add(statuscb);
		butpanel1.add(startbut);
		startbut.setMnemonic(83);
		butpanel1.add(rowlabel);

		butpanel2.add(showallbut);
		showallbut.setMnemonic(87);

		completebut.setMnemonic(77);

		revisionbut.setMnemonic(82);
		finalbut.setMnemonic(70);
		butpanel2.add(closebut);
		closebut.setMnemonic(67);

		northpanel.add(northp1, "North");
		northpanel.add(northp2, "Center");

		centerpanel.add(butpanel1, "North");
		centerpanel.add(sp1, "Center");
		centerpanel.add(butpanel2, "South");

		tb1.setSelectionBackground(new java.awt.Color(60, 130, 130));
		tb1.setSelectionForeground(java.awt.Color.white);
		tb1.setGridColor(new java.awt.Color(100, 150, 100));

		c.setBackground(java.awt.Color.blue);
		c.add(westimagelabel, "West");
		westimagelabel.setPreferredSize(new Dimension(100, 0));
		c.add(centerpanel, "Center");
		c.add(northimagelabel, "North");

		dtlcb.addItemListener(this);
		statuscb.addItemListener(this);
		showallbut.addActionListener(this);
		completebut.addActionListener(this);
		startbut.addActionListener(this);
		revisionbut.addActionListener(this);
		finalbut.addActionListener(this);
		closebut.addActionListener(this);

		empcb.addActionListener(this);
		empcb.addKeyListener(this);
		dtlcb.addKeyListener(this);
		tb1.addKeyListener(this);
		startbut.addKeyListener(this);
		showallbut.addKeyListener(this);
		revisionbut.addKeyListener(this);
		completebut.addKeyListener(this);
		finalbut.addKeyListener(this);

		f.setLocation((ss.width - fs.width) / 800, (ss.height - fs.height) / 16);

		f.setTitle("Daily Job Process");
		f.setSize(900, 600);
		f.setVisible(true);
	}

	public void paramUser() {
		unittf.setText(TechMainFrame.stf5.getText());
		empcb.addItem(TechMainFrame.textFieldLoggedBy.getText());
		String str = new String(TechMainFrame.textFieldPost.getText());

		if (str.equalsIgnoreCase("Detailer")) {
			dtlcb.addItem("Detailing");
		}
		if (str.equalsIgnoreCase("Checker")) {
			dtlcb.addItem("Checking");
		}
	}

	public void countRow() {
		int i = tb1.getRowCount();
		rowlabel.setText("Rows : " + String.valueOf(i));
	}

	String mins;
	String chktimestr;

	public void currentTime() {
		java.util.Date localDate = new java.util.Date();
		java.text.SimpleDateFormat localSimpleDateFormat1 = new java.text.SimpleDateFormat("H");
		java.text.SimpleDateFormat localSimpleDateFormat2 = new java.text.SimpleDateFormat("mm");

		try {
			hrs = localSimpleDateFormat1.format(localDate);
			mins = localSimpleDateFormat2.format(localDate);
		} catch (IllegalArgumentException localIllegalArgumentException) {
			System.out.println(localIllegalArgumentException);
		}
	}

	public void showDrawingNo() {
		String str1 = (String) dtlcb.getSelectedItem();
		String str2 = (String) statuscb.getSelectedItem();
		model1.setNumRows(0);

		Object localObject1 = "Detailing";
		Object localObject2 = "Checking";
		if (str1.equalsIgnoreCase("Checking")) {
			localObject1 = localObject2;
			localObject2 = localObject1;
		}

		try {
			con = DBConnectionUtil.getConnection();
			stat = con.createStatement();
			rs = stat.executeQuery(
					"SELECT D.PROJECT_NO, D.ITEM_CODE, D.DRAWING_NO, D.DATE_MONTH, D.S_TIME,D.F_TIME, D.T_TIME, D.E_TIME, D.SHOOT, D.STATUS, C.EMP_CODE FROM "
							+ (String) localObject1 + " D, " + (String) localObject2
							+ " C WHERE D.DRAWING_NO=C.DRAWING_NO and D.emp_code='" + empcb.getSelectedItem()
							+ "' AND D.STATUS='" + str2 + "' order by d.drawing_no ");
			rs.getMetaData().getColumnCount();
			while (rs.next()) {
				String str3 = new String(rs.getString(1));
				String str4 = new String(rs.getString(2));
				String str5 = new String(rs.getString(3));
				String str6 = new String(rs.getString(4));
				String str10 = new String(rs.getString(8));
				String str12 = new String(rs.getString(10));
				String str13 = new String(rs.getString(11));

				model1.addRow(new Object[] { str3, str4, str5, str6, str10, str12, str13 });
			}
		} catch (Exception localException) {
			System.out.println(localException);
		}
		countRow();
	}

	public void showF1() {
		tb1.setEnabled(false);
		String str1 = (String) dtlcb.getSelectedItem();
		String str2 = (String) empcb.getSelectedItem();

		model1.setNumRows(0);

		try {
			con = DBConnectionUtil.getConnection();
			stat = con.createStatement();
			rs = stat.executeQuery(
					"select project_no, item_code, drawing_no from " + str1 + " where emp_code='" + str2 + "'  ");
			rs.getMetaData().getColumnCount();
			while (rs.next()) {
				String str3 = new String(rs.getString(1));
				String str4 = new String(rs.getString(2));
				String str5 = new String(rs.getString(3));

				model1.addRow(new Object[] { str3, str4, str5 });
			}
		} catch (Exception localException) {
			System.out.println(localException);
		}
	}

	public void showAllRecord() {
		tb1.setEnabled(true);
		String str1 = (String) dtlcb.getSelectedItem();
		statuscb.getSelectedItem();
		model1.setNumRows(0);
		String str3;
		String str4;
		String str5;
		String str6;
		String str7;
		String str8;
		String str9;
		String str10;
		String str11;
		String str12;
		String str13;
		if (str1.equalsIgnoreCase("Detailing")) {
			try {
				con = DBConnectionUtil.getConnection();
				stat = con.createStatement();
				rs = stat.executeQuery(
						"SELECT D.PROJECT_NO, D.ITEM_CODE, D.DRAWING_NO, D.DATE_MONTH, D.S_TIME,D.F_TIME, D.T_TIME, D.E_TIME, D.SHOOT, D.STATUS, C.EMP_CODE FROM DETAILING D, CHECKING C WHERE D.DRAWING_NO=C.DRAWING_NO and D.emp_code='"
								+ empcb.getSelectedItem() + "' order by d.drawing_no ");
				rs.getMetaData().getColumnCount();
				while (rs.next()) {
					str3 = new String(rs.getString(1));
					str4 = new String(rs.getString(2));
					str5 = new String(rs.getString(3));
					str6 = new String(rs.getString(4));
					str7 = new String(rs.getString(5));
					str8 = new String(rs.getString(6));
					str9 = new String(rs.getString(7));
					str10 = new String(rs.getString(8));
					str11 = new String(rs.getString(9));
					str12 = new String(rs.getString(10));
					str13 = new String(rs.getString(11));

					model1.addRow(
							new Object[] { str3, str4, str5, str6, str7, str8, str9, str10, str11, str12, str13 });
				}
			} catch (Exception localException1) {
				System.out.println(localException1);
			}
			countRow();
		}

		if (str1.equalsIgnoreCase("Checking")) {
			try {
				con = DBConnectionUtil.getConnection();
				stat = con.createStatement();
				rs = stat.executeQuery(
						"SELECT C.PROJECT_NO, C.ITEM_CODE, C.DRAWING_NO, C.DATE_MONTH, C.S_TIME, C.F_TIME, C.T_TIME, C.E_TIME, C.SHOOT, C.STATUS, D.EMP_CODE FROM DETAILING D, CHECKING C WHERE D.DRAWING_NO=C.DRAWING_NO and C.emp_code='"
								+ empcb.getSelectedItem() + "' order by d.drawing_no ");
				rs.getMetaData().getColumnCount();
				while (rs.next()) {
					str3 = new String(rs.getString(1));
					str4 = new String(rs.getString(2));
					str5 = new String(rs.getString(3));
					str6 = new String(rs.getString(4));
					str7 = new String(rs.getString(5));
					str8 = new String(rs.getString(6));
					str9 = new String(rs.getString(7));
					str10 = new String(rs.getString(8));
					str11 = new String(rs.getString(9));
					str12 = new String(rs.getString(10));
					str13 = new String(rs.getString(11));

					model1.addRow(
							new Object[] { str3, str4, str5, str6, str7, str8, str9, str10, str11, str12, str13 });
				}
			} catch (Exception localException2) {
				System.out.println(localException2);
			}
			countRow();
			tb1.setEnabled(true);
		}
	}

	String finalstr1 = "";
	String finalstr2 = "";
	String finalstr3 = "";
	String finalstr4 = "";
	String finalstr5 = "";
	String finalstr6 = "";
	String finalstr7 = "";
	String finalstr8 = "";
	String finalstr9 = "";

	public void finalFetch() {
		int i = tb1.getSelectedRow();
		projectcodestr = String.valueOf(model1.getValueAt(i, 0));
		itemcodestr = String.valueOf(model1.getValueAt(i, 1));
		String str1 = String.valueOf(model1.getValueAt(i, 2));

		new String(String.valueOf(model1.getValueAt(i, 5)));

		try {
			con = DBConnectionUtil.getConnection();
			Statement localStatement = con.createStatement();
			ResultSet localResultSet = localStatement.executeQuery(
					"SELECT  SUM(E.PERSHEET_TIME), SUM(D.T_TIME), SUM(C.T_TIME), SUM(D.T_TIME)+SUM(C.T_TIME), MAX(D.SHOOT), MAX(C.SHOOT), MAX(D.SHOOT)/2+MAX(C.SHOOT)/2, MAX(D.EMP_CODE), MAX(C.EMP_CODE) FROM ESTIMATION E, DETAILING D, CHECKING C WHERE D.PROJECT_NO='"
							+ projectcodestr + "' AND  D.ITEM_CODE='" + itemcodestr + "' AND D.DRAWING_NO='"
							+ str1 + "' ");
			localResultSet.getMetaData().getColumnCount();
			while (localResultSet.next()) {
				finalstr1 = new String(localResultSet.getString(1));
				finalstr2 = new String(localResultSet.getString(2));
				finalstr3 = new String(localResultSet.getString(3));
				finalstr4 = new String(localResultSet.getString(4));
				finalstr5 = new String(localResultSet.getString(5));
				finalstr6 = new String(localResultSet.getString(6));
				finalstr7 = new String(localResultSet.getString(7));
				finalstr8 = new String(localResultSet.getString(8));
				finalstr9 = new String(localResultSet.getString(9));

				System.out.println(finalstr1);
				System.out.println(finalstr2);
				System.out.println(finalstr3);
				System.out.println(finalstr4);
				System.out.println(finalstr5);
				System.out.println(finalstr6);
				System.out.println(finalstr7);
				System.out.println(finalstr8);
				System.out.println(finalstr9);
			}
		} catch (Exception localException) {
			System.out.println(localException);
		}
	}

	public void finalDraw1() {
		int i = tb1.getSelectedRow();
		projectcodestr = String.valueOf(model1.getValueAt(i, 0));
		itemcodestr = String.valueOf(model1.getValueAt(i, 1));
		String str1 = String.valueOf(model1.getValueAt(i, 2));

		String str2 = new String(String.valueOf(model1.getValueAt(i, 5)));

		finalFetch();

		if ((str2.equalsIgnoreCase("Final")) || (str2.equalsIgnoreCase("On Going")) || (str2.equalsIgnoreCase("Ready"))
				|| (str2.equalsIgnoreCase("Fresh")) || (str2.equalsIgnoreCase("RC"))) {
			JOptionPane.showMessageDialog(f, "Cannot Final  This Drawing is Not Completed Till.");
		}

		if (str2.equalsIgnoreCase("Completed")) {
			try {
				stat = con.createStatement();
				stat.executeUpdate("insert into FINALDB values('" + projectcodestr + "','" + itemcodestr
						+ "','" + str1 + "','" + finalstr1 + "','" + finalstr2 + "','" + finalstr3
						+ "','" + finalstr4 + "','" + finalstr5 + "','" + finalstr6 + "','"
						+ finalstr7 + "','" + finalstr8 + "','" + finalstr9 + "','"
						+ unittf.getText() + "','" + datestr + "' )");
			} catch (Exception localException1) {
				System.out.println(localException1);
			}
			try {
				stat = con.createStatement();
				stat.executeQuery("Update DETAILING set status='Final' where project_no='" + projectcodestr
						+ "' and item_code='" + itemcodestr + "' and drawing_no='" + str1 + "' ");
			} catch (Exception localException2) {
				if (localException2.getMessage().toString().equalsIgnoreCase("No ResultSet was Produced")) {

					model1.setValueAt("Final", i, 5);
				} else {
					JOptionPane.showMessageDialog(f, localException2.getMessage().toString());
				}
			}

			try {
				stat = con.createStatement();
				stat.executeQuery("Update CHECKING set status='Final'  where project_no='" + projectcodestr
						+ "' and item_code='" + itemcodestr + "' and drawing_no='" + str1 + "' ");
			} catch (Exception localException3) {
				System.out.println(localException3);
			}
		}
	}

	public void sumTime() {
		try {
			con = DBConnectionUtil.getConnection();
			stat = con.createStatement();
			ResultSet localResultSet = stat.executeQuery(
					"select sum(t_time) from detailing where project_no='200701' and item_code='ABD' and drawing_no='S110'   ");
			localResultSet.getMetaData().getColumnCount();
			while (localResultSet.next()) {
				String str = new String(localResultSet.getString(1));

				System.out.println(str);
			}
		} catch (Exception localException) {
			System.out.println(localException);
		}
	}

	public void actionPerformed(java.awt.event.ActionEvent paramActionEvent) {
		if (paramActionEvent.getSource() == showallbut) {
			tb1.setEnabled(false);
			showAllRecord();
		}
		String str4;
		if (paramActionEvent.getSource() == completebut) {
			String str1 = (String) dtlcb.getSelectedItem();
			currentTime();
			int j = tb1.getSelectedRow();
			tb1.getSelectedColumn();

			projectcodestr = String.valueOf(model1.getValueAt(j, 0));
			itemcodestr = String.valueOf(model1.getValueAt(j, 1));
			draw_nostr = String.valueOf(model1.getValueAt(j, 2));
			emp_codestr = ((String) empcb.getSelectedItem());

			str4 = new String(String.valueOf(model1.getValueAt(j, 5)));

			if ((str4.equalsIgnoreCase("Completed")) || (str4.equalsIgnoreCase("Final"))
					|| (str4.equalsIgnoreCase("RC"))) {
				JOptionPane.showMessageDialog(f, "Cannot Completed.");
			}

			if (str4.equalsIgnoreCase("On Going")) {

				if (str1.equalsIgnoreCase("Detailing")) {

					try {

						con = DBConnectionUtil.getConnection();
						stat = con.createStatement();

						stat.executeQuery(
								"Update DETAILING set status='Completed', f_time='0',t_time ='0', shoot='0' , C_DATE='"
										+ datestr + "' where project_no='" + projectcodestr
										+ "' and item_code='" + itemcodestr + "' and drawing_no='"
										+ draw_nostr + "' ");
					} catch (Exception localException3) {
						if (localException3.getMessage().toString().equalsIgnoreCase("No ResultSet was Produced")) {

							model1.setValueAt("Completed", j, 5);
						} else {
							JOptionPane.showMessageDialog(f, localException3.getMessage().toString());
						}
					}

					try {
						stat = con.createStatement();
						stat.executeQuery("Update CHECKING set status='RC'  where project_no='"
								+ projectcodestr + "' and item_code='" + itemcodestr + "' and drawing_no='"
								+ draw_nostr + "' ");
					} catch (Exception localException4) {
						if (localException4.getMessage().toString().equalsIgnoreCase("No ResultSet was Produced")) {

							model1.setValueAt("Completed", j, 5);
						} else {
							JOptionPane.showMessageDialog(f, localException4.getMessage().toString());

						}

					}

				} else {

					try {

						stat = con.createStatement();
						stat.executeQuery(
								"Update CHECKING set status='Completed', s_time='0',f_time='0',t_time='0', shoot='0', C_DATE='"
										+ datestr + "' where project_no='" + projectcodestr
										+ "' and item_code='" + itemcodestr + "' and drawing_no='"
										+ draw_nostr + "'  ");
						model1.setValueAt("Completed", j, 9);
					} catch (Exception localException5) {
						if (localException5.getMessage().toString().equalsIgnoreCase("No ResultSet was Produced")) {

							model1.setValueAt("Completed", j, 5);
						} else {
							JOptionPane.showMessageDialog(f, localException5.getMessage().toString());
						}
					}
				}
			}
		}
		int i;
		if (paramActionEvent.getSource() == revisionbut) {

			i = tb1.getSelectedRow();
			String str2 = (String) dtlcb.getSelectedItem();
			projectcodestr = String.valueOf(model1.getValueAt(i, 0));
			itemcodestr = String.valueOf(model1.getValueAt(i, 1));
			draw_nostr = String.valueOf(model1.getValueAt(i, 2));

			if (str2.equalsIgnoreCase("Checking")) {
				try {
					stat = con.createStatement();
					stat.executeQuery("Update DETAILING set status ='Revision' where project_no='"
							+ projectcodestr + "' and item_code='" + itemcodestr + "' and drawing_no='"
							+ draw_nostr + "'   ");
				} catch (Exception localException1) {
					if (localException1.getMessage().toString().equalsIgnoreCase("No ResultSet was Produced")) {

						model1.setValueAt("Revision", i, 5);
					} else {
						JOptionPane.showMessageDialog(f, localException1.getMessage().toString());
					}
				}
			}

			if (str2.equalsIgnoreCase("Detailing")) {
				try {
					stat = con.createStatement();
					stat.executeQuery("Update CHECKING set status ='Revision' where project_no='"
							+ projectcodestr + "' and item_code='" + itemcodestr + "' and drawing_no='"
							+ draw_nostr + "'   ");
				} catch (Exception localException2) {
					if (localException2.getMessage().toString().equalsIgnoreCase("No ResultSet was Produced")) {

						model1.setValueAt("Revision", i, 5);
					} else {
						JOptionPane.showMessageDialog(f, localException2.getMessage().toString());
					}
				}
			}
		}

		if (paramActionEvent.getSource() == startbut) {
			currentTime();
			i = tb1.getSelectedRow();
			tb1.getSelectedColumn();

			draw_nostr = String.valueOf(model1.getValueAt(i, 2));
			projectcodestr = String.valueOf(model1.getValueAt(i, 0));
			itemcodestr = String.valueOf(model1.getValueAt(i, 1));

			emp_codestr = ((String) empcb.getSelectedItem());
			String str3 = String.valueOf(model1.getValueAt(i, 3));

			str4 = String.valueOf(model1.getValueAt(i, 4));

			String str5 = new String(String.valueOf(model1.getValueAt(i, 5)));

			if (str5.equalsIgnoreCase("Final")) {
				JOptionPane.showMessageDialog(f, "Cannot Assign This Drawing. It's Allready Final.");
			}
			if (str5.equalsIgnoreCase("On Going")) {
				JOptionPane.showMessageDialog(f, "Drawing is On Going.");
			}
			if (str5.equalsIgnoreCase("Completed")) {
				JOptionPane.showMessageDialog(f, "Drawing is Completed.");
			}
			if (str5.equalsIgnoreCase("Fresh")) {
				JOptionPane.showMessageDialog(f, "Drawing is Not Alloted to Process.");
			}

			if ((str5.equalsIgnoreCase("Ready")) || (str5.equalsIgnoreCase("RC"))
					|| (str5.equalsIgnoreCase("Revision"))) {
				String str6 = (String) dtlcb.getSelectedItem();

				try {
					stat = con.createStatement();

					stat.executeQuery("UPDATE " + str6 + " set date_month='" + str3
							+ "', s_time='0',f_time='0', status='On Going' where project_no='" + projectcodestr
							+ "' and item_code='" + itemcodestr + "' and drawing_no='" + draw_nostr + "' ");
				} catch (Exception localException6) {
					System.out.println(localException6);
				}
				model1.setValueAt("On Going", i, 5);
			}
		}
		if (paramActionEvent.getSource() == finalbut) {
			finalDraw1();
		}
		if (paramActionEvent.getSource() == closebut) {
			f.setVisible(false);
		}
	}

	public void keyPressed(java.awt.event.KeyEvent paramKeyEvent) {
		int i = paramKeyEvent.getKeyCode();

		if (paramKeyEvent.getSource() == tb1) {
			if (i == 9) {
				currentTime();
				int j = tb1.getSelectedRow();
				tb1.getSelectedColumn();
				String.valueOf(model1.getValueAt(j, 2));
				model1.setValueAt(hrs + "." + mins, j, 4);
				model1.setValueAt("On Going", j, 9);
			}
		}
		if (i == 112) {
			showF1();
		}
	}

	public void keyReleased(java.awt.event.KeyEvent paramKeyEvent) {
	}

	public void keyTyped(java.awt.event.KeyEvent paramKeyEvent) {
	}

	public void mousePressed(MouseEvent paramMouseEvent) {
		showDrawingNo();
	}

	public void mouseReleased(MouseEvent paramMouseEvent) {
	}

	public void mouseClicked(MouseEvent paramMouseEvent) {
	}

	public void mouseEntered(MouseEvent paramMouseEvent) {
	}

	public void mouseExited(MouseEvent paramMouseEvent) {
	}

	public void itemStateChanged(java.awt.event.ItemEvent paramItemEvent) {
		dtlcb.getSelectedItem();
		if (paramItemEvent.getSource() == dtlcb) {
			if (dtlcb.getSelectedItem().equals("Detailing")) {
				butpanel2.remove(finalbut);
				butpanel2.revalidate();
				statuscb.removeItem("Ready");
				statuscb.removeItem("RC");
				statuscb.addItem("Ready");
			}
			if (dtlcb.getSelectedItem().equals("Checking")) {
				butpanel2.add(finalbut);
				butpanel2.revalidate();
				statuscb.removeItem("Ready");
				statuscb.removeItem("RC");
				statuscb.addItem("RC");
			}
		}

		if (paramItemEvent.getSource() == statuscb) {
			tb1.setEnabled(true);
			showDrawingNo();
			if (statuscb.getSelectedItem().equals("Ready")) {
				butpanel2.remove(completebut);
				butpanel2.remove(revisionbut);
				butpanel2.revalidate();
			}

			if (statuscb.getSelectedItem().equals("On Going")) {
				butpanel2.add(completebut);
				butpanel2.remove(revisionbut);
				butpanel2.revalidate();
			}
			if (statuscb.getSelectedItem().equals("Completed")) {
				butpanel2.add(completebut);
				butpanel2.add(revisionbut);
				butpanel2.revalidate();
			}
		}
	}
}
