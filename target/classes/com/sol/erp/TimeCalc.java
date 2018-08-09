package com.sol.erp;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.text.DecimalFormat;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.sol.erp.util.DateDifferencesUtil;
import com.sol.erp.util.formater.SolDateFormatter;

public class TimeCalc implements java.awt.event.FocusListener, java.awt.event.ActionListener {
	DateDifferencesUtil diff = new DateDifferencesUtil();


	DecimalFormat df1 = new DecimalFormat("00");

	JFrame f = new JFrame("Time Calculator");

	JLabel l1 = new JLabel("Time (hh.mm)");
	JLabel l2 = new JLabel("Value (hh.mm or hh)");
	JLabel l3 = new JLabel("Result");

	JTextField tf1 = new JTextField("12.45");
	JTextField tf2 = new JTextField("09.45");
	JTextField tf3 = new JTextField("00.00");

	JButton b1 = new JButton("+");
	JButton b2 = new JButton("-");
	JButton b3 = new JButton("x");
	JButton b4 = new JButton("/");
	JButton b5 = new JButton("HTM");
	JButton b6 = new JButton("MTH");

	JPanel p1 = new JPanel();
	JPanel p2 = new JPanel();
	JPanel mainpanel = new JPanel();

	java.awt.Font fo = new java.awt.Font("Verdana", 1, 15);
	java.awt.Font fo1 = new java.awt.Font("Verdana", 1, 10);

	javax.swing.border.Border line = javax.swing.BorderFactory.createLineBorder(Color.red);
	javax.swing.border.Border bor = javax.swing.BorderFactory.createTitledBorder(line, "Time Calculator", 0, 0,
			fo1, Color.darkGray);
	javax.swing.border.Border bor1 = javax.swing.BorderFactory.createTitledBorder(line, "Functions", 0, 0,
			fo1, Color.darkGray);

	public void DesignFrame() {
		java.awt.Container localContainer = f.getContentPane();
		localContainer.setLayout(new java.awt.BorderLayout());
		mainpanel.setLayout(new java.awt.BorderLayout());

		p1.setLayout(new java.awt.GridLayout(3, 2, 3, 3));
		p2.setLayout(new java.awt.GridLayout(2, 3, 3, 3));

		p1.setBorder(bor);
		p2.setBorder(bor1);

		p1.add(l1);
		p1.add(tf1);
		p1.add(l2);
		p1.add(tf2);
		p1.add(l3);
		p1.add(tf3);
		tf3.setEditable(false);

		p2.add(b1);
		p2.add(b2);
		p2.add(b3);
		p2.add(b4);
		p2.add(b5);
		p2.add(b6);

		l1.setHorizontalAlignment(4);
		l2.setHorizontalAlignment(4);
		l3.setHorizontalAlignment(4);

		tf1.setHorizontalAlignment(4);
		tf2.setHorizontalAlignment(4);
		tf3.setHorizontalAlignment(4);

		l1.setFont(fo1);
		l2.setFont(fo1);
		l3.setFont(fo1);

		tf1.setFont(fo);
		tf2.setFont(fo);
		tf3.setFont(fo);

		mainpanel.add(p1, "Center");
		mainpanel.add(p2, "South");

		tf1.addFocusListener(this);
		tf2.addFocusListener(this);
		tf3.addFocusListener(this);

		b1.addActionListener(this);
		b2.addActionListener(this);
		b3.addActionListener(this);
		b4.addActionListener(this);
		b5.addActionListener(this);
		b6.addActionListener(this);

		localContainer.add(mainpanel, "Center");
	}

	public void showFrame() {
		DesignFrame();

		f.setSize(300, 200);
		f.setLocationRelativeTo(null);
		f.setResizable(false);
		f.setVisible(true);
	}

	public void getTimeAdd() {
		int i = SolDateFormatter.convertHrsToMinute(tf1.getText());
		int j = SolDateFormatter.convertHrsToMinute(tf2.getText());
		int k = i + j;

		if ((i >= 0) || (j >= 0)) {
			tf3.setText(SolDateFormatter.convertMinuteToHRS(k));
		} else {
			javax.swing.JOptionPane.showMessageDialog(f, "Wrong Input");
		}
	}

	public void getTimeMinus() {
		float f1 = SolDateFormatter.convertHrsToMinute2(tf1.getText());
		float f2 = SolDateFormatter.convertHrsToMinute2(tf2.getText());

		if ((f1 >= 0.0F) || (f2 >= 0.0F)) {
			tf3.setText(DateDifferencesUtil.getTimeDiff(tf2.getText(), tf1.getText()));
		} else {
			javax.swing.JOptionPane.showMessageDialog(f, "Wrong Input");
		}
	}

	public void getTimeMultiplied() {
		float f1 = SolDateFormatter.convertHrsToMinute2(tf1.getText());
		float f2 = SolDateFormatter.convertHrsToMinute2(tf2.getText());

		if ((f1 >= 0.0F) || (f2 >= 0.0F)) {
			tf3.setText(SolDateFormatter.getTimeMultipliedBy(tf1.getText(),
					Integer.parseInt(String.valueOf(df1.format(Float.parseFloat(tf2.getText()))))));
			tf2.setText(String.valueOf(df1.format(Float.parseFloat(tf2.getText()))));
		} else {
			javax.swing.JOptionPane.showMessageDialog(f, "Wrong Input");
		}
	}

	public void getTimeDivided() {
		float f1 = SolDateFormatter.convertHrsToMinute2(tf1.getText());
		float f2 = SolDateFormatter.convertHrsToMinute2(tf2.getText());

		if ((f1 >= 0.0F) || (f2 >= 0.0F)) {
			tf3.setText(SolDateFormatter.getTimeDividedBy(tf1.getText(),
					Integer.parseInt(String.valueOf(df1.format(Float.parseFloat(tf2.getText()))))));
			tf2.setText(String.valueOf(df1.format(Float.parseFloat(tf2.getText()))));
		} else {
			javax.swing.JOptionPane.showMessageDialog(f, "Wrong Input");
		}
	}

	public void getMTH() {
		float f1 = SolDateFormatter.convertHrsToMinute2(tf2.getText());

		if (f1 >= 0.0F) {
			tf1.setText("00.00");
			tf3.setText(SolDateFormatter.convertMinuteToHRS(
					Integer.parseInt(String.valueOf(df1.format(Float.parseFloat(tf2.getText()))))));
		} else {
			javax.swing.JOptionPane.showMessageDialog(f, "Wrong Input");
		}
	}

	public void getHTM() {
		float f1 = SolDateFormatter.convertHrsToMinute2(tf2.getText());

		if (f1 >= 0.0F) {
			tf1.setText("00.00");
			tf3.setText(String.valueOf(df1.format(SolDateFormatter.convertHrsToMinute2(tf2.getText()))));
		} else {
			javax.swing.JOptionPane.showMessageDialog(f, "Wrong Input");
		}
	}

	public void actionPerformed(ActionEvent paramActionEvent) {
		if (paramActionEvent.getSource() == b1) {
			getTimeAdd();
		}
		if (paramActionEvent.getSource() == b2) {
			getTimeMinus();
		}
		if (paramActionEvent.getSource() == b3) {
			getTimeMultiplied();
		}
		if (paramActionEvent.getSource() == b4) {
			getTimeDivided();
		}
		if (paramActionEvent.getSource() == b5) {
			getHTM();
		}
		if (paramActionEvent.getSource() == b6) {
			getMTH();
		}
	}

	public void focusGained(FocusEvent paramFocusEvent) {
		if (paramFocusEvent.getSource() == tf1) {
			tf1.setBackground(new Color(60, 60, 150));
			tf1.setForeground(Color.white);
			tf1.setCaretColor(Color.white);
		}
		if (paramFocusEvent.getSource() == tf2) {
			tf2.setBackground(new Color(60, 60, 150));
			tf2.setForeground(Color.white);
			tf2.setCaretColor(Color.white);
		}
		if (paramFocusEvent.getSource() == tf3) {
			tf3.setBackground(Color.white);
			tf3.setForeground(Color.red);
			tf3.setCaretColor(Color.white);
		}
	}

	public void focusLost(FocusEvent paramFocusEvent) {
		if (paramFocusEvent.getSource() == tf1) {
			tf1.setForeground(new Color(60, 60, 150));
			tf1.setBackground(Color.white);
			tf1.setCaretColor(new Color(60, 60, 150));
		}
		if (paramFocusEvent.getSource() == tf2) {
			tf2.setForeground(new Color(60, 60, 150));
			tf2.setBackground(Color.white);
			tf2.setCaretColor(new Color(60, 60, 150));
		}
		if (paramFocusEvent.getSource() == tf3) {
			tf3.setForeground(Color.white);
			tf3.setForeground(Color.red);
			tf3.setCaretColor(new Color(60, 60, 150));
		}
	}

}
