package com.sol.erp;

import java.sql.ResultSet;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.sol.erp.dao.UtilQueryResult;
import com.sol.erp.ui.custom.SolCellModel;
import com.sol.erp.ui.custom.SolTableModel;
import com.sol.erp.util.DBConnectionUtil;

public class LeavePolicy extends javax.swing.JInternalFrame implements java.awt.event.ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	java.sql.Connection con = null;
	java.sql.Statement stat = null;
	ResultSet rs = null;


	UtilQueryResult qr = new UtilQueryResult();
	SolCellModel skc = new SolCellModel();

	String[] head = { "Leave Type" };

	Object[][] data = { { null, null, null, null }, { null, null, null, null }, { null, null, null, null },
			{ null, null, null, null } };
	private JButton b1;
	private JButton b2;
	private JButton b3;
	private JComboBox cb1;
	private JScrollPane jScrollPane1;
	private javax.swing.JTextArea ta;
	private JScrollPane jScrollPane2;
	private DefaultTableModel model;
	private JTable tb1;

	public LeavePolicy() {
		initComponents();
		con = DBConnectionUtil.getConnection();

		model.setNumRows(0);
		try {
			rs = stat.executeQuery("Select leave_type from HRLEAVE_tYPE ");

			while (rs.next()) {
				String str = new String(rs.getString(1));

				model.addRow(new Object[] { str });
			}
		} catch (Exception localException) {
		}
	}

	private void initComponents() {
		p1 = new JPanel();
		l1 = new JLabel();
		cb1 = new JComboBox();
		l2 = new JLabel();
		tf1 = new JTextField("");
		l3 = new JLabel();
		tf2 = new JTextField("");
		l4 = new JLabel();
		tf3 = new JTextField("");
		p3 = new JPanel();
		b1 = new JButton();
		b2 = new JButton();
		b3 = new JButton();
		jScrollPane1 = new JScrollPane();
		model = new DefaultTableModel(data, head);
		tb1 = new JTable(model);

		ta = new javax.swing.JTextArea();
		jScrollPane2 = new JScrollPane(ta);

		getContentPane().setLayout(null);

		setClosable(true);
		setIconifiable(true);
		setMaximizable(true);
		setResizable(true);
		setTitle("Company Leave Policy");
		p1.setLayout(null);

		p1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
		l1.setFont(new java.awt.Font("MS Sans Serif", 1, 11));
		l1.setForeground(new java.awt.Color(102, 102, 102));
		l1.setText("Company ID");
		p1.add(l1);
		l1.setBounds(10, 10, 65, 15);

		p1.add(cb1);
		cb1.setBounds(90, 10, 80, 21);

		l2.setFont(new java.awt.Font("MS Sans Serif", 1, 11));
		l2.setForeground(new java.awt.Color(102, 102, 102));
		l2.setText("Name");
		p1.add(l2);
		l2.setBounds(10, 40, 50, 20);

		tf1.setEditable(false);
		p1.add(tf1);
		tf1.setBounds(90, 40, 370, 21);

		l3.setFont(new java.awt.Font("MS Sans Serif", 1, 11));
		l3.setForeground(new java.awt.Color(102, 102, 102));
		l3.setText("Branch Code");
		p1.add(l3);
		l3.setBounds(10, 70, 70, 20);

		p1.add(tf2);
		tf2.setBounds(90, 70, 80, 21);

		l4.setFont(new java.awt.Font("MS Sans Serif", 1, 11));
		l4.setForeground(new java.awt.Color(102, 102, 102));
		l4.setText("Name");
		p1.add(l4);
		l4.setBounds(180, 70, 40, 20);

		tf3.setEditable(false);
		p1.add(tf3);
		tf3.setBounds(220, 70, 240, 21);

		getContentPane().add(p1);
		p1.setBounds(20, 20, 480, 110);

		p3.setLayout(null);

		p3.setBorder(javax.swing.BorderFactory.createEtchedBorder());
		b1.setText("Save Policies");
		p3.add(b1);
		b1.setBounds(20, 20, 110, 25);

		b2.setText("Show");
		p3.add(b2);
		b2.setBounds(160, 20, 110, 25);

		b3.setText("Remove");
		p3.add(b3);
		b3.setBounds(290, 20, 110, 25);

		getContentPane().add(p3);
		p3.setBounds(50, 360, 430, 60);

		tb1.setModel(model);
		jScrollPane1.setViewportView(tb1);

		getContentPane().add(jScrollPane1);
		jScrollPane1.setBounds(20, 150, 120, 200);

		getContentPane().add(jScrollPane2);
		jScrollPane2.setBounds(150, 150, 120, 200);

		setBounds(220, 100, 522, 462);
		cb1.setEditable(true);

		SolTableModel.decorateTable(tb1);

		for (int i = 0; i < tb1.getColumnCount(); i++) {
			tb1.getColumnModel().getColumn(i).setCellRenderer(skc);
		}

		ta.setEditable(false);
		ta.setFont(new java.awt.Font("Arial", 1, 11));

		cb1.addActionListener(this);
		b1.addActionListener(this);
		b2.addActionListener(this);
		b3.addActionListener(this);
	}

	private JTextField tf2;
	private JTextField tf3;
	private JLabel l1;
	private JLabel l2;
	private JLabel l3;
	private JLabel l4;
	private JPanel p1;
	private JPanel p3;
	private JTextField tf1;

	public void companyDetails() {
		try {
			rs = stat.executeQuery("Select  CO_Name, BRANCH_CODE, CITY from HRCOMPANY_ID where company_id ='"
					+ cb1.getSelectedItem() + "' ");

			while (rs.next()) {
				String str1 = new String(rs.getString(1));
				String str2 = new String(rs.getString(2));
				String str3 = new String(rs.getString(3));

				tf1.setText(str1);
				tf2.setText(str2);
				tf3.setText(str3);
			}
		} catch (Exception localException) {
		}
	}

	public void showRecord() {
		model.setNumRows(0);

		try {
			rs = stat.executeQuery(
					"Select LEAVE_TYPE from HRleave_policy where company_id = '" + cb1.getSelectedItem() + "' ");

			while (rs.next()) {
				String str = new String(rs.getString(1));

				model.addRow(new Object[] { str });
			}
		} catch (Exception localException) {
		}
	}

	public void actionPerformed(java.awt.event.ActionEvent paramActionEvent) {
		if (paramActionEvent.getSource() == cb1) {
			companyDetails();
		}
		String str1;
		int[] arrayOfInt;
		if (paramActionEvent.getSource() == b1) {
			str1 = null;

			arrayOfInt = tb1.getSelectedRows();

			for (int i = 0; i < arrayOfInt.length; i++) {
				str1 = String.valueOf(model.getValueAt(arrayOfInt[i], 0));

				qr.Query("insert into HRLEAVE_POLICY values('" + cb1.getSelectedItem() + "','"
						+ tf2.getText() + "','" + str1 + "') ");
			}

			String str2 = qr.getMessage();
			if (str2.equalsIgnoreCase("succeed")) {
				ta.append(str1 + "\n");
			} else {
				javax.swing.JOptionPane.showMessageDialog(this, str2);
			}
		}
		if (paramActionEvent.getSource() == b3) {
			str1 = null;

			arrayOfInt = tb1.getSelectedRows();

			for (int j = 0; j < arrayOfInt.length; j++) {
				str1 = String.valueOf(model.getValueAt(arrayOfInt[j], 0));

				qr.Query("delete from HRLEAVE_POLICY where company_id='" + cb1.getSelectedItem()
						+ "' and branch_code='" + tf2.getText() + "' and policy='" + str1 + "' ");
			}

			String str3 = qr.getMessage();
			javax.swing.JOptionPane.showMessageDialog(this, str3);
		}

		if (paramActionEvent.getSource() == b2) {
			showRecord();
		}
	}
}

/*
 * Location: E:\Shekhar_SOL\Running-SOLERP.jar!\LeavePolicy.class Java compiler
 * version: 5 (49.0) JD-Core Version: 0.7.1
 */