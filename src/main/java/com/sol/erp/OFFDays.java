package com.sol.erp;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Hashtable;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.sol.erp.ui.custom.SolCalendar2;
import com.sol.erp.ui.custom.SolCommentBox;
import com.sol.erp.ui.custom.SolProgressMonitor;
import com.sol.erp.ui.custom.SolTableModel;
import com.sol.erp.util.DBConnectionUtil;
import com.sol.erp.util.DateCalculationUtil;
import com.sol.erp.util.formater.SolDateFormatter;

public class OFFDays
		implements java.awt.event.ActionListener, java.awt.event.MouseListener, java.awt.event.ItemListener {

	java.sql.Connection con;

	java.text.DecimalFormat df = new java.text.DecimalFormat("00");
	HolidayList hl = new HolidayList();

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
			setHorizontalAlignment(0);
			if (paramInt2 <= 14)
				if (paramInt1 % 2 == 0) {
					setBackground(new java.awt.Color(240, 240, 240));
					setForeground(java.awt.Color.darkGray);
				} else {
					setBackground(new java.awt.Color(230, 230, 230));
					setForeground(java.awt.Color.darkGray);
				}
			if (paramInt2 > 14)
				if (paramInt1 % 2 == 0) {
					setBackground(new java.awt.Color(160, 200, 230));
					setForeground(java.awt.Color.darkGray);
				} else {
					setBackground(new java.awt.Color(200, 220, 250));
					setForeground(java.awt.Color.darkGray);
				}
			super.getTableCellRendererComponent(paramJTable, paramObject, paramBoolean1, paramBoolean2, paramInt1,
					paramInt2);
			return this;
		}
	}

	public OFFDays() {

		prmonitor = new SolProgressMonitor();
		commentbox = new SolCommentBox();
		cal1 = new SolCalendar2();
		cal2 = new SolCalendar2();
		cal3 = new SolCalendar2();
		cal4 = new SolCalendar2();
		cal5 = new SolCalendar2();
		cal6 = new SolCalendar2();
		cal7 = new SolCalendar2();
		cal8 = new SolCalendar2();
		cal9 = new SolCalendar2();
		cal10 = new SolCalendar2();
		cal11 = new SolCalendar2();
		cal12 = new SolCalendar2();
		yearcb1 = new JComboBox();
		yearlabel = new JLabel("Select Year");
		calendarPanel = new JPanel();
		calendarSP = new javax.swing.JScrollPane(calendarPanel);
		data = new Object[0][];
		data1 = new Object[0][];
		data2 = new Object[0][];
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
				int i = convertColumnIndexToModel(paramAnonymousInt2);
				return i != 0;
			}

		};
		sp = new javax.swing.JScrollPane(tb);
		model1 = new DefaultTableModel(data1, columnNames1);
		tb1 = new JTable(model1) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public Class<? extends Object> getColumnClass(int paramAnonymousInt) {
				return getValueAt(0, paramAnonymousInt).getClass();
			}

			public boolean isCellEditable(int paramAnonymousInt1, int paramAnonymousInt2) {
				int i = convertColumnIndexToModel(paramAnonymousInt2);
				return i != 0;
			}

		};
		sp1 = new javax.swing.JScrollPane(tb1);
		model2 = new DefaultTableModel(data2, columnNames2);
		tb2 = new JTable(model2) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public Class<? extends Object> getColumnClass(int paramAnonymousInt) {
				return getValueAt(0, paramAnonymousInt).getClass();
			}

			public boolean isCellEditable(int paramAnonymousInt1, int paramAnonymousInt2) {
				int i = convertColumnIndexToModel(paramAnonymousInt2);
				return ((i >= 1) && (i <= 7)) || (i == tb2.getColumnCount() - 2);
			}

		};
		sp2 = new javax.swing.JScrollPane(tb2);
		f = new javax.swing.JFrame();
		tabbedpane = new JTabbedPane();
		tabbedpane2 = new JTabbedPane();
		depttab = new JPanel();
		desigtab = new JPanel();
		emptab = new JPanel();
		offtab = new JPanel();
		holidaytab = new JPanel();
		calendartab = new JPanel();
		l1 = new JLabel("Day Of Week");
		l2 = new JLabel("Day");
		l3 = new JLabel("OT Rate");
		l4 = new JLabel("Dept");
		l5 = new JLabel("Group");
		l6 = new JLabel("Single");
		l7 = new JLabel("Permission");
		tf1 = new JTextField(4);
		tf2 = new JTextField(12);
		tf3 = new JTextField(4);
		tf4 = new JTextField();
		tf5 = new JTextField();
		tf6 = new JTextField();
		tf7 = new JTextField();
		coidlabel = new JLabel("Co ID");
		brcodelabel = new JLabel("BR Code");
		coidtf = new JTextField("C01", 3);
		brcodetf = new JTextField("0", 3);
		shiftlabel = new JLabel("Shift");
		cb1 = new JComboBox();
		empcodelabel = new JLabel("Emp Code");
		empnamelabel = new JLabel("Name");
		dojlabel = new JLabel("DOJ");
		otvaluelabel = new JLabel("Value");
		ottypelabel = new JLabel("Type");
		empcodetf = new JTextField(4);
		nametf = new JTextField(22);
		dojtf = new JTextField(8);
		otvaluetf = new JTextField(3);
		ottypecb = new JComboBox();
		ch1 = new javax.swing.JCheckBox("Current Status", true);
		ch2 = new javax.swing.JCheckBox("Editable Mode", false);
		checkboxgroup = new javax.swing.ButtonGroup();
		showbut = new JButton("Previous Status");
		b1 = new JButton("Save");
		b2 = new JButton("Update");
		b3 = new JButton("Close");
		commentbut = new JButton("Comment");
		saveotbut = new JButton("Set OFF Days");
		setOFFDayButton = new JButton("Set OFF Days");
		setOTButton = new JButton("Set OT");
		cleanButton = new JButton("Clean OFF Days");

		setoffdaycheckbox1 = new javax.swing.JRadioButton("Show OFF Days", false);
		setoffdaycheckbox2 = new javax.swing.JRadioButton("Show No OT Days", false);
		setworkdaycheckbox = new javax.swing.JRadioButton("Show Holidays", false);
		checkboxgroup1 = new javax.swing.ButtonGroup();
		centerButtonPanel = new JPanel();
		p1 = new JPanel();
		p2 = new JPanel();
		p3 = new JPanel();
		CALVIEWPANEL = new JPanel();
		CALEASTPANEL = new JPanel();
		southPanel = new JPanel();
		mainPanel = new JPanel();
		calnorthp1 = new JPanel();
		calnorthp2 = new JPanel();
		northPanel = new JPanel();
		northp1 = new JPanel();
		northp2 = new JPanel();
		southp1 = new JPanel();
		southp2 = new JPanel();
		checkotpanel = new JPanel();
		defineotpanel = new JPanel();
		packotpanel = new JPanel();
		fo = new java.awt.Font("Tahoma", 1, 10);
		skr = new OFFDays.ColoredTableCellRenderer();
		ht = new java.util.Hashtable<String, String>();
		dateArrayList = new ArrayList();
	}

	public JPanel DesignFrame() {
		con = DBConnectionUtil.getConnection();
		java.awt.Container localContainer = f.getContentPane();
		localContainer.setLayout(new java.awt.BorderLayout());
		p1.setLayout(new java.awt.FlowLayout(0));
		p2.setLayout(null);
		northPanel.setLayout(new java.awt.BorderLayout());
		northp1.setLayout(new java.awt.FlowLayout(0));
		northp2.setLayout(new java.awt.FlowLayout(2));
		calnorthp1.setLayout(new java.awt.FlowLayout(0));
		calnorthp2.setLayout(new java.awt.FlowLayout(2));
		southp1.setLayout(new java.awt.FlowLayout(0));
		southp2.setLayout(new java.awt.FlowLayout(2));
		offtab.setLayout(new java.awt.BorderLayout());
		holidaytab.setLayout(new java.awt.BorderLayout());
		calendartab.setLayout(new java.awt.BorderLayout());
		calendarPanel.setLayout(new java.awt.GridLayout(3, 4, 3, 3));
		depttab.setLayout(new java.awt.BorderLayout());
		desigtab.setLayout(new java.awt.BorderLayout());
		emptab.setLayout(new java.awt.BorderLayout());
		mainPanel.setLayout(new java.awt.BorderLayout());
		CALVIEWPANEL.setLayout(new java.awt.BorderLayout());
		CALEASTPANEL.setLayout(new java.awt.BorderLayout());
		southPanel.setLayout(new java.awt.BorderLayout());
		centerButtonPanel.setLayout(new java.awt.FlowLayout(0));
		checkotpanel.setLayout(new java.awt.FlowLayout());
		checkotpanel.setLayout(new java.awt.GridLayout(3, 1));
		defineotpanel.setLayout(new java.awt.GridLayout(6, 2, 5, 5));
		packotpanel.setLayout(new java.awt.BorderLayout());
		tabbedpane.add("OFF/OT Days", offtab);
		tabbedpane.add("Office Calendar", calendartab);
		tabbedpane.add("Holiday Setup", holidaytab);

		tabbedpane2.add("Department", depttab);
		tabbedpane2.add("Designation", desigtab);
		tabbedpane2.add("Employee", emptab);
		depttab.add(sp, "Center");
		desigtab.add(sp1, "Center");
		emptab.add(sp2, "Center");
		tb.setRowSelectionAllowed(true);
		tb.setColumnSelectionAllowed(false);
		tb.setCellSelectionEnabled(true);
		tb1.setRowSelectionAllowed(true);
		tb1.setColumnSelectionAllowed(false);
		tb1.setCellSelectionEnabled(true);
		tb2.setRowSelectionAllowed(true);
		tb2.setColumnSelectionAllowed(false);
		tb2.setCellSelectionEnabled(true);
		SolTableModel.decorateTable(tb);
		tb.setIntercellSpacing(new java.awt.Dimension(1, 1));
		tb.setAutoResizeMode(0);
		tb.setSelectionBackground(new java.awt.Color(250, 200, 200));
		tb.setSelectionForeground(java.awt.Color.red);
		SolTableModel.decorateTable(tb1);
		tb1.setIntercellSpacing(new java.awt.Dimension(1, 1));
		tb1.setAutoResizeMode(0);
		tb1.setSelectionBackground(new java.awt.Color(250, 200, 200));
		tb1.setSelectionForeground(java.awt.Color.red);
		SolTableModel.decorateTable(tb2);
		tb2.setIntercellSpacing(new java.awt.Dimension(1, 1));
		tb2.setAutoResizeMode(0);
		tb2.setSelectionBackground(new java.awt.Color(250, 200, 200));
		tb2.setSelectionForeground(java.awt.Color.red);
		tb.getColumnModel().getColumn(0).setCellRenderer(skr);
		tb1.getColumnModel().getColumn(0).setCellRenderer(skr);
		tb2.getColumnModel().getColumn(0).setCellRenderer(skr);
		for (int i = 8; i < tb.getColumnCount(); i++) {
			tb.getColumnModel().getColumn(i).setCellRenderer(skr);
			tb1.getColumnModel().getColumn(i).setCellRenderer(skr);
			tb2.getColumnModel().getColumn(i).setCellRenderer(skr);
		}

		tb.getColumnModel().getColumn(0).setPreferredWidth(200);
		tb1.getColumnModel().getColumn(0).setPreferredWidth(200);
		tb2.getColumnModel().getColumn(0).setPreferredWidth(200);
		for (int i = 1; i <= 7; i++) {
			tb.getColumnModel().getColumn(i).setPreferredWidth(30);
			tb1.getColumnModel().getColumn(i).setPreferredWidth(30);
			tb2.getColumnModel().getColumn(i).setPreferredWidth(30);
		}

		for (int i = 8; i <= 21; i++) {
			tb.getColumnModel().getColumn(i).setPreferredWidth(20);
			tb1.getColumnModel().getColumn(i).setPreferredWidth(20);
			tb2.getColumnModel().getColumn(i).setPreferredWidth(20);
		}

		tb2.getColumnModel().getColumn(23).setCellRenderer(skr);
		tb2.getColumnModel().getColumn(22).setPreferredWidth(38);
		tb2.getColumnModel().getColumn(23).setPreferredWidth(90);
		checkboxgroup.add(ch1);
		checkboxgroup.add(ch2);
		checkboxgroup1.add(setoffdaycheckbox1);
		checkboxgroup1.add(setoffdaycheckbox2);
		checkboxgroup1.add(setworkdaycheckbox);
		northp1.add(coidlabel);
		northp1.add(coidtf);
		northp1.add(brcodelabel);
		northp1.add(brcodetf);
		northp1.add(shiftlabel);
		northp1.add(cb1);
		northPanel.add(northp1, "West");
		northPanel.add(northp2, "East");
		calnorthp1.add(yearlabel);
		calnorthp1.add(yearcb1);
		calnorthp1.add(setoffdaycheckbox1);
		calnorthp1.add(setworkdaycheckbox);

		calendarPanel.setPreferredSize(new java.awt.Dimension(490, 530));
		calendarPanel.setBorder(javax.swing.BorderFactory.createBevelBorder(1));
		CALVIEWPANEL.add(calnorthp1, "North");
		CALVIEWPANEL.add(calendarSP, "Center");
		CALEASTPANEL.add(packotpanel, "North");
		calendartab.add(CALVIEWPANEL, "Center");
		calendartab.add(CALEASTPANEL, "East");
		cb1.addItem("G");
		cb1.addItem("A");
		cb1.addItem("B");
		cb1.addItem("C");
		ottypecb.addItem("F");
		ottypecb.addItem("P");
		p1.add(l1);
		p1.add(tf1);
		p1.add(l2);
		p1.add(tf2);
		p1.add(l3);
		p1.add(tf3);
		tf1.setHorizontalAlignment(4);
		tf2.setHorizontalAlignment(4);
		tf3.setHorizontalAlignment(4);
		tf4.setHorizontalAlignment(4);
		tf5.setHorizontalAlignment(4);
		tf6.setHorizontalAlignment(4);
		tf7.setHorizontalAlignment(4);
		empcodetf.setHorizontalAlignment(4);
		nametf.setHorizontalAlignment(4);
		dojtf.setHorizontalAlignment(4);
		tf1.setFont(fo);
		tf2.setFont(fo);
		tf3.setFont(fo);
		tf3.setEditable(false);
		tf4.setFont(fo);
		tf5.setFont(fo);
		tf6.setFont(fo);
		tf6.setEditable(false);
		tf7.setFont(fo);
		coidtf.setFont(fo);
		brcodetf.setFont(fo);
		empcodetf.setFont(fo);
		nametf.setFont(fo);
		nametf.setEditable(false);
		dojtf.setFont(fo);
		dojtf.setEditable(false);

		for (int i = 2005; i < 2100; i++) {
			yearcb1.addItem(String.valueOf(i));
		}
		calendarPanel.add(cal1.DesignFrame());
		calendarPanel.add(cal2.DesignFrame());
		calendarPanel.add(cal3.DesignFrame());
		calendarPanel.add(cal4.DesignFrame());
		calendarPanel.add(cal5.DesignFrame());
		calendarPanel.add(cal6.DesignFrame());
		calendarPanel.add(cal7.DesignFrame());
		calendarPanel.add(cal8.DesignFrame());
		calendarPanel.add(cal9.DesignFrame());
		calendarPanel.add(cal10.DesignFrame());
		calendarPanel.add(cal11.DesignFrame());
		calendarPanel.add(cal12.DesignFrame());
		cal1.removeTOPPanel();
		cal2.removeTOPPanel();
		cal3.removeTOPPanel();
		cal4.removeTOPPanel();
		cal5.removeTOPPanel();
		cal6.removeTOPPanel();
		cal7.removeTOPPanel();
		cal8.removeTOPPanel();
		cal9.removeTOPPanel();
		cal10.removeTOPPanel();
		cal11.removeTOPPanel();
		cal12.removeTOPPanel();
		cal1.setBorderTitle("January");
		cal2.setBorderTitle("February");
		cal3.setBorderTitle("March");
		cal4.setBorderTitle("April");
		cal5.setBorderTitle("May");
		cal6.setBorderTitle("June");
		cal7.setBorderTitle("July");
		cal8.setBorderTitle("August");
		cal9.setBorderTitle("September");
		cal10.setBorderTitle("October");
		cal11.setBorderTitle("November");
		cal12.setBorderTitle("December");
		p2.setBorder(javax.swing.BorderFactory.createTitledBorder("Urgent OT"));
		defineotpanel.add(otvaluelabel);
		otvaluelabel.setHorizontalAlignment(4);
		defineotpanel.add(otvaluetf);
		defineotpanel.add(ottypelabel);
		ottypelabel.setHorizontalAlignment(4);
		defineotpanel.add(ottypecb);
		defineotpanel.add(new JLabel());
		defineotpanel.add(commentbut);
		defineotpanel.add(new JLabel());
		defineotpanel.add(saveotbut);
		defineotpanel.add(new JLabel());

		packotpanel.add(checkotpanel, "North");
		packotpanel.add(defineotpanel, "Center");
		southp1.add(ch1);
		southp1.add(ch2);
		southp2.add(b1);
		b1.setEnabled(false);
		southp2.add(b3);
		southPanel.add(southp1, "West");
		southPanel.add(southp2, "East");
		offtab.add(tabbedpane2, "North");

		holidaytab.add(hl.DesignFrame(), "Center");
		offtab.add(southPanel, "Center");
		tabbedpane.setBorder(javax.swing.BorderFactory.createBevelBorder(1));
		mainPanel.add(northPanel, "North");
		mainPanel.add(tabbedpane, "Center");
		localContainer.add(mainPanel, "Center");
		setoffdaycheckbox1.addActionListener(this);
		setoffdaycheckbox2.addActionListener(this);
		setworkdaycheckbox.addActionListener(this);
		cleanButton.addActionListener(this);
		commentbut.addActionListener(this);
		saveotbut.addActionListener(this);
		b1.addActionListener(this);
		b2.addActionListener(this);
		b3.addActionListener(this);
		setOFFDayButton.addActionListener(this);
		setOTButton.addActionListener(this);
		ch1.addActionListener(this);
		ch2.addActionListener(this);

		tabbedpane2.addMouseListener(this);
		coidtf.addActionListener(this);
		brcodetf.addActionListener(this);
		cb1.addActionListener(this);
		showbut.addActionListener(this);
		tb.addMouseListener(this);
		tb1.addMouseListener(this);
		tb2.addMouseListener(this);
		yearcb1.addItemListener(this);

		return mainPanel;
	}

	public void showOFFDays() {
		DesignFrame();
		f.setTitle("Setting Working Hrs Validations");
		f.getRootPane().setDefaultButton(b1);
		f.setSize(860, 530);
		f.setLocationRelativeTo(null);
		f.setVisible(true);
	}

	public void setCalendarDate() {
		int i = Integer.parseInt(String.valueOf(yearcb1.getSelectedItem()));
		cal1.runDateParam(i, 1);
		cal2.runDateParam(i, 2);
		cal3.runDateParam(i, 3);
		cal4.runDateParam(i, 4);
		cal5.runDateParam(i, 5);
		cal6.runDateParam(i, 6);
		cal7.runDateParam(i, 7);
		cal8.runDateParam(i, 8);
		cal9.runDateParam(i, 9);
		cal10.runDateParam(i, 10);
		cal11.runDateParam(i, 11);
		cal12.runDateParam(i, 12);
	}

	public void empDetails() {
		nametf.setText(EmpTB.getEmpName(empcodetf.getText()));
		dojtf.setText(EmpTB.getDOJ(empcodetf.getText()));
	}

	public void showDept() {
		String str1 = coidtf.getText();
		String str2 = brcodetf.getText();
		model.setNumRows(0);
		model1.setNumRows(0);
		model2.setNumRows(0);

		try {
			PreparedStatement localPreparedStatement = con
					.prepareStatement("SELECT DEPTS from HRCOMPANY_DEPTS where company_id='" + str1 + "' and br_code='"
							+ str2 + "' and args='true' ");
			ResultSet localResultSet = localPreparedStatement.executeQuery();
			System.out.println("Dbrow : " + localResultSet.getRow());

			while (localResultSet.next()) {
				String str7 = new String(localResultSet.getString(1));
				System.out.println(str7);
				model.addRow(new Object[] { str7, new Boolean(false), new Boolean(false), new Boolean(false),
						new Boolean(false), new Boolean(false), new Boolean(false), new Boolean(false), " ", " ", " ",
						" ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " " });
			}
		} catch (Exception localException) {
			System.out.println(localException);
		}
	}

	public void showDesig() {
		model1.setNumRows(0);
		model2.setNumRows(0);
		String str1 = coidtf.getText();
		String str2 = brcodetf.getText();
		int i = tb.getSelectedRow();
		int j = tb.getSelectedColumn();
		String str3 = String.valueOf(model.getValueAt(i, j));
		String str4 = String.valueOf(model.getValueAt(i, 1));
		String str5 = String.valueOf(model.getValueAt(i, 2));
		String str6 = String.valueOf(model.getValueAt(i, 3));
		String str7 = String.valueOf(model.getValueAt(i, 4));
		String str8 = String.valueOf(model.getValueAt(i, 5));
		String str9 = String.valueOf(model.getValueAt(i, 6));
		String str10 = String.valueOf(model.getValueAt(i, 7));
		String str11 = String.valueOf(model.getValueAt(i, 8));
		String str12 = String.valueOf(model.getValueAt(i, 9));
		String str13 = String.valueOf(model.getValueAt(i, 10));
		String str14 = String.valueOf(model.getValueAt(i, 11));
		String str15 = String.valueOf(model.getValueAt(i, 12));
		String str16 = String.valueOf(model.getValueAt(i, 13));
		String str17 = String.valueOf(model.getValueAt(i, 14));
		String str18 = String.valueOf(model.getValueAt(i, 15));
		String str19 = String.valueOf(model.getValueAt(i, 16));
		String str20 = String.valueOf(model.getValueAt(i, 17));
		String str21 = String.valueOf(model.getValueAt(i, 18));
		String str22 = String.valueOf(model.getValueAt(i, 19));
		String str23 = String.valueOf(model.getValueAt(i, 20));
		String str24 = String.valueOf(model.getValueAt(i, 21));
		try {
			con = DBConnectionUtil.getConnection();
			PreparedStatement localPreparedStatement = con
					.prepareStatement("SELECT desig from HR_DEPT_DESIG where company_id='" + str1
							+ "' and branch_code='" + str2 + "' and dept='" + str3 + "' ");
			ResultSet localResultSet = localPreparedStatement.executeQuery();

			while (localResultSet.next()) {
				String str25 = localResultSet.getString(1);
				model1.addRow(new Object[] { str25, new Boolean(str4), new Boolean(str5), new Boolean(str6),
						new Boolean(str7), new Boolean(str8), new Boolean(str9), new Boolean(str10), str11, str12,
						str13, str14, str15, str16, str17, str18, str19, str20, str21, str22, str23, str24 });
			}
		} catch (Exception localException) {
			System.out.println(localException);
		}
	}

	public void showEmpList() {
		model2.setNumRows(0);
		String str1 = coidtf.getText();
		String str2 = brcodetf.getText();
		int i = tb.getSelectedRow();
		tb.getSelectedColumn();
		String str3 = String.valueOf(model.getValueAt(i, 0));
		int k = tb1.getSelectedRow();
		tb1.getSelectedColumn();
		String str4 = String.valueOf(model1.getValueAt(k, 0));
		String str5 = String.valueOf(model1.getValueAt(k, 1));
		String str6 = String.valueOf(model1.getValueAt(k, 2));
		String str7 = String.valueOf(model1.getValueAt(k, 3));
		String str8 = String.valueOf(model1.getValueAt(k, 4));
		String str9 = String.valueOf(model1.getValueAt(k, 5));
		String str10 = String.valueOf(model1.getValueAt(k, 6));
		String str11 = String.valueOf(model1.getValueAt(k, 7));
		String str12 = String.valueOf(model1.getValueAt(k, 8));
		String str13 = String.valueOf(model1.getValueAt(k, 9));
		String str14 = String.valueOf(model1.getValueAt(k, 10));
		String str15 = String.valueOf(model1.getValueAt(k, 11));
		String str16 = String.valueOf(model1.getValueAt(k, 12));
		String str17 = String.valueOf(model1.getValueAt(k, 13));
		String str18 = String.valueOf(model1.getValueAt(k, 14));
		String str19 = String.valueOf(model1.getValueAt(k, 15));
		String str20 = String.valueOf(model1.getValueAt(k, 16));
		String str21 = String.valueOf(model1.getValueAt(k, 17));
		String str22 = String.valueOf(model1.getValueAt(k, 18));
		String str23 = String.valueOf(model1.getValueAt(k, 19));
		String str24 = String.valueOf(model1.getValueAt(k, 20));
		String str25 = String.valueOf(model1.getValueAt(k, 21));
		if (str19.equalsIgnoreCase(" "))
			str19 = " ";
		if (str20.equalsIgnoreCase(" "))
			str20 = " ";
		if (str21.equalsIgnoreCase(" "))
			str21 = " ";
		if (str22.equalsIgnoreCase(" "))
			str22 = " ";
		if (str23.equalsIgnoreCase(" "))
			str23 = " ";
		if (str24.equalsIgnoreCase(" "))
			str24 = " ";
		if (str25.equalsIgnoreCase(" ")) {
			str25 = " ";
		}
		try {
			con = DBConnectionUtil.getConnection();
			PreparedStatement localPreparedStatement = con
					.prepareStatement("SELECT emp_code,permission,DOJ from HR_OT_PERMISSION where COMPANY_ID='" + str1
							+ "' and BRANCH_CODE='" + str2 + "' and DEPT ='" + str3 + "' and DESIG ='" + str4 + "' ");
			ResultSet localResultSet = localPreparedStatement.executeQuery();

			while (localResultSet.next()) {
				String str26 = localResultSet.getString(1);
				String str27 = localResultSet.getString(2);
				java.sql.Date localDate = localResultSet.getDate(3);
				String str28 = SolDateFormatter.SQLtoDDMMYY(localDate);
				model2.addRow(new Object[] { str26, new Boolean(str5), new Boolean(str6), new Boolean(str7),
						new Boolean(str8), new Boolean(str9), new Boolean(str10), new Boolean(str11), str12, str13,
						str14, str15, str16, str17, str18, str19, str20, str21, str22, str23, str24, str25,
						new Boolean(str27), str28 });
			}
		} catch (Exception localException) {
			System.out.println(localException);
		}
	}

	public void deleteOFFDAYStatus() {
		String str1 = coidtf.getText();
		String str2 = brcodetf.getText();
		int i = tb.getSelectedRow();
		String str3 = String.valueOf(model.getValueAt(i, 0));
		int j = tb1.getSelectedRow();
		String str4 = String.valueOf(model1.getValueAt(j, 0));

		try {
			con = DBConnectionUtil.getConnection();
			PreparedStatement localPreparedStatement = con.prepareStatement("delete from HR_OFF_DAYS where COMPANY_ID='"
					+ str1 + "' and BRANCH_CODE='" + str2 + "' and DEPT='" + str3 + "' and DESIG='" + str4 + "' ");
			localPreparedStatement.executeUpdate();
		} catch (Exception localException) {
			if (!localException.getMessage().toString().equalsIgnoreCase("No ResultSet was Produced")) {
				javax.swing.JOptionPane.showMessageDialog(f, localException.getMessage().toString());
			}
		}
	}

	public void saveOFFDAYStatus() {
		deleteOFFDAYStatus();
		String str1 = coidtf.getText();
		String str2 = brcodetf.getText();
		int i = tb.getSelectedRow();
		String str3 = String.valueOf(model.getValueAt(i, 0));
		int j = tb1.getSelectedRow();
		String str4 = String.valueOf(model1.getValueAt(j, 0));
		prmonitor.showMonitor();
		prmonitor.setMIN(0);
		prmonitor.setMAX(tb2.getRowCount());
		prmonitor.setProgressMessage("Saving Status...");
		prmonitor.setStatusMessage("Processing...");
		prmonitor.getProgressBar().setForeground(new java.awt.Color(0, 0, 150));
		prmonitor.runProgressMonitor();
		for (int k = 0; k < tb2.getRowCount(); k++) {
			prmonitor.getProgressBar().setValue(k);
			String str5 = String.valueOf(model2.getValueAt(k, 0));
			String.valueOf(model2.getValueAt(k, 1));
			String.valueOf(model2.getValueAt(k, 2));
			String.valueOf(model2.getValueAt(k, 3));
			String.valueOf(model2.getValueAt(k, 4));
			String.valueOf(model2.getValueAt(k, 5));
			String.valueOf(model2.getValueAt(k, 6));
			String.valueOf(model2.getValueAt(k, 7));
			String str13 = String.valueOf(model2.getValueAt(k, 8));
			String str14 = String.valueOf(model2.getValueAt(k, 9));
			String str15 = String.valueOf(model2.getValueAt(k, 10));
			String str16 = String.valueOf(model2.getValueAt(k, 11));
			String str17 = String.valueOf(model2.getValueAt(k, 12));
			String str18 = String.valueOf(model2.getValueAt(k, 13));
			String str19 = String.valueOf(model2.getValueAt(k, 14));
			String str20 = String.valueOf(model2.getValueAt(k, 15));
			String str21 = String.valueOf(model2.getValueAt(k, 16));
			String str22 = String.valueOf(model2.getValueAt(k, 17));
			String str23 = String.valueOf(model2.getValueAt(k, 18));
			String str24 = String.valueOf(model2.getValueAt(k, 19));
			String str25 = String.valueOf(model2.getValueAt(k, 20));
			String str26 = String.valueOf(model2.getValueAt(k, 21));
			String str27 = String.valueOf(model2.getValueAt(k, 22));
			if (str20.equalsIgnoreCase(" "))
				str20 = "P";
			if (str21.equalsIgnoreCase(" "))
				str21 = "P";
			if (str22.equalsIgnoreCase(" "))
				str22 = "P";
			if (str23.equalsIgnoreCase(" "))
				str23 = "P";
			if (str24.equalsIgnoreCase(" "))
				str24 = "P";
			if (str25.equalsIgnoreCase(" "))
				str25 = "P";
			if (str26.equalsIgnoreCase(" ")) {
				str26 = "P";
			}
			try {
				con = DBConnectionUtil.getConnection();
				PreparedStatement localPreparedStatement = con.prepareStatement("Insert into HR_OFF_DAYS values('"
						+ str1 + "', '" + str2 + "', '" + str3 + "', '" + str4 + "', '" + str5 + "', '" + str13 + "', '"
						+ str14 + "', '" + str15 + "', '" + str16 + "', '" + str17 + "', '" + str18 + "', '" + str19
						+ "', '" + str20 + "', '" + str21 + "', '" + str22 + "', '" + str23 + "', '" + str24 + "', '"
						+ str25 + "', '" + str26 + "','" + str27 + "')");
				localPreparedStatement.executeUpdate();
			} catch (Exception localException) {
				if (!localException.getMessage().toString().equalsIgnoreCase("No ResultSet was Produced")) {
					javax.swing.JOptionPane.showMessageDialog(f, localException.getMessage().toString());
				}
			}
		}
	}

	public void doClear(JTable paramJTable, DefaultTableModel paramDefaultTableModel) {
		for (int i = 0; i < paramJTable.getRowCount(); i++) {
			paramDefaultTableModel.setValueAt(new Boolean(false), i, 1);
			paramDefaultTableModel.setValueAt(new Boolean(false), i, 2);
			paramDefaultTableModel.setValueAt(new Boolean(false), i, 3);
			paramDefaultTableModel.setValueAt(new Boolean(false), i, 4);
			paramDefaultTableModel.setValueAt(new Boolean(false), i, 5);
			paramDefaultTableModel.setValueAt(new Boolean(false), i, 6);
			paramDefaultTableModel.setValueAt(new Boolean(false), i, 7);
			paramDefaultTableModel.setValueAt("", i, 8);
			paramDefaultTableModel.setValueAt("", i, 9);
			paramDefaultTableModel.setValueAt("", i, 10);
			paramDefaultTableModel.setValueAt("", i, 11);
			paramDefaultTableModel.setValueAt("", i, 12);
			paramDefaultTableModel.setValueAt("", i, 13);
			paramDefaultTableModel.setValueAt("", i, 14);
			paramDefaultTableModel.setValueAt(" ", i, 15);
			paramDefaultTableModel.setValueAt(" ", i, 16);
			paramDefaultTableModel.setValueAt(" ", i, 17);
			paramDefaultTableModel.setValueAt(" ", i, 18);
			paramDefaultTableModel.setValueAt(" ", i, 19);
			paramDefaultTableModel.setValueAt(" ", i, 20);
			paramDefaultTableModel.setValueAt(" ", i, 21);
		}
	}

	public void setValueTo(String paramString, JTable paramJTable, DefaultTableModel paramDefaultTableModel,
			int paramInt1, int paramInt2) {
		paramDefaultTableModel.setValueAt(paramString, paramInt1, paramInt2);
	}

	public void showOFFDAYStatusTick(JTable paramJTable, DefaultTableModel paramDefaultTableModel, String paramString1,
			String paramString2, int paramInt1, int paramInt2) {
		String str1 = coidtf.getText();
		String str2 = brcodetf.getText();
		try {
			con = DBConnectionUtil.getConnection();
			PreparedStatement localPreparedStatement = con
					.prepareStatement("select sun, mon, tue, wed, thu, fri, sat from HR_OFF_DAYS where company_id='"
							+ str1 + "' and branch_code='" + str2 + "' and " + paramString1 + " ='" + paramString2
							+ "' group by sun,mon,tue,wed,thu,fri,sat ");
			ResultSet localResultSet = localPreparedStatement.executeQuery();
			while (localResultSet.next()) {
				String str3 = new String(localResultSet.getString(1));
				String str4 = new String(localResultSet.getString(2));
				String str5 = new String(localResultSet.getString(3));
				String str6 = new String(localResultSet.getString(4));
				String str7 = new String(localResultSet.getString(5));
				String str8 = new String(localResultSet.getString(6));
				String str9 = new String(localResultSet.getString(7));
				if (!str3.equalsIgnoreCase(" "))
					paramDefaultTableModel.setValueAt(new Boolean(true), paramInt1, paramInt2 + 0);
				if (!str4.equalsIgnoreCase(" "))
					paramDefaultTableModel.setValueAt(new Boolean(true), paramInt1, paramInt2 + 1);
				if (!str5.equalsIgnoreCase(" "))
					paramDefaultTableModel.setValueAt(new Boolean(true), paramInt1, paramInt2 + 2);
				if (!str6.equalsIgnoreCase(" "))
					paramDefaultTableModel.setValueAt(new Boolean(true), paramInt1, paramInt2 + 3);
				if (!str7.equalsIgnoreCase(" "))
					paramDefaultTableModel.setValueAt(new Boolean(true), paramInt1, paramInt2 + 4);
				if (!str8.equalsIgnoreCase(" "))
					paramDefaultTableModel.setValueAt(new Boolean(true), paramInt1, paramInt2 + 5);
				if (!str9.equalsIgnoreCase(" ")) {
					paramDefaultTableModel.setValueAt(new Boolean(true), paramInt1, paramInt2 + 6);
				}
			}
		} catch (Exception localException) {
			javax.swing.JOptionPane.showMessageDialog(f, localException.getMessage().toString());
		}
	}

	public Hashtable<String, String> showOFFDAYStatusValue(JTable paramJTable, DefaultTableModel paramDefaultTableModel,
			String paramString1, String paramString2, int paramInt1, int paramInt2) {
		ht.clear();
		String str1 = coidtf.getText();
		String str2 = brcodetf.getText();
		try {
			con = DBConnectionUtil.getConnection();
			PreparedStatement localPreparedStatement = con.prepareStatement(
					"select sun, mon, tue, wed, thu, fri, sat,sun_ot, mon_ot, tue_ot, wed_ot, thu_ot, fri_ot, sat_ot  from HR_OFF_DAYS where company_id='"
							+ str1 + "' and branch_code='" + str2 + "' and " + paramString1 + " ='" + paramString2
							+ "' group by sun,mon,tue,wed,thu,fri,sat,sun_ot, mon_ot, tue_ot, wed_ot, thu_ot, fri_ot, sat_ot ");
			ResultSet localResultSet = localPreparedStatement.executeQuery();

			while (localResultSet.next()) {
				String str3 = new String(localResultSet.getString(1));
				String str4 = new String(localResultSet.getString(2));
				String str5 = new String(localResultSet.getString(3));
				String str6 = new String(localResultSet.getString(4));
				String str7 = new String(localResultSet.getString(5));
				String str8 = new String(localResultSet.getString(6));
				String str9 = new String(localResultSet.getString(7));
				String str10 = new String(localResultSet.getString(8));
				String str11 = new String(localResultSet.getString(9));
				String str12 = new String(localResultSet.getString(10));
				String str13 = new String(localResultSet.getString(11));
				String str14 = new String(localResultSet.getString(12));
				String str15 = new String(localResultSet.getString(13));
				String str16 = new String(localResultSet.getString(14));
				if (!str3.equalsIgnoreCase(" ")) {
					setValueTo(str3, paramJTable, paramDefaultTableModel, paramInt1, paramInt2 + 0);
					setValueTo(str10, paramJTable, paramDefaultTableModel, paramInt1, paramInt2 + 7);
					ht.put("Sun", str3 + "," + str10);
				}
				if (!str4.equalsIgnoreCase(" ")) {
					setValueTo(str4, paramJTable, paramDefaultTableModel, paramInt1, paramInt2 + 1);
					setValueTo(str11, paramJTable, paramDefaultTableModel, paramInt1, paramInt2 + 8);
					ht.put("Mon", str4 + "," + str11);
				}
				if (!str5.equalsIgnoreCase(" ")) {
					setValueTo(str5, paramJTable, paramDefaultTableModel, paramInt1, paramInt2 + 2);
					setValueTo(str12, paramJTable, paramDefaultTableModel, paramInt1, paramInt2 + 9);
					ht.put("Tue", str5 + "," + str12);
				}
				if (!str6.equalsIgnoreCase(" ")) {
					setValueTo(str6, paramJTable, paramDefaultTableModel, paramInt1, paramInt2 + 3);
					setValueTo(str13, paramJTable, paramDefaultTableModel, paramInt1, paramInt2 + 10);
					ht.put("Wed", str6 + "," + str13);
				}
				if (!str7.equalsIgnoreCase(" ")) {
					setValueTo(str7, paramJTable, paramDefaultTableModel, paramInt1, paramInt2 + 4);
					setValueTo(str14, paramJTable, paramDefaultTableModel, paramInt1, paramInt2 + 11);
					ht.put("Thu", str7 + "," + str14);
				}
				if (!str8.equalsIgnoreCase(" ")) {
					setValueTo(str8, paramJTable, paramDefaultTableModel, paramInt1, paramInt2 + 5);
					setValueTo(str15, paramJTable, paramDefaultTableModel, paramInt1, paramInt2 + 12);
					ht.put("Fri", str8 + "," + str15);
				}
				if (!str9.equalsIgnoreCase(" ")) {
					setValueTo(str9, paramJTable, paramDefaultTableModel, paramInt1, paramInt2 + 6);
					setValueTo(str16, paramJTable, paramDefaultTableModel, paramInt1, paramInt2 + 13);
					ht.put("Sat", str9 + "," + str16);
				}

			}
		} catch (Exception localException) {
			javax.swing.JOptionPane.showMessageDialog(f, localException.getMessage().toString());
		}
		return ht;
	}

	public void currentStatus(JTable paramJTable, DefaultTableModel paramDefaultTableModel, String paramString) {
		int i = paramJTable.getSelectedRow();
		String str = String.valueOf(paramDefaultTableModel.getValueAt(i, 0));
		showOFFDAYStatusTick(paramJTable, paramDefaultTableModel, paramString, str, i, 1);
		showOFFDAYStatusValue(paramJTable, paramDefaultTableModel, paramString, str, i, 8);
	}

	public void allCurrentStatus(JTable paramJTable, DefaultTableModel paramDefaultTableModel, String paramString) {
		for (int i = 0; i < paramJTable.getRowCount(); i++) {
			String str = String.valueOf(paramDefaultTableModel.getValueAt(i, 0));
			showOFFDAYStatusTick(paramJTable, paramDefaultTableModel, paramString, str, i, 1);
			showOFFDAYStatusValue(paramJTable, paramDefaultTableModel, paramString, str, i, 8);
		}
	}

	public ArrayList UrgentWork() {
		int i = tb.getSelectedRow();
		int j = tb1.getSelectedRow();

		ArrayList localArrayList = new ArrayList();
		String str1 = coidtf.getText();
		String str2 = brcodetf.getText();

		String str3 = String.valueOf(model.getValueAt(i, 0));
		String str4 = String.valueOf(model1.getValueAt(j, 0));

		try {
			con = DBConnectionUtil.getConnection();
			PreparedStatement localPreparedStatement = con.prepareStatement(
					"select date from HR_OT_URGENT where company_id = '" + str1 + "' AND branch_code='" + str2
							+ "' AND DEPT='" + str3 + "' and Desig='" + str4 + "' and work_args='true' ");
			ResultSet localResultSet = localPreparedStatement.executeQuery();
			while (localResultSet.next()) {
				java.sql.Date localDate = localResultSet.getDate(1);
				String str5 = SolDateFormatter.SQLtoDDMMYY(localDate);

				System.out.println("HR_OT_URGENT : " + str5);
				localArrayList.add(str5);
			}

		} catch (Exception localException) {
			javax.swing.JOptionPane.showMessageDialog(f, localException.getMessage().toString());
		}

		return localArrayList;
	}

	public ArrayList Holidays() {
		int i = tb.getSelectedRow();
		int j = tb1.getSelectedRow();

		ArrayList localArrayList = new ArrayList();
		String str1 = coidtf.getText();
		String str2 = brcodetf.getText();

		String.valueOf(model.getValueAt(i, 0));
		String.valueOf(model1.getValueAt(j, 0));

		try {
			PreparedStatement localPreparedStatement = con
					.prepareStatement("select date from HR_HOLIDAY_LIST where company_id = '" + str1
							+ "' AND branch_code='" + str2 + "'  ");
			ResultSet localResultSet = localPreparedStatement.executeQuery();
			while (localResultSet.next()) {
				java.sql.Date localDate = localResultSet.getDate(1);
				String str5 = SolDateFormatter.SQLtoDDMMYY(localDate);

				localArrayList.add(str5);
			}

		} catch (Exception localException) {
			javax.swing.JOptionPane.showMessageDialog(f, localException.getMessage().toString());
		}

		return localArrayList;
	}

	public void DrawCalendarForUrgent(JTable paramJTable, DefaultTableModel paramDefaultTableModel, int paramInt) {
		ArrayList localArrayList = new ArrayList();

		String str1 = "red";
		int i = 0;
		if (setoffdaycheckbox1.isSelected()) {
			localArrayList = UrgentWork();
			System.out.println("Ugent Leave List size : " + localArrayList.size());
			str1 = "yellow";
			i = localArrayList.size();
		}
		if (setworkdaycheckbox.isSelected()) {
			localArrayList = Holidays();
			str1 = "lime";
			i = localArrayList.size();
		}

		for (int j = 0; j < i; j++) {
			String str2 = "00";

			if (setoffdaycheckbox1.isSelected()) {
				str2 = String.valueOf(localArrayList.get(j));
			}
			if (setworkdaycheckbox.isSelected()) {
				str2 = String.valueOf(localArrayList.get(j));
			}

			for (int k = 0; k < 5; k++) {
				for (int m = 0; m < 7; m++) {
					String str3 = String.valueOf(paramDefaultTableModel.getValueAt(k, m));
					if ((!str3.equalsIgnoreCase("null")) && (str3.length() <= 2)) {

						String str4 = String.valueOf(df.format(Integer.parseInt(str3)));
						String str5 = String.valueOf(
								str4 + "/" + df.format(paramInt) + "/" + String.valueOf(yearcb1.getSelectedItem()));
						if (str2.equalsIgnoreCase(str5)) {

							String str6 = String.valueOf(paramDefaultTableModel.getValueAt(k, m));
							if ((str6 == null) || (str6.equalsIgnoreCase("null"))) {
								str6 = "";
							}
							paramDefaultTableModel.setValueAt("<html><Font Color=" + str1 + ">" + str6, k, m);

							System.out.println("Matching Date : " + str2);
						}
					}
				}
			}
		}
	}

	public void updateOTStatus() {
		int i = tb2.getSelectedRow();
		coidtf.getText();
		brcodetf.getText();
		String str3 = String.valueOf(model2.getValueAt(i, 0));
		String str4 = String.valueOf(model2.getValueAt(i, 22));
		try {
			con = DBConnectionUtil.getConnection();
			PreparedStatement localPreparedStatement1 = con.prepareStatement(
					"update HR_OT_PERMISSION set PERMISSION='" + str4 + "' where EMP_CODE='" + str3 + "' ");
			localPreparedStatement1.executeUpdate();
		} catch (Exception localException1) {
			System.out.println("HR_OT_PERMISSION : " + localException1);

			if (localException1.getMessage().toString().equalsIgnoreCase("No ResultSet was Produced")) {
				try {
					PreparedStatement localPreparedStatement2 = con.prepareStatement(
							"update HR_OFF_DAYS set PERMISSION='" + str4 + "' where EMP_CODE='" + str3 + "' ");
					localPreparedStatement2.executeUpdate();
				} catch (Exception localException2) {
					if (localException1.getMessage().toString().equalsIgnoreCase("No ResultSet was Produced")) {
						System.out.println("HR_OFF_DAYS : " + localException2);
						javax.swing.JOptionPane.showMessageDialog(f, "Permission Updated.");
					}
				}
			}
		}
	}

	public void selectedDates() {
		dateArrayList.clear();
		String str;
		for (int i = 0; i < cal1.getDates2(String.valueOf(yearcb1.getSelectedItem()), 1).size(); i++) {
			str = String.valueOf(cal1.getDates2(String.valueOf(yearcb1.getSelectedItem()), 1).get(i));
			if (str.length() >= 1) {
				dateArrayList.add(str);
			}
		}
		for (int i = 0; i < cal2.getDates2(String.valueOf(yearcb1.getSelectedItem()), 2).size(); i++) {
			str = String.valueOf(cal2.getDates2(String.valueOf(yearcb1.getSelectedItem()), 2).get(i));
			if (str.length() >= 1) {
				dateArrayList.add(str);
			}
		}
		for (int i = 0; i < cal3.getDates2(String.valueOf(yearcb1.getSelectedItem()), 3).size(); i++) {
			str = String.valueOf(cal3.getDates2(String.valueOf(yearcb1.getSelectedItem()), 3).get(i));
			if (str.length() >= 1) {
				dateArrayList.add(str);
			}
		}
		for (int i = 0; i < cal4.getDates2(String.valueOf(yearcb1.getSelectedItem()), 4).size(); i++) {
			str = String.valueOf(cal4.getDates2(String.valueOf(yearcb1.getSelectedItem()), 4).get(i));
			if (str.length() >= 1) {
				dateArrayList.add(str);
			}
		}
		for (int i = 0; i < cal5.getDates2(String.valueOf(yearcb1.getSelectedItem()), 5).size(); i++) {
			str = String.valueOf(cal5.getDates2(String.valueOf(yearcb1.getSelectedItem()), 5).get(i));
			if (str.length() >= 1) {
				dateArrayList.add(str);
			}
		}
		for (int i = 0; i < cal6.getDates2(String.valueOf(yearcb1.getSelectedItem()), 6).size(); i++) {
			str = String.valueOf(cal6.getDates2(String.valueOf(yearcb1.getSelectedItem()), 6).get(i));
			if (str.length() >= 1) {
				dateArrayList.add(str);
			}
		}
		for (int i = 0; i < cal7.getDates2(String.valueOf(yearcb1.getSelectedItem()), 7).size(); i++) {
			str = String.valueOf(cal7.getDates2(String.valueOf(yearcb1.getSelectedItem()), 7).get(i));
			if (str.length() >= 1) {
				dateArrayList.add(str);
			}
		}
		for (int i = 0; i < cal8.getDates2(String.valueOf(yearcb1.getSelectedItem()), 8).size(); i++) {
			str = String.valueOf(cal8.getDates2(String.valueOf(yearcb1.getSelectedItem()), 8).get(i));
			if (str.length() >= 1) {
				dateArrayList.add(str);
			}
		}
		for (int i = 0; i < cal9.getDates2(String.valueOf(yearcb1.getSelectedItem()), 9).size(); i++) {
			str = String.valueOf(cal9.getDates2(String.valueOf(yearcb1.getSelectedItem()), 9).get(i));
			if (str.length() >= 1) {
				dateArrayList.add(str);
			}
		}
		for (int i = 0; i < cal10.getDates2(String.valueOf(yearcb1.getSelectedItem()), 10).size(); i++) {
			str = String.valueOf(cal10.getDates2(String.valueOf(yearcb1.getSelectedItem()), 10).get(i));
			if (str.length() >= 1) {
				dateArrayList.add(str);
			}
		}
		for (int i = 0; i < cal11.getDates2(String.valueOf(yearcb1.getSelectedItem()), 11).size(); i++) {
			str = String.valueOf(cal11.getDates2(String.valueOf(yearcb1.getSelectedItem()), 11).get(i));
			if (str.length() >= 1) {
				dateArrayList.add(str);
			}
		}
		for (int i = 0; i < cal12.getDates2(String.valueOf(yearcb1.getSelectedItem()), 12).size(); i++) {
			str = String.valueOf(cal12.getDates2(String.valueOf(yearcb1.getSelectedItem()), 12).get(i));
			if (str.length() >= 1) {
				dateArrayList.add(str);
			}
		}
	}

	public void cancelOT() {
		String str1 = coidtf.getText();
		String str2 = brcodetf.getText();
		int i = tb.getSelectedRow();
		tb.getSelectedColumn();
		String str3 = String.valueOf(model.getValueAt(i, 0));
		int k = tb1.getSelectedRow();
		tb1.getSelectedColumn();
		String str4 = String.valueOf(model1.getValueAt(k, 0));

		System.out.println("Canceling OT : ");
		try {
			con = DBConnectionUtil.getConnection();
			PreparedStatement localPreparedStatement = con
					.prepareStatement("delete from HR_OT_URGENT where COMPANY_ID='" + str1 + "' and BRANCH_CODE='"
							+ str2 + "' and DEPT='" + str3 + "' and DESIG='" + str4 + "' and DATE >= '"
							+ yearcb1.getSelectedItem() + "-01-01" + "' ");
			localPreparedStatement.executeUpdate();
		} catch (Exception localException) {
			if (localException.getMessage().toString().equalsIgnoreCase("No ResultSet was Produced")) {
				System.out.println("Record Deleted.");
			}
		}
	}

	public void setOFFDays() {
		cancelOT();
		selectedDates();
		int i = tb.getSelectedRow();
		tb.getSelectedColumn();

		int k = tb1.getSelectedRow();
		tb1.getSelectedColumn();

		String str1 = coidtf.getText();
		String str2 = brcodetf.getText();
		String str3 = String.valueOf(model.getValueAt(i, 0));
		String str4 = String.valueOf(model1.getValueAt(k, 0));
		String str5 = otvaluetf.getText();
		String str6 = String.valueOf(ottypecb.getSelectedItem());
		String str7 = "Saturday,Sunday";

		for (int n = 0; n < dateArrayList.size(); n++) {
			String str8 = String.valueOf(dateArrayList.get(n));

			int i1 = DateCalculationUtil.getMonth(str8);
			int i2 = DateCalculationUtil.getDate(str8);
			String str9 = (String) yearcb1.getSelectedItem();

			String str10 = String.valueOf(df.format(i2)) + "/" + String.valueOf(df.format(i1)) + "/"
					+ String.valueOf(str9);
			java.sql.Date localDate = SolDateFormatter.DDMMYYtoSQL(str10);

			System.out.println("Date :" + localDate);

			String str11 = null;
			str11 = "Insert into HR_OT_URGENT values('" + str1 + "', '" + str2 + "', '" + str3 + "', '" + str4
					+ "', ? ,'" + str6 + "','" + str5 + "','" + str7 + "','false')";
			try {
				PreparedStatement localPreparedStatement = con.prepareStatement(str11);
				localPreparedStatement.setDate(1, localDate);
				localPreparedStatement.executeUpdate();
			} catch (Exception localException) {
				if (!localException.getMessage().toString().equalsIgnoreCase("No ResultSet was Produced")) {
					javax.swing.JOptionPane.showMessageDialog(f, localException.getMessage().toString());
				} else {
					System.out.println("HR_OT_URGENT : " + localException);
				}
			}
		}
		javax.swing.JOptionPane.showMessageDialog(f, "OFF Days Updated.");
	}

	public void setOT() {
		selectedDates();
		int i = tb.getSelectedRow();
		tb.getSelectedColumn();

		int k = tb1.getSelectedRow();
		tb1.getSelectedColumn();

		String str1 = coidtf.getText();
		String str2 = brcodetf.getText();
		String str3 = String.valueOf(model.getValueAt(i, 0));
		String str4 = String.valueOf(model1.getValueAt(k, 0));
		String str5 = otvaluetf.getText();
		String str6 = String.valueOf(ottypecb.getSelectedItem());
		String str7 = commentbox.getComment();

		System.out.println("Date List Size : " + dateArrayList.size());

		for (int n = 0; n < dateArrayList.size(); n++) {
			String str8 = String.valueOf(dateArrayList.get(n));
			int i1 = DateCalculationUtil.getMonth(str8);
			int i2 = DateCalculationUtil.getDate(str8);
			String str9 = (String) yearcb1.getSelectedItem();

			String str10 = String.valueOf(df.format(i2)) + "/" + String.valueOf(df.format(i1)) + "/"
					+ String.valueOf(str9);
			java.sql.Date localDate = SolDateFormatter.DDMMYYtoSQL(str10);

			String str11 = null;
			System.out.println("Selected Date : " + localDate);

			str11 = "Insert into HR_OT_URGENT values('" + str1 + "', '" + str2 + "', '" + str3 + "', '" + str4
					+ "', ? ,'" + str6 + "','" + str5 + "','" + str7 + "','true')";
			try {
				PreparedStatement localPreparedStatement = con.prepareStatement(str11);
				localPreparedStatement.setDate(1, localDate);
				localPreparedStatement.executeUpdate();
			} catch (Exception localException) {
				if (!localException.getMessage().toString().equalsIgnoreCase("No ResultSet was Produced")) {
					javax.swing.JOptionPane.showMessageDialog(f, localException.getMessage().toString());
				} else {
					System.out.println("HR_OT_URGENT : " + localException);
				}
			}
		}
		javax.swing.JOptionPane.showMessageDialog(f, "Record Updated.");
	}

	public void actionPerformed(java.awt.event.ActionEvent paramActionEvent) {
		if (paramActionEvent.getSource() == b1) {
			if ((tabbedpane.getSelectedIndex() == 0) || (tabbedpane2.getSelectedIndex() == 1))
				saveOFFDAYStatus();
			if (tabbedpane2.getSelectedIndex() == 2)
				saveOFFDAYStatus();
		}
		if (paramActionEvent.getSource() == ch1) {
			if (tabbedpane.getSelectedIndex() == 0)
				currentStatus(tb, model, "dept");
			if (tabbedpane2.getSelectedIndex() == 1) {
				doClear(tb1, model1);
				currentStatus(tb1, model1, "Desig");
			}
			if (tabbedpane2.getSelectedIndex() == 2)
				currentStatus(tb2, model2, "Emp_Code");
		}
		if ((paramActionEvent.getSource() == coidtf) 
				|| (paramActionEvent.getSource() == brcodetf) 
				|| (paramActionEvent.getSource() == cb1)) {
			showDept();
		}
		if ((paramActionEvent.getSource() == empcodetf) 
				|| (((paramActionEvent.getSource() != b2) 
						|| ((tabbedpane.getSelectedIndex() != 0) && (tabbedpane.getSelectedIndex() == 1)))
						|| (paramActionEvent.getSource() == b3))){
			//frameTech.setVisible(false);
		}
		if(paramActionEvent.getSource() == b3){
			f.setVisible(false);
		}
		if (paramActionEvent.getSource() == commentbut){
			commentbox.showCommentBox();
		}
		if (paramActionEvent.getSource() == setoffdaycheckbox1) {
			setCalendarDate();
			otvaluetf.setText(" ");
			otvaluetf.setEditable(true);
			ottypecb.setEnabled(true);
			commentbut.setEnabled(true);
			saveotbut.setEnabled(true);

			DrawCalendarForUrgent(cal1.CalTable(), cal1.CalTableModel(), 1);
			DrawCalendarForUrgent(cal2.CalTable(), cal2.CalTableModel(), 2);
			DrawCalendarForUrgent(cal3.CalTable(), cal3.CalTableModel(), 3);
			DrawCalendarForUrgent(cal4.CalTable(), cal4.CalTableModel(), 4);
			DrawCalendarForUrgent(cal5.CalTable(), cal5.CalTableModel(), 5);
			DrawCalendarForUrgent(cal6.CalTable(), cal6.CalTableModel(), 6);
			DrawCalendarForUrgent(cal7.CalTable(), cal7.CalTableModel(), 7);
			DrawCalendarForUrgent(cal8.CalTable(), cal8.CalTableModel(), 8);
			DrawCalendarForUrgent(cal9.CalTable(), cal9.CalTableModel(), 9);
			DrawCalendarForUrgent(cal10.CalTable(), cal10.CalTableModel(), 10);
			DrawCalendarForUrgent(cal11.CalTable(), cal11.CalTableModel(), 11);
			DrawCalendarForUrgent(cal12.CalTable(), cal12.CalTableModel(), 12);
		}

		if (paramActionEvent.getSource() == setworkdaycheckbox) {
			setCalendarDate();
			otvaluetf.setText("1");
			otvaluetf.setText(" ");
			otvaluetf.setEditable(false);
			ottypecb.setEnabled(false);
			commentbut.setEnabled(false);
			saveotbut.setEnabled(false);

			DrawCalendarForUrgent(cal1.CalTable(), cal1.CalTableModel(), 1);
			DrawCalendarForUrgent(cal2.CalTable(), cal2.CalTableModel(), 2);
			DrawCalendarForUrgent(cal3.CalTable(), cal3.CalTableModel(), 3);
			DrawCalendarForUrgent(cal4.CalTable(), cal4.CalTableModel(), 4);
			DrawCalendarForUrgent(cal5.CalTable(), cal5.CalTableModel(), 5);
			DrawCalendarForUrgent(cal6.CalTable(), cal6.CalTableModel(), 6);
			DrawCalendarForUrgent(cal7.CalTable(), cal7.CalTableModel(), 7);
			DrawCalendarForUrgent(cal8.CalTable(), cal8.CalTableModel(), 8);
			DrawCalendarForUrgent(cal9.CalTable(), cal9.CalTableModel(), 9);
			DrawCalendarForUrgent(cal10.CalTable(), cal10.CalTableModel(), 10);
			DrawCalendarForUrgent(cal11.CalTable(), cal11.CalTableModel(), 11);
			DrawCalendarForUrgent(cal12.CalTable(), cal12.CalTableModel(), 12);
		}

		if (paramActionEvent.getSource() == setOFFDayButton) {
			cancelOT();
			setOFFDays();
		}
		if (paramActionEvent.getSource() == saveotbut) {
			cancelOT();
			setOT();
		}
		if (paramActionEvent.getSource() == cleanButton) {
			cancelOT();
		}
	}

	public void mousePressed(java.awt.event.MouseEvent paramMouseEvent) {
		if (paramMouseEvent.getSource() == tabbedpane2) {
			if (tabbedpane2.getSelectedIndex() == 0)
				b1.setEnabled(false);
			if (tabbedpane2.getSelectedIndex() == 1)
				b1.setEnabled(true);
			if (tabbedpane2.getSelectedIndex() == 2)
				b1.setEnabled(true);
		}
	}

	public void mouseReleased(java.awt.event.MouseEvent paramMouseEvent) {
	}

	public void mouseClicked(java.awt.event.MouseEvent paramMouseEvent) {
		int i;
		int j;
		String str1;
		String str4;
		String str5;
		if (paramMouseEvent.getSource() == tb) {
			if (ch1.isSelected()) {
				currentStatus(tb, model, "dept");
				showDesig();
				doClear(tb1, model1);
			} else {
				i = tb.getSelectedRow();
				j = tb.getSelectedColumn();
				str1 = String.valueOf(model.getValueAt(i, j));
				if ((j >= 1) && (j <= 8)) {
					if (str1.equalsIgnoreCase("true")) {
						str4 = javax.swing.JOptionPane.showInputDialog(f, "Enter OT Value");
						model.setValueAt(str4.toUpperCase(), i, j + 7);
						str5 = javax.swing.JOptionPane.showInputDialog(f, "Enter TO TYPE");
						model.setValueAt(str5.toUpperCase(), i, j + 14);
					}
					if (str1.equalsIgnoreCase("false")) {
						model.setValueAt(" ", i, j + 7);
						model.setValueAt(" ", i, j + 14);
					}
				}
				showDesig();
			}
		}

		if (paramMouseEvent.getSource() == tb1) {
			if (ch1.isSelected()) {
				currentStatus(tb1, model1, "Desig");
				showEmpList();
				allCurrentStatus(tb2, model2, "Emp_Code");
			} else {
				showEmpList();
				i = tb1.getSelectedRow();
				j = tb1.getSelectedColumn();
				str1 = String.valueOf(model1.getValueAt(i, j));
				if ((j >= 1) && (j <= 8)) {
					if (str1.equalsIgnoreCase("true")) {
						str4 = javax.swing.JOptionPane.showInputDialog(f, "Enter OT Value");
						model1.setValueAt(str4.toUpperCase(), i, j + 7);
						str5 = javax.swing.JOptionPane.showInputDialog(f, "Enter TO TYPE");
						model1.setValueAt(str5.toUpperCase(), i, j + 14);
						showEmpList();
					}
					if (str1.equalsIgnoreCase("false")) {
						model1.setValueAt(" ", i, j + 7);
						model1.setValueAt(" ", i, j + 14);
					}
				}
			}
		}
		if (paramMouseEvent.getSource() == tb2) {

			i = tb2.getSelectedRow();
			j = tb2.getSelectedColumn();
			str1 = String.valueOf(model2.getValueAt(i, j));
			if ((j >= 1) && (j <= 8)) {
				if (str1.equalsIgnoreCase("true")) {
					str4 = javax.swing.JOptionPane.showInputDialog(f, "Enter OT Value");
					model2.setValueAt(str4.toUpperCase(), i, j + 7);
					str5 = javax.swing.JOptionPane.showInputDialog(f, "Enter TO TYPE");
					model2.setValueAt(str5.toUpperCase(), i, j + 14);
				}
				if (str1.equalsIgnoreCase("false")) {
					model2.setValueAt(" ", i, j + 7);
					model2.setValueAt(" ", i, j + 14);
				}
			}
			if (j == 22) {
				updateOTStatus();
			}
		}
	}


	public void mouseEntered(java.awt.event.MouseEvent paramMouseEvent) {
	}

	public void mouseExited(java.awt.event.MouseEvent paramMouseEvent) {
	}

	public void itemStateChanged(java.awt.event.ItemEvent paramItemEvent) {
		if (paramItemEvent.getSource() == yearcb1) {
			setCalendarDate();
		}
	}

	SolProgressMonitor prmonitor;

	SolCommentBox commentbox;

	SolCalendar2 cal1;

	SolCalendar2 cal2;

	SolCalendar2 cal3;

	SolCalendar2 cal4;
	SolCalendar2 cal5;
	SolCalendar2 cal6;
	SolCalendar2 cal7;
	SolCalendar2 cal8;
	SolCalendar2 cal9;
	SolCalendar2 cal10;
	SolCalendar2 cal11;
	SolCalendar2 cal12;
	JComboBox yearcb1;
	JLabel yearlabel;
	JPanel calendarPanel;
	javax.swing.JScrollPane calendarSP;
	String[] columnNames = { "Department", "S", "M", "T", "W", "T", "F", "S", "", "", "", "", "", "", "", "", "", "",
			"", "", "", "" };

	Object[][] data;

	String[] columnNames1 = { "Designation", "S", "M", "T", "W", "T", "F", "S", "", "", "", "", "", "", "", "", "", "",
			"", "", "", "" };

	Object[][] data1;

	String[] columnNames2 = { "EMP CODE", "S", "M", "T", "W", "T", "F", "S", "", "", "", "", "", "", "", "", "", "", "",
			"", "", "", "A", "DOJ" };
	Object[][] data2;
	DefaultTableModel model;
	JTable tb;
	javax.swing.JScrollPane sp;
	DefaultTableModel model1;
	JTable tb1;
	javax.swing.JScrollPane sp1;
	DefaultTableModel model2;
	JTable tb2;
	javax.swing.JScrollPane sp2;
	javax.swing.JFrame f;
	JTabbedPane tabbedpane;
	JTabbedPane tabbedpane2;
	JPanel depttab;
	JPanel desigtab;
	JPanel emptab;
	JPanel offtab;
	JPanel holidaytab;
	JPanel calendartab;
	JLabel l1;
	JLabel l2;
	JLabel l3;
	JLabel l4;
	JLabel l5;
	JLabel l6;
	JLabel l7;
	JTextField tf1;
	JTextField tf2;
	JTextField tf3;
	JTextField tf4;
	JTextField tf5;
	JTextField tf6;
	JTextField tf7;
	JLabel coidlabel;
	JLabel brcodelabel;
	JTextField coidtf;
	JTextField brcodetf;
	JLabel shiftlabel;
	JComboBox cb1;
	JLabel empcodelabel;
	JLabel empnamelabel;
	JLabel dojlabel;
	JLabel otvaluelabel;
	JLabel ottypelabel;
	JTextField empcodetf;
	JTextField nametf;
	JTextField dojtf;
	JTextField otvaluetf;
	JComboBox ottypecb;
	javax.swing.JCheckBox ch1;
	javax.swing.JCheckBox ch2;
	javax.swing.ButtonGroup checkboxgroup;
	JButton showbut;
	JButton b1;
	JButton b2;
	JButton b3;
	JButton commentbut;
	JButton saveotbut;
	JButton setOFFDayButton;
	JButton setOTButton;
	JButton cleanButton;
	javax.swing.JRadioButton setoffdaycheckbox1;
	javax.swing.JRadioButton setoffdaycheckbox2;
	javax.swing.JRadioButton setworkdaycheckbox;
	javax.swing.ButtonGroup checkboxgroup1;
	JPanel centerButtonPanel;
	JPanel p1;
	JPanel p2;
	JPanel p3;
	JPanel CALVIEWPANEL;
	JPanel CALEASTPANEL;
	JPanel southPanel;
	JPanel mainPanel;
	JPanel calnorthp1;
	JPanel calnorthp2;
	JPanel northPanel;
	JPanel northp1;
	JPanel northp2;
	JPanel southp1;
	JPanel southp2;
	JPanel checkotpanel;
	JPanel defineotpanel;
	JPanel packotpanel;
	java.awt.Font fo;
	OFFDays.ColoredTableCellRenderer skr;
	Hashtable<String, String> ht;
	ArrayList dateArrayList;
}
