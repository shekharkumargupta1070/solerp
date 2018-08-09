package com.sol.erp;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;

import com.sol.erp.dao.ProjectDAO;
import com.sol.erp.ui.custom.SolTableModel;
import com.sol.erp.util.DBConnectionUtil;
import com.sol.erp.util.UIUtil;
import com.sol.erp.util.formater.SolDateFormatter;

public class ProjectSummary implements ActionListener, KeyListener, MouseListener, ItemListener {

	String[] column = { "ITEM Code", "ITEM NAME", "# OF DRGS.", "HRS.", "<HTML><CENTER>COMPLETED<BR> DRGS.",
			"REST JOB (%)", "BFA", "REV", "WASTED", "EXTRA" };
	Object[][] data = new Object[0][];

	String[] column1 = { "EMP CODE", "EMP NAME", "DESIGNATION", "<HTML><Center># ALLOTED<BR>DRGs.",
			"<HTML><Center># COMPLETED<BR>DRGs.", "<HTML><Center>ALLOTED<BR>Hrs.", "<HTML><Center>ACTUAL<BR>USED Hrs.",
			"<HTML><CENTER>FAC.", "<HTML><CENTER>JOB%", "<HTML><CENTER>COMPLTD.<BR>JOB Hrs.",
			"<HTML><CENTER>PERFD.<BR>FAC.", "BFA", "REV." };
	Object[][] data1 = new Object[0][];

	String[] column2 = { "Emp Code", "Name", "D/C", "DATE", "Hrs.", "Eff. Hrs." };
	Object[][] data2 = new Object[0][];

	String[] column3 = { "Emp Code", "Name", "D/C", "DATE", "Hrs.", "Eff. Hrs." };
	Object[][] data3 = new Object[0][];

	int[] days = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31, 365 };

	DecimalFormat df = new DecimalFormat("0");
	DecimalFormat df1 = new DecimalFormat("0");
	DecimalFormat df2 = new DecimalFormat("0.00");
	DecimalFormat df3 = new DecimalFormat("0.0");
	DecimalFormat df4 = new DecimalFormat("00.00");
	DecimalFormat monthformat = new DecimalFormat("00");
	DecimalFormat monthformat2 = new DecimalFormat("00");

	JInternalFrame internalFrame = new JInternalFrame("Project Summary", true, true, true, true);
	JProgressBar progress = new JProgressBar();

	JTabbedPane tabbedpane = new JTabbedPane();
	JPanel summarypanel = new JPanel();
	JPanel hrspanel = new JPanel();
	JPanel hrspanel2 = new JPanel();
	JPanel hrspanel3 = new JPanel();

	JLabel emplabel = new JLabel("Emp Code :");
	JLabel yearlabel = new JLabel("Report for the Year of :");
	JTextField emptf = new JTextField(4);
	JTextField yeartf = new JTextField(7);
	javax.swing.JButton genbut = new javax.swing.JButton("Generate");

	JLabel dtlabel = new JLabel("DTL :");
	JLabel chlabel = new JLabel("CHK :");
	JLabel stdlabel = new JLabel("Standard Work Hrs :");

	JTextField dttf = new JTextField(3);
	JTextField chtf = new JTextField(3);
	JTextField stdtf = new JTextField("9", 3);

	DefaultTableModel model = new DefaultTableModel(data, column);
	JTable tb = new JTable(model);
	JScrollPane sp = new JScrollPane(tb);

	DefaultTableModel model1 = new DefaultTableModel(data1, column1);
	JTable tb1 = new JTable(model1);
	JScrollPane sp1 = new JScrollPane(tb1);

	DefaultTableModel model2 = new DefaultTableModel(data2, column2);
	JTable tb2 = new JTable(model2);
	JScrollPane sp2 = new JScrollPane(tb2);

	DefaultTableModel model3 = new DefaultTableModel(data3, column3);
	JTable tb3 = new JTable(model3);
	JScrollPane sp3 = new JScrollPane(tb3);

	JLabel l1 = new JLabel("Project No");
	JLabel l2 = new JLabel("Name");
	JLabel l3 = new JLabel("TL Code");
	JLabel l4 = new JLabel("MP Quoted HRs");
	JLabel l5 = new JLabel("Start Date");
	JLabel l6 = new JLabel("Final Date");
	JLabel l7 = new JLabel("Team");
	JLabel l8 = new JLabel("Taken Time");
	JLabel l9 = new JLabel("TL Quoted HRs");
	JLabel l10 = new JLabel("Exp Taken Time");
	JLabel l11 = new JLabel("Exp Shoot %");

	JComboBox comboBoxProject = new JComboBox();
	JTextField tf2 = new JTextField(17);
	JTextField tf3 = new JTextField(5);
	JTextField tf4 = new JTextField(5);
	JTextField tf5 = new JTextField(7);
	JTextField tf6 = new JTextField(7);
	JComboBox tf7 = new JComboBox();
	JTextField tf8 = new JTextField(5);
	JTextField tf9 = new JTextField(5);
	JTextField tf10 = new JTextField(7);
	JTextField tf11 = new JTextField(7);

	JMenuBar mb = new JMenuBar();
	JMenu m1 = new JMenu("File");
	JMenuItem mi1 = new JMenuItem("Print Report");
	JMenuItem mi2 = new JMenuItem("Close");

	JPopupMenu pop = new JPopupMenu();
	JCheckBoxMenuItem popm1 = new JCheckBoxMenuItem("UnGroup This Table", true);

	JPanel northpanel = new JPanel();
	JPanel centerpanel = new JPanel();
	JPanel southpanel = new JPanel();
	JPanel mainpanel = new JPanel();

	java.awt.Font fo = new java.awt.Font("Verdana", 0, 11);
	Border bor = BorderFactory.createTitledBorder("Project Details");

	class ColoredTableCellRenderer extends javax.swing.table.DefaultTableCellRenderer {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		ColoredTableCellRenderer() {
		}

		public java.awt.Component getTableCellRendererComponent(JTable paramJTable, Object paramObject,
				boolean paramBoolean1, boolean paramBoolean2, int paramInt1, int paramInt2) {
			setEnabled((paramJTable == null) || (paramJTable.isEnabled()));

			if (paramInt2 == 0) {
				setHorizontalAlignment(0);
			}
			if (paramInt2 == 1) {
				setHorizontalAlignment(2);
			}
			if (paramInt2 > 1) {
				setHorizontalAlignment(4);
			}

			if (paramInt1 % 2 == 0) {
				setBackground(new Color(240, 240, 240));
				setForeground(Color.darkGray);

			} else {
				setBackground(new Color(250, 250, 250));
				setForeground(Color.darkGray);
			}

			tb.setRowHeight(25);
			tb.setRowHeight(0, 55);

			super.getTableCellRendererComponent(paramJTable, paramObject, paramBoolean1, paramBoolean2, paramInt1,
					paramInt2);

			return this;
		}
	}

	class ColoredTableCellRenderer2 extends javax.swing.table.DefaultTableCellRenderer {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		ColoredTableCellRenderer2() {
		}

		public java.awt.Component getTableCellRendererComponent(JTable paramJTable, Object paramObject,
				boolean paramBoolean1, boolean paramBoolean2, int paramInt1, int paramInt2) {
			setEnabled((paramJTable == null) || (paramJTable.isEnabled()));

			if (paramInt1 == 0) {
				setBackground(Color.darkGray);
				setForeground(Color.white);
				setHorizontalAlignment(0);
			}

			if (paramInt1 > 0) {
				String str = String.valueOf(model1.getValueAt(paramInt1, 0));

				if (paramInt2 <= 2) {
					setHorizontalAlignment(2);
				}
				if (paramInt2 > 2) {
					setHorizontalAlignment(4);
				}

				if (str.equalsIgnoreCase("Detailing")) {
					setBackground(new Color(160, 180, 255));
					setForeground(Color.darkGray);
				}
				if (str.equalsIgnoreCase("Checking")) {
					setBackground(new Color(165, 220, 210));
					setForeground(Color.darkGray);
				}
				if (str.equalsIgnoreCase("TOTAL")) {
					setBackground(Color.darkGray);
					setForeground(Color.white);
				}
				if (str.length() <= 3) {
					if (paramInt1 % 2 == 0) {
						setBackground(new Color(245, 245, 245));
						setForeground(Color.darkGray);
					} else {
						setBackground(new Color(250, 250, 250));
						setForeground(Color.darkGray);
					}
				}

				if ((paramInt2 == 4) && (tb1.getRowCount() > 1)) {

					float f1 = Float.parseFloat(String.valueOf(model1.getValueAt(paramInt1, 3)));
					float f2 = Float.parseFloat(String.valueOf(model1.getValueAt(paramInt1, 4)));

					if (((f2 <= 0.0F) || (f2 >= f1 / 2.0F)) ||

							(f2 > f1)) {
						setBackground(Color.red);
						setForeground(Color.white);
					}
				}
				if ((paramInt2 != 10) || (tb1.getRowCount() <= 1)) {
				}
			}

			tb1.setRowHeight(25);
			tb1.setRowHeight(0, 110);

			super.getTableCellRendererComponent(paramJTable, paramObject, paramBoolean1, paramBoolean2, paramInt1,
					paramInt2);

			return this;
		}
	}

	class ColoredTableCellRenderer3 extends javax.swing.table.DefaultTableCellRenderer {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		ColoredTableCellRenderer3() {
		}

		public java.awt.Component getTableCellRendererComponent(JTable paramJTable, Object paramObject,
				boolean paramBoolean1, boolean paramBoolean2, int paramInt1, int paramInt2) {
			setEnabled((paramJTable == null) || (paramJTable.isEnabled()));

			if (paramInt2 == 0) {
				setHorizontalAlignment(0);
			}
			if ((paramInt2 == 1) && (paramInt1 > 0)) {
				setHorizontalAlignment(2);
			}
			if ((paramInt2 > 1) && (paramInt1 > 0)) {
				setHorizontalAlignment(0);
			}
			if ((paramInt2 > 3) && (paramInt1 > 0)) {
				setHorizontalAlignment(4);
			}

			if (paramInt1 % 2 == 0) {
				setBackground(new Color(240, 240, 240));
				setForeground(Color.darkGray);

			} else {
				setBackground(new Color(250, 250, 250));
				setForeground(Color.darkGray);
			}

			if (paramInt1 == 0) {
				setBackground(Color.darkGray);
				setForeground(Color.white);
				setHorizontalAlignment(0);
			}
			String str1;
			if ((paramInt2 == 2) && (paramInt1 > 0)) {
				str1 = String.valueOf(model2.getValueAt(paramInt1, paramInt2));
				if (str1.equalsIgnoreCase("D")) {
					setBackground(new Color(250, 250, 250));
					setForeground(Color.darkGray);
				}
				if (str1.equalsIgnoreCase("C")) {
					setBackground(new Color(250, 50, 50));
					setForeground(Color.white);
				}
				if (str1.equalsIgnoreCase("R")) {
					setBackground(new Color(50, 150, 50));
					setForeground(Color.white);
				}
				if (str1.equalsIgnoreCase("B")) {
					setBackground(new Color(50, 50, 250));
					setForeground(Color.white);
				}
				if ((str1.equalsIgnoreCase("I")) || (str1.equalsIgnoreCase("J")) || (str1.equalsIgnoreCase("T"))
						|| (str1.equalsIgnoreCase("N"))) {
					setBackground(new Color(160, 50, 150));
					setForeground(Color.white);
				}
			}

			if ((tb2.getRowCount() > 1) && (tb2.getSelectedRows().length > 0)) {
				str1 = String.valueOf(model2.getValueAt(tb2.getSelectedRow(), 0));
				String str2 = String.valueOf(model2.getValueAt(paramInt1, 0));
				if (str2.equalsIgnoreCase(str1)) {
					setBackground(Color.orange);
					setForeground(Color.blue);
				}
			}

			tb2.setRowHeight(25);
			tb2.setRowHeight(0, 110);

			super.getTableCellRendererComponent(paramJTable, paramObject, paramBoolean1, paramBoolean2, paramInt1,
					paramInt2);

			return this;
		}
	}

	ColoredTableCellRenderer skr = new ColoredTableCellRenderer();
	ColoredTableCellRenderer2 skr2 = new ColoredTableCellRenderer2();
	ColoredTableCellRenderer3 skr3 = new ColoredTableCellRenderer3();

	public void designFrame() {
		Container localContainer = internalFrame.getContentPane();
		localContainer.setLayout(new BorderLayout());
		mainpanel.setLayout(new BorderLayout());

		northpanel.setLayout(new FlowLayout());
		centerpanel.setLayout(new BorderLayout());
		southpanel.setLayout(new FlowLayout());

		summarypanel.setLayout(new BorderLayout());
		hrspanel.setLayout(new BorderLayout());
		hrspanel2.setLayout(new BorderLayout());
		hrspanel3.setLayout(new BorderLayout());

		tabbedpane.add("ITEM DETAILS", summarypanel);
		tabbedpane.add("HRS DETAILS & PERFORMANCE", hrspanel);
		tabbedpane.add("ACTUAL SPENT HRS.", hrspanel2);

		tabbedpane.setFont(new java.awt.Font("Verdana", 1, 10));

		pop.add(popm1);

		m1.add(mi1);
		m1.addSeparator();
		m1.add(mi2);

		mb.add(m1);

		progress.setPreferredSize(new java.awt.Dimension(300, 16));

		northpanel.setBorder(bor);
		tf7.addItem("%");

		for (int i = 0; i < tb.getColumnCount(); i++) {
			tb.getColumnModel().getColumn(i).setCellRenderer(skr);
		}
		tb.getColumnModel().getColumn(1).setPreferredWidth(250);
		tb.getColumnModel().getColumn(2).setPreferredWidth(80);
		tb.getColumnModel().getColumn(3).setPreferredWidth(80);
		tb.getColumnModel().getColumn(4).setPreferredWidth(80);
		tb.getColumnModel().getColumn(5).setPreferredWidth(100);
		tb.getColumnModel().getColumn(6).setPreferredWidth(50);
		tb.getColumnModel().getColumn(7).setPreferredWidth(50);
		tb.getColumnModel().getColumn(8).setPreferredWidth(50);

		SolTableModel.decorateTable(tb);
		tb.setShowGrid(false);
		tb.setBackground(Color.white);
		tb.setIntercellSpacing(new java.awt.Dimension(1, 1));
		tb.setFont(new java.awt.Font("Tahoma", 1, 11));
		tb.setSelectionBackground(Color.orange);
		tb.setSelectionForeground(Color.darkGray);
		tb.getTableHeader().setPreferredSize(new java.awt.Dimension(35, 35));

		tb1.getColumnModel().getColumn(0).setPreferredWidth(70);
		tb1.getColumnModel().getColumn(1).setPreferredWidth(280);
		tb1.getColumnModel().getColumn(2).setPreferredWidth(100);
		tb1.getColumnModel().getColumn(3).setPreferredWidth(72);
		tb1.getColumnModel().getColumn(4).setPreferredWidth(90);
		tb1.getColumnModel().getColumn(5).setPreferredWidth(65);
		tb1.getColumnModel().getColumn(6).setPreferredWidth(65);
		tb1.getColumnModel().getColumn(7).setPreferredWidth(45);
		tb1.getColumnModel().getColumn(8).setPreferredWidth(45);
		tb1.getColumnModel().getColumn(9).setPreferredWidth(60);
		tb1.getColumnModel().getColumn(10).setPreferredWidth(45);
		tb1.getColumnModel().getColumn(11).setPreferredWidth(35);
		tb1.getColumnModel().getColumn(12).setPreferredWidth(35);

		SolTableModel.decorateTable(tb1);
		tb1.getTableHeader().setPreferredSize(new java.awt.Dimension(35, 35));
		tb1.getTableHeader().setFont(new java.awt.Font("Tahoma", 1, 9));
		tb1.setBackground(Color.darkGray);
		tb1.setIntercellSpacing(new java.awt.Dimension(1, 1));
		tb1.setFont(new java.awt.Font("Tahoma", 1, 11));
		tb1.setAutoResizeMode(0);
		tb1.setShowGrid(false);

		SolTableModel.decorateTable(tb2);
		tb2.getTableHeader().setPreferredSize(new java.awt.Dimension(35, 35));
		tb2.setBackground(Color.darkGray);
		tb2.setIntercellSpacing(new java.awt.Dimension(1, 1));
		tb2.setFont(new java.awt.Font("Tahoma", 1, 11));
		tb2.setAutoResizeMode(0);
		tb2.setShowGrid(false);
		tb2.setAutoscrolls(false);

		tb2.getColumnModel().getColumn(0).setPreferredWidth(80);
		tb2.getColumnModel().getColumn(1).setPreferredWidth(300);
		tb2.getColumnModel().getColumn(2).setPreferredWidth(30);
		tb2.getColumnModel().getColumn(3).setPreferredWidth(150);
		tb2.getColumnModel().getColumn(4).setPreferredWidth(70);
		tb2.getColumnModel().getColumn(5).setPreferredWidth(70);
		for (int i = 0; i < tb2.getColumnCount(); i++) {
			tb2.getColumnModel().getColumn(i).setCellRenderer(skr3);
		}

		SolTableModel.decorateTable(tb3);
		tb3.getTableHeader().setPreferredSize(new java.awt.Dimension(35, 35));
		tb3.setBackground(Color.darkGray);
		tb3.setIntercellSpacing(new java.awt.Dimension(1, 1));
		tb3.setFont(new java.awt.Font("Tahoma", 1, 11));
		tb3.setAutoResizeMode(0);
		tb3.setShowGrid(false);
		tb3.setAutoscrolls(false);

		tb3.getColumnModel().getColumn(0).setPreferredWidth(80);
		tb3.getColumnModel().getColumn(1).setPreferredWidth(300);
		tb3.getColumnModel().getColumn(2).setPreferredWidth(40);
		tb3.getColumnModel().getColumn(3).setPreferredWidth(150);
		tb3.getColumnModel().getColumn(4).setPreferredWidth(70);
		tb3.getColumnModel().getColumn(5).setPreferredWidth(70);
		for (int i = 0; i < tb3.getColumnCount(); i++) {
			tb3.getColumnModel().getColumn(i).setCellRenderer(skr3);
		}

		northpanel.add(l1);
		northpanel.add(comboBoxProject);
		northpanel.add(l2);
		northpanel.add(tf2);
		northpanel.add(l3);
		northpanel.add(tf3);
		northpanel.add(l5);
		northpanel.add(tf5);
		northpanel.add(l6);
		northpanel.add(tf6);
		northpanel.add(genbut);

		genbut.setBackground(new Color(160, 250, 160));
		genbut.setForeground(Color.black);
		genbut.setFont(new java.awt.Font("Verdana", 1, 10));

		southpanel.add(l4);
		southpanel.add(tf4);
		southpanel.add(l9);
		southpanel.add(tf9);
		southpanel.add(l8);
		southpanel.add(tf8);
		southpanel.add(l10);
		southpanel.add(tf10);
		southpanel.add(l11);
		southpanel.add(tf11);
		southpanel.add(l7);
		southpanel.add(tf7);

		summarypanel.add(sp, "Center");
		hrspanel.add(sp1, "Center");
		hrspanel2.add(sp2, "Center");

		mainpanel.add(northpanel, "North");
		mainpanel.add(tabbedpane, "Center");

		localContainer.add(mainpanel, "Center");

		comboBoxProject.setBackground(Color.white);
		tf2.setBackground(Color.white);
		tf3.setBackground(Color.white);
		tf4.setBackground(Color.white);
		tf5.setBackground(Color.white);
		tf6.setBackground(Color.white);

		comboBoxProject.setEditable(true);
		comboBoxProject.setFont(fo);
		tf2.setEditable(false);
		tf2.setFont(fo);
		tf3.setEditable(false);
		tf3.setFont(fo);
		tf4.setEditable(false);
		tf4.setFont(fo);
		tf5.setEditable(false);
		tf5.setFont(fo);
		tf6.setEditable(false);
		tf6.setFont(fo);
		tf7.setEditable(true);
		tf7.setFont(fo);
		tf8.setEditable(false);
		tf8.setFont(fo);
		tf9.setEditable(false);
		tf9.setFont(fo);
		tf10.setEditable(false);
		tf10.setFont(fo);
		tf11.setEditable(false);
		tf11.setFont(fo);

		tf4.setHorizontalAlignment(4);
		tf8.setHorizontalAlignment(4);
		tf9.setHorizontalAlignment(4);
		tf10.setHorizontalAlignment(4);
		tf11.setHorizontalAlignment(4);

		popm1.addActionListener(this);

		tb2.addMouseListener(this);

		comboBoxProject.addItemListener(this);
		// comboBoxProject.addActionListener(this);
		tf7.addActionListener(this);

		genbut.addActionListener(this);
		yeartf.addActionListener(this);

		mi1.addActionListener(this);
		mi2.addActionListener(this);

		internalFrame.setJMenuBar(mb);

		internalFrame.setSize(1024, 550);
		UIUtil.centreToApplication(internalFrame);
		internalFrame.setVisible(true);

		projectList();
		// refresh();
	}

	public void projectItemDetails() {
		model.setNumRows(0);
		model1.setNumRows(0);
		model.addRow(new Object[] {
				"<html><CENTER><Font size='2' color='black'><b>" + comboBoxProject.getSelectedItem() + "<BR>( "
						+ tf3.getText() + " )",
				"<html><CENTER><font size='5'><B>" + tf2.getText() + "</B><br><FONT size='2'> ( " + tf5.getText()
						+ " - " + tf6.getText() + " )" });
		String str1 = "%" + comboBoxProject.getSelectedItem() + "%";

		Connection con = null;
		Statement stat = null;
		ResultSet rs = null;

		try {
			con = DBConnectionUtil.getConnection();
			stat = con.createStatement();
			rs = stat.executeQuery(
					"select e.item_code, e.sheet_qty, e.total_time, i.Item_Name from Estimation E, ItemCode I where e.Project_no LIKE '"
							+ str1 + "' and e.item_code=i.item_code");

			while (rs.next()) {
				String str2 = new String(rs.getString(1));
				String str3 = new String(rs.getString(2));
				String str4 = new String(rs.getString(3));
				String str5 = new String(rs.getString(4));

				model.addRow(new Object[] { str2, str5, str3, str4 });
			}
		} catch (Exception localException) {
			System.out.println(localException);
		} finally {
			DBConnectionUtil.closeConnection(rs, stat, con);
		}

	}

	public void detailerList() {
		float f1 = 0.0F;
		int i = 0;

		model1.addRow(new Object[] {
				"<html><CENTER><Font size='3' color='white'>" + comboBoxProject.getSelectedItem() + "<BR>( "
						+ tf3.getText() + " )",
				"<html><CENTER><font size='5'><B>" + tf2.getText() + "</B><br><FONT size='2'> ( " + tf5.getText()
						+ " - " + tf6.getText() + " )<BR>" + "<html><Font size='3' Color='Yellow'>EST. Sheet : "
						+ tf8.getText() + ", EST. Hrs : " + tf4.getText() + "<BR"
						+ "<html><B><Font size='3' Color='White'>BFA Hrs : "
						+ String.valueOf(
								df.format(ProjectDAO.getEffectiveBFAHrs((String) comboBoxProject.getSelectedItem())))
						+ ", REV. Hrs : " + String.valueOf(df.format(
								ProjectDAO.getProjectRevisionHrs((String) comboBoxProject.getSelectedItem()))) });

		Connection con = null;
		Statement stat = null;
		ResultSet rs = null;
		try {
			con = DBConnectionUtil.getConnection();
			stat = con.createStatement();
			rs = stat.executeQuery(
					"select distinct(d.Detailing), COUNT(d.DRAWING_NO), sum(d.DTL_TIME) from DRAWINGNO d where d.PROJECT_NO='"
							+ comboBoxProject.getSelectedItem() + "' group by d.detailing");

			while (rs.next()) {
				String str = new String(rs.getString(1));
				int j = rs.getInt(2);
				float f2 = Float.parseFloat(rs.getString(3));

				model1.addRow(new Object[] { str.toUpperCase(), "", "", String.valueOf(j), "0",
						String.valueOf(df1.format(f2)), "0", "0", "0", "0", "0" });
				f1 += f2;
				i += j;
			}
			if (tb.getRowCount() >= 2) {
				model1.addRow(new Object[] { "DETAILING", "", "", String.valueOf(i), "0", String.valueOf(df.format(f1)),
						"0", "0", "0", "0", "0" });
			}
			f1 = 0.0F;
			i = 0;
		} catch (Exception localException) {
			System.out.println(localException);
		} finally {
			DBConnectionUtil.closeConnection(rs, stat, con);
		}
	}

	public void checkerList() {
		float f1 = 0.0F;
		int i = 0;

		Connection con = null;
		Statement stat = null;
		ResultSet rs = null;
		try {
			con = DBConnectionUtil.getConnection();
			stat = con.createStatement();
			rs = stat.executeQuery(
					"select distinct(d.checking), COUNT(d.DRAWING_NO), sum(d.chk_TIME) from DRAWINGNO d where d.PROJECT_NO='"
							+ comboBoxProject.getSelectedItem() + "' group by d.checking");

			while (rs.next()) {
				String str = new String(rs.getString(1));
				int j = rs.getInt(2);
				float f2 = Float.parseFloat(rs.getString(3));

				model1.addRow(new Object[] { str.toUpperCase(), "", "", String.valueOf(j), "0",
						String.valueOf(df1.format(f2)), "0", "0", "0", "0", "0" });
				f1 += f2;
				i += j;
			}
			if (tb.getRowCount() >= 2) {
				model1.addRow(new Object[] { "CHECKING", "", "", String.valueOf(i), "0", String.valueOf(df.format(f1)),
						"0", "0", "0", "0", "0" });
			}
			f1 = 0.0F;
		} catch (Exception localException) {
			System.out.println(localException);
		} finally {
			DBConnectionUtil.closeConnection(rs, stat, con);
		}
	}

	public void attendanceList(boolean groupUngroup) {
		model2.setNumRows(0);
		model2.addRow(new Object[] {
				"<html><CENTER><Font size='3' color='white'>" + comboBoxProject.getSelectedItem() + "<BR>( "
						+ tf3.getText() + " )",
				"<html><CENTER><font size='5'><B>" + tf2.getText() + "</B><br><FONT size='2'> ( " + tf5.getText()
						+ " - " + tf6.getText() + " )<BR>" + "<html><Font size='3' Color='Yellow'>EST. Sheet : "
						+ tf8.getText() + ", EST. Hrs : " + tf4.getText() + "<BR>"
						+ "<html><B><Font size='3' Color='White'>Eff. Used BFA Hrs : "
						+ String.valueOf(
								df.format(ProjectDAO.getEffectiveBFAHrs((String) comboBoxProject.getSelectedItem())))
						+ ", Revison Hrs : " + String.valueOf(df.format(
								ProjectDAO.getProjectRevisionHrs((String) comboBoxProject.getSelectedItem()))) });

		float f2 = 0.0F;
		float f3 = 0.0F;
		String str2;
		String str3;
		float f5;
		String projectNumber = comboBoxProject.getSelectedItem().toString();
		Connection con = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;

		String createTempTableQuery = "CREATE TEMPORARY TABLE IF NOT EXISTS temp_proj_summary AS"
				+ " (SELECT * FROM hrtimemaster where PROJECT_NO = '" + projectNumber + "')";

		StringBuilder fetchResultQuery = new StringBuilder();

		fetchResultQuery.append(" select EMP_CODE, EMP_NAME, trim(D_C), DATE, SUM(TOTAL_TIME) ");
		fetchResultQuery.append(" from temp_proj_summary ");
		if (groupUngroup) {
			fetchResultQuery.append(" where PROJECT_NO = '" + projectNumber + "' group by EMP_CODE, DATE ");
		} else {
			fetchResultQuery.append(" where PROJECT_NO = '" + projectNumber + "' group by EMP_CODE");
		}
		fetchResultQuery.append(" UNION ");
		fetchResultQuery
				.append(" (select DISTINCT(b.EMP_CODE), h.FULL_NAME, b.REASON, b.DATE_MONTH, SUM(b.TOTAL_TIME) ");
		fetchResultQuery.append(" from BFA_TIME b, HREMP_JOIN h where b.PROJECT_NO = '" + projectNumber + "' ");
		fetchResultQuery.append(" and b.EMP_CODE = h.EMP_CODE group by b.EMP_CODE, h.FULL_NAME, b.REASON ) ");
		fetchResultQuery.append(" UNION ");
		fetchResultQuery
				.append(" (select DISTINCT(r.EMP_CODE), h.FULL_NAME, r.REASON, r.DATE_MONTH, SUM(r.TOTAL_TIME) ");
		fetchResultQuery.append(" from REVISION_TIME r, HREMP_JOIN h where r.PROJECT_NO = '" + projectNumber + "' ");
		fetchResultQuery
				.append(" and r.EMP_CODE = h.EMP_CODE group by r.EMP_CODE, h.FULL_NAME, r.REASON ) order by EMP_CODE ");

		String dropTempTableQuery = "DROP TABLE temp_proj_summary";

		System.out.println("ActualSpentHrs: " + fetchResultQuery);

		try {
			con = DBConnectionUtil.getConnection();
			preparedStatement = con.prepareStatement(createTempTableQuery);
			int updatedRecord = preparedStatement.executeUpdate();

			if (updatedRecord > 0) {
				// Closing the preparedStatement so that can initialize again.
				DBConnectionUtil.closeConnection(preparedStatement, null);

				preparedStatement = con.prepareStatement(fetchResultQuery.toString());
				rs = preparedStatement.executeQuery();
				while (rs.next()) {
					String str1 = new String(rs.getString(1));
					str2 = new String(rs.getString(2));
					str3 = new String(rs.getString(3));
					String str4 = SolDateFormatter.SQLtoDDMMYY(rs.getDate(4));
					f5 = rs.getFloat(5);

					int k = SolDateFormatter.convertHrsToMinute(String.valueOf(f5));
					String str6 = SolDateFormatter.convertMinuteToHRS(k);

					model2.addRow(new Object[] { str1, str2, str3.trim(), str4, String.valueOf(str6) });
				}

				// Closing the preparedStatement so that can initialize again.
				DBConnectionUtil.closeConnection(preparedStatement, null);

				preparedStatement = con.prepareStatement(dropTempTableQuery);
				preparedStatement.executeUpdate();
			}
		} catch (Exception localException) {
			System.out.println(localException);
		} finally {
			DBConnectionUtil.closeConnection(rs, preparedStatement, con);
		}

		for (int i = 1; i < tb2.getRowCount(); i++) {
			str2 = String.valueOf(model2.getValueAt(i, 0));
			str3 = String.valueOf(model2.getValueAt(i, 2));

			float f4 = Float.parseFloat(String.valueOf(model2.getValueAt(i, 4)));
			f2 += f4;
			f5 = 0.0F;

			if ((str3.trim().equalsIgnoreCase("B")) || (str3.trim().equalsIgnoreCase("R"))) {
				f5 = effectiveHrs(str2, f4);
			}
			if ((str3.trim().equalsIgnoreCase("D")) || (str3.trim().equalsIgnoreCase("C"))
					|| (str3.trim().equalsIgnoreCase("A")) || (str3.trim().equalsIgnoreCase("M"))) {
				f5 = effectiveHrs(str2, f4);
			}
			if ((str3.trim().equalsIgnoreCase("I")) || (str3.trim().equalsIgnoreCase("T"))
					|| (str3.trim().equalsIgnoreCase("J")) || (str3.trim().equalsIgnoreCase("N"))) {
				f5 = effectiveHrs(str2, f4);
			}
			model2.setValueAt(String.valueOf(df4.format(f5)), i, 5);

			f3 += f5;
		}

		int i = 0;
		for (int j = 1; j < tb2.getRowCount(); j++) {
			str3 = String.valueOf(model2.getValueAt(j, 0));
			String str5 = String.valueOf(model2.getValueAt(j - 1, 0));

			if (!str3.equalsIgnoreCase(str5)) {
				i += 1;
			}
		}

		if (model2.getRowCount() > 0) {
			model2.addRow(new Object[] { "TOTAL : " + String.valueOf(i), "", "", "", String.valueOf(df4.format(f2)),
					String.valueOf(df4.format(f3)) });
			model2.setValueAt("<HTML><CENTER>Used Hrs./EFF. Hrs.<BR><Font Size='5' Color='Yellow'>"
					+ String.valueOf(df.format(f2)) + "/<HTML>" + String.valueOf(df.format(f3)), 0, 3);
		}
	}

	public void showTimeFormat() {
		int i = tb2.getRowCount() - 1;

		String str1 = String.valueOf(model2.getValueAt(i, 4));
		String str2 = String.valueOf(model2.getValueAt(i, 5));

		int j = SolDateFormatter.convertHrsToMinute(str1);
		String str3 = SolDateFormatter.convertMinuteToHRS(j);

		int k = SolDateFormatter.convertHrsToMinute(str2);
		String str4 = SolDateFormatter.convertMinuteToHRS(k);

		model2.setValueAt(str3, i, 4);
		model2.setValueAt(str4, i, 5);
	}

	public float effectiveHrs(String paramString, float paramFloat) {
		float f1 = ProjectDAO.getEmpFactorByProject(paramString, String.valueOf(comboBoxProject.getSelectedItem()));
		float f2 = paramFloat * f1;

		float f3 = SolDateFormatter.convertHrsToMinute2(String.valueOf(f2));
		String str = SolDateFormatter.convertMinuteToHRS2(f3);

		return Float.parseFloat(str);
	}

	public void nameList() {
		for (int i = 1; i < tb1.getRowCount() - 1; i++) {
			String str = String.valueOf(model1.getValueAt(i, 0));
			if (str.length() == 3) {
				model1.setValueAt(" " + EmpTB.getEmpName(str).toUpperCase(), i, 1);
				model1.setValueAt(" " + EmpTB.getDesig(str).toUpperCase(), i, 2);
			}
		}
	}

	public void projectList() {
		Connection con = null;
		Statement stat = null;
		ResultSet rs = null;
		List projectList = new ArrayList();
		try {
			con = DBConnectionUtil.getConnection();
			stat = con.createStatement();
			rs = stat.executeQuery("select Project_No from PROJECT_CO");
			while (rs.next()) {
				String projectNumber = rs.getString("Project_No");
				projectList.add(projectNumber);
			}
		} catch (Exception localException) {
			System.out.println(localException);
		} finally {
			DBConnectionUtil.closeConnection(rs, stat, con);
		}

		for (Object projectNumber : projectList) {
			comboBoxProject.addItem(projectNumber);
		}
	}

	public void projectDetails() {
		String selectedProject = (String) comboBoxProject.getSelectedItem();
		Connection con = null;
		PreparedStatement stat = null;
		ResultSet rs = null;
		try {
			con = DBConnectionUtil.getConnection();
			stat = con.prepareStatement(
					"select p.Project_Name, p.CO_CODE, p.START, p.NEW_FINAL_DATE, sum(e.TOTAL_TIME), sum(e.SHEET_QTY) from PROJECT_CO p, ESTIMATION e where p.PROJECT_NO = ?  and p.PROJECT_NO=e.PROJECT_NO Group by p.PROJECT_NO ");
			stat.setLong(1, Long.parseLong((String) comboBoxProject.getSelectedItem()));
			rs = stat.executeQuery();
			rs.getMetaData().getColumnCount();
			while (rs.next()) {
				String str2 = new String(rs.getString(1));
				String str3 = new String(rs.getString(2));
				String str4 = SolDateFormatter.SQLtoDDMMYY(rs.getDate(3));
				String str5 = SolDateFormatter.SQLtoDDMMYY(rs.getDate(4));
				String str6 = String.valueOf(df.format(rs.getFloat(5)));
				String str7 = String.valueOf(df.format(rs.getInt(6)));

				tf2.setText(String.valueOf(str2).toUpperCase());
				tf3.setText(str3);
				tf4.setText(str6);
				tf5.setText(str4);
				tf6.setText(str5);
				tf8.setText(str7);
			}
		} catch (Exception localException) {
			System.out.println(localException);
		} finally {
			DBConnectionUtil.closeConnection(rs, stat, con);
		}

		System.out.println("Project Details : "
				+ String.valueOf(ProjectDAO.getProjectEstimatedHrs(String.valueOf(selectedProject.trim()))));
		tf4.setText(
				String.valueOf(df.format(ProjectDAO.getProjectEstimatedHrs(String.valueOf(selectedProject.trim())))));
		projectItemDetails();
	}

	public void teamMember() {
		tf7.removeAllItems();
		tf7.addItem("%");

		Connection con = null;
		Statement stat = null;
		ResultSet rs = null;
		try {
			con = DBConnectionUtil.getConnection();
			stat = con.createStatement();
			rs = stat.executeQuery("select distinct(emp_code) from PROJECTMANPOWER where PROJECT_NO='"
					+ comboBoxProject.getSelectedItem() + "'  ");

			while (rs.next()) {
				String str = new String(rs.getString(1));
				tf7.addItem(String.valueOf(str).toUpperCase());
			}
		} catch (Exception localException) {
			System.out.println(localException);
		} finally {
			DBConnectionUtil.closeConnection(rs, stat, con);
		}
	}

	public void takenTime() {
		tf8.setText("0");
		String str1 = "%" + comboBoxProject.getSelectedItem() + "%";

		Connection con = null;
		Statement stat = null;
		ResultSet rs = null;
		try {
			con = DBConnectionUtil.getConnection();
			stat = con.createStatement();
			rs = stat.executeQuery("select sum(total_time) from HRTIMEMASTER where PROJECT_NO like '" + str1 + "' ");

			while (rs.next()) {
				String str2 = new String(rs.getString(1));
				float f1 = Float.parseFloat(str2);

				tf8.setText(String.valueOf(df.format(f1)));
			}
		} catch (java.sql.SQLException localSQLException) {
			System.out.println("Taken Time : " + localSQLException);
		} finally {
			DBConnectionUtil.closeConnection(rs, stat, con);
		}
	}

	public void totalTakenTime() {
		int i = 0;
		float f1 = 0.0F;
		float f2 = 0.0F;

		String str1 = String.valueOf(comboBoxProject.getSelectedItem());
		String str2 = "Detailing";
		String str3;
		for (int j = 1; j < tb1.getRowCount(); j++) {
			str3 = String.valueOf(model1.getValueAt(j, 0));
			if (str3.trim().length() > 3) {
				str2 = "Checking";
			}

			f2 = ProjectDAO.getUsedHrs(str3, str1, str2).floatValue();
			int k = ProjectDAO.getCountCompletedDrg(str1, str3, str2);
			f1 += f2;
			i += k;

			model1.setValueAt(String.valueOf(df.format(f2)), j, 6);
			model1.setValueAt(String.valueOf(df.format(k)), j, 4);

			if (str3.equalsIgnoreCase("Detailing")) {
				model1.setValueAt(String.valueOf(i), j, 4);
				model1.setValueAt(String.valueOf(df.format(f1)), j, 6);
				i = 0;
				f1 = 0.0F;
			}
			if (str3.equalsIgnoreCase("Checking")) {
				model1.setValueAt(String.valueOf(i), j, 4);
				model1.setValueAt(String.valueOf(df.format(f1)), j, 6);
				i = 0;
				f1 = 0.0F;
			}
		}

		if (tb1.getRowCount() > 1) {
			for (int j = 1; j < tb1.getRowCount() - 1; j++) {
				str3 = String.valueOf(model1.getValueAt(j, 0));
				Float.parseFloat(String.valueOf(model1.getValueAt(j, 6)));

				float f4 = ProjectDAO.getEmpFactorByProject(str3, String.valueOf(comboBoxProject.getSelectedItem()));
				model1.setValueAt(String.valueOf(f4), j, 7);
			}
		}
	}

	public void totalUsed() {
		float f1 = 0.0F;
		float f2 = 0.0F;
		int i = 0;
		int j = 0;
		int k = 0;
		float f3 = 0.0F;

		for (int m = 1; m < tb1.getRowCount(); m++) {
			String str = String.valueOf(model1.getValueAt(m, 0));
			float f4 = Float.parseFloat(String.valueOf(model1.getValueAt(m, 6)));

			float f5 = effectiveHrs(str, f4);

			int n = Integer.parseInt(String.valueOf(model1.getValueAt(m, 3)));
			int i2 = Integer.parseInt(String.valueOf(model1.getValueAt(m, 5)));

			f2 += f5;
			f3 += f5;

			if (str.equalsIgnoreCase("Detailing")) {
				i += n;
				model1.setValueAt(String.valueOf(df.format(f2)), m, 7);
				f2 = 0.0F;
			}
			if (str.equalsIgnoreCase("Checking")) {

				model1.setValueAt(String.valueOf(df.format(f2)), m, 7);
				f2 = 0.0F;
			}
			if ((str.equalsIgnoreCase("Detailing")) || (str.equalsIgnoreCase("Checking"))) {
				k += i2;
				f1 += f4;
			}
		}

		model1.setValueAt("<HTML><CENTER>Used Hrs./EFF. Hrs.<BR><Font Size='5' Color='Yellow'>"
				+ String.valueOf(df.format(f1)) + "/" + String.valueOf(df.format(f3)), 0, 2);
		model1.addRow(new Object[] { "TOTAL", "", "", String.valueOf(i), String.valueOf(j), String.valueOf(k),
				String.valueOf(df.format(f1)), String.valueOf(df.format(f3)), "0", "0", "0" });
	}

	public void calculate() {
		float f1 = 0.0F;
		float f2 = 0.0F;
		float f3 = 0.0F;
		float f4 = 0.0F;

		String str1 = "Detailing";
		String str2;
		float f6;
		float f7;
		float f8;
		float f9;
		for (int i = 1; i < tb1.getRowCount(); i++) {
			str2 = String.valueOf(model1.getValueAt(i, 0));
			String str3 = String.valueOf(comboBoxProject.getSelectedItem());
			f6 = Float.parseFloat(String.valueOf(model1.getValueAt(i, 7)));

			if (str2.trim().length() > 3) {
				str1 = "Checking";
			}

			f7 = ProjectDAO.getCompletedHrs(str3, str2.trim(), str1).floatValue();
			f8 = ProjectDAO.getBFAHrs(str2.trim(), str3).floatValue() * f6;
			f9 = ProjectDAO.getRevisionHrs(str2.trim(), str3).floatValue() * f6;

			model1.setValueAt("0", i, 9);
			model1.setValueAt("0", i, 10);
			model1.setValueAt("0", i, 11);
			model1.setValueAt("0", i, 12);
			model1.setValueAt(String.valueOf(df.format(f7)), i, 9);
			model1.setValueAt(String.valueOf(df.format(f8)), i, 11);
			model1.setValueAt(String.valueOf(df.format(f9)), i, 12);

			f1 += f7;
			f2 += f7;

			if ((str2.trim().equalsIgnoreCase("Detailing")) || (str2.trim().equalsIgnoreCase("Checking"))) {
				model1.setValueAt(String.valueOf(df.format(f1)), i, 9);
				f1 = 0.0F;
			}
			if (str2.trim().equalsIgnoreCase("Total")) {
				model1.setValueAt(String.valueOf(df.format(f2)), i, 9);
				f2 = 0.0F;
			}
		}

		for (int i = 1; i < tb1.getRowCount(); i++) {
			str2 = String.valueOf(model1.getValueAt(i, 0));

			float f5 = Integer.parseInt(String.valueOf(model1.getValueAt(i, 3)));
			f6 = Integer.parseInt(String.valueOf(model1.getValueAt(i, 4)));

			f7 = Float.parseFloat(String.valueOf(model1.getValueAt(i, 9)));
			f8 = Float.parseFloat(String.valueOf(model1.getValueAt(i, 6)));
			f9 = Float.parseFloat(String.valueOf(model1.getValueAt(i, 11)));
			float f10 = Float.parseFloat(String.valueOf(model1.getValueAt(i, 12)));

			f3 += f9;
			f4 += f10;
			float f11;
			float f12;
			if (f8 >= 0.0F) {
				f11 = f6 / f5 * 100.0F;
				f12 = f7 / f8;

				model1.setValueAt("0", i, 8);

				model1.setValueAt(String.valueOf(df.format(f11)), i, 8);
				model1.setValueAt(String.valueOf(df3.format(f12)), i, 10);
			}

			if ((str2.trim().equalsIgnoreCase("Detailing")) || (str2.trim().equalsIgnoreCase("Checking"))
					|| (str2.trim().equalsIgnoreCase("Total"))) {
				f11 = Float.parseFloat(String.valueOf(model1.getValueAt(i, 7)));
				f12 = Float.parseFloat(String.valueOf(model1.getValueAt(i, 9)));
				float f13 = f7 / f11;
				model1.setValueAt(String.valueOf(df3.format(f13)), i, 10);
				model1.setValueAt(String.valueOf(df.format(f3)), i, 11);
				model1.setValueAt(String.valueOf(df.format(f4)), i, 12);
			}
		}
	}

	public void completedDrg() {
		int totalRows = tb.getRowCount();
		Connection con = null;
		Statement stat = null;
		ResultSet rs = null;

		try {
			con = DBConnectionUtil.getConnection();
			for (int rowIndex = 1; rowIndex < totalRows; rowIndex++) {
				String itemCode = String.valueOf(model.getValueAt(rowIndex, 0));
				model.setValueAt("0", rowIndex, 4);

				stat = con.createStatement();
				rs = stat.executeQuery("select count(T.drg_no) from timesheet T where T.project_no='"
						+ comboBoxProject.getSelectedItem() + "' and T.item_code='" + itemCode
						+ "' and T.drgstatus1='Check' ");

				while (rs.next()) {
					String totalCount = rs.getString(1);
					if (totalCount == null) {
						totalCount = "0";
					}
					model.setValueAt(totalCount, rowIndex, 4);
				}

			}
		} catch (Exception ex) {
			System.out.println(ex);
		} finally {
			DBConnectionUtil.closeConnection(rs, stat, con);
		}
	}

	public void BFA() {
		int totalRows = tb.getRowCount();
		String selectedProject = (String) comboBoxProject.getSelectedItem();
		Connection con = null;
		PreparedStatement stat = null;
		ResultSet rs = null;
		try {
			con = DBConnectionUtil.getConnection();
			String query = "select sum(total_time) from BFA_TIME where"
					+ " project_no = ? and item_code= ? and remarks = ? ";

			for (int rowIndex = 1; rowIndex < totalRows; rowIndex++) {
				String itemCode = String.valueOf(model.getValueAt(rowIndex, 0));
				model.setValueAt("0", rowIndex, 6);

				stat = con.prepareStatement(query);
				stat.setString(1, selectedProject);
				stat.setString(2, itemCode);
				stat.setString(3, "APPROVED");
				rs = stat.executeQuery();

				while (rs.next()) {
					String totalTime = rs.getString(1);
					if (totalTime == null) {
						totalTime = "0";
					}
					model.setValueAt(totalTime, rowIndex, 6);
				}

			}
		} catch (Exception ex) {
			System.out.println(ex);
		} finally {
			DBConnectionUtil.closeConnection(rs, stat, con);
		}

	}

	public void REV() {
		int totalRows = tb.getRowCount();
		Connection con = null;
		Statement stat = null;
		ResultSet rs = null;
		try {
			con = DBConnectionUtil.getConnection();
			for (int rowIndex = 1; rowIndex < totalRows; rowIndex++) {
				String itemCode = String.valueOf(model.getValueAt(rowIndex, 0));
				model.setValueAt("0", rowIndex, 7);
				stat = con.createStatement();
				rs = stat.executeQuery("select sum(total_time) from REVISION_TIME where project_no='"
						+ comboBoxProject.getSelectedItem() + "' and item_code='" + itemCode + "' and project_no='"
						+ comboBoxProject.getSelectedItem() + "' AND REMARKS='APPROVED'  ");

				while (rs.next()) {
					String totalTime = rs.getString(1);
					if (totalTime == null) {
						totalTime = "0";
					}
					model.setValueAt(totalTime, rowIndex, 7);
				}
			}
		} catch (Exception ex) {
			System.out.println(ex);
		} finally {
			DBConnectionUtil.closeConnection(rs, stat, con);
		}
	}

	public void WASTED() {
		int totalRows = tb.getRowCount();
		Connection con = null;
		Statement stat = null;
		ResultSet rs = null;
		try {
			con = DBConnectionUtil.getConnection();
			for (int rowIndex = 1; rowIndex < totalRows; rowIndex++) {
				String itemCode = String.valueOf(model.getValueAt(rowIndex, 0));
				model.setValueAt("0", rowIndex, 8);

				stat = con.createStatement();
				rs = stat.executeQuery("select sum(total_time) from WASTED where project_no='"
						+ comboBoxProject.getSelectedItem() + "' and item_code='" + itemCode + "' and project_no='"
						+ comboBoxProject.getSelectedItem() + "' AND REMARKS='APPROVED'  ");

				while (rs.next()) {
					String totalTime = rs.getString(1);
					if (totalTime == null) {
						totalTime = "0";
					}
					model.setValueAt(totalTime, rowIndex, 8);
				}
			}
		} catch (Exception ex) {
			System.out.println(ex);
		} finally {
			DBConnectionUtil.closeConnection(rs, stat, con);
		}
	}

	public void REST() {
		int i = tb.getRowCount();
		for (int rowIndex = 1; rowIndex < i; rowIndex++) {
			int totalDrawings = Integer.parseInt(String.valueOf(model.getValueAt(rowIndex, 2)));
			int completedDrawings = Integer.parseInt(String.valueOf(model.getValueAt(rowIndex, 4)));

			if (!String.valueOf(model.getValueAt(rowIndex, 4)).equals("")) {

				float bfaDrawings = Float.parseFloat(String.valueOf(model.getValueAt(rowIndex, 6)));
				float revesionDrawings = Float.parseFloat(String.valueOf(model.getValueAt(rowIndex, 7)));
				float westedDrawings = Float.parseFloat(String.valueOf(model.getValueAt(rowIndex, 8)));
				float extraDrawings = 0.0F;

				int restDrawings = totalDrawings - completedDrawings;
				int completedPercentage = completedDrawings / totalDrawings * 100;
				if (completedPercentage >= 90) {
					model.setValueAt("<Html><font Color='blue'>" + String.valueOf(restDrawings) + " ("
							+ String.valueOf(completedPercentage) + ")</font></html>", rowIndex, 5);
				}
				if ((completedPercentage > 50) && (completedPercentage < 90)) {
					model.setValueAt("<Html><font Color='green'>" + String.valueOf(restDrawings) + " ("
							+ String.valueOf(completedPercentage) + ")</font></html>", rowIndex, 5);
				}
				if (completedPercentage <= 10) {
					model.setValueAt("<Html><font Color='red'>" + String.valueOf(restDrawings) + " ("
							+ String.valueOf(completedPercentage) + ")</font></html>", rowIndex, 5);
				}

				extraDrawings = bfaDrawings + revesionDrawings + westedDrawings;
				model.setValueAt(String.valueOf(extraDrawings), rowIndex, 9);

				bfaDrawings = 0.0F;
				revesionDrawings = 0.0F;
				westedDrawings = 0.0F;
			}
		}
	}

	public void calc() {
		float f1 = 0.0F;
		float f2 = 0.0F;
		float f3 = 0.0F;
		float f4 = 0.0F;

		int i = tb.getRowCount();

		for (int j = 1; j < i; j++) {
			float f6 = Float.parseFloat(String.valueOf(model.getValueAt(j, 2)));
			float f7 = SolDateFormatter.convertHrsToMinute2(String.valueOf(model.getValueAt(j, 3)));
			float f8 = Float.parseFloat(String.valueOf(model.getValueAt(j, 4)));
			float f9 = Float.parseFloat(String.valueOf(model.getValueAt(j, 9)));

			f1 += f6;
			f2 += f7;
			f3 += f8;

			f4 += f9;
		}

		float f5 = f3 / f1 * 100.0F;

		float f6 = f1 - f3;
		float f7 = f6 / f1 * 100.0F;

		model.addRow(new Object[] { "TOTAL : " + String.valueOf(tb.getRowCount()), "", String.valueOf(df.format(f1)),
				SolDateFormatter.convertMinuteToHRS2(f2),
				String.valueOf(df.format(f3)) + " (" + String.valueOf(df.format(f5)) + "%)",
				String.valueOf(df.format(f6)) + " (" + String.valueOf(df.format(f7)) + "%)", "", "", "",
				String.valueOf(df.format(f4)) });
		tf9.setText("0");
		tf9.setText(String.valueOf(df.format(f2)));

		float f8 = Float.parseFloat(tf9.getText());
		float f9 = Float.parseFloat(tf8.getText()) - f4;
		float f12 = f9 / f3 * f1;
		float f13 = f8 / f12 * 100.0F;

		tf10.setText(String.valueOf(df.format(f12)));

		if (f13 > 100.0F) {
			tf11.setText(String.valueOf(df.format(f13 - 100.0F)) + "(+)");
		}
		if (f13 < 100.0F) {
			tf11.setText(String.valueOf(df.format(f13)) + "(-)");
		}

		f1 = 0.0F;
		f2 = 0.0F;
		f3 = 0.0F;
		f4 = 0.0F;
	}

	public class myPrint extends Thread implements Runnable {
		public myPrint() {
		}

		public void run() {
			try {
				java.text.MessageFormat localMessageFormat1 = new java.text.MessageFormat("- {0} -");
				java.text.MessageFormat localMessageFormat2;
				if (tabbedpane.getSelectedIndex() == 0) {
					localMessageFormat2 = new java.text.MessageFormat("Project Profile");
					tb.print(javax.swing.JTable.PrintMode.FIT_WIDTH, localMessageFormat2, localMessageFormat1);
				}
				if (tabbedpane.getSelectedIndex() == 1) {
					localMessageFormat2 = new java.text.MessageFormat("Project Hrs. Details & Performance");
					tb1.print(javax.swing.JTable.PrintMode.FIT_WIDTH, localMessageFormat2, localMessageFormat1);
				}
				if (tabbedpane.getSelectedIndex() == 2) {
					localMessageFormat2 = new java.text.MessageFormat("Acutal Hrs. Details");
					tb2.print(javax.swing.JTable.PrintMode.FIT_WIDTH, localMessageFormat2, localMessageFormat1);
				}
			} catch (java.awt.print.PrinterException localPrinterException) {
				System.err.println("Error printing: " + localPrinterException.getMessage());
			}
		}
	}

	ExcelExporter exp = new ExcelExporter();

	public void exportToExcel() {
		try {
			exp.exportTable(tb, model, new java.io.File("results.xls"));
		} catch (java.io.IOException localIOException) {
			System.out.println(localIOException.getMessage());
			localIOException.printStackTrace();
		}
	}

	public void refresh() {
		projectDetails();
		completedDrg();
		BFA();
		REV();
		WASTED();
		REST();
		calc();
		totalTakenTime();
	}

	public void refresh2() {
		projectDetails();
		detailerList();
		checkerList();
		nameList();
		totalTakenTime();
		totalUsed();
		calculate();

		if (tb1.getRowCount() > 1) {
			for (int i = 0; i < tb1.getColumnCount(); i++) {
				tb1.getColumnModel().getColumn(i).setCellRenderer(skr2);
			}
		}
	}

	public void refresh3() {
		projectDetails();
		attendanceList(false);

	}

	public void actionPerformed(ActionEvent actionEvent) {
		if (actionEvent.getSource() == genbut) {
			if (tabbedpane.getSelectedIndex() == 0) {
				refresh();
			}
			if (tabbedpane.getSelectedIndex() == 1) {
				refresh2();
			}
			if (tabbedpane.getSelectedIndex() == 2) {
				refresh3();
			}
		}
		Object localObject;
		if (actionEvent.getSource() == popm1) {
			localObject = String.valueOf(model2.getValueAt(tb2.getSelectedRow(), 0));
			
			Boolean localBoolean = Boolean.valueOf(popm1.getState());
			if (localBoolean.booleanValue() == false) {
				attendanceList(true);
				popm1.setText("Group This Table");
			}
			if (localBoolean.booleanValue() == true) {
				attendanceList(false);
				popm1.setText("UnGroup This Table");
			}

			for (int i = 1; i < tb2.getRowCount() - 1; i++) {
				String str3 = String.valueOf(model2.getValueAt(i, 0));
				if (str3.equalsIgnoreCase((String) localObject)) {
					tb2.setRowSelectionInterval(i, i);
					tb2.scrollRectToVisible(tb2.getCellRect(i, i, true));
				}
			}
		}

		if (actionEvent.getSource() == tf7) {
			takenTime();
		}
		if (actionEvent.getSource() == mi1) {
			localObject = new myPrint();
			((myPrint) localObject).start();
		}

		if (actionEvent.getSource() == mi2) {
			internalFrame.setVisible(false);
		}
	}

	public void keyPressed(java.awt.event.KeyEvent paramKeyEvent) {
	}

	public void keyReleased(java.awt.event.KeyEvent paramKeyEvent) {
		int i = paramKeyEvent.getKeyCode();

		if (i == 117) {
		}
	}

	public void keyTyped(java.awt.event.KeyEvent paramKeyEvent) {
	}

	public void mousePressed(java.awt.event.MouseEvent paramMouseEvent) {
		if (paramMouseEvent.getSource() == tb2) {
			if (javax.swing.SwingUtilities.isRightMouseButton(paramMouseEvent)) {
				pop.show((javax.swing.JComponent) paramMouseEvent.getSource(), paramMouseEvent.getX(),
						paramMouseEvent.getY());
			}
		}
	}

	public void mouseReleased(java.awt.event.MouseEvent paramMouseEvent) {
	}

	public void mouseClicked(java.awt.event.MouseEvent paramMouseEvent) {
	}

	public void mouseEntered(java.awt.event.MouseEvent paramMouseEvent) {
	}

	public void mouseExited(java.awt.event.MouseEvent paramMouseEvent) {
	}

	@Override
	public void itemStateChanged(ItemEvent itemEvent) {
		if (itemEvent.getSource() == comboBoxProject) {
			projectDetails();
		}
	}

}