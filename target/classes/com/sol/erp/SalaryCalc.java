package com.sol.erp;

import java.awt.Color;
import java.awt.Container;
import java.sql.ResultSet;
import java.text.DecimalFormat;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.sol.erp.ui.custom.SolCellModel;
import com.sol.erp.ui.custom.SolTableModel;
import com.sol.erp.util.DBConnectionUtil;

public class SalaryCalc implements java.awt.event.ActionListener {
	

	SolCellModel skc = new SolCellModel();


	java.sql.Connection con;

	java.sql.Statement stat;
	ResultSet rs;
	String grossStr = null;
	String packageStr = null;
	String inhandStr = null;
	String modeStr = null;

	String[] head = { "Heads", "Type", "Percent", "Amount" };
	Object[][] data = new Object[0][];

	DecimalFormat df = new DecimalFormat("0000");
	DecimalFormat df1 = new DecimalFormat("00.00");

	JFrame f = new JFrame("Salary Calculator");
	DefaultTableModel model = new DefaultTableModel(data, head);
	JTable tb = new JTable(model);
	javax.swing.JScrollPane sp = new javax.swing.JScrollPane(tb);

	JComboBox cb = new JComboBox();
	javax.swing.JButton b1 = new javax.swing.JButton("Close");

	JLabel l = new JLabel("InHand Salary");
	JLabel l1 = new JLabel("Gross Salary");
	JLabel l2 = new JLabel("Package Salary");

	JTextField tf = new JTextField("12000", 8);
	JTextField tf1 = new JTextField(8);
	JTextField tf2 = new JTextField(8);

	JPanel northp1 = new JPanel();
	JPanel southp1 = new JPanel();

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

	SalaryCalc.ColoredTableCellRenderer renderer1 = new SalaryCalc.ColoredTableCellRenderer();

	public void showSalaryCalc() {
		Container localContainer = f.getContentPane();
		localContainer.setLayout(new java.awt.BorderLayout());
		northp1.setLayout(new java.awt.FlowLayout(2));
		southp1.setLayout(new java.awt.FlowLayout(0));

		SolTableModel.decorateTable(tb);

		northp1.add(l);
		northp1.add(tf);
		northp1.add(cb);
		northp1.add(b1);

		southp1.add(l1);
		southp1.add(tf1);
		southp1.add(l2);
		southp1.add(tf2);

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

		tb.getColumnModel().getColumn(0).setPreferredWidth(120);

		localContainer.add(northp1, "North");
		localContainer.add(sp, "Center");
		localContainer.add(southp1, "South");

		cb.addActionListener(this);
		b1.addActionListener(this);

		f.setSize(400, 300);
		f.setVisible(true);
		reqMode();
	}

	public void reqMode() {
		String str1 = "C01";
		String str2 = "0";

		cb.removeAllItems();

		try {
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
			con = java.sql.DriverManager.getConnection(
					"jdbc:odbc:Driver={MicroSoft Access Driver (*.mdb)};DBQ=D:/solgrouperp1/Erp/database/db.mdb", "",
					"");
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
		String str1 = "C01";
		String str2 = "0";

		String str3 = "Professional";

		model.setNumRows(0);

		try {
			con = DBConnectionUtil.getConnection();
			stat = con.createStatement();
			rs = stat.executeQuery("select HEAD,TYPE,PERCENT,AMOUNT from HRSALARY_STRUCT where company_id='"
					+ str1 + "' and branch_code='" + str2 + "' and req_mode='" + str3 + "' ");
			while (rs.next()) {
				String str4 = rs.getString(1);
				String str5 = rs.getString(2);
				String str6 = rs.getString(3);
				String str7 = rs.getString(4);

				model.addRow(new Object[] { str4, str5, str6, str7 });
			}
		} catch (Exception localException) {
			System.out.println(localException);
		}
	}

	public void salaryCalc() {
		float f1 = Float.parseFloat(tf.getText());
		float f2 = 0.0F;
		float f3 = 0.0F;
		float f4 = 0.0F;
		float f5 = 0.0F;

		for (int i = 0; i < tb.getRowCount(); i++) {

			float f6 = Float.parseFloat(String.valueOf(model.getValueAt(i, 2)));
			float f7;
			float f8;
			if (f6 > 0.0F) {
				f7 = Float.parseFloat(tf.getText());
				f8 = f7 * f6 / 100.0F;
				model.setValueAt(String.valueOf(df1.format(f8)), i, 3);
			}

			if (String.valueOf(model.getValueAt(i, 1)).equalsIgnoreCase("Benefite")) {
				f7 = Float.parseFloat(String.valueOf(model.getValueAt(i, 3)));
				f1 -= f7;
				f5 = f1;
			}

			if (String.valueOf(model.getValueAt(i, 1)).equalsIgnoreCase("Deduct")) {
				f7 = Float.parseFloat(String.valueOf(model.getValueAt(i, 2)));
				f8 = 100.0F - f7;
				float f9 = f7 / f8 * 100.0F;
				f4 = f1 * f9 / 100.0F;

				f3 += f4;
				f2 = f1 + f3;
			}
		}
		model.setValueAt(String.valueOf(df1.format(f5)), 0, 3);
		tf1.setText(String.valueOf(df1.format(f2)));
		tf2.setText(String.valueOf(df1.format(f2 * 12.0F)));
	}

	public void setInhand(String paramString) {
		tf.setText(paramString);
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
		modeStr = String.valueOf(tf1.getText());
		return modeStr;
	}

	public void actionPerformed(java.awt.event.ActionEvent paramActionEvent) {
		if (paramActionEvent.getSource() == cb) {
			salStruct();
			salaryCalc();
		}
		if (paramActionEvent.getSource() == b1) {
			f.setVisible(false);
		}
	}

}
