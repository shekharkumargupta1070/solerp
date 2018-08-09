package com.sol.erp.ui.custom;

import java.awt.Dimension;

import javax.swing.JTable;

public class SolTableModel {

	public static void decorateTable(JTable paramJTable) {
		paramJTable.getTableHeader().setPreferredSize(new Dimension(50, 25));
		paramJTable.setRowHeight(25);
		paramJTable.setIntercellSpacing(new Dimension(0, 0));
	}

}
