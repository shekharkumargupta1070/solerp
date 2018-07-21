package com.sol.erp;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

import com.sol.erp.util.DBConnectionUtil;
import com.sol.erp.util.DateDifferencesUtil;

public class OfficeHrs implements java.awt.event.ActionListener, java.awt.event.FocusListener {
	Connection con;
	Statement stat;
	ResultSet rs;
	int affected = 0;

	JDialog f = new JDialog();
	JTabbedPane tabbedpane = new JTabbedPane();

	JPanel emptab = new JPanel();
	JPanel timetab = new JPanel();

	JLabel l1 = new JLabel("In Time");
	JLabel l2 = new JLabel("Out Time");
	JLabel l3 = new JLabel("Total Hrs");

	JTextField tf1 = new JTextField("00.00");
	JTextField tf2 = new JTextField("00.00");
	JTextField tf3 = new JTextField("00.00");

	JLabel l4 = new JLabel("Break Out");
	JLabel l5 = new JLabel("Break IN");
	JLabel l6 = new JLabel("Break Hrs");

	JTextField tf4 = new JTextField("00.00");
	JTextField tf5 = new JTextField("00.00");
	JTextField tf6 = new JTextField("00.00");

	JLabel l7 = new JLabel("Late Count");
	JLabel l8 = new JLabel("OT Not Before ");
	JLabel l9 = new JLabel("Min. OT Value");

	JLabel l10 = new JLabel("Absent Count");

	JTextField tf7 = new JTextField("00.00");
	JTextField tf8 = new JTextField("00.00");
	JTextField tf9 = new JTextField("00.00");

	JTextField tf10 = new JTextField("00.00", 6);

	JLabel coidlabel = new JLabel("Co ID");
	JLabel brcodelabel = new JLabel("BR Code");

	JTextField coidtf = new JTextField("0", 3);
	JTextField brcodetf = new JTextField("0", 3);

	JLabel shiftlabel = new JLabel("Shift");
	JComboBox cb1 = new JComboBox();

	JLabel empcodelabel = new JLabel("Emp Code");
	JLabel empnamelabel = new JLabel("Name");
	JLabel dojlabel = new JLabel("DOJ");

	JTextField empcodetf = new JTextField(4);
	JTextField nametf = new JTextField(22);
	JTextField dojtf = new JTextField(8);

	JButton b1 = new JButton("Save");
	JButton b2 = new JButton("Update");
	JButton b3 = new JButton("Close");

	JPanel p1 = new JPanel();
	JPanel p2 = new JPanel();
	JPanel p3 = new JPanel();
	JPanel p4 = new JPanel();

	JPanel southp1 = new JPanel();
	JPanel southp2 = new JPanel();

	JPanel southPanel = new JPanel();
	JPanel mainPanel = new JPanel();

	JPanel empcenterpanel = new JPanel();

	java.awt.Font fo = new java.awt.Font("Tahoma", 1, 10);

	public void DesignFrame() {
		con = DBConnectionUtil.getConnection();
		java.awt.Container localContainer = f.getContentPane();
		localContainer.setLayout(new java.awt.BorderLayout());
		p1.setLayout(new java.awt.GridLayout(3, 2, 2, 2));
		p2.setLayout(new java.awt.GridLayout(3, 2, 2, 2));
		p3.setLayout(new java.awt.GridLayout(3, 2, 2, 2));

		southp1.setLayout(new java.awt.FlowLayout(0));
		southp2.setLayout(new java.awt.FlowLayout(2));

		emptab.setLayout(new java.awt.BorderLayout());
		timetab.setLayout(new java.awt.BorderLayout());
		mainPanel.setLayout(new java.awt.BorderLayout());
		southPanel.setLayout(new java.awt.BorderLayout());

		tabbedpane.add("Emp Shift", emptab);
		tabbedpane.add("Timing", timetab);

		empcenterpanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Define Shift for Emp"));

		empcenterpanel.add(empcodelabel);
		empcenterpanel.add(empcodetf);
		empcenterpanel.add(empnamelabel);
		empcenterpanel.add(nametf);
		empcenterpanel.add(dojlabel);
		empcenterpanel.add(dojtf);

		emptab.add(empcenterpanel, "Center");

		cb1.addItem("G");
		cb1.addItem("A");
		cb1.addItem("B");
		cb1.addItem("C");

		p1.add(l1);
		p1.add(tf1);
		p1.add(l2);
		p1.add(tf2);
		p1.add(l3);
		p1.add(tf3);

		p2.add(l4);
		p2.add(tf4);
		p2.add(l5);
		p2.add(tf5);
		p2.add(l6);
		p2.add(tf6);

		p3.add(l7);
		p3.add(tf7);
		p3.add(l8);
		p3.add(tf8);
		p3.add(l9);
		p3.add(tf9);

		p4.add(l10);
		p4.add(tf10);

		tf1.setHorizontalAlignment(4);
		tf2.setHorizontalAlignment(4);
		tf3.setHorizontalAlignment(4);
		tf4.setHorizontalAlignment(4);
		tf5.setHorizontalAlignment(4);
		tf6.setHorizontalAlignment(4);
		tf7.setHorizontalAlignment(4);
		tf8.setHorizontalAlignment(4);
		tf9.setHorizontalAlignment(4);
		tf10.setHorizontalAlignment(4);

		empcodetf.setHorizontalAlignment(4);
		nametf.setHorizontalAlignment(4);
		dojtf.setHorizontalAlignment(4);

		tf1.setFont(fo);
		tf2.setFont(fo);
		tf3.setFont(fo);
		tf3.setEditable(false);
		tf4.setFont(fo);
		tf5.setFont(fo);
		tf6.setFont(fo);
		tf6.setEditable(false);
		tf7.setFont(fo);
		tf8.setFont(fo);
		tf9.setFont(fo);
		tf10.setFont(fo);
		coidtf.setFont(fo);
		brcodetf.setFont(fo);

		empcodetf.setFont(fo);
		nametf.setFont(fo);
		nametf.setEditable(false);
		dojtf.setFont(fo);
		dojtf.setEditable(false);

		p1.setBorder(javax.swing.BorderFactory.createTitledBorder("Office HRS"));
		p2.setBorder(javax.swing.BorderFactory.createTitledBorder("Break Time"));
		p3.setBorder(javax.swing.BorderFactory.createTitledBorder("Validation"));

		p1.setPreferredSize(new java.awt.Dimension(180, 50));
		p2.setPreferredSize(new java.awt.Dimension(180, 50));
		p3.setPreferredSize(new java.awt.Dimension(180, 50));
		p4.setPreferredSize(new java.awt.Dimension(180, 50));

		southp1.add(coidlabel);
		southp1.add(coidtf);
		southp1.add(brcodelabel);
		southp1.add(brcodetf);
		southp1.add(shiftlabel);
		southp1.add(cb1);

		southp2.add(b1);
		southp2.add(b2);
		southp2.add(b3);

		southPanel.add(southp1, "West");
		southPanel.add(southp2, "East");

		timetab.add(p1, "West");
		timetab.add(p2, "Center");
		timetab.add(p3, "East");
		timetab.add(p4, "South");

		mainPanel.add(tabbedpane, "Center");
		mainPanel.add(southPanel, "South");

		localContainer.add(mainPanel, "Center");

		tf1.addFocusListener(this);
		tf2.addFocusListener(this);
		tf3.addFocusListener(this);
		tf4.addFocusListener(this);
		tf5.addFocusListener(this);
		tf6.addFocusListener(this);
		tf7.addFocusListener(this);
		tf8.addFocusListener(this);
		tf9.addFocusListener(this);
		tf10.addFocusListener(this);
		coidtf.addFocusListener(this);
		brcodetf.addFocusListener(this);

		b1.addActionListener(this);
		b2.addActionListener(this);
		b3.addActionListener(this);

		empcodetf.addActionListener(this);
		empcodetf.addFocusListener(this);
		nametf.addFocusListener(this);
		dojtf.addFocusListener(this);

		coidtf.addActionListener(this);
		brcodetf.addActionListener(this);
		cb1.addActionListener(this);
	}

	public void showOfficeHRS() {
		DesignFrame();
		f.setTitle("Setting Working Hrs Validations");
		f.getRootPane().setDefaultButton(b1);
		f.setSize(550, 230);
		f.setLocationRelativeTo(null);
		f.setVisible(true);
	}

	public void empDetails() {
		nametf.setText(EmpTB.getEmpName(empcodetf.getText()));
		dojtf.setText(EmpTB.getDOJ(empcodetf.getText()));
	}

	public void totalHRS() {
		tf3.setText(DateDifferencesUtil.getTimeDiff(tf1.getText(), tf2.getText()));
	}

	public void breakHRS() {
		tf6.setText(DateDifferencesUtil.getTimeDiff(tf4.getText(), tf5.getText()));
	}

	public void saveRecord() {
		try {
			con = DBConnectionUtil.getConnection();
			stat = con.createStatement();
			affected = stat.executeUpdate("insert into HRSHIFT values('" + coidtf.getText() + "','"
					+ brcodetf.getText() + "','" + cb1.getSelectedItem() + "','" + tf1.getText() + "','"
					+ tf2.getText() + "','" + tf3.getText() + "','" + tf4.getText() + "','"
					+ tf5.getText() + "','" + tf6.getText() + "','" + tf7.getText() + "','"
					+ tf8.getText() + "','" + tf9.getText() + "','" + tf10.getText() + "')");

			if (affected > 0) {
				JOptionPane.showMessageDialog(f, "Official Time Validation Saved");
				tf1.setText("00.00");
				tf2.setText("00.00");
				tf3.setText("00.00");
				tf4.setText("00.00");
				tf5.setText("00.00");
				tf6.setText("00.00");
				tf7.setText("00.00");
				tf8.setText("00.00");
				tf9.setText("00.00");
				tf10.setText("00.00");
			}
		} catch (Exception localException) {
			if (localException.getMessage().toString().equalsIgnoreCase("General error")) {
				JOptionPane.showMessageDialog(f, "Time allready Save");
			} else {
				JOptionPane.showMessageDialog(f, localException.getMessage().toString());
			}
		}
	}

	public void saveEmpShift() {
		try {
			con = DBConnectionUtil.getConnection();
			stat = con.createStatement();
			affected = stat.executeUpdate(
					"insert into HREMP_SHIFT values('" + coidtf.getText() + "','" + brcodetf.getText() + "','"
							+ empcodetf.getText() + "','" + cb1.getSelectedItem() + "') ");

			if (affected > 0) {
				JOptionPane.showMessageDialog(f, "Record Saved.");
				empcodetf.setText("");
				nametf.setText("");
				dojtf.setText("");
			}
		} catch (Exception localException) {
			if (localException.getMessage().toString().equalsIgnoreCase("General error")) {
				JOptionPane.showMessageDialog(f, "Record Allread Exist.");
			} else {
				JOptionPane.showMessageDialog(f, localException.getMessage().toString());
			}
		}
	}

	public void updateRecord() {
		try {
			con = DBConnectionUtil.getConnection();
			stat = con.createStatement();
			affected = stat.executeUpdate("update HRSHIFT set INTIME='" + tf1.getText() + "', OUTTIME='"
					+ tf2.getText() + "',TOTALTIME='" + tf3.getText() + "', BREAK_OUT='" + tf4.getText()
					+ "', BREAK_IN='" + tf5.getText() + "', TOTAL_BREAK='" + tf6.getText() + "', LATE_COUNT='"
					+ tf7.getText() + "', OT_NOT_BEFORE='" + tf8.getText() + "', MIN_OT_VALUE='"
					+ tf9.getText() + "', ABSENT_COUNT='" + tf10.getText() + "' WHERE COMPANY_ID='"
					+ coidtf.getText() + "' and BRANCH_CODE='" + brcodetf.getText() + "' and SHIFT='"
					+ cb1.getSelectedItem() + "'  ");

			if (affected > 0) {
				JOptionPane.showMessageDialog(f, "Record Updated");
			}
		} catch (Exception localException) {
			JOptionPane.showMessageDialog(f, localException.getMessage().toString());
		}
	}

	public void updateEmpRecord() {
		try {
			con = DBConnectionUtil.getConnection();
			stat = con.createStatement();
			affected = stat.executeUpdate("update HREMP_SHIFT set SHIFT='" + cb1.getSelectedItem()
					+ "' WHERE COMPANY_ID='" + coidtf.getText() + "' and BRANCH_CODE='" + brcodetf.getText()
					+ "' and EMP_CODE='" + empcodetf.getText() + "'  ");

			if (affected > 0) {
				JOptionPane.showMessageDialog(f, "Record Updated");
			}
		} catch (Exception localException) {
			JOptionPane.showMessageDialog(f, localException.getMessage().toString());
		}
	}

	public void showRecord() {
		tf1.setText("00.00");
		tf2.setText("00.00");
		tf3.setText("00.00");
		tf4.setText("00.00");
		tf5.setText("00.00");
		tf6.setText("00.00");
		tf7.setText("00.00");
		tf8.setText("00.00");
		tf9.setText("00.00");
		tf10.setText("00.00");

		try {
			con = DBConnectionUtil.getConnection();
			stat = con.createStatement();
			rs = stat.executeQuery(
					"select * from HRSHIFT where company_id='" + coidtf.getText() + "' and branch_code='"
							+ brcodetf.getText() + "' and shift='" + cb1.getSelectedItem() + "' ");

			while (rs.next()) {
				String str4 = new String(rs.getString(4));
				String str5 = new String(rs.getString(5));
				String str6 = new String(rs.getString(6));
				String str7 = new String(rs.getString(7));
				String str8 = new String(rs.getString(8));
				String str9 = new String(rs.getString(9));
				String str10 = new String(rs.getString(10));
				String str11 = new String(rs.getString(11));
				String str12 = new String(rs.getString(12));
				String str13 = new String(rs.getString(13));

				tf1.setText(str4);
				tf2.setText(str5);
				tf3.setText(str6);
				tf4.setText(str7);
				tf5.setText(str8);
				tf6.setText(str9);
				tf7.setText(str10);
				tf8.setText(str11);
				tf9.setText(str12);
				tf10.setText(str13);
			}
		} catch (Exception localException) {
			JOptionPane.showMessageDialog(f, localException.getMessage().toString());
		}
	}

	public void actionPerformed(ActionEvent paramActionEvent) {
		if (paramActionEvent.getSource() == b1) {
			if (tabbedpane.getSelectedIndex() == 0) {
				saveEmpShift();
			}
			if (tabbedpane.getSelectedIndex() == 1) {
				saveRecord();
			}
		}
		if ((paramActionEvent.getSource() == coidtf) || (paramActionEvent.getSource() == brcodetf)
				|| (paramActionEvent.getSource() == cb1)) {
			showRecord();
		}
		if (paramActionEvent.getSource() == empcodetf) {
			empDetails();
		}
		if (paramActionEvent.getSource() == b2) {
			if (tabbedpane.getSelectedIndex() == 0) {
				updateEmpRecord();
			}
			if (tabbedpane.getSelectedIndex() == 1) {
				updateRecord();
			}
		}
		if (paramActionEvent.getSource() == b3) {
			f.setVisible(false);
		}
	}

	public void focusGained(FocusEvent paramFocusEvent) {
		if (paramFocusEvent.getSource() == empcodetf) {
			empcodetf.setBackground(new Color(60, 60, 150));
			empcodetf.setForeground(Color.white);
			empcodetf.setCaretColor(Color.white);
		}
		if (paramFocusEvent.getSource() == nametf) {
			nametf.setBackground(new Color(60, 60, 150));
			nametf.setForeground(Color.white);
			nametf.setCaretColor(Color.white);
		}
		if (paramFocusEvent.getSource() == dojtf) {
			dojtf.setBackground(new Color(60, 60, 150));
			dojtf.setForeground(Color.white);
			dojtf.setCaretColor(Color.white);
		}
		if (paramFocusEvent.getSource() == tf1) {
			tf1.setBackground(new Color(60, 60, 150));
			tf1.setForeground(Color.white);
			tf1.setCaretColor(Color.white);
			tf1.selectAll();
		}
		if (paramFocusEvent.getSource() == tf2) {
			tf2.setBackground(new Color(60, 60, 150));
			tf2.setForeground(Color.white);
			tf2.setCaretColor(Color.white);
			tf2.selectAll();
		}
		if (paramFocusEvent.getSource() == tf3) {
			tf3.setBackground(new Color(60, 60, 150));
			tf3.setForeground(Color.white);
			tf3.setCaretColor(Color.white);
		}
		if (paramFocusEvent.getSource() == tf4) {
			tf4.setBackground(new Color(60, 60, 150));
			tf4.setForeground(Color.white);
			tf4.setCaretColor(Color.white);
			tf4.selectAll();
		}
		if (paramFocusEvent.getSource() == tf5) {
			tf5.setBackground(new Color(60, 60, 150));
			tf5.setForeground(Color.white);
			tf5.setCaretColor(Color.white);
			tf5.selectAll();
		}
		if (paramFocusEvent.getSource() == tf6) {
			tf6.setBackground(new Color(60, 60, 150));
			tf6.setForeground(Color.white);
			tf6.setCaretColor(Color.white);
		}
		if (paramFocusEvent.getSource() == tf7) {
			tf7.setBackground(new Color(60, 60, 150));
			tf7.setForeground(Color.white);
			tf7.setCaretColor(Color.white);
			tf7.selectAll();
		}
		if (paramFocusEvent.getSource() == tf8) {
			tf8.setBackground(new Color(60, 60, 150));
			tf8.setForeground(Color.white);
			tf8.setCaretColor(Color.white);
			tf8.selectAll();
		}
		if (paramFocusEvent.getSource() == tf9) {
			tf9.setBackground(new Color(60, 60, 150));
			tf9.setForeground(Color.white);
			tf9.setCaretColor(Color.white);
			tf9.selectAll();
		}
		if (paramFocusEvent.getSource() == tf10) {
			tf10.setBackground(new Color(60, 60, 150));
			tf10.setForeground(Color.white);
			tf10.setCaretColor(Color.white);
			tf10.selectAll();
		}
		if (paramFocusEvent.getSource() == coidtf) {
			coidtf.setBackground(new Color(60, 60, 150));
			coidtf.setForeground(Color.white);
			coidtf.setCaretColor(Color.white);
			coidtf.selectAll();
		}
		if (paramFocusEvent.getSource() == brcodetf) {
			brcodetf.setBackground(new Color(60, 60, 150));
			brcodetf.setForeground(Color.white);
			brcodetf.setCaretColor(Color.white);
			brcodetf.selectAll();
		}
	}

	public void focusLost(FocusEvent paramFocusEvent) {
		if (paramFocusEvent.getSource() == empcodetf) {
			empDetails();
		}
		if (paramFocusEvent.getSource() == empcodetf) {
			empcodetf.setForeground(new Color(60, 60, 150));
			empcodetf.setBackground(Color.white);
			empcodetf.setCaretColor(new Color(60, 60, 150));
		}
		if (paramFocusEvent.getSource() == nametf) {
			nametf.setForeground(new Color(60, 60, 150));
			nametf.setBackground(Color.white);
			nametf.setCaretColor(new Color(60, 60, 150));
		}
		if (paramFocusEvent.getSource() == dojtf) {
			dojtf.setForeground(new Color(60, 60, 150));
			dojtf.setBackground(Color.white);
			dojtf.setCaretColor(new Color(60, 60, 150));
		}
		if (paramFocusEvent.getSource() == tf1) {
			tf1.setForeground(new Color(60, 60, 150));
			tf1.setBackground(Color.white);
			tf1.setCaretColor(new Color(60, 60, 150));
		}
		if (paramFocusEvent.getSource() == tf2) {
			tf2.setForeground(new Color(60, 60, 150));
			tf2.setBackground(Color.white);
			tf2.setCaretColor(new Color(60, 60, 150));

			totalHRS();
		}
		if (paramFocusEvent.getSource() == tf3) {
			tf3.setForeground(new Color(60, 60, 150));
			tf3.setBackground(Color.white);
			tf3.setCaretColor(new Color(60, 60, 150));
		}
		if (paramFocusEvent.getSource() == tf4) {
			tf4.setForeground(new Color(60, 60, 150));
			tf4.setBackground(Color.white);
			tf4.setCaretColor(new Color(60, 60, 150));
		}
		if (paramFocusEvent.getSource() == tf5) {
			tf5.setForeground(new Color(60, 60, 150));
			tf5.setBackground(Color.white);
			tf5.setCaretColor(new Color(60, 60, 150));

			breakHRS();
		}
		if (paramFocusEvent.getSource() == tf6) {
			tf6.setForeground(new Color(60, 60, 150));
			tf6.setBackground(Color.white);
			tf6.setCaretColor(new Color(60, 60, 150));
		}
		if (paramFocusEvent.getSource() == tf7) {
			tf7.setForeground(new Color(60, 60, 150));
			tf7.setBackground(Color.white);
			tf7.setCaretColor(new Color(60, 60, 150));
		}
		if (paramFocusEvent.getSource() == tf8) {
			tf8.setForeground(new Color(60, 60, 150));
			tf8.setBackground(Color.white);
			tf8.setCaretColor(new Color(60, 60, 150));
		}
		if (paramFocusEvent.getSource() == tf9) {
			tf9.setForeground(new Color(60, 60, 150));
			tf9.setBackground(Color.white);
			tf9.setCaretColor(new Color(60, 60, 150));
		}
		if (paramFocusEvent.getSource() == tf10) {
			tf10.setForeground(new Color(60, 60, 150));
			tf10.setBackground(Color.white);
			tf10.setCaretColor(new Color(60, 60, 150));
		}
		if (paramFocusEvent.getSource() == coidtf) {
			coidtf.setForeground(new Color(60, 60, 150));
			coidtf.setBackground(Color.white);
			coidtf.setCaretColor(new Color(60, 60, 150));
		}
		if (paramFocusEvent.getSource() == brcodetf) {
			brcodetf.setForeground(new Color(60, 60, 150));
			brcodetf.setBackground(Color.white);
			brcodetf.setCaretColor(new Color(60, 60, 150));
		}
	}
}
