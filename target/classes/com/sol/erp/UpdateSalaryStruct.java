package com.sol.erp;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import com.sol.erp.ui.custom.SolSalaryCalc;
import com.sol.erp.ui.custom.SolTableModel;
import com.sol.erp.util.DBConnectionUtil;
import com.sol.erp.util.SessionUtil;

public class UpdateSalaryStruct implements ActionListener {
	Connection con;
	Statement stat;
	ResultSet rs;
	
	SolSalaryCalc sksal;
	
	String[] head;
	Object[][] data;
	JFrame f;
	JPanel mainpanel;
	JProgressBar progressBar;
	DefaultTableModel model;
	JTable tb;
	JScrollPane sp;
	JButton salbut;
	JButton showbut;
	JButton updatebut;
	JPanel northpanel;
	JPanel southpanel;
	JPanel centerpanel;
	JPanel northwestpanel;
	JPanel northeastpanel;
	ColoredTableCellRenderer skr;
	Font fo;

	class ColoredTableCellRenderer extends DefaultTableCellRenderer {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		ColoredTableCellRenderer() {
		}

		public Component getTableCellRendererComponent(JTable paramJTable, Object paramObject, boolean paramBoolean1,
				boolean paramBoolean2, int paramInt1, int paramInt2) {
			setEnabled((paramJTable == null) || (paramJTable.isEnabled()));

			if (paramInt2 > 0) {
				setHorizontalAlignment(0);
			}

			if (paramInt1 % 2 == 0) {
				setBackground(new Color(240, 240, 240));
				setForeground(Color.darkGray);

			} else {
				setBackground(new Color(230, 230, 230));
				setForeground(Color.darkGray);
			}

			super.getTableCellRendererComponent(paramJTable, paramObject, paramBoolean1, paramBoolean2, paramInt1,
					paramInt2);

			return this;
		}
	}

	public UpdateSalaryStruct() {
		con = null;
		stat = null;
		rs = null;

		
		sksal = new SolSalaryCalc();
		

		head = new String[] { "Punch Card", "Emp Code", "Salary" };
		data = new Object[0][];

		f = new JFrame("Update Salary Structrues for All");
		mainpanel = new JPanel();

		progressBar = new JProgressBar(0, 100);

		model = new DefaultTableModel(data, head);
		tb = new JTable(model) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int paramAnonymousInt1, int paramAnonymousInt2) {
				int i = convertColumnIndexToModel(paramAnonymousInt2);
				return i < 0;
			}
		};
		sp = new JScrollPane(tb);

		salbut = new JButton("Salary Struct");
		showbut = new JButton("Show Emp");
		updatebut = new JButton("Update Salary Struct");

		northpanel = new JPanel();
		southpanel = new JPanel();

		centerpanel = new JPanel();

		northwestpanel = new JPanel();
		northeastpanel = new JPanel();

		skr = new ColoredTableCellRenderer();
		fo = new Font("Tahoma", 1, 10);
	}

	public void DesignFrame() {
		Container localContainer = f.getContentPane();
		localContainer.setLayout(new BorderLayout());
		mainpanel.setLayout(new BorderLayout());
		southpanel.setLayout(new FlowLayout(0));
		northpanel.setLayout(new BorderLayout());
		centerpanel.setLayout(new BorderLayout());

		sp.setPreferredSize(new Dimension(210, 210));
		SolTableModel.decorateTable(tb);
		tb.setIntercellSpacing(new Dimension(0, 1));
		tb.setShowGrid(false);

		for (int i = 0; i < tb.getColumnCount(); i++) {
			tb.getColumnModel().getColumn(i).setCellRenderer(skr);
		}

		progressBar.setPreferredSize(new Dimension(250, 21));
		progressBar.setForeground(new Color(110, 110, 180));
		progressBar.setStringPainted(true);

		northwestpanel.add(salbut);
		northeastpanel.add(progressBar);

		northpanel.add(northwestpanel, "West");
		northpanel.add(northeastpanel, "East");

		southpanel.add(showbut);
		southpanel.add(updatebut);

		centerpanel.setBorder(BorderFactory.createBevelBorder(1));

		centerpanel.add(sp, "West");
		centerpanel.add(sksal.DesignFrame(), "Center");
		sp.setBorder(BorderFactory.createBevelBorder(1));

		mainpanel.add(northpanel, "North");
		mainpanel.add(centerpanel, "Center");
		mainpanel.add(southpanel, "South");

		localContainer.add(mainpanel, "Center");

		salbut.addActionListener(this);
		showbut.addActionListener(this);
		updatebut.addActionListener(this);

		f.setSize(950, 500);
		f.setLocationRelativeTo(null);
		f.setVisible(true);
	}

	public void showEmpList() {
		String str1 = SessionUtil.getLoginInfo().getCompanyId();
		String str2 = SessionUtil.getLoginInfo().getBranchCode();
		model.setNumRows(0);
		String str3 = sksal.getSalaryMode();
		System.out.println("Mode:" + str3);

		try {
			con = DBConnectionUtil.getConnection();
			stat = con.createStatement();
			rs = stat
					.executeQuery("select PunchCard_no, Emp_Code, Basic_Salary from HREMP_JOIN  where co_id ='" + str1
							+ "' and br_code='" + str2 + "' and req_mode = '" + str3 + "' ");
			while (rs.next()) {
				String str4 = new String(rs.getString(1));
				String str5 = new String(rs.getString(2));
				String str6 = new String(rs.getString(3));

				model.addRow(new Object[] { str4, str5, str6 });
			}
		} catch (Exception localException) {
			System.out.println(localException);
		}
	}

	public void saveEmpSalary(int paramInt) {
		String str1 = SessionUtil.getLoginInfo().getCompanyId();
		String str2 = SessionUtil.getLoginInfo().getBranchCode();

		String str3 = String.valueOf(model.getValueAt(paramInt, 1));
		String str4 = sksal.getSalaryMode();

		try {
			stat = con.createStatement();
			stat.executeUpdate("delete from hr_emp_salary where company_id='" + str1 + "' and branch_code='" + str2
					+ "' and emp_code='" + str3 + "'  ");
		} catch (Exception localException1) {
			System.out.println("HR_EMP_SALARY :" + localException1);
		}

		for (int i = 0; i < sksal.getBasicTable().getRowCount(); i++) {
			String str5 = String.valueOf(sksal.getBasicTableModel().getValueAt(i, 0));
			String str6 = String.valueOf(sksal.getBasicTableModel().getValueAt(i, 1));
			String str7 = String.valueOf(sksal.getBasicTableModel().getValueAt(i, 2));
			String str8 = String.valueOf(sksal.getBasicTableModel().getValueAt(i, 3));
			String str9 = String.valueOf(sksal.getBasicTableModel().getValueAt(i, 4));

			try {
				stat = con.createStatement();
				stat.executeUpdate("insert into HR_EMP_SALARY values('" + str1 + "','" + str2 + "','" + str3
						+ "','" + str4 + "', '" + str5 + "', '" + str6 + "', '" + str7 + "', '" + str8 + "','" + str9
						+ "')  ");
			} catch (Exception localException2) {
				System.out.println("************************************");
				System.out.println("Update Salary Struct(HR_EMP_SALARY): " + localException2);
				System.out.println("************************************");
			}
		}
	}

	public void saveExtraSalary(int paramInt) {
		String str1 = SessionUtil.getLoginInfo().getCompanyId();
		String str2 = SessionUtil.getLoginInfo().getBranchCode();

		String str3 = String.valueOf(model.getValueAt(paramInt, 1));
		String str4 = sksal.getSalaryMode();

		try {
			stat = con.createStatement();
			stat.executeUpdate("delete from hr_emp_extra where company_id='" + str1 + "' and branch_code='" + str2
					+ "' and emp_code='" + str3 + "'  ");
		} catch (Exception localException1) {
			System.out.println("HR_EMP_EXTRA :" + localException1);
		}

		for (int i = 0; i < sksal.getAdvanceTable().getRowCount(); i++) {
			String str5 = String.valueOf(sksal.getAdvanceTableModel().getValueAt(i, 1));
			String str6 = String.valueOf(sksal.getAdvanceTableModel().getValueAt(i, 2));
			String str7 = String.valueOf(sksal.getAdvanceTableModel().getValueAt(i, 3));
			String str8 = String.valueOf(sksal.getAdvanceTableModel().getValueAt(i, 4));

			try {
				stat = con.createStatement();
				stat.executeUpdate("insert into HR_EMP_EXTRA values('" + str1 + "','" + str2 + "','" + str3 + "','"
						+ str4 + "', '" + str5 + "', '" + str6 + "', '" + str7 + "', '" + str8 + "','0','0')  ");
			} catch (Exception localException2) {
				System.out.println("************************************");
				System.out.println("Update Salary Struct (HR_EMP_EXTRA): " + localException2);
				System.out.println("************************************");
			}
		}
	}

	public class updateSalary extends Thread {
		public updateSalary() {
		}

		public void run() {
			progressBar.setMaximum(tb.getRowCount() - 1);

			for (int i = 0; i < tb.getRowCount(); i++) {
				String str = String.valueOf(model.getValueAt(i, 2));
				sksal.setInhand(str);
				sksal.salaryCalc();
				sksal.extraBenefite();

				saveEmpSalary(i);
				saveExtraSalary(i);

				try {
					Thread.sleep(100L);
				} catch (InterruptedException localInterruptedException) {
					System.out.println(localInterruptedException);
				}

				tb.setSelectionBackground(new Color(150, 150, 220));
				tb.setSelectionForeground(Color.white);
				tb.setRowSelectionInterval(i, i);
				progressBar.setValue(i);
			}
		}
	}

	public void actionPerformed(ActionEvent paramActionEvent) {
		if (paramActionEvent.getSource() == showbut) {
			showEmpList();
		}
		Object localObject;
		if (paramActionEvent.getSource() == updatebut) {
			showEmpList();
			localObject = new updateSalary();
			((updateSalary) localObject).start();
		}
		if (paramActionEvent.getSource() == salbut) {
			localObject = new DefReqMode();
			((DefReqMode) localObject).px();
		}
	}
}
	