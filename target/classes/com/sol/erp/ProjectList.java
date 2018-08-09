package com.sol.erp;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.print.PrinterException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import com.sol.erp.constants.ApplicationConstants;
import com.sol.erp.dao.ProjectDAO;
import com.sol.erp.ui.custom.SolTableModel;
import com.sol.erp.util.DBConnectionUtil;
import com.sol.erp.util.formater.SolDateFormatter;

public class ProjectList implements java.awt.event.ActionListener, java.awt.event.ItemListener {

	String[] columnNames;
	Object[][] data;
	DecimalFormat df;
	DecimalFormat df3;
	DecimalFormat df4;
	DefaultTableModel model;
	JTable tb;
	JScrollPane sp;
	JInternalFrame f;
	JProgressBar progress;
	JLabel l1;
	JLabel l2;
	JComboBox cb1;
	JComboBox cb2;
	JTextField fromtf;
	JTextField totf;
	JButton b1;
	JButton b2;
	JButton b3;
	JButton b4;
	JButton searchbut;
	JMenuBar mb;
	JMenu menu1;
	JMenu menu2;
	JMenu menu3;
	JMenuItem mi1;
	JMenuItem mi2;
	JMenuItem mi3;
	JMenuItem mi4;
	JMenuItem mb1;
	JMenuItem mb2;
	JMenuItem mc1;
	JMenuItem mc2;
	JMenuItem mc3;
	JRadioButton check1;
	JRadioButton check2;
	JCheckBox effcheckbox;
	ButtonGroup grp;
	JPanel centerpanel;
	JPanel butpanel;
	JPanel southpanel;
	Font fo;
	Border line;
	Border bor1;
	TableColumn col3;
	TableColumn col6;
	TableColumn col7;
	TableColumn col8;
	Toolkit tk;
	Dimension ss;
	Dimension fs;
	java.util.Date today;
	SimpleDateFormat formater;
	String datestr;

	ColoredTableCellRenderer skr;
	Container c;

	class ColoredTableCellRenderer extends DefaultTableCellRenderer {
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
				setBackground(new Color(240, 240, 240));
				setForeground(Color.darkGray);

			} else {
				setBackground(new Color(230, 230, 230));
				setForeground(Color.darkGray);
			}

			if (paramInt2 < 4) {
				setHorizontalAlignment(2);
			}
			if (paramInt2 >= 4) {
				setHorizontalAlignment(4);
			}

			String str = String.valueOf(model.getValueAt(paramInt1, 10));
			if (paramInt2 == 8) {
				if (!str.equalsIgnoreCase("0")) {

					setForeground(Color.red);
				}
			}

			if (paramInt2 == 11) {
			}

			super.getTableCellRendererComponent(paramJTable, paramObject, paramBoolean1, paramBoolean2, paramInt1,
					paramInt2);

			return this;
		}
	}

	public ProjectList() {

		columnNames = new String[] { "DATE", "<HTML><CENTER>PROJECT<BR>NO.", "PROJECT NAME", "CLIENT", "TL",
				"<HTML><CENTER>START<BR>DATE", "<HTML><CENTER>FINAL<BR>DATE", "<HTML><CENTER>EST.<BR>HRS.",
				"<HTML><CENTER>EST.<BR>SHEET", "<HTML><CENTER>FINAL<BR>DATE", "##", "<HTML><CENTER>SHEET<BR>STATUS",
				"<HTML><CENTER>COMPLETED JOB Hrs/<br>EFF. Used Hrs", "EFF." };
		data = new Object[0][];

		df = new DecimalFormat("00.00");
		df3 = new DecimalFormat("0");
		df4 = new DecimalFormat("0.0");

		model = new DefaultTableModel(data, columnNames);

		tb = new JTable(model) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@SuppressWarnings({ "unchecked", "rawtypes" })
			public Class getColumnClass(int paramAnonymousInt) {
				return getValueAt(0, paramAnonymousInt).getClass();
			}

			public boolean isCellEditable(int paramAnonymousInt1, int paramAnonymousInt2) {
				int i = convertColumnIndexToModel(paramAnonymousInt2);
				return i < 0;
			}
		};
		sp = new JScrollPane(tb);

		f = new JInternalFrame("Project List", true, true, true, true);

		progress = new JProgressBar();

		l1 = new JLabel("Search");
		l2 = new JLabel("Client List");

		cb1 = new JComboBox();
		cb2 = new JComboBox();

		fromtf = new JTextField(10);
		totf = new JTextField(10);

		b1 = new JButton("Common Report",
				new ImageIcon(ClassLoader.getSystemResource(ApplicationConstants.IMAGE_PATH + "play.gif")));
		b2 = new JButton("Close",
				new ImageIcon(ClassLoader.getSystemResource(ApplicationConstants.IMAGE_PATH + "bigclose.gif")));
		b3 = new JButton("Save As HTML",
				new ImageIcon(ClassLoader.getSystemResource(ApplicationConstants.IMAGE_PATH + "save.gif")));
		b4 = new JButton("Execute");

		searchbut = new JButton("Advance Search");

		mb = new JMenuBar();
		menu1 = new JMenu("File");
		menu2 = new JMenu("Report Type");
		menu3 = new JMenu("Process Control");

		mi1 = new JMenuItem("Advance Search");
		mi2 = new JMenuItem("Effective Used Hrs");
		mi3 = new JMenuItem("Print");
		mi4 = new JMenuItem("Close");

		mb1 = new JMenuItem("Report with No Period Effect");
		mb2 = new JMenuItem("Report with Period Effect");

		mc1 = new JMenuItem("Stop Process");
		mc2 = new JMenuItem("Resume Process");
		mc3 = new JMenuItem("Restart Process");

		check1 = new JRadioButton("All Projects Between the Date", false);
		check2 = new JRadioButton("Projects Started Between the Date", true);
		effcheckbox = new JCheckBox("Effective Hrs Reports");

		grp = new ButtonGroup();

		centerpanel = new JPanel();
		butpanel = new JPanel();
		southpanel = new JPanel();

		fo = new Font("Tahoma", 1, 11);
		line = BorderFactory.createLineBorder(Color.darkGray);
		bor1 = BorderFactory.createTitledBorder(line, "Search By", 0, 0, fo, Color.darkGray);

		col3 = tb.getColumnModel().getColumn(2);
		col6 = tb.getColumnModel().getColumn(5);
		col7 = tb.getColumnModel().getColumn(6);
		col8 = tb.getColumnModel().getColumn(7);

		tk = Toolkit.getDefaultToolkit();
		ss = tk.getScreenSize();
		fs = f.getSize();

		today = new java.util.Date();
		formater = new SimpleDateFormat("dd/MM/yyyy");
		datestr = null;

		try {
			datestr = formater.format(today);
			fromtf.setText(datestr);
			totf.setText(datestr);
		} catch (IllegalArgumentException localIllegalArgumentException) {
			System.out.println(localIllegalArgumentException.getMessage());
		}

		skr = new ColoredTableCellRenderer();
	}

	public void px() {
		c = f.getContentPane();
		c.setLayout(new BorderLayout());
		centerpanel.setLayout(new BorderLayout());
		butpanel.setLayout(new FlowLayout(0));
		southpanel.setLayout(new FlowLayout(0));

		grp.add(check1);
		grp.add(check2);

		SolTableModel.decorateTable(tb);
		tb.setShowGrid(false);
		tb.setAutoscrolls(false);
		tb.setBackground(Color.white);
		tb.setIntercellSpacing(new Dimension(1, 1));
		tb.setRowHeight(26);
		tb.setFont(fo);
		tb.setRowMargin(2);
		tb.setShowVerticalLines(false);
		tb.getTableHeader().setPreferredSize(new Dimension(45, 45));
		tb.setSelectionBackground(Color.orange);
		tb.setSelectionForeground(Color.darkGray);

		tb.getTableHeader().setFont(new Font("Verdana", 1, 10));

		tb.setAutoCreateRowSorter(true);

		for (int i = 0; i < tb.getColumnCount(); i++) {
			tb.getColumnModel().getColumn(i).setCellRenderer(skr);
		}

		tb.getColumnModel().getColumn(0).setPreferredWidth(90);
		tb.getColumnModel().getColumn(1).setPreferredWidth(70);
		tb.getColumnModel().getColumn(2).setPreferredWidth(180);
		tb.getColumnModel().getColumn(3).setPreferredWidth(90);
		tb.getColumnModel().getColumn(4).setPreferredWidth(50);
		tb.getColumnModel().getColumn(5).setPreferredWidth(90);
		tb.getColumnModel().getColumn(6).setPreferredWidth(90);
		tb.getColumnModel().getColumn(7).setPreferredWidth(60);
		tb.getColumnModel().getColumn(8).setPreferredWidth(60);
		tb.getColumnModel().getColumn(9).setPreferredWidth(90);
		tb.getColumnModel().getColumn(10).setPreferredWidth(30);
		tb.getColumnModel().getColumn(11).setPreferredWidth(90);
		tb.getColumnModel().getColumn(12).setPreferredWidth(150);
		tb.getColumnModel().getColumn(13).setPreferredWidth(40);

		tb.removeColumn(tb.getColumnModel().getColumn(0));
		tb.removeColumn(tb.getColumnModel().getColumn(5));

		cb1.setToolTipText(
				"<Html><Font color='red' size=5>You Can Type Project Name OR Project No. to<BR> List out the Project");

		menu1.add(mi1);

		menu1.addSeparator();
		menu1.add(mi3);
		menu1.addSeparator();
		menu1.add(mi4);

		mb.add(menu1);

		menu2.add(mb1);
		menu2.addSeparator();
		menu2.add(mb2);

		menu3.add(mc1);
		menu3.add(mc2);
		menu3.addSeparator();
		menu3.add(mc3);

		f.setJMenuBar(mb);

		butpanel.add(new JLabel("Date From"));
		butpanel.add(fromtf);
		butpanel.add(new JLabel("To"));
		butpanel.add(totf);

		butpanel.add(check1);
		butpanel.add(check2);
		butpanel.add(b4);
		b4.setMnemonic(80);
		butpanel.add(effcheckbox);

		progress.setStringPainted(true);
		progress.setPreferredSize(new Dimension(400, 22));

		cb1.setPreferredSize(new Dimension(280, 20));
		cb1.setFont(new Font("Verdana", 0, 11));
		cb2.setFont(new Font("Verdana", 0, 11));

		southpanel.add(l1);
		southpanel.add(cb1);
		cb1.setEditable(true);
		southpanel.add(l2);
		southpanel.add(cb2);
		cb2.setEditable(true);

		c.add(butpanel, "North");
		c.add(sp, "Center");
		c.add(southpanel, "South");

		searchbut.addActionListener(this);
		b1.addActionListener(this);
		b2.addActionListener(this);
		b3.addActionListener(this);
		b4.addActionListener(this);

		mi1.addActionListener(this);
		mi2.addActionListener(this);
		mi3.addActionListener(this);
		mi4.addActionListener(this);

		mb1.addActionListener(this);
		mb2.addActionListener(this);

		cb1.addItemListener(this);
		cb2.addItemListener(this);

		cb1.addActionListener(this);
		cb2.addActionListener(this);

		check1.addActionListener(this);
		check2.addActionListener(this);
		effcheckbox.addActionListener(this);

		f.setSize(1024, 600);
		f.setVisible(true);

		paramUser();

		tb.requestFocus();
	}

	public void paramUser() {
		cb1.setSelectedItem(TechMainFrame.textFieldLoggedBy.getText());
	}

	public void TLCode() {
		cb1.addItem("ALL");
		Connection con = null;
		Statement stat = null;
		ResultSet rs = null;
		try {
			con = DBConnectionUtil.getConnection();
			stat = con.createStatement();
			rs = stat.executeQuery(
					"select distinct(Emp_code) From emp_status where Designation='Team Leader' OR Designation='Project Manager'  ORDER BY EMP_CODE");
			while (rs.next()) {
				String str = new String(rs.getString(1));
				cb1.addItem(str.toUpperCase());
			}
		} catch (Exception localException) {
			JOptionPane.showMessageDialog(f, localException.getMessage().toString());
		} finally {
			DBConnectionUtil.closeConnection(rs, stat, con);
		}
	}

	public void clientList() {
		cb2.removeAllItems();
		cb2.addItem("ALL");
		Connection con = null;
		Statement stat = null;
		ResultSet rs = null;
		try {
			con = DBConnectionUtil.getConnection();
			stat = con.createStatement();
			rs = stat.executeQuery("select distinct(CLIENT_NAME) From ESTIMATION_MP ORDER BY CLIENT_NAME");
			while (rs.next()) {
				String str = new String(rs.getString(1));
				cb2.addItem(str.toUpperCase());
			}
		} catch (Exception localException) {
			JOptionPane.showMessageDialog(f, localException.getMessage().toString());
		} finally {
			DBConnectionUtil.closeConnection(rs, stat, con);
		}
	}

	public void projectList(String paramString1, String paramString2) {
		model.setNumRows(0);
		String str1 = "select * from project_co " + paramString1 + " order by " + paramString2;

		Connection con = null;
		Statement stat = null;
		ResultSet rs = null;

		try {
			con = DBConnectionUtil.getConnection();
			stat = con.createStatement();
			rs = stat.executeQuery(str1);

			while (rs.next()) {
				java.sql.Date localDate1 = rs.getDate(1);
				int i = Integer.parseInt(rs.getString(2));
				String str2 = new String(rs.getString(3));
				String str3 = new String(rs.getString(4));
				String str4 = new String(rs.getString(5));
				new String(rs.getString(6));
				new String(rs.getString(7));
				java.sql.Date localDate2 = rs.getDate(8);
				java.sql.Date localDate3 = rs.getDate(9);

				int j = Integer.parseInt(df3.format(rs.getFloat(10)));
				int k = rs.getInt(11);
				java.sql.Date localDate4 = rs.getDate(12);
				String str7 = new String(rs.getString(13));

				model.addRow(
						new Object[] { SolDateFormatter.SQLtoDDMMYY(localDate1), Integer.valueOf(i), str2.toUpperCase(),
								str3.toUpperCase(), str4.toUpperCase(), SolDateFormatter.SQLtoDDMMYY(localDate2),
								SolDateFormatter.SQLtoDDMMYY(localDate3), Integer.valueOf(j), Integer.valueOf(k),
								SolDateFormatter.SQLtoDDMMYY(localDate4), str7, "0", "0", "0", "0" });

			}

		} catch (Exception localException) {

			System.out.println(localException);
		} finally {
			DBConnectionUtil.closeConnection(rs, stat, con);
		}
		sheetQty();
		completedDrg();
	}

	public class ProjectListBetweenPeriodService extends Thread implements Runnable {
		public ProjectListBetweenPeriodService() {
		}

		public void run() {
			b4.setEnabled(false);

			java.sql.Date localDate3 = SolDateFormatter.DDMMYYtoSQL(fromtf.getText());
			java.sql.Date localDate4 = SolDateFormatter.DDMMYYtoSQL(totf.getText());

			System.out.println("Between Date : " + localDate3 + "\t" + localDate4);

			String str2 = "%" + String.valueOf(cb1.getSelectedItem());

			if (str2.equalsIgnoreCase("%All")) {
				str2 = "%";
			}

			model.setNumRows(0);

			String str3 = "select distinct(project_no) from hrtimemaster where project_no in (select project_no from project_co where CO_CODE like '"
					+ str2 + "' order by project_no) and date between '" + localDate3 + "' and '" + localDate4
					+ "' group by project_no order by PROJECT_NO ";

			Connection con = null;
			Statement stat = null;
			ResultSet rs = null;

			try {
				con = DBConnectionUtil.getConnection();
				stat = con.createStatement();
				rs = stat.executeQuery(str3);

				int i = rs.getRow();
				progress.setMaximum(i);
				progress.setMinimum(0);
				int j = 0;
				while (rs.next()) {
					j += 1;
					
					progress.setValue(j);
					progress.setStringPainted(true);
					
					String str4 = rs.getString(1).trim();
					String str5 = ProjectDAO.getProjectName(str4);
					String str6 = ProjectDAO.getClientName(str4);
					String str7 = ProjectDAO.getTeamLeader(str4);
					EmpTB.getDesig(str7);
					EmpTB.getEmpName(str7);
					java.sql.Date localDate5 = ProjectDAO.getProjectMinDate(str4);
					java.sql.Date localDate6 = ProjectDAO.getProjectMaxDate(str4);

					int k = Integer.parseInt(df3.format(ProjectDAO.getProjectEstimatedHrs(str4)));
					int m = ProjectDAO.getCountSheet(str4.trim(), "");
					ProjectDAO.getFinalDate(str4);
					String str11 = "0";

					model.addRow(new Object[] { SolDateFormatter.SQLtoDDMMYY(localDate3),
							Integer.valueOf(Integer.parseInt(str4)), str5.toUpperCase(), str6.toUpperCase(),
							str7.toUpperCase(), SolDateFormatter.SQLtoDDMMYY(localDate5),
							SolDateFormatter.SQLtoDDMMYY(localDate6), Integer.valueOf(k), Integer.valueOf(m),
							SolDateFormatter.SQLtoDDMMYY(localDate6), str11, "0", "0", "0", "0" });

				}

			} catch (Exception localException) {
				System.out.println("ProjectListBetweenPeriod : " + localException);
			} finally {
				DBConnectionUtil.closeConnection(rs, stat, con);
			}
			completedPercent();
			b4.setEnabled(true);
		}
	}

	public void sheetQty() {
		int i = tb.getRowCount();

		int j = 0;
		int k = 0;

		Connection con = null;
		Statement stat = null;
		ResultSet rs = null;

		try {
			con = DBConnectionUtil.getConnection();
			for (int m = 0; m < i; m++) {
				String str1 = String.valueOf(model.getValueAt(m, 1));
				stat = con.createStatement();
				rs = stat.executeQuery("select sum(sheet_qty) from ESTIMATION where project_no='" + str1 + "' ");

				while (rs.next()) {
					String str2 = new String(rs.getString(1));
					float f1 = Float.parseFloat(str2);

					model.setValueAt(String.valueOf(df3.format(ProjectDAO.getProjectEstimatedHrs(str1)
							+ ProjectDAO.getProjectRevisionHrs(str1).floatValue())), m, 7);
					model.setValueAt(String.valueOf(df3.format(f1)), m, 8);

					int n = Integer.parseInt(String.valueOf(model.getValueAt(m, 7)));
					int i1 = Integer.parseInt(String.valueOf(model.getValueAt(m, 8)));

					j += n;
					k += i1;
				}
			}
		} catch (Exception ex) {
			System.out.println(ex);
		} finally {
			DBConnectionUtil.closeConnection(rs, stat, con);
		}
		model.addRow(new Object[] { "", "TOTAL : " + String.valueOf(tb.getRowCount()), "", "", "", "", "0",
				String.valueOf(j), String.valueOf(k), "0", "0", "0", "0", "0", "0", "0" });
	}

	public void completedDrg() {
		int i = tb.getRowCount();
		float f1 = 0.0F;

		Connection con = null;
		Statement stat = null;
		ResultSet rs = null;
		try {
			con = DBConnectionUtil.getConnection();
			for (int j = 0; j < i; j++) {
				String str1 = String.valueOf(model.getValueAt(j, 1));
				
				stat = con.createStatement();
				rs = stat.executeQuery(
						"select count(drg_no) from timesheet where project_no='" + str1 + "' and drgstatus1='Check' ");

				while (rs.next()) {
					String str2 = new String(rs.getString(1));
					model.setValueAt(str2, j, 11);
				}

				float f4 = Float.parseFloat(String.valueOf(model.getValueAt(j, 8)));
				float f5 = Float.parseFloat(String.valueOf(model.getValueAt(j, 11)));
				float f6 = f5 / f4 * 100.0F;

				f1 += f5;

				if (f6 > 90.0F) {
					model.setValueAt("<html><font Color='blue'>" + String.valueOf(df3.format(f5)) + " ("
							+ String.valueOf(df3.format(f6)) + "%)", j, 11);
				}
				if ((f6 >= 70.0F) && (f6 < 90.0F)) {
					model.setValueAt("<html><font Color='green'>" + String.valueOf(df3.format(f5)) + " ("
							+ String.valueOf(df3.format(f6)) + "%)", j, 11);
				}
				if (f6 < 70.0F) {
					model.setValueAt("<html><font Color='red'>" + String.valueOf(df3.format(f5)) + " ("
							+ String.valueOf(df3.format(f6)) + "%)", j, 11);
				}
			}
		} catch (Exception ex) {
			DBConnectionUtil.closeConnection(rs, stat, con);
		}

		float f2 = Float.parseFloat(String.valueOf(model.getValueAt(tb.getRowCount() - 1, 8)));
		float f3 = f1 / f2 * 100.0F;
		model.setValueAt(String.valueOf(df3.format(f1)) + " (" + String.valueOf(df3.format(f3)) + "%)",
				tb.getRowCount() - 1, 11);
	}

	public void clear() {
		for (int i = 0; i < tb.getRowCount(); i++) {
			model.setValueAt("0", i, 12);
			model.setValueAt("0", i, 13);
		}
	}

	public class HrsStatus extends Thread implements Runnable {
		public HrsStatus() {
		}

		public void run() {
			
				float f1 = 0.0F;

				float f2 = 0.0F;
				float f3 = 0.0F;

				for (int i = 0; i < tb.getRowCount() - 1; i++) {

					java.sql.Date localDate1 = SolDateFormatter.DDMMYYtoSQL(fromtf.getText());
					java.sql.Date localDate2 = SolDateFormatter.DDMMYYtoSQL(totf.getText());

					String str1 = String.valueOf(model.getValueAt(i, 1));
					String.valueOf(model.getValueAt(i, 3));
					float f5 = ProjectDAO.getCompletedHrsBetweenPeriod(str1, localDate1, localDate2).floatValue();

					ProjectDAO.getProjectEstimatedHrs(str1);

					float f7 = ProjectDAO.getEffectiveBFAHrs(str1, localDate1, localDate2);
					float f8 = ProjectDAO.getEffectiveRevisionHrs(str1, localDate1, localDate2);

					f1 = ProjectDAO.getEffectiveUsedHrsBetweenPeriod(str1, "", localDate1, localDate2);
					f1 = f1 + f7 + f8;

					ProjectDAO.getProjectBFAHrs(str1).floatValue();
					ProjectDAO.getProjectRevisionHrs(str1).floatValue();

					float f11 = f5 / f1;

					f3 += f1;
					f2 += f5;

					model.setValueAt(String.valueOf(df3.format(f5)) + "/" + String.valueOf(df3.format(f1)), i, 12);
					model.setValueAt(String.valueOf(df4.format(f11)), i, 13);
					f5 = 0.0F;
					f1 = 0.0F;
					f7 = 0.0F;
					f8 = 0.0F;
					tb.setRowSelectionInterval(i, i);
				}

				float f4 = f2 / f3;
				model.setValueAt(String.valueOf(df3.format(f2)) + "/" + String.valueOf(df3.format(f3)),
						tb.getRowCount() - 1, 12);
				model.setValueAt(String.valueOf(df4.format(f4)), tb.getRowCount() - 1, 13);
				effcheckbox.setSelected(false);
				effcheckbox.setEnabled(true);
			
		}
	}

	public void completedPercent() {
		java.sql.Date localDate1 = SolDateFormatter.DDMMYYtoSQL(fromtf.getText());
		java.sql.Date localDate2 = SolDateFormatter.DDMMYYtoSQL(totf.getText());

		float f1 = 0.0F;
		float f2 = 0.0F;

		for (int i = 0; i < tb.getRowCount(); i++) {
			String str = String.valueOf(model.getValueAt(i, 1));
			int j = ProjectDAO.getCompletedDrgBetweenPeriod(str, localDate1, localDate2);
			float f3 = Float.parseFloat(String.valueOf(model.getValueAt(i, 7)));
			float f4 = Float.parseFloat(String.valueOf(model.getValueAt(i, 8)));
			float f5 = j / f4 * 100.0F;

			f1 += f4;
			f2 += f3;

			if (f5 > 90.0F) {
				model.setValueAt("<html><font Color='blue'>" + String.valueOf(df3.format(j)) + " ("
						+ String.valueOf(df3.format(f5)) + "%)", i, 11);
			}
			if ((f5 >= 70.0F) && (f5 < 90.0F)) {
				model.setValueAt("<html><font Color='green'>" + String.valueOf(df3.format(j)) + " ("
						+ String.valueOf(df3.format(f5)) + "%)", i, 11);
			}
			if (f5 < 70.0F) {
				model.setValueAt("<html><font Color='red'>" + String.valueOf(df3.format(j)) + " ("
						+ String.valueOf(df3.format(f5)) + "%)", i, 11);
			}
		}

		model.addRow(new Object[] { "", "TOTAL : " + String.valueOf(tb.getRowCount()), "", "", "", "", "0",
				String.valueOf(df3.format(f2)), String.valueOf(df3.format(f1)), "0", "0", "0", "0", "0", "0", "0" });
	}

	public class myPrint extends Thread implements Runnable {
		public myPrint() {
		}

		public void run() {
			try {
				String str = "PROJECT STATUS (" + String.valueOf(cb1.getSelectedItem()) + ")";

				if (check2.isSelected()) {
					str = "Projects Started Between Period(" + String.valueOf(cb1.getSelectedItem()) + ")";
				}
				if (check1.isSelected()) {
					str = "Projects Going within this Period(" + String.valueOf(cb1.getSelectedItem()) + ")";
				}

				MessageFormat localMessageFormat1 = new MessageFormat(str);
				MessageFormat localMessageFormat2 = new MessageFormat("- {0} -");
				tb.print(JTable.PrintMode.FIT_WIDTH, localMessageFormat1, localMessageFormat2);
			} catch (PrinterException localPrinterException) {
				System.err.println("Error printing: " + localPrinterException.getMessage());
			}
		}
	}

	public void actionPerformed(ActionEvent paramActionEvent) {
		Object localObject1;
		java.sql.Date localDate;
		Object localObject2;
		String str1;
		String str2;
		if ((paramActionEvent.getSource() == b1) || (paramActionEvent.getSource() == mb1)) {

			localObject1 = SolDateFormatter.DDMMYYtoSQL(fromtf.getText());
			localDate = SolDateFormatter.DDMMYYtoSQL(totf.getText());

			localObject2 = String.valueOf(cb1.getSelectedItem());
			str1 = String.valueOf(cb2.getSelectedItem());

			if (str1.equalsIgnoreCase("ALL")) {
				str1 = "%";
			}
			if (((String) localObject2).equalsIgnoreCase("ALL")) {
				localObject2 = "%";
			}

			str2 = "WHERE (CO_CODE like '" + (String) localObject2 + "' or Project_no like '" + (String) localObject2
					+ "' or Project_Name like '" + (String) localObject2 + "' and CLIENT_NAME like '" + str1
					+ "') and START between '" + localObject1 + "' and '" + localDate + "' ";
			projectList(str2, "Project_No");
		}
		if ((paramActionEvent.getSource() == mi1) || (paramActionEvent.getSource() == searchbut)) {
			localObject1 = SolDateFormatter.DDMMYYtoSQL(fromtf.getText());
			localDate = SolDateFormatter.DDMMYYtoSQL(totf.getText());

			localObject2 = "0";
			localObject2 = JOptionPane.showInputDialog(f, "Project No.,  TL/MP, Project Name");

			str1 = "WHERE CO_CODE like '" + (String) localObject2 + "' or Project_no like '" + (String) localObject2
					+ "' or Project_Name like '" + (String) localObject2 + "' and START between '" + localObject1
					+ "' and '" + localDate + "'";
			projectList(str1, "Project_no");

			cb2.setSelectedItem("ALL");
		}

		if ((paramActionEvent.getSource() == mi4) || (paramActionEvent.getSource() == b2)) {
			f.setVisible(false);
		}
		if (paramActionEvent.getSource() == mi3) {
			localObject1 = new myPrint();
			((myPrint) localObject1).start();
		}
		if ((paramActionEvent.getSource() == b4) || (paramActionEvent.getSource() == mb2)
				|| (paramActionEvent.getSource() == cb1) || (paramActionEvent.getSource() == cb2)) {
			if (check2.isSelected()) {
				localObject1 = SolDateFormatter.DDMMYYtoSQL(fromtf.getText());
				localDate = SolDateFormatter.DDMMYYtoSQL(totf.getText());

				localObject2 = String.valueOf(cb1.getSelectedItem());
				str1 = String.valueOf(cb2.getSelectedItem());

				if (str1.equalsIgnoreCase("ALL")) {
					str1 = "%";
				}
				if (((String) localObject2).equalsIgnoreCase("ALL")) {
					localObject2 = "%";
				}

				str2 = "WHERE (CO_CODE like '" + (String) localObject2 + "' or Project_no like '"
						+ (String) localObject2 + "' or Project_Name like '" + (String) localObject2
						+ "') and CLIENT_NAME like '" + str1 + "' and START between '" + localObject1 + "' and '"
						+ localDate + "'";
				projectList(str2, "Project_No");
			}
			if (check1.isSelected()) {
				ProjectListBetweenPeriodService projectListBetweenPeriod = new ProjectListBetweenPeriodService();
				projectListBetweenPeriod.start();
			}
		}

		if (paramActionEvent.getSource() == check1) {

			southpanel.remove(l2);
			southpanel.remove(cb2);
			southpanel.revalidate();
		}

		if (paramActionEvent.getSource() == check2) {
			southpanel.add(l2);
			southpanel.add(cb2);
			southpanel.revalidate();
		}

		if ((paramActionEvent.getSource() == mi2) || (paramActionEvent.getSource() == effcheckbox)) {
			effcheckbox.setEnabled(false);

			localObject1 = new Object[] { "Yes", "No" };
			int i = JOptionPane.showOptionDialog(f,
					"It will take some time to get the Effective value. \n Would You Like to Proceed?", "Confirm ", 0,
					3, null, (Object[]) localObject1, localObject1);
			if (i == 0) {
				tb.setEnabled(false);
				clear();
				localObject2 = new HrsStatus();
				((HrsStatus) localObject2).start();
				tb.setEnabled(true);
			}
			if (i == 1) {
			}

			for (int j = 0; j < tb.getColumnCount(); j++) {
				tb.getColumnModel().getColumn(j).setCellRenderer(skr);
			}
		}
	}

	public void itemStateChanged(ItemEvent paramItemEvent) {
		if ((paramItemEvent.getSource() != cb1) ||

				(paramItemEvent.getSource() == cb2)) {
		}
	}

}
