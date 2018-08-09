package com.sol.erp;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import com.sol.erp.constants.ApplicationConstants;
import com.sol.erp.ui.custom.SolTableModel;
import com.sol.erp.util.DBConnectionUtil;
import com.sol.erp.util.formater.SolDateFormatter;

public class RevLog implements ActionListener, FocusListener, ItemListener, MouseListener {
	
	Connection con;
	PreparedStatement stat;
	ResultSet rs;
	int affected;
	String[] itemcolumn;
	Object[][] itemdata;
	String[] drgcolumn;
	Object[][] drgdata;
	String[] column;
	Object[][] data;
	DecimalFormat df;
	DecimalFormat df1;

	public RevLog() {

		affected = 0;

		itemcolumn = new String[] { "Item Code" };
		itemdata = new Object[0][];

		drgcolumn = new String[] { "Drg No" };
		drgdata = new Object[0][];

		column = new String[] { "Item Code", "Drg No" };
		data = new Object[0][];

		df = new DecimalFormat("00.00");
		df1 = new DecimalFormat("00");
		timeformat = new DecimalFormat("0000");

		itemmodel = new DefaultTableModel(itemdata, itemcolumn);
		drgmodel = new DefaultTableModel(drgdata, drgcolumn);
		model = new DefaultTableModel(data, column);

		itemtb = new JTable(itemmodel);
		itemsp = new JScrollPane(itemtb);

		drgtb = new JTable(drgmodel);
		drgsp = new JScrollPane(drgtb);

		tb = new JTable(model);
		sp = new JScrollPane(tb);

		f = new JInternalFrame("Revision Log", true, true, true, true);
		l = new JLabel("Date");
		l1 = new JLabel("Project No");
		l2 = new JLabel("Project Name");
		l3 = new JLabel("Client Name");
		l4 = new JLabel("Delta #");
		l5 = new JLabel("Description");
		l6 = new JLabel("Affected Item");
		l7 = new JLabel("Affected Drg");
		l8 = new JLabel("Extra Hrs");

		northlabel = new JLabel("Revision LOG Form");
		westlabel = new JLabel(new ImageIcon(ClassLoader.getSystemResource(ApplicationConstants.IMAGE_PATH + "left3.gif")));

		tf = new JTextField();
		tf1 = new JComboBox();
		tf2 = new JTextField();
		tf3 = new JTextField();
		tf4 = new JTextField("0");
		tf5 = new JTextPane();
		tfsp5 = new JScrollPane(tf5);
		tf6 = new JComboBox();
		tf7 = new JComboBox();
		tf8 = new JTextField();

		addbut = new JButton("Add Affected Item");
		savebut = new JButton("Save");
		checkbut = new JButton("Check");
		updatebut = new JButton("Update");
		deletebut = new JButton("DELETE");

		jpm1 = new JPopupMenu();
		popm1 = new JMenuItem("Set Affected", new ImageIcon(ClassLoader.getSystemResource(ApplicationConstants.IMAGE_PATH + "check.gif")));

		jpm2 = new JPopupMenu();
		popm2 = new JMenuItem("Remove");
		popm3 = new JMenuItem("Remove All");

		centerpanel = new JPanel();
		northpanel = new JPanel();
		westpanel = new JPanel();
		southpanel = new JPanel();

		dat = new java.util.Date();
		formatter = new SimpleDateFormat("dd/MM/yyyy");

		try {
			dateString = formatter.format(dat);
			tf = new JTextField(String.valueOf(dateString));
		} catch (IllegalArgumentException localIllegalArgumentException) {
			System.out.println(localIllegalArgumentException.getMessage());
		}

		tk = Toolkit.getDefaultToolkit();
		ss = tk.getScreenSize();
		fs = f.getSize();

		fo = new Font("Tahoma", 1, 11);
		bigfo = new Font("Tahoma", 1, 35);

	}

	DecimalFormat timeformat;
	DefaultTableModel itemmodel;

	public void px() {
		Container localContainer = f.getContentPane();
		localContainer.setLayout(new BorderLayout());
		centerpanel.setLayout(null);
		northpanel.setLayout(new BorderLayout());
		westpanel.setLayout(new BorderLayout());

		northpanel.add(northlabel, "Center");
		westpanel.add(westlabel, "Center");
		northlabel.setForeground(Color.red);
		northlabel.setFont(bigfo);

		SolTableModel.decorateTable(tb);
		SolTableModel.decorateTable(itemtb);
		SolTableModel.decorateTable(drgtb);

		jpm1.add(popm1);
		jpm2.add(popm2);
		jpm2.add(popm3);

		centerpanel.setBackground(new Color(60, 130, 130));
		l.setForeground(Color.white);
		l1.setForeground(Color.white);
		l2.setForeground(Color.white);
		l3.setForeground(Color.white);
		l4.setForeground(Color.white);
		l5.setForeground(Color.white);
		l6.setForeground(Color.white);
		l7.setForeground(Color.white);
		l8.setForeground(Color.white);

		tf.setFont(fo);
		tf.setEditable(true);
		tf1.setFont(fo);
		tf2.setFont(fo);
		tf2.setEditable(false);
		tf3.setFont(fo);
		tf3.setEditable(false);
		tf4.setFont(fo);
		tfsp5.setFont(fo);
		tf5.setFont(fo);
		tf6.setFont(fo);
		tf6.setEditable(false);
		tf7.setFont(fo);
		tf8.setFont(fo);

		northpanel.setBackground(Color.white);
		westpanel.setBackground(Color.white);

		northpanel.setPreferredSize(new Dimension(100, 70));
		westpanel.setPreferredSize(new Dimension(150, 70));

		l.setBounds(30, 30, 150, 20);
		tf.setBounds(200, 30, 90, 20);
		l1.setBounds(300, 30, 100, 20);
		tf1.setBounds(400, 30, 90, 20);
		sp.setBounds(500, 30, 250, 230);
		l2.setBounds(30, 55, 150, 20);
		tf2.setBounds(200, 55, 290, 20);
		l3.setBounds(30, 80, 150, 20);
		tf3.setBounds(200, 80, 290, 20);
		l4.setBounds(30, 105, 150, 20);
		tf4.setBounds(200, 105, 90, 20);
		l5.setBounds(30, 130, 150, 20);
		tfsp5.setBounds(200, 130, 290, 80);

		l6.setBounds(30, 215, 150, 20);
		itemsp.setBounds(200, 215, 90, 120);
		l7.setBounds(300, 215, 100, 20);
		drgsp.setBounds(400, 215, 90, 120);
		l8.setBounds(30, 340, 150, 20);
		tf8.setBounds(200, 340, 90, 20);
		addbut.setBounds(300, 340, 150, 20);

		savebut.setBounds(150, 290, 100, 25);
		deletebut.setBounds(270, 290, 100, 25);

		savebut.setMnemonic(83);
		deletebut.setMnemonic(67);

		tf1.setEditable(true);
		centerpanel.add(l);
		centerpanel.add(tf);
		centerpanel.add(sp);
		centerpanel.add(l1);
		centerpanel.add(tf1);
		centerpanel.add(l2);
		centerpanel.add(tf2);
		centerpanel.add(l3);
		centerpanel.add(tf3);
		centerpanel.add(l4);
		centerpanel.add(tf4);
		centerpanel.add(l5);
		centerpanel.add(tfsp5);
		centerpanel.add(l6);
		centerpanel.add(drgsp);
		centerpanel.add(l7);
		centerpanel.add(itemsp);
		centerpanel.add(l8);
		centerpanel.add(tf8);
		centerpanel.add(addbut);

		southpanel.add(checkbut);
		southpanel.add(savebut);
		southpanel.add(updatebut);
		southpanel.add(deletebut);

		addbut.addActionListener(this);
		checkbut.addActionListener(this);
		savebut.addActionListener(this);
		updatebut.addActionListener(this);
		deletebut.addActionListener(this);

		tf1.addFocusListener(this);
		tf4.addFocusListener(this);

		tf1.addItemListener(this);
		tf6.addItemListener(this);
		tf4.addActionListener(this);

		popm1.addActionListener(this);
		popm2.addActionListener(this);
		popm3.addActionListener(this);

		itemtb.addMouseListener(this);
		drgtb.addMouseListener(this);
		tb.addMouseListener(this);

		localContainer.add(northpanel, "North");
		localContainer.add(centerpanel, "Center");
		localContainer.add(westpanel, "West");
		localContainer.add(southpanel, "South");

		f.setSize(930, 550);

		f.setVisible(true);
	}

	DefaultTableModel drgmodel;
	DefaultTableModel model;
	JTable itemtb;

	public void projectNo() {
		tf1.addItem("Select");
		try {
			con = DBConnectionUtil.getConnection();
			stat = con.prepareStatement("select distinct(project_no) From estimation_MP");
			rs = stat.executeQuery();
			while (rs.next()) {
				String str = new String(rs.getString(1));
				tf1.addItem(str);
			}
		} catch (Exception localException) {
			JOptionPane.showMessageDialog(f, localException.getMessage().toString());
		}
	}

	JScrollPane itemsp;
	JTable drgtb;

	public void projectDetails() {
		try {
			con = DBConnectionUtil.getConnection();
			stat = con.prepareStatement(
					"select max(project_name), max(client_name) From estimation_MP WHERE project_no=? ");
			stat.setString(1, String.valueOf(tf1.getSelectedItem()));
			rs = stat.executeQuery();
			rs.getMetaData().getColumnCount();
			while (rs.next()) {
				String str1 = new String(rs.getString(1));
				String str2 = new String(rs.getString(2));

				tf2.setText(str1);
				tf3.setText(str2);
				tf4.requestFocus();
			}
		} catch (Exception localException) {
			JOptionPane.showMessageDialog(f, localException.getMessage().toString());
		}
	}

	JScrollPane drgsp;

	public void maxNo() {
		tf4.setText("");
		try {
			stat = con.prepareStatement("select MAX(delta_no) from REV_LOG");
			rs = stat.executeQuery();
			rs.getMetaData().getColumnCount();
			while (rs.next()) {
				int j = rs.getInt(1) + 1;
				tf4.setText(String.valueOf(j));
			}
		} catch (Exception localException) {
			JOptionPane.showMessageDialog(f, localException.getMessage().toString());
		}
	}

	JTable tb;
	JScrollPane sp;
	JInternalFrame f;
	JLabel l;
	JLabel l1;
	JLabel l2;
	JLabel l3;
	JLabel l4;

	public void check() {
		try {
			PreparedStatement localPreparedStatement = con.prepareStatement(
					"select PROJECT_NO, PROJECT_NAME, CLIENT_NAME, DESC1, EXTRA_HRS from REV_LOG where DELTA_NO = ? and PROJECT_NO = ? ");
			localPreparedStatement.setString(1, tf4.getText());
			localPreparedStatement.setString(2, String.valueOf(tf1.getSelectedItem()));
			ResultSet localResultSet = localPreparedStatement.executeQuery();

			while (localResultSet.next()) {
				String str1 = new String(localResultSet.getString(1));
				String str2 = new String(localResultSet.getString(2));
				String str3 = new String(localResultSet.getString(3));
				String str4 = new String(localResultSet.getString(4));
				String str5 = new String(localResultSet.getString(5));

				tf1.setSelectedItem(str1);
				tf2.setText(str2);
				tf3.setText(str3);
				tf5.setText(str4);
				tf8.setText(str5);
			}
		} catch (Exception localException) {
			JOptionPane.showMessageDialog(f, localException.getMessage().toString());
		}
	}

	JLabel l5;
	JLabel l6;

	public void itemDetails() {
		itemmodel.setNumRows(0);
		itemmodel.addRow(new Object[] { "0" });
		try {
			con = DBConnectionUtil.getConnection();
			stat = con.prepareStatement(
					"select item_code From Estimation WHERE Project_no ='" + tf1.getSelectedItem() + "' ");
			rs = stat.executeQuery();
			rs.getMetaData().getColumnCount();
			while (rs.next()) {
				String str = new String(rs.getString(1));
				itemmodel.addRow(new Object[] { str });
			}
		} catch (Exception localException) {
			JOptionPane.showMessageDialog(f, localException.getMessage().toString());
		}
	}

	JLabel l7;
	JLabel l8;
	JLabel northlabel;

	public void drgDetails() {
		drgmodel.setNumRows(0);
		drgmodel.addRow(new Object[] { "0" });

		int i = itemtb.getSelectedRow();
		String str1 = String.valueOf(itemmodel.getValueAt(i, 0));
		try {
			con = DBConnectionUtil.getConnection();
			stat = con
					.prepareStatement("select drawing_no From drawingno WHERE Project_no = ? and item_code= ? ");
			stat.setString(1, String.valueOf(tf1.getSelectedItem()));
			stat.setString(2, str1);
			rs = stat.executeQuery();
			rs.getMetaData().getColumnCount();
			while (rs.next()) {
				String str2 = new String(rs.getString(1));
				drgmodel.addRow(new Object[] { str2 });
			}
		} catch (Exception localException) {
			JOptionPane.showMessageDialog(f, localException.getMessage().toString());
		}
	}

	JLabel westlabel;
	JTextField tf;
	JComboBox tf1;
	JTextField tf2;

	public void addAffectedItem() {
		int i = itemtb.getSelectedRow();
		String str1 = String.valueOf(itemmodel.getValueAt(i, 0));
		String str2 = "sk";

		int[] arrayOfInt = drgtb.getSelectedRows();

		for (int j = 0; j < arrayOfInt.length; j++) {
			str2 = String.valueOf(drgmodel.getValueAt(arrayOfInt[j], 0));
			model.addRow(new Object[] { str1, str2 });
		}
	}

	JTextField tf3;
	JTextField tf4;
	JTextPane tf5;
	JScrollPane tfsp5;

	public void saveRecord() {
		java.sql.Date localDate = SolDateFormatter.DDMMYYtoSQL(tf.getText());
		try {
			stat = con.prepareStatement("insert into REV_LOG values(?,?,?,?,?,?,?,?,?)  ");
			stat.setDate(1, localDate);
			stat.setString(2, String.valueOf(tf1.getSelectedItem()));
			stat.setString(3, tf2.getText());
			stat.setString(4, tf3.getText());
			stat.setString(5, tf4.getText());
			stat.setString(6, tf5.getText());
			stat.setString(7, "0");
			stat.setString(8, "0");
			stat.setFloat(9, Float.parseFloat(tf8.getText()));
			affected = stat.executeUpdate();

			if (affected > 0) {
				int i = tb.getRowCount();
				for (int j = 0; j < i; j++) {
					String str1 = String.valueOf(model.getValueAt(j, 0));
					String str2 = String.valueOf(model.getValueAt(j, 1));

					try {
						stat = con.prepareStatement("insert into DELTA values(?,?,?,?,?) ");
						stat.setString(1, String.valueOf(tf1.getSelectedItem()));
						stat.setString(2, tf4.getText());
						stat.setString(3, str1);
						stat.setString(4, str2);
						stat.setFloat(5, Float.parseFloat(tf8.getText()));
						affected = stat.executeUpdate();
						if (affected > 0) {
							model.setValueAt("Saved", j, 0);
						}
					} catch (Exception localException2) {
						JOptionPane.showMessageDialog(f, localException2.getMessage().toString());
					}
				}
				JOptionPane.showMessageDialog(f, "Revision Log Updated");
			}
		} catch (Exception localException1) {
			if (localException1.getMessage().toString().equalsIgnoreCase("General error")) {
				JOptionPane.showMessageDialog(f, "Data Not Saved.");
			} else {
				JOptionPane.showMessageDialog(f, localException1.getMessage().toString());
			}
		}
	}

	JComboBox tf6;
	JComboBox tf7;
	JTextField tf8;
	JButton addbut;
	JButton savebut;

	public void deleteRecord() {
		try {
			stat = con.prepareStatement("delete from REV_LOG  where DELTA_NO ='" + tf4.getText()
					+ "' and project_no='" + tf1.getSelectedItem() + "' ");
			affected = stat.executeUpdate();

			if (affected > 0) {
				affected = stat.executeUpdate("delete from DELTA where DELTA_NO ='" + tf4.getText()
						+ "' and project_no='" + tf1.getSelectedItem() + "' ");
				if (affected > 0) {
					JOptionPane.showMessageDialog(f, "Log Deleted");
				}
			}
		} catch (Exception localException) {
			JOptionPane.showMessageDialog(f, localException.getMessage().toString());
		}
	}

	JButton checkbut;
	JButton updatebut;
	JButton deletebut;
	JPopupMenu jpm1;
	JMenuItem popm1;
	JPopupMenu jpm2;
	JMenuItem popm2;

	public void updateRecord() {
		try {
			stat = con
					.prepareStatement("update REV_LOG  set desc1 ='" + tf5.getText() + "' where DELTA_NO ='"
							+ tf4.getText() + "' and project_no='" + tf1.getSelectedItem() + "' ");
			affected = stat.executeUpdate();

			if (affected > 0) {
				affected = stat.executeUpdate("delete from DELTA where DELTA_NO ='" + tf4.getText()
						+ "' and project_no='" + tf1.getSelectedItem() + "' ");
				if (affected > 0) {
					JOptionPane.showMessageDialog(f, "Log Updated");
				}
			}
		} catch (Exception localException) {
			JOptionPane.showMessageDialog(f, localException.getMessage().toString());
		}
	}

	public void setAffected() {
		int i = itemtb.getSelectedRow();
		int[] arrayOfInt = drgtb.getSelectedRows();

		String str1 = String.valueOf(itemmodel.getValueAt(i, 0));

		for (int j = 0; j < arrayOfInt.length; j++) {
			String str2 = String.valueOf(drgmodel.getValueAt(arrayOfInt[j], 0));
			model.addRow(new Object[] { str1, str2 });
		}
	}

	public void actionPerformed(ActionEvent paramActionEvent) {
		if ((paramActionEvent.getSource() == savebut) || (paramActionEvent.getSource() == tf8)) {
			saveRecord();
		}
		if (paramActionEvent.getSource() == addbut) {
			addAffectedItem();
		}
		if (paramActionEvent.getSource() == checkbut) {
			check();
		}
		if (paramActionEvent.getSource() == deletebut) {
			deleteRecord();
		}
		if (paramActionEvent.getSource() == updatebut) {
			updateRecord();
		}

		if (paramActionEvent.getSource() == popm1) {
			setAffected();
		}
		if (paramActionEvent.getSource() == popm3) {
			model.setNumRows(0);
		}

		if (paramActionEvent.getSource() == popm2) {
			int[] arrayOfInt = tb.getSelectedRows();

			for (int i = 0; i < arrayOfInt.length; i++) {
				model.removeRow(arrayOfInt[i]);
			}
		}
	}

	JMenuItem popm3;
	JPanel centerpanel;
	JPanel northpanel;
	JPanel westpanel;
	JPanel southpanel;
	java.util.Date dat;
	SimpleDateFormat formatter;

	public void itemStateChanged(ItemEvent paramItemEvent) {
		if (paramItemEvent.getSource() == tf1) {
			projectDetails();
			itemDetails();
		}

		if (paramItemEvent.getSource() == tf6) {
			drgDetails();
		}
		if (paramItemEvent.getSource() == tf4) {
		}
	}

	public void focusGained(FocusEvent paramFocusEvent) {
	}

	String dateString;
	Toolkit tk;
	Dimension ss;
	Dimension fs;

	public void focusLost(FocusEvent paramFocusEvent) {
		if (paramFocusEvent.getSource() == tf1) {
			projectDetails();
		}
		if (paramFocusEvent.getSource() == tf4) {
		}
	}

	Font fo;
	Font bigfo;

	public void mousePressed(MouseEvent paramMouseEvent) {
		if (paramMouseEvent.getSource() == itemtb) {
			drgDetails();
		}

		if (paramMouseEvent.getSource() == drgtb) {
			if (SwingUtilities.isRightMouseButton(paramMouseEvent)) {
				jpm1.show((JComponent) paramMouseEvent.getSource(), paramMouseEvent.getX(),
						paramMouseEvent.getY());
			}
		}

		if (paramMouseEvent.getSource() == tb) {
			if (SwingUtilities.isRightMouseButton(paramMouseEvent)) {
				jpm2.show((JComponent) paramMouseEvent.getSource(), paramMouseEvent.getX(),
						paramMouseEvent.getY());
			}
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
