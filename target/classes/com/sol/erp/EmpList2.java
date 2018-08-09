package com.sol.erp;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import com.sol.erp.ui.custom.SolTableModel;
import com.sol.erp.util.DBConnectionUtil;
import com.sol.erp.util.DateDifferencesUtil;
import com.sol.erp.util.formater.SolDateFormatter;

public class EmpList2 implements ActionListener, MouseListener, KeyListener {

	
	Connection con;
	Statement stat;
	ResultSet rs;
	int affected;
	public static String codestr;
	String[] columnNames;
	Object[][] data;
	JFrame f;
	JButton b4;
	JButton b5;
	DefaultTableModel model;
	JTable tb;
	JScrollPane sp;
	DefaultTableCellRenderer renderer;
	TableColumnModel tcm;
	TableColumn col;
	TableColumn col1;
	TableColumn col2;
	TableColumn col3;
	TableColumn col4;
	JPanel mainPanel;
	JPanel p1;
	JPanel p2;
	JPanel buttonpanel;
	JPanel statuspanel;
	JLabel searchlb;
	JTextField searchtf;
	JLabel statuslb;
	JLabel datelabel;
	SimpleDateFormat sdf;
	java.util.Date dat;
	String dateString;
	JRadioButton rb1;
	JRadioButton rb2;
	JRadioButton rb3;
	ButtonGroup grp;
	Border bor1;
	Border lowerbor;
	Font fo;
	Toolkit tk;
	Dimension ss;
	Dimension fs;
	EmpList2.ColoredTableCellRenderer skr;

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

			if (paramInt1 % 2 == 0) {
				setBackground(new Color(250, 250, 250));
				setForeground(Color.darkGray);

			} else {
				setBackground(new Color(240, 240, 240));
				setForeground(Color.darkGray);
			}

			if (paramInt2 < 2) {
				setHorizontalAlignment(0);
			}

			if ((paramInt2 == 2) || (paramInt2 == 3)) {
				setHorizontalAlignment(2);
			}

			if (paramInt2 > 3) {
				setHorizontalAlignment(4);
			}

			if (paramInt2 == 11) {
				int i = Integer.parseInt(String.valueOf(model.getValueAt(paramInt1, 11)));

				if ((i >= 0) && (i <= 8)) {
					setBackground(Color.red);
					setForeground(Color.white);
				}
				if (i < 0) {
					setBackground(Color.black);
					setForeground(Color.white);
				}
			}

			super.getTableCellRendererComponent(paramJTable, paramObject, paramBoolean1, paramBoolean2, paramInt1,
					paramInt2);

			return this;
		}
	}

	public JPanel DesignFrame() {
		Container localContainer = f.getContentPane();
		localContainer.setLayout(new BorderLayout());
		mainPanel.setLayout(new BorderLayout());
		p1.setLayout(new GridLayout(4, 2, 2, 2));
		p2.setLayout(new BorderLayout());
		buttonpanel.setLayout(new FlowLayout(0));
		statuspanel.setLayout(new FlowLayout(0));

		statuspanel.add(datelabel);
		datelabel.setForeground(Color.blue);
		statuspanel.add(statuslb);
		statuspanel.setBorder(lowerbor);

		SolTableModel.decorateTable(tb);
		tb.setAutoResizeMode(0);
		tb.setBackground(Color.white);
		tb.setShowGrid(false);
		tb.setFont(new Font("Verdana", 0, 10));
		tb.setIntercellSpacing(new Dimension(1, 1));
		tb.setRowHeight(22, 22);

		col.setPreferredWidth(70);
		col1.setPreferredWidth(50);
		col2.setPreferredWidth(160);
		col3.setPreferredWidth(130);
		tb.getColumnModel().getColumn(8).setPreferredWidth(25);

		for (int i = 0; i < tb.getColumnCount() - 5; i++) {
			tb.getColumnModel().getColumn(i).setCellRenderer(skr);
		}

		tb.getColumnModel().getColumn(9).setCellRenderer(skr);
		tb.getColumnModel().getColumn(10).setCellRenderer(skr);
		tb.getColumnModel().getColumn(11).setCellRenderer(skr);

		tb.getColumnModel().getColumn(10).setPreferredWidth(40);
		tb.getColumnModel().getColumn(11).setPreferredWidth(40);
		tb.getColumnModel().getColumn(12).setPreferredWidth(25);

		b4.setMnemonic(87);
		b5.setMnemonic(83);

		grp.add(rb1);
		grp.add(rb2);
		grp.add(rb3);

		buttonpanel.add(rb1);
		buttonpanel.add(rb2);
		buttonpanel.add(rb3);
		buttonpanel.setBorder(bor1);

		buttonpanel.add(searchtf);
		buttonpanel.add(b5);

		p2.add(buttonpanel, "North");
		p2.add(sp, "Center");

		mainPanel.add(p2, "Center");
		mainPanel.add(statuspanel, "South");

		localContainer.add(mainPanel, "Center");

		tb.addMouseListener(this);
		searchtf.addKeyListener(this);

		rb1.addActionListener(this);
		rb2.addActionListener(this);
		rb3.addActionListener(this);

		f.setTitle("Employee List");
		return mainPanel;
	}

	public void showEmpList() {
		DesignFrame();
		con = DBConnectionUtil.getConnection();
		f.setLocation((ss.width - fs.width) / 10, (ss.height - fs.height) / 8);
		f.setSize(810, 530);
		f.setVisible(true);
	}

	public void showRecord() {
		model.setNumRows(0);
		String str1 = searchtf.getText() + "%";

		String str2 = "0";
		String str3 = "true";
		if (rb1.isSelected()) {
			str2 = "0";
			str3 = "true";
		}
		if (rb2.isSelected()) {
			str2 = "1";
			str3 = "false";
		}
		if (rb3.isSelected()) {
			str2 = "2";
			str3 = "false";
		}

		try {
			stat = con.createStatement();
			rs = stat.executeQuery(
					"select punchcard_no, emp_code, full_name, desig, doj2, basic_salary,prob,OT from HREMP_JOIN where full_name like '"
							+ str1 + "' and status = '" + str2 + "' order by punchcard_no");

			while (rs.next()) {
				String str4 = new String(rs.getString(1));
				String str5 = new String(rs.getString(2));
				String str6 = new String(rs.getString(3));
				String str7 = new String(rs.getString(4));
				java.sql.Date localDate = rs.getDate(5);
				String str8 = SolDateFormatter.SQLtoDDMMYY(localDate);
				String str9 = new String(rs.getString(6));
				String str10 = new String(rs.getString(7));
				String str11 = new String(rs.getString(8));

				model.addRow(
						new Object[] { str4.toUpperCase(), str5.toUpperCase(), str6.toUpperCase(), str7.toUpperCase(),
								str8, str9, "0", "0", new Boolean(str11), str10, "0", "0", new Boolean(str3) });
			}

			statuslb.setText("Total Number of Employee is : " + String.valueOf(tb.getRowCount()));
		} catch (Exception localException) {
			System.out.println(localException);
		}
	}

	public void showRecord2(int paramInt) {
		String str1 = String.valueOf(model.getValueAt(paramInt, 1));

		try {
			rs = stat.executeQuery("select releave_date from HR_EX_EMP where emp_code='" + str1 + "' ");

			while (rs.next()) {
				java.sql.Date localDate = rs.getDate(1);
				String str2 = SolDateFormatter.SQLtoDDMMYY(localDate);

				model.setValueAt(str2, paramInt, 6);
			}
		} catch (Exception localException) {
			System.out.println(localException);
		}
	}

	public void showReport() {
		for (int i = 0; i < tb.getRowCount(); i++) {
			showRecord2(i);
		}
	}

	public void probDays() {
		for (int i = 0; i < tb.getRowCount(); i++) {
			String str1 = String.valueOf(model.getValueAt(i, 4));
			String str2 = String.valueOf(model.getValueAt(i, 9));

			int j = DateDifferencesUtil.getDayCount(str1, str2);
			model.setValueAt(String.valueOf(j), i, 10);

			String str3 = String.valueOf(datelabel.getText());
			int k = DateDifferencesUtil.getDayCount(str3, str2);
			model.setValueAt(String.valueOf(k), i, 11);
		}
	}

	public void updateOTStatus() {
		int i = tb.getSelectedRow();
		String str1 = String.valueOf(model.getValueAt(i, 1));
		String str2 = String.valueOf(model.getValueAt(i, 8));
		try {
			Statement localStatement = con.createStatement();
			affected = localStatement
					.executeUpdate("update HREMP_JOIN set OT='" + str2 + "' where EMP_CODE='" + str1 + "' ");

			if (affected > 0) {
				System.out.println("1st Record Update.");

				affected = localStatement.executeUpdate(
						"update HR_OFF_DAYS set PERMISSION='" + str2 + "' where EMP_CODE='" + str1 + "' ");
				if (affected > 0) {
					System.out.println("2nd Record Update.");
				}

			}

		} catch (Exception localException) {
			System.out.println("HREMP_JOIN : " + localException);
		}
	}

	public void updateConfirm() {
		int i = tb.getSelectedRow();
		String str1 = String.valueOf(model.getValueAt(i, 1));
		String str2 = String.valueOf(model.getValueAt(i, 12));
		String str3 = null;

		if ((rb3.isSelected()) && (str2.equalsIgnoreCase("true"))) {
			str3 = "update HREMP_JOIN set STATUS='0' where EMP_CODE='" + str1 + "' ";
		}
		if ((rb1.isSelected()) && (str2.equalsIgnoreCase("false"))) {
			str3 = "update HREMP_JOIN set STATUS='2' where EMP_CODE='" + str1 + "' ";
		}
		if ((rb2.isSelected()) && (str2.equalsIgnoreCase("false"))) {
			str3 = "update HREMP_JOIN set STATUS='0' where EMP_CODE='" + str1 + "' ";
		}

		try {
			Statement localStatement = con.createStatement();
			affected = localStatement.executeUpdate(str3);

			if (affected > 0) {
				JOptionPane.showMessageDialog(f, "Record Updated Successfully.");
			}

		} catch (Exception localException) {
			System.out.println("HREMP_JOIN : " + localException);
		}
	}

	static JTextField sktf = new JTextField();
	int row;

	public EmpList2() {

		affected = 0;

		columnNames = new String[] { "PunchCard", "Code", "Name", "Designation", "DOJ", "Join Salary", "DOR",
				"Last Salary", "OT", "DOP", "Total", "RD", " " };
		data = new Object[0][];

		f = new JFrame();

		b4 = new JButton("Show All");
		b5 = new JButton("Search");

		model = new DefaultTableModel(data, columnNames);
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
				return (paramAnonymousInt2 <= 0) || (paramAnonymousInt2 >= 8);
			}
		};
		sp = new JScrollPane(tb);

		renderer = new DefaultTableCellRenderer();

		tcm = tb.getColumnModel();
		col = tcm.getColumn(0);
		col1 = tcm.getColumn(1);
		col2 = tcm.getColumn(2);
		col3 = tcm.getColumn(3);
		col4 = tcm.getColumn(4);

		mainPanel = new JPanel();
		p1 = new JPanel();
		p2 = new JPanel();
		buttonpanel = new JPanel();
		statuspanel = new JPanel();

		searchlb = new JLabel("EMP Name");
		searchtf = new JTextField(15);

		statuslb = new JLabel("Status :");
		datelabel = new JLabel("Today");

		sdf = new SimpleDateFormat("dd/MM/yyyy");
		dat = new java.util.Date();

		try {
			dateString = sdf.format(dat);
			datelabel.setText(dateString);
		} catch (IllegalArgumentException localIllegalArgumentException) {
			System.out.println(localIllegalArgumentException.getMessage());
		}

		rb1 = new JRadioButton("Current Employee", true);
		rb2 = new JRadioButton("EX. Employee", false);
		rb3 = new JRadioButton("Trainee", false);

		grp = new ButtonGroup();

		bor1 = BorderFactory.createTitledBorder("Search Option");
		lowerbor = BorderFactory.createBevelBorder(1);

		fo = new Font("Arial", 1, 11);

		tk = Toolkit.getDefaultToolkit();
		ss = tk.getScreenSize();
		fs = f.getSize();

		skr = new EmpList2.ColoredTableCellRenderer();

		row = 0;
	}

	public void getCode(JTextField paramJTextField) {
		row = tb.getSelectedRow();
		sktf = paramJTextField;
		if ((row != -1) ||

		(row >= 0)) {
			codestr = String.valueOf(model.getValueAt(row, 0));
			sktf.setText(codestr);
			paramJTextField = sktf;
		}
	}

	public void actionPerformed(ActionEvent paramActionEvent) {
		if (paramActionEvent.getSource() == b4) {
			showRecord();
		}
		if (paramActionEvent.getSource() == rb1) {
			showRecord();
			showReport();
		}
		if (paramActionEvent.getSource() == rb2) {
			showRecord();
			showReport();
		}

		if (paramActionEvent.getSource() == rb3) {
			showRecord();
			showReport();
		}
	}

	public void keyPressed(KeyEvent paramKeyEvent) {
		int i = paramKeyEvent.getKeyCode();

		if (i == 27) {
			f.setVisible(false);
		}
	}

	public void keyReleased(KeyEvent paramKeyEvent) {
		if (paramKeyEvent.getSource() == searchtf) {
			showRecord();
		}
	}

	public void mouseReleased(MouseEvent paramMouseEvent) {
		if (paramMouseEvent.getSource() == tb) {
			getCode(sktf);
		}
	}

	public void mouseClicked(MouseEvent paramMouseEvent) {
		int i = tb.getSelectedColumn();

		if (i == 8) {
			updateOTStatus();
		}
		if (i == 12) {
			updateConfirm();
		}
	}

	public void keyTyped(KeyEvent paramKeyEvent) {
	}

	public void mousePressed(MouseEvent paramMouseEvent) {
	}

	public void mouseExited(MouseEvent paramMouseEvent) {
	}

	public void mouseEntered(MouseEvent paramMouseEvent) {
	}
}
