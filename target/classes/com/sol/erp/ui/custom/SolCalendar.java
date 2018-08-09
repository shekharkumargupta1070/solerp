package com.sol.erp.ui.custom;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.text.DecimalFormat;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.sol.erp.util.DateCalculationUtil;

public class SolCalendar implements java.awt.event.ActionListener, java.awt.event.MouseListener, java.awt.event.KeyListener,
		javax.swing.event.ChangeListener {
    
	JDialog f;
	public static String myDate = null;

	static String[] head = { "S", "M", "T", "W", "T", "F", "S" };
	static Object[][] data = { new Object[0], new Object[0], new Object[0], new Object[0], new Object[0] };

	DecimalFormat df1;

	DecimalFormat df2;

	public static DefaultTableModel model = new DefaultTableModel(data, head);
	public static JTable tb = new JTable(model) {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public boolean isCellEditable(int paramAnonymousInt1, int paramAnonymousInt2) {

			int i = convertColumnIndexToModel(paramAnonymousInt2);
			return i < 0;
		}
	};
	javax.swing.JScrollPane sp;
	public static JSpinner spin1 = new JSpinner();
	public static JComboBox cb = new JComboBox();

	JTextField datetf;
	static javax.swing.JButton okbut = new javax.swing.JButton("OK");

	JPanel northpanel;

	JPanel southpanel;

	//SolTableModel skt;

	SolCellModel skc;

	//DateCalculationUtil dm;

	Toolkit tk;

	Dimension ss;

	Dimension fs;

	Object min;

	Object max;

	java.util.Date dat;

	java.text.SimpleDateFormat formatter;

	String dateString;

	java.util.Calendar gcal;

	int startDay;

	public void px() {
		Container localContainer = f.getContentPane();
		localContainer.setLayout(new java.awt.BorderLayout());
		northpanel.setLayout(new java.awt.BorderLayout());
		southpanel.setLayout(new java.awt.BorderLayout());

		cb.removeAllItems();

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

		SolTableModel.decorateTable(tb);
		for (int j = 0; j < tb.getColumnCount(); j++) {
			tb.getColumnModel().getColumn(j).setCellRenderer(skc);
		}

		tb.setRowSelectionAllowed(true);
		tb.setColumnSelectionAllowed(true);
		tb.setCellSelectionEnabled(true);

		datetf.setEditable(false);
		datetf.setFont(new java.awt.Font("Tahoma", 1, 11));

		northpanel.setPreferredSize(new Dimension(100, 25));

		northpanel.add(spin1, "West");
		northpanel.add(cb, "East");

		southpanel.add(datetf, "West");
		southpanel.add(okbut, "East");

		sp.setBorder(javax.swing.BorderFactory.createTitledBorder("Single Selection"));

		tb.setShowVerticalLines(false);
		tb.setIntercellSpacing(new Dimension(1, 1));
		tb.setShowGrid(false);
		tb.setRowHeight(28);

		localContainer.add(northpanel, "North");
		localContainer.add(sp, "Center");
		localContainer.add(southpanel, "South");

		f.setTitle("skCalendar1.0");
		f.setSize(200, 270);
		f.setLocation((ss.width - fs.width) / 3, (ss.height - fs.height) / 3);
		f.setVisible(true);

		// j = dm.getMonth(dateString);

		spin1.addChangeListener(this);
		cb.addActionListener(this);
		okbut.addActionListener(this);
		tb.addMouseListener(this);

		localContainer.addKeyListener(this);
		tb.addKeyListener(this);
	}

	public SolCalendar() {
		f = new JDialog();

		df1 = new DecimalFormat("00");
		df2 = new DecimalFormat("0,0");

		sp = new javax.swing.JScrollPane(tb);

		datetf = new JTextField("Date", 8);

		northpanel = new JPanel();
		southpanel = new JPanel();
		
		skc = new SolCellModel();

		tk = Toolkit.getDefaultToolkit();
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

		startDay = 2;
	}

	public void getStartDay() {
		int i = 1980;
		int j = Integer.parseInt(String.valueOf(spin1.getValue()));
		for (;

		i <= j; i++) {

			if (i > 2050) {
				javax.swing.JOptionPane.showMessageDialog(f, "Year is too long.");
				break;
			}

			int k = i % 4;

			if (k - 1 == 0) {
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

		model.setNumRows(0);
		model.addRow(new Object[0]);
		model.addRow(new Object[0]);
		model.addRow(new Object[0]);
		model.addRow(new Object[0]);
		model.addRow(new Object[0]);
		model.setValueAt("1", 0, startDay - 1);
	}

	public void runDates() {
		int i = cb.getSelectedIndex() + 1;
		int j = 0;
		int k = 0;
		int m = startDay - 1;

		gcal = new java.util.GregorianCalendar(Integer.parseInt(String.valueOf(spin1.getValue())),
				Integer.parseInt(String.valueOf(cb.getSelectedIndex())), 1);

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

			model.setNumRows(0);
			model.addRow(new Object[0]);
			model.addRow(new Object[0]);
			model.addRow(new Object[0]);
			model.addRow(new Object[0]);
			model.addRow(new Object[0]);

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

				model.setValueAt(String.valueOf(i1), k, m);
				m += 1;
			}
		}
	}

	public void showCalendar() {
		SolCalendar localskCal = new SolCalendar();
		localskCal.px();
		localskCal.getStartDay();
		localskCal.runDates();
	}

	static JTextField sktf = new JTextField();

	public void getDate(JTextField paramJTextField) {
		sktf = paramJTextField;
		sktf.setText(myDate);
		paramJTextField = sktf;

	}

	public String getYear() {
		return String.valueOf(spin1.getValue());
	}

	public int getMonth() {
		return cb.getSelectedIndex();
	}

	public void actionPerformed(ActionEvent paramActionEvent) {
		if (paramActionEvent.getSource() == cb) {
			getStartDay();
			runDates();
		}
		if (paramActionEvent.getSource() == okbut) {
			f.setVisible(false);
			getDate(sktf);
			sktf.requestFocus();
		}
	}

	public void stateChanged(javax.swing.event.ChangeEvent paramChangeEvent) {
		if (paramChangeEvent.getSource() == spin1) {
			getStartDay();
			runDates();
		}
	}

	public void mousePressed(MouseEvent paramMouseEvent) {
		if (paramMouseEvent.getSource() == tb) {
			int i = tb.getSelectedRow();
			int j = tb.getSelectedColumn();
			int k = Integer.parseInt(String.valueOf(model.getValueAt(i, j)));

			myDate = String.valueOf(df1.format(k)) + "/"
					+ String.valueOf(df1.format(cb.getSelectedIndex() + 1)) + "/"
					+ String.valueOf(spin1.getValue());
			datetf.setText(myDate);
		}
	}

	public void keyPressed(KeyEvent paramKeyEvent) {
		int i = paramKeyEvent.getKeyCode();
		if (i == 27) {
			f.setVisible(false);
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

	public void keyReleased(KeyEvent paramKeyEvent) {
	}

	public void keyTyped(KeyEvent paramKeyEvent) {
	}
}
