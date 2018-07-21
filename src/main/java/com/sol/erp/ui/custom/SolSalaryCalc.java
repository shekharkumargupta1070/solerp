package com.sol.erp.ui.custom;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.sql.ResultSet;
import java.text.DecimalFormat;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.sol.erp.util.DBConnectionUtil;
import com.sol.erp.util.SessionUtil;

public class SolSalaryCalc
		implements java.awt.event.ActionListener, java.awt.event.ItemListener, java.awt.event.KeyListener {
	
	SolCellModel skc = new SolCellModel();

	

	java.sql.Connection con;

	java.sql.Statement stat;
	ResultSet rs;
	String grossStr = null;
	String packageStr = null;
	String inhandStr = null;
	String modeStr = null;
	Object[][] details = (Object[][]) null;
	Object[][] benefites = (Object[][]) null;

	String[] head = { "Heads", "Type", "Base", "Percent", "Amount" };
	Object[][] data = new Object[0][];

	String[] head1 = { " ", "Extra Benefites", "Rate", "Value", "Total" };
	Object[][] data1 = new Object[0][];

	DecimalFormat df1 = new DecimalFormat("0.00");

	JDialog f = new JDialog();
	DefaultTableModel model = new DefaultTableModel(data, head);
	JTable tb = new JTable(model);
	javax.swing.JScrollPane sp = new javax.swing.JScrollPane(tb);

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
	};
	javax.swing.JScrollPane sp1 = new javax.swing.JScrollPane(tb1);

	JLabel modeLabel = new JLabel("Salary Mode");
	JComboBox cb = new JComboBox();
	JButton b1 = new JButton("Close");

	JLabel l = new JLabel("INHand Salary");
	JLabel l1 = new JLabel("Gross Salary");
	JLabel l2 = new JLabel("Package Salary");

	JTextField tf = new JTextField("0", 6);
	JTextField tf1 = new JTextField(6);
	JTextField tf2 = new JTextField(6);

	JButton ctcbut = new JButton("Total CTC");

	JPanel northp1 = new JPanel();
	JPanel southp1 = new JPanel();
	javax.swing.JSplitPane spliter = new javax.swing.JSplitPane(1, sp, sp1);

	java.awt.Toolkit tk = java.awt.Toolkit.getDefaultToolkit();
	java.awt.Dimension ss = tk.getScreenSize();
	java.awt.Dimension fs = f.getSize();

	java.awt.Font fo = new java.awt.Font("Tahoma", 1, 11);

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

			if (paramInt1 % 2 == 0)
				setBackground(new Color(240, 240, 240));
			setForeground(Color.darkGray);

			if (paramInt1 % 2 != 0) {
				setBackground(new Color(220, 230, 250));
			}
			setForeground(Color.darkGray);

			if (paramInt2 > 1) {
				setHorizontalAlignment(4);
			}
			if (paramInt2 <= 1) {
				setHorizontalAlignment(2);
			}

			super.getTableCellRendererComponent(paramJTable, paramObject, paramBoolean1, paramBoolean2, paramInt1,
					paramInt2);

			return this;
		}
	}

	SolSalaryCalc.ColoredTableCellRenderer renderer1 = new SolSalaryCalc.ColoredTableCellRenderer();

	JPanel mainpanel = new JPanel();

	public JPanel DesignFrame() {
		f.setSize(800, 380);
		java.awt.Container localContainer = f.getContentPane();
		localContainer.setLayout(new java.awt.BorderLayout());
		mainpanel.setLayout(new java.awt.BorderLayout());
		northp1.setLayout(new java.awt.FlowLayout(0));
		southp1.setLayout(new java.awt.FlowLayout(2));

		SolTableModel.decorateTable(tb);
		SolTableModel.decorateTable(tb1);

		northp1.add(l);
		northp1.add(tf);
		northp1.add(modeLabel);
		northp1.add(cb);
		northp1.add(ctcbut);

		southp1.add(l1);
		southp1.add(tf1);
		southp1.add(l2);
		southp1.add(tf2);
		southp1.add(b1);

		tf1.setForeground(Color.red);
		tf2.setForeground(Color.blue);

		tf.setEditable(false);
		tf.setFont(fo);
		tf1.setEditable(false);
		tf1.setFont(fo);
		tf2.setEditable(false);
		tf2.setFont(fo);

		tf.setHorizontalAlignment(4);
		tf1.setHorizontalAlignment(4);
		tf2.setHorizontalAlignment(4);

		for (int i = 0; i < tb.getColumnCount(); i++) {
			tb.getColumnModel().getColumn(i).setCellRenderer(renderer1);
		}

		for (int i = 1; i < tb1.getColumnCount(); i++) {
			tb1.getColumnModel().getColumn(i).setCellRenderer(renderer1);
		}

		tb.getColumnModel().getColumn(0).setPreferredWidth(90);
		tb.setIntercellSpacing(new java.awt.Dimension(1, 1));
		tb.setGridColor(Color.white);
		tb.setSelectionBackground(new Color(220, 240, 200));
		tb.setSelectionForeground(new Color(50, 120, 50));

		tb1.getColumnModel().getColumn(0).setPreferredWidth(20);
		tb1.getColumnModel().getColumn(1).setPreferredWidth(120);
		tb1.setIntercellSpacing(new java.awt.Dimension(1, 1));
		tb1.setGridColor(Color.white);
		tb1.setSelectionBackground(new Color(220, 240, 200));
		tb1.setSelectionForeground(new Color(50, 120, 50));

		spliter.setOneTouchExpandable(true);
		spliter.setDividerLocation(f.getWidth() / 2);
		spliter.setBorder(javax.swing.BorderFactory.createEtchedBorder());

		sp.setBorder(javax.swing.BorderFactory.createTitledBorder("Basic Structures"));
		sp1.setBorder(javax.swing.BorderFactory.createTitledBorder("Advance Structures"));

		mainpanel.add(northp1, "North");
		mainpanel.add(spliter, "Center");
		mainpanel.add(southp1, "South");

		localContainer.add(mainpanel, "Center");

		cb.addItemListener(this);
		cb.addActionListener(this);
		b1.addActionListener(this);
		ctcbut.addActionListener(this);
		ctcbut.addKeyListener(this);

		cb.addKeyListener(this);
		tb.addKeyListener(this);
		tf.addKeyListener(this);
		tf1.addKeyListener(this);
		tf2.addKeyListener(this);
		b1.addKeyListener(this);

		prepareSalary();

		return mainpanel;
	}

	public void showSalaryCalc() {
		DesignFrame();
		f.setSize(900, 450);
		f.setTitle("STRUCTURES Online Salary Calculator");
		f.setLocationRelativeTo(null);
		f.setVisible(true);
		prepareSalary();
	}

	public void prepareSalary() {
		cb.removeAllItems();
		reqMode();
		reqMode();
		salStruct();
		salaryCalc();
	}

	public void reqMode() {
		String str1 = SessionUtil.getLoginInfo().getCompanyId();
		String str2 = SessionUtil.getLoginInfo().getBranchCode();

		try {
			con = DBConnectionUtil.getConnection();
			stat = con.createStatement();
			rs = stat.executeQuery("select distinct(req_mode) from HRSALARY_STRUCT where company_id='" + str1
					+ "' and branch_code='" + str2 + "' ");
			while (rs.next()) {
				String str3 = rs.getString(1);
				cb.addItem(str3);
			}
		} catch (Exception localException) {
			System.out.println(localException);
		}
	}

	public void salStruct() {
		String str1 = SessionUtil.getLoginInfo().getCompanyId();
		String str2 = SessionUtil.getLoginInfo().getBranchCode();

		String str3 = String.valueOf(cb.getSelectedItem());

		model.setNumRows(0);
		model1.setNumRows(0);

		try {
			con = DBConnectionUtil.getConnection();
			stat = con.createStatement();
			rs = stat.executeQuery("select HEAD,TYPE,BASE_PREF, PERCENT,AMOUNT from HRSALARY_STRUCT where company_id='"
					+ str1 + "' and branch_code='" + str2 + "' and req_mode='" + str3 + "' ");
			while (rs.next()) {
				String str4 = rs.getString(1);
				String str5 = rs.getString(2);
				String str6 = rs.getString(3);
				String str7 = rs.getString(4);
				String str8 = rs.getString(5);

				model.addRow(new Object[] { str4, str5, str6, str7, str8 });
			}
		} catch (Exception localException) {
			System.out.println(localException);
		}
	}

	float basicamount = 0.0F;

	public void salaryCalc() {
		float f1 = Float.parseFloat(tf.getText());
		float f3 = 0.0F;
		f3 = f1;
		String str1;
		String str2;
		float f4;
		for (int i = 0; i < tb.getRowCount(); i++) {
			str1 = String.valueOf(model.getValueAt(i, 1));
			str2 = String.valueOf(model.getValueAt(i, 2));

			if ((str1.equalsIgnoreCase("Deduct")) && (str2.equalsIgnoreCase("INHAND"))) {
				f4 = Float.parseFloat(String.valueOf(model.getValueAt(i, 3)));
				f3 = f1 - f1 * f4 / 100.0F;
				model.setValueAt(String.valueOf(f1 * f4 / 100.0F), i, 4);
			}
		}

		for (int i = 0; i < tb.getRowCount(); i++) {
			str1 = String.valueOf(model.getValueAt(i, 1));
			str2 = String.valueOf(model.getValueAt(i, 2));

			if (str1.equalsIgnoreCase("Benefite")) {
				f4 = Float.parseFloat(String.valueOf(model.getValueAt(i, 3)));
				model.setValueAt(String.valueOf(f3 * f4 / 100.0F), i, 4);
			}
		}

		for (int i = 0; i < tb.getRowCount(); i++) {
			str1 = String.valueOf(model.getValueAt(i, 0));
			if (str1.equalsIgnoreCase("Basic")) {
				basicamount = Float.parseFloat(String.valueOf(model.getValueAt(i, 4)));
			}
		}

		for (int i = 0; i < tb.getRowCount(); i++) {
			str1 = String.valueOf(model.getValueAt(i, 1));
			str2 = String.valueOf(model.getValueAt(i, 2));
			if ((str1.equalsIgnoreCase("Deduct")) && (str2.equalsIgnoreCase("BASIC"))) {
				f4 = Float.parseFloat(String.valueOf(model.getValueAt(i, 3)));
				model.setValueAt(String.valueOf(basicamount * f4 / 100.0F), i, 4);
			}
			f4 = Float.parseFloat(String.valueOf(model.getValueAt(i, 4)));
			model.setValueAt(String.valueOf(df1.format(f4)), i, 4);
		}
	}

	public void OTBENEFITE() {
		model1.setNumRows(0);
		float f1 = basicamount / 30.0F / 9.0F * 2.0F;
		float f2 = 40.0F;
		float f3 = f1 * f2;
		System.out.println("OT RATE " + f1);
		System.out.println("VALUE   " + f2);
		System.out.println("Total   " + f3);

		model1.addRow(new Object[] { new Boolean(false), "OT", String.valueOf(df1.format(f1)),
				String.valueOf(df1.format(f2)), String.valueOf(df1.format(f3)) });
	}

	public void ELENCASHMENT() {
		float f1 = basicamount / 30.0F;
		float f2 = 20.0F;
		float f3 = f1 * f2 / 12.0F;

		model1.addRow(new Object[] { new Boolean(false), "EL_ENCASHMENT", String.valueOf(df1.format(f1)),
				String.valueOf(df1.format(f2)), String.valueOf(df1.format(f3)) });
	}

	public void BONUS() {
		float f1 = basicamount * 1.5F;
		model1.addRow(new Object[] { new Boolean(false), "Bonus", String.valueOf(df1.format(f1)), "1",
				String.valueOf(df1.format(f1)) });
	}

	public void ADVANCE() {
		model1.addRow(new Object[] { new Boolean(false), "ADVANCE", String.valueOf("0"), "1", String.valueOf("00") });
	}

	public void CESS() {
		model1.addRow(new Object[] { new Boolean(false), "CESS", String.valueOf("0"), "0", String.valueOf("00") });
	}

	public void CTC() {
		float f1 = 0.0F;
		float f2 = 0.0F;
		float f4;
		for (int i = 0; i < tb.getRowCount(); i++) {
			f4 = Float.parseFloat(String.valueOf(model.getValueAt(i, 4)));
			f1 += f4;
		}

		for (int i = 0; i < tb1.getRowCount(); i++) {
			f4 = Float.parseFloat(String.valueOf(model1.getValueAt(i, 4)));
			f2 += f4;
		}

		float f3 = f1 + f2;
		f4 = f3 * 12.0F;

		tf1.setText(String.valueOf(df1.format(f3)));
		tf2.setText(String.valueOf(df1.format(f4)));
	}

	public void setInhand(String paramString) {
		tf.setText(paramString);
	}

	public void setSalaryMode(String paramString) {
		cb.setSelectedItem(paramString);
	}

	public void extraBenefite() {
		OTBENEFITE();
		ELENCASHMENT();
		BONUS();
		ADVANCE();
		CESS();
		CTC();
	}

	public String getGross() {
		grossStr = String.valueOf(tf1.getText());
		return grossStr;
	}

	public String getPackage() {
		packageStr = String.valueOf(tf2.getText());
		return packageStr;
	}

	public String getInhand() {
		grossStr = String.valueOf(tf.getText());
		return inhandStr;
	}

	public String getSalaryMode() {
		modeStr = String.valueOf(cb.getSelectedItem());
		return modeStr;
	}

	public JTable getBasicTable() {
		return tb;
	}

	public DefaultTableModel getBasicTableModel() {
		return model;
	}

	public JTable getAdvanceTable() {
		return tb1;
	}

	public DefaultTableModel getAdvanceTableModel() {
		return model1;
	}

	public Object[][] getSalaryDetails() {
		details = new String[tb.getRowCount()][tb.getColumnCount()];

		for (int i = 0; i < tb.getRowCount(); i++) {
			for (int j = 0; j < tb.getColumnCount(); j++) {
				details[i][j] = model.getValueAt(i, j);
			}
		}
		return details;
	}

	public Object[][] getExtraBenefites() {
		extraBenefite();

		benefites = new String[tb1.getRowCount()][tb1.getColumnCount() - 1];

		for (int i = 0; i < tb1.getRowCount(); i++) {
			for (int j = 1; j < tb1.getColumnCount(); j++) {
				benefites[i][(j - 1)] = model1.getValueAt(i, j);
			}
		}
		return benefites;
	}

	public void actionPerformed(java.awt.event.ActionEvent paramActionEvent) {
		if (paramActionEvent.getSource() == cb) {
			salStruct();
			salaryCalc();
			CTC();
		}
		if (paramActionEvent.getSource() == ctcbut) {
			extraBenefite();
		}
		if (paramActionEvent.getSource() == b1) {
			f.setVisible(false);
		}
	}

	public void itemStateChanged(java.awt.event.ItemEvent paramItemEvent) {
		if (paramItemEvent.getSource() == cb) {
		}
	}

	public void keyPressed(KeyEvent paramKeyEvent) {
	}

	public void keyReleased(KeyEvent paramKeyEvent) {
		int i = paramKeyEvent.getKeyCode();
		if (i == 27) {
			f.setVisible(false);
		}
	}

	public void keyTyped(KeyEvent paramKeyEvent) {
	}

}
