package com.sol.erp;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

public class ColoredCells extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	ColoredCells(String paramString) {
		super(paramString);

		setDefaultCloseOperation(3);

		String[] arrayOfString1 = { "Name", "Balance" };

		DefaultTableModel localDefaultTableModel = new DefaultTableModel(arrayOfString1, 5);

		String[] arrayOfString2 = { "John Doe", "Jane Smith", "Jack Jones", "Paul Finch", "Susan Smyth" };

		double[] arrayOfDouble = { -50000.0D, 4000.0D, -32.3D, 802.5D, -128.0D };

		int i = localDefaultTableModel.getRowCount();
		int j = localDefaultTableModel.getColumnCount();

		for (int k = 0; k < i; k++) {
			localDefaultTableModel.setValueAt(arrayOfString2[k], k, 0);
			localDefaultTableModel.setValueAt(new Double(arrayOfDouble[k]), k, 1);
		}

		JTable localJTable = new JTable(localDefaultTableModel);

		TableRenderer localTableRenderer = new TableRenderer();

		TableColumnModel localTableColumnModel = localJTable.getColumnModel();

		for (int m = 0; m < j; m++) {
			TableColumn localTableColumn = localTableColumnModel.getColumn(m);
			localTableColumn.setCellRenderer(localTableRenderer);
		}

		JScrollPane localJScrollPane = new JScrollPane(localJTable);

		getContentPane().add(localJScrollPane);

		setSize(200, 130);

		localJTable.setAutoResizeMode(0);
		localJTable.scrollRectToVisible(localJTable.getCellRect(2, 1, false));

		setVisible(true);
	}
}
