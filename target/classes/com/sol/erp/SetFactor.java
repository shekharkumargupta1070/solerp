package com.sol.erp;

import java.awt.Container;
import java.awt.List;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.sol.erp.util.DBConnectionUtil;

public class SetFactor implements java.awt.event.ActionListener {
	

	Connection con;

	Statement stat;
	ResultSet rs;
	JFrame f = new JFrame("Setting Factors");
	JLabel unit = new JLabel("Unit No");
	JLabel empcode = new JLabel("Emp Code");
	JLabel itemcode = new JLabel("Item Code");
	JLabel factor = new JLabel("Factor");

	JTextField unittf = new JTextField("001", 5);
	List emplist = new List(8, false);
	List itemlist = new List(8, true);
	JTextField factortf = new JTextField("60", 5);

	JButton b1 = new JButton("Set Factor");
	JButton b2 = new JButton("Close");

	JPanel centerpanel = new JPanel();
	JPanel butpanel1 = new JPanel();
	JPanel butpanel2 = new JPanel();

	java.awt.Font fo = new java.awt.Font("Arial", 1, 12);
	javax.swing.border.Border line = javax.swing.BorderFactory.createLineBorder(java.awt.Color.darkGray);
	javax.swing.border.Border bor1 = javax.swing.BorderFactory.createTitledBorder(line, "Factor Setting", 0, 0,
			fo, java.awt.Color.darkGray);

	public void px() {
		Container localContainer = f.getContentPane();
		localContainer.setLayout(new java.awt.BorderLayout());
		centerpanel.setLayout(null);

		unit.setBounds(30, 30, 100, 20);
		unittf.setBounds(120, 30, 100, 20);
		empcode.setBounds(30, 55, 100, 20);
		emplist.setBounds(120, 55, 200, 200);
		itemcode.setBounds(335, 55, 100, 20);
		itemlist.setBounds(440, 55, 200, 200);
		factor.setBounds(30, 260, 100, 20);
		factortf.setBounds(120, 260, 100, 20);

		centerpanel.setBorder(bor1);

		emplist.setFont(fo);
		itemlist.setFont(fo);
		unittf.setFont(fo);
		factortf.setFont(fo);

		butpanel1.add(unit);
		butpanel1.add(unittf);
		butpanel1.add(factor);
		butpanel1.add(factortf);

		centerpanel.add(empcode);
		centerpanel.add(emplist);
		centerpanel.add(itemcode);
		centerpanel.add(itemlist);

		butpanel2.add(b1);
		b1.setMnemonic(83);
		butpanel2.add(b2);
		b2.setMnemonic(67);

		unittf.addActionListener(this);
		b1.addActionListener(this);
		b2.addActionListener(this);

		localContainer.add(butpanel1, "North");
		localContainer.add(emplist, "Center");
		localContainer.add(itemlist, "East");
		localContainer.add(butpanel2, "South");

		f.setLocation(75, 100);
		f.setSize(800, 500);
		f.setVisible(true);
		f.setResizable(false);
	}

	@SuppressWarnings("deprecation")
	public void empList() {
		emplist.clear();
		try {
			con = DBConnectionUtil.getConnection();
			stat = con.createStatement();
			rs = stat.executeQuery("select distinct(emp_Code) from emp_Register ");

			while (rs.next()) {
				String str = new String(rs.getString(1));
				emplist.addItem(str);
			}
		} catch (Exception localException) {
			System.out.println(localException);
		}
	}

	@SuppressWarnings("deprecation")
	public void itemList() {
		try {
			con = DBConnectionUtil.getConnection();
			stat = con.createStatement();
			rs = stat.executeQuery("select item_Code from ITEMCODE ");

			while (rs.next()) {
				String str = new String(rs.getString(1));
				itemlist.addItem(str);
			}
		} catch (Exception localException) {
			System.out.println(localException);
		}
	}

	public void actionPerformed(ActionEvent paramActionEvent) {
		if (paramActionEvent.getSource() == unittf) {
			empList();
		}

		if (paramActionEvent.getSource() == b1) {
			String[] arrayOfString = itemlist.getSelectedItems();

			for (int i = 0; i < arrayOfString.length; i++) {
				try {
					stat = con.createStatement();
					stat.executeUpdate("insert into emp_status values('" + emplist.getSelectedItem() + "','"
							+ factortf.getText() + "','0','0','8','" + unittf.getText() + "','"
							+ arrayOfString[i] + "','Detailier','detailing') ");
				} catch (Exception localException) {
					if (localException.getMessage().toString().equalsIgnoreCase("No ResultSet was Produced")) {
						javax.swing.JOptionPane.showMessageDialog(f,
								"Factor Set Completed of " + arrayOfString[i]);
					} else {
						javax.swing.JOptionPane.showMessageDialog(f, localException.getMessage().toString());
					}
				}
			}
		}

		if (paramActionEvent.getSource() == b2) {
			f.setVisible(false);
		}
	}
}
