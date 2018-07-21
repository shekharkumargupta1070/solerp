package com.sol.erp;

import java.awt.Container;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.sol.erp.ui.custom.SimplePrintableJTextArea;

public class printText implements java.awt.event.ActionListener {
	JFrame f = new JFrame("JTextArea Printing Demo");

	SimplePrintableJTextArea ta = new SimplePrintableJTextArea();
	javax.swing.JScrollPane sp = new javax.swing.JScrollPane(this.ta);
	JButton b1 = new JButton("Print Me");
	JPanel p1 = new JPanel();

	public void px() {
		Container localContainer = this.f.getContentPane();
		localContainer.setLayout(new java.awt.BorderLayout());
		this.p1.setLayout(new java.awt.FlowLayout(2));

		this.p1.add(this.b1);

		localContainer.add(this.sp, "Center");
		localContainer.add(this.p1, "South");

		this.f.setSize(600, 400);
		this.f.setVisible(true);

		this.b1.addActionListener(this);
	}

	public void actionPerformed(java.awt.event.ActionEvent paramActionEvent) {
		if (paramActionEvent.getSource() == this.b1) {
			this.ta.print();
		}
	}

}
