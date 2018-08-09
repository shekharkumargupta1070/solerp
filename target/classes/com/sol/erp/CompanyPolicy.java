package com.sol.erp;

import java.awt.Choice;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;

import com.sol.erp.dao.UtilQueryResult;
import com.sol.erp.ui.custom.SolTableModel;
import com.sol.erp.util.DBConnectionUtil;

public class CompanyPolicy extends javax.swing.JInternalFrame
		implements java.awt.event.ActionListener, java.awt.event.MouseListener, java.awt.event.ItemListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	java.sql.Connection con = null;
	java.sql.Statement stat = null;
	java.sql.ResultSet rs = null;

	UtilQueryResult qr = new UtilQueryResult();

	JPanel northpanel = new JPanel();
	JPanel northp1 = new JPanel();
	Choice cho = new Choice();

	public CompanyPolicy() {
		initComponents();
		companyID();
		policyList();
		likePolicyList();
	}

	javax.swing.JCheckBox cb = new javax.swing.JCheckBox();

	String[] head = { "Select", "Policy List" };
	Object[][] data = new Object[0][];

	String[] head1 = { "Company ID", "Company Name", "Branch Code", "Policy" };
	Object[][] data1 = new Object[0][];

	private JButton b1;
	private JButton b2;
	private JButton b3;
	private JComboBox tf1;

	private void initComponents() {
		p1 = new JPanel();
		l1 = new JLabel();
		tf1 = new JComboBox();
		l2 = new JLabel();
		tf4 = new JTextField();
		l3 = new JLabel();
		tf2 = new JTextField();
		l4 = new JLabel();
		tf3 = new JTextField();
		p3 = new JPanel();
		b1 = new JButton();
		b2 = new JButton();
		b3 = new JButton();
		jScrollPane1 = new javax.swing.JScrollPane();
		model = new javax.swing.table.DefaultTableModel(data, head);
		jTable1 = new JTable(model) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public Class<? extends Object> getColumnClass(int paramAnonymousInt) {
				return getValueAt(0, paramAnonymousInt).getClass();
			}

		};
		cho.addItem("DEPTS");
		cho.addItem("POSITIONS");
		cho.addItem("POLICIES");
		cho.addItem("SAL_BENEFITES");
		cho.addItem("SAL_DEDUCTIONS");
		cho.addItem("LEAVES");
		cho.addItem("REQ_MODES");

		SolTableModel.decorateTable(jTable1);
		jTable1.setShowGrid(false);
		jTable1.setSelectionBackground(new java.awt.Color(220, 220, 220));
		jTable1.setSelectionForeground(java.awt.Color.darkGray);
		jTable1.getColumnModel().getColumn(0).setPreferredWidth(10);
		jTable1.getColumnModel().getColumn(1).setPreferredWidth(370);

		getContentPane().setLayout(new java.awt.BorderLayout());

		setClosable(true);
		setIconifiable(true);
		setMaximizable(true);
		setResizable(true);
		setTitle("Company Policy");
		p1.setLayout(null);

		p1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
		l1.setFont(new java.awt.Font("MS Sans Serif", 1, 11));

		l1.setText("Company ID");
		p1.add(l1);
		l1.setBounds(10, 10, 65, 15);

		tf1.setEditable(true);
		p1.add(tf1);
		tf1.setFont(new java.awt.Font("MS Sans Serif", 1, 11));
		tf1.setBounds(90, 10, 80, 21);

		l2.setFont(new java.awt.Font("MS Sans Serif", 1, 11));
		l2.setText("Name");
		p1.add(l2);
		l2.setBounds(10, 40, 50, 20);

		tf4.setEditable(false);
		p1.add(tf4);
		tf4.setFont(new java.awt.Font("MS Sans Serif", 1, 11));
		tf4.setBounds(90, 40, 370, 21);

		l3.setFont(new java.awt.Font("MS Sans Serif", 1, 11));
		l3.setText("Branch Code");
		p1.add(l3);
		l3.setBounds(10, 70, 70, 20);

		p1.add(tf2);
		tf2.setFont(new java.awt.Font("MS Sans Serif", 1, 11));
		tf2.setBounds(90, 70, 80, 21);

		l4.setFont(new java.awt.Font("MS Sans Serif", 1, 11));
		l4.setText("City");
		p1.add(l4);
		l4.setBounds(180, 70, 40, 20);

		tf3.setEditable(false);
		p1.add(tf3);
		tf3.setFont(new java.awt.Font("MS Sans Serif", 1, 11));
		tf3.setBounds(220, 70, 240, 21);

		p3.setLayout(new java.awt.FlowLayout(2));

		p3.setBorder(javax.swing.BorderFactory.createEtchedBorder());
		b1.setText("Create Policy");

		b2.setText("Show All");

		b3.setText("Close");
		p3.add(b3);

		northpanel.setLayout(new java.awt.BorderLayout());
		northp1.setLayout(new java.awt.FlowLayout(0));

		northp1.add(cho);

		northpanel.add(northp1, "North");
		northpanel.add(p1, "Center");

		northpanel.setPreferredSize(new java.awt.Dimension(150, 150));
		getContentPane().add(northpanel, "North");
		getContentPane().add(jScrollPane1, "Center");
		getContentPane().add(p3, "South");

		jScrollPane1.setViewportView(jTable1);
		jScrollPane1.setBounds(20, 140, 480, 280);

		setBounds(220, 60, 490, 500);

		b1.addActionListener(this);
		b2.addActionListener(this);
		b3.addActionListener(this);

		tf1.addActionListener(this);
		tf2.addActionListener(this);
		tf3.addActionListener(this);

		jTable1.addMouseListener(this);
		cho.addItemListener(this);
	}

	private JTable jTable1;
	private javax.swing.table.DefaultTableModel model;
	private javax.swing.JScrollPane jScrollPane1;
	private JTextField tf2;

	public void companyID() {
		try {
			con =  DBConnectionUtil.getConnection();
			stat = con.createStatement();
			rs = stat.executeQuery("Select COMPANY_ID from HRCOMPANY_ID");
			while (rs.next()) {
				String str = new String(rs.getString(1));
				tf1.addItem(str);
			}
		} catch (Exception localException) {
		}
	}

	public void policyList() {
		String str1 = "HR" + String.valueOf(cho.getSelectedItem());
		model.setNumRows(0);
		try {
			stat = con.createStatement();
			rs = stat.executeQuery("Select name from " + str1);
			while (rs.next()) {
				String str2 = new String(rs.getString(1));
				model.addRow(new Object[] { new Boolean(false), str2 });
			}
		} catch (Exception localException) {
		}
	}

	public void likePolicyList() {
		String str1 = "HRCOMPANY_" + String.valueOf(cho.getSelectedItem());
		for (int i = 0; i < jTable1.getRowCount(); i++) {
			String str2 = String.valueOf(model.getValueAt(i, 1));
			String str3 = String.valueOf(cho.getSelectedItem());
			try {
				stat = con.createStatement();
				rs = stat
						.executeQuery("Select args from " + str1 + " where company_id='" + tf1.getSelectedItem()
								+ "' and br_code='" + tf2.getText() + "' and " + str3 + " ='" + str2 + "' ");
				while (rs.next()) {
					String str4 = new String(rs.getString(1));
					if (str4.equalsIgnoreCase("true")) {
						model.setValueAt(new Boolean(true), i, 0);
					} else {
						model.setValueAt(new Boolean(false), i, 0);
					}
				}
			} catch (Exception localException) {
				System.out.println(localException);
			}
		}
	}

	public void companyDetails() {
		try {
			stat = con.createStatement();
			rs = stat.executeQuery("Select  Name, BR_CODE, CITY from HRCOMPANY_ID where company_id ='"
					+ tf1.getSelectedItem() + "' ");

			while (rs.next()) {
				String str1 = new String(rs.getString(1));
				String str2 = new String(rs.getString(2));
				String str3 = new String(rs.getString(3));

				tf4.setText(str1);
				tf2.setText(str2);
				tf3.setText(str3);
			}
		} catch (Exception localException) {
		}
	}

	public void insert() {
		int i = jTable1.getSelectedRow();

		String str1 = "HRCOMPANY_" + String.valueOf(cho.getSelectedItem());
		String str2 = String.valueOf(model.getValueAt(i, 1));

		try {
			stat = con.createStatement();
			int j = stat.executeUpdate(
					"Insert into " + str1 + " values('" + tf1.getSelectedItem() + "', '" + tf4.getText()
							+ "', '" + tf2.getText() + "','" + tf3.getText() + "','" + str2 + "','true') ");

			if (j > 0) {
				javax.swing.JOptionPane.showMessageDialog(this, str2 + " Created.");
			}
		} catch (Exception localException) {
			javax.swing.JOptionPane.showMessageDialog(this, str2 + " Can't Created or It's Allready Created.");
		}
	}

	public void remove() {
		int i = jTable1.getSelectedRow();
		String str1 = "HRCOMPANY_" + String.valueOf(cho.getSelectedItem());
		String str2 = String.valueOf(cho.getSelectedItem());
		String str3 = String.valueOf(model.getValueAt(i, 1));

		try {
			stat = con.createStatement();
			int j = stat.executeUpdate(" delete from " + str1 + " WHERE company_id='" + tf1.getSelectedItem()
					+ "' and br_code= '" + tf2.getText() + "' and " + str2 + " ='" + str3 + "' ");
			if (j > 0) {
				javax.swing.JOptionPane.showMessageDialog(this, str3 + " Removed.");
			}
		} catch (Exception localException) {
			javax.swing.JOptionPane.showMessageDialog(this, localException.getMessage().toString());
		}
	}

	private JTextField tf3;
	private JLabel l1;
	private JLabel l2;
	private JLabel l3;
	private JLabel l4;
	private JPanel p1;
	private JPanel p3;
	private JTextField tf4;

	public void actionPerformed(java.awt.event.ActionEvent paramActionEvent) {
		if (paramActionEvent.getSource() == b1) {
			insert();
		}
		if (paramActionEvent.getSource() == b2) {
			policyList();
		}
		if (paramActionEvent.getSource() == b3) {
			setVisible(false);
		}
		if (paramActionEvent.getSource() == tf1) {
			companyDetails();
			policyList();
			likePolicyList();
		}
		if (paramActionEvent.getSource() == tf2) {
			policyList();
			likePolicyList();
		}
	}

	public void mousePressed(MouseEvent paramMouseEvent) {
	}

	public void mouseReleased(MouseEvent paramMouseEvent) {
	}

	public void mouseClicked(MouseEvent paramMouseEvent) {
		if (paramMouseEvent.getSource() == jTable1) {
			if (jTable1.getSelectedColumn() == 0) {
				int i = jTable1.getSelectedRow();
				String str = String.valueOf(model.getValueAt(i, 0));

				if (str.equalsIgnoreCase("true")) {
					insert();
				}
				if (str.equalsIgnoreCase("false")) {
					remove();
				}
			}
		}
	}

	public void mouseEntered(MouseEvent paramMouseEvent) {
	}

	public void mouseExited(MouseEvent paramMouseEvent) {
	}

	public void itemStateChanged(java.awt.event.ItemEvent paramItemEvent) {
		if (paramItemEvent.getSource() == cho) {
			policyList();
			likePolicyList();
		}
	}
}
