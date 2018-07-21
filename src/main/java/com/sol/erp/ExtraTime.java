package com.sol.erp;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.print.PrinterException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import com.sol.erp.ui.custom.SolProgressMonitor;
import com.sol.erp.util.DBConnectionUtil;
import com.sol.erp.util.DateDifferencesUtil;
import com.sol.erp.util.formater.SolDateFormatter;

public class ExtraTime implements java.awt.event.ActionListener, java.awt.event.MouseListener {
	
	TIMOption tm;
	SolProgressMonitor monitor;
	Connection con;
	java.sql.Statement stat;
	ResultSet rs;
	DecimalFormat df;
	DecimalFormat df1;
	DecimalFormat df2;
	DecimalFormat df3;
	DecimalFormat df4;
	String[] column;
	Object[][] data;
	String[] column2;
	Object[][] data2;
	DefaultTableModel model;
	JTable tb;
	JScrollPane sp;
	DefaultTableModel model2;
	JTable tb2;
	JScrollPane sp2;
	JFrame f;
	JComboBox cb;
	EmpCodeList emc;
	JTextField fromcb;
	JTextField tocb;
	JButton b1;
	JButton b2;
	JButton b3;
	JPopupMenu pop;
	JCheckBoxMenuItem popm1;
	JPanel toolpanel;
	JPanel mainpanel;
	JPanel secondpanel;
	JTabbedPane tabpane;
	javax.swing.border.Border bor;
	java.util.Date today;
	SimpleDateFormat sdf;
	String datestr;
	ExtraTime.ColoredTableCellRenderer skr;
	ExtraTime.ColoredTableCellRenderer2 skr2;
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

			if (paramInt2 == 0) {
				setHorizontalAlignment(0);
			}
			if ((paramInt2 == 1) && (paramInt1 > 0)) {
				setHorizontalAlignment(2);
			}
			if ((paramInt2 > 1) && (paramInt1 > 0)) {
				setHorizontalAlignment(0);
			}
			if ((paramInt2 > 3) && (paramInt1 > 0)) {
				setHorizontalAlignment(4);
			}

			if (paramInt1 % 2 == 0) {
				setBackground(new Color(240, 240, 240));
				setForeground(Color.darkGray);

			} else {
				setBackground(new Color(250, 250, 250));
				setForeground(Color.darkGray);
			}

			if (paramInt1 == 0) {
				setBackground(Color.darkGray);
				setForeground(Color.white);
				setHorizontalAlignment(0);
			}
			String str1;
			if ((paramInt2 == 2) && (paramInt1 > 0)) {
				str1 = String.valueOf(model.getValueAt(paramInt1, paramInt2));
				if (str1.equalsIgnoreCase("D")) {
					setBackground(new Color(250, 250, 250));
					setForeground(Color.darkGray);
				}
				if (str1.equalsIgnoreCase("C")) {
					setBackground(new Color(250, 50, 50));
					setForeground(Color.white);
				}
				if (str1.equalsIgnoreCase("R")) {
					setBackground(new Color(50, 150, 50));
					setForeground(Color.white);
				}
				if (str1.equalsIgnoreCase("B")) {
					setBackground(new Color(50, 50, 250));
					setForeground(Color.white);
				}
				if ((str1.equalsIgnoreCase("I")) || (str1.equalsIgnoreCase("J")) || (str1.equalsIgnoreCase("T"))
						|| (str1.equalsIgnoreCase("N"))) {
					setBackground(new Color(160, 50, 150));
					setForeground(Color.white);
				}
			}

			if ((tb.getRowCount() > 1) && (tb.getSelectedRows().length > 0)) {
				str1 = String.valueOf(model.getValueAt(tb.getSelectedRow(), 0));
				String str2 = String.valueOf(model.getValueAt(paramInt1, 0));
				if (str2.equalsIgnoreCase(str1)) {
					setBackground(Color.orange);
					setForeground(Color.blue);
				}
			}

			tb.setRowHeight(25);
			tb.setRowHeight(0, 80);

			super.getTableCellRendererComponent(paramJTable, paramObject, paramBoolean1, paramBoolean2, paramInt1,
					paramInt2);

			return this;
		}
	}

	class ColoredTableCellRenderer2 extends DefaultTableCellRenderer {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		ColoredTableCellRenderer2() {
		}

		public java.awt.Component getTableCellRendererComponent(JTable paramJTable, Object paramObject,
				boolean paramBoolean1, boolean paramBoolean2, int paramInt1, int paramInt2) {
			setEnabled((paramJTable == null) || (paramJTable.isEnabled()));

			if (paramInt2 == 0) {
				setHorizontalAlignment(0);
			}

			if (paramInt2 > 0) {
				setHorizontalAlignment(4);
			}

			if (paramInt1 % 2 == 0) {
				setBackground(new Color(240, 240, 240));
				setForeground(Color.darkGray);

			} else {
				setBackground(new Color(250, 250, 250));
				setForeground(Color.darkGray);
			}

			if (paramInt1 == 0) {
				setBackground(Color.darkGray);
				setForeground(Color.white);
				setHorizontalAlignment(0);
			}

			tb2.setRowHeight(25);
			tb2.setRowHeight(0, 80);

			super.getTableCellRendererComponent(paramJTable, paramObject, paramBoolean1, paramBoolean2, paramInt1,
					paramInt2);

			return this;
		}
	}

	public ExtraTime() {
		tm = new TIMOption();
		monitor = new SolProgressMonitor();
		con = null;
		stat = null;
		rs = null;

		df = new DecimalFormat("0");
		df1 = new DecimalFormat("0");
		df2 = new DecimalFormat("0.00");
		df3 = new DecimalFormat("0.0");
		df4 = new DecimalFormat("00.00");

		column = new String[] { "Emp Code", "Name", "D/C", "DATE", "Hrs." };
		data = new Object[0][];

		column2 = new String[] { "Date" };
		data2 = new Object[0][];

		model = new DefaultTableModel(data, column);
		tb = new JTable(model);
		sp = new JScrollPane(tb);

		model2 = new DefaultTableModel(data2, column2);
		tb2 = new JTable(model2);
		sp2 = new JScrollPane(tb2);

		f = new JFrame("Training, Ideal and Management Time");
		cb = new JComboBox();

		emc = new EmpCodeList("C01", "0", "");

		fromcb = new JTextField(8);
		tocb = new JTextField(8);

		b1 = new JButton("Execute");
		b2 = new JButton("Print");
		b3 = new JButton("Compress Report");

		pop = new JPopupMenu();
		popm1 = new JCheckBoxMenuItem("UnGroup This Table", true);

		toolpanel = new JPanel();
		mainpanel = new JPanel();
		secondpanel = new JPanel();

		tabpane = new JTabbedPane();

		bor = javax.swing.BorderFactory.createBevelBorder(0, Color.red, Color.pink);

		today = new java.util.Date();
		sdf = new SimpleDateFormat("dd/MM/yyyy");
		datestr = null;

		try {
			datestr = sdf.format(today);
			fromcb.setText(datestr);
			tocb.setText(datestr);
		} catch (IllegalArgumentException localIllegalArgumentException) {
			System.out.println(localIllegalArgumentException.getMessage());
		}

		skr = new ExtraTime.ColoredTableCellRenderer();
		skr2 = new ExtraTime.ColoredTableCellRenderer2();
	}

	public JPanel designFrame() {
		c = f.getContentPane();
		c.setLayout(new BorderLayout());
		mainpanel.setLayout(new BorderLayout());
		toolpanel.setLayout(new java.awt.FlowLayout(0));
		secondpanel.setLayout(new BorderLayout());

		toolpanel.setBorder(bor);

		toolpanel.add(new JLabel("Employee List"));
		toolpanel.add(emc);
		toolpanel.add(new JLabel("Select Option"));
		toolpanel.add(cb);
		toolpanel.add(new JLabel("Select From"));
		toolpanel.add(fromcb);
		toolpanel.add(new JLabel("Select To"));
		toolpanel.add(tocb);
		toolpanel.add(b1);
		toolpanel.add(b2);
		toolpanel.add(b3);

		pop.add(popm1);

		cb.addItem("Select");
		cb.addItem("I Ideal Time");
		cb.addItem("T Training");
		cb.addItem("M Management");

		tb.setAutoResizeMode(0);
		tb.getTableHeader().setPreferredSize(new java.awt.Dimension(30, 30));
		tb.getTableHeader().setFont(new Font("Verdana", 1, 10));
		tb.setFont(new Font("Tahoma", 1, 11));

		tb.getColumnModel().getColumn(0).setPreferredWidth(80);
		tb.getColumnModel().getColumn(1).setPreferredWidth(300);
		tb.getColumnModel().getColumn(2).setPreferredWidth(40);
		tb.getColumnModel().getColumn(3).setPreferredWidth(150);
		tb.getColumnModel().getColumn(4).setPreferredWidth(70);

		for (int i = 0; i < tb.getColumnCount(); i++) {
			tb.getColumnModel().getColumn(i).setCellRenderer(skr);
		}

		tb2.getTableHeader().setPreferredSize(new java.awt.Dimension(30, 30));
		tb2.getTableHeader().setFont(new Font("Verdana", 1, 10));
		tb2.setFont(new Font("Tahoma", 1, 11));

		tb2.setRowHeight(22);

		mainpanel.add(sp, "Center");
		secondpanel.add(sp2, "Center");

		tabpane.addTab("Company Report", mainpanel);

		c.add(toolpanel, "North");
		c.add(tabpane, "Center");

		emc.addActionListener(this);
		popm1.addActionListener(this);

		cb.addActionListener(this);
		b1.addActionListener(this);
		b2.addActionListener(this);
		b3.addActionListener(this);

		tb.addMouseListener(this);

		return mainpanel;
	}

	public void showForm() {
		designFrame();
		f.setSize(1024, 600);
		f.setLocationRelativeTo(null);
		f.setVisible(true);
	}

	public float effectiveHrs(String paramString, float paramFloat) {
		float f1 = Float.parseFloat(EmpTB.getCurrentFactor(paramString));
		float f2 = paramFloat * f1;

		float f3 = SolDateFormatter.convertHrsToMinute2(String.valueOf(f2));
		String str = SolDateFormatter.convertMinuteToHRS2(f3);

		return Float.parseFloat(str);
	}

	public void showTimeFormat() {
		int i = tb.getRowCount() - 1;

		String str1 = String.valueOf(model.getValueAt(i, 4));

		int j = SolDateFormatter.convertHrsToMinute(str1);
		String str2 = SolDateFormatter.convertMinuteToHRS(j);

		model.setValueAt(str2, i, 4);
	}

	public void getExtraHrsLIST(String paramString) {
		model.setNumRows(0);
		model.addRow(new Object[] { "<html><CENTER><Font size='5' color='white'><b>EMP Code",
				"<html><CENTER><font size='8'><B>" + cb.getSelectedItem() + "<br><FONT size='3'> ( "
						+ fromcb.getText() + " - " + tocb.getText() + " )" });

		float f2 = 0.0F;
		String str2;
		String str3;
		float f5;
		try {
			con = DBConnectionUtil.getConnection();
			stat = con.createStatement();
			rs = stat.executeQuery(paramString);

			while (rs.next()) {
				String str1 = new String(rs.getString(1));
				str2 = new String(rs.getString(2));
				str3 = new String(rs.getString(3));
				String str4 = SolDateFormatter.SQLtoDDMMYY(rs.getDate(4));
				f5 = rs.getFloat(5);

				int k = SolDateFormatter.convertHrsToMinute(String.valueOf(f5));
				String str6 = SolDateFormatter.convertMinuteToHRS(k);

				model.addRow(new Object[] { str1, str2.toUpperCase(), str3.trim(), str4, String.valueOf(str6) });
			}
		} catch (Exception localException) {
			System.out.println(localException);
		}

		for (int i = 1; i < tb.getRowCount(); i++) {
			str2 = String.valueOf(model.getValueAt(i, 0));
			str3 = String.valueOf(model.getValueAt(i, 2));

			float f4 = Float.parseFloat(String.valueOf(model.getValueAt(i, 4)));
			f2 += f4;
			f5 = 0.0F;
		}

		int i = 0;
		for (int j = 1; j < tb.getRowCount(); j++) {
			str3 = String.valueOf(model.getValueAt(j, 0));
			String str5 = String.valueOf(model.getValueAt(j - 1, 0));

			if (!str3.equalsIgnoreCase(str5)) {
				i += 1;
			}
		}

		if (model.getRowCount() > 0) {

			model.addRow(
					new Object[] { "TOTAL : " + String.valueOf(i), "", "", "", String.valueOf(df4.format(f2)) });
			model.setValueAt("<HTML><CENTER>Used Hrs./EFF. Hrs.<BR><Font Size='5' Color='Yellow'>"
					+ String.valueOf(df.format(f2)), 0, 3);
		}

		showTimeFormat();
	}

	public void createSheet(String paramString) {
		tb2.setModel(model2 = new DefaultTableModel(data2, column2));

		String[] arrayOfString = tm.getOptionArray(paramString);

		for (int i = 0; i < arrayOfString.length; i++) {
			model2.addColumn(arrayOfString[i]);
		}

		model2.addRow(new Object[] { "<html><CENTER><Font size='5' color='white'><b>" + emc.getText(),
				"<html><CENTER><font size='8'><B>" + cb.getSelectedItem() + "<br><FONT size='3'> ( "
						+ fromcb.getText() + " - " + tocb.getText() + " )" });

		for (int i = 0; i < tb2.getColumnCount(); i++) {
			tb2.getColumnModel().getColumn(i).setCellRenderer(skr2);
		}
	}

	public void personReport() {
		ArrayList localArrayList = new ArrayList();
		int i = DateDifferencesUtil.getDayCount(fromcb.getText(), tocb.getText());
		localArrayList = DateDifferencesUtil.getDate(fromcb.getText(), tocb.getText(), i);

		for (int j = 0; j < localArrayList.size(); j++) {
			model2.addRow(new Object[] { localArrayList.get(j) });
		}

		model2.addRow(new Object[] { "Total : " });
	}

	public class myPrint extends Thread implements Runnable {
		public myPrint() {
		}

		public void run() {
			try {
				MessageFormat localMessageFormat1 = new MessageFormat("- {0} -");
				MessageFormat localMessageFormat2 = new MessageFormat("Wastage Hrs Details");
				tb.print(javax.swing.JTable.PrintMode.FIT_WIDTH, localMessageFormat2, localMessageFormat1);
			} catch (PrinterException localPrinterException) {
				System.err.println("Error printing: " + localPrinterException.getMessage());
			}
		}
	}

	public class MyReport extends Thread implements Runnable {
		public MyReport() {
		}

		public void run() {
			float f1 = 0.0F;

			for (int i = 1; i < tb2.getColumnCount(); i++) {
				try {
					Thread.sleep(50L);
				} catch (InterruptedException localInterruptedException) {
					System.out.println("MyReport Thread : " + localInterruptedException);
				}
				for (int j = 1; j < tb2.getRowCount() - 1; j++) {

					model2.setValueAt("0", j, i);

					String str1 = String.valueOf(model2.getValueAt(j, 0)).trim();
					String str2 = "%" + tb2.getColumnName(i);
					String str3 = "%" + emc.getText();

					java.sql.Date localDate1 = SolDateFormatter.DDMMYYtoSQL(str1);

					SolDateFormatter.DDMMYYtoSQL(fromcb.getText());
					SolDateFormatter.DDMMYYtoSQL(tocb.getText());

					String str4 = "select TOTAL_TIME from HRTIMEMASTER where EMP_CODE like '" + str3
							+ "' and PROJECT_NO like '" + str2 + "' and DATE = '" + localDate1 + "' ";
					try {
						con = DBConnectionUtil.getConnection();
						stat = con.createStatement();
						rs = stat.executeQuery(str4);

						while (rs.next()) {
							float f2 = rs.getFloat(1);
							f1 += f2;

							int k = SolDateFormatter.convertHrsToMinute(String.valueOf(f2));
							String str5 = SolDateFormatter.convertMinuteToHRS(k);

							model2.setValueAt(str5, j, i);
						}
					} catch (Exception localException) {
						System.out.println(localException);
					}
				}

				int j = SolDateFormatter.convertHrsToMinute(String.valueOf(f1));
				String str1 = SolDateFormatter.convertMinuteToHRS(j);

				model2.setValueAt(String.valueOf(str1), tb2.getRowCount() - 1, i);
				f1 = 0.0F;
			}
		}
	}

	public void removeRow() {
		int i = tb2.getRowCount();

		for (int j = 1; j < i; j++) {
			int k = 0;
			for (int m = 1; m < tb2.getColumnCount(); m++) {
				String str = String.valueOf(model2.getValueAt(j, m));

				if (str.trim().equalsIgnoreCase("0")) {
					k += 1;
				}
			}

			if (k == tb2.getColumnCount() - 1) {
				model2.removeRow(j);
				i = tb2.getRowCount();
			}
		}
	}

	public void actionPerformed(ActionEvent paramActionEvent) {
		Object localObject1;
		Object localObject2;
		String str1;
		if (paramActionEvent.getSource() == cb) {
			localObject1 = String.valueOf(cb.getSelectedItem());
			localObject2 = ((String) localObject1).substring(0, 2).trim();
			str1 = "%" + (String) localObject2;
		}

		if (paramActionEvent.getSource() == b3) {
			removeRow();
		}
		Object localObject3;
		Object localObject4;
		String str2;
		if (paramActionEvent.getSource() == b1) {
			localObject1 = String.valueOf(cb.getSelectedItem());
			localObject2 = ((String) localObject1).substring(0, 2).trim();
			str1 = "%" + (String) localObject2;

			if (tabpane.getSelectedIndex() == 0) {
				localObject3 = SolDateFormatter.DDMMYYtoSQL(fromcb.getText());
				localObject4 = SolDateFormatter.DDMMYYtoSQL(tocb.getText());

				str2 = "select DISTINCT(EMP_CODE), EMP_NAME, D_C, DATE, SUM(TOTAL_TIME) from HRTIMEMASTER where D_C like '"
						+ str1 + "' and DATE between '" + localObject3 + "' and '" + localObject4
						+ "' group by EMP_CODE order by emp_code, date";
				getExtraHrsLIST(str2);
			}
			if (tabpane.getSelectedIndex() == 1) {
				createSheet((String) localObject2);
				personReport();
				localObject3 = new ExtraTime.MyReport();
				((ExtraTime.MyReport) localObject3).start();
			}
		}

		if (paramActionEvent.getSource() == b2) {
			localObject1 = new ExtraTime.myPrint();
			((ExtraTime.myPrint) localObject1).start();
		}

		if (paramActionEvent.getSource() == popm1) {
			localObject1 = SolDateFormatter.DDMMYYtoSQL(fromcb.getText());
			localObject2 = SolDateFormatter.DDMMYYtoSQL(tocb.getText());

			str1 = String.valueOf(cb.getSelectedItem());
			localObject3 = "%" + str1.substring(0, 2).trim();

			localObject4 = String.valueOf(model.getValueAt(tb.getSelectedRow(), 0));
			str2 = null;

			Boolean localBoolean = Boolean.valueOf(popm1.getState());
			if (!localBoolean.booleanValue()) {
				str2 = "select EMP_CODE, EMP_NAME, D_C, DATE, TOTAL_TIME from HRTIMEMASTER where D_C like '"
						+ (String) localObject3 + "' and DATE between '" + localObject1 + "' and '" + localObject2
						+ "' order by emp_code, date";
				popm1.setText("Group This Table");
			}
			if (localBoolean.booleanValue() == true) {
				str2 = "select DISTINCT(EMP_CODE), EMP_NAME, D_C, DATE, SUM(TOTAL_TIME) from HRTIMEMASTER where D_C like '"
						+ (String) localObject3 + "' and DATE between '" + localObject1 + "' and '" + localObject2
						+ "' group by EMP_CODE order by emp_code, date";
				popm1.setText("UnGroup This Table");
			}
			getExtraHrsLIST(str2);

			for (int i = 1; i < tb.getRowCount() - 1; i++) {
				String str3 = String.valueOf(model.getValueAt(i, 0));
				if (str3.equalsIgnoreCase((String) localObject4)) {
					tb.setRowSelectionInterval(i, i);
					tb.scrollRectToVisible(tb.getCellRect(i, i, true));
				}
			}
		}
	}

	public void mousePressed(MouseEvent paramMouseEvent) {
		if (paramMouseEvent.getSource() == tb) {
			if (javax.swing.SwingUtilities.isRightMouseButton(paramMouseEvent)) {
				pop.show((javax.swing.JComponent) paramMouseEvent.getSource(), paramMouseEvent.getX(),
						paramMouseEvent.getY());
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
