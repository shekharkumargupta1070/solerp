package com.sol.erp;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.print.PrinterException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import com.sol.erp.constants.ApplicationConstants;
import com.sol.erp.dao.ProjectDAO;
import com.sol.erp.ui.custom.SolCalendar;
import com.sol.erp.ui.custom.SolTableModel;
import com.sol.erp.util.DBConnectionUtil;
import com.sol.erp.util.DateDifferencesUtil;
import com.sol.erp.util.DateCalculationUtil;
import com.sol.erp.util.formater.SolDateFormatter;

public class EmpPerform2 implements ActionListener, MouseListener {
	

	Connection con;
	Statement stat;
	ResultSet rs;
	ResultSet rs1;
	int affected;
	float sheet;
	float alloted;
	float completed;
	float taken;
	float bfa;
	float rev;
	float wasted;
	float used;
	float eff;
	String[] columnNames;
	Object[][] data;
	String[] columnNames1;
	Object[][] data1;
	String empcodestr;
	String itemcodestr;
	String projectnostr;
	DecimalFormat df;
	DecimalFormat df1;
	DecimalFormat df2;
	DecimalFormat df3;
	DefaultTableModel model;
	JTable tb;
	JScrollPane sp;
	DefaultTableModel model1;
	JTable tb1;
	JScrollPane sp1;
	JSplitPane spl1;
	JFrame f;
	JFrame f1;
	JMenuBar mb;
	JMenu menu1;
	JCheckBoxMenuItem mi1;
	JCheckBoxMenuItem mi2;
	JMenuItem mi3;
	JMenuItem mi4;
	ButtonGroup group;
	JLabel fromlabel;
	JLabel tolabel;
	JLabel southl1;
	JLabel southl2;
	JTextField fromtf;
	JTextField totf;
	JTextField southtf1;
	JComboBox southtf2;
	JLabel l1;
	JTextField tf1;
	JButton dtlbut;
	JButton chkbut;
	JButton closebut;
	JButton performbut;
	JButton totalbut;
	JButton updatebut;
	JLabel progressLabel;
	JProgressBar progress;
	JPanel centerpanel;
	JPanel northpanel;
	JPanel southpanel;
	JPanel southp1;
	JPanel southp2;
	JPanel southpanel2;
	Font fo;
	Border line;
	Border bor1;
	Border bor2;
	Toolkit tk;
	Dimension ss;
	Dimension fs;
	SimpleDateFormat sdf;
	SimpleDateFormat sdf2;
	java.util.Date dat;
	String dateString;
	ColoredTableCellRenderer skr;
	ColoredTableCellRenderer1 skr1;
	
	DateDifferencesUtil skd;
	Container c;
	Container c1;

	class ColoredTableCellRenderer extends DefaultTableCellRenderer {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		ColoredTableCellRenderer() {
		}

		public Component getTableCellRendererComponent(JTable paramJTable, Object paramObject, boolean paramBoolean1,
				boolean paramBoolean2, int paramInt1, int paramInt2) {
			setEnabled((paramJTable == null) || (paramJTable.isEnabled()));

			if (paramInt1 % 2 == 0) {
				setBackground(new Color(240, 240, 240));
				setForeground(Color.darkGray);

			} else {
				setBackground(new Color(250, 250, 250));
				setForeground(Color.darkGray);
			}

			if (paramInt2 <= 1) {
				setHorizontalAlignment(0);
			}
			if (paramInt2 > 1) {
				setHorizontalAlignment(2);
			}

			float f = Float.parseFloat(String.valueOf(model1.getValueAt(paramInt1, 2)));
			if (paramInt2 == 0) {
				if (f <= 0.4D) {
					setBackground(new Color(250, 220, 220));
					setForeground(Color.red);
				}
				if ((f > 0.4D) && (f < 0.7D)) {
					setBackground(new Color(220, 250, 220));
					setForeground(Color.darkGray);
				}
				if ((f >= 0.7D) && (f <= 1.0F)) {
					setBackground(new Color(220, 220, 250));
					setForeground(Color.darkGray);
				}
				if (f > 1.0F) {
					setBackground(new Color(120, 120, 250));
					setForeground(new Color(60, 60, 160));
				}
			}

			super.getTableCellRendererComponent(paramJTable, paramObject, paramBoolean1, paramBoolean2, paramInt1,
					paramInt2);

			return this;
		}
	}

	class ColoredTableCellRenderer1 extends DefaultTableCellRenderer {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		ColoredTableCellRenderer1() {
		}

		public Component getTableCellRendererComponent(JTable paramJTable, Object paramObject, boolean paramBoolean1,
				boolean paramBoolean2, int paramInt1, int paramInt2) {
			setEnabled((paramJTable == null) || (paramJTable.isEnabled()));

			String str1 = String.valueOf(String.valueOf(model.getValueAt(paramInt1, 0)));
			float f = Float.parseFloat(String.valueOf(model.getValueAt(paramInt1, 7)));

			setHorizontalAlignment(0);

			if (paramInt2 > 1) {
				setHorizontalAlignment(4);
			}

			if (str1.length() <= 3) {
				if (f <= 0.4D) {
					setBackground(new Color(250, 220, 220));
					setForeground(Color.red);
				}
				if ((f > 0.4D) && (f < 0.7D)) {
					setBackground(new Color(220, 250, 220));
					setForeground(Color.darkGray);
				}
				if ((f >= 0.7D) && (f <= 1.0F)) {
					setBackground(new Color(220, 220, 250));
					setForeground(Color.darkGray);
				}
				if (f > 1.0F) {
					setBackground(new Color(120, 170, 250));
					setForeground(Color.white);
				}
			} else {
				setBackground(new Color(230, 240, 250));
				setForeground(Color.darkGray);
			}

			super.getTableCellRendererComponent(paramJTable, paramObject, paramBoolean1, paramBoolean2, paramInt1,
					paramInt2);

			String str2 = String.valueOf(model.getValueAt(paramInt1, 0));
			tb.setRowHeight(paramInt1, 22);
			if (str2.length() <= 3) {
				tb.setRowHeight(paramInt1, 35);
			}

			return this;
		}
	}

	public EmpPerform2() {
		affected = 0;

		sheet = 0.0F;
		alloted = 0.0F;
		completed = 0.0F;
		taken = 0.0F;
		bfa = 0.0F;
		rev = 0.0F;
		wasted = 0.0F;
		used = 0.0F;
		eff = 0.0F;

		columnNames = new String[] { "PROJECT No.", "<HTML><Center>TEAM<BR>LEADER",
				"<HTML><Center>ALLOTED<BR>DRGS", "<HTML><Center>COMPLETED<BR>DRGS", "<HTML><Center>ALLOTED<BR>Hrs",
				"<HTML><Center>COMPLETED <BR>JOB Hrs", "<HTML><Center>ACTUAL <BR>USED Hrs",
				"<HTML><Center>JOB<BR>COMPLETED%", "<HTML><Center>EFF." };

		data = new Object[0][];

		columnNames1 = new String[] { "Emp Code", "Desig", "F", "Name" };
		data1 = new Object[0][];

		empcodestr = "Shekhar";
		itemcodestr = "AAA";
		projectnostr = "001";

		df = new DecimalFormat("0.00");
		df1 = new DecimalFormat("0");
		df2 = new DecimalFormat(".0");
		df3 = new DecimalFormat("00");

		model = new DefaultTableModel(data, columnNames);
		tb = new JTable(model) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int paramAnonymousInt1, int paramAnonymousInt2) {
				int i = convertColumnIndexToModel(paramAnonymousInt2);
				return i > 8;
			}
		};
		sp = new JScrollPane(tb);

		model1 = new DefaultTableModel(data1, columnNames1);
		tb1 = new JTable(model1) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int paramAnonymousInt1, int paramAnonymousInt2) {
				int i = convertColumnIndexToModel(paramAnonymousInt2);
				return i < 0;
			}
		};
		sp1 = new JScrollPane(tb1);

		spl1 = new JSplitPane(1, sp1, sp);

		f = new JFrame("Performance Monitoring");
		f1 = new JFrame();

		mb = new JMenuBar();
		menu1 = new JMenu("Report");
		mi1 = new JCheckBoxMenuItem("Current Employee", true);
		mi2 = new JCheckBoxMenuItem("EX. Employee", false);
		mi3 = new JMenuItem("Printable Report");
		mi4 = new JMenuItem("Exit");

		group = new ButtonGroup();

		fromlabel = new JLabel("From");
		tolabel = new JLabel("To");
		southl1 = new JLabel("Project No");
		southl2 = new JLabel("Designation");

		fromtf = new JTextField("01/08/2007", 10);
		totf = new JTextField("30/08/2007", 10);
		southtf1 = new JTextField(6);
		southtf2 = new JComboBox();

		l1 = new JLabel("Project No");
		tf1 = new JTextField("07001", 10);

		dtlbut = new JButton("DTL Report");
		chkbut = new JButton("CHK Report");
		closebut = new JButton("Close", new ImageIcon(ClassLoader.getSystemResource(ApplicationConstants.IMAGE_PATH + "close.gif")));
		performbut = new JButton("View Report");
		totalbut = new JButton("Company AVG");
		updatebut = new JButton("Update Factor");

		progressLabel = new JLabel("Progress");
		progress = new JProgressBar(0, 100);

		centerpanel = new JPanel();
		northpanel = new JPanel();
		southpanel = new JPanel();
		southp1 = new JPanel();
		southp2 = new JPanel();

		southpanel2 = new JPanel();

		fo = new Font("Tahoma", 1, 10);
		line = BorderFactory.createLineBorder(Color.darkGray);
		bor1 = BorderFactory.createTitledBorder(line, "Date Range", 0, 0, fo, Color.darkGray);
		bor2 = BorderFactory.createTitledBorder(line, "Employee Performance Report", 0, 0, fo,
				Color.darkGray);

		tk = Toolkit.getDefaultToolkit();
		ss = tk.getScreenSize();
		fs = f.getSize();

		sdf = new SimpleDateFormat("dd/MM/yyyy");
		sdf2 = new SimpleDateFormat("MMM/yyyy");

		dat = new java.util.Date();

		try {
			dateString = sdf.format(dat);
			totf.setText(dateString);
		} catch (IllegalArgumentException localIllegalArgumentException) {
			System.out.println(localIllegalArgumentException.getMessage());
		}

		skr = new ColoredTableCellRenderer();
		skr1 = new ColoredTableCellRenderer1();

		skd = new DateDifferencesUtil();
	}

	public void px() {
		con = DBConnectionUtil.getConnection();
		c = f.getContentPane();
		c.setLayout(new BorderLayout());
		northpanel.setLayout(new FlowLayout(2));
		southpanel.setLayout(new BorderLayout(1, 2));
		southp1.setLayout(new FlowLayout(0));
		southp2.setLayout(new FlowLayout(2));
		centerpanel.setLayout(new BorderLayout());

		southtf2.addItem("Detailing");
		southtf2.addItem("Checking");

		fromtf.setText("01/" + String.valueOf(df3.format(DateCalculationUtil.getMonth(totf.getText()))) + "/"
				+ String.valueOf(DateCalculationUtil.getYear(totf.getText())));

		fromlabel.setFont(fo);
		fromtf.setFont(fo);
		fromtf.setPreferredSize(new Dimension(60, 22));
		tolabel.setFont(fo);
		totf.setFont(fo);
		totf.setPreferredSize(new Dimension(60, 22));
		southl1.setFont(fo);
		southtf2.setFont(fo);
		southtf2.setPreferredSize(new Dimension(130, 22));
		southl2.setFont(fo);
		southtf2.setFont(fo);
		southtf2.setPreferredSize(new Dimension(130, 22));
		progressLabel.setFont(fo);
		progress.setFont(fo);

		southl1.setFont(fo);
		southp1.setFont(fo);
		performbut.setFont(fo);
		totalbut.setFont(fo);
		updatebut.setFont(fo);

		fromtf.setHorizontalAlignment(4);
		fromtf.setForeground(Color.gray);
		totf.setHorizontalAlignment(4);
		totf.setForeground(Color.gray);

		southtf2.setForeground(Color.gray);

		northpanel.add(fromlabel);
		northpanel.add(fromtf);
		northpanel.add(tolabel);
		northpanel.add(totf);
		northpanel.add(southl2);
		northpanel.add(southtf2);
		northpanel.add(performbut);
		northpanel.add(progressLabel);
		northpanel.add(progress);

		progress.setForeground(new Color(100, 100, 150));
		progress.setStringPainted(true);
		progress.setPreferredSize(new Dimension(350, 21));

		southpanel.add(southp1, "West");
		southpanel.add(southp2, "East");

		totf.setFont(fo);
		fromtf.setFont(fo);
		southtf1.setFont(fo);

		tb1.getColumnModel().getColumn(0).setCellRenderer(skr);
		tb1.getColumnModel().getColumn(2).setCellRenderer(skr);
		tb1.getColumnModel().getColumn(3).setCellRenderer(skr);
		tb1.getColumnModel().getColumn(0).setPreferredWidth(50);
		tb1.getColumnModel().getColumn(2).setPreferredWidth(30);
		tb1.getColumnModel().getColumn(3).setPreferredWidth(140);

		tb.getColumnModel().getColumn(0).setPreferredWidth(90);
		tb.getColumnModel().getColumn(1).setPreferredWidth(50);
		tb.getColumnModel().getColumn(7).setPreferredWidth(100);
		tb.getColumnModel().getColumn(8).setPreferredWidth(40);

		for (int i = 0; i < tb.getColumnCount(); i++) {
			tb.getColumnModel().getColumn(i).setCellRenderer(skr1);
		}

		SolTableModel.decorateTable(tb);
		SolTableModel.decorateTable(tb1);

		tb.setShowGrid(false);
		tb1.setShowGrid(false);

		tb.setFont(new Font("Verdana", 1, 11));
		tb1.setFont(new Font("Verdana", 0, 10));

		tb.setIntercellSpacing(new Dimension(1, 1));
		tb1.setIntercellSpacing(new Dimension(1, 1));

		tb.getTableHeader().setPreferredSize(new Dimension(40, 40));
		tb.getTableHeader().setFont(new Font("Verdana", 1, 10));
		tb1.getTableHeader().setPreferredSize(new Dimension(40, 40));
		tb1.getTableHeader().setFont(new Font("Verdana", 0, 10));

		northpanel.setBorder(BorderFactory.createEtchedBorder());

		group.add(mi1);
		group.add(mi2);

		mb.add(menu1);
		menu1.add(mi1);
		menu1.add(mi2);
		menu1.addSeparator();
		menu1.add(mi3);
		menu1.addSeparator();
		menu1.add(mi4);

		spl1.setOneTouchExpandable(true);
		spl1.setDividerLocation(260);

		southpanel.setPreferredSize(new Dimension(133, 133));
		southpanel.setBorder(BorderFactory.createEtchedBorder());
		c.add(northpanel, "North");
		c.add(spl1, "Center");

		dtlbut.addActionListener(this);
		chkbut.addActionListener(this);
		closebut.addActionListener(this);

		tf1.addActionListener(this);
		southtf1.addActionListener(this);

		fromtf.addActionListener(this);
		fromtf.addMouseListener(this);
		totf.addActionListener(this);
		totf.addMouseListener(this);

		southtf2.addActionListener(this);
		performbut.addActionListener(this);
		updatebut.addActionListener(this);

		mi1.addActionListener(this);
		mi2.addActionListener(this);
		mi3.addActionListener(this);
		mi4.addActionListener(this);
		tb1.addMouseListener(this);

		f.getRootPane().setDefaultButton(performbut);
		f.setJMenuBar(mb);
		f.setSize(865, 550);
		f.setVisible(true);

		f.setLocationRelativeTo(null);
		tb1.removeColumn(tb1.getColumnModel().getColumn(1));

		String str1 = "%" + String.valueOf(southtf2.getSelectedItem());
		String str2 = "select EMP_CODE, JOB, FACTOR, EMP_NAME from emp_status where job like '" + str1
				+ "' and EMP_CODE not in(select EMP_CODE from HR_EX_EMP) ORDER BY EMP_CODE ";
		empList(str2);
	}

	public void setSKRenderer() {
		for (int i = 0; i < tb.getColumnCount(); i++) {
		}
	}

	public void empList(String paramString) {
		model1.setNumRows(0);
		try {
			con = DBConnectionUtil.getConnection();
			stat = con.createStatement();
			rs = stat.executeQuery(paramString);
			rs.getMetaData().getColumnCount();

			while (rs.next()) {
				String str1 = new String(rs.getString(1));
				String str2 = new String(rs.getString(2));
				String str3 = new String(rs.getString(3));
				String str4 = new String(rs.getString(4));

				model1.addRow(new Object[] { str1, str2, str3.toUpperCase(), str4.toUpperCase() });
			}
		} catch (Exception localException) {
			System.out.println(localException);
		}
	}

	public void projectList(String paramString1, String paramString2) {
		java.sql.Date localDate1 = SolDateFormatter.DDMMYYtoSQL(fromtf.getText());
		java.sql.Date localDate2 = SolDateFormatter.DDMMYYtoSQL(totf.getText());
		float f5;
		float f6;
		try {
			PreparedStatement localPreparedStatement = con
					.prepareStatement("select DISTINCT(PROJECT_NO) from projectmanpower WHERE EMP_CODE='" + paramString1
							+ "' AND DTL_CHK= '" + paramString2
							+ "' and from_date2 between ? and ? GROUP BY PROJECT_NO ");
			localPreparedStatement.setDate(1, localDate1);
			localPreparedStatement.setDate(2, localDate2);
			rs = localPreparedStatement.executeQuery();

			float f2 = 0.0F;
			float f3 = 0.0F;
			float f4 = 0.0F;
			f5 = 0.0F;
			f6 = 0.0F;

			while (rs.next()) {
				String str1 = new String(rs.getString(1));

				String str2 = ProjectDAO.getTeamLeader(str1);
				String str3 = String.valueOf(ProjectDAO.getCountAllotedDrg(str1, paramString1,
						String.valueOf(southtf2.getSelectedItem())));
				String str4 = String.valueOf(ProjectDAO.getCountCompletedDrg(str1, paramString1));
				String str5 = String.valueOf(df1.format(ProjectDAO.getAllotedHrs(str1, paramString1,
						String.valueOf(southtf2.getSelectedItem()))));
				String str6 = String.valueOf(df1.format(ProjectDAO.getCompletedHrs(str1, paramString1)));
				String str7 = String.valueOf(df1.format(ProjectDAO.getUsedHrs(paramString1, str1,
						String.valueOf(southtf2.getSelectedItem()))));

				if (Float.parseFloat(str6) > 0.0F) {
					f3 += Integer.parseInt(str3);
					f4 += Integer.parseInt(str4);
					f2 += Float.parseFloat(str5);
					f5 += Float.parseFloat(str6);
					f6 += Float.parseFloat(str7);
				}
				model.addRow(new Object[] { str1, str2, str3, str4, str5, str6, str7, "0", "0" });
			}
			model.addRow(new Object[] { paramString1, "0", String.valueOf(df1.format(f3)),
					String.valueOf(df1.format(f4)), String.valueOf(df1.format(f2)),
					String.valueOf(df1.format(f5)), String.valueOf(df1.format(f6)), "0", "0" });
			f2 = 0.0F;
			f5 = 0.0F;
			f6 = 0.0F;
		} catch (Exception localException) {
			System.out.println(localException);
		}

		for (int i = 0; i < tb.getRowCount(); i++) {
			int j = Integer.parseInt(String.valueOf(model.getValueAt(i, 2)));
			int k = Integer.parseInt(String.valueOf(model.getValueAt(i, 3)));

			int m = 0;
			if (k > 0) {
				m = k / j * 100;
				model.setValueAt(String.valueOf(m), i, 7);
			}

			f5 = Float.parseFloat(String.valueOf(model.getValueAt(i, 5)));
			f6 = Float.parseFloat(String.valueOf(model.getValueAt(i, 6)));
			float f7 = 0.0F;
			if (f6 > 0.0F) {
				f7 = f5 / f6;
				model.setValueAt(String.valueOf(df.format(f7)), i, 8);
			}
		}
	}

	public class processReport extends Thread {
		public processReport() {
		}

		public void run() {
			int i = tb1.getSelectedRow();
			int[] arrayOfInt = tb1.getSelectedRows();
			progress.setMaximum(arrayOfInt.length - 1);

			for (int j = 0; j < arrayOfInt.length; j++) {
				tb1.setRowSelectionInterval(i + j, i + j);
				String str = String.valueOf(model1.getValueAt(arrayOfInt[j], 0));
				projectList(str, String.valueOf(southtf2.getSelectedItem()));

				progress.setValue(j);
				progress.setString(str);
				progress.setStringPainted(true);

				try {
					Thread.sleep(10L);
				} catch (InterruptedException localInterruptedException) {
					System.out.println("processReport Thread : " + localInterruptedException);
				}
			}

			totalbut.setEnabled(true);
			updatebut.setEnabled(true);
			performbut.setEnabled(true);
			southtf1.setEditable(true);
			southtf2.setEnabled(true);
			mi3.setEnabled(true);
			mi4.setEnabled(true);
		}
	}

	public class updateFactor extends Thread {
		public updateFactor() {
		}

		public void run() {
			for (int i = 0; i < tb.getRowCount(); i++) {
				try {
					Thread.sleep(10L);
				} catch (InterruptedException localInterruptedException) {
					System.out.println("updateFactor Thread : " + localInterruptedException);
				}
				String str1 = String.valueOf(String.valueOf(model.getValueAt(i, 0)));
				String str2 = "C01";
				String str3 = "0";
				String str4 = String.valueOf(String.valueOf(model.getValueAt(i, 9)));

				tb.setRowSelectionInterval(i, i);

				if (str1.length() <= 3) {
					progress.setValue(i);
					progress.setString(str1);
					try {
						stat = con.createStatement();
						affected = stat
								.executeUpdate("insert into HR_FACTOR_REPORT values('" + str2 + "','" + str3 + "','"
										+ str1 + "','" + fromtf.getText() + "','" + str4 + "') ");
						if (affected > 0) {
							JOptionPane.showMessageDialog(f, "Record Saved.");
							stat
									.executeUpdate("update EMP_STATUS set Factor='" + str4 + "' where emp_code='" + str1
											+ "' and co_id='" + str2 + "' and branch_code='" + str3 + "' ");
						}
					} catch (Exception localException) {
						JOptionPane.showMessageDialog(f, localException.getMessage().toString());
					}
				}
			}
			progress.setString("Updated Successfully.");
			totalbut.setEnabled(true);
			updatebut.setEnabled(true);
			performbut.setEnabled(true);
			southtf1.setEditable(true);
			southtf2.setEnabled(true);
			mi3.setEnabled(true);
			mi4.setEnabled(true);
		}
	}

	public class myPrint extends Thread implements Runnable {
		public myPrint() {
		}

		public void run() {
			String str = "Efficiency Report : " + fromtf.getText() + " To "
					+ totf.getText();

			try {
				MessageFormat localMessageFormat1 = new MessageFormat(str);
				MessageFormat localMessageFormat2 = new MessageFormat("- {0} -");
				tb.print(JTable.PrintMode.FIT_WIDTH, localMessageFormat1, localMessageFormat2);
			} catch (PrinterException localPrinterException) {
				System.err.println("Error printing: " + localPrinterException.getMessage());
			}
		}
	}

	public void actionPerformed(ActionEvent paramActionEvent) {
		
		if (paramActionEvent.getSource() == mi3) {
			myPrint myPrintObject = new myPrint();
			myPrintObject.start();
		}
		if ((paramActionEvent.getSource() != dtlbut) || (

		(paramActionEvent.getSource() != chkbut) || (

		(paramActionEvent.getSource() == southtf2) || (paramActionEvent.getSource() == mi1)
				|| (paramActionEvent.getSource() == mi2)))) {
			model.setNumRows(0);
			String valueString = "%" + String.valueOf(southtf2.getSelectedItem());
			String str;
			if (mi1.getState() == true) {
				str = "select EMP_CODE, JOB, FACTOR, EMP_NAME from emp_status where job like '" + valueString
						+ "' and EMP_CODE not in(select EMP_CODE from HR_EX_EMP) ORDER BY EMP_CODE ";
				empList(str);
			}
			if (mi2.getState() == true) {
				str = "select EMP_CODE, JOB, FACTOR, EMP_NAME from emp_status where job like '" + valueString
						+ "' and EMP_CODE in(select EMP_CODE from HR_EX_EMP) ORDER BY EMP_CODE ";
				empList(str);
			}
		}

		if (paramActionEvent.getSource() == mi4) {
			f.setVisible(false);
		}
		if (paramActionEvent.getSource() == performbut) {
			System.out.println("Employee Performance Report.");
			model.setNumRows(0);
			processReport processReportObject = new processReport();
			processReportObject.start();
		}
		if (paramActionEvent.getSource() == updatebut) {
			totalbut.setEnabled(false);
			updatebut.setEnabled(false);
			performbut.setEnabled(false);
			southtf1.setEditable(false);
			southtf2.setEnabled(false);
			mi3.setEnabled(false);
			mi4.setEnabled(false);

			updateFactor updateFactorObject = new updateFactor();
			updateFactorObject.start();
		}
	}

	public void mousePressed(MouseEvent paramMouseEvent) {
		String.valueOf(model1.getValueAt(tb1.getSelectedRow(), 0));
		if (paramMouseEvent.getSource() == tb1) {
			if ((!SwingUtilities.isRightMouseButton(paramMouseEvent)) ||

					(SwingUtilities.isLeftMouseButton(paramMouseEvent))) {
				f1.setVisible(false);
			}
		}

		if (paramMouseEvent.getSource() == fromtf) {
			if (SwingUtilities.isRightMouseButton(paramMouseEvent)) {
			    SolCalendar skl = new SolCalendar();
				skl.showCalendar();
				skl.getDate(fromtf);
			}
		}
		if (paramMouseEvent.getSource() == totf) {
			if (SwingUtilities.isRightMouseButton(paramMouseEvent)) {
			    SolCalendar skl = new SolCalendar();
				skl.showCalendar();
				skl.getDate(totf);
			}
		}
	}

	public void mouseReleased(MouseEvent paramMouseEvent) {
	}

	public void mouseClicked(MouseEvent paramMouseEvent) {
	}

	public void mouseEntered(MouseEvent paramMouseEvent) {
	}

	public void mouseExited(MouseEvent paramMouseEvent) {
	}

}
