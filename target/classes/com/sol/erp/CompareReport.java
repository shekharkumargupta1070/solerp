package com.sol.erp;

import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DecimalFormat;

import javax.swing.DefaultListModel;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.sol.erp.util.DBConnectionUtil;

public class CompareReport implements java.awt.event.ActionListener, java.awt.event.MouseListener,
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

	String[] columnNames1 = { "Project No", "Client", "Team Leader", "# of Sheet", "ADMIN Quote", "TL Quote",
			"Actual Used", "Admin Shoot%", "TL Shoot %" };
	Object[][] data1 = new Object[0][];

	DecimalFormat df = new DecimalFormat("00.00");

	int v = 20;
	int h = 32;

	DefaultListModel model = new DefaultListModel();
	@SuppressWarnings({ "unchecked", "rawtypes" })
	javax.swing.JList itemlist = new javax.swing.JList(this.model);
	javax.swing.JScrollPane sp = new javax.swing.JScrollPane(this.itemlist);

	DefaultListModel prjmodel = new DefaultListModel();
	@SuppressWarnings({ "unchecked", "rawtypes" })
	javax.swing.JList prjlist = new javax.swing.JList(this.prjmodel);
	javax.swing.JScrollPane prjsp = new javax.swing.JScrollPane(this.prjlist);

	DefaultTableModel model1 = new DefaultTableModel(this.data1, this.columnNames1);
	JTable tb1 = new JTable(this.model1);
	javax.swing.JScrollPane sp1 = new javax.swing.JScrollPane(this.tb1);

	javax.swing.JSplitPane spl1 = new javax.swing.JSplitPane(1, this.sp, this.sp1);

	JDialog f = new JDialog();
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
	javax.swing.border.Border line = javax.swing.BorderFactory.createLineBorder(java.awt.Color.darkGray);
	javax.swing.border.Border bor1 = javax.swing.BorderFactory.createTitledBorder(this.line, "", 0, 0, this.fo,
			java.awt.Color.darkGray);
	javax.swing.border.Border bor2 = javax.swing.BorderFactory.createTitledBorder(this.line, "Compare Report of MP/TL",
			0, 0, this.fo, java.awt.Color.darkGray);

	java.awt.Toolkit tk = java.awt.Toolkit.getDefaultToolkit();
	java.awt.Dimension ss = this.tk.getScreenSize();
	java.awt.Dimension fs = this.f.getSize();
	java.awt.Container c;

	public void px() {
		this.c = this.f.getContentPane();
		this.c.setLayout(new java.awt.BorderLayout());
		this.northp1.setLayout(new java.awt.FlowLayout());
		this.northp2.setLayout(new java.awt.GridLayout(2, 2, 2, 2));
		this.northpanel.setLayout(new java.awt.BorderLayout());
		this.butpanel1.setLayout(new java.awt.FlowLayout());
		this.butpanel2.setLayout(new java.awt.FlowLayout());
		this.centerpanel.setLayout(new java.awt.BorderLayout());

		this.northpanel.setBorder(this.bor1);
		this.sp1.setBorder(this.bor2);

		this.tf1.addItem("Project No");

		this.tf1.setFont(this.fo);
		this.tf1.setPreferredSize(new java.awt.Dimension(100, 20));

		this.tb1.setFont(this.fo);
		this.tb1.setGridColor(new java.awt.Color(100, 150, 100));
		this.tb1.getTableHeader().setPreferredSize(new java.awt.Dimension(50, 20));
		this.tb1.getTableHeader().setFont(this.fo);
		this.tb1.setRowHeight(20);
		this.tb1.setSelectionBackground(new java.awt.Color(60, 130, 100));
		this.tb1.setSelectionForeground(java.awt.Color.white);

		this.l1.setForeground(java.awt.Color.white);
		this.northp1.add(this.showbut);
		this.showbut.setMnemonic(80);
		this.northp1.add(this.closebut);
		this.closebut.setMnemonic(67);

		this.northpanel.add(this.northp1, "Center");

		this.centerpanel.add(this.butpanel1, "North");
		this.centerpanel.add(this.sp1, "Center");

		this.c.add(this.northpanel, "North");
		this.c.add(this.centerpanel, "Center");

		this.showbut.addActionListener(this);
		this.closebut.addActionListener(this);
		this.tf1.addActionListener(this);
		this.itemlist.addListSelectionListener(this);

		this.spl1.setDividerLocation(100);
		this.f.setLocation((this.ss.width - this.fs.width) / 900, (this.ss.height - this.fs.height) / 17);
		this.f.setTitle("Quotation Compare Report for MP/TL");
		this.f.setSize(1024, 600);
		this.f.setVisible(true);
	}

	public void projectNo() {
		this.model.clear();

		try {
			con =  DBConnectionUtil.getConnection();
			this.stat = this.con.createStatement();
			this.rs = this.stat.executeQuery("select Distinct(Project_No) from Estimation");

			while (this.rs.next()) {
				String str = new String(this.rs.getString(1));
				this.tf1.addItem(str);
			}
		} catch (Exception localException) {
			System.out.println(localException);
		}
	}

	public void itemList() {
		this.model.clear();
		try {
			this.stat = this.con.createStatement();
			this.rs = this.stat
					.executeQuery("select item_code from Estimation where project_no='" + this.projectnostr + "' ");
			this.rs.getMetaData().getColumnCount();
			while (this.rs.next()) {
				String str = new String(this.rs.getString(1));
				this.model.addElement(str);
			}
		} catch (Exception localException) {
			System.out.println(localException);
		}
	}

	public void projectList() {
		this.prjmodel.clear();
		try {
			this.stat = this.con.createStatement();
			this.rs = this.stat.executeQuery("select distinct(project_no) from Estimation ");
			this.rs.getMetaData().getColumnCount();
			while (this.rs.next()) {
				String str = new String(this.rs.getString(1));
				this.prjmodel.addElement(str);
			}
		} catch (Exception localException) {
			System.out.println(localException);
		}
	}

	public void notinlist() {
		try {
			this.stat = this.con.createStatement();
			this.rs = this.stat.executeQuery(
					"select distinct(project_no) from estimation where Project_no NOT IN(SELECT DISTINCT(PROJECT_NO) FROM project_co)  ");

			while (this.rs.next()) {
				String str = new String(this.rs.getString(1));

				this.model1.addRow(new Object[] { str, "0", "0", "0", "0", "0", "0", "0", "0", "0" });
			}
		} catch (Exception localException) {
			System.out.println(localException);
		}
	}

	public void itemDetails() {
		this.model1.setNumRows(0);

		String str1 = "shekhar";
		String str2 = "kumar";
		String str3 = "gupta";

		projectList();
		for (int i = 0; i < this.prjmodel.getSize(); i++) {
			this.projectnostr = ((String) this.prjmodel.getElementAt(i));

			try {
				this.stat = this.con.createStatement();
				this.rs = this.stat.executeQuery(
						"select sum(total_time) FROM ESTIMATION_MP where Project_no='" + this.projectnostr + "' ");

				while (this.rs.next()) {
					str1 = new String(this.rs.getString(1));
					System.out.println(str1);
				}
			} catch (Exception localException1) {
				System.out.println(localException1);
			}

			try {
				this.stat = this.con.createStatement();
				this.rs = this.stat.executeQuery(
						"select sum(total_time) FROM OLD_TL_ESTIMATION where Project_no='" + this.projectnostr + "' ");

				while (this.rs.next()) {
					str2 = new String(this.rs.getString(1));
				}
			} catch (Exception localException2) {
				System.out.println(localException2);
			}

			try {
				this.stat = this.con.createStatement();
				this.rs = this.stat.executeQuery(
						"select sum(total_time) FROM ESTIMATION3 where Project_no='" + this.projectnostr + "' ");

				while (this.rs.next()) {
					str3 = new String(this.rs.getString(1));
				}
			} catch (Exception localException3) {
				System.out.println(localException3);
			}

			try {
				this.stat = this.con.createStatement();
				this.rs = this.stat.executeQuery(
						"select max(client_name), max(co_code), sum(sheet_qty) from project_co where Project_no='"
								+ this.projectnostr + "'   ");

				while (this.rs.next()) {
					String str4 = new String(this.rs.getString(1));
					String str5 = new String(this.rs.getString(2));
					String str6 = new String(this.rs.getString(3));

					float f1 = Float.parseFloat(str1);
					float f2 = Float.parseFloat(str2);
					float f3 = Float.parseFloat(str3);

					float f4 = f1 * 100.0F / f2;
					float f5 = f2 * 100.0F / f3;

					this.model1.addRow(new Object[] { this.projectnostr, str4, str5, str6 });
					this.model1.setValueAt(str1, i, 4);
					this.model1.setValueAt(str2, i, 5);
					this.model1.setValueAt(str3, i, 6);
					this.model1.setValueAt(this.df.format(f4), i, 7);
					this.model1.setValueAt(this.df.format(f5), i, 8);
				}
			} catch (Exception localException4) {
				System.out.println(localException4);
			}
		}
		notinlist();
		this.tb1.selectAll();
	}

	public void sumDetails() {
		String str1 = (String) this.itemlist.getSelectedValue();

		String str2 = "";
		try {
			this.stat = this.con.createStatement();
			this.rs = this.stat.executeQuery("select count(Drawing_no) from drawingno WHERE project_no='"
					+ this.projectnostr + "' and Item_Code Like '" + str1 + "' ");
			this.rs.getMetaData().getColumnCount();
			while (this.rs.next()) {
				str2 = new String(this.rs.getString(1));
			}
		} catch (Exception localException1) {
			System.out.println(localException1);
		}

		try {
			this.stat = this.con.createStatement();
			this.rs = this.stat.executeQuery(
					"select count(Drawing_no),sum(e_time),sum(dtl_time),sum(chk_time),sum(T_time)  from FINALDB WHERE project_no='"
							+ this.projectnostr + "' and Item_Code Like '" + str1 + "' ");
			this.rs.getMetaData().getColumnCount();
			while (this.rs.next()) {
				String str3 = new String(this.rs.getString(1));
				String str4 = new String(this.rs.getString(2));
				String str5 = new String(this.rs.getString(3));
				String str6 = new String(this.rs.getString(4));
				String str7 = new String(this.rs.getString(5));

				float f1 = Float.parseFloat(str3);
				float f2 = Float.parseFloat(str4);
				float f3 = Float.parseFloat(str5);
				float f4 = Float.parseFloat(str6);
				float f5 = Float.parseFloat(str7);
				float f6 = f2 * 100.0F / f5;
				float f7 = Float.parseFloat(str2);
				float f8 = f1 * 100.0F / f7;

				this.model1.addRow(new Object[] { "", str3 + "/" + str2, String.valueOf(this.df.format(f2)),
						String.valueOf(this.df.format(f3)), String.valueOf(this.df.format(f4)),
						String.valueOf(this.df.format(f5)), String.valueOf(this.df.format(f6)), "", "" });

				this.model1.addRow(new Object[] { "Final", String.valueOf(this.df.format(f8)) });
			}
		} catch (Exception localException2) {
			System.out.println(localException2);
		}
	}

	public void actionPerformed(java.awt.event.ActionEvent paramActionEvent) {
		if (paramActionEvent.getSource() == this.tf1) {
			this.model1.setNumRows(0);
			itemList();
			itemDetails();
		}
		if (paramActionEvent.getSource() == this.showbut) {
			this.model1.setNumRows(0);
			itemList();
			itemDetails();
		}
		if (paramActionEvent.getSource() == this.closebut) {
			this.f.setVisible(false);
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
		if (paramListSelectionEvent.getSource() == this.itemlist) {
		}
	}
}
