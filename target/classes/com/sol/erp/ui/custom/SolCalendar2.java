package com.sol.erp.ui.custom;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.text.DecimalFormat;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.sol.erp.util.DateCalculationUtil;

public class SolCalendar2 implements java.awt.event.ActionListener, java.awt.event.MouseListener, java.awt.event.KeyListener {

    
    int colorrow;
	int colorcol;
	String colorstr;
	int colorrow1;
	int colorcol1;
	String colorstr1;
	JDialog f;
	public static String myDate = null;

	static String[] calhead = { "S", "M", "T", "W", "T", "F", "S" };
	static Object[][] caldata = { new Object[0], new Object[0], new Object[0], new Object[0], new Object[0] };



	DecimalFormat df1;

	DecimalFormat df2;

	DefaultTableModel calmodel;

	JTable caltb;

	JScrollPane calsp;

	JSpinner spin1;

	JComboBox cb;

	JTextField datetf;

	JButton okbut;

	JPanel mainPanel;

	JPanel calnorthpanel;

	JPanel calsouthpanel;

	JPanel calpanel;


	java.awt.Toolkit tk;

	Dimension ss;

	Dimension fs;

	Object min;

	Object max;

	java.util.Date dat;

	java.text.SimpleDateFormat formatter;

	String dateString;

	Renderer rnd;

	java.util.Calendar gcal;

	int startDay;

	int cbmonth;

	ArrayList arl;

	ArrayList arl2;

	class Renderer extends javax.swing.table.DefaultTableCellRenderer {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		Renderer() {
		}

		public java.awt.Component getTableCellRendererComponent(JTable paramJTable, Object paramObject,
				boolean paramBoolean1, boolean paramBoolean2, int paramInt1, int paramInt2) {
			if (paramInt2 >= 0) {
				setHorizontalAlignment(0);
				isEnabled();
			}

			String str = String.valueOf(calmodel.getValueAt(paramInt1, paramInt2));
			if (str.length() > 4) {
				setBackground(Color.black);

			} else if (paramInt1 % 2 == 0) {
				if (paramInt2 == 0) {
					setBackground(new Color(240, 210, 210));
					setForeground(Color.red);
				} else {
					setBackground(new Color(240, 240, 240));
					setForeground(Color.darkGray);
				}

			} else if (paramInt2 == 0) {
				setBackground(new Color(240, 210, 210));
				setForeground(Color.red);
			} else {
				setBackground(new Color(230, 230, 230));
				setForeground(Color.darkGray);
			}

			super.getTableCellRendererComponent(paramJTable, paramObject, paramBoolean1, paramBoolean2, paramInt1,
					paramInt2);

			return this;
		}
	}

	public JPanel DesignFrame() {
		Container localContainer = f.getContentPane();
		localContainer.setLayout(new BorderLayout());
		mainPanel.setLayout(new BorderLayout());
		calpanel.setLayout(new BorderLayout());
		calnorthpanel.setLayout(new BorderLayout());
		calsouthpanel.setLayout(new BorderLayout());

		cb.addItem("January");
		cb.addItem("February");
		cb.addItem("March");
		cb.addItem("April");
		cb.addItem("May");
		cb.addItem("June");
		cb.addItem("July");
		cb.addItem("August");
		cb.addItem("September");
		cb.addItem("October");
		cb.addItem("November");
		cb.addItem("December");

		int i = DateCalculationUtil.getYear(dateString);
		spin1.setValue(new Integer(i));

		spin1.setEnabled(true);
		spin1.setPreferredSize(new Dimension(100, 25));

		SolTableModel.decorateTable(caltb);

		caltb.setRowSelectionAllowed(true);
		caltb.setColumnSelectionAllowed(true);
		caltb.setCellSelectionEnabled(true);

		for (int j = 0; j < caltb.getColumnCount(); j++) {
			caltb.getColumnModel().getColumn(j).setCellRenderer(rnd);
		}

		datetf.setEditable(false);
		datetf.setFont(new java.awt.Font("Tahoma", 1, 11));

		calnorthpanel.setPreferredSize(new Dimension(100, 25));

		calnorthpanel.add(spin1, "West");
		calnorthpanel.add(cb, "East");

		calsouthpanel.add(okbut, "East");

		calsp.setPreferredSize(new Dimension(200, 200));
		calsp.setBorder(javax.swing.BorderFactory.createTitledBorder("Multi Selection"));

		caltb.setRowSelectionAllowed(true);
		caltb.setColumnSelectionAllowed(false);
		caltb.setCellSelectionEnabled(false);

		caltb.setShowVerticalLines(false);
		caltb.setIntercellSpacing(new Dimension(1, 1));
		caltb.setShowGrid(true);
		caltb.setRowHeight(24);

		calpanel.add(calnorthpanel, "North");
		calpanel.add(calsp, "Center");

		mainPanel.add(calpanel, "Center");
		localContainer.add(mainPanel, "Center");

		// j = dm.getMonth(dateString);
		// cb.setSelectedIndex(j - 1);

		cb.addActionListener(this);
		okbut.addActionListener(this);
		caltb.addMouseListener(this);

		localContainer.addKeyListener(this);
		caltb.addKeyListener(this);

		return mainPanel;
	}

	public void showCalendar2() {
		DesignFrame();
		f.setTitle("skCalendar1.1");
		f.setSize(210, 225);
		f.setLocation((ss.width - fs.width) / 8, (ss.height - fs.height) / 8);
		f.setVisible(true);
	}

	public void getStartDay(int paramInt) {
		for (int i = 1980;

		i <= paramInt; i++) {

			if (i > 2050) {
				javax.swing.JOptionPane.showMessageDialog(f, "Year is too long.");
				break;
			}

			int j = i % 4;

			if (j - 1 == 0) {
				startDay += 2;

				if (startDay > 7) {
					startDay = 1;
				}

			} else {
				startDay += 1;

				if (startDay > 7) {
					startDay = 1;
				}
			}

			if ((i == 2001) || (i == 2029)) {
				startDay = 2;
			}
		}

		calmodel.setNumRows(0);
		calmodel.addRow(new Object[0]);
		calmodel.addRow(new Object[0]);
		calmodel.addRow(new Object[0]);
		calmodel.addRow(new Object[0]);
		calmodel.addRow(new Object[0]);
		calmodel.setValueAt("1", 0, startDay - 1);
	}

	public void runDates() {
		getStartDay(Integer.parseInt(String.valueOf(spin1.getValue())));
		int i = cb.getSelectedIndex() + 1;
		int j = 0;
		int k = 0;
		int m = startDay - 1;

		gcal = new java.util.GregorianCalendar(Integer.parseInt(String.valueOf(spin1.getValue())),
				Integer.parseInt(String.valueOf(cb.getSelectedIndex())), 1);

		gcal.get(6);
		int i1 = 0;

		for (int i2 = 1; i2 <= i; i2++) {
			int i3 = i2;

			if ((i3 == 4) || (i3 == 6) || (i3 == 9) || (i3 == 11)) {
				j = 30;
			}

			if ((i3 == 1) || (i3 == 3) || (i3 == 5) || (i3 == 7) || (i3 == 8) || (i3 == 10) || (i3 == 12)) {
				j = 31;
			}

			int i4 = Integer.parseInt(String.valueOf(spin1.getValue()));
			int i5 = i4 % 4;

			if (i3 == 2) {
				if (i5 == 0) {
					j = 29;
				} else {
					j = 28;
				}
			}

			calmodel.setNumRows(0);
			calmodel.addRow(new Object[0]);
			calmodel.addRow(new Object[0]);
			calmodel.addRow(new Object[0]);
			calmodel.addRow(new Object[0]);
			calmodel.addRow(new Object[0]);

			for (int i6 = 0; i6 <= j; i6++) {
				i1 += 1;

				if (m == 7) {
					k += 1;
					m = 0;
				}

				if (k > 4) {
					k = 0;
				}

				if (i6 == j) {
					k = 0;
					i1 = 0;
					break;
				}

				calmodel.setValueAt(String.valueOf(i1), k, m);
				m += 1;
			}
		}
	}

	public void runDateParam(int paramInt1, int paramInt2) {
		getStartDay(paramInt1);
		cbmonth = paramInt2;
		int i = 0;
		int j = 0;
		int k = startDay - 1;

		gcal = new java.util.GregorianCalendar(Integer.parseInt(String.valueOf(spin1.getValue())),
				cbmonth, 1);

		gcal.get(6);
		int n = 0;

		for (int i1 = 1; i1 <= cbmonth; i1++) {
			int i2 = i1;

			if ((i2 == 4) || (i2 == 6) || (i2 == 9) || (i2 == 11)) {
				i = 30;
			}

			if ((i2 == 1) || (i2 == 3) || (i2 == 5) || (i2 == 7) || (i2 == 8) || (i2 == 10) || (i2 == 12)) {
				i = 31;
			}

			int i3 = paramInt1 % 4;

			if (i2 == 2) {
				if (i3 == 0) {
					i = 29;
				} else {
					i = 28;
				}
			}

			calmodel.setNumRows(0);
			calmodel.addRow(new Object[0]);
			calmodel.addRow(new Object[0]);
			calmodel.addRow(new Object[0]);
			calmodel.addRow(new Object[0]);
			calmodel.addRow(new Object[0]);

			for (int i4 = 0; i4 <= i; i4++) {
				n += 1;

				if (k == 7) {
					j += 1;
					k = 0;
				}

				if (j > 4) {
					j = 0;
				}

				if (i4 == i) {
					j = 0;
					n = 0;
					break;
				}

				calmodel.setValueAt(String.valueOf(n), j, k);
				k += 1;
			}
		}
	}

	public ArrayList getDates(String paramString) {
		arl.clear();
		for (int i = 0; i < caltb.getRowCount(); i++) {
			for (int j = 0; j < caltb.getColumnCount(); j++) {
				String str1 = String.valueOf(calmodel.getValueAt(i, j));
				if (str1.length() > 4) {
					String str2 = String.valueOf(calmodel.getValueAt(i, j));
					String str3 = str2.substring(str2.lastIndexOf(">") + 1, str2.length());
					arl.add(String.valueOf(df1.format(Integer.parseInt(str3))) + "/"
							+ String.valueOf(df1.format(getMonth() + 1)) + "/" + getYear());
				}
			}
		}
		return arl;
	}

	public SolCalendar2() {
		colorstr = "blue";

		colorstr1 = "white";

		f = new JDialog();

		df1 = new DecimalFormat("00");
		df2 = new DecimalFormat("0,0");

		calmodel = new DefaultTableModel(caldata, calhead);
		caltb = new JTable(calmodel) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public Class<? extends Object> getColumnClass(int paramAnonymousInt) {
				return getValueAt(0, paramAnonymousInt).getClass();
			}

			public boolean isCellEditable(int paramAnonymousInt1, int paramAnonymousInt2) {
				int i = convertColumnIndexToModel(paramAnonymousInt2);
				return i < 0;
			}
		};
		calsp = new JScrollPane(caltb);

		spin1 = new JSpinner();
		cb = new JComboBox();

		datetf = new JTextField("Date", 8);
		okbut = new JButton("OK");

		mainPanel = new JPanel();

		calnorthpanel = new JPanel();
		calsouthpanel = new JPanel();
		calpanel = new JPanel();


		tk = java.awt.Toolkit.getDefaultToolkit();
		ss = tk.getScreenSize();
		fs = f.getSize();

		min = "1980";
		max = "2050";

		dat = new java.util.Date();
		formatter = new java.text.SimpleDateFormat("dd/MM/yyyy");

		try {
			dateString = formatter.format(dat);
		} catch (IllegalArgumentException localIllegalArgumentException) {
			System.out.println(localIllegalArgumentException.getMessage());
		}

		rnd = new Renderer();

		startDay = 2;

		cbmonth = 1;

		arl = new ArrayList();

		arl2 = new ArrayList();
	}

	public ArrayList<?> getDates2(String paramString, int paramInt) {
		arl2.clear();
		for (int i = 0; i < caltb.getRowCount(); i++) {
			for (int j = 0; j < caltb.getColumnCount(); j++) {
				String str1 = String.valueOf(calmodel.getValueAt(i, j));
				if (str1.length() > 4) {
					String str2 = String.valueOf(calmodel.getValueAt(i, j));
					String str3 = str2.substring(str2.lastIndexOf(">") + 1, str2.length());
					arl2.add(String.valueOf(df1.format(Integer.parseInt(str3))) + "/"
							+ String.valueOf(df1.format(paramInt)) + "/" + getYear());
				}
			}
		}
		return arl2;
	}

	public static ArrayList arl3 = new ArrayList();

	public ArrayList addDate() {
		arl3.clear();
		arl3.add("12/01/2008_RED");
		arl3.add("15/01/2008_GREEN");
		arl3.add("18/01/2008_BLUE");

		return arl3;
	}

	public void setDates(ArrayList<?> paramArrayList) {
		String str1 = "gray";

		for (int i = 0; i < paramArrayList.size(); i++) {
			String str2 = String.valueOf(paramArrayList.get(i));

			String str3 = String.valueOf(DateCalculationUtil.getYear(str2.substring(0, str2.indexOf('_'))));
			String str4 = getYear();

			str1 = String.valueOf(str2.substring(str2.indexOf('_') + 1, str2.length()));

			System.out.println(str4 + "\t" + str1);

			int j = DateCalculationUtil.getMonth(str2);
			int k = getMonth() + 1;

			int m = DateCalculationUtil.getDate(str2);

			if ((Integer.parseInt(str3) == Integer.parseInt(str4)) && (k == j)) {
				for (int n = 0; n < caltb.getRowCount(); n++) {
					for (int i1 = 0; i1 < caltb.getColumnCount(); i1++) {
						String str5 = String.valueOf(calmodel.getValueAt(n, i1));
						if (str5.length() <= 2) {
							int i2 = Integer.parseInt(str5);

							if (m == i2) {
								calmodel.setValueAt("<html><Font Color=" + str1 + ">" + String.valueOf(i2), n, i1);
							}
						}
					}
				}
			}
		}
	}

	public String getYear() {
		return String.valueOf(spin1.getValue());
	}

	public int getMonth() {
		return cb.getSelectedIndex();
	}

	public String getMonthName() {
		return String.valueOf(cb.getSelectedItem());
	}

	public void removeOKPanel() {
		calpanel.remove(calsouthpanel);
		f.pack();
	}

	public void removeTOPPanel() {
		calpanel.remove(calnorthpanel);
		f.pack();
	}

	public void setSize(int paramInt1, int paramInt2) {
		f.setSize(paramInt1, paramInt2);
	}

	public void setBorderTitle(String paramString) {
		calsp.setBorder(javax.swing.BorderFactory.createTitledBorder(paramString));
	}

	public JComboBox MonthList() {
		return cb;
	}

	public JButton OKButton() {
		return okbut;
	}

	public JPanel OKPanel() {
		return calsouthpanel;
	}

	public JSpinner YearList() {
		return spin1;
	}

	public DefaultTableModel CalTableModel() {
		return calmodel;
	}

	public JTable CalTable() {
		return caltb;
	}

	public void actionPerformed(java.awt.event.ActionEvent paramActionEvent) {
		if (paramActionEvent.getSource() == cb) {
			runDates();
		}
		if (paramActionEvent.getSource() == okbut) {
			getDates(String.valueOf(spin1.getValue()));
			System.out.println(arl);
			for (int i = 0; i < arl.size(); i++) {
				System.out.println(arl.get(i));
			}
		}
	}

	public void mousePressed(MouseEvent paramMouseEvent) {
		if (paramMouseEvent.getSource() == caltb) {
			int i = caltb.rowAtPoint(paramMouseEvent.getPoint());
			int j = caltb.columnAtPoint(paramMouseEvent.getPoint());
			String str = String.valueOf(calmodel.getValueAt(i, j));
			if ((str == null) || (str.equalsIgnoreCase("null"))) {
				str = "";
			}
			calmodel.setValueAt("<html><Font Color=white>" + str, i, j);
		}
	}

	public void mouseReleased(MouseEvent paramMouseEvent) {
		if (paramMouseEvent.getSource() == caltb) {
			if (javax.swing.SwingUtilities.isRightMouseButton(paramMouseEvent)) {
				int i = caltb.rowAtPoint(paramMouseEvent.getPoint());
				int j = caltb.columnAtPoint(paramMouseEvent.getPoint());

				String str1 = String.valueOf(calmodel.getValueAt(i, j));
				String str2 = str1.substring(str1.lastIndexOf(">") + 1, str1.length());

				if ((str2 == null) || (str2.equalsIgnoreCase("null"))) {
					str2 = "";
				}
				calmodel.setValueAt(str2, i, j);
			}
		}
	}

	public void keyPressed(KeyEvent paramKeyEvent) {
		int i = paramKeyEvent.getKeyCode();
		if (i == 27) {
			f.setVisible(false);
		}
	}

	public void mouseClicked(MouseEvent paramMouseEvent) {
	}

	public void mouseEntered(MouseEvent paramMouseEvent) {
	}

	public void mouseExited(MouseEvent paramMouseEvent) {
	}

	public void keyReleased(KeyEvent paramKeyEvent) {
	}

	public void keyTyped(KeyEvent paramKeyEvent) {
	}
}
