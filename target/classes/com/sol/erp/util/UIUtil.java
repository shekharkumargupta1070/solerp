package com.sol.erp.util;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JTable;

public class UIUtil {

	public static void centreToMainScreen(JFrame frame) {
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (int) ((dimension.getWidth() - frame.getWidth()) / 2);
		int y = (int) ((dimension.getHeight() - frame.getHeight()) / 2);
		frame.setLocation(x, y);
	}

	public static void centreToApplication(JInternalFrame frame) {
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (int) ((dimension.getWidth() - frame.getWidth()) / 2);
		int y = (int) ((dimension.getHeight() - frame.getHeight()) / 2) - 80;
		frame.setLocation(x, y);
	}

	public static void customTableDecoration(JTable table) {
		table.getTableHeader().setPreferredSize(new Dimension(50, 25));
		table.setRowHeight(25);
		table.setIntercellSpacing(new Dimension(0, 0));
	}
	
	public static void shutDownCleanUp(){
		
	}
}
