package com.sol.erp;

import java.awt.event.MouseEvent;
import java.sql.ResultSet;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.sol.erp.ui.custom.SolCellModel;
import com.sol.erp.ui.custom.SolTableModel;
import com.sol.erp.util.DBConnectionUtil;
import com.sol.erp.util.SessionUtil;

public class DefReqMode
		implements java.awt.event.ActionListener, java.awt.event.ItemListener, java.awt.event.MouseListener {
	

	SolCellModel skc = new SolCellModel();

	java.sql.Connection con;
	java.sql.Statement stat;
	ResultSet rs;
	int affected = 0;

	String[] head = { " ", "Benefites", " Percent", "Amount", "P", "B" };
	Object[][] data = new Object[0][];

	String[] head1 = { " ", "Deductions", "Percent", "Amount", "P", "B" };
	Object[][] data1 = new Object[0][];

	JFrame f = new JFrame("Define Salary Structures");
	DefaultTableModel model = new DefaultTableModel(data, head);
	JTable tb = new JTable(model) {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public Class<? extends Object> getColumnClass(int paramAnonymousInt) {
			return getValueAt(0, paramAnonymousInt).getClass();
		}

		public boolean isCellEditable(int paramAnonymousInt1, int paramAnonymousInt2) {
			int i = convertColumnIndexToModel(paramAnonymousInt2);
			return (i < 1) || (i >= 4);
		}
	};
	javax.swing.JScrollPane sp = new javax.swing.JScrollPane(tb);

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
			return (i < 1) || (i >= 4);
		}
	};
	javax.swing.JScrollPane sp1 = new javax.swing.JScrollPane(tb1);

	javax.swing.JSplitPane spliter = new javax.swing.JSplitPane(1, sp, sp1);

	JComboBox ch = new JComboBox();

	javax.swing.JLabel colabel = new javax.swing.JLabel("Company Id");
	javax.swing.JLabel brlabel = new javax.swing.JLabel("Br Code");

	JTextField coidtf = new JTextField(4);
	JTextField brcodetf = new JTextField(4);
	JTextField conametf = new JTextField(20);

	JButton defaultbut = new JButton("Default List", new javax.swing.ImageIcon("image/play.gif"));
	JButton savebut = new JButton("Save");
	JButton closebut = new JButton("Close");

	JPanel p1 = new JPanel();
	JPanel p2 = new JPanel();

	JPanel centerpanel = new JPanel();

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

			if (paramInt2 > 0) {
				setHorizontalAlignment(0);
			}

			if (paramInt1 % 2 == 0) {
				setBackground(new java.awt.Color(240, 240, 240));
				setForeground(java.awt.Color.darkGray);

			} else {
				setBackground(new java.awt.Color(230, 230, 230));
				setForeground(java.awt.Color.darkGray);
			}

			super.getTableCellRendererComponent(paramJTable, paramObject, paramBoolean1, paramBoolean2, paramInt1,
					paramInt2);

			return this;
		}
	}

	DefReqMode.ColoredTableCellRenderer skr = new DefReqMode.ColoredTableCellRenderer();

	java.awt.Font fo = new java.awt.Font("Tahoma", 1, 10);

	public void px() {
		java.awt.Container localContainer = f.getContentPane();
		localContainer.setLayout(new java.awt.BorderLayout());
		centerpanel.setLayout(new java.awt.GridLayout(1, 2, 5, 5));

		p1.setLayout(new java.awt.FlowLayout(0));
		p2.setLayout(new java.awt.FlowLayout(2));

		SolTableModel.decorateTable(tb);
		SolTableModel.decorateTable(tb1);

		tb.getColumnModel().getColumn(0).setPreferredWidth(40);
		tb.getColumnModel().getColumn(1).setPreferredWidth(170);
		tb.getColumnModel().getColumn(4).setPreferredWidth(30);
		tb.getColumnModel().getColumn(5).setPreferredWidth(30);
		tb.setShowVerticalLines(false);
		tb.setIntercellSpacing(new java.awt.Dimension(1, 1));

		tb1.getColumnModel().getColumn(0).setPreferredWidth(40);
		tb1.getColumnModel().getColumn(1).setPreferredWidth(170);
		tb1.getColumnModel().getColumn(4).setPreferredWidth(30);
		tb1.getColumnModel().getColumn(5).setPreferredWidth(30);

		tb1.setIntercellSpacing(new java.awt.Dimension(1, 1));

		for (int i = 1; i < 4; i++) {
			tb.getColumnModel().getColumn(i).setCellRenderer(skr);
			tb1.getColumnModel().getColumn(i).setCellRenderer(skr);
		}

		tb.setSelectionBackground(new java.awt.Color(220, 220, 220));
		tb.setSelectionForeground(java.awt.Color.darkGray);
		tb.setShowGrid(false);

		tb1.setSelectionBackground(new java.awt.Color(220, 220, 220));
		tb1.setSelectionForeground(java.awt.Color.darkGray);
		tb1.setShowGrid(false);

		coidtf.setFont(fo);
		brcodetf.setFont(fo);
		conametf.setFont(fo);

		p1.add(colabel);
		p1.add(coidtf);
		p1.add(brlabel);
		p1.add(brcodetf);
		p1.add(conametf);
		conametf.setEditable(false);
		p1.add(ch);
		p1.add(defaultbut);
		defaultbut.setMnemonic(68);
		conametf.setBorder(javax.swing.BorderFactory.createBevelBorder(1));

		coidtf.setText(SessionUtil.getLoginInfo().getCompanyId());
		brcodetf.setText(SessionUtil.getLoginInfo().getBranchCode());

		p2.add(savebut);
		savebut.setMnemonic(83);
		p2.add(closebut);
		closebut.setMnemonic(67);

		spliter.setDividerLocation(350);

		centerpanel.add(spliter);
		centerpanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Salary Structures"));

		localContainer.add(p1, "North");
		localContainer.add(centerpanel, "Center");
		localContainer.add(p2, "South");

		defaultbut.addActionListener(this);
		savebut.addActionListener(this);
		closebut.addActionListener(this);
		ch.addItemListener(this);

		tb.addMouseListener(this);
		tb1.addMouseListener(this);

		f.setLocation(100, 150);
		f.setSize(700, 420);
		f.setResizable(false);
		f.setLocationRelativeTo(null);
		f.setVisible(true);
		show();
		show();
		benefiteList();
		deductionList();
	}

	public void show() {
		String str1 = SessionUtil.getLoginInfo().getCompanyId();
		String str2 = SessionUtil.getLoginInfo().getBranchCode();
		try {
			con = DBConnectionUtil.getConnection();
			stat = con.createStatement();
			rs = stat.executeQuery("select distinct(REQ_MODES) from HRCOMPANY_REQ_MODES WHERE COMPANY_ID = '"
					+ str1 + "' and br_code='" + str2 + "' ");
			while (rs.next()) {
				String str3 = new String(rs.getString(1));
				ch.addItem(str3);
			}
		} catch (Exception localException) {
			System.out.println("Show : " + localException);
		}
	}

	public void benefiteList() {
		model.setNumRows(0);
		try {
			rs = stat.executeQuery("select distinct(SAL_BENEFITES) from HRCOMPANY_SAL_BENEFITES");
			while (rs.next()) {
				String str = new String(rs.getString(1));
				model.addRow(new Object[] { new Boolean(true), str.toUpperCase(), "0", "0", new Boolean(false),
						new Boolean(false) });
			}
		} catch (Exception localException) {
			System.out.println("Show : " + localException);
		}
	}

	public void deductionList() {
		model1.setNumRows(0);
		try {
			rs = stat.executeQuery("select distinct(SAL_DEDUCTIONS) from HRCOMPANY_SAL_DEDUCTIONS");
			while (rs.next()) {
				String str = new String(rs.getString(1));
				model1.addRow(new Object[] { new Boolean(true), str.toUpperCase(), "0", "0", new Boolean(false),
						new Boolean(false) });
			}
		} catch (Exception localException) {
			System.out.println("Show : " + localException);
		}
	}

	public void showCompanyBenefites() {
		model.setNumRows(0);
		try {
			rs = stat
					.executeQuery("select head, percent, amount, pref,base_pref from HRSALARY_STRUCT WHERE COMPANY_ID='"
							+ coidtf.getText() + "' and branch_code='" + brcodetf.getText()
							+ "' and req_mode='" + ch.getSelectedItem() + "' and type='BENEFITE'  ");
			while (rs.next()) {
				String str1 = new String(rs.getString(1));
				String str2 = new String(rs.getString(2));
				String str3 = new String(rs.getString(3));
				String str4 = new String(rs.getString(4));
				String str5 = new String(rs.getString(5));

				boolean bool = false;
				if (str5.equalsIgnoreCase("Basic")) {
					bool = true;
				} else {
					bool = false;
				}

				if (str4.equalsIgnoreCase("percent")) {
					model.addRow(new Object[] { new Boolean(true), str1.toUpperCase(), str2, str3,
							new Boolean(true), new Boolean(bool) });
				} else {
					model.addRow(new Object[] { new Boolean(true), str1.toUpperCase(), str2, str3,
							new Boolean(false), new Boolean(bool) });
				}
			}
		} catch (Exception localException) {
			System.out.println("Show : " + localException);
		}
	}

	public void showCompanyDeductions() {
		model1.setNumRows(0);
		try {
			rs = stat.executeQuery(
					"select head, percent, amount, pref, base_pref from HRSALARY_STRUCT WHERE COMPANY_ID='"
							+ coidtf.getText() + "' and branch_code='" + brcodetf.getText()
							+ "' and req_mode='" + ch.getSelectedItem() + "' and type='DEDUCT'  ");
			while (rs.next()) {
				String str1 = new String(rs.getString(1));
				String str2 = new String(rs.getString(2));
				String str3 = new String(rs.getString(3));
				String str4 = new String(rs.getString(4));
				String str5 = new String(rs.getString(5));

				boolean bool = false;
				if (str5.equalsIgnoreCase("Basic")) {
					bool = true;
				} else {
					bool = false;
				}

				if (str4.equalsIgnoreCase("percent")) {
					model1.addRow(new Object[] { new Boolean(true), str1.toUpperCase(), str2, str3,
							new Boolean(true), new Boolean(bool) });
				} else {
					model1.addRow(new Object[] { new Boolean(true), str1.toUpperCase(), str2, str3,
							new Boolean(false), new Boolean(bool) });
				}
			}
		} catch (Exception localException) {
			System.out.println("Show : " + localException);
		}
	}

	public void saveBenefites() {
		int i = tb.getRowCount();

		try {
			stat = con.createStatement();
			stat.executeQuery(
					"DELETE FROM HRSALARY_STRUCT where COMPANY_ID='" + coidtf.getText() + "' and branch_code='"
							+ brcodetf.getText() + "' and req_mode='" + ch.getSelectedItem() + "' ");
		} catch (Exception localException1) {
			System.out.println("Save Benefites [delete]: " + localException1);
		}

		for (int j = 0; j < i; j++) {
			String str1 = String.valueOf(model.getValueAt(j, 1));
			String str2 = "Benefite";
			String str3 = String.valueOf(model.getValueAt(j, 2));
			String str4 = String.valueOf(model.getValueAt(j, 3));
			String str5 = String.valueOf(model.getValueAt(j, 4));
			String str6 = String.valueOf(model.getValueAt(j, 5));
			String str7 = "0";
			String str8 = "0";

			if (str5.equalsIgnoreCase("true")) {
				str7 = "PERCENT";
			}
			if (str5.equalsIgnoreCase("false")) {
				str7 = "AMOUNT";
			}
			if (str6.equalsIgnoreCase("true")) {
				str8 = "BASIC";
			}
			if (str6.equalsIgnoreCase("false")) {
				str8 = "INHAND";
			}

			try {
				stat = con.createStatement();
				stat.executeUpdate("Insert into HRSALARY_STRUCT values('" + coidtf.getText() + "','"
						+ brcodetf.getText() + "','" + ch.getSelectedItem() + "','" + str1 + "','" + str2
						+ "','" + str3 + "','" + str4 + "','" + str7 + "','" + str8 + "') ");
			} catch (Exception localException2) {
				System.out.println("Save Benefites : " + localException2);
			}
		}
	}

	public void saveDeductions() {
		int i = tb1.getRowCount();

		for (int j = 0; j < i; j++) {
			String str1 = String.valueOf(model1.getValueAt(j, 1));
			String str2 = "Deduct";
			String str3 = String.valueOf(model1.getValueAt(j, 2));
			String str4 = String.valueOf(model1.getValueAt(j, 3));
			String str5 = String.valueOf(model1.getValueAt(j, 4));
			String str6 = String.valueOf(model1.getValueAt(j, 5));
			String str7 = "0";
			String str8 = "0";

			if (str5.equalsIgnoreCase("true")) {
				str7 = "PERCENT";
			}
			if (str5.equalsIgnoreCase("false")) {
				str7 = "AMOUNT";
			}
			if (str6.equalsIgnoreCase("true")) {
				str8 = "BASIC";
			}
			if (str6.equalsIgnoreCase("false")) {
				str8 = "INHAND";
			}

			try {
				stat = con.createStatement();
				affected = stat.executeUpdate("Insert into HRSALARY_STRUCT values('" + coidtf.getText()
						+ "','" + brcodetf.getText() + "','" + ch.getSelectedItem() + "','" + str1 + "','"
						+ str2 + "','" + str3 + "','" + str4 + "','" + str7 + "','" + str8 + "') ");

				if (affected > 0) {
					javax.swing.JOptionPane.showMessageDialog(f, "Record Updated.");
				}
			} catch (Exception localException) {
				javax.swing.JOptionPane.showMessageDialog(f, localException.getMessage().toString());
			}
		}
	}

	public void actionPerformed(java.awt.event.ActionEvent paramActionEvent) {
		if (paramActionEvent.getSource() == defaultbut) {
			benefiteList();
			deductionList();
		}
		if (paramActionEvent.getSource() == savebut) {
			saveBenefites();
			saveDeductions();
		}
		if (paramActionEvent.getSource() == closebut) {
			f.setVisible(false);
		}
	}

	public void itemStateChanged(java.awt.event.ItemEvent paramItemEvent) {
		if (paramItemEvent.getSource() == ch) {
			showCompanyBenefites();
			showCompanyDeductions();
		}
	}

	public void mousePressed(MouseEvent paramMouseEvent) {
	}

	public void mouseReleased(MouseEvent paramMouseEvent) {
		int i;
		int j;
		String str1;
		String str2;
		if (paramMouseEvent.getSource() == tb) {
			i = tb.getSelectedColumn();
			j = tb.getSelectedRow();

			if (tb.getSelectedColumn() == 0) {
				str1 = String.valueOf(model.getValueAt(j, 0));

				if ((!str1.equalsIgnoreCase("true")) ||

				(str1.equalsIgnoreCase("false"))) {
					model.removeRow(j);
				}
			}
			if ((i != tb.getColumnCount() - 1) ||

			(i == tb.getColumnCount() - 2)) {
				str1 = String.valueOf(model.getValueAt(j, i));
				if (str1.equalsIgnoreCase("true")) {
					str2 = "0";
					str2 = javax.swing.JOptionPane.showInputDialog(f,
							"Enter Benefites Percent for " + model.getValueAt(j, 1));
					model.setValueAt(str2, j, i - 2);
					model.setValueAt("0", j, i - 1);
				}
				if (str1.equalsIgnoreCase("false")) {
					str2 = "0";
					str2 = javax.swing.JOptionPane.showInputDialog(f,
							"Enter Benefites Amount for " + model.getValueAt(j, 1));
					model.setValueAt(str2, j, i - 1);
					model.setValueAt("0", j, i - 2);
				}
			}
		}

		if (paramMouseEvent.getSource() == tb1) {
			i = tb1.getSelectedColumn();
			j = tb1.getSelectedRow();

			if (tb1.getSelectedColumn() == 0) {
				str1 = String.valueOf(model1.getValueAt(j, 0));

				if ((!str1.equalsIgnoreCase("true")) ||

				(str1.equalsIgnoreCase("false"))) {
					model1.removeRow(j);
				}
			}
			if ((i != tb1.getColumnCount() - 1) ||

			(i == tb1.getColumnCount() - 2)) {
				str1 = String.valueOf(model1.getValueAt(j, i));
				if (str1.equalsIgnoreCase("true")) {
					model1.setValueAt(new Boolean(false), j, i + 1);
					str2 = "0";
					str2 = javax.swing.JOptionPane.showInputDialog(f,
							"Enter Deduction Percent for " + model1.getValueAt(j, 1));
					model1.setValueAt(str2, j, i - 2);
					model1.setValueAt("0", j, i - 1);
				}
				if (str1.equalsIgnoreCase("false")) {
					model1.setValueAt(new Boolean(true), j, i + 1);
					str2 = "0";
					str2 = javax.swing.JOptionPane.showInputDialog(f,
							"Enter Deduction Amount for " + model1.getValueAt(j, 1));
					model1.setValueAt(str2, j, i - 1);
					model1.setValueAt("0", j, i - 2);
				}
			}
		}
	}

	public void mouseClicked(MouseEvent paramMouseEvent) {
	}

	public void mouseEntered(MouseEvent paramMouseEvent) {
	}

	public void mouseExited(MouseEvent paramMouseEvent) {
	}
}
