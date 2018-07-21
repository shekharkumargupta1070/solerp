package com.sol.erp;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PrinterException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import com.sol.erp.constants.ApplicationConstants;
import com.sol.erp.ui.custom.SolTableModel;
import com.sol.erp.util.DBConnectionUtil;
import com.sol.erp.util.formater.SolDateFormatter;
import com.sol.erp.util.DateDifferencesUtil;
import com.sol.erp.util.DateCalculationUtil;

public class LeaveApplicationChart implements ActionListener {
	
	DateCalculationUtil dmy;
	DateDifferencesUtil skf;
	Connection con;
	PreparedStatement prsm;
	ResultSet rs;
	int affected;
	public static String codestr;
	String[] columnNames;
	Object[][] data;
	JFrame f;
	JTabbedPane tabbedpane;
	JMenuBar menubar;
	JMenu ma;
	JMenuItem ma1;
	JMenuItem ma2;
	JButton b4;
	JButton b5;
	DefaultTableModel model;
	JTable tb;
	JScrollPane sp;
	JPanel chartpanel;
	JPanel monthlypanel;
	JPanel mainPanel;
	JPanel p1;
	JPanel p2;
	JPanel buttonpanel;
	JPanel statuspanel;
	JLabel searchlb;
	JTextField searchtf;
	JLabel statuslb;
	JLabel datelabel;
	SimpleDateFormat formater;
	java.util.Date dat;
	String dateString;
	JRadioButton rb1;
	JRadioButton rb2;
	JRadioButton rb3;
	ButtonGroup grp;
	Border bor1;
	Border lowerbor;
	Font fo;
	Font fo2;
	Toolkit tk;
	Dimension ss;
	Dimension fs;
	LeaveApplicationChart.ColoredTableCellRenderer skr;


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
				setBackground(new Color(250, 250, 250));
				setForeground(Color.darkGray);

			} else {
				setBackground(new Color(240, 240, 240));
				setForeground(Color.darkGray);
			}

			if ((paramInt2 == 0) || (paramInt2 == 1) || (paramInt2 == 3) || (paramInt2 == 4) || (paramInt2 == 6)) {
				setHorizontalAlignment(0);
			}
			if (paramInt2 == 2) {
				setHorizontalAlignment(2);
			}
			if (paramInt2 == 5) {
				setHorizontalAlignment(4);
			}

			super.getTableCellRendererComponent(paramJTable, paramObject, paramBoolean1, paramBoolean2, paramInt1,
					paramInt2);

			return this;
		}
	}

	public LeaveApplicationChart() {
		dmy = new DateCalculationUtil();
		skf = new DateDifferencesUtil();

		affected = 0;

		columnNames = new String[] { "", "Code", "Name", "Designation", "Leave Date", "Total", "Contact No." };
		data = new Object[0][];

		f = new JFrame();
		tabbedpane = new JTabbedPane();

		menubar = new JMenuBar();
		ma = new JMenu("REPORT");

		ma1 = new JMenuItem("PRINT LEAVE APPLICATION CHART");
		ma2 = new JMenuItem("EXIT");

		b4 = new JButton("Print", new ImageIcon(ClassLoader.getSystemResource(ApplicationConstants.IMAGE_PATH + "print.gif")));
		b5 = new JButton("Close", new ImageIcon(ClassLoader.getSystemResource(ApplicationConstants.IMAGE_PATH + "close.gif")));

		model = new DefaultTableModel(data, columnNames);
		tb = new JTable(model) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public Class<? extends Object> getColumnClass(int paramAnonymousInt) {
				return getValueAt(0, paramAnonymousInt).getClass();
			}

			public boolean isCellEditable(int paramAnonymousInt1, int paramAnonymousInt2) {
				return (paramAnonymousInt2 <= 0) || (paramAnonymousInt2 >= 8);
			}
		};
		sp = new JScrollPane(tb);

		chartpanel = new JPanel();
		monthlypanel = new JPanel();

		mainPanel = new JPanel();
		p1 = new JPanel();
		p2 = new JPanel();
		buttonpanel = new JPanel();
		statuspanel = new JPanel();

		searchlb = new JLabel("EMP Name");
		searchtf = new JTextField(15);

		statuslb = new JLabel("Status :");
		datelabel = new JLabel("Today");

		formater = new SimpleDateFormat("dd/MM/yyyy");
		dat = new java.util.Date();

		try {
			dateString = formater.format(dat);
			datelabel.setText(dateString);
		} catch (IllegalArgumentException localIllegalArgumentException) {
			System.out.println(localIllegalArgumentException.getMessage());
		}

		rb1 = new JRadioButton("Current Employee", true);
		rb2 = new JRadioButton("EX. Employee", false);
		rb3 = new JRadioButton("Trainee", false);

		grp = new ButtonGroup();

		bor1 = BorderFactory.createTitledBorder("Search Option");
		lowerbor = BorderFactory.createBevelBorder(1);

		fo = new Font("Tahoma", 1, 11);
		fo2 = new Font("Verdana", 1, 10);

		tk = Toolkit.getDefaultToolkit();
		ss = tk.getScreenSize();
		fs = f.getSize();

		skr = new LeaveApplicationChart.ColoredTableCellRenderer();
	}

	public JPanel DesignFrame() {
		Container localContainer = f.getContentPane();
		localContainer.setLayout(new BorderLayout());
		mainPanel.setLayout(new BorderLayout());
		p1.setLayout(new GridLayout(4, 2, 2, 2));
		p2.setLayout(new BorderLayout());
		buttonpanel.setLayout(new FlowLayout(0));
		statuspanel.setLayout(new FlowLayout(0));

		monthlypanel.setLayout(new BorderLayout());
		chartpanel.setLayout(new BorderLayout());

		tabbedpane.add("LEAVE APPLICATION CHART", chartpanel);
		tabbedpane.add("MONTHLY LEAVE DETAILS", monthlypanel);

		statuspanel.add(datelabel);
		datelabel.setForeground(Color.blue);

		statuspanel.setBorder(lowerbor);

		b4.setMnemonic(87);
		b5.setMnemonic(83);

		menubar.add(ma);

		ma.add(ma1);
		ma.addSeparator();
		ma.add(ma2);

		SolTableModel.decorateTable(tb);
		tb.setAutoResizeMode(0);
		tb.setBackground(Color.white);
		tb.setShowGrid(false);
		tb.setFont(new Font("Tahoma", 1, 13));
		tb.setIntercellSpacing(new Dimension(1, 1));
		tb.setRowHeight(35);
		tb.getTableHeader().setPreferredSize(new Dimension(35, 35));
		tb.setAutoCreateRowSorter(true);
		tb.getTableHeader().setFont(new Font("Tahoma", 1, 12));
		tb.getTableHeader().setToolTipText("<HTML><Font color='red' size='13'> Click Me to Sort the Table");

		tb.getColumnModel().getColumn(0).setPreferredWidth(35);
		tb.getColumnModel().getColumn(1).setPreferredWidth(60);
		tb.getColumnModel().getColumn(2).setPreferredWidth(280);
		tb.getColumnModel().getColumn(3).setPreferredWidth(100);
		tb.getColumnModel().getColumn(4).setPreferredWidth(210);
		tb.getColumnModel().getColumn(5).setPreferredWidth(60);
		tb.getColumnModel().getColumn(6).setPreferredWidth(210);

		for (int i = 0; i < tb.getColumnCount(); i++) {
			tb.getColumnModel().getColumn(i).setCellRenderer(skr);
		}

		buttonpanel.add(b4);
		buttonpanel.add(b5);

		p2.add(sp, "Center");

		chartpanel.add(p2, "Center");
		chartpanel.add(statuspanel, "South");
		monthlypanel.add(new MonthlyLeaveDetails().DesignFrame(), "Center");

		mainPanel.add(tabbedpane, "Center");
		localContainer.add(mainPanel, "Center");

		ma1.addActionListener(this);
		ma2.addActionListener(this);

		b4.addActionListener(this);
		b5.addActionListener(this);

		f.setJMenuBar(menubar);
		f.setTitle("Leave Application Chart");
		empList();

		ma.setFont(fo2);
		ma1.setFont(fo2);
		ma2.setFont(fo2);

		ma1.setBackground(new Color(200, 120, 120));
		ma1.setForeground(Color.white);

		ma2.setBackground(new Color(200, 120, 120));
		ma2.setForeground(Color.white);

		tabbedpane.setFont(fo2);

		return mainPanel;
	}

	public void showLeaveApplicationChart() {
		DesignFrame();
		con = DBConnectionUtil.getConnection();
		f.setLocation((ss.width - fs.width) / 10, (ss.height - fs.height) / 8);
		f.setSize(810, 530);
		f.setVisible(true);
	}

	public void empList() {
		model.setNumRows(0);

		java.sql.Date localDate1 = SolDateFormatter.DDMMYYtoSQL(datelabel.getText());

		try {
			con = DBConnectionUtil.getConnection();
			prsm = con.prepareStatement(
					"Select labelEstimatedBy.EMP_CODE, j.EMP_NAME, j.DESIGNATION, count(labelEstimatedBy.DATE), min(labelEstimatedBy.DATE), max(labelEstimatedBy.DATE) from HREMP_LEAVES labelEstimatedBy, EMP_STATUS j  where labelEstimatedBy.DATE >= ? and labelEstimatedBy.EMP_CODE=j.EMP_CODE group by labelEstimatedBy.EMP_CODE order by labelEstimatedBy.emp_code ");
			prsm.setDate(1, localDate1);
			rs = prsm.executeQuery();

			int i = 0;

			while (rs.next()) {
				i += 1;
				String str1 = new String(rs.getString(1));
				String str2 = new String(rs.getString(2));
				String str3 = new String(rs.getString(3));
				int j = rs.getInt(4);

				java.sql.Date localDate2 = rs.getDate(5);
				java.sql.Date localDate3 = rs.getDate(6);

				String str4 = SolDateFormatter.SQLtoDDMMYY(localDate2);
				String str5 = SolDateFormatter.SQLtoDDMMYY(localDate3);

				model.addRow(new Object[] { Integer.valueOf(i), str1, str2.toUpperCase(), str3.toUpperCase(),
						str4 + " - " + str5, Integer.valueOf(j), "" });
			}
		} catch (Exception localException) {
			System.out.println(localException);
		}
		contactList();
	}

	public void contactList() {
		for (int i = 0; i < tb.getRowCount(); i++) {
			String str1 = String.valueOf(model.getValueAt(i, 1));

			try {
				con = DBConnectionUtil.getConnection();
				prsm = con.prepareStatement("select personal from phone where emp_code='" + str1 + "' ");
				rs = prsm.executeQuery();

				while (rs.next()) {
					String str2 = new String(rs.getString(1));
					model.setValueAt(str2, i, 6);
				}
			} catch (Exception localException) {
				System.out.println(localException);
			}
		}
	}

	public class myPrint extends Thread implements Runnable {
		public myPrint() {
		}

		public void run() {
			String str = "SOL Leave Application Chart";

			try {
				MessageFormat localMessageFormat1 = new MessageFormat(str);
				MessageFormat localMessageFormat2 = new MessageFormat(datelabel.getText());
				tb.print(JTable.PrintMode.FIT_WIDTH, localMessageFormat1,
						localMessageFormat2);
			} catch (PrinterException localPrinterException) {
				System.err.println("Error printing: " + localPrinterException.getMessage());
			}
		}
	}

	public void actionPerformed(ActionEvent paramActionEvent) {
		if ((paramActionEvent.getSource() == b4) || (paramActionEvent.getSource() == ma1)) {
			LeaveApplicationChart.myPrint localmyPrint = new LeaveApplicationChart.myPrint();
			localmyPrint.start();
		}
		if ((paramActionEvent.getSource() == b5) || (paramActionEvent.getSource() == ma2)) {
			f.setVisible(false);
		}
	}

}
