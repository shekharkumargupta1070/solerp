package com.sol.erp;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import com.sol.erp.dao.UtilListQuery;
import com.sol.erp.dao.UtilQueryResult;
import com.sol.erp.ui.custom.SolTableModel;
import com.sol.erp.util.DBConnectionUtil;

public class CreateIncLevel extends javax.swing.JInternalFrame implements java.awt.event.ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	java.sql.Connection con = null;
	java.sql.Statement stat = null;
	java.sql.ResultSet rs = null;

	UtilQueryResult qr = new UtilQueryResult();
	UtilListQuery sklist = new UtilListQuery();

	String[] head = { "S.Lvl", "Amount" };
	String[][] data = { { " A", null }, { " B", null }, { " C", null }, { " D", null }, { " E", null }, { " F", null },
			{ " G", null }, { " H", null }, { " I", null }, { " J", null } };
	private JButton b1;
	private JButton b2;
	private JButton b4;
	private JButton b5;
	private JButton b6;
	private JComboBox cb1;
	private JScrollPane jScrollPane1;
	private JTable tb1;
	private JLabel l1;
	private JLabel l2;
	private JLabel l3;

	public CreateIncLevel() {
		initComponents();
		con = DBConnectionUtil.getConnection();
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
		new JPanel();
		new JLabel();
		new JTextField();
		b1 = new JButton();
		sp1 = new JScrollPane();

		listmodel = new DefaultListModel();
		list1 = new javax.swing.JList(listmodel);
		jScrollPane1 = new JScrollPane(list1);

		model = new javax.swing.table.DefaultTableModel(data, head);
		tb1 = new JTable(model);
		new JPanel();
		new JLabel();
		new JTextField();
		new JLabel();
		new JTextField();
		b2 = new JButton();
		b4 = new JButton();
		b5 = new JButton();
		b6 = new JButton();

		getContentPane().setLayout(null);

		setClosable(true);
		setIconifiable(true);
		setMaximizable(true);
		setResizable(true);
		setTitle("Create Increament Level");
		p1.setLayout(null);

		p1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
		l1.setFont(new java.awt.Font("MS Sans Serif", 1, 11));
		l1.setForeground(new java.awt.Color(51, 51, 51));
		l1.setText("Company ID");
		p1.add(l1);
		l1.setBounds(20, 10, 80, 20);

		cb1.setEditable(true);
		p1.add(cb1);
		cb1.setBounds(110, 10, 80, 20);

		l2.setFont(new java.awt.Font("MS Sans Serif", 1, 11));
		l2.setForeground(new java.awt.Color(51, 51, 51));
		l2.setText("Name");
		p1.add(l2);
		l2.setBounds(20, 40, 80, 20);

		tf1.setEditable(false);
		p1.add(tf1);
		tf1.setBounds(110, 40, 380, 21);

		l3.setFont(new java.awt.Font("MS Sans Serif", 1, 11));
		l3.setForeground(new java.awt.Color(51, 51, 51));
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

		sp1.setViewportView(list1);

		getContentPane().add(sp1);
		sp1.setBounds(30, 250, 130, 200);

		tb1.setModel(model);
		jScrollPane1.setViewportView(tb1);

		getContentPane().add(jScrollPane1);
		jScrollPane1.setBounds(390, 250, 150, 200);

		b4.setText("Save");
		getContentPane().add(b4);
		b4.setBounds(50, 460, 140, 30);

		b5.setText("Update");
		getContentPane().add(b5);
		b5.setBounds(210, 460, 140, 30);

		b6.setText("Close");
		getContentPane().add(b6);
		b6.setBounds(370, 460, 140, 30);

		java.awt.Dimension localDimension = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
		setBounds((localDimension.width - 571) / 2, (localDimension.height - 541) / 2, 571, 541);

		listmodel.addElement("LX");
		listmodel.addElement("L1");
		listmodel.addElement("L2");
		listmodel.addElement("L3");
		listmodel.addElement("L4");
		listmodel.addElement("L5");
		listmodel.addElement("L6");
		listmodel.addElement("L7");
		listmodel.addElement("L8");

		SolTableModel.decorateTable(tb1);
		tb1.getColumnModel().getColumn(0).setPreferredWidth(20);

		cb1.addActionListener(this);
		b1.addActionListener(this);
		b2.addActionListener(this);
		b4.addActionListener(this);
		b5.addActionListener(this);
		b6.addActionListener(this);
	}

	private JList list1;
	private JPanel p1;
	private JScrollPane sp1;
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

	public void insert() {
		int i = tb1.getRowCount();
		String str1 = qr.getMessage();

		for (int j = 0; j < i; j++) {
			String str2 = (String) list1.getSelectedValue();
			String str3 = String.valueOf(model.getValueAt(j, 0));
			String str4 = String.valueOf(model.getValueAt(j, 1));

			qr.Query("Insert into HRSALARY_SLAB values('" + cb1.getSelectedItem() + "', '" + tf2.getText() + "',  '"
					+ str2 + "', '" + str3 + "','" + str4 + "')");

			if (!str1.equalsIgnoreCase("Succeed")) {

				javax.swing.JOptionPane.showMessageDialog(this, "Could not Save " + str2 + " " + str3 + " " + str4);
			}
		}

		if (str1.equalsIgnoreCase("Succeed")) {
			javax.swing.JOptionPane.showMessageDialog(this, "Slab Created.");
		}
	}

	private JTextField tf2;
	private JTextField tf3;

	public void actionPerformed(java.awt.event.ActionEvent paramActionEvent) {
		if (paramActionEvent.getSource() == cb1) {
			companyDetails();
		}
		if (paramActionEvent.getSource() == b4) {
			insert();
		}
		if (paramActionEvent.getSource() == b6) {
			setVisible(false);
		}
	}

	private javax.swing.table.DefaultTableModel model;
	private DefaultListModel listmodel;
}
