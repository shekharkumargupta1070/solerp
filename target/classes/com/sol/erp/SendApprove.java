package com.sol.erp;

import java.awt.Color;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import com.sol.erp.util.DBConnectionUtil;

public class SendApprove implements java.awt.event.ActionListener, java.awt.event.MouseListener {
	
	Connection con;
	Statement stat;
	ResultSet rs;
	int affected = 0;

	String[] approvestr = { "0", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q",
			"R", "S", "T", "U", "V", "W", "X", "Y", "Z" };

	String[] columnNames1 = { "Code", "Item Name", "DRG No", "Drg Status", "Detailer", "Checker", "Sent Date", "APP",
			"BFA", "FAB", "RFI", "REV", "Remarks" };
	Object[][] data1 = new Object[0][];

	java.text.DecimalFormat df = new java.text.DecimalFormat("00.00");
	java.text.DecimalFormat df1 = new java.text.DecimalFormat("00");

	int v = 20;
	int h = 32;

	DefaultTableModel model1 = new DefaultTableModel(data1, columnNames1);
	JTable tb1 = new JTable(model1) {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public boolean isCellEditable(int paramAnonymousInt1, int paramAnonymousInt2) {
			int i = convertColumnIndexToModel(paramAnonymousInt2);
			return i < 0;
		}
	};
	javax.swing.JScrollPane sp1 = new javax.swing.JScrollPane(tb1);

	javax.swing.JInternalFrame f = new javax.swing.JInternalFrame("Drawin Log", true, true, true, true);

	javax.swing.JLabel topimagelabel = new javax.swing.JLabel("Drawing LOG/STATUS");
	javax.swing.JLabel l1 = new javax.swing.JLabel("Project No");
	javax.swing.JLabel l2 = new javax.swing.JLabel("Project Name");
	javax.swing.JLabel l3 = new javax.swing.JLabel("Client Name");
	javax.swing.JLabel l4 = new javax.swing.JLabel("Team Leader");

	javax.swing.JLabel southl1 = new javax.swing.JLabel("Fetched Row");
	javax.swing.JLabel southl2 = new javax.swing.JLabel("Fresh Drg");
	javax.swing.JLabel southl3 = new javax.swing.JLabel("Detailed");
	javax.swing.JLabel southl4 = new javax.swing.JLabel("Checked");

	JTextField southtf1 = new JTextField(4);
	JTextField southtf2 = new JTextField(4);
	JTextField southtf3 = new JTextField(4);
	JTextField southtf4 = new JTextField(4);

	JComboBox tf1 = new JComboBox();
	JTextField tf2 = new JTextField(15);
	JTextField tf3 = new JTextField(15);
	JTextField tf4 = new JTextField(5);

	JButton approvebut = new JButton("Send for Approval");
	JButton fabbut = new JButton("Ready for Fab");
	JButton markbut = new JButton("Mark Correction");
	JButton viewbut = new JButton("Filter View");
	JButton closebut = new JButton("Close", new javax.swing.ImageIcon("image/close.gif"));

	javax.swing.JMenuBar menubar = new javax.swing.JMenuBar();
	JMenu ma = new JMenu("File");
	JMenu mb = new JMenu("Approvals");
	JMenu mc = new JMenu("AllRecords");

	JMenuItem printmenu = new JMenuItem("Print Report");
	JMenuItem ma1 = new JMenuItem("Make HTML Report");
	JMenuItem ma2 = new JMenuItem("Make XLS Report");
	JMenuItem ma3 = new JMenuItem("Exit");

	JMenuItem mc1 = new JMenuItem("Fresh DRG");
	JMenuItem mc2 = new JMenuItem("Under Process DRG");
	JMenuItem mc3 = new JMenuItem("Completed DRG");
	JMenuItem mc4 = new JMenuItem("View All DRG");

	javax.swing.JPopupMenu viewpop = new javax.swing.JPopupMenu();
	JMenuItem vpop = new JMenuItem("Ready For Fab", new javax.swing.ImageIcon("image/check.gif"));
	JMenuItem vpop1 = new JMenuItem("In Approval/Not Answered", new javax.swing.ImageIcon("image/check.gif"));
	JMenuItem vpop2 = new JMenuItem("In Correction", new javax.swing.ImageIcon("image/check.gif"));
	JMenuItem vpop3 = new JMenuItem("View All", new javax.swing.ImageIcon("image/check.gif"));

	javax.swing.JPopupMenu tabpop = new javax.swing.JPopupMenu();
	JMenuItem tpop1 = new JMenuItem("Remove From Approval", new javax.swing.ImageIcon("image/close.gif"));

	java.util.GregorianCalendar gcal = new java.util.GregorianCalendar();
	int currentdt = gcal.get(5);
	int currentmn = gcal.get(2);
	int currentyr = gcal.get(1);

	String datestr = String.valueOf(df1.format(currentdt)) + "/" + String.valueOf(df1.format(currentmn)) + "/"
			+ String.valueOf(currentyr);

	JPanel northp1 = new JPanel();
	JPanel northp2 = new JPanel();
	JPanel northp3 = new JPanel();

	JPanel butpanel1 = new JPanel();
	JPanel butpanel2 = new JPanel();

	JPanel centerpanel = new JPanel();
	JPanel northpanel = new JPanel();

	java.awt.Font fo = new java.awt.Font("Verdana", 0, 10);
	java.awt.Font bigfo = new java.awt.Font("Tahoma", 1, 30);
	javax.swing.border.Border line = javax.swing.BorderFactory.createLineBorder(Color.darkGray);
	javax.swing.border.Border bor1 = javax.swing.BorderFactory.createTitledBorder(line, "Drawing LOG/STATUS", 0, 0, fo,
			new Color(60, 130, 130));
	javax.swing.border.Border bor2 = javax.swing.BorderFactory.createTitledBorder(line, "Project Status", 0, 0, fo,
			Color.darkGray);

	TableColumn col1 = tb1.getColumnModel().getColumn(0);
	TableColumn col2 = tb1.getColumnModel().getColumn(1);
	TableColumn col3 = tb1.getColumnModel().getColumn(2);
	TableColumn col4 = tb1.getColumnModel().getColumn(3);
	TableColumn col5 = tb1.getColumnModel().getColumn(4);
	TableColumn col6 = tb1.getColumnModel().getColumn(5);
	TableColumn col7 = tb1.getColumnModel().getColumn(6);
	TableColumn col8 = tb1.getColumnModel().getColumn(7);
	TableColumn col9 = tb1.getColumnModel().getColumn(8);
	TableColumn col10 = tb1.getColumnModel().getColumn(9);
	TableColumn col11 = tb1.getColumnModel().getColumn(10);
	TableColumn col12 = tb1.getColumnModel().getColumn(11);
	TableColumn col13 = tb1.getColumnModel().getColumn(12);

	java.awt.Toolkit tk = java.awt.Toolkit.getDefaultToolkit();
	java.awt.Dimension ss = tk.getScreenSize();
	java.awt.Dimension fs = f.getSize();

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

			if (paramInt2 == 1) {
				setHorizontalAlignment(2);
			}

			if ((paramInt2 == 0) || (paramInt2 > 1)) {

				setHorizontalAlignment(0);
			}

			String str1 = String.valueOf(model1.getValueAt(paramInt1, 3));
			String str2 = String.valueOf(model1.getValueAt(paramInt1, 4));
			String str3 = String.valueOf(model1.getValueAt(paramInt1, 5));

			String str4 = String.valueOf(model1.getValueAt(paramInt1, 7));
			String str5 = String.valueOf(model1.getValueAt(paramInt1, 8));
			String str6 = String.valueOf(model1.getValueAt(paramInt1, 9));

			if ((str1.equalsIgnoreCase("Under Process")) && (str2.length() >= 1) && (str3.length() <= 1)) {
				setBackground(new Color(220, 220, 250));
				setForeground(Color.darkGray);
			}
			if ((str1.equalsIgnoreCase("Under Process")) && (str3.length() > 1)) {
				setBackground(new Color(220, 250, 220));
				setForeground(Color.darkGray);
			}

			if ((str1.equalsIgnoreCase("Completed")) && (str3.length() > 1)) {
				setForeground(new Color(50, 150, 50));
				setBackground(Color.white);
			}

			if ((str1.equalsIgnoreCase("Completed")) && (str3.length() <= 1)) {
				setForeground(new Color(50, 50, 150));
				setBackground(Color.white);
			}

			if (str1.equalsIgnoreCase("0")) {
				setBackground(Color.white);
				setForeground(Color.black);
			}

			if (paramInt2 == 7) {
				if (str4.equalsIgnoreCase("A")) {
					setBackground(Color.white);
					setForeground(Color.darkGray);
				}

				if (str4.equalsIgnoreCase("B")) {
					setBackground(new Color(120, 220, 250));
					setForeground(Color.blue);
				}
				if (str4.equalsIgnoreCase("mainContainer")) {
					setBackground(new Color(250, 220, 225));
					setForeground(Color.red);
				}
			}

			if (paramInt2 == 8) {
				if (str5.equalsIgnoreCase("C")) {
					setBackground(Color.red);
					setForeground(Color.white);
				}
			}

			if (paramInt2 == 9) {
				if (Integer.parseInt(str6) > 0) {
					setBackground(new Color(180, 80, 180));
					setForeground(Color.white);
				}
			}

			super.getTableCellRendererComponent(paramJTable, paramObject, paramBoolean1, paramBoolean2, paramInt1,
					paramInt2);

			return this;
		}
	}

	ColoredTableCellRenderer skr = new ColoredTableCellRenderer();

	java.awt.Container c;

	public void px() {
		c = f.getContentPane();
		c.setLayout(new java.awt.BorderLayout());
		northp1.setLayout(new java.awt.FlowLayout(0));
		northp2.setLayout(new java.awt.GridLayout(2, 2, 2, 2));
		northpanel.setLayout(new java.awt.BorderLayout());
		butpanel1.setLayout(new java.awt.FlowLayout());
		butpanel2.setLayout(new java.awt.FlowLayout());
		centerpanel.setLayout(new java.awt.BorderLayout());

		northpanel.setBorder(bor1);
		sp1.setBorder(bor2);

		tf1.setPreferredSize(new java.awt.Dimension(100, 20));

		for (int i = 0; i < tb1.getColumnCount(); i++) {
			tb1.getColumnModel().getColumn(i).setCellRenderer(skr);
		}

		col1.setPreferredWidth(60);
		col2.setPreferredWidth(130);
		col3.setPreferredWidth(80);
		col4.setPreferredWidth(100);
		col5.setPreferredWidth(65);
		col6.setPreferredWidth(65);
		col7.setPreferredWidth(100);
		col8.setPreferredWidth(30);
		col9.setPreferredWidth(30);
		col10.setPreferredWidth(30);
		col11.setPreferredWidth(40);
		col12.setPreferredWidth(40);
		col13.setPreferredWidth(240);

		ma.setMnemonic(70);
		mb.setMnemonic(86);
		mc.setMnemonic(65);

		ma.add(printmenu);
		ma.addSeparator();
		ma.add(ma1);
		ma.add(ma2);
		ma.addSeparator();
		ma.add(ma3);

		mb.add(vpop);
		mb.addSeparator();
		mb.add(vpop1);
		mb.add(vpop2);
		mb.addSeparator();
		mb.add(vpop3);

		mc.add(mc1);

		mc.add(mc3);
		mc.addSeparator();
		mc.add(mc4);

		menubar.add(ma);
		menubar.add(mb);
		menubar.add(mc);

		tb1.setFont(fo);
		tb1.setRowHeight(20);
		tb1.setAutoResizeMode(0);
		tb1.setGridColor(new Color(245, 245, 245));

		tb1.getTableHeader().setPreferredSize(new java.awt.Dimension(50, 30));
		tb1.getTableHeader().setFont(fo);
		tb1.setRowHeight(21);

		tb1.setAutoCreateRowSorter(true);

		northp1.add(l1);
		northp1.add(tf1);
		tf1.setEditable(true);
		northp1.add(l2);
		northp1.add(tf2);
		tf2.setEditable(false);
		northp1.add(l3);
		northp1.add(tf3);
		tf3.setEditable(false);
		northp1.add(l4);
		northp1.add(tf4);
		tf4.setEditable(false);

		tf1.setFont(fo);
		tf2.setFont(fo);
		tf3.setFont(fo);
		tf4.setFont(fo);

		tabpop.add(tpop1);

		southtf1.setEditable(false);
		southtf2.setEditable(false);
		southtf3.setEditable(false);
		southtf4.setEditable(false);

		butpanel2.add(southl1);
		butpanel2.add(southtf1);
		southtf1.setFont(fo);
		butpanel2.add(southl2);
		butpanel2.add(southtf2);
		southtf2.setFont(fo);
		butpanel2.add(southl3);
		butpanel2.add(southtf3);
		southtf3.setFont(fo);
		butpanel2.add(southl4);
		butpanel2.add(southtf4);
		southtf4.setFont(fo);

		butpanel2.add(approvebut);
		approvebut.setMnemonic(83);
		butpanel2.add(markbut);
		markbut.setMnemonic(77);
		butpanel2.add(fabbut);
		fabbut.setMnemonic(70);

		topimagelabel.setFont(bigfo);
		topimagelabel.setForeground(Color.red);
		northpanel.setBackground(Color.white);
		northpanel.add(topimagelabel, "West");
		northpanel.setPreferredSize(new java.awt.Dimension(100, 80));

		centerpanel.add(northp1, "North");
		centerpanel.add(sp1, "Center");
		centerpanel.add(butpanel2, "South");

		c.add(centerpanel, "Center");

		printmenu.addActionListener(this);
		approvebut.addActionListener(this);
		markbut.addActionListener(this);
		viewbut.addActionListener(this);
		fabbut.addActionListener(this);
		closebut.addActionListener(this);
		tf1.addActionListener(this);
		tb1.addMouseListener(this);

		approvebut.addMouseListener(this);
		markbut.addMouseListener(this);
		viewbut.addMouseListener(this);
		closebut.addMouseListener(this);
		fabbut.addMouseListener(this);

		ma1.addActionListener(this);
		ma2.addActionListener(this);
		ma3.addActionListener(this);

		vpop.addActionListener(this);
		vpop1.addActionListener(this);
		vpop2.addActionListener(this);
		vpop3.addActionListener(this);

		mc1.addActionListener(this);
		mc2.addActionListener(this);
		mc3.addActionListener(this);
		mc4.addActionListener(this);

		tpop1.addActionListener(this);

		f.setJMenuBar(menubar);

		f.setLocation((ss.width - fs.width) / 15, (ss.height - fs.height) / 170);
		f.setSize(900, 600);
		f.setVisible(true);
	}

	public class myPrint extends Thread implements Runnable {
		public myPrint() {
		}

		public void run() {
			String str1 = tf1.getSelectedItem() + ": " + tf2.getText();

			java.util.Date localDate = new java.util.Date();
			java.text.SimpleDateFormat localSimpleDateFormat = new java.text.SimpleDateFormat("dd/MM/yyyy");
			String str2 = null;
			try {
				str2 = localSimpleDateFormat.format(localDate);

				java.text.MessageFormat localMessageFormat1 = new java.text.MessageFormat(str1);
				java.text.MessageFormat localMessageFormat2 = new java.text.MessageFormat(str2 + " Page{0}");
				tb1.print(javax.swing.JTable.PrintMode.FIT_WIDTH, localMessageFormat1, localMessageFormat2);
			} catch (java.awt.print.PrinterException localPrinterException) {
				System.err.println("Error printing: " + localPrinterException.getMessage());
			}
		}
	}

	public void paramUser() {
		String str = TechMainFrame.textFieldPost.getText();
		if ((str.equalsIgnoreCase("Detailer")) || (str.equalsIgnoreCase("Checker"))) {
			approvebut.setEnabled(false);
			markbut.setEnabled(false);
			viewbut.setEnabled(false);
			fabbut.setEnabled(false);

			vpop.setEnabled(false);
			vpop1.setEnabled(false);
			vpop2.setEnabled(false);
			vpop3.setEnabled(false);

			tpop1.setEnabled(false);
		}
	}

	public void projectList() {
		tf1.removeAllItems();
		try {
			con = DBConnectionUtil.getConnection();
			stat = con.createStatement();
			rs = stat.executeQuery("select distinct(project_no) from estimation ");
			rs.getMetaData().getColumnCount();
			while (rs.next()) {
				String str = new String(rs.getString(1));
				tf1.addItem(str);
			}
		} catch (Exception localException) {
			System.out.println(localException);
		}
	}

	public void projectName() {
		try {
			con = DBConnectionUtil.getConnection();
			stat = con.createStatement();
			rs = stat.executeQuery("select project_name,client_name,Co_code from project_co where Project_no='"
					+ tf1.getSelectedItem() + "' ");
			rs.getMetaData().getColumnCount();
			while (rs.next()) {
				String str1 = new String(rs.getString(1));
				String str2 = new String(rs.getString(2));
				String str3 = new String(rs.getString(3));

				tf2.setText(str1);
				tf3.setText(str2);
				tf4.setText(str3);
			}
		} catch (Exception localException) {
			System.out.println(localException);
		}
	}

	int prevrow = 0;

	public void drgList() {
		model1.setNumRows(0);
		try {
			stat = con.createStatement();
			rs = stat.executeQuery("select distinct(drg_no) from timesheet where Project_no='" + tf1.getSelectedItem()
					+ "' order by drg_no  ");
			rs.getMetaData().getColumnCount();
			while (rs.next()) {
				String str = new String(rs.getString(1));
				model1.addRow(new Object[] { "0", "0", str, "0", "0", "0", "0", "0", "0", "0", "0", "0", "0" });
			}
		} catch (Exception localException) {
			System.out.println(localException);
		}
		prevrow = model1.getRowCount();
	}

	public void drgList2() {
		model1.setNumRows(0);
		try {
			stat = con.createStatement();
			rs = stat.executeQuery("select ITEM_CODE, DRAWING_NO FROM DRAWINGNO where Project_no='"
					+ tf1.getSelectedItem() + "' order by drawing_no ");
			rs.getMetaData().getColumnCount();
			while (rs.next()) {
				String str1 = new String(rs.getString(1));
				String str2 = new String(rs.getString(2));
				model1.addRow(new Object[] { str1, "", str2, "Fresh" });
			}
		} catch (Exception localException) {
			System.out.println(localException);
		}
	}

	public void freshDrgList() {
		model1.setNumRows(0);
		try {
			stat = con.createStatement();
			rs = stat.executeQuery("select ITEM_CODE, DRAWING_NO FROM DRAWINGNO where Project_no='"
					+ tf1.getSelectedItem() + "' and drawing_no not in(select drg_no from timesheet WHERE PROJECT_NO='"
					+ tf1.getSelectedItem()
					+ "' ) and drawing_no not in (select drg_no from under_process WHERE PROJECT_NO='"
					+ tf1.getSelectedItem() + "' )  ORDER BY ITEM_CODE   ");
			rs.getMetaData().getColumnCount();
			while (rs.next()) {
				String str1 = new String(rs.getString(1));
				String str2 = new String(rs.getString(2));
				model1.addRow(new Object[] { str1, "", str2, "0" });
			}
		} catch (Exception localException) {
			System.out.println(localException);
		}
	}

	public void underProcessLIst() {
		model1.setNumRows(0);
		try {
			stat = con.createStatement();
			rs = stat.executeQuery("select ITEM_CODE, DRG_NO, DTL_CHK FROM Under_Process where Project_no='"
					+ tf1.getSelectedItem() + "' and drg_status='Under Process' ORDER BY ITEM_CODE ");
			rs.getMetaData().getColumnCount();
			while (rs.next()) {
				String str1 = new String(rs.getString(1));
				String str2 = new String(rs.getString(2));
				String str3 = new String(rs.getString(3));

				if (str3.equalsIgnoreCase("Detail")) {
					model1.addRow(new Object[] { str1, "", str2, "Under Process", "Detail", "0" });
				}
				if (str3.equalsIgnoreCase("Check")) {
					model1.addRow(new Object[] { str1, "", str2, "Under Process", "0", "Check" });
				}
			}
		} catch (Exception localException) {
			System.out.println(localException);
		}
	}

	public void completedList() {
		model1.setNumRows(0);
		try {
			stat = con.createStatement();
			rs = stat.executeQuery("select ITEM_CODE, DRG_NO FROM TimeSheet where Project_no='" + tf1.getSelectedItem()
					+ "' and drgstatus1 like '%Check%' ORDER BY ITEM_CODE ");
			rs.getMetaData().getColumnCount();
			while (rs.next()) {
				String str1 = new String(rs.getString(1));
				String str2 = new String(rs.getString(2));

				System.out.println(str1 + "\t" + str2);
				model1.addRow(new Object[] { str1, "", str2, "Completed" });
			}
		} catch (Exception localException) {
			System.out.println(localException);
		}
	}

	public void itemList() {
		int i = model1.getRowCount();
		for (int j = 0; j < i; j++) {
			String str1 = String.valueOf(model1.getValueAt(j, 2));
			try {
				stat = con.createStatement();
				rs = stat.executeQuery("select item_code from drawingno where Project_no='" + tf1.getSelectedItem()
						+ "' and drawing_no='" + str1 + "' ORDER BY ITEM_CODE ");
				rs.getMetaData().getColumnCount();

				while (rs.next()) {
					String str2 = new String(rs.getString(1));
					model1.setValueAt(str2, j, 0);
				}
			} catch (Exception localException) {
				System.out.println(localException);
			}
		}
	}

	public void itemName() {
		int i = model1.getRowCount();
		for (int j = 0; j < i; j++) {
			String str1 = String.valueOf(model1.getValueAt(j, 0));
			try {
				stat = con.createStatement();
				rs = stat.executeQuery("select item_name from itemcode where item_code='" + str1 + "'   ");
				rs.getMetaData().getColumnCount();
				while (rs.next()) {
					String str2 = new String(rs.getString(1));
					model1.setValueAt(str2, j, 1);
				}
			} catch (Exception localException) {
				System.out.println(localException);
			}
		}
	}

	public void RFINO() {
		int i = model1.getRowCount();
		String str1 = String.valueOf(tf1.getSelectedItem());

		for (int j = 0; j < i; j++) {
			String str2 = String.valueOf(model1.getValueAt(j, 2));
			try {
				stat = con.createStatement();
				rs = stat.executeQuery(
						"select SOL_NO from AFFECTEDITEM where drg_no='" + str2 + "' and project_no ='" + str1 + "'  ");
				rs.getMetaData().getColumnCount();

				int m = rs.getRow();
				if (m < 1) {
					model1.setValueAt("0", j, 10);
					System.out.println(m);
				}

				while (rs.next()) {
					String str3 = new String(rs.getString(1));
					model1.setValueAt(str3, j, 10);
					System.out.println(str3);
				}
			} catch (Exception localException) {
				System.out.println(localException);
			}
		}
	}

	public void REVNO() {
		int i = model1.getRowCount();
		String str1 = String.valueOf(tf1.getSelectedItem());

		for (int j = 0; j < i; j++) {
			String str2 = String.valueOf(model1.getValueAt(j, 2));
			try {
				stat = con.createStatement();
				rs = stat.executeQuery(
						"select DELTA_NO from DELTA where drg_no='" + str2 + "' and project_no = '" + str1 + "'  ");
				rs.getMetaData().getColumnCount();

				int m = rs.getRow();
				if (m < 1) {
					model1.setValueAt("0", j, 11);
				}

				while (rs.next()) {
					String str3 = new String(rs.getString(1));
					model1.setValueAt(str3, j, 11);
				}
			} catch (Exception localException) {
				System.out.println(localException);
			}
		}
	}

	public void removeList() {
		int i = tb1.getSelectedRow();
		String str1 = String.valueOf(model1.getValueAt(i, 2));
		String str2 = String.valueOf(tf1.getSelectedItem());
		try {
			stat = con.createStatement();
			affected = stat.executeUpdate(
					"delete from approval_data where project_no='" + str2 + "' and drawing_no='" + str1 + "' ");

			if (affected > 0) {
				javax.swing.JOptionPane.showMessageDialog(f, str1 + " Removed.");
				model1.removeRow(i);
			}
		} catch (Exception localException) {
			javax.swing.JOptionPane.showMessageDialog(f, localException.getMessage().toString());
		}
	}

	public void drgStatus() {
		int i = model1.getRowCount();

		for (int j = 0; j < i; j++) {
			String str1 = String.valueOf(model1.getValueAt(j, 2));
			try {
				stat = con.createStatement();
				rs = stat.executeQuery("select drg_status, emp_code,DTL_CHK from Under_Process where Project_no='"
						+ tf1.getSelectedItem() + "' and drg_no='" + str1 + "'   ");

				int k = rs.getRow();
				if (k < 1) {
					model1.setValueAt("0", j, 3);
					model1.setValueAt("0", j, 4);
					model1.setValueAt("0", j, 5);
				}

				while (rs.next()) {

					new String(rs.getString(1));
					String str3 = new String(rs.getString(2));
					String str4 = new String(rs.getString(3));

					if (str4.equalsIgnoreCase("Detail")) {
						model1.setValueAt("Under Process", j, 3);
						model1.setValueAt(str3, j, 4);
						model1.setValueAt("0", j, 5);
					}
					if (str4.equalsIgnoreCase("Check")) {
						model1.setValueAt("Under Process", j, 3);
						model1.setValueAt("0", j, 4);
						model1.setValueAt(str3, j, 5);
					}
				}
			} catch (Exception localException) {
				System.out.println(localException);
			}
		}
	}

	public void drgStatus2() {
		int i = model1.getRowCount();

		for (int j = 0; j < i; j++) {
			String str1 = String.valueOf(model1.getValueAt(j, 2));
			String str2 = String.valueOf(model1.getValueAt(j, 3));
			String str3 = String.valueOf(model1.getValueAt(j, 5));
			try {
				stat = con.createStatement();
				rs = stat.executeQuery("select emp_code, DRGSTATUS,DRGSTATUS1 from TIMESHEET where Project_no='"
						+ tf1.getSelectedItem() + "' and drg_no='" + str1 + "'   ");

				int k = 0;
				while (rs.next()) {
					k += 1;

					String str4 = new String(rs.getString(1));
					String str5 = new String(rs.getString(2));
					String str6 = new String(rs.getString(3));

					if (k == 1) {
						if (str5.equalsIgnoreCase("Detail")) {
							if ((str2.equalsIgnoreCase("Under Process")) && (str3.length() > 1)) {
								model1.setValueAt(str4, j, 4);
							} else {
								model1.setValueAt("Completed", j, 3);
								model1.setValueAt(str4, j, 4);
								model1.setValueAt("0", j, 5);
							}
						}
					}
					if (k == 2) {
						if (str6.equalsIgnoreCase("Check")) {
							model1.setValueAt("Completed", j, 3);
							model1.setValueAt(str4, j, 5);
						}
					}
				}
			} catch (Exception localException) {
				System.out.println(localException);
			}
		}
	}

	public void drgStatus3() {
		int i = model1.getRowCount();

		for (int j = 0; j < i; j++) {
			String str1 = String.valueOf(model1.getValueAt(j, 2));
			try {
				stat = con.createStatement();
				rs = stat.executeQuery("select drgstatus,emp_code from timesheet where Project_no='"
						+ tf1.getSelectedItem() + "' and drg_no='" + str1 + "'   ");

				while (rs.next()) {
					String str2 = new String(rs.getString(1));
					String str3 = new String(rs.getString(2));

					if (str2.equalsIgnoreCase("Detail")) {
						model1.setValueAt("", j, 3);
						model1.setValueAt(str3, j, 4);
						model1.setValueAt("0", j, 5);
					}
					if (!str2.equalsIgnoreCase("Check")) {
					}
				}
			} catch (Exception localException) {
				System.out.println(localException);
			}
		}
	}

	public void itemDetails() {
		int[] arrayOfInt = tb1.getSelectedRows();

		for (int i = 0; i < arrayOfInt.length; i++) {
			String str = String.valueOf(model1.getValueAt(arrayOfInt[i], 2));
			try {
				stat = con.createStatement();
				rs = stat.executeQuery("select max(approve_status),max(fab) from approval_data where Project_no='"
						+ tf1.getSelectedItem() + "' and drawing_no ='" + str + "' ");

				int j = rs.getRow();
				if (j < 0) {
					model1.setValueAt("A", arrayOfInt[i], 7);
					model1.setValueAt("0", arrayOfInt[i], 9);
				}

				while (rs.next()) {
					int k = Integer.parseInt(String.valueOf(rs.getString(1)));
					int m = Integer.parseInt(String.valueOf(rs.getString(2)));
					model1.setValueAt(String.valueOf(approvestr[k]), arrayOfInt[i], 7);
					model1.setValueAt(String.valueOf(m), arrayOfInt[i], 9);

					System.out.println("ApproveString " + k);
				}
			} catch (Exception localException) {
				System.out.println(localException);
			}
		}
	}

	String bfastr = "%";
	String fabstr = " '0','1','2','3','4','5','6' ";

	public void viewAll() {
		model1.setNumRows(0);

		try {
			stat = con.createStatement();
			rs = stat.executeQuery(
					"select item_code,Drawing_No,sent_date,approve_status,BFA_status,fab from APPROVAL_DATA where Project_no='"
							+ tf1.getSelectedItem() + "' and bfa_status like '" + bfastr + "' and fab in (" + fabstr
							+ ") ");
			rs.getMetaData().getColumnCount();
			while (rs.next()) {
				String str1 = new String(rs.getString(1));
				String str2 = new String(rs.getString(2));
				String str3 = new String(rs.getString(3));
				int j = Integer.parseInt(String.valueOf(rs.getString(4)));
				String str4 = new String(rs.getString(5));
				String str5 = new String(rs.getString(6));

				model1.addRow(new Object[] { str1, "", str2, "", "", "", str3, approvestr[j], str4, str5 });
			}
			itemName();
			RFINO();
			REVNO();
		} catch (Exception localException) {
			System.out.println(localException);
		}
	}

	public void approveStatus() {
		int i = tb1.getRowCount();
		for (int j = 0; j < i; j++) {
			String str1 = String.valueOf(model1.getValueAt(j, 0));
			String str2 = String.valueOf(model1.getValueAt(j, 2));

			try {
				stat = con.createStatement();
				rs = stat.executeQuery(
						"select sent_date,approve_status,BFA_status,fab from APPROVAL_DATA where Project_no='"
								+ tf1.getSelectedItem() + "' and item_code='" + str1 + "' and drawing_no='" + str2
								+ "' ");

				int k = rs.getRow();
				if (k < 1) {
					model1.setValueAt("0", j, 6);
					model1.setValueAt("0", j, 7);
					model1.setValueAt("0", j, 8);
					model1.setValueAt("0", j, 9);
				}

				while (rs.next()) {
					String str3 = new String(rs.getString(1));
					String str4 = new String(rs.getString(2));
					String str5 = new String(rs.getString(3));
					String str6 = new String(rs.getString(4));

					model1.setValueAt(str3, j, 6);
					model1.setValueAt(approvestr[Integer.parseInt(str4)], j, 7);
					model1.setValueAt(str5, j, 8);
					model1.setValueAt(str6, j, 9);
				}
			} catch (Exception localException) {
				System.out.println(localException);
			}
		}
	}

	JTextArea ta = new JTextArea();

	public void makeHtml() {
		int i = tb1.getRowCount();

		ta.setText("");
		ta.append("<body bgcolor='white' topmargin='0' leftmargin='0'>");
		ta.append("<title>DRAWING LOG/STATUS</title>");
		ta.append(
				"<center><font face='ARIAL BLACK' SIZE='6'><B>STRUCTURES</B></FONT><font face='TIMES NEW ROMAN' SIZE='6'><I> Online</i><br>");
		ta.append("<font face='System' SIZE=4 color='ORANE'><B>Drawing Log/Status</b></font><br>");
		ta.append(
				"<table width='100%' bgcolor='white'  border='1' cellpadding='0' cellspacing='0' bordercolor='white'><th bgcolor='orane' width='12%' height='35'><Font face='tahoma' color='white' size=1><center><b>Item Name<th bgcolor='orane'><Font face='tahoma' color='white' size=1><center><b>Drg No<th bgcolor='orane'><Font face='tahoma' color='white' size=1><center><b>Drg Status<th bgcolor='orane'><Font face='tahoma' color='white' size=1><center><b>Detailed By<th bgcolor='orane'><Font face='tahoma' color='white' size=1><center><b>Checked By<th bgcolor='orane'><Font face='tahoma' color='white' size=1><center><b>Sent Date<th bgcolor='orane'><Font face='tahoma' color='white' size=1><center><b>App<th bgcolor='orane'><Font face='tahoma' color='white' size=1><center><b>BFA<th bgcolor='orane'><Font face='tahoma' color='white' size=1><center><b>FAB<th bgcolor='orane'><Font face='tahoma' color='white' size=1><center><b>RFI<th bgcolor='orane'><Font face='tahoma' color='white' size=1><center><b>REV<th bgcolor='orane'><Font face='tahoma' color='white' size=1><center><b>Remarks<tr>");

		ta.append("");
		ta.append("<b><font face='tahoma' SIZE=1 color='orane'>&nbsp PROJECT NAME\t: "
				+ String.valueOf(tf2.getText()).toUpperCase() + "<br></font>");
		ta.append("<font face='tahoma' SIZE=1 color='orane'>&nbsp CLIENT NAME\t&nbsp&nbsp : "
				+ String.valueOf(tf3.getText()).toUpperCase() + "<br><br></font>");

		String str1 = "0";
		for (int j = 0; j < i; j++) {
			String str2 = String.valueOf(model1.getValueAt(j, 1));
			String str3 = String.valueOf(model1.getValueAt(j, 2));
			String str4 = String.valueOf(model1.getValueAt(j, 3));
			String str5 = String.valueOf(model1.getValueAt(j, 4));
			String str6 = String.valueOf(model1.getValueAt(j, 5));
			String str7 = String.valueOf(model1.getValueAt(j, 6));
			String str8 = String.valueOf(model1.getValueAt(j, 7));
			String str9 = String.valueOf(model1.getValueAt(j, 8));
			String str10 = String.valueOf(model1.getValueAt(j, 9));
			String str11 = String.valueOf(model1.getValueAt(j, 10));
			String str12 = String.valueOf(model1.getValueAt(j, 11));
			String str13 = String.valueOf(model1.getValueAt(j, 12));

			if (str13.equalsIgnoreCase("")) {
				str13 = "X";
			}

			ta.append("<td bgcolor='orane' height='20'><Font face='tahoma' color='white' size=1><center><b>" + str2);
			ta.append("<td bgcolor='silver'><Font face='tahoma' color='white' size=1><center><b>" + str3);
			ta.append("<td bgcolor='silver'><Font face='tahoma' color='white' size=1><center><b>" + str4);
			ta.append("<td bgcolor='silver'><Font face='tahoma' color='white' size=1><center><b>" + str5);
			ta.append("<td bgcolor='silver'><Font face='tahoma' color='white' size=1><center><b>" + str6);
			ta.append("<td bgcolor='silver'><Font face='tahoma' color='white' size=1><center><b>" + str7);
			ta.append("<td bgcolor='reen'><Font face='tahoma' color='white' size=1><center><b>" + str8);
			ta.append("<td bgcolor='orane'><Font face='tahoma' color='white' size=1><center><b>" + str9);
			ta.append("<td bgcolor='pink'><Font face='tahoma' color='white' size=1><center><b>" + str10);
			ta.append("<td bgcolor='red'><Font face='tahoma' color='white' size=1><center><b>" + str11);
			ta.append("<td bgcolor='orange'><Font face='tahoma' color='white' size=1><center><b>" + str12);
			ta.append("<td bgcolor='silver'><Font face='tahoma' color='white' size=1><center><b>" + str13 + "<tr>");
			str1 = ta.getText();
		}

		ta.append("</table>");

		try {
			java.io.FileWriter localFileWriter = new java.io.FileWriter("DrgLog.html");

			for (int k = 0; k < str1.length(); k++) {
				localFileWriter.write(str1.charAt(k));
			}
			localFileWriter.close();
			runReport localrunReport = new runReport();
			localrunReport.px();
		} catch (java.io.IOException localIOException) {
			System.out.println(localIOException);
		}
	}

	public void status() {
		int i = tb1.getRowCount();
		int j = 0;
		int k = 0;
		int m = 0;

		for (int n = 0; n < i; n++) {
			String str1 = String.valueOf(model1.getValueAt(n, 3));
			String str2 = String.valueOf(model1.getValueAt(n, 4));
			String str3 = String.valueOf(model1.getValueAt(n, 5));

			if ((str1.equalsIgnoreCase("Completed")) && (str2.length() > 1)) {
				k += 1;
			}
			if ((str1.equalsIgnoreCase("Completed")) && (str3.length() > 1)) {
				m += 1;
			}
			if (str1.equalsIgnoreCase("0")) {
				j += 1;
			}
		}

		southtf1.setText(String.valueOf(i));
		southtf2.setText(String.valueOf(j));
		southtf3.setText(String.valueOf(k));
		southtf4.setText(String.valueOf(m));
	}

	public void actionPerformed(java.awt.event.ActionEvent paramActionEvent) {
		int[] localObject;
		if (paramActionEvent.getSource() == printmenu) {
			// localObject = new PrintAttendanceReport();
			// ((PrintAttendanceReport) localObject).start();
		}
		if (paramActionEvent.getSource() == tf1) {
			model1.setNumRows(0);
			projectName();
			drgList();
			itemList();
			itemName();
			drgStatus();
			drgStatus2();
			RFINO();
			REVNO();
			approveStatus();
			status();
		}

		if (paramActionEvent.getSource() == ma1) {
			makeHtml();
		}

		if (paramActionEvent.getSource() == ma3) {
			f.setVisible(false);
		}
		int i;
		String str1;
		String str2;
		String str3;
		if (paramActionEvent.getSource() == approvebut) {
			itemDetails();

			localObject = tb1.getSelectedRows();

			for (i = 0; i < localObject.length; i++) {
				str1 = String.valueOf(model1.getValueAt(localObject[i], 0));
				str2 = String.valueOf(model1.getValueAt(localObject[i], 2));
				str3 = String.valueOf(model1.getValueAt(localObject[i], 3));
				String str4 = String.valueOf(model1.getValueAt(localObject[i], 7));
				int k = Integer.parseInt(String.valueOf(model1.getValueAt(localObject[i], 9)));

				if (str3.equalsIgnoreCase("Under Process")) {
					javax.swing.JOptionPane.showMessageDialog(f, "Under Process Drawing Cannot be Send For Approvals");
					break;
				}

				if (str4.equalsIgnoreCase("0")) {
					str4 = "1";
				}
				if (str4.equalsIgnoreCase("A")) {
					str4 = "2";
				}
				if (str4.equalsIgnoreCase("B")) {
					str4 = "3";
				}
				if (str4.equalsIgnoreCase("C")) {
					str4 = "4";
				}
				if (str4.equalsIgnoreCase("D")) {
					str4 = "5";
				}
				if (str4.equalsIgnoreCase("E")) {
					str4 = "6";
				}

				int m = Integer.parseInt(str4);

				String str6 = String.valueOf(m);
				String str7 = String.valueOf(k);

				try {
					stat = con.createStatement();
					affected = stat.executeUpdate("delete * from approval_data where project_no='"
							+ tf1.getSelectedItem() + "' and drawing_no='" + str2 + "' and item_code='" + str1 + "'  ");

					if (affected > 0) {
						javax.swing.JOptionPane.showMessageDialog(f, "Deleted " + str6 + "\t" + str7);
					}
				} catch (Exception localException4) {
					javax.swing.JOptionPane.showMessageDialog(f, localException4.getMessage().toString());
				}

				try {
					stat = con.createStatement();
					affected = stat.executeUpdate("insert into APPROVAL_DATA values('" + tf1.getSelectedItem() + "','"
							+ str1 + "','" + str2 + "','0','" + datestr + "','0','0','0','0','0','" + str6 + "','"
							+ str7 + "','" + tf2.getText() + "', '" + tf3.getText() + "', '" + tf4.getText() + "' )");

					if (affected > 0) {
						model1.setValueAt(datestr, localObject[i], 6);
						model1.setValueAt(approvestr[m], localObject[i], 7);
						model1.setValueAt("0", localObject[i], 8);
						model1.setValueAt(str7, localObject[i], 9);

						System.out.println(approvestr[m]);
					}
				} catch (Exception localException5) {
					javax.swing.JOptionPane.showMessageDialog(f, localException5.getMessage().toString());
				}

				try {
					stat = con.createStatement();
					stat.executeUpdate("update timesheet set approve_status='" + str6 + "' where project_no='"
							+ tf1.getSelectedItem() + "' and item_code='" + str1 + "' and drg_no='" + str2 + "' ");
				} catch (Exception localException6) {
					javax.swing.JOptionPane.showMessageDialog(f, localException6.getMessage().toString());
				}
			}
		}
		int j;
		String str5;
		if (paramActionEvent.getSource() == markbut) {
			localObject = tb1.getSelectedRows();
			for (i = 0; i < localObject.length; i++) {
				str1 = (String) tf1.getSelectedItem();
				str2 = String.valueOf(model1.getValueAt(localObject[i], 0));
				str3 = String.valueOf(model1.getValueAt(localObject[i], 2));
				j = Integer.parseInt(String.valueOf(model1.getValueAt(localObject[i], 9)));
				str5 = String.valueOf(j);

				try {
					stat = con.createStatement();
					affected = stat.executeUpdate(" update APPROVAL_DATA set RECEIVE_DATE='" + datestr
							+ "' , BFA_STATUS='C', fab='" + str5 + "' where project_no='" + str1 + "' and item_code='"
							+ str2 + "' and drawing_no='" + str3 + "'  ");

					if (affected > 0) {
						model1.setValueAt(datestr, localObject[i], 6);
						model1.setValueAt("C", localObject[i], 8);
						model1.setValueAt(str5, localObject[i], 9);
					}
				} catch (Exception localException1) {
					javax.swing.JOptionPane.showMessageDialog(f, localException1.getMessage().toString());
				}
			}
		}

		if (paramActionEvent.getSource() == fabbut) {
			localObject = tb1.getSelectedRows();
			for (i = 0; i < localObject.length; i++) {
				str1 = (String) tf1.getSelectedItem();
				str2 = String.valueOf(model1.getValueAt(localObject[i], 0));
				str3 = String.valueOf(model1.getValueAt(localObject[i], 2));
				j = Integer.parseInt(String.valueOf(model1.getValueAt(localObject[i], 9)));
				str5 = String.valueOf(j + 1);

				try {
					stat = con.createStatement();
					affected = stat.executeUpdate(" update APPROVAL_DATA set RECEIVE_DATE='" + datestr
							+ "' , BFA_STATUS='0', fab='" + str5 + "' where project_no='" + str1 + "' and item_code='"
							+ str2 + "' and drawing_no='" + str3 + "'  ");

					if (affected > 0) {
						model1.setValueAt(datestr, localObject[i], 6);
						model1.setValueAt("0", localObject[i], 8);
						model1.setValueAt(str5, localObject[i], 9);
					}
				} catch (Exception localException2) {
					javax.swing.JOptionPane.showMessageDialog(f, localException2.getMessage().toString());
				}

				try {
					stat = con.createStatement();
					stat.executeUpdate("update timesheet set fab_status='" + str5 + "' where project_no='"
							+ tf1.getSelectedItem() + "' and item_code='" + str2 + "' and drg_no='" + str3 + "' ");
				} catch (Exception localException3) {
					javax.swing.JOptionPane.showMessageDialog(f, localException3.getMessage().toString());
				}
			}
		}

		if (paramActionEvent.getSource() == vpop) {

			bfastr = "0";
			fabstr = " '1','2','3','4','5','6' ";
			viewAll();
			drgStatus();
			drgStatus2();
			RFINO();
			REVNO();
		}

		if (paramActionEvent.getSource() == vpop1) {
			fabstr = " '0','1','2','3','4','5','6' ";
			bfastr = "0";
			viewAll();
			drgStatus();
			drgStatus2();
			RFINO();
			REVNO();
		}

		if (paramActionEvent.getSource() == vpop2) {
			fabstr = " '0','1','2','3','4','5','6' ";
			bfastr = "C";
			viewAll();
			drgStatus();
			drgStatus2();
			RFINO();
			REVNO();
		}
		if (paramActionEvent.getSource() == vpop3) {
			fabstr = " '0','1','2','3','4','5','6' ";
			bfastr = "%";
			viewAll();
			drgStatus();
			drgStatus2();
			RFINO();
			REVNO();
		}
		if (paramActionEvent.getSource() == tpop1) {
			removeList();
		}

		if (paramActionEvent.getSource() == mc1) {
			freshDrgList();
			itemName();
			drgStatus();
			drgStatus2();
			RFINO();
			REVNO();
			approveStatus();
			status();
		}
		if (paramActionEvent.getSource() == mc2) {
			underProcessLIst();
			itemName();
			drgStatus();
			drgStatus2();
			RFINO();
			REVNO();
			approveStatus();
			status();
		}
		if (paramActionEvent.getSource() == mc3) {
			completedList();
			itemName();
			drgStatus();
			drgStatus2();
			RFINO();
			REVNO();
			fabstr = " '0','1','2','3','4','5','6' ";
			bfastr = "%";
			approveStatus();
			status();
		}

		if (paramActionEvent.getSource() == mc4) {
			drgList2();
			projectName();
			itemName();
			drgStatus();
			drgStatus2();
			RFINO();
			REVNO();
			approveStatus();

			status();
		}
	}

	public void mousePressed(java.awt.event.MouseEvent paramMouseEvent) {
		if ((paramMouseEvent.getSource() != viewbut) ||

		(paramMouseEvent.getSource() == tb1)) {
			if (javax.swing.SwingUtilities.isRightMouseButton(paramMouseEvent)) {
				tabpop.show((javax.swing.JComponent) paramMouseEvent.getSource(), paramMouseEvent.getX(),
						paramMouseEvent.getY());
			}
		}
	}

	public void mouseReleased(java.awt.event.MouseEvent paramMouseEvent) {
	}

	public void mouseClicked(java.awt.event.MouseEvent paramMouseEvent) {
	}

	public void mouseEntered(java.awt.event.MouseEvent paramMouseEvent) {
	}

	public void mouseExited(java.awt.event.MouseEvent paramMouseEvent) {
	}

}
