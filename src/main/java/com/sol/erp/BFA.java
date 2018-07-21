package com.sol.erp;

import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.GregorianCalendar;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.sol.erp.util.DBConnectionUtil;

public class BFA implements java.awt.event.ActionListener, javax.swing.event.ListSelectionListener,
		java.awt.event.MouseListener {
	Connection con;
	Statement stat;
	ResultSet rs;
	int affected = 0;

	DecimalFormat df = new DecimalFormat("00.00");
	DecimalFormat df1 = new DecimalFormat("00");
	DecimalFormat timeformat = new DecimalFormat("0000");

	String[] column = { "Item Code", "Drawing No", "Receive Date", "Status", "Emp_Code" };
	Object[][] data = new Object[0][];

	javax.swing.JInternalFrame f = new javax.swing.JInternalFrame("BFA Entry", true, true, true, true);
	JLabel l = new JLabel("Job");
	JLabel l1 = new JLabel("Date");
	JLabel l2 = new JLabel("Emp Code");
	JLabel l3 = new JLabel("Project No");
	JLabel l4 = new JLabel("Drawing No");
	JLabel l5 = new JLabel("Start Time");
	JLabel l6 = new JLabel("End Time");
	JLabel l7 = new JLabel("Total Time");

	javax.swing.JComboBox dtlcb = new javax.swing.JComboBox();
	JTextField tf1 = new JTextField(8);
	JTextField tf2 = new JTextField(5);

	@SuppressWarnings("rawtypes")
	javax.swing.DefaultListModel projectmodel = new javax.swing.DefaultListModel();
	@SuppressWarnings({ "unchecked", "rawtypes" })
	JList tf3 = new JList(projectmodel);
	javax.swing.JScrollPane projectsp = new javax.swing.JScrollPane(tf3);

	DefaultTableModel drgmodel = new DefaultTableModel(data, column);
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
	String datestr = String.valueOf(df1.format(currentdt)) + "/" + String.valueOf(df1.format(currentmn)) + "/"
			+ String.valueOf(currentyr);
	String etimestr = "";

	JPanel centerpanel = new JPanel();
	JPanel butpanel1 = new JPanel();
	JPanel butpanel2 = new JPanel();

	javax.swing.border.Border bor1 = javax.swing.BorderFactory.createTitledBorder("BFA Time Entry");
	java.awt.Font fo = new java.awt.Font("Arial", 1, 12);

	java.awt.Toolkit tk = java.awt.Toolkit.getDefaultToolkit();
	java.awt.Dimension ss = tk.getScreenSize();
	java.awt.Dimension fs = f.getSize();

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

		butpanel1.add(l);
		butpanel1.add(dtlcb);
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

		centerpanel.setBorder(bor1);

		centerpanel.add(butpanel1, "North");

		localContainer.add(centerpanel, "Center");

		b.addActionListener(this);
		b1.addActionListener(this);
		b2.addActionListener(this);
		b3.addActionListener(this);
		b4.addActionListener(this);

		tf2.addActionListener(this);
		tf3.addListSelectionListener(this);
		tf4.addMouseListener(this);

		f.setSize(860, 450);
		f.setLocation((ss.width - fs.width) / 12, (ss.height - fs.height) / 8);

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
	}

	@SuppressWarnings("unchecked")
	public void projectList() {
		projectmodel.clear();
		try {
			con = DBConnectionUtil.getConnection();
			stat = con.createStatement();
			rs = stat.executeQuery("SELECT distinct(Project_no) from APPROVAL_DATA where BFA_status='C' ");
			while (rs.next()) {
				String str = new String(rs.getString(1));
				projectmodel.addElement(str);
			}
		} catch (Exception localException) {
			System.out.println(localException);
		}
	}

	public void drgList() {
		drgmodel.setNumRows(0);
		try {
			stat = con.createStatement();
			rs = stat.executeQuery(
					"select item_code, drawing_no, receive_date, bfa_status from approval_data where project_no='"
							+ tf3.getSelectedValue()
							+ "' and BFA_STATUS='C' AND c_status='0' and drawing_no in (select drg_no from timesheet where emp_code='"
							+ tf2.getText() + "') ");
			while (rs.next()) {
				String str1 = new String(rs.getString(1));
				String str2 = new String(rs.getString(2));
				String str3 = new String(rs.getString(3));
				String str4 = new String(rs.getString(4));
				drgmodel.addRow(new Object[] { str1, str2, str3, str4, tf2.getText() });
			}
		} catch (Exception localException) {
			System.out.println(localException);
		}
	}

	@SuppressWarnings("unchecked")
	public void getProjectList() {
		projectmodel.clear();
		int i = tf4.getSelectedRow();
		String str1 = String.valueOf(drgmodel.getValueAt(i, 1));
		String str2 = String.valueOf(drgmodel.getValueAt(i, 3));

		try {
			stat = con.createStatement();
			rs = stat.executeQuery(
					"SELECT project_no from BFA where drawing_no='" + str1 + "' and status='" + str2 + "' ");
			while (rs.next()) {
				String str3 = new String(rs.getString(1));
				projectmodel.addElement(str3);
			}
		} catch (Exception localException) {
			System.out.println(localException);
		}
		tf3.setSelectedIndex(0);
	}

	public void underProcess() {
		drgmodel.setNumRows(0);
		try {
			stat = con.createStatement();
			rs = stat.executeQuery(
					"SELECT item_code,drawing_no,receive_date,BFA_status from APPROVAL_DATA where BFA_status='U' AND DRAWING_NO IN (select drg_no from timesheet where emp_code='"
							+ tf2.getText() + "')  ");
			while (rs.next()) {
				String str1 = new String(rs.getString(1));
				String str2 = new String(rs.getString(2));
				String str3 = new String(rs.getString(3));
				String str4 = new String(rs.getString(4));
				drgmodel.addRow(new Object[] { str1, str2, str3, str4, tf2.getText() });
			}
		} catch (Exception localException) {
			System.out.println(localException);
		}
	}

	public void startTime() {
		int i = tf4.getSelectedRow();
		String.valueOf(drgmodel.getValueAt(i, 0));
		String str2 = String.valueOf(drgmodel.getValueAt(i, 1));
		String str3 = (String) tf3.getSelectedValue();

		try {
			stat = con.createStatement();
			rs = stat.executeQuery("SELECT start_time from BFA where emp_code = '" + tf2.getText()
					+ "' and drawing_no='" + str2 + "' and project_no='" + str3 + "' ");
			while (rs.next()) {
				String str4 = new String(rs.getString(1));
				tf5.setText(str4);
			}
		} catch (Exception localException) {
			System.out.println(localException);
		}
	}

	public void start() {
		int i = tf4.getSelectedRow();
		String str1 = String.valueOf(drgmodel.getValueAt(i, 0));
		String str2 = String.valueOf(drgmodel.getValueAt(i, 1));

		String str3 = (String) tf3.getSelectedValue();

		try {
			stat = con.createStatement();
			stat.executeUpdate("update APPROVAL_DATA set BFA_status='U' where project_no='" + tf3.getSelectedValue()
					+ "' and item_code='" + str1 + "' and drawing_no='" + str2 + "' ");
		} catch (Exception localException1) {
			System.out.println(localException1);
		}
		try {
			stat = con.createStatement();
			affected = stat.executeUpdate(
					"INSERT INTO BFA VALUES('" + str3 + "','" + str1 + "','" + str2 + "','" + tf2.getText() + "','"
							+ tf5.getText() + "','" + tf6.getText() + "','" + tf7.getText() + "','U','0','0') ");

			if (affected > 0) {
				javax.swing.JOptionPane.showMessageDialog(f, str2 + " is now UnderProcess for correction.");
				drgmodel.setValueAt("U", i, 3);
			}
		} catch (Exception localException2) {
			javax.swing.JOptionPane.showMessageDialog(f, localException2.getMessage().toString());
		}
	}

	public void updateTime() {
		int i = tf4.getSelectedRow();
		String str1 = String.valueOf(drgmodel.getValueAt(i, 0));
		String str2 = String.valueOf(drgmodel.getValueAt(i, 1));
		tf3.getSelectedValue();
		try {
			stat = con.createStatement();
			affected = stat.executeUpdate("update APPROVAL_DATA set BFA_status='F' where project_no='"
					+ tf3.getSelectedValue() + "' and item_code='" + str1 + "' and drawing_no='" + str2 + "' ");

			if (affected > 0) {
				javax.swing.JOptionPane.showMessageDialog(f, str2 + " Corrected.");
				drgmodel.setValueAt("F", i, 3);
				stat.executeUpdate("update BFA set end_time='" + tf6.getText() + "',total_time='" + tf7.getText()
						+ "', status='F' ,DATE_MONTH='" + tf1.getText() + "', c_status='Updated' where project_no='"
						+ tf3.getSelectedValue() + "' and item_code='" + str1 + "' and drawing_no='" + str2 + "' ");
			}
		} catch (Exception localException) {
			javax.swing.JOptionPane.showMessageDialog(f, localException.getMessage().toString());
		}
	}

	public void actionPerformed(java.awt.event.ActionEvent paramActionEvent) {
		if (paramActionEvent.getSource() == b) {
			projectList();
		}
		int i;
		int j;
		if (paramActionEvent.getSource() == b1) {
			gcal = new GregorianCalendar();
			i = gcal.get(10);
			j = gcal.get(12);
			etimestr = (String.valueOf(df1.format(i)) + String.valueOf(df1.format(j)));
			tf5.setText(etimestr);
			start();
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
			updateTime();
		}
		if (paramActionEvent.getSource() == b3) {
			underProcess();
		}
		if (paramActionEvent.getSource() == b4) {
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
