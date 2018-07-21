package com.sol.erp;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import com.sol.erp.util.DBConnectionUtil;

public class RevisionTime
		implements java.awt.event.ActionListener, java.awt.event.FocusListener, java.awt.event.ItemListener {
	
	Connection con;
	Statement stat;
	ResultSet rs;
	int affected;
	java.text.DecimalFormat df;
	java.text.DecimalFormat df1;
	java.text.DecimalFormat timeformat;
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
	JLabel l12;
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
	static JComboBox tf9 = new JComboBox();
	javax.swing.JTextArea tfsp;
	javax.swing.JScrollPane tf10;
	static JComboBox tf11 = new JComboBox();
	JTextField tf12;
	JTextField reqtf;
	JButton savebut;
	JButton closebut;
	JRadioButton rb1;
	JRadioButton rb2;
	JRadioButton rb3;
	javax.swing.ButtonGroup grp;
	JPanel centerpanel;
	JPanel northpanel;
	JPanel westpanel;
	JPanel southpanel;
	java.util.Date dat;
	java.text.SimpleDateFormat formatter;
	java.text.SimpleDateFormat formatter1;
	String dateString;
	String dateString1;
	java.awt.Toolkit tk;
	Dimension ss;
	Dimension fs;
	java.awt.Font fo;

	public RevisionTime() {

		affected = 0;

		df = new java.text.DecimalFormat("00.00");
		df1 = new java.text.DecimalFormat("00");
		timeformat = new java.text.DecimalFormat("0000");

		f = new JInternalFrame("BFA/Revision/Wasted Time Request Form", true, false, false, true);
		l1 = new JLabel("Date");
		l2 = new JLabel("Emp Code");
		l3 = new JLabel("Name ");
		l4 = new JLabel("Designation");
		l5 = new JLabel("System No");
		l6 = new JLabel("Req Time");
		l7 = new JLabel("Req TL");
		l8 = new JLabel("Time ");
		l9 = new JLabel("Project No");
		l10 = new JLabel("Reason/Remarks");
		l11 = new JLabel("Drg No*");
		l12 = new JLabel("Item Code");

		northlabel = new JLabel(new javax.swing.ImageIcon("image/projectco.gif"));
		westlabel = new JLabel(new javax.swing.ImageIcon("image/report.gif"));

		tf1 = new JTextField();
		tf2 = new JTextField();
		tf3 = new JTextField();
		tf4 = new JTextField();
		tf5 = new JTextField();
		tf6 = new JTextField();

		tf8 = new JTextField();

		tfsp = new javax.swing.JTextArea();
		tf10 = new javax.swing.JScrollPane(tfsp);

		tf12 = new JTextField();

		reqtf = new JTextField("1", 4);

		savebut = new JButton("Send to TL");
		closebut = new JButton("Close");

		rb1 = new JRadioButton("BFA", true);
		rb2 = new JRadioButton("Revision", false);
		rb3 = new JRadioButton("Wasted", false);

		grp = new javax.swing.ButtonGroup();

		centerpanel = new JPanel();
		northpanel = new JPanel();
		westpanel = new JPanel();
		southpanel = new JPanel();

		dat = new java.util.Date();
		formatter = new java.text.SimpleDateFormat("dd/MM/yyyy");
		formatter1 = new java.text.SimpleDateFormat("hhmm");

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
	}

	public void px() {
		java.awt.Container localContainer = f.getContentPane();
		localContainer.setLayout(new java.awt.BorderLayout());
		centerpanel.setLayout(null);
		westpanel.setLayout(new java.awt.BorderLayout());

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
		l12.setForeground(Color.white);

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
		tfsp.setFont(fo);
		tf9.setFont(fo);
		tf10.setFont(fo);
		tf11.setFont(fo);
		tf11.setEditable(false);
		tf12.setFont(fo);
		tf12.setEditable(false);

		grp.add(rb1);
		grp.add(rb2);
		grp.add(rb3);

		northpanel.add(rb1);
		northpanel.add(rb2);
		northpanel.add(rb3);
		northpanel.add(reqtf);
		reqtf.setEditable(false);
		reqtf.setFont(fo);

		westpanel.setBackground(Color.white);

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
		l11.setBounds(30, 180, 150, 20);
		tf11.setBounds(200, 180, 90, 20);
		l12.setBounds(300, 180, 60, 20);
		tf12.setBounds(360, 180, 90, 20);

		l10.setBounds(30, 205, 150, 20);
		tf10.setBounds(30, 225, 420, 90);

		savebut.setBounds(60, 320, 100, 25);
		closebut.setBounds(305, 320, 100, 25);

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
		centerpanel.add(l10);
		centerpanel.add(tf10);
		centerpanel.add(l11);
		centerpanel.add(tf11);
		centerpanel.add(l12);
		centerpanel.add(tf12);

		southpanel.add(savebut);
		southpanel.add(closebut);

		savebut.addActionListener(this);
		closebut.addActionListener(this);

		tf2.addFocusListener(this);
		tf11.addFocusListener(this);
		tf11.addActionListener(this);

		tf7.addItemListener(this);
		tf9.addItemListener(this);
		tf11.addItemListener(this);

		rb1.addActionListener(this);
		rb2.addActionListener(this);
		rb3.addActionListener(this);

		localContainer.add(centerpanel, "Center");
		localContainer.add(southpanel, "South");
		localContainer.add(northpanel, "North");

		f.setSize(490, 460);
		f.setLocation((ss.width - fs.width) / 4, (ss.height - fs.height) / 8);
		f.setResizable(false);
		f.setVisible(true);
		tf2.requestFocus();
	}

	public void paramUser() {
		tf2.setText(TechMainFrame.textFieldLoggedBy.getText());
		tf2.requestFocus();
		projectList();
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
			}
		} catch (Exception localException) {
			JOptionPane.showMessageDialog(f, localException.getMessage().toString());
		}
	}

	public void maxReq() {
		try {
			con = DBConnectionUtil.getConnection();
			stat = con.createStatement();
			rs = stat.executeQuery("select max(req_no) From time_req where emp_code='" + tf2.getText()
					+ "' and date_month='" + tf1.getText() + "' ");
			int i = rs.getRow();

			System.out.println(i);

			while (rs.next()) {
				String str = new String(rs.getString(1));
				int j = Integer.parseInt(str) + 1;
				reqtf.setText(String.valueOf(j));
			}
		} catch (Exception localException) {
			JOptionPane.showMessageDialog(f, localException.getMessage().toString());
		}
	}

	public void itemCode() {
		try {
			con = DBConnectionUtil.getConnection();
			stat = con.createStatement();
			rs = stat.executeQuery("select item_code from drawingno where project_no='" + tf9.getSelectedItem()
					+ "' and DRAWING_NO='" + tf11.getSelectedItem() + "' ");
			while (rs.next()) {
				String str = new String(rs.getString(1));
				tf12.setText(str);
			}
		} catch (Exception localException) {
			JOptionPane.showMessageDialog(f, localException.getMessage().toString());
		}
	}

	public void TLCode() {
		tf7.removeAllItems();
		try {
			con = DBConnectionUtil.getConnection();
			stat = con.createStatement();
			rs = stat.executeQuery("select co_code From project_co where project_no='" + tf9.getSelectedItem() + "' ");
			while (rs.next()) {
				String str = new String(rs.getString(1));
				tf7.addItem(str);
			}
		} catch (Exception localException) {
			JOptionPane.showMessageDialog(f, localException.getMessage().toString());
		}
	}

	public void projectList() {
		tf9.removeAllItems();
		tf9.addItem("XXXXXX");
		try {
			con = DBConnectionUtil.getConnection();
			stat = con.createStatement();
			rs = stat.executeQuery(
					"select distinct(Project_no) From projectManPower where EMP_CODE='" + tf2.getText() + "' ");
			while (rs.next()) {
				String str = new String(rs.getString(1));
				tf9.addItem(str);
			}
		} catch (Exception localException) {
			JOptionPane.showMessageDialog(f, localException.getMessage().toString());
		}
	}

	public void drgList() {
		tf11.removeAllItems();

		String str1 = "0";

		if (rb1.isSelected()) {
			str1 = "select DISTINCT(drawing_no) From APPROVAL_DATA where project_no='" + tf9.getSelectedItem()
					+ "' and bfa_status='C'  ";
		}

		if (rb2.isSelected()) {
			str1 = "select DISTINCT(drg_no) From timesheet where project_no='" + tf9.getSelectedItem() + "' ";
		}

		if (rb3.isSelected()) {
			str1 = "select drawing_no From drawingno where project_no='" + tf9.getSelectedItem() + "' ";
		}

		try {
			con = DBConnectionUtil.getConnection();
			stat = con.createStatement();
			rs = stat.executeQuery(str1);
			while (rs.next()) {
				String str2 = new String(rs.getString(1));
				tf11.addItem(str2);
			}
		} catch (Exception localException) {
			JOptionPane.showMessageDialog(f, localException.getMessage().toString());
		}
	}

	public void itemStateChanged(java.awt.event.ItemEvent paramItemEvent) {
		if (paramItemEvent.getSource() == tf9) {
			drgList();
			TLCode();
		}
	}

	public void actionPerformed(ActionEvent paramActionEvent) {
		if (paramActionEvent.getSource() == rb1) {
			tf9.setEditable(false);
			tf11.setEditable(false);
		}
		if (paramActionEvent.getSource() == rb2) {
			tf9.setEditable(false);
			tf11.setEditable(false);
		}
		if (paramActionEvent.getSource() == rb3) {
			tf9.setEditable(true);
			tf11.setEditable(true);
		}

		if (paramActionEvent.getSource() == savebut) {
			String str1 = "Revision_time";
			String str2 = "Revision";
			if (rb1.isSelected()) {
				str1 = "BFA_time";
				str2 = "BFA";
			}
			if (rb2.isSelected()) {
				str1 = "Revision_time";
				str2 = "Revision";
			}
			if (rb3.isSelected()) {
				str1 = "Wasted";
				str2 = "Wasted";
			}

			try {
				con = DBConnectionUtil.getConnection();
				stat = con.createStatement();
				affected = stat.executeUpdate("insert into " + str1 + " values('" + tf1.getText() + "', '"
						+ tf2.getText() + "','" + tf9.getSelectedItem() + "','" + tf11.getSelectedItem() + "', '"
						+ tf12.getText() + "', '" + tf8.getText() + "','" + tfsp.getText() + "','0','" + reqtf.getText()
						+ "')  ");

				if (affected > 0) {
					new TechMainFrame();
					int i = TechMainFrame.requestTableModel.getRowCount() + 1;
					TechMainFrame.requestTableModel
							.addRow(new Object[] { String.valueOf(i), tf1.getText(), tf2.getText(), reqtf.getText(),
									tf3.getText(), tf4.getText(), tf6.getText(), tf7.getSelectedItem(), str2, "0" });
					tf8.setText("");
					tfsp.setText("");
					JOptionPane.showMessageDialog(f, "Your Request Sent to " + tf7.getSelectedItem());
					tf2.requestFocus();

					new TechMainFrame();
					int j = TechMainFrame.requestTableModel.getRowCount() + 1;

					stat.executeUpdate("insert into TIME_REQ values('" + String.valueOf(j) + "','" + tf1.getText()
							+ "','" + tf2.getText() + "','" + tf3.getText() + "','" + tf4.getText() + "','"
							+ tf5.getText() + "','" + tf6.getText() + "','" + tf7.getSelectedItem() + "','" + str2
							+ "','0','" + reqtf.getText() + "' )  ");
					reqtf.setText(String.valueOf(Integer.parseInt(reqtf.getText()) + 1));
				}
			} catch (Exception localException) {
				JOptionPane.showMessageDialog(f, localException.getMessage().toString());
			}
		}

		if (paramActionEvent.getSource() == tf11) {
			itemCode();
		}

		if (paramActionEvent.getSource() == closebut) {
			f.setVisible(false);
		}

		if (paramActionEvent.getSource() == rb1) {
			tf9.setSelectedIndex(0);
		}
		if (paramActionEvent.getSource() == rb2) {
			tf9.setSelectedIndex(0);
		}
		if (paramActionEvent.getSource() == rb3) {
			tf9.setSelectedIndex(0);
		}
	}

	public void focusGained(java.awt.event.FocusEvent paramFocusEvent) {
		if (paramFocusEvent.getSource() == tf2) {
			empDetails();
		}
	}

	public void focusLost(java.awt.event.FocusEvent paramFocusEvent) {
		if (paramFocusEvent.getSource() == tf9) {
			itemCode();
		}
		if (paramFocusEvent.getSource() == tf11) {
			tf11.setEditable(false);
			itemCode();
		}
	}

}
