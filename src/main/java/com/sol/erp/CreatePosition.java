package com.sol.erp;

import java.awt.Choice;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.sql.Connection;

import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.sol.erp.ui.custom.SolTableModel;
import com.sol.erp.util.DBConnectionUtil;

public class CreatePosition implements java.awt.event.ActionListener, java.awt.event.ItemListener {
	Connection con;
	java.sql.Statement stat;
	java.sql.ResultSet rs;
	int affected = 0;

	String[] head = { "Name" };
	Object[][] data = new Object[0][];

	JInternalFrame f = new JInternalFrame("Create Basic Setups", true, true, true, true);
	DefaultTableModel model = new DefaultTableModel(data, head);
	JTable tb = new JTable(model);
	javax.swing.JScrollPane sp = new javax.swing.JScrollPane(tb);

	Choice ch = new Choice();

	JTextField tf = new JTextField(20);
	JButton b1 = new JButton("Create", new javax.swing.ImageIcon("image/create.gif"));
	JButton b2 = new JButton("Remove");
	JButton b3 = new JButton("Show List", new javax.swing.ImageIcon("image/play.gif"));
	JButton b4 = new JButton("Close", new javax.swing.ImageIcon("image/close.gif"));

	JPanel p1 = new JPanel();
	JPanel p2 = new JPanel();


	public void px() {
		Container localContainer = f.getContentPane();
		localContainer.setLayout(new java.awt.BorderLayout());

		p1.setLayout(new java.awt.FlowLayout(0));
		p2.setLayout(new java.awt.FlowLayout(2));

		SolTableModel.decorateTable(tb);

		ch.addItem("DEPTS");
		ch.addItem("POSITIONS");
		ch.addItem("POLICIES");
		ch.addItem("SAL_BENEFITES");
		ch.addItem("SAL_DEDUCTIONS");
		ch.addItem("LEAVES");
		ch.addItem("REQ_MODES");

		p1.add(ch);
		p1.add(b3);

		p2.add(tf);
		p2.add(b1);
		p2.add(b2);
		p2.add(b4);

		localContainer.add(p1, "North");
		localContainer.add(sp, "Center");
		localContainer.add(p2, "South");

		tf.addActionListener(this);
		b1.addActionListener(this);
		b2.addActionListener(this);
		b3.addActionListener(this);
		b4.addActionListener(this);
		ch.addItemListener(this);

		f.setLocation(300, 150);
		f.setSize(520, 400);
		f.setResizable(false);
		f.setVisible(true);
	}

	public void create() {
		String str = "HR" + ch.getSelectedItem();
		try {
			con = DBConnectionUtil.getConnection();
			stat = con.createStatement();
			affected = stat.executeUpdate("insert into " + str + " values('" + tf.getText() + "') ");

			if (affected > 0) {
				model.addRow(new Object[] { tf.getText() });
				tf.setText("");
			}
		} catch (Exception localException) {
			javax.swing.JOptionPane.showMessageDialog(f, localException.getMessage().toString());
		}
	}

	public void remove() {
		String str1 = String.valueOf(model.getValueAt(tb.getSelectedRow(), 0));
		String str2 = "HR" + ch.getSelectedItem();
		try {
			con = DBConnectionUtil.getConnection();
			stat = con.createStatement();
			affected = stat.executeUpdate("delete from " + str2 + " where name ='" + str1 + "'  ");

			if (affected > 0) {
				model.removeRow(tb.getSelectedRow());
			}
		} catch (Exception localException) {
			javax.swing.JOptionPane.showMessageDialog(f, localException.getMessage().toString());
		}
	}

	public void show() {
		model.setNumRows(0);
		String str1 = "HR" + ch.getSelectedItem();
		try {
			con = DBConnectionUtil.getConnection();
			stat = con.createStatement();
			rs = stat.executeQuery("select * from " + str1);
			while (rs.next()) {
				String str2 = new String(rs.getString(1));
				model.addRow(new Object[] { str2 });
			}
		} catch (Exception localException) {
			System.out.println("Show : " + localException);
		}
	}

	public void actionPerformed(ActionEvent paramActionEvent) {
		if ((paramActionEvent.getSource() == b1) || (paramActionEvent.getSource() == tf)) {
			create();
		}
		if (paramActionEvent.getSource() == b2) {
			remove();
		}
		if (paramActionEvent.getSource() == b3) {
			show();
		}
		if (paramActionEvent.getSource() == b4) {
			f.setVisible(false);
		}
	}

	public void itemStateChanged(java.awt.event.ItemEvent paramItemEvent) {
		if (paramItemEvent.getSource() == ch) {
			show();
		}
	}
}
