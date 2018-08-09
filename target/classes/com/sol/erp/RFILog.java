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
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

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
import com.sol.erp.util.DBConnectionUtil;

public class RFILog implements ActionListener, FocusListener, ItemListener, KeyListener, MouseListener {
	
	Connection con;
	Statement stat;
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
	DecimalFormat timeformat;
	DefaultTableModel itemmodel;
	DefaultTableModel drgmodel;
	DefaultTableModel model;
	JTable itemtb;
	JScrollPane itemsp;
	JTable drgtb;
	JScrollPane drgsp;
	JTable tb;
	JScrollPane sp;
	JInternalFrame f;
	JLabel l;

	public RFILog() {
		

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

		f = new JInternalFrame("RFI Log Form", true, true, true, true);
		l = new JLabel("Date");
		l1 = new JLabel("Project No");
		l2 = new JLabel("Project Name");
		l3 = new JLabel("Client Name");
		l4 = new JLabel("SOL #");
		l5 = new JLabel("Client #");
		l6 = new JLabel("Submit Date");
		l7 = new JLabel("Return Date");
		l8 = new JLabel("Question");
		l9 = new JLabel("Answer");
		l10 = new JLabel("Affected Items");
		l11 = new JLabel("Affected Drg");
		l12 = new JLabel("Extra Hrs");
		l13 = new JLabel("Image No");

		northlabel = new JLabel("RFI LOG Form");
		westlabel = new JLabel(new ImageIcon(ClassLoader.getSystemResource(ApplicationConstants.IMAGE_PATH + "left1.gif")));

		tf = new JTextField();
		tf1 = new JComboBox();
		tf2 = new JTextField();
		tf3 = new JTextField("0");
		tf4 = new JTextField();
		tf5 = new JTextField("0");
		tf6 = new JTextField();
		tf7 = new JTextField("0");
		tfsp8 = new JTextPane();
		tf8 = new JScrollPane(tfsp8);
		tfsp9 = new JTextPane();
		tf9 = new JScrollPane(tfsp9);
		tf10 = new JComboBox();
		tf11 = new JComboBox();
		tf12 = new JTextField("0");
		tf13 = new JTextField("0");

		jpm1 = new JPopupMenu();
		popm1 = new JMenuItem("Set Affected", new ImageIcon(ClassLoader.getSystemResource(ApplicationConstants.IMAGE_PATH + "check.gif")));

		jpm2 = new JPopupMenu();
		popm2 = new JMenuItem("Remove", new ImageIcon(ClassLoader.getSystemResource(ApplicationConstants.IMAGE_PATH + "false.gif")));
		popm3 = new JMenuItem("Remove All", new ImageIcon(ClassLoader.getSystemResource(ApplicationConstants.IMAGE_PATH + "false.gif")));

		newbut = new JButton("New");
		checkbut = new JButton("Check F1");
		savebut = new JButton("Save");
		updatebut = new JButton("Update");
		deletebut = new JButton("Delete");
		addbut = new JButton("Add Attached Item");

		centerpanel = new JPanel();
		northpanel = new JPanel();
		westpanel = new JPanel();
		southpanel = new JPanel();

		dat = new Date();
		formatter = new SimpleDateFormat("dd/MM/yyyy");

		try {
			dateString = formatter.format(dat);
			tf = new JTextField(String.valueOf(dateString));
			tf6 = new JTextField(String.valueOf(dateString));
		} catch (IllegalArgumentException localIllegalArgumentException) {
			System.out.println(localIllegalArgumentException.getMessage());
		}

		tk = Toolkit.getDefaultToolkit();
		ss = tk.getScreenSize();
		fs = f.getSize();

		fo = new Font("Tahoma", 1, 11);
		bigfo = new Font("Tahoma", 1, 32);
	}

	JLabel l1;
	JLabel l2;
	JLabel l3;
	JLabel l4;
	JLabel l5;

	public void px() {
		Container localContainer = f.getContentPane();
		localContainer.setLayout(new BorderLayout());
		centerpanel.setLayout(null);
		northpanel.setLayout(new BorderLayout());
		westpanel.setLayout(new BorderLayout());

		northpanel.add(northlabel, "Center");
		westpanel.add(westlabel, "Center");
		northlabel.setFont(bigfo);
		northlabel.setForeground(new Color(250, 60, 80));
		northpanel.setBackground(Color.white);
		centerpanel.setBackground(new Color(60, 130, 130));

		jpm1.add(popm1);

		jpm2.add(popm2);
		jpm2.add(popm3);

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

		tf.setFont(fo);
		tf.setEditable(true);
		tf1.setFont(fo);
		tf1.setEditable(true);
		tf2.setFont(fo);
		tf2.setEditable(false);
		tf3.setFont(fo);
		tf3.setEditable(false);
		tf4.setFont(fo);
		tf5.setFont(fo);
		tf6.setFont(fo);
		tf6.setEditable(true);
		tf7.setFont(fo);
		tf7.setEditable(true);
		tfsp8.setFont(fo);
		tfsp9.setFont(fo);
		tf10.setFont(fo);
		tf10.setEditable(true);
		tf11.setFont(fo);
		tf11.setEditable(true);
		tf12.setFont(fo);
		tf13.setFont(fo);

		northpanel.setBackground(Color.white);
		westpanel.setBackground(Color.white);

		northpanel.setPreferredSize(new Dimension(60, 60));
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
		l5.setBounds(300, 105, 100, 20);
		tf5.setBounds(400, 105, 90, 20);
		l6.setBounds(30, 130, 150, 20);
		tf6.setBounds(200, 130, 90, 20);
		l7.setBounds(300, 130, 100, 20);
		tf7.setBounds(400, 130, 90, 20);
		l8.setBounds(30, 155, 60, 20);
		l13.setBounds(300, 155, 100, 20);
		tf13.setBounds(400, 155, 90, 20);
		tf8.setBounds(30, 180, 460, 80);

		l9.setBounds(30, 265, 60, 20);
		tf9.setBounds(30, 290, 460, 80);

		l10.setBounds(30, 375, 150, 20);
		itemsp.setBounds(200, 375, 90, 100);
		l11.setBounds(300, 375, 100, 20);
		drgsp.setBounds(400, 375, 90, 100);
		l12.setBounds(30, 480, 150, 20);
		tf12.setBounds(200, 480, 90, 20);
		addbut.setBounds(400, 480, 100, 20);

		southpanel.add(newbut);
		newbut.setMnemonic(78);
		southpanel.add(checkbut);
		checkbut.setMnemonic(75);
		southpanel.add(savebut);
		savebut.setMnemonic(83);
		southpanel.add(updatebut);
		updatebut.setMnemonic(85);
		southpanel.add(deletebut);
		deletebut.setMnemonic(67);

		savebut.setEnabled(false);

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
		centerpanel.add(tf5);
		centerpanel.add(l6);
		centerpanel.add(tf6);
		centerpanel.add(l7);
		centerpanel.add(tf7);
		centerpanel.add(l8);
		centerpanel.add(tf8);
		centerpanel.add(l9);
		centerpanel.add(tf9);
		centerpanel.add(l10);
		centerpanel.add(itemsp);
		centerpanel.add(l11);
		centerpanel.add(drgsp);
		centerpanel.add(l12);
		centerpanel.add(tf12);
		centerpanel.add(l13);
		centerpanel.add(tf13);
		centerpanel.add(addbut);

		addbut.addActionListener(this);
		newbut.addActionListener(this);
		checkbut.addActionListener(this);
		savebut.addActionListener(this);
		updatebut.addActionListener(this);
		deletebut.addActionListener(this);

		tf1.addFocusListener(this);
		tf4.addFocusListener(this);

		tf1.addItemListener(this);
		tf1.addActionListener(this);

		tf4.addActionListener(this);
		tf4.addKeyListener(this);

		tf10.addItemListener(this);
		tf10.addActionListener(this);
		tf11.addActionListener(this);

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

		f.setSize(930, 630);
		f.setLocation((ss.width - fs.width) / 930, (ss.height - fs.height) / 640);

		f.setVisible(true);
	}

	JLabel l6;
	JLabel l7;
	JLabel l8;
	JLabel l9;
	JLabel l10;

	public void projectNo() {
		tf1.removeAllItems();
		try {
			con = DBConnectionUtil.getConnection();
			stat = con.createStatement();
			rs = stat
					.executeQuery("select distinct(project_no) From estimation WHERE PROJECT_NO > '200701' ");
			while (rs.next()) {
				String str = new String(rs.getString(1));
				tf1.addItem(str);
			}
		} catch (Exception localException) {
			JOptionPane.showMessageDialog(f, localException.getMessage().toString());
		}
	}

	JLabel l11;
	JLabel l12;
	JLabel l13;
	JLabel northlabel;

	public void projectDetails() {
		try {
			con = DBConnectionUtil.getConnection();
			stat = con.createStatement();
			rs = stat
					.executeQuery("select max(project_name), max(client_name) From estimation WHERE project_no='"
							+ tf1.getSelectedItem() + "' ");
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

	JLabel westlabel;
	JTextField tf;
	JComboBox tf1;
	JTextField tf2;

	public void maxNo() {
		tf4.setText("1");
		tf5.setText("1");
		tf13.setText("1");
		try {
			stat = con.createStatement();
			rs = stat
					.executeQuery("select max(sol_no), MAX(CLIENT_NO), MAX(IMG_NO) from RFI_LOG WHERE PROJECT_NO='"
							+ tf1.getSelectedItem() + "' ");
			rs.getMetaData().getColumnCount();

			while (rs.next()) {
				String str1 = new String(rs.getString(1));
				String str2 = new String(rs.getString(2));
				String str3 = new String(rs.getString(3));

				int j = Integer.parseInt(str1) + 1;
				int k = Integer.parseInt(str2) + 1;
				int m = Integer.parseInt(str3) + 1;

				tf4.setText(String.valueOf(j));
				tf5.setText(String.valueOf(k));
				tf13.setText(String.valueOf(m));
			}
		} catch (Exception localException) {
			JOptionPane.showMessageDialog(f, localException.getMessage().toString());
		}
	}

	JTextField tf3;
	JTextField tf4;
	JTextField tf5;
	JTextField tf6;
	JTextField tf7;

	public void itemDetails() {
		itemmodel.setNumRows(0);
		itemmodel.addRow(new Object[] { "0" });
		try {
			con = DBConnectionUtil.getConnection();
			stat = con.createStatement();
			rs = stat.executeQuery(
					"select item_code From Estimation WHERE Project_no ='" + tf1.getSelectedItem() + "' ");
			rs.getMetaData().getColumnCount();
			while (rs.next()) {
				String str = new String(rs.getString(1));
				itemmodel.addRow(new Object[] { str });
			}
		} catch (Exception localException) {
			JOptionPane.showMessageDialog(f, localException.getMessage().toString());
		}
	}

	JTextPane tfsp8;
	JScrollPane tf8;

	public void drgDetails() {
		drgmodel.setNumRows(0);
		drgmodel.addRow(new Object[] { "0" });
		String str1 = String.valueOf(itemmodel.getValueAt(itemtb.getSelectedRow(), 0));
		try {
			con = DBConnectionUtil.getConnection();
			stat = con.createStatement();
			rs = stat.executeQuery("select drawing_no From drawingno WHERE Project_no ='"
					+ tf1.getSelectedItem() + "' and item_code='" + str1 + "' ");
			rs.getMetaData().getColumnCount();
			while (rs.next()) {
				String str2 = new String(rs.getString(1));
				drgmodel.addRow(new Object[] { str2 });
			}
		} catch (Exception localException) {
			JOptionPane.showMessageDialog(f, localException.getMessage().toString());
		}
	}

	JTextPane tfsp9;
	JScrollPane tf9;

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

	JComboBox tf10;
	JComboBox tf11;

	public void saveRecord() {
		String str1 = "Sk";
		String str2 = "sk";
		try {
			stat = con.createStatement();
			affected = stat.executeUpdate("insert into RFI_LOG values('" + tf1.getSelectedItem() + "','"
					+ tf2.getText() + "','" + tf3.getText() + "','" + tf4.getText() + "','"
					+ tf5.getText() + "','" + tf6.getText() + "','" + tf7.getText() + "','"
					+ tfsp8.getText() + "','" + tfsp9.getText() + "','0','0','" + tf12.getText() + "','"
					+ tf13.getText() + "')  ");

			if (affected > 0) {
				int i = tb.getRowCount();
				for (int j = 0; j < i; j++) {
					str1 = String.valueOf(model.getValueAt(j, 0));
					str2 = String.valueOf(model.getValueAt(j, 1));

					affected = stat.executeUpdate("insert into affectedItem values('"
							+ tf1.getSelectedItem() + "','" + tf4.getText() + "','" + str1 + "','" + str2
							+ "','" + tf12.getText() + "')  ");
					if (affected > 0) {
						model.setValueAt("Saved", j, 0);
					}
				}
				JOptionPane.showMessageDialog(f, "Query Submited");
			}
		} catch (Exception localException) {
			if (localException.getMessage().toString().equalsIgnoreCase("General error")) {
				JOptionPane.showMessageDialog(f, str2 + " Allready Affected.");
			} else {
				JOptionPane.showMessageDialog(f, localException.getMessage().toString());
			}
		}
	}

	public void updateRecord() {
		try {
			stat = con.createStatement();
			affected = stat
					.executeUpdate("update RFI_LOG set answer='" + tfsp9.getText() + "', return_date='"
							+ tf7.getText() + "', extra_hrs='" + tf12.getText() + "' where sol_no='"
							+ tf4.getText() + "' and project_no='" + tf1.getSelectedItem() + "' ");

			if (affected > 0) {
				JOptionPane.showMessageDialog(f, "Answer Update.");
			}
		} catch (Exception localException) {
			if (localException.getMessage().toString().equalsIgnoreCase("General error")) {
				JOptionPane.showMessageDialog(f, tf1.getSelectedItem() + " Allready Assigned.");
			} else {
				JOptionPane.showMessageDialog(f, localException.getMessage().toString());
			}
		}
	}

	JTextField tf12;
	JTextField tf13;
	JPopupMenu jpm1;
	JMenuItem popm1;
	JPopupMenu jpm2;

	public void deleteRecord() {
		try {
			stat = con.createStatement();
			affected = stat.executeUpdate("delete from RFI_LOG  where sol_no='" + tf4.getText()
					+ "' and project_no='" + tf1.getSelectedItem() + "' ");

			if (affected > 0) {
				JOptionPane.showMessageDialog(f, "Log Deleted");

				affected = stat.executeUpdate("delete from affecteditem where sol_no='" + tf4.getText()
						+ "' and project_no='" + tf1.getSelectedItem() + "' ");
				if (affected > 0) {
					JOptionPane.showMessageDialog(f, "Log Items Deleted");
				}
			}
		} catch (Exception localException) {
			JOptionPane.showMessageDialog(f, localException.getMessage().toString());
		}
	}

	public void newRecord() {
		tf2.setText("");
		tf3.setText("");
		tf5.setText("");
		tf6.setText(tf.getText());
		tf7.setText("");
		tfsp8.setText("");
		tfsp9.setText("");
		tf12.setText("0");
		tf1.requestFocus();
	}

	public void check() {
		newRecord();
		try {
			stat = con.createStatement();
			rs = stat.executeQuery(
					"select project_no,project_name,client_name,client_no,submit_date,return_date,question,answer,affec_item,affec_drg,extra_hrs From RFI_LOG WHERE SOL_NO ='"
							+ tf4.getText() + "' and project_no='" + tf1.getSelectedItem() + "' ");
			rs.getMetaData().getColumnCount();
			while (rs.next()) {
				String str1 = new String(rs.getString(1));
				String str2 = new String(rs.getString(2));
				String str3 = new String(rs.getString(3));
				String str4 = new String(rs.getString(4));
				String str5 = new String(rs.getString(5));
				String str6 = new String(rs.getString(6));
				String str7 = new String(rs.getString(7));
				String str8 = new String(rs.getString(8));
				new String(rs.getString(9));
				new String(rs.getString(10));
				String str11 = new String(rs.getString(11));

				tf1.setSelectedItem(str1);
				tf2.setText(str2);
				tf3.setText(str3);
				tf5.setText(str4);
				tf6.setText(str5);
				tf7.setText(str6);
				tfsp8.setText(str7);
				tfsp9.setText(str8);
				tf12.setText(str11);
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

	JMenuItem popm2;
	JMenuItem popm3;
	JButton newbut;
	JButton checkbut;
	JButton savebut;

	public void actionPerformed(ActionEvent paramActionEvent) {
		if (paramActionEvent.getSource() == newbut) {
			newRecord();
		}
		if (paramActionEvent.getSource() == checkbut) {
			check();
		}
		if ((paramActionEvent.getSource() == savebut) || (paramActionEvent.getSource() == tfsp8)) {
			saveRecord();
		}
		if (paramActionEvent.getSource() == updatebut) {
			updateRecord();
		}
		if (paramActionEvent.getSource() == tf1) {
			projectDetails();
			itemDetails();
		}
		if (paramActionEvent.getSource() == tf10) {
			drgDetails();
		}

		if (paramActionEvent.getSource() == addbut) {
			savebut.setEnabled(true);
			addAffectedItem();
		}
		if (paramActionEvent.getSource() == deletebut) {
			deleteRecord();
		}
		if (paramActionEvent.getSource() == popm1) {
			setAffected();
		}

		if (paramActionEvent.getSource() == popm2) {
			int[] arrayOfInt = tb.getSelectedRows();

			for (int i = 0; i < arrayOfInt.length; i++) {
				model.removeRow(arrayOfInt[i]);
			}
		}
		if (paramActionEvent.getSource() == popm3) {
			model.setNumRows(0);
		}
	}

	JButton updatebut;
	JButton deletebut;
	JButton addbut;
	JPanel centerpanel;
	JPanel northpanel;

	public void itemStateChanged(ItemEvent paramItemEvent) {
		if (paramItemEvent.getSource() == tf1) {
			projectDetails();
			itemDetails();
		}

		if (paramItemEvent.getSource() == tf10) {
			drgDetails();
		}
		if (paramItemEvent.getSource() == tf4) {
		}
	}

	JPanel westpanel;
	JPanel southpanel;
	Date dat;
	SimpleDateFormat formatter;
	String dateString;

	public void focusGained(FocusEvent paramFocusEvent) {
		if (paramFocusEvent.getSource() == tf4) {
		}
	}

	public void focusLost(FocusEvent paramFocusEvent) {
		if (paramFocusEvent.getSource() == tf1) {
		}
	}

	Toolkit tk;
	Dimension ss;
	Dimension fs;
	Font fo;
	Font bigfo;

	public void keyPressed(KeyEvent paramKeyEvent) {
		int i = paramKeyEvent.getKeyCode();
		if (i == 112) {
			check();
		}
	}

	public void keyReleased(KeyEvent paramKeyEvent) {
	}

	public void keyTyped(KeyEvent paramKeyEvent) {
	}

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