package com.sol.erp;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import com.sol.erp.ui.custom.SolTableModel;
import com.sol.erp.util.DBConnectionUtil;
import com.sol.erp.util.SessionUtil;

public class EmpList implements java.awt.event.ActionListener, java.awt.event.MouseListener,
		javax.swing.event.TableModelListener, java.awt.event.KeyListener {
	

	java.sql.Connection con;

	java.sql.Statement stat;
	ResultSet rs;
	public static String codestr;
	String[] columnNames = { " ", "EMP Code", "Designation", "Name", "Factor", "Unit", "Ext.", "Personal No.",
			"Residence" };
	final Object[][] data = new Object[0][];

	JFrame f = new JFrame();

	javax.swing.JMenuBar menubar = new javax.swing.JMenuBar();
	javax.swing.JMenu menu1 = new javax.swing.JMenu("Report");
	javax.swing.JCheckBoxMenuItem ma1 = new javax.swing.JCheckBoxMenuItem("Current Employee", true);
	javax.swing.JCheckBoxMenuItem ma2 = new javax.swing.JCheckBoxMenuItem("Ex. Employee", false);
	javax.swing.JMenuItem ma3 = new javax.swing.JMenuItem("Save Changes");
	javax.swing.JMenuItem ma4 = new javax.swing.JMenuItem("Print");
	javax.swing.JMenuItem ma5 = new javax.swing.JMenuItem("Close");

	javax.swing.ButtonGroup menugroup = new javax.swing.ButtonGroup();

	JButton b4 = new JButton("Show All");
	JButton b5 = new JButton("Search");

	JButton addbut = new JButton("New");
	JButton savebut = new JButton("Save Changes");

	JComboBox unitcomboBox = new JComboBox();

	DefaultTableModel model = new DefaultTableModel(data, columnNames);
	JTable tb = new JTable(model);
	javax.swing.JScrollPane sp = new javax.swing.JScrollPane(tb);

	javax.swing.table.DefaultTableCellRenderer renderer = new javax.swing.table.DefaultTableCellRenderer();

	TableColumnModel tcm = tb.getColumnModel();
	TableColumn col = tcm.getColumn(0);
	TableColumn col1 = tcm.getColumn(1);
	TableColumn col2 = tcm.getColumn(2);
	TableColumn col3 = tcm.getColumn(3);
	TableColumn col4 = tcm.getColumn(4);

	JPanel mainPanel = new JPanel();
	JPanel p1 = new JPanel();
	JPanel p2 = new JPanel();
	JPanel buttonpanel = new JPanel();
	JPanel southpanel = new JPanel();

	JTextField searchtf = new JTextField(15);
	JRadioButton rb1 = new JRadioButton("Emp Code", true);
	JRadioButton rb2 = new JRadioButton("Name");
	JRadioButton rb3 = new JRadioButton("Desig");
	javax.swing.ButtonGroup grp = new javax.swing.ButtonGroup();

	javax.swing.border.Border bor1 = javax.swing.BorderFactory.createTitledBorder("Search Option");

	java.awt.Font fo = new java.awt.Font("Arial", 1, 11);

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

			if (paramInt1 % 2 == 0) {
				setBackground(new Color(250, 250, 250));
				setForeground(Color.darkGray);

			} else {
				setBackground(new Color(240, 240, 240));
				setForeground(Color.darkGray);
			}

			if ((paramInt2 == 0) || (paramInt2 == 1) || (paramInt2 == 4) || (paramInt2 == 5) || (paramInt2 == 6)) {
				setHorizontalAlignment(0);
			}
			if ((paramInt2 == 2) || (paramInt2 == 3) || (paramInt2 == 7) || (paramInt2 == 8)) {
				setHorizontalAlignment(2);
			}

			if (paramInt2 == 4) {
				String str = String.valueOf(model.getValueAt(paramInt1, 4));
				float f = Float.parseFloat(str);

				if (f > 0.8D) {
					setBackground(new Color(60, 160, 200));
					setForeground(Color.white);
				}
				if (f < 0.4D) {
					setBackground(new Color(250, 100, 100));
					setForeground(Color.white);
				}
			}

			super.getTableCellRendererComponent(paramJTable, paramObject, paramBoolean1, paramBoolean2, paramInt1,
					paramInt2);

			return this;
		}
	}

	EmpList.ColoredTableCellRenderer skr = new EmpList.ColoredTableCellRenderer();

	String empcodestr = null;

	public JPanel DesignFrame(String paramString) {
		empcodestr = paramString;

		java.awt.Container localContainer = f.getContentPane();
		localContainer.setLayout(new java.awt.BorderLayout());
		mainPanel.setLayout(new java.awt.BorderLayout());
		p1.setLayout(new java.awt.GridLayout(4, 2, 2, 2));
		p2.setLayout(new java.awt.BorderLayout());
		buttonpanel.setLayout(new java.awt.FlowLayout(0));
		southpanel.setLayout(new java.awt.FlowLayout(0));

		menugroup.add(ma1);
		menugroup.add(ma2);

		menu1.add(ma1);
		menu1.add(ma2);
		menu1.addSeparator();
		menu1.add(ma3);
		menu1.addSeparator();
		menu1.add(ma4);
		menu1.addSeparator();
		menu1.add(ma5);

		menubar.add(menu1);

		SolTableModel.decorateTable(tb);
		tb.setAutoResizeMode(0);
		tb.setShowGrid(false);
		tb.setIntercellSpacing(new java.awt.Dimension(1, 1));
		tb.getTableHeader().setPreferredSize(new java.awt.Dimension(30, 30));
		tb.setRowHeight(22);
		tb.setFont(new java.awt.Font("Tahoma", 1, 10));
		tb.setAutoCreateRowSorter(true);

		col.setPreferredWidth(60);
		col1.setPreferredWidth(140);
		col2.setPreferredWidth(210);
		col3.setPreferredWidth(50);
		col4.setPreferredWidth(170);

		tb.getColumnModel().getColumn(0).setPreferredWidth(30);
		tb.getColumnModel().getColumn(1).setPreferredWidth(85);
		tb.getColumnModel().getColumn(2).setPreferredWidth(130);
		tb.getColumnModel().getColumn(3).setPreferredWidth(200);
		tb.getColumnModel().getColumn(4).setPreferredWidth(65);
		tb.getColumnModel().getColumn(5).setPreferredWidth(50);
		tb.getColumnModel().getColumn(6).setPreferredWidth(60);
		tb.getColumnModel().getColumn(7).setPreferredWidth(180);
		tb.getColumnModel().getColumn(8).setPreferredWidth(180);

		tb.getTableHeader().setToolTipText("<Html><Font Color='red' Size='13'>Click Me to Sort the Table");

		for (int i = 0; i < tb.getColumnCount(); i++) {
			tb.getColumnModel().getColumn(i).setCellRenderer(skr);
		}

		b4.setBackground(Color.white);
		b5.setBackground(Color.white);
		addbut.setBackground(Color.white);
		savebut.setBackground(Color.white);
		rb1.setBackground(Color.white);
		rb2.setBackground(Color.white);

		b4.setMnemonic(87);
		b5.setMnemonic(83);

		buttonpanel.add(b5);

		grp.add(rb1);
		grp.add(rb2);

		buttonpanel.add(rb1);
		buttonpanel.add(rb2);
		buttonpanel.add(searchtf);
		searchtf.setFont(fo);
		buttonpanel.add(b5);

		p2.add(buttonpanel, "North");
		p2.add(sp, "Center");
		p2.add(southpanel, "South");

		mainPanel.add(p2, "Center");
		localContainer.add(mainPanel, "Center");

		buttonpanel.setBorder(bor1);

		searchtf.addActionListener(this);
		b5.addActionListener(this);

		tb.addMouseListener(this);
		model.addTableModelListener(this);

		searchtf.addKeyListener(this);
		b4.addKeyListener(this);
		b5.addKeyListener(this);
		rb1.addKeyListener(this);
		rb2.addKeyListener(this);
		rb3.addKeyListener(this);
		tb.addKeyListener(this);

		addbut.addActionListener(this);
		savebut.addActionListener(this);

		ma1.addActionListener(this);
		ma2.addActionListener(this);
		ma3.addActionListener(this);
		ma4.addActionListener(this);
		ma5.addActionListener(this);

		f.setTitle("Employee List");
		showRecord();

		setUpUnitColumn(tb, tb.getColumnModel().getColumn(5));

		return mainPanel;
	}

	public void showEmpList(String paramString) {
		DesignFrame(paramString);
		f.setJMenuBar(menubar);
		f.setLocation((ss.width - fs.width) / 10, (ss.height - fs.height) / 8);
		f.setSize(810, 530);
		f.setVisible(true);
	}

	public void showRecord() {
		model.setNumRows(0);

		String str1 = searchtf.getText() + "%";
		String str2 = "s.EMP_CODE";

		if (rb1.isSelected()) {
			str2 = "s.Emp_CODE";
		}
		if (rb2.isSelected()) {
			str2 = "s.Emp_NAME";
		}

		String str3 = "select s.emp_code, s.designation, s.emp_name, s.factor, p.unit, p.ext_no, p.personal, p.remarks from emp_status s, phone p WHERE s.emp_code not in(select emp_code from HR_EX_EMP) and "
				+ str2 + " like '" + str1 + "' and s.emp_code=p.emp_code group by p.emp_code order by p.emp_code";

		if (ma1.getState() == true) {
			str3 = "select s.emp_code, s.designation, s.emp_name, s.factor, p.unit, p.ext_no, p.personal, p.remarks from emp_status s, phone p WHERE s.emp_code not in(select emp_code from HR_EX_EMP) and "
					+ str2 + " like '" + str1 + "' and s.emp_code=p.emp_code group by p.emp_code order by p.emp_code";
		}
		if (ma2.getState() == true) {
			str3 = "select s.emp_code, s.designation, s.emp_name, s.factor, p.unit, p.ext_no, p.personal, p.remarks from emp_status s, phone p WHERE s.emp_code in(select emp_code from HR_EX_EMP) and "
					+ str2 + " like '" + str1 + "' and s.emp_code=p.emp_code group by p.emp_code order by p.emp_code";
		}

		try {
			con = DBConnectionUtil.getConnection();
			stat = con.createStatement();
			rs = stat.executeQuery(str3);
			rs.getMetaData().getColumnCount();
			rs.getRow();
			int k = 0;
			while (rs.next()) {
				k += 1;
				String str4 = new String(rs.getString(1));
				String str5 = new String(rs.getString(2));
				String str6 = new String(rs.getString(3));
				String str7 = new String(rs.getString(4));
				String str8 = new String(rs.getString(5));
				String str9 = new String(rs.getString(6));
				String str10 = new String(rs.getString(7));
				String str11 = new String(rs.getString(8));

				model.addRow(new Object[] { Integer.valueOf(k), str4.toUpperCase(), str5.toUpperCase(),
						str6.toUpperCase(), str7, str8.trim(), str9.trim(), str10.trim(), str11.trim() });
			}
		} catch (Exception localException) {
			JOptionPane.showMessageDialog(f, localException.getMessage().toString());
		}
	}

	public void newRecord() {
		if ((searchtf.getText().length() < 3) || (searchtf.getText().length() > 3)) {
			JOptionPane.showMessageDialog(f, "Not a Valid Emp_Code.");
		} else {
			try {
				con = DBConnectionUtil.getConnection();
				PreparedStatement localPreparedStatement = con
						.prepareStatement("Insert into PHONE values(?,?,?,?,?,?,?)");
				localPreparedStatement.setString(1, searchtf.getText());
				localPreparedStatement.setString(2, " ");
				localPreparedStatement.setString(3, " ");
				localPreparedStatement.setString(4, " ");
				localPreparedStatement.setString(5, " ");
				localPreparedStatement.setString(6, " ");
				localPreparedStatement.setString(7, " ");
				int i = localPreparedStatement.executeUpdate();

				if (i > 0) {
					model.addRow(
							new Object[] { Integer.valueOf(Integer.valueOf(tb.getRowCount()).intValue() + 1),
									searchtf.getText(), "", "", "0", "", "", "", "" });
				}
			} catch (Exception localException) {
				JOptionPane.showMessageDialog(f, localException.getMessage().toString());
			}
		}
	}

	public void saveRecord() {
		int i = tb.getSelectedRow();

		String str1 = String.valueOf(model.getValueAt(i, 1));
		String str2 = String.valueOf(model.getValueAt(i, 4));
		String str3 = String.valueOf(model.getValueAt(i, 5)) + " ";
		String str4 = String.valueOf(model.getValueAt(i, 6)) + " ";
		String str5 = String.valueOf(model.getValueAt(i, 7)) + " ";
		String str6 = String.valueOf(model.getValueAt(i, 8)) + " ";
		//String str7 = emptb.getUserType(empCodeString);
		String str7 = SessionUtil.getLoginInfo().getUserType();

		PreparedStatement localPreparedStatement = null;
		if (str7.equalsIgnoreCase("Admin")) {
			try {
				con = DBConnectionUtil.getConnection();
				localPreparedStatement = con.prepareStatement("update emp_status set factor=? where emp_code=? ");
				localPreparedStatement.setString(1, str2);
				localPreparedStatement.setString(2, str1);
				int j = localPreparedStatement.executeUpdate();
				if (j > 0) {
					JOptionPane.showMessageDialog(f, "Factor Updated.");
				}

				localPreparedStatement = con
						.prepareStatement("update phone set unit=?, ext_no=?, personal=?, remarks=? where emp_code=? ");
				localPreparedStatement.setString(1, str3);
				localPreparedStatement.setString(2, str4);
				localPreparedStatement.setString(3, str5);
				localPreparedStatement.setString(4, str6);
				localPreparedStatement.setString(5, str1);
				j = localPreparedStatement.executeUpdate();
				if (j > 0) {
					JOptionPane.showMessageDialog(f, "Record Updated.");
				}
			} catch (Exception localException1) {
				JOptionPane.showMessageDialog(f, localException1.getMessage().toString());
			}
		}
		if (str7.equalsIgnoreCase("User")) {
			if (empcodestr.equalsIgnoreCase(str1)) {
				try {
					con = DBConnectionUtil.getConnection(); 
					localPreparedStatement = con.prepareStatement(
							"update phone set unit=?, ext_no=?, personal=?, remarks=? where emp_code=? ");
					localPreparedStatement.setString(1, str3);
					localPreparedStatement.setString(2, str4);
					localPreparedStatement.setString(3, str5);
					localPreparedStatement.setString(4, str6);
					localPreparedStatement.setString(5, str1);
					int k = localPreparedStatement.executeUpdate();
					if (k > 0) {
						JOptionPane.showMessageDialog(f, "Record Updated.");
					}
				} catch (Exception localException2) {
					JOptionPane.showMessageDialog(f, localException2.getMessage().toString());
				}

			} else {
				JOptionPane.showMessageDialog(f, "You can only change your own Details.");
			}
		}
	}

	public void setUpUnitColumn(JTable paramJTable, TableColumn paramTableColumn) {
		unitcomboBox.setEditable(false);
		unitcomboBox.removeAllItems();
		paramTableColumn.setCellEditor(new javax.swing.DefaultCellEditor(unitcomboBox));

		try {
			con = DBConnectionUtil.getConnection();
			PreparedStatement localPreparedStatement = con.prepareStatement("select UNIT from HR_UNITSETUP");
			rs = localPreparedStatement.executeQuery();
			while (rs.next()) {
				String str = rs.getString(1);
				unitcomboBox.addItem(str);
			}
		} catch (Exception localException) {
			JOptionPane.showMessageDialog(f, localException.getMessage().toString());
		}
	}

	public class myPrint extends Thread implements Runnable {
		public myPrint() {
		}

		public void run() {
			String str = null;
			if (ma1.getState() == true) {
				str = "Current Employee's Factor & Contact";
			}
			if (ma2.getState() == true) {
				str = "Ex. Employee's Factor & Contacts";
			}
			try {
				java.text.MessageFormat localMessageFormat1 = new java.text.MessageFormat(str);
				java.text.MessageFormat localMessageFormat2 = new java.text.MessageFormat("- {0} -");
				tb.print(javax.swing.JTable.PrintMode.FIT_WIDTH, localMessageFormat1, localMessageFormat2);
			} catch (java.awt.print.PrinterException localPrinterException) {
				System.err.println("Error printing: " + localPrinterException.getMessage());
			}
		}
	}

	static JTextField sktf = new JTextField();
	int row = 0;

	public void getCode(JTextField paramJTextField) {
		row = tb.getSelectedRow();
		sktf = paramJTextField;
		if ((row != -1) ||

		(row >= 0)) {
			codestr = String.valueOf(model.getValueAt(row, 1));
			sktf.setText(codestr);
			paramJTextField = sktf;
		}
	}

	public void actionPerformed(ActionEvent paramActionEvent) {
		if (paramActionEvent.getSource() == b4) {
			showRecord();
		}
		if (paramActionEvent.getSource() == addbut) {
			newRecord();
		}
		if (paramActionEvent.getSource() == savebut) {
			saveRecord();
		}
		if ((paramActionEvent.getSource() == b5) || (paramActionEvent.getSource() == searchtf)
				|| (paramActionEvent.getSource() == ma1) || (paramActionEvent.getSource() == ma2)) {
			showRecord();
		}
		if (paramActionEvent.getSource() == ma3) {
			saveRecord();
		}
		if (paramActionEvent.getSource() == ma4) {
			EmpList.myPrint localmyPrint = new EmpList.myPrint();
			localmyPrint.start();
		}
		if (paramActionEvent.getSource() == ma5) {
			f.setVisible(false);
		}
	}

	public void keyPressed(java.awt.event.KeyEvent paramKeyEvent) {
		int i = paramKeyEvent.getKeyCode();

		if (i == 27) {
			f.setVisible(false);
		}
	}

	public void keyReleased(java.awt.event.KeyEvent paramKeyEvent) {
	}

	public void keyTyped(java.awt.event.KeyEvent paramKeyEvent) {
	}

	public void mousePressed(MouseEvent paramMouseEvent) {
	}

	public void mouseReleased(MouseEvent paramMouseEvent) {
		if (paramMouseEvent.getSource() == tb) {
			getCode(sktf);
		}
	}

	public void mouseClicked(MouseEvent paramMouseEvent) {
	}

	public void mouseExited(MouseEvent paramMouseEvent) {
	}

	public void mouseEntered(MouseEvent paramMouseEvent) {
	}

	public void tableChanged(javax.swing.event.TableModelEvent paramTableModelEvent) {
		tb.setForeground(Color.blue);
	}
}