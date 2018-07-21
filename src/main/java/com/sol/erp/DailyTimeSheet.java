package com.sol.erp;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import com.sol.erp.constants.ApplicationConstants;
import com.sol.erp.ui.custom.SolCalendar;
import com.sol.erp.util.DBConnectionUtil;
import com.sol.erp.util.formater.SolDateFormatter;

public class DailyTimeSheet implements java.awt.event.ActionListener, java.awt.event.FocusListener,
		javax.swing.event.ListSelectionListener, java.awt.event.MouseListener, java.awt.event.KeyListener {

	SolCalendar skl;
	Connection con;
	Statement stat;
	ResultSet rs;
	int i;
	int affected;
	String[] column;
	Object[][] data;
	String[] column2;
	Object[][] data2;
	DecimalFormat df;
	DecimalFormat df1;
	DecimalFormat timeformat;
	String drgstr;
	String eststr;
	JInternalFrame f;
	JLabel projectlabel;
	DefaultListModel projectmodel;
	JList projectlist;
	JScrollPane projectsp;
	JLabel itemlabel;
	DefaultListModel itemmodel;
	JList itemlist;
	JScrollPane itemsp;
	JLabel drglabel;
	DefaultListModel drgmodel;
	JList drglist;
	JScrollPane drgsp;
	DefaultListModel estmodel;
	JList estlist;
	JScrollPane estsp;
	DefaultTableModel tbmodel;
	JTable tb;
	JScrollPane tbsp;
	DefaultTableModel tbmodel2;
	JTable tb2;
	JScrollPane tbsp2;
	JLabel todaylabel;
	JTextField todaytf;
	JLabel drgl;
	JTextField drgtf;
	JLabel iteml;
	JTextField itemtf;
	JLabel finallabel;
	JLabel l;
	JLabel l1;
	JLabel l2;
	JLabel l3;
	JLabel l4;
	JLabel l5;
	JLabel totallabel;
	JLabel l6;
	JLabel l7;
	JLabel l8;
	JLabel l9;
	JLabel breaklabel;
	JLabel l10;
	JLabel l11;
	JLabel bdrgl;
	JLabel bitml;
	JLabel l12;
	JLabel l13;
	JLabel rdrgl;
	JLabel ritml;
	JLabel l14;
	JLabel westprjlabel;
	JLabel westdrgl;
	JLabel westitml;
	JLabel l15;
	JLabel northlabel;
	JLabel westlabel;
	static JTextField tf = new JTextField();
	static JTextField tf1 = new JTextField("AKD");

	JTextField tf2;

	JTextField tf3;

	JTextField tf4;

	JTextField tf5;

	JTextField totaltf;

	JTextField tf6;

	JTextField tf7;

	JTextField tf8;

	JTextField tf9;

	JTextField breaktf;

	JTextField tf10;

	JTextField tf11;

	JTextField tf12;

	JTextField bdrgtf;

	JTextField bitmtf;

	JTextField tf13;
	JTextField tf14;
	JTextField rdrgtf;
	JTextField ritmtf;
	JTextField westprjtf;
	JTextField westdrgtf;
	JTextField westitmtf;
	JTextArea tf15;
	JScrollPane sp;
	JButton savebut;
	JButton clearbut;
	JButton closebut;
	JPopupMenu jpm;
	JMenuItem popm1;
	JPopupMenu jpm2;
	JMenuItem popm2;
	JRadioButton rb1;
	JRadioButton rb2;
	ButtonGroup grp;
	JRadioButton rg1;
	JRadioButton rg2;
	ButtonGroup grp2;
	JPanel centerpanel;
	JPanel northpanel;
	JPanel westpanel;
	JPanel eastpanel;
	JPanel southpanel;
	JPanel dtlpanel;
	JPanel dtlpanel2;
	java.util.Date dat;
	SimpleDateFormat formatter;
	String dateString;
	Toolkit tk;
	Dimension ss;
	Dimension fs;
	java.awt.Font fo;
	DailyTimeSheet.ColoredTableCellRenderer skr;
	DailyTimeSheet.ColoredTableCellRenderer1 skr1;
	int ska;
	String skitem;
	String skproject;
	String skdrg;

	class ColoredTableCellRenderer extends DefaultTableCellRenderer {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		ColoredTableCellRenderer() {
		}

		public java.awt.Component getTableCellRendererComponent(JTable paramJTable, Object paramObject,
				boolean paramBoolean1, boolean paramBoolean2, int paramInt1, int paramInt2) {
			setEnabled((paramJTable == null) || (paramJTable.isEnabled()));

			String str = String.valueOf(tbmodel.getValueAt(paramInt1, 4));
			setToolTipText(str);

			if (str.equalsIgnoreCase("BFA")) {
				setBackground(new Color(70, 100, 220));
				setForeground(Color.white);
			}
			if (str.equalsIgnoreCase("Revision")) {
				setBackground(new Color(180, 250, 250));
				setForeground(Color.black);
			}
			if (str.equalsIgnoreCase("Wasted")) {
				setBackground(new Color(80, 150, 50));
				setForeground(Color.white);
			}

			super.getTableCellRendererComponent(paramJTable, paramObject, paramBoolean1, paramBoolean2, paramInt1,
					paramInt2);

			return this;
		}
	}

	class ColoredTableCellRenderer1 extends DefaultTableCellRenderer {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		ColoredTableCellRenderer1() {
		}

		public java.awt.Component getTableCellRendererComponent(JTable paramJTable, Object paramObject,
				boolean paramBoolean1, boolean paramBoolean2, int paramInt1, int paramInt2) {
			setEnabled((paramJTable == null) || (paramJTable.isEnabled()));

			if (paramInt1 % 2 == 0) {
				setBackground(Color.gray);
				setForeground(Color.white);
			} else {
				setBackground(Color.darkGray);
				setForeground(Color.white);
			}

			super.getTableCellRendererComponent(paramJTable, paramObject, paramBoolean1, paramBoolean2, paramInt1,
					paramInt2);

			return this;
		}
	}

	public void px() {
		Container localContainer = f.getContentPane();
		localContainer.setLayout(new BorderLayout());
		centerpanel.setLayout(null);
		northpanel.setLayout(new BorderLayout());
		westpanel.setLayout(new BorderLayout());
		southpanel.setLayout(new java.awt.FlowLayout());
		dtlpanel.setLayout(new BorderLayout());

		westpanel.add(westlabel, "Center");

		for (int j = 0; j < tb.getColumnCount(); j++) {
			tb.getColumnModel().getColumn(j).setCellRenderer(skr);
		}

		for (int j = 0; j < tb2.getColumnCount(); j++) {
			tb2.getColumnModel().getColumn(j).setCellRenderer(skr1);
		}

		jpm.add(popm1);
		jpm2.add(popm2);

		grp.add(rb1);
		rb1.setEnabled(true);
		grp.add(rb2);
		rb2.setEnabled(true);

		dtlpanel.add(rb1, "West");
		dtlpanel.add(rb2, "East");

		grp2.add(rg1);
		grp2.add(rg2);
		rg2.setEnabled(false);

		dtlpanel2.add(rg1, "West");
		dtlpanel2.add(rg2, "East");

		finallabel.setHorizontalAlignment(0);

		northpanel.add(dtlpanel, "West");
		northpanel.add(dtlpanel2, "East");
		northpanel.add(finallabel, "Center");

		centerpanel.setBackground(new Color(60, 130, 130));

		totallabel.setForeground(Color.white);
		drgl.setForeground(Color.white);
		drglabel.setForeground(Color.white);
		iteml.setForeground(Color.white);
		itemlabel.setForeground(Color.white);
		projectlabel.setForeground(Color.white);
		breaklabel.setForeground(Color.white);
		westprjlabel.setForeground(Color.white);
		bdrgl.setForeground(Color.white);
		rdrgl.setForeground(Color.white);
		westdrgl.setForeground(Color.white);
		bitml.setForeground(Color.white);
		ritml.setForeground(Color.white);
		westitml.setForeground(Color.white);

		tb.setFont(fo);
		tb.setGridColor(new Color(100, 150, 100));
		tb.getTableHeader().setPreferredSize(new Dimension(50, 20));
		tb.getTableHeader().setFont(fo);
		tb.setRowHeight(20);
		tb.setSelectionBackground(Color.blue);
		tb.setSelectionForeground(Color.white);

		tb2.setFont(fo);
		tb2.setGridColor(new Color(100, 150, 100));
		tb2.getTableHeader().setPreferredSize(new Dimension(50, 20));
		tb2.getTableHeader().setFont(fo);
		tb2.setRowHeight(20);
		tb2.setSelectionBackground(Color.black);
		tb2.setSelectionForeground(Color.white);

		todaylabel.setForeground(Color.white);
		l.setForeground(Color.white);
		l1.setForeground(Color.white);
		l2.setForeground(Color.white);
		l3.setForeground(Color.white);
		l4.setForeground(Color.white);
		l5.setForeground(Color.white);
		l6.setForeground(Color.white);
		l7.setForeground(Color.white);
		l8.setForeground(Color.white);
		l9.setForeground(Color.white);
		l10.setForeground(Color.white);
		l11.setForeground(Color.white);
		l12.setForeground(Color.white);
		l13.setForeground(Color.white);
		l14.setForeground(Color.white);
		l15.setForeground(Color.white);

		todaytf.setFont(fo);
		totaltf.setFont(fo);
		breaktf.setFont(fo);
		breaktf.setEditable(false);
		drgtf.setFont(fo);
		itemtf.setFont(fo);
		westprjtf.setFont(fo);
		westprjtf.setEditable(false);
		westdrgtf.setFont(fo);
		westdrgtf.setEditable(false);
		tf.setFont(fo);
		tf.setEditable(true);
		tf1.setFont(fo);
		tf1.setEditable(false);
		tf1.selectAll();
		tf1.setForeground(Color.green);
		tf1.setBackground(Color.black);
		tf2.setFont(fo);
		tf2.setEditable(false);
		tf3.setFont(fo);
		tf3.setEditable(false);
		tf4.setFont(fo);
		tf4.setEditable(false);
		tf5.setFont(fo);
		tf5.setEditable(false);
		tf6.setFont(fo);
		tf6.setEditable(false);
		tf7.setFont(fo);
		tf8.setFont(fo);
		tf8.setEditable(false);
		tf9.setFont(fo);
		tf9.setEditable(false);
		tf10.setFont(fo);
		tf10.setEditable(false);
		tf11.setFont(fo);
		tf11.setEditable(false);
		tf12.setFont(fo);
		tf12.setEditable(false);
		tf13.setFont(fo);
		tf13.setEditable(false);
		tf14.setFont(fo);
		tf14.setEditable(false);
		tf15.setFont(fo);
		tf15.setEditable(false);
		bdrgtf.setFont(fo);
		bdrgtf.setEditable(false);
		rdrgtf.setFont(fo);
		rdrgtf.setEditable(false);
		bitmtf.setFont(fo);
		bitmtf.setEditable(false);
		ritmtf.setFont(fo);
		ritmtf.setEditable(false);
		westitmtf.setFont(fo);
		westitmtf.setEditable(false);

		westpanel.setBackground(Color.white);

		northpanel.setPreferredSize(new Dimension(40, 40));
		westpanel.setPreferredSize(new Dimension(150, 70));

		todaylabel.setBounds(30, 15, 150, 20);
		todaytf.setBounds(200, 15, 90, 20);
		todaytf.setEditable(false);
		l.setBounds(30, 50, 150, 20);
		tf.setBounds(200, 50, 90, 20);
		l1.setBounds(300, 50, 60, 20);
		tf1.setBounds(360, 50, 90, 20);
		l2.setBounds(30, 75, 150, 20);
		tf2.setBounds(200, 75, 250, 20);
		l3.setBounds(30, 100, 150, 20);
		tf3.setBounds(200, 100, 150, 20);

		projectlabel.setBounds(30, 140, 150, 20);
		projectsp.setBounds(200, 140, 90, 70);
		drglabel.setBounds(300, 140, 150, 20);
		drgsp.setBounds(360, 140, 90, 70);
		itemlabel.setBounds(460, 140, 50, 20);
		itemsp.setBounds(520, 140, 90, 70);
		l8.setBounds(30, 215, 150, 20);
		tf8.setBounds(200, 215, 90, 20);
		l9.setBounds(300, 215, 60, 20);
		tf9.setBounds(360, 215, 90, 20);
		breaklabel.setBounds(460, 215, 50, 20);
		breaktf.setBounds(520, 215, 90, 20);

		tbsp.setBounds(30, 240, 580, 130);
		tbsp2.setBounds(30, 380, 580, 130);

		savebut.setBounds(165, 530, 100, 25);
		clearbut.setBounds(270, 530, 100, 25);
		closebut.setBounds(375, 530, 100, 25);

		savebut.setMnemonic(83);
		clearbut.setMnemonic(82);
		closebut.setMnemonic(67);

		centerpanel.add(todaylabel);
		centerpanel.add(todaytf);
		centerpanel.add(l);
		centerpanel.add(tf);
		centerpanel.add(l1);
		centerpanel.add(tf1);
		centerpanel.add(l2);
		centerpanel.add(tf2);
		centerpanel.add(l3);
		centerpanel.add(tf3);
		centerpanel.add(l4);
		centerpanel.add(tf4);
		centerpanel.add(l5);
		centerpanel.add(tf5);
		centerpanel.add(l6);
		centerpanel.add(tf6);
		centerpanel.add(totallabel);
		centerpanel.add(totaltf);
		centerpanel.add(drgl);
		centerpanel.add(drgtf);
		centerpanel.add(projectlabel);
		centerpanel.add(projectsp);
		centerpanel.add(drglabel);
		centerpanel.add(drgsp);

		centerpanel.add(itemlabel);
		centerpanel.add(itemsp);
		centerpanel.add(l7);
		centerpanel.add(tf7);
		centerpanel.add(l8);
		centerpanel.add(tf8);
		centerpanel.add(l9);
		centerpanel.add(tf9);
		centerpanel.add(breaklabel);
		centerpanel.add(breaktf);
		centerpanel.add(l10);
		centerpanel.add(tf10);
		centerpanel.add(l11);
		centerpanel.add(tf11);
		centerpanel.add(l12);
		centerpanel.add(tf12);
		centerpanel.add(bdrgl);
		centerpanel.add(bdrgtf);
		centerpanel.add(bitml);
		centerpanel.add(bitmtf);
		centerpanel.add(l13);
		centerpanel.add(tf13);
		centerpanel.add(l14);
		centerpanel.add(tf14);
		centerpanel.add(rdrgl);
		centerpanel.add(rdrgtf);
		centerpanel.add(ritml);
		centerpanel.add(ritmtf);
		centerpanel.add(westprjlabel);
		centerpanel.add(westprjtf);
		centerpanel.add(westdrgl);
		centerpanel.add(westdrgtf);
		centerpanel.add(westitml);
		centerpanel.add(westitmtf);
		centerpanel.add(l15);
		centerpanel.add(sp);

		centerpanel.add(tbsp);
		centerpanel.add(tbsp2);

		southpanel.add(savebut);
		southpanel.add(clearbut);
		southpanel.add(closebut);

		tf.addMouseListener(this);
		tf.addFocusListener(this);
		tf1.addFocusListener(this);
		tf1.addKeyListener(this);
		tf2.addFocusListener(this);
		tf3.addFocusListener(this);
		tf4.addFocusListener(this);
		tf6.addFocusListener(this);
		tf7.addFocusListener(this);
		tf8.addFocusListener(this);
		tf9.addFocusListener(this);
		tf10.addFocusListener(this);
		tf11.addFocusListener(this);
		tf12.addFocusListener(this);
		tf13.addFocusListener(this);
		tf14.addFocusListener(this);
		tf15.addFocusListener(this);
		tf2.addFocusListener(this);
		westprjtf.addFocusListener(this);
		drgtf.addFocusListener(this);
		itemtf.addFocusListener(this);
		bdrgtf.addFocusListener(this);
		rdrgtf.addFocusListener(this);
		westdrgtf.addFocusListener(this);
		westprjtf.addFocusListener(this);

		popm1.addActionListener(this);
		popm2.addActionListener(this);
		tf2.addActionListener(this);
		tf7.addActionListener(this);

		tb.addMouseListener(this);
		tb2.addMouseListener(this);

		tf.addActionListener(this);
		tf1.addActionListener(this);
		drgtf.addActionListener(this);
		itemtf.addActionListener(this);
		projectlist.addListSelectionListener(this);
		projectlist.addMouseListener(this);
		itemlist.addListSelectionListener(this);
		drglist.addListSelectionListener(this);

		savebut.addActionListener(this);
		clearbut.addActionListener(this);
		closebut.addActionListener(this);

		rb1.addActionListener(this);
		rb2.addActionListener(this);
		rg1.addActionListener(this);
		rg2.addActionListener(this);

		localContainer.add(northpanel, "North");
		localContainer.add(centerpanel, "Center");
		localContainer.add(westpanel, "West");
		localContainer.add(southpanel, "South");

		f.setSize(800, 630);

		f.setVisible(true);

		tf1.requestFocus();
	}

	public void paramUser() {
		tf1.setText(TechMainFrame.textFieldLoggedBy.getText());
		tf3.setText(TechMainFrame.textFieldPost.getText());

		if (TechMainFrame.textFieldUserType.getText().equalsIgnoreCase("Admin")) {
			tf1.setEditable(true);
			tf1.setBackground(Color.white);
			tf1.setForeground(Color.black);
		}
	}

	public void projectNo() {
		if (rb1.isSelected()) {
		}
		if (rb2.isSelected()) {
		}
		projectmodel.clear();
		projectlist.setBackground(Color.white);
		try {
			con = DBConnectionUtil.getConnection();
			stat = con.createStatement();
			rs = stat.executeQuery("select distinct(project_no) from projectmanpower WHERE EMP_CODE='"
					+ tf1.getText() + "' and from_date='" + tf.getText() + "' ");
			while (rs.next()) {
				String str2 = new String(rs.getString(1));
				projectmodel.addElement(str2);
			}
		} catch (Exception localException) {
		}
	}

	public void finalDate() {
		finallabel.setText("##/##/####");

		try {
			PreparedStatement localPreparedStatement = con
					.prepareStatement("select new_final_date from project_co WHERE project_no = ? ");
			localPreparedStatement.setInt(1, Integer.parseInt((String) projectlist.getSelectedValue()));
			rs = localPreparedStatement.executeQuery();
			while (rs.next()) {
				Date localObject = rs.getDate(1);
				String str2 = SolDateFormatter.SQLtoDDMMYY((java.sql.Date) localObject);
				finallabel.setText(str2);
			}
		} catch (Exception localException) {
			System.out.println(localException);
		}

		String str1 = todaytf.getText();
		Object localObject = str1.substring(0, 2);
		String str2 = str1.substring(3, 5);
		String str3 = str1.substring(7, 10);

		int j = Integer.parseInt((String) localObject);
		int k = Integer.parseInt(str2);
		int m = Integer.parseInt(str3);

		String str4 = finallabel.getText();
		String str5 = str4.substring(0, 2);
		String str6 = str4.substring(3, 5);
		String str7 = str4.substring(7, 10);

		int n = Integer.parseInt(str5);
		int i1 = Integer.parseInt(str6);
		int i2 = Integer.parseInt(str7);

		int i3 = j + k * 100 + m;
		int i4 = n + i1 * 100 + i2;

		if (i3 <= i4) {
			savebut.setEnabled(true);
			popm1.setEnabled(true);
			popm2.setEnabled(true);
			tb.setEnabled(true);
			tb2.setEnabled(true);
		} else {
			if (TechMainFrame.textFieldPost.getText().equalsIgnoreCase("Team Leader")) {
				savebut.setEnabled(true);
				popm1.setEnabled(true);
				popm2.setEnabled(true);
				tb.setEnabled(true);
				tb2.setEnabled(true);
			} else {
				savebut.setEnabled(false);
				popm1.setEnabled(false);
				popm2.setEnabled(false);
				tb.setEnabled(false);
				tb2.setEnabled(false);
				JOptionPane.showMessageDialog(f, "Final Date of The Project " + projectlist.getSelectedValue()
						+ " is Over.\n" + "Please Inform to Concern Team Leader.");
			}
		}
	}

	public void drgNo() {
		drgmodel.clear();
		String str1 = tf3.getText();
		String str2 = "0";

		if (str1.equalsIgnoreCase("Detailer")) {
			str1 = "Detailing";
		}
		if (str1.equalsIgnoreCase("Checker")) {
			str1 = "Checking";
		}

		String str3 = (String) projectlist.getSelectedValue();
		String str4 = String.valueOf(tf1.getText());
		if (rb1.isSelected()) {
			str2 = "Select DISTINCT(Drawing_no) from " + str1 + " Where emp_code='" + str4 + "' and project_no='" + str3
					+ "' and drawing_no not in(select drg_no from Under_Process where dtl_chk='Detail' AND DRG_STATUS LIKE '%' and project_no='"
					+ str3
					+ "') AND drawing_no not in(select drg_no from timesheet where drgstatus LIKE '%' and project_no='"
					+ str3 + "'  and drgstatus like 'Detail' )  ";
		}
		if (rb2.isSelected()) {
			str2 = "Select DISTINCT(Drawing_no) from " + str1 + " Where emp_code='" + str4 + "' and project_no='" + str3
					+ "' and drawing_no not in(select drg_no from Under_Process where dtl_chk='Check' AND DRG_STATUS LIKE '%' and project_no='"
					+ str3
					+ "') AND drawing_no in(select drg_no from timesheet where drgstatus LIKE 'Detail'  and project_no='"
					+ str3
					+ "') AND drawing_no not in(select drg_no from timesheet where drgstatus1 LIKE 'Check'  and project_no='"
					+ str3 + "')  ";
		}

		try {
			stat = con.createStatement();
			rs = stat.executeQuery(str2);
			while (rs.next()) {
				String str5 = new String(rs.getString(1));
				drgmodel.addElement(str5);
			}
		} catch (Exception localException) {
		}
	}

	public void jobType() {
		try {
			con = DBConnectionUtil.getConnection();
			stat = con.createStatement();
			rs = stat.executeQuery("select dtl_chk From projectmanpower WHERE EMP_CODE='" + tf1.getText()
					+ "' and from_date='" + tf.getText() + "' ");
			while (rs.next()) {
				String str = new String(rs.getString(1));
				if (str.equalsIgnoreCase("Detailing")) {
					rb1.setSelected(true);
				}
				if (str.equalsIgnoreCase("Checking")) {
					rb2.setSelected(true);
				}
			}
		} catch (Exception localException) {
		}
	}

	public void details1() {
		try {
			con = DBConnectionUtil.getConnection();
			stat = con.createStatement();
			rs = stat
					.executeQuery("select FULL_NAME, DESIG From HREMP_JOIN WHERE EMP_CODE='" + tf1.getText() + "' ");
			while (rs.next()) {
				String str1 = new String(rs.getString(1));
				String str2 = new String(rs.getString(2));

				tf2.setText(str1);
				tf3.setText(str2);
			}
		} catch (Exception localException) {
		}
		tf4.requestFocus();
	}

	public void details2() {
		try {
			stat = con.createStatement();
			rs = stat.executeQuery("select INTIME, OUTTIME, OT From ATTENDANCE WHERE EMP_CODE='"
					+ tf1.getText() + "' AND DATE_MONTH='" + tf.getText() + "' ");
			while (rs.next()) {
				String str1 = new String(rs.getString(1));
				String str2 = new String(rs.getString(2));
				String str3 = new String(rs.getString(3));

				tf4.setText(str1);
				tf5.setText(str2);
				tf6.setText(str3);
				tf4.requestFocus();
				tf7.requestFocus();
			}
		} catch (Exception localException) {
		}
	}

	public void details3() {
		String str1 = "Detailing";
		if (tf3.getText().equalsIgnoreCase("Checker")) {
			str1 = "Checking";
		}
		if (tf3.getText().equalsIgnoreCase("Detailer")) {
			str1 = "Detailing";
		}

		try {
			stat = con.createStatement();
			rs = stat.executeQuery("select DISTINCT(PROJECT_NO) From " + str1 + " WHERE EMP_CODE ='"
					+ tf1.getText() + "' AND DATE_MONTH='" + tf.getText() + "' AND STATUS='Completed' ");
			while (rs.next()) {
				String str2 = new String(rs.getString(1));
				projectmodel.addElement(str2);
			}
		} catch (Exception localException) {
		}

		projectlist.setSelectedIndex(0);
		itemlist.setSelectedIndex(0);
	}

	public void details4() {
		try {
			stat = con.createStatement();
			rs = stat.executeQuery("select OUT_TIME, IN_TIME From BREAK_TIME WHERE EMP_CODE='" + tf1.getText()
					+ "' AND DATE_MONTH='" + tf.getText() + "' and Remarks='Approved' ");
			while (rs.next()) {
				String str1 = new String(rs.getString(1));
				String str2 = new String(rs.getString(2));

				tf8.setText(str1);
				tf9.setText(str2);
			}
		} catch (Exception localException) {
		}
	}

	public void bfaTime() {
		tbmodel.setNumRows(0);

		try {
			stat = con.createStatement();
			rs = stat
					.executeQuery("select PROJECT_NO, ITEM_CODE,DRG_NO,TOTAL_TIME From BFA_TIME WHERE EMP_CODE='"
							+ tf1.getText() + "' AND DATE_MONTH='" + tf.getText() + "' and remarks='Approved' ");
			while (rs.next()) {
				String str1 = new String(rs.getString(1));
				String str2 = new String(rs.getString(2));
				String str3 = new String(rs.getString(3));
				String str4 = new String(rs.getString(4));

				tbmodel.addRow(new Object[] { str1, str2, str3, str4, "BFA" });
			}
		} catch (Exception localException) {
		}
	}

	public void revisionTime() {
		try {
			stat = con.createStatement();
			rs = stat
					.executeQuery("select PROJECT_NO, ITEM_CODE,DRG_NO,TOTAL_TIME From REVISION_TIME WHERE EMP_CODE='"
							+ tf1.getText() + "' AND DATE_MONTH='" + tf.getText() + "' and remarks='Approved' ");
			while (rs.next()) {
				String str1 = new String(rs.getString(1));
				String str2 = new String(rs.getString(2));
				String str3 = new String(rs.getString(3));
				String str4 = new String(rs.getString(4));

				tbmodel.addRow(new Object[] { str1, str2, str3, str4, "Revision" });
			}
		} catch (Exception localException) {
		}
	}

	public void wasted() {
		try {
			stat = con.createStatement();
			rs = stat
					.executeQuery("select PROJECT_NO, ITEM_CODE,DRG_NO,TOTAL_TIME From wasted WHERE EMP_CODE='"
							+ tf1.getText() + "' AND DATE_MONTH='" + tf.getText() + "' and remarks='Approved' ");
			while (rs.next()) {
				String str1 = new String(rs.getString(1));
				String str2 = new String(rs.getString(2));
				String str3 = new String(rs.getString(3));
				String str4 = new String(rs.getString(4));

				tbmodel.addRow(new Object[] { str1, str2, str3, str4, "Wasted" });
			}
		} catch (Exception localException) {
			System.out.println(localException);
		}
	}

	public void details5() {
		bfaTime();
		revisionTime();
		wasted();
	}

	public void totalTime() {
		int j = Integer.parseInt(tf4.getText());
		int k = Integer.parseInt(tf5.getText());

		int m = k - j;
		totaltf.setText(String.valueOf(timeformat.format(m)));

		String str1 = totaltf.getText();
		String str2 = str1.substring(0, 2);
		String str3 = str1.substring(2, 4);

		int n = Integer.parseInt(str2);
		int i1 = Integer.parseInt(str3);

		if (i1 >= 60) {
			i1 -= 40;
		}

		String str4 = String.valueOf(df1.format(n)) + String.valueOf(df1.format(i1));
		int i2 = Integer.parseInt(str4);

		totaltf.setText(String.valueOf(timeformat.format(i2)));
	}

	public void totalBreak() {
		int j = Integer.parseInt(tf8.getText());
		int k = Integer.parseInt(tf9.getText());

		int m = k - j;
		breaktf.setText(String.valueOf(timeformat.format(m)));

		String str1 = breaktf.getText();
		String str2 = str1.substring(0, 2);
		String str3 = str1.substring(2, 4);

		int n = Integer.parseInt(str2);
		int i1 = Integer.parseInt(str3);

		if (i1 >= 60) {
			i1 -= 40;
		}

		String str4 = String.valueOf(df1.format(n)) + String.valueOf(df1.format(i1));
		int i2 = Integer.parseInt(str4);

		breaktf.setText(String.valueOf(timeformat.format(i2)));
		tf10.requestFocus();
		tf7.requestFocus();
	}

	public void updateRecord() {
		try {
			stat = con.createStatement();
			stat.executeUpdate("update Approval_data set c_status='Updated' WHERE project_no='"
					+ tf11.getText() + "' and Drawing_no='" + bdrgtf.getText() + "' ");
		} catch (Exception localException) {
		}
	}

	public void bfaSave() {
		String str1 = "0";
		String str2 = "0";

		if (rb1.isSelected()) {
			str1 = "Detail";
			str2 = "0";
		}
		if (rb2.isSelected()) {
			str1 = "Detail";
			str2 = "Check";
		}

		int[] arrayOfInt = tb.getSelectedRows();
		String str3 = String.valueOf(tbmodel.getValueAt(arrayOfInt[ska], 0));
		String str4 = String.valueOf(tbmodel.getValueAt(arrayOfInt[ska], 1));
		String str5 = String.valueOf(tbmodel.getValueAt(arrayOfInt[ska], 2));
		String str6 = String.valueOf(tbmodel.getValueAt(arrayOfInt[ska], 3));
		String str7 = String.valueOf(tbmodel.getValueAt(arrayOfInt[ska], 5));

		java.sql.Date localDate = SolDateFormatter.DDMMYYtoSQL(tf.getText());

		try {
			stat = con.createStatement();
			affected = stat.executeUpdate("insert into timesheet values('" + tf.getText() + "','"
					+ tf1.getText() + "','0','0','0','0','" + str3 + "','" + str4 + "','" + str5 + "','" + str7
					+ "','0','0','0','" + str6 + "','" + str3 + "','" + str5 + "','0','0','0','0','0', '0','0','0','"
					+ str4 + "','0','0', '0','" + str1 + "','" + str2 + "','" + localDate + "' ) ");
			if (affected > 0) {
				updateRecord();
				stat.executeUpdate("update BFA_TIME set REMARKS='Saved' WHERE project_no='" + str3
						+ "' and Drg_no='" + str5 + "' and item_code='" + str4 + "' ");
				drgmodel.remove(drglist.getSelectedIndex());
				itemmodel.remove(itemlist.getSelectedIndex());
			}
		} catch (Exception localException) {
			System.out.println(localException);
		}
	}

	public void revSave() {
		String str1 = "0";
		String str2 = "0";

		if (rb1.isSelected()) {
			str1 = "Detail";
			str2 = "0";
		}
		if (rb2.isSelected()) {
			str1 = "Detail";
			str2 = "Check";
		}
		int[] arrayOfInt = tb.getSelectedRows();
		String str3 = String.valueOf(tbmodel.getValueAt(arrayOfInt[ska], 0));
		String str4 = String.valueOf(tbmodel.getValueAt(arrayOfInt[ska], 1));
		String str5 = String.valueOf(tbmodel.getValueAt(arrayOfInt[ska], 2));
		String str6 = String.valueOf(tbmodel.getValueAt(arrayOfInt[ska], 3));
		String str7 = String.valueOf(tbmodel.getValueAt(arrayOfInt[ska], 5));

		java.sql.Date localDate = SolDateFormatter.DDMMYYtoSQL(tf.getText());

		try {
			stat = con.createStatement();
			affected = stat.executeUpdate(
					"insert into timesheet values('" + tf.getText() + "','" + tf1.getText() + "','0','0','0','0','"
							+ str3 + "','" + str4 + "','" + str5 + "','" + str7 + "','0','0','0','0','0','0','" + str6
							+ "','" + str3 + "','" + str5 + "','0','0', '0','0','0','0','" + str4 + "','0', '0','"
							+ str1 + "','" + str2 + "','" + localDate + "' ) ");
			if (affected > 0) {
				updateRecord();
				stat.executeUpdate("update REVISION_TIME set REMARKS='Saved' WHERE project_no='" + str3
						+ "' and Drg_no='" + str5 + "' and item_code='" + str4 + "' ");
			}
		} catch (Exception localException) {
			System.out.println(localException);
		}
	}

	public void wastedSave() {
		String str1 = "0";
		String str2 = "0";

		if (rb1.isSelected()) {
			str1 = "Detail";
			str2 = "0";
		}
		if (rb2.isSelected()) {
			str1 = "Detail";
			str2 = "Check";
		}

		int[] arrayOfInt = tb.getSelectedRows();
		String str3 = String.valueOf(tbmodel.getValueAt(arrayOfInt[ska], 0));
		String str4 = String.valueOf(tbmodel.getValueAt(arrayOfInt[ska], 1));
		String str5 = String.valueOf(tbmodel.getValueAt(arrayOfInt[ska], 2));
		String str6 = String.valueOf(tbmodel.getValueAt(arrayOfInt[ska], 3));
		String str7 = String.valueOf(tbmodel.getValueAt(arrayOfInt[ska], 5));

		java.sql.Date localDate = SolDateFormatter.DDMMYYtoSQL(tf.getText());

		try {
			stat = con.createStatement();
			affected = stat.executeUpdate(
					"insert into timesheet values('" + tf.getText() + "','" + tf1.getText() + "','0','0','0','0','"
							+ str3 + "','" + str4 + "','" + str5 + "','" + str7 + "','0','0','0','0','0','0','" + str6
							+ "','" + str3 + "','" + str5 + "','0','0', '0','0','0','0','" + str4 + "','0', '0','"
							+ str1 + "','" + str2 + "','" + localDate + "' ) ");
			if (affected > 0) {
				updateRecord();
				stat.executeUpdate("update WASTED set REMARKS='Saved' WHERE project_no='" + str3 + "' and Drg_no='"
						+ str5 + "' and item_code='" + str4 + "' ");
			}
		} catch (Exception localException) {
			System.out.println(localException);
		}
	}

	public void saveRecord() {
		String str1 = "0";
		String str2 = "0";

		String str3 = "0";
		String str4 = "0";

		if (rb1.isSelected()) {
			str3 = "Detail";
			str4 = "0";
		}
		if (rb2.isSelected()) {
			str3 = "Detail";
			str4 = "Check";
			str1 = "A";
		}

		String str5 = (String) projectlist.getSelectedValue();
		String str6 = (String) drglist.getSelectedValue();
		String str7 = (String) estmodel.getElementAt(0);
		String str8 = (String) itemmodel.getElementAt(0);

		java.sql.Date localDate =  SolDateFormatter.DDMMYYtoSQL(tf.getText());

		if (itemmodel.getSize() < 0) {
			JOptionPane.showMessageDialog(f, "No Drawing Selected.\nor No Item Selected");
		} else {
			try {

				stat = con.createStatement();
				affected = stat
						.executeUpdate(
								"insert into timesheet values('" + tf.getText() + "','" + tf1.getText()
										+ "','" + tf4.getText() + "','" + tf5.getText() + "','" + tf6
												.getText()
										+ "','" + totaltf.getText() + "', '" + str5 + "','" + str8 + "', '" + str6
										+ "','" + str7 + "', '" + tf8.getText() + "','" + tf9.getText()
										+ "','" + breaktf.getText() + "', '" + tf10.getText() + "','"
										+ tf11.getText() + "','" + bdrgtf.getText() + "','"
										+ tf12.getText() + "','" + tf13.getText() + "','"
										+ rdrgtf.getText() + "','" + tf14.getText() + "','"
										+ westprjtf.getText() + "', '" + tf15.getText() + "','" + str2 + "','"
										+ str1 + "','" + bitmtf.getText() + "','" + ritmtf.getText() + "','"
										+ westdrgtf.getText() + "', '" + westitmtf.getText() + "','" + str3
										+ "','" + str4 + "','" + localDate + "') ");
				if (affected > 0) {
					updateRecord();
					drgmodel.remove(drglist.getSelectedIndex());
					itemmodel.remove(itemlist.getSelectedIndex());
				}
				if (affected <= 0) {
					JOptionPane.showMessageDialog(f, str3 + "ing" + " is AllReady Done");
				}
			} catch (Exception localException) {
				System.out.println(localException);
			}
		}
	}

	public void underProcess() {
		String str1 = "";
		if (rb1.isSelected()) {
			str1 = rb1.getText();
			System.out.println(str1);
		}
		if (rb2.isSelected()) {
			str1 = rb2.getText();
			System.out.println(str1);
		}

		String str4 = (String) projectlist.getSelectedValue();
		String str5 = (String) itemlist.getSelectedValue();
		String str6 = (String) drglist.getSelectedValue();
		try {
			stat = con.createStatement();
			affected = stat
					.executeUpdate("insert into under_process values('" + tf.getText() + "','" + tf1.getText() + "', '"
							+ str4 + "','" + str5 + "', '" + str6 + "','Under Process','" + str1 + "') ");
			if (affected > 0) {
				tbmodel2.addRow(new Object[] { tf.getText(), tf1.getText(), projectlist.getSelectedValue(),
						str5, str6, str1 });
			}
		} catch (Exception localException) {
			JOptionPane.showMessageDialog(f, localException.getMessage().toString());
		}
		drgmodel.clear();
		itemmodel.clear();
	}

	public void showUnderProcess() {
		try {
			con = DBConnectionUtil.getConnection();
			stat = con.createStatement();
			rs = stat.executeQuery("select * from under_process where emp_code='" + tf1.getText()
					+ "' and drg_status = 'Under Process' ");
			while (rs.next()) {
				String str1 = new String(rs.getString(1));
				String str2 = new String(rs.getString(2));
				String str3 = new String(rs.getString(3));
				String str4 = new String(rs.getString(4));
				String str5 = new String(rs.getString(5));
				String str7 = new String(rs.getString(7));

				System.out.println(str1);
				tbmodel2.addRow(new Object[] { str1, str2, str3, str4, str5, str7 });
			}
		} catch (Exception localException) {
			System.out.println(localException);
		}
	}

	public DailyTimeSheet() {
		con = DBConnectionUtil.getConnection();

		skl = new SolCalendar();

		affected = 0;

		column = new String[] { "Project No", "Item Code", "Drg", "Total Time", "B.R.W", "QTD Time" };
		data = new Object[0][];

		column2 = new String[] { "Date", "Emp_Code", "Project No", "Item Code", "Drg No", "DTL/CHK" };
		data2 = new Object[0][];

		df = new DecimalFormat("00.00");
		df1 = new DecimalFormat("00");
		timeformat = new DecimalFormat("0000");

		f = new JInternalFrame("Daily Time Sheet", true, true, true, true);

		projectlabel = new JLabel("Project List");
		projectmodel = new DefaultListModel();
		projectlist = new JList(projectmodel);
		projectsp = new JScrollPane(projectlist);

		itemlabel = new JLabel("Item List");
		itemmodel = new DefaultListModel();
		itemlist = new JList(itemmodel);
		itemsp = new JScrollPane(itemlist);

		drglabel = new JLabel("Drg List");
		drgmodel = new DefaultListModel();
		drglist = new JList(drgmodel);
		drgsp = new JScrollPane(drglist);

		estmodel = new DefaultListModel();
		estlist = new JList(estmodel);
		estsp = new JScrollPane(estlist);

		tbmodel = new DefaultTableModel(data, column);
		tb = new JTable(tbmodel);
		tbsp = new JScrollPane(tb);

		tbmodel2 = new DefaultTableModel(data2, column2);
		tb2 = new JTable(tbmodel2);
		tbsp2 = new JScrollPane(tb2);

		todaylabel = new JLabel("Today");
		todaytf = new JTextField();

		drgl = new JLabel("Drg No *");
		drgtf = new JTextField();

		iteml = new JLabel("ItemCode*");
		itemtf = new JTextField();

		finallabel = new JLabel("Final Date");
		l = new JLabel("Date");
		l1 = new JLabel("Emp Code");
		l2 = new JLabel("Emp Name");
		l3 = new JLabel("Designation");
		l4 = new JLabel("Time In");
		l5 = new JLabel("Time Out");
		totallabel = new JLabel("Total *");
		l6 = new JLabel("OT Status");
		l7 = new JLabel("Project No");
		l8 = new JLabel("Break Out");
		l9 = new JLabel("Break In");
		breaklabel = new JLabel("Total");
		l10 = new JLabel("BFA Time");
		l11 = new JLabel("Project No (BFA)");
		bdrgl = new JLabel("DRG NO*");
		bitml = new JLabel("Item *");
		l12 = new JLabel("Revision Time");
		l13 = new JLabel("Project No (Revision)");
		rdrgl = new JLabel("DRG NO*");
		ritml = new JLabel("Item *");
		l14 = new JLabel("Wasted Time");
		westprjlabel = new JLabel("Project");
		westdrgl = new JLabel("Drg No *");
		westitml = new JLabel("Item *");
		l15 = new JLabel("Reason");

		northlabel = new JLabel(new ImageIcon(ClassLoader.getSystemResource(ApplicationConstants.IMAGE_PATH + "timesheet.gif")));
		westlabel = new JLabel(new ImageIcon(ClassLoader.getSystemResource(ApplicationConstants.IMAGE_PATH + "time.gif")));

		tf2 = new JTextField();
		tf3 = new JTextField();
		tf4 = new JTextField("0");
		tf5 = new JTextField("0");
		totaltf = new JTextField("0");
		tf6 = new JTextField("0");
		tf7 = new JTextField("");
		tf8 = new JTextField("0");
		tf9 = new JTextField("0");
		breaktf = new JTextField("0");
		tf10 = new JTextField("0");
		tf11 = new JTextField("0");
		tf12 = new JTextField("0");
		bdrgtf = new JTextField("0");
		bitmtf = new JTextField("0");
		tf13 = new JTextField("0");
		tf14 = new JTextField("0");
		rdrgtf = new JTextField("0");
		ritmtf = new JTextField("0");
		westprjtf = new JTextField("0");
		westdrgtf = new JTextField("0");
		westitmtf = new JTextField("0");
		tf15 = new JTextArea("0");
		sp = new JScrollPane(tf15);

		savebut = new JButton("Save");
		clearbut = new JButton("Clear");
		closebut = new JButton("Close");

		jpm = new JPopupMenu();
		popm1 = new JMenuItem("Save", new ImageIcon(ClassLoader.getSystemResource(ApplicationConstants.IMAGE_PATH + "check.gif")));

		jpm2 = new JPopupMenu();
		popm2 = new JMenuItem("Completed", new ImageIcon(ClassLoader.getSystemResource(ApplicationConstants.IMAGE_PATH + "check.gif")));

		rb1 = new JRadioButton("Detail", true);
		rb2 = new JRadioButton("Check", false);
		grp = new ButtonGroup();

		rg1 = new JRadioButton("Completed", true);
		rg2 = new JRadioButton("Under Process");
		grp2 = new ButtonGroup();

		centerpanel = new JPanel();
		northpanel = new JPanel();
		westpanel = new JPanel();
		eastpanel = new JPanel();
		southpanel = new JPanel();

		dtlpanel = new JPanel();
		dtlpanel2 = new JPanel();

		dat = new java.util.Date();
		formatter = new SimpleDateFormat("dd/MM/yyyy");

		try {
			dateString = formatter.format(dat);

			todaytf = new JTextField(String.valueOf(dateString));
		} catch (IllegalArgumentException localIllegalArgumentException) {
			System.out.println(localIllegalArgumentException.getMessage());
		}

		tk = Toolkit.getDefaultToolkit();
		ss = tk.getScreenSize();
		fs = f.getSize();

		fo = new java.awt.Font("Tahoma", 1, 11);

		skr = new DailyTimeSheet.ColoredTableCellRenderer();
		skr1 = new DailyTimeSheet.ColoredTableCellRenderer1();

		skitem = "";
		skproject = "";
		skdrg = "";
	}

	public void skItem() {
		try {
			stat = con.createStatement();
			rs = stat.executeQuery("select ITEM_CODE From DRAWINGNO WHERE PROJECT_NO='" + skproject
					+ "' AND DRAWING_NO='" + skdrg + "' ");
			rs.getMetaData().getColumnCount();
			while (rs.next()) {
				skitem = new String(rs.getString(1));
			}
		} catch (Exception localException) {
		}
	}

	public void dynamicSave() {
		tb.getSelectedRow();
		tb.getSelectedRows();
		tb.getRowCount();

		int[] arrayOfInt2 = tb.getSelectedRows();

		for (ska = 0; ska < 1; ska += 1) {
			String str = String.valueOf(tbmodel.getValueAt(arrayOfInt2[ska], 4));

			if (str.equalsIgnoreCase("BFA")) {
				bfaSave();
			}
			if (str.equalsIgnoreCase("Revision")) {
				revSave();
			}
			if (str.equalsIgnoreCase("Wasted")) {
				wastedSave();
			}

			tbmodel.removeRow(arrayOfInt2[ska]);
		}
	}

	public void qtdTime() {
		int j = tb.getSelectedRow();
		int[] arrayOfInt = tb.getSelectedRows();
		int k = arrayOfInt.length + j - 1;
		for (; j <= k; j++) {
			String str1 = String.valueOf(tbmodel.getValueAt(j, 0));
			String str2 = String.valueOf(tbmodel.getValueAt(j, 1));
			String str3 = String.valueOf(tbmodel.getValueAt(j, 2));

			String str4 = tf3.getText();
			if (str4.equalsIgnoreCase("Detailer")) {
				str4 = "DTL_TIME";
			}
			if (str4.equalsIgnoreCase("Checker")) {
				str4 = "CHK_TIME";
			}

			try {
				stat = con.createStatement();
				rs = stat.executeQuery("select " + str4 + " From DRAWINGNO WHERE PROJECT_NO='" + str1
						+ "' and item_code='" + str2 + "' AND DRAWING_NO='" + str3 + "' ");
				rs.getMetaData().getColumnCount();
				while (rs.next()) {
					String str5 = new String(rs.getString(1));
					tbmodel.setValueAt(str5, j, 5);
				}
			} catch (Exception localException) {
			}
		}
	}

	public void itemCode(String paramString) {
		itemmodel.clear();

		String str1 = tf3.getText();
		if (str1.equalsIgnoreCase("Detailer")) {
			str1 = "DTL_TIME";
		}
		if (str1.equalsIgnoreCase("Checker")) {
			str1 = "CHK_TIME";
		}

		try {
			stat = con.createStatement();
			rs = stat.executeQuery("select ITEM_CODE," + str1 + " From DRAWINGNO WHERE PROJECT_NO='"
					+ projectlist.getSelectedValue() + "' AND DRAWING_NO='" + paramString + "' ");
			rs.getMetaData().getColumnCount();

			int k = rs.getRow();
			if (k == -1) {
				JOptionPane.showMessageDialog(f, "Invalid Drg NO");
			}

			while (rs.next()) {
				String str2 = new String(rs.getString(1));
				String str3 = new String(rs.getString(2));

				itemmodel.addElement(str2);
				estmodel.addElement(str3);
				drgmodel.addElement(drgtf.getText());
			}
		} catch (Exception localException) {
		}

		drgtf.setText("");
		itemlist.setSelectedIndex(0);
	}

	public void actionPerformed(ActionEvent paramActionEvent) {
		if (paramActionEvent.getSource() == savebut) {
			if (rg1.isSelected()) {
				totalBreak();
				bfaTime();
				wasted();
				saveRecord();
			}
			if (rg2.isSelected()) {
				underProcess();
			}
		}
		if ((paramActionEvent.getSource() == tf) || (paramActionEvent.getSource() == tf1)) {
			projectlist.setBackground(Color.pink);
			details1();
			jobType();
			projectNo();
			tf4.requestFocus();
		}
		if (paramActionEvent.getSource() == tf7) {
			projectmodel.addElement(tf7.getText());
			tf7.setText("");
			drgtf.requestFocus();
		}
		if (paramActionEvent.getSource() == drgtf) {
			String str1 = drgtf.getText();
			itemCode(str1);
		}

		if (paramActionEvent.getSource() == itemtf) {
			itemmodel.addElement(itemtf.getText());
			itemtf.setText("");
		}
		if (paramActionEvent.getSource() == clearbut) {
			tf7.setText("");
			drgtf.setText("");
			tf7.requestFocus();
			projectmodel.clear();
			itemmodel.clear();
			drgmodel.clear();
			estmodel.clear();
		}
		if (paramActionEvent.getSource() == closebut) {
			f.setVisible(false);
		}

		if (paramActionEvent.getSource() == popm1) {
			dynamicSave();
		}
		if (paramActionEvent.getSource() == popm2) {

			int j = tb2.getSelectedRow();
			String str2 = String.valueOf(tbmodel2.getValueAt(j, 2));
			String str3 = String.valueOf(tbmodel2.getValueAt(j, 3));
			String str4 = String.valueOf(tbmodel2.getValueAt(j, 4));

			try {
				con = DBConnectionUtil.getConnection();
				stat = con.createStatement();
				affected = stat
						.executeUpdate("UPDATE UNDER_PROCESS SET DRG_STATUS ='Completed' WHERE PROJECT_NO='" + str2
								+ "' and item_code='" + str3 + "' AND DRG_NO='" + str4 + "' ");

				if (affected > 0) {
					int k = tb2.getSelectedRow();
					tbmodel2.removeRow(k);
					saveRecord();
				}
			} catch (Exception localException) {
				JOptionPane.showMessageDialog(f, localException.getMessage().toString());
			}
		}

		if (paramActionEvent.getSource() == rb1) {
			tf3.setText("Detailer");
			drgNo();
		}
		if (paramActionEvent.getSource() == rb2) {
			tf3.setText("Checker");
			drgNo();
		}
		if ((paramActionEvent.getSource() != rg1) ||

		(paramActionEvent.getSource() == rg2)) {
		}
	}

	public void valueChanged(ListSelectionEvent paramListSelectionEvent) {
		if (paramListSelectionEvent.getSource() == projectlist) {
			finalDate();
		}
		if ((paramListSelectionEvent.getSource() != itemlist) ||

		(paramListSelectionEvent.getSource() == drglist)) {
			String str = (String) drglist.getSelectedValue();
			itemCode(str);
		}
	}

	public void focusGained(FocusEvent paramFocusEvent) {
		if ((paramFocusEvent.getSource() != tf1) ||

		(paramFocusEvent.getSource() == tf4)) {
			details2();
			details3();
			details4();
			details5();
			totalTime();
			totalBreak();
		}
		if (paramFocusEvent.getSource() == tf6) {
			tf6.selectAll();
		}
		if (paramFocusEvent.getSource() == tf7) {
			tf7.selectAll();
		}
		if (paramFocusEvent.getSource() == drgtf) {
			drgtf.selectAll();
		}
		if (paramFocusEvent.getSource() == tf8) {
			tf8.selectAll();
		}
		if (paramFocusEvent.getSource() == tf9) {
			tf9.selectAll();
		}
		if (paramFocusEvent.getSource() == tf10) {
			tf10.selectAll();
		}
		if (paramFocusEvent.getSource() == tf11) {
			tf11.selectAll();
		}
		if (paramFocusEvent.getSource() == tf12) {
			tf12.selectAll();
		}
		if (paramFocusEvent.getSource() == tf13) {
			tf13.selectAll();
		}
		if (paramFocusEvent.getSource() == tf14) {
			wasted();
			tf14.selectAll();
		}
		if (paramFocusEvent.getSource() == tf15) {
			tf15.selectAll();
		}
		if (paramFocusEvent.getSource() == bdrgtf) {
			bdrgtf.selectAll();
		}
		if (paramFocusEvent.getSource() == rdrgtf) {
			rdrgtf.selectAll();
		}
		if (paramFocusEvent.getSource() == westprjtf) {
			westprjtf.selectAll();
		}
	}

	public void focusLost(FocusEvent paramFocusEvent) {
		if (((paramFocusEvent.getSource() == tf1) || (paramFocusEvent.getSource() != tf)) ||

		(paramFocusEvent.getSource() == tf4)) {
			totalBreak();
			totalTime();
		}

		if (paramFocusEvent.getSource() == tf6) {
			totalTime();
		}

		if (paramFocusEvent.getSource() == tf11) {
			bfaTime();
		}
		if (paramFocusEvent.getSource() == tf7) {
			if (tf7.getText().equals("0")) {
				itemmodel.clear();
				drgmodel.clear();
				estmodel.clear();
				projectmodel.addElement("100001");
				itemmodel.addElement("AAA");
				drgmodel.addElement("S100");
				estmodel.addElement("01");
				tf10.requestFocus();

				tf10.setEditable(true);
				tf11.setEditable(true);
				bdrgtf.setEditable(true);
				bitmtf.setEditable(true);
				tf12.setEditable(true);
				tf13.setEditable(true);
				tf14.setEditable(true);
				rdrgtf.setEditable(true);
				ritmtf.setEditable(true);

				westprjtf.setEditable(true);
				westdrgtf.setEditable(true);
				westitmtf.setEditable(true);
			}
		}

		if (paramFocusEvent.getSource() == tf9) {
			totalBreak();
		}

		if (paramFocusEvent.getSource() == bdrgtf) {
			skproject = tf11.getText();
			skdrg = bdrgtf.getText();
			skItem();
			bitmtf.setText(skitem);
			skitem = "";
		}
		if (paramFocusEvent.getSource() == rdrgtf) {
			skproject = tf13.getText();
			skdrg = rdrgtf.getText();
			skItem();
			ritmtf.setText(skitem);
			skitem = "";
		}
		if (paramFocusEvent.getSource() == westdrgtf) {
			skproject = westprjtf.getText();
			skdrg = westdrgtf.getText();
			skItem();
			westitmtf.setText(skitem);
			skitem = "";
		}
	}

	public void mousePressed(MouseEvent paramMouseEvent) {
		if (paramMouseEvent.getSource() == tb) {
			if (SwingUtilities.isRightMouseButton(paramMouseEvent)) {
				jpm.show((javax.swing.JComponent) paramMouseEvent.getSource(), paramMouseEvent.getX(),
						paramMouseEvent.getY());
			}
			qtdTime();
		}
		if (paramMouseEvent.getSource() == tb) {
			qtdTime();
		}
	}

	public void mouseReleased(MouseEvent paramMouseEvent) {
		if (paramMouseEvent.getSource() == tb) {
			qtdTime();
		}

		if (paramMouseEvent.getSource() == tb2) {
			if (SwingUtilities.isRightMouseButton(paramMouseEvent)) {
				jpm2.show((javax.swing.JComponent) paramMouseEvent.getSource(), paramMouseEvent.getX(),
						paramMouseEvent.getY());
			}

			if (SwingUtilities.isLeftMouseButton(paramMouseEvent)) {

				drgmodel.clear();
				itemmodel.clear();
				projectmodel.clear();
				estmodel.clear();

				int j = tb2.getSelectedRow();
				String str1 = String.valueOf(tbmodel2.getValueAt(j, 2));
				String str2 = String.valueOf(tbmodel2.getValueAt(j, 3));
				String str3 = String.valueOf(tbmodel2.getValueAt(j, 4));
				String str4 = String.valueOf(tbmodel2.getValueAt(j, 5));

				projectmodel.addElement(str1);
				projectlist.setSelectedIndex(0);
				itemmodel.addElement(str2);
				itemlist.setSelectedIndex(0);
				drgmodel.addElement(str3);
				drglist.setSelectedIndex(0);
				estmodel.addElement(eststr);
				estlist.setSelectedIndex(0);

				if (str4.equalsIgnoreCase("Detail")) {
					rb1.setSelected(true);
				}
				if (str4.equalsIgnoreCase("Check")) {
					rb2.setSelected(true);
				}
			}
		}
	}

	public void mouseClicked(MouseEvent paramMouseEvent) {
		if (paramMouseEvent.getSource() == projectlist) {
			drgNo();
		}

		if (paramMouseEvent.getSource() == tf) {
			if (SwingUtilities.isRightMouseButton(paramMouseEvent)) {
				skl.showCalendar();
				skl.getDate(tf);
			}
		}
	}

	public void keyReleased(KeyEvent paramKeyEvent) {
		int j = paramKeyEvent.getKeyCode();

		if (j == 112) {
			new TechMainFrame();
			EmpWorkAlloted localEmpWorkAlloted = new EmpWorkAlloted();
			TechMainFrame.desktop.add(localEmpWorkAlloted.f);
			TechMainFrame.desktop.moveToFront(localEmpWorkAlloted.f);
			localEmpWorkAlloted.px("true");
			EmpWorkAlloted.insertbut.setEnabled(true);

			EmpWorkAlloted.tf3.setText(tf.getText());
			EmpWorkAlloted.tf4.setText(tf.getText());
			EmpWorkAlloted.tf1.setSelectedItem(tf1.getText());

			localEmpWorkAlloted.dateDiff();
			localEmpWorkAlloted.workDetails();
			localEmpWorkAlloted.jobDetails();
		}
	}

	public void mouseEntered(MouseEvent paramMouseEvent) {
	}

	public void mouseExited(MouseEvent paramMouseEvent) {
	}

	public void keyPressed(KeyEvent paramKeyEvent) {
	}

	public void keyTyped(KeyEvent paramKeyEvent) {
	}
}
