package com.sol.erp.ui.custom;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class SolTableCheckBoxFinder {

	/*
	 * Somtime a Programer Need to set a Checkbox in a JTable. It Means There is
	 * a Checkbox in Each Row. And all the Checkboxes are Selectable. You can
	 * Select any number of checkbox. This is a by Default Feature. But Now, I
	 * want to Select only one checkbox at a Time. it Must work as a
	 * radiobutton.
	 */

	public void makeCheckBoxSingleSelectable(JTable tb, DefaultTableModel model, int col) {
		for (int row = 0; row < tb.getRowCount(); row++) {
			model.setValueAt(new Boolean("false"), row, col);
		}

		int row = tb.getSelectedRow();
		model.setValueAt(new Boolean("true"), row, col);
	}

	/*
	 * Sometime we want to Select a Checkbox in a Table According to the
	 * Parameter we Passed at Runtime. I mean, exmp : There is a Table
	 * Containing These Columns : checkbox, Roll, Marks, Division. Now We want
	 * to Search for 2nd Division records. For That When we Pass String(e.g.
	 * 2nd) Parameter. It will Select the Checkbox reside in that Row.
	 */

	public void setChecked(JTable tb, DefaultTableModel model, String str, int checkboxcol, int datacol) {
		for (int row = 0; row < tb.getRowCount(); row++) {
			String valstr = String.valueOf(model.getValueAt(row, datacol));
			if (valstr.equalsIgnoreCase(str)) {
				model.setValueAt(new Boolean("true"), row, checkboxcol);
			}
		}
	}

	/*
	 * Sometime we need to get some values from Selected Row. Selected by
	 * Checkbox. This Code Return a String;
	 */

	public String getChecked(JTable tb, DefaultTableModel model, int checkboxcol, int datacol) {
		String str = null;

		for (int col = 0; col < tb.getColumnCount(); col++) {
			for (int row = 0; row < tb.getRowCount(); row++) {
				String valstr = String.valueOf(model.getValueAt(row, checkboxcol));
				if (valstr.equalsIgnoreCase("true")) {
					str = String.valueOf(model.getValueAt(row, datacol));
				}
			}
		}

		return str;

	}

}