package com.sol.erp;

import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.util.GregorianCalendar;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;

import com.sol.erp.ui.custom.SolTableModel;
import com.sol.erp.util.DBConnectionUtil;

public class WorkReport implements java.awt.event.ActionListener, javax.swing.event.ListSelectionListener,
		java.awt.event.MouseListener {
	

	Connection con;
	java.sql.Statement stat;
	ResultSet rs;
	DecimalFormat df = new DecimalFormat("00.00");
	DecimalFormat df1 = new DecimalFormat("00");
	DecimalFormat timeformat = new DecimalFormat("0000");

	String[] column = { "Project No", "Item Code", "Item Name", "Drg No", "Drg Status" };
	Object[][] data = new Object[0][];

	JInternalFrame f = new JInternalFrame("EMP'S WORK MONITORING", true, true, true, true);
	JLabel l = new JLabel("Job");
	JLabel l1 = new JLabel("Date");
	JLabel l2 = new JLabel("TL Code");
	JLabel l3 = new JLabel("Project No");
	JLabel l4 = new JLabel("Drawing No");
	JLabel l5 = new JLabel("Start Time");
	JLabel l6 = new JLabel("End Time");
	JLabel l7 = new JLabel("Total Time");

	JLabel sl = new JLabel("Project No");
	JLabel sl1 = new JLabel("Project Name");
	JLabel sl2 = new JLabel("Client Name");
	JLabel sl3 = new JLabel("TL Code");

	JTextField stf = new JTextField(8);
	JTextField stf1 = new JTextField(15);
	JTextField stf2 = new JTextField(15);
	JTextField stf3 = new JTextField(5);

	javax.swing.JComboBox dtlcb = new javax.swing.JComboBox();
	JTextField tf1 = new JTextField(8);
	JTextField tf2 = new JTextField("ADN", 5);

	DefaultListModel empmodel = new DefaultListModel();
	javax.swing.JList tf3 = new javax.swing.JList(empmodel);
	javax.swing.JScrollPane projectsp = new javax.swing.JScrollPane(tf3);

	javax.swing.table.DefaultTableModel drgmodel = new javax.swing.table.DefaultTableModel(data, column);
	JTable tf4 = new JTable(drgmodel);
	javax.swing.JScrollPane drgsp = new javax.swing.JScrollPane(tf4);

	JTextField tf5 = new JTextField("0", 6);
	JTextField tf6 = new JTextField("0", 6);
	JTextField tf7 = new JTextField("0", 6);

	JButton b = new JButton("Show", new javax.swing.ImageIcon("image/list.gif"));
	JButton b1 = new JButton("Start", new javax.swing.ImageIcon("image/yes.gif"));
	JButton b2 = new JButton("Finish", new javax.swing.ImageIcon("image/final.gif"));
	JButton b3 = new JButton("Under Process", new javax.swing.ImageIcon("image/list.gif"));
	JButton b4 = new JButton("Close", new javax.swing.ImageIcon("image/close.gif"));

	GregorianCalendar gcal = new GregorianCalendar();
	int currentdt = gcal.get(5);
	int currentmn = gcal.get(2) + 1;
	int currentyr = gcal.get(1);
	String datestr = String.valueOf(df1.format(currentdt)) + "/"
			+ String.valueOf(df1.format(currentmn)) + "/" + String.valueOf(currentyr);
	String etimestr = "";

	JPanel centerpanel = new JPanel();
	JPanel southpanel = new JPanel();
	JPanel butpanel1 = new JPanel();
	JPanel butpanel2 = new JPanel();

	javax.swing.border.Border bor1 = javax.swing.BorderFactory.createTitledBorder("EMP Work Status");
	java.awt.Font fo = new java.awt.Font("Arial", 1, 12);

	java.awt.Toolkit tk = java.awt.Toolkit.getDefaultToolkit();
	java.awt.Dimension ss = tk.getScreenSize();
	java.awt.Dimension fs = f.getSize();

	javax.swing.table.TableColumn col3 = tf4.getColumnModel().getColumn(2);
	javax.swing.table.TableColumn col5 = tf4.getColumnModel().getColumn(4);


	public void px() {
		java.awt.Container localContainer = f.getContentPane();
		localContainer.setLayout(new java.awt.BorderLayout());
		butpanel1.setLayout(new java.awt.FlowLayout());
		centerpanel.setLayout(new java.awt.BorderLayout());
		butpanel2.setLayout(new java.awt.FlowLayout());

		tf1.setText(datestr);
		tf1.setFont(fo);
		tf2.setFont(fo);
		tf3.setFont(fo);
		tf4.setFont(fo);
		tf5.setFont(fo);
		tf6.setFont(fo);
		tf7.setFont(fo);

		col3.setPreferredWidth(250);
		col5.setPreferredWidth(130);

		butpanel1.add(l1);
		butpanel1.add(tf1);
		butpanel1.add(l2);
		butpanel1.add(tf2);
		butpanel1.add(b);
		b.setMnemonic(87);
		butpanel1.add(b4);
		b4.setMnemonic(67);

		centerpanel.add(projectsp, "West");
		projectsp.setPreferredSize(new java.awt.Dimension(120, 20));
		centerpanel.add(drgsp, "Center");

		butpanel2.add(l5);
		butpanel2.add(tf5);
		butpanel2.add(l6);
		butpanel2.add(tf6);
		butpanel2.add(l7);
		butpanel2.add(tf7);

		tf2.setEditable(false);
		tf5.setEditable(false);
		tf6.setEditable(false);
		tf7.setEditable(false);

		SolTableModel.decorateTable(tf4);

		centerpanel.setBorder(bor1);

		southpanel.add(sl);
		southpanel.add(stf);
		stf.setFont(fo);
		southpanel.add(sl1);
		southpanel.add(stf1);
		stf1.setEditable(false);
		stf1.setFont(fo);
		southpanel.add(sl2);
		southpanel.add(stf2);
		stf2.setEditable(false);
		stf2.setFont(fo);
		southpanel.add(sl3);
		southpanel.add(stf3);
		stf3.setEditable(false);
		stf3.setFont(fo);

		centerpanel.add(butpanel1, "North");

		localContainer.add(centerpanel, "Center");
		localContainer.add(southpanel, "South");

		stf.addActionListener(this);

		b.addActionListener(this);
		b1.addActionListener(this);
		b2.addActionListener(this);
		b3.addActionListener(this);
		b4.addActionListener(this);

		tf2.addActionListener(this);
		tf3.addListSelectionListener(this);
		tf4.addMouseListener(this);

		f.setSize(860, 450);

		f.setVisible(true);
	}

	public void paramUser() {
		tf2.setText(TechMainFrame.textFieldLoggedBy.getText());
		String str = new String(TechMainFrame.textFieldPost.getText());

		if (str.equalsIgnoreCase("Detailer")) {
			dtlcb.addItem("Detailing");
		}
		if (str.equalsIgnoreCase("Checker")) {
			dtlcb.addItem("Checking");
		}
		if ((str.equalsIgnoreCase("CEO")) || (str.equalsIgnoreCase("Project Manager"))) {
			tf2.setEditable(true);
		}
	}

	public void empList() {
		empmodel.clear();
		try {
			con = DBConnectionUtil.getConnection();
			stat = con.createStatement();
			rs = stat.executeQuery(
					"SELECT distinct(emp_code) from PROJECTMANPOWER where PROJECT_CO='" + tf2.getText() + "' ");
			while (rs.next()) {
				String str = new String(rs.getString(1));
				System.out.println(str);
				empmodel.addElement(str.toUpperCase());
			}
		} catch (Exception localException) {
			System.out.println(localException);
		}
	}

	public void empList2() {
		empmodel.clear();
		try {
			con = DBConnectionUtil.getConnection();
			stat = con.createStatement();
			rs = stat.executeQuery(
					"SELECT distinct(emp_code) from PROJECTMANPOWER where PROJECT_NO='" + stf.getText() + "' ");
			while (rs.next()) {
				String str = new String(rs.getString(1));
				System.out.println(str);
				empmodel.addElement(str.toUpperCase());
			}
		} catch (Exception localException) {
			System.out.println(localException);
		}
	}

	public void projectDetails() {
		empmodel.clear();
		try {
			con = DBConnectionUtil.getConnection();
			stat = con.createStatement();
			rs = stat
					.executeQuery("SELECT project_name, client_name, co_code FROM PROJECT_CO where PROJECT_NO='"
							+ stf.getText() + "' ");
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

	public void drgList() {
		drgmodel.setNumRows(0);
		try {
			con = DBConnectionUtil.getConnection();
			stat = con.createStatement();
			rs = stat.executeQuery(
					"select u.project_no, u.item_code, i.item_name, u.drg_no, u.drg_status from UNDER_PROCESS u ,itemcode i where u.date='"
							+ tf1.getText() + "' and u.emp_code='" + tf3.getSelectedValue() + "' ");
			while (rs.next()) {
				String str1 = new String(rs.getString(1));
				String str2 = new String(rs.getString(2));
				String str3 = new String(rs.getString(3));
				String str4 = new String(rs.getString(4));
				String str5 = new String(rs.getString(5));

				System.out.println(str1 + " " + str2);

				drgmodel.addRow(new Object[] { str1, str2, str3, str4, str5 });
			}
		} catch (Exception localException) {
			System.out.println(localException);
		}
	}

	public void actionPerformed(ActionEvent paramActionEvent) {
		if ((paramActionEvent.getSource() == b) || (paramActionEvent.getSource() == tf2)) {
			empList();
		}
		if (paramActionEvent.getSource() == stf) {
			projectDetails();
			empList2();
		}
		int i;
		int j;
		if (paramActionEvent.getSource() == b1) {
			gcal = new GregorianCalendar();
			i = gcal.get(10);
			j = gcal.get(12);
			etimestr = (String.valueOf(df1.format(i)) + String.valueOf(df1.format(j)));
			tf5.setText(etimestr);
		}

		if (paramActionEvent.getSource() == b2) {

			gcal = new GregorianCalendar();
			i = gcal.get(10);
			j = gcal.get(12);
			String str1 = String.valueOf(df1.format(i)) + String.valueOf(df1.format(j));
			String str2 = tf5.getText();

			int k = Integer.parseInt(str2);
			int m = Integer.parseInt(str1);
			int n = m - k;

			tf6.setText(str1);
			tf7.setText(String.valueOf(timeformat.format(n)));
		}
		if ((paramActionEvent.getSource() != b3) ||

		(paramActionEvent.getSource() == b4)) {
			f.setVisible(false);
		}
	}

	public void valueChanged(javax.swing.event.ListSelectionEvent paramListSelectionEvent) {
		if (paramListSelectionEvent.getSource() == tf3) {
			drgList();
		}
	}

	public void mousePressed(MouseEvent paramMouseEvent) {
		if (paramMouseEvent.getSource() == tf4) {
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
