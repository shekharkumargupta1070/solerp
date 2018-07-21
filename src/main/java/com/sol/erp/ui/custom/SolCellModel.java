package com.sol.erp.ui.custom;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class SolCellModel extends DefaultTableCellRenderer {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Component getTableCellRendererComponent(JTable paramJTable, Object paramObject, boolean paramBoolean1,
			boolean paramBoolean2, int paramInt1, int paramInt2) {
		setEnabled((paramJTable == null) || (paramJTable.isEnabled()));

		if (paramInt1 % 2 == 0) {
			setBackground(new Color(240, 240, 240));
			setForeground(Color.darkGray);

		} else {
			setBackground(new Color(230, 230, 230));
			setForeground(Color.darkGray);
		}

		setHorizontalAlignment(2);

		if (paramInt2 == 0) {
			setBackground(new Color(200, 220, 240));
			setForeground(Color.blue);
		}

		super.getTableCellRendererComponent(paramJTable, paramObject, paramBoolean1, paramBoolean2, paramInt1,
				paramInt2);

		return this;
	}
}