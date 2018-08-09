package com.sol.erp;

import java.awt.Font;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.sol.erp.dao.UtilListQuery;
import com.sol.erp.dao.UtilQueryResult;
import com.sol.erp.util.DBConnectionUtil;

public class ReqMode extends javax.swing.JInternalFrame implements java.awt.event.ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	java.sql.Connection con = null;
	java.sql.Statement stat = null;
	ResultSet rs = null;

	
	UtilQueryResult qr = new UtilQueryResult();
	UtilListQuery sklist = new UtilListQuery();

	public ReqMode() {
		con = DBConnectionUtil.getConnection();
		initComponents();
		companyList();
	}

	private void initComponents() {
		p1 = new JPanel();
		l1 = new JLabel();
		cb1 = new JComboBox();
		l2 = new JLabel();
		tf1 = new JTextField();
		l3 = new JLabel();
		tf2 = new JTextField();
		tf3 = new JTextField();
		p2 = new JPanel();
		l4 = new JLabel();
		tf4 = new JTextField();
		p3 = new JPanel();
		b1 = new JButton();
		b2 = new JButton();
		listmodel = new javax.swing.DefaultListModel();
		list = new javax.swing.JList(listmodel);
		jScrollPane1 = new javax.swing.JScrollPane(list);
		b3 = new JButton();

		getContentPane().setLayout(null);

		setClosable(true);
		setIconifiable(true);
		setMaximizable(true);
		setResizable(true);
		setTitle("Create Reqrutment Mode");
		p1.setLayout(null);

		p1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
		l1.setFont(new Font("MS Sans Serif", 1, 11));
		l1.setForeground(new java.awt.Color(102, 102, 102));
		l1.setText("Company ID");
		p1.add(l1);
		l1.setBounds(20, 10, 80, 20);

		cb1.setEditable(true);
		p1.add(cb1);
		cb1.setBounds(110, 10, 80, 20);

		l2.setFont(new Font("MS Sans Serif", 1, 11));
		l2.setForeground(new java.awt.Color(102, 102, 102));
		l2.setText("Name");
		p1.add(l2);
		l2.setBounds(20, 40, 80, 20);

		tf1.setEditable(false);
		p1.add(tf1);
		tf1.setBounds(110, 40, 380, 21);

		l3.setFont(new Font("MS Sans Serif", 1, 11));
		l3.setForeground(new java.awt.Color(102, 102, 102));
		l3.setText("Branch No");
		p1.add(l3);
		l3.setBounds(20, 70, 80, 20);

		tf2.setEditable(false);
		p1.add(tf2);
		tf2.setBounds(110, 70, 80, 21);

		tf3.setEditable(false);
		p1.add(tf3);
		tf3.setBounds(200, 70, 290, 21);

		getContentPane().add(p1);
		p1.setBounds(30, 20, 510, 110);

		p2.setLayout(null);

		p2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
		l4.setFont(new Font("MS Sans Serif", 1, 11));
		l4.setForeground(new java.awt.Color(102, 102, 102));
		l4.setText("Recruitment Mode Name");
		p2.add(l4);
		l4.setBounds(50, 10, 140, 20);

		p2.add(tf4);
		tf4.setBounds(20, 30, 200, 21);

		getContentPane().add(p2);
		p2.setBounds(30, 140, 240, 70);

		p3.setLayout(null);

		p3.setBorder(javax.swing.BorderFactory.createEtchedBorder());
		b1.setText("Add");
		p3.add(b1);
		b1.setBounds(20, 25, 90, 30);

		b2.setText("Remove");
		p3.add(b2);
		b2.setBounds(130, 25, 90, 30);

		getContentPane().add(p3);
		p3.setBounds(30, 220, 240, 80);

		list.setFont(new Font("Tahoma", 1, 11));
		jScrollPane1.setViewportView(list);

		getContentPane().add(jScrollPane1);
		jScrollPane1.setBounds(280, 140, 260, 160);

		b3.setText("Close");
		getContentPane().add(b3);
		b3.setBounds(440, 310, 100, 30);

		setBounds(0, 0, 566, 379);

		cb1.addActionListener(this);
		b1.addActionListener(this);
		b2.addActionListener(this);
		b3.addActionListener(this);
	}

	ArrayList ar = new ArrayList();
	private JComboBox cb1;
	private JButton b1;

	@SuppressWarnings("static-access")
	public void companyList() {
		ar = sklist.skList("company_id", "HRCOMPANY_ID");
		System.out.println(ar);

		for (int i = 0; i < ar.size(); i++) {
			cb1.addItem(String.valueOf(ar.get(i)).toUpperCase());
		}
	}

	private JButton b2;
	private JButton b3;
	private javax.swing.JScrollPane jScrollPane1;

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

	private JLabel l1;
	private JLabel l2;
	private JLabel l3;
	private JLabel l4;

	public void insert() {
		qr.Query("insert into HRREQ_MODE Values('" + cb1.getSelectedItem() + "','" + tf2.getText()
				+ "','" + tf4.getText() + "') ");
		String str = qr.getMessage();
		if (str.equalsIgnoreCase("succeed")) {
			listmodel.addElement(String.valueOf(tf4.getText()));
			tf4.setText("");
		}
	}

	private javax.swing.JList list;
	private javax.swing.DefaultListModel listmodel;
	private JPanel p1;
	private JPanel p2;

	public void actionPerformed(java.awt.event.ActionEvent paramActionEvent) {
		if (paramActionEvent.getSource() == cb1) {
			companyDetails();
		}
		if (paramActionEvent.getSource() == b1) {
			insert();
		}
	}

	private JPanel p3;
	private JTextField tf1;
	private JTextField tf2;
	private JTextField tf3;
	private JTextField tf4;
}
