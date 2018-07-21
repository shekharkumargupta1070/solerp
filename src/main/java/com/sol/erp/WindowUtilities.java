package com.sol.erp;

import java.awt.Color;
import java.awt.Container;

import javax.swing.JFrame;
import javax.swing.UIManager;

public class WindowUtilities {
	public static void setNativeLookAndFeel() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception localException) {
			System.out.println("Error setting native LAF: " + localException);
		}
	}

	public static void setJavaLookAndFeel() {
		try {
			UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
		} catch (Exception localException) {
			System.out.println("Error setting Java LAF: " + localException);
		}
	}

	public static void setMotifLookAndFeel() {
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
		} catch (Exception localException) {
			System.out.println("Error setting Motif LAF: " + localException);
		}
	}

	public static JFrame openInJFrame(Container paramContainer, int paramInt1, int paramInt2, String paramString,
			Color paramColor) {
		JFrame localJFrame = new JFrame(paramString);
		localJFrame.setBackground(paramColor);
		paramContainer.setBackground(paramColor);
		localJFrame.setSize(paramInt1, paramInt2);
		localJFrame.setContentPane(paramContainer);
		localJFrame.addWindowListener(new ExitListener());
		localJFrame.setVisible(true);
		return localJFrame;
	}

	public static JFrame openInJFrame(Container paramContainer, int paramInt1, int paramInt2, String paramString) {
		return openInJFrame(paramContainer, paramInt1, paramInt2, paramString, Color.white);
	}

	public static JFrame openInJFrame(Container paramContainer, int paramInt1, int paramInt2) {
		return openInJFrame(paramContainer, paramInt1, paramInt2, paramContainer.getClass().getName(), Color.white);
	}
}
	