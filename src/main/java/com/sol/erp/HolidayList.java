package com.sol.erp;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.sol.erp.ui.custom.SolCalendar;
import com.sol.erp.util.DBConnectionUtil;
import com.sol.erp.util.DateCalculationUtil;
import com.sol.erp.util.formater.SolDateFormatter;

public class HolidayList implements java.awt.event.ActionListener, java.awt.event.MouseListener {
	java.sql.Connection con;
	PreparedStatement stat;
	ResultSet rs;
	int affected = 0;

	
	SolCalendar cal1 = new SolCalendar();
	CompanyTable ct = new CompanyTable();

	JFrame f = new JFrame("Holiday List");

	String[] heads = { "Date", "Occasion", "Holiday Type", "OT Type", "OT Value" };
	Object[][] data = new Object[0][];

	DefaultTableModel model = new DefaultTableModel(data, heads);
	JTable tb = new JTable(model);
	javax.swing.JScrollPane sp = new javax.swing.JScrollPane(tb);

	JLabel l1 = new JLabel("Date");
	JLabel l2 = new JLabel("Holiday Type");
	JLabel l3 = new JLabel("OT Type");
	JLabel l4 = new JLabel("OT Value");
	JLabel l5 = new JLabel("Occasion");

	JTextField tf1 = new JTextField("01/01/2008", 7);
	JComboBox cb1 = new JComboBox();
	JComboBox cb2 = new JComboBox();
	JComboBox cb3 = new JComboBox();
	JTextField tf3 = new JTextField(15);

	JButton b1 = new JButton("Show");
	JButton b2 = new JButton("Add");
	JButton b3 = new JButton("Remove");

	JPanel northpanel = new JPanel();
	JPanel centerpanel = new JPanel();
	JPanel southpanel = new JPanel();

	JPanel centerp1 = new JPanel();
	JPanel centerp2 = new JPanel();

	JPanel mainpanel = new JPanel();

	javax.swing.border.Border bor1 = javax.swing.BorderFactory.createTitledBorder("Occasion");

	java.awt.Font fo = new java.awt.Font("Verdana", 0, 10);

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

			setHorizontalAlignment(0);

			if (paramInt1 % 2 == 0) {
				setBackground(new Color(250, 250, 250));
				setForeground(Color.darkGray);
			}
			if (paramInt1 % 2 == 1) {
				setBackground(new Color(240, 240, 240));
				setForeground(Color.darkGray);
			}

			String str = String.valueOf(model.getValueAt(paramInt1, 2));

			if (str.equalsIgnoreCase("NH")) {
				setBackground(new Color(240, 250, 240));
				setForeground(Color.darkGray);
			}
			if (str.equalsIgnoreCase("RH")) {
				setBackground(new Color(250, 240, 240));
				setForeground(Color.darkGray);
			}
			if (str.equalsIgnoreCase("OH")) {
				setBackground(new Color(240, 220, 250));
				setForeground(Color.darkGray);
			}
			if ((paramInt2 == 0) && (paramInt1 > 0)) {
				setBackground(new Color(220, 220, 220));
				setForeground(Color.darkGray);
			}
			if ((paramInt2 == 1) && (paramInt1 > 0)) {
				setHorizontalAlignment(2);
			}

			tb.setRowHeight(30);
			tb.setRowHeight(0, 60);
			tb.setRowHeight(tb.getRowCount() - 1, 60);

			super.getTableCellRendererComponent(paramJTable, paramObject, paramBoolean1, paramBoolean2, paramInt1,
					paramInt2);

			return this;
		}
	}

	Renderer skr = new Renderer();

	public JPanel DesignFrame() {
		java.awt.Container localContainer = f.getContentPane();
		localContainer.setLayout(new java.awt.BorderLayout());
		mainpanel.setLayout(new java.awt.BorderLayout());
		centerpanel.setLayout(new java.awt.BorderLayout());
		centerp1.setLayout(new java.awt.GridLayout(7, 4, 2, 2));
		centerp2.setLayout(new java.awt.BorderLayout());

		cb1.addItem("NH");
		cb1.addItem("RH");
		cb1.addItem("OH");

		cb2.addItem("F");
		cb2.addItem("P");

		cb3.addItem("1");
		cb3.addItem("0");

		centerp1.add(l1);
		centerp1.add(tf1);
		centerp1.add(new JPanel());
		centerp1.add(new JPanel());
		centerp1.add(l2);
		centerp1.add(cb1);
		centerp1.add(new JPanel());
		centerp1.add(new JPanel());
		centerp1.add(l3);
		centerp1.add(cb2);
		centerp1.add(new JPanel());
		centerp1.add(new JPanel());
		centerp1.add(l4);
		centerp1.add(cb3);
		centerp1.add(new JPanel());
		centerp1.add(new JPanel());
		centerp1.add(l5);
		centerp1.add(tf3);

		centerp1.add(new JPanel());
		centerp1.add(new JPanel());
		centerp1.add(b1);
		centerp1.add(b2);
		centerp1.add(new JPanel());
		centerp1.add(new JPanel());
		centerp1.add(b3);

		centerpanel.setPreferredSize(new java.awt.Dimension(50, 100));

		centerpanel.add(centerp1, "North");
		centerpanel.add(sp, "Center");

		tb.getTableHeader().setPreferredSize(new java.awt.Dimension(50, 30));
		tb.getTableHeader().setFont(new java.awt.Font("Tahoma", 1, 11));
		tb.setAutoResizeMode(0);
		tb.setBackground(Color.white);
		tb.setFont(new java.awt.Font("Tahoma", 1, 11));
		tb.setSelectionBackground(new Color(170, 200, 170));
		tb.setSelectionForeground(Color.white);
		tb.setIntercellSpacing(new java.awt.Dimension(1, 1));
		tb.setShowGrid(false);

		for (int i = 0; i < tb.getColumnCount(); i++) {
			tb.getColumnModel().getColumn(i).setCellRenderer(skr);
		}

		tb.getColumnModel().getColumn(0).setPreferredWidth(100);
		tb.getColumnModel().getColumn(1).setPreferredWidth(550);
		tb.getColumnModel().getColumn(2).setPreferredWidth(110);
		tb.getColumnModel().getColumn(3).setPreferredWidth(70);
		tb.getColumnModel().getColumn(4).setPreferredWidth(70);

		mainpanel.add(ct.showContextFrame(), "North");
		mainpanel.add(centerpanel, "Center");
		mainpanel.add(southpanel, "South");

		localContainer.add(mainpanel, "Center");

		tf1.addMouseListener(this);

		b1.addActionListener(this);
		b2.addActionListener(this);
		b3.addActionListener(this);

		showRecord();

		return mainpanel;
	}

	public void showFrame() {
		DesignFrame();
		f.setSize(800, 600);
		f.setLocationRelativeTo(null);
		f.setVisible(true);
	}

	public void saveRecord() {
		String str1 = tf1.getText();
		String str2 = tf3.getText();
		String str3 = (String) cb1.getSelectedItem();
		String str4 = (String) cb2.getSelectedItem();
		String str5 = (String) cb3.getSelectedItem();

		String str6 = ct.getMyCompanyId();
		String str7 = ct.getMyBranchCode();
		java.sql.Date localDate = SolDateFormatter.DDMMYYtoSQL(tf1.getText());

		if ((tf3.getText().length() < 1) || (str5.length() < 1) || (tf1.getText().length() < 1)) {
			javax.swing.JOptionPane.showMessageDialog(f,
					"Date, Occasion or OTvalue may be Empty. or\n This Occasion may allready Exists.");
		} else {
			try {
				con = DBConnectionUtil.getConnection();
				stat = con.prepareStatement("insert into HR_HOLIDAY_LIST values('" + str6 + "','" + str7
						+ "',?,'" + str3 + "','" + str4 + "','" + str5 + "','" + str2 + "' )");
				stat.setDate(1, localDate);
				affected = stat.executeUpdate();

				if (affected > 0) {
					javax.swing.JOptionPane.showMessageDialog(f, "Record Saved.");
					model.addRow(new Object[] { str1, " " + str2, str3, str4, str5 });
				}
			} catch (Exception localException) {
				javax.swing.JOptionPane.showMessageDialog(f, localException.getMessage().toString());
			}
		}

		tf3.setText("");
	}

	public void showRecord() {
		model.setNumRows(0);

		model.addRow(new Object[] { " ", "<HTML><CENTER><Font Size=14>List of Holidays</FONT>" });

		String str1 = ct.getMyCompanyId();
		String str2 = ct.getMyBranchCode();

		int i = DateCalculationUtil.getYear(tf1.getText());

		@SuppressWarnings("deprecation")
		java.sql.Date localDate1 = new java.sql.Date(i - 1900, 0, 1);
		@SuppressWarnings("deprecation")
		java.sql.Date localDate2 = new java.sql.Date(i - 1900, 11, 31);

		try {
			con = DBConnectionUtil.getConnection();
			stat = con.prepareStatement(
					"select date, occasion, holiday_type, ot_type, ot_value from HR_HOLIDAY_LIST where Company_id ='"
							+ str1 + "' and Branch_code='" + str2 + "' and DATE Between ? and ? ");
			stat.setDate(1, localDate1);
			stat.setDate(2, localDate2);
			rs = stat.executeQuery();

			while (rs.next()) {
				java.sql.Date localDate3 = rs.getDate(1);
				String str3 = SolDateFormatter.SQLtoDDMMYY(localDate3);
				String str4 = new String(rs.getString(2));
				String str5 = new String(rs.getString(3));
				String str6 = new String(rs.getString(4));
				String str7 = new String(rs.getString(5));

				model.addRow(new Object[] { str3, " " + str4, str5, str6, str7 });
			}
			model.addRow(new Object[] { "Total: " + String.valueOf(tb.getRowCount() + 1 - 2),
					"<HTML><font size=2 face='Verdana' align='Left'> NH - NATIONAL HOLIDAYS <BR> RH - RELEGIOUS HOLIDAYS<BR> OH - OTHER HOLIDAYS" });

		} catch (Exception localException) {
			javax.swing.JOptionPane.showMessageDialog(f, localException.getMessage().toString());
		}
	}

	public void removeRecord() {
		String str1 = ct.getMyCompanyId();
		String str2 = ct.getMyBranchCode();

		int i = tb.getSelectedRow();

		String str3 = String.valueOf(model.getValueAt(i, 1));

		try {
			con = DBConnectionUtil.getConnection();
			stat = con.prepareStatement("Delete from HR_HOLIDAY_LIST where Company_id ='" + str1
					+ "' and Branch_code='" + str2 + "' and occasion='" + str3 + "' ");
			affected = stat.executeUpdate();

			if (affected > 0) {
				model.removeRow(tb.getSelectedRow());
			}
		} catch (Exception localException) {
			javax.swing.JOptionPane.showMessageDialog(f, localException.getMessage().toString());
		}
	}

	public void actionPerformed(java.awt.event.ActionEvent paramActionEvent) {
		if (paramActionEvent.getSource() == b1) {
			showRecord();
		}
		if (paramActionEvent.getSource() == b2) {
			saveRecord();
		}
		if (paramActionEvent.getSource() == b3) {
			removeRecord();
		}
	}

	public void mousePressed(MouseEvent paramMouseEvent) {
	}

	public void mouseReleased(MouseEvent paramMouseEvent) {
		if (paramMouseEvent.getSource() == tf1) {
			if (javax.swing.SwingUtilities.isRightMouseButton(paramMouseEvent)) {
				cal1.showCalendar();
				cal1.getDate(tf1);
			}
		}
	}

	public void mouseClicked(MouseEvent paramMouseEvent) {
	}

	public void mouseEntered(MouseEvent paramMouseEvent) {
	}

	public void mouseExited(MouseEvent paramMouseEvent) {
	}

}
