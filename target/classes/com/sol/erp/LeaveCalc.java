package com.sol.erp;

import java.awt.Color;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.table.DefaultTableModel;

import com.sol.erp.ui.custom.SolCalendar;
import com.sol.erp.ui.custom.SolCalendar2;
import com.sol.erp.ui.custom.SolCellModel;
import com.sol.erp.ui.custom.SolCommentBox;
import com.sol.erp.ui.custom.SolProgressMonitor;
import com.sol.erp.ui.custom.SolTableModel;
import com.sol.erp.util.DBConnectionUtil;
import com.sol.erp.util.DateCalculationUtil;
import com.sol.erp.util.SessionUtil;
import com.sol.erp.util.formater.SolDateFormatter;

public class LeaveCalc
		implements java.awt.event.ActionListener, java.awt.event.MouseListener, java.awt.event.KeyListener {
	
	EmpLeaveTB leavetb;
	public static SolCommentBox commentBox = new SolCommentBox();
	public static SolCommentBox directMessageBox = new SolCommentBox();

	

	SolCalendar skl;

	SolCalendar2 cal1;

	SolCalendar2 cal2;

	SolCalendar2 cal3;

	SolProgressMonitor prmonitor;
	LeaveStatus ls;
	ManpowerAvail mp;
	HolidayList hl;

	java.sql.Connection con;
	java.sql.Statement stat;
	ResultSet rs;
	int affected;
	java.text.DecimalFormat df1;
	java.text.DecimalFormat df2;
	javax.swing.JFrame f;
	static String[] head = { "Date", "Month", "Category", "Reason" };
	static Object[][] data = new Object[0][];

	static String[] typesHead = { "Type", "Total/Yr", "Used" };
	static Object[][] typesData = { { "EL", "20" }, { "CL", "12" }, { "LWP", "0" } };

	static String[] requestHead = { "Code", "Req Date", "Size", "TL", "HR", "Reason" };
	static Object[][] requestData = { new Object[0], new Object[0], new Object[0] };

	static String[] approveHead = { "Date", "TL", "HR", "Reason", "Remarks", "Remarks" };
	static Object[][] approveData = { new Object[0], new Object[0], new Object[0] };

	DefaultTableModel model;

	JTable tb;

	JScrollPane sp;

	DefaultTableModel typesModel;

	JTable typesTB;

	JScrollPane typesSP;

	javax.swing.JTabbedPane tabbedpane;

	JPanel detailsPanel;

	JPanel calPanel;

	JPanel leaveStatusPanel;

	JPanel manpowerPanel;

	JPanel holidayPanel;

	javax.swing.JTabbedPane tabbedpane2;

	JPanel applicationPanel;

	JPanel reqListPanel;

	JPanel pastreqListPanel;

	JPanel approvesPanel;

	JPanel applicationnorthpanel;

	JPanel applicationsouthpanel;

	JPanel typesPanel;

	JPanel calendarPanel;

	JPanel calendarmainPanel;

	JPanel calendarsouthPanel;

	JPanel southPanel;

	JPanel reqNorthListPanel;

	javax.swing.JSplitPane spliter1;

	javax.swing.JSplitPane spliter2;

	JLabel codelb;

	JLabel namelb;

	JLabel postlb;

	JLabel coidlb;

	JLabel brcodelb;

	JLabel optionlb;

	JTextField codetf;

	JTextField nametf;

	JTextField posttf;

	JTextField coidtf;

	JTextField brcodetf;

	JComboBox optioncb;

	JTextField yeartf;

	JTextPane tp;

	JScrollPane applicationSP;

	DefaultListModel listModel;

	JList tlList;

	JScrollPane tlListSP;

	JPanel statuspanel;

	JLabel scodelb;

	JLabel susertypelb;

	JLabel snamelb;

	JLabel spostlb;

	JLabel scoidlb;

	JLabel sbrcodelb;

	JLabel soptionlb;

	JTextField scodetf;

	JTextField susertypetf;

	JTextField sdepttf;

	JTextField sposttf;

	JTextField scoidtf;

	JTextField sbrcodetf;

	DefaultTableModel requestListModel;

	JTable requestTB;

	JScrollPane requestSP;

	JTextPane tp1;

	JScrollPane reasonSP;

	JTextPane remarkstp;

	JScrollPane remarksSP;

	javax.swing.JTabbedPane remarksTab;

	JPanel reasonPanel;

	JPanel remarksPanel;

	JTextPane tp2;

	JScrollPane approveRemarksSP;

	DefaultTableModel approveListModel;

	JTable approveTB;

	JScrollPane approveSP;

	JButton directOKBut;

	JButton OKBut;

	JButton ApproveBut;

	JButton ApproveBut2;

	JButton cancelleavebut;

	public static JButton directBut = new JButton("SET LEAVES");

	JButton deleteleavebut;

	JButton refreshcalbut;

	JButton pastrequestbut;

	JButton currentrequestbut;

	JButton monthlybut;

	JButton yearlybut;

	JPanel southButtonPanel;

	JPanel northpanel;

	JPanel centerpanel;

	JPanel southpanel;

	JPanel centersouthpanel;


	SolCellModel skc;

	DateCalculationUtil dm;

	java.awt.Toolkit tk;
	java.awt.Dimension ss;
	java.awt.Dimension fs;
	java.util.Date dat;
	java.text.SimpleDateFormat formatter;
	String dateString;
	java.awt.Font fo;
	LeaveCalc.Renderer rnd;
	ArrayList dateArrayList;
	String datestr;

	class Renderer extends javax.swing.table.DefaultTableCellRenderer {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		Renderer() {
		}

		public java.awt.Component getTableCellRendererComponent(JTable paramJTable, Object paramObject,
				boolean paramBoolean1, boolean paramBoolean2, int paramInt1, int paramInt2) {
			setHorizontalAlignment(0);

			if (paramInt1 % 2 == 0) {
				setBackground(new Color(250, 250, 250));
				setForeground(Color.darkGray);
			} else {
				setBackground(new Color(245, 245, 245));
				setForeground(Color.darkGray);
			}

			if (paramInt2 <= 2) {
				setHorizontalAlignment(0);
			}
			if (paramInt2 > 2) {
				setHorizontalAlignment(2);
			}

			if ((paramInt2 == 2) && (paramInt1 >= 0)) {
				String str = String.valueOf(model.getValueAt(paramInt1, 2));

				if (str.equalsIgnoreCase("LWP")) {
					setForeground(Color.red);
				}
				if (str.equalsIgnoreCase("EL")) {
					setForeground(Color.blue);
				}
				if (str.equalsIgnoreCase("CL")) {
					setForeground(Color.green);
				}
			}

			super.getTableCellRendererComponent(paramJTable, paramObject, paramBoolean1, paramBoolean2, paramInt1,
					paramInt2);

			return this;
		}
	}

	public void px() {
		directOKBut = directMessageBox.OK();

		java.awt.Container localContainer = f.getContentPane();
		localContainer.setLayout(new java.awt.BorderLayout());
		centerpanel.setLayout(new java.awt.BorderLayout());
		detailsPanel.setLayout(new java.awt.BorderLayout());
		calPanel.setLayout(new java.awt.BorderLayout());
		leaveStatusPanel.setLayout(new java.awt.BorderLayout());
		manpowerPanel.setLayout(new java.awt.BorderLayout());
		calendarPanel.setLayout(new java.awt.GridLayout(1, 3, 10, 10));
		calendarmainPanel.setLayout(new java.awt.BorderLayout());
		calendarsouthPanel.setLayout(new java.awt.FlowLayout(2));
		typesPanel.setLayout(new java.awt.BorderLayout());
		applicationPanel.setLayout(new java.awt.BorderLayout());
		reqListPanel.setLayout(new java.awt.BorderLayout());
		reqNorthListPanel.setLayout(new java.awt.FlowLayout(0));
		approvesPanel.setLayout(new java.awt.BorderLayout());
		northpanel.setLayout(new java.awt.FlowLayout(2));
		southPanel.setLayout(new java.awt.BorderLayout());
		southButtonPanel.setLayout(new java.awt.FlowLayout(0));
		centersouthpanel.setLayout(new java.awt.FlowLayout(0));
		applicationsouthpanel.setLayout(new java.awt.FlowLayout(2));
		applicationnorthpanel.setLayout(new java.awt.BorderLayout());
		statuspanel.setLayout(new java.awt.FlowLayout(2));
		reasonPanel.setLayout(new java.awt.BorderLayout());
		remarksPanel.setLayout(new java.awt.BorderLayout());
		southpanel.setLayout(new java.awt.FlowLayout(0));
		holidayPanel.setLayout(new java.awt.BorderLayout());

		northpanel.add(codelb);
		northpanel.add(codetf);
		codetf.setEditable(true);
		northpanel.add(namelb);
		northpanel.add(nametf);
		nametf.setEditable(false);
		northpanel.add(postlb);
		northpanel.add(posttf);
		posttf.setEditable(false);
		northpanel.add(coidlb);
		northpanel.add(coidtf);
		coidtf.setEditable(false);
		northpanel.add(brcodelb);
		northpanel.add(brcodetf);
		brcodetf.setEditable(false);

		codetf.setFont(new java.awt.Font("Verdana", 0, 10));
		nametf.setFont(new java.awt.Font("Tahoma", 1, 10));
		nametf.setHorizontalAlignment(4);
		posttf.setFont(new java.awt.Font("Tahoma", 1, 10));
		posttf.setHorizontalAlignment(4);
		coidtf.setFont(new java.awt.Font("Tahoma", 1, 10));
		coidtf.setHorizontalAlignment(4);
		brcodetf.setFont(new java.awt.Font("Tahoma", 1, 10));
		brcodetf.setHorizontalAlignment(4);

		statuspanel.add(scodelb);
		statuspanel.add(scodetf);
		scodetf.setEditable(false);
		statuspanel.add(susertypelb);
		statuspanel.add(susertypetf);
		susertypetf.setEditable(false);
		statuspanel.add(snamelb);
		statuspanel.add(sdepttf);
		sdepttf.setEditable(false);
		statuspanel.add(spostlb);
		statuspanel.add(sposttf);
		sposttf.setEditable(false);
		statuspanel.add(scoidlb);
		statuspanel.add(scoidtf);
		scoidtf.setEditable(false);
		statuspanel.add(sbrcodelb);
		statuspanel.add(sbrcodetf);
		sbrcodetf.setEditable(false);

		scodetf.setFont(new java.awt.Font("Tahoma", 1, 10));
		scodetf.setHorizontalAlignment(4);
		susertypetf.setFont(new java.awt.Font("Tahoma", 1, 10));
		susertypetf.setHorizontalAlignment(4);
		sdepttf.setFont(new java.awt.Font("Tahoma", 1, 10));
		sdepttf.setHorizontalAlignment(4);
		sposttf.setFont(new java.awt.Font("Tahoma", 1, 10));
		sposttf.setHorizontalAlignment(4);
		scoidtf.setFont(new java.awt.Font("Tahoma", 1, 10));
		scoidtf.setHorizontalAlignment(4);
		sbrcodetf.setFont(new java.awt.Font("Tahoma", 1, 10));
		sbrcodetf.setHorizontalAlignment(4);

		optioncb.addItem("MONTHLY");
		optioncb.addItem("YEARLY");
		optioncb.setBackground(Color.white);

		southButtonPanel.add(ApproveBut);
		southButtonPanel.add(ApproveBut2);

		applicationsouthpanel.add(OKBut);
		applicationSP.setBorder(javax.swing.BorderFactory.createTitledBorder("Reason"));

		applicationPanel.add(applicationSP, "Center");
		applicationPanel.add(tlListSP, "East");
		tlListSP.setPreferredSize(new java.awt.Dimension(100, 100));
		applicationPanel.add(applicationsouthpanel, "South");
		tlListSP.setBorder(javax.swing.BorderFactory.createTitledBorder("TL List"));

		reqNorthListPanel.add(currentrequestbut);

		calendarsouthPanel.add(directBut);
		calendarsouthPanel.add(refreshcalbut);
		calendarsouthPanel.add(deleteleavebut);

		reqListPanel.add(reqNorthListPanel, "North");
		reqListPanel.add(requestSP, "West");
		reqListPanel.add(remarksTab, "Center");
		requestSP.setPreferredSize(new java.awt.Dimension(400, 200));
		requestSP.setBorder(javax.swing.BorderFactory.createTitledBorder("Request List"));

		remarkstp.setFont(new java.awt.Font("Arial", 1, 11));
		tp1.setFont(new java.awt.Font("Arial", 1, 11));
		tp2.setFont(new java.awt.Font("Arial", 1, 11));
		tp2.setEditable(false);
		approvesPanel.add(approveSP, "West");
		approveSP.setPreferredSize(new java.awt.Dimension(250, 200));
		approvesPanel.add(approveRemarksSP, "Center");
		approvesPanel.add(applicationPanel, "South");
		applicationPanel.setPreferredSize(new java.awt.Dimension(100, 250));
		approveRemarksSP.setBorder(javax.swing.BorderFactory.createTitledBorder("Reason/Remarks"));

		reasonPanel.add(reasonSP, "Center");
		remarksPanel.add(remarksSP, "Center");

		remarksTab.add("Reason", reasonPanel);
		remarksTab.add("Remarks", remarksPanel);

		tabbedpane.add("Approvals", approvesPanel);

		tabbedpane.add("Leave Processor", calPanel);
		tabbedpane.add("Past Leaves", detailsPanel);

		tabbedpane2.add("Request List", reqListPanel);

		cal3.removeOKPanel();
		cal1.removeOKPanel();
		cal2.removeOKPanel();

		calendarmainPanel.add(calendarPanel, "Center");
		calendarmainPanel.add(calendarsouthPanel, "South");

		calendarPanel.add(cal1.DesignFrame());
		calendarPanel.add(cal2.DesignFrame());
		calendarPanel.add(cal3.DesignFrame());

		cal1.YearList().setEnabled(false);
		cal3.YearList().setEnabled(false);
		cal1.MonthList().setEnabled(false);
		cal3.MonthList().setEnabled(false);

		cal1.OKButton().setEnabled(false);
		cal2.OKButton().setEnabled(false);
		cal3.OKButton().setEnabled(false);

		spliter1.setDividerLocation(340);
		spliter2.setDividerLocation(240);
		typesPanel.add(typesSP, "Center");
		calPanel.add(spliter2, "Center");

		leaveStatusPanel.add(ls.DesignFrame(), "Center");
		manpowerPanel.add(mp.DesignFrame(), "Center");
		holidayPanel.add(hl.DesignFrame(), "Center");

		SolTableModel.decorateTable(typesTB);
		typesTB.setSelectionBackground(new Color(250, 250, 170));
		typesTB.setSelectionForeground(Color.blue);
		typesTB.setIntercellSpacing(new java.awt.Dimension(1, 1));

		SolTableModel.decorateTable(requestTB);
		requestTB.setSelectionBackground(new Color(250, 250, 170));
		requestTB.setSelectionForeground(Color.blue);
		requestTB.setIntercellSpacing(new java.awt.Dimension(1, 1));

		SolTableModel.decorateTable(approveTB);
		approveTB.setSelectionBackground(new Color(250, 250, 170));
		approveTB.setSelectionForeground(Color.blue);
		approveTB.setIntercellSpacing(new java.awt.Dimension(1, 1));

		for (int i = 0; i < tb.getColumnCount(); i++) {
			tb.getColumnModel().getColumn(i).setCellRenderer(rnd);
		}
		tb.getTableHeader().setPreferredSize(new java.awt.Dimension(30, 30));
		tb.getTableHeader().setFont(new java.awt.Font("Verdana", 1, 10));
		tb.getColumnModel().getColumn(0).setPreferredWidth(80);
		tb.getColumnModel().getColumn(1).setPreferredWidth(100);
		tb.getColumnModel().getColumn(2).setPreferredWidth(90);
		tb.getColumnModel().getColumn(3).setPreferredWidth(820);
		tb.setFont(fo);
		tb.setIntercellSpacing(new java.awt.Dimension(1, 1));
		tb.setRowHeight(22);

		requestTB.getColumnModel().getColumn(1).setPreferredWidth(230);

		centersouthpanel.add(optionlb);
		optionlb.setFont(fo);
		centersouthpanel.add(yeartf);
		yeartf.setFont(fo);
		yeartf.setHorizontalAlignment(4);
		centersouthpanel.add(optioncb);
		optioncb.setFont(fo);

		yeartf.setPreferredSize(new java.awt.Dimension(100, 20));
		optioncb.setPreferredSize(new java.awt.Dimension(80, 20));

		southPanel.add(tabbedpane2, "Center");

		centerpanel.add(sp, "Center");
		centerpanel.add(centersouthpanel, "North");

		detailsPanel.add(centerpanel, "Center");
		localContainer.add(tabbedpane, "Center");
		localContainer.add(northpanel, "North");
		localContainer.add(statuspanel, "South");

		tabbedpane.setBorder(javax.swing.BorderFactory.createBevelBorder(1));

		cal2.MonthList().addActionListener(this);

		directOKBut.addActionListener(this);
		directBut.addActionListener(this);
		OKBut.addActionListener(this);
		ApproveBut.addActionListener(this);
		ApproveBut2.addActionListener(this);
		yeartf.addActionListener(this);
		yeartf.addMouseListener(this);
		cancelleavebut.addActionListener(this);
		refreshcalbut.addActionListener(this);
		pastrequestbut.addActionListener(this);
		currentrequestbut.addActionListener(this);
		deleteleavebut.addActionListener(this);

		tabbedpane2.addMouseListener(this);
		requestTB.addMouseListener(this);
		approveTB.addMouseListener(this);

		optioncb.addActionListener(this);

		f.setTitle("Leave Calculator");

		f.setSize(880, 600);
		f.setLocationRelativeTo(null);
		f.setVisible(true);

		localContainer.addKeyListener(this);
		codetf.addActionListener(this);
		codetf.addKeyListener(this);

		empDetails(codetf.getText());
		showTL();
		reqList();
		leaveRemain();
		approvalStatus();

		if (susertypetf.getText().equalsIgnoreCase("User")) {
			tabbedpane.remove(calPanel);
			applicationPanel.add(typesPanel, "West");
			typesPanel.setPreferredSize(new java.awt.Dimension(250, 100));
			codetf.setEditable(false);
		}

		requestTB.removeColumn(requestTB.getColumnModel().getColumn(5));
		approveTB.removeColumn(approveTB.getColumnModel().getColumn(3));
		runAllDates();

		java.awt.Font localFont = new java.awt.Font("Verdana", 1, 10);

		directOKBut.setBackground(new Color(200, 120, 200));
		directOKBut.setForeground(Color.white);
		directOKBut.setFont(localFont);

		OKBut.setBackground(new Color(200, 120, 200));
		OKBut.setForeground(Color.white);
		OKBut.setFont(localFont);

		ApproveBut.setBackground(new Color(200, 120, 200));
		ApproveBut.setForeground(Color.white);
		ApproveBut.setFont(localFont);

		ApproveBut2.setBackground(new Color(200, 120, 200));
		ApproveBut2.setForeground(Color.white);
		ApproveBut2.setFont(localFont);

		cancelleavebut.setBackground(new Color(200, 120, 200));
		cancelleavebut.setForeground(Color.white);
		cancelleavebut.setFont(localFont);

		directBut.setBackground(new Color(200, 120, 200));
		directBut.setForeground(Color.white);
		directBut.setFont(localFont);

		deleteleavebut.setBackground(new Color(200, 120, 200));
		deleteleavebut.setForeground(Color.white);
		deleteleavebut.setFont(localFont);

		refreshcalbut.setBackground(new Color(200, 120, 200));
		refreshcalbut.setForeground(Color.white);
		refreshcalbut.setFont(localFont);

		pastrequestbut.setBackground(new Color(200, 120, 200));
		pastrequestbut.setForeground(Color.white);
		pastrequestbut.setFont(localFont);

		currentrequestbut.setBackground(new Color(200, 120, 200));
		currentrequestbut.setForeground(Color.white);
		currentrequestbut.setFont(localFont);
	}

	public void setLoginCode(String paramString) {
		codetf.setText(paramString);
		scodetf.setText(paramString);
	}

	public void selectedDates() {
		dateArrayList.clear();
		String str;
		for (int i = 0; i < cal1.getDates(cal1.getYear()).size(); i++) {
			str = String.valueOf(cal1.getDates(cal1.getYear()).get(i));
			if (str.length() >= 1) {
				dateArrayList.add(str);
			}
		}
		for (int i = 0; i < cal2.getDates(cal2.getYear()).size(); i++) {
			str = String.valueOf(cal2.getDates(cal2.getYear()).get(i));
			if (str.length() >= 1) {
				dateArrayList.add(str);
			}
		}
		for (int i = 0; i < cal3.getDates(cal3.getYear()).size(); i++) {
			str = String.valueOf(cal3.getDates(cal3.getYear()).get(i));
			if (str.length() >= 1) {
				dateArrayList.add(str);
			}
		}
	}

	public void directLeaveRequest() {
		model.setNumRows(0);
		String str1 = scoidtf.getText();
		String str2 = sbrcodetf.getText();
		String str3 = scodetf.getText();
		String str4 = directMessageBox.getComment();

		prmonitor.showMonitor();
		prmonitor.setMIN(0);
		prmonitor.setMAX(dateArrayList.size());
		prmonitor.setProgressMessage("Sending Request...");
		prmonitor.setStatusMessage("Sending To TL/Project Manager/HR Dept.");
		prmonitor.getProgressBar().setForeground(new Color(0, 0, 150));
		prmonitor.runProgressMonitor();

		System.out.println("Reason " + str4);

		try {
			con = DBConnectionUtil.getConnection();
			stat = con.createStatement();
			affected = stat.executeUpdate(
					"insert into HREMP_LEAVE_REQ values('" + str1 + "','" + str2 + "','" + codetf.getText() + "', '"
							+ str4 + "','" + str3 + "','true','false','" + dateString + "','0','0') ");

			if (affected > 0) {
				requestListModel.addRow(new Object[] { codetf.getText(), dateString, String.valueOf(str4.length()),
						new Boolean(true), new Boolean(false), dateString });
			}
		} catch (Exception localException) {
			javax.swing.JOptionPane.showMessageDialog(f, localException.getMessage().toString());
		}
	}

	public void saveLeaves() {
		model.setNumRows(0);
		String str1 = scoidtf.getText();
		String str2 = sbrcodetf.getText();
		String str3 = (String) tlList.getSelectedValue();

		prmonitor.showMonitor();
		prmonitor.setMIN(0);
		prmonitor.setMAX(dateArrayList.size());
		prmonitor.setProgressMessage("Sending Request...");
		prmonitor.setStatusMessage("Sending To TL/Project Manager/HR Dept.");
		prmonitor.getProgressBar().setForeground(new Color(0, 0, 150));
		prmonitor.runProgressMonitor();

		try {
			con = DBConnectionUtil.getConnection();
			stat = con.createStatement();
			affected = stat.executeUpdate(
					"insert into HREMP_LEAVE_REQ values('" + str1 + "','" + str2 + "','" + scodetf.getText() + "', '"
							+ tp.getText() + "','" + str3 + "','false','false','" + dateString + "','0','0') ");

			if (affected > 0) {
				model.addRow(new Object[] { datestr, "0", tp.getText(), "" });
				approveListModel.addRow(new Object[] { dateString, new Boolean(false), new Boolean(false) });
				tp.setText("");
			}
		} catch (Exception localException) {
			javax.swing.JOptionPane.showMessageDialog(f, localException.getMessage().toString());
		}
	}

	public void reqList() {
		requestListModel.setNumRows(0);
		String str1 = scoidtf.getText();
		String str2 = sbrcodetf.getText();
		scodetf.getText();
		String str4 = sdepttf.getText();

		String str5 = "select emp_code, req_date, REASON, approve_tl, approve_hr from HREMP_LEAVE_REQ where company_id='"
				+ str1 + "' and branch_code='" + str2 + "' ";

		if (str4.equalsIgnoreCase("HR")) {
			str5 = "select emp_code, req_date, REASON, approve_tl, approve_hr from HREMP_LEAVE_REQ where company_id='"
					+ str1 + "' and branch_code='" + str2 + "' and approve_tl='true' and approve_hr ='false' ";
		}

		System.out.println("***********************************************");
		System.out.println("Get Id :" + codetf.getText());
		System.out.println("***********************************************");

		try {
			stat = con.createStatement();
			rs = stat.executeQuery(str5);

			while (rs.next()) {
				String str6 = new String(rs.getString(1));
				String str7 = new String(rs.getString(2));
				String str8 = new String(rs.getString(3));
				String str9 = new String(rs.getString(4));
				String str10 = new String(rs.getString(5));

				requestListModel.addRow(new Object[] { str6, str7, String.valueOf(str8.length()), new Boolean(str9),
						new Boolean(str10), str8 });
			}
		} catch (Exception localException) {
			javax.swing.JOptionPane.showMessageDialog(f, localException.getMessage().toString());
		}
	}

	public void pastReqList() {
		requestListModel.setNumRows(0);
		String str1 = scoidtf.getText();
		String str2 = sbrcodetf.getText();
		scodetf.getText();
		sdepttf.getText();

		String str5 = "select emp_code, req_date, REASON, approve_tl, approve_hr from HREMP_LEAVE_REQ where company_id='"
				+ str1 + "' and branch_code='" + str2 + "' and approve_tl='true' and approve_hr ='true' ";

		try {
			stat = con.createStatement();
			rs = stat.executeQuery(str5);

			while (rs.next()) {
				String str6 = new String(rs.getString(1));
				String str7 = new String(rs.getString(2));
				String str8 = new String(rs.getString(3));
				String str9 = new String(rs.getString(4));
				String str10 = new String(rs.getString(5));

				requestListModel.addRow(new Object[] { str6, str7, String.valueOf(str8.length()), new Boolean(str9),
						new Boolean(str10), str8 });
			}
		} catch (Exception localException) {
			javax.swing.JOptionPane.showMessageDialog(f, localException.getMessage().toString());
		}
	}

	public void leaveReason() {
		int i = requestTB.getSelectedRow();
		String str = String.valueOf(requestListModel.getValueAt(i, 5));
		tp1.setText("");
		tp1.setText(str);
	}

	public void ApprovedByTech() {
		int i = requestTB.getSelectedRow();
		requestTB.getSelectedColumn();

		coidtf.getText();
		brcodetf.getText();
		String str3 = String.valueOf(requestListModel.getValueAt(i, 0));
		String str4 = String.valueOf(requestListModel.getValueAt(i, 1));
		String str5 = String.valueOf(requestListModel.getValueAt(i, 3));
		String str6 = "Processing...";

		if (str5.equalsIgnoreCase("true")) {
			str5 = "true";
			str6 = "Sending Response...";
		} else {
			str5 = "false";
			str6 = "Canceling Leaves...";
		}

		try {
			stat = con.createStatement();
			affected = stat.executeUpdate("update HREMP_LEAVE_REQ set Approve_TL = '" + str5 + "', TL_Remarks = '"
					+ remarkstp.getText() + "' WHERE EMP_CODE='" + str3 + "' AND REQ_DATE='" + str4 + "'  ");

			if (affected > 0) {
				prmonitor.showMonitor();
				prmonitor.setMIN(0);
				prmonitor.setMAX(dateArrayList.size());
				prmonitor.setProgressMessage(str6);
				prmonitor.setStatusMessage("Sending To : " + str3);
				prmonitor.getProgressBar().setForeground(new Color(0, 0, 150));
				prmonitor.runProgressMonitor();
				approvalStatus();
				leaveRemain();
			}
		} catch (Exception localException) {
			javax.swing.JOptionPane.showMessageDialog(f, localException.getMessage().toString());
		}
	}

	public LeaveCalc() {
		leavetb = new EmpLeaveTB();

		
		skl = new SolCalendar();
		cal1 = new SolCalendar2();
		cal2 = new SolCalendar2();
		cal3 = new SolCalendar2();
		prmonitor = new SolProgressMonitor();
		ls = new LeaveStatus();
		mp = new ManpowerAvail();
		hl = new HolidayList();

		affected = 0;

		df1 = new java.text.DecimalFormat("00");
		df2 = new java.text.DecimalFormat("0,0");

		f = new javax.swing.JFrame();

		model = new DefaultTableModel(data, head);
		tb = new JTable(model) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int paramAnonymousInt1, int paramAnonymousInt2) {
				int i = convertColumnIndexToModel(paramAnonymousInt2);
				return i < 0;
			}

		};
		sp = new JScrollPane(tb);

		typesModel = new DefaultTableModel(typesData, typesHead);
		typesTB = new JTable(typesModel) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int paramAnonymousInt1, int paramAnonymousInt2) {
				int i = convertColumnIndexToModel(paramAnonymousInt2);
				return i < 0;
			}
		};
		typesSP = new JScrollPane(typesTB);

		tabbedpane = new javax.swing.JTabbedPane();
		detailsPanel = new JPanel();
		calPanel = new JPanel();
		leaveStatusPanel = new JPanel();
		manpowerPanel = new JPanel();
		holidayPanel = new JPanel();

		tabbedpane2 = new javax.swing.JTabbedPane();
		applicationPanel = new JPanel();
		reqListPanel = new JPanel();
		pastreqListPanel = new JPanel();
		approvesPanel = new JPanel();

		applicationnorthpanel = new JPanel();
		applicationsouthpanel = new JPanel();

		typesPanel = new JPanel();
		calendarPanel = new JPanel();
		calendarmainPanel = new JPanel();
		calendarsouthPanel = new JPanel();
		southPanel = new JPanel();

		reqNorthListPanel = new JPanel();

		spliter1 = new javax.swing.JSplitPane(1, typesPanel, calendarmainPanel);
		spliter2 = new javax.swing.JSplitPane(0, spliter1, southPanel);

		codelb = new JLabel("EMP Code");
		namelb = new JLabel("Name");
		postlb = new JLabel("Post");
		coidlb = new JLabel("Company ID");
		brcodelb = new JLabel("Branch Code");
		optionlb = new JLabel("Select Date");

		codetf = new JTextField(4);
		nametf = new JTextField(20);
		posttf = new JTextField(14);
		coidtf = new JTextField(4);
		brcodetf = new JTextField(4);
		optioncb = new JComboBox();
		yeartf = new JTextField(6);

		tp = new JTextPane();
		applicationSP = new JScrollPane(tp);
		listModel = new DefaultListModel();
		tlList = new JList(listModel);
		tlListSP = new JScrollPane(tlList);

		statuspanel = new JPanel();
		scodelb = new JLabel("Logged By");
		susertypelb = new JLabel("User Type");
		snamelb = new JLabel("Dept");
		spostlb = new JLabel("Post");
		scoidlb = new JLabel("Company ID");
		sbrcodelb = new JLabel("Branch Code");
		soptionlb = new JLabel("Status Option");

		scodetf = new JTextField(4);
		susertypetf = new JTextField(6);
		sdepttf = new JTextField(12);
		sposttf = new JTextField(12);
		scoidtf = new JTextField(4);
		sbrcodetf = new JTextField(4);

		requestListModel = new DefaultTableModel(requestData, requestHead);
		requestTB = new JTable(requestListModel) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@SuppressWarnings({})
			public Class<? extends Object> getColumnClass(int paramAnonymousInt) {
				return getValueAt(0, paramAnonymousInt).getClass();
			}

			public boolean isCellEditable(int paramAnonymousInt1, int paramAnonymousInt2) {
				int i = convertColumnIndexToModel(paramAnonymousInt2);

				if (i <= 2) {
					return i > 2;
				}
				if (sdepttf.getText().equalsIgnoreCase("HR")) {
					return i != 3;
				}

				return i != 4;
			}

		};
		requestSP = new JScrollPane(requestTB);

		tp1 = new JTextPane();
		reasonSP = new JScrollPane(tp1);

		remarkstp = new JTextPane();
		remarksSP = new JScrollPane(remarkstp);

		remarksTab = new javax.swing.JTabbedPane();
		reasonPanel = new JPanel();
		remarksPanel = new JPanel();

		tp2 = new JTextPane();
		approveRemarksSP = new JScrollPane(tp2);
		approveListModel = new DefaultTableModel(approveData, approveHead);
		approveTB = new JTable(approveListModel) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public Class<? extends Object> getColumnClass(int paramAnonymousInt) {
				return getValueAt(0, paramAnonymousInt).getClass();
			}

			public boolean isCellEditable(int paramAnonymousInt1, int paramAnonymousInt2) {
				int i = convertColumnIndexToModel(paramAnonymousInt2);
				return i < 0;
			}
		};
		approveSP = new JScrollPane(approveTB);

		directOKBut = new JButton("DIRECT_OK");
		OKBut = new JButton("SEND APPLICATION");
		ApproveBut = new JButton("APPROVED TECH.");
		ApproveBut2 = new JButton("APPROVED HR.");
		cancelleavebut = new JButton("CANCEL REQUEST");

		deleteleavebut = new JButton("REMOVE LEAVES");
		refreshcalbut = new JButton("REFRESH");
		pastrequestbut = new JButton("PAST REQUEST");
		currentrequestbut = new JButton("CURRENT REQUEST");

		monthlybut = new JButton("Monthly");
		yearlybut = new JButton("Monthly");

		southButtonPanel = new JPanel();

		northpanel = new JPanel();
		centerpanel = new JPanel();
		southpanel = new JPanel();

		centersouthpanel = new JPanel();

		skc = new SolCellModel();
		dm = new DateCalculationUtil();

		tk = java.awt.Toolkit.getDefaultToolkit();
		ss = tk.getScreenSize();
		fs = f.getSize();

		dat = new java.util.Date();
		formatter = new java.text.SimpleDateFormat("dd/MM/yyyy");

		try {
			dateString = formatter.format(dat);
			yeartf.setText(dateString);
		} catch (IllegalArgumentException localIllegalArgumentException) {
			System.out.println(localIllegalArgumentException.getMessage());
		}

		fo = new java.awt.Font("Verdana", 0, 10);

		rnd = new LeaveCalc.Renderer();

		dateArrayList = new ArrayList();

		datestr = null;
	}

	public void ApprovedByHR() {
		selectedDates();
		int i = requestTB.getSelectedRow();
		requestTB.getSelectedColumn();
		String str1 = String.valueOf(requestListModel.getValueAt(i, 4));

		if (str1.equalsIgnoreCase("true")) {

			if ((typesTB.getSelectedRow() < 0) || (dateArrayList.size() <= 0)) {
				javax.swing.JOptionPane.showMessageDialog(f, "Date or Leave Type is Not Selected.");
				reqList();

			} else {
				String str2 = null;
				int k = typesTB.getSelectedRow();
				str2 = String.valueOf(typesModel.getValueAt(k, 0));

				String str3 = coidtf.getText();
				String str4 = brcodetf.getText();
				String str5 = String.valueOf(requestListModel.getValueAt(i, 0));
				String str6 = String.valueOf(requestListModel.getValueAt(i, 1));

				try {
					stat = con.createStatement();
					affected = stat.executeUpdate("update HREMP_LEAVE_REQ set Approve_HR = 'true', HR_Remarks = '"
							+ remarkstp.getText() + "' WHERE EMP_CODE='" + str5 + "' AND REQ_DATE='" + str6 + "' ");

					if (affected > 0) {
						for (int m = 0; m < dateArrayList.size(); m++) {
							datestr = String.valueOf(dateArrayList.get(m));
							java.sql.Date localDate = SolDateFormatter.DDMMYYtoSQL(datestr);

							try {
								PreparedStatement localPreparedStatement = con
										.prepareStatement("insert into HREMP_LEAVES values('" + str3 + "','" + str4
												+ "','" + codetf.getText() + "', ? ,'" + str2 + "','" + tp1.getText()
												+ "','" + scodetf.getText() + "','true','true','" + dateString + "') ");
								localPreparedStatement.setDate(1, localDate);
								affected = localPreparedStatement.executeUpdate();
								if (affected > 0) {
									model.addRow(new Object[] { datestr, str2, tp1.getText(), "" });
								}
							} catch (Exception localException2) {
							}
						}
						leaveRemain();
						approvalStatus();
					}
				} catch (Exception localException1) {
					prmonitor.showMonitor();
					prmonitor.setMIN(0);
					prmonitor.setMAX(dateArrayList.size());
					prmonitor.getProgressBar().setForeground(new Color(0, 0, 150));
					prmonitor.setProgressMessage("Sending Reponse...");
					prmonitor.setStatusMessage("Sending To : " + str5);
					prmonitor.runProgressMonitor();
					approvalStatus();
					javax.swing.JOptionPane.showMessageDialog(f, localException1.getMessage().toString());
				}
			}
		}
	}

	public void markLeave() {
		int i = 0;
		selectedDates();

		int j = typesTB.getSelectedRow();

		String str1 = SessionUtil.getLoginInfo().getCompanyId();
		String str2 = SessionUtil.getLoginInfo().getBranchCode();
		String str3 = String.valueOf(typesModel.getValueAt(j, 0));

		String str4 = codetf.getText();
		String str5 = ">> " + tp1.getText();

		if ((typesTB.getSelectedRow() < 0) || (dateArrayList.size() <= 0)) {
			javax.swing.JOptionPane.showMessageDialog(f, "Date or Leave Type is Not Selected.");

		} else {

			for (int k = 0; k < dateArrayList.size(); k++) {
				datestr = String.valueOf(dateArrayList.get(k));
				java.sql.Date localDate = SolDateFormatter.DDMMYYtoSQL(datestr);
				int m = 0;

				try {
					PreparedStatement localPreparedStatement1 = con.prepareStatement(
							"select COUNT(EMP_CODE) from HRTIMEMASTER where EMP_CODE=? and DATE=? and TOTAL_TIME NOT LIKE ? ");
					localPreparedStatement1.setString(1, str4);
					localPreparedStatement1.setDate(2, localDate);
					localPreparedStatement1.setString(3, "%0");
					ResultSet localResultSet = localPreparedStatement1.executeQuery();

					while (localResultSet.next()) {
						m = localResultSet.getInt(1);
						System.out.println("Counted Record :" + m);
					}
				} catch (Exception localException1) {
					System.out.println("HRTIMEMASTER : " + localException1);
				}
				if (m <= 0) {
					try {
						PreparedStatement localPreparedStatement2 = con
								.prepareStatement("insert into HREMP_LEAVES values('" + str1 + "','" + str2 + "','"
										+ codetf.getText() + "', ? ,'" + str3 + "','" + str5 + "','" + scodetf.getText()
										+ "','true','true','" + dateString + "') ");
						localPreparedStatement2.setDate(1, localDate);
						affected = localPreparedStatement2.executeUpdate();

						if (affected > 0) {
							i += 1;
						}
					} catch (Exception localException2) {
						System.out.println("HREMP_LEAVE : " + localException2);
					}
				}
				if (m > 0) {
					javax.swing.JOptionPane.showMessageDialog(f,
							"Cannot Mark Absent Becoz.\nHe/She was Present on " + datestr + ".");
					break;
				}
			}

			leaveRemain();
			approvalStatus();
		}

		if (i > 0) {
			javax.swing.JOptionPane.showMessageDialog(f, "Leave Updated.");
			i = 0;
		}
	}

	public void removeLeave() {
		selectedDates();

		for (int i = 0; i < dateArrayList.size(); i++) {
			datestr = String.valueOf(dateArrayList.get(i));
			java.sql.Date localDate = SolDateFormatter.DDMMYYtoSQL(datestr);

			Object[] arrayOfObject = { "Yes, Please", "No, Thanks",
					"<HTML><Center>No, I want to   <br>Reselect the Dates." };
			int j = javax.swing.JOptionPane.showOptionDialog(f, "Do You Want to Remove " + localDate + ".",
					"Confirm Remove", 1, 3, null, arrayOfObject, arrayOfObject[2]);

			if (j == 0) {
				try {
					PreparedStatement localPreparedStatement = con.prepareStatement(
							"Delete from HREMP_LEAVES where emp_code='" + codetf.getText() + "' and Date = ?");
					localPreparedStatement.setDate(1, localDate);
					affected = localPreparedStatement.executeUpdate();

					if (affected > 0) {
					}

					leaveRemain();
				} catch (Exception localException) {
					System.out.println(localException);
				}

				approvalStatus();
			}

			if ((j == 1) &&

			(j == 2)) {
				break;
			}
		}
	}

	public void CancelLeave() {
		int i = requestTB.getSelectedRow();
		requestTB.getSelectedColumn();

		String str1 = coidtf.getText();
		String str2 = brcodetf.getText();
		String str3 = String.valueOf(requestListModel.getValueAt(i, 0));
		String str4 = String.valueOf(requestListModel.getValueAt(i, 1));
		String.valueOf(requestListModel.getValueAt(i, 4));

		try {
			stat = con.createStatement();
			affected = stat.executeUpdate("update HREMP_LEAVE_REQ SET APPROVE_HR='false' WHERE EMP_CODE='" + str3
					+ "' AND REQ_DATE='" + str4 + "' and company_id='" + str1 + "' and Branch_code='" + str2 + "' ");

			if (affected > 0) {
				try {
					stat = con.createStatement();
					affected = stat
							.executeUpdate("DELETE FROM HREMP_LEAVES WHERE EMP_CODE='" + str3 + "' AND REQ_DATE='"
									+ str4 + "' and company_id='" + str1 + "' and Branch_code='" + str2 + "' ");
				} catch (Exception localException1) {
					if (affected > 0) {
						System.out.println("Deleting HREMP_LEAVES : " + localException1);
						prmonitor.showMonitor();
						prmonitor.setMIN(0);
						prmonitor.setMAX(dateArrayList.size());
						prmonitor.getProgressBar().setForeground(new Color(0, 0, 150));
						prmonitor.setProgressMessage("Canceling Leave");
						prmonitor.setStatusMessage("Sending To : " + str3);
						prmonitor.runProgressMonitor();
						approvalStatus();
						leaveRemain();
					}
				}
			}
		} catch (Exception localException2) {
			javax.swing.JOptionPane.showMessageDialog(f, localException2.getMessage().toString());
		}
	}

	public void approvalStatus() {
		approveListModel.setNumRows(0);

		String str1 = codetf.getText();
		String str2 = coidtf.getText();
		String str3 = brcodetf.getText();

		try {
			stat = con.createStatement();
			rs = stat.executeQuery(
					"select req_date, approve_tl, approve_hr, reason, tl_remarks, hr_remarks from HREMP_LEAVE_REQ WHERE emp_code='"
							+ str1 + "' and company_id='" + str2 + "' and Branch_code='" + str3 + "' ");

			while (rs.next()) {
				String str4 = new String(rs.getString(1));
				String str5 = new String(rs.getString(2));
				String str6 = new String(rs.getString(3));
				String str7 = new String(rs.getString(4));
				String str8 = new String(rs.getString(5));
				String str9 = new String(rs.getString(6));

				approveListModel.addRow(new Object[] { str4, new Boolean(str5), new Boolean(str6), str7, str8, str9 });
				System.out.println(str4 + "\t" + str5 + "\t" + str6);
			}
		} catch (Exception localException) {
			javax.swing.JOptionPane.showMessageDialog(f, localException.getMessage().toString());
		}
		for (int i = 3; i < approveTB.getColumnCount(); i++) {
			approveTB.removeColumn(approveTB.getColumnModel().getColumn(i));
		}
	}

	public void showRemarks() {
		int i = approveTB.getSelectedRow();
		String str1 = String.valueOf(approveListModel.getValueAt(i, 3));
		String str2 = String.valueOf(approveListModel.getValueAt(i, 4));
		String str3 = String.valueOf(approveListModel.getValueAt(i, 5));

		tp2.setText("");
		tp2.setText(tp2.getText() + "Reason. \n");
		tp2.setText(tp2.getText() + "----------------------------------------------------\n");
		tp2.setText(tp2.getText() + str1 + "\n\n\n");
		tp2.setText(tp2.getText() + "----------------------------------------------------\n");
		tp2.setText(tp2.getText() + "TL Remarks. \n");
		tp2.setText(tp2.getText() + "----------------------------------------------------\n");
		tp2.setText(tp2.getText() + str2 + "\n\n\n");
		tp2.setText(tp2.getText() + "----------------------------------------------------\n");
		tp2.setText(tp2.getText() + "HR Remarks. \n");
		tp2.setText(tp2.getText() + str3);
		tp2.setText(tp2.getText() + "----------------------------------------------------\n");
	}

	public void empDetails(String paramString) {
		nametf.setText(EmpTB.getEmpName(paramString));
		posttf.setText(EmpTB.getDesig(paramString));
		coidtf.setText(EmpTB.getCompanyID(paramString));
		brcodetf.setText(EmpTB.getBranchCode(paramString));

		susertypetf.setText(EmpTB.getUserType(scodetf.getText()));
		sdepttf.setText(EmpTB.getDept(scodetf.getText()));
		sposttf.setText(EmpTB.getDesig(scodetf.getText()));
		scoidtf.setText(EmpTB.getCompanyID(scodetf.getText()));
		sbrcodetf.setText(EmpTB.getBranchCode(scodetf.getText()));
	}

	public void leaveDetails(String paramString) {
		model.setNumRows(0);

		String str1 = String.valueOf(DateCalculationUtil.getYear(yeartf.getText()));
		String str2 = String.valueOf(optioncb.getSelectedItem());

		java.sql.Date localDate1 = null;
		java.sql.Date localDate2 = null;

		if (str2.equalsIgnoreCase("Yearly")) {
			localDate1 = SolDateFormatter.DDMMYYtoSQL("01/01/" + str1);
			localDate2 = SolDateFormatter.DDMMYYtoSQL("31/12/" + str1);
		}
		if (str2.equalsIgnoreCase("Monthly")) {
			localDate1 = SolDateFormatter.DDMMYYtoSQL("01/" + String.valueOf(DateCalculationUtil.getMonth(paramString)) + "/" + str1);
			localDate2 = SolDateFormatter.DDMMYYtoSQL("31/" + String.valueOf(DateCalculationUtil.getMonth(paramString)) + "/" + str1);
		}

		java.sql.Date localDate3 = SolDateFormatter.DDMMYYtoSQL(EmpTB.getDOJ(codetf.getText()));

		try {
			con = DBConnectionUtil.getConnection();
			PreparedStatement localPreparedStatement = con.prepareStatement(
					"select emp_code, date, category, reason, approve_tl, approve_hr from HREmp_Leaves where emp_code='"
							+ codetf.getText() + "' and DATE between ? and ? and DATE > ? ORDER BY Date ");
			localPreparedStatement.setDate(1, localDate1);
			localPreparedStatement.setDate(2, localDate2);
			localPreparedStatement.setDate(3, localDate3);
			ResultSet localResultSet = localPreparedStatement.executeQuery();
			localResultSet.getMetaData().getColumnCount();
			while (localResultSet.next()) {
				new String(localResultSet.getString(1));
				java.sql.Date localDate4 = localResultSet.getDate(2);
				String str4 = String.valueOf(SolDateFormatter.SQLtoDDMMYY(localDate4));
				String str5 = new String(localResultSet.getString(3));
				String str6 = new String(localResultSet.getString(4));
				new String(localResultSet.getString(5));
				new String(localResultSet.getString(6));

				String str9 = DateCalculationUtil.getNameofMonth(DateCalculationUtil.getMonth(str4) - 1);

				model.addRow(new Object[] { str4, str9.toUpperCase(), str5, str6 });
			}
		} catch (Exception localException) {
			System.out.println(localException);
		}
		calculate();
	}

	public void calculate() {
		int i = 0;
		int j = 0;
		int k = 0;
		int m = 0;

		for (int n = 0; n < tb.getRowCount(); n++) {
			String str = String.valueOf(model.getValueAt(n, 2));
			if (str.equalsIgnoreCase("LWP")) {
				i += 1;
				m += 1;
			}
			if (str.equalsIgnoreCase("CL")) {
				j += 1;
				m += 1;
			}
			if (str.equalsIgnoreCase("EL")) {
				k += 1;
				m += 1;
			}
		}

		model.addRow(new Object[] { "", "", "",
				"<HTML><b><Font size='3' color='black'>Leave Without Pay: <HTML><Font size='3' color='RED'>"
						+ String.valueOf(i)
						+ ",<HTML><Font size='3' color='black'> Casual Leave: <HTML><Font size='3' color='GREEN'>"
						+ String.valueOf(j)
						+ ",<HTML><Font size='3' color='black'> Earn Leave: <HTML><Font size='3' color='BLUE'>"
						+ String.valueOf(k)
						+ "<HTML><Font size='3' color='BLACK'> Total Leave: <HTML><Font size='3' color='BLUE'>"
						+ String.valueOf(m) });
	}

	public void approvedLeaveDetails(String paramString) {
		model.setNumRows(0);

		try {
			con = DBConnectionUtil.getConnection();
			PreparedStatement localPreparedStatement = con.prepareStatement(
					"select emp_code, date, category, reason, approve_tl, approve_hr from HREmp_Leaves where emp_code='"
							+ codetf.getText() + "' and req_date='" + paramString + "' ");
			ResultSet localResultSet = localPreparedStatement.executeQuery();
			localResultSet.getMetaData().getColumnCount();
			while (localResultSet.next()) {
				new String(localResultSet.getString(1));
				java.sql.Date localDate = localResultSet.getDate(2);
				String str2 = String.valueOf(SolDateFormatter.SQLtoDDMMYY(localDate));
				String str3 = new String(localResultSet.getString(3));
				String str4 = new String(localResultSet.getString(4));
				new String(localResultSet.getString(5));
				new String(localResultSet.getString(6));

				model.addRow(new Object[] { str2, str3, str4 });
			}
		} catch (Exception localException) {
			System.out.println(localException);
		}
	}

	public void showRecord() {
		model.setNumRows(0);

		try {
			con = DBConnectionUtil.getConnection();
			stat = con.createStatement();
			rs = stat.executeQuery("Select date, category, reason, approved from HREMP_LEAVES where Emp_Code='"
					+ codetf.getText() + "' ");
			while (rs.next()) {
				String str1 = new String(rs.getString(1));
				String str2 = new String(rs.getString(2));
				String str3 = new String(rs.getString(3));
				String str4 = new String(rs.getString(4));

				model.addRow(new Object[] { str1, str2, str3, str4 });
			}
		} catch (Exception localException) {
			System.out.println(localException);
		}
	}

	public void showTL() {
		String str1 = coidtf.getText();
		String str2 = brcodetf.getText();

		System.out.println("TL List :");
		try {
			con = DBConnectionUtil.getConnection();
			stat = con.createStatement();
			rs = stat.executeQuery("Select emp_code from HREMP_JOIN where CO_ID='" + str1 + "' and br_code='" + str2
					+ "' AND Desig='Project Manager' ");
			while (rs.next()) {
				String str3 = new String(rs.getString(1));
				System.out.println(str3);
				listModel.addElement(str3);
			}
		} catch (Exception localException) {
			System.out.println(localException);
		}
	}

	public void leaveRemain() {
		String str1 = coidtf.getText();
		String str2 = brcodetf.getText();
		String str3 = codetf.getText();

		String str4 = cal2.getYear();
		@SuppressWarnings("deprecation")
		java.sql.Date localDate1 = new java.sql.Date(Integer.parseInt(str4) - 1900, 0, 1);
		@SuppressWarnings("deprecation")
		java.sql.Date localDate2 = new java.sql.Date(Integer.parseInt(str4) - 1900, 11, 31);
		System.out.println(str4 + "\t" + localDate1);

		for (int i = 0; i < typesTB.getRowCount(); i++) {
			String str5 = String.valueOf(typesModel.getValueAt(i, 0));
			try {
				PreparedStatement localPreparedStatement = con
						.prepareStatement("select count(Category) from HREMP_LEAVES where Category='" + str5
								+ "' and emp_code='" + str3 + "' and company_id='" + str1 + "' and branch_code='" + str2
								+ "' and approve_tl not like'false' and approve_hr not like 'false' and date between ? and ? ");
				localPreparedStatement.setDate(1, localDate1);
				localPreparedStatement.setDate(2, localDate2);
				ResultSet localResultSet = localPreparedStatement.executeQuery();
				while (localResultSet.next()) {
					String str6 = new String(localResultSet.getString(1));
					typesModel.setValueAt(str6, i, 2);
				}
			} catch (Exception localException) {
				System.out.println(localException);
			}
		}
	}

	public void runAllDates() {
		cal2.runDates();
		int i;
		if (String.valueOf(cal2.MonthList().getSelectedItem()).equalsIgnoreCase("January")) {
			i = Integer.parseInt(cal2.getYear());

			cal1.MonthList().setSelectedIndex(11);
			cal1.YearList().setValue(new Integer(i - 1));
			cal1.runDates();

			cal3.YearList().setValue(new Integer(i));
			cal3.MonthList().setSelectedIndex(cal2.MonthList().getSelectedIndex() + 1);
			cal3.runDates();
		}

		if (String.valueOf(cal2.MonthList().getSelectedItem()).equalsIgnoreCase("December")) {
			i = Integer.parseInt(cal2.getYear());
			cal1.YearList().setValue(new Integer(i));
			cal1.MonthList().setSelectedIndex(cal2.MonthList().getSelectedIndex() - 1);
			cal1.runDates();

			cal3.MonthList().setSelectedIndex(0);
			cal3.YearList().setValue(new Integer(i + 1));
			cal3.runDates();
		}

		if ((cal2.MonthList().getSelectedIndex() > 0) && (cal2.MonthList().getSelectedIndex() < 11)) {
			i = Integer.parseInt(cal2.getYear());
			cal1.MonthList().setSelectedIndex(cal2.MonthList().getSelectedIndex() - 1);
			cal1.YearList().setValue(new Integer(i));
			cal1.runDates();

			cal3.MonthList().setSelectedIndex(cal2.MonthList().getSelectedIndex() + 1);
			cal3.YearList().setValue(new Integer(i));
			cal3.runDates();
		}
	}

	public ArrayList<?> leaveDateList() {
		ArrayList localArrayList = new ArrayList();
		localArrayList.clear();

		for (int i = 0; i < tb.getRowCount() - 1; i++) {
			String str1 = String.valueOf(model.getValueAt(i, 0));
			String str2 = String.valueOf(model.getValueAt(i, 2));

			System.out.println("Date Length :" + str1.length());

			if (str2.equalsIgnoreCase("LWP")) {
				str2 = "Red";
			}
			if (str2.equalsIgnoreCase("EL")) {
				str2 = "Blue";
			}
			if (str2.equalsIgnoreCase("CL")) {
				str2 = "Yellow";
			}

			localArrayList.add(str1 + "_" + str2);
		}

		return localArrayList;
	}

	public void setLeaveColored() {
		leaveDateList();
		cal1.setDates(leaveDateList());
		cal2.setDates(leaveDateList());
		cal3.setDates(leaveDateList());
	}

	public void actionPerformed(java.awt.event.ActionEvent paramActionEvent) {
		if (paramActionEvent.getSource() == codetf) {
			yeartf.setText("01/" + String.valueOf(df1.format(cal2.getMonth() + 1)) + "/" + cal2.getYear());

			runAllDates();
			empDetails(codetf.getText());
			leaveDetails("01/" + String.valueOf(df1.format(cal2.getMonth() + 1)) + "/" + cal2.getYear());
			setLeaveColored();
			leaveRemain();
			approvalStatus();

			LeaveStatus.tf1.setText(yeartf.getText());
			LeaveStatus.tf2.setText(codetf.getText());
			ls.empList();
		}
		if (paramActionEvent.getSource() == OKBut) {
			saveLeaves();
		}

		if ((paramActionEvent.getSource() != directOKBut) ||

		(paramActionEvent.getSource() == directBut)) {

			markLeave();
		}
		if (paramActionEvent.getSource() == pastrequestbut) {
			pastReqList();
		}
		if (paramActionEvent.getSource() == currentrequestbut) {
			reqList();
		}

		if (paramActionEvent.getSource() == cal2.MonthList()) {
			runAllDates();
		}

		if (paramActionEvent.getSource() == yeartf) {
			leaveDetails(yeartf.getText());
			LeaveStatus.tf1.setText(yeartf.getText());
			LeaveStatus.tf2.setText(codetf.getText());
			ls.empList();
		}
		if (paramActionEvent.getSource() == refreshcalbut) {
			runAllDates();
			leaveDetails("01/" + String.valueOf(df1.format(cal2.getMonth() + 1)) + "/" + cal2.getYear());
			setLeaveColored();
		}
		if (paramActionEvent.getSource() == cancelleavebut) {
			CancelLeave();
			leaveRemain();
			approvalStatus();
		}
		if (paramActionEvent.getSource() == deleteleavebut) {
			removeLeave();
		}
		if (paramActionEvent.getSource() == optioncb) {
			leaveDetails(yeartf.getText());
			LeaveStatus.tf1.setText(yeartf.getText());
			LeaveStatus.tf2.setText(codetf.getText());
			ls.empList();
		}
	}

	public void mousePressed(java.awt.event.MouseEvent paramMouseEvent) {
		if (paramMouseEvent.getSource() == approveTB) {
			showRemarks();
		}
		if (paramMouseEvent.getSource() == tabbedpane2) {
			int i = tabbedpane2.getSelectedIndex();
			if (i == 0) {
				southButtonPanel.add(OKBut);
				southButtonPanel.remove(ApproveBut);
				southButtonPanel.setBackground(Color.darkGray);
			}
			if (i == 1) {
				southButtonPanel.remove(OKBut);
				southButtonPanel.add(ApproveBut);
				southButtonPanel.setBackground(Color.gray);
			}
		}

		if (paramMouseEvent.getSource() == yeartf) {
			if (javax.swing.SwingUtilities.isRightMouseButton(paramMouseEvent)) {
				skl.showCalendar();
				skl.getDate(yeartf);
			}
		}
	}

	public void mouseClicked(java.awt.event.MouseEvent paramMouseEvent) {
		if (paramMouseEvent.getSource() == requestTB) {
			int i = requestTB.getSelectedColumn();
			int j = requestTB.getSelectedRow();
			String str1 = String.valueOf(requestListModel.getValueAt(j, 0));
			String str2 = String.valueOf(requestListModel.getValueAt(j, 1));

			if (i <= 5) {
				codetf.setText(str1);
				empDetails(str1);
				leaveRemain();
				leaveReason();
				approvalStatus();
				approvedLeaveDetails(str2);
				setLeaveColored();
			}

			if (sdepttf.getText().equalsIgnoreCase("HR")) {
				if (i == 4) {
					ApprovedByHR();
				}

			} else if (i == 3) {
				ApprovedByTech();
			}
		}
	}

	public void keyPressed(java.awt.event.KeyEvent paramKeyEvent) {
		if (paramKeyEvent.getSource() == codetf) {
			int i = paramKeyEvent.getKeyCode();

			if (i == 112) {
				HelpTable localHelpTable = new HelpTable();
				localHelpTable.showFrame();
				localHelpTable.getEmpCode(codetf);
			}
		}
	}

	public void mouseReleased(java.awt.event.MouseEvent paramMouseEvent) {
	}

	public void mouseEntered(java.awt.event.MouseEvent paramMouseEvent) {
	}

	public void mouseExited(java.awt.event.MouseEvent paramMouseEvent) {
	}

	public void keyReleased(java.awt.event.KeyEvent paramKeyEvent) {
	}

	public void keyTyped(java.awt.event.KeyEvent paramKeyEvent) {
	}
}
