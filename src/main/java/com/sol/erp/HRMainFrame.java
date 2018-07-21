package com.sol.erp;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDesktopPane;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;

import com.sol.erp.util.SessionUtil;

public class HRMainFrame extends javax.swing.JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	

	java.util.GregorianCalendar gcal;

	java.text.DecimalFormat df = new java.text.DecimalFormat("00.00");
	java.text.DecimalFormat df1 = new java.text.DecimalFormat("00");

	javax.swing.JToolBar toolbar = new javax.swing.JToolBar();
	javax.swing.JButton mas_reportbut = new javax.swing.JButton("MyReport Builder");

	String codestr = null;
	String usertypestr = null;

	public HRMainFrame() {
		initComponents();

		paramUser();
		loginTime();
	}

	java.awt.Font fo = new java.awt.Font("Tahoma", 1, 11);
	private JDesktopPane jDesktopPane1;
	private JLabel l1;
	private JLabel l2;
	private JLabel l3;
	private JLabel l4;
	private JLabel l5;
	private JLabel l6;
	private JLabel l7;
	private JMenuItem jMenuItem1;
	private JMenuItem jMenuItem2;
	private JMenuItem jMenuItem3;
	private JMenuItem jMenuItem4;
	private JMenuItem jMenuItem5;
	private JMenuItem jMenuItem6;
	private JMenuItem mfa1;
	private JMenuItem mfa2;
	private JMenuItem mfa3;
	private JPanel jPanel1;
	private JSeparator jSeparator1;
	private JSeparator jSeparator10;
	private JSeparator jSeparator11;
	private JSeparator jSeparator12;

	private void initComponents() {
		java.awt.Toolkit localToolkit = java.awt.Toolkit.getDefaultToolkit();
		jDesktopPane1 = new JDesktopPane();
		jPanel1 = new JPanel();
		l1 = new JLabel();
		stf1 = new JTextField();
		l2 = new JLabel();
		stf2 = new JTextField();
		l3 = new JLabel();
		stf3 = new JTextField();
		l4 = new JLabel();
		stf4 = new JTextField();
		l5 = new JLabel();
		stf5 = new JTextField();
		l6 = new JLabel();
		stf6 = new JTextField();
		l7 = new JLabel();
		stf7 = new JTextField("0");

		menubar = new JMenuBar();
		m = new JMenu();
		m1 = new JMenuItem();
		m2 = new JMenuItem();
		m3 = new JMenuItem();
		m4 = new JMenuItem();

		ma = new JMenu();
		ma2 = new JMenuItem();
		ma3 = new JMenuItem();

		jSeparator1 = new JSeparator();
		ma4 = new JMenuItem();
		jSeparator2 = new JSeparator();
		ma5 = new JMenuItem();
		ma6 = new JMenuItem();
		new JSeparator();
		ma7 = new JMenuItem();
		ma8 = new JMenuItem();
		new JSeparator();
		ma9 = new JMenuItem();
		ma10 = new JMenuItem();
		jSeparator5 = new JSeparator();
		ma11 = new JMenuItem();
		jSeparator6 = new JSeparator();
		ma12 = new JMenuItem();
		jSeparator7 = new JSeparator();
		ma13 = new JMenuItem();
		ma14 = new JMenuItem();
		mb = new JMenu();
		mb1 = new JMenuItem();
		mb2 = new JMenuItem();
		mb3 = new JMenuItem();
		mc = new JMenu();
		mc1 = new JMenuItem();
		mc2 = new JMenuItem();

		new JSeparator();
		mc3 = new JMenuItem();
		mc4 = new JMenuItem();
		new JSeparator();
		mc5 = new JMenuItem();
		md = new JMenu();
		mfa1 = new JMenuItem();
		new JSeparator();
		mfa2 = new JMenuItem();
		new JSeparator();
		mfa3 = new JMenuItem();
		me = new JMenu();
		jMenuItem1 = new JMenuItem();
		jMenuItem2 = new JMenuItem();
		jSeparator11 = new JSeparator();
		jMenuItem3 = new JMenuItem();
		jMenuItem4 = new JMenuItem();
		jMenuItem5 = new JMenuItem();
		jSeparator12 = new JSeparator();
		jMenuItem6 = new JMenuItem();
		mf = new JMenu();

		mfa = new JMenu();
		mfb = new JMenuItem();
		mfc = new JMenuItem();
		mfd = new JMenuItem();
		new JMenu();

		mg = new JMenu();

		mh = new JMenu();
		mh1 = new JMenuItem();
		mh2 = new JMenuItem();
		mh3 = new JMenuItem();
		mh4 = new JMenuItem();

		mi = new JMenu();
		mj = new JMenu();
		mj1 = new JMenuItem();
		mj2 = new JMenuItem();

		mk = new JMenu();
		mk1 = new JMenuItem();
		jSeparator10 = new JSeparator();
		mk2 = new JMenuItem();
		mk3 = new JMenuItem();

		ml = new JMenu();
		ml1 = new JMenuItem();
		jSeparator10 = new JSeparator();
		ml2 = new JMenuItem();
		ml3 = new JMenuItem();

		mn = new JMenu();
		mn1 = new JMenuItem();

		setTitle("Structures Online's HRD");
		setCursor(new java.awt.Cursor(0));
		getContentPane().add(jDesktopPane1, "Center");
		jDesktopPane1.setBackground(new java.awt.Color(160, 190, 160));
		jDesktopPane1.setBorder(javax.swing.BorderFactory.createBevelBorder(1));

		jPanel1.setLayout(new java.awt.FlowLayout(0));

		l1.setText("Logged By");
		l2.setText("User Type");
		l3.setText("DEPT");
		l4.setText("Post");
		l5.setText("Login Time");
		l6.setText("Co Id");
		l7.setText("Branch Code");

		jPanel1.add(l1);
		jPanel1.add(stf1);
		jPanel1.add(l2);
		jPanel1.add(stf2);
		jPanel1.add(l3);
		jPanel1.add(stf3);
		jPanel1.add(l4);
		jPanel1.add(stf4);
		jPanel1.add(l5);
		jPanel1.add(stf5);
		jPanel1.add(l6);
		jPanel1.add(stf6);
		jPanel1.add(l7);
		jPanel1.add(stf7);

		stf1.setEditable(false);
		stf1.setFont(fo);
		stf2.setEditable(false);
		stf2.setFont(fo);
		stf3.setEditable(false);
		stf3.setFont(fo);
		stf4.setEditable(false);
		stf4.setFont(fo);
		stf5.setEditable(false);
		stf5.setFont(fo);
		stf6.setEditable(false);
		stf6.setFont(fo);
		stf7.setEditable(false);
		stf7.setFont(fo);

		stf2.setPreferredSize(new Dimension(100, 21));
		stf1.setPreferredSize(new Dimension(100, 21));
		stf3.setPreferredSize(new Dimension(100, 21));
		stf4.setPreferredSize(new Dimension(150, 21));
		stf5.setPreferredSize(new Dimension(50, 21));
		stf6.setPreferredSize(new Dimension(50, 21));
		stf7.setPreferredSize(new Dimension(50, 21));

		getContentPane().add(jPanel1, "South");

		m.setText("INITIAL SetUp");
		m.add(m1);
		m1.setText("Create Company");
		m1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent paramAnonymousActionEvent) {
				m1ActionPerformed(paramAnonymousActionEvent);
			}

		});
		m.add(m2);
		m2.setText("Create Basic Setup");
		m2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent paramAnonymousActionEvent) {
				m2ActionPerformed(paramAnonymousActionEvent);
			}

		});
		m3.setText("Assets/Accessories");
		m3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent paramAnonymousActionEvent) {
				m3ActionPerformed(paramAnonymousActionEvent);

			}

		});
		m4.setText("Time Schedules");
		m4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent paramAnonymousActionEvent) {
				m4ActionPerformed(paramAnonymousActionEvent);
			}

		});
		m.add(jSeparator7);

		m.add(ma14);
		ma14.setText("Close");
		ma14.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent paramAnonymousActionEvent) {
				ma14ActionPerformed(paramAnonymousActionEvent);
			}

		});
		menubar.add(m);

		ma.setText("Company Master");
		ma2.setText("Company Setup");
		ma2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent paramAnonymousActionEvent) {
				ma2ActionPerformed(paramAnonymousActionEvent);
			}

		});
		ma.add(ma2);

		ma3.setText("Designation Setup");
		ma3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent paramAnonymousActionEvent) {
				ma3ActionPerformed(paramAnonymousActionEvent);
			}

		});
		ma.add(ma3);

		ma.add(jSeparator1);

		ma4.setText("Shift & Schedule");
		ma4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent paramAnonymousActionEvent) {
				ma4ActionPerformed(paramAnonymousActionEvent);
			}

		});
		ma.add(ma4);

		ma.add(jSeparator2);

		ma5.setText("Recruitment Mode");
		ma5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent paramAnonymousActionEvent) {
				ma5ActionPerformed(paramAnonymousActionEvent);

			}

		});
		ma6.setText("Define Salary Structures");
		ma6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent paramAnonymousActionEvent) {
				ma6ActionPerformed(paramAnonymousActionEvent);
			}

		});
		ma.add(ma6);

		ma7.setText("Create Basic Salary Structures");
		ma7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent paramAnonymousActionEvent) {
				ma7ActionPerformed(paramAnonymousActionEvent);

			}

		});
		ma8.setText("Define Level");

		ma9.setText("Create & Define Leaves");
		ma9.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent paramAnonymousActionEvent) {
				ma9ActionPerformed(paramAnonymousActionEvent);

			}

		});
		ma10.setText("Company Leave Policies");
		ma10.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent paramAnonymousActionEvent) {
				ma10ActionPerformed(paramAnonymousActionEvent);

			}

		});
		ma11.setText("Define OT Structure");
		ma.add(ma11);
		ma11.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent paramAnonymousActionEvent) {
				ma11ActionPerformed(paramAnonymousActionEvent);
			}

		});
		ma.add(jSeparator5);

		ma.add(mfb);

		ma.add(jSeparator6);

		ma12.setText("Probation Period");

		ma.add(ma13);
		ma13.setText("Login");
		ma13.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent paramAnonymousActionEvent) {
				ma13ActionPerformed(paramAnonymousActionEvent);

			}

		});
		menubar.add(ma);

		mb2.setText("Employee Factor & Contacts");
		mb2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent paramAnonymousActionEvent) {
				mb2ActionPerformed(paramAnonymousActionEvent);
			}

		});
		mb.setText("Employee Master");
		mb1.setText("Employee Registration & Details");
		mb1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent paramAnonymousActionEvent) {
				mb1ActionPerformed(paramAnonymousActionEvent);
			}

		});
		mb.add(mb1);

		mb3.setText("OT Permission & Probation Status");
		mb3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent paramAnonymousActionEvent) {
				mb3ActionPerformed(paramAnonymousActionEvent);
			}

		});
		mb.add(mb3);
		mb.addSeparator();
		mb.add(mb2);

		menubar.add(mb);

		mc.setText("Attendance");
		mc1.setText("For Tech Dept.");
		mc.add(mc1);

		mc1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent paramAnonymousActionEvent) {
				mc1ActionPerformed(paramAnonymousActionEvent);
			}

		});
		mc4.setText("Monthly Attendance Report");
		mc.add(mc4);
		mc4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent paramAnonymousActionEvent) {
				mc4ActionPerformed(paramAnonymousActionEvent);

			}

		});
		mc3.setText("Daily Attendance Report");

		mc2.setText("For NonTechnichals");

		mc5.setText("Break Time Entry");

		mc5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent paramAnonymousActionEvent) {
				mc5ActionPerformed(paramAnonymousActionEvent);
			}

		});
		menubar.add(mc);

		md.setText("Leave Master");

		me.setText("Letters");
		jMenuItem1.setText("Offer Letter");
		me.add(jMenuItem1);

		jMenuItem2.setText("Joining Letters");
		me.add(jMenuItem2);

		me.add(jSeparator11);

		jMenuItem3.setText("Confirmation");
		me.add(jMenuItem3);

		jMenuItem4.setText("Increament");
		me.add(jMenuItem4);

		jMenuItem5.setText("Warning");
		me.add(jMenuItem5);

		me.add(jSeparator12);

		jMenuItem6.setText("Termination");
		me.add(jMenuItem6);

		mf.setText("Management");
		menubar.add(mf);

		mfa.setText("Leave Management");
		mfb.setText("Setting Holidays");

		mfd.setText("Leave Application Chart");
		mfa.add(mfd);

		mf.add(mfa);

		mfa1.setText("Leave Approvals & Reports");
		mfa2.setText("Manage Leaves");
		mfa3.setText("Leave Reports");

		mfa.add(mfa1);

		mfa1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent paramAnonymousActionEvent) {
				mfa1ActionPerformed(paramAnonymousActionEvent);
			}

		});
		mfb.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent paramAnonymousActionEvent) {
				mfbActionPerformed(paramAnonymousActionEvent);
			}

		});
		mfd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent paramAnonymousActionEvent) {
				mfdActionPerformed(paramAnonymousActionEvent);
			}

		});
		mfc.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent paramAnonymousActionEvent) {
				mfcActionPerformed(paramAnonymousActionEvent);

			}

		});
		mg.setText("Appreasal");

		mh.setText("PayRoll");
		menubar.add(mh);

		mh1.setText("Account Details");
		mh2.setText("Salary Sheet");
		mh2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent paramAnonymousActionEvent) {
				mh2ActionPerformed(paramAnonymousActionEvent);
			}
		});
		mh3.setText("Salary Slip");
		mh4.setText("Employee TDS");
		mh4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent paramAnonymousActionEvent) {
				mh4ActionPerformed(paramAnonymousActionEvent);
			}

		});
		mh.add(mh2);

		mh.add(mh4);

		mi.setText("Views");

		mj.setText("Resume Box");
		mj1.setText("Aplicant Status");
		mj1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent paramAnonymousActionEvent) {
				mj1ActionPerformed(paramAnonymousActionEvent);
			}

		});
		mj.add(mj1);

		mj2.setText("Save Resume");
		mj2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent paramAnonymousActionEvent) {
				mj2ActionPerformed(paramAnonymousActionEvent);
			}

		});
		mj.add(mj2);

		mk.setText("Recruitment");
		mk1.setText("Call For Interview");
		mk1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent paramAnonymousActionEvent) {
				mk1ActionPerformed(paramAnonymousActionEvent);
			}

		});
		mk.add(mk1);

		mk.add(jSeparator10);

		mk2.setText("Interview Rounds & Reports");
		mk2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent paramAnonymousActionEvent) {
				mk2ActionPerformed(paramAnonymousActionEvent);
			}

		});
		mk.add(mk2);

		mk3.setText("Selection Report");

		menubar.add(mk);

		ml.setText("Utilities");

		ml1.setText("Change Password");
		ml1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent paramAnonymousActionEvent) {
				ml1ActionPerformed(paramAnonymousActionEvent);
			}
		});
		ml2.setText("Office Phone Diary");
		ml2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent paramAnonymousActionEvent) {
				ml2ActionPerformed(paramAnonymousActionEvent);
			}
		});
		ml3.setText("Emp List");
		ml3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent paramAnonymousActionEvent) {
				ml3ActionPerformed(paramAnonymousActionEvent);
			}

		});
		ml.add(ml1);
		ml.add(ml2);

		menubar.add(ml);

		mn.setText("Reports");

		mn1.setText("Employee Performance");
		mn1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent paramAnonymousActionEvent) {
				mn1ActionPerformed(paramAnonymousActionEvent);
			}

		});
		mn.add(mn1);
		menubar.add(mn);

		setJMenuBar(menubar);
		setBounds(0, 0, 1024, 745);

		mas_reportbut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent paramAnonymousActionEvent) {
				mas_reportbutActionPerformed(paramAnonymousActionEvent);

			}

		});
		setSize(localToolkit.getScreenSize());
		setVisible(true);

		add(toolbar, "North");
	}

	private JSeparator jSeparator2;
	private JSeparator jSeparator5;
	private JSeparator jSeparator6;
	private JSeparator jSeparator7;


	public static JTextField stf1;
	public static JTextField stf2;
	public static JTextField stf3;
	public static JTextField stf4;
	public static JTextField stf5;
	public static JTextField stf6;
	public static JTextField stf7;

	public static void paramUser() {
		stf1.setText(SessionUtil.getLoginInfo().getId());
		stf2.setText(SessionUtil.getLoginInfo().getUserType());
		stf3.setText(SessionUtil.getLoginInfo().getDept());
		stf4.setText(SessionUtil.getLoginInfo().getPost());
		stf5.setText(SessionUtil.getLoginInfo().getUserType());

		if (stf5.getText().equalsIgnoreCase("User")) {

			m1.setEnabled(false);
			m2.setEnabled(false);
			ma.setEnabled(false);
			mb.setEnabled(false);

			md.setEnabled(false);
			me.setEnabled(false);
			mf.setEnabled(false);
			mg.setEnabled(false);
			mh.setEnabled(false);
			mi.setEnabled(false);
			mj.setEnabled(false);
			mk.setEnabled(false);
			ml.setEnabled(false);
			mn.setEnabled(false);
		}

		stf6.setText(SuperAdminAccess.l2.getText());
		stf7.setText(SuperAdminAccess.l3.getText());
	}

	public static JMenu m;
	public static JMenuItem m1;
	public static JMenuItem m2;
	public static JMenuItem m3;
	public static JMenuItem m4;
	public static JMenu ma;
	private JMenuItem ma10;

	public void loginTime() {
		gcal = new java.util.GregorianCalendar();
		int i = gcal.get(10);
		int j = gcal.get(12);
		stf5.setText(String.valueOf(df1.format(i)) + String.valueOf(df1.format(j)));
	}

	private JMenuItem ma11;
	private JMenuItem ma12;
	private JMenuItem ma13;

	private void mas_reportbutActionPerformed(ActionEvent paramActionEvent) {
		MasterReport localMasterReport = new MasterReport();
		localMasterReport.show();
	}

	private JMenuItem ma14;
	private JMenuItem ma2;

	private void mb3ActionPerformed(ActionEvent paramActionEvent) {
		EmpList2 localEmpList2 = new EmpList2();
		localEmpList2.showEmpList();
	}

	private JMenuItem ma3;

	private void mfa1ActionPerformed(ActionEvent paramActionEvent) {
		LeaveCalc localLeaveCalc = new LeaveCalc();
		localLeaveCalc.setLoginCode(stf1.getText());
		localLeaveCalc.px();
	}

	private JMenuItem ma4;
	private JMenuItem ma5;
	private JMenuItem ma6;

	private void mfbActionPerformed(ActionEvent paramActionEvent) {
		HolidayList localHolidayList = new HolidayList();
		localHolidayList.showFrame();
	}

	private void mfcActionPerformed(ActionEvent paramActionEvent) {
		Letters localLetters = new Letters();
		localLetters.showFrame();
	}

	private void mfdActionPerformed(ActionEvent paramActionEvent) {
		LeaveApplicationChart localLeaveApplicationChart = new LeaveApplicationChart();
		localLeaveApplicationChart.showLeaveApplicationChart();
	}

	private JMenuItem ma7;
	private JMenuItem ma8;
	private JMenuItem ma9;
	public static JMenu mb;
	private JMenuItem mb1;
	private JMenuItem mb2;
	private JMenuItem mb3;
	public static JMenu mc;

	private void mh2ActionPerformed(ActionEvent paramActionEvent) {
		SalarySheet localSalarySheet = new SalarySheet();
		jDesktopPane1.add(localSalarySheet.f);
		localSalarySheet.px();
	}

	private void mh4ActionPerformed(ActionEvent paramActionEvent) {
		TDS localTDS = new TDS();
		localTDS.px();
	}

	private void mj2ActionPerformed(ActionEvent paramActionEvent) {
		EmpDetails localEmpDetails = new EmpDetails();
		jDesktopPane1.add(localEmpDetails);
		localEmpDetails.show();
	}

	private void mk2ActionPerformed(ActionEvent paramActionEvent) {
		Rounds localRounds = new Rounds();

		localRounds.px(stf1.getText());
	}

	private void mn1ActionPerformed(ActionEvent paramActionEvent) {
		EmpPerform2 localEmpPerform2 = new EmpPerform2();
		localEmpPerform2.px();
	}

	private void ml1ActionPerformed(ActionEvent paramActionEvent) {
		ChangePassword localChangePassword = new ChangePassword();
		jDesktopPane1.add(localChangePassword.f);
		localChangePassword.px();
		localChangePassword.setUserID(stf1.getText());
		jDesktopPane1.moveToFront(localChangePassword.f);
	}

	private void ml2ActionPerformed(ActionEvent paramActionEvent) {
		Phone1 localPhone1 = new Phone1();
		jDesktopPane1.add(localPhone1.f);
		jDesktopPane1.moveToFront(localPhone1.f);
		localPhone1.px();
		localPhone1.paramUser(codestr, usertypestr);
	}

	private void ml3ActionPerformed(ActionEvent paramActionEvent) {
	}

	private JMenuItem mc1;
	private JMenuItem mc2;
	private JMenuItem mc3;
	private JMenuItem mc4;
	private JMenuItem mc5;

	private void mk1ActionPerformed(ActionEvent paramActionEvent) {
		CallForInterview localCallForInterview = new CallForInterview();
		jDesktopPane1.add(localCallForInterview.f);
		localCallForInterview.px();
		jDesktopPane1.moveToFront(localCallForInterview.f);
	}

	private void mj1ActionPerformed(ActionEvent paramActionEvent) {
		AplicantStatus localAplicantStatus = new AplicantStatus();
		jDesktopPane1.add(localAplicantStatus);
		localAplicantStatus.show();
	}

	private void mc4ActionPerformed(ActionEvent paramActionEvent) {
		AttendanceReport localATNDReport = new AttendanceReport();
		localATNDReport.px();
	}

	private void mc1ActionPerformed(ActionEvent paramActionEvent) {
		Attendance localAttendance = new Attendance();
		localAttendance.designFrame();
	}

	public static JMenu md;
	public static JMenu me;
	private JMenuBar menubar;
	public static JMenu mf;
	public static JMenu mfa;
	private JMenuItem mfb;
	private JMenuItem mfc;

	private void mc5ActionPerformed(ActionEvent paramActionEvent) {
		BreakTimeEntry localBreakTimeEntry = new BreakTimeEntry();
		localBreakTimeEntry.px();
	}

	private JMenuItem mfd;
	public static JMenu mg;

	private void ma10ActionPerformed(ActionEvent paramActionEvent) {
		LeavePolicy localLeavePolicy = new LeavePolicy();
		jDesktopPane1.add(localLeavePolicy);
		localLeavePolicy.show();
	}

	private void ma9ActionPerformed(ActionEvent paramActionEvent) {
		CreateLeave localCreateLeave = new CreateLeave();
		jDesktopPane1.add(localCreateLeave);
		localCreateLeave.show();
	}

	private void mb1ActionPerformed(ActionEvent paramActionEvent) {
		EmpDetails localEmpDetails = new EmpDetails();
		jDesktopPane1.add(localEmpDetails);
		localEmpDetails.show();
	}

	private void ma7ActionPerformed(ActionEvent paramActionEvent) {
		CreateIncLevel localCreateIncLevel = new CreateIncLevel();
		jDesktopPane1.add(localCreateIncLevel);
		localCreateIncLevel.show();
	}

	public static JMenu mh;
	private JMenuItem mh1;
	private JMenuItem mh2;
	private JMenuItem mh3;
	private JMenuItem mh4;
	public static JMenu mi;
	public static JMenu mj;
	private JMenuItem mj1;
	private JMenuItem mj2;
	public static JMenu mk;

	private void ma6ActionPerformed(ActionEvent paramActionEvent) {
		UpdateSalaryStruct localUpdateSalaryStruct = new UpdateSalaryStruct();
		localUpdateSalaryStruct.DesignFrame();
	}

	private JMenuItem mk1;
	private JMenuItem mk2;
	private JMenuItem mk3;

	private void ma5ActionPerformed(ActionEvent paramActionEvent) {
		ReqMode localReqMode = new ReqMode();
		jDesktopPane1.add(localReqMode);
		localReqMode.show();
	}

	private void ma4ActionPerformed(ActionEvent paramActionEvent) {
		OfficeHrs localofficeHRS = new OfficeHrs();
		localofficeHRS.showOfficeHRS();
	}

	public static JMenu ml;
	private JMenuItem ml1;
	private JMenuItem ml2;
	private JMenuItem ml3;
	public static JMenu mn;
	private JMenuItem mn1;

	private void ma3ActionPerformed(ActionEvent paramActionEvent) {
		DeptDesig localDeptDesig = new DeptDesig();
		localDeptDesig.showDeptDesig();
	}

	private void ma11ActionPerformed(ActionEvent paramActionEvent) {
		OFFDays localOFFDays = new OFFDays();
		localOFFDays.showOFFDays();
	}

	private void ma13ActionPerformed(ActionEvent paramActionEvent) {
		setVisible(false);
		Login locallogin = new Login();
		locallogin.px();
	}

	private void ma14ActionPerformed(ActionEvent paramActionEvent) {
		setVisible(false);
	}

	private void ma2ActionPerformed(ActionEvent paramActionEvent) {
		CompanyPolicy localCompanyPolicy = new CompanyPolicy();
		jDesktopPane1.add(localCompanyPolicy);
		localCompanyPolicy.show();
	}

	private void m1ActionPerformed(ActionEvent paramActionEvent) {
		CreateCompany createCompany = new CreateCompany();
		jDesktopPane1.add(createCompany.f);
		createCompany.designScreen();
		jDesktopPane1.moveToFront(createCompany.f);
	}

	private void m2ActionPerformed(ActionEvent paramActionEvent) {
		CreatePosition localcreatePosition = new CreatePosition();
		jDesktopPane1.add(localcreatePosition.f);
		localcreatePosition.px();
		jDesktopPane1.moveToFront(localcreatePosition.f);
	}

	private void m3ActionPerformed(ActionEvent paramActionEvent) {
	}

	private void m4ActionPerformed(ActionEvent paramActionEvent) {
	}

	private void mb2ActionPerformed(ActionEvent paramActionEvent) {
		EmpList localEmpList = new EmpList();
		localEmpList.showEmpList("");
	}
}
