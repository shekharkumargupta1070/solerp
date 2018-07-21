package com.sol.erp;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.sol.erp.util.DBConnectionUtil;

public class ReceiveApprove implements java.awt.event.ActionListener, java.awt.event.MouseListener {
	
	java.sql.Connection con;
	java.sql.Statement stat;
	ResultSet rs;
	int affected = 0;

	String[] columnNames = { "Value" };
	Object[][] data = new Object[0][];

	String[] columnNames1 = { "Item Code", "DRG No", "Sent Date", "Receive Date", "Approval Status", "BFA Status",
			"Ready Fab" };
	Object[][] data1 = new Object[0][];

	java.text.DecimalFormat df = new java.text.DecimalFormat("00.00");
	java.text.DecimalFormat df1 = new java.text.DecimalFormat("00");

	int v = 20;
	int h = 32;

	DefaultTableModel model = new DefaultTableModel(data, columnNames);
	JTable tb = new JTable(model);
	javax.swing.JScrollPane sp = new javax.swing.JScrollPane(tb);

	DefaultTableModel model1 = new DefaultTableModel(data1, columnNames1);
	JTable tb1 = new JTable(model1);
	javax.swing.JScrollPane sp1 = new javax.swing.JScrollPane(tb1);

	javax.swing.JSplitPane spl1 = new javax.swing.JSplitPane(1, sp, sp1);

	JDialog f = new JDialog();
	javax.swing.JLabel topimagelabel = new javax.swing.JLabel(new javax.swing.ImageIcon("image/receive.gif"), 2);

	JButton correctbut = new JButton("Mark Correction");
	JButton viewbut = new JButton("View Approval Data");
	JButton closebut = new JButton("Close", new javax.swing.ImageIcon("image/close.gif"));

	JRadioButton rb1 = new JRadioButton("Unit_No", true);
	JRadioButton rb2 = new JRadioButton("Project_No", false);
	JRadioButton rb3 = new JRadioButton("Sent_Date ", false);

	javax.swing.ButtonGroup group = new javax.swing.ButtonGroup();

	java.util.GregorianCalendar gcal = new java.util.GregorianCalendar();
	int currentdt = gcal.get(5);
	int currentmn = gcal.get(2);
	int currentyr = gcal.get(1);

	String datestr = String.valueOf(df1.format(currentdt)) + "/"
			+ String.valueOf(df1.format(currentmn)) + "/" + String.valueOf(currentyr);

	JPanel northp1 = new JPanel();
	JPanel northp2 = new JPanel();
	JPanel northp3 = new JPanel();

	JPanel butpanel1 = new JPanel();
	JPanel butpanel2 = new JPanel();

	JPanel centerpanel = new JPanel();
	JPanel northpanel = new JPanel();

	java.awt.Font fo = new java.awt.Font("Tahoma", 1, 11);
	javax.swing.border.Border line = javax.swing.BorderFactory.createLineBorder(new Color(60, 130, 130));
	javax.swing.border.Border bor1 = javax.swing.BorderFactory.createTitledBorder(line, "Search By", 0, 0, fo,
			new Color(60, 130, 130));
	javax.swing.border.Border bor2 = javax.swing.BorderFactory.createTitledBorder(line, "Approval Data Status", 0,
			0, fo, new Color(60, 130, 130));

	java.awt.Toolkit tk = java.awt.Toolkit.getDefaultToolkit();
	Dimension ss = tk.getScreenSize();
	Dimension fs = f.getSize();
	java.awt.Container c;

	public void px() {
		c = f.getContentPane();
		c.setLayout(new java.awt.BorderLayout());
		northp1.setLayout(new java.awt.FlowLayout());
		northp2.setLayout(new java.awt.GridLayout(2, 2, 2, 2));
		northpanel.setLayout(new java.awt.BorderLayout());
		butpanel1.setLayout(new java.awt.FlowLayout());
		butpanel2.setLayout(new java.awt.FlowLayout());
		centerpanel.setLayout(new java.awt.BorderLayout());

		northpanel.setBorder(bor1);
		sp1.setBorder(bor2);

		tb1.setFont(fo);
		tb1.setGridColor(new Color(100, 150, 100));
		tb1.getTableHeader().setPreferredSize(new Dimension(50, 20));
		tb1.getTableHeader().setFont(fo);
		tb1.setRowHeight(20);
		tb1.setSelectionBackground(new Color(60, 130, 130));
		tb1.setSelectionForeground(Color.white);

		tb.setFont(fo);
		tb.getTableHeader().setPreferredSize(new Dimension(50, 20));
		tb.getTableHeader().setFont(fo);
		tb.setRowHeight(20);
		tb.setSelectionBackground(new Color(60, 130, 130));
		tb.setSelectionForeground(Color.white);
		tb.setGridColor(new Color(100, 150, 100));

		group.add(rb1);
		group.add(rb2);
		group.add(rb3);

		northp1.setBackground(Color.lightGray);

		northp1.add(rb2);
		northp1.add(rb3);

		butpanel2.add(correctbut);
		correctbut.setMnemonic(77);
		butpanel2.add(viewbut);
		viewbut.setMnemonic(86);
		butpanel2.add(closebut);
		closebut.setMnemonic(67);

		northpanel.add(topimagelabel, "North");
		northpanel.add(northp1, "Center");

		centerpanel.add(butpanel1, "North");
		centerpanel.add(spl1, "Center");
		centerpanel.add(butpanel2, "South");

		c.add(northpanel, "North");
		c.add(centerpanel, "Center");

		rb1.addActionListener(this);
		rb1.setBackground(new Color(60, 130, 130));
		rb2.addActionListener(this);
		rb3.addActionListener(this);

		correctbut.addActionListener(this);
		viewbut.addActionListener(this);
		closebut.addActionListener(this);
		tb.addMouseListener(this);

		spl1.setDividerLocation(100);
		f.setLocation((ss.width - fs.width) / 900, (ss.height - fs.height) / 17);
		f.setTitle("Receive Approval Drawing");
		f.setSize(900, 600);
		f.setVisible(true);
	}

	String wisestr = "";
	String valuestr = "";

	public void itemList() {
		if (rb1.isSelected()) {
			wisestr = rb1.getText();
		}
		if (rb2.isSelected()) {
			wisestr = rb2.getText();
		}
		if (rb3.isSelected()) {
			wisestr = rb3.getText();
		}

		model.setNumRows(0);
		model.addRow(new Object[] { "%" });

		try {
			con = DBConnectionUtil.getConnection();
			stat = con.createStatement();
			rs = stat.executeQuery("select DISTINCT(" + wisestr + ") from APPROVAL_DATA ");
			rs.getMetaData().getColumnCount();
			while (rs.next()) {
				String str = new String(rs.getString(1));
				model.addRow(new Object[] { str });
			}
		} catch (Exception localException) {
			System.out.println(localException);
		}
	}

	public void itemDetails() {
		model1.setNumRows(0);
		int i = tb.getSelectedRow();
		valuestr = String.valueOf(model.getValueAt(i, 0));
		try {
			stat = con.createStatement();
			rs = stat.executeQuery(
					"select item_code, drawing_no, Sent_date, Receive_date, approve_status, bfa_status, fab from APPROVAL_DATA where "
							+ wisestr + " LIKE '" + valuestr + "' and RECEIVE_DATE='0' ");
			rs.getMetaData().getColumnCount();
			while (rs.next()) {

				String str1 = new String(rs.getString(1));
				String str2 = new String(rs.getString(2));
				String str3 = new String(rs.getString(3));
				String str4 = new String(rs.getString(4));
				String str5 = new String(rs.getString(5));
				String str6 = new String(rs.getString(6));
				String str7 = new String(rs.getString(7));

				model1.addRow(new Object[] { str1, str2, str3, str4, str5, str6, str7 });
			}
		} catch (Exception localException) {
			System.out.println(localException);
		}
	}

	public void viewAll() {
		model1.setNumRows(0);
		tb.getSelectedRow();

		try {
			stat = con.createStatement();
			rs = stat.executeQuery(
					"select item_code, drawing_no, Sent_date, Receive_date, approve_status, bfa_status, fab from APPROVAL_DATA where "
							+ wisestr + " like '" + valuestr + "'   ");
			rs.getMetaData().getColumnCount();
			while (rs.next()) {
				String str1 = new String(rs.getString(1));
				String str2 = new String(rs.getString(2));
				String str3 = new String(rs.getString(3));
				String str4 = new String(rs.getString(4));
				String str5 = new String(rs.getString(5));
				String str6 = new String(rs.getString(6));
				String str7 = new String(rs.getString(7));

				model1.addRow(new Object[] { str1, str2, str3, str4, str5, str6, str7 });
			}
		} catch (Exception localException) {
			System.out.println(localException);
		}
	}

	public void actionPerformed(ActionEvent paramActionEvent) {
		if (paramActionEvent.getSource() == closebut) {
			f.setVisible(false);
		}
		if (paramActionEvent.getSource() == correctbut) {
			int i = tb.getSelectedRow();
			int[] arrayOfInt = tb1.getSelectedRows();
			for (int j = 0; j < arrayOfInt.length; j++) {
				String str1 = String.valueOf(model.getValueAt(i, 0));
				String str2 = String.valueOf(model1.getValueAt(arrayOfInt[j], 0));
				String str3 = String.valueOf(model1.getValueAt(arrayOfInt[j], 1));
				int k = Integer.parseInt(String.valueOf(model1.getValueAt(arrayOfInt[j], 6)));
				String str4 = String.valueOf(k + 1);

				try {
					stat = con.createStatement();
					affected = stat.executeUpdate(" update APPROVAL_DATA set RECEIVE_DATE='" + datestr
							+ "' , BFA_STATUS='C', fab='" + str4 + "' where project_no='" + str1 + "' and item_code='"
							+ str2 + "' and drawing_no='" + str3 + "'  ");
					if (affected > 0) {
						model1.setValueAt(datestr, arrayOfInt[j], 3);
						model1.setValueAt("C", arrayOfInt[j], 5);
						model1.setValueAt(str4, arrayOfInt[j], 6);
					}
				} catch (Exception localException) {
					javax.swing.JOptionPane.showMessageDialog(f, localException.getMessage().toString());
				}
			}
		}

		if (paramActionEvent.getSource() == rb1) {

			rb1.setBackground(new Color(60, 130, 130));
			rb2.setBackground(Color.lightGray);
			rb3.setBackground(Color.lightGray);
			itemList();
		}
		if (paramActionEvent.getSource() == rb2) {
			rb2.setBackground(new Color(60, 130, 130));
			rb1.setBackground(Color.lightGray);
			rb3.setBackground(Color.lightGray);
			itemList();
		}
		if (paramActionEvent.getSource() == rb3) {
			rb3.setBackground(new Color(60, 130, 130));
			rb1.setBackground(Color.lightGray);
			rb2.setBackground(Color.lightGray);
			itemList();
		}

		if (paramActionEvent.getSource() == viewbut) {
			viewAll();
			correctbut.setEnabled(false);
		}
	}

	public void mousePressed(MouseEvent paramMouseEvent) {
		itemDetails();
		correctbut.setEnabled(true);
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
