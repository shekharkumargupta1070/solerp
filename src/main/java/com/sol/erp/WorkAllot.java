package com.sol.erp;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.sol.erp.constants.ProjectConstants;
import com.sol.erp.dao.ProjectDAO;
import com.sol.erp.ui.custom.SolTableModel;
import com.sol.erp.util.DBConnectionUtil;
import com.sol.erp.util.UIUtil;
import com.sol.erp.util.formater.SolDateFormatter;

public class WorkAllot implements java.awt.event.ActionListener, java.awt.event.MouseListener,
		java.awt.event.KeyListener, java.awt.event.ItemListener {

	String skquery = "sk";

	int startdate = 0;
	int enddate = 0;
	int daterange = 0;
	float employeeNeededTime = 0.0F;
	float drawingNeededTime = 0.0F;
	String firstdrg;
	String lastdrg;
	String firstdate;
	String lastdate;
	String[] columnNames = { "Day", "Date", "Project No", "ITEM Code", "DRG No" };
	Object[][] data = new Object[0][];

	String[] itemColumn = { "Item_Code", "Item_Name", "Status" };
	Object[][] itemData = new Object[0][];

	String[] drawColumn = { "DRG No", "DTL Time", "CHK Time", "T Time", "Status", "Detailer", "Checker", "Status",
			"Item Code" };
	Object[][] drawData = new Object[0][];

	String[] empColumn = { "Code", "Desig", "Name" };
	Object[][] empData = new Object[0][];

	DecimalFormat df = new DecimalFormat("00");
	DecimalFormat df1 = new DecimalFormat(".00");

	int v = 20;
	int h = 32;

	DefaultTableModel model = new DefaultTableModel(data, columnNames);
	JTable tb = new JTable(model) {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public boolean isCellEditable(int paramAnonymousInt1, int paramAnonymousInt2) {
			int i = convertColumnIndexToModel(paramAnonymousInt2);
			return i < 0;
		}
	};
	JScrollPane sp = new JScrollPane(tb, v, h);

	DefaultTableModel itemmodel = new DefaultTableModel(itemData, itemColumn);
	JTable itemtb = new JTable(itemmodel) {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public boolean isCellEditable(int paramAnonymousInt1, int paramAnonymousInt2) {
			int i = convertColumnIndexToModel(paramAnonymousInt2);
			return i < 0;
		}
	};
	JScrollPane itemsp = new JScrollPane(itemtb);

	DefaultTableModel drawmodel = new DefaultTableModel(drawData, drawColumn);
	JTable drawtb = new JTable(drawmodel) {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public boolean isCellEditable(int paramAnonymousInt1, int paramAnonymousInt2) {
			int i = convertColumnIndexToModel(paramAnonymousInt2);
			return i < 0;
		}
	};
	JScrollPane drawsp = new JScrollPane(drawtb);

	DefaultTableModel empmodel = new DefaultTableModel(empData, empColumn);
	JTable emptb = new JTable(empmodel) {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public boolean isCellEditable(int paramAnonymousInt1, int paramAnonymousInt2) {
			int i = convertColumnIndexToModel(paramAnonymousInt2);
			return i < 0;
		}
	};
	JScrollPane empsp = new JScrollPane(emptb);

	JProgressBar progressBar = new JProgressBar(0, 100);

	JInternalFrame internalFrame = new JInternalFrame("Work Allotment", true, true, true, true);

	JLabel topimage = new JLabel("SOL Group Time Status");
	JLabel topimage1 = new JLabel("");

	JMenuBar mb = new JMenuBar();
	JMenu m1 = new JMenu("File");
	JMenuItem mi1 = new JMenuItem("Exit");

	JLabel expemp = new JLabel("Expert Emp");

	JLabel factorlabel = new JLabel("Factor");
	JLabel unitlabel = new JLabel("Unit No");
	JLabel l1 = new JLabel("Project No");
	JLabel l2 = new JLabel("Project Name");
	JLabel l3 = new JLabel("Client Name");
	JLabel l4 = new JLabel("Emp_Code");
	JLabel l5 = new JLabel("Post");
	JLabel l6 = new JLabel("Avg Hrs");
	JLabel l7 = new JLabel("Item Code");
	JLabel l8 = new JLabel("Item Name");

	JLabel tllabel = new JLabel("TL Code");

	JLabel fromlabel = new JLabel("From");
	JLabel tolabel = new JLabel("To");
	JLabel noofdaylabel = new JLabel("No of Days");

	JTextField textFieldFactory = new JTextField();
	JTextField unittf = new JTextField("001");
	JComboBox tf1 = new JComboBox();
	JTextField tf2 = new JTextField(22);
	JTextField tf3 = new JTextField(22);
	JTextField tf4 = new JTextField(6);
	JTextField textFieldPosition = new JTextField();
	JTextField tf6 = new JTextField();
	JTextField tf7 = new JTextField();
	JTextField tf8 = new JTextField();

	JTextField tltf = new JTextField(6);

	JTextField fromtf = new JTextField(9);
	JTextField totf = new JTextField(9);
	JTextField noofdaytf = new JTextField("5", 4);
	javax.swing.Icon showbutic = new javax.swing.ImageIcon("image/list.gif");
	JButton showbut = new JButton("Show", showbutic);

	JComboBox yearcb = new JComboBox();

	JButton calcbut = new JButton("Calculate");
	JButton savebut = new JButton("Allot", new javax.swing.ImageIcon("image/create.gif"));
	JButton updatebut = new JButton("Update");
	JButton removebut = new JButton("Remove");
	JButton closebut = new JButton("Close", new javax.swing.ImageIcon("image/close.gif"));

	javax.swing.JPopupMenu jpm = new javax.swing.JPopupMenu();
	JMenuItem popm1 = new JMenuItem("New Detailer", new javax.swing.ImageIcon("image/check.gif"));
	JMenuItem popm2 = new JMenuItem("New Checker", new javax.swing.ImageIcon("image/check.gif"));
	JMenuItem popm3 = new JMenuItem("Make It Fresh", new javax.swing.ImageIcon("image/check.gif"));
	JMenuItem popm4 = new JMenuItem("Remove Forcefully", new javax.swing.ImageIcon("image/close.gif"));

	javax.swing.JPopupMenu pop = new javax.swing.JPopupMenu();
	JMenuItem popm = new JMenuItem("Remove From Drg List", new javax.swing.ImageIcon("image/close.gif"));
	JMenuItem clearpop = new JMenuItem("Just Clear", new javax.swing.ImageIcon("image/close.gif"));

	javax.swing.JToolTip tip = new javax.swing.JToolTip();

	String[] iColumn = { "Date", "Item Code", "Drg No" };
	Object[][] iData = new Object[0][];

	DefaultTableModel imodel = new DefaultTableModel(iData, iColumn);

	javax.swing.JFrame skf = new javax.swing.JFrame("Remove Job");
	JTextField itf1 = new JTextField(5);
	JTextField itf2 = new JTextField(10);
	JTextField itf3 = new JTextField(10);
	JComboBox icb = new JComboBox();
	JButton ibut1 = new JButton("Remove");
	JButton ibut2 = new JButton("Remove All");
	JButton ibut3 = new JButton("Close");

	JTable itb = new JTable(imodel);
	javax.swing.JScrollPane isp = new javax.swing.JScrollPane(itb);
	JPanel ipanel1 = new JPanel();
	JPanel ipanel2 = new JPanel();

	JPanel northp1 = new JPanel();
	JPanel northp2 = new JPanel();
	JPanel northp3 = new JPanel();
	JPanel nspanel = new JPanel();

	JPanel butpanel1 = new JPanel();
	JPanel butpanel2 = new JPanel();
	JPanel butpanel3 = new JPanel();

	JPanel centerpanel = new JPanel();
	JPanel northpanel = new JPanel();
	JPanel northeastpanel = new JPanel();

	JPanel prgPanel = new JPanel();
	JPanel prgPanel1 = new JPanel();

	javax.swing.JSplitPane spl1 = new javax.swing.JSplitPane(1, itemsp, northeastpanel);
	javax.swing.JSplitPane spl2 = new javax.swing.JSplitPane(0, spl1, northp2);

	java.awt.Font fo = new java.awt.Font("Verdana", 0, 9);
	javax.swing.border.Border line = javax.swing.BorderFactory.createLineBorder(Color.darkGray);
	javax.swing.border.Border bor1 = javax.swing.BorderFactory.createTitledBorder(line, "Work Allotment", 0, 0, fo,
			Color.darkGray);
	javax.swing.border.Border bor2 = javax.swing.BorderFactory.createTitledBorder(line, "Work Allotment Details", 0, 0,
			fo, Color.darkGray);

	java.awt.Toolkit tk = java.awt.Toolkit.getDefaultToolkit();
	java.awt.Dimension ss = tk.getScreenSize();
	java.awt.Dimension fs = internalFrame.getSize();

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

			if (paramInt2 >= 0) {
				setHorizontalAlignment(0);
			}

			String str1 = String.valueOf(empmodel.getValueAt(paramInt1, 1));
			String str2 = String.valueOf(empmodel.getValueAt(paramInt1, 2));

			setToolTipText(str1 + " ( " + str2 + " )");

			if (str1.equalsIgnoreCase("Detailing")) {
				setBackground(Color.white);
				setForeground(Color.black);
			}

			if (str1.equalsIgnoreCase("Checking")) {
				setBackground(Color.red);
				setForeground(Color.white);
			}
			if (str1.equalsIgnoreCase("")) {
				setBackground(Color.white);
			}

			super.getTableCellRendererComponent(paramJTable, paramObject, paramBoolean1, paramBoolean2, paramInt1,
					paramInt2);

			return this;
		}
	}

	class ColoredTableCellRenderer1 extends javax.swing.table.DefaultTableCellRenderer {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		ColoredTableCellRenderer1() {
		}

		public java.awt.Component getTableCellRendererComponent(JTable paramJTable, Object paramObject,
				boolean paramBoolean1, boolean paramBoolean2, int paramInt1, int paramInt2) {
			setEnabled((paramJTable == null) || (paramJTable.isEnabled()));

			setHorizontalAlignment(0);

			String str1 = String.valueOf(drawmodel.getValueAt(paramInt1, 4));
			String str2 = String.valueOf(drawmodel.getValueAt(paramInt1, 5));
			String str3 = String.valueOf(drawmodel.getValueAt(paramInt1, 6));
			String.valueOf(drawmodel.getValueAt(paramInt1, 7));

			int i = str2.length();
			int j = str3.length();

			if ((i <= 1) && (j > 1) && (str1.equalsIgnoreCase("Alloted"))) {
				setBackground(Color.white);
				setForeground(Color.red);
			}
			if ((i > 1) && (j <= 1) && (str1.equalsIgnoreCase("Alloted"))) {
				setBackground(Color.white);
				setForeground(new Color(150, 150, 250));
			}
			if ((i == 1) && (j == 1) && (str1.equalsIgnoreCase("Fresh"))) {
				setBackground(Color.white);
				setForeground(Color.black);
			}
			if ((i > 1) && (j > 1) && (str1.equalsIgnoreCase("Alloted"))) {
				setBackground(Color.white);
				setForeground(new Color(50, 150, 50));
			}

			if ((i == 1) && (j == 1) && (str1.equalsIgnoreCase("Alloted"))) {
				setBackground(Color.white);
				setForeground(Color.darkGray);
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

			String str = String.valueOf(drawmodel.getValueAt(paramInt1, 7));

			setHorizontalAlignment(0);

			if (str.equalsIgnoreCase("Detail-U")) {
				setBackground(new Color(240, 240, 250));
				setForeground(Color.darkGray);
			}
			if (str.equalsIgnoreCase("Detail-C")) {
				setBackground(new Color(200, 200, 250));
				setForeground(Color.darkGray);
			}
			if (str.equalsIgnoreCase("Check-U")) {
				setBackground(new Color(50, 200, 50));
				setForeground(Color.black);
			}
			if (str.equalsIgnoreCase("Check-C")) {
				setBackground(new Color(50, 150, 50));
				setForeground(Color.white);
			}
			if (str.equalsIgnoreCase("0")) {
				setBackground(Color.white);
				setForeground(Color.darkGray);
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

			String str = String.valueOf(model.getValueAt(paramInt1, 0));

			setHorizontalAlignment(0);

			if (str.equalsIgnoreCase("SUN")) {
				setBackground(new Color(250, 240, 230));
			}

			super.getTableCellRendererComponent(paramJTable, paramObject, paramBoolean1, paramBoolean2, paramInt1,
					paramInt2);

			return this;
		}
	}

	ColoredTableCellRenderer skr = new ColoredTableCellRenderer();
	ColoredTableCellRenderer1 skr1 = new ColoredTableCellRenderer1();
	ColoredTableCellRenderer2 skr2 = new ColoredTableCellRenderer2();
	ColoredTableCellRenderer3 skr3 = new ColoredTableCellRenderer3();


	java.awt.Container c;
	java.awt.Container c1;

	public void px1() {
		c1 = skf.getContentPane();
		c1.setLayout(new java.awt.BorderLayout());
		ipanel1.add(itf1);
		itf1.setEditable(true);
		ipanel1.add(itf2);
		itf2.setEditable(false);
		ipanel1.add(icb);
		icb.setEditable(true);

		ipanel2.add(itf3);
		itf3.setEditable(false);
		ipanel2.add(ibut1);
		ibut1.setMnemonic(82);
		ipanel2.add(ibut3);
		ibut3.setMnemonic(67);

		itf1.setHorizontalAlignment(0);
		itf1.setFont(fo);
		itf2.setHorizontalAlignment(0);
		itf2.setFont(fo);
		itf3.setHorizontalAlignment(0);
		itf3.setFont(fo);

		c1.add(isp, "Center");
		c1.add(ipanel1, "North");
		c1.add(ipanel2, "South");

		icb.addItemListener(this);
		ibut1.addActionListener(this);
		ibut2.addActionListener(this);
		ibut3.addActionListener(this);

		skf.setSize(400, 400);
		skf.setLocation(300, 120);
		skf.setVisible(true);
	}

	public void px() {
		c = internalFrame.getContentPane();
		c.setLayout(new java.awt.BorderLayout());
		northp1.setLayout(null);
		northp2.setLayout(new java.awt.BorderLayout());
		northp3.setLayout(new java.awt.GridLayout(12, 2, 2, 2));
		northpanel.setLayout(new java.awt.BorderLayout());
		nspanel.setLayout(new java.awt.FlowLayout());
		northeastpanel.setLayout(new java.awt.BorderLayout());
		butpanel1.setLayout(new java.awt.FlowLayout());
		butpanel2.setLayout(new java.awt.BorderLayout());
		centerpanel.setLayout(new java.awt.BorderLayout());

		prgPanel.setLayout(new java.awt.FlowLayout(0));
		prgPanel1.setLayout(new java.awt.FlowLayout(2));

		jpm.add(popm4);

		SolTableModel.decorateTable(tb);
		SolTableModel.decorateTable(itb);
		SolTableModel.decorateTable(drawtb);
		SolTableModel.decorateTable(itemtb);
		SolTableModel.decorateTable(emptb);

		tb.setIntercellSpacing(new java.awt.Dimension(1, 1));
		itb.setIntercellSpacing(new java.awt.Dimension(1, 1));
		drawtb.setIntercellSpacing(new java.awt.Dimension(1, 1));
		itemtb.setIntercellSpacing(new java.awt.Dimension(1, 1));
		emptb.setIntercellSpacing(new java.awt.Dimension(1, 1));

		tb.setGridColor(new Color(240, 240, 250));
		drawtb.setGridColor(new Color(240, 240, 250));
		itb.setGridColor(new Color(240, 240, 250));
		itemtb.setGridColor(new Color(240, 240, 250));
		emptb.setGridColor(new Color(240, 240, 250));

		mb.add(m1);
		m1.add(mi1);

		pop.add(clearpop);
		pop.add(popm);

		for (int i = 0; i < emptb.getColumnCount(); i++) {
			emptb.getColumnModel().getColumn(i).setCellRenderer(skr);
		}

		for (int i = 0; i < drawtb.getColumnCount(); i++) {
			drawtb.getColumnModel().getColumn(i).setCellRenderer(skr1);
		}

		drawtb.getColumnModel().getColumn(7).setCellRenderer(skr2);

		emptb.removeColumn(emptb.getColumnModel().getColumn(1));
		emptb.getColumnModel().getColumn(1).setPreferredWidth(210);

		tb.getColumnModel().getColumn(3).setPreferredWidth(170);
		tb.getColumnModel().getColumn(4).setPreferredWidth(170);

		tb.setFont(fo);
		itemtb.setFont(fo);
		drawtb.setFont(fo);
		emptb.setFont(fo);

		itemtb.getColumnModel().getColumn(0).setPreferredWidth(30);
		emptb.getColumnModel().getColumn(0);

		for (int i = 0; i < tb.getColumnCount(); i++) {
			tb.getColumnModel().getColumn(i).setCellRenderer(skr3);
		}

		topimage.setBounds(5, 10, 800, 80);
		topimage1.setBounds(810, 10, 200, 80);
		l1.setBounds(195, 89, 80, 20);
		tf1.setBounds(275, 89, 80, 20);
		tf1.setEditable(true);
		l2.setBounds(355, 89, 110, 20);
		tf2.setBounds(465, 89, 210, 20);
		tf2.setEditable(false);
		l3.setBounds(645, 89, 110, 20);
		tf3.setBounds(730, 89, 180, 20);
		tf3.setEditable(false);
		tllabel.setBounds(915, 89, 110, 20);
		tltf.setBounds(125, 89, 60, 20);
		tltf.setEditable(false);

		topimage.setFont(new java.awt.Font("Tahoma", 1, 22));
		topimage.setForeground(Color.orange);
		topimage.setHorizontalAlignment(0);
		topimage1.setFont(new java.awt.Font("Tahoma", 1, 22));
		topimage1.setForeground(Color.orange);
		topimage1.setHorizontalAlignment(0);

		northpanel.add(topimage, "West");
		northpanel.add(topimage1, "East");
		nspanel.add(l1);
		nspanel.add(tf1);
		tf1.setFont(fo);
		nspanel.add(l2);
		nspanel.add(tf2);
		tf2.setFont(fo);
		nspanel.add(l3);
		nspanel.add(tf3);
		tf3.setFont(fo);
		nspanel.add(tllabel);
		nspanel.add(tltf);
		tltf.setFont(fo);

		northpanel.setPreferredSize(new java.awt.Dimension(100, 100));
		northpanel.setBackground(new Color(60, 130, 130));

		northp3.add(unitlabel);
		northp3.add(unittf);
		unittf.setFont(fo);
		northp3.add(factorlabel);
		northp3.add(textFieldFactory);
		textFieldFactory.setFont(fo);
		textFieldFactory.setEditable(false);
		northp3.add(l4);
		northp3.add(tf4);
		tf4.setFont(fo);
		tf4.setEditable(false);
		northp3.add(l5);
		northp3.add(textFieldPosition);
		textFieldPosition.setFont(fo);
		textFieldPosition.setEditable(false);
		northp3.add(l6);
		northp3.add(tf6);
		tf6.setFont(fo);
		tf6.setEditable(false);
		northp3.add(l7);
		northp3.add(tf7);
		tf7.setFont(fo);
		tf7.setEditable(false);
		northp3.add(l8);
		northp3.add(tf8);
		tf8.setFont(fo);
		tf8.setEditable(false);

		prgPanel.add(savebut);
		savebut.setMnemonic(83);
		prgPanel.add(removebut);
		removebut.setMnemonic(82);
		prgPanel.add(closebut);
		closebut.setMnemonic(67);

		prgPanel1.add(progressBar, "East");
		progressBar.setPreferredSize(new java.awt.Dimension(200, 23));
		progressBar.setStringPainted(true);
		progressBar.setForeground(new Color(60, 60, 140));

		butpanel2.add(prgPanel, "West");
		butpanel2.add(prgPanel1, "East");

		fromtf.setEditable(false);
		totf.setEditable(false);
		noofdaytf.setEditable(false);

		butpanel3.add(fromlabel);
		butpanel3.add(fromtf);
		fromtf.setFont(fo);
		butpanel3.add(tolabel);
		butpanel3.add(totf);
		totf.setFont(fo);
		butpanel3.add(noofdaylabel);
		butpanel3.add(noofdaytf);
		noofdaytf.setFont(fo);
		butpanel3.add(showbut);
		showbut.setMnemonic(87);

		northpanel.add(northp1, "North");
		northpanel.add(nspanel, "South");

		northeastpanel.add(drawsp, "Center");

		centerpanel.add(butpanel3, "North");
		centerpanel.add(sp, "Center");
		centerpanel.add(butpanel2, "South");
		centerpanel.setBorder(bor1);

		northp3.setPreferredSize(new java.awt.Dimension(150, 30));
		empsp.setPreferredSize(new java.awt.Dimension(240, 30));

		northp2.add(empsp, "West");
		northp2.add(centerpanel, "Center");
		northp2.add(northp3, "East");

		c.add(northpanel, "North");
		c.add(spl2, "Center");

		spl1.setOneTouchExpandable(true);
		spl2.setOneTouchExpandable(true);
		spl1.setDividerLocation(340);
		spl2.setDividerLocation(225);

		calcbut.addActionListener(this);
		savebut.addActionListener(this);
		updatebut.addActionListener(this);
		removebut.addActionListener(this);
		closebut.addActionListener(this);
		showbut.addActionListener(this);

		unittf.addActionListener(this);

		tf1.addActionListener(this);
		tf2.addActionListener(this);
		tf3.addActionListener(this);
		tf4.addActionListener(this);
		textFieldPosition.addActionListener(this);
		tf6.addActionListener(this);
		tf7.addActionListener(this);
		tf8.addActionListener(this);

		clearpop.addActionListener(this);
		popm.addActionListener(this);
		popm1.addActionListener(this);
		popm2.addActionListener(this);
		popm3.addActionListener(this);
		popm4.addActionListener(this);
		mi1.addActionListener(this);

		itb.addMouseListener(this);
		tb.addMouseListener(this);
		itemtb.addMouseListener(this);
		itemtb.addKeyListener(this);
		drawtb.addMouseListener(this);
		emptb.addMouseListener(this);
		emptb.addKeyListener(this);

		internalFrame.setTitle("Work Allotment");

		internalFrame.setSize(1024, 620);
		internalFrame.setVisible(true);
		UIUtil.centreToApplication(internalFrame);
	}

	String empstr = "";

	public void paramUser() {
		new TechMainFrame();
		String str = new String(TechMainFrame.textFieldPost.getText());
		empstr = TechMainFrame.textFieldLoggedBy.getText();

		if (str.equalsIgnoreCase("Team Leader")) {
			projectList();
		}
	}

	public void projectList() {
		tf1.removeAllItems();

		Connection con = null;
		Statement stat = null;
		ResultSet rs = null;
		try {
			con = DBConnectionUtil.getConnection();
			stat = con.createStatement();
			rs = stat.executeQuery("select project_no from project_co where co_code='" + empstr + "' ");
			while (rs.next()) {
				String str = new String(rs.getString(1));
				tf1.addItem(String.valueOf(str));
			}
		} catch (Exception localException) {
			System.out.println(localException);
		} finally {
			DBConnectionUtil.closeConnection(rs, stat, con);
		}

	}

	public void showItem() {
		Connection con = null;
		Statement stat = null;
		ResultSet rs = null;
		try {
			con = DBConnectionUtil.getConnection();
			stat = con.createStatement();
			rs = stat.executeQuery(
					"select item_code,item_name from ESTIMATION where project_no='" + tf1.getSelectedItem() + "'  ");
			rs.getMetaData().getColumnCount();
			while (rs.next()) {
				String str1 = new String(rs.getString(1));
				String str2 = new String(rs.getString(2));
				itemmodel.addRow(new Object[] { str1, str2 });
			}
		} catch (Exception localException) {
			System.out.println(localException);
		} finally {
			DBConnectionUtil.closeConnection(rs, stat, con);
		}
	}

	public void showStatus() {
		int i = itemtb.getRowCount();
		Connection con = null;
		Statement stat = null;
		ResultSet rs = null;

		try {
			con = DBConnectionUtil.getConnection();
			for (int j = 0; j < i; j++) {
				String str1 = String.valueOf(itemmodel.getValueAt(j, 0));

				stat = con.createStatement();
				rs = stat.executeQuery(
						"select count(DRAWING_NO), (select count(status) from drawingno where status='Alloted' and project_no='"
								+ tf1.getSelectedItem() + "' and item_code='" + str1
								+ "'), min(Drawing_no), max(Drawing_no) from drawingno where project_no='"
								+ tf1.getSelectedItem() + "' and item_code='" + str1 + "' ");
				rs.getMetaData().getColumnCount();
				while (rs.next()) {
					String str2 = new String(rs.getString(1));
					String str3 = new String(rs.getString(2));
					String str4 = new String(rs.getString(3));
					String str5 = new String(rs.getString(4));

					itemmodel.setValueAt("(" + str4 + "-" + str5 + ") " + str2 + "-" + str3, j, 2);
				}
			}
		} catch (Exception localException) {
			System.out.println(localException);
		} finally {
			DBConnectionUtil.closeConnection(rs, stat, con);
		}
	}

	public void showDraw() {
		int i = itemtb.getSelectedRow();
		String str1 = String.valueOf(itemmodel.getValueAt(i, 0));
		String str2 = String.valueOf(itemmodel.getValueAt(i, 1));

		tf7.setText(str1);
		tf8.setText(str2);

		Connection con = null;
		Statement stat = null;
		ResultSet rs = null;
		try {
			String query = "select drawing_no,dtl_time,chk_time,total_time,status,detailing,checking from DRAWINGNO where project_no='"
					+ tf1.getSelectedItem() + "' and item_code='" + str1 + "' order by drawing_no";
			con = DBConnectionUtil.getConnection();
			stat = con.createStatement();
			rs = stat.executeQuery(query);
			System.out.println("showDraw: " + query);
			rs.getMetaData().getColumnCount();
			while (rs.next()) {
				String str3 = new String(rs.getString(1));
				String str4 = new String(rs.getString(2));
				String str5 = new String(rs.getString(3));
				String str6 = new String(rs.getString(4));
				String str7 = new String(rs.getString(5));
				String str8 = new String(rs.getString(6));
				String str9 = new String(rs.getString(7));

				drawmodel.addRow(new Object[] { str3, str4, str5, str6, str7, str8, str9, "", str1 });
				System.out.println("Shekhar : " + str1);
			}
		} catch (Exception localException) {
			System.out.println(localException);
		} finally {
			DBConnectionUtil.closeConnection(rs, stat, con);
		}
	}

	int sheetqty = 0;
	int remainqty = 0;
	java.util.Calendar gcal;

	public void showDraw2() {
		int i = itemtb.getSelectedRow();
		String str1 = String.valueOf(itemmodel.getValueAt(i, 0));
		String str2 = String.valueOf(itemmodel.getValueAt(i, 1));

		tf7.setText(str1);
		tf8.setText(str2);

		Connection con = null;
		Statement stat = null;
		ResultSet rs = null;
		try {
			con = DBConnectionUtil.getConnection();
			stat = con.createStatement();
			rs = stat.executeQuery("select sheet_qty, remaining from estimation where project_no='"
					+ tf1.getSelectedItem() + "' and item_code='" + str1 + "' ");
			rs.getMetaData().getColumnCount();
			while (rs.next()) {
				String str3 = new String(rs.getString(1));
				String str4 = new String(rs.getString(2));

				sheetqty = (Integer.parseInt(str3) - 1);
				remainqty = Integer.parseInt(str4);

				if (remainqty == 0) {
					remainqty = 0;
				}
				if (remainqty > 0) {
					remainqty -= 1;
				}

				System.out.println(sheetqty + "\t" + remainqty);
			}
		} catch (Exception localException) {
			System.out.println(localException);
		} finally {
			DBConnectionUtil.closeConnection(rs, stat, con);
		}
	}

	public void empDetails() {
		int i = emptb.getSelectedRow();
		String str1 = String.valueOf(empmodel.getValueAt(i, 0));

		int j = itemtb.getSelectedRow();
		String str2 = String.valueOf(itemmodel.getValueAt(j, 0));
		String str3 = String.valueOf(itemmodel.getValueAt(j, 1));

		tf7.setText(str2);
		tf8.setText(str3);

		Connection con = null;
		Statement stat = null;
		ResultSet rs = null;
		try {
			con = DBConnectionUtil.getConnection();
			stat = con.createStatement();
			rs = stat.executeQuery("select job,Average_hrs,factor from emp_Status where emp_code='" + str1 + "' ");
			rs.getMetaData().getColumnCount();
			while (rs.next()) {
				String str5 = new String(rs.getString(1));
				String str6 = new String(rs.getString(2));
				new String(rs.getString(3));
				tf4.setText(str1);
				textFieldPosition.setText(str5);
				tf6.setText(str6);

				tf4.selectAll();
			}
		} catch (Exception localException) {
			System.out.println(localException);
		} finally {
			DBConnectionUtil.closeConnection(rs, stat, con);
		}
		String str4 = String.valueOf(tf1.getSelectedItem());
		textFieldFactory.setText(String.valueOf(ProjectDAO.getEmpFactorByProject(str1, str4)));
	}

	public void expertEmp() {
		empmodel.setNumRows(0);
		Connection con = null;
		Statement stat = null;
		ResultSet rs = null;
		try {
			con = DBConnectionUtil.getConnection();
			stat = con.createStatement();
			rs = stat.executeQuery(
					"select DISTINCT(p.Emp_Code), p.dtl_chk, e.emp_name from PROJECTMANPOWER p, EMP_STATUS e where p.project_no='"
							+ tf1.getSelectedItem() + "' and p.emp_code=e.emp_code  ");
			rs.getMetaData().getColumnCount();
			while (rs.next()) {
				String str1 = new String(rs.getString(1));
				String str2 = new String(rs.getString(2));
				String str3 = new String(rs.getString(3));

				empmodel.addRow(new Object[] { str1.toUpperCase(), str2.toUpperCase(), str3.toUpperCase() });
				System.out.println(str1 + " " + str2);
			}
		} catch (Exception localException) {
			System.out.println(localException);
		} finally {
			DBConnectionUtil.closeConnection(rs, stat, con);
		}
	}

	public void fromDate() {
		int i = emptb.getSelectedRow();
		String str1 = String.valueOf(empmodel.getValueAt(i, 0));
		Connection con = null;
		Statement stat = null;
		ResultSet rs = null;
		try {
			con = DBConnectionUtil.getConnection();
			stat = con.createStatement();
			rs = stat.executeQuery("select min(from_Date),min(to_date) from PROJECTMANPOWER where project_no='"
					+ tf1.getSelectedItem() + "' and emp_code='" + str1 + "' ");
			rs.getMetaData().getColumnCount();
			while (rs.next()) {
				String str2 = new String(rs.getString(1));
				String str3 = new String(rs.getString(2));

				fromtf.setText(str2);
				totf.setText(str3);
			}
		} catch (Exception localException) {
			System.out.println(localException);
		} finally {
			DBConnectionUtil.closeConnection(rs, stat, con);
		}
	}

	public void allotedDates() {
		model.setNumRows(0);
		int i = emptb.getSelectedRow();
		String str1 = String.valueOf(empmodel.getValueAt(i, 0));
		String str2 = String.valueOf(empmodel.getValueAt(i, 1));

		Connection con = null;
		PreparedStatement stat = null;
		ResultSet rs = null;
		try {
			con = DBConnectionUtil.getConnection();
			stat = con.prepareStatement(
					"select from_Date2, project_no from PROJECTMANPOWER where project_no='" + tf1.getSelectedItem()
							+ "' and emp_code='" + str1 + "' and dtl_chk='" + str2 + "' order by From_Date2 ");
			rs = stat.executeQuery();
			rs.getMetaData().getColumnCount();
			while (rs.next()) {
				java.sql.Date localDate = rs.getDate(1);
				String str3 = SolDateFormatter.SQLtoDDMMYY(localDate);
				String str4 = new String(rs.getString(2));

				model.addRow(new Object[] { "", str3, str4 });

				int k = rs.getRow();
				noofdaytf.setText(String.valueOf(k));
			}
		} catch (Exception localException) {
			System.out.println(localException);
		} finally {
			DBConnectionUtil.closeConnection(rs, stat, con);
		}
	}

	String[] days = { "SUN", "MON", "TUE", "WED", "THU", "FRI", "SAT" };

	public void dayName() {
		int i = tb.getRowCount();

		for (int j = 0; j < i; j++) {
			String str1 = String.valueOf(model.getValueAt(j, 1));

			String str2 = str1.substring(0, 2);
			String str3 = str1.substring(3, 5);
			String str4 = str1.substring(6, 10);

			int k = Integer.parseInt(str2);
			int m = Integer.parseInt(str3);
			int n = Integer.parseInt(str4);

			gcal = new java.util.GregorianCalendar(n, m - 1, k);
			String str5 = String.valueOf(days[(gcal.get(7) - 1)]);

			System.out.println(str5);

			model.setValueAt(str5, j, 0);
		}
	}

	public void jobDetails() {
		textFieldPosition.setText(String.valueOf(empmodel.getValueAt(emptb.getSelectedRow(), 1)));
		String str1 = "0";
		String str2 = "0";
		String str3 = "0";

		int i = tb.getRowCount();
		tb.getSelectedRow();
		System.out.println(i);

		int k = 0;

		Connection con = null;
		Statement stat = null;
		ResultSet rs = null;
		try {
			con = DBConnectionUtil.getConnection();
			for (int m = 0; m < i; m++) {
				String str4 = String.valueOf(model.getValueAt(m, 1));
				String str5 = String.valueOf(empmodel.getValueAt(emptb.getSelectedRow(), 1));

				stat = con.createStatement();
				rs = stat.executeQuery("select Project_No,Item_Code,Drawing_No from " + str5 + " where emp_code='"
						+ tf4.getText() + "' and date_month='" + str4 + "' ");

				while (rs.next()) {
					k += 1;
					str1 = new String(rs.getString(1));
					str2 = new String(rs.getString(2));
					str3 = new String(rs.getString(3));

					if (k <= 1) {
						model.setValueAt(str1, m, 2);
						model.setValueAt(str2, m, 3);
						model.setValueAt(str3, m, 4);
					}
					if (k > 1) {
						String str6 = String.valueOf(model.getValueAt(m, 3));
						String str7 = String.valueOf(model.getValueAt(m, 4));

						model.setValueAt(str1, m, 2);
						model.setValueAt(str6 + ", " + str2, m, 3);
						model.setValueAt(str7 + ", " + str3, m, 4);
					}
				}

				str1 = "0";

				k = 0;
			}
		} catch (Exception ex) {
			System.out.println(ex);
		}
	}

	String remainRangestr = "0";

	public void remainRange() {
		int[] arrayOfInt = tb.getSelectedRows();

		Connection con = null;
		Statement stat = null;
		ResultSet rs = null;
		try {

			con = DBConnectionUtil.getConnection();
			for (int i = 0; i < arrayOfInt.length; i++) {
				String str1 = String.valueOf(model.getValueAt(arrayOfInt[i], 2));
				String str2 = String.valueOf(model.getValueAt(arrayOfInt[i], 3));
				String str3 = String.valueOf(model.getValueAt(arrayOfInt[i], 4));

				stat = con.createStatement();
				rs = stat.executeQuery(
						"select count(drawing_no) from " + textFieldPosition.getText() + " where project_NO='" + str1
								+ "' and item_code='" + str2 + "' and drawing_no='" + str3 + "' ");
				rs.getMetaData().getColumnCount();
				while (rs.next()) {
					remainRangestr = new String(rs.getString(1));
				}
			}
		} catch (Exception localException) {
			JOptionPane.showMessageDialog(internalFrame, localException.getMessage().toString());
		} finally {
			DBConnectionUtil.closeConnection(rs, stat, con);
		}
	}

	public void clearDrgRange() {
		textFieldPosition.getText();

		int[] arrayOfInt = tb.getSelectedRows();

		Connection con = null;
		Statement stat = null;
		try {
			con = DBConnectionUtil.getConnection();
			for (int i = 0; i < arrayOfInt.length; i++) {
				String str2 = String.valueOf(model.getValueAt(arrayOfInt[i], 2));
				String str3 = String.valueOf(model.getValueAt(arrayOfInt[i], 3));
				String str4 = String.valueOf(model.getValueAt(arrayOfInt[i], 4));
				String str5 = str4;

				int j = str5.indexOf('-');
				int k = str5.length();

				String str6 = str5.substring(0, j);
				String str7 = str5.substring(j + 1, k);

				System.out.println(str6 + "\t" + str7);

				stat = con.createStatement();
				int affected = stat.executeUpdate(
						"update drawingno set " + textFieldPosition.getText() + " ='0' where project_no='" + str2
								+ "' and item_code='" + str3 + "' and drawing_no between '" + str6 + "' and '" + str7
								+ "' AND drawing_no not in(select drg_no from timesheet where project_no='" + str2
								+ "' and item_code='" + str3 + "') ");

				if (affected > 0) {
					model.setValueAt("0", arrayOfInt[i], 3);
					model.setValueAt("0", arrayOfInt[i], 4);
				}

			}
		} catch (Exception localException) {
			JOptionPane.showMessageDialog(internalFrame, localException.getMessage().toString());
		} finally {
			DBConnectionUtil.closeConnection(stat, con);
		}
	}

	public void collectDate() {
		itf1.setText(tf4.getText());
		itf2.setText(textFieldPosition.getText());
		itf3.setText(String.valueOf(tf1.getSelectedItem()));

		icb.removeAllItems();
		icb.addItem("%");
		Connection con = null;
		Statement stat = null;
		ResultSet rs = null;
		try {
			con = DBConnectionUtil.getConnection();
			stat = con.createStatement();
			rs = stat.executeQuery("select distinct(DATE_MONTH) FROM " + itf2.getText() + " where emp_code='"
					+ itf1.getText() + "' and project_no='" + itf3.getText() + "' and date_month like '%' ");
			rs.getMetaData().getColumnCount();
			while (rs.next()) {
				String str = new String(rs.getString(1));
				icb.addItem(str);
			}
		} catch (Exception localException) {
			System.out.println(localException);
		} finally {
			DBConnectionUtil.closeConnection(rs, stat, con);
		}
	}

	public void dateWiseJob() {
		itf1.setText(tf4.getText());
		itf2.setText(textFieldPosition.getText());
		itf3.setText(String.valueOf(tf1.getSelectedItem()));

		imodel.setNumRows(0);
		Connection con = null;
		Statement stat = null;
		ResultSet rs = null;
		try {
			con = DBConnectionUtil.getConnection();
			stat = con.createStatement();
			rs = stat.executeQuery("select DATE_MONTH,ITEM_CODE, DRAWING_NO FROM " + itf2.getText()
					+ " where emp_code='" + itf1.getText() + "' and project_no='" + itf3.getText()
					+ "' and date_month like '" + icb.getSelectedItem() + "' ");
			rs.getMetaData().getColumnCount();
			while (rs.next()) {
				String str1 = new String(rs.getString(1));
				String str2 = new String(rs.getString(2));
				String str3 = new String(rs.getString(3));

				imodel.addRow(new Object[] { str1, str2, str3 });
			}
		} catch (Exception localException) {
			System.out.println(localException);
		} finally {
			DBConnectionUtil.closeConnection(rs, stat, con);
		}
	}

	public class removeJobs extends Thread {
		public removeJobs() {
		}

		public void run() {
			Connection con = null;
			Statement stat = null;

			int[] arrayOfInt = itb.getSelectedRows();
			try {
				con = DBConnectionUtil.getConnection();
				for (int i = 0; i < arrayOfInt.length; i++) {

					String.valueOf(imodel.getValueAt(arrayOfInt[i], 0));
					String str2 = itf2.getText();

					String str3 = itf3.getText();
					String str4 = String.valueOf(imodel.getValueAt(arrayOfInt[i], 1));
					String str5 = String.valueOf(imodel.getValueAt(arrayOfInt[i], 2));

					progressBar.setMinimum(0);
					progressBar.setMaximum(arrayOfInt.length - 1);
					progressBar.setValue(i);
					progressBar.setStringPainted(true);
					try {
						Thread.sleep(100L);
					} catch (InterruptedException localInterruptedException) {
						System.out.println(localInterruptedException);
					}

					stat = con.createStatement();
					int affected = stat.executeUpdate("delete from " + str2 + " where project_no='" + str3
							+ "' and drawing_no='" + str5 + "' and emp_code='" + itf1.getText()
							+ "' and drawing_no not in(select drg_no from timesheet where project_no='" + str3
							+ "' and item_code='" + str4 + "' and drgstatus like 'Detail' ) ");

					if (affected > 0) {
						affected = stat.executeUpdate("update drawingno set " + itf2.getText()
								+ " ='0' where project_no='" + str3 + "' and item_code='" + str4 + "' and drawing_no ='"
								+ str5 + "' AND drawing_no not in(select drawing_no from " + itf2.getText()
								+ " where project_no='" + str3 + "' and item_code='" + str4 + "') ");
						if (affected > 0) {
							imodel.setValueAt("0", arrayOfInt[i], 1);
							imodel.setValueAt("0", arrayOfInt[i], 2);

							currentStatus1();
							currentStatus2();
							dateWiseJob();
						}

					}
				}
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(internalFrame, ex.getMessage().toString());
			} finally {
				DBConnectionUtil.closeConnection(stat, con);
			}
		}
	}

	public void removeForcefully() {
		int i = itb.getSelectedRow();

		String.valueOf(imodel.getValueAt(i, 0));
		String str2 = itf2.getText();

		String str3 = itf3.getText();
		String str4 = String.valueOf(imodel.getValueAt(i, 1));
		String str5 = String.valueOf(imodel.getValueAt(i, 2));

		String str6 = null;

		Connection con = null;
		Statement stat = null;
		ResultSet rs = null;
		try {
			con = DBConnectionUtil.getConnection();
			stat = con.createStatement();
			rs = stat.executeQuery("select fab_status from timesheet where project_no='" + str3 + "' and drg_no='"
					+ str5 + "' and item_code='" + str4 + "' ");

			while (rs.next()) {
				str6 = rs.getString(1);
			}

			if ((str6.equalsIgnoreCase("0")) || (str6 == null)) {
				String str8 = null;

				if (str2.equalsIgnoreCase("Detailing")) {
					str8 = "delete from timesheet where project_no='" + str3 + "' and drg_no='" + str5
							+ "' and item_code='" + str4 + "' and drgstatus='DETAIL' ";
				}
				if (str2.equalsIgnoreCase("Checking")) {
					str8 = "delete from timesheet where project_no='" + str3 + "' and drg_no='" + str5
							+ "' and item_code='" + str4 + "' and drgstatus1='CHECK' ";
				}

				int affected = stat.executeUpdate(str8);
				if (affected > 0) {
					affected = stat.executeUpdate("delete from under_process where project_no='" + str3
							+ "' and drg_no='" + str5 + "' and item_code='" + str4 + "' ");
					affected = stat.executeUpdate("delete from " + str2 + " where project_no='" + str3
							+ "' and drawing_no='" + str5 + "' and emp_code='" + itf1.getText() + "' ");
					if (affected > 0) {
						affected = stat
								.executeUpdate("update drawingno set " + itf2.getText() + " ='0' where project_no='"
										+ str3 + "' and item_code='" + str4 + "' and drawing_no ='" + str5 + "' ");
						if (affected > 0) {
							imodel.setValueAt("0", i, 1);
							imodel.setValueAt("0", i, 2);
							drawmodel.setNumRows(0);

							showDraw();
							currentStatus1();
							currentStatus2();
						}
					}
				}
			}
		} catch (Exception localException2) {
			JOptionPane.showMessageDialog(internalFrame, localException2.getMessage().toString());
		} finally {
			DBConnectionUtil.closeConnection(rs, stat, con);
		}
		str6 = null;
	}

	public void alert() {
		int i = drawtb.getSelectedRow();
		String status = (String) drawmodel.getValueAt(i, 4);
		String detailer = (String) drawmodel.getValueAt(i, 5);
		String checker = (String) drawmodel.getValueAt(i, 6);
		String jobType = textFieldPosition.getText();

		System.out.println("Status: " + status);
		System.out.println("detailer: " + detailer);
		System.out.println("checker: " + checker);
		System.out.println("jobType: " + jobType);

		if (jobType.equalsIgnoreCase(ProjectConstants.JOB_DETAILING)
				&& status.equalsIgnoreCase(ProjectConstants.ALLOTED) && detailer.length() == 3) {
			JOptionPane.showMessageDialog(internalFrame,
					"Drawing is Allready Alloted To " + detailer + " For Detailing");
			drawtb.clearSelection();
			tf1.requestFocus();
		}
		if (jobType.equalsIgnoreCase(ProjectConstants.JOB_DETAILING)
				&& status.equalsIgnoreCase(ProjectConstants.ALLOTED) &&  checker.length() == 3) {
			JOptionPane.showMessageDialog(internalFrame, "Drawing is Allready Alloted To " + checker + " For Checking");
			drawtb.clearSelection();
			tf1.requestFocus();
		}
	}

	public void neededTime() {
		int[] arrayOfInt = drawtb.getSelectedRows();
		int i = arrayOfInt.length;

		String drawingNeededTimeString = "0";
		String jobType = textFieldPosition.getText();
		String factorString = textFieldFactory.getText();

		if (jobType.equalsIgnoreCase("Detailing")) {
			drawingNeededTimeString = String.valueOf(drawmodel.getValueAt(0, 1));
			drawingNeededTime = Float.parseFloat(drawingNeededTimeString);
		}
		if (jobType.equalsIgnoreCase("Checking")) {
			drawingNeededTimeString = String.valueOf(drawmodel.getValueAt(0, 2));
			drawingNeededTime = Float.parseFloat(drawingNeededTimeString);
		}

		if (factorString != null && !factorString.isEmpty()) {
			float factor = Float.parseFloat(factorString);
			employeeNeededTime = (drawingNeededTime / factor * i);
			String neededTimeString = df1.format(employeeNeededTime);
			topimage.setText(tf4.getText() + " Needs " + neededTimeString + " Hrs To Complete This Drawing.");
		}
	}

	public void allotedTime() {
		int i = tb.getRowCount();
		int[] arrayOfInt = tb.getSelectedRows();
		int j = arrayOfInt.length;
		int k = Integer.parseInt(tf6.getText());

		int m = i * k;
		int n = j * k;

		topimage.setText(topimage.getText());
		topimage1.setText(" [" + String.valueOf(n) + "/" + String.valueOf(m) + "] ");
	}

	public class SaveRecordThread extends Thread {

		Connection con = null;
		Statement stat = null;
		int result = 0;

		public SaveRecordThread(Connection con, Statement stat, int result) {
			this.con = con;
			this.stat = stat;
			this.result = result;
		}

		public void run() {
			String str1 = textFieldPosition.getText();

			int i = drawtb.getSelectedRow();
			int[] arrayOfInt1 = drawtb.getSelectedRows();
			int j = arrayOfInt1.length + i - 1;

			firstdrg = String.valueOf(drawmodel.getValueAt(i, 0));
			lastdrg = String.valueOf(drawmodel.getValueAt(j, 0));
			int k = tb.getSelectedRow();
			int[] arrayOfInt2 = tb.getSelectedRows();
			int m = arrayOfInt2.length + k - 1;

			firstdate = String.valueOf(model.getValueAt(k, 1));
			lastdate = String.valueOf(model.getValueAt(m, 1));

			int[] arrayOfInt3 = tb.getSelectedRows();
			ArrayList localArrayList1 = new ArrayList();
			ArrayList<Object> localArrayList2 = new ArrayList<Object>();
			ArrayList localArrayList3 = new ArrayList();
			ArrayList localArrayList4 = new ArrayList();

			int[] arrayOfInt4 = drawtb.getSelectedRows();
			String str3;
			Object localObject;
			String str5;
			String str6;
			for (int n = 0; n < arrayOfInt4.length; n++) {
				str3 = String.valueOf(drawmodel.getValueAt(arrayOfInt4[n], 0));
				localObject = String.valueOf(drawmodel.getValueAt(arrayOfInt4[n], 8));
				String str4 = String.valueOf(drawmodel.getValueAt(arrayOfInt4[n], 3));

				localArrayList1.add(str3);
				localArrayList2.add(localObject);
				localArrayList4.add(str4);

				str5 = "0";
				str6 = textFieldPosition.getText();
				if (str6.equalsIgnoreCase("Detailing")) {
					str5 = String.valueOf(drawmodel.getValueAt(arrayOfInt4[n], 1));
					localArrayList3.add(str5);
				}
				if (str6.equalsIgnoreCase("Checking")) {
					str5 = String.valueOf(drawmodel.getValueAt(arrayOfInt4[n], 2));
					localArrayList3.add(str5);
				}
			}
			try {
				for (int n = 0; n < arrayOfInt3.length; n++) {
					str3 = String.valueOf(model.getValueAt(arrayOfInt3[n], 1));
					localObject = SolDateFormatter.DDMMYYtoSQL(str3);

					for (int i1 = 0; i1 < localArrayList1.size(); i1++) {

						str5 = String.valueOf(localArrayList1.get(i1));
						str6 = String.valueOf(localArrayList2.get(i1));
						String str7 = String.valueOf(localArrayList3.get(i1));
						String str8 = String.valueOf(localArrayList4.get(i1));

						progressBar.setMinimum(0);
						progressBar.setMaximum(localArrayList1.size() - 1);
						progressBar.setValue(i1);
						progressBar.setStringPainted(true);

						stat = con.createStatement();
						int affected = stat.executeUpdate("insert into " + str1 + " values('" + tf1.getSelectedItem()
								+ "','" + str6 + "','" + str5 + "','" + tf4.getText() + "','" + str3 + "','0','0','"
								+ str8 + "','" + str7 + "','0','Ready','0','" + localObject + "')");

						if (affected > 0) {
							int[] arrayOfInt5;
							int i3;
							if (textFieldPosition.getText().equalsIgnoreCase("Detailing")) {
								arrayOfInt5 = drawtb.getSelectedRows();
								for (i3 = 0; i3 < arrayOfInt5.length; i3++) {
									drawmodel.setValueAt(tf4.getText(), arrayOfInt5[i3], 5);
								}
							}
							if (textFieldPosition.getText().equalsIgnoreCase("Checking")) {
								arrayOfInt5 = drawtb.getSelectedRows();
								for (i3 = 0; i3 < arrayOfInt5.length; i3++) {
									drawmodel.setValueAt(tf4.getText(), arrayOfInt5[i3], 6);
								}
							}
							for (int i2 = 0; i2 < localArrayList1.size(); i2++) {
								String str9 = String.valueOf(localArrayList1.get(i2));
								model.setValueAt(str9 + ",", arrayOfInt3[n], 4);

								stat = con.createStatement();
								affected = stat.executeUpdate("update drawingno set status='Alloted', " + str1 + " ='"
										+ tf4.getText() + "' where project_no='" + tf1.getSelectedItem()
										+ "' and item_code='" + str6 + "' and drawing_no ='" + str5 + "'  ");

								if (affected > 0) {
									drawmodel.setValueAt("Alloted", i, 4);
									drawmodel.setValueAt("Alloted", j, 4);
								}

							}
						}
					}
				}
				result = 1;
			} catch (Exception localException2) {
				JOptionPane.showMessageDialog(internalFrame, localException2.getMessage().toString());
			} finally {
				if (result == 1) {
					DBConnectionUtil.closeConnection(stat, con);
				}
			}
		}
	}

	String tblstr = "sk";

	public void deleteDTLCHK() {
		int i = tb.getSelectedRow();

		String str1 = String.valueOf(model.getValueAt(i, 2));
		String str2 = String.valueOf(model.getValueAt(i, 4));
		Connection con = null;
		Statement stat = null;
		try {
			con = DBConnectionUtil.getConnection();
			stat = con.createStatement();
			stat.executeUpdate("delete from " + tblstr + " where drawing_no='" + str2 + "' and project_no='"
					+ tf1.getSelectedItem() + "'   ");
		} catch (Exception localException) {
			JOptionPane.showMessageDialog(internalFrame, str2 + " Deleted of " + tblstr + " from " + str1);
		} finally {
			DBConnectionUtil.closeConnection(stat, con);
		}
	}

	String skclmstr = "sk";

	public void makeFreshDrg() {
		String str1 = null;

		int[] arrayOfInt = tb.getSelectedRows();

		Connection con = null;
		Statement stat = null;
		try {
			con = DBConnectionUtil.getConnection();
			for (int i = 0; i < arrayOfInt.length; i++) {
				str1 = String.valueOf(model.getValueAt(arrayOfInt[i], 4));
				int j = str1.indexOf('-');
				int k = str1.length();

				String str2 = str1.substring(0, j);
				String str3 = str1.substring(j + 1, k);
				System.out.println(str2 + "\t" + str3);

				String.valueOf(tf1.getSelectedItem());

				stat = con.createStatement();
				int affected = stat.executeUpdate("update drawingno set " + skclmstr + " ='0' where PROJECT_NO ='"
						+ tf1.getSelectedItem() + "' and  drawing_no between '" + str2 + "' and '" + str3 + "' ");
				if (affected > 0) {
					model.setValueAt("0", arrayOfInt[i], 4);
				}

			}
		} catch (Exception localException) {
			System.out.println(localException);
		} finally {
			DBConnectionUtil.closeConnection(stat, con);
		}
	}

	public void makeFreshDrgAll() {
		String str1 = null;

		int[] arrayOfInt = tb.getSelectedRows();

		Connection con = null;
		Statement stat = null;
		ResultSet rs = null;
		try {
			con = DBConnectionUtil.getConnection();
			for (int i = 0; i < arrayOfInt.length; i++) {
				str1 = String.valueOf(model.getValueAt(arrayOfInt[i], 4));
				int j = str1.indexOf('-');
				int k = str1.length();

				String str2 = str1.substring(0, j);
				String str3 = str1.substring(j + 1, k);
				System.out.println(str2 + "\t" + str3);

				String.valueOf(tf1.getSelectedItem());

				stat = con.createStatement();
				int affected = stat
						.executeUpdate("update drawingno set detailing='0' and CHECKING= '0' where PROJECT_NO ='"
								+ tf1.getSelectedItem() + "' and  drawing_no between '" + str2 + "' and '" + str3
								+ "' ");
				if (affected > 0) {
					model.setValueAt("0", arrayOfInt[i], 4);
				}
			}
		} catch (Exception ex) {
			System.out.println(ex);
		} finally {
			DBConnectionUtil.closeConnection(rs, stat, con);
		}
	}

	public void currentStatus1() {
		int i = drawtb.getRowCount();

		Connection con = null;
		Statement stat = null;
		ResultSet rs = null;
		try {
			con = DBConnectionUtil.getConnection();
			for (int j = 0; j < i; j++) {
				drawmodel.setValueAt("0", j, 7);
				String str1 = String.valueOf(drawmodel.getValueAt(j, 0));

				stat = con.createStatement();
				rs = stat.executeQuery("select distinct(DTL_CHK) FROM UNDER_PROCESS WHERE PROJECT_NO='"
						+ tf1.getSelectedItem() + "' and drg_no='" + str1 + "' AND DRG_STATUS='Under Process' ");
				rs.getMetaData().getColumnCount();

				int m = rs.getRow();
				if (m < 1) {
					drawmodel.setValueAt("0", j, 7);
				}

				while (rs.next()) {
					String str2 = new String(rs.getString(1));
					drawmodel.setValueAt(str2 + "-U", j, 7);
				}
			}
		} catch (Exception ex) {
			System.out.println(ex);
		} finally {
			DBConnectionUtil.closeConnection(rs, stat, con);
		}
	}

	public void currentStatus2() {
		int i = drawtb.getRowCount();

		Connection con = null;
		Statement stat = null;
		ResultSet rs = null;
		try {
			con = DBConnectionUtil.getConnection();
			for (int j = 0; j < i; j++) {
				String str1 = String.valueOf(drawmodel.getValueAt(j, 0));
				String str2 = String.valueOf(drawmodel.getValueAt(j, 7));

				stat = con.createStatement();
				rs = stat.executeQuery("select DRGSTATUS, DRGSTATUS1 FROM TIMESHEET WHERE PROJECT_NO='"
						+ tf1.getSelectedItem() + "' and drg_no='" + str1 + "' ");
				rs.getMetaData().getColumnCount();

				int m = rs.getRow();

				if (m < 1) {
					if (str2.length() > 1) {
						drawmodel.setValueAt(str2, j, 7);
					}
				}

				while (rs.next()) {
					String str3 = new String(rs.getString(1));
					String str4 = new String(rs.getString(2));

					if ((str3.length() > 1) && (str4.length() <= 1)) {
						if (str2.length() > 1) {
							str3 = str2;
						} else {
							drawmodel.setValueAt("Detail-C", j, 7);
						}
					}
					if ((str3.length() > 1) && (str4.length() > 1)) {
						drawmodel.setValueAt("Check-C", j, 7);
					}
				}
			}
		} catch (Exception localException) {
			System.out.println(localException);
		} finally {
			DBConnectionUtil.closeConnection(rs, stat, con);
		}
	}

	public void removeDrg() {
		int i = drawtb.getSelectedRow();
		int j = itemtb.getSelectedRow();

		String str1 = String.valueOf(itemmodel.getValueAt(j, 0));
		String str2 = String.valueOf(drawmodel.getValueAt(i, 0));

		Connection con = null;
		Statement stat = null;
		try {
			con = DBConnectionUtil.getConnection();
			stat = con.createStatement();
			int affected = stat.executeUpdate("delete from drawingno where project_no='" + tf1.getSelectedItem()
					+ "' and item_code='" + str1 + "' and drawing_no='" + str2 + "' ");
			if (affected > 0) {
				String str3 = String.valueOf(remainqty);
				String str4 = String.valueOf(sheetqty);

				affected = stat.executeUpdate(
						"update estimation set sheet_qty='" + str4 + "', remarks='Remaining', REMAINING='" + str3
								+ "' WHERE project_no='" + tf1.getSelectedItem() + "' and item_code='" + str1 + "' ");
				if (affected > 0) {
					drawmodel.removeRow(drawtb.getSelectedRow());
				}

			}
		} catch (Exception localException1) {
			JOptionPane.showMessageDialog(internalFrame, localException1.getMessage().toString());
		} finally {
			DBConnectionUtil.closeConnection(stat, con);
		}
	}

	public void actionPerformed(ActionEvent paramActionEvent) {
		if (paramActionEvent.getSource() == tf1) {

			itemmodel.setNumRows(0);
			showItem();
			showStatus();
			expertEmp();

			Connection con = null;
			PreparedStatement stat = null;
			ResultSet rs = null;
			try {
				con = DBConnectionUtil.getConnection();
				stat = con.prepareStatement(
						"select project_name, client_name, CO_CODE from PROJECT_CO where project_no=? ");
				stat.setInt(1, Integer.parseInt((String) tf1.getSelectedItem()));
				rs = stat.executeQuery();
				while (rs.next()) {
					String str1 = new String(rs.getString(1));
					String str2 = new String(rs.getString(2));
					String str3 = new String(rs.getString(3));
					tf2.setText(str1);
					tf3.setText(str2);
					tltf.setText(str3);
					itemtb.requestFocus();
				}
			} catch (Exception localException) {
				System.out.println(localException);
			} finally {
				DBConnectionUtil.closeConnection(rs, stat, con);
			}
			itemtb.selectAll();
		}

		if (paramActionEvent.getSource() == tf2) {
			tf3.requestFocus();
			tf3.selectAll();
		}
		if (paramActionEvent.getSource() == tf4) {
			empDetails();
		}

		if (paramActionEvent.getSource() == unittf) {
			expertEmp();
		}
		if (paramActionEvent.getSource() == showbut) {
			allotedDates();
			jobDetails();
		}
		if (paramActionEvent.getSource() == removebut) {
			px1();
			collectDate();
			dateWiseJob();
		}

		if (paramActionEvent.getSource() == closebut) {
			internalFrame.setVisible(false);
		}
		Object localObject2;
		if (paramActionEvent.getSource() == savebut) {
			int[] arrayOfInt = emptb.getSelectedRows();
			if ((arrayOfInt.length < 1) || (arrayOfInt.length > 1)) {
				JOptionPane.showMessageDialog(internalFrame, "Emp Code Not Selected Properlly");
			} else {
				Connection con = DBConnectionUtil.getConnection();
				Statement stat = null;
				int result = 0;
				SaveRecordThread saveThread = new SaveRecordThread(con, stat, result);
				saveThread.start();
				if (result == 1) {
					DBConnectionUtil.closeConnection(stat, con);
					currentStatus1();
					currentStatus2();
					jobDetails();
				}
			}
		}

		if (paramActionEvent.getSource() == popm) {
			int i = drawtb.getSelectedRow();
			localObject2 = String.valueOf(drawmodel.getValueAt(i, 7));
			System.out.println("Status : " + (String) localObject2);

			if (((String) localObject2).length() > 1) {
				JOptionPane.showMessageDialog(internalFrame, "Cannot Remove ");
			}
			if (((String) localObject2).length() <= 1) {
				removeDrg();
				showStatus();
			}
		}
		int[] localObject1;
		if (paramActionEvent.getSource() == clearpop) {
			localObject1 = drawtb.getSelectedRows();

			for (int k = 0; k < localObject1.length; k++) {
				drawmodel.removeRow(localObject1[k]);
			}
		}

		if (paramActionEvent.getSource() == ibut1) {
			removeJobs localObject21 = new removeJobs();
			((removeJobs) localObject21).start();
		}
		if (paramActionEvent.getSource() == ibut3) {
			skf.setVisible(false);
		}

		if (paramActionEvent.getSource() == popm1) {
			tblstr = "Detailing";
			deleteDTLCHK();
			skclmstr = "Detailing";
			makeFreshDrg();
			showDraw();
			currentStatus1();
			currentStatus2();
			jobDetails();
		}
		if (paramActionEvent.getSource() == popm2) {
			tblstr = "Checking";
			deleteDTLCHK();
			skclmstr = "Checking";
			makeFreshDrg();
			jobDetails();
		}
		if (paramActionEvent.getSource() == popm3) {
			tblstr = "Detailing";
			deleteDTLCHK();
			tblstr = "Checking";
			deleteDTLCHK();
			makeFreshDrgAll();
			showDraw();
			currentStatus1();
			currentStatus2();
			jobDetails();
		}
		if (paramActionEvent.getSource() == popm4) {
			removeForcefully();
		}
		if (paramActionEvent.getSource() == mi1) {
			internalFrame.setVisible(false);
		}
	}

	public void mousePressed(MouseEvent paramMouseEvent) {
		if (paramMouseEvent.getSource() == itemtb) {

			currentStatus1();
			currentStatus2();
		}
		if (paramMouseEvent.getSource() == emptb) {
			fromDate();
			empDetails();
			allotedTime();
			allotedDates();
			jobDetails();
			dayName();
			neededTime();
		}

		if (paramMouseEvent.getSource() == itb) {
			if (javax.swing.SwingUtilities.isRightMouseButton(paramMouseEvent)) {
				jpm.show((javax.swing.JComponent) paramMouseEvent.getSource(), paramMouseEvent.getX(),
						paramMouseEvent.getY());
			}
		}
		if (paramMouseEvent.getSource() == drawtb) {
			if (javax.swing.SwingUtilities.isRightMouseButton(paramMouseEvent)) {
				pop.show((javax.swing.JComponent) paramMouseEvent.getSource(), paramMouseEvent.getX(),
						paramMouseEvent.getY());
			}
			if (javax.swing.SwingUtilities.isLeftMouseButton(paramMouseEvent)) {
				alert();
			}
		}
	}

	public void mouseReleased(MouseEvent paramMouseEvent) {
		if (paramMouseEvent.getSource() == drawtb) {
			showDraw2();
			neededTime();
			allotedTime();
		}
		if (paramMouseEvent.getSource() == tb) {
			allotedTime();
		}
	}

	public void mouseClicked(MouseEvent paramMouseEvent) {
	}

	public void mouseEntered(MouseEvent paramMouseEvent) {
	}

	public void mouseExited(MouseEvent paramMouseEvent) {
	}

	public void keyPressed(java.awt.event.KeyEvent paramKeyEvent) {
		int i = paramKeyEvent.getKeyCode();
		if (paramKeyEvent.getSource() == itemtb) {
			if (i == 10) {
				unittf.requestFocus();
			}
			if (i == 116) {
				drawmodel.setNumRows(0);
				drawtb.requestFocus();
			}
			if (i == 112) {
				showDraw();
				currentStatus1();
				currentStatus2();
			}
		}
	}

	public void keyReleased(java.awt.event.KeyEvent paramKeyEvent) {
		if ((paramKeyEvent.getSource() != itemtb) ||

				(paramKeyEvent.getSource() == emptb)) {
		}
	}

	public void keyTyped(java.awt.event.KeyEvent paramKeyEvent) {
	}

	public void itemStateChanged(java.awt.event.ItemEvent paramItemEvent) {
		if (paramItemEvent.getSource() == icb) {
			dateWiseJob();
		}
	}
}
