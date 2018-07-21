package com.sol.erp;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DecimalFormat;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.sol.erp.ui.custom.SolCellModel;
import com.sol.erp.ui.custom.SolTableModel;
import com.sol.erp.util.DBConnectionUtil;

public class ProjectStatus implements java.awt.event.ActionListener, java.awt.event.MouseListener,
		javax.swing.event.ListSelectionListener, java.awt.event.ItemListener {
	

	java.sql.Connection con;
	Statement stat;
	ResultSet rs;
	String prjstr = "0";
	String itemstr = "%";
	String clientstr = "%";

	String[] columnNames = { "Project No", "Name", "Total DRG", "CMPL", "RFI ANS", "RFI PND", "EXT Hrs", "Rdy FAB",
			"BFA Rec'd", "IN APP" };
	Object[][] data = new Object[0][];

	String[] columnNames1 = { "Item Code", "Item Name", "Total DRG", "CMPL", "RFI ANS", "RFI PND", "EXT Hrs", "Rdy FAB",
			"BFA Rec'd", "IN APP" };
	Object[][] data1 = new Object[0][];

	DecimalFormat df = new DecimalFormat("00.00");
	DecimalFormat df1 = new DecimalFormat("0");

	int v = 20;
	int h = 32;

	DefaultTableModel model = new DefaultTableModel(data, columnNames);
	JTable tb = new JTable(model);
	javax.swing.JScrollPane sp = new javax.swing.JScrollPane(tb);

	DefaultTableModel model1 = new DefaultTableModel(data1, columnNames1);
	JTable tb1 = new JTable(model1);
	javax.swing.JScrollPane sp1 = new javax.swing.JScrollPane(tb1);

	javax.swing.JSplitPane spl1 = new javax.swing.JSplitPane(0, sp, sp1);

	javax.swing.JInternalFrame f = new javax.swing.JInternalFrame(
			"Project Report [RFI(Answered/Pending), ExtraHrs, FAB, BFA]", true, true, true, true);
	JLabel l1 = new JLabel("Display Option");
	JLabel l2 = new JLabel("Client List");

	JLabel topimagelabel = new JLabel(new javax.swing.ImageIcon("image/projectreport.gif"), 2);
	JLabel statuslabel = new JLabel("Status");

	JComboBox cb1 = new JComboBox();
	JComboBox cb2 = new JComboBox();

	JButton showbut = new JButton("Show Report");
	JButton closebut = new JButton("Close", new javax.swing.ImageIcon("image/close.gif"));

	JPanel northp1 = new JPanel();
	JPanel southp1 = new JPanel();

	java.awt.Font fo = new java.awt.Font("Tahoma", 1, 11);
	javax.swing.border.Border line = javax.swing.BorderFactory.createLineBorder(Color.darkGray);
	javax.swing.border.Border bor1 = javax.swing.BorderFactory.createTitledBorder(line, "Report Maker", 0, 0, fo,
			Color.darkGray);
	javax.swing.border.Border bor2 = javax.swing.BorderFactory.createTitledBorder(line, "Summarized", 0, 0, fo,
			Color.darkGray);
	javax.swing.border.Border bor3 = javax.swing.BorderFactory.createTitledBorder(line, "Details", 0, 0, fo,
			Color.darkGray);

	java.awt.Toolkit tk = java.awt.Toolkit.getDefaultToolkit();
	java.awt.Dimension ss = tk.getScreenSize();
	java.awt.Dimension fs = f.getSize();

	class ColoredTableCellRenderer1 extends javax.swing.table.DefaultTableCellRenderer {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		ColoredTableCellRenderer1() {
		}

		public java.awt.Component getTableCellRendererComponent(JTable paramJTable, Object paramObject,
				boolean paramBoolean1, boolean paramBoolean2, int paramInt1, int paramInt2) {
			setEnabled((paramJTable == null) || (paramJTable.isEnabled()));

			if (paramInt2 > 1) {
				setHorizontalAlignment(0);
			}

			if ((paramObject == null) || (String.valueOf(paramObject).equalsIgnoreCase("0"))) {

				if (paramInt1 % 2 == 0) {
					setBackground(new Color(240, 240, 240));
					setForeground(Color.darkGray);

				} else {
					setBackground(new Color(230, 230, 230));
					setForeground(Color.darkGray);
				}
			} else {
				if (paramInt2 == 4) {
					setBackground(new Color(120, 150, 190));
					setForeground(Color.white);
				}
				if (paramInt2 == 5) {
					setBackground(new Color(240, 120, 160));
					setForeground(Color.white);
				}
				if (paramInt2 == 6) {
					setBackground(Color.red);
					setForeground(Color.white);
				}
				if (paramInt2 == 7) {
					setBackground(new Color(120, 240, 160));
					setForeground(Color.white);
				}
				if (paramInt2 == 8) {
					setBackground(new Color(250, 30, 160));
					setForeground(Color.white);
				}
				if (paramInt2 == 9) {
					setBackground(new Color(120, 180, 250));
					setForeground(Color.white);
				}
			}

			super.getTableCellRendererComponent(paramJTable, paramObject, paramBoolean1, paramBoolean2, paramInt1,
					paramInt2);

			return this;
		}
	}

	SolCellModel skc = new SolCellModel();

	ProjectStatus.ColoredTableCellRenderer1 skr1 = new ProjectStatus.ColoredTableCellRenderer1();

	java.awt.Container c;

	public void px() {
		c = f.getContentPane();
		c.setLayout(new java.awt.BorderLayout());
		northp1.setLayout(new java.awt.FlowLayout(0));
		southp1.setLayout(new java.awt.FlowLayout(2));

		northp1.setBorder(bor1);
		sp.setBorder(bor2);
		sp1.setBorder(bor3);

		cb1.addItem("Project Wise");
		cb1.addItem("Client Wise");

		cb2.addItem("200700");
		SolTableModel.decorateTable(tb);
		SolTableModel.decorateTable(tb1);

		tb.setAutoResizeMode(0);
		tb.getColumnModel().getColumn(1).setPreferredWidth(220);
		tb.setShowGrid(false);

		tb.setColumnSelectionAllowed(true);
		tb.setRowSelectionAllowed(false);
		tb.setCellSelectionEnabled(true);

		tb1.setAutoResizeMode(0);
		tb1.getColumnModel().getColumn(1).setPreferredWidth(220);
		tb1.setShowGrid(false);

		for (int i = 4; i < tb1.getColumnCount(); i++) {
			tb1.getColumnModel().getColumn(i).setPreferredWidth(65);

			tb.getColumnModel().getColumn(i).setPreferredWidth(65);
			tb.getColumnModel().getColumn(i).setCellRenderer(skr1);
			tb1.getColumnModel().getColumn(i).setCellRenderer(skr1);
		}

		for (int i = 0; i < 4; i++) {
			tb.getColumnModel().getColumn(i).setCellRenderer(skc);
			tb1.getColumnModel().getColumn(i).setCellRenderer(skc);
		}

		northp1.add(l1);
		northp1.add(cb1);

		showbut.setMnemonic(80);
		northp1.add(closebut);
		closebut.setMnemonic(67);

		statuslabel.setHorizontalAlignment(2);
		southp1.add(statuslabel);
		statuslabel.setForeground(Color.darkGray);
		southp1.add(closebut);
		closebut.setMnemonic(67);

		c.add(northp1, "North");
		c.add(spl1, "Center");
		c.add(southp1, "South");

		showbut.addActionListener(this);
		closebut.addActionListener(this);

		cb1.addItemListener(this);
		cb2.addItemListener(this);

		tb.addMouseListener(this);

		spl1.setDividerLocation(225);
		spl1.setOneTouchExpandable(true);

		f.setLocation((ss.width - fs.width) / 16, (ss.height - fs.height) / 180);
		f.setSize(880, 625);
		f.setVisible(true);
	}

	public void clientList() {
		cb2.removeAllItems();
		try {
			con = DBConnectionUtil.getConnection();
			stat = con.createStatement();
			rs = stat.executeQuery("select Distinct(CLIENT_NAME) from Estimation");
			while (rs.next()) {
				String str = new String(rs.getString(1));
				cb2.addItem(str);
				System.out.println(str);
			}
		} catch (Exception localException) {
			System.out.println(localException);
		}
	}

	public void projectNo() {
		model.setNumRows(0);

		try {
			con = DBConnectionUtil.getConnection();
			stat = con.createStatement();
			rs = stat.executeQuery(
					"select project_no,Project_name from PROJECT_CO where CLIENT_NAME like '" + clientstr + "' ");
			while (rs.next()) {
				String str1 = new String(rs.getString(1));
				String str2 = new String(rs.getString(2));
				model.addRow(new Object[] { str1, str2.toUpperCase() });
			}
		} catch (Exception localException) {
			System.out.println(localException);
		}
	}

	public void TLCode() {
		try {
			con = DBConnectionUtil.getConnection();
			stat = con.createStatement();
			rs = stat.executeQuery("select Project_Name, Client_name, Co_code, name from project_co where Project_no='"
					+ prjstr + "' ");
			while (rs.next()) {
				String str1 = new String(rs.getString(1));
				String str2 = new String(rs.getString(2));
				String str3 = new String(rs.getString(3));
				String str4 = new String(rs.getString(4));

				statuslabel.setText("<html><font color ='red'>" + prjstr + "</font> (" + str1 + ")<font Color=blue> "
						+ str2 + " </font>, " + str3 + "<font color='blue'> (" + str4 + ")");
			}
		} catch (Exception localException) {
			System.out.println(localException);
		}
	}

	public void itemList() {
		int i = tb.getSelectedRow();
		String str1 = String.valueOf(model.getValueAt(i, 0));

		model1.setNumRows(0);

		try {
			rs = stat.executeQuery("select Distinct(item_code), MAX(item_name) from Estimation where project_no='"
					+ str1 + "' group by item_code");
			while (rs.next()) {
				String str2 = new String(rs.getString(1));
				String str3 = new String(rs.getString(2));
				model1.addRow(new Object[] { str2, str3 });
			}
		} catch (Exception localException) {
			System.out.println(localException);
		}
	}

	float rfi = 0.0F;
	float rev = 0.0F;

	public void RfiExtra() {
		try {
			rs = stat.executeQuery("select sum(extra_hrs) from RFI_LOG where Project_no='" + prjstr
					+ "' and affec_item like '" + itemstr + "' ");

			while (rs.next()) {
				String str = new String(rs.getString(1));
				rfi = Float.parseFloat(str);
				System.out.println("RFI : " + rfi);
			}
		} catch (Exception localException) {
			System.out.println("RFI : " + localException);
		}
	}

	public void RevExtra() {
		try {
			rs = stat.executeQuery("select sum(extra_hrs) from REV_LOG where Project_no='" + prjstr
					+ "' and affec_item like '" + itemstr + "' ");

			while (rs.next()) {
				String str = new String(rs.getString(1));
				rev = Float.parseFloat(str);
				System.out.println("REV : " + rev);
			}
		} catch (Exception localException) {
			System.out.println("REV : " + localException);
		}
	}

	float fab = 0.0F;

	public void FabStatus() {
		try {
			rs = stat.executeQuery("select count(FAB_STATUS) from TIMESHEET where Project_no='" + prjstr
					+ "' AND fab_status not like '0' and item_code like '" + itemstr + "'");

			while (rs.next()) {
				String str = new String(rs.getString(1));
				fab = Float.parseFloat(str);
			}
		} catch (Exception localException) {
			System.out.println("FAB : " + localException);
		}
	}

	float bfa1 = 0.0F;
	float bfa2 = 0.0F;

	public void BfaStatus() {
		try {
			rs = stat.executeQuery("select (select count(drawing_no) from APPROVAL_DATA where Project_no='" + prjstr
					+ "' AND RECEIVE_DATE NOT LIKE '0'and ITEM_CODE like '" + itemstr
					+ "'),(select count(drawing_no) from APPROVAL_DATA where Project_no='" + cb2.getSelectedItem()
					+ "' AND RECEIVE_DATE LIKE '0' and ITEM_CODE like '" + itemstr + "') from APPROVAL_DATA ");

			while (rs.next()) {
				String str1 = new String(rs.getString(1));
				String str2 = new String(rs.getString(2));

				bfa1 = Float.parseFloat(str1);
				bfa2 = Float.parseFloat(str2);
			}
		} catch (Exception localException) {
			System.out.println("BFA : " + localException);
		}
	}

	public void summarizedList() {
		projectNo();
		String str1 = "";
		String str4;
		String str5;
		String str6;
		for (int i = 0; i < tb.getRowCount(); i++) {

			prjstr = String.valueOf(model.getValueAt(i, 0));

			str1 = "select  sum(SHEET_QTY),(select count(DRG_NO) from TIMESHEET where PROJECT_NO='" + prjstr
					+ "' and DRGSTATUS1='Check' ),(select count(ANSWER) from RFI_LOG where PROJECT_NO='" + prjstr
					+ "' and  ANSWER NOT LIKE ''),(select COUNT(ANSWER) from RFI_LOG where PROJECT_NO='" + prjstr
					+ "' and ANSWER LIKE '') from ESTIMATION where PROJECT_NO ='" + prjstr + "' and ITEM_CODE like '"
					+ itemstr + "' group by PROJECT_NO ";

			try {
				rs = stat.executeQuery(str1);

				while (rs.next()) {
					String str2 = df1.format(Float.parseFloat(rs.getString(1)));
					str4 = df1.format(Float.parseFloat(rs.getString(2)));
					str5 = df1.format(Float.parseFloat(rs.getString(3)));
					str6 = df1.format(Float.parseFloat(rs.getString(4)));

					model.setValueAt(str2, i, 2);
					model.setValueAt(str4, i, 3);
					model.setValueAt(str5, i, 4);
					model.setValueAt(str6, i, 5);
				}
			} catch (Exception localException) {
				System.out.println(localException);
			}
		}
		for (int i = 0; i < tb.getRowCount(); i++) {
			prjstr = String.valueOf(model.getValueAt(i, 0));

			RfiExtra();
			RevExtra();
			FabStatus();
			BfaStatus();

			String str3 = String.valueOf(df1.format(rfi + rev));
			str4 = String.valueOf(df1.format(fab));
			str5 = String.valueOf(df1.format(bfa1));
			str6 = String.valueOf(df1.format(bfa2));

			model.setValueAt(str3, i, 6);
			model.setValueAt(str4, i, 7);
			model.setValueAt(str5, i, 8);
			model.setValueAt(str6, i, 9);

			rfi = 0.0F;
			rev = 0.0F;
			fab = 0.0F;
			bfa1 = 0.0F;
			bfa2 = 0.0F;
		}
	}

	public void actionPerformed(java.awt.event.ActionEvent paramActionEvent) {
		if ((paramActionEvent.getSource() != cb1) ||

		(paramActionEvent.getSource() == showbut)) {
			summarizedList();
		}
		if (paramActionEvent.getSource() == closebut) {
			f.setVisible(false);
		}
	}

	public void mousePressed(MouseEvent paramMouseEvent) {
		if (paramMouseEvent.getSource() == tb) {
			String str1;
			String str4;
			String str5;
			if (!prjstr.equalsIgnoreCase(String.valueOf(model.getValueAt(tb.getSelectedRow(), 0)))) {

				itemList();

				for (int i = 0; i < tb1.getRowCount(); i++) {
					prjstr = String.valueOf(model.getValueAt(tb.getSelectedRow(), 0));
					System.out.println("Project No : " + prjstr);
					itemstr = String.valueOf(model1.getValueAt(i, 0));
					str1 = "";

					str1 = "select sum(SHEET_QTY),(select count(DRG_NO) from TIMESHEET where PROJECT_NO='" + prjstr
							+ "' and item_code='" + itemstr
							+ "' and DRGSTATUS1='Check' ),(select count(ANSWER) from RFI_LOG where PROJECT_NO='"
							+ prjstr + "' AND affec_item='" + itemstr
							+ "' and ANSWER NOT LIKE '' ),(select COUNT(ANSWER) from RFI_LOG where PROJECT_NO='"
							+ prjstr + "'  AND affec_item='" + itemstr
							+ "' and ANSWER LIKE '' ) from ESTIMATION where PROJECT_NO ='" + prjstr
							+ "' and ITEM_CODE like '" + itemstr + "' group by ITEM_CODE ";

					try {
						rs = stat.executeQuery(str1);

						while (rs.next()) {
							String str2 = df1.format(Float.parseFloat(rs.getString(1)));
							str4 = df1.format(Float.parseFloat(rs.getString(2)));
							str5 = df1.format(Float.parseFloat(rs.getString(3)));
							String str6 = df1.format(Float.parseFloat(rs.getString(4)));

							model1.setValueAt(str2, i, 2);
							model1.setValueAt(str4, i, 3);
							model1.setValueAt(str5, i, 4);
							model1.setValueAt(str6, i, 5);
						}
					} catch (Exception localException) {
						System.out.println(localException);
					}
				}
			}

			for (int i = 0; i < tb1.getRowCount(); i++) {
				itemstr = String.valueOf(model1.getValueAt(i, 0));

				RfiExtra();
				RevExtra();
				FabStatus();
				BfaStatus();

				str1 = String.valueOf(df1.format(rfi + rev));
				String str3 = String.valueOf(df1.format(fab));
				str4 = String.valueOf(df1.format(bfa1));
				str5 = String.valueOf(df1.format(bfa2));

				model1.setValueAt(str1, i, 6);
				model1.setValueAt(str3, i, 7);
				model1.setValueAt(str4, i, 8);
				model1.setValueAt(str5, i, 9);

				rfi = 0.0F;
				rev = 0.0F;
				fab = 0.0F;
				bfa1 = 0.0F;
				bfa2 = 0.0F;
			}
			itemstr = "%";
			TLCode();
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

	public void valueChanged(javax.swing.event.ListSelectionEvent paramListSelectionEvent) {
	}

	public void itemStateChanged(java.awt.event.ItemEvent paramItemEvent) {
		if (paramItemEvent.getSource() == cb1) {
			if (String.valueOf(cb1.getSelectedItem()).equalsIgnoreCase("Project Wise")) {
				northp1.remove(l2);
				northp1.remove(cb2);
				northp1.remove(showbut);
				northp1.revalidate();
				summarizedList();
			}
			if (String.valueOf(cb1.getSelectedItem()).equalsIgnoreCase("Client Wise")) {
				northp1.add(l2);
				northp1.add(cb2);
				northp1.add(showbut);
				northp1.revalidate();
				clientList();
			}
		}

		if (paramItemEvent.getSource() == cb2) {
			clientstr = String.valueOf(cb2.getSelectedItem());
			summarizedList();
			clientstr = "%";
		}
	}

}
