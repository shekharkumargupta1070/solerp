package com.sol.erp;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.sol.erp.ui.custom.SolTableModel;
import com.sol.erp.ui.custom.SolTableCheckBoxFinder;
import com.sol.erp.util.DBConnectionUtil;

public class ModuleAccess implements java.awt.event.ActionListener, java.awt.event.MouseListener {

	public static Login lg = new Login();

	java.sql.Connection con;
	String idstr = "";
	String branchstr = "";
	String deptstr = "";

	SolTableCheckBoxFinder checkBoxFinder = new SolTableCheckBoxFinder();

	String[] head = { " ", "Co Id", "Company Name", "Logo" };
	Object[][] data = new Object[0][];

	String[] head1 = { " ", "BR_CODE", "Company Name", "Location" };
	Object[][] data1 = new Object[0][];

	String[] head2 = { " ", "DEPARTMENTS" };
	Object[][] data2 = new Object[0][];

	DefaultTableModel model = new DefaultTableModel(data, head);
	JTable tb = new JTable(model) {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@SuppressWarnings({ "unchecked", "rawtypes" })
		public Class getColumnClass(int paramAnonymousInt) {
			return getValueAt(0, paramAnonymousInt).getClass();
		}

		public boolean isCellEditable(int paramAnonymousInt1, int paramAnonymousInt2) {
			int i = convertColumnIndexToModel(paramAnonymousInt2);
			return (i < 1) || (i >= 4);
		}
	};
	JScrollPane sp = new JScrollPane(tb);

	DefaultTableModel model1 = new DefaultTableModel(data1, head1);
	JTable tb1 = new JTable(model1) {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@SuppressWarnings({ "unchecked", "rawtypes" })
		public Class getColumnClass(int paramAnonymousInt) {
			return getValueAt(0, paramAnonymousInt).getClass();
		}

		public boolean isCellEditable(int paramAnonymousInt1, int paramAnonymousInt2) {
			int i = convertColumnIndexToModel(paramAnonymousInt2);
			return (i < 1) || (i >= 4);
		}
	};
	JScrollPane sp1 = new JScrollPane(tb1);

	DefaultTableModel model2 = new DefaultTableModel(data2, head2);
	JTable tb2 = new JTable(model2) {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@SuppressWarnings({ "unchecked", "rawtypes" })
		public Class getColumnClass(int paramAnonymousInt) {
			return getValueAt(0, paramAnonymousInt).getClass();
		}

		public boolean isCellEditable(int paramAnonymousInt1, int paramAnonymousInt2) {
			int i = convertColumnIndexToModel(paramAnonymousInt2);
			return (i < 1) || (i >= 4);
		}
	};
	JScrollPane sp2 = new JScrollPane(tb2);

	JDialog f = new JDialog();

	public static JLabel l1 = new JLabel("EMP Code");
	public static JLabel l2 = new JLabel("CO_Id");
	public static JLabel l3 = new JLabel("BRANCH Code");

	JLabel westlabel = new JLabel("");
	JLabel eastlabel = new JLabel("");

	JButton closebut = new JButton("Close");
	JButton enterbut = new JButton("Next >>");

	javax.swing.ButtonGroup group1 = new javax.swing.ButtonGroup();
	javax.swing.ButtonGroup group2 = new javax.swing.ButtonGroup();

	JPanel northp1 = new JPanel();
	JPanel centerp1 = new JPanel();
	JPanel centerp2 = new JPanel();
	JPanel southp1 = new JPanel();
	JPanel centerpanel = new JPanel();

	java.awt.Toolkit tk = java.awt.Toolkit.getDefaultToolkit();

	Dimension ss = tk.getScreenSize();
	Dimension fs = f.getSize();

	class Renderer extends javax.swing.table.DefaultTableCellRenderer {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		Renderer() {
		}

		public java.awt.Component getTableCellRendererComponent(JTable paramJTable, Object paramObject,
				boolean paramBoolean1, boolean paramBoolean2, int paramInt1, int paramInt2) {
			setEnabled((paramJTable == null) || (paramJTable.isEnabled()));

			if (paramInt1 % 2 == 0) {
				setBackground(new Color(240, 240, 240));
				setForeground(Color.darkGray);
			} else {
				setBackground(new Color(230, 230, 230));
				setForeground(Color.darkGray);
			}

			String str = String.valueOf(paramJTable.getValueAt(paramInt1, 0));
			if (str.equalsIgnoreCase("true")) {
				setBackground(Color.black);
				setForeground(Color.yellow);
			}

			super.getTableCellRendererComponent(paramJTable, paramObject, paramBoolean1, paramBoolean2, paramInt1,
					paramInt2);

			return this;
		}
	}

	ModuleAccess.Renderer rnd = new ModuleAccess.Renderer();

	public void px() {
		java.awt.Container localContainer = f.getContentPane();
		localContainer.setLayout(new java.awt.BorderLayout());
		centerpanel.setLayout(new java.awt.GridLayout(1, 2, 5, 5));
		centerp1.setLayout(new java.awt.GridLayout(6, 1));

		centerp2.setLayout(new java.awt.BorderLayout());
		northp1.setLayout(new java.awt.FlowLayout(0));
		southp1.setLayout(new java.awt.FlowLayout(2));

		northp1.add(l1);

		southp1.add(l2);
		southp1.add(l3);

		southp1.add(closebut);
		closebut.setEnabled(false);
		southp1.add(enterbut);

		centerpanel.add(sp);
		sp.setPreferredSize(new Dimension(400, 200));
		centerpanel.add(sp1);
		sp1.setPreferredSize(new Dimension(400, 200));

		centerp2.add(sp2);
		sp2.setPreferredSize(new Dimension(400, 200));

		westlabel.setPreferredSize(new Dimension(50, 20));
		eastlabel.setPreferredSize(new Dimension(200, 20));

		northp1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
		southp1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

		localContainer.add(northp1, "North");
		localContainer.add(centerpanel, "Center");
		localContainer.add(southp1, "South");

		SolTableModel.decorateTable(tb);
		SolTableModel.decorateTable(tb1);
		SolTableModel.decorateTable(tb2);
		tb.setIntercellSpacing(new Dimension(1, 1));
		tb1.setIntercellSpacing(new Dimension(1, 1));
		tb2.setIntercellSpacing(new Dimension(1, 1));

		tb.getColumnModel().getColumn(0).setPreferredWidth(20);
		tb.getColumnModel().getColumn(1).setPreferredWidth(30);
		tb.getColumnModel().getColumn(2).setPreferredWidth(180);
		tb.getColumnModel().getColumn(3).setPreferredWidth(30);

		tb1.getColumnModel().getColumn(0).setPreferredWidth(20);
		tb1.getColumnModel().getColumn(1).setPreferredWidth(30);
		tb1.getColumnModel().getColumn(2).setPreferredWidth(180);
		tb1.getColumnModel().getColumn(3).setPreferredWidth(30);

		tb2.getColumnModel().getColumn(0).setPreferredWidth(20);
		tb2.getColumnModel().getColumn(1).setPreferredWidth(730);

		tb.getColumnModel().getColumn(1).setCellRenderer(rnd);
		tb.getColumnModel().getColumn(2).setCellRenderer(rnd);
		tb.getColumnModel().getColumn(3).setCellRenderer(rnd);

		tb1.getColumnModel().getColumn(1).setCellRenderer(rnd);
		tb1.getColumnModel().getColumn(2).setCellRenderer(rnd);
		tb1.getColumnModel().getColumn(3).setCellRenderer(rnd);

		tb2.getColumnModel().getColumn(1).setCellRenderer(rnd);

		closebut.addActionListener(this);
		enterbut.addActionListener(this);
		closebut.addActionListener(this);

		tb.addMouseListener(this);
		tb1.addMouseListener(this);
		tb2.addMouseListener(this);

		f.setSize(800, 270);
		f.setLocationRelativeTo(null);
		f.setVisible(true);
		f.getRootPane().setDefaultButton(enterbut);

		l1.setText(lg.tf1.getText().toUpperCase());

		companyList();

		enterbut.requestFocus();
	}

	public void selectSingleCheckBox(JTable paramJTable, DefaultTableModel paramDefaultTableModel, int paramInt) {
		for (int i = 0; i < paramJTable.getRowCount(); i++) {
			paramDefaultTableModel.setValueAt(new Boolean("false"), i, paramInt);
		}

		int i = paramJTable.getSelectedRow();
		paramDefaultTableModel.setValueAt(new Boolean("true"), i, paramInt);
	}

	public void matchTextForCheckBox(JTable paramJTable, DefaultTableModel paramDefaultTableModel, String paramString,
			int paramInt1, int paramInt2) {
		for (int i = 0; i < paramJTable.getRowCount(); i++) {
			String str = String.valueOf(paramDefaultTableModel.getValueAt(i, paramInt2));
			if (str.equalsIgnoreCase(paramString)) {
				paramDefaultTableModel.setValueAt(new Boolean("true"), i, paramInt1);
			}
		}
	}

	public String getCheckedText(JTable paramJTable, DefaultTableModel paramDefaultTableModel, int paramInt1,
			int paramInt2) {
		String str1 = null;

		for (int i = 0; i < paramJTable.getColumnCount(); i++) {
			for (int j = 0; j < paramJTable.getRowCount(); j++) {
				String str2 = String.valueOf(paramDefaultTableModel.getValueAt(j, paramInt1));
				if (str2.equalsIgnoreCase("true")) {
					str1 = String.valueOf(paramDefaultTableModel.getValueAt(j, paramInt2));
				}
			}
		}

		return str1;
	}

	public void companyList() {
		centerp1.add(new JPanel());

		try {
			con = DBConnectionUtil.getConnection();
			Statement localStatement = con.createStatement();
			ResultSet localObject = localStatement
					.executeQuery("select company_id, name from HRCOMPANY_ID WHERE BR_CODE ='0' ");
			while (((ResultSet) localObject).next()) {
				String str2 = new String(((ResultSet) localObject).getString(1));
				String str3 = new String(((ResultSet) localObject).getString(2));

				model.addRow(new Object[] { new Boolean(false), str2, str3, " " });
			}
		} catch (Exception localException) {
			System.out.println(localException);
		}

		String str1 = EmpTB.getCompanyID(String.valueOf(l1.getText()));
		Object localObject = EmpTB.getBranchCode(String.valueOf(l1.getText()));

		checkBoxFinder.setChecked(tb, model, str1, 0, 1);

		branchList();
		checkBoxFinder.setChecked(tb1, model1, (String) localObject, 0, 1);
	}

	public void branchList() {
		model1.setNumRows(0);

		String str1 = checkBoxFinder.getChecked(tb, model, 0, 2);

		System.out.println(str1);

		try {
			con = DBConnectionUtil.getConnection();
			Statement localStatement = con.createStatement();
			ResultSet localResultSet = localStatement
					.executeQuery("select Br_CODE, Address, City from HRCOMPANY_ID WHERE name ='" + str1 + "' ");
			while (localResultSet.next()) {
				String str2 = new String(localResultSet.getString(1));
				String str3 = new String(localResultSet.getString(2));
				String str4 = new String(localResultSet.getString(3));

				model1.addRow(new Object[] { new Boolean("false"), str2, str3, str4 });
			}
		} catch (Exception localException) {
			System.out.println(localException);
		}
	}

	public void deptList() {
		centerp2.removeAll();
		centerp2.revalidate();

		model2.setNumRows(0);

		String str1 = checkBoxFinder.getChecked(tb, model, 0, 1);
		String str2 = checkBoxFinder.getChecked(tb1, model1, 0, 1);

		l2.setText(str1);
		l3.setText(str2);

		try {
			Statement localStatement = con.createStatement();
			ResultSet localResultSet = localStatement.executeQuery(
					"select depts from HRCOMPANY_DEPTS WHERE COMPANY_ID ='" + str1 + "' and BR_CODE='" + str2 + "' ");
			while (localResultSet.next()) {
				String str4 = new String(localResultSet.getString(1));
				model2.addRow(new Object[] { new Boolean("false"), str4 });

				centerp2.add(sp2, "Center");
			}
		} catch (Exception localException) {
			System.out.println(localException);
		}

		String str3 = EmpTB.getDept(String.valueOf(l1.getText()));
		checkBoxFinder.setChecked(tb2, model2, str3, 0, 1);
	}

	@SuppressWarnings("deprecation")
	public void actionPerformed(java.awt.event.ActionEvent paramActionEvent) {
		if (paramActionEvent.getSource() == enterbut) {
			if (enterbut.getText().equalsIgnoreCase("Next >>")) {
				centerpanel.removeAll();
				centerp1.removeAll();
				centerp2.removeAll();
				deptList();
				centerpanel.add(centerp2, "Center");
				closebut.setEnabled(true);

				centerpanel.revalidate();
				f.setSize(800, 270);
			}
			if (paramActionEvent.getSource() == closebut) {
				System.exit(0);
			}

			if (enterbut.getText().equalsIgnoreCase("Enter")) {
				String str = getCheckedText(tb2, model2, 0, 1);
				Object localObject;
				if (str.equalsIgnoreCase("HR")) {
					localObject = new HRMainFrame();
					((HRMainFrame) localObject).show();
					// ((HRMainFrame) localObject).createTrayIcon();
					f.setVisible(false);
				}
				if (str.equalsIgnoreCase("TECH")) {
					localObject = new TechMainFrame();
					((TechMainFrame) localObject).paramUser();
					((TechMainFrame) localObject).loginTime();
					((TechMainFrame) localObject).designFrame();
					f.setVisible(false);
				}
				if (str.equalsIgnoreCase("IT")) {
					localObject = new ITMainFrame();
					((ITMainFrame) localObject).px();
					f.setVisible(false);
				}
			}
			enterbut.setText("Enter");
		}
	}

	public void mousePressed(MouseEvent paramMouseEvent) {
	}

	public void mouseReleased(MouseEvent paramMouseEvent) {
	}

	public void mouseClicked(MouseEvent paramMouseEvent) {
		int i;
		String str1;
		String str2;
		if (paramMouseEvent.getSource() == tb) {
			i = tb.getSelectedRow();
			str1 = String.valueOf(model.getValueAt(i, 0));
			str2 = String.valueOf(model.getValueAt(i, 1));

			if (str1.equalsIgnoreCase("true")) {
				selectSingleCheckBox(tb, model, 0);
				branchList();
				l2.setText(str2);
			}
		}

		if (paramMouseEvent.getSource() == tb1) {
			i = tb1.getSelectedRow();
			str1 = String.valueOf(model1.getValueAt(i, 0));
			str2 = String.valueOf(model1.getValueAt(i, 1));

			if (str1.equalsIgnoreCase("true")) {
				selectSingleCheckBox(tb1, model1, 0);
				l3.setText(str2);
			}
		}

		if (paramMouseEvent.getSource() == tb2) {
			i = tb2.getSelectedRow();
			str1 = String.valueOf(model2.getValueAt(i, 0));
			str2 = String.valueOf(model2.getValueAt(i, 1));

			if (str1.equalsIgnoreCase("true")) {
				selectSingleCheckBox(tb2, model2, 0);
			}
		}
	}

	public void mouseEntered(MouseEvent paramMouseEvent) {
	}

	public void mouseExited(MouseEvent paramMouseEvent) {
	}

}
