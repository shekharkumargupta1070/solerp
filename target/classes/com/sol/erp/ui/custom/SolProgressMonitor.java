package com.sol.erp.ui.custom;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

public class SolProgressMonitor implements java.awt.event.ActionListener {
	JDialog f;
	public static JProgressBar progress = new JProgressBar(0, 100);
	public static javax.swing.JLabel statusMessage = new javax.swing.JLabel("Message : Processing");

	JButton b1;

	JButton b2;

	JPanel northPanel;
	JPanel southPanel;
	public static JPanel p1 = new JPanel();
	java.awt.Toolkit tk;
	java.awt.Dimension ss;
	java.awt.Dimension fs;

	public SolProgressMonitor() {
		this.f = new JDialog();

		this.b1 = new JButton("OK");
		this.b2 = new JButton("Cancel");

		this.northPanel = new JPanel();
		this.southPanel = new JPanel();

		this.tk = java.awt.Toolkit.getDefaultToolkit();
		this.ss = this.tk.getScreenSize();
		this.fs = this.f.getSize();
	}

	public void DesignFrame() {
		java.awt.Container localContainer = this.f.getContentPane();
		localContainer.setLayout(new java.awt.BorderLayout());
		this.southPanel.setLayout(new java.awt.BorderLayout());
		p1.setLayout(new java.awt.FlowLayout(0));

		p1.add(statusMessage);
		progress.setStringPainted(true);

		this.southPanel.setPreferredSize(new java.awt.Dimension(100, 60));
		this.southPanel.add(p1, "South");

		localContainer.add(this.northPanel, "North");
		this.northPanel.setPreferredSize(new java.awt.Dimension(100, 40));
		localContainer.add(progress, "Center");
		progress.setPreferredSize(new java.awt.Dimension(100, 25));
		localContainer.add(this.southPanel, "South");
		p1.setBorder(javax.swing.BorderFactory.createBevelBorder(1));

		this.b1.addActionListener(this);
		this.b2.addActionListener(this);
	}

	public void showProgressMonitor() {
		DesignFrame();

		this.f.setTitle("SolProgressMonitor Version 1.0");
		this.f.getRootPane().setDefaultButton(this.b1);
		this.f.setResizable(false);
		this.f.setSize(400, 150);
		this.f.setLocationRelativeTo(null);
		this.f.setVisible(true);
	}

	public void showMonitor() {
		showProgressMonitor();
	}

	public void closeMonitor() {
		this.f.setVisible(false);
	}

	public JProgressBar getProgressBar() {
		return progress;
	}

	public javax.swing.JLabel getMessage() {
		return statusMessage;
	}

	public JPanel getMessageBackground() {
		return p1;
	}

	public void setMAX(int paramInt) {
		progress.setMaximum(paramInt);
	}

	public void setMIN(int paramInt) {
		progress.setMinimum(paramInt);
	}

	public void setValue(int paramInt) {
		progress.setValue(paramInt);
	}

	public int getValue() {
		return progress.getValue();
	}

	public int getMaximum() {
		return progress.getMaximum();
	}

	public int getMinimum() {
		return progress.getMinimum();
	}

	public void setProgressMessage(String paramString) {
		progress.setString(paramString);
	}

	public void setStatusMessage(String paramString) {
		statusMessage.setText(paramString);
	}

	public void setTitle(String paramString) {
		this.f.setTitle(paramString);
	}

	public int setSleepTime(int paramInt) {
		return paramInt;
	}

	public class runProgress extends Thread {
		public runProgress() {
		}

		public void run() {
			SolProgressMonitor.progress.setMinimum(0);
			SolProgressMonitor.progress.setMaximum(100);

			for (int i = SolProgressMonitor.progress.getMinimum(); i <= SolProgressMonitor.progress.getMaximum(); i++) {

				try {

					Thread.sleep(10L);
				} catch (InterruptedException localInterruptedException) {
					System.out.println(localInterruptedException);
				}
				SolProgressMonitor.progress.setValue(i);

				if (SolProgressMonitor.progress.getValue() + 1 == SolProgressMonitor.progress.getMaximum()) {
					SolProgressMonitor.this.f.setTitle("Job Completed");
					SolProgressMonitor.this.f.setVisible(false);
					// runProgress localrunProgress = new
					// runProgress(SolProgressMonitor.this);
					// localrunProgress.stop();
				}
			}
		}
	}

	public void runProgressMonitor() {
		runProgress localrunProgress = new runProgress();
		localrunProgress.start();
	}

	public void actionPerformed(java.awt.event.ActionEvent paramActionEvent) {
		if (paramActionEvent.getSource() == this.b1) {
		}
	}

}
