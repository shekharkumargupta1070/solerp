package com.sol.erp;

import javax.swing.JTextField;

import com.sol.erp.util.DBConnectionUtil;

public class DrgIndex
		implements java.awt.event.ActionListener, java.awt.event.FocusListener, java.awt.event.ItemListener {
	java.sql.Connection con;
	java.sql.Statement stat;
	java.sql.ResultSet rs;
	java.text.DecimalFormat df;
	java.text.DecimalFormat df1;
	java.text.DecimalFormat timeformat;
	String[] column;
	Object[][] data;
	javax.swing.table.DefaultTableModel drgmodel;
	javax.swing.JTable tb;

	public DrgIndex() {

		df = new java.text.DecimalFormat("00.00");
		df1 = new java.text.DecimalFormat("00");
		timeformat = new java.text.DecimalFormat("0000");

		column = new String[] { "SL", "DRG No" };
		data = new Object[0][];

		drgmodel = new javax.swing.table.DefaultTableModel(data, column);
		tb = new javax.swing.JTable(drgmodel);
		drgsp = new javax.swing.JScrollPane(tb);

		f = new javax.swing.JFrame("Drawing Index");
		l1 = new javax.swing.JLabel("Date");
		l2 = new javax.swing.JLabel("Project No");
		l3 = new javax.swing.JLabel("Name");
		l4 = new javax.swing.JLabel("Client");
		l5 = new javax.swing.JLabel("Item Code");
		l6 = new javax.swing.JLabel("Drg Format");
		l7 = new javax.swing.JLabel("Drg Title");
		l8 = new javax.swing.JLabel("Remarks");

		northlabel = new javax.swing.JLabel("Create Drawing Index");
		westlabel = new javax.swing.JLabel(new javax.swing.ImageIcon("image/report.gif"));

		tf1 = new JTextField();
		tf2 = new JTextField();
		tf3 = new JTextField();
		tf4 = new JTextField();
		tf5 = new java.awt.Choice();
		tf6 = new java.awt.Choice();
		tf7 = new JTextField();
		tf8 = new JTextField();

		savebut = new javax.swing.JButton("Save");
		closebut = new javax.swing.JButton("Close");

		centerpanel = new javax.swing.JPanel();
		northpanel = new javax.swing.JPanel();
		westpanel = new javax.swing.JPanel();

		dat = new java.util.Date();
		formatter = new java.text.SimpleDateFormat("dd/MM/yyyy");

		try {
			dateString = formatter.format(dat);
			tf1 = new JTextField(String.valueOf(dateString));
		} catch (IllegalArgumentException localIllegalArgumentException) {
			System.out.println(localIllegalArgumentException.getMessage());
		}

		tk = java.awt.Toolkit.getDefaultToolkit();
		ss = tk.getScreenSize();
		fs = f.getSize();

		fo = new java.awt.Font("Tahoma", 1, 11);
		bigfo = new java.awt.Font("Tahoma", 1, 24);
	}

	javax.swing.JScrollPane drgsp;

	public void px() {
		java.awt.Container localContainer = f.getContentPane();
		localContainer.setLayout(new java.awt.BorderLayout());
		centerpanel.setLayout(null);
		northpanel.setLayout(new java.awt.BorderLayout());
		westpanel.setLayout(new java.awt.BorderLayout());

		northpanel.add(northlabel, "Center");
		westpanel.add(westlabel, "Center");

		tf6.addItem("A1");
		tf6.addItem("A2");
		tf6.addItem("A3");
		tf6.addItem("A4");
		tf6.setEnabled(false);

		centerpanel.setBackground(new java.awt.Color(60, 130, 130));

		northlabel.setForeground(java.awt.Color.red);
		l1.setForeground(java.awt.Color.white);
		l2.setForeground(java.awt.Color.white);
		l3.setForeground(java.awt.Color.white);
		l4.setForeground(java.awt.Color.white);
		l5.setForeground(java.awt.Color.white);
		l6.setForeground(java.awt.Color.white);
		l7.setForeground(java.awt.Color.white);
		l8.setForeground(java.awt.Color.white);

		northlabel.setFont(bigfo);
		tf1.setFont(fo);
		tf1.setEditable(false);
		tf2.setFont(fo);
		tf3.setFont(fo);
		tf3.setEditable(false);
		tf4.setFont(fo);
		tf4.setEditable(false);
		tf5.setFont(fo);
		tf6.setFont(fo);
		tf7.setFont(fo);
		tf8.setFont(fo);

		northpanel.setBackground(java.awt.Color.white);

		northpanel.setPreferredSize(new java.awt.Dimension(100, 70));

		l1.setBounds(30, 30, 150, 20);
		tf1.setBounds(200, 30, 90, 20);
		l2.setBounds(300, 30, 60, 20);
		tf2.setBounds(360, 30, 90, 20);
		l3.setBounds(30, 55, 150, 20);
		tf3.setBounds(200, 55, 250, 20);
		l4.setBounds(30, 80, 150, 20);
		tf4.setBounds(200, 80, 250, 20);
		l5.setBounds(30, 105, 150, 20);
		tf5.setBounds(200, 105, 60, 20);
		l6.setBounds(270, 105, 90, 20);
		tf6.setBounds(340, 105, 110, 20);
		l7.setBounds(30, 130, 150, 20);
		tf7.setBounds(200, 130, 250, 20);
		l8.setBounds(30, 155, 150, 20);
		tf8.setBounds(200, 155, 250, 20);
		drgsp.setBounds(460, 30, 120, 145);

		savebut.setBounds(150, 185, 100, 25);
		closebut.setBounds(270, 185, 100, 25);

		savebut.setMnemonic(83);
		closebut.setMnemonic(67);

		centerpanel.add(l1);
		centerpanel.add(tf1);
		centerpanel.add(l2);
		centerpanel.add(tf2);
		centerpanel.add(l3);
		centerpanel.add(tf3);
		centerpanel.add(l4);
		centerpanel.add(tf4);
		centerpanel.add(l5);
		centerpanel.add(tf5);
		centerpanel.add(l6);
		centerpanel.add(tf6);
		centerpanel.add(l7);
		centerpanel.add(tf7);
		centerpanel.add(l8);
		centerpanel.add(tf8);
		centerpanel.add(drgsp);

		centerpanel.add(savebut);
		centerpanel.add(closebut);

		localContainer.add(northpanel, "North");
		localContainer.add(centerpanel, "Center");

		tf2.addFocusListener(this);
		tf5.addItemListener(this);
		savebut.addActionListener(this);
		closebut.addActionListener(this);

		f.setSize(620, 335);
		f.setLocation((ss.width - fs.width) / 10, (ss.height - fs.height) / 5);
		f.setResizable(false);
		f.setVisible(true);
		tf2.requestFocus();
	}

	javax.swing.JFrame f;
	javax.swing.JLabel l1;

	public void itemCode() {
		tf5.removeAll();
		try {
			con = DBConnectionUtil.getConnection();
			stat = con.createStatement();
			rs = stat.executeQuery(
					"select distinct(item_code) From estimation WHERE project_no='" + tf2.getText() + "' ");
			rs.getMetaData().getColumnCount();
			while (rs.next()) {
				String str = new String(rs.getString(1));

				tf5.addItem(str);
				tf5.requestFocus();
			}
		} catch (Exception localException) {
			javax.swing.JOptionPane.showMessageDialog(f, localException.getMessage().toString());
		}
	}

	javax.swing.JLabel l2;

	public void drgFormat() {
		tf6.removeAll();
		try {
			stat = con.createStatement();
			rs = stat.executeQuery("select distinct(Sheet_size) From Estimation WHERE project_no='"
					+ tf2.getText() + "' AND ITEM_CODE='" + tf5.getSelectedItem() + "' ");

			while (rs.next()) {
				String str = new String(rs.getString(1));
				tf6.addItem(str);

				tf7.requestFocus();
			}
		} catch (Exception localException) {
			javax.swing.JOptionPane.showMessageDialog(f, localException.getMessage().toString());
		}
	}

	javax.swing.JLabel l3;
	javax.swing.JLabel l4;
	javax.swing.JLabel l5;

	public void drgList() {
		drgFormat();

		drgmodel.setNumRows(0);
		int i = 1;
		try {
			stat = con.createStatement();
			rs = stat.executeQuery("select Drawing_no From DRAWINGNO WHERE project_no='" + tf2.getText()
					+ "' AND ITEM_CODE='" + tf5.getSelectedItem() + "' and status not like 'Indexed' ");

			while (rs.next()) {
				String str = new String(rs.getString(1));

				drgmodel.addRow(new Object[] { String.valueOf(i), str });
				i += 1;
				tf7.requestFocus();
			}
		} catch (Exception localException) {
			javax.swing.JOptionPane.showMessageDialog(f, localException.getMessage().toString());
		}
	}

	javax.swing.JLabel l6;
	javax.swing.JLabel l7;
	javax.swing.JLabel l8;
	javax.swing.JLabel northlabel;

	public void projectDetails() {
		if (tf2.getText().equalsIgnoreCase("")) {
			javax.swing.JOptionPane.showMessageDialog(f, "Project No is Empty");

		} else {
			itemCode();
			tf3.setText("");
			tf4.setText("");

			try {
				con = DBConnectionUtil.getConnection();
				stat = con.createStatement();
				rs = stat
						.executeQuery("select max(project_name), max(client_name) From estimation_MP WHERE project_no='"
								+ tf2.getText() + "' ");
				rs.getMetaData().getColumnCount();
				while (rs.next()) {
					String str1 = new String(rs.getString(1));
					String str2 = new String(rs.getString(2));

					tf3.setText(str1);
					tf4.setText(str2);
					tf5.requestFocus();
				}
			} catch (Exception localException) {
				javax.swing.JOptionPane.showMessageDialog(f, localException.getMessage().toString());
			}
		}
	}

	public void saveRecord() {
		int i = tb.getSelectedRow();
		int[] arrayOfInt = tb.getSelectedRows();
		int j = arrayOfInt.length + i - 1;

		String.valueOf(drgmodel.getValueAt(i, 1));
		String.valueOf(drgmodel.getValueAt(j, 1));
		for (; i <= j; i++) {
			String str3 = String.valueOf(drgmodel.getValueAt(i, 1));
			try {
				stat = con.createStatement();
				stat.executeUpdate("insert into DRG_INDEX values('" + tf2.getText() + "','"
						+ tf5.getSelectedItem() + "','" + str3 + "','" + tf7.getText() + "','"
						+ tf6.getSelectedItem() + "','" + tf8.getText() + "' )");
			} catch (Exception localException1) {
				if (localException1.getMessage().toString().equalsIgnoreCase("No ResultSet was Produced")) {
				}

				javax.swing.JOptionPane.showMessageDialog(f, "Index Updated.");
			}

			try {
				stat = con.createStatement();
				stat.executeUpdate("update drawingno set status='Indexed' where project_no='" + tf2.getText()
						+ "' and item_code='" + tf5.getSelectedItem() + "' and drawing_no ='" + str3 + "'  ");
			} catch (Exception localException2) {
				if (localException2.getMessage().toString().equalsIgnoreCase("No ResultSet was Produced")) {
				}

				System.out.println("Drg no Updated as Indexed.");
			}
		}
	}

	javax.swing.JLabel westlabel;
	JTextField tf1;
	JTextField tf2;
	JTextField tf3;
	JTextField tf4;

	public void actionPerformed(java.awt.event.ActionEvent paramActionEvent) {
		if (paramActionEvent.getSource() == savebut) {
			saveRecord();
		}
		if (paramActionEvent.getSource() == closebut) {
			f.setVisible(false);
		}
	}

	java.awt.Choice tf5;
	java.awt.Choice tf6;

	public void focusGained(java.awt.event.FocusEvent paramFocusEvent) {
	}

	public void focusLost(java.awt.event.FocusEvent paramFocusEvent) {
		if (paramFocusEvent.getSource() == tf2) {
			projectDetails();
		}
	}

	JTextField tf7;
	JTextField tf8;
	javax.swing.JButton savebut;

	public void itemStateChanged(java.awt.event.ItemEvent paramItemEvent) {
		if (paramItemEvent.getSource() == tf5) {
			drgList();
		}
	}

	javax.swing.JButton closebut;
	javax.swing.JPanel centerpanel;
	javax.swing.JPanel northpanel;


	javax.swing.JPanel westpanel;
	java.util.Date dat;
	java.text.SimpleDateFormat formatter;
	String dateString;
	java.awt.Toolkit tk;
	java.awt.Dimension ss;
	java.awt.Dimension fs;
	java.awt.Font fo;
	java.awt.Font bigfo;
}
