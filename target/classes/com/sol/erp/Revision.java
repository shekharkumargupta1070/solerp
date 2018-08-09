package com.sol.erp;

import java.awt.Container;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Revision implements java.awt.event.ActionListener {
    
	JFrame f = new JFrame("Revision Time Entry");
	JLabel l1 = new JLabel("Date");
	JLabel l2 = new JLabel("Emp Code");
	JLabel l3 = new JLabel("Project No");
	JLabel l4 = new JLabel("Project Name");
	JLabel l5 = new JLabel("Client Name");
	JLabel l6 = new JLabel("Sheet/Drg No");
	JLabel l7 = new JLabel("Start Time");
	JLabel l8 = new JLabel("End Time");
	JLabel l9 = new JLabel("Total Time");

	JTextField tf1 = new JTextField();
	JTextField tf2 = new JTextField();
	JTextField tf3 = new JTextField();
	JTextField tf4 = new JTextField(15);
	JTextField tf5 = new JTextField();
	JTextField tf6 = new JTextField();
	JTextField tf7 = new JTextField();
	JTextField tf8 = new JTextField();
	JTextField tf9 = new JTextField();

	JButton b1 = new JButton("Save");
	JButton b2 = new JButton("Update");
	JButton b3 = new JButton("Close");

	JPanel centerpanel = new JPanel();

	JPanel p1 = new JPanel();

	JPanel butpanel = new JPanel();

	javax.swing.border.Border bor1 = javax.swing.BorderFactory.createTitledBorder("Revision Time Entry");

	public void px() {
		Container localContainer = this.f.getContentPane();
		localContainer.setLayout(new java.awt.BorderLayout());
		this.p1.setLayout(new java.awt.GridLayout(5, 2, 2, 2));

		this.p1.add(this.l1);
		this.p1.add(this.tf1);
		this.p1.add(this.l2);
		this.p1.add(this.tf2);
		this.p1.add(this.l3);
		this.p1.add(this.tf3);
		this.p1.add(this.l4);
		this.p1.add(this.tf4);
		this.p1.add(this.l5);
		this.p1.add(this.tf5);
		this.p1.add(this.l6);
		this.p1.add(this.tf6);
		this.p1.add(this.l7);
		this.p1.add(this.tf7);
		this.p1.add(this.l8);
		this.p1.add(this.tf8);
		this.p1.add(this.l9);
		this.p1.add(this.tf9);

		this.centerpanel.setBorder(this.bor1);

		this.butpanel.add(this.b1);
		this.b1.setMnemonic(83);
		this.butpanel.add(this.b2);
		this.b2.setMnemonic(85);
		this.butpanel.add(this.b3);
		this.b3.setMnemonic(67);

		this.centerpanel.add(this.p1, "Center");

		localContainer.add(this.centerpanel, "Center");
		localContainer.add(this.butpanel, "South");

		this.b1.addActionListener(this);
		this.b2.addActionListener(this);
		this.b3.addActionListener(this);

		this.f.setSize(700, 300);
		this.f.setResizable(false);
		this.f.setLocation(0, 45);
		this.f.setVisible(true);
	}

	public void actionPerformed(ActionEvent paramActionEvent) {
		if (paramActionEvent.getSource() == this.b3) {
			this.f.setVisible(false);
		}
	}
}
