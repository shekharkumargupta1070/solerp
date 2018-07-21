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
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import com.sol.erp.constants.ApplicationConstants;
import com.sol.erp.ui.custom.SolCalendar;
import com.sol.erp.ui.custom.SolCommentBox;
import com.sol.erp.ui.custom.SolSalaryCalc;
import com.sol.erp.ui.custom.SolTableModel;
import com.sol.erp.util.DBConnectionUtil;
import com.sol.erp.util.SessionUtil;

public class Rounds implements ActionListener, MouseListener, FocusListener {
	
	Connection con;
	Statement stat;
	ResultSet rs;
	int affected;
	DeptList dep;
	
	SolSalaryCalc salCalc;
	SolCalendar skl;
	
	String[] heads;
	String[][] data;
	String[] heads1;
	String[][] data1;
	JFrame f;
	JTabbedPane tab;
	DefaultTableModel model;
	JTable tb;
	JScrollPane sp;
	DefaultTableModel model1;
	JTable tb1;
	JScrollPane sp1;
	JPanel tab1;
	JPanel tab2;
	JPanel tab3;
	JPanel tab4;
	JPanel tab5;
	JLabel aplicantlabel;
	JLabel namelabel;
	JLabel datelabel;
	JTextField aplicanttf;
	JTextField nametf;
	JTextField datetf;
	JLabel techl1;
	JLabel techl2;
	JLabel techl3;
	JLabel techl4;
	JLabel techl5;
	JLabel techl6;
	JLabel techl7;
	JLabel techl8;
	JLabel techl9;
	JLabel techl10;
	JLabel techl11;
	SalaryModeList techtf1;
	JTextField techtf2;
	DesigList techtf3;
	JComboBox techtf4;
	JComboBox techtf5;
	JCheckBox techtf6;
	JTextField techtf7;
	JComboBox techtf8;
	static JButton techcommentbut = new JButton("Comment");
	static SolCommentBox techtf9 = new SolCommentBox();

	JTextField techtf10;

	JTextField techtf11;
	JLabel hrl1;
	JLabel hrl2;
	JLabel hrl3;
	JLabel hrl4;
	JLabel hrl5;
	JLabel hrl6;
	JLabel hrl7;
	JComboBox hrtf1;
	JComboBox hrtf2;
	JComboBox hrtf3;
	JComboBox hrtf4;
	JButton hrtf5;
	JComboBox hrtf6;
	static JButton hrtf7 = new JButton("Comment");

	SolCommentBox hrReasonBox;

	SolCommentBox hrRemarksBox;

	JLabel ceol;

	JLabel ceol1;

	JLabel ceol2;

	JLabel ceol3;

	JTextField ceotf;

	SalaryModeList ceotf1;

	JTextField ceotf2;

	JComboBox ceotf3;

	JButton ceocommentbut;

	SolCommentBox ceocommentbox;

	Date dat;

	SimpleDateFormat formatter;

	String dateString;

	JButton savebut;

	JButton closebut;

	JPanel northPanel;

	JPanel techcenterpanel;
	JPanel hrcenterpanel;
	JPanel statusPanel;
	JPanel ceocenterpanel;
	Font boldfont;
	Font smallfont;
	Toolkit tk;
	Dimension ss;
	Dimension fs;
	Rounds.ColoredTableCellRenderer skr;

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

			if (paramInt1 % 2 == 0) {
				setBackground(new Color(240, 240, 240));
			}
			setForeground(Color.darkGray);

			if (paramInt1 % 2 != 0) {
				setBackground(new Color(250, 250, 250));
			}
			setForeground(Color.darkGray);

			if (paramInt2 >= 0) {
				setHorizontalAlignment(0);
			}

			String str = String.valueOf(model1.getValueAt(paramInt1, 4));
			if (str.equalsIgnoreCase("1")) {
				setBackground(new Color(200, 220, 240));
				setForeground(Color.blue);
			}
			if (str.equalsIgnoreCase("2")) {
				setBackground(new Color(220, 250, 220));
				setForeground(new Color(80, 140, 80));
			}
			if (str.equalsIgnoreCase("3")) {
				setBackground(new Color(250, 220, 220));
				setForeground(Color.red);
			}

			super.getTableCellRendererComponent(paramJTable, paramObject, paramBoolean1, paramBoolean2, paramInt1,
					paramInt2);

			return this;
		}
	}

	public Rounds() {


		affected = 0;

		dep = new DeptList("C01", "0");
		
		salCalc = new SolSalaryCalc();
		skl = new SolCalendar();

		heads = new String[] { "Aplicant No", "Name", "Post", "Salary", "SalaryMode", "Result" };
		data = new String[][] { new String[0], new String[0] };

		heads1 = new String[] { "Aplicant No", "Name", "Post", "Ref", "Rounded", "ST" };
		data1 = new String[][] { new String[0], new String[0] };

		f = new JFrame("Interview FeedBacks");
		tab = new JTabbedPane();

		model = new DefaultTableModel(data, heads);
		tb = new JTable(model);
		sp = new JScrollPane(tb);

		model1 = new DefaultTableModel(data1, heads1);
		tb1 = new JTable(model1) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public Class<? extends Object> getColumnClass(int paramAnonymousInt) {
				return getValueAt(0, paramAnonymousInt).getClass();
			}
		};
		sp1 = new JScrollPane(tb1);

		tab1 = new JPanel();
		tab2 = new JPanel();
		tab3 = new JPanel();
		tab4 = new JPanel();
		tab5 = new JPanel();

		aplicantlabel = new JLabel("Aplicant No");
		namelabel = new JLabel("Name");
		datelabel = new JLabel("Interview Date");
		aplicanttf = new JTextField("", 7);
		nametf = new JTextField("", 20);
		datetf = new JTextField("", 8);

		techl1 = new JLabel("Mode of Salary");
		techl2 = new JLabel("Recomended Salary");
		techl3 = new JLabel("Recomended Post");
		techl4 = new JLabel("Knowladge (I.Q.) Level");
		techl5 = new JLabel("Experience Level");
		techl6 = new JLabel("OT Applicablity");
		techl7 = new JLabel("Probation Period");
		techl8 = new JLabel("Employbility");
		techl9 = new JLabel("Remarks");
		techl10 = new JLabel("Performance Factor");
		techl11 = new JLabel("Expected Working Hrs/Daily");

		techtf1 = new SalaryModeList("C01", "0");
		techtf2 = new JTextField();
		techtf3 = new DesigList("C01", "0", "Tech");
		techtf4 = new JComboBox();
		techtf5 = new JComboBox();
		techtf6 = new JCheckBox("OT Applicability");

		techtf7 = new JTextField("3");
		techtf8 = new JComboBox();

		techtf10 = new JTextField();
		techtf11 = new JTextField();

		hrl1 = new JLabel("Commitment Level");
		hrl2 = new JLabel("Discipline/Behavior");
		hrl3 = new JLabel("Communication Skill");
		hrl4 = new JLabel("Stablity");
		hrl5 = new JLabel("Reason of Leave Last Job");
		hrl6 = new JLabel("Employbility");
		hrl7 = new JLabel("Remarks");

		hrtf1 = new JComboBox();
		hrtf2 = new JComboBox();
		hrtf3 = new JComboBox();
		hrtf4 = new JComboBox();
		hrtf5 = new JButton("Reason");
		hrtf6 = new JComboBox();

		hrReasonBox = new SolCommentBox();
		hrRemarksBox = new SolCommentBox();

		ceol = new JLabel("Recomended Post");
		ceol1 = new JLabel("Recomended Salary Mode");
		ceol2 = new JLabel("Recomended Salary");
		ceol3 = new JLabel("Employblity");

		ceotf = new JTextField();

		ceotf1 = new SalaryModeList("C01", "0");
		ceotf2 = new JTextField();
		ceotf3 = new JComboBox();
		ceocommentbut = new JButton("Comment");
		ceocommentbox = new SolCommentBox();

		dat = new Date();
		formatter = new SimpleDateFormat("dd/MM/yyyy");

		try {
			dateString = formatter.format(dat);
			datetf = new JTextField(String.valueOf(dateString));
		} catch (IllegalArgumentException localIllegalArgumentException) {
			System.out.println(localIllegalArgumentException.getMessage());
		}

		savebut = new JButton("Save FeedBack");
		closebut = new JButton("Close");

		northPanel = new JPanel();
		techcenterpanel = new JPanel();
		hrcenterpanel = new JPanel();
		statusPanel = new JPanel();
		ceocenterpanel = new JPanel();

		boldfont = new Font("Verdana", 0, 11);
		smallfont = new Font("Verdana", 0, 11);

		tk = Toolkit.getDefaultToolkit();
		ss = tk.getScreenSize();
		fs = f.getSize();

		skr = new Rounds.ColoredTableCellRenderer();
	}

	public void px(String paramString) {
		Container localContainer = f.getContentPane();
		localContainer.setLayout(new BorderLayout());
		tab1.setLayout(null);
		tab2.setLayout(null);
		tab3.setLayout(null);
		tab4.setLayout(new BorderLayout());
		tab5.setLayout(new BorderLayout());
		localContainer.setBackground(Color.darkGray);

		techcenterpanel.setLayout(new GridLayout(11, 2, 2, 2));
		hrcenterpanel.setLayout(new GridLayout(7, 2, 2, 2));
		ceocenterpanel.setLayout(new GridLayout(5, 2, 2, 2));

		northPanel.setLayout(new FlowLayout(0));
		statusPanel.setLayout(new FlowLayout(2));

		tab.addTab("TECH. FeedBack", tab1);
		tab.addTab("HR. FeedBack", tab2);
		tab.addTab("CEO. FeedBack", tab3);
		tab.addTab("Aplicant List", tab4);

		SolTableModel.decorateTable(tb1);
		tab4.add(sp1, "Center");

		for (int i = 0; i < tb1.getColumnCount() - 2; i++) {
			tb1.getColumnModel().getColumn(i).setCellRenderer(skr);
		}
		tb1.removeColumn(tb1.getColumnModel().getColumn(4));

		tb1.getColumnModel().getColumn(1).setPreferredWidth(180);
		tb1.getColumnModel().getColumn(4).setPreferredWidth(12);
		tb1.setShowVerticalLines(false);
		tb1.setIntercellSpacing(new Dimension(0, 1));
		tb1.setFont(smallfont);
		tb1.setGridColor(Color.gray);
		tb1.setRowHeight(25);

		SolTableModel.decorateTable(tb);
		tab5.add(sp, "Center");

		techtf4.addItem("A");
		techtf4.addItem("B");
		techtf4.addItem("C");
		techtf4.addItem("D");

		techtf5.addItem("A");
		techtf5.addItem("B");
		techtf5.addItem("C");
		techtf5.addItem("D");

		techtf8.addItem("Yes");
		techtf8.addItem("No");
		techtf8.addItem("Pending");

		northPanel.add(aplicantlabel);
		northPanel.add(aplicanttf);
		northPanel.add(namelabel);
		northPanel.add(nametf);
		northPanel.add(datelabel);
		northPanel.add(datetf);
		nametf.setEditable(false);
		nametf.setFont(boldfont);
		datetf.setEditable(false);
		datetf.setFont(boldfont);

		techcenterpanel.setBounds(100, 20, 400, 270);
		techcenterpanel.add(techl1);
		techcenterpanel.add(techtf1);
		techcenterpanel.add(techl2);
		techcenterpanel.add(techtf2);
		techcenterpanel.add(techl3);
		techcenterpanel.add(techtf3);
		techcenterpanel.add(techl4);
		techcenterpanel.add(techtf4);
		techcenterpanel.add(techl5);
		techcenterpanel.add(techtf5);
		techcenterpanel.add(techl6);
		techcenterpanel.add(techtf6);
		techcenterpanel.add(techl7);
		techcenterpanel.add(techtf7);
		techcenterpanel.add(techl8);
		techcenterpanel.add(techtf8);
		techcenterpanel.add(techl9);
		techcenterpanel.add(techcommentbut);
		techcommentbut.setToolTipText(String.valueOf(techtf9.getComment()));
		techcenterpanel.add(techl10);
		techcenterpanel.add(techtf10);
		techcenterpanel.add(techl11);
		techcenterpanel.add(techtf11);
		techtf1.setEditable(false);
		techtf3.setEditable(false);
		techcenterpanel.setBorder(BorderFactory.createTitledBorder("Feed Back Form"));
		tab1.add(techcenterpanel);

		hrcenterpanel.setBounds(100, 60, 400, 200);
		hrcenterpanel.add(hrl1);
		hrcenterpanel.add(hrtf1);
		hrcenterpanel.add(hrl2);
		hrcenterpanel.add(hrtf2);
		hrcenterpanel.add(hrl3);
		hrcenterpanel.add(hrtf3);
		hrcenterpanel.add(hrl4);
		hrcenterpanel.add(hrtf4);
		hrcenterpanel.add(hrl5);
		hrcenterpanel.add(hrtf5);
		hrcenterpanel.add(hrl6);
		hrcenterpanel.add(hrtf6);
		hrcenterpanel.add(hrl7);
		hrcenterpanel.add(hrtf7);
		hrcenterpanel.setBorder(BorderFactory.createTitledBorder("Feed Back Form"));
		tab2.add(hrcenterpanel);

		ceocenterpanel.setBounds(60, 70, 510, 145);
		ceocenterpanel.add(ceol);
		ceocenterpanel.add(ceotf);
		ceocenterpanel.add(ceol1);
		ceocenterpanel.add(ceotf1);
		ceocenterpanel.add(ceol2);
		ceocenterpanel.add(ceotf2);
		ceocenterpanel.add(ceol3);
		ceocenterpanel.add(ceotf3);
		ceocenterpanel.add(ceocommentbut);
		ceocenterpanel.setBorder(BorderFactory.createTitledBorder("FeedBack Form"));
		tab3.add(ceocenterpanel);
		ceotf.setEditable(false);
		ceotf1.setEditable(false);
		ceotf2.setEditable(true);

		ceotf3.addItem("Selected");
		ceotf3.addItem("Deselected");
		ceotf3.addItem("Pending");

		hrtf1.addItem("Good");
		hrtf1.addItem("Very Good");
		hrtf1.addItem("Poor");

		hrtf2.addItem("Good");
		hrtf2.addItem("Very Good");
		hrtf2.addItem("Poor");

		hrtf3.addItem("Good");
		hrtf3.addItem("Very Good");
		hrtf3.addItem("Poor");

		hrtf4.addItem("Yes");
		hrtf4.addItem("No");
		hrtf4.addItem("I Can't Say");

		hrtf6.addItem("Yes");
		hrtf6.addItem("No");
		hrtf6.addItem("Pending");

		statusPanel.add(savebut);
		statusPanel.add(closebut);

		localContainer.add(northPanel, "North");
		localContainer.add(tab, "Center");
		localContainer.add(statusPanel, "South");

		f.setSize(720, 420);
		f.setLocationRelativeTo(null);
		f.setResizable(true);
		f.setVisible(true);
		f.setTitle(paramString);

		techcommentbut.addActionListener(this);
		ceocommentbut.addActionListener(this);
		hrtf5.addActionListener(this);
		hrtf7.addActionListener(this);
		closebut.addActionListener(this);

		tb1.addMouseListener(this);

		techtf1.addMouseListener(this);
		techtf2.addMouseListener(this);
		techtf3.addMouseListener(this);
		techtf6.addMouseListener(this);
		techtf7.addMouseListener(this);
		techtf10.addMouseListener(this);
		techtf11.addMouseListener(this);

		techtf2.addActionListener(this);
		ceotf.addMouseListener(this);
		ceotf1.addMouseListener(this);
		ceotf2.addActionListener(this);

		savebut.addActionListener(this);
		closebut.addActionListener(this);
		aplicanttf.addActionListener(this);
		aplicanttf.addFocusListener(this);
		datetf.addMouseListener(this);
		datetf.addFocusListener(this);

		tab.addMouseListener(this);
		tab1.addMouseListener(this);
		tab2.addMouseListener(this);
		tab3.addMouseListener(this);
		tab4.addMouseListener(this);
		tab5.addMouseListener(this);
	}

	public void TECHRECORDSAVE() {
		String str1 = SessionUtil.getLoginInfo().getId();
		String str2 = SessionUtil.getLoginInfo().getCompanyId();
		String str3 = SessionUtil.getLoginInfo().getBranchCode();
		String str4 = "false";

		if (techtf6.isSelected() == true) {
			str4 = "true";
		}
		if (!techtf6.isSelected()) {
			str4 = "false";
		}

		try {
			con = DBConnectionUtil.getConnection();
			stat = con.createStatement();
			stat.executeUpdate(" DELETE FROM HR_TECH_ROUNDS WHERE company_id = '" + str2 + "' and branch_code= '"
					+ str3 + "' and  aplicant_no ='" + aplicanttf.getText() + "' ");
		} catch (Exception localException1) {
			System.out.println("HR_TECH_ROUNDS " + localException1.getMessage().toString());
		}

		try {
			stat = con.createStatement();
			affected = stat.executeUpdate("Insert into HR_TECH_ROUNDS values('" + str2 + "','" + str3 + "','"
					+ aplicanttf.getText() + "','" + nametf.getText() + "','" + techtf1.getText() + "','"
					+ techtf2.getText() + "','" + techtf3.getText() + "','" + techtf4.getSelectedItem()
					+ "','" + techtf5.getSelectedItem() + "','" + str4 + "','" + techtf7.getText() + "','"
					+ techtf8.getSelectedItem() + "','" + techtf9.getComment() + "','" + techtf10.getText()
					+ "','" + techtf11.getText() + "','" + str1 + "','" + datetf.getText() + "' ) ");

			if (affected > 0) {
				affected = stat.executeUpdate("update HRINTERVIEW_CALL set Rounded='1' where aplicant_no='"
						+ aplicanttf.getText() + "' ");
				if (affected > 0) {
					JOptionPane.showMessageDialog(f, "Thank You.");
				}
			}
		} catch (Exception localException2) {
			JOptionPane.showMessageDialog(f, localException2.getMessage().toString());
		}
	}

	public void HRRECORDSAVE() {
		String str1 = SessionUtil.getLoginInfo().getId();
		String str2 = SessionUtil.getLoginInfo().getCompanyId();
		String str3 = SessionUtil.getLoginInfo().getBranchCode();

		try {
			con = DBConnectionUtil.getConnection();
			stat = con.createStatement();
			stat.executeUpdate(" DELETE FROM HR_HR_ROUNDS WHERE company_id = '" + str2 + "' and branch_code= '"
					+ str3 + "' and  aplicant_no ='" + aplicanttf.getText() + "' ");
		} catch (Exception localException1) {
			System.out.println("HR_HR_ROUNDS " + localException1.getMessage().toString());
		}
		try {
			stat = con.createStatement();
			affected = stat.executeUpdate("Insert into HR_HR_ROUNDS values('" + str2 + "','" + str3 + "','"
					+ aplicanttf.getText() + "','" + nametf.getText() + "','" + hrtf1.getSelectedItem()
					+ "','" + hrtf2.getSelectedItem() + "','" + hrtf3.getSelectedItem() + "','"
					+ hrtf4.getSelectedItem() + "','" + hrReasonBox.getComment() + "','"
					+ hrtf6.getSelectedItem() + "','" + hrRemarksBox.getComment() + "','" + str1 + "','"
					+ datetf.getText() + "' ) ");

			if (affected > 0) {
				affected = stat.executeUpdate("update HRINTERVIEW_CALL set Rounded='1' where aplicant_no='"
						+ aplicanttf.getText() + "' ");
				if (affected > 0) {
					JOptionPane.showMessageDialog(f, "Thank You.");
				}
			}
		} catch (Exception localException2) {
			JOptionPane.showMessageDialog(f, localException2.getMessage().toString());
		}
	}

	public void CEORECORDSAVE() {
		String str2 = SessionUtil.getLoginInfo().getCompanyId();
		String str3 = SessionUtil.getLoginInfo().getBranchCode();

		String str4 = "false";

		if (techtf6.isSelected() == true) {
			str4 = "true";
		}
		if (!techtf6.isSelected()) {
			str4 = "false";
		}

		try {
			con = DBConnectionUtil.getConnection();
			stat = con.createStatement();
			affected = stat.executeUpdate("DELETE FROM HR_CEO_ROUNDS WHERE company_id = '" + str2
					+ "' and branch_code= '" + str3 + "' and  aplicant_no ='" + aplicanttf.getText() + "' ");
			if (affected > 0) {
			}

			try {
				stat = con.createStatement();
				stat.executeUpdate("Insert into HR_CEO_ROUNDS values('" + str2 + "','" + str3 + "','"
						+ aplicanttf.getText() + "','" + nametf.getText() + "','" + ceotf1.getText()
						+ "','" + ceotf2.getText() + "','" + ceotf.getText() + "','"
						+ ceotf3.getSelectedItem() + "','" + ceocommentbox.getComment() + "','"
						+ datetf.getText() + "','" + str4 + "','" + techtf7.getText() + "','"
						+ techtf10.getText() + "','" + techtf11.getText() + "') ");

				if (affected > 0) {
					affected = stat.executeUpdate(
							"update HRINTERVIEW_CALL set Rounded='3', STATUS='" + ceotf3.getSelectedItem()
									+ "' where aplicant_no='" + aplicanttf.getText() + "'");
					if (affected > 0) {
						JOptionPane.showMessageDialog(f, "Thank You.");
					}
				}
			} catch (Exception localException2) {
				JOptionPane.showMessageDialog(f, localException2.getMessage().toString());
			}
		} catch (Exception localException1) {
			System.out.println("HR_CEO_ROUNDS " + localException1.getMessage().toString());
		}
	}

	public void getAplicantName() {
		try {
			con = DBConnectionUtil.getConnection();
			stat = con.createStatement();
			rs = stat.executeQuery(
					"select first_name, middle_name, last_name, DATE from HRINTERVIEW_CALL where Aplicant_no='"
							+ aplicanttf.getText() + "' ");

			while (rs.next()) {
				String str1 = new String(rs.getString(1));
				String str2 = new String(rs.getString(2));
				String str3 = new String(rs.getString(3));
				String str4 = new String(rs.getString(4));

				nametf.setText(str1.toUpperCase() + " " + str2.toUpperCase() + " " + str3.toUpperCase());
				datetf.setText(str4);
			}
		} catch (Exception localException) {
			System.out.println("getAplicantName : " + localException);
		}
	}

	public void getAplicantHRDetails() {
		nametf.setText("");
		hrtf1.setSelectedIndex(0);
		hrtf2.setSelectedIndex(0);
		hrtf3.setSelectedIndex(0);
		hrtf4.setSelectedIndex(0);
		hrtf6.setSelectedIndex(0);
		hrReasonBox.clear();
		hrRemarksBox.clear();

		try {
			con = DBConnectionUtil.getConnection();
			stat = con.createStatement();
			rs = stat.executeQuery(
					"select name, com_level, disp, comm_skill, stable, reason, employblity, remarks, Person, DATE from HR_HR_ROUNDS where Aplicant_no='"
							+ aplicanttf.getText() + "' ");

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
				String str10 = new String(rs.getString(10));

				nametf.setText(str1);
				hrtf1.setSelectedItem(str2);
				hrtf2.setSelectedItem(str3);
				hrtf3.setSelectedItem(str4);
				hrtf4.setSelectedItem(str5);
				hrReasonBox.setComment(str6);
				hrtf6.setSelectedItem(str7);
				hrRemarksBox.setComment(str8);

				datetf.setText(str10);
			}
		} catch (Exception localException) {
			System.out.println("getAplicantHRDetails : " + localException);
		}
	}

	public void getAplicantTechDetails() {
		nametf.setText("");
		techtf1.setText("");
		techtf2.setText("");
		techtf3.setText("");
		techtf4.setSelectedIndex(0);
		techtf5.setSelectedIndex(0);

		techtf7.setText("3");
		techtf8.setSelectedIndex(0);
		techtf9.clear();
		techtf10.setText(".2");
		techtf11.setText("9");

		try {
			con = DBConnectionUtil.getConnection();
			stat = con.createStatement();
			rs = stat.executeQuery(
					"select name, rec_mode_salary, rec_salary, rec_post, iq_level, exp_level, OT_apl, Prob_Period, Employblity, Remarks, Factor, Work_hrs, Person  from HR_TECH_ROUNDS where Aplicant_no='"
							+ aplicanttf.getText() + "' ");

			while (rs.next()) {
				String str1 = new String(rs.getString(1));
				String str2 = new String(rs.getString(2));
				String str3 = new String(rs.getString(3));
				String str4 = new String(rs.getString(4));
				String str5 = new String(rs.getString(5));
				String str6 = new String(rs.getString(6));
				String str7 = new String(rs.getString(7));
				String str8 = new String(rs.getString(8));
				String str9 = new String(rs.getString(9));
				String str10 = new String(rs.getString(10));
				String str11 = new String(rs.getString(11));
				String str12 = new String(rs.getString(12));
				new String(rs.getString(13));

				nametf.setText(str1);
				techtf1.setText(str2);
				techtf2.setText(str3);
				techtf3.setText(str4);
				techtf4.setSelectedItem(str5);
				techtf5.setSelectedItem(str6);
				techtf6.setSelected(new Boolean(str7).booleanValue());
				techtf7.setText(str8);
				techtf8.setSelectedItem(str9);
				techtf9.setComment(str10);
				techtf10.setText(str11);
				techtf11.setText(str12);

				if (ceotf.getText().length() < 2) {
					ceotf.setText(str4);
					ceotf1.setText(str2);
					ceotf2.setText(str3);
				}
			}
		} catch (Exception localException) {
			System.out.println("getAplicantTechDetails : " + localException);
		}
	}

	public void getAplicantCEODetails() {
		ceotf.setText("");
		ceotf1.setText("");
		ceotf2.setText("");
		ceotf3.setSelectedIndex(0);
		ceocommentbox.clear();

		try {
			con = DBConnectionUtil.getConnection();
			stat = con.createStatement();
			rs = stat.executeQuery(
					"select name, rec_post,rec_mode_salary, rec_salary, Employblity, Remarks from HR_CEO_ROUNDS where Aplicant_no='"
							+ aplicanttf.getText() + "' ");

			while (rs.next()) {
				String str1 = new String(rs.getString(1));
				String str2 = new String(rs.getString(2));
				String str3 = new String(rs.getString(3));
				String str4 = new String(rs.getString(4));
				String str5 = new String(rs.getString(5));
				String str6 = new String(rs.getString(6));

				nametf.setText(str1);
				ceotf.setText(str2);
				ceotf1.setText(str3);
				ceotf2.setText(str4);
				ceotf3.setSelectedItem(str5);
				ceocommentbox.setComment(str6);
			}
		} catch (Exception localException) {
			System.out.println("getAplicantCEODetails : " + localException);
		}
	}

	public void getAplicantList() {
		model1.setNumRows(0);
		aplicanttf.getText();
		try {
			con = DBConnectionUtil.getConnection();
			stat = con.createStatement();
			rs = stat.executeQuery(
					"select aplicant_no, first_name, middle_name, Last_name, post, Ref,Rounded,Status from HRINTERVIEW_CALL where date='"
							+ datetf.getText() + "' ORDER BY Aplicant_no ");

			while (rs.next()) {
				String str2 = new String(rs.getString(1));
				String str3 = new String(rs.getString(2));
				String str4 = new String(rs.getString(3));
				String str5 = new String(rs.getString(4));
				String str6 = new String(rs.getString(5));
				String str7 = new String(rs.getString(6));
				String str8 = new String(rs.getString(7));
				String str9 = new String(rs.getString(8));

				model1.addRow(new Object[] { str2.toUpperCase(),
						str3.toUpperCase() + " " + str4.toUpperCase() + " " + str5.toUpperCase(), str6.toUpperCase(),
						str7.toUpperCase(), str8.toUpperCase(), new ImageIcon(ClassLoader.getSystemResource(ApplicationConstants.IMAGE_PATH + str9 + ".gif")) });
			}
		} catch (Exception localException) {
			System.out.println("getAplicantName : " + localException);
		}
		tab.setSelectedIndex(3);
	}

	public void getResult() {
		model.setNumRows(0);
		aplicanttf.getText();
		try {
			con = DBConnectionUtil.getConnection();
			stat = con.createStatement();
			rs = stat.executeQuery(
					"select aplicant_no, name, rec_post,rec_mode_salary, rec_salary, Employblity, Remarks from HR_CEO_ROUNDS where date='"
							+ datetf.getText() + "' ");

			while (rs.next()) {
				String str2 = new String(rs.getString(1));
				String str3 = new String(rs.getString(2));
				String str4 = new String(rs.getString(3));
				String str5 = new String(rs.getString(4));
				String str6 = new String(rs.getString(5));
				String str7 = new String(rs.getString(6));

				model.addRow(new Object[] { str2.toUpperCase(), str3.toUpperCase(), str4.toUpperCase(),
						str5.toUpperCase(), str6.toUpperCase(), str7.toUpperCase() });
			}
		} catch (Exception localException) {
			System.out.println("getAplicantName : " + localException);
		}
	}

	public void selectedRecord() {
		int i = tb1.getSelectedRow();
		String str1 = String.valueOf(model1.getValueAt(i, 0));
		String str2 = String.valueOf(model1.getValueAt(i, 1));
		aplicanttf.setText(str1);
		nametf.setText(str2);
	}

	public void setButtonFalse() {
		String str = String.valueOf(f.getTitle());

		savebut.setEnabled(true);
		int i = tab.getSelectedIndex();

		if (EmpTB.getDept(str).equalsIgnoreCase("Tech")) {
			savebut.setEnabled(true);
			if ((i == 1) || (i == 2) || (i == 3)) {
				savebut.setEnabled(false);
			}
			if (EmpTB.getDesig(str).equalsIgnoreCase("CEO")) {
				savebut.setEnabled(true);
				if ((i == 0) || (i == 1) || (i == 3)) {
					savebut.setEnabled(false);
				}
			}
		}
		if (EmpTB.getDept(str).equalsIgnoreCase("HR")) {
			if ((i == 0) || (i == 2) || (i == 3)) {
				savebut.setEnabled(false);
			}
		}
	}

	public void actionPerformed(ActionEvent paramActionEvent) {
		if (paramActionEvent.getSource() == aplicanttf) {
			getAplicantCEODetails();
			getAplicantTechDetails();
			getAplicantName();
			getAplicantList();
		}

		if (paramActionEvent.getSource() == techcommentbut) {
			techtf9.showCommentBox();
			techtf9.setTitle("Manager Project's Employblity Comments");
		}
		if (paramActionEvent.getSource() == ceocommentbut) {
			ceocommentbox.showCommentBox();
			ceocommentbox.setTitle("CEO's Employblity Comments");
		}
		if (paramActionEvent.getSource() == hrtf5) {
			hrReasonBox.showCommentBox();
			hrReasonBox.setTitle("Reason of Leaving The Last Job");
		}
		if (paramActionEvent.getSource() == hrtf7) {
			hrRemarksBox.showCommentBox();
			hrRemarksBox.setTitle("HR Person's Employblity Comments");
		}
		if (paramActionEvent.getSource() == savebut) {
			int i = tab.getSelectedIndex();
			if (i == 0) {
				TECHRECORDSAVE();
			}
			if (i == 1) {
				HRRECORDSAVE();
			}
			if (i == 2) {
				CEORECORDSAVE();
			}
		}

		if (paramActionEvent.getSource() == closebut) {
			f.setVisible(false);
		}
		if (paramActionEvent.getSource() == techtf2) {
			salCalc.showSalaryCalc();
			salCalc.setSalaryMode(String.valueOf(techtf1.getText()));
			salCalc.setInhand(String.valueOf(techtf2.getText()));
			salCalc.salaryCalc();
		}
		if (paramActionEvent.getSource() == ceotf2) {
			salCalc.showSalaryCalc();
			salCalc.setSalaryMode(String.valueOf(ceotf2.getText()));
			salCalc.setInhand(String.valueOf(ceotf2.getText()));
			salCalc.salaryCalc();
		}
	}

	public void mousePressed(MouseEvent paramMouseEvent) {
		if (paramMouseEvent.getSource() == tb1) {
			selectedRecord();
			getAplicantCEODetails();
			getAplicantTechDetails();
			getAplicantHRDetails();
			getAplicantName();
		}

		if (paramMouseEvent.getSource() == tab) {
			setButtonFalse();

			int i = tab.getSelectedIndex();
			if (i == 3) {
				getAplicantList();
				tb1.revalidate();
			}
			if (i == 4) {
				getResult();
			}
		}
	}

	public void mouseReleased(MouseEvent paramMouseEvent) {
		if (SwingUtilities.isRightMouseButton(paramMouseEvent)) {
			if ((paramMouseEvent.getSource() == techtf1) && (

			(paramMouseEvent.getSource() == techtf3) && (

			(paramMouseEvent.getSource() == ceotf) && (

			(paramMouseEvent.getSource() == ceotf1) &&

			(paramMouseEvent.getSource() != datetf))))) {
			}
		}
	}

	public void mouseClicked(MouseEvent paramMouseEvent) {
	}

	public void mouseEntered(MouseEvent paramMouseEvent) {
	}

	public void mouseExited(MouseEvent paramMouseEvent) {
	}

	public void focusGained(FocusEvent paramFocusEvent) {
	}

	public void focusLost(FocusEvent paramFocusEvent) {
		if ((paramFocusEvent.getSource() != aplicanttf) ||

		(paramFocusEvent.getSource() == datetf)) {
		}
	}
}
