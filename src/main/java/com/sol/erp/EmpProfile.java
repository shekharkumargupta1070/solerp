package com.sol.erp;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import com.sol.erp.ui.custom.SolTableModel;
import com.sol.erp.util.DBConnectionUtil;
import com.sol.erp.util.formater.SolDateFormatter;

public class EmpProfile implements java.awt.event.ActionListener, java.awt.event.MouseListener,
		javax.swing.event.TableModelListener, java.awt.event.KeyListener {
	

	java.sql.Connection con;
	java.sql.Statement stat;
	ResultSet rs;
	public static String codestr;
	java.text.DecimalFormat df;
	java.text.DecimalFormat monthformat;
	public static String[] lefthead = { "Punchcard", "EMP Code", "Designation", "Name" };
	public static Object[][] leftdata = new Object[0][];

	String[] head;

	Object[][] data;

	JFrame f;

	JLabel desiglabel;

	JLabel emplabel;

	JLabel choicelabel;

	JLabel tolabel;

	JLabel datelabel;

	JComboBox desigcb;

	EmpCodeList empcb;

	JComboBox choicecb;

	JComboBox tocb;

	JButton b4;

	JButton b5;
	JButton addbut;
	JButton savebut;
	public static DefaultTableModel LeftHeaderModel = new DefaultTableModel(leftdata, lefthead);
	public static JTable LeftHeaderTable = new JTable(LeftHeaderModel);

	javax.swing.JScrollPane sp;

	DefaultTableModel model;

	JTable tb;

	javax.swing.JScrollPane mainsp;

	javax.swing.table.DefaultTableCellRenderer renderer;

	TableColumnModel tcm;

	TableColumn col;

	TableColumn col1;

	TableColumn col2;

	TableColumn col3;

	JPanel mainPanel;

	JPanel p1;

	JPanel p2;

	JPanel buttonpanel;
	JPanel southpanel;
	javax.swing.JTextField searchtf;
	JRadioButton rb1;
	JRadioButton rb2;
	JRadioButton rb3;
	javax.swing.ButtonGroup grp;
	javax.swing.border.Border bor1;
	Font fo;
	Font fo2;
	java.awt.Toolkit tk;
	Dimension ss;
	Dimension fs;
	java.util.Date dat;
	java.text.SimpleDateFormat formatter;
	java.text.SimpleDateFormat formatter1;
	String dateString;
	ColoredTableCellRenderer skr;
	ColoredTableCellRenderer2 skr2;
	

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
				setBackground(new Color(250, 250, 250));
				setForeground(Color.darkGray);

			} else {
				setBackground(new Color(240, 240, 240));
				setForeground(Color.darkGray);
			}

			if ((paramInt1 > 1) && (paramInt1 == EmpProfile.LeftHeaderTable.getRowCount() - 1)) {
				setBackground(Color.darkGray);
				setForeground(Color.white);
			}

			if ((paramInt2 == 0) || (paramInt2 == 1) || (paramInt2 == 3)) {
				setHorizontalAlignment(0);
			}
			if ((paramInt2 == 2) || (paramInt2 == 3)) {
				setHorizontalAlignment(2);
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

			setHorizontalAlignment(0);

			if ((paramInt1 > 1) && (paramInt1 == EmpProfile.LeftHeaderTable.getRowCount() - 1)) {
				setBackground(Color.darkGray);
				setForeground(Color.white);
			}

			if ((paramInt2 > 4) && (paramObject != null)) {
				String str = String.valueOf(EmpProfile.LeftHeaderModel.getValueAt(paramInt1, paramInt2));
				float f = Float.parseFloat(str);

				setBackground(new Color(240, 240, 240));
				setForeground(Color.darkGray);
				if ((f <= 230.0F) || (

				(f > 0.0F) && (f < 190.0F))) {
					setBackground(new Color(250, 160, 200));
					setForeground(Color.black);
				}
			}
			super.getTableCellRendererComponent(paramJTable, paramObject, paramBoolean1, paramBoolean2, paramInt1,
					paramInt2);

			return this;
		}
	}

	public EmpProfile() {

		df = new java.text.DecimalFormat("0");
		monthformat = new java.text.DecimalFormat("00");

		head = new String[] { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N" };
		data = new Object[][] { new Object[0], new Object[0], new Object[0], new Object[0] };

		f = new JFrame();

		desiglabel = new JLabel("DESIGNATION LIST");
		emplabel = new JLabel("SELECT EMP");
		choicelabel = new JLabel("REPORT CHOICE");
		tolabel = new JLabel("TO MONTH");
		datelabel = new JLabel("Today : ");

		desigcb = new JComboBox();
		empcb = new EmpCodeList("C01", "0", "");
		choicecb = new JComboBox();
		tocb = new JComboBox();

		b4 = new JButton("Show All");
		b5 = new JButton("Search");

		addbut = new JButton("GENERATE >>");
		savebut = new JButton("Save Changes");

		sp = new javax.swing.JScrollPane(LeftHeaderTable);

		model = new DefaultTableModel(data, head);
		tb = new JTable(model);
		mainsp = new javax.swing.JScrollPane(tb);

		renderer = new javax.swing.table.DefaultTableCellRenderer();

		tcm = LeftHeaderTable.getColumnModel();
		col = tcm.getColumn(0);
		col1 = tcm.getColumn(1);
		col2 = tcm.getColumn(2);
		col3 = tcm.getColumn(3);

		mainPanel = new JPanel();
		p1 = new JPanel();
		p2 = new JPanel();
		buttonpanel = new JPanel();
		southpanel = new JPanel();

		searchtf = new javax.swing.JTextField(15);
		rb1 = new JRadioButton("Emp Code", true);
		rb2 = new JRadioButton("Name");
		rb3 = new JRadioButton("Desig");
		grp = new javax.swing.ButtonGroup();

		bor1 = javax.swing.BorderFactory.createTitledBorder("Search Option");

		fo = new Font("Arial", 1, 11);
		fo2 = new Font("Verdana", 1, 10);

		tk = java.awt.Toolkit.getDefaultToolkit();
		ss = tk.getScreenSize();
		fs = f.getSize();

		dat = new java.util.Date();
		formatter = new java.text.SimpleDateFormat("yyyy");
		formatter1 = new java.text.SimpleDateFormat("dd/MM/yyyy");

		try {
			datelabel.setText(String.valueOf(formatter1.format(dat)));
			dateString = formatter.format(dat);
		} catch (IllegalArgumentException localIllegalArgumentException) {
			System.out.println(localIllegalArgumentException.getMessage());
		}

		skr = new EmpProfile.ColoredTableCellRenderer();
		skr2 = new EmpProfile.ColoredTableCellRenderer2();

	}

	public JPanel DesignFrame() {
		java.awt.Container localContainer = f.getContentPane();
		localContainer.setLayout(new java.awt.BorderLayout());
		mainPanel.setLayout(new java.awt.BorderLayout());
		p1.setLayout(new java.awt.GridLayout(4, 2, 2, 2));
		p2.setLayout(new java.awt.BorderLayout());
		buttonpanel.setLayout(new java.awt.FlowLayout(0));
		southpanel.setLayout(new java.awt.FlowLayout(0));

		choicecb.addItem("MERGE");
		choicecb.addItem("SEPARATE");

		tocb.addItem("January");
		tocb.addItem("February");
		tocb.addItem("March");
		tocb.addItem("April");
		tocb.addItem("May");
		tocb.addItem("June");
		tocb.addItem("July");
		tocb.addItem("August");
		tocb.addItem("September");
		tocb.addItem("October");
		tocb.addItem("November");
		tocb.addItem("December");

		desiglabel.setFont(fo2);
		emplabel.setFont(fo2);
		choicelabel.setFont(fo2);

		desigcb.setFont(new Font("Tahoma", 1, 9));
		empcb.setFont(new Font("Tahoma", 1, 9));
		choicecb.setFont(fo2);

		buttonpanel.add(desiglabel);
		buttonpanel.add(desigcb);
		buttonpanel.add(emplabel);
		buttonpanel.add(empcb);
		buttonpanel.add(choicelabel);
		buttonpanel.add(choicecb);

		buttonpanel.add(addbut);

		addbut.setBackground(Color.white);
		desigcb.setBackground(Color.white);
		empcb.setBackground(Color.white);
		choicecb.setBackground(Color.white);
		tocb.setBackground(Color.white);

		addbut.setFont(fo2);

		SolTableModel.decorateTable(LeftHeaderTable);
		LeftHeaderTable.setAutoResizeMode(0);
		LeftHeaderTable.setShowGrid(false);
		LeftHeaderTable.setIntercellSpacing(new Dimension(1, 1));
		LeftHeaderTable.getTableHeader().setPreferredSize(new Dimension(30, 30));
		LeftHeaderTable.setRowHeight(25);
		LeftHeaderTable.setFont(new Font("Tahoma", 1, 10));
		LeftHeaderTable.setAutoCreateRowSorter(true);
		LeftHeaderTable.setBorder(javax.swing.BorderFactory.createBevelBorder(0));

		col.setPreferredWidth(60);
		col1.setPreferredWidth(140);
		col2.setPreferredWidth(210);
		col3.setPreferredWidth(50);

		LeftHeaderTable.getColumnModel().getColumn(0).setPreferredWidth(70);
		LeftHeaderTable.getColumnModel().getColumn(1).setPreferredWidth(65);
		LeftHeaderTable.getColumnModel().getColumn(2).setPreferredWidth(140);
		LeftHeaderTable.getColumnModel().getColumn(3).setPreferredWidth(160);

		LeftHeaderTable.getTableHeader().setToolTipText("<Html><Font Color='red' Size='13'>Click Me to Sort the Table");

		for (int i = 0; i < LeftHeaderTable.getColumnCount(); i++) {
			LeftHeaderTable.getColumnModel().getColumn(i).setCellRenderer(skr);
		}

		southpanel.add(datelabel);

		tb.setRowHeight(25);
		tb.setFont(new Font("Tahoma", 1, 10));
		tb.setAutoResizeMode(0);
		tb.setShowGrid(false);
		tb.setIntercellSpacing(new Dimension(1, 1));

		LeftHeaderTable.setPreferredSize(new Dimension(450, 600));
		mainsp.setRowHeaderView(LeftHeaderTable);

		p2.add(buttonpanel, "North");
		p2.add(mainsp, "Center");
		p2.add(southpanel, "South");

		mainPanel.add(p2, "Center");
		localContainer.add(mainPanel, "Center");

		buttonpanel.setBorder(bor1);

		searchtf.addActionListener(this);
		b5.addActionListener(this);

		LeftHeaderTable.addMouseListener(this);
		LeftHeaderModel.addTableModelListener(this);

		searchtf.addKeyListener(this);
		b4.addKeyListener(this);
		b5.addKeyListener(this);
		rb1.addKeyListener(this);
		rb2.addKeyListener(this);
		rb3.addKeyListener(this);
		LeftHeaderTable.addKeyListener(this);

		addbut.addActionListener(this);
		savebut.addActionListener(this);

		f.setTitle("Employee List");
		desigList();
		empList();

		return mainPanel;
	}

	public void showEmpList() {
		DesignFrame();
		f.setLocation((ss.width - fs.width) / 10, (ss.height - fs.height) / 8);
		f.setSize(810, 530);
		f.setVisible(true);
	}

	public void desigList() {
		desigcb.removeAllItems();

		String str1 = "jdbc:mysql://192.168.1.144:3306/ERPDB";
		String str2 = "root";
		String str3 = "myadmin";

		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = java.sql.DriverManager.getConnection(str1, str2, str3);
			stat = con.createStatement();
			rs = stat.executeQuery("select distinct(designation) from emp_status ");
			rs.getMetaData().getColumnCount();
			while (rs.next()) {
				String str4 = new String(rs.getString(1));
				desigcb.addItem(str4.toUpperCase());
			}
			desigcb.addItem("ALL");
		} catch (Exception localException) {
			javax.swing.JOptionPane.showMessageDialog(f, localException.getMessage().toString());
		}
	}

	public void empList() {
		LeftHeaderModel.setNumRows(0);

		String str1 = searchtf.getText() + "%";
		String str3 = (String) desigcb.getSelectedItem();

		if (str3.equalsIgnoreCase("All")) {
			str3 = "%";
		} else {
			str3 = "%" + (String) desigcb.getSelectedItem();
		}

		java.sql.Date localDate = SolDateFormatter.DDMMYYtoSQL(datelabel.getText());

		try {
			con = DBConnectionUtil.getConnection();
			java.sql.PreparedStatement localPreparedStatement = con.prepareStatement(
					"select PUNCHCARD_NO, EMP_CODE, DESIG, FULL_NAME  from HREMP_JOIN where EMP_CODE like '" + str1
							+ "' and emp_code not in(select emp_code from HR_EX_EMP where releave_date < ?) and DESIG like '"
							+ str3 + "' order by punchcard_no");
			localPreparedStatement.setDate(1, localDate);
			ResultSet localResultSet = localPreparedStatement.executeQuery();
			localResultSet.getMetaData().getColumnCount();
			localResultSet.getRow();
			while (localResultSet.next()) {
				String str4 = new String(localResultSet.getString(1));
				String str5 = new String(localResultSet.getString(2));
				String str6 = new String(localResultSet.getString(3));
				String str7 = new String(localResultSet.getString(4));

				LeftHeaderModel
						.addRow(new Object[] { str4, str5.toUpperCase(), str6.toUpperCase(), str7.toUpperCase() });
			}
		} catch (Exception localException) {
			javax.swing.JOptionPane.showMessageDialog(f, localException.getMessage().toString());
		}
	}

	public void actionPerformed(ActionEvent paramActionEvent) {
		if (paramActionEvent.getSource() == b4) {
			empList();
		}
		if ((paramActionEvent.getSource() != addbut) || (

		(paramActionEvent.getSource() != savebut) || (

		(paramActionEvent.getSource() == b5) || (paramActionEvent.getSource() == searchtf)))) {
			empList();
		}
	}

	public void keyPressed(KeyEvent paramKeyEvent) {
		int i = paramKeyEvent.getKeyCode();

		if (i == 27) {
			f.setVisible(false);
		}
	}

	public void keyReleased(KeyEvent paramKeyEvent) {
	}

	public void keyTyped(KeyEvent paramKeyEvent) {
	}

	public void mousePressed(MouseEvent paramMouseEvent) {
	}

	public void mouseReleased(MouseEvent paramMouseEvent) {
	}

	public void mouseClicked(MouseEvent paramMouseEvent) {
		for (int i = 5; i < LeftHeaderTable.getColumnCount(); i++) {
			LeftHeaderTable.getColumnModel().getColumn(i).setCellRenderer(skr2);
		}
	}

	public void mouseExited(MouseEvent paramMouseEvent) {
	}

	public void mouseEntered(MouseEvent paramMouseEvent) {
	}

	public void tableChanged(javax.swing.event.TableModelEvent paramTableModelEvent) {
		LeftHeaderTable.setForeground(Color.blue);
	}

}
