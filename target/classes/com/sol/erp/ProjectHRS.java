package com.sol.erp;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import com.sol.erp.ui.custom.SolCalendar;
import com.sol.erp.ui.custom.SolCellModel;
import com.sol.erp.ui.custom.SolTableModel;
import com.sol.erp.util.DBConnectionUtil;
import com.sol.erp.util.DateCalculationUtil;
import com.sol.erp.util.formater.SolDateFormatter;

public class ProjectHRS implements ActionListener, MouseListener, KeyListener {
	Connection con;
	Connection con1;
	Statement stat;
	ResultSet rs;
	public static String codestr;
	
	SolCalendar skl;

	CompanyTable ct;
	EmpWorkAlloted ew;
	String[] head;
	Object[][] data;
	ArrayList ar;
	DecimalFormat df;
	DecimalFormat df2;
	JFrame f;
	DefaultListModel listmodel;
	JList list;
	JScrollPane listsp;
	DefaultTableModel model;
	JTable tb;
	JScrollPane sp;
	JSplitPane split;
	JProgressBar progressBar;
	JProgressBar subprogress;
	JLabel proglabel;
	JLabel projectlabel;
	JTextField projecttf;
	JLabel datelabel1;
	JTextField datetf1;
	JLabel datelabel2;
	JTextField datetf2;
	JMenuBar menubar;
	JMenu ma;
	JMenuItem ma1;
	JMenuItem ma2;
	JPanel mainPanel;
	JPanel northpanel;
	JPanel centerpanel;
	JPanel bottompanel;
	JPanel northp1;
	JPanel northp2;
	JPanel progpanel1;
	JPanel progpanel2;
	JPanel southpanel;
	JPanel southpanel2;
	JPanel southpanel3;
	Toolkit tk;
	java.util.Date dat;
	SimpleDateFormat formatter;
	String dateString;
	String time;
	Font fo;

	SolCellModel skr;
	ProjectHRS.Renderer rend1;
	TechMainFrame tmf;
	String coidstr;
	String brcodestr;

	class Renderer extends DefaultTableCellRenderer {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		Renderer() {
		}

		public Component getTableCellRendererComponent(JTable paramJTable, Object paramObject, boolean paramBoolean1,
				boolean paramBoolean2, int paramInt1, int paramInt2) {
			setEnabled((paramJTable == null) || (paramJTable.isEnabled()));

			if (paramInt1 % 2 == 0) {
				setBackground(new Color(240, 240, 240));
				setForeground(Color.darkGray);
			} else {
				setBackground(new Color(230, 230, 230));
				setForeground(Color.darkGray);
			}

			if (paramInt2 == 0) {
				setHorizontalAlignment(0);
			}
			if (paramInt2 == 1) {
				setHorizontalAlignment(2);
			}

			if ((paramInt2 > 1) && (paramInt2 < 33)) {
				setHorizontalAlignment(0);
				setBackground(Color.white);

				if (String.valueOf(model.getValueAt(paramInt1, paramInt2)).equalsIgnoreCase("Y")) {
					setForeground(new Color(0, 140, 0));
					setBackground(Color.white);
				}
				if (String.valueOf(model.getValueAt(paramInt1, paramInt2)).equalsIgnoreCase("A")) {
					setForeground(Color.red);
					setBackground(Color.white);
				}
				if (String.valueOf(model.getValueAt(paramInt1, paramInt2)).equalsIgnoreCase("C")) {
					setForeground(Color.red);
					setBackground(new Color(240, 240, 255));
				}
				if (String.valueOf(model.getValueAt(paramInt1, paramInt2)).equalsIgnoreCase("E")) {
					setBackground(new Color(240, 255, 255));
					setForeground(Color.blue);
				}
			}

			if ((paramInt2 == 34) || (paramInt2 == 35)) {
				setHorizontalAlignment(4);
			}

			super.getTableCellRendererComponent(paramJTable, paramObject, paramBoolean1, paramBoolean2, paramInt1,
					paramInt2);

			return this;
		}
	}

	public JPanel DesignFrame() {
		con = DBConnectionUtil.getConnection();
		Container localContainer = f.getContentPane();
		localContainer.setLayout(new BorderLayout());
		mainPanel.setLayout(new BorderLayout());
		centerpanel.setLayout(new BorderLayout());
		northpanel.setLayout(new BorderLayout());
		northp1.setLayout(new FlowLayout(0));
		northp1.setLayout(new FlowLayout(2));
		bottompanel.setLayout(new BorderLayout());
		southpanel.setLayout(new BorderLayout());
		southpanel2.setLayout(new FlowLayout(2));
		southpanel3.setLayout(new FlowLayout(2));

		SolTableModel.decorateTable(tb);

		tb.getColumnModel().getColumn(0).setPreferredWidth(60);
		tb.getColumnModel().getColumn(1).setPreferredWidth(210);

		for (int i = 0; i < tb.getColumnCount(); i++) {
			tb.getColumnModel().getColumn(i).setCellRenderer(rend1);
		}

		for (int i = 0; i < tb.getColumnCount() - 5; i++) {
			tb.getColumnModel().getColumn(i + 2).setPreferredWidth(25);
		}

		tb.getColumnModel().getColumn(33).setPreferredWidth(45);
		tb.getColumnModel().getColumn(34).setPreferredWidth(60);
		tb.getColumnModel().getColumn(35).setPreferredWidth(60);

		tb.setIntercellSpacing(new Dimension(1, 1));
		tb.setFont(fo);

		northp1.add(projectlabel);
		northp1.add(projecttf);

		northp1.add(datelabel1);
		northp1.add(datetf1);
		northp1.add(datelabel2);
		northp1.add(datetf2);

		northpanel.add(northp1, "East");
		northpanel.add(northp2, "West");

		split.setOneTouchExpandable(true);
		centerpanel.add(sp, "Center");

		progpanel1.add(proglabel);

		progpanel1.add(progressBar);
		progressBar.setPreferredSize(new Dimension(350, 14));

		progressBar.setForeground(Color.blue);
		subprogress.setForeground(Color.blue);

		southpanel.add(progpanel1, "West");
		southpanel.add(progpanel2, "East");

		bottompanel.add(southpanel, "North");
		bottompanel.add(southpanel2, "Center");

		ma.add(ma1);
		ma.addSeparator();
		ma.add(ma2);
		menubar.add(ma);

		mainPanel.add(northpanel, "North");
		mainPanel.add(centerpanel, "Center");
		mainPanel.add(bottompanel, "South");

		localContainer.add(mainPanel, "Center");

		split.setDividerLocation(100);

		projecttf.addActionListener(this);

		tb.addMouseListener(this);
		tb.addKeyListener(this);

		datetf1.addMouseListener(this);

		f.setJMenuBar(menubar);

		return mainPanel;
	}

	public void px() {
		DesignFrame();
		f.setSize(tk.getScreenSize());
		f.setVisible(true);
	}

	public ProjectHRS() {
		skl = new SolCalendar();
		ct = new CompanyTable();
		ew = new EmpWorkAlloted();

		head = new String[] { "EMP", "Emp Name", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13",
				"14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30",
				"31", "H", "Cap", "Used", "Wasted", "Free" };
		data = new Object[][] { new Object[0] };

		ar = new ArrayList();
		df = new DecimalFormat("00");
		df2 = new DecimalFormat("0");

		f = new JFrame("SOL GROUP [MANPOWER STATUS]");

		listmodel = new DefaultListModel();
		list = new JList(listmodel);
		listsp = new JScrollPane(list);

		model = new DefaultTableModel(data, head);
		tb = new JTable(model) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int paramAnonymousInt1, int paramAnonymousInt2) {
				int i = convertColumnIndexToModel(paramAnonymousInt2);
				return i < 0;
			}
		};
		sp = new JScrollPane(tb);

		split = new JSplitPane(1, listsp, sp);

		progressBar = new JProgressBar(0, 100);
		subprogress = new JProgressBar(0, 100);
		proglabel = new JLabel("Emp Code : ");

		projectlabel = new JLabel("Project ");
		projecttf = new JTextField(7);

		datelabel1 = new JLabel("From");
		datetf1 = new JTextField(8);

		datelabel2 = new JLabel("TO");
		datetf2 = new JTextField(8);

		menubar = new JMenuBar();
		ma = new JMenu("File");
		ma1 = new JMenuItem("Print Report");
		ma2 = new JMenuItem("Exit");

		mainPanel = new JPanel();

		northpanel = new JPanel();
		centerpanel = new JPanel();
		bottompanel = new JPanel();

		northp1 = new JPanel();
		northp2 = new JPanel();

		progpanel1 = new JPanel();
		progpanel2 = new JPanel();
		southpanel = new JPanel();
		southpanel2 = new JPanel();
		southpanel3 = new JPanel();

		tk = Toolkit.getDefaultToolkit();

		dat = new java.util.Date();
		formatter = new SimpleDateFormat("dd/MM/yyyy");

		try {
			dateString = formatter.format(dat);
			datetf1.setText(dateString);
		} catch (IllegalArgumentException localIllegalArgumentException) {
			System.out.println(localIllegalArgumentException.getMessage());
		}

		fo = new Font("Tahoma", 1, 10);

		skr = new SolCellModel();

		rend1 = new ProjectHRS.Renderer();

		tmf = new TechMainFrame();
		coidstr = TechMainFrame.textFieldCompanyId.getText();
		brcodestr = TechMainFrame.textFieldBranchCode.getText();
	}

	public void empList2() {
		model.setNumRows(0);
		try {
			con = DBConnectionUtil.getConnection();
			stat = con.createStatement();
			rs = stat.executeQuery(
					"SELECT distinct(emp_code) from PROJECTMANPOWER where PROJECT_NO='" + projecttf.getText() + "' ");
			while (rs.next()) {
				String str = new String(rs.getString(1));
				model.addRow(new Object[] { str, str, "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "",
						"", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "0" });
			}
		} catch (Exception localException) {
			System.out.println(localException);
		}
		model.addRow(new Object[] { "", "TOTAL", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "",
				"", "", "", "", "", "", "", "", "", "", "", "", "", "0" });
	}

	public class report extends Thread {
		public report() {
		}

		public void run() {

			int j = tb.getRowCount() - 1;

			for (int k = 0; k < j; k++) {
				String.valueOf(model.getValueAt(k, 0));

				String str2 = String.valueOf(df.format(DateCalculationUtil.getMonth(datetf1.getText())));
				String str3 = String.valueOf(DateCalculationUtil.getYear(datetf1.getText()));

				String str4 = String.valueOf(model.getValueAt(k, 0));

				proglabel.setText("Emp Code : " + str4);
				progressBar.setMinimum(0);
				progressBar.setMaximum(j - 1);
				progressBar.setValue(k);

				try {
					Thread.sleep(10L);
				} catch (InterruptedException localInterruptedException) {
					System.out.println(localInterruptedException);
				}

				int n = DateCalculationUtil.getNumberOfDays(datetf1.getText());

				for (int m = 2; m < n + 2; m++) {
					String str5 = String.valueOf(df.format(m - 1));
					String str6 = str5 + "/" + str2 + "/" + str3;

					subprogress.setMinimum(2);
					subprogress.setMaximum(30);
					subprogress.setValue(m);

					java.sql.Date localDate = SolDateFormatter.DDMMYYtoSQL(str6);

					System.out.println(str6);
					ResultSet localResultSet;
					String str7;
					try {
						PreparedStatement localPreparedStatement1 = con
								.prepareStatement("Select project_no from projectmanpower where emp_code='" + str4
										+ "' and from_date2=? ");
						localPreparedStatement1.setDate(1, localDate);
						localResultSet = localPreparedStatement1.executeQuery();

						if (localResultSet.getRow() < 0) {
							model.setValueAt("X", k, m);
						}

						while (localResultSet.next()) {
							str7 = new String(localResultSet.getString(1));
							model.setValueAt("Y", k, m);
						}
					} catch (Exception localException1) {
						System.out.println(localException1);
					}

					try {
						PreparedStatement localPreparedStatement2 = con
								.prepareStatement("Select category from HREMP_LEAVES where emp_code='" + str4
										+ "' and approve_tl='true' and approve_hr='true' AND Reason not like 'ABSENT COUNT' and date= ? ");
						localPreparedStatement2.setDate(1, localDate);
						localResultSet = localPreparedStatement2.executeQuery();

						str7 = null;

						while (localResultSet.next()) {
							str7 = new String(localResultSet.getString(1));

							if (str7.equalsIgnoreCase("LWP")) {
								model.setValueAt("A", k, m);
								str7 = null;
							}
							if (str7.equalsIgnoreCase("EL")) {
								model.setValueAt("E", k, m);
								str7 = null;
							}
							if (str7.equalsIgnoreCase("CL")) {
								model.setValueAt("C", k, m);
								str7 = null;
							}
						}
						localPreparedStatement2.close();
					} catch (Exception localException2) {
						System.out.println("Thread Run [HREMP_LEAVES]: " + localException2);
					}
				}
			}
		}
	}

	public void capacity() {
		int i = 5;
		if ((i == 4) || (i == 6) || (i == 9) || (i == 11)) {
		}

		if ((i == 1) || (i == 3) || (i == 5) || (i == 7) || (i == 8) || (i == 10) || (i == 12)) {
		}

		int k = 2007;
		int m = k % 4;

		if (i == 2) {
			if (m == 0) {
			} else {
			}
		}
	}

	public void calculate() {
		int i = 0;
		int j = 0;

		float f1 = 0.0F;
		float f2 = 0.0F;
		float f3 = 0.0F;

		float f4 = 0.0F;
		float f5 = 0.0F;
		float f6 = 0.0F;
		float f7 = 0.0F;
		float f8 = 0.0F;
		int k = 0;

		for (int m = 0; m < tb.getRowCount() - 1; m++) {
			float f9 = Float.parseFloat(String.valueOf(model.getValueAt(m, 33)));
			float f10 = Float.parseFloat(String.valueOf(model.getValueAt(m, 34)));

			for (int n = 2; n < 33; n++) {
				String str = String.valueOf(model.getValueAt(m, n));

				if (str.equalsIgnoreCase("Y")) {
					i += 1;
				}
				if ((str.equalsIgnoreCase("A")) || (str.equalsIgnoreCase("C")) || (str.equalsIgnoreCase("E"))) {
					j += 1;
				}
			}

			f1 = f9 * i;
			f2 = f9 * j;
			f3 = f10 - (f1 + f2);

			model.setValueAt(String.valueOf(df2.format(f1)), m, 35);
			model.setValueAt(String.valueOf(df2.format(f2)), m, 36);
			model.setValueAt(String.valueOf(df2.format(f3)), m, 37);
			i = 0;
			j = 0;

			f4 += f9;
			f5 += f10;
			f6 += f1;
			f7 += f2;
			f8 += f3;
			k = m;
		}

		model.setValueAt(String.valueOf(df2.format(f4)), k + 1, 33);
		model.setValueAt(String.valueOf(df2.format(f5)), k + 1, 34);
		model.setValueAt(String.valueOf(df2.format(f6)), k + 1, 35);
		model.setValueAt(String.valueOf(df2.format(f7)), k + 1, 36);
		model.setValueAt(String.valueOf(df2.format(f8)), k + 1, 37);
	}

	public void clear() {
		for (int i = 0; i < tb.getRowCount(); i++) {
			for (int j = 2; j < tb.getColumnCount(); j++) {
				if (j != 33) {

					model.setValueAt(" ", i, j);
				}
			}
		}
	}

	public void actionPerformed(ActionEvent paramActionEvent) {
		if (paramActionEvent.getSource() == projecttf) {
			empList2();
		}
	}

	public void mouseClicked(MouseEvent paramMouseEvent) {
		if (paramMouseEvent.getSource() == datetf1) {
			if (SwingUtilities.isRightMouseButton(paramMouseEvent)) {
				skl.showCalendar();
				skl.getDate(datetf1);
			}
		}
	}

	public void keyPressed(KeyEvent paramKeyEvent) {
		int i = paramKeyEvent.getKeyCode();

		if (paramKeyEvent.getSource() == tb) {

			if (i == 123) {
				ew.px("false");
				int j = tb.getSelectedRow();
				String str1 = String.valueOf(model.getValueAt(j, 0));

				ew.getEmpComboBox().removeAllItems();
				ew.getEmpComboBox().addItem(str1);
				ew.getEmpComboBox().setEnabled(true);
				ew.getEmpComboBox().setEditable(false);

				String.valueOf(df.format(DateCalculationUtil.getDate(datetf1.getText())));
				String str3 = String.valueOf(df.format(DateCalculationUtil.getMonth(datetf1.getText())));
				String str4 = String.valueOf(df.format(DateCalculationUtil.getMonth(datetf1.getText()) + 1));
				String str5 = String.valueOf(DateCalculationUtil.getYear(datetf1.getText()));

				ew.getFromDateField().setText("01/" + str3 + "/" + str5);
				ew.getToDateField().setText("01/" + str4 + "/" + str5);
			}
		}
	}

	public void mousePressed(MouseEvent paramMouseEvent) {
	}

	public void mouseReleased(MouseEvent paramMouseEvent) {
	}

	public void mouseExited(MouseEvent paramMouseEvent) {
	}

	public void mouseEntered(MouseEvent paramMouseEvent) {
	}

	public void keyReleased(KeyEvent paramKeyEvent) {
	}

	public void keyTyped(KeyEvent paramKeyEvent) {
	}

	public class myPrint extends Thread implements Runnable {
		public myPrint() {
		}
	}
}
