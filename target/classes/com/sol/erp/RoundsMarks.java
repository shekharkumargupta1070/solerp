package com.sol.erp;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.FocusEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableModel;

import com.sol.erp.ui.custom.SolCalendar;
import com.sol.erp.ui.custom.SolCellModel;
import com.sol.erp.ui.custom.SolTableModel;
import com.sol.erp.util.DBConnectionUtil;

public class RoundsMarks implements javax.swing.event.TableModelListener, java.awt.event.ActionListener,
		java.awt.event.MouseListener, java.awt.event.KeyListener, java.awt.event.FocusListener {
	java.sql.Connection con;
	Statement stat;
	ResultSet rs;
	int col;

	SolCellModel skc = new SolCellModel();
	SolCalendar skl = new SolCalendar();

	String[] head = { "App No", "Name", "Tech", "HR", "Final", "Total", "Exp Join Date", "Exp Salary" };

	String[][] data = new String[0][];

	JInternalFrame f = new JInternalFrame("Rounds & Marks", true, true, true, true);

	DefaultTableModel model1 = new DefaultTableModel(data, head);
	JTable jTable1 = new JTable(model1);
	javax.swing.JScrollPane jScrollPane1 = new javax.swing.JScrollPane(jTable1);

	JLabel jLabel1 = new JLabel("Date");
	JLabel jLabel2 = new JLabel("Aplicant No");

	JTextField jTextField1 = new JTextField(8);
	JTextField jTextField2 = new JTextField(12);

	JButton jButton1 = new JButton("Search");
	JButton jButton2 = new JButton("Close");

	JPanel jPanel1 = new JPanel();
	JPanel jPanel2 = new JPanel();

	public void px() {
		Container localContainer = f.getContentPane();
		localContainer.setLayout(new java.awt.BorderLayout());

		jPanel1.setLayout(new java.awt.FlowLayout(0));
		jPanel2.setLayout(new java.awt.FlowLayout(2));

		jPanel1.add(jLabel1);
		jPanel1.add(jTextField1);
		jTextField1.setFont(new java.awt.Font("Tahoma", 1, 11));

		jPanel2.add(jLabel2);
		jPanel2.add(jTextField2);

		jPanel2.add(jButton1);
		jPanel2.add(jButton2);

		localContainer.add(jPanel1, "North");
		localContainer.add(jScrollPane1, "Center");
		localContainer.add(jPanel2, "South");

		Dimension localDimension = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
		f.setBounds((localDimension.width - 819) / 2, (localDimension.height - 600) / 2, 819, 517);

		SolTableModel.decorateTable(jTable1);

		for (int i = 0; i < jTable1.getColumnCount(); i++) {
			jTable1.getColumnModel().getColumn(i).setCellRenderer(skc);
		}

		jTable1.getColumnModel().getColumn(1).setPreferredWidth(230);

		jTable1.getColumnModel().getColumn(2).setPreferredWidth(10);
		jTable1.getColumnModel().getColumn(3).setPreferredWidth(10);
		jTable1.getColumnModel().getColumn(4).setPreferredWidth(10);
		jTable1.getColumnModel().getColumn(5).setPreferredWidth(15);

		jTable1.setShowGrid(true);
		jTable1.setIntercellSpacing(new Dimension(1, 1));

		f.setVisible(true);

		model1.addTableModelListener(this);
		jButton2.addActionListener(this);

		jTextField1.addMouseListener(this);
		jTextField1.addActionListener(this);
		jTextField1.addKeyListener(this);
		jTextField1.addFocusListener(this);

		jTable1.addMouseListener(this);
		jTable1.addKeyListener(this);
	}

	public void aplicantList() {
		model1.setNumRows(0);
		try {
			con = DBConnectionUtil.getConnection();
			stat = con.createStatement();
			rs = stat.executeQuery(
					"select Aplicant_no, FIRST_NAME, MIDDLE_NAME, LAST_NAME from HRINTERVIEW_CALL WHERE APLICANT_NO NOT LIKE '0' AND DATE='"
							+ jTextField1.getText() + "' ORDER BY APLICANT_NO");

			while (rs.next()) {
				String str1 = new String(rs.getString(1));
				String str2 = new String(rs.getString(2));
				String str3 = new String(rs.getString(3));
				String str4 = new String(rs.getString(4));

				model1.addRow(new Object[] { str1, str2 + " " + str3 + " " + str4 });
			}
		} catch (Exception localException) {
			System.out.println(localException);
		}
	}

	public void scoreList() {
		int i = jTable1.getRowCount();

		for (int j = 0; j < i; j++) {
			String str1 = String.valueOf(model1.getValueAt(j, 0));

			try {
				rs = stat
						.executeQuery("select r1,r2,r3,total,exp_join_date,exp_salary from HRROUNDS where aplicant_no='"
								+ str1 + "' ");

				while (rs.next()) {
					String str2 = new String(rs.getString(1));
					String str3 = new String(rs.getString(2));
					String str4 = new String(rs.getString(3));
					String str5 = new String(rs.getString(4));
					String str6 = new String(rs.getString(5));
					String str7 = new String(rs.getString(6));

					if (str2.equalsIgnoreCase("null")) {
						str2 = "";
					}
					if (str3.equalsIgnoreCase("null")) {
						str3 = "";
					}
					if (str4.equalsIgnoreCase("null")) {
						str4 = "";
					}
					if (str5.equalsIgnoreCase("null")) {
						str5 = "";
					}
					if (str6.equalsIgnoreCase("null")) {
						str6 = "";
					}
					if (str7.equalsIgnoreCase("null")) {
						str7 = "";
					}

					model1.setValueAt(str2, j, 2);
					model1.setValueAt(str3, j, 3);
					model1.setValueAt(str4, j, 4);
					model1.setValueAt(str5, j, 5);
					model1.setValueAt(str6, j, 6);
					model1.setValueAt(str7, j, 7);
				}
			} catch (Exception localException) {
				System.out.println(localException);
			}
		}
	}

	public void actionPerformed(java.awt.event.ActionEvent paramActionEvent) {
		if (paramActionEvent.getSource() == jButton2) {
			f.setVisible(false);
		}
		if (paramActionEvent.getSource() == jTextField1) {
			aplicantList();
			scoreList();
		}
	}

	public void mousePressed(MouseEvent paramMouseEvent) {
		if (paramMouseEvent.getSource() == jTextField1) {
			if (javax.swing.SwingUtilities.isRightMouseButton(paramMouseEvent)) {
				skl.showCalendar();
				skl.getDate(jTextField1);
			}
		}
		if (paramMouseEvent.getSource() == jTable1) {
			col = jTable1.getSelectedColumn();
			System.out.println("Column No : " + col);
		}
	}

	public void mouseReleased(MouseEvent paramMouseEvent) {
	}

	public void mouseClicked(MouseEvent paramMouseEvent) {
	}

	public void mouseEntered(MouseEvent paramMouseEvent) {
	}

	public void mouseExited(MouseEvent paramMouseEvent) {
	}

	public void keyPressed(KeyEvent paramKeyEvent) {
	}

	public void keyReleased(KeyEvent paramKeyEvent) {
		if (paramKeyEvent.getSource() == jTable1) {
			col = jTable1.getSelectedColumn();
			int i = paramKeyEvent.getKeyCode();

			if (i == 9) {
				if (col == 5) {
					int j = 0;
					int k = 0;
					int m = 0;
					int n = jTable1.getSelectedRow();

					j = Integer.parseInt(String.valueOf(model1.getValueAt(n, 2)));
					k = Integer.parseInt(String.valueOf(model1.getValueAt(n, 3)));
					m = Integer.parseInt(String.valueOf(model1.getValueAt(n, 4)));
					int i1 = j + k + m;
					model1.setValueAt(String.valueOf(i1), n, 5);
				}
			}
		}
	}

	public void keyTyped(KeyEvent paramKeyEvent) {
	}

	public void focusGained(FocusEvent paramFocusEvent) {
	}

	public void focusLost(FocusEvent paramFocusEvent) {
		if (paramFocusEvent.getSource() == jTextField1) {
		}
	}

	public void tableChanged(TableModelEvent paramTableModelEvent) {
		if (paramTableModelEvent.getSource() == model1) {
			int i = jTable1.getSelectedRow();
			String str1;
			String str2;
			String str3;
			if (col == 2) {
				str1 = String.valueOf(model1.getValueAt(i, 0));
				str2 = String.valueOf(model1.getValueAt(i, 1));
				str3 = String.valueOf(model1.getValueAt(i, 2));
				try {
					stat.executeUpdate("Insert into HRROUNDS values('" + jTextField1.getText() + "','" + str1
							+ "','" + str2 + "','" + str3 + "','0','0','0','0','0')");
				} catch (Exception localException1) {
					System.out.println(localException1);
				}
			}
			if (col >= 2) {
				str1 = String.valueOf(model1.getValueAt(i, 0));
				str2 = String.valueOf(model1.getValueAt(i, 3));
				str3 = String.valueOf(model1.getValueAt(i, 4));
				String str4 = String.valueOf(model1.getValueAt(i, 5));
				String str5 = String.valueOf(model1.getValueAt(i, 6));
				String str6 = String.valueOf(model1.getValueAt(i, 7));

				try {
					stat.executeUpdate("update HRROUNDS set r2='" + str2 + "', r3='" + str3 + "', total='" + str4
							+ "', exp_join_date='" + str5 + "', exp_salary='" + str6 + "' where aplicant_no='" + str1
							+ "'  ");
				} catch (Exception localException2) {
					System.out.println(localException2);
				}
			}
		}
	}

	public void TableCellUpdated(TableModelEvent paramTableModelEvent) {
		if (paramTableModelEvent.getSource() == model1) {
		}
	}
}
