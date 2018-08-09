package com.sol.erp;

import java.util.ArrayList;
import java.util.List;

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

public class CreateDesignation extends javax.swing.JInternalFrame
		implements java.awt.event.ActionListener, java.awt.event.KeyListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	java.sql.Connection con = null;
	java.sql.Statement stat = null;
	java.sql.ResultSet rs = null;

	UtilQueryResult qr = new UtilQueryResult();
	UtilListQuery sklist = new UtilListQuery();

	public CreateDesignation() {
		initComponents();
		companyList();
		con = DBConnectionUtil.getConnection();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void initComponents() {
		p1 = new JPanel();
		l1 = new JLabel();
		cb1 = new JComboBox();
		showbut = new JButton("Show");
		l2 = new JLabel();
		tf1 = new JTextField();
		l3 = new JLabel();
		tf2 = new JTextField();
		tf3 = new JTextField();
		p2 = new JPanel();
		l4 = new JLabel();
		tf5 = new JTextField();
		jScrollPane1 = new javax.swing.JScrollPane();
		listmodel = new javax.swing.DefaultListModel();
		list = new javax.swing.JList(listmodel);
		p3 = new JPanel();
		jButton1 = new JButton();
		jButton2 = new JButton();
		b3 = new java.awt.Button();

		getContentPane().setLayout(null);

		setClosable(true);
		setIconifiable(true);
		setMaximizable(true);
		setResizable(true);
		setTitle("Designation");
		p1.setLayout(null);

		p1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
		l1.setFont(new java.awt.Font("MS Sans Serif", 1, 11));
		l1.setForeground(new java.awt.Color(102, 102, 102));
		l1.setText("Company ID");
		p1.add(l1);
		l1.setBounds(20, 10, 80, 20);

		cb1.setEditable(true);
		p1.add(cb1);
		cb1.setBounds(110, 10, 80, 20);

		l2.setFont(new java.awt.Font("MS Sans Serif", 1, 11));
		l2.setForeground(new java.awt.Color(102, 102, 102));
		l2.setText("Name");
		p1.add(l2);
		l2.setBounds(20, 40, 80, 20);

		tf1.setEditable(false);
		p1.add(tf1);
		tf1.setBounds(110, 40, 380, 21);

		l3.setFont(new java.awt.Font("MS Sans Serif", 1, 11));
		l3.setForeground(new java.awt.Color(102, 102, 102));
		l3.setText("Branch No");
		p1.add(l3);
		l3.setBounds(20, 70, 80, 20);

		p1.add(tf2);
		tf2.setBounds(110, 70, 80, 21);

		tf3.setEditable(false);
		p1.add(tf3);
		tf3.setBounds(200, 70, 290, 21);

		getContentPane().add(p1);
		p1.setBounds(30, 20, 510, 110);

		p2.setLayout(null);

		p2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
		l4.setFont(new java.awt.Font("MS Sans Serif", 1, 11));
		l4.setForeground(new java.awt.Color(102, 102, 102));
		l4.setText("Designation Name");
		p2.add(l4);
		l4.setBounds(70, 10, 110, 20);

		p2.add(tf5);
		tf5.setBounds(20, 30, 200, 21);

		getContentPane().add(p2);
		p2.setBounds(30, 140, 240, 70);

		list.setFont(new java.awt.Font("Tahoma", 1, 11));
		jScrollPane1.setViewportView(list);

		getContentPane().add(jScrollPane1);
		jScrollPane1.setBounds(280, 140, 260, 160);

		p3.setLayout(null);

		p3.setBorder(javax.swing.BorderFactory.createEtchedBorder());
		jButton1.setText("Add");
		p3.add(jButton1);
		jButton1.setBounds(20, 25, 90, 30);

		jButton2.setText("Remove");
		p3.add(jButton2);
		jButton2.setBounds(130, 25, 90, 30);

		getContentPane().add(p3);
		p3.setBounds(30, 220, 240, 80);

		getContentPane().add(showbut);
		showbut.setBounds(300, 310, 80, 24);

		b3.setLabel("Close");
		getContentPane().add(b3);
		b3.setBounds(480, 310, 60, 24);

		setBounds(230, 125, 565, 380);

		jButton1.addActionListener(this);
		jButton2.addActionListener(this);
		b3.addActionListener(this);
		showbut.addActionListener(this);
		cb1.addActionListener(this);

		cb1.addKeyListener(this);
	}

	List ar = new ArrayList();
	List ar1 = new ArrayList();
	private java.awt.Button b3;
	private JComboBox cb1;

	public void companyList() {
		ar = UtilListQuery.skList("company_id", "HRCOMPANY_ID");
		System.out.println(ar);

		for (int i = 0; i < ar.size(); i++) {
			cb1.addItem(String.valueOf(ar.get(i)).toUpperCase());
		}
	}

	private JButton showbut;
	private JButton jButton1;
	private JButton jButton2;

	public void designationList() {
		listmodel.clear();
		ar1 = UtilListQuery.skDynamicList("DESIGNATION", "HRDESIGNATION", "Company_id",
				String.valueOf(cb1.getSelectedItem()));

		for (int i = 0; i < ar1.size(); i++) {
			listmodel.addElement(String.valueOf(ar1.get(i)).toUpperCase());
		}
	}

	private javax.swing.JScrollPane jScrollPane1;
	private JLabel l1;
	private JLabel l2;
	private JLabel l3;
	private JLabel l4;
	private JList list;
	private DefaultListModel listmodel;

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

	public void insert() {
		qr.Query("insert into HRDESIGNATION Values('" + cb1.getSelectedItem() + "','" + tf2.getText() + "','"
				+ tf5.getText() + "') ");
		String str = qr.getMessage();
		if (str.equalsIgnoreCase("succeed")) {
			listmodel.addElement(String.valueOf(tf5.getText()));
			tf5.setText("");
		}
	}

	public void remove() {
		String str1 = (String) cb1.getSelectedItem();
		String str2 = tf2.getText();
		String str3 = (String) list.getSelectedValue();

		qr.Query("Delete from HRDESIGNATION WHERE company_id='" + str1 + "' and branch_code='" + str2
				+ "' and designation='" + str3 + "' ");
		String str4 = qr.getMessage();
		if (str4.equalsIgnoreCase("succeed")) {
			listmodel.remove(list.getSelectedIndex());
		} else {
			javax.swing.JOptionPane.showMessageDialog(this, qr.getMessage());
		}
	}

	private JPanel p1;
	private JPanel p2;
	private JPanel p3;

	public void actionPerformed(java.awt.event.ActionEvent paramActionEvent) {
		if (paramActionEvent.getSource() == b3) {
			setVisible(false);
		}
		if (paramActionEvent.getSource() == jButton1) {
			insert();
		}
		if (paramActionEvent.getSource() == jButton2) {
			remove();
		}
		if (paramActionEvent.getSource() == showbut) {
			designationList();
		}
		if (paramActionEvent.getSource() == cb1) {
			companyDetails();
		}
	}

	private JTextField tf1;
	private JTextField tf2;
	private JTextField tf3;
	private JTextField tf5;

	public void keyPressed(java.awt.event.KeyEvent paramKeyEvent) {
		int i = paramKeyEvent.getKeyCode();
		if (i == 112) {
			designationList();
		}
	}

	public void keyReleased(java.awt.event.KeyEvent paramKeyEvent) {
	}

	public void keyTyped(java.awt.event.KeyEvent paramKeyEvent) {
	}
}
