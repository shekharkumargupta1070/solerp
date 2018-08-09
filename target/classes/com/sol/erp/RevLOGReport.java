package com.sol.erp;

import java.awt.Container;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;

import com.sol.erp.ui.custom.SolTableModel;
import com.sol.erp.util.DBConnectionUtil;
import com.sol.erp.util.formater.SolDateFormatter;

public class RevLOGReport
		implements java.awt.event.ActionListener, java.awt.event.ItemListener, java.awt.event.MouseListener {
	
	Connection con;

	PreparedStatement stat;
	ResultSet rs;
	String[] columnNames = { "Delta No", "Project No", "Extra Hrs", "Date" };
	Object[][] data = new Object[0][];

	java.text.DecimalFormat df = new java.text.DecimalFormat("00.00");
	java.text.DecimalFormat df3 = new java.text.DecimalFormat("000");

	DefaultTableModel model = new DefaultTableModel(data, columnNames);
	javax.swing.JTable tb = new javax.swing.JTable(model);
	javax.swing.JScrollPane sp = new javax.swing.JScrollPane(tb);

	JInternalFrame f = new JInternalFrame("Revision Log Report", true, true, true, true);

	javax.swing.JLabel l1 = new javax.swing.JLabel("Project List");
	JComboBox cb = new JComboBox();

	JTextArea area = new JTextArea();
	javax.swing.JScrollPane sp1 = new javax.swing.JScrollPane(area);

	javax.swing.JSplitPane spl1 = new javax.swing.JSplitPane(1, sp, sp1);

	JButton b1 = new JButton("Reload");
	JButton b2 = new JButton("Close", new javax.swing.ImageIcon("image/close.gif"));

	JPanel centerpanel = new JPanel();
	JPanel butpanel = new JPanel();
	JPanel butpanel2 = new JPanel();

	java.awt.Font fo = new java.awt.Font("Tahoma", 1, 11);
	javax.swing.border.Border line = javax.swing.BorderFactory.createLineBorder(java.awt.Color.darkGray);
	javax.swing.border.Border bor1 = javax.swing.BorderFactory.createTitledBorder(line, "Search By", 0, 0, fo,
			java.awt.Color.darkGray);

	java.awt.Toolkit tk = java.awt.Toolkit.getDefaultToolkit();
	java.awt.Dimension ss = tk.getScreenSize();
	java.awt.Dimension fs = f.getSize();

	Container c;

	public void px() {
		c = f.getContentPane();
		c.setLayout(new java.awt.BorderLayout());
		centerpanel.setLayout(new java.awt.BorderLayout());

		butpanel2.setLayout(new java.awt.BorderLayout());

		area.setFont(fo);
		area.setEditable(false);

		SolTableModel.decorateTable(tb);

		butpanel.add(l1);
		butpanel.add(cb);
		cb.setEditable(true);

		butpanel2.add(b2, "East");

		c.add(butpanel, "North");
		c.add(spl1, "Center");
		c.add(butpanel2, "South");
		b2.setMnemonic(67);

		sp1.setPreferredSize(new java.awt.Dimension(300, 300));

		b1.addActionListener(this);
		b2.addActionListener(this);
		cb.addItemListener(this);
		tb.addMouseListener(this);

		spl1.setDividerLocation(345);

		f.getRootPane().setDefaultButton(b1);

		f.setSize(824, 550);
		f.setVisible(true);

		projectNo();
	}

	public void projectNo() {
		cb.addItem("Select");
		try {
			con = DBConnectionUtil.getConnection();
			stat = con.prepareStatement("select distinct(project_no) From estimation ");
			rs = stat.executeQuery();
			while (rs.next()) {
				String str = new String(rs.getString(1));
				cb.addItem(str);
			}
		} catch (Exception localException) {
			javax.swing.JOptionPane.showMessageDialog(f, localException.getMessage().toString());
		}
	}

	public void revList() {
		model.setNumRows(0);

		String str1 = String.valueOf(cb.getSelectedItem());

		try {
			stat = con.prepareStatement(
					"select delta_no, PROJECT_NO, extra_hrs, date_month  from Rev_LOG where project_no like ? ");
			stat.setString(1, str1);
			rs = stat.executeQuery();
			rs.getMetaData().getColumnCount();
			while (rs.next()) {
				String str2 = new String(rs.getString(1));
				String str3 = new String(rs.getString(2));
				String str4 = new String(rs.getString(3));
				String str5 = SolDateFormatter.SQLtoDDMMYY(rs.getDate(4));

				model.addRow(new Object[] { str2, str3, str4, str5 });
			}
		} catch (Exception localException) {
			System.out.println(localException);
		}
	}

	public void descList() {
		area.setText("");
		int i = tb.getSelectedRow();

		String str1 = String.valueOf(model.getValueAt(i, 1));
		String str2 = String.valueOf(model.getValueAt(i, 0));

		try {
			stat = con.prepareStatement("select DESC1 from Rev_LOG WHERE project_no like ? and delta_no like ? ");
			stat.setString(1, str1);
			stat.setString(2, str2);
			rs = stat.executeQuery();
			rs.getMetaData().getColumnCount();
			while (rs.next()) {
				String str3 = new String(rs.getString(1));
				area.append("\t\t\tDESCRIPTION\n");
				area.append(
						"--------------------------------------------------------------------------------------------------------------------------------------------\n");
				area.append(str3 + "\n");
				area.append(
						"--------------------------------------------------------------------------------------------------------------------------------------------\n");
			}
		} catch (Exception localException) {
			System.out.println(localException);
		}
	}

	public void affectedItem() {
		int i = tb.getSelectedRow();

		String str1 = String.valueOf(model.getValueAt(i, 0));
		String str2 = String.valueOf(model.getValueAt(i, 1));

		area.append("\tItem Code\tDrg No\n");
		area.append(
				"--------------------------------------------------------------------------------------------------------------------------------------------\n");

		try {
			stat = con.prepareStatement(
					"select item_code, drg_no from delta WHERE project_no like ? and delta_no like ? ");
			stat.setString(1, str2);
			stat.setString(2, str1);
			rs = stat.executeQuery();
			rs.getMetaData().getColumnCount();
			while (rs.next()) {
				String str3 = new String(rs.getString(1));
				String str4 = new String(rs.getString(2));
				area.append("\t" + str3 + "\t" + str4 + "\n");
			}
		} catch (Exception localException) {
			System.out.println(localException);
		}
	}

	public void actionPerformed(java.awt.event.ActionEvent paramActionEvent) {
		if (paramActionEvent.getSource() == b1) {
			revList();
		}
		if (paramActionEvent.getSource() == b2) {
			f.setVisible(false);
		}
	}

	public void itemStateChanged(java.awt.event.ItemEvent paramItemEvent) {
		if (paramItemEvent.getSource() == cb) {
			revList();
		}
	}

	public void mousePressed(MouseEvent paramMouseEvent) {
		descList();
		affectedItem();
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
