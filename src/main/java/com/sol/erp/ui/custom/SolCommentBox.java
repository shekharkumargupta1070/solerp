package com.sol.erp.ui.custom;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class SolCommentBox implements java.awt.event.ActionListener {
	/* 7 */ public static JFrame f = new JFrame();
	/* 8 */ public static javax.swing.JTextArea tp = new javax.swing.JTextArea(20, 40);
	/* 9 */ public static javax.swing.JScrollPane sp = new javax.swing.JScrollPane(tp);

	public static JButton b1 = new JButton("OK");
	public static JButton b2 = new JButton("Cancel");

	JButton but1 = new JButton(new javax.swing.ImageIcon("new.gif"));
	JButton but2 = new JButton(new javax.swing.ImageIcon("cut.gif"));
	JButton but3 = new JButton(new javax.swing.ImageIcon("copy.gif"));
	JButton but4 = new JButton(new javax.swing.ImageIcon("paste.gif"));

	public static JPanel p1 = new JPanel();
	JPanel p2 = new JPanel();

	java.awt.Toolkit tk = java.awt.Toolkit.getDefaultToolkit();
	java.awt.Dimension ss = this.tk.getScreenSize();
	java.awt.Dimension fs = f.getSize();

	public void showCommentBox() {
		java.awt.Container localContainer = f.getContentPane();
		localContainer.setLayout(new java.awt.BorderLayout());
		this.p2.setLayout(new java.awt.FlowLayout(0));
		p1.setLayout(new java.awt.FlowLayout(2));

		p1.add(b1);
		b1.setMnemonic(79);
		p1.add(b2);
		b2.setMnemonic(67);
		p1.revalidate();
		p1.setBackground(new java.awt.Color(220, 220, 220));

		this.p2.add(this.but1);
		this.p2.add(this.but2);
		this.p2.add(this.but3);
		this.p2.add(this.but4);

		localContainer.add(sp, "Center");
		localContainer.add(p1, "South");

		b1.addActionListener(this);
		b2.addActionListener(this);

		this.but1.addActionListener(this);
		this.but2.addActionListener(this);
		this.but3.addActionListener(this);
		this.but4.addActionListener(this);

		f.getRootPane().setDefaultButton(b1);

		f.setTitle("skCommentBox1.0");

		f.setSize(460, 260);
		f.setLocationRelativeTo(null);
		f.setResizable(false);
		f.pack();
		f.setVisible(true);
	}

	String commentstr = null;

	public String getComment() {
		return this.commentstr;
	}

	public void clear() {
		tp.setText("");
	}

	public void setComment(String paramString) {
		tp.setText(tp.getText() + "\n" + paramString);
	}

	public void setTitle(String paramString) {
		f.setTitle(paramString);
	}

	public JButton OK() {
		return b1;
	}

	public JButton CANCEL() {
		return b2;
	}

	public void actionPerformed(java.awt.event.ActionEvent paramActionEvent) {
		if (paramActionEvent.getSource() == b1) {
			this.commentstr = String.valueOf(tp.getText());
			System.out.println(this.commentstr);
			f.setVisible(false);
		}
		if (paramActionEvent.getSource() == b2) {
			tp.setText("");
			f.setVisible(false);
		}
		if (paramActionEvent.getSource() == this.but1) {
			tp.setText("");
		}
		if (paramActionEvent.getSource() == this.but2) {
			tp.cut();
		}
		if (paramActionEvent.getSource() == this.but3) {
			tp.copy();
		}
		if (paramActionEvent.getSource() == this.but4) {
			tp.paste();
		}
	}

}
