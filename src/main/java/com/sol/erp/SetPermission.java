package com.sol.erp;

import java.awt.Dimension;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.sol.erp.util.DBConnectionUtil;

public class SetPermission implements java.awt.event.ActionListener, java.awt.event.KeyListener,
		javax.swing.event.ListSelectionListener, java.awt.event.FocusListener {
	

	java.sql.Connection con;
	java.sql.Statement stat;
	java.sql.ResultSet rs;
	JFrame f = new JFrame("Authentication System");

	JLabel l1 = new JLabel("Emp Code");
	JLabel l2 = new JLabel("Password");
	JLabel l3 = new JLabel("User Type");
	JLabel l4 = new JLabel("Dept");
	JLabel l5 = new JLabel("Designation");
	JLabel l6 = new JLabel("Unit No");

	JTextField tf1 = new JTextField(15);
	JTextField tf2 = new JTextField("Default");
	JComboBox tf3 = new JComboBox();
	JComboBox tf4 = new JComboBox();
	JComboBox tf5 = new JComboBox();
	JTextField tf6 = new JTextField();

	javax.swing.DefaultListModel model1 = new javax.swing.DefaultListModel();
	JList nameList = new JList(model1);
	javax.swing.JScrollPane listScroll = new javax.swing.JScrollPane(nameList);

	JButton b1 = new JButton("Save ");
	JButton b2 = new JButton("Close");

	JPanel p1 = new JPanel();
	JPanel p2 = new JPanel();

	javax.swing.border.Border bor1 = javax.swing.BorderFactory.createTitledBorder("Set Password");

	java.awt.Toolkit tk = java.awt.Toolkit.getDefaultToolkit();
	java.awt.Image ic = tk.getImage("/main.jpg");

	Dimension ss = tk.getScreenSize();
	Dimension fs = f.getSize();
	java.awt.Container c;

	public void px() {
		c = f.getContentPane();
		c.setLayout(new java.awt.BorderLayout());
		p1.setLayout(new java.awt.GridLayout(7, 2));
		p2.setLayout(new java.awt.BorderLayout());

		tf3.addItem("User");
		tf3.addItem("Admin");

		tf4.addItem("Tech");
		tf4.addItem("HR");
		tf4.addItem("Finance");
		tf4.addItem("Admin");

		tf5.addItem("Detailer");
		tf5.addItem("Checker");
		tf5.addItem("Project Manager");

		p1.add(l1);
		p1.add(tf1);
		p1.add(l2);
		p1.add(tf2);
		tf2.setEditable(true);
		p1.add(l3);
		p1.add(tf3);
		p1.add(l4);
		p1.add(tf4);
		p1.add(l5);
		p1.add(tf5);
		p1.add(l6);
		p1.add(tf6);
		p1.add(b1);
		b1.setMnemonic('S');
		b1.addActionListener(this);
		p1.add(b2);
		b2.setMnemonic('C');
		b2.addActionListener(this);

		nameList.addListSelectionListener(this);
		nameList.addFocusListener(this);
		tf1.addKeyListener(this);
		tf1.addFocusListener(this);
		tf1.addActionListener(this);
		tf4.addActionListener(this);
		tf2.addKeyListener(this);
		tf3.addKeyListener(this);
		b1.addKeyListener(this);
		b2.addKeyListener(this);
		p1.setBorder(bor1);
		c.add(p1, "Center");

		f.setLocation((ss.width - fs.width) / 4, (ss.height - fs.height) / 5);

		f.setSize(360, 360);
		f.setResizable(false);
		f.setVisible(true);
	}

	public void focusGained(java.awt.event.FocusEvent paramFocusEvent) {
		if (paramFocusEvent.getSource() == tf1) {
			p2.add(listScroll, "South");
			c.add(p2, "North");
			p2.revalidate();
		}
	}

	public void focusLost(java.awt.event.FocusEvent paramFocusEvent) {
		if (paramFocusEvent.getSource() == nameList) {
			tf2.requestFocus();
		}
	}

	public void valueChanged(javax.swing.event.ListSelectionEvent paramListSelectionEvent) {
		if (paramListSelectionEvent.getSource() == nameList) {
			String str = (String) nameList.getSelectedValue();
			tf1.setText(str);
		}
	}

	public void keyPressed(KeyEvent paramKeyEvent) {
		int i = paramKeyEvent.getKeyCode();
		if (i == 27) {
			f.setVisible(false);
		}
	}

	public void keyReleased(KeyEvent paramKeyEvent) {
		if (paramKeyEvent.getSource() == tf1) {
			model1.clear();
			String str1 = tf1.getText() + "%";
			try {
				con = DBConnectionUtil.getConnection();
				stat = con.createStatement();
				rs = stat.executeQuery("select emp_code from emp_status where emp_code like '" + str1 + "' ");
				while (rs.next()) {
					String str2 = new String(rs.getString(1));
					model1.addElement(str2);
				}
			} catch (Exception localException) {
				System.out.println(localException);
			}
		}
	}

	public void keyTyped(KeyEvent paramKeyEvent) {
	}

	public void actionPerformed(java.awt.event.ActionEvent paramActionEvent) {
		if (paramActionEvent.getSource() == b2) {
			f.setVisible(false);
		}
		if (paramActionEvent.getSource() == tf1) {
			nameList.requestFocus();
			nameList.setSelectedIndex(0);
		}
		if (paramActionEvent.getSource() == b1) {
			if (tf1.getText() == null) {
				javax.swing.JOptionPane.showMessageDialog(f, "Emp Code Required.");
			} else {
				try {
					con = DBConnectionUtil.getConnection();
					stat = con.createStatement();
					stat.executeUpdate(
							"insert into password values('" + tf1.getText() + "','" + tf2.getText() + "','"
									+ tf3.getSelectedItem() + "','" + tf4.getSelectedItem() + "','"
									+ tf5.getSelectedItem() + "','" + tf6.getText() + "')");
				} catch (Exception localException) {
					String str = "No ResultSet was produced";
					if (localException.getMessage().toString().equalsIgnoreCase(str)) {
						javax.swing.JOptionPane.showMessageDialog(f,
								"Authentication Defined for " + tf1.getText() + " As "
										+ tf3.getSelectedItem().toString() + " for Unit No " + tf6.getText());
						tf1.setText("");
						tf6.setText("");
						tf1.requestFocus();
					} else {
						javax.swing.JOptionPane.showMessageDialog(f, localException.getMessage().toString());
					}
				}
			}
		}
	}

}
