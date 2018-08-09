package com.sol.erp;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.sql.ResultSet;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTable;
import javax.swing.JTextField;

import com.sol.erp.util.DBConnectionUtil;

public class EmpFactorReport implements java.awt.event.ActionListener {

	java.sql.Connection con;
	java.sql.Statement stat;
	ResultSet rs;

	String[] columnNames = { "Emp Code", "Designation", "Unit No", "Item Code", "Factor", "Date of Joining" };
	Object[][] data = new Object[0][];

	java.text.DecimalFormat df = new java.text.DecimalFormat("00.00");

	javax.swing.table.DefaultTableModel model = new javax.swing.table.DefaultTableModel(data, columnNames);
	JTable tb = new JTable(model);
	javax.swing.JScrollPane sp = new javax.swing.JScrollPane(tb);

	JDialog f = new JDialog();

	JRadioButton rb2 = new JRadioButton("Unit Wise", true);
	JRadioButton rb3 = new JRadioButton("Emp Wise", false);

	javax.swing.ButtonGroup group = new javax.swing.ButtonGroup();

	JLabel l1 = new JLabel("Unit No");
	JLabel l2 = new JLabel("Factor Report");

	JTextField tf1 = new JTextField(6);
	JTextField tf2 = new JTextField(6);

	JButton b1 = new JButton("Show Status");
	JButton b2 = new JButton("Close", new javax.swing.ImageIcon("image/close.gif"));

	JPanel northp1 = new JPanel();
	JPanel northp2 = new JPanel();
	JPanel northp3 = new JPanel();

	JPanel butpanel1 = new JPanel();
	JPanel butpanel2 = new JPanel();

	JPanel centerpanel = new JPanel();
	JPanel northpanel = new JPanel();

	java.awt.Font fo = new java.awt.Font("Tahoma", 1, 11);
	javax.swing.border.Border line = javax.swing.BorderFactory.createLineBorder(Color.darkGray);
	javax.swing.border.Border bor1 = javax.swing.BorderFactory.createTitledBorder(line, "EMP Code", 0, 0, fo,
			Color.darkGray);
	javax.swing.border.Border bor2 = javax.swing.BorderFactory.createTitledBorder(line, "Factor Details", 0, 0,
			fo, Color.darkGray);

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

		tb.setFont(fo);
		tb.setBackground(Color.white);
		tb.setForeground(Color.black);
		tb.getTableHeader().setPreferredSize(new Dimension(50, 20));
		tb.getTableHeader().setFont(fo);
		tb.setRowHeight(20);
		tb.setGridColor(new Color(100, 150, 100));
		tb.setSelectionBackground(new Color(100, 150, 100));
		tb.setSelectionForeground(Color.white);

		northpanel.setBorder(bor1);
		sp.setBorder(bor2);

		group.add(rb2);
		group.add(rb3);

		northp1.add(rb2);
		northp1.add(rb3);
		northp1.add(l1);
		northp1.add(tf1);
		tf1.setFont(fo);
		northp1.add(l2);
		northp1.add(tf2);
		tf2.setFont(fo);
		northp1.add(b1);
		b1.setMnemonic(83);

		tf2.setEditable(false);

		butpanel2.add(l2);
		butpanel2.add(tf2);
		tf2.setFont(fo);
		butpanel2.add(b2);
		b2.setMnemonic(67);

		rb2.addActionListener(this);
		rb3.addActionListener(this);

		northpanel.add(northp1, "North");
		centerpanel.add(sp, "Center");
		centerpanel.add(butpanel2, "South");

		c.add(northpanel, "North");
		c.add(centerpanel, "Center");

		b1.addActionListener(this);
		b2.addActionListener(this);
		tf1.addActionListener(this);

		f.getRootPane().setDefaultButton(b1);
		f.setLocation((ss.width - fs.width) / 15, (ss.height - fs.height) / 6);
		f.setTitle("EMP Factor Report");
		f.setSize(700, 400);
		f.setVisible(true);
	}

	public void report() {
		model.setNumRows(0);
		int i = 0;
		float f1 = 0.0F;
		try {
			con = DBConnectionUtil.getConnection();
			stat = con.createStatement();
			rs = stat.executeQuery(
					"select emp_Code, designation,unit_no, item_code, factor from emp_status where unit_no='"
							+ tf1.getText() + "' or emp_code='" + tf1.getText() + "' ");
			while (rs.next()) {
				String str1 = new String(rs.getString(1));
				String str2 = new String(rs.getString(2));
				String str3 = new String(rs.getString(3));
				String str4 = new String(rs.getString(4));
				String str5 = new String(rs.getString(5));

				float f3 = Float.parseFloat(str5);
				model.addRow(new Object[] { str1, str2, str3, str4, df.format(f3) });

				f1 += f3;
				i += 1;
			}
			float f2 = f1 / i;
			tf2.setText(String.valueOf(df.format(f2)));

			i = 0;
			f1 = 0.0F;
		} catch (Exception localException) {
			System.out.println(localException);
		}
	}

	public void actionPerformed(ActionEvent paramActionEvent) {
		if ((paramActionEvent.getSource() == b1) || (paramActionEvent.getSource() == tf1)) {
			report();
		}
		if (paramActionEvent.getSource() == rb2) {
			l1.setText("Unit No");
		}
		if (paramActionEvent.getSource() == rb3) {
			l1.setText("Emp Code");
		}
		if (paramActionEvent.getSource() == b2) {
			f.setVisible(false);
		}
	}
}
