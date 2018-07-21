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
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import com.sol.erp.constants.ApplicationConstants;
import com.sol.erp.dao.ProjectDAO;
import com.sol.erp.ui.custom.SolTableModel;
import com.sol.erp.util.DBConnectionUtil;
import com.sol.erp.util.UIUtil;
import com.sol.erp.util.formater.SolDateFormatter;

public class Estimation implements ActionListener, KeyListener, MouseListener, ItemListener, FocusListener {

	CreateDrawingNumbers sdn;
	
	Connection con;
	Statement stat;
	ResultSet rs;
	int affected;
	String[] columnNames;
	Object[][] data;
	String[] columnNames2;
	Object[][] data2;
	String[] columnNames1;
	Object[][] data1;
	DecimalFormat df;
	DecimalFormat df2;
	DefaultTableModel projectManagerEstimationDefaultTableModel;
	JTable projectManagerEstimationTable;
	JScrollPane projectManagerEstimationTableScrollPane;
	DefaultTableModel teamLeaderEstimationDetaultTableModel;
	JTable teamLeaderEstimationTable;
	JScrollPane teamLeaderEstimationTableScrollPane;
	DefaultTableModel itemDefaultTableModel;
	JTable itemTable;
	JScrollPane itemTableScrollPane;
	JSplitPane splitPane;
	JInternalFrame internalFrame;
	JDialog tableframe;
	JLabel labelEstimatedBy;
	JLabel labelProjectNumber;
	JLabel labelProjectName;
	JLabel labelClientName;
	JLabel labelSerialNumber;
	JLabel labelItemCode;
	JLabel labelItemName;
	JLabel labelItemQuantity;
	JLabel labelSheetSize;
	JLabel labelSheetQuantity;
	JLabel labelDetailingTime;
	JLabel labelCheckingTime;
	JLabel labelTLTime;
	JLabel labelPerSheetTime;
	JLabel labelTotalTime;
	JLabel labelComplexityLevel;
	JComboBox textFieldEstimatedBy;
	JComboBox textFieldProjectNumber;
	JTextField textFieldProjectName;
	JTextField textFieldClientName;
	JTextField textFieldSerialNumber;
	JComboBox comboBoxItemCode = new JComboBox();
	JTextField textFieldItemName = new JTextField();

	JTextField textFieldItemQuantity;
	DefaultComboBoxModel cbm;
	JComboBox textFieldSheetSize;
	JTextField textFieldSheetQuantity;
	JTextField textFieldDetailingTime;
	JTextField textFieldTLTime;
	JTextField textFieldCheckingTime;
	JTextField textFieldPerSheetTime;
	JTextField textFieldTotalTime;
	JTextField textFieldComplexityLevel;
	JTextField datetf;
	JRadioButton mpbut;
	JRadioButton showbut;
	ButtonGroup grp;
	JButton prjbut;
	JButton addbut;
	JButton calcbut;
	JButton updatebut;
	JButton nextbut;
	JButton removebut;
	JButton closebut;
	JPopupMenu jpm;
	JMenuItem popm1;
	JMenuItem popm2;
	TableColumn cola1;
	TableColumn colb1;
	JPanel northp1;
	JPanel northp2;
	JPanel northp3;
	JPanel butpanel1;
	JPanel butpanel2;
	JPanel centerpanel;
	JPanel northpanel;
	Date dat;
	SimpleDateFormat formatter;
	String dateString;
	Font fo;
	Border line;
	Border bor1;
	Border bor2;
	Toolkit tk;
	Dimension ss;
	Dimension fs;
	Estimation.ColoredTableCellRenderer skc;
	Estimation.ColoredTableCellRenderer1 skc1;
	Container c;
	Container c1;
	JInternalFrame f2;
	JPanel f2southpanel;
	JButton f2updatebut;
	String empstr;
	String tblstr;
	String remain;
	String oldsheet;
	int sumsl;
	int sumitemqty;
	int sumsheetqty;
	float sumdtltime;
	float sumchktime;
	float sumhrs;
	float complexity;

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
				setBackground(new Color(200, 220, 240));
			}
			setForeground(Color.white);

			if (paramInt1 % 2 != 0) {
				setBackground(new Color(250, 250, 250));
			}
			setForeground(Color.darkGray);

			if (paramInt2 < 2) {
				setHorizontalAlignment(2);
				String str = String.valueOf(projectManagerEstimationDefaultTableModel.getValueAt(paramInt1, 12));

				if (str.equalsIgnoreCase("1")) {
					setBackground(new Color(180, 120, 240));
					setForeground(Color.white);
				}
				if (str.equalsIgnoreCase("0")) {
					setBackground(new Color(220, 220, 220));
					setForeground(Color.darkGray);
				}
			}

			if (paramInt2 >= 2) {
				setHorizontalAlignment(4);
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

			if (paramInt1 % 2 == 0) {
				setBackground(new Color(200, 220, 240));
			}
			setForeground(Color.white);

			if (paramInt1 % 2 != 0) {
				setBackground(new Color(250, 250, 250));
			}
			setForeground(Color.darkGray);

			if (paramInt2 < 2) {
				setHorizontalAlignment(2);
				setBackground(new Color(180, 120, 240));
				setForeground(Color.white);
			}

			if (paramInt2 >= 2) {
				setHorizontalAlignment(4);
			}

			super.getTableCellRendererComponent(paramJTable, paramObject, paramBoolean1, paramBoolean2, paramInt1,
					paramInt2);

			return this;
		}
	}

	public void px() {
		c = internalFrame.getContentPane();
		c1 = tableframe.getContentPane();
		c.setLayout(new BorderLayout());
		c1.setLayout(new BorderLayout());

		c1.add(itemTableScrollPane, "Center");
		northp1.setLayout(new FlowLayout());
		northp2.setLayout(new java.awt.GridLayout(4, 6, 2, 2));
		northpanel.setLayout(new BorderLayout());
		butpanel1.setLayout(new FlowLayout());
		butpanel2.setLayout(new FlowLayout());
		centerpanel.setLayout(new BorderLayout());

		textFieldSheetSize.addItem("A1");
		textFieldSheetSize.addItem("A2");
		textFieldSheetSize.addItem("A3");
		textFieldSheetSize.addItem("A4");

		northpanel.setBorder(bor1);
		projectManagerEstimationTableScrollPane.setBorder(bor2);
		northp1.setBorder(line);

		northp1.add(labelEstimatedBy);
		northp1.add(textFieldEstimatedBy);
		textFieldEstimatedBy.setFont(fo);
		northp1.add(labelProjectNumber);
		northp1.add(textFieldProjectNumber);
		textFieldProjectNumber.setFont(fo);
		northp1.add(labelProjectName);
		northp1.add(textFieldProjectName);
		textFieldProjectName.setFont(fo);
		northp1.add(labelClientName);
		northp1.add(textFieldClientName);
		textFieldClientName.setFont(fo);
		northp1.add(prjbut);

		northp2.add(labelSerialNumber);
		northp2.add(textFieldSerialNumber);
		textFieldSerialNumber.setFont(fo);
		textFieldSerialNumber.setEditable(false);
		northp2.add(labelItemCode);
		northp2.add(comboBoxItemCode);
		comboBoxItemCode.setFont(fo);
		northp2.add(labelItemName);
		northp2.add(textFieldItemName);
		textFieldItemName.setFont(fo);
		textFieldItemName.setEditable(false);
		northp2.add(labelItemQuantity);
		northp2.add(textFieldItemQuantity);
		textFieldItemQuantity.setFont(fo);
		northp2.add(labelSheetSize);
		northp2.add(textFieldSheetSize);
		textFieldSheetSize.setFont(fo);
		northp2.add(labelSheetQuantity);
		northp2.add(textFieldSheetQuantity);
		textFieldSheetQuantity.setFont(fo);
		northp2.add(labelDetailingTime);
		northp2.add(textFieldDetailingTime);
		textFieldDetailingTime.setFont(fo);
		northp2.add(labelCheckingTime);
		northp2.add(textFieldCheckingTime);
		textFieldCheckingTime.setFont(fo);
		textFieldCheckingTime.setEditable(false);
		northp2.add(labelTLTime);
		northp2.add(textFieldTLTime);
		textFieldTLTime.setFont(fo);
		textFieldTLTime.setEditable(false);
		northp2.add(labelPerSheetTime);
		northp2.add(textFieldPerSheetTime);
		textFieldPerSheetTime.setFont(fo);
		northp2.add(labelTotalTime);
		northp2.add(textFieldTotalTime);
		textFieldTotalTime.setFont(fo);
		textFieldTotalTime.setEditable(false);
		northp2.add(labelComplexityLevel);
		northp2.add(textFieldComplexityLevel);
		textFieldComplexityLevel.setFont(fo);
		textFieldComplexityLevel.setEditable(false);

		textFieldCheckingTime.setBackground(new Color(220, 250, 220));
		textFieldTLTime.setBackground(new Color(220, 250, 220));
		textFieldTotalTime.setBackground(new Color(220, 250, 220));
		textFieldComplexityLevel.setBackground(new Color(220, 250, 220));

		grp.add(showbut);
		grp.add(mpbut);

		showbut.setForeground(new Color(100, 20, 130));
		mpbut.setForeground(new Color(20, 50, 250));

		butpanel1.add(datetf);
		datetf.setFont(fo);
		datetf.setEditable(false);

		butpanel1.add(mpbut);
		mpbut.setMnemonic(77);
		butpanel1.add(showbut);
		showbut.setMnemonic(83);

		butpanel1.add(addbut);
		addbut.setMnemonic(65);
		butpanel1.add(calcbut);
		calcbut.setMnemonic(76);
		butpanel1.add(updatebut);
		updatebut.setMnemonic(85);
		updatebut.setEnabled(true);
		butpanel1.add(nextbut);
		nextbut.setMnemonic(78);

		butpanel2.add(removebut);
		removebut.setMnemonic(82);
		butpanel2.add(closebut);
		closebut.setMnemonic(67);

		calcbut.setEnabled(true);
		calcbut.setForeground(Color.red);

		removebut.setEnabled(true);

		textFieldSerialNumber.setHorizontalAlignment(4);
		textFieldItemQuantity.setHorizontalAlignment(4);

		textFieldSheetQuantity.setHorizontalAlignment(4);
		textFieldDetailingTime.setHorizontalAlignment(4);
		textFieldTLTime.setHorizontalAlignment(4);
		textFieldCheckingTime.setHorizontalAlignment(4);
		textFieldPerSheetTime.setHorizontalAlignment(4);
		textFieldTotalTime.setHorizontalAlignment(4);
		textFieldComplexityLevel.setHorizontalAlignment(4);

		cola1.setPreferredWidth(180);
		colb1.setPreferredWidth(180);

		for (int i = 0; i < projectManagerEstimationTable.getColumnCount(); i++) {
			projectManagerEstimationTable.getColumnModel().getColumn(i).setCellRenderer(skc);
		}

		for (int i = 0; i < teamLeaderEstimationTable.getColumnCount(); i++) {
			teamLeaderEstimationTable.getColumnModel().getColumn(i).setCellRenderer(skc1);
		}

		SolTableModel.decorateTable(projectManagerEstimationTable);
		SolTableModel.decorateTable(teamLeaderEstimationTable);

		projectManagerEstimationTable.setFont(new Font("Tahoma", 1, 9));
		projectManagerEstimationTable.setIntercellSpacing(new Dimension(0, 1));
		projectManagerEstimationTable.setGridColor(Color.white);

		teamLeaderEstimationTable.setFont(new Font("Tahoma", 1, 9));
		teamLeaderEstimationTable.setIntercellSpacing(new Dimension(0, 1));
		teamLeaderEstimationTable.setGridColor(Color.white);

		jpm.add(popm1);
		jpm.addSeparator();
		jpm.add(popm2);

		projectManagerEstimationTable.removeColumn(projectManagerEstimationTable.getColumnModel().getColumn(12));

		itemTable.setSelectionBackground(new Color(60, 130, 130));
		itemTable.setSelectionForeground(Color.darkGray);
		itemTable.getTableHeader().setPreferredSize(new Dimension(50, 20));
		itemTable.getTableHeader().setFont(fo);
		itemTable.setFont(fo);
		itemTable.setRowHeight(18);

		splitPane.setOneTouchExpandable(true);
		splitPane.setDividerLocation(180);

		northpanel.add(northp1, "North");
		northpanel.add(northp2, "Center");

		centerpanel.add(butpanel1, "North");
		centerpanel.add(splitPane, "Center");
		centerpanel.add(butpanel2, "South");

		c.add(northpanel, "North");
		c.add(centerpanel, "Center");

		prjbut.addActionListener(this);
		mpbut.addActionListener(this);
		addbut.addActionListener(this);
		calcbut.addActionListener(this);
		showbut.addActionListener(this);
		updatebut.addActionListener(this);
		nextbut.addActionListener(this);
		removebut.addActionListener(this);
		closebut.addActionListener(this);

		textFieldProjectNumber.addActionListener(this);
		textFieldProjectNumber.addItemListener(this);
		textFieldProjectNumber.addFocusListener(this);
		textFieldProjectName.addActionListener(this);
		textFieldClientName.addActionListener(this);
		textFieldSerialNumber.addActionListener(this);
		comboBoxItemCode.addKeyListener(this);
		comboBoxItemCode.addItemListener(this);
		textFieldItemName.addActionListener(this);
		textFieldItemQuantity.addActionListener(this);
		textFieldSheetSize.addKeyListener(this);
		textFieldSheetQuantity.addActionListener(this);
		textFieldDetailingTime.addActionListener(this);
		textFieldCheckingTime.addActionListener(this);
		textFieldTLTime.addActionListener(this);
		textFieldPerSheetTime.addActionListener(this);
		textFieldTotalTime.addActionListener(this);
		textFieldComplexityLevel.addActionListener(this);
		itemTable.addKeyListener(this);
		projectManagerEstimationTable.addMouseListener(this);
		teamLeaderEstimationTable.addMouseListener(this);
		popm1.addActionListener(this);
		popm2.addActionListener(this);

		textFieldSheetQuantity.addFocusListener(this);

		//tableframe.setLocation((ss.width - fs.width) / 3, (ss.height - fs.height) / 6);
		tableframe.setLocationRelativeTo(null);
		tableframe.setUndecorated(true);

		internalFrame.setSize(1024, 600);
		internalFrame.setVisible(true);
		UIUtil.centreToApplication(internalFrame);
		projectManagerEstimationTable.removeColumn(projectManagerEstimationTable.getColumnModel().getColumn(0));
		teamLeaderEstimationTable.removeColumn(teamLeaderEstimationTable.getColumnModel().getColumn(0));

		tableframe.setSize(300, 300);
		structIndex();
		textFieldProjectName.requestFocus();
	}

	public void px2() {
		Container localContainer = f2.getContentPane();
		localContainer.setLayout(new BorderLayout());
		f2southpanel.setLayout(new FlowLayout(2));

		f2southpanel.add(f2updatebut);

		localContainer.add(sdn.getCenterPanel(), "Center");
		localContainer.add(f2southpanel, "South");

		f2updatebut.addActionListener(this);

		TechMainFrame.desktop.add(f2);
		TechMainFrame.desktop.moveToFront(f2);

		f2.setSize(800, 400);
		f2.setVisible(true);
	}

	public void paramUser() {
		String str = new String(TechMainFrame.textFieldPost.getText());
		empstr = TechMainFrame.textFieldLoggedBy.getText();

		if (str.equalsIgnoreCase("Team Leader")) {
			textFieldProjectNumber.setEditable(false);
			textFieldEstimatedBy.addItem("Team Leader");
			projectList();
		} else {
			textFieldProjectNumber.setEditable(true);
			textFieldEstimatedBy.addItem("Project Manager");
			qtaNo();
		}

		if (String.valueOf(textFieldEstimatedBy.getSelectedItem()).equalsIgnoreCase("Project Manager")) {
			showbut.setEnabled(false);
		}
	}

	public void qtaNo() {
		try {
			con = DBConnectionUtil.getConnection();
			stat = con.createStatement();
			rs = stat.executeQuery("select max(quote_No) from quotation_list");

			while (rs.next()) {
				String str = new String(rs.getString(1));
				int i = Integer.parseInt(str);
				textFieldProjectNumber.addItem(String.valueOf(i + 1));
			}
		} catch (Exception localException) {
			System.out.println(localException);
		}
	}

	public void projectList() {
		textFieldProjectNumber.removeAllItems();
		try {
			con = DBConnectionUtil.getConnection();
			stat = con.createStatement();
			rs = stat.executeQuery("select project_no from project_co where co_code='" + empstr + "' ");

			while (rs.next()) {
				String str = new String(rs.getString(1));
				textFieldProjectNumber.addItem(String.valueOf(str));
			}
		} catch (Exception localException) {
			System.out.println(localException);
		}
	}

	public void projectName() {
		try {
			con = DBConnectionUtil.getConnection();
			stat = con.createStatement();
			rs = stat.executeQuery("select project_name, client_name from Estimation_MP where project_no='"
					+ textFieldProjectNumber.getSelectedItem() + "'  ");

			while (rs.next()) {
				String str1 = new String(rs.getString(1));
				String str2 = new String(rs.getString(2));

				textFieldProjectName.setText(str1);
				textFieldClientName.setText(str2);
			}
		} catch (Exception localException) {
			System.out.println(localException);
		}
	}

	public void structIndex() {
		try {
			con = DBConnectionUtil.getConnection();
			stat = con.createStatement();
			rs = stat.executeQuery("select * from ItemCode order by item_code");
			rs.getMetaData().getColumnCount();
			while (rs.next()) {
				String str1 = new String(rs.getString(1));
				String str2 = new String(rs.getString(2));

				itemDefaultTableModel.addRow(new Object[] { str1, str2 });
				comboBoxItemCode.addItem(str1);
			}
			itemDefaultTableModel.addRow(new Object[] { "", "" });
		} catch (Exception localException) {
			System.out.println(localException);
		}
	}

	public void showSelectedRecord() {
		int i = 0;

		if (mpbut.isSelected()) {
			i = projectManagerEstimationTable.getSelectedRow();
			textFieldSerialNumber.setText(String.valueOf(projectManagerEstimationDefaultTableModel.getValueAt(i, 0)));
			comboBoxItemCode.setSelectedItem(String.valueOf(projectManagerEstimationDefaultTableModel.getValueAt(i, 1)));
			textFieldItemName.setText(String.valueOf(projectManagerEstimationDefaultTableModel.getValueAt(i, 2)));
			textFieldItemQuantity.setText(String.valueOf(projectManagerEstimationDefaultTableModel.getValueAt(i, 3)));
			textFieldSheetSize.setSelectedItem(String.valueOf(projectManagerEstimationDefaultTableModel.getValueAt(i, 4)));
			textFieldSheetQuantity.setText(String.valueOf(projectManagerEstimationDefaultTableModel.getValueAt(i, 5)));
			textFieldDetailingTime.setText(String.valueOf(projectManagerEstimationDefaultTableModel.getValueAt(i, 6)));
			textFieldCheckingTime.setText(String.valueOf(projectManagerEstimationDefaultTableModel.getValueAt(i, 7)));
			textFieldTLTime.setText(String.valueOf(projectManagerEstimationDefaultTableModel.getValueAt(i, 8)));
			textFieldPerSheetTime.setText(String.valueOf(projectManagerEstimationDefaultTableModel.getValueAt(i, 9)));
			textFieldTotalTime.setText(String.valueOf(projectManagerEstimationDefaultTableModel.getValueAt(i, 10)));
			textFieldComplexityLevel.setText(String.valueOf(projectManagerEstimationDefaultTableModel.getValueAt(i, 11)));
		}
		if (showbut.isSelected()) {
			i = teamLeaderEstimationTable.getSelectedRow();
			textFieldSerialNumber.setText(String.valueOf(teamLeaderEstimationDetaultTableModel.getValueAt(i, 0)));
			comboBoxItemCode.setSelectedItem(String.valueOf(teamLeaderEstimationDetaultTableModel.getValueAt(i, 1)));
			textFieldItemName.setText(String.valueOf(teamLeaderEstimationDetaultTableModel.getValueAt(i, 2)));
			textFieldItemQuantity.setText(String.valueOf(teamLeaderEstimationDetaultTableModel.getValueAt(i, 3)));
			textFieldSheetSize.setSelectedItem(String.valueOf(teamLeaderEstimationDetaultTableModel.getValueAt(i, 4)));
			textFieldSheetQuantity.setText(String.valueOf(teamLeaderEstimationDetaultTableModel.getValueAt(i, 5)));
			textFieldDetailingTime.setText(String.valueOf(teamLeaderEstimationDetaultTableModel.getValueAt(i, 6)));
			textFieldCheckingTime.setText(String.valueOf(teamLeaderEstimationDetaultTableModel.getValueAt(i, 7)));
			textFieldTLTime.setText(String.valueOf(teamLeaderEstimationDetaultTableModel.getValueAt(i, 8)));
			textFieldPerSheetTime.setText(String.valueOf(teamLeaderEstimationDetaultTableModel.getValueAt(i, 9)));
			textFieldTotalTime.setText(String.valueOf(teamLeaderEstimationDetaultTableModel.getValueAt(i, 10)));
			textFieldComplexityLevel.setText(String.valueOf(teamLeaderEstimationDetaultTableModel.getValueAt(i, 11)));
		}
	}

	public void showAll() {
		projectManagerEstimationDefaultTableModel.setNumRows(0);
		teamLeaderEstimationDetaultTableModel.setNumRows(0);
		try {
			con = DBConnectionUtil.getConnection();
			stat = con.createStatement();
			rs = stat.executeQuery("select * from " + tblstr + " where project_no ='" + textFieldProjectNumber.getSelectedItem() + "' ");
			while (rs.next()) {
				String str1 = new String(rs.getString(1));
				String str2 = new String(rs.getString(2));
				String str3 = new String(rs.getString(3));
				String str4 = new String(rs.getString(4));
				String str5 = new String(rs.getString(5));
				String str6 = new String(rs.getString(6));
				String str7 = new String(rs.getString(7));
				String str8 = new String(rs.getString(8));
				String str9 = new String(rs.getString(9));
				String str10 = new String(rs.getString(10));
				String str11 = new String(rs.getString(11));
				String str12 = new String(rs.getString(12));
				String str13 = new String(rs.getString(13));
				String str14 = new String(rs.getString(14));
				String str15 = new String(rs.getString(15));
				String str16 = new String(rs.getString(18));

				textFieldProjectNumber.setSelectedItem(str1);
				textFieldProjectName.setText(str2);
				textFieldClientName.setText(str3);

				if ((String.valueOf(textFieldEstimatedBy.getSelectedItem()).equalsIgnoreCase("Project Manager"))
						&& (mpbut.isSelected())) {
					projectManagerEstimationDefaultTableModel.addRow(new Object[] { str4, str5, str6, str7, str8, str9, str10, str11, str12, str13, str14,
							str15, str16 });
				}
				if ((String.valueOf(textFieldEstimatedBy.getSelectedItem()).equalsIgnoreCase("Team Leader")) && (showbut.isSelected())) {
					teamLeaderEstimationDetaultTableModel.addRow(new Object[] { str4, str5, str6, str7, str8, str9, str10, str11, str12, str13, str14,
							str15 });
				}
				if ((String.valueOf(textFieldEstimatedBy.getSelectedItem()).equalsIgnoreCase("Team Leader")) && (mpbut.isSelected())) {
					projectManagerEstimationDefaultTableModel.addRow(new Object[] { str4, str5, str6, str7, str8, str9, str10, str11, str12, str13, str14,
							str15, str16 });
				}
			}
		} catch (Exception localException) {
			System.out.println(localException);
		}
	}

	public void showRemain() {
		int i;
		String str;
		if (String.valueOf(textFieldEstimatedBy.getSelectedItem()).equalsIgnoreCase("Project Manager")) {
			i = projectManagerEstimationTable.getSelectedRow();
			str = String.valueOf(projectManagerEstimationDefaultTableModel.getValueAt(i, 1));

			try {
				con = DBConnectionUtil.getConnection();
				stat = con.createStatement();
				rs = stat.executeQuery("select remaining,Sheet_Qty from estimation_mp where project_no ='"
						+ textFieldProjectNumber.getSelectedItem() + "' and item_code='" + str + "'");
				while (rs.next()) {
					remain = new String(rs.getString(1));
					oldsheet = new String(rs.getString(2));
				}
			} catch (Exception localException1) {
				System.out.println(localException1);
			}
		}
		if (String.valueOf(textFieldEstimatedBy.getSelectedItem()).equalsIgnoreCase("Team Leader")) {
			i = teamLeaderEstimationTable.getSelectedRow();
			str = String.valueOf(teamLeaderEstimationDetaultTableModel.getValueAt(i, 1));

			try {
				con = DBConnectionUtil.getConnection();
				stat = con.createStatement();
				rs = stat.executeQuery("select remaining,Sheet_Qty from estimation where project_no ='"
						+ textFieldProjectNumber.getSelectedItem() + "' and item_code='" + str + "'");
				while (rs.next()) {
					remain = new String(rs.getString(1));
					oldsheet = new String(rs.getString(2));
				}
			} catch (Exception localException2) {
				System.out.println(localException2);
			}
		}
	}

	public void updateProjectName() {
		try {
			con = DBConnectionUtil.getConnection();
			stat = con.createStatement();
			affected = stat.executeUpdate("UPDATE ESTIMATION SET PROJECT_NAME = '" + textFieldProjectName.getText()
					+ "', CLIENT_NAME = '" + textFieldClientName.getText() + "' where project_no='" + textFieldProjectNumber.getSelectedItem() + "' ");
			affected = stat.executeUpdate("UPDATE ESTIMATION_MP SET PROJECT_NAME = '" + textFieldProjectName.getText()
					+ "', CLIENT_NAME = '" + textFieldClientName.getText() + "' where project_no='" + textFieldProjectNumber.getSelectedItem() + "' ");
			affected = stat.executeUpdate("UPDATE OLD_TL_ESTIMATION SET PROJECT_NAME = '" + textFieldProjectName.getText()
					+ "', CLIENT_NAME = '" + textFieldClientName.getText() + "' where project_no='" + textFieldProjectNumber.getSelectedItem() + "' ");
			affected = stat.executeUpdate("UPDATE PROJECT_CO SET PROJECT_NAME = '" + textFieldProjectName.getText()
					+ "', CLIENT_NAME = '" + textFieldClientName.getText() + "' where project_no='" + textFieldProjectNumber.getSelectedItem() + "' ");
			affected = stat.executeUpdate("UPDATE QUOTATION_LIST SET PROJECT_NAME = '" + textFieldProjectName.getText()
					+ "', CLIENT_NAME = '" + textFieldClientName.getText() + "' where project_no='" + textFieldProjectNumber.getSelectedItem() + "' ");
			JOptionPane.showMessageDialog(internalFrame, "Project/Client Name Updated. OK!");
		} catch (Exception localException) {
			System.out.println(localException);
			JOptionPane.showMessageDialog(internalFrame, localException.getMessage().toString());
		}
	}

	public void changeTimeOfSheet() {
		try {
			con = DBConnectionUtil.getConnection();
			stat = con.createStatement();
			affected = stat.executeUpdate("UPDATE ESTIMATION SET dtl_time='" + textFieldDetailingTime.getText() + "',chk_time='"
					+ textFieldCheckingTime.getText() + "',tl_time='" + textFieldTLTime.getText() + "', persheet_time='" + textFieldPerSheetTime.getText()
					+ "',total_time='" + textFieldTotalTime.getText() + "', complexity='" + textFieldComplexityLevel.getText() + "' where project_no='"
					+ textFieldProjectNumber.getSelectedItem() + "' and item_code='" + String.valueOf(comboBoxItemCode.getSelectedItem()) + "'  ");

			if (affected > 0) {
				stat.executeUpdate("update DRAWINGNO set DTL_TIME ='" + textFieldDetailingTime.getText() + "', CHK_TIME ='"
						+ textFieldCheckingTime.getText() + "', TOTAL_TIME ='" + textFieldPerSheetTime.getText() + "' where project_no='"
						+ textFieldProjectNumber.getSelectedItem() + "' and item_code='" + String.valueOf(comboBoxItemCode.getSelectedItem()) + "'  ");
				if (showbut.isSelected()) {
					int i = teamLeaderEstimationTable.getSelectedRow();
					teamLeaderEstimationDetaultTableModel.setValueAt(String.valueOf(comboBoxItemCode.getSelectedItem()), i, 1);
					teamLeaderEstimationDetaultTableModel.setValueAt(textFieldItemName.getText(), i, 2);
					teamLeaderEstimationDetaultTableModel.setValueAt(textFieldItemQuantity.getText(), i, 3);
					teamLeaderEstimationDetaultTableModel.setValueAt(textFieldSheetSize.getSelectedItem(), i, 4);
					teamLeaderEstimationDetaultTableModel.setValueAt(textFieldSheetQuantity.getText(), i, 5);
					teamLeaderEstimationDetaultTableModel.setValueAt(textFieldDetailingTime.getText(), i, 6);
					teamLeaderEstimationDetaultTableModel.setValueAt(textFieldCheckingTime.getText(), i, 7);
					teamLeaderEstimationDetaultTableModel.setValueAt(textFieldTLTime.getText(), i, 8);
					teamLeaderEstimationDetaultTableModel.setValueAt(textFieldPerSheetTime.getText(), i, 9);
					teamLeaderEstimationDetaultTableModel.setValueAt(textFieldTotalTime.getText(), i, 10);
					teamLeaderEstimationDetaultTableModel.setValueAt(textFieldPerSheetTime.getText(), i, 11);
					JOptionPane.showMessageDialog(internalFrame, "Time Updated.");
				}
			}
		} catch (Exception localException) {
			System.out.println(localException);
			JOptionPane.showMessageDialog(internalFrame, localException.getMessage().toString());
		}
	}

	public void updateTLEstimation() {
		showRemain();

		int i = Integer.parseInt(remain);
		int j = Integer.parseInt(oldsheet);
		int k = Integer.parseInt(textFieldSheetQuantity.getText());

		int m = 0;

		if (j < k) {
			m = k - j;
			i += m;
		}
		if (j > k) {
			m = j - k;
			if (i == 0) {
				i -= 0;
			}

			if (i > 0) {
				i -= m;
			}
		}

		try {
			con = DBConnectionUtil.getConnection();
			stat = con.createStatement();
			affected = stat.executeUpdate("UPDATE ESTIMATION SET ITEM_QTY='" + textFieldItemQuantity.getText() + "', item_name='"
					+ textFieldItemName.getText() + "', sheet_size='" + textFieldSheetSize.getSelectedItem() + "', sheet_qty='" + textFieldSheetQuantity.getText()
					+ "',dtl_time='" + textFieldDetailingTime.getText() + "',chk_time='" + textFieldCheckingTime.getText() + "',tl_time='"
					+ textFieldTLTime.getText() + "', persheet_time='" + textFieldPerSheetTime.getText() + "',total_time='" + textFieldTotalTime.getText()
					+ "', complexity='" + textFieldComplexityLevel.getText() + "',remarks='Remaining', REMAINING='" + i
					+ "' where project_no='" + textFieldProjectNumber.getSelectedItem() + "' and item_code='"
					+ String.valueOf(comboBoxItemCode.getSelectedItem()) + "'  ");

			if (affected > 0) {
				stat.executeUpdate("update DRAWINGNO set DTL_TIME ='" + textFieldDetailingTime.getText() + "', CHK_TIME ='"
						+ textFieldCheckingTime.getText() + "', TOTAL_TIME ='" + textFieldPerSheetTime.getText() + "' where project_no='"
						+ textFieldProjectNumber.getSelectedItem() + "' and item_code='" + String.valueOf(comboBoxItemCode.getSelectedItem()) + "'  ");

				if (showbut.isSelected()) {
					int n = teamLeaderEstimationTable.getSelectedRow();
					teamLeaderEstimationDetaultTableModel.setValueAt(String.valueOf(comboBoxItemCode.getSelectedItem()), n, 1);
					teamLeaderEstimationDetaultTableModel.setValueAt(textFieldItemName.getText(), n, 2);
					teamLeaderEstimationDetaultTableModel.setValueAt(textFieldItemQuantity.getText(), n, 3);
					teamLeaderEstimationDetaultTableModel.setValueAt(textFieldSheetSize.getSelectedItem(), n, 4);
					teamLeaderEstimationDetaultTableModel.setValueAt(textFieldSheetQuantity.getText(), n, 5);
					teamLeaderEstimationDetaultTableModel.setValueAt(textFieldDetailingTime.getText(), n, 6);
					teamLeaderEstimationDetaultTableModel.setValueAt(textFieldCheckingTime.getText(), n, 7);
					teamLeaderEstimationDetaultTableModel.setValueAt(textFieldTLTime.getText(), n, 8);
					teamLeaderEstimationDetaultTableModel.setValueAt(textFieldPerSheetTime.getText(), n, 9);
					teamLeaderEstimationDetaultTableModel.setValueAt(textFieldTotalTime.getText(), n, 10);
					teamLeaderEstimationDetaultTableModel.setValueAt(textFieldPerSheetTime.getText(), n, 11);
					JOptionPane.showMessageDialog(internalFrame, "Estimation Updated.");
				}
			}
		} catch (Exception localException) {
			System.out.println(localException);
			JOptionPane.showMessageDialog(internalFrame, localException.getMessage().toString());
		}
	}

	public void updateMPEstimation() {
		showRemain();

		int i = Integer.parseInt(remain);
		int j = Integer.parseInt(oldsheet);
		int k = Integer.parseInt(textFieldSheetQuantity.getText());

		int m = 0;

		if (j < k) {
			m = k - j;
			i += m;
		}
		if (j > k) {
			m = j - k;
			if (i == 0) {
				i -= 0;
			}

			if (i > 0) {
				i -= m;
			}
		}
		try {
			con = DBConnectionUtil.getConnection();
			stat = con.createStatement();
			affected = stat.executeUpdate("UPDATE ESTIMATION_MP SET ITEM_QTY='" + textFieldItemQuantity.getText() + "', item_name='"
					+ textFieldItemName.getText() + "', sheet_size='" + textFieldSheetSize.getSelectedItem() + "', sheet_qty='" + textFieldSheetQuantity.getText()
					+ "',dtl_time='" + textFieldDetailingTime.getText() + "',chk_time='" + textFieldCheckingTime.getText() + "',persheet_time='"
					+ textFieldPerSheetTime.getText() + "',total_time='" + textFieldTotalTime.getText() + "', complexity='" + textFieldComplexityLevel.getText()
					+ "',remarks='Remaining', REMAINING='" + i + "' where project_no='" + textFieldProjectNumber.getSelectedItem()
					+ "' and item_code='" + String.valueOf(comboBoxItemCode.getSelectedItem()) + "'  ");

			if (affected > 0) {
				if (mpbut.isSelected()) {
					int n = projectManagerEstimationTable.getSelectedRow();
					projectManagerEstimationDefaultTableModel.setValueAt(String.valueOf(comboBoxItemCode.getSelectedItem()), n, 1);
					projectManagerEstimationDefaultTableModel.setValueAt(textFieldItemName.getText(), n, 2);
					projectManagerEstimationDefaultTableModel.setValueAt(textFieldItemQuantity.getText(), n, 3);
					projectManagerEstimationDefaultTableModel.setValueAt(textFieldSheetSize.getSelectedItem(), n, 4);
					projectManagerEstimationDefaultTableModel.setValueAt(textFieldSheetQuantity.getText(), n, 5);
					projectManagerEstimationDefaultTableModel.setValueAt(textFieldDetailingTime.getText(), n, 6);
					projectManagerEstimationDefaultTableModel.setValueAt(textFieldCheckingTime.getText(), n, 7);
					projectManagerEstimationDefaultTableModel.setValueAt(textFieldPerSheetTime.getText(), n, 9);
					projectManagerEstimationDefaultTableModel.setValueAt(textFieldTotalTime.getText(), n, 10);
					projectManagerEstimationDefaultTableModel.setValueAt(textFieldPerSheetTime.getText(), n, 11);

					JOptionPane.showMessageDialog(internalFrame, "Estimation Updated.");
				}
			}
		} catch (Exception localException) {
			System.out.println(localException);
			JOptionPane.showMessageDialog(internalFrame, localException.getMessage().toString());
		}
	}

	public void updateRecord() {
		showRemain();

		Integer.parseInt(remain);
		int j = Integer.parseInt(oldsheet);
		int k = Integer.parseInt(textFieldSheetQuantity.getText());
		int i1 = 0;

		try {
			con = DBConnectionUtil.getConnection();
			stat = con.createStatement();
			rs = stat.executeQuery("select approve_status from timesheet where project_no='" + textFieldProjectNumber.getSelectedItem()
					+ "' and item_code='" + String.valueOf(comboBoxItemCode.getSelectedItem()) + "' ");

			while (rs.next()) {
				i1 = Integer.parseInt(rs.getString(1));

				if (i1 > 0) {
					i1 = 1;
				}
			}
		} catch (Exception localException) {
			System.out.println(localException);
		}

		if (k > j) {
			if (String.valueOf(textFieldEstimatedBy.getSelectedItem()).equalsIgnoreCase("Project Manager")) {

				int i2 = projectManagerEstimationTable.getSelectedRow();
				String str = String.valueOf(projectManagerEstimationDefaultTableModel.getValueAt(i2, 12));

				if (str.equalsIgnoreCase("1")) {
					JOptionPane.showMessageDialog(internalFrame, "Item Is Freezed. Cannot Update ");
				} else {
					updateMPEstimation();
				}
			}

			if (String.valueOf(textFieldEstimatedBy.getSelectedItem()).equalsIgnoreCase("Team Leader")) {
				updateTLEstimation();
			}
		}

		if (k < j) {

			px2();
			sdn.paramUser();
			sdn.itemList(String.valueOf(String.valueOf(comboBoxItemCode.getSelectedItem())));
			sdn.comboBoxProjectNumber.setSelectedItem(textFieldProjectNumber.getSelectedItem());
			sdn.comboBoxProjectNumber.setEnabled(false);
		}
	}

	public void perSheetTime() {
		float f1 = Float.parseFloat(String.valueOf(textFieldDetailingTime.getText()));
		String str1 = String.valueOf(df.format(f1));
		int i = SolDateFormatter.convertHrsToMinute(str1);

		int j = i / 60 * 100;

		String str2 = SolDateFormatter.convertMinuteToHRS(j);

		textFieldPerSheetTime.setText(str2);
		textFieldCheckingTime.setText(SolDateFormatter.getPercentTime(textFieldPerSheetTime.getText(), 30));
		textFieldTLTime.setText(SolDateFormatter.getPercentTime(textFieldPerSheetTime.getText(), 10));
		textFieldDetailingTime.setText(SolDateFormatter.getPercentTime(textFieldPerSheetTime.getText(), 60));

		textFieldPerSheetTime.requestFocus();
		textFieldPerSheetTime.selectAll();

		totalSheetTime();
		textFieldComplexityLevel.setText(textFieldPerSheetTime.getText());
	}

	public void splitTime() {
		float f1 = Float.parseFloat(textFieldPerSheetTime.getText());
		textFieldPerSheetTime.setText(df.format(f1));

		textFieldDetailingTime.setText(SolDateFormatter.getPercentTime(textFieldPerSheetTime.getText(), 60));
		textFieldDetailingTime.setText(df.format(Float.parseFloat(textFieldDetailingTime.getText())));

		textFieldCheckingTime.setText(SolDateFormatter.getPercentTime(textFieldPerSheetTime.getText(), 30));
		textFieldCheckingTime.setText(df.format(Float.parseFloat(textFieldCheckingTime.getText())));

		textFieldTLTime.setText(SolDateFormatter.getPercentTime(textFieldPerSheetTime.getText(), 10));
		textFieldTLTime.setText(df.format(Float.parseFloat(textFieldTLTime.getText())));
	}

	public void totalSheetTime() {
		float f1 = new Float(textFieldPerSheetTime.getText()).floatValue();
		new Float(textFieldSheetQuantity.getText()).floatValue();

		float f4 = Float.parseFloat(SolDateFormatter.getTimeMultipliedBy(String.valueOf(f1), Integer.parseInt(textFieldSheetQuantity.getText())));

		textFieldTotalTime.setText(String.valueOf(df.format(f4)));

		int i = SolDateFormatter.convertHrsToMinute(textFieldTotalTime.getText());
		String str = SolDateFormatter.convertMinuteToHRS(i);
		textFieldTotalTime.setText(str);
	}

	public void calculateTotal(JTable paramJTable, DefaultTableModel paramDefaultTableModel) {
		int i = paramDefaultTableModel.getRowCount();
		float f1 = 0.0F;
		int j = 0;
		int k = 0;
		float f3 = 0.0F;
		float f4 = 0.0F;
		float f5 = 0.0F;
		float f6 = 0.0F;
		float f8 = 0.0F;
		float f9 = 0.0F;
		float f10 = 0.0F;
		float f11 = 0.0F;

		int m = 0;
		int n = 0;
		int i1 = 0;
		int i2 = 0;

		for (int i3 = 0; i3 < i; i3++) {
			f1 += Float.parseFloat(String.valueOf(paramDefaultTableModel.getValueAt(i3, 3)));
			j += Integer.parseInt(String.valueOf(paramDefaultTableModel.getValueAt(i3, 5)));
			k = Integer.parseInt(String.valueOf(paramDefaultTableModel.getValueAt(i3, 5)));
			f3 += Float.parseFloat(String.valueOf(paramDefaultTableModel.getValueAt(i3, 6)));
			f4 += Float.parseFloat(String.valueOf(paramDefaultTableModel.getValueAt(i3, 7)));
			f5 += Float.parseFloat(String.valueOf(paramDefaultTableModel.getValueAt(i3, 8)));
			f6 += Float.parseFloat(String.valueOf(paramDefaultTableModel.getValueAt(i3, 10)));
			Float.parseFloat(String.valueOf(paramDefaultTableModel.getValueAt(i3, 11)));

			f8 += k * f3;
			f9 += k * f4;
			f10 += k * f5;
			f11 += f6;

			m = SolDateFormatter.convertHrsToMinute(String.valueOf(f8));
			n = SolDateFormatter.convertHrsToMinute(String.valueOf(f9));
			i1 = SolDateFormatter.convertHrsToMinute(String.valueOf(f10));
			i2 = SolDateFormatter.convertHrsToMinute(String.valueOf(f11));

			k = 0;
			f3 = 0.0F;
			f4 = 0.0F;
			f5 = 0.0F;
		}
		paramDefaultTableModel
				.addRow(new Object[] { "", "", "", String.valueOf(f1), "", String.valueOf(j), SolDateFormatter.convertMinuteToHRS(m),
				        SolDateFormatter.convertMinuteToHRS(n), SolDateFormatter.convertMinuteToHRS(i1), "", SolDateFormatter.convertMinuteToHRS(i2), "" });
	}

	public int drgInUse() {
		int i = 0;

		try {
			con = DBConnectionUtil.getConnection();
			stat = con.createStatement();
			rs = stat.executeQuery("select count(item_code) from DRAWINGNO where PROJECT_NO='" + textFieldProjectNumber.getSelectedItem()
					+ "' and item_code='" + String.valueOf(comboBoxItemCode.getSelectedItem()) + "' and status like 'Alloted'  ");

			while (rs.next()) {
				i = rs.getInt(1);
			}
		} catch (Exception localException) {
			System.out.println(localException);
		}
		return i;
	}

	public void removeRecord() {
		String str1 = (String) textFieldEstimatedBy.getSelectedItem();
		int i = drgInUse();
		if (i > 0) {
			JOptionPane.showMessageDialog(internalFrame, "Some of Drawing are in Use of Item");

		} else {
			if (str1.equalsIgnoreCase("Team Leader")) {
				try {
					con = DBConnectionUtil.getConnection();
					stat = con.createStatement();
					affected = stat.executeUpdate("delete from estimation where project_no='" + textFieldProjectNumber.getSelectedItem()
							+ "' and item_code='" + String.valueOf(comboBoxItemCode.getSelectedItem())
							+ "' and item_code not in(select item_code from DRAWINGNO where PROJECT_NO='"
							+ textFieldProjectNumber.getSelectedItem() + "' and item_code='" + String.valueOf(comboBoxItemCode.getSelectedItem())
							+ "' and status like 'Alloted')  ");

					if (affected > 0) {
						JOptionPane.showMessageDialog(internalFrame, "Item Deleted.");
						teamLeaderEstimationDetaultTableModel.removeRow(teamLeaderEstimationTable.getSelectedRow());
						sumsl -= 1;
					} else {
						JOptionPane.showMessageDialog(internalFrame, "Some of Drawing are in Use of Item");
					}
				} catch (Exception localException1) {
					System.out.println(localException1);
					JOptionPane.showMessageDialog(internalFrame, localException1.getMessage().toString());
				}
			}

			if (str1.equalsIgnoreCase("Project Manager")) {
				try {
					con = DBConnectionUtil.getConnection();
					stat = con.createStatement();
					affected = stat.executeUpdate("delete from ESTIMATION_MP where project_no='" + textFieldProjectNumber.getSelectedItem()
							+ "' and item_code='" + String.valueOf(comboBoxItemCode.getSelectedItem())
							+ "'  and item_code not in(select item_code from DRAWINGNO where project_no='"
							+ textFieldProjectNumber.getSelectedItem() + "'and item_code='" + String.valueOf(comboBoxItemCode.getSelectedItem())
							+ "' and status like 'Alloted')  ");

					if (affected > 0) {
						JOptionPane.showMessageDialog(internalFrame, "Item Deleted.");
						projectManagerEstimationDefaultTableModel.removeRow(projectManagerEstimationTable.getSelectedRow());
						sumsl -= 1;
					} else {
						JOptionPane.showMessageDialog(internalFrame, "Some of Drawing are in Use of Item");
					}
				} catch (Exception localException2) {
					System.out.println(localException2);
					JOptionPane.showMessageDialog(internalFrame, localException2.getMessage().toString());
				}
			}
		}
	}

	public void dynamicSave() {
		int[] arrayOfInt = projectManagerEstimationTable.getSelectedRows();

		for (int i = 0; i < arrayOfInt.length; i++) {
			String str1 = String.valueOf(textFieldProjectNumber.getSelectedItem());
			String str2 = textFieldProjectName.getText();
			String str3 = textFieldClientName.getText();
			String str4 = String.valueOf(projectManagerEstimationDefaultTableModel.getValueAt(arrayOfInt[i], 0));
			String str5 = String.valueOf(projectManagerEstimationDefaultTableModel.getValueAt(arrayOfInt[i], 1));
			String str6 = String.valueOf(projectManagerEstimationDefaultTableModel.getValueAt(arrayOfInt[i], 2));
			String str7 = String.valueOf(projectManagerEstimationDefaultTableModel.getValueAt(arrayOfInt[i], 3));
			String str8 = String.valueOf(projectManagerEstimationDefaultTableModel.getValueAt(arrayOfInt[i], 4));
			String str9 = String.valueOf(projectManagerEstimationDefaultTableModel.getValueAt(arrayOfInt[i], 5));
			String str10 = String.valueOf(projectManagerEstimationDefaultTableModel.getValueAt(arrayOfInt[i], 6));
			String str11 = String.valueOf(projectManagerEstimationDefaultTableModel.getValueAt(arrayOfInt[i], 7));
			String str12 = String.valueOf(projectManagerEstimationDefaultTableModel.getValueAt(arrayOfInt[i], 8));
			String str13 = String.valueOf(projectManagerEstimationDefaultTableModel.getValueAt(arrayOfInt[i], 9));
			String str14 = String.valueOf(projectManagerEstimationDefaultTableModel.getValueAt(arrayOfInt[i], 10));
			String str15 = String.valueOf(projectManagerEstimationDefaultTableModel.getValueAt(arrayOfInt[i], 11));

			try {
				con = DBConnectionUtil.getConnection();
				stat = con.createStatement();
				affected = stat.executeUpdate("Insert into Estimation Values('" + str1 + "','" + str2 + "','" + str3
						+ "','" + str4 + "','" + str5 + "','" + str6 + "','" + str7 + "','" + str8 + "','" + str9
						+ "','" + str10 + "','" + str11 + "','" + str12 + "','" + str13 + "','" + str14 + "','" + str15
						+ "','Ready','" + str9 + "','0')");

				if (affected > 0) {
					teamLeaderEstimationDetaultTableModel.addRow(new Object[] { str4, str5, str6, str7, str8, str9, str10, str11, str12, str13, str14,
							str15 });
					stat.executeUpdate("Insert into OLD_TL_ESTIMATION Values('" + str1 + "','" + str2 + "','" + str3
							+ "','" + str4 + "','" + str5 + "','" + str6 + "','" + str7 + "','" + str8 + "','" + str9
							+ "','" + str10 + "','" + str11 + "','" + str12 + "','" + str13 + "','" + str14 + "','"
							+ str15 + "','Ready','" + str9 + "','0')");
				}
			} catch (Exception localException) {
				if (localException.getMessage().toString().equalsIgnoreCase("General Error")) {
					System.out.println(localException);
					JOptionPane.showMessageDialog(internalFrame, "Estimation Completed of Item.");
				} else {
					System.out.println(localException);
					JOptionPane.showMessageDialog(internalFrame, localException.getMessage().toString());
				}
			}
		}
	}

	public void dynamicFreeze() {
		int[] arrayOfInt = projectManagerEstimationTable.getSelectedRows();

		for (int i = 0; i < arrayOfInt.length; i++) {
			String str1 = String.valueOf(textFieldProjectNumber.getSelectedItem());
			String str2 = String.valueOf(projectManagerEstimationDefaultTableModel.getValueAt(arrayOfInt[i], 1));
			String str3 = String.valueOf(projectManagerEstimationDefaultTableModel.getValueAt(arrayOfInt[i], 12));

			if (str3.equalsIgnoreCase("1")) {
				JOptionPane.showMessageDialog(internalFrame, "Item Is Freezed. Cannot Update ");
			} else {
				try {
					con = DBConnectionUtil.getConnection();
					stat = con.createStatement();
					affected = stat.executeUpdate("UPDATE ESTIMATION_MP SET FREEZE='1' WHERE PROJECT_NO='" + str1
							+ "' and item_code='" + str2 + "' ");

					if (affected > 0) {
						projectManagerEstimationDefaultTableModel.setValueAt("1", arrayOfInt[i], 12);
					}
				} catch (Exception localException) {
					if (localException.getMessage().toString().equalsIgnoreCase("General Error")) {
						System.out.println(localException);
						JOptionPane.showMessageDialog(internalFrame, "Estimation Completed of Item.");
					} else {
						System.out.println(localException);
						JOptionPane.showMessageDialog(internalFrame, localException.getMessage().toString());
					}
				}
			}
		}
	}

	public Estimation() {
		sdn = new CreateDrawingNumbers();

		affected = 0;

		columnNames = new String[] { "SL_No", "Code", "Items", "Items Qty", "Sheet Size", "Sheet Qty", "D_Time",
				"C_Time", "TL Time", "PS Time", "T_Time", "C_Level", "Freeze" };
		data = new Object[0][];

		columnNames2 = new String[] { "SL_No", "Code", "Items", "Items Qty", "Sheet Size", "Sheet Qty", "D_Time",
				"C_Time", "TL Time", "PS Time", "T_Time", "C_Level" };
		data2 = new Object[0][];

		columnNames1 = new String[] { "Item Code", "Item Name" };
		data1 = new Object[0][];

		df = new DecimalFormat("00.00");
		df2 = new DecimalFormat("00");

		projectManagerEstimationDefaultTableModel = new DefaultTableModel(data, columnNames);
		projectManagerEstimationTable = new JTable(projectManagerEstimationDefaultTableModel) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int paramAnonymousInt1, int paramAnonymousInt2) {
				int i = convertColumnIndexToModel(paramAnonymousInt2);
				return i < 0;
			}
		};
		projectManagerEstimationTableScrollPane = new JScrollPane(projectManagerEstimationTable);

		teamLeaderEstimationDetaultTableModel = new DefaultTableModel(data2, columnNames2);
		teamLeaderEstimationTable = new JTable(teamLeaderEstimationDetaultTableModel) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int paramAnonymousInt1, int paramAnonymousInt2) {
				int i = convertColumnIndexToModel(paramAnonymousInt2);
				return i < 0;
			}
		};
		teamLeaderEstimationTableScrollPane = new JScrollPane(teamLeaderEstimationTable);

		itemDefaultTableModel = new DefaultTableModel(data1, columnNames1);
		itemTable = new JTable(itemDefaultTableModel);
		itemTableScrollPane = new JScrollPane(itemTable);

		splitPane = new JSplitPane(0, projectManagerEstimationTableScrollPane, teamLeaderEstimationTableScrollPane);

		internalFrame = new JInternalFrame("Project Estimation", true, true, true, true);
		tableframe = new JDialog();
		labelEstimatedBy = new JLabel("Estimated By");
		labelProjectNumber = new JLabel("Project No");
		labelProjectName = new JLabel("Project Name");
		labelClientName = new JLabel("Client Name");
		labelSerialNumber = new JLabel("Serial No");
		labelItemCode = new JLabel("Item Code");
		labelItemName = new JLabel("Item Name");
		labelItemQuantity = new JLabel("Items Quantity");
		labelSheetSize = new JLabel("Sheet Size");
		labelSheetQuantity = new JLabel("Sheet Quantity");
		labelDetailingTime = new JLabel("Detailing Time");
		labelCheckingTime = new JLabel("Checking Time");
		labelTLTime = new JLabel("TL Time");
		labelPerSheetTime = new JLabel("Per Sheet Time");
		labelTotalTime = new JLabel("Total Time");
		labelComplexityLevel = new JLabel("Complexity Level");

		textFieldEstimatedBy = new JComboBox();
		textFieldProjectNumber = new JComboBox();
		textFieldProjectName = new JTextField(15);
		textFieldClientName = new JTextField(15);
		textFieldSerialNumber = new JTextField("1");

		textFieldItemQuantity = new JTextField("1");

		cbm = new DefaultComboBoxModel();
		textFieldSheetSize = new JComboBox(cbm);
		textFieldSheetQuantity = new JTextField("0");
		textFieldDetailingTime = new JTextField("00.00");
		textFieldTLTime = new JTextField("00.00");
		textFieldCheckingTime = new JTextField("00.00");
		textFieldPerSheetTime = new JTextField("00.00");
		textFieldTotalTime = new JTextField("00.00");
		textFieldComplexityLevel = new JTextField("00.00");

		mpbut = new JRadioButton("Show MP Estimation", true);
		showbut = new JRadioButton("Show TL Estimation", false);

		grp = new ButtonGroup();

		prjbut = new JButton("Update");
		addbut = new JButton("Add to List");
		calcbut = new JButton("Calculate");
		updatebut = new JButton("Update");
		nextbut = new JButton("Change Time of Sheet");
		removebut = new JButton("Remove From List");
		closebut = new JButton("Close X");

		jpm = new JPopupMenu();
		popm1 = new JMenuItem("Make TL Estimation", new ImageIcon(ClassLoader.getSystemResource(ApplicationConstants.IMAGE_PATH + "check.gif")));
		popm2 = new JMenuItem("Freeze", new ImageIcon(ClassLoader.getSystemResource(ApplicationConstants.IMAGE_PATH + "check.gif")));

		cola1 = projectManagerEstimationTable.getColumnModel().getColumn(2);
		colb1 = teamLeaderEstimationTable.getColumnModel().getColumn(2);

		northp1 = new JPanel();
		northp2 = new JPanel();
		northp3 = new JPanel();

		butpanel1 = new JPanel();
		butpanel2 = new JPanel();

		centerpanel = new JPanel();
		northpanel = new JPanel();

		dat = new Date();
		formatter = new SimpleDateFormat("dd/MM/yyyy");

		try {
			dateString = formatter.format(dat);

			datetf = new JTextField(String.valueOf(dateString), 10);
		} catch (IllegalArgumentException localIllegalArgumentException) {
			System.out.println(localIllegalArgumentException.getMessage());
		}

		fo = new Font("Tahoma", 1, 11);
		line = BorderFactory.createLineBorder(Color.black);
		bor1 = BorderFactory.createTitledBorder(line, "Estimation Details", 0, 0, fo, Color.black);
		bor2 = BorderFactory.createTitledBorder(line, "Project Estimation Details", 0, 0, fo, Color.black);

		tk = Toolkit.getDefaultToolkit();
		ss = tk.getScreenSize();
		fs = internalFrame.getSize();

		skc = new Estimation.ColoredTableCellRenderer();
		skc1 = new Estimation.ColoredTableCellRenderer1();

		f2 = new JInternalFrame("Remove Drawing From Estimation", true, true, true, true);

		f2southpanel = new JPanel();
		f2updatebut = new JButton("Remove From Estimation & Update");

		empstr = "";

		tblstr = "Estimation";

		remain = "";
		oldsheet = "";

		sumsl = 0;
		sumitemqty = 0;
		sumsheetqty = 0;
		sumdtltime = 0.0F;
		sumchktime = 0.0F;
		sumhrs = 0.0F;
		complexity = 0.0F;
	}

	public void actionPerformed(ActionEvent paramActionEvent) {
		int i;
		int k;
		float f5;
		float f6;
		if (paramActionEvent.getSource() == addbut) {
			i = new Integer(textFieldSerialNumber.getText()).intValue();
			int j = new Integer(textFieldItemQuantity.getText()).intValue();
			k = new Integer(textFieldSheetQuantity.getText()).intValue();

			float f4 = new Float(textFieldTotalTime.getText()).floatValue();
			f5 = new Float(textFieldDetailingTime.getText()).floatValue();
			f6 = new Float(textFieldCheckingTime.getText()).floatValue();

			String str4 = (String) textFieldEstimatedBy.getSelectedItem();
			if (str4.equalsIgnoreCase("Team Leader")) {
				try {
					con = DBConnectionUtil.getConnection();
					stat = con.createStatement();
					affected = stat.executeUpdate("Insert into Estimation Values('"
							+ textFieldProjectNumber.getSelectedItem() + "','" + textFieldProjectName.getText() + "','"
							+ textFieldClientName.getText() + "','" + textFieldSerialNumber.getText() + "','"
							+ String.valueOf(comboBoxItemCode.getSelectedItem()) + "','" + textFieldItemName.getText()
							+ "','" + textFieldItemQuantity.getText() + "','" + textFieldSheetSize.getSelectedItem()
							+ "','" + textFieldSheetQuantity.getText() + "','" + textFieldDetailingTime.getText()
							+ "','" + textFieldCheckingTime.getText() + "','" + textFieldTLTime.getText() + "','"
							+ textFieldPerSheetTime.getText() + "','" + textFieldTotalTime.getText() + "','"
							+ textFieldComplexityLevel.getText() + "','Ready','" + textFieldSheetQuantity.getText()
							+ "','0')");

					if (affected > 0) {
						teamLeaderEstimationDetaultTableModel.addRow(new Object[] { textFieldSerialNumber.getText(),
								String.valueOf(comboBoxItemCode.getSelectedItem()), textFieldItemName.getText(),
								textFieldItemQuantity.getText(), textFieldSheetSize.getSelectedItem(),
								textFieldSheetQuantity.getText(), textFieldDetailingTime.getText(),
								textFieldCheckingTime.getText(), textFieldTLTime.getText(),
								textFieldPerSheetTime.getText(), textFieldTotalTime.getText(),
								textFieldComplexityLevel.getText() });
						sumsl += 1;
						sumsheetqty += k;
						sumhrs += f4;
						complexity = (sumhrs / sumsheetqty);

						textFieldSerialNumber.setText(String.valueOf(sumsl + 1));
						comboBoxItemCode.requestFocus();

						calcbut.setEnabled(true);
						calcbut.setForeground(Color.black);
						removebut.setEnabled(true);

						try {
							affected = stat.executeUpdate("Insert into OLD_TL_ESTIMATION Values('"
									+ textFieldProjectNumber.getSelectedItem() + "','" + textFieldProjectName.getText()
									+ "','" + textFieldClientName.getText() + "','" + textFieldSerialNumber.getText()
									+ "','" + String.valueOf(comboBoxItemCode.getSelectedItem()) + "','"
									+ textFieldItemName.getText() + "','" + textFieldItemQuantity.getText() + "','"
									+ textFieldSheetSize.getSelectedItem() + "','" + textFieldSheetQuantity.getText()
									+ "','" + textFieldDetailingTime.getText() + "','" + textFieldCheckingTime.getText()
									+ "','" + textFieldTLTime.getText() + "','" + textFieldPerSheetTime.getText()
									+ "','" + textFieldTotalTime.getText() + "','" + textFieldComplexityLevel.getText()
									+ "','Ready','" + textFieldSheetQuantity.getText() + "','0')");
							if (affected > 0) {
								comboBoxItemCode.setSelectedItem("");
								textFieldItemName.setText("");
								textFieldItemQuantity.setText("");
								textFieldSheetQuantity.setText("");
								textFieldDetailingTime.setText("");
								textFieldCheckingTime.setText("");
								textFieldTLTime.setText("");
								textFieldPerSheetTime.setText("");
								textFieldTotalTime.setText("");
								textFieldComplexityLevel.setText("");
								addbut.setEnabled(false);
								addbut.setBackground(new Color(220, 220, 220));
							}

						} catch (Exception localException1) {
						}
					}
				} catch (Exception localException2) {
					if (localException2.getMessage().toString().equalsIgnoreCase("General Error")) {
						System.out.println(localException2);
						JOptionPane.showMessageDialog(internalFrame, "Estimation Allready Completed of Item.");
					} else {
						System.out.println(localException2);
						JOptionPane.showMessageDialog(internalFrame, localException2.getMessage().toString());
					}
				} finally {
					DBConnectionUtil.closeConnection(rs, stat, con);
				}
			}

			if (str4.equalsIgnoreCase("Project Manager")) {
				try {
					con = DBConnectionUtil.getConnection();
					stat = con.createStatement();
					affected = stat.executeUpdate("Insert into Estimation_MP Values('"
							+ textFieldProjectNumber.getSelectedItem() + "','" + textFieldProjectName.getText() + "','"
							+ textFieldClientName.getText() + "','" + textFieldSerialNumber.getText() + "','"
							+ String.valueOf(comboBoxItemCode.getSelectedItem()) + "','" + textFieldItemName.getText()
							+ "','" + textFieldItemQuantity.getText() + "','" + textFieldSheetSize.getSelectedItem()
							+ "','" + textFieldSheetQuantity.getText() + "','" + textFieldDetailingTime.getText()
							+ "','" + textFieldCheckingTime.getText() + "','" + textFieldTLTime.getText() + "','"
							+ textFieldPerSheetTime.getText() + "','" + textFieldTotalTime.getText() + "','"
							+ textFieldComplexityLevel.getText() + "','Ready','" + textFieldSheetQuantity.getText()
							+ "','0')");

					if (affected > 0) {
						projectManagerEstimationDefaultTableModel.addRow(new Object[] { textFieldSerialNumber.getText(),
								String.valueOf(comboBoxItemCode.getSelectedItem()), textFieldItemName.getText(),
								textFieldItemQuantity.getText(), textFieldSheetSize.getSelectedItem(),
								textFieldSheetQuantity.getText(), textFieldDetailingTime.getText(),
								textFieldCheckingTime.getText(), textFieldTLTime.getText(),
								textFieldPerSheetTime.getText(), textFieldTotalTime.getText(),
								textFieldComplexityLevel.getText() });
						if (ProjectDAO.isQuoted(String.valueOf(textFieldProjectNumber.getSelectedItem())) <= 0) {
							try {
								con = DBConnectionUtil.getConnection();
								stat = con.createStatement();
								stat.executeUpdate("Insert into QUOTATION_LIST Values('" + datetf.getText() + "','"
										+ textFieldProjectNumber.getSelectedItem() + "','"
										+ textFieldProjectName.getText() + "','" + textFieldClientName.getText()
										+ "','0','0','0')");

								sumsl += 1;
								sumitemqty += j;
								sumsheetqty += k;
								sumhrs += f4;
								complexity = (sumhrs / sumsheetqty);

								addbut.setEnabled(false);
								calcbut.setEnabled(true);
								calcbut.setForeground(Color.black);
								removebut.setEnabled(true);

								comboBoxItemCode.setSelectedItem("");
								textFieldItemName.setText("");
								textFieldItemQuantity.setText("");
								textFieldSheetQuantity.setText("");
								textFieldDetailingTime.setText("");
								textFieldCheckingTime.setText("");
								textFieldTLTime.setText("");
								textFieldPerSheetTime.setText("");
								textFieldTotalTime.setText("");
								textFieldComplexityLevel.setText("");

								textFieldSerialNumber.setText(String.valueOf(sumsl + 1));
								comboBoxItemCode.requestFocus();
							} catch (Exception localException3) {
								System.out.println(localException3);
							}

						}

					}

				} catch (Exception localException4) {
					if (localException4.getMessage().toString().equalsIgnoreCase("General Error")) {
						System.out.println(localException4);
						JOptionPane.showMessageDialog(internalFrame, "Estimation Allready Completed of Item.");
					} else {
						System.out.println(localException4);
						JOptionPane.showMessageDialog(internalFrame, localException4.getMessage().toString());
					}
				} finally{
					DBConnectionUtil.closeConnection(rs, stat, con);
				}
			}
		}
		int m;
		float f7;
		float f8;
		float f9;
		float f10;
		float f11;
		int i1;
		if (paramActionEvent.getSource() == calcbut) {
			float f3;
			float f12;
			if (mpbut.isSelected()) {
				i = projectManagerEstimationDefaultTableModel.getRowCount();
				f3 = 0.0F;
				k = 0;
				m = 0;
				f5 = 0.0F;
				f6 = 0.0F;
				f7 = 0.0F;
				f8 = 0.0F;
				f9 = 0.0F;
				f10 = 0.0F;
				f11 = 0.0F;
				f12 = 0.0F;

				for (i1 = 0; i1 < i; i1++) {
					f3 += Float.parseFloat(String.valueOf(projectManagerEstimationDefaultTableModel.getValueAt(i1, 3)));
					k += Integer.parseInt(String.valueOf(projectManagerEstimationDefaultTableModel.getValueAt(i1, 5)));
					m = Integer.parseInt(String.valueOf(projectManagerEstimationDefaultTableModel.getValueAt(i1, 5)));
					f5 += Float.parseFloat(String.valueOf(projectManagerEstimationDefaultTableModel.getValueAt(i1, 6)));
					f6 += Float.parseFloat(String.valueOf(projectManagerEstimationDefaultTableModel.getValueAt(i1, 7)));
					f7 += Float.parseFloat(String.valueOf(projectManagerEstimationDefaultTableModel.getValueAt(i1, 8)));
					f8 += Float
							.parseFloat(String.valueOf(projectManagerEstimationDefaultTableModel.getValueAt(i1, 10)));
					f9 += Float
							.parseFloat(String.valueOf(projectManagerEstimationDefaultTableModel.getValueAt(i1, 11)));

					f10 += m * f5;
					f11 += m * f6;
					f12 += m * f7;
					m = 0;
					f5 = 0.0F;
					f6 = 0.0F;
					f7 = 0.0F;
				}
				projectManagerEstimationDefaultTableModel.addRow(new Object[] { "", "", "", String.valueOf(f3), "",
						String.valueOf(k), String.valueOf(df.format(f10)), String.valueOf(df.format(f11)),
						String.valueOf(df.format(f12)), "", String.valueOf(df.format(f8)), "" });
			}
			if (showbut.isSelected()) {
				i = teamLeaderEstimationDetaultTableModel.getRowCount();
				f3 = 0.0F;
				k = 0;
				m = 0;
				f5 = 0.0F;
				f6 = 0.0F;
				f7 = 0.0F;
				f8 = 0.0F;
				f9 = 0.0F;
				f10 = 0.0F;
				f11 = 0.0F;
				f12 = 0.0F;

				for (i1 = 0; i1 < i; i1++) {
					f3 += Float.parseFloat(String.valueOf(teamLeaderEstimationDetaultTableModel.getValueAt(i1, 3)));
					k += Integer.parseInt(String.valueOf(teamLeaderEstimationDetaultTableModel.getValueAt(i1, 5)));
					m = Integer.parseInt(String.valueOf(teamLeaderEstimationDetaultTableModel.getValueAt(i1, 5)));
					f5 += Float.parseFloat(String.valueOf(teamLeaderEstimationDetaultTableModel.getValueAt(i1, 6)));
					f6 += Float.parseFloat(String.valueOf(teamLeaderEstimationDetaultTableModel.getValueAt(i1, 7)));
					f7 += Float.parseFloat(String.valueOf(teamLeaderEstimationDetaultTableModel.getValueAt(i1, 8)));
					f8 += Float.parseFloat(String.valueOf(teamLeaderEstimationDetaultTableModel.getValueAt(i1, 10)));
					f9 += Float.parseFloat(String.valueOf(teamLeaderEstimationDetaultTableModel.getValueAt(i1, 11)));

					f10 += m * f5;
					f11 += m * f6;
					f12 += m * f7;
					m = 0;
					f5 = 0.0F;
					f6 = 0.0F;
					f7 = 0.0F;
				}
				teamLeaderEstimationDetaultTableModel.addRow(new Object[] { "", "", "", String.valueOf(f3), "",
						String.valueOf(k), String.valueOf(df.format(f10)), String.valueOf(df.format(f11)),
						String.valueOf(df.format(f12)), "", String.valueOf(df.format(f8)), "" });
			}
		}

		if (paramActionEvent.getSource() == showbut) {
			tblstr = "Estimation";
			showAll();
			if (String.valueOf(textFieldEstimatedBy.getSelectedItem()).equalsIgnoreCase("Team Leader")) {
				updatebut.setEnabled(true);
				removebut.setEnabled(true);
			}
		}
		if (paramActionEvent.getSource() == mpbut) {
			tblstr = "Estimation_MP";
			showAll();
			if (String.valueOf(textFieldEstimatedBy.getSelectedItem()).equalsIgnoreCase("Team Leader")) {
				updatebut.setEnabled(false);
				removebut.setEnabled(false);
			}
		}
		if (paramActionEvent.getSource() == updatebut) {
			showRemain();
			updateRecord();
		}
		if (paramActionEvent.getSource() == removebut) {
			removeRecord();
		}

		if (paramActionEvent.getSource() == nextbut) {
			changeTimeOfSheet();
		}
		if (paramActionEvent.getSource() == closebut) {
			internalFrame.setVisible(false);
		}
		if (paramActionEvent.getSource() == textFieldProjectNumber) {
			textFieldProjectName.requestFocus();
			textFieldProjectName.selectAll();
		}
		if (paramActionEvent.getSource() == textFieldProjectName) {
			textFieldClientName.requestFocus();
			textFieldClientName.selectAll();
		}
		if (paramActionEvent.getSource() == textFieldClientName 
				|| paramActionEvent.getSource() == textFieldSerialNumber) {
			
			textFieldItemName.setText(ItemTB.getItemName(String.valueOf(comboBoxItemCode.getSelectedItem()).trim()));
			textFieldItemQuantity.requestFocus();
			textFieldItemQuantity.selectAll();
		}
		if (paramActionEvent.getSource() == textFieldItemName) {
			textFieldItemQuantity.requestFocus();
			textFieldItemQuantity.selectAll();
		}
		if (paramActionEvent.getSource() == textFieldItemQuantity) {
			textFieldSheetQuantity.requestFocus();
			textFieldSheetQuantity.selectAll();
		}
		if (paramActionEvent.getSource() == textFieldSheetSize) {
			textFieldSheetQuantity.requestFocus();
			textFieldSheetQuantity.selectAll();
		}
		if (paramActionEvent.getSource() == textFieldSheetQuantity) {
			totalSheetTime();
			textFieldDetailingTime.requestFocus();
			textFieldDetailingTime.selectAll();
		}
		if (paramActionEvent.getSource() == textFieldDetailingTime) {
			float f1 = Float.parseFloat(textFieldDetailingTime.getText());
			textFieldDetailingTime.setText(String.valueOf(df.format(f1)));

			String str1 = textFieldDetailingTime.getText();
			k = str1.indexOf('.');
			m = str1.length();
			String str2 = str1.substring(0, k);
			String str3 = str1.substring(k + 1, m);
			System.out.println("Dtl time");
			System.out.println("-------------");
			System.out.println(str2);
			System.out.println(str3);

			f7 = Float.parseFloat(str2);
			f8 = f7 * 60.0F + Integer.parseInt(str3);

			f9 = 0.5F;
			f8 *= f9;

			f10 = f8 / 60.0F;
			f11 = f8 % 60.0F;

			int n = (int) f11;
			i1 = (int) f10;

			df.format(f8);
			textFieldCheckingTime.setText(String.valueOf(i1) + "." + String.valueOf(n));

			float f13 = Float.parseFloat(textFieldDetailingTime.getText());
			float f14 = Float.parseFloat(textFieldCheckingTime.getText());

			float f15 = f13 + f14;
			float f16 = f15 * 100.0F / 90.0F - f15;

			textFieldTLTime.setText(String.valueOf(df.format(f16)));

			String str6 = String.valueOf(textFieldTLTime.getText());

			int i2 = Integer.parseInt(str6.substring(0, str6.indexOf('.')));
			int i3 = Integer.parseInt(str6.substring(str6.indexOf('.') + 1, str6.length()));

			if (i3 >= 60) {
				i3 -= 40;
			}
			textFieldTLTime.setText(String.valueOf(df2.format(i2)) + "." + String.valueOf(df2.format(i3)));

			perSheetTime();
			totalSheetTime();

			textFieldComplexityLevel.setText(textFieldPerSheetTime.getText());
			addbut.requestFocus();
			addbut.setEnabled(true);
			addbut.setBackground(new Color(180, 200, 240));
		}

		if (paramActionEvent.getSource() == textFieldCheckingTime 
				|| paramActionEvent.getSource() == textFieldPerSheetTime) {
			splitTime();
			totalSheetTime();
		}
		if (paramActionEvent.getSource() == textFieldTotalTime || paramActionEvent.getSource() == popm1) {
			dynamicSave();
		}
		if (paramActionEvent.getSource() == popm2) {
			dynamicFreeze();
		}
		if (paramActionEvent.getSource() == f2updatebut) {
			sdn.removeFromEstimation();
			textFieldSheetQuantity.setText(sdn.textFieldTotalSheet.getText());
			totalSheetTime();
			updateTLEstimation();
		}
		if (paramActionEvent.getSource() == prjbut) {
			updateProjectName();
		}
	}

	public void itemStateChanged(ItemEvent paramItemEvent) {
		if (paramItemEvent.getSource() == textFieldProjectNumber) {
			projectName();
		}
		if(paramItemEvent.getSource() == comboBoxItemCode){
			textFieldItemName.setText(ItemTB.getItemName(String.valueOf(comboBoxItemCode.getSelectedItem()).trim()));
			textFieldItemQuantity.requestFocus();
			textFieldItemQuantity.selectAll();
		}
	}

	public void keyPressed(KeyEvent paramKeyEvent) {
		int i = paramKeyEvent.getKeyCode();
		if (paramKeyEvent.getSource() == comboBoxItemCode) {
			if (i == KeyEvent.VK_F1) {
				//new TechMainFrame();
				ItemCode localItemCode = new ItemCode(this);//this means Estimation
				TechMainFrame.desktop.add(localItemCode.f);
				TechMainFrame.desktop.moveToFront(localItemCode.f);
				localItemCode.designFrame();
			}
		}

		if (paramKeyEvent.getSource() == projectManagerEstimationTable) {
			if (paramKeyEvent.getSource() == projectManagerEstimationTable) {
				showSelectedRecord();
			}
		}
	}

	public void keyTyped(KeyEvent paramKeyEvent) {
		if (paramKeyEvent.getSource() == itemTable) {
			int i = itemTable.getSelectedRow();
			String str1 = String.valueOf(itemDefaultTableModel.getValueAt(i - 1, 0));
			String str2 = String.valueOf(itemDefaultTableModel.getValueAt(i - 1, 1));
			comboBoxItemCode.setSelectedItem(str1);
			textFieldItemName.setText(str2);
			tableframe.setVisible(false);
			textFieldItemQuantity.requestFocus();
			textFieldItemQuantity.selectAll();
		}
	}
	
	public void keyReleased(KeyEvent paramKeyEvent) {
	}

	public void focusGained(FocusEvent paramFocusEvent) {
		if (paramFocusEvent.getSource() == textFieldProjectNumber) {
		}
	}

	public void focusLost(FocusEvent paramFocusEvent) {
		if (paramFocusEvent.getSource() == textFieldSheetQuantity) {
			totalSheetTime();
		}
	}

	public void mousePressed(MouseEvent paramMouseEvent) {
		if (paramMouseEvent.getSource() == projectManagerEstimationTable) {
			if (SwingUtilities.isRightMouseButton(paramMouseEvent)) {
				if (String.valueOf(textFieldEstimatedBy.getSelectedItem()).equalsIgnoreCase("Team Leader")) {
					jpm.show((JComponent) paramMouseEvent.getSource(), paramMouseEvent.getX(), paramMouseEvent.getY());
					popm2.setEnabled(false);
				}
				if (String.valueOf(textFieldEstimatedBy.getSelectedItem()).equalsIgnoreCase("Project Manager")) {
					jpm.show((JComponent) paramMouseEvent.getSource(), paramMouseEvent.getX(), paramMouseEvent.getY());
					popm1.setEnabled(false);
				}
			} else {
				showSelectedRecord();
			}
		}

		if (paramMouseEvent.getSource() == teamLeaderEstimationTable) {
			showSelectedRecord();
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
