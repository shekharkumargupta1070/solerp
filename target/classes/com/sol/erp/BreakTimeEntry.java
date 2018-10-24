package com.sol.erp;

import com.sol.erp.util.DBConnectionUtil;
import com.sol.erp.util.DateDifferencesUtil;
import com.sol.erp.util.formater.SolDateFormatter;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

public class BreakTimeEntry
		implements java.awt.event.ActionListener, java.awt.event.FocusListener, java.awt.event.KeyListener {
    
	java.sql.Connection con;
	java.sql.PreparedStatement pstat;
	java.sql.ResultSet rs;
	int affected;
	String[] heads;
	Object[][] data;
	java.text.DecimalFormat df;
	java.text.DecimalFormat df1;
	java.text.DecimalFormat timeformat;
	private Attendance attendance;

	public BreakTimeEntry() {
		affected = 0;

		heads = new String[] { "Emp Code", "Out", "In", "Total" };
		data = new Object[0][];

		df = new java.text.DecimalFormat("00.00");
		df1 = new java.text.DecimalFormat("00");
		timeformat = new java.text.DecimalFormat("0000");

		model = new javax.swing.table.DefaultTableModel(data, heads);
		tb = new javax.swing.JTable(model);
		sp = new javax.swing.JScrollPane(tb);

		f = new javax.swing.JFrame("Break & Extra OT. Time Entry");
		l1 = new javax.swing.JLabel("Date");
		l2 = new javax.swing.JLabel("Emp Code");
		l3 = new javax.swing.JLabel("Name ");

		l4 = new javax.swing.JLabel("Out Time");
		l5 = new javax.swing.JLabel("In Time");
		l6 = new javax.swing.JLabel("Total Time ");

		tf1 = new JTextField(10);
		tf2 = new JTextField(5);
		tf3 = new JTextField(14);

		tf4 = new JTextField(5);
		tf5 = new JTextField(5);
		tf6 = new JTextField(5);

		westlabel = new javax.swing.JLabel(new javax.swing.ImageIcon("image/time.gif"));

		deletebut = new javax.swing.JButton("Delete");
		savebut = new javax.swing.JButton("Save");
		closebut = new javax.swing.JButton("Close");

		rb1 = new javax.swing.JRadioButton("BT Entry", true);
		rb2 = new javax.swing.JRadioButton("OT Entry", false);

		grp = new javax.swing.ButtonGroup();

		centerpanel = new javax.swing.JPanel();
		northpanel = new javax.swing.JPanel();
		westpanel = new javax.swing.JPanel();
		southpanel = new javax.swing.JPanel();

		centernorthpanel = new javax.swing.JPanel();

		dat = new java.util.Date();
		formatter = new java.text.SimpleDateFormat("dd/MM/yyyy");
		formatter1 = new java.text.SimpleDateFormat("hhmm");

		try {
			dateString = formatter.format(dat);
			dateString1 = formatter1.format(dat);
			tf1 = new JTextField(String.valueOf(dateString));
		} catch (IllegalArgumentException localIllegalArgumentException) {
			System.out.println(localIllegalArgumentException.getMessage());
		}

		tk = java.awt.Toolkit.getDefaultToolkit();
		ss = tk.getScreenSize();
		fs = f.getSize();

		fo = new java.awt.Font("Verdana", 0, 9);

		lineborder = javax.swing.BorderFactory.createLineBorder(java.awt.Color.white);
		bor1 = javax.swing.BorderFactory.createTitledBorder(lineborder, "Break & Extra OT. Time Entry", 0, 0, fo,
				java.awt.Color.white);
	}

	public void px() {
		java.awt.Container localContainer = f.getContentPane();
		localContainer.setLayout(new java.awt.BorderLayout());
		centerpanel.setLayout(new java.awt.BorderLayout());
		centernorthpanel.setLayout(new java.awt.GridLayout(3, 2, 5, 5));
		northpanel.setLayout(new java.awt.FlowLayout());
		westpanel.setLayout(new java.awt.BorderLayout());
		southpanel.setLayout(new java.awt.FlowLayout(2));

		centerpanel.setBackground(new java.awt.Color(60, 130, 130));
		centernorthpanel.setBackground(new java.awt.Color(60, 130, 130));
		l4.setForeground(java.awt.Color.white);
		l5.setForeground(java.awt.Color.white);
		l6.setForeground(java.awt.Color.white);

		l4.setHorizontalAlignment(4);
		l5.setHorizontalAlignment(4);
		l6.setHorizontalAlignment(4);

		tf1.setFont(fo);
		tf1.setEditable(true);
		tf2.setFont(fo);
		tf2.setEditable(true);
		tf3.setFont(fo);
		tf3.setEditable(false);
		tf4.setFont(fo);
		tf2.setEditable(true);
		tf5.setFont(fo);
		tf2.setEditable(true);
		tf6.setFont(fo);
		tf6.setEditable(false);

		l1.setFont(fo);
		l2.setFont(fo);
		l3.setFont(fo);
		l4.setFont(fo);
		l5.setFont(fo);
		l6.setFont(fo);

		rb1.setFont(fo);
		rb2.setFont(fo);

		grp.add(rb1);
		grp.add(rb2);

		northpanel.add(rb1);
		northpanel.add(rb2);

		southpanel.add(deletebut);
		southpanel.add(savebut);

		tb.getTableHeader().setFont(fo);
		tb.getTableHeader().setPreferredSize(new java.awt.Dimension(18, 18));
		tb.setRowHeight(18);
		tb.setFont(fo);

		northpanel.setBackground(java.awt.Color.white);
		westpanel.setBackground(java.awt.Color.white);

		northpanel.setPreferredSize(new java.awt.Dimension(100, 70));
		westpanel.setPreferredSize(new java.awt.Dimension(150, 70));

		savebut.setMnemonic(83);
		closebut.setMnemonic(67);

		northpanel.add(l1);
		northpanel.add(tf1);
		northpanel.add(l2);
		northpanel.add(tf2);
		northpanel.add(l3);
		northpanel.add(tf3);

		centernorthpanel.add(l4);
		centernorthpanel.add(tf4);
		centernorthpanel.add(l5);
		centernorthpanel.add(tf5);
		centernorthpanel.add(l6);
		centernorthpanel.add(tf6);

		centernorthpanel.setBorder(bor1);

		centerpanel.add(centernorthpanel, "North");
		centerpanel.add(sp, "Center");

		localContainer.add(northpanel, "North");
		localContainer.add(centerpanel, "Center");
		localContainer.add(southpanel, "South");

		deletebut.addActionListener(this);

		rb1.addActionListener(this);
		rb2.addActionListener(this);

		savebut.addActionListener(this);
		closebut.addActionListener(this);

		tf2.addFocusListener(this);
		tf2.addActionListener(this);

		tf5.addFocusListener(this);
		tf5.addActionListener(this);

		tf1.addKeyListener(this);
		tf2.addKeyListener(this);
		tf3.addKeyListener(this);
		tf4.addKeyListener(this);
		tf5.addKeyListener(this);

		rb1.addKeyListener(this);
		rb2.addKeyListener(this);
		tb.addKeyListener(this);

		localContainer.setBackground(java.awt.Color.white);

		f.setSize(400, 450);
		f.setLocation((ss.width - fs.width) / 4, (ss.height - fs.height) / 5);
		//f.setResizable(false);
		f.setVisible(true);
		tf2.requestFocus();
	}

	javax.swing.table.DefaultTableModel model;
	javax.swing.JTable tb;
	javax.swing.JScrollPane sp;

	public void setAttendance(Attendance attendance){
		this.attendance = attendance;
	}

	public void empDetails() {
		model.setNumRows(0);
		try {
			con =  DBConnectionUtil.getConnection();
			pstat = con.prepareStatement("select emp_name From emp_STATUS WHERE emp_code='" + tf2.getText() + "' ");
			rs = pstat.executeQuery();
			rs.getMetaData().getColumnCount();
			while (rs.next()) {
				String str = new String(rs.getString(1));
				tf3.setText(str);
				tf4.requestFocus();
			}
		} catch (Exception localException) {
			javax.swing.JOptionPane.showMessageDialog(f, localException.getMessage().toString());
		}
	}

	javax.swing.JFrame f;
	javax.swing.JLabel l1;
	javax.swing.JLabel l2;
	javax.swing.JLabel l3;
	javax.swing.JLabel l4;

	public void deleteRecord() {
		String str1 = tf2.getText();
		String str2 = " ";

		java.sql.Date localDate = SolDateFormatter.DDMMYYtoSQL(tf1.getText());

		if (rb1.isSelected()) {
			//Deleting BREAK HRS from HR_BREAK_TIME table.
			str2 = "delete from HR_BREAK_TIME  where emp_code = ? and date = ?";
			try {
				pstat = con.prepareStatement(str2);
				pstat.setString(1, str1);
				pstat.setDate(2, localDate);
				affected = pstat.executeUpdate();
				if (affected > 0) {
					javax.swing.JOptionPane.showMessageDialog(f, "Break Time is Clean now.");
					model.setNumRows(0);
				}
			} catch (Exception localException1) {
				System.out.println(localException1);
			}

			//Setting BREAK HRS as 0 hrs to HRTIMEMASTER table.
			str2 = "update HRTIMEMASTER set break_hrs = ? where emp_code = ? and date = ?";
			try {
				pstat = con.prepareStatement(str2);
				pstat.setInt(1, 0);
				pstat.setString(2, str1);
				pstat.setDate(3, localDate);
				affected = pstat.executeUpdate();
			} catch (Exception localException2) {
				System.out.println(localException2);
			}
		}
		if (rb2.isSelected()) {
			//Updating OT HRS as 0 hrs to HRTIMEMASTER TABLE.
			str2 = "update HRTIMEMASTER set ot2 = ? where emp_code = ? and date = ?";
			try {
				pstat = con.prepareStatement(str2);
				pstat.setInt(1, 0);
				pstat.setString(2, str1);
				pstat.setDate(3, localDate);
				affected = pstat.executeUpdate();
				if (affected > 0) {
					javax.swing.JOptionPane.showMessageDialog(f, "Extra OT Hrs Deleted Now.");
					model.setNumRows(0);
				}
			} catch (Exception localException2) {
				System.out.println(localException2);
			}
		}
	}

	javax.swing.JLabel l5;
	javax.swing.JLabel l6;
	JTextField tf1;
	JTextField tf2;
	JTextField tf3;

	public void showBreakTimeDetails() {
		model.setNumRows(0);
		java.sql.Date localDate = SolDateFormatter.DDMMYYtoSQL(tf1.getText());

		try {
			con =  DBConnectionUtil.getConnection();
			pstat = con.prepareStatement(
					"select EMP_CODE, OUT_TIME, IN_TIME, TOTAL_TIME From HR_BREAK_TIME WHERE emp_code='" + tf2.getText()
							+ "' AND Date=? ");
			pstat.setDate(1, localDate);
			rs = pstat.executeQuery();

			while (rs.next()) {
				String str = new String(rs.getString(1));
				float f1 = rs.getFloat(2);
				float f2 = rs.getFloat(3);
				float f3 = rs.getFloat(4);

				model.addRow(new Object[] { str, String.valueOf(df.format(f1)), String.valueOf(df.format(f2)),
						String.valueOf(df.format(f3)) });
			}
		} catch (Exception localException) {
			javax.swing.JOptionPane.showMessageDialog(f, localException.getMessage().toString());
		}
	}

	JTextField tf4;
	JTextField tf5;
	JTextField tf6;

	public void showOTDetails() {
		model.setNumRows(0);
		java.sql.Date localDate = SolDateFormatter.DDMMYYtoSQL(tf1.getText());

		try {
			con =  DBConnectionUtil.getConnection();
			pstat = con.prepareStatement(
					"select OT2 From HRTIMEMASTER WHERE emp_code='" + tf2.getText() + "' AND Date=? ");
			pstat.setDate(1, localDate);
			rs = pstat.executeQuery();

			while (rs.next()) {
				float f1 = rs.getFloat(1);

				model.addRow(new Object[] { tf2.getText(), "0", "0", String.valueOf(df.format(f1)) });
			}
		} catch (Exception localException) {
			javax.swing.JOptionPane.showMessageDialog(f, localException.getMessage().toString());
		}
	}

	javax.swing.JLabel westlabel;
	javax.swing.JButton deletebut;
	javax.swing.JButton savebut;
	javax.swing.JButton closebut;
	javax.swing.JRadioButton rb1;


	public void calculateTotalHRS() {
		String str = DateDifferencesUtil.getTimeDiff(tf4.getText(), tf5.getText());

		if (Float.parseFloat(str) <= 0.0F) {

			javax.swing.JOptionPane.showMessageDialog(f, "Check The Intime Please. It is a Wrong Entry.");
			tf5.requestFocus();
			tf5.setText("00.00");
			tf5.selectAll();
		} else {
			tf6.setText(str);
		}
		collectRecord();
	}

	public void collectRecord() {
		model.addRow(new Object[] { tf2.getText(), tf4.getText(), tf5.getText(), tf6.getText() });

		if ((tb.getRowCount() > 1) || (tb.getRowCount() > 2)) {
			model.removeRow(tb.getRowCount() - 2);
		}

		int i = 0;

		for (int j = 0; j < tb.getRowCount(); j++) {
			int k = SolDateFormatter.convertHrsToMinute(String.valueOf(model.getValueAt(j, 3)));
			i += k;
		}
		String breakTimeHrs = String.valueOf(SolDateFormatter.convertMinuteToHRS(i));
		model.addRow(new Object[] { "", "", "",  breakTimeHrs});
		attendance.refreshBreakTimeHrs(breakTimeHrs);

		tf4.setText("");
		tf5.setText("");
		tf6.setText("");
		tf4.requestFocus();
	}

	javax.swing.JRadioButton rb2;
	javax.swing.ButtonGroup grp;
	javax.swing.JPanel centerpanel;
	javax.swing.JPanel northpanel;
	javax.swing.JPanel westpanel;

	public void saveBreakTimeDetails() {
		java.sql.Date localDate = SolDateFormatter.DDMMYYtoSQL(tf1.getText());

		for(int i=0; i<tb.getRowCount()-1; i++) {
			String str1 = String.valueOf(tf4.getText());
			String str2 = String.valueOf(tf5.getText());
			String str3 = String.valueOf(tf6.getText());

			String outTime = (String) model.getValueAt(i, 1);
			String inTime = (String) model.getValueAt(i, 2);
			String totalTime = (String) model.getValueAt(i, 3);

			try {
				pstat = con.prepareStatement("insert into HR_BREAK_TIME values(?,'" + tf2.getText() + "', ?, ?, ?) ");
				pstat.setDate(1, localDate);
				pstat.setString(2, outTime);
				pstat.setString(3, inTime);
				pstat.setString(4, totalTime);
				affected = pstat.executeUpdate();
				if (affected > 0) {
					javax.swing.JOptionPane.showMessageDialog(f, "Break Time saved successfully.");
					model.setNumRows(0);
				}
			} catch (Exception localException) {
				javax.swing.JOptionPane.showMessageDialog(f, "This Time is Allready Added for This Employee.");
			}
		}
	}

	public void saveExtraOTDetails() {
		model.setNumRows(0);

		java.sql.Date localDate = SolDateFormatter.DDMMYYtoSQL(tf1.getText());

		String str1 = String.valueOf(tf4.getText());
		String str2 = String.valueOf(tf5.getText());
		String str3 = String.valueOf(tf6.getText());

		try {
			pstat = con.prepareStatement(
					"UPDATE HRTIMEMASTER set OT2=? where date=? and emp_code='" + tf2.getText() + "' ");
			pstat.setString(1, String.valueOf(Float.parseFloat(str3)));
			pstat.setDate(2, localDate);
			affected = pstat.executeUpdate();
			if (affected > 0) {
				javax.swing.JOptionPane.showMessageDialog(f, "Extra OT saved successfully.");
				model.setNumRows(0);
			}
		} catch (Exception localException) {
			javax.swing.JOptionPane.showMessageDialog(f, "This Time is Allready Added for This Employee.");
		}
	}

	javax.swing.JPanel southpanel;
	javax.swing.JPanel centernorthpanel;
	java.util.Date dat;
	java.text.SimpleDateFormat formatter;
	java.text.SimpleDateFormat formatter1;
	String dateString;

	public String getTotalBreakTime(String paramString1, String paramString2) {
		java.sql.Date localDate = SolDateFormatter.DDMMYYtoSQL(paramString2);

		int i = 0;

		try {
			con =  DBConnectionUtil.getConnection();
			pstat = con.prepareStatement(
					"select TOTAL_TIME From HR_BREAK_TIME WHERE emp_code='" + paramString1 + "' and date = ? ");
			pstat.setDate(1, localDate);
			rs = pstat.executeQuery();
			rs.getMetaData().getColumnCount();
			while (rs.next()) {
				float f1 = rs.getFloat(1);

				int k = SolDateFormatter.convertHrsToMinute(String.valueOf(f1));
				i += k;
			}
		} catch (Exception localException) {
			javax.swing.JOptionPane.showMessageDialog(f, localException.getMessage().toString());
		} finally{
			DBConnectionUtil.closeConnection(rs, pstat, con);
		}
		return SolDateFormatter.convertMinuteToHRS(i);
	}
	
	
	public Map<String, String> getTotalBreakTime(String dateString) {
		java.sql.Date localDate = SolDateFormatter.DDMMYYtoSQL(dateString);

		Map<String, String> breakHrsMap = new HashMap<String, String>();
		try {
			con =  DBConnectionUtil.getConnection();
			pstat = con.prepareStatement(
					"select EMP_CODE, TOTAL_TIME From HR_BREAK_TIME WHERE date = ? ");
			pstat.setDate(1, localDate);
			rs = pstat.executeQuery();
			rs.getMetaData().getColumnCount();
			while (rs.next()) {
				String empCode = rs.getString("EMP_CODE");
				float f1 = rs.getFloat("TOTAL_TIME");

				if(!breakHrsMap.containsKey(empCode)) {
					int k = SolDateFormatter.convertHrsToMinute(String.valueOf(f1));
					String hrs = SolDateFormatter.convertMinuteToHRS(k);
					breakHrsMap.put(empCode, hrs);
				}else{
					int existingValue = SolDateFormatter.convertHrsToMinute(breakHrsMap.get(empCode));
					int newValue = SolDateFormatter.convertHrsToMinute(String.valueOf(f1));
					int totalMinutes = existingValue + newValue;
					String hrs = SolDateFormatter.convertMinuteToHRS(totalMinutes);
					breakHrsMap.put(empCode, hrs);
				}
			}
		} catch (Exception localException) {
			javax.swing.JOptionPane.showMessageDialog(f, localException.getMessage().toString());
		} finally{
			DBConnectionUtil.closeConnection(rs, pstat, con);
		}
		return breakHrsMap;
	}

	String dateString1;
	java.awt.Toolkit tk;
	java.awt.Dimension ss;
	java.awt.Dimension fs;
	java.awt.Font fo;
	javax.swing.border.Border lineborder;
	javax.swing.border.Border bor1;

	public void actionPerformed(java.awt.event.ActionEvent paramActionEvent) {
		if (paramActionEvent.getSource() == savebut) {
			if(rb1.isSelected()){
				saveBreakTimeDetails();
			}
			if(rb2.isSelected()) {
				saveExtraOTDetails();
			}
		}
		if (paramActionEvent.getSource() == closebut) {
			f.setVisible(false);
		}
		if (paramActionEvent.getSource() == deletebut) {
			deleteRecord();
		}
		if (paramActionEvent.getSource() == tf2) {
			empDetails();
		}
		if (paramActionEvent.getSource() == rb1) {
			tf4.setText("");
			tf5.setText("");
			tf4.setEditable(true);
			tf5.setEditable(true);
			tf6.setEditable(false);
			tf6.setText("0");
			showBreakTimeDetails();
			//collectRecord();
		}
		if(paramActionEvent.getSource() == rb2){
			tf4.setText("0");
			tf5.setText("0");
			tf6.setText(" ");
			tf4.setEditable(false);
			tf5.setEditable(false);
			tf6.setEditable(true);
			showOTDetails();
			//collectRecord();
		}
	}

	public void focusGained(java.awt.event.FocusEvent paramFocusEvent) {
	}

	public void focusLost(java.awt.event.FocusEvent paramFocusEvent) {
		if (paramFocusEvent.getSource() == tf2) {
			empDetails();
		}
		if(paramFocusEvent.getSource() == tf5){
			calculateTotalHRS();
		}
	}

	public void keyPressed(java.awt.event.KeyEvent paramKeyEvent) {
	}

	public void keyReleased(java.awt.event.KeyEvent paramKeyEvent) {
		int i = paramKeyEvent.getKeyCode();

		if ((paramKeyEvent.getSource() == tf1) || (paramKeyEvent.getSource() == tf2)
				|| (paramKeyEvent.getSource() == tf3) || (paramKeyEvent.getSource() == tf4)
				|| (paramKeyEvent.getSource() == tf5) || (paramKeyEvent.getSource() == rb1)
				|| (paramKeyEvent.getSource() == rb2) || (paramKeyEvent.getSource() == tb)) {
			if (i == 27) {
				f.setVisible(false);
			}
		}
	}

	public void keyTyped(java.awt.event.KeyEvent paramKeyEvent) {
	}
}
