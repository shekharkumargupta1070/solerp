package com.sol.erp;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DecimalFormat;

import javax.swing.DefaultListModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.sol.erp.util.DBConnectionUtil;

public class CurrentReport implements java.awt.event.ActionListener, java.awt.event.MouseListener,
		javax.swing.event.ListSelectionListener {

	Connection con;
	Statement stat;
	ResultSet rs;
	String itemcodestr = "abc";
	String projectnostr = "000001";
	float totaldrg = 0.0F;
	float totalusedtime = 0.0F;
	float totalbfatime = 0.0F;
	float totalrevtime = 0.0F;
	float totalwestedtime = 0.0F;
	float totaltotaltime = 0.0F;
	float totaletime = 0.0F;
	float totalshoot = 0.0F;
	float totshoot = 0.0F;

	String[] columnNames = { "Item_Code" };
	Object[][] data = new Object[0][];

	String[] columnNames1 = { "Project No", "Client", "Team Leader", "Quoted Hr", "Lead Time", "Start Date", "End Date",
			"Status", "# of Sheet", "Completed", "% Complete" };
	Object[][] data1 = new Object[0][];

	DecimalFormat df = new DecimalFormat("00.00");

	int v = 20;
	int h = 32;

	DefaultListModel model = new DefaultListModel();
	JList itemlist = new JList(model);
	JScrollPane sp = new JScrollPane(itemlist);

	DefaultListModel prjmodel = new DefaultListModel();
	JList prjlist = new JList(prjmodel);
	JScrollPane prjsp = new JScrollPane(prjlist);

	DefaultListModel drgmodel = new DefaultListModel();
	JList drglist = new JList(prjmodel);
	JScrollPane drgsp = new JScrollPane(drglist);

	DefaultTableModel model1 = new DefaultTableModel(data1, columnNames1);
	JTable tb1 = new JTable(model1);
	JScrollPane sp1 = new JScrollPane(tb1);

	javax.swing.JSplitPane spl1 = new javax.swing.JSplitPane(1, sp, sp1);

	javax.swing.JFrame f = new javax.swing.JFrame();
	JLabel l1 = new JLabel("Project No");
	JLabel l2 = new JLabel("Sheet");
	JLabel l3 = new JLabel("E_Time");
	JLabel l4 = new JLabel("D_Total");
	JLabel l5 = new JLabel("C_Total");
	JLabel l6 = new JLabel("Total");
	JLabel l7 = new JLabel("Shoot");

	JLabel topimagelabel = new JLabel(new javax.swing.ImageIcon("image/projectreport.gif"), 2);

	JComboBox tf1 = new JComboBox();

	javax.swing.JButton showbut = new javax.swing.JButton("Show Report");
	javax.swing.JButton closebut = new javax.swing.JButton("Close", new javax.swing.ImageIcon("image/close.gif"));

	JPanel northp1 = new JPanel();
	JPanel northp2 = new JPanel();
	JPanel northp3 = new JPanel();

	JPanel butpanel1 = new JPanel();
	JPanel butpanel2 = new JPanel();

	JPanel centerpanel = new JPanel();
	JPanel northpanel = new JPanel();

	java.awt.Font fo = new java.awt.Font("Tahoma", 1, 11);
	javax.swing.border.Border line = javax.swing.BorderFactory.createLineBorder(Color.darkGray);
	javax.swing.border.Border bor1 = javax.swing.BorderFactory.createTitledBorder(line, "", 0, 0, fo,
			Color.darkGray);
	javax.swing.border.Border bor2 = javax.swing.BorderFactory.createTitledBorder(line, "Project No Wise Status",
			0, 0, fo, Color.darkGray);

	java.awt.Toolkit tk = java.awt.Toolkit.getDefaultToolkit();
	java.awt.Dimension ss = tk.getScreenSize();
	java.awt.Dimension fs = f.getSize();
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

		northpanel.setBorder(bor1);
		sp1.setBorder(bor2);

		tf1.addItem("Project No");

		tf1.setFont(fo);
		tf1.setPreferredSize(new java.awt.Dimension(100, 20));

		tb1.setBackground(new Color(60, 130, 100));
		tb1.setForeground(Color.white);
		tb1.setFont(fo);
		tb1.setGridColor(new Color(100, 150, 100));
		tb1.getTableHeader().setPreferredSize(new java.awt.Dimension(50, 20));
		tb1.getTableHeader().setFont(fo);
		tb1.setRowHeight(20);
		tb1.setSelectionBackground(new Color(50, 50, 150));
		tb1.setSelectionForeground(Color.yellow);

		l1.setForeground(Color.white);
		northp1.add(showbut);
		showbut.setMnemonic(80);
		northp1.add(closebut);
		closebut.setMnemonic(67);

		northpanel.add(northp1, "Center");

		centerpanel.add(butpanel1, "North");
		centerpanel.add(sp1, "Center");
		centerpanel.add(butpanel2, "South");

		c.add(northpanel, "North");
		c.add(centerpanel, "Center");

		showbut.addActionListener(this);
		closebut.addActionListener(this);
		tf1.addActionListener(this);
		itemlist.addListSelectionListener(this);

		spl1.setDividerLocation(100);

		f.setTitle("Project No Wise Report [Current Report]");
		f.setSize(1024, 740);
		f.setVisible(true);
	}

	public void projectNo() {
		model.clear();

		try {
			con = DBConnectionUtil.getConnection();
			stat = con.createStatement();
			rs = stat.executeQuery("select Distinct(Project_No) from Estimation");

			while (rs.next()) {
				String str = new String(rs.getString(1));
				tf1.addItem(str);
			}
		} catch (Exception localException) {
			System.out.println(localException);
		}
	}

	public void itemList() {
		projectNo();
		model.clear();
		try {
			stat = con.createStatement();
			rs = stat
					.executeQuery("select item_code from Estimation where project_no='" + projectnostr + "' ");
			rs.getMetaData().getColumnCount();
			while (rs.next()) {
				String str = new String(rs.getString(1));
				model.addElement(str);
			}
		} catch (Exception localException) {
			System.out.println(localException);
		}
	}

	public void projectList() {
		prjmodel.clear();
		try {
			stat = con.createStatement();
			rs = stat.executeQuery("select distinct(project_no) from Estimation ");
			rs.getMetaData().getColumnCount();
			while (rs.next()) {
				String str = new String(rs.getString(1));
				prjmodel.addElement(str);
			}
		} catch (Exception localException) {
			System.out.println(localException);
		}
	}

	public void notinlist() {
		try {
			stat = con.createStatement();
			rs = stat.executeQuery(
					"select distinct(project_no) from estimation where Project_no NOT IN(SELECT DISTINCT(PROJECT_NO) FROM project_co)  ");

			while (rs.next()) {
				String str = new String(rs.getString(1));

				model1.addRow(new Object[] { str, "0", "0", "0", "0", "0", "0", "0", "0", "0" });
			}
		} catch (Exception localException) {
			System.out.println(localException);
		}
	}

	public void itemDetails() {
		model1.setNumRows(0);

		String str1 = "shekhar";

		projectList();
		for (int i = 0; i < prjmodel.getSize(); i++) {
			projectnostr = ((String) prjmodel.getElementAt(i));

			try {
				stat = con.createStatement();
				rs = stat.executeQuery(
						"select distinct(DRG_NO) FROM TIMESHEET where Project_no='" + projectnostr + "' ");

				while (rs.next()) {
					str1 = new String(rs.getString(1));
					drgmodel.addElement(str1);
				}
			} catch (Exception localException1) {
				System.out.println(localException1);
			}
			int j = drgmodel.getSize();
			String str2 = String.valueOf(j);
			try {
				stat = con.createStatement();
				rs = stat.executeQuery(
						"select client_name, co_code, qtd_hrs, start, final, sheet_qty from project_co where Project_no='"
								+ projectnostr + "'   ");

				while (rs.next()) {
					String str3 = new String(rs.getString(1));
					String str4 = new String(rs.getString(2));
					String str5 = new String(rs.getString(3));
					String str6 = new String(rs.getString(4));
					String str7 = new String(rs.getString(5));
					String str8 = new String(rs.getString(6));

					model1.addRow(
							new Object[] { projectnostr, str3, str4, str5, "", str6, str7, "", str8, "", "" });

					float f1 = Float.parseFloat(str2);
					float f2 = Float.parseFloat(str8);
					float f3 = f1 * 100.0F / f2;

					model1.setValueAt(str2, i, 9);
					model1.setValueAt(String.valueOf(df.format(f3)), i, 10);

					if (f3 < 100.0F) {
						model1.setValueAt("Under Process", i, 7);
					}
					if (f3 >= 100.0F) {
						model1.setValueAt("Finished", i, 7);
					}
				}
			} catch (Exception localException2) {
				System.out.println(localException2);
			}
		}
		notinlist();
	}

	public void sumDetails() {
		String str1 = (String) itemlist.getSelectedValue();

		String str2 = "";
		try {
			stat = con.createStatement();
			rs = stat.executeQuery("select count(Drawing_no) from drawingno WHERE project_no='"
					+ projectnostr + "' and Item_Code Like '" + str1 + "' ");
			rs.getMetaData().getColumnCount();
			while (rs.next()) {
				str2 = new String(rs.getString(1));
			}
		} catch (Exception localException1) {
			System.out.println(localException1);
		}

		try {
			stat = con.createStatement();
			rs = stat.executeQuery(
					"select count(Drawing_no),sum(e_time),sum(dtl_time),sum(chk_time),sum(T_time)  from FINALDB WHERE project_no='"
							+ projectnostr + "' and Item_Code Like '" + str1 + "' ");
			rs.getMetaData().getColumnCount();
			while (rs.next()) {
				String str3 = new String(rs.getString(1));
				String str4 = new String(rs.getString(2));
				String str5 = new String(rs.getString(3));
				String str6 = new String(rs.getString(4));
				String str7 = new String(rs.getString(5));

				float f1 = Float.parseFloat(str3);
				float f2 = Float.parseFloat(str4);
				float f3 = Float.parseFloat(str5);
				float f4 = Float.parseFloat(str6);
				float f5 = Float.parseFloat(str7);
				float f6 = f2 * 100.0F / f5;
				float f7 = Float.parseFloat(str2);
				float f8 = f1 * 100.0F / f7;

				model1.addRow(new Object[] { "", str3 + "/" + str2, String.valueOf(df.format(f2)),
						String.valueOf(df.format(f3)), String.valueOf(df.format(f4)),
						String.valueOf(df.format(f5)), String.valueOf(df.format(f6)), "", "" });

				model1.addRow(new Object[] { "Final", String.valueOf(df.format(f8)) });
			}
		} catch (Exception localException2) {
			System.out.println(localException2);
		}
	}

	public void actionPerformed(java.awt.event.ActionEvent paramActionEvent) {
		if (paramActionEvent.getSource() == tf1) {
			model1.setNumRows(0);
			itemList();
			itemDetails();
		}
		if (paramActionEvent.getSource() == showbut) {
			model1.setNumRows(0);
			itemList();
			itemDetails();
		}
		if (paramActionEvent.getSource() == closebut) {
			f.setVisible(false);
		}
	}

	public void mousePressed(MouseEvent paramMouseEvent) {
		itemDetails();
		sumDetails();
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
		if (paramListSelectionEvent.getSource() == itemlist) {
		}
	}
}
