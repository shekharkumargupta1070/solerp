package com.sol.erp;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.FocusEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.sol.erp.util.DBConnectionUtil;

public class BreakTime implements java.awt.event.ActionListener, java.awt.event.FocusListener {
	Connection con;
	Statement stat;
	ResultSet rs;
	int affected;
	DecimalFormat df;
	DecimalFormat df1;
	DecimalFormat timeformat;
	JInternalFrame f;
	JLabel l1;
	JLabel l2;
	JLabel l3;
	JLabel l4;
	JLabel l5;
	JLabel l6;
	JLabel l7;
	JLabel l8;
	JLabel l9;
	JLabel l10;
	JLabel l11;
	JLabel northlabel;
	JLabel westlabel;
	JTextField tf1;
	JTextField tf2;
	JTextField tf3;
	JTextField tf4;
	JTextField tf5;
	JTextField tf6;
	static JComboBox tf7 = new JComboBox();

	JTextField tf8;

	JTextField tf9;

	JTextField tf10;

	javax.swing.JTextPane tfsp;

	javax.swing.JScrollPane tf11;

	JButton savebut;

	JButton closebut;

	JPanel centerpanel;

	JPanel northpanel;

	JPanel westpanel;

	JPanel southpanel;

	java.util.Date dat;

	SimpleDateFormat formatter;

	SimpleDateFormat formatter1;

	String dateString;

	String dateString1;
	java.awt.Toolkit tk;
	Dimension ss;
	Dimension fs;
	java.awt.Font fo;
	int maxreq;
	String maxstr;

	public void px() {
		java.awt.Container localContainer = f.getContentPane();
		localContainer.setLayout(new java.awt.BorderLayout());
		centerpanel.setLayout(null);
		northpanel.setLayout(new java.awt.BorderLayout());
		westpanel.setLayout(new java.awt.BorderLayout());

		northpanel.add(northlabel, "Center");
		westpanel.add(westlabel, "Center");

		centerpanel.setBackground(new Color(60, 130, 130));
		l1.setForeground(Color.white);
		l2.setForeground(Color.white);
		l3.setForeground(Color.white);
		l4.setForeground(Color.white);
		l5.setForeground(Color.white);
		l6.setForeground(Color.white);
		l7.setForeground(Color.white);
		l8.setForeground(Color.white);
		l9.setForeground(Color.white);
		l10.setForeground(Color.white);
		l11.setForeground(Color.white);

		tf1.setFont(fo);
		tf1.setEditable(false);
		tf2.setFont(fo);
		tf2.setEditable(false);
		tf3.setFont(fo);
		tf3.setEditable(false);
		tf4.setFont(fo);
		tf4.setEditable(false);
		tf5.setFont(fo);
		tf5.setEditable(false);
		tf6.setFont(fo);
		tf6.setEditable(false);
		tf7.setFont(fo);
		tf8.setFont(fo);
		tf9.setFont(fo);
		tf9.setEditable(true);
		tf10.setFont(fo);
		tfsp.setFont(fo);

		northpanel.setBackground(Color.white);
		westpanel.setBackground(Color.white);

		northpanel.setPreferredSize(new Dimension(100, 70));
		westpanel.setPreferredSize(new Dimension(150, 70));

		l1.setBounds(30, 30, 150, 20);
		tf1.setBounds(200, 30, 90, 20);
		l2.setBounds(300, 30, 60, 20);
		tf2.setBounds(360, 30, 90, 20);
		l3.setBounds(30, 55, 150, 20);
		tf3.setBounds(200, 55, 250, 20);
		l4.setBounds(30, 80, 150, 20);
		tf4.setBounds(200, 80, 250, 20);
		l5.setBounds(30, 105, 150, 20);
		tf5.setBounds(200, 105, 90, 20);
		l6.setBounds(30, 130, 150, 20);
		tf6.setBounds(200, 130, 90, 20);
		l7.setBounds(300, 130, 60, 20);
		tf7.setBounds(360, 130, 90, 20);
		l8.setBounds(30, 155, 150, 20);
		tf8.setBounds(200, 155, 90, 20);
		l9.setBounds(300, 155, 60, 20);
		tf9.setBounds(360, 155, 90, 20);
		l10.setBounds(30, 180, 150, 20);
		tf10.setBounds(200, 180, 90, 20);

		l11.setBounds(30, 205, 150, 20);
		tf11.setBounds(30, 230, 420, 90);

		savebut.setMnemonic(83);
		closebut.setMnemonic(67);

		centerpanel.add(l1);
		centerpanel.add(tf1);
		centerpanel.add(l2);
		centerpanel.add(tf2);
		centerpanel.add(l3);
		centerpanel.add(tf3);
		centerpanel.add(l4);
		centerpanel.add(tf4);
		centerpanel.add(l5);
		centerpanel.add(tf5);
		centerpanel.add(l6);
		centerpanel.add(tf6);
		centerpanel.add(l7);
		centerpanel.add(tf7);
		centerpanel.add(l8);
		centerpanel.add(tf8);
		centerpanel.add(l9);
		centerpanel.add(tf9);

		centerpanel.add(l11);
		centerpanel.add(tf11);

		southpanel.add(savebut);
		southpanel.add(closebut);

		savebut.addActionListener(this);
		closebut.addActionListener(this);
		tf10.addActionListener(this);

		tf2.addFocusListener(this);
		tf10.addFocusListener(this);

		localContainer.add(centerpanel, "Center");
		localContainer.add(southpanel, "South");

		f.setSize(490, 400);
		f.setLocation((ss.width - fs.width) / 4, (ss.height - fs.height) / 8);
		f.setResizable(false);
		f.setVisible(true);
		tf2.requestFocus();
	}

	public void paramUser() {
		tf2.setText(TechMainFrame.textFieldLoggedBy.getText());
		tf2.requestFocus();
	}

	public void empDetails() {
		try {
			con = DBConnectionUtil.getConnection();
			stat = con.createStatement();
			rs = stat.executeQuery(
					"select emp_name,DESIGNATION, System_no From emp_STATUS WHERE emp_code='" + tf2.getText() + "' ");
			rs.getMetaData().getColumnCount();
			while (rs.next()) {
				String str1 = new String(rs.getString(1));
				String str2 = new String(rs.getString(2));
				String str3 = new String(rs.getString(3));

				tf3.setText(str1);
				tf4.setText(str2);
				tf5.setText(str3);
				tf8.requestFocus();
			}
		} catch (Exception localException) {
			JOptionPane.showMessageDialog(f, localException.getMessage().toString());
		}
	}

	public BreakTime() {

		affected = 0;

		df = new DecimalFormat("00.00");
		df1 = new DecimalFormat("00");
		timeformat = new DecimalFormat("0000");

		f = new JInternalFrame("Break Time Request Form", true, false, false, false);
		l1 = new JLabel("Date");
		l2 = new JLabel("Emp Code");
		l3 = new JLabel("Name ");
		l4 = new JLabel("Designation");
		l5 = new JLabel("System No");
		l6 = new JLabel("Req Time");
		l7 = new JLabel("Req TL");
		l8 = new JLabel("Out Time");
		l9 = new JLabel("Exp In");
		l10 = new JLabel("Hrs");
		l11 = new JLabel("Reason");

		northlabel = new JLabel(new javax.swing.ImageIcon("image/projectco.gif"));
		westlabel = new JLabel(new javax.swing.ImageIcon("image/report.gif"));

		tf1 = new JTextField();
		tf2 = new JTextField();
		tf3 = new JTextField();
		tf4 = new JTextField();
		tf5 = new JTextField();
		tf6 = new JTextField();

		tf8 = new JTextField();
		tf9 = new JTextField();
		tf10 = new JTextField();
		tfsp = new javax.swing.JTextPane();
		tf11 = new javax.swing.JScrollPane(tfsp);

		savebut = new JButton("Send to TL");
		closebut = new JButton("Close");

		centerpanel = new JPanel();
		northpanel = new JPanel();
		westpanel = new JPanel();
		southpanel = new JPanel();

		dat = new java.util.Date();
		formatter = new SimpleDateFormat("dd/MM/yyyy");
		formatter1 = new SimpleDateFormat("hhmm");

		try {
			dateString = formatter.format(dat);
			dateString1 = formatter1.format(dat);
			tf1 = new JTextField(String.valueOf(dateString));
			tf6 = new JTextField(String.valueOf(dateString1));
		} catch (IllegalArgumentException localIllegalArgumentException) {
			System.out.println(localIllegalArgumentException.getMessage());
		}

		tk = java.awt.Toolkit.getDefaultToolkit();
		ss = tk.getScreenSize();
		fs = f.getSize();

		fo = new java.awt.Font("Tahoma", 1, 11);

		maxreq = 0;
		maxstr = "0";
	}

	public void maxReq() {
		try {
			con = DBConnectionUtil.getConnection();
			stat = con.createStatement();
			rs = stat.executeQuery("select max(req_no) From break_time where emp_code='" + tf2.getText()
					+ "' and date_month='" + tf1.getText() + "' ");

			if (rs.getRow() < 0) {
				maxstr = "0";
			}
			while (rs.next()) {
				maxstr = new String(rs.getString(1));

				maxreq = (Integer.parseInt(maxstr) + 1);
				System.out.println(maxreq);
			}
		} catch (Exception localException) {
			JOptionPane.showMessageDialog(f, localException.getMessage().toString());
		}
	}

	public void addTime() {
		Float.parseFloat(tf8.getText());
		Float.parseFloat(tf10.getText());
		String str1 = tf8.getText();
		str1.substring(0, 2);
		str1.substring(2, 4);
	}

	public void actionPerformed(java.awt.event.ActionEvent paramActionEvent) {
		if (paramActionEvent.getSource() == savebut) {
			maxReq();

			try {
				stat = con.createStatement();
				affected = stat.executeUpdate("insert into BREAK_TIME values('" + tf1.getText() + "', '" + tf2.getText()
						+ "','" + tf8.getText() + "','" + tf9.getText() + "','" + tfsp.getText() + "','0','" + maxreq
						+ "')  ");

				if (affected > 0) {
					int i = TechMainFrame.requestTableModel.getRowCount() + 1;
					TechMainFrame.requestTableModel
							.addRow(new Object[] { String.valueOf(i), tf1.getText(), tf2.getText(), tf3.getText(),
									tf4.getText(), tf5.getText(), tf6.getText(), tf7.getSelectedItem(), "BREAK" });
					tf8.setText("");
					tf9.setText("");
					tfsp.setText("");
					JOptionPane.showMessageDialog(f, "Your Request Sent to " + tf7.getSelectedItem());
					tf2.requestFocus();
				}
			} catch (Exception localException1) {
				JOptionPane.showMessageDialog(f, localException1.getMessage().toString());
			}

			int i = TechMainFrame.requestTableModel.getRowCount() + 1;

			try {
				stat = con.createStatement();
				stat.executeUpdate("insert into TIME_REQ values('" + String.valueOf(i) + "','" + tf1.getText() + "','"
						+ tf2.getText() + "','" + tf3.getText() + "','" + tf4.getText() + "','" + tf5.getText() + "','"
						+ tf6.getText() + "','" + tf7.getSelectedItem() + "','BREAK','0','" + maxreq + "' )  ");
			} catch (Exception localException2) {
				JOptionPane.showMessageDialog(f, localException2.getMessage().toString());
			}
		}

		if (paramActionEvent.getSource() == closebut) {
			f.setVisible(false);
		}

		if (paramActionEvent.getSource() == tf10) {
			addTime();
		}
	}

	public void focusGained(FocusEvent paramFocusEvent) {
		if (paramFocusEvent.getSource() == tf2) {
			empDetails();
		}
	}

	public void focusLost(FocusEvent paramFocusEvent) {
		if (paramFocusEvent.getSource() == tf10) {
			addTime();
		}
	}
}
