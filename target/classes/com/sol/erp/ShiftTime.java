package com.sol.erp;

import java.awt.Choice;
import java.awt.Container;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;

import com.sol.erp.util.DBConnectionUtil;
import com.sol.erp.util.DateDifferencesUtil;
import com.sol.erp.util.SessionUtil;

public class ShiftTime implements java.awt.event.ActionListener, javax.swing.event.ChangeListener {
	

	java.sql.Connection con;

	java.sql.PreparedStatement stat;
	java.sql.ResultSet rs;
	int affected = 0;

	JInternalFrame f = new JInternalFrame("Design Shift", true, true, true, true);
	JLabel label = new JLabel("Shift");
	JLabel l1 = new JLabel("In Time");
	JLabel l2 = new JLabel("Out Time");
	JLabel l3 = new JLabel("Total Hrs");
	JLabel l4 = new JLabel("Late Count");

	Choice ch = new Choice();
	JSlider slhr1 = new JSlider(0, 23);
	JSlider slmin1 = new JSlider(0, 59);
	JSlider slhr2 = new JSlider(0, 23);
	JSlider slmin2 = new JSlider(0, 59);
	JSlider sl3 = new JSlider(0, 59);

	JTextField tf1 = new JTextField(5);
	JTextField tf2 = new JTextField(5);
	JTextField tf3 = new JTextField(5);
	JTextField tf4 = new JTextField(5);

	JPanel northp1 = new JPanel();
	JPanel centerp1 = new JPanel();
	JPanel southp1 = new JPanel();

	JButton b1 = new JButton("Save");
	JButton b2 = new JButton("Close");

	JPanel p1 = new JPanel();
	JPanel p2 = new JPanel();
	JPanel p3 = new JPanel();
	JPanel p4 = new JPanel();

	java.awt.Font fo = new java.awt.Font("Tahoma", 1, 11);

	public void px() {
		Container localContainer = f.getContentPane();
		localContainer.setLayout(new java.awt.BorderLayout());
		northp1.setLayout(new FlowLayout(0));
		centerp1.setLayout(new java.awt.GridLayout(4, 1));
		southp1.setLayout(new FlowLayout(2));

		p1.setLayout(new FlowLayout(2));
		p2.setLayout(new FlowLayout(2));
		p3.setLayout(new FlowLayout(2));
		p4.setLayout(new FlowLayout(2));

		ch.addItem("A");
		ch.addItem("B");
		ch.addItem("C");
		ch.addItem("General");

		slhr1.setMajorTickSpacing(3);
		slhr1.setMinorTickSpacing(1);
		slhr1.setPaintTicks(true);
		slhr1.setPaintLabels(true);
		slmin1.setMajorTickSpacing(10);
		slmin1.setMinorTickSpacing(5);
		slmin1.setPaintTicks(true);
		slmin1.setPaintLabels(true);

		slhr2.setMajorTickSpacing(3);
		slhr2.setMinorTickSpacing(1);
		slhr2.setPaintTicks(true);
		slhr2.setPaintLabels(true);
		slmin2.setMajorTickSpacing(10);
		slmin2.setMinorTickSpacing(5);
		slmin2.setPaintTicks(true);
		slmin2.setPaintLabels(true);

		sl3.setMajorTickSpacing(10);
		sl3.setMinorTickSpacing(5);
		sl3.setPaintTicks(true);
		sl3.setPaintLabels(true);

		northp1.add(label);
		northp1.add(ch);

		slhr1.setPreferredSize(new java.awt.Dimension(300, 70));
		slmin1.setPreferredSize(new java.awt.Dimension(300, 70));
		slhr2.setPreferredSize(new java.awt.Dimension(300, 70));
		slmin2.setPreferredSize(new java.awt.Dimension(300, 70));

		sl3.setPreferredSize(new java.awt.Dimension(300, 70));

		p1.add(l1);
		p1.add(slhr1);
		p1.add(slmin1);
		p1.add(tf1);
		p2.add(l2);
		p2.add(slhr2);
		p2.add(slmin2);
		p2.add(tf2);
		p3.add(l3);
		p3.add(new JPanel());
		p3.add(tf3);
		p4.add(l4);
		p4.add(sl3);
		p4.add(tf4);

		tf1.setEditable(false);
		tf1.setFont(fo);
		tf2.setEditable(false);
		tf2.setFont(fo);
		tf3.setEditable(false);
		tf3.setFont(fo);
		tf4.setEditable(false);
		tf4.setFont(fo);

		centerp1.add(p1);
		centerp1.add(p2);
		centerp1.add(p3);
		centerp1.add(p4);

		centerp1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

		southp1.add(b1);
		southp1.add(b2);

		localContainer.add(northp1, "North");
		localContainer.add(centerp1, "Center");
		localContainer.add(southp1, "South");

		slhr1.addChangeListener(this);
		slmin1.addChangeListener(this);

		slhr2.addChangeListener(this);
		slmin2.addChangeListener(this);

		sl3.addChangeListener(this);

		b1.addActionListener(this);
		b2.addActionListener(this);

		f.setLocation(150, 150);
		f.pack();
		f.setVisible(true);
		calc();
	}

	public void calc() {
		tf1.setText(String.valueOf(slhr1.getValue()) + "." + String.valueOf(slmin1.getValue()));
		tf2.setText(String.valueOf(slhr2.getValue()) + "." + String.valueOf(slmin2.getValue()));

		String str1 = tf1.getText();
		String str2 = tf2.getText();

		tf3.setText(DateDifferencesUtil.getTimeDiff(str1, str2));
	}

	public void saveTime() {
		String str1 = SessionUtil.getLoginInfo().getCompanyId();
		String str2 = SessionUtil.getLoginInfo().getBranchCode();

		try {
			con = DBConnectionUtil.getConnection();
			stat = con.prepareStatement("Insert into HRShift values('" + str1 + "','" + str2 + "','"
					+ ch.getSelectedItem() + "','" + tf1.getText() + "','" + tf2.getText() + "','"
					+ tf3.getText() + "','" + tf4.getText() + "') ");
			affected = stat.executeUpdate();
			if (affected > 0) {
				javax.swing.JOptionPane.showMessageDialog(f, "Shift Timing Updated.");
			}
		} catch (Exception localException) {
			if (localException.getMessage().toString().equalsIgnoreCase("General Error")) {
				javax.swing.JOptionPane.showMessageDialog(f, "Click on UpdateButton to Update the Time.");
			} else {
				javax.swing.JOptionPane.showMessageDialog(f, localException.getMessage().toString());
			}
		}
	}

	public void stateChanged(ChangeEvent paramChangeEvent) {
		if ((paramChangeEvent.getSource() == slhr1) || (paramChangeEvent.getSource() == slmin1)
				|| (paramChangeEvent.getSource() == slhr2) || (paramChangeEvent.getSource() == slmin2)) {
			calc();
		}
		if (paramChangeEvent.getSource() == sl3) {
			tf4.setText(String.valueOf(sl3.getValue()));
		}
	}

	public void actionPerformed(java.awt.event.ActionEvent paramActionEvent) {
		if (paramActionEvent.getSource() == b1) {
			saveTime();
		}
		if (paramActionEvent.getSource() == b2) {
			f.setVisible(false);
		}
	}
}