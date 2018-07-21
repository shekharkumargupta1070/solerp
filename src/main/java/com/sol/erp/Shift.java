package com.sol.erp;

import java.awt.Color;
import java.awt.Font;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.sol.erp.dao.UtilListQuery;
import com.sol.erp.dao.UtilQueryResult;
import com.sol.erp.util.DBConnectionUtil;
import com.sol.erp.util.DateDifferencesUtil;

public class Shift extends javax.swing.JInternalFrame implements java.awt.event.ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	java.sql.Connection con = null;
	java.sql.Statement stat = null;
	java.sql.ResultSet rs = null;

	
	UtilQueryResult qr = new UtilQueryResult();

	public Shift() {
		initComponents();
		companyList();
	}

	private void initComponents() {
		p1 = new JPanel();
		l1 = new JLabel();
		cb1 = new JComboBox();
		l2 = new JLabel();
		tf1 = new JTextField("textFieldProjectNumber");
		l3 = new JLabel();
		tf2 = new JTextField();
		l4 = new JLabel();
		tf3 = new JTextField();
		p2 = new JPanel();
		jScrollPane1 = new javax.swing.JScrollPane();
		listmodel = new javax.swing.DefaultListModel();
		jList1 = new JList(listmodel);
		l5 = new JLabel();
		l6 = new JLabel();
		tf4 = new JTextField();
		l7 = new JLabel();
		tf5 = new JTextField();
		l8 = new JLabel();
		tf6 = new JTextField();
		p3 = new JPanel();
		b1 = new JButton();
		b2 = new JButton();
		b3 = new JButton();

		getContentPane().setLayout(null);

		setClosable(true);
		setIconifiable(true);
		setMaximizable(true);
		setResizable(true);
		setTitle("Shift");
		p1.setLayout(null);

		p1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
		l1.setFont(new Font("MS Sans Serif", 1, 11));
		l1.setForeground(new Color(102, 102, 102));
		l1.setText("Company ID");
		p1.add(l1);
		l1.setBounds(10, 10, 65, 15);

		cb1.setEditable(true);
		p1.add(cb1);
		cb1.setBounds(90, 10, 80, 21);

		l2.setFont(new Font("MS Sans Serif", 1, 11));
		l2.setForeground(new Color(102, 102, 102));
		l2.setText("Name");
		p1.add(l2);
		l2.setBounds(10, 40, 50, 20);

		tf1.setEditable(false);
		p1.add(tf1);
		tf1.setBounds(90, 40, 370, 21);

		l3.setFont(new Font("MS Sans Serif", 1, 11));
		l3.setForeground(new Color(102, 102, 102));
		l3.setText("Branch Code");
		p1.add(l3);
		l3.setBounds(10, 70, 70, 20);

		p1.add(tf2);
		tf2.setBounds(90, 70, 80, 21);

		l4.setFont(new Font("MS Sans Serif", 1, 11));
		l4.setForeground(new Color(102, 102, 102));
		l4.setText("Name");
		p1.add(l4);
		l4.setBounds(180, 70, 40, 20);

		tf3.setEditable(false);
		p1.add(tf3);
		tf3.setBounds(220, 70, 240, 21);

		getContentPane().add(p1);
		p1.setBounds(20, 20, 480, 110);

		p2.setLayout(null);

		p2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
		jList1.setFont(new Font("MS Sans Serif", 1, 11));
		jList1.setModel(new DefaultListModel() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			String[] strings = { "A", "B", "C", "G" };

			public int getSize() {
				return strings.length;
			}

			public String getElementAt(int paramAnonymousInt) {
				return strings[paramAnonymousInt];
			}
		});
		jList1.setSelectionMode(0);
		jScrollPane1.setViewportView(jList1);

		p2.add(jScrollPane1);
		jScrollPane1.setBounds(10, 30, 70, 110);

		l5.setFont(new Font("MS Sans Serif", 1, 11));
		l5.setForeground(new Color(102, 102, 102));
		l5.setText("Shift Code");
		p2.add(l5);
		l5.setBounds(10, 10, 80, 15);

		l6.setFont(new Font("MS Sans Serif", 1, 11));
		l6.setForeground(new Color(102, 102, 102));
		l6.setText("In Time");
		p2.add(l6);
		l6.setBounds(100, 30, 80, 15);

		p2.add(tf4);
		tf4.setBounds(180, 30, 100, 21);

		l7.setFont(new Font("MS Sans Serif", 1, 11));
		l7.setForeground(new Color(102, 102, 102));
		l7.setText("Out Time");
		p2.add(l7);
		l7.setBounds(100, 60, 80, 15);

		p2.add(tf5);
		tf5.setBounds(180, 60, 100, 21);

		l8.setFont(new Font("MS Sans Serif", 1, 11));
		l8.setForeground(new Color(102, 102, 102));
		l8.setText("Total Time");
		p2.add(l8);
		l8.setBounds(100, 90, 80, 15);

		p2.add(tf6);
		tf6.setBounds(180, 90, 100, 21);

		getContentPane().add(p2);
		p2.setBounds(20, 140, 300, 160);

		p3.setLayout(null);

		p3.setBorder(javax.swing.BorderFactory.createEtchedBorder());
		b1.setText("Save");
		p3.add(b1);
		b1.setBounds(50, 10, 90, 30);

		b2.setText("Update");
		p3.add(b2);
		b2.setBounds(50, 50, 90, 30);

		b3.setText("Close");
		p3.add(b3);
		b3.setBounds(50, 115, 90, 30);

		getContentPane().add(p3);
		p3.setBounds(330, 140, 170, 160);

		setBounds(0, 0, 526, 354);

		cb1.addActionListener(this);
		b1.addActionListener(this);
		b2.addActionListener(this);
		b3.addActionListener(this);

		tf3.addActionListener(this);
		tf4.addActionListener(this);
		tf5.addActionListener(this);
	}

	java.util.ArrayList ar = new java.util.ArrayList();
	private JButton b1;

	public void companyList() {
		ar = UtilListQuery.skList("company_id", "HRCOMPANY_ID");
		System.out.println(ar);

		for (int i = 0; i < ar.size(); i++) {
			cb1.addItem(String.valueOf(ar.get(i)).toUpperCase());
		}
	}

	private JButton b2;
	private JButton b3;
	private JComboBox cb1;

	public void companyDetails() {
		try {
			con = DBConnectionUtil.getConnection();
			stat = con.createStatement();
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

	private JList jList1;
	private javax.swing.DefaultListModel listmodel;
	private javax.swing.JScrollPane jScrollPane1;
	private JTextField tf2;
	private JTextField tf3;
	private JTextField tf4;
	private JTextField tf5;
	private JTextField tf6;

	public void actionPerformed(java.awt.event.ActionEvent paramActionEvent) {
		if (paramActionEvent.getSource() == cb1) {
			companyDetails();
		}
		String str;
		if (paramActionEvent.getSource() == b1) {
			qr.Query("insert into HRSHIFT values('" + cb1.getSelectedItem() + "','" + tf2.getText()
					+ "','" + jList1.getSelectedValue() + "','" + tf4.getText() + "','" + tf5.getText()
					+ "','" + tf6.getText() + "')");
			str = qr.getMessage();
			if (str.equalsIgnoreCase("succeed")) {
				javax.swing.JOptionPane.showMessageDialog(this, "Shift Time Set");
			} else {
				javax.swing.JOptionPane.showMessageDialog(this, str);
			}
		}

		if (paramActionEvent.getSource() == b3) {
			setVisible(false);
		}

		if (paramActionEvent.getSource() == tf5) {
			str = DateDifferencesUtil.getTimeDiff(String.valueOf(tf4.getText()), String.valueOf(tf5.getText()));
			tf6.setText("Shekhar");
			tf6.setText(str);
		}
	}

	private JLabel l1;
	private JLabel l2;
	private JLabel l3;
	private JLabel l4;
	private JLabel l5;
	private JLabel l6;
	private JLabel l7;
	private JLabel l8;
	private JPanel p1;
	private JPanel p2;
	private JPanel p3;
	private JTextField tf1;
}
