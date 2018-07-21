package com.sol.erp.ui.custom;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JRadioButton;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class SolTableFinder implements java.awt.event.ActionListener {
	String searchtextstr = "";
	int foundrow = 0;
	int foundcol = 0;
	static String[] head = { "S", "M", "T", "W", "T", "F", "S" };
	static Object[][] data = { new Object[0], new Object[0], new Object[0], new Object[0], new Object[0] };

	DefaultTableModel skmodel = new DefaultTableModel(data, head);
	JTable sktb = new JTable(this.skmodel);

	JDialog f = new JDialog();
	JProgressBar progress = new JProgressBar();
	JLabel l1 = new JLabel("Search Text");
	JLabel l2 = new JLabel("Search Result :");

	JLabel replacelabel = new JLabel("Replace With");
	JTextField replacetf = new JTextField(18);
	JButton replacebut = new JButton("Replace");
	JButton replaceallbut = new JButton("Replace All");

	JTextField tf1 = new JTextField("SHEKHAR KUMAR GUPTA", 18);

	JButton b1 = new JButton("Search");
	JButton b2 = new JButton("Cancel");

	javax.swing.JCheckBox matchbox = new javax.swing.JCheckBox("Match Case");

	JRadioButton upradio = new JRadioButton("Up");
	JRadioButton downradio = new JRadioButton("Down", true);

	ButtonGroup group = new ButtonGroup();

	JPanel directionpanel = new JPanel();
	JPanel progresspanel = new JPanel();

	JPanel northpanel = new JPanel();
	JPanel centerpanel = new JPanel();
	JPanel southpanel = new JPanel();

	JPanel centernorthpanel = new JPanel();
	JPanel centersouthpanel = new JPanel();
	JPanel centereastpanel = new JPanel();

	JPanel southeastpanel = new JPanel();
	JPanel southwestpanel = new JPanel();

	JPanel mainpanel = new JPanel();

	public JPanel DesignFrame() {
		java.awt.Container localContainer = this.f.getContentPane();
		localContainer.setLayout(new BorderLayout());
		this.mainpanel.setLayout(new BorderLayout());
		this.northpanel.setLayout(new BorderLayout());

		this.southeastpanel.setLayout(new FlowLayout(2));
		this.centerpanel.setLayout(new BorderLayout());
		this.centernorthpanel.setLayout(new java.awt.GridLayout(2, 2, 2, 2));
		this.centersouthpanel.setLayout(new FlowLayout(0));

		this.southpanel.setLayout(new BorderLayout());
		this.southeastpanel.setLayout(new FlowLayout(2));
		this.southwestpanel.setLayout(new FlowLayout(0));

		this.group.add(this.upradio);
		this.group.add(this.downradio);

		this.directionpanel.add(this.upradio);
		this.directionpanel.add(this.downradio);
		this.directionpanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Direction"));
		this.directionpanel.setPreferredSize(new java.awt.Dimension(130, 20));

		this.progress.setForeground(new java.awt.Color(60, 60, 150));
		this.progress.setStringPainted(true);
		this.progress.setPreferredSize(new java.awt.Dimension(200, 20));
		this.progresspanel.add(this.progress);
		this.northpanel.add(this.progresspanel, "West");
		this.northpanel.add(this.matchbox, "East");

		this.centerpanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Text Option"));
		this.centernorthpanel.add(this.l1);
		this.centernorthpanel.add(this.tf1);
		this.centernorthpanel.add(this.replacelabel);
		this.centernorthpanel.add(this.replacetf);

		this.tf1.setFont(new java.awt.Font("Tahoma", 1, 10));
		this.replacetf.setFont(new java.awt.Font("Tahoma", 1, 10));

		this.centersouthpanel.add(this.b1);
		this.centersouthpanel.add(this.b2);

		this.centerpanel.add(this.centernorthpanel, "Center");
		this.centerpanel.add(this.centersouthpanel, "South");
		this.centerpanel.add(this.directionpanel, "East");

		this.southeastpanel.add(this.l2);
		this.southwestpanel.add(this.replacebut);
		this.southwestpanel.add(this.replaceallbut);

		this.southpanel.add(this.southeastpanel, "West");
		this.southpanel.add(this.southwestpanel, "East");
		this.southpanel.setBorder(javax.swing.BorderFactory.createBevelBorder(1));

		this.mainpanel.add(this.northpanel, "North");
		this.mainpanel.add(this.centerpanel, "Center");
		this.mainpanel.add(this.southpanel, "South");

		this.b1.addActionListener(this);
		this.b2.addActionListener(this);

		localContainer.add(this.mainpanel, "Center");
		this.f.setTitle("skTBFinder1.0");
		this.f.getRootPane().setDefaultButton(this.b1);
		this.tf1.requestFocus();
		return this.mainpanel;
	}

	public void showFinder() {
		DesignFrame();
		this.f.setSize(520, 220);
		this.f.setLocationRelativeTo(null);
		this.f.setVisible(true);
	}

	public void setAutoSelectedText(JTable paramJTable, DefaultTableModel paramDefaultTableModel) {
		this.tf1.setText(String.valueOf(
				paramDefaultTableModel.getValueAt(paramJTable.getSelectedRow(), paramJTable.getSelectedColumn())));
	}

	public void totalResult(JTable paramJTable, DefaultTableModel paramDefaultTableModel) {
		this.sktb = paramJTable;
		this.skmodel = paramDefaultTableModel;

		int i = paramJTable.getRowCount();
		int j = paramJTable.getColumnCount();
		int k = 0;

		for (int m = 0; m < i; m++) {
			for (int n = 0; n < j; n++) {
				String str = String.valueOf(this.skmodel.getValueAt(m, n));
				if (this.tf1.getText().toString().equalsIgnoreCase(str)) {
					k += 1;
					this.l2.setText("Total Result : " + String.valueOf(k));
				}
			}
		}

		paramJTable = this.sktb;
		paramDefaultTableModel = this.skmodel;

		this.progress.setMinimum(0);
		this.progress.setMaximum(k);
	}

	int currentposition = 0;

	public void SearchInThisTable(JTable paramJTable, DefaultTableModel paramDefaultTableModel) {
		this.sktb = paramJTable;
		this.skmodel = paramDefaultTableModel;

		int i = paramJTable.getRowCount();
		int j = paramJTable.getColumnCount();

		this.sktb.setRowSelectionAllowed(true);
		this.sktb.setColumnSelectionAllowed(true);
		this.sktb.setCellSelectionEnabled(true);

		int k = 0;
		for (int m = this.sktb.getSelectedRow() + 1; m < i; m++) {
			for (int n = 0; n < j; n++) {
				String str = String.valueOf(this.skmodel.getValueAt(m, n));
				if (this.tf1.getText().toString().equalsIgnoreCase(str)) {
					this.currentposition += 1;
					this.sktb.setSelectionBackground(java.awt.Color.orange);
					this.sktb.setSelectionForeground(java.awt.Color.blue);
					this.sktb.setRowSelectionInterval(m, m);
					this.sktb.setColumnSelectionInterval(n, n);
					this.progress.setValue(this.currentposition);
					this.progress.setStringPainted(true);

					paramJTable.scrollRectToVisible(paramJTable.getCellRect(m, m, true));

					k = -1;
					break;
				}
			}
			if (k < 0) {
				break;
			}
		}

		paramJTable = this.sktb;
		paramDefaultTableModel = this.skmodel;
	}

	public void actionPerformed(java.awt.event.ActionEvent paramActionEvent) {
		if (paramActionEvent.getSource() == this.b1) {
			SearchInThisTable(this.sktb, this.skmodel);
			totalResult(this.sktb, this.skmodel);
		}
		if (paramActionEvent.getSource() == this.b2) {
			this.f.setVisible(false);
		}
	}

}
