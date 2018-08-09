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
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import com.sol.erp.ui.custom.SolTableModel;
import com.sol.erp.util.DBConnectionUtil;
import com.sol.erp.util.DateCalculationUtil;

public class LeaveStatus implements ActionListener {
	
	
	Connection con;
	PreparedStatement prsm;
	ResultSet rs;
	
	SolTableModel skt;
	
	String[] heads;
	Object[][] data;
	JFrame f;
	DefaultTableModel model;
	JTable tb;
	JScrollPane sp;
	JLabel l2;
	public static JTextField tf2 = new JTextField(5);

	JButton b3;
	JLabel formatlabel;
	JLabel l1;
	public static JTextField tf1 = new JTextField(10);

	JButton b1;

	JButton b2;

	JProgressBar progress;

	JProgressBar progress2;

	JPanel centerpanel;
	JPanel mainpanel;
	JPanel southpanel;
	JPanel northpanel;
	JPanel northeastpanel;
	JPanel northwestpanel;
	Font fo;
	ColoredTableCellRenderer skr;

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
				setBackground(new Color(250, 250, 250));
				setForeground(Color.darkGray);
			}

			if (paramInt2 == 0) {
				setHorizontalAlignment(0);
			}
			if (paramInt2 > 1) {
				setHorizontalAlignment(4);
			}

			super.getTableCellRendererComponent(paramJTable, paramObject, paramBoolean1, paramBoolean2, paramInt1,
					paramInt2);

			return this;
		}
	}

	public LeaveStatus() {
		skt = new SolTableModel();
		

		heads = new String[] { "EMP Name", "JAN\n", "FEB", "MAR", "APR", "MAY", "JUN", "JUL", "AUG", "SEP", "OCT",
				"NOV", "DEC", "TOTAL" };
		data = new Object[0][];

		f = new JFrame("Leave Status");

		model = new DefaultTableModel(data, heads);
		tb = new JTable(model) {
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
				return i < 0;
			}
		};
		sp = new JScrollPane(tb);

		l2 = new JLabel("EMP Code");

		b3 = new JButton("Search");
		formatlabel = new JLabel(
				"<HTML> Leave Type Format : <Font color='RED'>LWP, <Font color='GREEN'>CL, <Font color='BLUE'>EL");

		l1 = new JLabel("Date");

		b1 = new JButton("Run Report");
		b2 = new JButton("Calculate");

		progress = new JProgressBar(0, 100);
		progress2 = new JProgressBar(0, 100);

		centerpanel = new JPanel();
		mainpanel = new JPanel();

		southpanel = new JPanel();
		northpanel = new JPanel();

		northeastpanel = new JPanel();
		northwestpanel = new JPanel();

		fo = new Font("Verdana", 0, 11);

		skr = new ColoredTableCellRenderer();
	}

	public JPanel DesignFrame() {
		con = DBConnectionUtil.getConnection();

		Container localContainer = f.getContentPane();
		localContainer.setLayout(new BorderLayout());
		centerpanel.setLayout(new BorderLayout());
		mainpanel.setLayout(new BorderLayout());
		southpanel.setLayout(new FlowLayout(2));
		northeastpanel.setLayout(new FlowLayout(2));
		northwestpanel.setLayout(new FlowLayout(2));
		northpanel.setLayout(new BorderLayout());

		tb.setFont(fo);

		tb.setRowHeight(20);
		tb.getTableHeader().setPreferredSize(new Dimension(50, 25));
		tb.getTableHeader().setFont(fo);

		tb.setGridColor(Color.white);
		tb.setIntercellSpacing(new Dimension(1, 1));

		tb.getColumnModel().getColumn(0).setPreferredWidth(80);

		for (int i = 0; i < tb.getColumnCount(); i++) {
			tb.getColumnModel().getColumn(i).setCellRenderer(skr);
		}

		northwestpanel.add(l2);
		northwestpanel.add(tf2);
		tf2.setFont(fo);

		northwestpanel.add(l1);
		northwestpanel.add(tf1);
		tf1.setFont(fo);
		tf1.setPreferredSize(new Dimension(100, 20));
		tf1.setHorizontalAlignment(4);
		northwestpanel.add(b1);

		northeastpanel.add(formatlabel);

		southpanel.add(progress2);
		progress2.setForeground(Color.blue);
		progress2.setPreferredSize(new Dimension(280, 14));

		southpanel.add(progress);
		progress.setStringPainted(true);
		progress.setForeground(Color.red);
		progress.setPreferredSize(new Dimension(130, 20));

		northpanel.add(northeastpanel, "East");
		northpanel.add(northwestpanel, "West");

		centerpanel.add(sp, "Center");

		mainpanel.add(northpanel, "North");
		mainpanel.add(centerpanel, "Center");
		mainpanel.add(southpanel, "South");

		localContainer.add(mainpanel, "Center");

		b1.addActionListener(this);
		b2.addActionListener(this);
		b3.addActionListener(this);

		tf1.addActionListener(this);
		tf2.addActionListener(this);

		return mainpanel;
	}

	public void px() {
		DesignFrame();
		f.setSize(800, 600);
		f.setVisible(true);
	}

	public void empList() {
		model.setNumRows(0);
		String str1 = "%" + tf2.getText();

		try {
			con = DBConnectionUtil.getConnection();
			prsm = con
					.prepareStatement("Select EMP_CODE from EMP_STATUS where EMP_CODE LIKE '" + str1 + "' ");
			rs = prsm.executeQuery();

			while (rs.next()) {
				String str2 = new String(rs.getString(1));

				model.addRow(new Object[] { str2, "", "", "", "", "", "", "", "", "", "", "", "" });
			}
		} catch (Exception localException) {
			System.out.println(localException);
		}
	}

	public void totalLeaves() {
		for (int i = 0; i < tb.getRowCount(); i++) {
			String str1 = String.valueOf(model.getValueAt(i, 0));

			@SuppressWarnings("deprecation")
			Date localDate1 = new Date(108, 0, 1);
			@SuppressWarnings("deprecation")
			Date localDate2 = new Date(108, 11, 31);

			try {
				prsm = con.prepareStatement(
						"Select (select count(category) from HREMP_LEAVES where category='LWP' and emp_CODE='" + str1
								+ "' and date between ? and ? ), "
								+ " (select count(category) from HREMP_LEAVES where category='CL' and emp_CODE='" + str1
								+ "'  and date between ? and ? ), "
								+ " (select count(category) from HREMP_LEAVES where category='EL' and emp_CODE='" + str1
								+ "'  and date between ? and ? ) " + " from HREMP_LEAVES ");

				prsm.setDate(1, localDate1);
				prsm.setDate(2, localDate2);

				prsm.setDate(3, localDate1);
				prsm.setDate(4, localDate2);

				prsm.setDate(5, localDate1);
				prsm.setDate(6, localDate2);

				rs = prsm.executeQuery();

				while (rs.next()) {
					String str2 = new String(rs.getString(1));
					String str3 = new String(rs.getString(2));
					String str4 = new String(rs.getString(3));

					int j = Integer.parseInt(str2);
					int k = Integer.parseInt(str3);
					int m = Integer.parseInt(str4);

					if (j >= 1) {
						str2 = "<html><font color=red>" + str2 + "</font>";
					}
					if (j <= 0) {
						str2 = "<html><font color=black>" + str2 + "</font>";
					}

					if (k >= 1) {
						str3 = "<html><font color=green>" + str3 + "</font>";
					}
					if (j <= 0) {
						str3 = "<html><font color=black>" + str3 + "</font>";
					}

					if (m >= 1) {
						str4 = "<html><font color=blue>" + str4 + "</font>";
					}
					if (j <= 0) {
						str4 = "<html><font color=black>" + str4 + "</font>";
					}

					model.setValueAt(str2 + ", " + str3 + ", " + str4, i, 13);
				}
			} catch (Exception localException) {
				System.out.println(localException);
			}
		}
	}

	public class LeaveReport extends Thread {
		public LeaveReport() {
		}

		public void run() {
			for (int i = 0; i < tb.getRowCount(); i++) {
				try {
					Thread.sleep(10L);
				} catch (InterruptedException localInterruptedException) {
					System.out.println(localInterruptedException);
				}
				progress.setMinimum(0);
				progress.setMaximum(tb.getRowCount() - 1);
				progress.setValue(i);

				if (progress.getValue() == 100) {
					b2.setEnabled(true);
				}

				String str1 = String.valueOf(model.getValueAt(i, 0));

				for (int j = 1; j <= 12; j++) {
					progress2.setMinimum(0);
					progress2.setMaximum(12);
					progress2.setValue(j);

					int k = DateCalculationUtil.getYear(tf1.getText());

					@SuppressWarnings("deprecation")
					Date localDate1 = new Date(k - 1900, j - 1, 1);
					@SuppressWarnings("deprecation")
					Date localDate2 = new Date(k - 1900, j - 1, 31);

					try {
						prsm = con.prepareStatement(
								"Select (select count(category) from HREMP_LEAVES where category='LWP' and emp_CODE='"
										+ str1 + "' and date between ? and ? ), "
										+ " (select count(category) from HREMP_LEAVES where category='CL' and emp_CODE='"
										+ str1 + "'  and date between ? and ? ), "
										+ " (select count(category) from HREMP_LEAVES where category='EL' and emp_CODE='"
										+ str1 + "'  and date between ? and ? ) " + " from HREMP_LEAVES ");

						prsm.setDate(1, localDate1);
						prsm.setDate(2, localDate2);

						prsm.setDate(3, localDate1);
						prsm.setDate(4, localDate2);

						prsm.setDate(5, localDate1);
						prsm.setDate(6, localDate2);

						rs = prsm.executeQuery();

						while (rs.next()) {
							String str2 = new String(rs.getString(1));
							String str3 = new String(rs.getString(2));
							String str4 = new String(rs.getString(3));

							int m = Integer.parseInt(str2);
							int n = Integer.parseInt(str3);
							int i1 = Integer.parseInt(str4);

							if (m >= 1) {
								str2 = "<html><font color=red>" + str2 + "</font>";
							}
							if (m <= 0) {
								str2 = "<html><font color=black>" + str2 + "</font>";
							}

							if (n >= 1) {
								str3 = "<html><font color=green>" + str3 + "</font>";
							}
							if (m <= 0) {
								str3 = "<html><font color=black>" + str3 + "</font>";
							}

							if (i1 >= 1) {
								str4 = "<html><font color=blue>" + str4 + "</font>";
							}
							if (m <= 0) {
								str4 = "<html><font color=black>" + str4 + "</font>";
							}

							model.setValueAt(str2 + ", " + str3 + ", " + str4, i, j);
						}
					} catch (Exception localException) {
						System.out.println(localException);
					}
				}
			}
			b2.setEnabled(true);
			totalLeaves();
		}
	}

	public void actionPerformed(ActionEvent paramActionEvent) {
		if ((paramActionEvent.getSource() == b3) || (paramActionEvent.getSource() == tf2)) {
			empList();
		}
		if ((paramActionEvent.getSource() == b1) || (paramActionEvent.getSource() == tf1)) {
			b2.setEnabled(false);
			LeaveReport localLeaveReport = new LeaveReport();
			localLeaveReport.start();
		}
		if (paramActionEvent.getSource() == b2) {
			totalLeaves();
		}
	}
}
