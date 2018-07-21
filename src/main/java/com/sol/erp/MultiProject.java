package com.sol.erp;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

import javax.swing.BorderFactory;
import javax.swing.DefaultCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import com.sol.erp.constants.ApplicationConstants;
import com.sol.erp.util.DBConnectionUtil;
import com.sol.erp.util.DateDifferencesUtil;
import com.sol.erp.util.formater.SolDateFormatter;

public class MultiProject implements ActionListener, FocusListener, ItemListener {

	TIMOption tm;
	// Connection con;
	// PreparedStatement pstat;
	ResultSet rs;
	int affected;
	String[] heads;
	Object[][] data;
	DecimalFormat df;
	DecimalFormat df1;
	DecimalFormat timeformat;
	DefaultTableModel model;
	JTable tb;
	JScrollPane sp;
	JDialog f;
	JLabel punchlabel;
	JTextField punchtf;
	JLabel l1;
	JLabel l2;
	JLabel l3;
	JLabel l4;
	JLabel breakLabel;
	JLabel finalLabel;
	JLabel l5;
	JLabel l6;
	JTextField tf1;
	JTextField tf2;
	JTextField tf3;
	JTextField tf4;
	JTextField breakTF;
	JTextField finalTF;
	JTextField tf5;
	JTextField tf6;
	JComboBox combo;
	JComboBox combo2;
	JLabel westlabel;
	JButton savebut;
	JButton closebut;
	JPanel centerpanel;
	JPanel northpanel;
	JPanel westpanel;
	JPanel southpanel;
	JPanel centernorthpanel;
	java.util.Date dat;
	SimpleDateFormat formatter;
	SimpleDateFormat formatter1;
	String dateString;
	String dateString1;
	Toolkit tk;
	Dimension ss;
	Dimension fs;
	Font fo;
	Border lineborder;
	Border bor1;
	Border bor2;
	Border bor3;
	MultiProject.ColoredTableCellRenderer skr;

	class ColoredTableCellRenderer extends DefaultTableCellRenderer {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		ColoredTableCellRenderer() {
		}

		public Component getTableCellRendererComponent(JTable paramJTable, Object paramObject, boolean paramBoolean1,
				boolean paramBoolean2, int paramInt1, int paramInt2) {
			setEnabled((paramJTable == null) || (paramJTable.isEnabled()));

			if (paramInt2 == 0) {
				setHorizontalAlignment(0);
			}
			if ((paramInt2 == 1) || (paramInt2 == 5)) {
				setHorizontalAlignment(2);
			}
			if (paramInt2 > 1) {
				setHorizontalAlignment(0);
			}

			String str = String.valueOf(model.getValueAt(paramInt1, 5));

			if (str.equalsIgnoreCase("D")) {
				setBackground(Color.white);
				setForeground(Color.darkGray);
			}
			if (str.equalsIgnoreCase("B")) {
				setBackground(new Color(200, 160, 255));
				setForeground(Color.darkGray);
			}
			if (str.equalsIgnoreCase("R")) {
				setBackground(new Color(160, 200, 255));
				setForeground(Color.darkGray);
			}

			super.getTableCellRendererComponent(paramJTable, paramObject, paramBoolean1, paramBoolean2, paramInt1,
					paramInt2);

			return this;
		}
	}

	public MultiProject() {
		tm = new TIMOption();

		affected = 0;

		heads = new String[] { "Hrs", "Project", "D/C", "ITEM", "DRG No.", "D/B/R - T/I/J/M/N" };
		data = new Object[0][];

		df = new DecimalFormat("00.00");
		df1 = new DecimalFormat("00");
		timeformat = new DecimalFormat("0000");

		model = new DefaultTableModel(data, heads);
		tb = new JTable(model) {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int paramAnonymousInt1, int paramAnonymousInt2) {
				return paramAnonymousInt2 >= 0;
			}
		};
		sp = new JScrollPane(tb);

		f = new JDialog();
		punchlabel = new JLabel("Punch Card");
		punchtf = new JTextField(4);

		l1 = new JLabel("Date");
		l2 = new JLabel("Emp Code");
		l3 = new JLabel("Name ");

		l4 = new JLabel("Total Time");
		breakLabel = new JLabel("Break Time");
		finalLabel = new JLabel("Final Time");

		l5 = new JLabel("SPLIT");
		l6 = new JLabel("Total Time ");

		tf1 = new JTextField(10);
		tf2 = new JTextField(5);
		tf3 = new JTextField(14);

		tf4 = new JTextField(5);
		breakTF = new JTextField(5);
		finalTF = new JTextField(5);
		tf5 = new JTextField("2", 2);
		tf6 = new JTextField(5);

		combo = new JComboBox();
		combo2 = new JComboBox();

		westlabel = new JLabel(
				new ImageIcon(ClassLoader.getSystemResource(ApplicationConstants.IMAGE_PATH + "time.gif")));

		savebut = new JButton("Save");
		closebut = new JButton("Close");

		centerpanel = new JPanel();
		northpanel = new JPanel();
		westpanel = new JPanel();
		southpanel = new JPanel();

		centernorthpanel = new JPanel();

		dat = new java.util.Date();
		formatter = new SimpleDateFormat("dd/MM/yyyy");
		formatter1 = new SimpleDateFormat("hhmm");

		try {
			dateString = formatter.format(dat);
			dateString1 = formatter1.format(dat);
			tf1 = new JTextField(String.valueOf(dateString));
		} catch (IllegalArgumentException localIllegalArgumentException) {
			System.out.println(localIllegalArgumentException.getMessage());
		}

		tk = Toolkit.getDefaultToolkit();
		ss = tk.getScreenSize();
		fs = f.getSize();

		fo = new Font("Verdana", 0, 10);

		lineborder = BorderFactory.createLineBorder(Color.gray);
		bor1 = BorderFactory.createTitledBorder(lineborder, "Multi Projects Entry", 0, 0, fo, Color.black);
		bor2 = BorderFactory.createTitledBorder(lineborder, "Entry Table", 0, 0, fo, Color.black);
		bor3 = BorderFactory.createBevelBorder(1);

		skr = new MultiProject.ColoredTableCellRenderer();
	}

	public void px() {
		// con = DBConnectionUtil.getConnection();

		Container localContainer = f.getContentPane();
		localContainer.setLayout(new BorderLayout());
		centerpanel.setLayout(new BorderLayout());
		centernorthpanel.setLayout(new GridLayout(1, 8, 5, 5));
		northpanel.setLayout(new FlowLayout());
		westpanel.setLayout(new BorderLayout());
		southpanel.setLayout(new FlowLayout(2));

		combo.addItem(" D  Design Drawing");
		combo.addItem(" B  BFA");
		combo.addItem(" R  Revision");
		combo.addItem("----------------------");
		combo.addItem(" I  Ideal Time");
		combo.addItem(" T  Training");
		combo.addItem(" M  Management");
		combo.addItem(" J  Job Management");
		combo.addItem(" N  Connection Design");
		combo.addItem(" A  ABOM");

		combo2.addItem("Project No");
		combo2.setEditable(true);

		l4.setHorizontalAlignment(4);
		l5.setHorizontalAlignment(4);
		l6.setHorizontalAlignment(4);

		punchtf.setFont(fo);
		punchtf.setEditable(false);
		tf1.setFont(fo);
		tf1.setEditable(false);
		tf2.setFont(fo);
		tf2.setEditable(false);
		tf3.setFont(fo);
		tf3.setEditable(false);
		tf4.setFont(fo);
		tf4.setEditable(false);
		breakTF.setFont(fo);
		breakTF.setEditable(false);
		finalTF.setFont(fo);
		finalTF.setEditable(false);

		tf5.setFont(new Font("Times New Roman", 1, 16));
		tf5.setForeground(Color.red);
		tf5.setHorizontalAlignment(4);

		tf6.setFont(fo);
		tf6.setEditable(false);

		tf1.setForeground(Color.red);
		punchtf.setForeground(Color.red);

		tf4.setForeground(Color.blue);
		breakTF.setForeground(Color.blue);
		finalTF.setForeground(Color.red);

		tf4.setHorizontalAlignment(4);
		breakTF.setHorizontalAlignment(4);
		finalTF.setHorizontalAlignment(4);

		for (int i = 0; i < tb.getColumnCount(); i++) {
			tb.getColumnModel().getColumn(i).setCellRenderer(skr);
		}

		tb.getTableHeader().setForeground(Color.darkGray);
		tb.getTableHeader().setPreferredSize(new Dimension(20, 20));
		tb.getTableHeader().setFont(new Font("Verdana", 1, 10));
		tb.setFont(new Font("Verdana", 0, 9));
		tb.setRowHeight(20);

		tb.getColumnModel().getColumn(0).setPreferredWidth(30);
		tb.getColumnModel().getColumn(1).setPreferredWidth(100);
		tb.getColumnModel().getColumn(2).setPreferredWidth(20);
		tb.getColumnModel().getColumn(3).setPreferredWidth(30);
		tb.getColumnModel().getColumn(4).setPreferredWidth(30);
		tb.getColumnModel().getColumn(5).setPreferredWidth(100);
		sp.setBorder(bor2);
		sp.setBorder(bor3);

		westpanel.setBackground(Color.white);

		northpanel.setPreferredSize(new Dimension(100, 70));
		westpanel.setPreferredSize(new Dimension(150, 70));

		savebut.setMnemonic(83);
		closebut.setMnemonic(67);

		northpanel.add(punchlabel);
		northpanel.add(punchtf);
		northpanel.add(l1);
		northpanel.add(tf1);
		northpanel.add(l2);
		northpanel.add(tf2);
		northpanel.add(l3);
		northpanel.add(tf3);

		centernorthpanel.add(l4);
		centernorthpanel.add(tf4);
		centernorthpanel.add(breakLabel);
		centernorthpanel.add(breakTF);
		centernorthpanel.add(finalLabel);
		centernorthpanel.add(finalTF);
		centernorthpanel.add(l5);
		centernorthpanel.add(tf5);

		southpanel.add(savebut);
		southpanel.add(closebut);

		savebut.addActionListener(this);
		closebut.addActionListener(this);

		tf2.addFocusListener(this);
		tf2.addActionListener(this);

		tf5.addFocusListener(this);
		tf5.addActionListener(this);

		combo.addItemListener(this);
		combo.addFocusListener(this);

		centernorthpanel.setBorder(bor1);

		centerpanel.add(centernorthpanel, "North");
		centerpanel.add(sp, "Center");

		localContainer.add(northpanel, "North");
		localContainer.add(centerpanel, "Center");
		localContainer.add(southpanel, "South");

		localContainer.setBackground(Color.white);

		f.setTitle("Multi Projects Entry");
		f.setSize(620, 430);

		f.setLocationRelativeTo(null);
		f.setResizable(false);
		f.setVisible(true);
		tf2.requestFocus();
		tf2.selectAll();

		setOptionColumn(tb, tb.getColumnModel().getColumn(5));
		//setDetailsColumn(tb, tb.getColumnModel().getColumn(1));
	}

	public void setOptionColumn(JTable paramJTable, TableColumn paramTableColumn) {
		paramTableColumn.setCellEditor(new DefaultCellEditor(combo));
		DefaultTableCellRenderer localDefaultTableCellRenderer = new DefaultTableCellRenderer();
		localDefaultTableCellRenderer.setToolTipText("Click to Choose the Option.");
	}

	/*
	public void setDetailsColumn(JTable paramJTable, TableColumn paramTableColumn) {
		paramTableColumn.setCellEditor(new DefaultCellEditor(combo2));
		DefaultTableCellRenderer localDefaultTableCellRenderer = new DefaultTableCellRenderer();
		localDefaultTableCellRenderer.setToolTipText("Click to Choose the Option.");
	}*/

	public void empDetails() {
		Connection con = null;
		PreparedStatement pstat = null;
		ResultSet rs = null;
		try {
			con = DBConnectionUtil.getConnection();
			pstat = con.prepareStatement("select emp_name From emp_STATUS WHERE emp_code='" + tf2.getText() + "' ");
			rs = pstat.executeQuery();
			while (rs.next()) {
				String str = new String(rs.getString(1));
				tf3.setText(str);
				tf4.requestFocus();
			}
		} catch (Exception localException) {
			JOptionPane.showMessageDialog(f, localException.getMessage().toString());
		} finally {
			DBConnectionUtil.closeConnection(rs, pstat, con);
		}
	}

	public void showMultiDRG() {
		model.setNumRows(0);

		java.sql.Date localDate = SolDateFormatter.DDMMYYtoSQL(tf1.getText());

		Connection con = null;
		PreparedStatement pstat = null;
		ResultSet rs = null;
		try {
			con = DBConnectionUtil.getConnection();
			pstat = con.prepareStatement("select TOTAL_TIME, PROJECT_NO, D_C  From HRTIMEMASTER WHERE EMP_CODE='"
					+ tf2.getText() + "' and DATE= ? and M_STATUS='PM' ");
			pstat.setDate(1, localDate);
			rs = pstat.executeQuery();

			while (rs.next()) {
				String str1 = new String(rs.getString(1));
				String str2 = new String(rs.getString(2));
				String str3 = new String(rs.getString(3));

				System.out.println(str1 + "\t" + str2 + "\t" + str3);

				model.addRow(new Object[] { str1, str2, str3, "XXX", "XXX", str3 });
			}

		} catch (Exception localException) {
			System.out.println("SHOWMULTI_DRG " + localException);
		} finally {
			DBConnectionUtil.closeConnection(rs, pstat, con);
		}

		showMultiDRG2();
		showMultiDRG3();
	}

	public void showMultiDRG2() {
		java.sql.Date localDate = SolDateFormatter.DDMMYYtoSQL(tf1.getText());

		Connection con = null;
		PreparedStatement pstat = null;
		ResultSet rs = null;

		try {
			con = DBConnectionUtil.getConnection();
			pstat = con.prepareStatement("select TOTAL_TIME, PROJECT_NO  From BFA_TIME WHERE EMP_CODE='" + tf2.getText()
					+ "' and DATE_MONTH= ? ");
			pstat.setDate(1, localDate);
			rs = pstat.executeQuery();

			while (rs.next()) {
				String str1 = new String(rs.getString(1));
				String str2 = new String(rs.getString(2));

				model.addRow(new Object[] { str1, str2, "NA", "XXX", "XXX", "B" });
			}

		} catch (Exception localException) {
			System.out.println("SHOWMULTI_DRG " + localException);
		} finally {
			DBConnectionUtil.closeConnection(rs, pstat, con);
		}
	}

	public void showMultiDRG3() {
		java.sql.Date localDate = SolDateFormatter.DDMMYYtoSQL(tf1.getText());
		Connection con = null;
		PreparedStatement pstat = null;
		ResultSet rs = null;

		try {
			con = DBConnectionUtil.getConnection();
			pstat = con.prepareStatement("select TOTAL_TIME, PROJECT_NO  From REVISION_TIME WHERE EMP_CODE='"
					+ tf2.getText() + "' and DATE_MONTH= ? ");
			pstat.setDate(1, localDate);
			rs = pstat.executeQuery();

			while (rs.next()) {
				String str1 = new String(rs.getString(1));
				String str2 = new String(rs.getString(2));

				model.addRow(new Object[] { str1, str2, "NA", "XXX", "XXX", "R" });
			}

		} catch (Exception localException) {
			System.out.println("SHOWMULTI_DRG " + localException);
		} finally {
			DBConnectionUtil.closeConnection(rs, pstat, con);
		}
	}

	public void deleteRecord() {
		java.sql.Date localDate = SolDateFormatter.DDMMYYtoSQL(tf1.getText());

		System.out.println("localeDate: "+ localDate);
		
		Connection con = null;
		PreparedStatement pstat = null;

		String empCodeString = tf2.getText();
		try {
			con = DBConnectionUtil.getConnection();

			// 1st statement
			String sqlString1 = "delete from HRTIMEMASTER where DATE = '"+localDate+"' and EMP_CODE = '"+empCodeString+"' and M_STATUS = 'PM' "; 
			System.out.println("sqlString1: "+sqlString1);
			pstat = con.prepareStatement(sqlString1);
			
			// 2nd statement
			String sqlString2 = "update HRTIMEMASTER set M_Status= '0', UPDATED = '2' where DATE = '"+localDate+"' and EMP_CODE = '"+empCodeString+"' ";
			System.out.println("sqlString2: "+sqlString2);
			pstat.addBatch(sqlString2);

			// 3rd statement
			String sqlString3 = "delete from BFA_TIME  where DATE_MONTH = '"+localDate+"' and EMP_CODE= '"+empCodeString+"' ";
			System.out.println("sqlString3: "+sqlString3);
			pstat.addBatch(sqlString3);
			
			// 4th statement
			String sqlString4 = "delete from REVISION_TIME  where DATE_MONTH = '"+localDate+"' and EMP_CODE= +'"+empCodeString+"'"; 
			System.out.println("sqlString4: "+sqlString4);
			pstat.addBatch(sqlString4);
			pstat.executeBatch();

		} catch (Exception localException1) {
			JOptionPane.showMessageDialog(f, localException1.getMessage().toString());
		} finally {
			DBConnectionUtil.closeConnection(pstat, con);
		}
	}

	public void markRecord() {
		java.sql.Date localDate = SolDateFormatter.DDMMYYtoSQL(tf1.getText());

		PreparedStatement pstat = null;
		Connection con = null;

		try {
			con = DBConnectionUtil.getConnection();
			pstat = con.prepareStatement(
					"UPDATE HRTIMEMASTER SET UPDATED='5', M_STATUS ='M' where date = ? and punchcard='"
							+ punchtf.getText() + "' AND M_STATUS='0' ");
			pstat.setDate(1, localDate);
			affected = pstat.executeUpdate();

			if (affected > 0) {
				saveRecord();
			}
		} catch (Exception localException) {
			JOptionPane.showMessageDialog(f, localException.getMessage().toString());
		} finally {
			DBConnectionUtil.closeConnection(pstat, con);
		}
	}

	public void saveRecord() {
		java.sql.Date localDate = SolDateFormatter.DDMMYYtoSQL(tf1.getText());

		for (int i = 0; i < tb.getRowCount(); i++) {
			String str1 = String.valueOf(model.getValueAt(i, 0));
			String str2 = String.valueOf(model.getValueAt(i, 1));
			String str3 = String.valueOf(model.getValueAt(i, 2));
			String str4 = String.valueOf(model.getValueAt(i, 5));
			String str5 = str4.substring(0, 2).trim();

			if ((str2.equalsIgnoreCase("Saved")) || (str2.equalsIgnoreCase("Updated"))) {
				JOptionPane.showMessageDialog(f, "Not a Valid Project No.");
				model.setNumRows(0);
			}
			if ((str2.equalsIgnoreCase("null")) || (str2.length() < 1)) {
				JOptionPane.showMessageDialog(f, "Enter the Project No.");
			} else {
				if ((str5.equalsIgnoreCase("D")) || (str5.equalsIgnoreCase("I")) || (str5.equalsIgnoreCase("T"))
						|| (str5.equalsIgnoreCase("J")) || (str5.equalsIgnoreCase("M")) || (str5.equalsIgnoreCase("N"))
						|| (str5.equalsIgnoreCase("A"))) {
					PreparedStatement pstat = null;
					Connection con = null;
					try {

						con = DBConnectionUtil.getConnection();
						/*
						String query  = "insert into hrtimemaster ('DATE', 'PUNCHCARD', 'EMP_CODE', 'IN_TIME', 'OUT_TIME', 'TOTAL_TIME', "
									+ "	'PROJECT_NO', 'D_C', 'UPDATED', 'BREAK_HRS', 'OT_HRS', 'DEDUCT_OT', 'DEDUCT_REMARKS',"
									+ " 'OT_REMARKS', 'BREAK_UPDATED', 'M_STATUS', 'OT2') values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
						*/
						
						pstat = con.prepareStatement("insert into HRTIMEMASTER values(?,'" + punchtf.getText() + "', '"
								+ tf2.getText() + "','" + tf3.getText() + "','00.00','00.00','" + str1 + "','" + str2
								+ "', '" + str3 + "','0','00.00','00.00','00.00','false','false','0','PM','0') ");
						
						pstat.setDate(1, localDate);
						affected = pstat.executeUpdate();

						if (affected > 0) {
							model.setValueAt("Saved", i, 1);
						}
						if (affected <= 0) {
							JOptionPane.showMessageDialog(f, "This Project is Allready Added.");
						}
					} catch (Exception localException1) {
						JOptionPane.showMessageDialog(f, localException1.getMessage().toString());
					} finally {
						DBConnectionUtil.closeConnection(pstat, con);

					}
				}
				if (str5.equalsIgnoreCase("R")) {
					PreparedStatement pstat = null;
					Connection con = null;
					try {
						con = DBConnectionUtil.getConnection();
						pstat = con.prepareStatement("insert into REVISION_TIME values(?, '" + tf2.getText() + "','"
								+ str2 + "', '0','0','" + str1 + "','R','0','0') ");
						pstat.setDate(1, localDate);
						affected = pstat.executeUpdate();

						if (affected > 0) {
							model.setValueAt("Saved", i, 1);
						}
						if (affected <= 0) {
							JOptionPane.showMessageDialog(f, "This Project is Allready Added.");
						}
					} catch (Exception localException2) {
						JOptionPane.showMessageDialog(f, localException2.getMessage().toString());
					}finally {
						DBConnectionUtil.closeConnection(pstat, con);
					}
				}
				if (str5.equalsIgnoreCase("B")) {
					PreparedStatement pstat = null;
					Connection  con = null;
					try {
						con = DBConnectionUtil.getConnection();
						pstat = con.prepareStatement("insert into BFA_TIME values(?, '" + tf2.getText() + "','" + str2
								+ "', '0','0','" + str1 + "','B','0','0') ");
						pstat.setDate(1, localDate);
						affected = pstat.executeUpdate();

						if (affected > 0) {
							model.setValueAt("Saved", i, 1);
						}
						if (affected <= 0) {
							JOptionPane.showMessageDialog(f, "This Project is Allready Added.");
						}
					} catch (Exception localException3) {
						JOptionPane.showMessageDialog(f, localException3.getMessage().toString());
					} finally {
						DBConnectionUtil.closeConnection(pstat, con);
					}
				}
			}
		}
	}

	public void actionPerformed(ActionEvent paramActionEvent) {
		if (paramActionEvent.getSource() == savebut) {
			markRecord();
		}
		if (paramActionEvent.getSource() == closebut) {
			f.setVisible(false);
		}
		if (paramActionEvent.getSource() == tf2) {
			empDetails();
			tf5.requestFocus();
			tf5.selectAll();
		}
		if (paramActionEvent.getSource() == tf5) {
			deleteRecord();
			String str1 = SolDateFormatter.getTimeDividedBy(finalTF.getText(), Integer.parseInt(tf5.getText()));
			model.setNumRows(0);

			for (int i = 0; i < Integer.parseInt(tf5.getText()); i++) {
				String str2 = String.valueOf(combo.getSelectedItem());
				model.addRow(new Object[] { str1, "", "", "XXX", "XXX", str2 });
			}

			tb.requestFocus();
		}
	}

	public void itemStateChanged(ItemEvent paramItemEvent) {
	}

	public void focusGained(FocusEvent paramFocusEvent) {
		if (paramFocusEvent.getSource() == tf5) {
			finalTF.setText(DateDifferencesUtil.getTimeDiff(breakTF.getText(), tf4.getText()));
		}
	}

	public void focusLost(FocusEvent paramFocusEvent) {
		if (paramFocusEvent.getSource() == tf2) {
			empDetails();
			showMultiDRG();
		}
		/*
		if (paramFocusEvent.getSource() == combo) {
			int i = tb.getSelectedRow();

			String str1 = String.valueOf(combo.getSelectedItem());
			String str2 = str1.substring(0, 2).trim();
			model.setValueAt(str2, i, 2);

			combo2.removeAllItems();

			if ((str2.equalsIgnoreCase("I")) || (str2.equalsIgnoreCase("T")) || (str2.equalsIgnoreCase("M"))) {

				String[] arrayOfString = tm.getOptionArray(str2);

				for (int j = 0; j < arrayOfString.length; j++) {
					model.setValueAt(arrayOfString[0], i, 1);
					combo2.addItem(arrayOfString[j]);
				}
			}

			if (str2.equalsIgnoreCase("J")) {

				model.setValueAt("0", i, 1);
				combo2.setEditable(true);
			}
			if (str2.equalsIgnoreCase("M")) {

				model.setValueAt("0", i, 1);
				combo2.setEditable(true);
			}
			if (str2.equalsIgnoreCase("D")) {

				model.setValueAt("0", i, 1);
				combo2.setEditable(true);
			}
			if (str2.equalsIgnoreCase("B")) {

				model.setValueAt("0", i, 1);
				combo2.setEditable(true);
			}
			if (str2.equalsIgnoreCase("R")) {

				model.setValueAt("0", i, 1);
				combo2.setEditable(true);
			}
			if (str2.equalsIgnoreCase("A")) {

				model.setValueAt("0", i, 1);
				combo2.setEditable(true);
			}
		}*/
	}

}
