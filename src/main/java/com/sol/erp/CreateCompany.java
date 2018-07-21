package com.sol.erp;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.ResultSet;

import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.sol.erp.ui.custom.SolTableModel;
import com.sol.erp.util.DBConnectionUtil;

public class CreateCompany implements java.awt.event.ActionListener, java.awt.event.MouseListener {
	Connection con;
	java.sql.Statement stat;
	ResultSet rs;
	int affected = 0;

	String[] head = { "Company Name", "Address (Head Branch)", "Id" };
	Object[][] data = new Object[0][];

	java.text.DecimalFormat df = new java.text.DecimalFormat("00");

	JInternalFrame f = new JInternalFrame("Create Company", true, true, true, true);
	DefaultTableModel model = new DefaultTableModel(data, head);
	JTable tb = new JTable(model);
	javax.swing.JScrollPane sp = new javax.swing.JScrollPane(tb);

	JLabel l1 = new JLabel("Name");
	JLabel l2 = new JLabel("Address");
	JLabel l3 = new JLabel("Id");

	public static JTextField tf1 = new JTextField(12);
	public static JTextField tf2 = new JTextField(20);
	public static JTextField tf3 = new JTextField(4);

	JButton b1 = new JButton("Create Company", new javax.swing.ImageIcon("image/create.gif"));
	JButton b2 = new JButton("Remove", new javax.swing.ImageIcon("image/close.gif"));
	JButton b3 = new JButton("Show All", new javax.swing.ImageIcon("image/play.gif"));
	JButton b4 = new JButton("Close");
	JButton b5 = new JButton("Create Branch");

	JPanel p1 = new JPanel();
	JPanel p2 = new JPanel();


	public void designScreen() {
		Container localContainer = f.getContentPane();
		localContainer.setLayout(new java.awt.BorderLayout());

		p1.setLayout(new java.awt.FlowLayout(0));
		p2.setLayout(new java.awt.FlowLayout(2));

		SolTableModel.decorateTable(tb);
		tb.getColumnModel().getColumn(0).setPreferredWidth(150);
		tb.getColumnModel().getColumn(1).setPreferredWidth(360);
		tb.getColumnModel().getColumn(2).setPreferredWidth(30);

		p1.add(l1);
		p1.add(tf1);
		p1.add(l2);
		p1.add(tf2);
		p1.add(l3);
		p1.add(tf3);
		tf3.setEditable(false);
		p1.add(b1);
		p1.add(b5);

		p2.add(b3);
		p2.add(b2);
		p2.add(b4);

		localContainer.add(p1, "North");
		localContainer.add(sp, "Center");
		localContainer.add(p2, "South");

		tf1.addActionListener(this);
		tf2.addActionListener(this);
		b1.addActionListener(this);
		b2.addActionListener(this);
		b3.addActionListener(this);
		b4.addActionListener(this);
		b5.addActionListener(this);
		tb.addMouseListener(this);

		f.setLocation(200, 150);
		f.setSize(860, 400);
		f.setResizable(false);
		f.setVisible(true);
		maxID();
	}

	String maxid = "C01";
	int maxno = 0;
	String initialstr = "C";

	public void maxID() {
		tf3.setText("");
		try {
			con = DBConnectionUtil.getConnection();
			stat = con.createStatement();
			rs = stat.executeQuery("select max(COMPANY_id) from HRCOMPANY_ID");
			while (rs.next()) {
				String str1 = new String(rs.getString(1));
				String str2 = str1.substring(1, str1.length());

				maxno = (Integer.parseInt(str2) + 1);
				maxid = (initialstr + String.valueOf(df.format(maxno)));
				tf3.setText(maxid);
			}
		} catch (Exception localException) {
			System.out.println("Max : " + localException);
		}
	}

	public void create() {
		maxID();
		try {
			con = DBConnectionUtil.getConnection();
			stat = con.createStatement();
			affected = stat.executeUpdate("insert into HRCOMPANY_ID values('" + tf1.getText() + "','"
					+ tf2.getText() + "','0','0','" + maxid + "') ");
			if (affected > 0) {
				model.addRow(new Object[] { tf1.getText(), tf2.getText(), maxid });
				tf1.setText("");
				tf2.setText("");
				tf3.setText("");
			}
		} catch (Exception localException) {
			javax.swing.JOptionPane.showMessageDialog(f, localException.getMessage().toString());
		}
		maxID();
	}

	public void remove() {
		String str = String.valueOf(model.getValueAt(tb.getSelectedRow(), 2));
		try {
			con = DBConnectionUtil.getConnection();
			stat = con.createStatement();
			affected = stat.executeUpdate("delete from HRCOMPANY_ID where COMPANY_ID ='" + str + "'  ");
			if (affected > 0) {
				model.removeRow(tb.getSelectedRow());
			}
		} catch (Exception localException) {
			javax.swing.JOptionPane.showMessageDialog(f, localException.getMessage().toString());
		}
		maxID();
	}

	public void show() {
		model.setNumRows(0);
		try {
			con = DBConnectionUtil.getConnection();
			stat = con.createStatement();
			rs = stat.executeQuery("select * from HRCOMPANY_ID where Br_code ='0' ");
			while (rs.next()) {
				String str1 = new String(rs.getString(1));
				String str2 = new String(rs.getString(2));
				String str3 = new String(rs.getString(5));

				model.addRow(new Object[] { str1, str2, str3 });
			}
		} catch (Exception localException) {
			System.out.println("Show : " + localException);
		}
		maxID();
	}

	public void selectedRow() {
		int i = tb.getSelectedRow();
		String str1 = String.valueOf(model.getValueAt(i, 0));
		String str2 = String.valueOf(model.getValueAt(i, 1));
		String str3 = String.valueOf(model.getValueAt(i, 2));

		tf1.setText(str1);
		tf2.setText(str2);
		tf3.setText(str3);
	}

	public void actionPerformed(ActionEvent paramActionEvent) {
		if ((paramActionEvent.getSource() == b1) || (paramActionEvent.getSource() == tf2)) {
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
		if (paramActionEvent.getSource() == b5) {
			CreateBranch localcreateBranch2 = new CreateBranch();
			localcreateBranch2.px();
		}
	}

	public void mousePressed(MouseEvent paramMouseEvent) {
	}

	public void mouseReleased(MouseEvent paramMouseEvent) {
	}

	public void mouseClicked(MouseEvent paramMouseEvent) {
		selectedRow();
	}

	public void mouseEntered(MouseEvent paramMouseEvent) {
	}

	public void mouseExited(MouseEvent paramMouseEvent) {
	}
}
