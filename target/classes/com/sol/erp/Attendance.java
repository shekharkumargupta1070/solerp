package com.sol.erp;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.print.PrinterException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.filechooser.FileSystemView;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import com.sol.erp.constants.ApplicationConstants;
import com.sol.erp.dao.AttendanceDAO;
import com.sol.erp.dto.AttendanceDTO;
import com.sol.erp.dto.ShiftDefinitionDTO;
import com.sol.erp.ui.custom.SolCalendar3;
import com.sol.erp.ui.custom.SolTableFinder;
import com.sol.erp.ui.custom.SolTableModel;
import com.sol.erp.util.DBConnectionUtil;
import com.sol.erp.util.DateCalculationUtil;
import com.sol.erp.util.DateDifferencesUtil;
import com.sol.erp.util.SaviorConnectionUtil;
import com.sol.erp.util.SessionUtil;
import com.sol.erp.util.TimeUtil;
import com.sol.erp.util.UIUtil;
import com.sol.erp.util.formater.SolDateFormatter;

public class Attendance implements ActionListener, KeyListener, TableModelListener, MouseListener, FocusListener {

	private String[] attendanceColumns = { "Date", "Punchcard", "Emp Code", "Name", "In Time", "Out Time", "Total",
			"Project No", "D/C", "Upd", "Break", "OT Hrs", "Y/N", "Rt.", "T", "D.OT", "Y/N", "A", "Break UPD", "OT2" };

	private String[] companyColumns = { "CO Id", "BR Code", "Shift", "In Time", "Out Time", "Total", "Break In",
			"Break Out", "BREAK", "Late Count", "OT Not Before", "Min. OT", "Abs. Count" };

	private Object[][] attendanceData = new Object[0][];
	private Object[][] companyData = new Object[0][];

	BreakTimeEntry localBreakTimeEntry = new BreakTimeEntry();
	private Map<String, String> breakTimeMap = null;

	JPanel criteriaPanel;
	JPanel attendancePanel;

	JFrame frameMain;
	JComboBox comboBoxProject;
	DefaultTableModel defaultTableModelMain;
	JTable tableMain;
	JScrollPane scrollPaneMainTable;
	DefaultTableModel tableModelShiftDefinition;
	JTable tableTimeDefinition;
	JScrollPane scrollPaneTimeDefinitionTable;
	JSplitPane splitPanel;
	JLabel labelCompanyId;
	JLabel labelCompanyName;
	JLabel labelBranchCode;
	JLabel labelLate;
	JTextField textFieldCompanyId;
	JTextField textFieldCompanyName;
	JTextField textFieldBranchCode;
	JLabel labelDate;
	JLabel labelName;
	JLabel labelClient;
	JLabel labelTeamLead;
	JLabel labelDBStatus;
	JLabel labelSearch;
	JLabel labelNameOfDay;
	SolCalendar3 textFieldCalendar;
	JTextField tf2;
	JTextField tf3;
	JTextField tf4;
	JTextField tf6;
	JButton backbut;
	JButton forebut;
	JButton buttonShowFromMachine;
	JButton buttonSave;
	JButton buttonUpdate;
	JButton buttonClear;
	JButton buttonMount;
	JButton buttonClose;
	JButton buttonPrint;
	JRadioButton radioButtonProductionEmployee;
	JRadioButton radioButtonTrainee;
	JRadioButton radioButtonBothEmployee;
	ButtonGroup buttonGroup;
	JMenuBar menuBar;
	JMenu menuFile;
	JMenu menuEdit;
	JMenu menuView;
	JMenu menuSmartKey;
	JMenuItem miShowFromMachine;
	JMenuItem miSaveReport;
	JMenuItem miCleanThisDate;
	JMenuItem miPrintReport;
	JMenuItem miExportReport;
	JMenuItem miExit;
	JMenuItem miBreakTimeEntry;
	JMenuItem miMultiProjectEntry;
	JMenuItem miReportOfDay;
	JMenuItem miReportOfMonth;
	JMenuItem miTimeCalculator;
	JMenuItem miFindOption;
	JMenuItem miRecalculate;
	JPanel panelMain;
	JPanel panelSearch;
	JPanel panelTop;
	JPanel panelCenter;
	JPanel panelBottom;
	Font font;
	Border borderLine;
	Border borderLineDate;
	Border borderLineAttendanceDetails;
	Border borderLower;

	JProgressBar progress;

	CustomCellRenderer customeCellRenderer;
	int dateX;
	java.sql.Date lastdate;
	ArrayList ar;

	class CustomCellRenderer extends DefaultTableCellRenderer {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		CustomCellRenderer() {
		}

		public Component getTableCellRendererComponent(JTable paramJTable, Object paramObject, boolean paramBoolean1,
				boolean paramBoolean2, int paramInt1, int paramInt2) {
			setEnabled((paramJTable == null) || (paramJTable.isEnabled()));

			if (paramInt1 % 2 == 0) {
				setBackground(new Color(240, 240, 240));
				setForeground(Color.darkGray);
			}
			if (paramInt1 % 2 == 1) {
				setBackground(new Color(230, 230, 230));
				setForeground(Color.darkGray);
			}

			String str1 = String.valueOf(defaultTableModelMain.getValueAt(paramInt1, 4));
			String.valueOf(defaultTableModelMain.getValueAt(paramInt1, 5));

			if (paramInt2 == 6) {

				if (str1.length() <= 2) {
					setBackground(new Color(160, 120, 200));
					setForeground(Color.white);
				}
				if ((str1.length() <= 2) || (str1.length() >= 6) || (str1.equalsIgnoreCase("null"))) {
				}
			}

			float f1;

			if ((paramInt2 == 9) && (paramObject != null)) {

				f1 = Float.parseFloat(String.valueOf(defaultTableModelMain.getValueAt(paramInt1, 10)));

				if (f1 > 0.0F) {
					setBackground(new Color(60, 60, 190));
					setForeground(Color.white);
				}
			}

			if (paramInt2 == 10) {
				f1 = Float.parseFloat(String.valueOf(defaultTableModelMain.getValueAt(paramInt1, 11)));

				if (f1 > 0.0F) {
					setBackground(new Color(60, 190, 60));
					setForeground(Color.white);
				}
				if (f1 > 0.0F) {
				}
			}

			String str3 = String.valueOf(defaultTableModelMain.getValueAt(paramInt1, 4));
			float f3;
			if ((str3.length() > 2) && (str3.length() < 6)) {
				float f2 = Float.parseFloat(String.valueOf(defaultTableModelMain.getValueAt(paramInt1, 4)));
				f3 = Float.parseFloat(String.valueOf(tableModelShiftDefinition.getValueAt(0, 3)));

				float f4 = Float.parseFloat(String.valueOf(defaultTableModelMain.getValueAt(paramInt1, 5)));
				float f5 = Float.parseFloat(String.valueOf(tableModelShiftDefinition.getValueAt(0, 4)));

				if (paramInt2 == 4) {
					if (f2 - 0.1D > f3) {
						setBackground(new Color(255, 240, 240));
						setForeground(Color.red);
					}
				}

				if (paramInt2 == 5) {
					if (f4 < f5) {
						setBackground(new Color(240, 240, 255));
						setForeground(Color.blue);
					}
				}
			}

			if (paramInt2 == 7) {
				String str4 = String.valueOf(defaultTableModelMain.getValueAt(paramInt1, 9));
				if (str4.equalsIgnoreCase("1")) {
					setBackground(new Color(60, 130, 60));
					setForeground(Color.white);
				}
				if (str4.equalsIgnoreCase("2")) {
					setBackground(Color.red);
					setForeground(Color.white);
				}
			}

			if (paramInt2 == 2) {
				String str4 = String.valueOf(defaultTableModelMain.getValueAt(paramInt1, 9));
				if (str4.equalsIgnoreCase("5")) {
					setBackground(Color.black);
					setForeground(Color.white);
				}
			}

			String.valueOf(defaultTableModelMain.getValueAt(paramInt1, 14));
			if (paramInt2 == 14) {
				f3 = Float.parseFloat(String.valueOf(defaultTableModelMain.getValueAt(paramInt1, 15)));

				if (f3 > 1.0F) {
					setBackground(new Color(220, 60, 60));
					setForeground(Color.white);
				}
				if ((f3 > 0.0F) && (f3 <= 1.0F)) {
					setBackground(new Color(120, 220, 160));
					setForeground(Color.white);
				}
			}

			if (paramInt2 == 17) {

				f3 = Float.parseFloat(String.valueOf(defaultTableModelMain.getValueAt(paramInt1, 19)));
				if (f3 > 0.0F) {
					setBackground(new Color(245, 255, 245));
					setForeground(new Color(60, 160, 60));
				}
				if (f3 > 1.0F) {
					setBackground(new Color(230, 255, 230));
					setForeground(new Color(60, 160, 60));
				}
				if (f3 > 10.0F) {
					setBackground(new Color(60, 160, 60));
					setForeground(Color.white);
				}
			}

			if (paramInt2 == 3) {
				setHorizontalAlignment(2);
			} else {
				setHorizontalAlignment(0);
			}

			super.getTableCellRendererComponent(paramJTable, paramObject, paramBoolean1, paramBoolean2, paramInt1,
					paramInt2);

			return this;
		}
	}

	public JPanel designFrame() {
		criteriaPanel = new JPanel();
		attendancePanel = new JPanel();

		frameMain = new JFrame("Daily Attendance");

		comboBoxProject = new JComboBox();

		defaultTableModelMain = new DefaultTableModel(attendanceData, attendanceColumns);
		tableMain = new JTable(defaultTableModelMain) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public Class<? extends Object> getColumnClass(int paramAnonymousInt) {
				return getValueAt(0, paramAnonymousInt).getClass();
			}

			public boolean isCellEditable(int paramAnonymousInt1, int paramAnonymousInt2) {
				int i = convertColumnIndexToModel(paramAnonymousInt2);
				return i > 3;
			}

		};
		scrollPaneMainTable = new JScrollPane(tableMain);

		tableModelShiftDefinition = new DefaultTableModel(companyData, companyColumns);
		tableTimeDefinition = new JTable(tableModelShiftDefinition) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int paramAnonymousInt1, int paramAnonymousInt2) {
				int i = convertColumnIndexToModel(paramAnonymousInt2);
				return i < 0;
			}
		};
		scrollPaneTimeDefinitionTable = new JScrollPane(tableTimeDefinition);

		splitPanel = new JSplitPane(0, attendancePanel, scrollPaneTimeDefinitionTable);

		labelCompanyId = new JLabel("CO Id");
		labelCompanyName = new JLabel("CO Name");
		labelBranchCode = new JLabel("Branch Code");
		labelLate = new JLabel("");
		textFieldCompanyId = new JTextField(4);
		textFieldCompanyName = new JTextField(16);
		textFieldBranchCode = new JTextField(4);

		labelDate = new JLabel("Date");
		labelName = new JLabel("Name");
		labelClient = new JLabel("Client");
		labelTeamLead = new JLabel("TL");
		labelDBStatus = new JLabel("DB Status :");
		labelSearch = new JLabel("Search : ");
		labelNameOfDay = new JLabel("Name of Day");

		textFieldCalendar = new SolCalendar3();
		tf2 = new JTextField(12);
		tf3 = new JTextField(12);
		tf4 = new JTextField(4);
		tf6 = new JTextField(16);

		backbut = new JButton("<<");
		forebut = new JButton(">>");

		buttonShowFromMachine = new JButton("Show From Machine");
		buttonSave = new JButton("Save");
		buttonUpdate = new JButton("Updated View");
		buttonClear = new JButton("Clear Todays Attendance");
		buttonMount = new JButton("Mount DataBase");
		buttonClose = new JButton("Close",
				new ImageIcon(ClassLoader.getSystemResource(ApplicationConstants.IMAGE_PATH + "close.gif")));
		buttonPrint = new JButton("Print Report");

		radioButtonProductionEmployee = new JRadioButton("Production Employee");
		radioButtonTrainee = new JRadioButton("Trainee");
		radioButtonBothEmployee = new JRadioButton("Production+Trainee");

		buttonGroup = new ButtonGroup();

		menuBar = new JMenuBar();
		menuFile = new JMenu("File");
		menuEdit = new JMenu("Edit");
		menuView = new JMenu("View");
		menuSmartKey = new JMenu("Smart Key");

		miShowFromMachine = new JMenuItem("Show From Machine");
		miSaveReport = new JMenuItem("Save Report");
		miCleanThisDate = new JMenuItem("Clean This Date");
		miPrintReport = new JMenuItem("Print Report");
		miExportReport = new JMenuItem("Export Report");
		miExit = new JMenuItem("Exit");

		miBreakTimeEntry = new JMenuItem("Break Time Entry");
		miMultiProjectEntry = new JMenuItem("Multi Project Entry");

		miReportOfDay = new JMenuItem("Report of a Day");
		miReportOfMonth = new JMenuItem("Report of a Month");

		miTimeCalculator = new JMenuItem("Time Calculator");
		miFindOption = new JMenuItem("Find Option");
		miRecalculate = new JMenuItem("Recalculate");

		panelMain = new JPanel();
		panelSearch = new JPanel();
		panelTop = new JPanel();
		panelCenter = new JPanel();
		panelBottom = new JPanel();

		font = new Font("Verdana", 0, 10);
		borderLine = BorderFactory.createLineBorder(Color.darkGray);
		borderLineDate = BorderFactory.createTitledBorder(borderLine, "Date", 0, 0, font, Color.darkGray);
		borderLineAttendanceDetails = BorderFactory.createTitledBorder(borderLine, "Attendance Details", 0, 0, font,
				Color.darkGray);
		borderLower = BorderFactory.createBevelBorder(1);

		progress = new JProgressBar(0, 100);

		customeCellRenderer = new CustomCellRenderer();

		dateX = 0;

		lastdate = null;

		ar = new ArrayList();

		Container localContainer = frameMain.getContentPane();
		localContainer.setLayout(new BorderLayout());
		panelMain.setLayout(new BorderLayout());
		criteriaPanel.setLayout(new FlowLayout(0));
		attendancePanel.setLayout(new BorderLayout());
		panelTop.setLayout(new BorderLayout());

		panelCenter.setLayout(new BorderLayout());
		panelBottom.setLayout(new FlowLayout(0));
		panelSearch.setLayout(new FlowLayout(0));

		radioButtonProductionEmployee.setBackground(new Color(220, 255, 220));
		radioButtonTrainee.setBackground(new Color(220, 255, 220));
		radioButtonBothEmployee.setBackground(new Color(220, 255, 220));

		buttonGroup.add(radioButtonProductionEmployee);
		buttonGroup.add(radioButtonTrainee);
		buttonGroup.add(radioButtonBothEmployee);

		panelSearch.add(radioButtonProductionEmployee);
		panelSearch.add(radioButtonTrainee);
		panelSearch.add(radioButtonBothEmployee);
		panelSearch.add(labelSearch);
		panelSearch.add(tf6);
		tf6.setFont(font);
		panelSearch.add(labelLate);

		menuFile.add(miShowFromMachine);
		menuFile.add(miSaveReport);
		menuFile.addSeparator();
		menuFile.add(miCleanThisDate);
		menuFile.add(miPrintReport);
		menuFile.addSeparator();
		menuFile.add(miExportReport);
		menuFile.addSeparator();
		menuFile.add(miExit);

		menuEdit.add(miBreakTimeEntry);
		menuEdit.addSeparator();
		menuEdit.add(miMultiProjectEntry);

		menuView.add(miReportOfDay);
		menuView.addSeparator();
		menuView.add(miReportOfMonth);

		menuSmartKey.add(miTimeCalculator);
		menuSmartKey.addSeparator();
		menuSmartKey.add(miFindOption);
		menuSmartKey.addSeparator();
		menuSmartKey.add(miRecalculate);

		menuBar.add(menuFile);
		menuBar.add(menuEdit);
		menuBar.add(menuView);
		menuBar.add(menuSmartKey);

		frameMain.setJMenuBar(menuBar);

		attendancePanel.add(scrollPaneMainTable, "Center");
		attendancePanel.add(panelSearch, "South");

		scrollPaneTimeDefinitionTable.setPreferredSize(new Dimension(100, 100));

		progress.setPreferredSize(new Dimension(600, 20));
		progress.setStringPainted(true);
		progress.setForeground(new Color(60, 60, 140));

		panelTop.setBorder(borderLineDate);
		panelBottom.setBorder(borderLower);
		scrollPaneMainTable.setBorder(borderLineAttendanceDetails);

		textFieldCompanyId.setText(SessionUtil.getLoginInfo().getCompanyId());
		textFieldBranchCode.setText(SessionUtil.getLoginInfo().getBranchCode());

		SolTableModel.decorateTable(tableMain);
		tableMain.setBackground(new Color(220, 220, 220));
		tableMain.setSelectionBackground(new Color(170, 200, 170));
		tableMain.setSelectionForeground(Color.white);
		tableMain.setAutoscrolls(false);
		tableMain.setFont(font);
		tableMain.setIntercellSpacing(new Dimension(1, 1));
		tableMain.setGridColor(Color.white);
		tableMain.getColumnModel().getColumn(0).setPreferredWidth(100);
		tableMain.getColumnModel().getColumn(3).setPreferredWidth(190);
		tableMain.getColumnModel().getColumn(7).setPreferredWidth(120);
		tableMain.getColumnModel().getColumn(8).setPreferredWidth(45);
		tableMain.getColumnModel().getColumn(12).setPreferredWidth(35);
		tableMain.getColumnModel().getColumn(13).setPreferredWidth(30);
		tableMain.getColumnModel().getColumn(14).setPreferredWidth(30);
		tableMain.getColumnModel().getColumn(16).setPreferredWidth(35);
		tableMain.getColumnModel().getColumn(17).setPreferredWidth(35);

		tableMain.getColumnModel().getColumn(2).setCellRenderer(customeCellRenderer);
		tableMain.getColumnModel().getColumn(4).setCellRenderer(customeCellRenderer);
		tableMain.getColumnModel().getColumn(5).setCellRenderer(customeCellRenderer);
		tableMain.getColumnModel().getColumn(6).setCellRenderer(customeCellRenderer);
		tableMain.getColumnModel().getColumn(7).setCellRenderer(customeCellRenderer);
		tableMain.getColumnModel().getColumn(9).setCellRenderer(customeCellRenderer);
		tableMain.getColumnModel().getColumn(10).setCellRenderer(customeCellRenderer);
		tableMain.getColumnModel().getColumn(11).setCellRenderer(customeCellRenderer);
		tableMain.getColumnModel().getColumn(15).setCellRenderer(customeCellRenderer);
		tableMain.getColumnModel().getColumn(19).setCellRenderer(customeCellRenderer);

		tableMain.removeColumn(tableMain.getColumnModel().getColumn(9));
		tableMain.removeColumn(tableMain.getColumnModel().getColumn(17));
		tableMain.setEnabled(true);

		SolTableModel.decorateTable(tableTimeDefinition);

		textFieldCompanyId.setFont(font);
		textFieldCompanyName.setFont(font);
		textFieldBranchCode.setFont(font);

		textFieldCompanyName.setEditable(false);
		textFieldBranchCode.setEditable(false);

		buttonShowFromMachine.setBackground(new Color(220, 100, 100));

		buttonShowFromMachine.setFont(new Font("Verdana", 1, 10));

		criteriaPanel.add(labelCompanyId);
		criteriaPanel.add(textFieldCompanyId);
		criteriaPanel.add(labelCompanyName);
		criteriaPanel.add(textFieldCompanyName);
		criteriaPanel.add(labelBranchCode);
		criteriaPanel.add(textFieldBranchCode);
		criteriaPanel.add(labelDate);
		criteriaPanel.add(textFieldCalendar.DesignFrame());

		criteriaPanel.add(buttonShowFromMachine);
		buttonShowFromMachine.setMnemonic(87);

		panelBottom.add(labelNameOfDay);
		labelNameOfDay.setForeground(Color.blue);
		panelBottom.add(progress);

		panelTop.add(criteriaPanel, "West");
		panelCenter.add(splitPanel, "Center");

		splitPanel.setDividerLocation(980);
		splitPanel.setOneTouchExpandable(true);

		panelMain.add(panelTop, "North");
		panelMain.add(panelCenter, "Center");
		panelMain.add(panelBottom, "South");

		localContainer.add(panelMain, "Center");

		miShowFromMachine.addActionListener(this);
		miSaveReport.addActionListener(this);
		miCleanThisDate.addActionListener(this);
		miPrintReport.addActionListener(this);
		miExportReport.addActionListener(this);
		miExit.addActionListener(this);

		miBreakTimeEntry.addActionListener(this);
		miMultiProjectEntry.addActionListener(this);

		miReportOfDay.addActionListener(this);
		miReportOfMonth.addActionListener(this);

		miTimeCalculator.addActionListener(this);
		miFindOption.addActionListener(this);
		miRecalculate.addActionListener(this);

		radioButtonProductionEmployee.addActionListener(this);
		radioButtonTrainee.addActionListener(this);
		radioButtonBothEmployee.addActionListener(this);

		backbut.addActionListener(this);
		forebut.addActionListener(this);
		buttonMount.addActionListener(this);
		buttonShowFromMachine.addActionListener(this);
		buttonPrint.addActionListener(this);
		buttonClear.addActionListener(this);
		buttonClose.addActionListener(this);

		buttonSave.addActionListener(this);
		buttonUpdate.addActionListener(this);

		tableMain.addKeyListener(this);
		tableMain.addMouseListener(this);

		defaultTableModelMain.addTableModelListener(this);

		textFieldCompanyId.addKeyListener(this);
		textFieldBranchCode.addKeyListener(this);

		tf6.addActionListener(this);
		tf6.addKeyListener(this);

		comboBoxProject.addMouseListener(this);
		comboBoxProject.addFocusListener(this);

		showLastDate();
		showShiftDefinition();
		frameMain.setSize(1024, 650);
		frameMain.setVisible(true);
		DateDifferencesUtil.setTotalTime(String.valueOf(tableModelShiftDefinition.getValueAt(0, 5)));
		UIUtil.centreToMainScreen(frameMain);
		return panelMain;
	}

	public class PrintAttendanceReport extends Thread implements Runnable {

		public void run() {
			String str = "Attendance Report for : " + String
					.valueOf(DateCalculationUtil
							.getNameofMonth(DateCalculationUtil.getMonth(textFieldCalendar.getText()) - 1))
					.toUpperCase() + " - " + String.valueOf(DateCalculationUtil.getYear(textFieldCalendar.getText()));

			try {
				MessageFormat localMessageFormat1 = new MessageFormat(str);
				MessageFormat localMessageFormat2 = new MessageFormat("- {0} -");
				tableMain.print(JTable.PrintMode.FIT_WIDTH, localMessageFormat1, localMessageFormat2);
			} catch (PrinterException localPrinterException) {
				System.err.println("Error printing: " + localPrinterException.getMessage());
			}
		}
	}

	public void showLastDate() {
		DateFormat formater = SolDateFormatter.getDateFormater(ApplicationConstants.DD_MM_YYYY);
		Connection con = null;
		PreparedStatement stat = null;
		ResultSet rs = null;

		try {
			con = DBConnectionUtil.getConnection();
			stat = con.prepareStatement("select max(date) from HRTIMEMASTER ");
			rs = stat.executeQuery();
			while (rs.next()) {
				lastdate = rs.getDate(1);
				textFieldCalendar.setText(formater.format(lastdate));
			}
		} catch (Exception localException) {
			JOptionPane.showMessageDialog(frameMain, localException.getMessage().toString());
		} finally {
			DBConnectionUtil.closeConnection(rs, stat, con);
		}
	}

	public void showShiftDefinition() {
		String companyId = SessionUtil.getLoginInfo().getCompanyId();
		String branchCode = SessionUtil.getLoginInfo().getBranchCode();

		ShiftDefinitionDTO shiftDefinitionDTO = AttendanceDAO.getShiftDefinition(companyId, branchCode, "G");

		tableModelShiftDefinition.addRow(new Object[] { shiftDefinitionDTO.getCompanyId(),
				shiftDefinitionDTO.getBranchId(), shiftDefinitionDTO.getShiftCode(), shiftDefinitionDTO.getInTime(),
				shiftDefinitionDTO.getOutTime(), shiftDefinitionDTO.getTotalTime(),
				shiftDefinitionDTO.getBreakOutTime(), shiftDefinitionDTO.getBreakInTime(),
				shiftDefinitionDTO.getTotalBreakTime(), shiftDefinitionDTO.getLateCount(),
				shiftDefinitionDTO.getOtNotBefore(), shiftDefinitionDTO.getMinimumOtValue(),
				shiftDefinitionDTO.getAbsentCount(), "" });
	}

	public void countMountain() {
		ar.clear();
		Connection con = SaviorConnectionUtil.getConnection();
		PreparedStatement stat = null;
		ResultSet rs = null;
		try {
			String str1 = "select distinct(date) from mmm where date > ? ";
			stat = con.prepareStatement(str1);
			stat.setDate(1, lastdate);
			rs = stat.executeQuery();

			while (rs.next()) {
				String str2 = new String(rs.getString(1));
				ar.add(str2);
			}
		} catch (Exception localException) {
			System.out.println("Mountain : " + localException);
		} finally {
			SaviorConnectionUtil.closeConnection(rs, stat, con);
		}
	}

	public void Mountain() {
		String str1 = String.valueOf(defaultTableModelMain.getValueAt(0, 0));
		String str2 = str1.substring(0, 2);
		String str3 = str1.substring(3, 5);
		String str4 = str1.substring(6, 10);

		Connection con = SaviorConnectionUtil.getConnection();
		PreparedStatement stat = null;
		ResultSet rs = null;
		try {
			@SuppressWarnings("deprecation")
			java.sql.Date localDate = new java.sql.Date(Integer.parseInt(str4) - 1900, Integer.parseInt(str3),
					Integer.parseInt(str2));

			String str5 = "select empcd,date from mmm where date = ? ";
			stat = con.prepareStatement(str5);
			stat.setDate(1, localDate);
			rs = stat.executeQuery();

			while (rs.next()) {
				new String(rs.getString("empcd"));
				new String(rs.getString("date"));
			}
		} catch (Exception localException) {
			System.out.println("Mountain : " + localException);
		} finally {
			SaviorConnectionUtil.closeConnection(rs, stat, con);
		}
	}

	public void changeTimeFormat() {
		int i = tableMain.getRowCount();
		for (int j = 0; j < i; j++) {
			String inTimeString = String.valueOf(defaultTableModelMain.getValueAt(j, 4));
			String outTimeString = String.valueOf(defaultTableModelMain.getValueAt(j, 5));

			if ((inTimeString.length() <= 1) || (inTimeString.equalsIgnoreCase("null")) || (inTimeString == null)) {
				defaultTableModelMain.setValueAt("0", j, 4);
				defaultTableModelMain.setValueAt("0", j, 5);
			} else {
				String inTimeArray[] = inTimeString.split("\\.");
				String outTimeArray[] = outTimeString.split("\\.");
				
				String str3 = inTimeArray[0] + "." + inTimeArray[1];
				String str4 = outTimeArray[0] + "." + outTimeArray[1];

				defaultTableModelMain.setValueAt(str3, j, 4);
				defaultTableModelMain.setValueAt(str4, j, 5);
			}
		}
	}

	public void markPresent(int paramInt) {
		String str3 = String.valueOf(defaultTableModelMain.getValueAt(paramInt, 0));
		String str4 = String.valueOf(defaultTableModelMain.getValueAt(paramInt, 2));
		java.sql.Date localDate = SolDateFormatter.DDMMYYtoSQL(str3);

		Connection con = null;
		PreparedStatement stat = null;
		ResultSet rs = null;

		try {
			con = DBConnectionUtil.getConnection();
			stat = con.prepareStatement("DELETE FROM HREMP_LEAVES WHERE EMP_CODE = ? AND DATE=?");
			stat.setString(1, str4);
			stat.setDate(2, localDate);
			int affected = stat.executeUpdate();
			if (affected > 0) {
				labelDBStatus.setText("EMP Present");
				labelDBStatus.setBackground(Color.red);
			}
		} catch (Exception localException) {
			labelDBStatus.setText("DB Status : " + localException.getMessage().toString());
			labelDBStatus.setBackground(Color.red);
		} finally {
			DBConnectionUtil.closeConnection(rs, stat, con);
		}
	}

	public void markAbsent(int paramInt, Connection con) {
		/*
		 * labelDBStatus.setText("DB Status : ");
		 * labelDBStatus.setBackground(Color.black);
		 * 
		 * String str1 = SessionUtil.getLoginInfo().getCompanyId(); String str2 =
		 * SessionUtil.getLoginInfo().getBranchCode();
		 * 
		 * String str3 = String.valueOf(defaultTableModelMain.getValueAt(paramInt, 0));
		 * String str4 = String.valueOf(defaultTableModelMain.getValueAt(paramInt, 2));
		 * java.sql.Date localDate = SolDateFormatter.DDMMYYtoSQL(str3);
		 * 
		 * PreparedStatement stat = null; ResultSet rs = null;
		 * 
		 * try { stat = con.
		 * prepareStatement("insert into HREMP_LEAVES values(?,?,?,?,?,?,?,?,?,?)" );
		 * stat.setString(1, str1); stat.setString(2, str2); stat.setString(3, str4);
		 * stat.setDate(4, localDate); stat.setString(5, "LWP"); stat.setString(6,
		 * "ABSENT Count"); stat.setString(7, "0"); stat.setString(8, "true");
		 * stat.setString(9, "true"); stat.setString(10, str3); stat.executeUpdate();
		 * 
		 * } catch (Exception localException) { labelDBStatus.setText("DB Status : " +
		 * localException.getMessage().toString());
		 * labelDBStatus.setBackground(Color.red); System.out.println("DB Status : " +
		 * localException); } finally { DBConnectionUtil.closeConnection(rs, stat,
		 * null); }
		 */
	}

	public void checkTotalTime() {
		int i = tableMain.getSelectedRow();
		String str1 = String.valueOf(defaultTableModelMain.getValueAt(i, 4));
		String str2 = String.valueOf(defaultTableModelMain.getValueAt(i, 5));

		if ((str1.length() > 1) && (str2.length() > 1)) {
			String str4 = String.valueOf(defaultTableModelMain.getValueAt(i, 4));
			String str5 = String.valueOf(defaultTableModelMain.getValueAt(i, 5));

			String str6 = DateDifferencesUtil.getTimeDiff(str4, str5);
			defaultTableModelMain.setValueAt(str6, i, 6);
		}
	}

	public void showNameList() {
		labelNameOfDay.setText(DateCalculationUtil.NameOfDay(String.valueOf(textFieldCalendar.getText())));

		String str1 = textFieldCompanyId.getText();
		String str2 = textFieldBranchCode.getText();

		String str4 = String.valueOf(labelNameOfDay.getText());
		String str5 = str4 + "_OT";

		String str6 = "%" + tf6.getText() + "%";
		defaultTableModelMain.setNumRows(0);

		java.sql.Date localDate = SolDateFormatter.DDMMYYtoSQL(textFieldCalendar.getText());
		System.out.println("SQL DATE : " + localDate);
		String str7;
		String str8;

		Connection con = null;
		PreparedStatement stat = null;
		ResultSet rs = null;
		try {
			con = DBConnectionUtil.getConnection();
			String query = "select p.punchcard_no, p.emp_code, p.name, h.OT, o." + str4 + ", o." + str5 + ", ph.UNIT"
					+ " from PUNCHCARD p, HREMP_JOIN h, HR_OFF_DAYS o, PHONE ph where p.emp_code like'" + str6
					+ "' and p.CO_ID like '" + textFieldCompanyId.getText() + "' and p.BRANCH_CODE like '"
					+ textFieldBranchCode.getText()
					+ "' AND p.emp_code not in(select emp_code from HR_EX_EMP where releave_date < ?)  AND p.EMP_CODE=h.EMP_CODE "
					+ " and p.EMP_CODE=ph.EMP_CODE and p.EMP_CODE=o.EMP_CODE AND p.co_id='" + str1
					+ "' and p.Branch_code='" + str2 + "' group by punchcard_no order by ph.UNIT";
			System.out.println("NameList: " + query);
			stat = con.prepareStatement(query);
			stat.setDate(1, localDate);
			rs = stat.executeQuery();

			rs.getRow();

			while (rs.next()) {
				str7 = new String(rs.getString(1));
				str8 = new String(rs.getString(2));
				String str9 = new String(rs.getString(3));
				String str10 = new String(rs.getString(4));
				String str11 = new String(rs.getString(5));
				String str12 = new String(rs.getString(6));
				String unitName = new String(rs.getString(7));
				if ((str11.equalsIgnoreCase(" ")) || (str11 == null)) {
					str11 = "1";
				}
				if ((str12.equalsIgnoreCase(" ")) || (str12 == null)) {
					str12 = "P";
				}

				defaultTableModelMain.addRow(new Object[] { textFieldCalendar.getText(), str7, str8,
						unitName.trim() + " - " + str9.toUpperCase(), "0", "0", "0", " ", " ", "0", "00.00", "00.00",
						new Boolean(true), str11, str12, "0", new Boolean(true), new Boolean(str10), "0", "0", "0" });
			}

			tableMain.revalidate();
		} catch (Exception localException) {
			JOptionPane.showMessageDialog(frameMain, localException.getMessage().toString());
		} finally {
			DBConnectionUtil.closeConnection(rs, stat, con);
		}

		String dateString = textFieldCalendar.getText();
		breakTimeMap = localBreakTimeEntry.getTotalBreakTime(dateString);
		labelNameOfDay.setText(DateCalculationUtil.NameOfDay(String.valueOf(dateString)));

		for (int j = 0; j < tableMain.getRowCount(); j++) {
			showBreakTime(j);
		}

		Connection con1 = null;
		try {
			con1 = DBConnectionUtil.getConnection();
			for (int j = 0; j < tableMain.getRowCount(); j++) {
				str7 = String.valueOf(defaultTableModelMain.getValueAt(j, 6));
				str8 = String.valueOf(defaultTableModelMain.getValueAt(j, 14));

				if ((Float.parseFloat(str7) <= 0.0F) && (str8.equalsIgnoreCase("P"))) {
					markAbsent(j, con1);
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			DBConnectionUtil.closeConnection(null, null, con1);
		}

		progress.setString("DB Status : Total No of Emp = " + String.valueOf(tableMain.getRowCount()));
	}

	public void showBreakTime(int i) {
		String empCodeString = String.valueOf(defaultTableModelMain.getValueAt(i, 2));
		String totalBreakTimeString = breakTimeMap.get(empCodeString);
		if (totalBreakTimeString != null) {
			defaultTableModelMain.setValueAt(totalBreakTimeString, i, 10);
		} else {
			defaultTableModelMain.setValueAt("00.00", i, 10);
		}
	}

	public void showOFFDAYTypes() {
		for (int i = 0; i < tableMain.getRowCount(); i++) {
			String.valueOf(defaultTableModelMain.getValueAt(i, 2));
			String str2 = String.valueOf(defaultTableModelMain.getValueAt(i, 0));

			String str3 = textFieldCompanyId.getText();
			String str4 = textFieldBranchCode.getText();

			labelNameOfDay.setText(DateCalculationUtil.NameOfDay(str2));

			String str5 = String.valueOf(labelNameOfDay.getText());
			String str6 = str5 + "_OT";

			Connection con = null;
			PreparedStatement stat = null;
			ResultSet rs = null;

			try {
				con = DBConnectionUtil.getConnection();
				stat = con.prepareStatement("select " + str5 + ", " + str6 + " from HR_OFF_DAYS where COMPANY_ID like'"
						+ str3 + "' and BRANCH_CODE='" + str4 + "' ");
				rs = stat.executeQuery();

				rs.getRow();

				while (rs.next()) {
					String str8 = new String(rs.getString(1));
					String str9 = new String(rs.getString(2));

					if (str8.equalsIgnoreCase(" ")) {
						str8 = "1";
					}
					if (str9.equalsIgnoreCase(" ")) {
						str9 = "P";
					}

					defaultTableModelMain.setValueAt(str8, i, 13);
					defaultTableModelMain.setValueAt(str9, i, 14);
				}
			} catch (Exception localException) {
				JOptionPane.showMessageDialog(frameMain, localException.getMessage().toString());
			} finally {
				DBConnectionUtil.closeConnection(rs, stat, con);
			}
		}
	}

	public void showOFFDays() {
		String companyId = SessionUtil.getLoginInfo().getCompanyId();
		String branchCode = SessionUtil.getLoginInfo().getBranchCode();
		tf6.getText();

		Connection con = null;
		PreparedStatement stat = null;
		ResultSet rs = null;
		try {
			con = DBConnectionUtil.getConnection();

			for (int i = 0; i < tableMain.getRowCount(); i++) {
				String str4 = String.valueOf(defaultTableModelMain.getValueAt(i, 0));
				java.sql.Date localDate = SolDateFormatter.DDMMYYtoSQL(str4);

				defaultTableModelMain.setValueAt("P", i, 14);
				defaultTableModelMain.setValueAt("1", i, 13);
				String str5;
				String str6;

				stat = con.prepareStatement("select OT_TYPE,OT_VALUE from HR_OT_URGENT where company_id='" + companyId
						+ "' and branch_code='" + branchCode + "' and date= ? ");
				stat.setDate(1, localDate);
				rs = stat.executeQuery();
				while (rs.next()) {
					str5 = new String(rs.getString(1));
					str6 = new String(rs.getString(2));

					defaultTableModelMain.setValueAt(str5, i, 14);
					defaultTableModelMain.setValueAt(str6, i, 13);
				}

				DBConnectionUtil.closeConnection(rs, stat, null);

				stat = con.prepareStatement("select OT_TYPE,OT_VALUE from HR_HOLIDAY_LIST where company_id='"
						+ companyId + "' and branch_code='" + branchCode + "' and date= ? ");
				stat.setDate(1, localDate);
				rs = stat.executeQuery();
				while (rs.next()) {
					str5 = new String(rs.getString(1));
					str6 = new String(rs.getString(2));

					defaultTableModelMain.setValueAt(str5, i, 14);
					defaultTableModelMain.setValueAt(str6, i, 13);
				}

			}
		} catch (Exception localException2) {
			JOptionPane.showMessageDialog(frameMain, localException2.getMessage().toString());
		} finally {
			DBConnectionUtil.closeConnection(rs, stat, con);
		}
	}

	public void setParcelOTHrs(int paramInt, Connection con) {
		float f1 = 0.0F;

		defaultTableModelMain.setValueAt("00.00", paramInt, 15);

		String str1 = String.valueOf(defaultTableModelMain.getValueAt(paramInt, 6));
		String str2 = String.valueOf(defaultTableModelMain.getValueAt(paramInt, 17));
		int i = Integer.parseInt(String.valueOf(defaultTableModelMain.getValueAt(paramInt, 13)));
		String str3 = String.valueOf(defaultTableModelMain.getValueAt(paramInt, 14));
		float f2 = Float.parseFloat(String.valueOf(tableModelShiftDefinition.getValueAt(0, 11)));

		String str4 = "0";

		String str5 = String.valueOf(tableModelShiftDefinition.getValueAt(0, 3));
		String.valueOf(tableModelShiftDefinition.getValueAt(0, 4));
		String str7 = String.valueOf(tableModelShiftDefinition.getValueAt(0, 5));

		String.valueOf(defaultTableModelMain.getValueAt(paramInt, 2));
		String str9 = String.valueOf(defaultTableModelMain.getValueAt(paramInt, 4));
		String.valueOf(defaultTableModelMain.getValueAt(paramInt, 5));

		if ((Float.parseFloat(str1) <= 0.0F) && (str3.equalsIgnoreCase("P"))) {
			markAbsent(paramInt, con);
		}

		if (Float.parseFloat(str9) >= 0.0F) {
			f1 = Float.parseFloat(str1);

			int j = SolDateFormatter.convertHrsToMinute(str7);
			int k = SolDateFormatter.convertHrsToMinute(str1);

			float f3 = Float.parseFloat(String.valueOf(tableModelShiftDefinition.getValueAt(0, 12)));
			if ((Float.parseFloat(str1) < f3) && (str3.equalsIgnoreCase("P"))) {
				markAbsent(paramInt, con);
			}
			if ((k < j) && (k > 0) && (str3.equalsIgnoreCase("P"))) {
				int m = j - k;
				defaultTableModelMain.setValueAt(SolDateFormatter.convertMinuteToHRS(m), paramInt, 15);
			}
			if (f1 >= 0.0F) {
				if ((str2.equalsIgnoreCase("true")) || (str2.equalsIgnoreCase("false"))) {
					if (str3.equalsIgnoreCase("F")) {
						f1 = Float.parseFloat(str1);
						str4 = SolDateFormatter.getTimeMultipliedBy(str1, i);

						defaultTableModelMain.setValueAt(str4, paramInt, 11);
						defaultTableModelMain.setValueAt("00.00", paramInt, 15);
					}
					if (str3.equalsIgnoreCase("P")) {
						str4 = DateDifferencesUtil.getTimeDiff(str7, str1);

						if (Float.parseFloat(str9) < Float.parseFloat(str5)) {
							String str11 = DateDifferencesUtil.getTimeDiff(str9, str5);

							str4 = DateDifferencesUtil.getTimeDiff(str11, str4);
							str4 = SolDateFormatter.getTimeMultipliedBy(str4, i);

							if (Float.parseFloat(str4) >= f2) {
								defaultTableModelMain.setValueAt(str4, paramInt, 11);
							}
						}

						if (Float.parseFloat(str1) < Float.parseFloat(str7)) {
							defaultTableModelMain.setValueAt("00.00", paramInt, 11);
						}
					}

					if (Float.parseFloat(str9) >= Float.parseFloat(str5)) {
						f1 = Float.parseFloat(str1);
						str4 = SolDateFormatter.getTimeMultipliedBy(str4, i);

						if (Float.parseFloat(str4) >= f2) {
							defaultTableModelMain.setValueAt(str4, paramInt, 11);
						}
					}
				}
			}

			f1 = 0.0F;
			str1 = "0";
			str4 = "0";
		}
	}

	public void projectNo() {
		int i = tableMain.getRowCount();

		String fromDate = null;
		String empCode = "0";

		Connection con = null;
		PreparedStatement stat = null;
		ResultSet rs = null;

		try {
			con = DBConnectionUtil.getConnection();
			for (int j = 0; j < i; j++) {
				fromDate = String.valueOf(defaultTableModelMain.getValueAt(j, 0));
				empCode = String.valueOf(defaultTableModelMain.getValueAt(j, 2));

				stat = con.prepareStatement("select project_no, dtl_chk from projectmanpower where emp_code='" + empCode
						+ "' and from_date='" + fromDate + "' ");
				rs = stat.executeQuery();
				while (rs.next()) {
					String str4 = new String(rs.getString(1));
					String str5 = new String(rs.getString(2));
					if (str5.equalsIgnoreCase("Detailing")) {
						str5 = "D";
					}
					if (str5.equalsIgnoreCase("Checking")) {
						str5 = "C";
					}

					defaultTableModelMain.setValueAt(str4, j, 7);
					defaultTableModelMain.setValueAt(str5, j, 8);
				}

			}
		} catch (Exception localException) {
			JOptionPane.showMessageDialog(frameMain, localException.getMessage().toString());
		} finally {
			DBConnectionUtil.closeConnection(rs, stat, con);
		}
	}

	public Map<String, String> fetchAttendanceFromExcel(String excelFileName) {
		Map<String, String> attendanceMap = new HashMap<String, String>();
		Collection<String> punchcards = EmpTB.punchcardtable.values();

		try {
			FileInputStream excelFile = new FileInputStream(new File(excelFileName));
			HSSFWorkbook workbook = new HSSFWorkbook(excelFile);
			Sheet datatypeSheet = workbook.getSheetAt(0);
			DataFormatter formatter = new DataFormatter();
			Iterator<Row> iterator = datatypeSheet.iterator();
			System.out.println("Read Excel...");
			while (iterator.hasNext()) {
				Row currentRow = iterator.next();
				// String punchcardCode = currentRow.getCell(0).getStringCellValue();
				String punchcardCode = formatter.formatCellValue(currentRow.getCell(0));
				// String inTime = String.valueOf(currentRow.getCell(1).getNumericCellValue());
				String inTime = formatter.formatCellValue(currentRow.getCell(1));
				// String outTime = String.valueOf(currentRow.getCell(2).getNumericCellValue());
				String outTime = formatter.formatCellValue(currentRow.getCell(2));
				attendanceMap.put(punchcardCode, inTime + "_" + outTime);
				System.out.println(punchcardCode + " : " + inTime + "_" + outTime);
			}

		} catch (Exception localException) {
			// JOptionPane.showMessageDialog(frameMain,
			// localException.getMessage().toString());
			localException.printStackTrace();
		}

		return attendanceMap;
	}

	public Map<String, String> fetchAttendance() {
		Map<String, String> attendanceMap = new HashMap<String, String>();
		Collection<String> punchcards = EmpTB.punchcardtable.values();

		Connection con = null;

		try {
			con = SaviorConnectionUtil.getConnection();

			int counter = 0;
			for (String punchcardCode : punchcards) {
				counter++;
				java.sql.Date localDate = SolDateFormatter.DDMMYYtoSQL(textFieldCalendar.getText());

				PreparedStatement stat = con
						.prepareStatement("select min(time), max(time) from mmm Where empcd = ? and date = ? ");
				stat.setString(1, punchcardCode);
				stat.setDate(2, localDate);
				ResultSet rs = stat.executeQuery();

				while (rs.next()) {
					String inTime = rs.getString(1);
					String outTime = rs.getString(2);
					attendanceMap.put(punchcardCode, inTime + "_" + outTime);
				}

				if (counter == 40) {
					counter = 0;
					try {
						rs.close();
						stat.close();
						con.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
					con = SaviorConnectionUtil.getConnection();
				}
			}

		} catch (Exception localException) {
			// JOptionPane.showMessageDialog(frameMain,
			// localException.getMessage().toString());
			localException.printStackTrace();
		} finally {
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

		return attendanceMap;
	}

	/*
	 * public class showTodays extends Thread { public showTodays() { }
	 * 
	 * DecimalFormat fomatter2digit = new
	 * DecimalFormat(ApplicationConstants.DIGIT_2); DecimalFormat fomatter4digit =
	 * new DecimalFormat(ApplicationConstants.DIGIT_4);
	 * 
	 * String lastdatestr = textFieldCalendar.getText(); String dat =
	 * lastdatestr.substring(0, 2); String mon = lastdatestr.substring(3, 5); String
	 * yr = lastdatestr.substring(6, 10);
	 * 
	 * int row = tableMain.getRowCount(); Connection con =
	 * SaviorConnectionUtil.getConnection();
	 * 
	 * public void run() { for (int i = 0; i < row; i++) { String str1 =
	 * String.valueOf(defaultTableModelMain.getValueAt(i, 1));
	 * 
	 * progress.setMinimum(0); progress.setMaximum(row - 1); progress.setValue(i);
	 * progress.setStringPainted(true);
	 * 
	 * try { Thread.sleep(2L); } catch (InterruptedException
	 * localInterruptedException) { System.out.println(localInterruptedException); }
	 * String str3; String str4; try {
	 * 
	 * @SuppressWarnings("deprecation") java.sql.Date localDate = new
	 * java.sql.Date(Integer.parseInt(yr) - 1900, Integer.parseInt(mon) - 1,
	 * Integer.parseInt(dat));
	 * 
	 * PreparedStatement stat = con.prepareStatement(
	 * "select min(time), max(time) from mmm Where empcd ='" + str1 +
	 * "' AND  date = ? "); stat.setDate(1, localDate); ResultSet rs =
	 * stat.executeQuery();
	 * 
	 * if (rs.getRow() < 1) { }
	 * 
	 * while (rs.next()) { str3 = new String(rs.getString(1)); str4 = new
	 * String(rs.getString(2));
	 * 
	 * defaultTableModelMain.setValueAt(str3, i, 4);
	 * defaultTableModelMain.setValueAt(str4, i, 5);
	 * defaultTableModelMain.setValueAt("0", i, 6); } } catch (Exception
	 * localException) { // JOptionPane.showMessageDialog(frameMain, //
	 * localException.getMessage().toString()); localException.printStackTrace(); }
	 * String str2 = String.valueOf(defaultTableModelMain.getValueAt(i, 4)); if
	 * (str2.length() > 1) {
	 * 
	 * str3 = String.valueOf(defaultTableModelMain.getValueAt(i, 4)); str4 =
	 * String.valueOf(defaultTableModelMain.getValueAt(i, 5));
	 * 
	 * String str5 = str3.substring(0, 2) + str3.substring(3, 5); String str6 =
	 * str4.substring(0, 2) + str4.substring(3, 5);
	 * 
	 * String str7 = str3.substring(3, 5); String str8 = str4.substring(3, 5);
	 * 
	 * int j = Integer.parseInt(str7); int k = Integer.parseInt(str8);
	 * 
	 * int m = Integer.parseInt(str6) - Integer.parseInt(str5);
	 * 
	 * defaultTableModelMain.setValueAt(String.valueOf(fomatter4digit.format(m)) ,
	 * i, 6);
	 * 
	 * String str9 = String.valueOf(defaultTableModelMain.getValueAt(i, 6));
	 * 
	 * String str10 = str9.substring(0, 2); String str11 = str9.substring(2, 4); int
	 * n; int i1; if (j <= k) { n = Integer.parseInt(str11) / 60 +
	 * Integer.parseInt(str10); i1 = Integer.parseInt(str11) % 60;
	 * defaultTableModelMain.setValueAt(String.valueOf(fomatter2digit.format(n)) +
	 * "." + String.valueOf(fomatter2digit.format(i1)), i, 6); } if (j > k) { n =
	 * Integer.parseInt(str10); i1 = 60 - j + k;
	 * defaultTableModelMain.setValueAt(String.valueOf(fomatter2digit.format(n)) +
	 * "." + String.valueOf(fomatter2digit.format(i1)), i, 6); } }
	 * 
	 * changeTimeFormat(); }
	 * 
	 * try { Connection con = DBConnectionUtil.getConnection();
	 * 
	 * for (int i = 0; i < tableMain.getRowCount(); i++) { setParcelOTHrs(i, con); }
	 * } catch (Exception ex) { ex.printStackTrace(); } finally {
	 * DBConnectionUtil.closeConnection(null, null, con); } } }
	 */

	public class showTodays extends Thread {

		String excelFileName = null;
		DecimalFormat formatter2digit = new DecimalFormat(ApplicationConstants.DIGIT_2);
		DecimalFormat formatter4digit = new DecimalFormat(ApplicationConstants.DIGIT_4);
		int row = tableMain.getRowCount();

		public showTodays(String excelFileName) {
			this.excelFileName = excelFileName;
		}

		public void run() {
			Map<String, String> attendanceMap = fetchAttendanceFromExcel(excelFileName);

			for (int i = 0; i < row; i++) {
				try {
					Thread.sleep(2L);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				String punchcardCode = String.valueOf(defaultTableModelMain.getValueAt(i, 1));

				progress.setMinimum(0);
				progress.setMaximum(row - 1);
				progress.setValue(i);
				progress.setStringPainted(true);

				String inTime = "00.00";
				String outTime = "00.00";
				String totalTime = "00.00";

				String timeData = attendanceMap.get(punchcardCode);
				if (timeData != null) {
					String timeDataArray[] = timeData.split("_");
					inTime = timeDataArray[0];
					outTime = timeDataArray[1];
				}

				defaultTableModelMain.setValueAt(inTime, i, 4);
				defaultTableModelMain.setValueAt(outTime, i, 5);
				defaultTableModelMain.setValueAt(totalTime, i, 6);
				
				String totalTimeString = TimeUtil.getTotalWorkingHrs(inTime, outTime);
				defaultTableModelMain.setValueAt(totalTimeString, i, 6);
				changeTimeFormat();
			}

			Connection con = null;
			try {
				con = DBConnectionUtil.getConnection();
				for (int i = 0; i < tableMain.getRowCount(); i++) {
					setParcelOTHrs(i, con);
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			} finally {
				DBConnectionUtil.closeConnection(null, null, con);
			}
		}
	}

	public class MountainAll extends Thread {

		java.sql.Date currentDate = null;
		DateFormat formater = SolDateFormatter.getDateFormater(ApplicationConstants.DD_MM_YYYY);
		DecimalFormat fomatter2digit = new DecimalFormat(ApplicationConstants.DIGIT_2);
		DecimalFormat fomatter4digit = new DecimalFormat(ApplicationConstants.DIGIT_4);

		public void run() {
			countMountain();
			for (int i = 0; i < ar.size() - 1; i++) {
				int j = tableMain.getRowCount();
				String str1 = formater.format(java.sql.Date.valueOf(String.valueOf(ar.get(i))));
				String str2 = str1.substring(0, 2);
				String str3 = str1.substring(3, 5);
				String str4 = str1.substring(6, 10);

				textFieldCalendar.setText(str1);
				showNameList();
				Connection con = SaviorConnectionUtil.getConnection();
				for (int k = 0; k < j; k++) {
					String str5 = String.valueOf(defaultTableModelMain.getValueAt(k, 1));
					progress.setMinimum(0);
					progress.setMaximum(j - 1);
					progress.setValue(k);
					progress.setString(String.valueOf(i + 1) + "/" + String.valueOf(ar.size()));
					progress.setStringPainted(true);
					try {
						Thread.sleep(10L);
					} catch (InterruptedException localInterruptedException) {
						System.out.println(localInterruptedException);
					}
					String str7;
					String str8;
					try {
						@SuppressWarnings("deprecation")
						java.sql.Date localDate = new java.sql.Date(Integer.parseInt(str4) - 1900,
								Integer.parseInt(str3) - 1, Integer.parseInt(str2));

						PreparedStatement stat = con.prepareStatement(
								"select min(time), max(time) from mmm Where empcd ='" + str5 + "' AND  date = ? ");
						stat.setDate(1, localDate);
						ResultSet rs = stat.executeQuery();

						if (rs.getRow() < 1) {
						}

						while (rs.next()) {
							str7 = new String(rs.getString(1));
							str8 = new String(rs.getString(2));

							defaultTableModelMain.setValueAt(str7, k, 4);
							defaultTableModelMain.setValueAt(str8, k, 5);
							defaultTableModelMain.setValueAt("0", k, 6);
						}
					} catch (Exception localException) {
						JOptionPane.showMessageDialog(frameMain, localException.getMessage().toString());
					}
					String str6 = String.valueOf(defaultTableModelMain.getValueAt(k, 4));
					if (str6.length() > 1) {
						str7 = String.valueOf(defaultTableModelMain.getValueAt(k, 4));
						str8 = String.valueOf(defaultTableModelMain.getValueAt(k, 5));

						String str9 = str7.substring(0, 2) + str7.substring(3, 5);
						String str10 = str8.substring(0, 2) + str8.substring(3, 5);

						String str11 = str7.substring(3, 5);
						String str12 = str8.substring(3, 5);

						int m = Integer.parseInt(str11);
						int n = Integer.parseInt(str12);

						int i1 = Integer.parseInt(str10) - Integer.parseInt(str9);

						defaultTableModelMain.setValueAt(String.valueOf(fomatter4digit.format(i1)), k, 6);
						String str13 = String.valueOf(defaultTableModelMain.getValueAt(k, 6));

						String str14 = str13.substring(0, 2);
						String str15 = str13.substring(2, 4);
						int i2;
						int i3;
						if (m <= n) {
							i2 = Integer.parseInt(str15) / 60 + Integer.parseInt(str14);
							i3 = Integer.parseInt(str15) % 60;
							defaultTableModelMain.setValueAt(String.valueOf(fomatter2digit.format(i2)) + "."
									+ String.valueOf(fomatter2digit.format(i3)), k, 6);
						}
						if (m > n) {
							i2 = Integer.parseInt(str14);
							i3 = 60 - m + n;
							defaultTableModelMain.setValueAt(String.valueOf(fomatter2digit.format(i2)) + "."
									+ String.valueOf(fomatter2digit.format(i3)), k, 6);
						}
					}

					changeTimeFormat();
				}

				Connection con1 = null;
				try {
					con1 = DBConnectionUtil.getConnection();
					for (int k = 0; k < tableMain.getRowCount(); k++) {
						setParcelOTHrs(k, con1);
					}
				} catch (Exception ex) {
					ex.printStackTrace();
				} finally {
					DBConnectionUtil.closeConnection(null, null, con1);
				}

				saveRecord();
			}
		}
	}

	public void updatedView(String paramString) {
		DecimalFormat formatter2decimal = new DecimalFormat(ApplicationConstants.DIGIT_DECIMAL_2);
		defaultTableModelMain.setNumRows(0);
		String str1 = "%" + tf6.getText();
		String str2 = String.valueOf(textFieldCalendar.getText());
		String str3 = str2.substring(0, 2);
		String str4 = str2.substring(3, 5);
		String str5 = str2.substring(6, 10);

		@SuppressWarnings("deprecation")
		java.sql.Date localDate = new java.sql.Date(Integer.parseInt(str5) - 1900, Integer.parseInt(str4) - 1,
				Integer.parseInt(str3));

		Connection con = null;
		PreparedStatement stat = null;
		ResultSet rs = null;
		try {
			con = DBConnectionUtil.getConnection();
			String query = "select h.DATE, h.PUNCHCARD, h.EMP_CODE, h.EMP_NAME, h.IN_TIME, h.OUT_TIME, "
					+ " h.TOTAL_TIME, h.PROJECT_NO, h.D_C, h.UPDATED, h.BREAK_HRS, h.OT_HRS, h.DEDUCT_OT, "
					+ " h.DEDUCT_REMARKS, h.OT_REMARKS, h.BREAK_UPDATED, h.M_STATUS, h.OT2, p.UNIT "
					+ " from HRTIMEMASTER h, PHONE p where h.DATE = ? and h.EMP_CODE like'" + str1 + "' and "
					+ " h.EMP_CODE not in(select emp_code from HREMP_JOIN where status='" + paramString + "') and "
					+ " h.EMP_CODE = p.EMP_CODE and ( h.M_STATUS ='0' OR h.M_STATUS='M') order by UNIT";
			stat = con.prepareStatement(query);
			stat.setDate(1, localDate);
			rs = stat.executeQuery();

			while (rs.next()) {
				String date = new String(rs.getString(1));// Date
				String punchcard = new String(rs.getString(2));// PUNCHCARD
				String empCode = new String(rs.getString(3));// EMP_CODE
				String empName = new String(rs.getString(4));// EMP_NAME
				String inTime = new String(rs.getString(5));// IN_TIME
				String outTime = new String(rs.getString(6));// OUT_TIME
				String totalTime = new String(rs.getString(7));// TOTAL_TIME
				String projectNo = new String(rs.getString(8));// PROJECT_NO
				String detailingChecking = new String(rs.getString(9));// D_C
				String updated = new String(rs.getString(10)); // UPDATED
				String breakHrs = String.valueOf(formatter2decimal.format(rs.getFloat(11)));// BREAK_HRS
				String otHrs = String.valueOf(formatter2decimal.format(rs.getFloat(12)));// OT_HRS
				String deductOt = String.valueOf(formatter2decimal.format(rs.getFloat(13)));// DEDUCT_OT
				String deductRemarks = new String(rs.getString(14));// DEDUCT_REMARKS
				String otRemarks = new String(rs.getString(15));// OT_REMARKS
				String breakUpdate = new String(rs.getString(16));// BREAK_UPDATED
				String mStatus = new String(rs.getString(17));// M_STATUS
				String ot2 = String.valueOf(formatter2decimal.format(rs.getFloat(18)));// OT2
				String unit = rs.getString(19);// UNIT

				if (detailingChecking.equalsIgnoreCase("null")) {
					detailingChecking = " ";
				}
				if (totalTime.equalsIgnoreCase("null")) {
					totalTime = " ";
				}

				defaultTableModelMain.addRow(new Object[] { textFieldCalendar.getText(), punchcard, empCode,
						unit+" - "+empName.toUpperCase(), inTime, outTime, totalTime, projectNo, detailingChecking, updated,
						breakHrs, otHrs, new Boolean(otRemarks), " ", " ", deductOt, new Boolean(deductRemarks),
						new Boolean(false), breakUpdate, ot2 });
			}
		} catch (Exception localException) {
			JOptionPane.showMessageDialog(frameMain, localException.getMessage().toString());
		} finally {
			DBConnectionUtil.closeConnection(rs, stat, con);
		}

		personalOTPermission();
		showOFFDays();

		String dateString = textFieldCalendar.getText();
		breakTimeMap = localBreakTimeEntry.getTotalBreakTime(dateString);
		labelNameOfDay.setText(DateCalculationUtil.NameOfDay(String.valueOf(dateString)));

		for (int i = 0; i < tableMain.getRowCount(); i++) {
			showBreakTime(i);
		}
		System.out.println();
	}

	public void personalOTPermission() {
		int i = tableMain.getRowCount();
		String str1 = "0";

		Connection con = null;
		PreparedStatement stat = null;
		ResultSet rs = null;

		try {
			for (int j = 0; j < i; j++) {
				str1 = String.valueOf(defaultTableModelMain.getValueAt(j, 2));
				con = DBConnectionUtil.getConnection();
				stat = con.prepareStatement("select permission from HR_OT_PERMISSION where emp_code=? ");
				stat.setString(1, str1);
				rs = stat.executeQuery();
				while (rs.next()) {
					String str2 = new String(rs.getString(1));
					defaultTableModelMain.setValueAt(new Boolean(str2), j, 17);
				}
			}
		} catch (Exception localException) {
			progress.setString("DB Status : " + localException.getMessage().toString());
		} finally {
			DBConnectionUtil.closeConnection(rs, stat, con);
		}
	}

	public void personalUpdatedView() {
		DecimalFormat formatter2decimal = new DecimalFormat(ApplicationConstants.DIGIT_DECIMAL_2);
		defaultTableModelMain.setNumRows(0);

		String str1 = String.valueOf(textFieldCalendar.getText());
		str1.substring(0, 2);
		String str3 = str1.substring(3, 5);
		String str4 = str1.substring(6, 10);

		int i = DateCalculationUtil.getNumberOfDays(textFieldCalendar.getText());

		@SuppressWarnings("deprecation")
		java.sql.Date localDate1 = new java.sql.Date(Integer.parseInt(str4) - 1900, Integer.parseInt(str3) - 1, 1);
		@SuppressWarnings("deprecation")
		java.sql.Date localDate2 = new java.sql.Date(Integer.parseInt(str4) - 1900, Integer.parseInt(str3) - 1, i);

		String str5 = String.valueOf(tf6.getText());

		Connection con = null;
		PreparedStatement stat = null;
		ResultSet rs = null;

		try {
			con = DBConnectionUtil.getConnection();
			stat = con.prepareStatement("select * from HRTIMEMASTER where date Between ? and ? and emp_code = '" + str5
					+ "' and (M_STATUS  ='0' or M_STATUS = 'M') order by DATE ");
			stat.setDate(1, localDate1);
			stat.setDate(2, localDate2);

			rs = stat.executeQuery();

			while (rs.next()) {
				java.sql.Date localDate3 = rs.getDate(1);
				String str6 = new String(rs.getString(2));
				String str7 = new String(rs.getString(3));
				String str8 = new String(rs.getString(4));
				String str9 = new String(rs.getString(5));
				String str10 = new String(rs.getString(6));
				String str11 = new String(rs.getString(7));
				String str12 = new String(rs.getString(8));
				String str13 = new String(rs.getString(9));
				String str14 = new String(rs.getString(10));
				String str15 = String.valueOf(formatter2decimal.format(rs.getFloat(11)));
				String str16 = String.valueOf(formatter2decimal.format(rs.getFloat(12)));
				String str17 = String.valueOf(formatter2decimal.format(rs.getFloat(13)));
				String str18 = new String(rs.getString(14));
				String str19 = new String(rs.getString(15));
				String str20 = new String(rs.getString(16));
				String str21 = new String(rs.getString(17));
				String str22 = String.valueOf(formatter2decimal.format(rs.getFloat(18)));

				if (str13.equalsIgnoreCase("null")) {
					str13 = " ";
				}
				if (str11.equalsIgnoreCase("null")) {
					str11 = " ";
				}
				if (str22.equalsIgnoreCase("null")) {
					str22 = "0";
				}

				defaultTableModelMain.addRow(new Object[] { SolDateFormatter.SQLtoDDMMYY(localDate3), str6, str7,
						str8.toUpperCase(), str9, str10, str11, str12, str13, str14, str15, str16, new Boolean(str19),
						" ", " ", str17, new Boolean(str18), str20, str21, str22 });
			}
		} catch (Exception localException) {
			JOptionPane.showMessageDialog(frameMain, localException.getMessage().toString());
		} finally {
			DBConnectionUtil.closeConnection(rs, stat, con);
		}

		personalOTPermission();
		calculate();
		lateCount(tableMain.getRowCount() - 2);
		tableMain.setEnabled(true);
	}

	public void calculate() {
		float f1 = 0.0F;
		float f2 = 0.0F;
		float f3 = 0.0F;
		float f4 = 0.0F;
		float f5 = 0.0F;

		float f6 = 0.0F;
		float f7 = 0.0F;
		float f8 = 0.0F;
		float f9 = 0.0F;
		float f10 = 0.0F;

		for (int i = 0; i < tableMain.getRowCount(); i++) {
			f2 = SolDateFormatter.convertHrsToMinute(String.valueOf(defaultTableModelMain.getValueAt(i, 6)));

			String str2 = String.valueOf(defaultTableModelMain.getValueAt(i, 12));
			String str3 = String.valueOf(defaultTableModelMain.getValueAt(i, 16));

			if (str2.equalsIgnoreCase("true")) {
				f1 = SolDateFormatter.convertHrsToMinute(String.valueOf(defaultTableModelMain.getValueAt(i, 10)));
				f3 = SolDateFormatter.convertHrsToMinute(String.valueOf(defaultTableModelMain.getValueAt(i, 11)));
				f4 = SolDateFormatter.convertHrsToMinute(String.valueOf(defaultTableModelMain.getValueAt(i, 19)));

				if (str3.equalsIgnoreCase("true")) {
					f5 = SolDateFormatter.convertHrsToMinute(String.valueOf(defaultTableModelMain.getValueAt(i, 15)));
				}
			}

			f7 += f2;
			f6 += f1;
			f8 += f3;
			f10 += f4;
			f9 += f5;

			System.out.println("Deduct Hrs: " + f5);
		}

		DecimalFormat fomatter2digit = new DecimalFormat(ApplicationConstants.DIGIT_2);
		String str1 = SolDateFormatter.convertMinuteToHRS(Integer.parseInt(String.valueOf(fomatter2digit.format(f7))));
		String str2 = SolDateFormatter.convertMinuteToHRS(Integer.parseInt(String.valueOf(fomatter2digit.format(f8))));
		String str3 = SolDateFormatter.convertMinuteToHRS(Integer.parseInt(String.valueOf(fomatter2digit.format(f10))));
		String str4 = SolDateFormatter.convertMinuteToHRS(Integer.parseInt(String.valueOf(fomatter2digit.format(f6))));
		String str5 = SolDateFormatter.convertMinuteToHRS(Integer.parseInt(String.valueOf(fomatter2digit.format(f9))));

		System.out.println("SUMDeduct Hrs: " + str5 + "\t" + f9);

		int j = SolDateFormatter.convertHrsToMinute(str2) + SolDateFormatter.convertHrsToMinute(str3)
				- SolDateFormatter.convertHrsToMinute(str5) - SolDateFormatter.convertHrsToMinute(str4);

		String str6 = "00.00";
		if (j > 0) {
			SolDateFormatter.convertMinuteToHRS(j);
		}

		defaultTableModelMain.addRow(new Object[] { "", "", "", "", "0", "0", str1, "0", "0", "0", str4, str2,
				new Boolean(true), "0", "0", str5, new Boolean(true), new Boolean(true), "0", str3 });
		defaultTableModelMain.addRow(new Object[] { "00000000", "00000", "000", "000000", "00.00", "00.00", "00.00",
				"0", "0", "00.00", "00.00", str6, new Boolean(true), "0", "0", "00.00", new Boolean(true),
				new Boolean(true), "0", "00.00" });
		f2 = 0.0F;
		f3 = 0.0F;
		f5 = 0.0F;
		f1 = 0.0F;
		f4 = 0.0F;
	}

	public void lateCount(int paramInt) {
		labelLate.setText("");
		float f1 = Float.parseFloat(String.valueOf(tableModelShiftDefinition.getValueAt(0, 3)));
		float f2 = Float.parseFloat(String.valueOf(tableModelShiftDefinition.getValueAt(0, 4)));

		int i = 0;
		int j = 0;
		int k = 0;
		int m = 0;

		for (int n = 0; n < paramInt; n++) {
			m += 1;
			float f3 = Float.parseFloat(String.valueOf(defaultTableModelMain.getValueAt(n, 4)));
			float f4 = Float.parseFloat(String.valueOf(defaultTableModelMain.getValueAt(n, 5)));
			float f5 = Float.parseFloat(String.valueOf(defaultTableModelMain.getValueAt(n, 6)));

			if ((f3 > 0.0F) && (f3 > f1)) {
				i += 1;
			}
			if ((f3 > 0.0F) && (f4 < f2)) {
				j += 1;
			}
			if (f5 <= 0.0F) {
				k += 1;
			}
		}

		labelLate.setText("<html><font color='black'>Late Coming: <html><font color='red'>" + String.valueOf(i)
				+ ", <html><font color='black'>Early Goning: <html><font color='red'>" + String.valueOf(j)
				+ ", <html><font color='black'>Absents: <html><font color='red'>" + String.valueOf(k)
				+ " ,<html><Font color='BLACK'>Total : <html><Font color='BLUE'>" + String.valueOf(m));
	}

	public void DeleteBeforeSave() {
		java.sql.Date localDate = SolDateFormatter.DDMMYYtoSQL(textFieldCalendar.getText());

		Object[] arrayOfObject = { "Yes", "No! Not at All" };
		int i = JOptionPane.showOptionDialog(frameMain,
				"Do You Really Want to Clean " + textFieldCalendar.getText() + ".", "Confirm Clean", 0, 3, null,
				arrayOfObject, arrayOfObject[1]);

		if (i == 0) {
			Connection con = null;
			PreparedStatement stat = null;
			try {
				con = DBConnectionUtil.getConnection();
				stat = con.prepareStatement("Delete from HRTIMEMASTER where date=?");
				stat.setDate(1, localDate);
				int affected = stat.executeUpdate();
				DBConnectionUtil.closeConnection(stat, null);
				if (affected > 0) {
					defaultTableModelMain.setNumRows(0);
					stat = con.prepareStatement("Delete from HREMP_LEAVES where date=?");
					stat.setDate(1, localDate);
					stat.executeUpdate();
				}
			} catch (Exception localException) {
				progress.setString("DB Status : " + localException.getMessage().toString());
			} finally {
				DBConnectionUtil.closeConnection(stat, con);
			}
		}
	}

	public void saveRecord() {
		int i = tableMain.getRowCount();

		String dateString = "0";
		String punchCard = "0";
		String empCode = "0";
		String empName = "0";
		String inTime = "0";
		String outTime = "0";
		String totalTime = "0";
		String projectNo = "0";
		String dc = "0";
		String breakHrs = "0";
		String otHrs = "0";
		String ot2 = "0";

		Object[] arrayOfObject = { "Yes", "No! Not at All" };
		int j = JOptionPane.showOptionDialog(frameMain,
				"Would You Like to Save the Report of " + textFieldCalendar.getText() + ".", "Save Report", 0, 3, null,
				arrayOfObject, arrayOfObject[0]);

		if (j == 0) {
			Connection con = null;
			PreparedStatement stat = null;
			ResultSet rs = null;
			int affected = 0;
			java.sql.Date localDate = SolDateFormatter.DDMMYYtoSQL(textFieldCalendar.getText());
			con = DBConnectionUtil.getConnection();
			int m = 0;
			try {
				stat = con.prepareStatement("select count(emp_code) from HRTIMEMASTER where emp_code= ? and date=?");
				stat.setString(1, empCode);
				stat.setDate(2, localDate);
				rs = stat.executeQuery();
				while (rs.next()) {
					m = rs.getInt(1);
					System.out.println("******************************************");
					System.out.println("Emp Code : " + empCode + "\t" + m);
					System.out.println("******************************************");
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}

			try {

				for (int k = 0; k < i; k++) {
					float otHrsFloat = 0.0F;
					String otType = "0";
					float deductOtFloat = 0.0F;
					String deductRemarks = "0";
					String otRemarks = "0";

					dateString = String.valueOf(defaultTableModelMain.getValueAt(k, 0));
					dateString.substring(0, 2);
					dateString.substring(3, 5);
					dateString.substring(6, 10);

					punchCard = String.valueOf(defaultTableModelMain.getValueAt(k, 1));
					empCode = String.valueOf(defaultTableModelMain.getValueAt(k, 2));
					empName = String.valueOf(defaultTableModelMain.getValueAt(k, 3));
					
					if(empName.contains("-")) {
						int index = empName.indexOf("-");
						empName = empName.substring(index+1, empName.length());
					}
					
					inTime = String.valueOf(defaultTableModelMain.getValueAt(k, 4));
					outTime = String.valueOf(defaultTableModelMain.getValueAt(k, 5));
					totalTime = String.valueOf(defaultTableModelMain.getValueAt(k, 6));
					projectNo = String.valueOf(defaultTableModelMain.getValueAt(k, 7));
					dc = String.valueOf(defaultTableModelMain.getValueAt(k, 8));
					String.valueOf(defaultTableModelMain.getValueAt(k, 9));
					breakHrs = String.valueOf(defaultTableModelMain.getValueAt(k, 10));
					otHrs = String.valueOf(defaultTableModelMain.getValueAt(k, 11));
					otType = String.valueOf(defaultTableModelMain.getValueAt(k, 15));
					deductRemarks = String.valueOf(defaultTableModelMain.getValueAt(k, 16));
					otRemarks = String.valueOf(defaultTableModelMain.getValueAt(k, 12));
					ot2 = String.valueOf(defaultTableModelMain.getValueAt(k, 19));

					if (inTime == null) {
						inTime = "0";
					}
					if (outTime == null) {
						outTime = "0";
					}
					if (totalTime.length() < 2) {
						totalTime = "0";
					}
					if (dc == null) {
						dc = " ";
					}
					if ((breakHrs.length() < 2) || (breakHrs.equalsIgnoreCase("null")) || (breakHrs == null)) {
						breakHrs = "0";
					}
					if ((otHrs.length() < 2) || (otHrs.equalsIgnoreCase("null")) || (otHrs == null)) {
						otHrs = "0";
						otHrsFloat = 0.0F;
					}
					if (otHrs.length() >= 2) {
						otHrs.substring(0, otHrs.indexOf('.'));
						otHrs.substring(otHrs.indexOf('.') + 1, otHrs.length());
						otHrsFloat = Float.parseFloat(otHrs);
					}
					if (otType.length() >= 2) {
						deductOtFloat = Float.parseFloat(otType);
					}

					if (m <= 0) {
						stat = con.prepareStatement(
								"insert into HRTIMEMASTER (DATE, PUNCHCARD, EMP_CODE, EMP_NAME, IN_TIME, OUT_TIME, TOTAL_TIME, PROJECT_NO, "
								+ " D_C, UPDATED, BREAK_HRS, OT_HRS, DEDUCT_OT, DEDUCT_REMARKS, OT_REMARKS, BREAK_UPDATED, M_STATUS, OT2) "
								+ " values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ");
						stat.setDate(1, localDate);
						stat.setString(2, punchCard.trim());
						stat.setString(3, empCode.trim());
						stat.setString(4, empName);
						stat.setString(5, inTime.trim());
						stat.setString(6, outTime.trim());
						stat.setString(7, totalTime.trim());
						stat.setString(8, projectNo.trim());
						stat.setString(9, dc.trim());
						stat.setString(10, "0");
						stat.setString(11, breakHrs.trim());
						stat.setString(12, String.valueOf(otHrsFloat).trim());
						stat.setString(13, String.valueOf(deductOtFloat).trim());
						stat.setString(14, deductRemarks.trim());
						stat.setString(15, otRemarks.trim());
						stat.setString(16, "0");
						stat.setString(17, "0");
						stat.setString(18, ot2.trim());
						affected = stat.executeUpdate();
					}
					if (affected > 0) {
						progress.setString("DB Status : " + String.valueOf(i - 1) + "/" + String.valueOf(k) + "[" + punchCard
								+ ", " + empCode + "]");
					}
				}
			} catch (Exception localException) {
				labelDBStatus.setText(localException.getMessage().toString());
			} finally {
				DBConnectionUtil.closeConnection(rs, stat, con);
			}
			JOptionPane.showMessageDialog(frameMain, "Thanks. Record Saved.");
		}
	}

	public void updateBreakTime(int paramInt) {
		String str1 = String.valueOf(defaultTableModelMain.getValueAt(paramInt, 0));
		String str2 = str1.substring(0, 2);
		String str3 = str1.substring(3, 5);
		String str4 = str1.substring(6, 10);
		@SuppressWarnings("deprecation")
		java.sql.Date localDate = new java.sql.Date(Integer.parseInt(str4) - 1900, Integer.parseInt(str3) - 1,
				Integer.parseInt(str2));

		String.valueOf(defaultTableModelMain.getValueAt(paramInt, 1));
		String str6 = String.valueOf(defaultTableModelMain.getValueAt(paramInt, 2));
		String.valueOf(defaultTableModelMain.getValueAt(paramInt, 3));
		String.valueOf(defaultTableModelMain.getValueAt(paramInt, 4));
		String.valueOf(defaultTableModelMain.getValueAt(paramInt, 5));
		String str10 = String.valueOf(defaultTableModelMain.getValueAt(paramInt, 6));
		String.valueOf(defaultTableModelMain.getValueAt(paramInt, 7));
		String.valueOf(defaultTableModelMain.getValueAt(paramInt, 8));
		String.valueOf(defaultTableModelMain.getValueAt(paramInt, 9));

		Float.parseFloat(String.valueOf(defaultTableModelMain.getValueAt(paramInt, 6)));
		float f2 = Float.parseFloat(String.valueOf(defaultTableModelMain.getValueAt(paramInt, 10)));
		float f3 = Float.parseFloat(String.valueOf(defaultTableModelMain.getValueAt(paramInt, 11)));
		float f4 = Float.parseFloat(String.valueOf(defaultTableModelMain.getValueAt(paramInt, 15)));

		if (f2 > 0.0F) {
			Connection con = null;
			PreparedStatement stat = null;
			try {
				con = DBConnectionUtil.getConnection();
				stat = con.prepareStatement(
						"update HRTIMEMASTER set BREAK_HRS =?, TOTAL_TIME =?, DEDUCT_OT=? , OT_HRS = ?, BREAK_updated = '1' where EMP_CODE='"
								+ str6 + "' and DATE= ? and BREAK_updated='0'  ");
				stat.setFloat(1, f2);
				stat.setString(2, str10);
				stat.setFloat(3, f4);
				stat.setFloat(4, f3);
				stat.setDate(5, localDate);
				int affected = stat.executeUpdate();

				if (affected > 0) {
					progress.setString("DB Status : Break Time Updating for " + str6);
				}
			} catch (Exception localException) {
				progress.setString("DB Status : " + localException.getMessage().toString());
			} finally {
				DBConnectionUtil.closeConnection(stat, con);
			}
		}
	}

	public void update() {
		int i = tableMain.getSelectedRow();

		String str1 = String.valueOf(defaultTableModelMain.getValueAt(i, 0));
		java.sql.Date localDate = SolDateFormatter.DDMMYYtoSQL(str1);

		String.valueOf(defaultTableModelMain.getValueAt(i, 1));
		String str3 = String.valueOf(defaultTableModelMain.getValueAt(i, 2));
		String.valueOf(defaultTableModelMain.getValueAt(i, 3));
		String str5 = String.valueOf(defaultTableModelMain.getValueAt(i, 4));
		String str6 = String.valueOf(defaultTableModelMain.getValueAt(i, 5));
		String str7 = String.valueOf(defaultTableModelMain.getValueAt(i, 6));
		String str8 = String.valueOf(defaultTableModelMain.getValueAt(i, 7));
		String str9 = String.valueOf(defaultTableModelMain.getValueAt(i, 8));
		String str10 = String.valueOf(defaultTableModelMain.getValueAt(i, 9));
		float f1 = Float.parseFloat(String.valueOf(defaultTableModelMain.getValueAt(i, 10)));
		float f2 = Float.parseFloat(String.valueOf(defaultTableModelMain.getValueAt(i, 11)));
		float f3 = Float.parseFloat(String.valueOf(defaultTableModelMain.getValueAt(i, 15)));
		float f4 = Float.parseFloat(String.valueOf(defaultTableModelMain.getValueAt(i, 19)));

		System.out.println("OT2 : " + f4);

		if (SessionUtil.getLoginInfo().getUserType().equalsIgnoreCase("Admin")) {
			str10 = "2";
		}
		if (SessionUtil.getLoginInfo().getUserType().equalsIgnoreCase("User")) {
			str10 = "1";
		}

		Connection con = null;
		PreparedStatement stat = null;
		try {
			con = DBConnectionUtil.getConnection();
			stat = con.prepareStatement("update HRTIMEMASTER set IN_TIME='" + str5 + "', OUT_TIME='" + str6
					+ "', TOTAL_TIME='" + str7 + "', PROJECT_NO='" + str8 + "', D_C='" + str9 + "', UPDATED='" + str10
					+ "', ot_hrs=? , deduct_ot =?, break_hrs =?, ot2=?  where EMP_CODE='" + str3 + "' and DATE= ? ");
			stat.setString(1, String.valueOf(f2));
			stat.setString(2, String.valueOf(f3));
			stat.setFloat(3, f1);
			stat.setFloat(4, f4);
			stat.setDate(5, localDate);
			int affected = stat.executeUpdate();

			if (affected > 0) {
				progress.setString("DB Status : Record Updated.");
				defaultTableModelMain.setValueAt(str10, i, 9);
			}
		} catch (Exception localException) {
			progress.setString("DB Status : " + localException.getMessage().toString());
		} finally {
			DBConnectionUtil.closeConnection(stat, con);
		}
	}

	public void updateOTRemarks() {
		int i = tableMain.getSelectedRow();

		String str1 = String.valueOf(defaultTableModelMain.getValueAt(i, 0));
		java.sql.Date localDate = SolDateFormatter.DDMMYYtoSQL(str1);

		String str2 = String.valueOf(defaultTableModelMain.getValueAt(i, 2));
		String str3 = String.valueOf(defaultTableModelMain.getValueAt(i, 9));
		String str4 = String.valueOf(defaultTableModelMain.getValueAt(i, 12));

		if (SessionUtil.getLoginInfo().getUserType().equalsIgnoreCase("Admin")) {
			str3 = "2";
		}
		if (SessionUtil.getLoginInfo().getUserType().equalsIgnoreCase("User")) {
			str3 = "1";
		}

		Connection con = null;
		PreparedStatement stat = null;
		try {
			con = DBConnectionUtil.getConnection();
			stat = con.prepareStatement(
					"update HRTIMEMASTER set OT_REMARKS='" + str4 + "'  where EMP_CODE = ? and DATE= ? ");
			stat.setString(1, str2);
			stat.setDate(2, localDate);
			int affected = stat.executeUpdate();

			if (affected > 0) {
				progress.setString("DB Status : Record Updated.");
				defaultTableModelMain.setValueAt(str3, i, 9);
			}

		} catch (Exception localException) {
			progress.setString("DB Status : " + localException.getMessage().toString());
		} finally {
			DBConnectionUtil.closeConnection(stat, con);
		}
	}

	public void updateDeductRemarks() {
		int i = tableMain.getSelectedRow();

		String str1 = String.valueOf(defaultTableModelMain.getValueAt(i, 0));
		String str2 = str1.substring(0, 2);
		String str3 = str1.substring(3, 5);
		String str4 = str1.substring(6, 10);
		@SuppressWarnings("deprecation")
		java.sql.Date localDate = new java.sql.Date(Integer.parseInt(str4) - 1900, Integer.parseInt(str3) - 1,
				Integer.parseInt(str2));

		String str5 = String.valueOf(defaultTableModelMain.getValueAt(i, 2));
		String str6 = String.valueOf(defaultTableModelMain.getValueAt(i, 9));
		String str7 = String.valueOf(defaultTableModelMain.getValueAt(i, 16));

		if (SessionUtil.getLoginInfo().getUserType().equalsIgnoreCase("Admin")) {
			str6 = "2";
		}
		if (SessionUtil.getLoginInfo().getUserType().equalsIgnoreCase("User")) {
			str6 = "1";
		}

		Connection con = null;
		PreparedStatement stat = null;
		try {
			con = DBConnectionUtil.getConnection();
			stat = con.prepareStatement("update HRTIMEMASTER set DEDUCT_REMARKS='" + str7 + "'  where EMP_CODE='" + str5
					+ "' and DATE= ? ");
			stat.setDate(1, localDate);
			int affected = stat.executeUpdate();

			if (affected > 0) {
				progress.setString("DB Status : Record Updated.");
				defaultTableModelMain.setValueAt(str6, i, 9);
			}
		} catch (Exception localException) {
			progress.setString("DB Status : " + localException.getMessage().toString());
		} finally {
			DBConnectionUtil.closeConnection(stat, con);
		}
	}

	public void createExcelFile(String fileName) {

		String sheetName = "Sheet1";
		if (radioButtonBothEmployee.isSelected()) {
			sheetName = "Production_Trainee";
		}
		if (radioButtonProductionEmployee.isSelected()) {
			sheetName = "Production";
		}
		if (radioButtonTrainee.isSelected()) {
			sheetName = "Trainee";
		}

		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet();

		int rowSize = tableMain.getRowCount();
		int colSize = tableMain.getColumnCount();

		Row header = sheet.createRow(0);
		header.createCell(0).setCellValue("Date");
		header.createCell(1).setCellValue("Punchcard");
		header.createCell(2).setCellValue("Emp Code");
		header.createCell(3).setCellValue("Name");
		header.createCell(4).setCellValue("In Time");
		header.createCell(5).setCellValue("Out Time");
		header.createCell(6).setCellValue("Total");
		header.createCell(7).setCellValue("Project No.");
		header.createCell(8).setCellValue("D/C");
		header.createCell(9).setCellValue("Break");
		header.createCell(10).setCellValue("OT HRS");
		header.createCell(11).setCellValue("Y/N");
		header.createCell(12).setCellValue("RT.");
		header.createCell(13).setCellValue("T");
		header.createCell(14).setCellValue("D.OT");
		header.createCell(15).setCellValue("Y/N");
		header.createCell(16).setCellValue("A");
		header.createCell(17).setCellValue("OT2");

		/*
		 * for (int i = 0; i < rowSize; i++) { Row row = sheet.createRow(i+1); for (int
		 * j = 0; j < colSize; j++) { Cell cell = row.createCell(j);
		 * 
		 * cell.setCellValue(String.valueOf(tableMain.getValueAt(i, j))); } }
		 */

		String dateString = textFieldCalendar.getText();
		String splitDate[] = dateString.split("/");
		String newDateValue = splitDate[2] + "-" + splitDate[1] + "-" + splitDate[0];

		Map<String, List<AttendanceDTO>> attendanceMap = AttendanceDAO.getCompleteAttendance(newDateValue);
		Set<String> keySet = attendanceMap.keySet();

		int rowCounter = 1;
		for (String key : keySet) {
			List<AttendanceDTO> attendances = attendanceMap.get(key);
			for (AttendanceDTO dto : attendances) {
				Row row = sheet.createRow(rowCounter);
				row.createCell(0).setCellValue(dto.getDate());
				row.createCell(1).setCellValue(dto.getPunchcard());
				row.createCell(2).setCellValue(dto.getEmpCode());
				row.createCell(3).setCellValue(dto.getName());
				row.createCell(4).setCellValue(dto.getInTime());
				row.createCell(5).setCellValue(dto.getOutTime());
				row.createCell(6).setCellValue(dto.getTotal());
				row.createCell(7).setCellValue(dto.getProjectNumber());
				row.createCell(8).setCellValue(dto.getDetailingChecking().trim());
				row.createCell(9).setCellValue(dto.getBreakHrs());
				row.createCell(10).setCellValue(dto.getOtHrs());
				row.createCell(11).setCellValue(dto.getOtYesNo());
				row.createCell(12).setCellValue("");
				row.createCell(13).setCellValue(dto.getDeductOt());
				row.createCell(14).setCellValue(dto.getDeductOtYesNo());
				row.createCell(15).setCellValue(dto.getApplicable());
				row.createCell(16).setCellValue(dto.getOT2());

				rowCounter++;
			}
		}

		try {
			FileOutputStream outputStream = new FileOutputStream(fileName + ".xls");
			workbook.write(outputStream);
			workbook.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void exportReportAsExcel() {
		JFileChooser fileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
		int returnValue = fileChooser.showSaveDialog(null);
		if (returnValue == fileChooser.APPROVE_OPTION) {
			File file = fileChooser.getSelectedFile();
			createExcelFile(file.getAbsolutePath());
		}
	}

	public void actionPerformed(ActionEvent paramActionEvent) {
		/*
		 * if (paramActionEvent.getSource() == textFieldCalendar) {
		 * tableMain.setEnabled(true); showNameList(); showTodays localshowTodays1 = new
		 * showTodays(); localshowTodays1.start(); }
		 */
		if (paramActionEvent.getSource() == backbut) {
			defaultTableModelMain.setNumRows(0);
			dateX += 1;
			showLastDate();
		}
		if (paramActionEvent.getSource() == forebut) {
			defaultTableModelMain.setNumRows(0);
			dateX -= 1;
			showLastDate();
		}
		if (paramActionEvent.getSource() == tf6) {
			personalUpdatedView();
			lateCount(tableMain.getRowCount() - 2);
		}

		if ((paramActionEvent.getSource() == buttonShowFromMachine)
				|| (paramActionEvent.getSource() == miShowFromMachine)) {

			JFileChooser fileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
			int returnValue = fileChooser.showOpenDialog(null);
			if (returnValue == fileChooser.APPROVE_OPTION) {
				java.io.File selectedFile = fileChooser.getSelectedFile();
				String filePath = selectedFile.getAbsolutePath();
				System.out.println("selectedFile: " + selectedFile);

				tableMain.setEnabled(true);
				showNameList();

				showTodays localshowTodays2 = new showTodays(filePath);
				localshowTodays2.start();
			}

			/*
			 * DateCalculationUtil.isCurrentDate(textFieldCalendar.getText(),
			 * DateCalculationUtil.getCurrentDate("dd/MM/yyyy"));
			 * 
			 * tableMain.setEnabled(true); showNameList(); showTodays localshowTodays2 = new
			 * showTodays(); localshowTodays2.start();
			 */
		}
		if ((paramActionEvent.getSource() == buttonSave) || (paramActionEvent.getSource() == miSaveReport)) {
			saveRecord();
		}
		if (paramActionEvent.getSource() == buttonMount) {
			MountainAll mountainAll = new MountainAll();
			mountainAll.start();
		}
		if ((paramActionEvent.getSource() == buttonPrint) || (paramActionEvent.getSource() == miPrintReport)) {
			PrintAttendanceReport PrintAttendanceReport = new PrintAttendanceReport();
			PrintAttendanceReport.start();
		}
		if (paramActionEvent.getSource() == miExportReport) {
			exportReportAsExcel();
		}
		if (paramActionEvent.getSource() == miCleanThisDate) {
			DeleteBeforeSave();
		}
		if (paramActionEvent.getSource() == miExit) {
			frameMain.setVisible(false);
		}

		if (paramActionEvent.getSource() == miBreakTimeEntry) {
			BreakTimeEntry breakTimeEntry = new BreakTimeEntry();
			breakTimeEntry.px();
			if (tableMain.getSelectedRow() >= 0) {
				breakTimeEntry.tf1
						.setText(String.valueOf(defaultTableModelMain.getValueAt(tableMain.getSelectedRow(), 0)));
				breakTimeEntry.tf2
						.setText(String.valueOf(defaultTableModelMain.getValueAt(tableMain.getSelectedRow(), 2)));

			}
		}
		if (paramActionEvent.getSource() == miMultiProjectEntry) {
			MultiProject multiProject = new MultiProject();
			multiProject.px();
			multiProject.tf1.setText(String.valueOf(defaultTableModelMain.getValueAt(tableMain.getSelectedRow(), 0)));
			multiProject.punchtf
					.setText(String.valueOf(defaultTableModelMain.getValueAt(tableMain.getSelectedRow(), 1)));
			multiProject.tf2.setText(String.valueOf(defaultTableModelMain.getValueAt(tableMain.getSelectedRow(), 2)));
			multiProject.tf4.setText(String.valueOf(defaultTableModelMain.getValueAt(tableMain.getSelectedRow(), 6)));
			multiProject.breakTF
					.setText(String.valueOf(defaultTableModelMain.getValueAt(tableMain.getSelectedRow(), 10)));
			multiProject.empDetails();
			multiProject.showMultiDRG();
			multiProject.tf5.requestFocus();
			multiProject.tf5.selectAll();
		}

		if (paramActionEvent.getSource() == miTimeCalculator) {
			TimeCalc timeCalc = new TimeCalc();
			timeCalc.showFrame();
		}
		if (paramActionEvent.getSource() == miFindOption) {
			SolTableFinder solTableFinder = new SolTableFinder();
			solTableFinder.showFinder();
			solTableFinder.setAutoSelectedText(tableMain, defaultTableModelMain);
			solTableFinder.SearchInThisTable(tableMain, defaultTableModelMain);
		}
		if (paramActionEvent.getSource() == miRecalculate) {
			Object[] localObject = new Object[] { "Yes", "No Thanks" };
			int i = JOptionPane.showOptionDialog(frameMain,
					"This Function is Use to ReCalculat the Entities from Top to Bottom. "
							+ "\n In Case of Major Mistakes. Would you Like to Procced. "
							+ "\n This is Good to Contact to your ERP. Manager",
					"ReCalculation", 0, 3, null, (Object[]) localObject, localObject);

			String dateString = textFieldCalendar.getText();
			breakTimeMap = localBreakTimeEntry.getTotalBreakTime(dateString);
			labelNameOfDay.setText(DateCalculationUtil.NameOfDay(String.valueOf(dateString)));

			if (i == 0) {
				for (int j = 0; j < tableMain.getRowCount(); j++) {
					checkTotalTime();
				}
				for (int j = 0; j < tableMain.getRowCount(); j++) {
					showBreakTime(j);
				}

				Connection con = null;
				try {
					con = DBConnectionUtil.getConnection();
					for (int j = 0; j < tableMain.getRowCount(); j++) {
						setParcelOTHrs(j, con);
					}
				} catch (Exception ex) {
					ex.printStackTrace();
				} finally {
					DBConnectionUtil.closeConnection(null, null, con);
				}

				// for (int j = 0; j < tableMain.getRowCount(); j++) {
				update();
				// }

				JOptionPane.showMessageDialog(frameMain, "ReCalculation Completed.");
			}
			if (i == 1) {
				JOptionPane.showMessageDialog(frameMain, "ReCalculation Canceled.");
			}
		}

		if (paramActionEvent.getSource() == buttonUpdate) {
			updatedView("X");
			showOFFDAYTypes();

			buttonClear.setEnabled(true);
			tableMain.setEnabled(true);
		}
		if (paramActionEvent.getSource() == radioButtonProductionEmployee) {
			updatedView("2");
			lateCount(tableMain.getRowCount());
		}
		if (paramActionEvent.getSource() == radioButtonTrainee) {
			updatedView("0");
			lateCount(tableMain.getRowCount());
		}
		if (paramActionEvent.getSource() == radioButtonBothEmployee) {
			updatedView("X");
			lateCount(tableMain.getRowCount());
		}
	}

	public Attendance() {

	}

	public void keyPressed(KeyEvent paramKeyEvent) {
		progress.setString("DB Status : ");
	}

	public void keyReleased(KeyEvent paramKeyEvent) {
		if (paramKeyEvent.getSource() == tableMain) {
			int keyCode = paramKeyEvent.getKeyCode();
			int selectedColumn = tableMain.getSelectedColumn();
			int selectedRow;

			if (keyCode == KeyEvent.VK_TAB) {
				if (selectedColumn >= 5) {
					selectedRow = tableMain.getSelectedRow();
					checkTotalTime();

					String dateString = textFieldCalendar.getText();
					breakTimeMap = localBreakTimeEntry.getTotalBreakTime(dateString);
					labelNameOfDay.setText(DateCalculationUtil.NameOfDay(String.valueOf(dateString)));

					showBreakTime(tableMain.getSelectedRow());
					Connection con = null;
					try {
						con = DBConnectionUtil.getConnection();
						setParcelOTHrs(selectedRow, con);
					} catch (Exception ex) {
						ex.printStackTrace();
					} finally {
						DBConnectionUtil.closeConnection(null, null, con);
					}
					update();

					float f1 = Float.parseFloat(String.valueOf(defaultTableModelMain.getValueAt(selectedRow, 6)));
					float f2 = Float.parseFloat(String.valueOf(tableModelShiftDefinition.getValueAt(0, 12)));
					if (f1 < f2) {
						Connection con1 = null;
						try {
							markAbsent(selectedRow, con1);
						} catch (Exception ex) {
							ex.printStackTrace();
						} finally {
							DBConnectionUtil.closeConnection(null, null, con1);
						}
					}
					if (f1 >= f2) {
						markPresent(selectedRow);
					}
				}
			}
			if (keyCode == KeyEvent.VK_F9) {
				for (selectedRow = 0; selectedRow < tableMain.getRowCount(); selectedRow++) {
					checkTotalTime();

					String dateString = textFieldCalendar.getText();
					breakTimeMap = localBreakTimeEntry.getTotalBreakTime(dateString);
					labelNameOfDay.setText(DateCalculationUtil.NameOfDay(String.valueOf(dateString)));

					showBreakTime(selectedRow);
					Connection con = null;
					try {
						setParcelOTHrs(selectedRow, con);
					} catch (Exception ex) {
						ex.printStackTrace();
					} finally {
						DBConnectionUtil.closeConnection(null, null, con);
					}
					update();
				}
			}

			if (keyCode == KeyEvent.VK_INSERT) {
				update();
			}
			if (keyCode == KeyEvent.VK_F5) {
				BreakTimeEntry breakTimeEntry = new BreakTimeEntry();
				breakTimeEntry.px();
				if (tableMain.getSelectedRow() >= 0) {
					breakTimeEntry.tf1
							.setText(String.valueOf(defaultTableModelMain.getValueAt(tableMain.getSelectedRow(), 0)));
					breakTimeEntry.tf2
							.setText(String.valueOf(defaultTableModelMain.getValueAt(tableMain.getSelectedRow(), 2)));
				}
			}
			if (keyCode == KeyEvent.VK_F4) {
				MultiProject multiProject = new MultiProject();
				multiProject.px();
				multiProject.tf1
						.setText(String.valueOf(defaultTableModelMain.getValueAt(tableMain.getSelectedRow(), 0)));
				multiProject.punchtf
						.setText(String.valueOf(defaultTableModelMain.getValueAt(tableMain.getSelectedRow(), 1)));
				multiProject.tf2
						.setText(String.valueOf(defaultTableModelMain.getValueAt(tableMain.getSelectedRow(), 2)));
				multiProject.tf4
						.setText(String.valueOf(defaultTableModelMain.getValueAt(tableMain.getSelectedRow(), 6)));
				multiProject.breakTF
						.setText(String.valueOf(defaultTableModelMain.getValueAt(tableMain.getSelectedRow(), 10)));
				multiProject.empDetails();
				multiProject.tf5.requestFocus();
				multiProject.tf5.selectAll();
				multiProject.showMultiDRG();
			}
			if (keyCode == KeyEvent.VK_F11) {
				TimeCalc timeCalc = new TimeCalc();
				timeCalc.showFrame();
			}

			if (keyCode == KeyEvent.VK_F1) {
				SolTableFinder solTableFinder = new SolTableFinder();
				solTableFinder.showFinder();
				solTableFinder.setAutoSelectedText(tableMain, defaultTableModelMain);
				solTableFinder.SearchInThisTable(tableMain, defaultTableModelMain);
			}
		}
		if (paramKeyEvent.getSource() == tf6) {
			int keyCode = paramKeyEvent.getKeyCode();
			if (keyCode == KeyEvent.VK_F1) {
				HelpTable helpTable = new HelpTable();
				helpTable.showFrame();
				helpTable.getEmpCode(tf6);
			}
		}
		if ((paramKeyEvent.getSource() == textFieldCompanyId) || (paramKeyEvent.getSource() == textFieldBranchCode)) {
			int keyCode = paramKeyEvent.getKeyCode();
			if (keyCode == KeyEvent.VK_F1) {
				CompanyTable companyTable = new CompanyTable();
				companyTable.showFrame();
				companyTable.getCompanyId(textFieldCompanyId);
				companyTable.getCompanyName(textFieldCompanyName);
				companyTable.getBranchCode(textFieldBranchCode);
			}
		}
	}

	public void focusGained(FocusEvent paramFocusEvent) {
		if (paramFocusEvent.getSource() == comboBoxProject) {
		}
	}

	public void focusLost(FocusEvent paramFocusEvent) {
		if (paramFocusEvent.getSource() == comboBoxProject) {
			JOptionPane.showMessageDialog(frameMain, "OK. I am Fine.");
		}
	}

	public void mousePressed(MouseEvent paramMouseEvent) {
		if (paramMouseEvent.getSource() == textFieldCalendar) {
			if (!SwingUtilities.isRightMouseButton(paramMouseEvent)) {
			}
		}
	}

	public void mouseClicked(MouseEvent paramMouseEvent) {
		if (paramMouseEvent.getSource() == tableMain) {
			int i = tableMain.getSelectedColumn();
			if (i == 11) {
				updateOTRemarks();
			}
			if (i == 15) {
				updateDeductRemarks();
			}
			if (i < 0) {
			}
		}
	}

	public void clearOTHrs() {
	}

	public void keyTyped(KeyEvent paramKeyEvent) {
	}

	public void tableChanged(TableModelEvent paramTableModelEvent) {
	}

	public void TableCellUpdated(TableModelEvent paramTableModelEvent) {
	}

	public void mouseReleased(MouseEvent paramMouseEvent) {
	}

	public void mouseEntered(MouseEvent paramMouseEvent) {
	}

	public void mouseExited(MouseEvent paramMouseEvent) {
	}
}