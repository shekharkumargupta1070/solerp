package com.sol.erp;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.text.DecimalFormat;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.sol.erp.util.DBConnectionUtil;

public class ManPowerPlan
		implements java.awt.event.ActionListener, java.awt.event.MouseListener, java.awt.event.KeyListener {

	java.sql.Connection con;
	java.sql.Statement stat;
	ResultSet rs;
	int count = 1;
	int total = 0;

	int countdays;
	String fromdate;
	String todate;
	String empcodestr;
	String[] columnNames = { "Emp Code", "From", "To", "Alloted DAYS", "Alloted HRS", "Average Work HRS", "Ideal",
			"Leave", "Leave HRS", "Rest HRS" };
	Object[][] data = new Object[0][];

	String[] columnNames1 = { "Date", "Pproject No", "Drawing No", "Item Code", "Status %" };
	Object[][] data1 = new Object[0][];

	String[] monthstr = { "January", "February", "March", "April", "May", "June", "July", "August", "September",
			"October", "November", "December" };

	DecimalFormat df = new DecimalFormat("00.00");
	DecimalFormat df1 = new DecimalFormat("00");

	javax.swing.DefaultListModel empmodel = new javax.swing.DefaultListModel();
	javax.swing.JList emplist = new javax.swing.JList(empmodel);

	DefaultTableModel model = new DefaultTableModel(data, columnNames);
	JTable tb = new JTable(model);
	javax.swing.JScrollPane sp = new javax.swing.JScrollPane(tb);

	DefaultTableModel model1 = new DefaultTableModel(data1, columnNames1);
	JTable tb1 = new JTable(model1);
	javax.swing.JScrollPane sp1 = new javax.swing.JScrollPane(tb1);

	JFrame f = new JFrame("[SOL Group] ManPowerPlan");
	JLabel fromlabel = new JLabel("From");
	JLabel tolabel = new JLabel("To");

	JLabel totaldayslabel = new JLabel("Days #");
	JLabel nonworklabel = new JLabel("NW Days");
	JLabel avgworklabel = new JLabel("Avg Work Hrs");
	JLabel allotedhrslabel = new JLabel("Alloted Hrs");
	JLabel idealhrslabel = new JLabel("Ideal Hrs");
	JLabel leavelabel = new JLabel("L Status");
	JLabel leavehrslabel = new JLabel("L Hrs");
	JLabel restlabel = new JLabel("Rest");

	JTextField fromtf = new JTextField(8);
	JTextField totf = new JTextField(8);

	JTextField totaldaystf = new JTextField(3);
	JTextField nonworktf = new JTextField(3);
	JTextField avgworktf = new JTextField(6);
	JTextField allotedhrstf = new JTextField(6);
	JTextField idealhrstf = new JTextField(6);
	JTextField leavetf = new JTextField(3);
	JTextField leavehrstf = new JTextField(6);
	JTextField resttf = new JTextField(6);

	JComboBox monthcb = new JComboBox();
	JButton b1 = new JButton("Status", new javax.swing.ImageIcon("image/list.gif"));
	JButton b2 = new JButton("Close", new javax.swing.ImageIcon("image/close.gif"));

	JPanel butp1 = new JPanel();
	JPanel butp2 = new JPanel();
	JPanel southbutpanel = new JPanel();
	JPanel centerpanel = new JPanel();

	JPanel toppanel = new JPanel();
	JPanel bottompanel = new JPanel();

	javax.swing.JSplitPane spl1 = new javax.swing.JSplitPane(0, toppanel, bottompanel);

	java.awt.Font fo = new java.awt.Font("Tahoma", 1, 11);
	javax.swing.border.Border line = javax.swing.BorderFactory.createLineBorder(Color.darkGray);
	javax.swing.border.Border bor1 = javax.swing.BorderFactory.createTitledBorder(line, "Employee Average Status", 0, 0,
			fo, Color.darkGray);
	javax.swing.border.Border bor2 = javax.swing.BorderFactory.createTitledBorder(line, "Employee Current Details", 0,
			0, fo, Color.darkGray);

	javax.swing.table.TableColumn empColumn = tb.getColumnModel().getColumn(0);
	javax.swing.table.TableColumn avgworkColumn = tb.getColumnModel().getColumn(1);

	javax.swing.border.Border bor3 = javax.swing.BorderFactory.createBevelBorder(1);

	java.awt.Toolkit tk = java.awt.Toolkit.getDefaultToolkit();
	Dimension ss = tk.getScreenSize();
	Dimension fs = f.getSize();

	public void px() {
		java.awt.Container localContainer = f.getContentPane();
		localContainer.setLayout(new java.awt.BorderLayout());
		butp1.setLayout(new java.awt.FlowLayout());
		butp2.setLayout(new java.awt.GridLayout(2, 12, 2, 2));
		southbutpanel.setLayout(new java.awt.FlowLayout());
		centerpanel.setLayout(new java.awt.GridLayout(3, 1, 2, 2));
		toppanel.setLayout(new java.awt.BorderLayout());
		bottompanel.setLayout(new java.awt.BorderLayout());

		butp1.add(fromlabel);
		butp1.add(fromtf);
		butp1.add(tolabel);
		butp1.add(totf);

		butp2.add(totaldayslabel);
		butp2.add(totaldaystf);
		butp2.add(nonworklabel);
		butp2.add(nonworktf);
		butp2.add(avgworklabel);
		butp2.add(avgworktf);
		butp2.add(idealhrslabel);
		butp2.add(idealhrstf);
		butp2.add(allotedhrslabel);
		butp2.add(allotedhrstf);
		butp2.add(leavelabel);
		butp2.add(leavetf);
		butp2.add(leavehrslabel);
		butp2.add(leavehrstf);
		butp2.add(restlabel);
		butp2.add(resttf);

		southbutpanel.add(b2);
		b2.setMnemonic(67);

		totaldaystf.setHorizontalAlignment(4);
		nonworktf.setHorizontalAlignment(4);
		avgworktf.setHorizontalAlignment(4);
		idealhrstf.setHorizontalAlignment(4);
		allotedhrstf.setHorizontalAlignment(4);
		leavetf.setHorizontalAlignment(4);
		leavehrstf.setHorizontalAlignment(4);
		resttf.setHorizontalAlignment(4);

		totaldaystf.setBackground(Color.white);
		nonworktf.setBackground(Color.white);
		avgworktf.setBackground(Color.white);
		idealhrstf.setBackground(Color.white);
		allotedhrstf.setBackground(Color.white);
		leavetf.setBackground(Color.white);
		leavehrstf.setBackground(Color.white);
		resttf.setBackground(Color.white);

		totaldaystf.setEditable(false);
		nonworktf.setEditable(false);
		avgworktf.setEditable(false);
		allotedhrstf.setEditable(false);
		idealhrstf.setEditable(false);
		leavetf.setEditable(false);
		leavehrstf.setEditable(false);
		resttf.setEditable(false);

		totaldaystf.setFont(fo);
		nonworktf.setFont(fo);
		avgworktf.setFont(fo);
		allotedhrstf.setFont(fo);
		idealhrstf.setFont(fo);
		leavetf.setFont(fo);
		leavehrstf.setFont(fo);
		resttf.setFont(fo);

		fromtf.setFont(fo);
		totf.setFont(fo);
		fromtf.setPreferredSize(new Dimension(50, 22));
		totf.setPreferredSize(new Dimension(50, 22));
		b1.setPreferredSize(new Dimension(120, 30));

		empColumn.setPreferredWidth(50);
		avgworkColumn.setPreferredWidth(150);
		tb.getTableHeader().setPreferredSize(new Dimension(50, 20));
		tb.setRowHeight(18);
		tb.getTableHeader().setFont(fo);
		tb.setFont(fo);
		tb.setSelectionBackground(new Color(60, 130, 130));
		tb.setSelectionForeground(Color.white);
		tb.setGridColor(new Color(100, 150, 100));

		tb.setColumnSelectionAllowed(false);

		tb1.getColumn("Date").setPreferredWidth(10);
		tb1.getTableHeader().setPreferredSize(new Dimension(50, 20));
		tb1.setRowHeight(18);
		tb1.getTableHeader().setFont(fo);
		tb1.setFont(fo);
		tb1.setSelectionBackground(new Color(60, 130, 130));
		tb1.setSelectionForeground(Color.white);
		tb1.setGridColor(new Color(100, 150, 100));

		butp1.add(b1);
		b1.setMnemonic(83);

		b1.addActionListener(this);
		b2.addActionListener(this);
		fromtf.addActionListener(this);
		totf.addActionListener(this);
		tb.addMouseListener(this);
		tb.addKeyListener(this);

		toppanel.add(butp1, "North");
		toppanel.add(sp, "Center");
		toppanel.add(butp2, "South");
		bottompanel.add(sp1, "Center");
		localContainer.add(spl1);
		localContainer.add(southbutpanel, "South");

		f.setLocation((ss.width - fs.width) / 900, (ss.height - fs.height) / 18);
		spl1.setDividerLocation(310);
		f.setSize(1024, 650);
		f.setVisible(true);
	}

	public void dateDiff() {
		String str1 = fromtf.getText();
		String str2 = str1.substring(0, 2);
		String str3 = str1.substring(3, 5);
		String str4 = str1.substring(7, 10);

		int i = Integer.parseInt(str2);
		int j = Integer.parseInt(str3);
		int k = Integer.parseInt(str4);

		String str5 = totf.getText();
		String str6 = str5.substring(0, 2);
		String str7 = str5.substring(3, 5);
		String str8 = str5.substring(7, 10);

		int m = Integer.parseInt(str6);
		int n = Integer.parseInt(str7);
		int i1 = Integer.parseInt(str8);

		int i2 = 0;
		java.util.GregorianCalendar localGregorianCalendar1 = new java.util.GregorianCalendar(k, j, i);
		java.util.GregorianCalendar localGregorianCalendar2 = new java.util.GregorianCalendar(i1, n, m);
		while (localGregorianCalendar2.get(1) != localGregorianCalendar1.get(1)) {
			i2 += 360;
			localGregorianCalendar1.add(6, 360);
		}
		i2 += localGregorianCalendar2.get(6) - localGregorianCalendar1.get(6);
		countdays = (i2 + 1);
		totaldaystf.setText(String.valueOf(countdays));
		System.out.println(countdays);
	}

	public void empList() {
		try {
			con = DBConnectionUtil.getConnection();
			stat = con.createStatement();
			rs = stat.executeQuery("select distinct(emp_code) from projectmanpower ");
			rs.getMetaData().getColumnCount();
			while (rs.next()) {
				String str = new String(rs.getString(1));
				empmodel.addElement(str);
			}
		} catch (Exception localException) {
			System.out.println(localException);
		}
	}

	public void manpowerStatus() {
		model.setNumRows(0);
		String str1 = fromtf.getText();
		totf.getText();

		String str3 = str1.substring(0, 2);

		int i = Integer.parseInt(str3);

		String str4 = str1.substring(3, 5);

		String str5 = str1.substring(6, 10);

		int j = Integer.parseInt(str4);

		int k = Integer.parseInt(str5);

		int m = 0;
		int n = j % 2;
		int i1 = k % 4;

		String str6 = "/" + String.valueOf(df1.format(j)) + "/" + String.valueOf(k);
		String str8 = "";

		if (j == 12) {
			j = 0;
		}

		int i2 = countdays;

		for (int i3 = 0; i3 < i2; i3++) {
			str8 = df1.format(i) + str6;
			System.out.println(str8);

			empList();
			for (int i4 = 0; i4 < empmodel.getSize(); i4++) {
				empcodestr = ((String) empmodel.getElementAt(i4));

				try {
					con = DBConnectionUtil.getConnection();
					stat = con.createStatement();
					rs = stat.executeQuery(
							"select max(p.emp_code), min(p.from_date), max(p.to_date), sum(p.total_days), sum(p.total_hrs), max(p.Avg_work_hrs), (select count(date_month) from leave where emp_code='"
									+ empcodestr + "') from PROJECTMANPOWER p where p.emp_code='" + empcodestr
									+ "' and  p.from_date = '" + str8 + "' ");
					rs.getMetaData().getColumnCount();
					while (rs.next()) {
						String str9 = new String(rs.getString(1));
						String str10 = new String(rs.getString(2));
						String str11 = new String(rs.getString(3));
						String str12 = new String(rs.getString(4));
						String str13 = new String(rs.getString(5));
						String str14 = new String(rs.getString(6));
						String str15 = new String(rs.getString(7));

						float f1 = Float.parseFloat(totaldaystf.getText());

						float f2 = Float.parseFloat(str14);
						float f3 = Float.parseFloat(str15);

						float f4 = f2 * f1;
						float f5 = f2 * f3;

						float f6 = Float.parseFloat(str13);
						float f7 = f4 - f6 + f5;

						model.addRow(
								new Object[] { str9, str10, str11, str12, str13, str14, String.valueOf(df.format(f4)),
										str15, String.valueOf(df.format(f5)), String.valueOf(df.format(f7)) });

						System.out.println(str9 + " " + str15 + " " + String.valueOf(df.format(f7)));
					}
				} catch (Exception localException) {
					System.out.println(localException);
				}
			}
			empmodel.clear();

			if (n == 0) {
				if (j == 2) {
					if (i1 == 0) {
						m = 29;
					} else {
						m = 28;
					}

				} else {
					m = 30;
				}
			}
			if (n == 1) {
				m = 31;
			}

			if (i == m) {
				i = 0;
				j += 1;
				str6 = "/" + String.valueOf(df1.format(j)) + "/" + String.valueOf(k);
			}
			i += 1;
			n = j % 2;
		}
	}

	public void notinlist() {
		try {
			con = DBConnectionUtil.getConnection();
			stat = con.createStatement();
			rs = stat.executeQuery(
					"select emp_code from emp_status where  designation like 'checker' or 'detailer' and emp_code NOT IN(SELECT distinct(emp_code) FROM PROJECTMANPOWER where from_date between '"
							+ fromtf.getText() + "' and '" + totf.getText() + "' )   ");

			while (rs.next()) {
				String str = new String(rs.getString(1));

				model.addRow(new Object[] { str, " ", "0", "0", "0", "0", "0", "0", "0", "0" });
				count += 1;
			}

			count = 1;
			total = 0;
		} catch (Exception localException) {
			System.out.println(localException);
		}
	}

	public void showDetailsRecord() {
		model1.setNumRows(0);
		try {
			stat = con.createStatement();
			rs = stat.executeQuery(
					"select DATE_MONTH, PROJECT_NO, DRAWING_NO, ITEM_CODE FROM MANPOWERPLAN WHERE EMP_CODE='"
							+ empcodestr + "' AND DATE_MONTH BETWEEN '" + fromtf.getText() + "' and '" + totf.getText()
							+ "' ");
			rs.getMetaData().getColumnCount();
			while (rs.next()) {
				String str1 = new String(rs.getString(1));
				String str2 = new String(rs.getString(2));
				String str3 = new String(rs.getString(3));
				String str4 = new String(rs.getString(4));

				model1.addRow(new Object[] { str1, str2, str3, str4 });
			}
		} catch (Exception localException) {
			System.out.println(localException);
		}
	}

	public void actionPerformed(java.awt.event.ActionEvent paramActionEvent) {
		if ((paramActionEvent.getSource() == b1) || (paramActionEvent.getSource() == totf)) {
			dateDiff();
			manpowerStatus();
			notinlist();
		}
		if (paramActionEvent.getSource() == fromtf) {
			totf.requestFocus();
			totf.selectAll();
		}
		if (paramActionEvent.getSource() == b2) {
			f.setVisible(false);
		}
	}

	public void mousePressed(MouseEvent paramMouseEvent) {
		int i = tb.getSelectedRow();
		empcodestr = String.valueOf(model.getValueAt(i, 0));
		showDetailsRecord();
	}

	public void mouseReleased(MouseEvent paramMouseEvent) {
	}

	public void mouseClicked(MouseEvent paramMouseEvent) {
	}

	public void mouseEntered(MouseEvent paramMouseEvent) {
	}

	public void mouseExited(MouseEvent paramMouseEvent) {
	}

	public void keyPressed(KeyEvent paramKeyEvent) {
	}

	public void keyReleased(KeyEvent paramKeyEvent) {
		if (paramKeyEvent.getSource() == tb) {
			int i = tb.getSelectedRow();
			empcodestr = String.valueOf(model.getValueAt(i, 0));
			showDetailsRecord();
		}
	}

	public void keyTyped(KeyEvent paramKeyEvent) {
	}

}