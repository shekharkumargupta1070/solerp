package com.sol.erp;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;

public class TableRenderer extends DefaultTableCellRenderer {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int row;
	private int col;

	public Component getTableCellRendererComponent(JTable paramJTable, Object paramObject, boolean paramBoolean1,
			boolean paramBoolean2, int paramInt1, int paramInt2) {
		this.row = paramInt1;
		this.col = paramInt2;

		return super.getTableCellRendererComponent(paramJTable, paramObject, paramBoolean1, paramBoolean2, paramInt1,
				paramInt2);
	}

	protected void setValue(Object paramObject) {
		super.setValue(paramObject);

		if (this.col == 0) {
			if (this.row % 2 == 0) {
				setForeground(Color.white);
				setBackground(new Color(0, 128, 0));
			} else {
				setForeground(UIManager.getColor("Table.foreground"));
				setBackground(UIManager.getColor("Table.background"));
			}

			return;
		}

		if (paramObject == null) {
			return;
		}

		Double localDouble = (Double) paramObject;

		if (localDouble.doubleValue() < 0.0D) {
			setForeground(Color.yellow);
			setBackground(Color.red);
		} else {
			setForeground(UIManager.getColor("Table.foreground"));
			setBackground(UIManager.getColor("Table.background"));
		}
	}
}
