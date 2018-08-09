package com.sol.erp;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import com.sol.erp.constants.ApplicationConstants;
import com.sol.erp.ui.custom.SolTableModel;
import com.sol.erp.util.DBConnectionUtil;
import com.sol.erp.util.DateCalculationUtil;
import com.sol.erp.util.formater.SolDateFormatter;

public class TDS implements ActionListener, MouseListener, KeyListener {
	
	Connection con;
	Statement stat;
	ResultSet rs;
	int affected;
	String[] columnNames;
	Object[][] data;
	DecimalFormat df;
	DecimalFormat df3;
	DecimalFormat df2;
	DecimalFormat df4;
	JFrame f;
	DefaultTableModel model;
	JTable tb;
	JScrollPane sp;
	JLabel imagelabel;
	JLabel l1;
	JTextField tf1;
	JButton clearbut;
	JButton updatebut;
	JButton closebut;
	JPanel westpanel;
	JPanel northpanel;
	JPanel centerpanel;
	JPanel butpanel;
	Font fo;
	Border line;
	Border bor1;
	Toolkit tk;
	Dimension ss;
	Dimension fs;
	java.util.Date dat;
	SimpleDateFormat formatter;
	String dateString;
	String time;
	TDS.ColoredTableCellRenderer skr;

	Container c;

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
				setBackground(new Color(240, 240, 240));
				setForeground(Color.darkGray);

			} else {
				setBackground(new Color(250, 250, 250));
				setForeground(Color.darkGray);
			}

			if (paramInt2 == 1) {
				setHorizontalAlignment(4);
			}
			if (paramInt2 == 2) {
				setHorizontalAlignment(4);
			}

			String str = String.valueOf(model.getValueAt(paramInt1, 7));

			if (str.equalsIgnoreCase("1")) {
				setBackground(new Color(210, 240, 240));
				setForeground(Color.darkGray);
			}
			if (str.equalsIgnoreCase("2")) {
				setBackground(new Color(250, 220, 190));
				setForeground(new Color(60, 60, 140));
			}

			if (str.equalsIgnoreCase("0")) {

				if (paramInt1 % 2 == 0) {
					setBackground(new Color(240, 240, 240));
					setForeground(Color.darkGray);

				} else {
					setBackground(new Color(250, 250, 250));
					setForeground(Color.darkGray);
				}
			}

			if (paramInt2 == 0) {
				setHorizontalAlignment(0);
				setBackground(Color.black);
				setForeground(Color.white);
			}

			super.getTableCellRendererComponent(paramJTable, paramObject, paramBoolean1, paramBoolean2, paramInt1,
					paramInt2);

			int i = tb.getSelectedRow();
			int j = tb.getSelectedColumn();

			if (paramInt1 == i) {
				setFont(new Font("Currier", 1, 14));
				if (paramInt2 == j) {
					setBackground(Color.blue);
					setForeground(Color.white);
				}
			}

			return this;
		}
	}

	public TDS() {
		affected = 0;

		columnNames = new String[] { "Code", "Salary", "TDS", "Date", "Advance Taken", "ADVANCE Deduct", " ", "UPDATED",
				"Name" };
		data = new Object[0][];

		df = new DecimalFormat("00.00");
		df3 = new DecimalFormat("0");
		df2 = new DecimalFormat("00");
		df4 = new DecimalFormat("000");

		f = new JFrame("EMP TDS");

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
				convertColumnIndexToModel(paramAnonymousInt2);
				return paramAnonymousInt2 != 0;
			}
		};
		sp = new JScrollPane(tb);

		imagelabel = new JLabel(new ImageIcon(ClassLoader.getSystemResource(ApplicationConstants.IMAGE_PATH + "currency2.jpg")));

		l1 = new JLabel("Emp Code");
		tf1 = new JTextField(16);

		clearbut = new JButton("Clear");
		updatebut = new JButton("Save");
		closebut = new JButton("Close");

		westpanel = new JPanel();
		northpanel = new JPanel();
		centerpanel = new JPanel();
		butpanel = new JPanel();

		fo = new Font("Verdana", 0, 10);
		line = BorderFactory.createLineBorder(Color.darkGray);
		bor1 = BorderFactory.createTitledBorder(line, "Search By", 0, 0, fo, Color.darkGray);

		tk = Toolkit.getDefaultToolkit();
		ss = tk.getScreenSize();
		fs = f.getSize();

		dat = new java.util.Date();
		formatter = new SimpleDateFormat("yyyy");

		try {
			dateString = formatter.format(dat);
			System.out.println(dateString);
		} catch (IllegalArgumentException localIllegalArgumentException) {
			System.out.println(localIllegalArgumentException.getMessage());
		}

		skr = new TDS.ColoredTableCellRenderer();

	}

	public void px() {
		con = DBConnectionUtil.getConnection();

		c = f.getContentPane();
		c.setLayout(new BorderLayout());
		northpanel.setLayout(new FlowLayout(0));
		centerpanel.setLayout(new BorderLayout());
		westpanel.setLayout(new BorderLayout());
		butpanel.setLayout(new FlowLayout(2));

		SolTableModel.decorateTable(tb);
		for (int i = 0; i < tb.getColumnCount() - 3; i++) {
			tb.getColumnModel().getColumn(i).setCellRenderer(skr);
		}

		tb.getColumnModel().getColumn(0).setPreferredWidth(50);
		tb.getColumnModel().getColumn(1).setPreferredWidth(60);
		tb.getColumnModel().getColumn(2).setPreferredWidth(60);
		tb.getColumnModel().getColumn(3).setPreferredWidth(70);
		tb.getColumnModel().getColumn(4).setPreferredWidth(90);
		tb.getColumnModel().getColumn(5).setPreferredWidth(90);
		tb.getColumnModel().getColumn(6).setPreferredWidth(10);
		tb.getColumnModel().getColumn(8).setPreferredWidth(120);

		tb.setBackground(Color.white);
		tb.setIntercellSpacing(new Dimension(1, 1));
		tb.setAutoscrolls(true);

		tb.setFont(fo);

		northpanel.add(l1);
		northpanel.add(tf1);
		tf1.setFont(fo);

		butpanel.add(clearbut);
		clearbut.setMnemonic(82);
		butpanel.add(updatebut);
		updatebut.setMnemonic(83);
		butpanel.add(closebut);
		closebut.setMnemonic(67);

		sp.setBorder(bor1);

		westpanel.add(imagelabel);
		westpanel.setPreferredSize(new Dimension(150, 100));
		westpanel.setBackground(Color.white);

		c.add(northpanel, "North");
		c.add(sp, "Center");
		c.add(butpanel, "South");

		tb.addMouseListener(this);
		tb.addKeyListener(this);

		tf1.addActionListener(this);

		clearbut.addActionListener(this);
		updatebut.addActionListener(this);
		closebut.addActionListener(this);

		f.setSize(700, 408);

		f.setLocationRelativeTo(null);
		f.setVisible(true);

		tb.removeColumn(tb.getColumnModel().getColumn(7));

		EmpList();
		tdsList();
	}

	public void EmpList() {
		model.setNumRows(0);
		String str1 = tf1.getText() + "%";
		try {
			con = DBConnectionUtil.getConnection();
			stat = con.createStatement();
			rs = stat.executeQuery("select EMP_CODE,BASIC_SALARY,FULL_NAME from HREMP_JOIN WHERE EMP_CODE LIKE '" + str1
					+ "' or punchcard_no like'" + str1 + "' or full_name  like '" + str1 + "' ");
			rs.getMetaData().getColumnCount();
			rs.getRow();
			while (rs.next()) {
				String str2 = new String(rs.getString(1));
				String str3 = new String(rs.getString(2));
				String str4 = new String(rs.getString(3));

				model.addRow(
						new Object[] { str2, str3, "0", "0", "0", "0", new Boolean(false), "0", str4.toUpperCase() });
			}
		} catch (Exception localException) {
		}
	}

	public void tdsList() {
		tb.getSelectedRow();

		for (int j = 0; j < tb.getRowCount(); j++) {
			String str1 = String.valueOf(model.getValueAt(j, 0));
			try {
				stat = con.createStatement();
				rs = stat.executeQuery(
						"select TDS, TAKEN_DATE, ADVANCE_TAKEN, ADVANCE_DEDUCT, PAID_STATUS from HR_TDS WHERE EMP_CODE ='"
								+ str1 + "' ");

				rs.getRow();
				while (rs.next()) {

					String str2 = String.valueOf(df3.format(rs.getFloat(1)));

					java.sql.Date localDate = rs.getDate(2);

					String str3 = SolDateFormatter.SQLtoDDMMYY(localDate);

					String str4 = String.valueOf(df3.format(rs.getFloat(3)));
					String str5 = String.valueOf(df3.format(rs.getFloat(4)));
					String str6 = rs.getString(5);

					model.setValueAt(str2, j, 2);
					model.setValueAt(str3, j, 3);
					model.setValueAt(str4, j, 4);
					model.setValueAt(str5, j, 5);
					model.setValueAt(new Boolean(str6), j, 6);
				}
			} catch (Exception localException) {
				System.out.println(localException);
			}
		}
	}

	public void saveRecord() {
		int i = tb.getSelectedRow();
		String str1 = String.valueOf(model.getValueAt(i, 0));
		String str2 = String.valueOf(model.getValueAt(i, 1));
		String str3 = String.valueOf(model.getValueAt(i, 2));

		try {
			con = DBConnectionUtil.getConnection();
			stat = con.createStatement();
			affected = stat
					.executeUpdate("Insert into HR_TDS values('" + str1 + "', '" + str2 + "','" + str3 + "','1')");

			if (affected > 0) {
				JOptionPane.showMessageDialog(f, "<HTML><Font color='blue'> [ " + str1 + " ] Updated Successcully.");
				model.setValueAt("1", i, 4);
			}
		} catch (Exception localException) {
			JOptionPane.showMessageDialog(f, localException.getMessage().toString());
		}
	}

	public void updateRecord(int paramInt) {
		String str1 = String.valueOf(model.getValueAt(paramInt, 0));

		float f1 = Float.parseFloat(String.valueOf(model.getValueAt(paramInt, 2)));
		String str2 = String.valueOf(model.getValueAt(paramInt, 3));
		float f2 = Float.parseFloat(String.valueOf(model.getValueAt(paramInt, 4)));
		float f3 = Float.parseFloat(String.valueOf(model.getValueAt(paramInt, 5)));

		String str3 = String.valueOf(model.getValueAt(paramInt, 6));

		java.sql.Date localDate = SolDateFormatter.DDMMYYtoSQL(str2);

		try {
			con = DBConnectionUtil.getConnection();
			PreparedStatement localPreparedStatement = con.prepareStatement(
					"update HR_TDS set TDS = ? , TAKEN_DATE=?, ADVANCE_TAKEN = ?, ADVANCE_DEDUCT=?, UPDATE_STATUS='1', PAID_STATUS='"
							+ str3 + "' where Emp_Code='" + str1 + "'  ");
			localPreparedStatement.setFloat(1, f1);
			localPreparedStatement.setDate(2, localDate);
			localPreparedStatement.setFloat(3, f2);
			localPreparedStatement.setFloat(4, f3);
			affected = localPreparedStatement.executeUpdate();

			if (affected > 0) {
				int i = DateCalculationUtil.getMonth(str2);
				int j = DateCalculationUtil.getYear(str2);
				try {
					localPreparedStatement = con.prepareStatement(
							"update HR_EMP_EXTRA set RATE = ?, Month=?, Year=? where Emp_Code= ? AND Heads = ? ");
					localPreparedStatement.setString(1, String.valueOf(f2));
					localPreparedStatement.setInt(2, i);
					localPreparedStatement.setInt(3, j);
					localPreparedStatement.setString(4, str1);
					localPreparedStatement.setString(5, "ADVANCE");
					affected = localPreparedStatement.executeUpdate();

					if (affected > 0) {
						try {
							localPreparedStatement = con
									.prepareStatement("INSERT into HR_EMP_ADVANCE Values (?,?,?,?,?,?,?,?)");
							localPreparedStatement.setString(1, str1);
							localPreparedStatement.setDate(2, localDate);
							localPreparedStatement.setFloat(3, f2);
							localPreparedStatement.setFloat(4, Float.parseFloat("0"));
							localPreparedStatement.setFloat(5, Float.parseFloat("0"));
							localPreparedStatement.setFloat(6, Float.parseFloat("0"));
							localPreparedStatement.setFloat(7, Float.parseFloat("0"));
							localPreparedStatement.setString(8, "0");
							affected = localPreparedStatement.executeUpdate();

							if (affected > 0) {
								JOptionPane.showMessageDialog(f,
										"<HTML> <Font color='blue'> [ " + str1 + " ] Updated Successcully.");
								model.setValueAt("1", paramInt, 7);
							}
						} catch (Exception localException2) {
							JOptionPane.showMessageDialog(f, localException2.getMessage().toString());
						}
					}
				} catch (Exception localException3) {
					JOptionPane.showMessageDialog(f, localException3.getMessage().toString());
				}
			}
		} catch (Exception localException1) {
			JOptionPane.showMessageDialog(f, localException1.getMessage().toString());
		}
	}

	public void clear() {
		int i = tb.getSelectedColumn();

		for (int j = 0; j < tb.getRowCount(); j++) {
			tb.setRowSelectionInterval(j, j);

			String str1 = String.valueOf(model.getValueAt(j, i));
			String str2 = tb.getColumnName(i);
			String str3 = String.valueOf(model.getValueAt(j, 0));
			String str4 = String.valueOf(model.getValueAt(j, 8));
			if (str1.length() > 1) {
				Object[] arrayOfObject = { "Yes, Please", "No, Thanks",
						"<HTML><body bgcolor='red'><Center>I want to Stop" };
				int k = JOptionPane.showOptionDialog(f,
						"<HTML>Would You like to Clear <Font color='red'>" + str2
								+ "<Font color='black'> for <Font color='blue'> [ " + str3 + " ] " + str4,
						"Confirm Remove", 1, 3, null, arrayOfObject, arrayOfObject[2]);

				if (k == 0) {
					model.setValueAt("1", j, 7);
					model.setValueAt("0", j, i);
					updateRecord(j);
				}
				if ((k == 1) &&

				(k == 2)) {
					break;
				}
			}
		}
	}

	public void actionPerformed(ActionEvent paramActionEvent) {
		if (paramActionEvent.getSource() == tf1) {
			EmpList();
			tdsList();
		}
		if (paramActionEvent.getSource() == clearbut) {

			clear();
		}
		if (paramActionEvent.getSource() == updatebut) {
			updateRecord(tb.getSelectedRow());
		}
		if (paramActionEvent.getSource() == closebut) {
			f.setVisible(false);
		}
	}

	public void mousePressed(MouseEvent paramMouseEvent) {
		if (paramMouseEvent.getSource() == tb) {
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
		int i = tb.getSelectedColumn();

		if (i == 2) {
			int j = paramKeyEvent.getKeyCode();
			if (j == 9) {
				saveRecord();
			}
		}
	}

	public void keyReleased(KeyEvent paramKeyEvent) {
	}

	public void keyTyped(KeyEvent paramKeyEvent) {
	}
}
