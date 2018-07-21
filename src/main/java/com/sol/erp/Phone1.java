package com.sol.erp;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import com.sol.erp.ui.custom.SolCellModel;
import com.sol.erp.ui.custom.SolTableModel;
import com.sol.erp.util.DBConnectionUtil;

public class Phone1 implements java.awt.event.ActionListener, java.awt.event.MouseListener, java.awt.event.ItemListener,
		javax.swing.event.TableModelListener, java.awt.event.KeyListener {
	
	Connection con;
	Statement stat;
	ResultSet rs;
	int affected = 0;

	String[] columnNames = { "Sl No", "Name", "Company", "Unit", "Ext", "Personal", "Remarks" };
	final Object[][] data = new Object[0][];

	javax.swing.JInternalFrame f = new javax.swing.JInternalFrame("Office Phone Diary", true, true, true, true);
	JLabel l1 = new JLabel("Name");
	JLabel l2 = new JLabel("Company");
	JLabel l3 = new JLabel("Unit No");
	JLabel l4 = new JLabel("Office Ext No");
	JLabel l5 = new JLabel("Personal No");
	JLabel l6 = new JLabel("Remarks");

	JLabel codelabel = new JLabel("Emp Code");

	JComboBox tf1 = new JComboBox();
	JComboBox tf2 = new JComboBox();
	JComboBox tf3 = new JComboBox();
	JComboBox tf4 = new JComboBox();
	JTextField tf5 = new JTextField();
	JTextField tf6 = new JTextField();

	JTextField codetf = new JTextField(5);
	JTextField nametf = new JTextField(16);

	JButton b1 = new JButton("Add Contact");
	JButton b2 = new JButton("Update");
	JButton b3 = new JButton("Delete");
	JButton b4 = new JButton("Show All");
	JButton b5 = new JButton("Search");

	javax.swing.JMenuBar mb = new javax.swing.JMenuBar();
	javax.swing.JMenu m1 = new javax.swing.JMenu("Diary");
	javax.swing.JMenuItem mi1 = new javax.swing.JMenuItem("Close");

	DefaultTableModel model = new DefaultTableModel(data, columnNames);
	final JTable tb = new JTable(model);
	javax.swing.JScrollPane sp = new javax.swing.JScrollPane(tb);

	javax.swing.table.TableColumnModel tcm = tb.getColumnModel();
	TableColumn tc = tcm.getColumn(3);

	JPanel p1 = new JPanel();
	JPanel p2 = new JPanel();
	JPanel buttonpanel1 = new JPanel();
	JPanel buttonpanel2 = new JPanel();

	JTextField searchtf = new JTextField(15);
	javax.swing.JRadioButton rb1 = new javax.swing.JRadioButton("Name", true);
	javax.swing.JRadioButton rb2 = new javax.swing.JRadioButton("Office Ext", false);
	javax.swing.JRadioButton rb3 = new javax.swing.JRadioButton("Personal", false);
	javax.swing.ButtonGroup grp = new javax.swing.ButtonGroup();

	javax.swing.border.Border bor1 = javax.swing.BorderFactory.createTitledBorder("User Option");
	java.awt.Font fo = new java.awt.Font("Tahoma", 1, 11);

	java.awt.Toolkit tk = java.awt.Toolkit.getDefaultToolkit();

	java.awt.Dimension ss = tk.getScreenSize();
	java.awt.Dimension fs = f.getSize();

	TableColumn col1 = tb.getColumnModel().getColumn(0);
	TableColumn col2 = tb.getColumnModel().getColumn(1);
	TableColumn col3 = tb.getColumnModel().getColumn(2);
	TableColumn col4 = tb.getColumnModel().getColumn(3);
	TableColumn col5 = tb.getColumnModel().getColumn(4);
	TableColumn col6 = tb.getColumnModel().getColumn(5);
	TableColumn col7 = tb.getColumnModel().getColumn(6);

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
				setBackground(new Color(200, 220, 240));
				setForeground(Color.darkGray);

			} else {
				setBackground(Color.white);
				setForeground(Color.darkGray);
			}

			if (paramInt2 == 0) {
				setHorizontalAlignment(0);
			}
			if ((paramInt2 == 1) || (paramInt2 == 2)) {
				setHorizontalAlignment(2);
			}
			if (paramInt2 == 3) {
				setHorizontalAlignment(2);
			}

			String str1 = String.valueOf(model.getValueAt(paramInt1, 4));
			String str2 = String.valueOf(model.getValueAt(paramInt1, 5));

			if (str1.length() < 2) {
				setBackground(Color.red);
				setForeground(Color.white);
			}
			if (str2.length() < 2) {
				setBackground(Color.pink);
				setForeground(Color.black);
			}

			super.getTableCellRendererComponent(paramJTable, paramObject, paramBoolean1, paramBoolean2, paramInt1,
					paramInt2);

			return this;
		}
	}

	Phone1.ColoredTableCellRenderer skr = new Phone1.ColoredTableCellRenderer();
	SolCellModel skc = new SolCellModel();
	String code;
	String usertype;

	public void px() {
		java.awt.Container localContainer = f.getContentPane();
		localContainer.setLayout(new java.awt.BorderLayout());
		p1.setLayout(new java.awt.GridLayout(2, 6, 2, 2));
		p2.setLayout(new java.awt.BorderLayout());
		p2.setLayout(new java.awt.BorderLayout());

		p1.add(l1);
		p1.add(tf1);
		tf1.setFont(fo);
		tf1.setEditable(false);
		p1.add(l2);
		p1.add(tf2);
		tf2.setFont(fo);
		p1.add(l3);
		p1.add(tf3);
		tf3.setFont(fo);
		p1.add(l4);
		p1.add(tf4);
		tf4.setFont(fo);
		tf4.setEditable(true);
		p1.add(l5);
		p1.add(tf5);
		tf5.setFont(fo);
		p1.add(l6);
		p1.add(tf6);
		tf6.setFont(fo);

		codetf.setFont(fo);
		codetf.setForeground(Color.red);
		nametf.setFont(fo);
		nametf.setForeground(Color.red);

		tf2.addItem("STRUCTURES Online");
		tf2.addItem("Atecture");
		tf2.addItem("ROARK");

		/*
		tf3.addItem("GA");
		tf3.addItem("GB");
		tf3.addItem("1A");
		tf3.addItem("1B");
		tf3.addItem("2A");
		tf3.addItem("2B");
		tf3.addItem("3A");
		tf3.addItem("3B");
		tf3.addItem("4A");
		tf3.addItem("4B");
		tf3.addItem("5A");
		tf3.addItem("5B");
		*/
		
		SolTableModel.decorateTable(tb);
		tb.setShowGrid(false);

		col1.setPreferredWidth(50);
		col2.setPreferredWidth(200);
		col3.setPreferredWidth(150);
		col4.setPreferredWidth(50);
		col5.setPreferredWidth(50);
		col6.setPreferredWidth(150);
		col7.setPreferredWidth(150);

		col1.setCellRenderer(skc);
		col2.setCellRenderer(skc);
		col3.setCellRenderer(skc);
		col4.setCellRenderer(skc);
		col5.setCellRenderer(skr);
		col6.setCellRenderer(skr);
		col7.setCellRenderer(skc);

		b1.setMnemonic(65);
		b2.setMnemonic(85);
		b3.setMnemonic(68);
		b4.setMnemonic(87);
		b5.setMnemonic(83);

		grp.add(rb1);
		grp.add(rb2);
		grp.add(rb3);

		mb.add(m1);
		m1.add(mi1);

		buttonpanel1.add(rb1);
		buttonpanel1.add(rb2);
		buttonpanel1.add(rb3);
		buttonpanel1.add(searchtf);
		searchtf.addActionListener(this);
		buttonpanel1.add(b5);
		b5.addActionListener(this);

		buttonpanel1.setPreferredSize(new java.awt.Dimension(40, 40));
		buttonpanel1.setBackground(new Color(160, 160, 190));

		buttonpanel2.add(codelabel);
		buttonpanel2.add(codetf);
		codetf.setEditable(false);
		buttonpanel2.add(nametf);
		nametf.setEditable(false);

		mi1.addActionListener(this);
		buttonpanel2.add(b1);
		b1.addActionListener(this);
		buttonpanel2.add(b2);
		b2.addActionListener(this);
		buttonpanel2.add(b3);
		b3.addActionListener(this);
		buttonpanel2.add(b4);
		b4.addActionListener(this);

		tf2.addItemListener(this);

		tb.addMouseListener(this);
		tb.addKeyListener(this);
		model.addTableModelListener(this);

		p2.add(buttonpanel1, "North");
		p2.add(sp, "Center");
		p2.add(buttonpanel2, "South");

		localContainer.add(p1, "North");
		localContainer.add(p2, "Center");

		f.setJMenuBar(mb);
		f.setSize(800, 450);
		f.setLocation((ss.width - fs.width) / 10, (ss.height - fs.height) / 8);
		f.setVisible(true);
		
		setUpUnitColumn();
	}
	
	public void setUpUnitColumn() {
		tf3.setEditable(false);
		tf3.removeAllItems();

		try {
			con = DBConnectionUtil.getConnection();
			PreparedStatement localPreparedStatement = con.prepareStatement("select UNIT from HR_UNITSETUP");
			rs = localPreparedStatement.executeQuery();
			while (rs.next()) {
				String str = rs.getString(1);
				tf3.addItem(str);
			}
		} catch (Exception localException) {
			JOptionPane.showMessageDialog(f, localException.getMessage().toString());
		}
	}

	public void paramUser(String paramString1, String paramString2) {

		code = paramString1;
		usertype = paramString2;

		codetf.setText(code);

		if (usertype.equalsIgnoreCase("User")) {
			b2.setEnabled(false);
			b3.setEnabled(false);
		}
		empName();
	}

	public void empName() {
		model.setNumRows(0);
		tf1.removeAllItems();
		try {
			con = DBConnectionUtil.getConnection();
			stat = con.createStatement();
			rs = stat.executeQuery("select FIRST_NAME, MIDDLE_NAME, LAST_NAME from HREMP_JOIN where emp_code='"
					+ codetf.getText() + "' ");
			rs.getMetaData().getColumnCount();
			while (rs.next()) {
				String str1 = new String(rs.getString(1));
				String str2 = new String(rs.getString(2));
				String str3 = new String(rs.getString(3));

				nametf.setText(str1 + " " + str2 + " " + str3);
				tf1.addItem(str1 + " " + str2 + " " + str3);
			}
		} catch (Exception localException) {
		}
	}

	public void showRecord() {
		model.setNumRows(0);
		try {
			con = DBConnectionUtil.getConnection();
			stat = con.createStatement();
			rs = stat.executeQuery("select * from phone");
			rs.getMetaData().getColumnCount();
			while (rs.next()) {
				String str1 = new String(rs.getString(1));
				String str2 = new String(rs.getString(2));
				String str3 = new String(rs.getString(3));
				String str4 = new String(rs.getString(4));
				String str5 = new String(rs.getString(5));
				String str6 = new String(rs.getString(6));
				String str7 = new String(rs.getString(7));
				model.addRow(new Object[] { str1, str2, str3, str4, str5, str6, str7 });
			}
		} catch (Exception localException) {
		}
	}

	public void empList() {
		tf1.removeAllItems();
		try {
			con = DBConnectionUtil.getConnection();
			stat = con.createStatement();
			rs = stat.executeQuery("select emp_name from emp_register");
			rs.getMetaData().getColumnCount();
			while (rs.next()) {
				String str = new String(rs.getString(1));
				tf1.addItem(str);
			}
		} catch (Exception localException) {
		}
	}

	public void extList() {
		tf4.removeAllItems();
		try {
			con = DBConnectionUtil.getConnection();
			stat = con.createStatement();
			rs = stat.executeQuery("select ext_no from extension where company='" + tf2.getSelectedItem() + "' ");
			rs.getMetaData().getColumnCount();
			while (rs.next()) {
				String str = new String(rs.getString(1));
				tf4.addItem(str);
			}
		} catch (Exception localException) {
		}
	}

	public void showNameLike() {
		String str1 = searchtf.getText() + "%";
		try {
			con = DBConnectionUtil.getConnection();
			stat = con.createStatement();
			rs = stat.executeQuery("select * from phone where name like '" + str1 + "' ");
			rs.getMetaData().getColumnCount();
			while (rs.next()) {
				String str2 = new String(rs.getString(1));
				String str3 = new String(rs.getString(2));
				String str4 = new String(rs.getString(3));
				String str5 = new String(rs.getString(4));
				String str6 = new String(rs.getString(5));
				String str7 = new String(rs.getString(6));
				String str8 = new String(rs.getString(7));

				model.addRow(new Object[] { str2, str3, str4, str5, str6, str7, str8 });
			}
		} catch (Exception localException) {
		}
	}

	public void showOfficeLike() {
		String str1 = searchtf.getText() + "%";
		try {
			con = DBConnectionUtil.getConnection();
			stat = con.createStatement();
			rs = stat.executeQuery("select * from phone where ext_no like '" + str1 + "' ");
			rs.getMetaData().getColumnCount();
			while (rs.next()) {
				String str2 = new String(rs.getString(1));
				String str3 = new String(rs.getString(2));
				String str4 = new String(rs.getString(3));
				String str5 = new String(rs.getString(4));
				String str6 = new String(rs.getString(5));
				String str7 = new String(rs.getString(6));
				String str8 = new String(rs.getString(7));

				model.addRow(new Object[] { str2, str3, str4, str5, str6, str7, str8 });
			}
		} catch (Exception localException) {
		}
	}

	public void showPersonalLike() {
		String str1 = searchtf.getText() + "%";
		try {
			con = DBConnectionUtil.getConnection();
			stat = con.createStatement();
			rs = stat.executeQuery("select * from phone where personal like '" + str1 + "' ");
			rs.getMetaData().getColumnCount();
			while (rs.next()) {
				String str2 = new String(rs.getString(1));
				String str3 = new String(rs.getString(2));
				String str4 = new String(rs.getString(3));
				String str5 = new String(rs.getString(4));
				String str6 = new String(rs.getString(5));
				String str7 = new String(rs.getString(6));
				String str8 = new String(rs.getString(7));

				model.addRow(new Object[] { str2, str3, str4, str5, str6, str7, str8 });
			}
		} catch (Exception localException) {
		}
	}

	public void showSelectedRecord() {
		int i = tb.getSelectedRow();
		tf1.removeAllItems();
		tf1.addItem(String.valueOf(model.getValueAt(i, 1)));
		tf2.setSelectedItem(String.valueOf(model.getValueAt(i, 2)));
		tf3.setSelectedItem(String.valueOf(model.getValueAt(i, 3)));
		tf4.setSelectedItem(String.valueOf(model.getValueAt(i, 4)));
		tf5.setText(String.valueOf(model.getValueAt(i, 5)));
		tf6.setText(String.valueOf(model.getValueAt(i, 6)));

		String str = String.valueOf(model.getValueAt(i, 1));

		if (str.equalsIgnoreCase(nametf.getText())) {
			b2.setEnabled(true);
		} else {
			b2.setEnabled(false);
		}
	}

	int sl = 0;

	public void maxSL() {
		try {
			con = DBConnectionUtil.getConnection();
			stat = con.createStatement();
			rs = stat.executeQuery("select max(sl_no) from phone");
			while (rs.next()) {
				String str = new String(rs.getString(1));
				sl = (Integer.parseInt(str) + 1);
			}
		} catch (Exception localException) {
			System.out.println(localException);
		}
	}

	public void updateRecord() {
		int i = tb.getSelectedRow();
		String str = String.valueOf(model.getValueAt(i, 0));
		int j = Integer.parseInt(str);
		try {
			con = DBConnectionUtil.getConnection();
			stat = con.createStatement();
			affected = stat.executeUpdate("update phone set unit='" + tf3.getSelectedItem() + "', ext_no='"
					+ tf4.getSelectedItem() + "', personal='" + tf5.getText() + "',remarks='" + tf6.getText()
					+ "' where sl_no=" + j + " ");

			if (affected > 0) {
				model.setValueAt(tf3.getSelectedItem(), i, 3);
				model.setValueAt(tf4.getSelectedItem(), i, 4);
				model.setValueAt(tf5.getText(), i, 5);
				model.setValueAt(tf6.getText(), i, 6);
			}
		} catch (Exception localException) {
			javax.swing.JOptionPane.showMessageDialog(f, localException.getMessage().toString());
		}
	}

	public void actionPerformed(java.awt.event.ActionEvent paramActionEvent) {
		if ((paramActionEvent.getSource() == b1) || (paramActionEvent.getSource() == tf4)) {
			maxSL();
			try {
				con = DBConnectionUtil.getConnection();
				stat = con.createStatement();
				stat.executeUpdate("Insert into phone values('" + sl + "','" + tf1.getSelectedItem() + "','"
						+ tf2.getSelectedItem() + "','" + tf3.getSelectedItem() + "','" + tf4.getSelectedItem() + "','"
						+ tf5.getText() + "','" + tf6.getText() + "') ");
			} catch (Exception localException1) {
				System.out.println(localException1);
			}
			model.addRow(new Object[] { String.valueOf(sl), tf1.getSelectedItem(), tf2.getSelectedItem(),
					tf3.getSelectedItem(), tf4.getSelectedItem(), tf5.getText(), tf6.getText() });
			tf5.setText("");
			tf6.setText("");
			tf1.requestFocus();
		}

		if (paramActionEvent.getSource() == b4) {
			showRecord();
		}

		if ((paramActionEvent.getSource() == b5) || (paramActionEvent.getSource() == searchtf)) {
			if (rb1.isSelected()) {
				model.setNumRows(0);
				showNameLike();
			}
			if (rb2.isSelected()) {
				model.setNumRows(0);
				showOfficeLike();
			}
			if (rb3.isSelected()) {
				model.setNumRows(0);
				showPersonalLike();
			}
		}

		if (paramActionEvent.getSource() == b2) {
			updateRecord();
		}

		if (paramActionEvent.getSource() == b3) {
			int i = tb.getSelectedRow();
			String str = String.valueOf(model.getValueAt(i, 0));
			int j = Integer.parseInt(str);
			try {
				con = DBConnectionUtil.getConnection();
				stat = con.createStatement();
				stat.executeUpdate("delete from phone where sl_no=" + j + " ");
			} catch (Exception localException2) {
				System.out.println(localException2);
			}
			int k = tb.getSelectedRow();
			model.removeRow(k);
		}

		if (paramActionEvent.getSource() == mi1) {
			f.setVisible(false);
		}
	}

	public void itemStateChanged(java.awt.event.ItemEvent paramItemEvent) {
		if (paramItemEvent.getSource() == tf2) {
			extList();
		}
	}

	public void mousePressed(MouseEvent paramMouseEvent) {
		showSelectedRecord();
	}

	public void mouseReleased(MouseEvent paramMouseEvent) {
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

	public void keyPressed(java.awt.event.KeyEvent paramKeyEvent) {
		showSelectedRecord();
	}

	public void keyReleased(java.awt.event.KeyEvent paramKeyEvent) {
	}

	public void keyTyped(java.awt.event.KeyEvent paramKeyEvent) {
	}

}
