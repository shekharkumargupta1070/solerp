package com.sol.erp.ui.custom;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.MouseEvent;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

public class SolTableColumnWork implements java.awt.event.MouseListener {
    
	public static SolTableColumnModel myColumnModel = new SolTableColumnModel();
	JDialog f = new JDialog();
	JPanel mainpanel = new JPanel();
	Object[][] data = new Object[0][];
	String[] heads = { " ", "Column List" };
	DefaultTableModel model = new DefaultTableModel(data, heads);
	JTable tb = new JTable(model) {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@SuppressWarnings({ "unchecked", "rawtypes" })
		public Class getColumnClass(int paramAnonymousInt) {
			return getValueAt(0, paramAnonymousInt).getClass();
		}

		public boolean isCellEditable(int paramAnonymousInt1, int paramAnonymousInt2) {
			int i = convertColumnIndexToModel(paramAnonymousInt2);
			return i <= 0;
		}
	};

	javax.swing.JScrollPane sp = new javax.swing.JScrollPane(tb);

	public JPanel DesignFrame(JTable paramJTable, DefaultTableModel paramDefaultTableModel) {
		f.setTitle("List of Table Column");
		tb.getColumnModel().getColumn(0).setPreferredWidth(15);
		tb.getColumnModel().getColumn(1).setPreferredWidth(120);

		tb.setFont(new java.awt.Font("verdana", 0, 10));
		int i;
		if (tb.getRowCount() > 0) {
			if (tb.getRowCount() != paramDefaultTableModel.getColumnCount()) {
				model.setNumRows(0);
				for (i = 0; i < paramJTable.getColumnCount(); i++) {
					model.addRow(new Object[] { new Boolean(true), paramJTable.getColumnName(i) });
				}
				paramJTable.setColumnModel(myColumnModel);
				paramJTable.createDefaultColumnsFromModel();
			}
			if (tb.getRowCount() != paramDefaultTableModel.getColumnCount()) {
			}
		}

		if (tb.getRowCount() <= 0) {

			for (i = 0; i < paramDefaultTableModel.getColumnCount(); i++) {
				model.addRow(new Object[] { new Boolean(true), paramJTable.getColumnName(i) });
			}
			paramJTable.setColumnModel(myColumnModel);
			paramJTable.createDefaultColumnsFromModel();
		}

		Container localContainer = f.getContentPane();
		localContainer.setLayout(new BorderLayout());
		mainpanel.setLayout(new BorderLayout());
		mainpanel.add(sp, "Center");
		localContainer.add(mainpanel, "Center");
		tb.addMouseListener(this);

		return mainpanel;
	}

	public void showFrame() {
		f.setSize(200, 400);
		f.setLocationRelativeTo(null);
		f.setVisible(true);
	}

	public void setColumnListSize(int paramInt) {
		model.setNumRows(0);
	}

	public void mousePressed(MouseEvent paramMouseEvent) {
	}

	public void mouseReleased(MouseEvent paramMouseEvent) {
	}

	public void mouseClicked(MouseEvent paramMouseEvent) {
		if (paramMouseEvent.getSource() == tb) {
			int i = tb.getSelectedRow();
			int j = tb.getSelectedColumn();
			String str = String.valueOf(model.getValueAt(i, 0));
			if (j == 0) {
				TableColumn localTableColumn;
				if (str.equalsIgnoreCase("false")) {
					localTableColumn = myColumnModel.getColumnByModelIndex(i);
					myColumnModel.setColumnVisible(localTableColumn, false);
				}
				if (str.equalsIgnoreCase("true")) {
					localTableColumn = myColumnModel.getColumnByModelIndex(i);
					myColumnModel.setColumnVisible(localTableColumn, true);
				}
			}
		}
	}

	public void mouseEntered(MouseEvent paramMouseEvent) {
	}

	public void mouseExited(MouseEvent paramMouseEvent) {
	}
}
