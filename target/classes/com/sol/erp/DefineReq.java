package com.sol.erp;

import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import com.sol.erp.dao.UtilListQuery;
import com.sol.erp.dao.UtilQueryResult;
import com.sol.erp.util.DBConnectionUtil;

public class DefineReq extends javax.swing.JInternalFrame implements java.awt.event.ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	java.sql.Connection con = null;
	java.sql.Statement stat = null;
	java.sql.ResultSet rs = null;

	UtilQueryResult qr = new UtilQueryResult();
	UtilListQuery sklist = new UtilListQuery();

	public DefineReq() {
		initComponents();
	}

	private void initComponents() {
		p1 = new JPanel();
		l1 = new JLabel();
		cb1 = new JComboBox();
		l2 = new JLabel();
		tf3 = new JTextField();
		l3 = new JLabel();
		tf1 = new JTextField();
		l4 = new JLabel();
		tf2 = new JTextField();
		p2 = new JPanel();
		listmodel2 = new DefaultListModel();
		list2 = new JList(listmodel2);
		sp2 = new JScrollPane(list2);

		listmodel1 = new DefaultListModel();
		list1 = new JList(listmodel1);
		sp1 = new JScrollPane(list1);

		b1 = new JButton();
		b2 = new JButton();
		b3 = new JButton();

		getContentPane().setLayout(null);

		setClosable(true);
		setIconifiable(true);
		setMaximizable(true);
		setResizable(true);
		p1.setLayout(null);

		p1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
		l1.setFont(new java.awt.Font("MS Sans Serif", 1, 11));
		l1.setForeground(new java.awt.Color(102, 102, 102));
		l1.setText("Company ID");
		p1.add(l1);
		l1.setBounds(10, 10, 65, 15);

		cb1.setEditable(true);
		p1.add(cb1);
		cb1.setBounds(90, 10, 80, 21);

		l2.setFont(new java.awt.Font("MS Sans Serif", 1, 11));
		l2.setForeground(new java.awt.Color(102, 102, 102));
		l2.setText("Name");
		p1.add(l2);
		l2.setBounds(10, 40, 50, 20);

		tf3.setEditable(false);
		p1.add(tf3);
		tf3.setBounds(90, 40, 370, 21);

		l3.setFont(new java.awt.Font("MS Sans Serif", 1, 11));
		l3.setForeground(new java.awt.Color(102, 102, 102));
		l3.setText("Branch Code");
		p1.add(l3);
		l3.setBounds(10, 70, 70, 20);

		tf1.setEditable(false);
		p1.add(tf1);
		tf1.setBounds(90, 70, 80, 21);

		l4.setFont(new java.awt.Font("MS Sans Serif", 1, 11));
		l4.setForeground(new java.awt.Color(102, 102, 102));
		l4.setText("Name");
		p1.add(l4);
		l4.setBounds(180, 70, 40, 20);

		tf2.setEditable(false);
		p1.add(tf2);
		tf2.setBounds(220, 70, 240, 21);

		getContentPane().add(p1);
		p1.setBounds(20, 20, 480, 110);

		p2.setLayout(null);

		p2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
		sp2.setViewportView(list2);

		p2.add(sp1);
		sp1.setBounds(320, 20, 140, 150);

		sp1.setViewportView(list1);

		p2.add(sp2);
		sp2.setBounds(30, 20, 140, 150);

		b1.setText("Set Mode");
		p2.add(b1);
		b1.setBounds(190, 20, 120, 30);

		b2.setText("Remove Mode");
		p2.add(b2);
		b2.setBounds(190, 50, 120, 30);

		b3.setText("Close");
		p2.add(b3);
		b3.setBounds(190, 140, 120, 30);

		getContentPane().add(p2);
		p2.setBounds(20, 140, 480, 190);

		setBounds(0, 0, 531, 374);
		setTitle("Define Reqrurtment Mode");

		cb1.addActionListener(this);
		b1.addActionListener(this);
		b2.addActionListener(this);
		b3.addActionListener(this);
	}

	List ar1 = new ArrayList();
	List ar = new ArrayList();
	private JComboBox cb1;
	private JButton b1;

	public void modeList() {
		listmodel2.clear();

		ar1 = UtilListQuery.skDynamicList("REQ_MODE", "HRREQ_MODE", "Company_id", String.valueOf(cb1.getSelectedItem()));

		for (int i = 0; i < ar1.size(); i++) {
			listmodel2.addElement(String.valueOf(ar1.get(i)).toUpperCase());
		}
	}

	private JButton b2;
	private JButton b3;
	private JTextField tf1;

	public void policyList() {
		listmodel1.clear();
		ar = UtilListQuery.skDynamicList("POLICY", "HRCOMPANY_POLICY", "Company_id", String.valueOf(cb1.getSelectedItem()));
		System.out.println(ar);

		for (int i = 0; i < ar.size(); i++) {
			listmodel1.addElement(String.valueOf(ar.get(i)).toUpperCase());
		}
	}

	private JTextField tf2;
	private JLabel l1;
	private JLabel l2;
	private JLabel l3;
	private JLabel l4;
	private DefaultListModel listmodel1;
	private javax.swing.JList list1;

	public void companyDetails() {
		try {
			con = DBConnectionUtil.getConnection();
			rs = stat.executeQuery("Select  CO_Name, BRANCH_CODE, CITY from HRCOMPANY_ID where company_id ='"
					+ cb1.getSelectedItem() + "' ");
			
			while (rs.next()) {
				String str1 = new String(rs.getString(1));
				String str2 = new String(rs.getString(2));
				String str3 = new String(rs.getString(3));

				tf3.setText(str1);
				tf1.setText(str2);
				tf2.setText(str3);
			}
		} catch (Exception localException) {
		}
	}

	private DefaultListModel listmodel2;
	private JList list2;
	private JPanel p1;
	private JPanel p2;
	private JScrollPane sp1;
	private JScrollPane sp2;
	private JTextField tf3;

	public void actionPerformed(java.awt.event.ActionEvent paramActionEvent) {
		if (paramActionEvent.getSource() == cb1) {
			modeList();
			policyList();
			companyDetails();
		}
	}
}
