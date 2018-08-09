package com.sol.erp;

import java.awt.Container;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.DefaultMutableTreeNode;

public class Letters implements javax.swing.event.TreeSelectionListener, java.awt.event.ActionListener {
	static OfferLetter ofr = new OfferLetter();
	static AppointLetter apt = new AppointLetter();
	static SalarySlip1 slp = new SalarySlip1();

	JFrame f = new JFrame("SOL Letters");
	JMenuBar menubar = new JMenuBar();
	javax.swing.JMenu m1 = new javax.swing.JMenu("File");
	javax.swing.JMenuItem mi1 = new javax.swing.JMenuItem("Close");

	DefaultMutableTreeNode top = new DefaultMutableTreeNode("Departments");
	DefaultMutableTreeNode finance = new DefaultMutableTreeNode("Finance");
	DefaultMutableTreeNode tech = new DefaultMutableTreeNode("Technichal");

	DefaultMutableTreeNode hr = new DefaultMutableTreeNode("HR");
	DefaultMutableTreeNode offer = new DefaultMutableTreeNode("OFFER Letter");
	DefaultMutableTreeNode appoint = new DefaultMutableTreeNode("Appt. Letter");
	DefaultMutableTreeNode increament = new DefaultMutableTreeNode("Increament");
	DefaultMutableTreeNode memo = new DefaultMutableTreeNode("Memo");
	DefaultMutableTreeNode releaving = new DefaultMutableTreeNode("Releaving");
	DefaultMutableTreeNode salary = new DefaultMutableTreeNode("Salary Slip");
	DefaultMutableTreeNode salaried = new DefaultMutableTreeNode("SALARIED");
	DefaultMutableTreeNode packaged = new DefaultMutableTreeNode("PACKAGED");
	DefaultMutableTreeNode nontech = new DefaultMutableTreeNode("NON Tech");

	DefaultMutableTreeNode personal = new DefaultMutableTreeNode("Personal");
	DefaultMutableTreeNode it = new DefaultMutableTreeNode("IT");

	JTree tree = new JTree(this.top);
	javax.swing.JScrollPane treeSP = new javax.swing.JScrollPane(this.tree);
	JPanel centerPanel = new JPanel();

	JSplitPane spliter = new JSplitPane(1, this.treeSP, this.centerPanel);

	JPanel mainPanel = new JPanel();

	public void DesignFrame() {
		Container localContainer = this.f.getContentPane();
		localContainer.setLayout(new java.awt.BorderLayout());
		this.mainPanel.setLayout(new java.awt.BorderLayout());
		this.centerPanel.setLayout(new java.awt.BorderLayout());

		this.top.add(this.hr);

		this.hr.add(this.offer);
		this.hr.add(this.appoint);

		this.salary.add(this.salaried);
		this.salary.add(this.packaged);
		this.salary.add(this.nontech);

		this.spliter.setDividerLocation(250);
		this.spliter.setOneTouchExpandable(true);
		this.mainPanel.add(this.spliter, "Center");

		this.m1.add(this.mi1);
		this.menubar.add(this.m1);

		this.f.setJMenuBar(this.menubar);
		localContainer.add(this.mainPanel, "Center");

		this.tree.addTreeSelectionListener(this);
		this.mi1.addActionListener(this);
	}

	public void showFrame() {
		DesignFrame();
		this.f.setSize(800, 600);
		this.f.setLocationRelativeTo(null);
		this.f.setVisible(true);
	}

	public void valueChanged(TreeSelectionEvent paramTreeSelectionEvent) {
		if (paramTreeSelectionEvent.getSource() == this.tree) {
			DefaultMutableTreeNode localDefaultMutableTreeNode = (DefaultMutableTreeNode) this.tree
					.getLastSelectedPathComponent();
			String str = String.valueOf(localDefaultMutableTreeNode.getUserObject());

			if (str.equalsIgnoreCase("Salary Slip")) {
				this.centerPanel.removeAll();
				this.centerPanel.add(slp.DesignFrame(), "Center");
				this.centerPanel.revalidate();
			}
			if (str.equalsIgnoreCase("memo")) {
				javax.swing.JOptionPane.showMessageDialog(this.f, "Hello Memo");
			}
			if (str.equalsIgnoreCase("OFFER Letter")) {
				this.centerPanel.removeAll();
				this.centerPanel.add(ofr.DesignFrame(), "Center");
				this.centerPanel.revalidate();
			}
			if (str.equalsIgnoreCase("Appt. Letter")) {
				this.centerPanel.removeAll();
				this.centerPanel.add(apt.DesignFrame(), "Center");
				this.centerPanel.revalidate();
			} else {
				this.centerPanel.setBackground(java.awt.Color.darkGray);
				this.centerPanel.revalidate();
			}
		}
	}

	public void actionPerformed(java.awt.event.ActionEvent paramActionEvent) {
		if (paramActionEvent.getSource() == this.mi1) {
		}

		this.f.setVisible(false);
	}

}
