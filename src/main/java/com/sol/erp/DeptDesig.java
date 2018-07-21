package com.sol.erp;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.sol.erp.ui.custom.SolProgressMonitor;
import com.sol.erp.ui.custom.SolTableModel;
import com.sol.erp.util.DBConnectionUtil;

public class DeptDesig implements java.awt.event.ActionListener, java.awt.event.MouseListener,
		javax.swing.event.ListSelectionListener {
	
	SolProgressMonitor prmonitor = new SolProgressMonitor();

	Connection con;

	Statement stat;
	ResultSet rs;
	int affected = 0;

	String[] head1 = { " ", "Desig", "Salary" };
	Object[][] data1 = new Object[0][];

	String[] head2 = { "Dept.", "Desig", "Salary", " " };
	Object[][] data2 = new Object[0][];

	JFrame f = new JFrame();
	JTabbedPane tabbedpane = new JTabbedPane();
	JPanel tab1 = new JPanel();
	JPanel tab2 = new JPanel();

	JLabel l1 = new JLabel("Company Id");
	JLabel l2 = new JLabel("Branch Code");
	JLabel l3 = new JLabel("DEPT.");

	JTextField tf1 = new JTextField("C01", 4);
	JTextField tf2 = new JTextField("0", 4);

	DefaultListModel listmodel = new DefaultListModel();
	JList deptlist = new JList(listmodel);
	JScrollPane deptsp = new JScrollPane(deptlist);

	DefaultTableModel model1 = new DefaultTableModel(data1, head1);
	JTable tb1 = new JTable(model1) {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public Class<? extends Object> getColumnClass(int paramAnonymousInt) {
			return getValueAt(0, paramAnonymousInt).getClass();
		}

		public boolean isCellEditable(int paramAnonymousInt1, int paramAnonymousInt2) {
			int i = convertColumnIndexToModel(paramAnonymousInt2);
			return i < 1;
		}
	};
	JScrollPane sp1 = new JScrollPane(tb1);

	DefaultTableModel model2 = new DefaultTableModel(data2, head2);
	JTable tb2 = new JTable(model2) {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public Class<? extends Object> getColumnClass(int paramAnonymousInt) {
			return getValueAt(0, paramAnonymousInt).getClass();
		}

		public boolean isCellEditable(int paramAnonymousInt1, int paramAnonymousInt2) {
			int i = convertColumnIndexToModel(paramAnonymousInt2);
			return i >= 3;
		}
	};
	JScrollPane sp2 = new JScrollPane(tb2);

	JButton b1 = new JButton("Setup Desig");
	JButton b2 = new JButton("Close");
	JButton b3 = new JButton("Refresh Designation List");

	JPanel northpanel = new JPanel();
	JPanel southpanel = new JPanel();

	JPanel mainpanel = new JPanel();
	java.awt.Font fo = new java.awt.Font("Courier New", 0, 11);

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

			setHorizontalAlignment(4);

			if (paramInt1 % 2 == 0) {
				setBackground(new Color(240, 240, 240));
				setForeground(Color.darkGray);

			} else {
				setBackground(new Color(230, 230, 230));
				setForeground(Color.darkGray);
			}

			super.getTableCellRendererComponent(paramJTable, paramObject, paramBoolean1, paramBoolean2, paramInt1,
					paramInt2);

			return this;
		}
	}

	DeptDesig.ColoredTableCellRenderer skr = new DeptDesig.ColoredTableCellRenderer();

	public JPanel DesignFrame() {
		java.awt.Container localContainer = f.getContentPane();
		localContainer.setLayout(new java.awt.BorderLayout());
		mainpanel.setLayout(new java.awt.BorderLayout());
		northpanel.setLayout(new java.awt.FlowLayout(0));
		southpanel.setLayout(new java.awt.FlowLayout(2));
		tab1.setLayout(new java.awt.BorderLayout());
		tab2.setLayout(new java.awt.BorderLayout());

		tabbedpane.add("Desig. List", tab1);
		tabbedpane.add("Desig_Dept", tab2);
		tabbedpane.setBorder(javax.swing.BorderFactory.createBevelBorder(1));

		northpanel.add(l1);
		northpanel.add(tf1);
		northpanel.add(l2);
		northpanel.add(tf2);
		northpanel.add(b3);

		southpanel.add(b1);
		southpanel.add(b2);

		SolTableModel.decorateTable(tb1);
		tb1.setAutoResizeMode(0);
		tb1.getColumnModel().getColumn(0).setPreferredWidth(25);
		tb1.getColumnModel().getColumn(1).setPreferredWidth(255);
		tb1.getColumnModel().getColumn(2).setPreferredWidth(150);

		tb1.getColumnModel().getColumn(1).setCellRenderer(skr);
		tb1.getColumnModel().getColumn(2).setCellRenderer(skr);

		SolTableModel.decorateTable(tb2);
		tb2.setAutoResizeMode(0);
		tb2.getColumnModel().getColumn(0).setPreferredWidth(90);
		tb2.getColumnModel().getColumn(1).setPreferredWidth(190);
		tb2.getColumnModel().getColumn(2).setPreferredWidth(100);
		tb2.getColumnModel().getColumn(3).setPreferredWidth(25);

		tb2.getColumnModel().getColumn(0).setCellRenderer(skr);
		tb2.getColumnModel().getColumn(1).setCellRenderer(skr);
		tb2.getColumnModel().getColumn(2).setCellRenderer(skr);

		tb1.setFont(fo);
		tb2.setFont(fo);
		tf1.setFont(fo);
		tf2.setFont(fo);

		tab1.add(sp1, "Center");
		tab2.add(sp2, "Center");

		deptsp.setPreferredSize(new java.awt.Dimension(110, 110));
		mainpanel.add(deptsp, "West");
		mainpanel.add(northpanel, "North");
		mainpanel.add(tabbedpane, "Center");
		mainpanel.add(southpanel, "South");

		localContainer.add(mainpanel, "Center");

		tf1.addActionListener(this);
		tf2.addActionListener(this);

		b1.addActionListener(this);
		b2.addActionListener(this);
		b3.addActionListener(this);

		tb1.addMouseListener(this);
		tb2.addMouseListener(this);

		deptlist.addListSelectionListener(this);

		f.getRootPane().setDefaultButton(b3);

		return mainpanel;
	}

	public void showDeptDesig() {
		DesignFrame();

		f.setSize(600, 390);
		f.setResizable(false);
		f.setLocationRelativeTo(null);
		f.setVisible(true);
	}

	public void deptList() {
		listmodel.clear();
		String str1 = tf1.getText();
		String str2 = tf2.getText();

		try {
			con = DBConnectionUtil.getConnection();
			stat = con.createStatement();
			rs = stat
					.executeQuery("select distinct(depts) from HRCOMPANY_DEPTS where args = 'true' and company_id='"
							+ str1 + "' and br_code='" + str2 + "' ");

			while (rs.next()) {
				String str3 = new String(rs.getString(1));
				listmodel.addElement(str3);
			}
		} catch (Exception localException) {
			JOptionPane.showMessageDialog(f, localException.getMessage().toString());
		}
	}

	public void desigList() {
		model1.setNumRows(0);
		String str1 = tf1.getText();
		String str2 = tf2.getText();

		try {
			con = DBConnectionUtil.getConnection();
			stat = con.createStatement();
			rs = stat.executeQuery(
					"select distinct(positions) from HRCOMPANY_POSITIONS where args = 'true' and company_id='" + str1
							+ "' and br_code='" + str2 + "' ");

			while (rs.next()) {
				String str3 = new String(rs.getString(1));
				model1.addRow(new Object[] { new Boolean(false), str3, "" });
			}
		} catch (Exception localException) {
			JOptionPane.showMessageDialog(f, localException.getMessage().toString());
		}
	}

	public void desigInfo() {
		String str1 = tf1.getText();
		String str2 = tf2.getText();
		String str3 = (String) deptlist.getSelectedValue();

		for (int i = 0; i < tb1.getRowCount(); i++) {
			String str4 = String.valueOf(model1.getValueAt(i, 1));
			try {
				stat = con.createStatement();
				rs = stat.executeQuery("select salary from HR_DEPT_DESIG where company_id='" + str1
						+ "' and branch_code='" + str2 + "' and dept='" + str3 + "' and desig='" + str4 + "' ");
				while (rs.next()) {
					String str5 = new String(rs.getString(1));

					model1.setValueAt(new Boolean(true), i, 0);
					model1.setValueAt(str5, i, 2);
				}
			} catch (Exception localException) {
			}
		}
	}

	public void deptDesigList2() {
		model2.setNumRows(0);
		String str1 = tf1.getText();
		String str2 = tf2.getText();
		String str3 = (String) deptlist.getSelectedValue();

		try {
			con = DBConnectionUtil.getConnection();
			stat = con.createStatement();
			rs = stat.executeQuery("select dept,desig,salary from HR_DEPT_DESIG where company_id='" + str1
					+ "' and branch_code='" + str2 + "' and Dept='" + str3 + "' ");

			while (rs.next()) {
				String str4 = new String(rs.getString(1));
				String str5 = new String(rs.getString(2));
				String str6 = new String(rs.getString(3));

				model2.addRow(new Object[] { str4, str5, str6, new Boolean(true) });
			}
		} catch (Exception localException) {
			JOptionPane.showMessageDialog(f, localException.getMessage().toString());
		}
	}

	public void saveRecord() {
		String str1 = tf1.getText();
		String str2 = tf2.getText();
		String str3 = (String) deptlist.getSelectedValue();

		prmonitor.showMonitor();
		prmonitor.setMIN(0);
		prmonitor.setMAX(tb1.getRowCount());
		prmonitor.setProgressMessage("Setting Up Designations...");
		prmonitor.setStatusMessage("Status : Processing...");
		prmonitor.getProgressBar().setForeground(new Color(0, 0, 150));

		for (int i = 0; i < tb1.getRowCount(); i++) {
			prmonitor.getProgressBar().setValue(i);
			prmonitor.runProgressMonitor();

			String str4 = String.valueOf(model1.getValueAt(i, 0));
			if (str4.equalsIgnoreCase("true")) {
				String str5 = String.valueOf(model1.getValueAt(i, 1));
				String str6 = String.valueOf(model1.getValueAt(i, 2));
				try {
					stat = con.createStatement();
					affected = stat.executeUpdate("insert into HR_DEPT_DESIG values('" + str1 + "','" + str2
							+ "', '" + str3 + "','" + str5 + "','" + str6 + "')");

					if (affected > 0) {
						model2.addRow(new Object[] { str3, str5, str6, new Boolean(true) });
						tabbedpane.setSelectedIndex(1);
					}
				} catch (Exception localException) {
					if (localException.getMessage().toString().equalsIgnoreCase("General error")) {
						JOptionPane.showMessageDialog(f, str5 + " is Allready Setup for " + str3);
					} else {
						JOptionPane.showMessageDialog(f, localException.getMessage().toString());
					}
				}
			}
		}
	}

	public void removeDesig() {
		int i = tb2.getSelectedRow();
		String str1 = tf1.getText();
		String str2 = tf2.getText();
		String str3 = String.valueOf(model2.getValueAt(i, 0));
		String str4 = String.valueOf(model2.getValueAt(i, 1));
		String str5 = String.valueOf(model2.getValueAt(i, 3));

		if (str5.equalsIgnoreCase("false")) {
			try {
				stat = con.createStatement();
				affected = stat.executeUpdate("delete from HR_DEPT_DESIG where company_id='" + str1
						+ "' and branch_code='" + str2 + "' and Dept='" + str3 + "' and desig='" + str4 + "'  ");

				if (affected > 0) {
					model2.removeRow(i);
					desigList();
				}
			} catch (Exception localException) {
				JOptionPane.showMessageDialog(f, localException.getMessage().toString());
			}
		}
	}

	public void actionPerformed(ActionEvent paramActionEvent) {
		if ((paramActionEvent.getSource() == tf1) || (paramActionEvent.getSource() == tf2)
				|| (paramActionEvent.getSource() == b3)) {
			deptList();
			desigList();
		}
		if (paramActionEvent.getSource() == b1) {
			saveRecord();
		}
		if (paramActionEvent.getSource() == b2) {
			f.setVisible(false);
		}
	}

	public void valueChanged(javax.swing.event.ListSelectionEvent paramListSelectionEvent) {
		if (paramListSelectionEvent.getSource() == deptlist) {
			if (tb1.getRowCount() < 0) {
				desigList();
			}

			deptDesigList2();
			desigInfo();
		}
	}

	public void mousePressed(MouseEvent paramMouseEvent) {
	}

	public void mouseReleased(MouseEvent paramMouseEvent) {
	}

	public void mouseEntered(MouseEvent paramMouseEvent) {
	}

	public void mouseExited(MouseEvent paramMouseEvent) {
	}

	public void mouseClicked(MouseEvent paramMouseEvent) {
		if (paramMouseEvent.getSource() == tb1) {
			int i = tb1.getSelectedRow();
			int j = tb1.getSelectedColumn();

			int k = deptlist.getSelectedIndex();

			String str1 = String.valueOf(model1.getValueAt(i, j));

			String str2 = "0";
			if (k < 0) {
				JOptionPane.showMessageDialog(f, "Select a Department.");

			} else if ((j == 0) && (k >= 0)) {
				if (str1.equalsIgnoreCase("true")) {
					str2 = JOptionPane.showInputDialog(f, "Enter Salary");
					model1.setValueAt(str2, i, 2);
				} else {
					model1.setValueAt("0", i, 2);
				}
			}
		}

		if (paramMouseEvent.getSource() == tb2) {
			removeDesig();
		}
	}
}