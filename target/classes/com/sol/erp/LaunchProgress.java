/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sol.erp;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

import com.sol.erp.constants.ApplicationConstants;
import com.sol.erp.dao.ProjectDAO;
import com.sol.erp.util.DBConnectionUtil;

/**
 *
 * @author shekharkumar
 */
public class LaunchProgress extends javax.swing.JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Creates new form LaunchProgress
	 */
	public LaunchProgress() {
		designScreen();
	}

	private void designScreen() {
		Icon imageIcon = new ImageIcon(ClassLoader.getSystemResource(ApplicationConstants.IMAGE_PATH + "sollog.jpg"));
		jProgressBar1 = new javax.swing.JProgressBar();
		labelImage = new javax.swing.JLabel(imageIcon);
		labelMsg = new javax.swing.JLabel();

		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		setMaximumSize(new java.awt.Dimension(500, 300));
		setMinimumSize(new java.awt.Dimension(500, 300));
		setUndecorated(true);
		setPreferredSize(new java.awt.Dimension(500, 300));
		setResizable(false);

		jProgressBar1.setMaximumSize(new java.awt.Dimension(400, 14));
		jProgressBar1.setMinimumSize(new java.awt.Dimension(400, 14));
		jProgressBar1.setPreferredSize(new java.awt.Dimension(400, 14));
		jProgressBar1.setIndeterminate(true);

		labelMsg.setText("Initializing...");

		JPanel progresPanel = new JPanel();
		progresPanel.setLayout(new GridLayout(0, 1));
		progresPanel.add(labelMsg);
		progresPanel.add(jProgressBar1);
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(labelImage, BorderLayout.CENTER);
		getContentPane().add(progresPanel, BorderLayout.SOUTH);

		Toolkit toolKit = Toolkit.getDefaultToolkit();
		Dimension screenSize = toolKit.getScreenSize();
		Dimension frameSize = this.getSize();
		this.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
	}

	private void launchApp() {
		Properties prop = PropertiesHandler.readProperties();
		if (prop != null && DBConnectionUtil.isDBRunning()) {
			this.setVisible(false);
			Login lo = new Login();
			lo.px();
			EmpTB.populateDB();
			ProjectDAO.chacheInMemory();
			ItemTB.populateDB();
			EmpLeaveTB.populateDB();
		} else {
			this.setVisible(false);
			DBConfig config = new DBConfig();
			config.showScreen();
		}
	}

	public static void main(String args[]) {
		try {
			for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					javax.swing.UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (ClassNotFoundException ex) {
			java.util.logging.Logger.getLogger(LaunchProgress.class.getName()).log(java.util.logging.Level.SEVERE, null,
					ex);
		} catch (InstantiationException ex) {
			java.util.logging.Logger.getLogger(LaunchProgress.class.getName()).log(java.util.logging.Level.SEVERE, null,
					ex);
		} catch (IllegalAccessException ex) {
			java.util.logging.Logger.getLogger(LaunchProgress.class.getName()).log(java.util.logging.Level.SEVERE, null,
					ex);
		} catch (javax.swing.UnsupportedLookAndFeelException ex) {
			java.util.logging.Logger.getLogger(LaunchProgress.class.getName()).log(java.util.logging.Level.SEVERE, null,
					ex);
		}

		LaunchProgress launchProgress = new LaunchProgress();
		launchProgress.setVisible(true);
		try {
			Thread.sleep(2000);
		} catch (InterruptedException ex) {
			Logger.getLogger(LaunchProgress.class.getName()).log(Level.SEVERE, null, ex);
		}
		launchProgress.launchApp();

	}

	private javax.swing.JProgressBar jProgressBar1;
	private javax.swing.JLabel labelImage;
	private javax.swing.JLabel labelMsg;
	// End of variables declaration//GEN-END:variables
}
