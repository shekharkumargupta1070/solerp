package com.sol.erp;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DecimalFormat;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.sol.erp.util.DBConnectionUtil;
import com.sol.erp.util.formater.SolDateFormatter;

public class ProjectCo implements java.awt.event.ActionListener, java.awt.event.FocusListener,
		java.awt.event.ItemListener, java.awt.event.KeyListener {
	
	Connection con;
	Statement stat;
	ResultSet rs;
	int affected;

	DecimalFormat df;
	DecimalFormat df1;
	DecimalFormat timeformat;
	JInternalFrame f;
	JLabel l;
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
	JLabel statuslabel;
	JLabel northlabel;
	JLabel westlabel;
	JTextField tf;
	public static JComboBox tf1 = new JComboBox();

	JTextField tf2;

	JTextField tf3;

	JComboBox tf4;

	JTextField tf5;

	JTextField tf6;

	JTextField tf7;

	JTextField tf8;

	JTextField tf9;

	JTextField tf10;

	JButton savebut;

	JButton setbut;

	JButton updatebut;

	JButton closebut;

	JPanel centerpanel;

	JPanel northpanel;

	JPanel westpanel;
	JPanel southpanel;
	JPanel mainPanel;
	java.util.Date dat;
	java.text.SimpleDateFormat formatter;
	String dateString;
	java.awt.Toolkit tk;
	Dimension ss;
	Dimension fs;
	java.awt.Font fo;
	int updated;

	public JPanel DesignFrame() {
		Container localContainer = f.getContentPane();
		localContainer.setLayout(new BorderLayout());
		mainPanel.setLayout(new BorderLayout());
		centerpanel.setLayout(null);
		northpanel.setLayout(new BorderLayout());
		westpanel.setLayout(new BorderLayout());

		northpanel.add(northlabel, "Center");
		westpanel.add(westlabel, "Center");

		tf1.setEditable(true);
		centerpanel.setBackground(new Color(60, 130, 130));
		l.setForeground(Color.white);
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

		tf.setFont(fo);
		tf.setEditable(true);

		tf2.setFont(fo);
		tf2.setEditable(false);
		tf3.setFont(fo);
		tf3.setEditable(false);
		tf4.setFont(fo);
		tf5.setFont(fo);
		tf5.setEditable(false);
		tf6.setFont(fo);
		tf6.setEditable(false);
		tf7.setFont(fo);
		tf8.setFont(fo);
		tf9.setFont(fo);
		tf9.setEditable(false);
		tf10.setFont(fo);
		tf10.setEditable(false);

		northpanel.setBackground(Color.white);
		westpanel.setBackground(Color.white);

		northpanel.setPreferredSize(new Dimension(100, 70));
		westpanel.setPreferredSize(new Dimension(150, 70));

		l.setBounds(30, 30, 150, 20);
		tf.setBounds(200, 30, 90, 20);
		l1.setBounds(300, 30, 60, 20);
		tf1.setBounds(360, 30, 90, 20);
		l2.setBounds(30, 55, 150, 20);
		tf2.setBounds(200, 55, 250, 20);
		l3.setBounds(30, 80, 150, 20);
		tf3.setBounds(200, 80, 250, 20);
		l4.setBounds(30, 105, 150, 20);
		tf4.setBounds(200, 105, 60, 20);
		l5.setBounds(270, 105, 90, 20);
		tf5.setBounds(320, 105, 130, 20);
		l6.setBounds(30, 130, 150, 20);
		tf6.setBounds(200, 130, 250, 20);
		l7.setBounds(30, 155, 150, 20);
		tf7.setBounds(200, 155, 90, 20);
		l8.setBounds(300, 155, 60, 20);
		tf8.setBounds(360, 155, 90, 20);
		l9.setBounds(30, 180, 150, 20);
		tf9.setBounds(200, 180, 90, 20);
		l10.setBounds(300, 180, 60, 20);
		tf10.setBounds(360, 180, 90, 20);

		savebut.setBounds(150, 205, 100, 25);
		closebut.setBounds(270, 205, 100, 25);

		centerpanel.add(l);
		centerpanel.add(tf);
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

		southpanel.add(savebut);
		southpanel.add(setbut);
		setbut.setEnabled(true);

		savebut.setMnemonic(83);
		setbut.setMnemonic(78);
		closebut.setMnemonic(67);

		savebut.addActionListener(this);
		setbut.addActionListener(this);
		updatebut.addActionListener(this);
		closebut.addActionListener(this);
		tf8.addActionListener(this);
		tf4.addFocusListener(this);

		tf1.addKeyListener(this);
		tf1.addItemListener(this);

		mainPanel.add(centerpanel, "Center");

		mainPanel.add(southpanel, "South");

		localContainer.add(mainPanel, "Center");

		TLCode();

		return mainPanel;
	}

	public void px() {
		DesignFrame();
		f.setSize(650, 355);
		f.setLocation((ss.width - fs.width) / 6, (ss.height - fs.height) / 8);
		f.setVisible(true);
		f.setResizable(false);
	}

	public void TLCode() {
		try {
			con = DBConnectionUtil.getConnection();
			stat = con.createStatement();
			rs = stat.executeQuery(
					"select distinct(Emp_code) From HREMP_JOIN where Desig='Team Leader' or Desig='Project Manager' ");
			while (rs.next()) {
				String str = new String(rs.getString(1));
				tf4.addItem(str);
			}
		} catch (Exception localException) {
			JOptionPane.showMessageDialog(f, localException.getMessage().toString());
		}
	}

	public void projectDetails() {
		tf5.setText("");
		tf6.setText("");
		tf7.setText("");
		tf8.setText("");
		tf7.setText(dateString);
		try {
			con = DBConnectionUtil.getConnection();
			stat = con.createStatement();
			rs = stat.executeQuery(
					"select max(project_name), max(client_name), SUM(TOTAL_TIME),sum(sheet_Qty) From estimation_MP WHERE project_no='"
							+ tf1.getSelectedItem() + "' ");
			while (rs.next()) {
				String str1 = new String(rs.getString(1));
				String str2 = new String(rs.getString(2));
				String str3 = new String(rs.getString(3));
				String str4 = new String(rs.getString(4));

				tf2.setText(str1);
				tf3.setText(str2);
				tf9.setText(str3);
				tf10.setText(str4);
			}
		} catch (Exception localException) {
			JOptionPane.showMessageDialog(f, localException.getMessage().toString());
		}
	}

	public ProjectCo() {

		affected = 0;

		df = new DecimalFormat("00.00");
		df1 = new DecimalFormat("00");
		timeformat = new DecimalFormat("0000");

		f = new JInternalFrame("Assign Project to Team Leader", true, true, true, true);
		l = new JLabel("Date");
		l1 = new JLabel("Project No");
		l2 = new JLabel("Project Name");
		l3 = new JLabel("Client Name");
		l4 = new JLabel("TL Code");
		l5 = new JLabel("Desig");
		l6 = new JLabel("Name");
		l7 = new JLabel("Start Date");
		l8 = new JLabel("Final Date");
		l9 = new JLabel("Quoted Hrs");
		l10 = new JLabel("# of Sheet");

		statuslabel = new JLabel("F1 (Alloted Project), F2 (Fresh Project) ");

		northlabel = new JLabel(new javax.swing.ImageIcon("image/projectco.gif"));
		westlabel = new JLabel(new javax.swing.ImageIcon("image/report.gif"));

		tf = new JTextField();

		tf2 = new JTextField();
		tf3 = new JTextField();
		tf4 = new JComboBox();
		tf5 = new JTextField();
		tf6 = new JTextField();
		tf7 = new JTextField();
		tf8 = new JTextField();
		tf9 = new JTextField();
		tf10 = new JTextField();

		savebut = new JButton("Save");
		setbut = new JButton("Update Record");
		updatebut = new JButton("Set New Team Leader");
		closebut = new JButton("Close");

		centerpanel = new JPanel();
		northpanel = new JPanel();
		westpanel = new JPanel();
		southpanel = new JPanel();

		mainPanel = new JPanel();

		dat = new java.util.Date();
		formatter = new java.text.SimpleDateFormat("dd/MM/yyyy");

		try {
			dateString = formatter.format(dat);
			tf = new JTextField(String.valueOf(dateString));
			tf7 = new JTextField(String.valueOf(dateString));
		} catch (IllegalArgumentException localIllegalArgumentException) {
			System.out.println(localIllegalArgumentException.getMessage());
		}

		tk = java.awt.Toolkit.getDefaultToolkit();
		ss = tk.getScreenSize();
		fs = f.getSize();

		fo = new java.awt.Font("Tahoma", 1, 11);

		updated = 0;
	}

	public void details() {
		try {
			con = DBConnectionUtil.getConnection();
			PreparedStatement localPreparedStatement = con.prepareStatement(
					"SELECT CO_CODE, START, NEW_FINAL_DATE, UPDATED FROM PROJECT_CO WHERE PROJECT_NO = ? ");
			localPreparedStatement.setInt(1, Integer.parseInt((String) tf1.getSelectedItem()));
			rs = localPreparedStatement.executeQuery();

			rs.getMetaData().getColumnCount();
			while (rs.next()) {
				String str1 = new String(rs.getString(1));
				java.sql.Date localDate1 = rs.getDate(2);
				java.sql.Date localDate2 = rs.getDate(3);
				String str2 = new String(rs.getString(4));

				String str3 = SolDateFormatter.SQLtoDDMMYY(localDate1);
				String str4 = SolDateFormatter.SQLtoDDMMYY(localDate2);

				tf4.setSelectedItem(str1);
				tf7.setText(str3);
				tf8.setText(str4);
				tf4.requestFocus();

				updated = (Integer.parseInt(str2) + 1);
			}
		} catch (Exception localException) {
			JOptionPane.showMessageDialog(f, localException.getMessage().toString());
			System.out.println(localException);
		}
	}

	public void empDetails() {
		try {
			con = DBConnectionUtil.getConnection();
			stat = con.createStatement();
			rs = stat.executeQuery(
					"select DESIGNATION,emp_name From emp_STATUS WHERE emp_code='" + tf4.getSelectedItem() + "' ");
			rs.getMetaData().getColumnCount();
			while (rs.next()) {
				String str1 = new String(rs.getString(1));
				String str2 = new String(rs.getString(2));

				tf5.setText(str1);
				tf6.setText(str2);
			}
		} catch (Exception localException) {
			JOptionPane.showMessageDialog(f, localException.getMessage().toString());
		}
	}

	public void saveRecord() {
		java.sql.Date localDate1 = SolDateFormatter.DDMMYYtoSQL(tf.getText());
		java.sql.Date localDate2 = SolDateFormatter.DDMMYYtoSQL(tf7.getText());
		java.sql.Date localDate3 = SolDateFormatter.DDMMYYtoSQL(tf8.getText());
		java.sql.Date localDate4 = SolDateFormatter.DDMMYYtoSQL(tf8.getText());

		try {
			PreparedStatement localPreparedStatement = con
					.prepareStatement("insert into project_co values(?,?,?,?,?,?,?,?,?,?,?,?,?)");
			localPreparedStatement.setDate(1, localDate1);
			localPreparedStatement.setInt(2, Integer.parseInt((String) tf1.getSelectedItem()));
			localPreparedStatement.setString(3, tf2.getText());
			localPreparedStatement.setString(4, tf3.getText());
			localPreparedStatement.setString(5, (String) tf4.getSelectedItem());
			localPreparedStatement.setString(6, tf5.getText());
			localPreparedStatement.setString(7, tf6.getText());
			localPreparedStatement.setDate(8, localDate2);
			localPreparedStatement.setDate(9, localDate3);
			localPreparedStatement.setFloat(10, Float.parseFloat(tf9.getText()));
			localPreparedStatement.setFloat(11, Float.parseFloat(tf10.getText()));
			localPreparedStatement.setDate(12, localDate4);
			localPreparedStatement.setString(13, "0");
			affected = localPreparedStatement.executeUpdate();
			if (affected > 0) {
				JOptionPane.showMessageDialog(f, tf1.getSelectedItem() + " Assigned to "
						+ tf4.getSelectedItem() + " (" + tf6.getText() + ") ");
				tf2.setText("");
				tf3.setText("");
				tf5.setText("");
				tf6.setText("");
				tf9.setText("");
				tf10.setText("");
			}
		} catch (Exception localException) {
			if (localException.getMessage().toString().equalsIgnoreCase("General error")) {
				JOptionPane.showMessageDialog(f, tf1.getSelectedItem() + " Allready Assigned.");
			} else {
				JOptionPane.showMessageDialog(f, localException.getMessage().toString());
			}
		}
	}

	public void updateProject() {
		String str = String.valueOf(updated);
		SolDateFormatter.DDMMYYtoSQL(tf.getText());
		java.sql.Date localDate2 = SolDateFormatter.DDMMYYtoSQL(tf7.getText());
		java.sql.Date localDate3 = SolDateFormatter.DDMMYYtoSQL(tf8.getText());
		java.sql.Date localDate4 = SolDateFormatter.DDMMYYtoSQL(tf8.getText());

		try {
			PreparedStatement localPreparedStatement = con.prepareStatement(
					"update Project_Co set Co_Code=?, Desig=?, Name=?, start=?, final=?, new_final_date=?, updated=? where project_no=?  ");
			localPreparedStatement.setString(1, (String) tf4.getSelectedItem());
			localPreparedStatement.setString(2, tf5.getText());
			localPreparedStatement.setString(3, tf6.getText());
			localPreparedStatement.setDate(4, localDate2);
			localPreparedStatement.setDate(5, localDate3);
			localPreparedStatement.setDate(6, localDate4);
			localPreparedStatement.setString(7, str);
			localPreparedStatement.setInt(8, Integer.parseInt((String) tf1.getSelectedItem()));
			affected = localPreparedStatement.executeUpdate();

			if (affected > 0) {
				stat.executeUpdate("update QUOTATION_LIST set DATE ='" + tf.getText() + "' where project_no='"
						+ tf1.getSelectedItem() + "'  ");
				stat.executeUpdate("update projectmanpower set project_co='" + tf4.getSelectedItem()
						+ "' where project_no='" + tf1.getSelectedItem() + "'  ");

				JOptionPane.showMessageDialog(f, tf1.getSelectedItem() + " is Updated.");
				tf2.setText("");
				tf3.setText("");
				tf5.setText("");
				tf6.setText("");
				tf9.setText("");
				tf10.setText("");
			}
		} catch (Exception localException) {
			if (localException.getMessage().toString().equalsIgnoreCase("General error")) {
				JOptionPane.showMessageDialog(f, tf1.getSelectedItem() + " Allready Assigned.");
			} else {
				JOptionPane.showMessageDialog(f, localException.getMessage().toString());
			}
		}
	}

	public void actionPerformed(ActionEvent paramActionEvent) {
		if ((paramActionEvent.getSource() == savebut) || (paramActionEvent.getSource() == tf8)) {
			saveRecord();
		}
		if (paramActionEvent.getSource() == setbut) {
			updateProject();
		}

		if (paramActionEvent.getSource() != updatebut){
		    
		}
		if(paramActionEvent.getSource() == closebut) {
			f.setVisible(false);
		}
	}

	public void itemStateChanged(java.awt.event.ItemEvent paramItemEvent) {
		if (paramItemEvent.getSource() == tf1) {
			projectDetails();
			details();
			empDetails();
		}
	}

	public void focusLost(FocusEvent paramFocusEvent) {
		if (paramFocusEvent.getSource() == tf4) {
			empDetails();
		}
	}

	public void keyReleased(KeyEvent paramKeyEvent) {
		int i = paramKeyEvent.getKeyCode();
		if (paramKeyEvent.getSource() == tf1) {
			if ((i != 112) || (

			(i != 113) ||

			(i == 116))) {
				details();
			}
		}
	}

	public void focusGained(FocusEvent paramFocusEvent) {
	}

	public void keyPressed(KeyEvent paramKeyEvent) {
	}

	public void keyTyped(KeyEvent paramKeyEvent) {
	}
}
