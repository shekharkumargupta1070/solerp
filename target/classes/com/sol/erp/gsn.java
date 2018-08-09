package com.sol.erp;

import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;

import com.sol.erp.ui.custom.SolTableModel;
import com.sol.erp.util.DBConnectionUtil;

public class gsn implements java.awt.event.ActionListener, java.awt.event.MouseListener, java.awt.event.ItemListener {
	

	Connection con;
	Statement stat;
	ResultSet rs;
	String[] columnNames = { "Item Name", "Description" };
	Object[][] data = new Object[0][];

	javax.swing.JInternalFrame f = new javax.swing.JInternalFrame("SOL GSN", true, true, true, true);
	JLabel l1 = new JLabel("Project No.");
	JLabel l2 = new JLabel("Item Name");
	JLabel l3 = new JLabel("Description");

	JComboBox tf1 = new JComboBox();
	JComboBox tf2 = new JComboBox();
	JTextArea tf3 = new JTextArea();
	javax.swing.JScrollPane tfsp = new javax.swing.JScrollPane(tf3);

	JButton b1 = new JButton("Add GSN");
	JButton b2 = new JButton("Update");
	JButton b3 = new JButton("Delete");
	JButton b4 = new JButton("Show All");
	JButton b5 = new JButton("Close");
	javax.swing.ButtonGroup grp = new javax.swing.ButtonGroup();

	DefaultTableModel model = new DefaultTableModel(data, columnNames);
	javax.swing.JTable tb = new javax.swing.JTable(model);
	javax.swing.JScrollPane sp = new javax.swing.JScrollPane(tb);

	JPanel p1 = new JPanel();
	JPanel p2 = new JPanel();

	JPanel buttonpanel = new JPanel();
	JPanel optionpanel = new JPanel();
	JPanel southpanel = new JPanel();

	JRadioButton rb1 = new JRadioButton("GSN", true);
	JRadioButton rb2 = new JRadioButton("Foundation Notes");
	JRadioButton rb3 = new JRadioButton("Structure Notes");

	javax.swing.border.Border bor1 = javax.swing.BorderFactory.createTitledBorder("Control Option");
	javax.swing.border.Border bor2 = javax.swing.BorderFactory.createTitledBorder("Project Standards Notes");

	java.awt.Font fo = new java.awt.Font("Tahoma", 1, 11);

	java.awt.Toolkit tk = java.awt.Toolkit.getDefaultToolkit();
	java.awt.Dimension fs = f.getSize();
	java.awt.Dimension ss = tk.getScreenSize();


	javax.swing.table.TableColumn col1 = tb.getColumnModel().getColumn(0);
	javax.swing.table.TableColumn col2 = tb.getColumnModel().getColumn(1);

	public void px() {
		java.awt.Container localContainer = f.getContentPane();
		localContainer.setLayout(new java.awt.BorderLayout());
		p1.setLayout(null);
		p2.setLayout(new java.awt.BorderLayout());
		optionpanel.setLayout(new java.awt.GridLayout(1, 3, 2, 2));

		l1.setBounds(20, 20, 100, 20);
		tf1.setBounds(150, 20, 150, 20);
		l2.setBounds(20, 45, 100, 20);
		tf2.setBounds(150, 45, 150, 20);
		l3.setBounds(20, 70, 100, 20);
		tfsp.setBounds(150, 70, 700, 120);
		optionpanel.setBounds(320, 20, 530, 45);

		l1.setForeground(java.awt.Color.white);
		l2.setForeground(java.awt.Color.white);
		l3.setForeground(java.awt.Color.white);

		p1.setBackground(new java.awt.Color(60, 130, 130));
		p2.setBackground(new java.awt.Color(60, 130, 130));

		buttonpanel.setBackground(new java.awt.Color(60, 130, 130));
		southpanel.setBackground(new java.awt.Color(60, 130, 130));

		tf1.setEditable(true);
		tf1.setFont(fo);
		tf2.setFont(fo);
		tf3.setFont(fo);

		SolTableModel.decorateTable(tb);

		col1.setPreferredWidth(200);
		col2.setPreferredWidth(680);

		p1.add(l1);
		p1.add(tf1);
		p1.add(l2);
		p1.add(tf2);
		p1.add(l3);
		p1.add(tfsp);

		optionpanel.add(rb1);
		optionpanel.add(rb2);
		optionpanel.add(rb3);

		grp.add(rb1);
		rb1.addActionListener(this);
		grp.add(rb2);
		rb2.addActionListener(this);
		grp.add(rb3);
		rb3.addActionListener(this);

		p1.add(optionpanel);

		buttonpanel.add(b1);
		b1.setMnemonic(65);
		buttonpanel.add(b2);
		b2.setMnemonic(85);

		southpanel.add(b3);
		b3.setMnemonic(68);
		southpanel.add(b5);
		b5.setMnemonic(67);

		b1.addActionListener(this);
		b2.addActionListener(this);
		b3.addActionListener(this);
		b5.addActionListener(this);

		tb.addMouseListener(this);

		tf1.addItemListener(this);
		tf2.addItemListener(this);

		p2.add(buttonpanel, "North");
		p2.add(sp, "Center");

		p1.setPreferredSize(new java.awt.Dimension(190, 190));

		localContainer.add(p1, "North");
		localContainer.add(p2, "Center");
		localContainer.add(southpanel, "South");

		buttonpanel.setBorder(bor1);
		optionpanel.setBorder(bor2);

		f.setSize(900, 600);

		f.setVisible(true);
		projectNo();
	}

	public void paramUser() {
		String str = TechMainFrame.textFieldPost.getText();
		if ((str.equalsIgnoreCase("Detailer")) || (str.equalsIgnoreCase("Checker"))) {
			tf1.setEditable(false);
			tf2.setEditable(false);
			tf3.setEditable(false);
			b1.setEnabled(false);
			b2.setEnabled(false);
			b3.setEnabled(false);
			b4.setEnabled(false);
		}
	}

	public void projectNo() {
		tf1.removeAllItems();
		try {
			con = DBConnectionUtil.getConnection();
			stat = con.createStatement();
			rs = stat.executeQuery("select distinct(project_no) From estimation");
			while (rs.next()) {
				String str = new String(rs.getString(1));
				tf1.addItem(str);
			}
		} catch (Exception localException) {
			javax.swing.JOptionPane.showMessageDialog(f, localException.getMessage().toString());
		}
	}

	public void itemName() {
		tf2.removeAllItems();
		try {
			con = DBConnectionUtil.getConnection();
			stat = con.createStatement();
			rs = stat.executeQuery("select distinct(ITEM_name) From estimation where project_no='"
					+ tf1.getSelectedItem() + "' ");
			while (rs.next()) {
				String str = new String(rs.getString(1));
				tf2.addItem(str);
			}
		} catch (Exception localException) {
			javax.swing.JOptionPane.showMessageDialog(f, localException.getMessage().toString());
		}
	}

	public void showRecord() {
		String str1 = "gsn";

		if (rb1.isSelected()) {
			str1 = "gsn";
		}
		if (rb2.isSelected()) {
			str1 = "f_notes";
		}
		if (rb3.isSelected()) {
			str1 = "s_notes";
		}

		try {
			con = DBConnectionUtil.getConnection();
			stat = con.createStatement();
			rs = stat
					.executeQuery("select * from " + str1 + " where project_no='" + tf1.getSelectedItem() + "' ");
			rs.getMetaData().getColumnCount();
			while (rs.next()) {
				String str2 = new String(rs.getString(2));
				String str3 = new String(rs.getString(3));
				model.addRow(new Object[] { str2, str3 });
			}
		} catch (Exception localException) {
		}
	}

	public void showSelectedRecord() {
		int i = tb.getSelectedRow();
		tf2.setSelectedItem(String.valueOf(model.getValueAt(i, 0)));
		tf3.setText(String.valueOf(model.getValueAt(i, 1)));
	}

	public void actionPerformed(ActionEvent paramActionEvent) {
		if ((paramActionEvent.getSource() == b1) || (paramActionEvent.getSource() == tf3)) {
			if (rb1.isSelected()) {
				try {
					con = DBConnectionUtil.getConnection();
					stat = con.createStatement();
					stat.executeUpdate("Insert into gsn values('" + tf1.getSelectedItem() + "','"
							+ tf2.getSelectedItem() + "','" + tf3.getText() + "') ");
				} catch (Exception localException1) {
				}
				model.addRow(new Object[] { tf2.getSelectedItem(), tf3.getText() });
			}
			if (rb2.isSelected()) {
				try {
					con = DBConnectionUtil.getConnection();
					stat = con.createStatement();
					stat.executeUpdate("Insert into f_notes values('" + tf1.getSelectedItem() + "','"
							+ tf2.getSelectedItem() + "','" + tf3.getText() + "') ");
				} catch (Exception localException2) {
				}
				model.addRow(new Object[] { tf2.getSelectedItem(), tf3.getText() });
			}
			if (rb3.isSelected()) {
				try {
					con = DBConnectionUtil.getConnection();
					stat = con.createStatement();
					stat.executeUpdate("Insert into S_notes values('" + tf1.getSelectedItem() + "','"
							+ tf2.getSelectedItem() + "','" + tf3.getText() + "') ");
				} catch (Exception localException3) {
				}
				model.addRow(new Object[] { "", tf3.getText() });
			}
			tf3.setText("");
			tf2.requestFocus();
		}
		String str;
		if (paramActionEvent.getSource() == b2) {
			str = "gsn";

			if (rb1.isSelected()) {
				str = "gsn";
			}
			if (rb2.isSelected()) {
				str = "f_notes";
			}
			if (rb3.isSelected()) {
				str = "s_notes";
			}

			try {
				con = DBConnectionUtil.getConnection();
				stat = con.createStatement();
				stat.executeUpdate("update " + str + " set Description='" + tf3.getText() + "' where I_name='"
						+ tf2.getSelectedItem() + "' and project_no='" + tf1.getSelectedItem() + "' ");
			} catch (Exception localException4) {
				if (localException4.getMessage().toString().equalsIgnoreCase("No ResultSet was produced")) {
					int j = tb.getSelectedRow();
					model.setValueAt(tf3.getText(), j, 1);
				} else {
					javax.swing.JOptionPane.showMessageDialog(f, localException4.getMessage().toString());
				}
			}
		}

		if (paramActionEvent.getSource() == b3) {
			str = "gsn";

			if (rb1.isSelected()) {
				str = "gsn";
			}
			if (rb2.isSelected()) {
				str = "f_notes";
			}
			if (rb3.isSelected()) {
				str = "s_notes";
			}
			try {
				con = DBConnectionUtil.getConnection();
				stat = con.createStatement();
				stat.executeUpdate("delete from " + str + " where I_name ='" + tf2.getSelectedItem() + "' ");
			} catch (Exception localException5) {
			}
			int i = tb.getSelectedRow();
			model.removeRow(i);
			f.setTitle("Description of " + tf2.getSelectedItem() + " of " + tf1.getSelectedItem()
					+ " is deleted.");

			tf3.setText("");
			tf1.requestFocus();
		}
		if ((paramActionEvent.getSource() != b4) ||

		(paramActionEvent.getSource() == b5)) {
			f.setVisible(false);
		}

		if (paramActionEvent.getSource() == rb1) {
			model.setNumRows(0);
			showRecord();
			itemName();
			tf3.requestFocus();
		}
		if (paramActionEvent.getSource() == rb2) {
			model.setNumRows(0);
			showRecord();
			itemName();
			tf3.requestFocus();
		}
		if (paramActionEvent.getSource() == rb3) {
			model.setNumRows(0);
			showRecord();
			tf2.removeAllItems();
			tf3.requestFocus();
		}
	}

	public void itemStateChanged(java.awt.event.ItemEvent paramItemEvent) {
		if (paramItemEvent.getSource() == tf1) {
			itemName();
		}
	}

	public void mousePressed(MouseEvent paramMouseEvent) {
		showSelectedRecord();
	}

	public void mouseReleased(MouseEvent paramMouseEvent) {
	}

	public void mouseClicked(MouseEvent paramMouseEvent) {
	}

	public void mouseExited(MouseEvent paramMouseEvent) {
	}

	public void mouseEntered(MouseEvent paramMouseEvent) {
	}
}
