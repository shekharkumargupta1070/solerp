package com.sol.erp;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.sol.erp.util.DBConnectionUtil;

public class SupportFrame implements java.awt.event.ActionListener {
	
	
	JFrame f;
	static JDesktopPane desktop = new JDesktopPane();

	JMenuBar menubar;

	JMenu ma;

	JMenu mb;

	JMenu mc;

	JMenu md;

	JMenuItem ma1;

	JMenuItem mb1;

	JMenuItem mb2;

	JMenuItem mb3;

	JMenuItem mc1;

	JMenuItem mc2;

	JMenuItem mc3;

	JMenuItem mc4;

	JMenuItem mc5;

	JMenuItem mc6;

	Toolkit tk;

	JLabel datelabel;

	SimpleDateFormat sdf;

	SimpleDateFormat sdf2;

	JPanel centerpanel;

	Date dat;
	String dateString;
	String dateString2;
	Connection con;
	PreparedStatement prsm;
	java.sql.ResultSet rs;

	public void px() {
		Container localContainer = f.getContentPane();
		localContainer.setLayout(new BorderLayout());
		centerpanel.setLayout(new BorderLayout());

		centerpanel.setBackground(new Color(160, 220, 160));

		ma.add(ma1);
		mb.add(mb1);
		mb.add(mb2);
		mb.add(mb3);

		mc.add(mc1);
		mc.add(mc2);
		mc.add(mc3);
		mc.add(mc4);
		mc.add(mc5);
		mc.add(mc6);

		menubar.add(ma);
		menubar.add(mb);
		menubar.add(mc);
		menubar.add(md);

		datelabel.setForeground(Color.red);
		datelabel.setFont(new Font("Times New Roman", 1, 36));
		datelabel.setHorizontalAlignment(0);

		centerpanel.add(datelabel, "Center");
		localContainer.add(centerpanel, "Center");

		localContainer.setBackground(Color.darkGray);
		desktop.setBackground(Color.darkGray);

		ma1.addActionListener(this);

		mb1.addActionListener(this);
		mb2.addActionListener(this);
		mb3.addActionListener(this);

		mc1.addActionListener(this);
		mc2.addActionListener(this);
		mc3.addActionListener(this);
		mc4.addActionListener(this);
		mc5.addActionListener(this);
		mc6.addActionListener(this);

		f.setJMenuBar(menubar);
		f.setSize(tk.getScreenSize());
		f.setVisible(true);
	}

	public SupportFrame() {

		f = new JFrame("Technichal Dept");

		menubar = new JMenuBar();
		ma = new JMenu("Daily Job");
		mb = new JMenu("H.R.");
		mc = new JMenu("Technical");
		md = new JMenu("General Utility");

		ma1 = new JMenuItem("Exit");

		mb1 = new JMenuItem("Attendance");
		mb2 = new JMenuItem("Leave Managment");
		mb3 = new JMenuItem("List & Reports");

		mc1 = new JMenuItem("Estimation");
		mc2 = new JMenuItem("Assiging Projects to Team Leader");
		mc3 = new JMenuItem("Manpower Planning");
		mc4 = new JMenuItem("Set Drawing No");
		mc5 = new JMenuItem("Work Allotment");
		mc6 = new JMenuItem("List & Reports");

		tk = Toolkit.getDefaultToolkit();

		datelabel = new JLabel("Date");
		sdf = new SimpleDateFormat("dd/MM/yyyy");
		sdf2 = new SimpleDateFormat("hh:mm");

		centerpanel = new JPanel();

		dat = new Date();

		try {
			dateString = sdf.format(dat);
			dateString2 = sdf2.format(dat);

			datelabel
					.setText("<html><Center><marquee>ERP Support<BR>" + dateString + ", " + dateString2);
		} catch (IllegalArgumentException localIllegalArgumentException) {
			System.out.println(localIllegalArgumentException.getMessage());
		}

		con = null;
		prsm = null;
		rs = null;
	}

	public void RecyclePassword() {
		String str1 = "0";
		str1 = JOptionPane.showInputDialog(f, "Enter EmpCode To ReCycle the Password");

		String str2 = EmpTB.getUserType(str1);
		String str3 = null;

		if (str2.equalsIgnoreCase("User")) {
			str3 = "DEFAULT";
		}
		if (str2.equalsIgnoreCase("Admin")) {
			str3 = "SUPER";
		}

		try {
			con = DBConnectionUtil.getConnection();
			prsm = con.prepareStatement("Update PASSWORD set password=? where user =?");
			prsm.setString(1, str3);
			prsm.setString(2, str1);
			int i = prsm.executeUpdate();

			if (i > 0) {
				JOptionPane.showMessageDialog(f, "Password ReCycled Successfully.");
			}
		} catch (Exception localException) {
			JOptionPane.showMessageDialog(f, localException.getMessage().toString());
		}
	}

	public void checkConnect() {
		/*
		 * RDBMS_DBPATH localRDBMS_DBPATH = new RDBMS_DBPATH(); DBPATH
		 * localDBPATH = new DBPATH(); localRDBMS_DBPATH.DesignFrame();
		 * localRDBMS_DBPATH.getConnectionState(); DBPATH.con =
		 * RDBMS_DBPATH.con; String str =
		 * localRDBMS_DBPATH.getConnectionState(); System.out.println(
		 * "statestr : " + str);
		 * 
		 * if (str.equalsIgnoreCase("Connection Established")) { px();
		 * emptb.populateDB(); } else { localDBPATH.showFrame(); }
		 */
	}

	public void actionPerformed(ActionEvent paramActionEvent) {
		if (paramActionEvent.getSource() == ma1) {
			System.exit(0);
		}
		Object localObject;
		if (paramActionEvent.getSource() == mb1) {
			localObject = new AttendanceReport();
			((AttendanceReport) localObject).px();
		}
		if (paramActionEvent.getSource() == mb2) {
			localObject = new LeaveCalc();
			((LeaveCalc) localObject).px();
		}
		if ((paramActionEvent.getSource() != mb3) ||

				(paramActionEvent.getSource() == mc1)) {
			localObject = new Estimation();
			desktop.add(((Estimation) localObject).internalFrame);
			desktop.moveToFront(((Estimation) localObject).internalFrame);
			((Estimation) localObject).paramUser();
			((Estimation) localObject).px();
			((Estimation) localObject).structIndex();
		}
		if ((paramActionEvent.getSource() != mc2) || (

		(paramActionEvent.getSource() != mc3) || (

		(paramActionEvent.getSource() != mc4) || (

		(paramActionEvent.getSource() != mc5) ||

				(paramActionEvent.getSource() == mc6))))) {
		}
	}
}
