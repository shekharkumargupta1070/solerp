package com.sol.erp;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;

import com.sol.erp.constants.ApplicationConstants;
import com.sol.erp.util.DBConnectionUtil;

public class TimeSheet implements ActionListener {

	Connection con;
	Statement stat;
	ResultSet rs;
	String[] columnNames;
	Object[][] data;
	DecimalFormat df;
	DefaultTableModel model;
	JTable tb;
	JScrollPane sp;
	JFrame f;
	JLabel l1;
	JTextField tf1;
	JTextField tf2;
	JButton b1;
	JButton b2;
	JPanel northp1;
	JPanel northp2;
	JPanel northp3;
	JPanel butpanel1;
	JPanel butpanel2;
	JPanel centerpanel;
	JPanel northpanel;
	Font fo;
	Border line;
	Border bor1;
	Border bor2;
	Toolkit tk;
	Dimension ss;
	Dimension fs;
	Date dat;
	SimpleDateFormat formatter;
	String dateString;
	Container c;
	int count;
	float total;

	public void px() {
		c = f.getContentPane();
		c.setLayout(new BorderLayout());
		northp1.setLayout(new FlowLayout());
		northp2.setLayout(new GridLayout(2, 2, 2, 2));
		northpanel.setLayout(new BorderLayout());
		butpanel1.setLayout(new FlowLayout());
		butpanel2.setLayout(new FlowLayout());
		centerpanel.setLayout(new BorderLayout());

		tb.setFont(fo);
		tb.setBackground(Color.white);
		tb.setForeground(Color.black);
		tb.getTableHeader().setPreferredSize(new Dimension(50, 20));
		tb.getTableHeader().setFont(fo);
		tb.setRowHeight(20);
		tb.setGridColor(new Color(100, 150, 100));
		tb.setSelectionBackground(new Color(60, 130, 130));
		tb.setSelectionForeground(Color.white);

		northpanel.setBorder(bor1);
		sp.setBorder(bor2);

		northp1.add(l1);
		northp1.add(tf1);
		tf1.setFont(fo);
		northp1.add(b1);
		b1.setMnemonic(83);
		northp1.add(b2);
		b2.setMnemonic(67);

		tf2.setEditable(false);

		northpanel.add(northp1, "North");
		centerpanel.add(sp, "Center");

		c.add(northpanel, "North");
		c.add(centerpanel, "Center");

		b1.addActionListener(this);
		b2.addActionListener(this);
		tf1.addActionListener(this);

		f.getRootPane().setDefaultButton(b1);

		f.setTitle("Daily Time Sheet");
		f.setSize(1024, 750);
		f.setVisible(true);
	}

	public TimeSheet() {

		columnNames = new String[] { "SL", "Emp Code", "Desig", "Total Time", "Project No", "Break", "BFA", "Revision",
				"Wested", "App Auth" };
		data = new Object[0][];

		df = new DecimalFormat("00.00");

		model = new DefaultTableModel(data, columnNames);
		tb = new JTable(model);
		sp = new JScrollPane(tb);

		f = new JFrame();

		l1 = new JLabel("Date");

		tf1 = new JTextField(10);
		tf2 = new JTextField(10);

		b1 = new JButton("Show Status");
		b2 = new JButton("Close", new ImageIcon(ClassLoader.getSystemResource(ApplicationConstants.IMAGE_PATH + "close.gif")));

		northp1 = new JPanel();
		northp2 = new JPanel();
		northp3 = new JPanel();

		butpanel1 = new JPanel();
		butpanel2 = new JPanel();

		centerpanel = new JPanel();
		northpanel = new JPanel();

		fo = new Font("Tahoma", 1, 11);
		line = BorderFactory.createLineBorder(Color.darkGray);
		bor1 = BorderFactory.createTitledBorder(line, "Search By", 0, 0, fo, Color.darkGray);
		bor2 = BorderFactory.createTitledBorder(line, "Time Sheet", 0, 0, fo, Color.darkGray);

		tk = Toolkit.getDefaultToolkit();
		ss = tk.getScreenSize();
		fs = f.getSize();

		dat = new Date();
		formatter = new SimpleDateFormat("dd/MM/yyyy");

		try {
			dateString = formatter.format(dat);
			tf1 = new JTextField(String.valueOf(dateString));
		} catch (IllegalArgumentException localIllegalArgumentException) {
			System.out.println(localIllegalArgumentException.getMessage());
		}

		count = 1;
		total = 0.0F;
	}

	public void report() {
		model.setNumRows(0);

		try {
			con = DBConnectionUtil.getConnection();
			stat = con.createStatement();
			rs = stat.executeQuery(
					"select max(emp_Code), max(total_time), min(project_no),min(total_break),min(bfa_time),min(rev_time),min(wested_time) from timesheet where  date='"
							+ tf1.getText() + "' ");
			while (rs.next()) {
				String str1 = new String(rs.getString(1));
				String str2 = new String(rs.getString(2));
				String str3 = new String(rs.getString(3));
				String str4 = new String(rs.getString(4));
				String str5 = new String(rs.getString(5));
				String str6 = new String(rs.getString(6));
				String str7 = new String(rs.getString(7));

				model.addRow(new Object[] { String.valueOf(count), str1, "", str2, str3, str4, str5, str6, str7 });
				count += 1;
			}
		} catch (Exception localException) {
			System.out.println(localException);
		}
	}

	public void notinlist() {
		try {
			con = DBConnectionUtil.getConnection();
			stat = con.createStatement();
			rs = stat.executeQuery(
					"select emp_code from emp_status where emp_code NOT IN(SELECT distinct(emp_code) FROM timesheet where date ='"
							+ tf1.getText() + "')  ");

			while (rs.next()) {
				String str = new String(rs.getString(1));

				model.addRow(new Object[] { String.valueOf(count), str, " ", "0", "0", "0", "0", "0", "0" });
				count += 1;
			}

			count = 1;
			total = 0.0F;
		} catch (Exception localException) {
			System.out.println(localException);
		}
	}

	public void actionPerformed(ActionEvent paramActionEvent) {
		if ((paramActionEvent.getSource() == b1) || (paramActionEvent.getSource() == tf1)) {
			report();
			notinlist();
		}

		if (paramActionEvent.getSource() == b2) {
			f.setVisible(false);
		}
	}

}
