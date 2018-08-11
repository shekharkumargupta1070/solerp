package com.sol.erp;

import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.GregorianCalendar;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import com.sol.erp.constants.ApplicationConstants;
import com.sol.erp.util.DBConnectionUtil;
import com.sol.erp.util.SessionUtil;

public class TechMainFrame implements ActionListener, MouseListener, KeyListener {

    Connection con;

    Statement stat;

    ResultSet rs;

    static String[] requestColumns = { "SL", "Date", "EMP Code", "REQ No", "Name", "DESIG", "System No", "REQ TL",
            "Reason", "Approved" };
    static Object[][] requestData = new Object[0][];

    static DefaultTableModel requestTableModel = new DefaultTableModel(requestData, requestColumns);
    JTable requestTable = new JTable(requestTableModel);
    JScrollPane requestScrollPane = new JScrollPane(requestTable);

    JTextArea textAreaRequestDetails = new JTextArea();
    JScrollPane reqestTextAreaScrollPane = new JScrollPane(textAreaRequestDetails);

    JSplitPane splitPane = new JSplitPane(0, requestScrollPane, reqestTextAreaScrollPane);

    GregorianCalendar gcal;

    DecimalFormat df = new DecimalFormat("00.00");
    DecimalFormat df1 = new DecimalFormat("00");

    static JDesktopPane desktop = new JDesktopPane();
    JFrame frameTech = new JFrame("Technichal");
    JInternalFrame inf = new JInternalFrame("Request Box", true, true, true, true);

    JPopupMenu jpm = new JPopupMenu();
    JMenuItem popm1 = new JMenuItem("Approved",
            new ImageIcon(ClassLoader.getSystemResource(ApplicationConstants.IMAGE_PATH + "check.gif")));
    JMenuItem popm2 = new JMenuItem("Denied",
            new ImageIcon(ClassLoader.getSystemResource(ApplicationConstants.IMAGE_PATH + "redx.gif")));
    JMenuItem popm3 = new JMenuItem("Close", new ImageIcon("image/close.gif"));

    javax.swing.JMenuBar menubar = new javax.swing.JMenuBar();
    JMenu menuProjectMaster = new JMenu("Project Master");
    JMenu menuJobMaster = new JMenu("Job Master");
    JMenu menuTimeMaster = new JMenu("Time Master");
    JMenu menuReportMaster = new JMenu("Report Master");
    JMenu menuSearch = new JMenu("Search");
    JMenu menuProjectManager = new JMenu("Project Manager");
    JMenu menuSolGroup = new JMenu("Sol Group");
    JMenu menuUtilities = new JMenu("Utilities");

    JMenu menuTimeRequest = new JMenu("Time Request");

    JMenuItem menuItemProjectEstimation = new JMenuItem("Project Estimation");
    JMenuItem menuItemSetDrawingNumber = new JMenuItem("Set Drawing No");
    JMenuItem menuItemManpowerStatus = new JMenuItem("Manpower Status");
    JMenuItem menuItemAssignProjects = new JMenuItem("Assign Project To Team Leader");
    JMenuItem menuItemManpowerPlanning = new JMenuItem("Manpower Planning");
    JMenuItem menuItemWorkAllotment = new JMenuItem("Work Allotment");
    JMenuItem menuItemLogin = new JMenuItem("Login",
            new ImageIcon(ClassLoader.getSystemResource(ApplicationConstants.IMAGE_PATH + "usergrp.gif")));
    JMenuItem menuItemClose = new JMenuItem("Close",
            new ImageIcon(ClassLoader.getSystemResource(ApplicationConstants.IMAGE_PATH + "close.gif")));

    JMenuItem menuItemDailyJobProcess = new JMenuItem("Daily Job Process");
    JMenuItem menuItemRFILog = new JMenuItem("RFI Log");
    JMenuItem menuItemRFILogDetails = new JMenuItem("RFI Log Details");

    JMenuItem menuItemDailyAttendance = new JMenuItem("Daily Attendance");
    JMenuItem menuItemBFADrawing = new JMenuItem("BFA Drawing");
    JMenuItem menuItemBFATimeRequest = new JMenuItem("BFA Time Request");
    JMenuItem menuItemBFARevisionWastedTimeRequest = new JMenuItem("BFA/Revision/Wasted Time Request");
    JMenuItem menuItemWastedTimeRequest = new JMenuItem("Wasted Time Request");
    JMenuItem menuItemBreakTimeRequest = new JMenuItem("Break Time Request");
    JMenuItem menuItemDailyTimeSheet = new JMenuItem("Daily Timesheet");
    JMenuItem menuItemDailyTimeSheetReport = new JMenuItem("Daily Timesheet Report");
    JMenuItem menuItemOTStatus = new JMenuItem("OT STATUS");

    JMenuItem menuItemEmployeeEfficientyReport = new JMenuItem("Emp Efficiency Report");
    JMenuItem menuItemEmployeePerformanceReport = new JMenuItem("Emp Performance Report");
    JMenuItem menuItemEmployeeAllotedJob = new JMenuItem("Emp Alloted Job");

    JMenu menuReportSOL = new JMenu("Reports (SOL)");
    JMenu menuReportClients = new JMenu("Reports (CLIENTS)");

	JMenuItem menuItemProjectPerformanceReport = new JMenuItem("Project Performance Report");
    JMenuItem menuItemProjectCompletionReport = new JMenuItem("Project Completion Report");
    JMenuItem menuItemQuotationCompare = new JMenuItem("Quotation Compare of TL/MP ");
    JMenuItem menuItemProjectManagerEstimation = new JMenuItem("View MP Estimation");
    JMenuItem menuItemTeamLeaderEstimation = new JMenuItem("View TL Estimation");
    JMenuItem menuItemProjectListAndStatus = new JMenuItem("Project List & Status");
    JMenuItem menuItemExtraHrsReport = new JMenuItem("Extra HRS Report ");
    JMenuItem menuItemClientWiseReport = new JMenuItem("Client Wise Report ");
    JMenuItem menuItemQuotationList = new JMenuItem("Quotation List");
    JMenuItem menuItemTimeManagement = new JMenuItem("Training/Ideal/Management Time");
    JMenuItem menuItemExportProjectReport = new JMenuItem("Export Project Report");

    JMenu menuDrawingLog = new JMenu("Drawing Log");
    JMenu menuDrawingIndex = new JMenu("Drawing Index");
    JMenu menuRequestBox = new JMenu("Request Box");
    JMenu menuRFILog = new JMenu("RFI LOG");
    JMenu menuRevisionLog = new JMenu("Revision LOG");

    JMenuItem menuItemCreateItemCode = new JMenuItem("Create Item Code");
    JMenuItem menuItemStructuralDrawingIndex = new JMenuItem("Structural Drawing Index");
    JMenuItem menuItemSetFactor = new JMenuItem("Set Factor");
    JMenuItem menuItemFactorReport = new JMenuItem("Factor Report");
    JMenuItem menuItemProjectSheetDetails = new JMenuItem("Project Sheets Details");
    JMenuItem menuItemUpdateSheets = new JMenuItem("Update Sheets");
    JMenuItem menuItemDrawingLog = new JMenuItem("Drawing Log/Status");
    JMenuItem menuItemReceiveApprovalData = new JMenuItem("Receive Approval Data");
    JMenuItem menuItemCreateDrawingIndex = new JMenuItem("Create Drawing Index");
    JMenuItem menuItemShowIndexReport = new JMenuItem("Show Index Report");
    JMenuItem menuItemOpenRequestBox = new JMenuItem("Open Request Box");
    JMenuItem menuItemCloseRequestBox = new JMenuItem("Close Request Box");
    JMenuItem menuItemRFIQueryForm = new JMenuItem("RFI Query Form");
    JMenuItem menuItemRFIReport = new JMenuItem("RFI REPORT");
    JMenuItem menuItemRevisionLogForm = new JMenuItem("Revision Log Form");
    JMenuItem menuItemRevisionLogReport = new JMenuItem("Revesion Log Report");
    JMenuItem menuItemSolGsn = new JMenuItem("SOL GSN");
    JMenuItem menuItemEmployeesWorkMonitoring = new JMenuItem("Employees Work Monitoring");

    JMenuItem menuItemUnitDetails = new JMenuItem("Unit Details");
    JMenuItem menuItemEmployeeReportUnitWise = new JMenuItem("Emp Report Unit Wise");
    JMenuItem menuItemMailBox = new JMenuItem("Mail Box");
    JMenuItem menuItemLeaveReport = new JMenuItem("Leave Report");
    JMenuItem menuItemSaveEmployeesRecord = new JMenuItem("Save Employees Record");
    JMenuItem menuItemEmployeeCodeGeneration = new JMenuItem("Empployee Code Generation");
    JMenuItem menuItemSystemAllotment = new JMenuItem("System Allotment");
    JMenuItem menuItemFinalEstimation = new JMenuItem("Final Estimation");

    JMenuItem menuItemUserAuthentication = new JMenuItem("User Authentication",
            new ImageIcon(ClassLoader.getSystemResource(ApplicationConstants.IMAGE_PATH + "usergrp.gif")));
    JMenuItem menuItemUpdateAuthentication = new JMenuItem("Update Authentication",
            new ImageIcon(ClassLoader.getSystemResource(ApplicationConstants.IMAGE_PATH + "yes.gif")));
    JMenuItem menuItemChangePassword = new JMenuItem("Change Password",
            new ImageIcon(ClassLoader.getSystemResource(ApplicationConstants.IMAGE_PATH + "sec.gif")));
    JMenuItem menuItemUpdateDBServer = new JMenuItem("Update DB Server",
            new ImageIcon(ClassLoader.getSystemResource(ApplicationConstants.IMAGE_PATH + "sec.gif")));
    JMenuItem menuItemPhoneDiary = new JMenuItem("Office Phone Diary",
            new ImageIcon(ClassLoader.getSystemResource(ApplicationConstants.IMAGE_PATH + "phone.gif")));
    JMenuItem menuItemEmployeeFactorsAndContact = new JMenuItem("Employee Factor & Contacts",
            new ImageIcon(ClassLoader.getSystemResource(ApplicationConstants.IMAGE_PATH + "usergrp.gif")));
    JMenuItem menuItemLeaveApplicationChart = new JMenuItem("Leave Application Chart");
    JMenuItem menuItemRoundAndMarks = new JMenuItem("Rounds and Marks");

    JPanel statusPanel = new JPanel();
    Icon ic = new ImageIcon("image/superuser.gif");
    JLabel labelLoggedBy = new JLabel("Logged By", ic, 10);
    JLabel labelUserType = new JLabel("User Type");
    JLabel labelDepartment = new JLabel("Dept");
    JLabel labelPost = new JLabel("Post");
    JLabel labelUnitNumber = new JLabel("Unit No");
    JLabel labelLoginTime = new JLabel("Login Time");
    JLabel labelCompanyId = new JLabel("Co Id");
    JLabel labelBranchCode = new JLabel("Branch Code");

    public static JTextField textFieldLoggedBy = new JTextField(6);
    public static JTextField textFieldUserType = new JTextField(6);
    public static JTextField textFieldDepartment = new JTextField(10);
    public static JTextField textFieldPost = new JTextField(12);
    public static JTextField stf5 = new JTextField(6);
    public static JTextField textFieldLoginTime = new JTextField(6);
    public static JTextField textFieldCompanyId = new JTextField("0", 5);
    public static JTextField textFieldBranchCode = new JTextField(5);

    JToolBar tool = new JToolBar();

    JButton b1 = new JButton(
            new ImageIcon(ClassLoader.getSystemResource(ApplicationConstants.IMAGE_PATH + "empbig.gif")));
    JButton b2 = new JButton(
            new ImageIcon(ClassLoader.getSystemResource(ApplicationConstants.IMAGE_PATH + "allot.gif")));
    JButton b3 = new JButton(
            new ImageIcon(ClassLoader.getSystemResource(ApplicationConstants.IMAGE_PATH + "revise.gif")));
    JButton b4 = new JButton(
            new ImageIcon(ClassLoader.getSystemResource(ApplicationConstants.IMAGE_PATH + "timebig.gif")));
    JButton b5 = new JButton(
            new ImageIcon(ClassLoader.getSystemResource(ApplicationConstants.IMAGE_PATH + "open.gif")));
    JButton b6 = new JButton(
            new ImageIcon(ClassLoader.getSystemResource(ApplicationConstants.IMAGE_PATH + "save.gif")));
    JButton b7 = new JButton(
            new ImageIcon(ClassLoader.getSystemResource(ApplicationConstants.IMAGE_PATH + "empbig.gif")));
    JButton b8 = new JButton(
            new ImageIcon(ClassLoader.getSystemResource(ApplicationConstants.IMAGE_PATH + "security.gif")));

    TableColumn col1 = requestTable.getColumnModel().getColumn(0);
    TableColumn col2 = requestTable.getColumnModel().getColumn(1);
    TableColumn col3 = requestTable.getColumnModel().getColumn(2);
    TableColumn col4 = requestTable.getColumnModel().getColumn(3);
    TableColumn col5 = requestTable.getColumnModel().getColumn(4);
    TableColumn col6 = requestTable.getColumnModel().getColumn(5);
    TableColumn col7 = requestTable.getColumnModel().getColumn(6);
    TableColumn col8 = requestTable.getColumnModel().getColumn(7);
    TableColumn col9 = requestTable.getColumnModel().getColumn(8);
    TableColumn col10 = requestTable.getColumnModel().getColumn(9);

    JPanel requestBoxPanel = new JPanel();

    java.awt.Font fo = new java.awt.Font("Tahoma", 1, 11);

    java.awt.Toolkit tk = java.awt.Toolkit.getDefaultToolkit();
    java.awt.Dimension ss = tk.getScreenSize();

    class ColoredTableCellRenderer extends javax.swing.table.DefaultTableCellRenderer {
        /**
         * 
         */
        private static final long serialVersionUID = 1L;

        ColoredTableCellRenderer() {
        }

        public java.awt.Component getTableCellRendererComponent(JTable paramJTable, Object paramObject,
                boolean paramBoolean1, boolean paramBoolean2, int paramInt1, int paramInt2) {
            setEnabled((paramJTable == null) || (paramJTable.isEnabled()));

            if (paramInt1 % 2 == 0) {
                setBackground(Color.darkGray);
                setForeground(Color.white);

            } else {
                setBackground(Color.gray);
                setForeground(Color.white);
            }

            super.getTableCellRendererComponent(paramJTable, paramObject, paramBoolean1, paramBoolean2, paramInt1,
                    paramInt2);

            return this;
        }
    }

    class ColoredTableCellRenderer2 extends javax.swing.table.DefaultTableCellRenderer {
        /**
         * 
         */
        private static final long serialVersionUID = 1L;

        ColoredTableCellRenderer2() {
        }

        public java.awt.Component getTableCellRendererComponent(JTable paramJTable, Object paramObject,
                boolean paramBoolean1, boolean paramBoolean2, int paramInt1, int paramInt2) {
            setEnabled((paramJTable == null) || (paramJTable.isEnabled()));

            if (paramInt1 % 2 == 0) {
                setBackground(Color.darkGray);
                setForeground(Color.white);
            } else {
                setBackground(Color.gray);
                setForeground(Color.white);
            }

            String str1 = String.valueOf(TechMainFrame.requestTableModel.getValueAt(paramInt1, 3));
            String.valueOf(TechMainFrame.requestTableModel.getValueAt(paramInt1, 9));

            int i = Integer.parseInt(str1);

            if (i >= 3) {
                setBackground(Color.red);
            }

            super.getTableCellRendererComponent(paramJTable, paramObject, paramBoolean1, paramBoolean2, paramInt1,
                    paramInt2);

            return this;
        }
    }

    class ColoredTableCellRenderer3 extends javax.swing.table.DefaultTableCellRenderer {
        /**
         * 
         */
        private static final long serialVersionUID = 1L;

        ColoredTableCellRenderer3() {
        }

        public java.awt.Component getTableCellRendererComponent(JTable paramJTable, Object paramObject,
                boolean paramBoolean1, boolean paramBoolean2, int paramInt1, int paramInt2) {
            setEnabled((paramJTable == null) || (paramJTable.isEnabled()));

            if (paramInt1 % 2 == 0) {
                setBackground(Color.darkGray);
                setForeground(Color.white);
            } else {
                setBackground(Color.gray);
                setForeground(Color.white);
            }

            String str = String.valueOf(TechMainFrame.requestTableModel.getValueAt(paramInt1, 9));

            if (str.equalsIgnoreCase("0")) {
                setBackground(Color.red);
                setForeground(Color.white);
            }
            if (str.equalsIgnoreCase("Denied")) {
                setBackground(Color.orange);
                setForeground(Color.black);
            }

            super.getTableCellRendererComponent(paramJTable, paramObject, paramBoolean1, paramBoolean2, paramInt1,
                    paramInt2);

            return this;
        }
    }

    TechMainFrame.ColoredTableCellRenderer skr = new TechMainFrame.ColoredTableCellRenderer();
    TechMainFrame.ColoredTableCellRenderer2 skr1 = new TechMainFrame.ColoredTableCellRenderer2();
    TechMainFrame.ColoredTableCellRenderer3 skr2 = new TechMainFrame.ColoredTableCellRenderer3();
    Container mainContainer;
    Container requestBoxContainer;

    public void designFrame() {
        frameTech.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainContainer = frameTech.getContentPane();
        requestBoxContainer = inf.getContentPane();
        mainContainer.setLayout(new java.awt.BorderLayout());
        requestBoxContainer.setLayout(new java.awt.BorderLayout());
        statusPanel.setLayout(new java.awt.FlowLayout(0));
        requestBoxPanel.setLayout(new java.awt.BorderLayout());

        requestBoxPanel.add(splitPane, "Center");
        splitPane.setOneTouchExpandable(true);

        textAreaRequestDetails.setFont(fo);
        textAreaRequestDetails.setEditable(false);

        statusPanel.add(labelLoggedBy);
        statusPanel.add(textFieldLoggedBy);
        textFieldLoggedBy.setEditable(false);
        statusPanel.add(labelUserType);
        statusPanel.add(textFieldUserType);
        textFieldUserType.setEditable(false);
        statusPanel.add(labelDepartment);
        statusPanel.add(textFieldDepartment);
        textFieldDepartment.setEditable(false);
        statusPanel.add(labelPost);
        statusPanel.add(textFieldPost);
        textFieldPost.setEditable(false);

        statusPanel.add(labelLoginTime);
        statusPanel.add(textFieldLoginTime);
        textFieldLoginTime.setEditable(false);
        statusPanel.add(labelCompanyId);
        statusPanel.add(textFieldCompanyId);
        textFieldCompanyId.setEditable(false);
        statusPanel.add(labelBranchCode);
        statusPanel.add(textFieldBranchCode);
        textFieldBranchCode.setEditable(false);

        for (int i = 0; i < requestTable.getColumnCount(); i++) {
            requestTable.getColumnModel().getColumn(i).setCellRenderer(skr);
        }

        requestTable.getColumnModel().getColumn(3).setCellRenderer(skr1);
        requestTable.getColumnModel().getColumn(9).setCellRenderer(skr2);

        col1.setPreferredWidth(25);
        col2.setPreferredWidth(80);
        col3.setPreferredWidth(60);
        col4.setPreferredWidth(45);
        col5.setPreferredWidth(275);
        col6.setPreferredWidth(65);
        col7.setPreferredWidth(100);
        col8.setPreferredWidth(100);
        col9.setPreferredWidth(100);
        col10.setPreferredWidth(100);

        textFieldLoggedBy.setFont(fo);
        textFieldUserType.setFont(fo);
        textFieldDepartment.setFont(fo);
        textFieldPost.setFont(fo);
        stf5.setFont(fo);
        textFieldLoginTime.setFont(fo);
        textFieldCompanyId.setFont(fo);
        textFieldBranchCode.setFont(fo);

        requestTable.setFont(fo);

        requestTable.getTableHeader().setPreferredSize(new java.awt.Dimension(50, 20));
        requestTable.getTableHeader().setFont(fo);
        requestTable.setRowHeight(25);

        requestTable.setSelectionBackground(Color.black);
        requestTable.setSelectionForeground(Color.white);

        tool.add(b1);
        b1.setToolTipText("Manpower Planning");
        tool.add(b2);
        b2.setToolTipText("Work Allotment");

        tool.add(b4);
        b4.setToolTipText("Daily Time Sheet");

        mainContainer.add(tool, "North");

        mainContainer.add(statusPanel, "South");
        mainContainer.add(desktop, "Center");
        // desktop.setBackground(Color.darkGray);

        requestBoxContainer.add(requestBoxPanel, "Center");

        requestTable.addMouseListener(this);

        popm1.addActionListener(this);
        popm2.addActionListener(this);
        popm3.addActionListener(this);

        menuProjectMaster.add(menuItemProjectEstimation);
        menuItemProjectEstimation.addActionListener(this);
        menuProjectMaster.add(menuItemSetDrawingNumber);
        menuItemSetDrawingNumber.addActionListener(this);
        menuProjectMaster.addSeparator();
        menuProjectMaster.add(menuItemManpowerStatus);
        menuItemManpowerStatus.addActionListener(this);
        menuProjectMaster.add(menuItemAssignProjects);
        menuItemAssignProjects.addActionListener(this);
        menuProjectMaster.add(menuItemManpowerPlanning);
        menuItemManpowerPlanning.addActionListener(this);
        menuProjectMaster.addSeparator();
        menuProjectMaster.add(menuItemWorkAllotment);
        menuItemWorkAllotment.addActionListener(this);
        menuProjectMaster.addSeparator();

        menuProjectMaster.add(menuItemClose);
        menuItemClose.addActionListener(this);

        menuJobMaster.addSeparator();
        menuJobMaster.add(menuItemRFILog);
        menuItemRFILog.addActionListener(this);
        menuJobMaster.add(menuItemRFILogDetails);
        menuItemRFILog.addActionListener(this);

        menuTimeMaster.add(menuItemBFADrawing);
        menuItemBFADrawing.addActionListener(this);
        menuTimeMaster.addSeparator();

        menuTimeMaster.addSeparator();
        menuTimeMaster.add(menuItemDailyTimeSheet);
        menuItemDailyTimeSheet.addActionListener(this);

        menuTimeRequest.add(menuItemBFARevisionWastedTimeRequest);
        menuItemBFARevisionWastedTimeRequest.addActionListener(this);

        menuTimeRequest.add(menuItemWastedTimeRequest);
        menuItemWastedTimeRequest.addActionListener(this);
        menuTimeRequest.addSeparator();
        menuTimeRequest.add(menuItemBreakTimeRequest);
        menuItemBreakTimeRequest.addActionListener(this);

        menuReportSOL.setEnabled(true);
        menuReportClients.setEnabled(true);

        menuReportMaster.add(menuItemEmployeeAllotedJob);
        menuItemEmployeeAllotedJob.addActionListener(this);
        menuReportMaster.addSeparator();
        menuReportMaster.add(menuReportSOL);
        menuReportMaster.addSeparator();
        menuReportMaster.add(menuReportClients);
        menuReportMaster.addSeparator();

        menuReportMaster.add(menuItemProjectManagerEstimation);
        menuItemProjectManagerEstimation.addActionListener(this);
        menuReportMaster.add(menuItemTeamLeaderEstimation);
        menuItemTeamLeaderEstimation.addActionListener(this);
        menuReportMaster.addSeparator();
        menuReportMaster.add(menuItemProjectListAndStatus);
        menuItemProjectListAndStatus.addActionListener(this);
        menuReportMaster.add(menuItemQuotationList);
        menuItemQuotationList.addActionListener(this);
        menuReportMaster.add(menuItemTimeManagement);
        menuItemTimeManagement.addActionListener(this);

        menuReportSOL.add(menuItemEmployeePerformanceReport);
        menuItemEmployeePerformanceReport.addActionListener(this);
        menuReportSOL.addSeparator();
        menuReportSOL.add(menuItemProjectPerformanceReport);
        menuItemProjectPerformanceReport.addActionListener(this);
        menuReportSOL.addSeparator();
        menuReportSOL.add(menuItemTimeManagement);
        menuReportSOL.add(menuItemExportProjectReport);
        menuItemExportProjectReport.addActionListener(this);


        menuReportClients.add(menuItemExtraHrsReport);
        menuItemExtraHrsReport.addActionListener(this);

        menuDrawingLog.add(menuItemDrawingLog);
        menuItemDrawingLog.addActionListener(this);

        menuDrawingIndex.add(menuItemCreateDrawingIndex);
        menuItemCreateDrawingIndex.addActionListener(this);
        menuItemCreateDrawingIndex.setEnabled(false);
        menuDrawingIndex.add(menuItemShowIndexReport);
        menuItemShowIndexReport.addActionListener(this);
        menuItemShowIndexReport.setEnabled(false);
        menuRequestBox.add(menuItemOpenRequestBox);
        menuItemOpenRequestBox.addActionListener(this);

        menuRFILog.add(menuItemRFIQueryForm);
        menuItemRFIQueryForm.addActionListener(this);
        menuRFILog.add(menuItemRFIReport);
        menuItemRFIReport.addActionListener(this);

        menuRevisionLog.add(menuItemRevisionLogForm);
        menuItemRevisionLogForm.addActionListener(this);
        menuRevisionLog.add(menuItemRevisionLogReport);
        menuItemRevisionLogReport.addActionListener(this);

        menuProjectManager.add(menuItemCreateItemCode);
        menuItemCreateItemCode.addActionListener(this);
        menuProjectManager.add(menuItemProjectSheetDetails);
        menuItemProjectSheetDetails.addActionListener(this);
        menuProjectManager.addSeparator();
        menuProjectManager.add(menuDrawingLog);
        menuProjectManager.addSeparator();

        menuProjectManager.add(menuRequestBox);
        menuProjectManager.addSeparator();
        menuProjectManager.add(menuRFILog);
        menuProjectManager.addSeparator();
        menuProjectManager.add(menuRevisionLog);
        menuProjectManager.addSeparator();
        menuProjectManager.add(menuItemSolGsn);
        menuItemSolGsn.addActionListener(this);
        menuProjectManager.addSeparator();

        menuSolGroup.add(menuItemMailBox);
        menuItemMailBox.addActionListener(this);

        menuSolGroup.addSeparator();
        menuSolGroup.add(menuItemSaveEmployeesRecord);
        menuItemSaveEmployeesRecord.addActionListener(this);

        menuSolGroup.add(menuItemSystemAllotment);
        menuItemSystemAllotment.addActionListener(this);

        menuSolGroup.addSeparator();
        menuSolGroup.add(menuItemFinalEstimation);
        menuItemFinalEstimation.addActionListener(this);

        menuUtilities.add(menuItemChangePassword);
        menuItemChangePassword.addActionListener(this);

        menuUtilities.addSeparator();
        menuUtilities.add(menuItemEmployeeFactorsAndContact);
        menuItemEmployeeFactorsAndContact.addActionListener(this);
        menuUtilities.addSeparator();
        menuUtilities.add(menuItemLeaveApplicationChart);
        menuItemLeaveApplicationChart.addActionListener(this);
        menuUtilities.addSeparator();
        menuUtilities.add(menuItemRoundAndMarks);
        menuItemRoundAndMarks.addActionListener(this);

        b1.addActionListener(this);
        b2.addActionListener(this);
        b3.addActionListener(this);
        b4.addActionListener(this);
        b5.addActionListener(this);
        b6.addActionListener(this);
        b7.addActionListener(this);
        b8.addActionListener(this);

        desktop.addKeyListener(this);
        frameTech.addKeyListener(this);
        mainContainer.addKeyListener(this);

        jpm.add(popm1);
        jpm.add(popm2);
        jpm.addSeparator();
        jpm.add(popm3);

        menubar.add(menuProjectMaster);
        menuProjectMaster.setMnemonic(80);

        menubar.add(menuTimeMaster);
        menuTimeMaster.setMnemonic(84);
        menubar.add(menuReportMaster);
        menuReportMaster.setMnemonic(82);

        menubar.add(menuProjectManager);
        menuProjectManager.setMnemonic(77);
        menubar.add(menuSolGroup);
        menuSolGroup.setMnemonic(83);
        menubar.add(menuUtilities);
        menuUtilities.setMnemonic(67);

        splitPane.setDividerLocation(250);
        frameTech.setJMenuBar(menubar);

        frameTech.setSize(tk.getScreenSize());
        frameTech.setVisible(true);
    }

    public void paramUser() {
        textFieldLoggedBy.setText(SessionUtil.getLoginInfo().getId());
        textFieldUserType.setText(SessionUtil.getLoginInfo().getUserType());
        textFieldDepartment.setText(SessionUtil.getLoginInfo().getDept());
        textFieldPost.setText(SessionUtil.getLoginInfo().getPost());

        textFieldCompanyId.setText(SessionUtil.getLoginInfo().getCompanyId());
        textFieldBranchCode.setText(SessionUtil.getLoginInfo().getBranchCode());

        permissionCheck();
    }

    public void permissionCheck() {
        String str = String.valueOf(textFieldPost.getText());
        if ((textFieldPost.getText().equalsIgnoreCase("Team Leader")) || (str.equalsIgnoreCase("System Manager"))) {
            menuItemAssignProjects.setEnabled(false);
            menuItemManpowerPlanning.setEnabled(false);
            menuItemRoundAndMarks.setEnabled(false);
        }

        if ((textFieldPost.getText().equalsIgnoreCase("Project Manager")) && (str.equalsIgnoreCase("Admin"))) {
            menuItemRoundAndMarks.setEnabled(true);
        }

        if (textFieldUserType.getText().equalsIgnoreCase("User")) {
            menuItemProjectEstimation.setEnabled(false);
            menuItemSetDrawingNumber.setEnabled(false);

            menuItemAssignProjects.setEnabled(false);
            menuItemManpowerPlanning.setEnabled(false);
            menuItemWorkAllotment.setEnabled(false);

            menuItemDailyAttendance.setEnabled(false);
            menuItemOTStatus.setEnabled(false);

            menuItemProjectPerformanceReport.setEnabled(true);
            menuItemProjectCompletionReport.setEnabled(false);
            menuItemQuotationCompare.setEnabled(false);
            menuItemProjectManagerEstimation.setEnabled(false);
            menuItemTeamLeaderEstimation.setEnabled(false);

            menuItemExtraHrsReport.setEnabled(true);
            menuItemClientWiseReport.setEnabled(true);
            menuItemQuotationList.setEnabled(false);

            menuItemCreateItemCode.setEnabled(false);
            menuItemStructuralDrawingIndex.setEnabled(false);
            menuItemSetFactor.setEnabled(false);
            menuItemFactorReport.setEnabled(false);
            menuItemProjectSheetDetails.setEnabled(false);
            menuItemUpdateSheets.setEnabled(false);
            menuItemDrawingLog.setEnabled(true);
            menuItemReceiveApprovalData.setEnabled(false);
            menuItemCreateDrawingIndex.setEnabled(false);
            menuItemShowIndexReport.setEnabled(false);
            menuItemOpenRequestBox.setEnabled(false);
            menuItemRFIQueryForm.setEnabled(false);
            menuItemRFIReport.setEnabled(false);
            menuItemRevisionLogForm.setEnabled(false);
            menuItemRevisionLogReport.setEnabled(false);

            menuItemEmployeesWorkMonitoring.setEnabled(false);

            menuItemUnitDetails.setEnabled(false);
            menuItemEmployeeReportUnitWise.setEnabled(false);
            menuItemSaveEmployeesRecord.setEnabled(false);
            menuItemEmployeeCodeGeneration.setEnabled(false);
            menuItemSystemAllotment.setEnabled(false);
            menuItemFinalEstimation.setEnabled(false);

            menuItemUserAuthentication.setEnabled(false);
            menuItemUpdateAuthentication.setEnabled(false);
            menuItemRoundAndMarks.setEnabled(false);

            popm1.setEnabled(false);
            popm2.setEnabled(false);

            b1.setEnabled(false);
            b2.setEnabled(false);

            b5.setEnabled(false);
            b6.setEnabled(false);
            b7.setEnabled(false);
            b8.setEnabled(false);
        }
        if (str.equalsIgnoreCase("CEO")) {
            menuItemProjectEstimation.setEnabled(false);
            menuItemSetDrawingNumber.setEnabled(false);
            menuItemManpowerStatus.setEnabled(false);
            menuItemAssignProjects.setEnabled(false);
            menuItemManpowerPlanning.setEnabled(false);
            menuItemWorkAllotment.setEnabled(false);

            menuItemBFADrawing.setEnabled(false);
            menuItemBFATimeRequest.setEnabled(false);
            menuItemBFARevisionWastedTimeRequest.setEnabled(false);
            menuItemWastedTimeRequest.setEnabled(false);
            menuItemBreakTimeRequest.setEnabled(false);
            menuItemDailyTimeSheet.setEnabled(false);
            menuItemDailyTimeSheetReport.setEnabled(false);
            menuItemOTStatus.setEnabled(false);

            menuItemEmployeeEfficientyReport.setEnabled(false);

            menuItemCreateItemCode.setEnabled(false);
            menuItemStructuralDrawingIndex.setEnabled(false);
            menuItemSetFactor.setEnabled(false);
            menuItemFactorReport.setEnabled(false);
            menuItemProjectSheetDetails.setEnabled(false);
            menuItemUpdateSheets.setEnabled(false);
            menuItemReceiveApprovalData.setEnabled(false);
            menuItemCreateDrawingIndex.setEnabled(false);
            menuItemShowIndexReport.setEnabled(false);

            menuItemUnitDetails.setEnabled(false);
            menuItemEmployeeReportUnitWise.setEnabled(false);
            menuItemSaveEmployeesRecord.setEnabled(false);
            menuItemEmployeeCodeGeneration.setEnabled(false);
            menuItemSystemAllotment.setEnabled(false);
            menuItemFinalEstimation.setEnabled(false);

            menuItemUserAuthentication.setEnabled(false);
            menuItemUpdateAuthentication.setEnabled(false);
            menuItemEmployeeFactorsAndContact.setEnabled(true);
            menuItemRoundAndMarks.setEnabled(true);

            popm1.setEnabled(false);
            popm2.setEnabled(false);

            b1.setEnabled(false);
            b2.setEnabled(false);

            b5.setEnabled(false);
            b6.setEnabled(false);
            b7.setEnabled(false);
            b8.setEnabled(false);
        }
    }

    public void loginTime() {
        gcal = new java.util.GregorianCalendar();
        int i = gcal.get(10);
        int j = gcal.get(12);
        textFieldLoginTime.setText(String.valueOf(df1.format(i)) + String.valueOf(df1.format(j)));
    }

    public void showInf() {
        desktop.add(inf);
        desktop.moveToFront(inf);
        inf.setSize(960, 600);
        inf.setVisible(true);
        requestList();
    }

    public void requestList() {
        requestTableModel.setNumRows(0);
        int i = 0;
        String str1 = "";
        if ((textFieldPost.getText().equalsIgnoreCase("Detailer")) || (textFieldPost.getText().equalsIgnoreCase("Checker"))) {
            str1 = "select * from time_req where emp_code='" + textFieldLoggedBy.getText() + "' order by sl";
        } else {
            str1 = "select * from time_req order by REQ_TL ";
        }

        try {
            con = DBConnectionUtil.getConnection();
            stat = con.createStatement();
            rs = stat.executeQuery(str1);
            rs.getMetaData().getColumnCount();
            while (rs.next()) {
                i += 1;
                new String(rs.getString(1));
                String str3 = new String(rs.getString(2));
                String str4 = new String(rs.getString(3));
                String str5 = new String(rs.getString(4));
                String str6 = new String(rs.getString(5));
                new String(rs.getString(6));
                String str8 = new String(rs.getString(7));
                String str9 = new String(rs.getString(8));
                String str10 = new String(rs.getString(9));
                String str11 = new String(rs.getString(10));
                String str12 = new String(rs.getString(11));

                requestTableModel.addRow(
                        new Object[] { String.valueOf(i), str3, str4, str12, str5, str6, str8, str9, str10, str11 });
            }
        } catch (Exception localException) {
            System.out.println(localException);
        }
    }

    public void requestDetails() {
        int i = requestTable.getSelectedRow();
        String str1 = String.valueOf(requestTableModel.getValueAt(i, 8));
        String str2 = String.valueOf(requestTableModel.getValueAt(i, 1));
        String str3 = String.valueOf(requestTableModel.getValueAt(i, 2));
        String str4 = String.valueOf(requestTableModel.getValueAt(i, 3));

        if (str1.equalsIgnoreCase("Break")) {
            str1 = "break_time";
        }
        if (str1.equalsIgnoreCase("Wasted")) {
            str1 = "Wasted";
        }
        if (str1.equalsIgnoreCase("BFA")) {
            str1 = "BFA_TIME";
        }
        if (str1.equalsIgnoreCase("Revision")) {
            str1 = "Revision_TIME";
        }

        textAreaRequestDetails.setText("");
        try {
            con = DBConnectionUtil.getConnection();
            stat = con.createStatement();
            rs = stat.executeQuery("select * from " + str1 + " where emp_code='" + str3 + "' and date_month='" + str2
                    + "' and req_no='" + str4 + "' ");
            rs.getMetaData().getColumnCount();
            while (rs.next()) {
                String str5 = new String(rs.getString(1));
                String str6 = new String(rs.getString(2));
                String str7 = new String(rs.getString(3));
                String str8 = new String(rs.getString(4));
                String str9 = new String(rs.getString(5));
                new String(rs.getString(6));
                new String(rs.getString(7));

                textAreaRequestDetails.append("\n\t" + str5 + "\n");
                textAreaRequestDetails.append("\t" + str6 + "\n");
                textAreaRequestDetails.append("\tOut Time\t: " + str7 + "\n");
                textAreaRequestDetails.append("\tExp In\t: " + str8 + "\n");
                textAreaRequestDetails.append("\tReason\t:");
                textAreaRequestDetails.append("\n************************************************\n");
                textAreaRequestDetails.append("\n" + str9 + "\n");
            }
        } catch (Exception localException) {
            System.out.println(localException);
        }
    }

    public void updateDetails() {
        int i = requestTable.getSelectedRow();
        String str1 = String.valueOf(requestTableModel.getValueAt(i, 8));
        String str2 = String.valueOf(requestTableModel.getValueAt(i, 1));
        String str3 = String.valueOf(requestTableModel.getValueAt(i, 2));

        if (str1.equalsIgnoreCase("Break")) {
            str1 = "break_time";
        }
        if (str1.equalsIgnoreCase("Wasted")) {
            str1 = "Wasted";
        }
        if (str1.equalsIgnoreCase("BFA")) {
            str1 = "BFA_TIME";
        }
        if (str1.equalsIgnoreCase("Revision")) {
            str1 = "Revision_TIME";
        }

        try {
            con = DBConnectionUtil.getConnection();
            stat = con.createStatement();
            stat.executeUpdate("UPDATE " + str1 + " set Remarks='Approved' where emp_code='" + str3
                    + "' and date_month='" + str2 + "' ");
        } catch (Exception localException1) {
            System.out.println(localException1);
            requestTableModel.setValueAt("Approved", i, 9);
        }

        try {
            stat = con.createStatement();
            stat.executeUpdate("UPDATE time_req set Remarks='Approved' where emp_code='" + str3 + "' and date_month='"
                    + str2 + "' ");
        } catch (Exception localException2) {
            System.out.println(localException2);
        }
    }

    public void deniedDetails() {
        int i = requestTable.getSelectedRow();
        String str1 = String.valueOf(requestTableModel.getValueAt(i, 8));
        String str2 = String.valueOf(requestTableModel.getValueAt(i, 1));
        String str3 = String.valueOf(requestTableModel.getValueAt(i, 2));

        if (str1.equalsIgnoreCase("Break")) {
            str1 = "break_time";
        }
        if (str1.equalsIgnoreCase("Wasted")) {
            str1 = "Wasted";
        }
        if (str1.equalsIgnoreCase("BFA")) {
            str1 = "BFA_TIME";
        }
        if (str1.equalsIgnoreCase("Revision")) {
            str1 = "Revision_TIME";
        }

        try {
            con = DBConnectionUtil.getConnection();
            stat = con.createStatement();
            stat.executeUpdate("UPDATE " + str1 + " set Remarks='Denied' where emp_code='" + str3 + "' and date_month='"
                    + str2 + "' ");
        } catch (Exception localException1) {
            System.out.println(localException1);
            requestTableModel.setValueAt("Denied", i, 9);
        }

        try {
            stat = con.createStatement();
            stat.executeUpdate("UPDATE time_req set Remarks='Denied' where emp_code='" + str3 + "' and date_month='"
                    + str2 + "' ");
        } catch (Exception localException2) {
            System.out.println(localException2);
        }
    }

    public void actionPerformed(ActionEvent paramActionEvent) {
        // Object localObject;
        if (paramActionEvent.getSource() == menuItemProjectEstimation) {
            Estimation localObject = new Estimation();
            desktop.add(((Estimation) localObject).internalFrame);
            desktop.moveToFront(((Estimation) localObject).internalFrame);
            ((Estimation) localObject).paramUser();
            ((Estimation) localObject).px();
            ((Estimation) localObject).structIndex();
        }

        if (paramActionEvent.getSource() == menuItemSetDrawingNumber) {
            CreateDrawingNumbers localObject = new CreateDrawingNumbers();
            desktop.add(((CreateDrawingNumbers) localObject).internalFrame);
            desktop.moveToFront(((CreateDrawingNumbers) localObject).internalFrame);
            ((CreateDrawingNumbers) localObject).paramUser();
            ((CreateDrawingNumbers) localObject).designFrame();
        }
        if (paramActionEvent.getSource() == menuItemManpowerStatus) {
            ManpowerAvail manpowerAvail = new ManpowerAvail();
            desktop.add(manpowerAvail.internalFrame);
            desktop.moveToFront(manpowerAvail.internalFrame);
            manpowerAvail.px();
        }
        if (paramActionEvent.getSource() == menuItemAssignProjects) {
            ProjectCo localObject = new ProjectCo();
            desktop.add(((ProjectCo) localObject).f);
            desktop.moveToFront(((ProjectCo) localObject).f);
            ((ProjectCo) localObject).px();
        }
        if ((paramActionEvent.getSource() == menuItemManpowerPlanning) || (paramActionEvent.getSource() == b1)) {
            ProjectManPower localObject = new ProjectManPower();
            desktop.add(((ProjectManPower) localObject).f);
            desktop.moveToFront(((ProjectManPower) localObject).f);
            ((ProjectManPower) localObject).projectList();
            ((ProjectManPower) localObject).px();
        }
        if ((paramActionEvent.getSource() == menuItemWorkAllotment) || (paramActionEvent.getSource() == b2)) {
            WorkAllot localObject = new WorkAllot();
            desktop.add(((WorkAllot) localObject).internalFrame);
            desktop.moveToFront(((WorkAllot) localObject).internalFrame);
            ((WorkAllot) localObject).paramUser();
            ((WorkAllot) localObject).px();
        }
        if (paramActionEvent.getSource() == menuItemLogin) {
            frameTech.setVisible(false);
            Login localObject = new Login();
            ((Login) localObject).px();
        }
        if (paramActionEvent.getSource() == menuItemClose) {
            System.exit(0);
        }

        if (paramActionEvent.getSource() == menuItemDailyJobProcess) {
            JobProcess localObject = new JobProcess();
            ((JobProcess) localObject).px();
            ((JobProcess) localObject).paramUser();
        }
        if (paramActionEvent.getSource() == menuItemRFILog) {
        }
        if (paramActionEvent.getSource() == menuItemDailyAttendance) {
            Attendance attendance = new Attendance();
            desktop.add(attendance.frameMain);
            desktop.moveToFront(attendance.frameMain);
            attendance.designFrame();
        }

        if (paramActionEvent.getSource() == menuItemBFADrawing) {
            BFA localObject = new BFA();
            desktop.add(((BFA) localObject).f);
            desktop.moveToFront(((BFA) localObject).f);
            ((BFA) localObject).px();
            ((BFA) localObject).paramUser();
        }
        if (paramActionEvent.getSource() == menuItemBFATimeRequest) {
            BFATime localObject = new BFATime();
            ((BFATime) localObject).px();
            ((BFATime) localObject).paramUser();
            ((BFATime) localObject).TLCode();
        }
        if (paramActionEvent.getSource() == menuItemBFARevisionWastedTimeRequest) {
            RevisionTime localObject = new RevisionTime();
            desktop.add(((RevisionTime) localObject).f);
            desktop.moveToFront(((RevisionTime) localObject).f);
            ((RevisionTime) localObject).px();
            ((RevisionTime) localObject).paramUser();
            ((RevisionTime) localObject).maxReq();
        }
        ProjectCo localProjectCo;
        if (paramActionEvent.getSource() == menuItemWastedTimeRequest) {
            WastedTime localObject = new WastedTime();
            localProjectCo = new ProjectCo();
            localProjectCo.TLCode();
            ((WastedTime) localObject).px();
            ((WastedTime) localObject).paramUser();
        }
        if (paramActionEvent.getSource() == menuItemBreakTimeRequest) {
            BreakTime localObject = new BreakTime();
            localProjectCo = new ProjectCo();
            desktop.add(((BreakTime) localObject).f);
            desktop.moveToFront(((BreakTime) localObject).f);
            ((BreakTime) localObject).px();
            ((BreakTime) localObject).paramUser();
            localProjectCo.TLCode();
        }

        if ((paramActionEvent.getSource() == menuItemDailyTimeSheet) || (paramActionEvent.getSource() == b4)) {
            DailyTimeSheet localObject = new DailyTimeSheet();
            desktop.add(((DailyTimeSheet) localObject).f);
            desktop.moveToFront(((DailyTimeSheet) localObject).f);
            ((DailyTimeSheet) localObject).px();
            ((DailyTimeSheet) localObject).paramUser();
            ((DailyTimeSheet) localObject).wasted();
            ((DailyTimeSheet) localObject).showUnderProcess();
        }

        if (paramActionEvent.getSource() == menuItemDailyTimeSheetReport) {
            TimeSheet localObject = new TimeSheet();
            ((TimeSheet) localObject).px();
        }
        if (paramActionEvent.getSource() == menuItemOTStatus) {
            OTStatus localObject = new OTStatus();
            desktop.add(((OTStatus) localObject).f);
            desktop.moveToFront(((OTStatus) localObject).f);
            ((OTStatus) localObject).px();
            ((OTStatus) localObject).paramUser();
            ((OTStatus) localObject).desig();
            ((OTStatus) localObject).checkDesig();
        }

        if (paramActionEvent.getSource() == menuItemEmployeeEfficientyReport) {
            EmpMonthlyReport localObject = new EmpMonthlyReport();
            ((EmpMonthlyReport) localObject).px();
        }
        if (paramActionEvent.getSource() == menuItemEmployeePerformanceReport) {
            EmpPerform2 localObject = new EmpPerform2();
            ((EmpPerform2) localObject).px();
        }

        if (paramActionEvent.getSource() == menuItemEmployeeAllotedJob) {
            EmpWorkAlloted empWorkAlloted = new EmpWorkAlloted();
            desktop.add(empWorkAlloted.f);
            desktop.moveToFront(empWorkAlloted.f);
            empWorkAlloted.px("true");
        }

        if (paramActionEvent.getSource() == menuItemProjectPerformanceReport) {
            ProjectSummary projectSummary = new ProjectSummary();
            desktop.add(projectSummary.internalFrame);
            desktop.moveToFront(projectSummary.internalFrame);
            projectSummary.designFrame();
        }

        if (paramActionEvent.getSource() == menuItemProjectCompletionReport) {
            CurrentReport localObject = new CurrentReport();
            ((CurrentReport) localObject).px();
        }

        if (paramActionEvent.getSource() == menuItemQuotationCompare) {
            CompareReport localObject = new CompareReport();
            ((CompareReport) localObject).px();
        }

        if (paramActionEvent.getSource() == menuItemProjectManagerEstimation) {
            MPEstimationReport localObject = new MPEstimationReport();
            desktop.add(((MPEstimationReport) localObject).f);
            desktop.moveToFront(((MPEstimationReport) localObject).f);
            ((MPEstimationReport) localObject).projectNo();
            ((MPEstimationReport) localObject).px();
        }
        if (paramActionEvent.getSource() == menuItemTeamLeaderEstimation) {
            EstimationReport localObject = new EstimationReport();
            desktop.add(((EstimationReport) localObject).f);
            desktop.moveToFront(((EstimationReport) localObject).f);
            ((EstimationReport) localObject).projectNo();
            ((EstimationReport) localObject).px();
        }
        if (paramActionEvent.getSource() == menuItemProjectListAndStatus) {
            ProjectList localObject = new ProjectList();
            ((ProjectList) localObject).px();
            desktop.add(((ProjectList) localObject).f);
            desktop.moveToFront(((ProjectList) localObject).f);
            ((ProjectList) localObject).TLCode();
            ((ProjectList) localObject).clientList();
            ((ProjectList) localObject).paramUser();
        }

        if (paramActionEvent.getSource() == menuItemExtraHrsReport) {
            ProjectStatus localObject = new ProjectStatus();
            ((ProjectStatus) localObject).px();
            desktop.add(((ProjectStatus) localObject).f);
            desktop.moveToFront(((ProjectStatus) localObject).f);
        }
        if (paramActionEvent.getSource() == menuItemClientWiseReport) {
            ClientStatus localObject = new ClientStatus();
            ((ClientStatus) localObject).px();
        }
        if (paramActionEvent.getSource() == menuItemQuotationList) {
            QuotationList localObject = new QuotationList();
            desktop.add(((QuotationList) localObject).internalFrame);
            desktop.moveToFront(((QuotationList) localObject).internalFrame);
            ((QuotationList) localObject).designScreen();
        }

        if (paramActionEvent.getSource() == menuItemTimeManagement) {
            ExtraTime localObject = new ExtraTime();
            ((ExtraTime) localObject).showForm();
        }

        if(paramActionEvent.getSource() == menuItemExportProjectReport){
            ExportProjectReport exportProjectReport = new ExportProjectReport();
            exportProjectReport.showForm();
        }

        if (paramActionEvent.getSource() == menuItemCreateItemCode) {
            ItemCode localObject = new ItemCode(null);
            desktop.add(((ItemCode) localObject).f);
            desktop.moveToFront(((ItemCode) localObject).f);
            ((ItemCode) localObject).designFrame();
        }
        if (paramActionEvent.getSource() == menuItemSetFactor) {
            SetFactor localObject = new SetFactor();
            ((SetFactor) localObject).itemList();
            ((SetFactor) localObject).px();
        }
        if (paramActionEvent.getSource() == menuItemFactorReport) {
            EmpFactorReport localObject = new EmpFactorReport();
            ((EmpFactorReport) localObject).px();
        }
        if (paramActionEvent.getSource() == menuItemProjectSheetDetails) {
            SheetReport localObject = new SheetReport();
            desktop.add(((SheetReport) localObject).f);
            desktop.moveToFront(((SheetReport) localObject).f);
            ((SheetReport) localObject).projectNo();
            ((SheetReport) localObject).px();
        }
        if (paramActionEvent.getSource() == menuItemDrawingLog) {
            SendApprove localObject = new SendApprove();
            desktop.add(((SendApprove) localObject).f);
            desktop.moveToFront(((SendApprove) localObject).f);
            ((SendApprove) localObject).px();
            ((SendApprove) localObject).projectList();
            ((SendApprove) localObject).paramUser();
        }
        if (paramActionEvent.getSource() == menuItemReceiveApprovalData) {
            ReceiveApprove localObject = new ReceiveApprove();
            ((ReceiveApprove) localObject).px();
        }
        if (paramActionEvent.getSource() == menuItemCreateDrawingIndex) {
            DrgIndex localObject = new DrgIndex();
            ((DrgIndex) localObject).px();
        }
        if (paramActionEvent.getSource() == menuItemShowIndexReport) {
            DrgIndex localObject = new DrgIndex();
            ((DrgIndex) localObject).px();
        }

        if (paramActionEvent.getSource() == menuItemOpenRequestBox) {
            showInf();
        }
        if (paramActionEvent.getSource() == menuItemCloseRequestBox) {
            inf.setVisible(false);
        }
        if (paramActionEvent.getSource() == menuItemRFIQueryForm) {
            RFILog localObject = new RFILog();
            desktop.add(((RFILog) localObject).f);
            desktop.moveToFront(((RFILog) localObject).f);
            ((RFILog) localObject).projectNo();
            ((RFILog) localObject).px();
        }
        if (paramActionEvent.getSource() == menuItemRFIReport) {
            RFILOGReport localObject = new RFILOGReport();
            desktop.add(((RFILOGReport) localObject).f);
            desktop.moveToFront(((RFILOGReport) localObject).f);
            ((RFILOGReport) localObject).px();
        }
        if (paramActionEvent.getSource() == menuItemRevisionLogForm) {
            RevLog localObject = new RevLog();
            desktop.add(((RevLog) localObject).f);
            desktop.moveToFront(((RevLog) localObject).f);
            ((RevLog) localObject).projectNo();
            ((RevLog) localObject).px();
        }
        if (paramActionEvent.getSource() == menuItemRevisionLogReport) {
            RevLOGReport localObject = new RevLOGReport();
            desktop.add(((RevLOGReport) localObject).f);
            desktop.moveToFront(((RevLOGReport) localObject).f);
            ((RevLOGReport) localObject).px();
            ((RevLOGReport) localObject).revList();
        }
        if (paramActionEvent.getSource() == menuItemSolGsn) {
            gsn localObject = new gsn();
            desktop.add(((gsn) localObject).f);
            desktop.moveToFront(((gsn) localObject).f);
            ((gsn) localObject).px();
            ((gsn) localObject).projectNo();
            ((gsn) localObject).paramUser();
        }
        if (paramActionEvent.getSource() == menuItemEmployeesWorkMonitoring) {
            WorkReport localObject = new WorkReport();
            desktop.add(((WorkReport) localObject).f);
            desktop.moveToFront(((WorkReport) localObject).f);
            ((WorkReport) localObject).px();
            ((WorkReport) localObject).paramUser();
            ((WorkReport) localObject).empList();
        }

        if (paramActionEvent.getSource() == menuItemEmployeeCodeGeneration) {
            EmpRegister localObject = new EmpRegister();
            desktop.add(((EmpRegister) localObject).f);
            desktop.moveToFront(((EmpRegister) localObject).f);
            ((EmpRegister) localObject).DesignFrame();
        }

        if (paramActionEvent.getSource() == menuItemFinalEstimation) {
            Estimation3 localObject = new Estimation3();
            desktop.add(((Estimation3) localObject).f);
            desktop.moveToFront(((Estimation3) localObject).f);
            ((Estimation3) localObject).px();
        }

        if (paramActionEvent.getSource() == menuItemUserAuthentication) {
            UserAuth localObject = new UserAuth();
            desktop.add(((UserAuth) localObject).f);
            desktop.moveToFront(((UserAuth) localObject).f);
            ((UserAuth) localObject).px();
        }
        if (paramActionEvent.getSource() == menuItemChangePassword) {
            ChangePassword localObject = new ChangePassword();
            desktop.add(((ChangePassword) localObject).f);
            desktop.moveToFront(((ChangePassword) localObject).f);
            ((ChangePassword) localObject).px();
            ((ChangePassword) localObject).setUserID(textFieldLoggedBy.getText());
        }
        if (paramActionEvent.getSource() == menuItemPhoneDiary) {
            Phone1 localObject = new Phone1();
            desktop.add(((Phone1) localObject).f);
            desktop.moveToFront(((Phone1) localObject).f);
            ((Phone1) localObject).px();
            ((Phone1) localObject).paramUser(textFieldLoggedBy.getText(), textFieldUserType.getText());
        }
        if (paramActionEvent.getSource() == menuItemEmployeeFactorsAndContact) {
            EmpList localObject = new EmpList();
            ((EmpList) localObject).showEmpList(textFieldLoggedBy.getText());
        }
        if (paramActionEvent.getSource() == menuItemLeaveApplicationChart) {

            LeaveApplicationChart localObject = new LeaveApplicationChart();
            ((LeaveApplicationChart) localObject).showLeaveApplicationChart();
        }

        if (paramActionEvent.getSource() == menuItemRoundAndMarks) {
            Rounds localObject = new Rounds();
            ((Rounds) localObject).px(textFieldLoggedBy.getText());
        }
        if (paramActionEvent.getSource() == b3) {
            showInf();
        }
        if (paramActionEvent.getSource() == popm1) {
            updateDetails();
        }
        if (paramActionEvent.getSource() == popm2) {
            deniedDetails();
        }
        if (paramActionEvent.getSource() == popm3) {
            mainContainer.setBackground(new Color(60, 130, 130));
            mainContainer.remove(requestBoxPanel);
            mainContainer.validate();
            mainContainer.setBackground(Color.darkGray);
        }
    }

    public void mousePressed(MouseEvent paramMouseEvent) {
    }

    public void mouseReleased(MouseEvent paramMouseEvent) {
    }

    public void mouseEntered(MouseEvent paramMouseEvent) {
    }

    public void mouseExited(MouseEvent paramMouseEvent) {
    }

    public void mouseClicked(MouseEvent paramMouseEvent) {
        if (paramMouseEvent.getSource() == requestTable) {
            if (javax.swing.SwingUtilities.isRightMouseButton(paramMouseEvent)) {
                System.out.println("Right button released.");

                jpm.show((javax.swing.JComponent) paramMouseEvent.getSource(), paramMouseEvent.getX(),
                        paramMouseEvent.getY());
            }
            requestDetails();
        }
    }

    public void keyPressed(java.awt.event.KeyEvent paramKeyEvent) {
        int i = paramKeyEvent.getKeyCode();
        System.out.println(i);
        if (i == 112) {

            System.out.println("F1 Key Pressed.");
        }
    }

    public void keyReleased(java.awt.event.KeyEvent paramKeyEvent) {
    }

    public void keyTyped(java.awt.event.KeyEvent paramKeyEvent) {
    }

}
