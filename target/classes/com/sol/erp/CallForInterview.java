package com.sol.erp;

import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.sol.erp.dao.UtilQueryResult;
import com.sol.erp.ui.custom.SimplePrintableJTextArea;
import com.sol.erp.ui.custom.SolCalendar;
import com.sol.erp.ui.custom.SolCalendar3;
import com.sol.erp.ui.custom.SolTableModel;
import com.sol.erp.util.DBConnectionUtil;

public class CallForInterview implements java.awt.event.ActionListener, java.awt.event.MouseListener {
	java.sql.Connection con;
	java.sql.Statement stat;
	ResultSet rs;
	UtilQueryResult qr = new UtilQueryResult();


	SolCalendar skl = new SolCalendar();
	

	Object[][] data = new Object[0][];

	String[] head = { "Serial", "Candidate Name", "Contact", "Post Appling For", "Date", "Remarks", "Aplicant No",
			"Round", "Status" };

	javax.swing.JInternalFrame f = new javax.swing.JInternalFrame("Call For Interview", true, true, true, true);

	DefaultTableModel model1 = new DefaultTableModel(data, head);
	JTable tb = new JTable(model1) {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public boolean isCellEditable(int paramAnonymousInt1, int paramAnonymousInt2) {
			convertColumnIndexToModel(paramAnonymousInt2);
			return paramAnonymousInt2 < 0;
		}
	};
	javax.swing.JScrollPane sp = new javax.swing.JScrollPane(tb);

	JLabel l = new JLabel("Serial No : ");
	JLabel l1 = new JLabel("First Name : ");
	JLabel l2 = new JLabel("Middle Name : ");
	JLabel l3 = new JLabel("Last Name : ");
	JLabel l4 = new JLabel("Phone : ");
	JLabel deptlabel = new JLabel("Department : ");
	JLabel l5 = new JLabel("Post : ");
	JLabel l6 = new JLabel("Date of Interview : ");
	JLabel l7 = new JLabel("Time of Interview : ");
	JLabel l8 = new JLabel("Ref. & Remarks : ");
	JLabel l9 = new JLabel("Search By : ");

	JTextField tf = new JTextField();
	JTextField tf1 = new JTextField();
	JTextField tf2 = new JTextField();
	JTextField tf3 = new JTextField();
	JTextField tf4 = new JTextField();

	DeptList depttf = new DeptList("C01", "0");
	DesigList tf5 = new DesigList("C01", "0", depttf.getText());

	SolCalendar3 tf6 = new SolCalendar3();
	JTextField tf7 = new JTextField();
	JTextField tf8 = new JTextField("0");
	JTextField tf9 = new JTextField(20);

	JComboBox cb1 = new JComboBox();

	JButton b1 = new JButton("SAVE");
	JButton b2 = new JButton("UPDATE");

	JButton b3 = new JButton("SEARCH");
	JButton b4 = new JButton("SHOW ALL");
	JButton b5 = new JButton("GENERATE APPLICANT NO.");
	JButton b6 = new JButton("REMOVE");

	JPanel p1 = new JPanel();
	JPanel p2 = new JPanel();

	JPanel toppanel = new JPanel();
	JPanel northpanel = new JPanel();
	JPanel centerpanel = new JPanel();
	JPanel southpanel = new JPanel();

	javax.swing.border.Border bor1 = javax.swing.BorderFactory.createBevelBorder(0);

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

			if ((paramInt2 == 0) || (paramInt2 == 1) || (paramInt2 == 2)) {
				setHorizontalAlignment(2);
			}
			if (paramInt2 > 2) {
				setHorizontalAlignment(0);
			}

			if (paramInt1 % 2 == 0) {
				setBackground(new Color(240, 240, 240));
				setForeground(Color.darkGray);
			} else {
				setBackground(new Color(250, 250, 250));
				setForeground(Color.darkGray);
			}

			if (paramInt1 > 0) {
				int i = Integer.parseInt(String.valueOf(model1.getValueAt(paramInt1, 7)));
				if (i > 0) {
					setBackground(Color.orange);
					setForeground(Color.blue);
				}
			}

			super.getTableCellRendererComponent(paramJTable, paramObject, paramBoolean1, paramBoolean2, paramInt1,
					paramInt2);

			return this;
		}
	}

	java.awt.Font fo = new java.awt.Font("Verdana", 0, 11);
	CallForInterview.ColoredTableCellRenderer skc = new CallForInterview.ColoredTableCellRenderer();
	java.awt.Font myfont = new java.awt.Font("Verdana", 1, 9);

	public void px() {
		serialNo();

		Container localContainer = f.getContentPane();
		localContainer.setLayout(new java.awt.BorderLayout());
		northpanel.setLayout(new java.awt.BorderLayout());
		centerpanel.setLayout(new java.awt.BorderLayout());
		southpanel.setLayout(new java.awt.FlowLayout(2));
		p2.setLayout(new java.awt.FlowLayout(2));
		p1.setLayout(new java.awt.GridLayout(10, 2, 2, 2));
		toppanel.setLayout(new java.awt.FlowLayout(0));

		p2.setBorder(bor1);

		l.setHorizontalAlignment(4);
		l1.setHorizontalAlignment(4);
		l2.setHorizontalAlignment(4);
		l3.setHorizontalAlignment(4);
		l4.setHorizontalAlignment(4);
		l5.setHorizontalAlignment(4);
		deptlabel.setHorizontalAlignment(4);
		l6.setHorizontalAlignment(4);
		l7.setHorizontalAlignment(4);
		l8.setHorizontalAlignment(4);

		p1.add(l);
		p1.add(tf);
		tf.setEditable(false);
		p1.add(l1);
		p1.add(tf1);
		p1.add(l2);
		p1.add(tf2);
		p1.add(l3);
		p1.add(tf3);
		p1.add(l4);
		p1.add(tf4);
		p1.add(deptlabel);
		p1.add(depttf);
		depttf.setEditable(false);
		p1.add(l5);
		p1.add(tf5);
		tf5.setEditable(false);
		p1.add(l6);
		p1.add(tf6.DesignFrame());

		p1.add(l8);
		p1.add(tf8);

		p2.add(b1);
		p2.add(b2);
		p2.add(b6);
		p2.add(l9);
		p2.add(tf9);
		tf9.setFont(myfont);
		tf9.setHorizontalAlignment(4);
		p2.add(b3);
		p2.add(b5);

		northpanel.add(p1, "West");
		p1.setPreferredSize(new java.awt.Dimension(530, 230));
		northpanel.add(p2, "South");

		centerpanel.add(sp, "Center");

		northpanel.add(toppanel, "North");

		localContainer.add(northpanel, "North");
		localContainer.add(centerpanel, "Center");
		localContainer.add(southpanel, "South");

		SolTableModel.decorateTable(tb);

		for (int i = 0; i < tb.getColumnCount(); i++) {
			tb.getColumnModel().getColumn(i).setCellRenderer(skc);
		}

		tb.getTableHeader().setPreferredSize(new java.awt.Dimension(30, 30));
		tb.setRowHeight(25);
		tb.setAutoResizeMode(0);
		tb.getColumnModel().getColumn(0).setPreferredWidth(40);
		tb.getColumnModel().getColumn(1).setPreferredWidth(180);
		tb.getColumnModel().getColumn(2).setPreferredWidth(120);
		tb.getColumnModel().getColumn(3).setPreferredWidth(120);
		tb.getColumnModel().getColumn(4).setPreferredWidth(100);
		tb.getColumnModel().getColumn(5).setPreferredWidth(245);
		tb.setIntercellSpacing(new java.awt.Dimension(1, 1));
		tb.setFont(new java.awt.Font("Tahoma", 1, 10));
		tb.setShowGrid(false);
		tb.setAutoCreateRowSorter(true);

		tb.removeColumn(tb.getColumnModel().getColumn(0));

		cb1.addItem("First_Name");
		cb1.addItem("Middle_Name");
		cb1.addItem("Last_Name");
		cb1.addItem("Date");
		cb1.addItem("Post");
		cb1.addItem("Ref");

		depttf.addActionListener(this);

		tf8.addActionListener(this);
		tf9.addActionListener(this);

		b1.addActionListener(this);
		b2.addActionListener(this);
		b3.addActionListener(this);
		b4.addActionListener(this);
		b5.addActionListener(this);
		b6.addActionListener(this);

		tf5.addMouseListener(this);
		depttf.addMouseListener(this);

		f.setSize(800, 600);
		f.setVisible(true);

		b1.setBackground(new Color(200, 120, 120));
		b1.setForeground(Color.white);
		b1.setFont(myfont);

		b2.setBackground(new Color(200, 120, 120));
		b2.setForeground(Color.white);
		b2.setFont(myfont);

		b3.setBackground(new Color(200, 120, 120));
		b3.setForeground(Color.white);
		b3.setFont(myfont);

		b5.setBackground(new Color(200, 120, 120));
		b5.setForeground(Color.white);
		b5.setFont(myfont);

		b6.setBackground(new Color(200, 120, 120));
		b6.setForeground(Color.white);
		b6.setFont(myfont);
	}

	JFrame printFrame = new JFrame("Print Aplicant No");
	SimplePrintableJTextArea printTA = new SimplePrintableJTextArea();
	javax.swing.JScrollPane sp1 = new javax.swing.JScrollPane(printTA);

	JPanel printPanel = new JPanel();
	JButton printBut = new JButton("<< OK >>");
	JButton cancelBut = new JButton("Cancel");

	public void printFrame() {
		Container localContainer = printFrame.getContentPane();
		localContainer.setLayout(new java.awt.BorderLayout());
		printPanel.setLayout(new java.awt.FlowLayout(0));

		printTA.setFont(new java.awt.Font("Tahoma", 1, 11));

		printTA.setEditable(false);
		localContainer.add(sp1, "Center");
		localContainer.add(printPanel, "South");

		printPanel.add(printBut);
		printPanel.add(cancelBut);

		printBut.addActionListener(this);
		cancelBut.addActionListener(this);

		printFrame.setSize(600, 500);
		printFrame.setLocation(220, 120);
		printFrame.setVisible(true);
		printFrame.setResizable(false);
	}

	public void serialNo() {
		try {
			con =  DBConnectionUtil.getConnection();
			stat = con.createStatement();
			rs = stat.executeQuery("Select max(sl_no) from HRINTERVIEW_CALL");

			while (rs.next()) {
				String str = new String(rs.getString(1));
				int i = Integer.parseInt(str);

				tf.setText(String.valueOf(i + 1));
			}
		} catch (Exception localException) {
		}
	}

	int affected = 0;

	public void insert() {
		model1.setNumRows(0);

		try {
			con =  DBConnectionUtil.getConnection();
			stat = con.createStatement();
			affected = stat.executeUpdate("Insert into HRINTERVIEW_CALL values('" + tf.getText() + "','"
					+ tf1.getText() + "', '" + tf2.getText() + "', '" + tf3.getText() + "', '"
					+ tf4.getText() + "', '" + tf5.getText() + "', '" + tf6.getText() + "', '"
					+ tf7.getText() + "', '" + tf8.getText() + "','0','0','0')");

			if (affected > 0) {
				model1.addRow(new Object[] { tf.getText(),
						tf1.getText() + " " + tf2.getText() + " " + tf3.getText(), tf4.getText(),
						tf5.getText(), tf6.getText() + " " + tf7.getText(), tf8.getText(), "0" });
				int i = Integer.parseInt(tf.getText());
				tf.setText(String.valueOf(i + 1));
				tf1.setText("");
				tf2.setText("");
				tf3.setText("");
				tf4.setText("");
				tf7.setText("");
				tf8.setText("");
			}
		} catch (Exception localException) {
			javax.swing.JOptionPane.showMessageDialog(f, localException.getMessage().toString());
		}
	}

	public void searchRecord() {
		model1.setNumRows(0);
		cb1.getSelectedItem();
		String str2 = "%" + tf9.getText() + "%";

		try {
			con =  DBConnectionUtil.getConnection();
			stat = con.createStatement();
			rs = stat.executeQuery("Select * from HRINTERVIEW_CALL where FIRST_NAME like '" + str2
					+ "' or MIDDLE_NAME like '" + str2 + "'  or LAST_NAME like '" + str2 + "'  or DATE like '" + str2
					+ "'  or POST like '" + str2 + "'  or REF like '" + str2 + "'  or PHONE like '" + str2
					+ "' and aplicant_no like '" + str2 + "' and status='0' order by aplicant_no ");

			while (rs.next()) {
				String str3 = new String(rs.getString(1));
				String str4 = new String(rs.getString(2));
				String str5 = new String(rs.getString(3));
				String str6 = new String(rs.getString(4));
				String str7 = new String(rs.getString(5));
				String str8 = new String(rs.getString(6));
				String str9 = new String(rs.getString(7));
				new String(rs.getString(8));
				String str11 = new String(rs.getString(9));
				String str12 = new String(rs.getString(10));
				String str13 = new String(rs.getString(11));
				String str14 = new String(rs.getString(12));

				model1.addRow(new Object[] { str3,
						" " + str4.toUpperCase() + " " + str5.toUpperCase() + " " + str6.toUpperCase(),
						str7.toUpperCase(), str8.toUpperCase(), str9.toUpperCase(), str11.toUpperCase(),
						str12.toUpperCase(), str13.toUpperCase(), str14.toUpperCase() });
			}
		} catch (Exception localException) {
		}
	}

	public void showRecord() {
		model1.setNumRows(0);
		String str1 = (String) cb1.getSelectedItem();
		String str2 = tf6.getText() + "%";

		try {
			con =  DBConnectionUtil.getConnection();
			stat = con.createStatement();
			rs = stat.executeQuery(
					"Select * from HRINTERVIEW_CALL where " + str1 + " like '" + str2 + "' order by First_Name ");

			while (rs.next()) {
				String str3 = new String(rs.getString(1));
				String str4 = new String(rs.getString(2));
				String str5 = new String(rs.getString(3));
				String str6 = new String(rs.getString(4));
				String str7 = new String(rs.getString(5));
				String str8 = new String(rs.getString(6));
				String str9 = new String(rs.getString(7));
				new String(rs.getString(8));
				String str11 = new String(rs.getString(9));
				String str12 = new String(rs.getString(10));
				String str13 = new String(rs.getString(11));
				String str14 = new String(rs.getString(12));

				model1.addRow(
						new Object[] { str3, str4.toUpperCase() + " " + str5.toUpperCase() + " " + str6.toUpperCase(),
								str7.toUpperCase(), str8.toUpperCase(), str9.toUpperCase(), str11.toUpperCase(),
								str12.toUpperCase(), str13.toUpperCase(), str14.toUpperCase() });
			}
		} catch (Exception localException) {
		}
	}

	String apnostr = "";

	public void aplicantNo() {
		try {
			rs = stat.executeQuery("select max(aplicant_no) from HRINTERVIEW_CALL");
			while (rs.next()) {
				String str = new String(rs.getString(1));
				apnostr = str;
			}
		} catch (Exception localException) {
			System.out.println(localException);
		}
	}

	public void actionPerformed(ActionEvent paramActionEvent) {
		if ((paramActionEvent.getSource() == b1) || (paramActionEvent.getSource() == tf8)) {
			insert();
		}
		if ((paramActionEvent.getSource() == b3) || (paramActionEvent.getSource() == tf9)) {
			searchRecord();
			b5.setEnabled(true);
		}
		if (paramActionEvent.getSource() == b4) {
			showRecord();
			b5.setEnabled(false);
		}
		if (paramActionEvent.getSource() == depttf) {
			tf5.refreshList("C01", "0", depttf.getText());
		}

		if (paramActionEvent.getSource() == b6) {
			f.setVisible(false);
		}
		int[] arrayOfInt;
		int i;
		String str3;
		String str5;
		String str6;
		String str7;
		if (paramActionEvent.getSource() == b5) {
			printFrame();
			aplicantNo();

			arrayOfInt = tb.getSelectedRows();
			i = Integer.parseInt(apnostr);
			printTA.setText("");

			for (int j = 0; j < arrayOfInt.length; j++) {
				i += 1;
				int k = String.valueOf(model1.getValueAt(arrayOfInt[j], 6)).length();
				if (k > 1) {
					javax.swing.JOptionPane.showMessageDialog(printFrame,
							"Applicant No. is Allready Generated for This Candidate.");
					break;
				}
				model1.setValueAt(String.valueOf(i), arrayOfInt[j], 6);

				str3 = String.valueOf(model1.getValueAt(arrayOfInt[j], 1));
				String.valueOf(model1.getValueAt(arrayOfInt[j], 2));
				str5 = String.valueOf(model1.getValueAt(arrayOfInt[j], 3));
				str6 = String.valueOf(model1.getValueAt(arrayOfInt[j], 4));
				str7 = String.valueOf(model1.getValueAt(arrayOfInt[j], 5));

				printTA.append("\t\n\t--------------------------------------------------------------------\n");
				printTA.append("\tApplicant No \t\t: " + String.valueOf(i) + "\n");
				printTA.append("\tApplicant Name \t: " + str3 + "\n");
				printTA.append("\tPost \t\t: " + str5 + "\n");
				printTA.append("\tDate \t\t: " + str6 + "\n");
				printTA.append("\tRefferrenced By \t: " + str7);
				printTA.append("\t\n\t--------------------------------------------------------------------\n\n\n");
			}
			i = 0;
		}

		if (paramActionEvent.getSource() == printBut) {
			arrayOfInt = tb.getSelectedRows();
			for (i = 0; i < arrayOfInt.length;) {
				String str1 = String.valueOf(model1.getValueAt(arrayOfInt[i], 0));
				String.valueOf(model1.getValueAt(arrayOfInt[i], 1));
				str3 = String.valueOf(model1.getValueAt(arrayOfInt[i], 2));
				String.valueOf(model1.getValueAt(arrayOfInt[i], 3));
				str5 = String.valueOf(model1.getValueAt(arrayOfInt[i], 4));
				str6 = String.valueOf(model1.getValueAt(arrayOfInt[i], 5));
				str7 = String.valueOf(model1.getValueAt(arrayOfInt[i], 6));

				try {
					con =  DBConnectionUtil.getConnection();
					stat = con.createStatement();
					affected = stat.executeUpdate("update HRINTERVIEW_CALL set Aplicant_no='" + str7
							+ "' where sl_no='" + str1 + "' and phone='" + str3 + "' ");
					if (affected > 0) {
					}
					i++;

				} catch (Exception localException) {

					javax.swing.JOptionPane.showMessageDialog(f, localException.getMessage().toString());
				}
			}
			printTA.print();
			printFrame.setVisible(false);
		}

		if (paramActionEvent.getSource() == cancelBut) {
			printFrame.setVisible(false);
		}
	}

	public void mousePressed(MouseEvent paramMouseEvent) {
		if (javax.swing.SwingUtilities.isRightMouseButton(paramMouseEvent)) {
			if (paramMouseEvent.getSource() == tf6) {
				skl.showCalendar();
			}

			if ((paramMouseEvent.getSource() == depttf) &&

					(paramMouseEvent.getSource() != tf5)) {
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
