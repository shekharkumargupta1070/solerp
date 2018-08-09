package com.sol.erp.ui.custom;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.text.DecimalFormat;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.sol.erp.util.DateCalculationUtil;

public class SolCalendar4 implements java.awt.event.ActionListener, java.awt.event.MouseListener, java.awt.event.KeyListener,
		javax.swing.event.ChangeListener {
	public static String myDate = null;

	String[] head;

	Object[][] data;

	DecimalFormat df1;

	DecimalFormat df2;

	DefaultTableModel model;

	JTable tb;

	javax.swing.JScrollPane sp;

	JSpinner spin1;

	JComboBox cb;

	JTextField tf;

	public static JTextField datetf = new JTextField("dd/MM/yyyy");

	JButton okbut;

	JTextField datetf1;

	JButton okbut1;

	JPanel northpanel;

	JPanel southpanel;



	Object min;

	Object max;

	java.util.Date dat;

	java.text.SimpleDateFormat formatter;

	String dateString;

	JPanel mainpanel;

	JPanel calpanel;

	JPanel calsouthpanel;

	skRenderer skc;

	public JTextField DesignFrame() {
		mainpanel.setLayout(new BorderLayout());
		southpanel.setLayout(new java.awt.FlowLayout(0, 0, 0));
		datetf.setBorder(javax.swing.BorderFactory.createLineBorder(Color.red));

		SolTableModel.decorateTable(tb);
		for (int i = 0; i < tb.getColumnCount(); i++) {
			tb.getColumnModel().getColumn(i).setCellRenderer(skc);
		}

		tb.setRowSelectionAllowed(true);
		tb.setColumnSelectionAllowed(true);
		tb.setCellSelectionEnabled(true);

		datetf.setBackground(new Color(250, 220, 240));
		datetf.setForeground(Color.blue);
		datetf.setEditable(true);
		datetf.setFont(new java.awt.Font("Tahoma", 1, 11));
		datetf.setHorizontalAlignment(4);

		sp.setBorder(javax.swing.BorderFactory.createTitledBorder("Single Date Selection"));

		tb.setShowVerticalLines(false);
		tb.setIntercellSpacing(new Dimension(1, 1));
		tb.setShowGrid(false);
		tb.setRowHeight(28);

		southpanel.add(datetf);

		mainpanel.add(southpanel, "North");

		datetf.setPreferredSize(new Dimension(90, 22));

		northpanel.setPreferredSize(new Dimension(100, 30));
		sp.setPreferredSize(new Dimension(210, 200));

		// i = dm.getMonth(dateString);

		spin1.addChangeListener(this);
		cb.addActionListener(this);
		tb.addMouseListener(this);
		datetf.addMouseListener(this);

		tb.addKeyListener(this);

		return datetf;
	}

	class skRenderer extends javax.swing.table.DefaultTableCellRenderer {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		skRenderer() {
		}

		public java.awt.Component getTableCellRendererComponent(JTable paramJTable, Object paramObject,
				boolean paramBoolean1, boolean paramBoolean2, int paramInt1, int paramInt2) {
			setEnabled((paramJTable == null) || (paramJTable.isEnabled()));

			if (paramInt1 % 2 == 0) {
				setBackground(new Color(250, 250, 250));
				setForeground(Color.darkGray);

			} else {
				setBackground(new Color(240, 240, 240));
				setForeground(Color.darkGray);
			}

			setHorizontalAlignment(0);
			if (paramInt2 == 0) {
				setBackground(new Color(110, 110, 220));
				setForeground(Color.white);
			}

			if (paramObject != null) {
				int i = DateCalculationUtil.getDate(datetf.getText());
				int j = Integer.parseInt(String.valueOf(model.getValueAt(paramInt1, paramInt2)));
				if (i == j) {
					setBackground(new Color(110, 220, 110));
					setForeground(Color.white);
				}
			}

			super.getTableCellRendererComponent(paramJTable, paramObject, paramBoolean1, paramBoolean2, paramInt1,
					paramInt2);

			return this;
		}
	}

	public static JDialog f = new JDialog();
	java.util.Calendar gcal;
	int startDay;

	public void maximizeCalendar() {
		f.setTitle("skCalendar");
		java.awt.Container localContainer = f.getContentPane();
		localContainer.setLayout(new BorderLayout());
		calpanel.setLayout(new BorderLayout());
		northpanel.setLayout(new java.awt.FlowLayout(1, 0, 0));
		calsouthpanel.setLayout(new BorderLayout());

		northpanel.setBorder(javax.swing.BorderFactory.createBevelBorder(0));

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

		northpanel.add(spin1);
		northpanel.add(cb);

		calsouthpanel.add(datetf1, "West");
		calsouthpanel.add(okbut1, "East");
		calsouthpanel.add(new JPanel());

		datetf1.setBackground(new Color(250, 220, 240));
		datetf1.setForeground(Color.blue);
		datetf1.setEditable(false);
		datetf1.setFont(new java.awt.Font("Tahoma", 1, 10));
		datetf1.setHorizontalAlignment(4);
		datetf1.setForeground(Color.red);
		okbut1.setFont(new java.awt.Font("Verdana", 1, 10));

		datetf1.setPreferredSize(new Dimension(150, 22));

		calpanel.add(northpanel, "North");
		calpanel.add(sp, "Center");
		calpanel.add(calsouthpanel, "South");
		calpanel.revalidate();
		calpanel.setBackground(Color.black);
		localContainer.add(calpanel, "Center");

		okbut1.addActionListener(this);

		f.setSize(210, 275);
		f.setLocationRelativeTo(datetf);
		f.setVisible(true);
	}

	public void minimizeCalendar() {
		f.setVisible(false);
	}

	public SolCalendar4() {
		head = new String[] { "S", "M", "T", "W", "T", "F", "S" };
		data = new Object[][] { new Object[0], new Object[0], new Object[0], new Object[0], new Object[0] };

		df1 = new DecimalFormat("00");
		df2 = new DecimalFormat("0,0");

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
		sp = new javax.swing.JScrollPane(tb);

		spin1 = new JSpinner();
		cb = new JComboBox();

		tf = new JTextField();

		okbut = new JButton("0");

		datetf1 = new JTextField("dd/MM/yyyy");
		okbut1 = new JButton("OK");

		northpanel = new JPanel();
		southpanel = new JPanel();


		min = "1980";
		max = "2050";

		dat = new java.util.Date();
		formatter = new java.text.SimpleDateFormat("dd/MM/yyyy");

		try {
			dateString = formatter.format(dat);
			datetf.setText(dateString);
			datetf1.setText(dateString);
		} catch (IllegalArgumentException localIllegalArgumentException) {
			System.out.println(localIllegalArgumentException.getMessage());
		}

		mainpanel = new JPanel();
		calpanel = new JPanel();
		calsouthpanel = new JPanel();

		skc = new skRenderer();

		startDay = 2;
	}

	public void getStartDay() {
		int i = 1980;
		int j = Integer.parseInt(String.valueOf(spin1.getValue()));
		for (;

		i <= j; i++) {

			if (i > 2050) {
				javax.swing.JOptionPane.showMessageDialog(null, "Year is too long.");
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

	public void setYear(int paramInt) {
		spin1.setValue(Integer.valueOf(paramInt));
	}

	public void setMonth(int paramInt) {
		cb.setSelectedIndex(paramInt - 1);
	}

	public String getText() {
		return datetf.getText();
	}

	public void setText(String paramString) {
		datetf.setText(String.valueOf(paramString));
	}

	public void actionPerformed(java.awt.event.ActionEvent paramActionEvent) {
		if (paramActionEvent.getSource() == cb) {
			getStartDay();
			runDates();
		}
		if (paramActionEvent.getSource() == okbut1) {
			f.setVisible(false);
		}
	}

	public void stateChanged(javax.swing.event.ChangeEvent paramChangeEvent) {
		if (paramChangeEvent.getSource() == spin1) {
			getStartDay();
			runDates();
		}
	}

	public void mousePressed(MouseEvent paramMouseEvent) {
		int i;
		int j;
		if (paramMouseEvent.getSource() == datetf) {
			maximizeCalendar();

			i = DateCalculationUtil.getYear(datetf.getText());
			j = DateCalculationUtil.getMonth(datetf.getText());

			setYear(i);
			setMonth(j);
			runDates();

			getDate(sktf);
			sktf.requestFocus();
		}
		if (paramMouseEvent.getSource() == tb) {
			i = tb.getSelectedRow();
			j = tb.getSelectedColumn();
			int k = Integer.parseInt(String.valueOf(model.getValueAt(i, j)));

			myDate = String.valueOf(df1.format(k)) + "/"
					+ String.valueOf(df1.format(cb.getSelectedIndex() + 1)) + "/"
					+ String.valueOf(spin1.getValue());
			datetf.setText(myDate);
			datetf1.setText(myDate);
		}
	}

	public void keyPressed(KeyEvent paramKeyEvent) {
		int i = paramKeyEvent.getKeyCode();
		if (i == 27) {
			minimizeCalendar();
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
