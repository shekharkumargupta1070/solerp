package com.sol.erp;

import java.awt.event.MouseEvent;
import java.sql.ResultSet;

import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.sol.erp.ui.custom.SolTableModel;
import com.sol.erp.util.DBConnectionUtil;

public class RFILOGReport
		implements java.awt.event.ActionListener, java.awt.event.ItemListener, java.awt.event.MouseListener {
	

	java.sql.Connection con;

	java.sql.Statement stat;
	ResultSet rs;
	String[] columnNames = { "SOL#", "CLIENT#", "Submit Date", "Return Date", "Extra Hrs", "Img" };
	Object[][] data = new Object[0][];

	String[] columnNames1 = { "Item Code", "Drg No", "DTL/CHK" };
	Object[][] data1 = new Object[0][];

	java.text.DecimalFormat df = new java.text.DecimalFormat("00.00");
	java.text.DecimalFormat df3 = new java.text.DecimalFormat("000");

	DefaultTableModel model = new DefaultTableModel(data, columnNames);
	JTable tb = new JTable(model);
	javax.swing.JScrollPane sp = new javax.swing.JScrollPane(tb);

	DefaultTableModel model1 = new DefaultTableModel(data1, columnNames1);
	JTable tb1 = new JTable(model1);
	javax.swing.JScrollPane sp1 = new javax.swing.JScrollPane(tb1);

	JInternalFrame f = new JInternalFrame("RFI Log Report", true, true, true, true);

	JLabel l1 = new JLabel("Order By");
	JLabel l2 = new JLabel("Search By");

	JPanel imagepanel = new JPanel();
	javax.swing.ImageIcon ic = new javax.swing.ImageIcon("image/close.gif");
	JLabel imagelabel = new JLabel("", ic, 2);
	JPanel p1 = new JPanel();

	JComboBox cb = new JComboBox();
	JComboBox searchcb = new JComboBox();
	JComboBox optioncb = new JComboBox();

	JTextArea area = new JTextArea();
	javax.swing.JScrollPane sp2 = new javax.swing.JScrollPane(area);

	JSplitPane spl = new JSplitPane(1, sp, sp2);
	JSplitPane spl1 = new JSplitPane(1, sp1, imagepanel);
	JSplitPane spl2 = new JSplitPane(0, spl, spl1);

	javax.swing.JButton b1 = new javax.swing.JButton("Reload");
	javax.swing.JButton b2 = new javax.swing.JButton("Close", new javax.swing.ImageIcon("image/close.gif"));

	JLabel sl1 = new JLabel("Total RFI");
	JLabel sl2 = new JLabel("Pending RFI");
	JLabel sl3 = new JLabel("Answered RFI");

	JTextField stf1 = new JTextField(4);
	JTextField stf2 = new JTextField(4);
	JTextField stf3 = new JTextField(4);

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

	java.awt.Container c;

	public void px() {
		c = f.getContentPane();
		c.setLayout(new java.awt.BorderLayout());
		centerpanel.setLayout(new java.awt.BorderLayout());
		imagepanel.setLayout(new java.awt.BorderLayout());

		imagepanel.add(imagelabel, "Center");

		imagelabel.setHorizontalAlignment(0);

		optioncb.setEditable(true);

		cb.addItem("SOL_NO");
		cb.addItem("CLIENT_No");
		cb.addItem("Project_No");
		cb.addItem("SUBMIT_DATE");
		cb.addItem("RETURN_DATE");
		cb.addItem("AFFEC_ITEM");
		cb.addItem("AFFEC_DRG");

		searchcb.addItem("Project_No");
		searchcb.addItem("Client_Name");

		SolTableModel.decorateTable(tb);
		SolTableModel.decorateTable(tb1);

		optioncb.addItem("%");
		area.setEditable(false);

		area.setFont(fo);
		stf1.setFont(fo);
		stf2.setFont(fo);
		stf3.setFont(fo);

		butpanel.add(searchcb);
		butpanel.add(optioncb);
		butpanel.add(l1);
		butpanel.add(cb);

		butpanel.add(b1);
		b1.setMnemonic(82);

		butpanel2.add(sl1);
		butpanel2.add(stf1);
		stf1.setEditable(false);
		butpanel2.add(sl2);
		butpanel2.add(stf2);
		stf2.setEditable(false);
		butpanel2.add(sl3);
		butpanel2.add(stf3);
		stf3.setEditable(false);
		butpanel2.add(b2);
		b2.setMnemonic(67);

		c.add(butpanel, "North");
		c.add(spl2, "Center");
		c.add(butpanel2, "South");

		spl.setOneTouchExpandable(true);
		spl1.setOneTouchExpandable(true);
		spl2.setOneTouchExpandable(true);

		spl.setDividerLocation(460);
		spl1.setDividerLocation(250);
		spl2.setDividerLocation(200);

		b1.addActionListener(this);
		b2.addActionListener(this);

		cb.addItemListener(this);
		cb.addActionListener(this);

		tb.addMouseListener(this);
		tb1.addMouseListener(this);
		optioncb.addItemListener(this);
		searchcb.addItemListener(this);

		f.setLocation((ss.width - fs.width) / 15, (ss.height - fs.height) / 40);
		f.setTitle("RFI Report");
		f.setSize(900, 600);
		f.setVisible(true);
		optionWise();
	}

	public void optionWise() {
		optioncb.removeAllItems();
		try {
			con = DBConnectionUtil.getConnection();
			stat = con.createStatement();
			rs = stat.executeQuery("select distinct " + searchcb.getSelectedItem() + " from ESTIMATION ");
			rs.getMetaData().getColumnCount();
			while (rs.next()) {
				String str = new String(rs.getString(1));
				optioncb.addItem(str);
			}
		} catch (Exception localException) {
			System.out.println(localException);
		}
	}

	public void rfiList() {
		returnCount();
		model.setNumRows(0);
		try {
			con = DBConnectionUtil.getConnection();
			stat = con.createStatement();
			rs = stat.executeQuery(
					"select sol_no,client_no, submit_date, return_date, extra_hrs, IMG_NO from RFI_LOG where "
							+ searchcb.getSelectedItem() + " like '" + optioncb.getSelectedItem() + "' ORDER BY "
							+ cb.getSelectedItem() + " ");
			rs.getMetaData().getColumnCount();
			while (rs.next()) {
				String str1 = new String(rs.getString(1));
				String str2 = new String(rs.getString(2));
				String str3 = new String(rs.getString(3));
				String str4 = new String(rs.getString(4));
				String str5 = new String(rs.getString(5));
				String str6 = new String(rs.getString(6));

				model.addRow(new Object[] { str1, str2, str3, str4, str5, str6 });
			}
		} catch (Exception localException) {
			System.out.println(localException);
		}
	}

	public void returnCount() {
		stf1.setText("0");
		stf2.setText("0");
		stf3.setText("0");

		try {
			stat = con.createStatement();
			rs = stat.executeQuery("select (select count(project_no) from RFI_LOG WHERE " + searchcb.getSelectedItem()
					+ " like '" + optioncb.getSelectedItem()
					+ "'),(select count(project_no) from RFI_LOG WHERE RETURN_DATE ='0'AND "
					+ searchcb.getSelectedItem() + " like '" + optioncb.getSelectedItem()
					+ "' ),(select count(project_no) from RFI_LOG WHERE RETURN_DATE NOT LIKE '0' AND "
					+ searchcb.getSelectedItem() + " like '" + optioncb.getSelectedItem() + "') from RFI_LOG  ");
			rs.getMetaData().getColumnCount();
			while (rs.next()) {
				String str1 = new String(rs.getString(1));
				String str2 = new String(rs.getString(2));
				String str3 = new String(rs.getString(3));

				stf1.setText(str1);
				stf2.setText(str2);
				stf3.setText(str3);
			}
		} catch (Exception localException) {
			System.out.println(localException);
		}
	}

	public void answerList() {
		area.setText("");
		int i = tb.getSelectedRow();

		String str1 = String.valueOf(model.getValueAt(i, 0));
		String str2 = String.valueOf(optioncb.getSelectedItem());
		try {
			con = DBConnectionUtil.getConnection();
			stat = con.createStatement();
			rs = stat.executeQuery(
					"select question,answer from RFI_LOG WHERE SOL_NO='" + str1 + "' and project_no= '" + str2 + "' ");
			rs.getMetaData().getColumnCount();
			while (rs.next()) {
				String str3 = new String(rs.getString(1));
				String str4 = new String(rs.getString(2));

				area.append("\t\tQuestion\n");
				area.append(
						"-------------------------------------------------------------------------------------------------------\n");
				area.append(str3 + "\n");
				area.append(
						"-------------------------------------------------------------------------------------------------------\n");
				area.append("\t\tAnswer\n");
				area.append(
						"-------------------------------------------------------------------------------------------------------\n\n\n");
				area.append(str4 + "\n\n\n");
				area.append(
						"-------------------------------------------------------------------------------------------------------\n");
			}
		} catch (Exception localException) {
			System.out.println(localException);
		}
	}

	public void affectedItem() {
		int i = tb.getSelectedRow();

		String str1 = String.valueOf(model.getValueAt(i, 0));
		String str2 = (String) optioncb.getSelectedItem();
		model1.setNumRows(0);
		try {
			con = DBConnectionUtil.getConnection();
			stat = con.createStatement();
			rs = stat.executeQuery(
					"select a.item_code, a.drg_no, d.detailing, d.checking from affectedItem a, drawingno d WHERE a.SOL_NO='"
							+ str1 + "' and a.project_no ='" + str2
							+ "' and a.drg_no=d.drawing_no AND A.ITEM_CODE=D.ITEM_CODE and a.project_no = d.project_no ");
			rs.getMetaData().getColumnCount();
			while (rs.next()) {
				String str3 = new String(rs.getString(1));
				String str4 = new String(rs.getString(2));
				String str5 = new String(rs.getString(3));
				String str6 = new String(rs.getString(4));
				model1.addRow(new Object[] { str3, str4, str5 + "/" + str6 });
			}
		} catch (Exception localException) {
			System.out.println(localException);
		}
	}

	public void actionPerformed(java.awt.event.ActionEvent paramActionEvent) {
		if (paramActionEvent.getSource() == b1) {
			rfiList();
		}
		if (paramActionEvent.getSource() == cb) {
			rfiList();
		}
		if (paramActionEvent.getSource() == b2) {
			f.setVisible(false);
		}
	}

	public void itemStateChanged(java.awt.event.ItemEvent paramItemEvent) {
		if (paramItemEvent.getSource() == cb) {
			rfiList();
		}
		if (paramItemEvent.getSource() == searchcb) {
			optionWise();
		}
		if (paramItemEvent.getSource() == optioncb) {
			rfiList();
		}
	}

	public void mousePressed(MouseEvent paramMouseEvent) {
		if (paramMouseEvent.getSource() == tb) {
			answerList();
			affectedItem();

			int i = tb.getSelectedRow();
			String str = "image/" + optioncb.getSelectedItem() + "/" + String.valueOf(model.getValueAt(i, 5)) + ".tiff";
			System.out.println(str);

			imagelabel.setIcon(new javax.swing.ImageIcon(str));
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
