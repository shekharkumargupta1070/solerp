package com.sol.erp;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;

import com.sol.erp.ui.custom.SolCalendar;
import com.sol.erp.util.DBConnectionUtil;
import com.sol.erp.util.DateCalculationUtil;

public class PrevSalary
		implements java.awt.event.ActionListener, java.awt.event.KeyListener, java.awt.event.MouseListener {
	
	SolCalendar skl = new SolCalendar();

	JFrame f = new JFrame("Previous Salary Report");

	JComboBox cb = new JComboBox();
	javax.swing.JButton b1 = new javax.swing.JButton("Show Report");

	javax.swing.JScrollPane sp;
	JTable tb;
	JLabel l1 = new JLabel("Company Id");
	JLabel l2 = new JLabel("Branch Code");
	JLabel l3 = new JLabel("Salary Mode");
	JLabel l4 = new JLabel("Year & Month");

	JTextField tf1 = new JTextField(3);
	JTextField tf2 = new JTextField(3);
	JTextField tf3 = new JTextField(10);
	JTextField tf4 = new JTextField(6);

	JPanel p1 = new JPanel();
	JPanel centerpanel = new JPanel();

	JPanel mainpanel = new JPanel();

	public JPanel DesignFrame() {
		java.awt.Container localContainer = f.getContentPane();
		localContainer.setLayout(new java.awt.BorderLayout());
		p1.setLayout(new java.awt.FlowLayout(0));
		centerpanel.setLayout(new java.awt.BorderLayout());
		mainpanel.setLayout(new java.awt.BorderLayout());

		cb.addItem("HREmp_JOIN");
		cb.addItem("Punchcard");
		cb.addItem("HRTIMEMASTER");

		p1.add(l1);
		p1.add(tf1);
		p1.add(l2);
		p1.add(tf2);
		p1.add(l3);
		p1.add(tf3);
		p1.add(l4);
		p1.add(tf4);

		tf2.setEditable(false);
		tf3.setEditable(false);
		tf4.setEditable(false);

		p1.add(b1);

		mainpanel.add(p1, "North");
		mainpanel.add(centerpanel, "Center");

		centerpanel.setBackground(java.awt.Color.darkGray);

		localContainer.add(mainpanel, "Center");

		b1.addActionListener(this);
		cb.addActionListener(this);

		tf4.addMouseListener(this);

		tf1.addKeyListener(this);
		tf1.addActionListener(this);

		return mainpanel;
	}

	public void showFrame() {
		DesignFrame();
		f.setSize(800, 580);
		f.setLocationRelativeTo(null);
		f.setVisible(true);
		tf1.requestFocus();
	}

	public void getRecord() {
		String str1 = tf1.getText();
		String str2 = tf2.getText();
		String str3 = tf3.getText();

		String str4 = String.valueOf(DateCalculationUtil.getYear(tf4.getText()));
		String str5 = DateCalculationUtil.getNameofMonth(DateCalculationUtil.getMonth(tf4.getText()) - 1);

		String str6 = "111_" + str1 + "_" + str2 + "_" + str3 + "_" + str4 + "_" + str5;

		if (sp != null) {
			centerpanel.remove(sp);
		}
		try {
			java.sql.Connection localConnection = DBConnectionUtil.getConnection();
			java.sql.Statement localStatement = localConnection.createStatement();
			ResultSet localResultSet = localStatement.executeQuery("select * from " + str6);
			int i = 0;
			int j = 0;
			while (localResultSet.next()) {
				i++;
			}
			localResultSet = localStatement.executeQuery("select * from " + str6);
			j = localResultSet.getMetaData().getColumnCount() - 1;
			Object[][] arrayOfObject = new Object[i][j];
			localResultSet = localStatement.executeQuery("select * from " + str6);
			for (int k = 0; localResultSet.next(); k++) {
				for (int m = 0; m < j; m++) {
					arrayOfObject[k][m] = localResultSet.getString(m + 1);
				}
			}

			String[] arrayOfString = new String[j];
			localResultSet = localStatement.executeQuery("select * from " + str6);
			for (int m = 0; m < j; m++) {
				arrayOfString[m] = localResultSet.getMetaData().getColumnLabel(m + 1);
			}

			tb = new JTable(arrayOfObject, arrayOfString) {

				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				public boolean isCellEditable(int paramAnonymousInt1, int paramAnonymousInt2) {
					int i = convertColumnIndexToModel(paramAnonymousInt2);
					return i < 0;

				}

			};
			sp = new javax.swing.JScrollPane(tb);

			tb.setAutoResizeMode(0);
			tb.getTableHeader().setPreferredSize(new java.awt.Dimension(50, 25));
			tb.getTableHeader().setFont(new java.awt.Font("Verdana", 0, 10));
			tb.setRowHeight(40);
			tb.setBackground(java.awt.Color.white);
			tb.setFont(new java.awt.Font("Verdana", 0, 10));
			tb.setSelectionBackground(new java.awt.Color(170, 200, 170));
			tb.setSelectionForeground(java.awt.Color.white);

			tb.setIntercellSpacing(new java.awt.Dimension(1, 1));

			centerpanel.add(sp, "Center");
			tb.setBackground(new java.awt.Color(250, 250, 200));
			centerpanel.revalidate();
		} catch (Exception localException) {
			javax.swing.JOptionPane.showMessageDialog(f, localException.getMessage().toString());
		}
	}

	public void removeColumn() {
		tb.removeColumn(tb.getColumnModel().getColumn(0));
		tb.removeColumn(tb.getColumnModel().getColumn(3));
		tb.removeColumn(tb.getColumnModel().getColumn(3));
		tb.removeColumn(tb.getColumnModel().getColumn(4));
		tb.removeColumn(tb.getColumnModel().getColumn(tb.getColumnCount() - 1));
		tb.removeColumn(tb.getColumnModel().getColumn(tb.getColumnCount() - 1));

		tb.getColumnModel().getColumn(0).setPreferredWidth(50);
		tb.getColumnModel().getColumn(1).setPreferredWidth(40);
		tb.getColumnModel().getColumn(2).setPreferredWidth(150);
		tb.getColumnModel().getColumn(3).setPreferredWidth(30);
		tb.getColumnModel().getColumn(4).setPreferredWidth(70);
		tb.getColumnModel().getColumn(5).setPreferredWidth(70);
		tb.getColumnModel().getColumn(6).setPreferredWidth(70);
		tb.getColumnModel().getColumn(7).setPreferredWidth(70);
		tb.getColumnModel().getColumn(8).setPreferredWidth(70);
		tb.getColumnModel().getColumn(tb.getColumnCount() - 1).setPreferredWidth(100);
	}

	public void actionPerformed(java.awt.event.ActionEvent paramActionEvent) {
		if (paramActionEvent.getSource() == b1) {
			getRecord();
			removeColumn();
		}
		if (paramActionEvent.getSource() == tf1) {
			CompanyTable localCompanyTable = new CompanyTable();
			localCompanyTable.showFrame();
		}
		if (paramActionEvent.getSource() == cb) {
		}
	}

	public void keyPressed(KeyEvent paramKeyEvent) {
	}

	public void keyReleased(KeyEvent paramKeyEvent) {
		if (paramKeyEvent.getSource() == tf1) {
			int i = paramKeyEvent.getKeyCode();
			if (i == 112) {
				CompanyTable localCompanyTable = new CompanyTable();
				localCompanyTable.showFrame();
				localCompanyTable.getCompanyId(tf1);
				localCompanyTable.getBranchCode(tf2);
				localCompanyTable.getSalaryMode(tf3);
			}
		}
	}

	public void keyTyped(KeyEvent paramKeyEvent) {
	}

	public void mousePressed(MouseEvent paramMouseEvent) {
		if (paramMouseEvent.getSource() == tf4) {
			if (javax.swing.SwingUtilities.isRightMouseButton(paramMouseEvent)) {
				skl.showCalendar();
				skl.getDate(tf4);
			}
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

}
