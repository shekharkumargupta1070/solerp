package com.sol.erp;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import com.sol.erp.ui.custom.SolTableModel;
import com.sol.erp.util.DBConnectionUtil;
import com.sol.erp.util.SessionUtil;

public class ReqModeList
		implements java.awt.event.MouseListener, javax.swing.event.TableModelListener, java.awt.event.KeyListener {
	
	

	java.sql.Connection con;
	java.sql.Statement stat;
	ResultSet rs;
	public static String reqmodes = "sk";

	static String[] columnNames = { "REQ Mode" };
	static Object[][] data = new Object[0][];

	ArrayList<String> arl = new ArrayList<String>();

	JDialog f = new JDialog();

	JButton b4 = new JButton("Show All");
	JButton b5 = new JButton("Search");

	static DefaultTableModel model = new DefaultTableModel(data, columnNames);
	static JTable tb = new JTable(model);
	javax.swing.JScrollPane sp = new javax.swing.JScrollPane(tb);

	DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();

	JPanel p1 = new JPanel();
	JPanel p2 = new JPanel();
	JPanel buttonpanel = new JPanel();

	javax.swing.border.Border bor1 = javax.swing.BorderFactory.createTitledBorder("Search Option");

	java.awt.Font fo = new java.awt.Font("Arial", 1, 11);

	java.awt.Toolkit tk = java.awt.Toolkit.getDefaultToolkit();
	Dimension ss = tk.getScreenSize();
	Dimension fs = f.getSize();

	class ColoredTableCellRenderer extends DefaultTableCellRenderer {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		ColoredTableCellRenderer() {
		}

		public java.awt.Component getTableCellRendererComponent(JTable paramJTable, Object paramObject,
				boolean paramBoolean1, boolean paramBoolean2, int paramInt1, int paramInt2) {
			setEnabled((paramJTable == null) || (paramJTable.isEnabled()));

			if (paramInt1 % 2 == 0) {
				setBackground(new Color(250, 250, 250));
				setForeground(Color.darkGray);

			} else {
				setBackground(new Color(240, 240, 240));
				setForeground(Color.darkGray);
			}

			if (paramInt2 > 0) {
				setHorizontalAlignment(0);
			}

			super.getTableCellRendererComponent(paramJTable, paramObject, paramBoolean1, paramBoolean2, paramInt1,
					paramInt2);

			return this;
		}
	}

	ReqModeList.ColoredTableCellRenderer skr = new ReqModeList.ColoredTableCellRenderer();


	public void showReqModeList() {
		Container localContainer = f.getContentPane();
		localContainer.setLayout(new java.awt.BorderLayout());
		p1.setLayout(new java.awt.GridLayout(4, 2, 2, 2));
		p2.setLayout(new java.awt.BorderLayout());

		SolTableModel.decorateTable(tb);

		tb.setShowGrid(false);

		tb.getColumnModel().getColumn(0).setCellRenderer(skr);
		tb.getColumnModel().getColumn(0).setPreferredWidth(150);

		model.addTableModelListener(this);

		tb.addMouseListener(this);
		tb.addKeyListener(this);

		p2.add(sp, "Center");

		localContainer.add(p2, "Center");

		buttonpanel.setBorder(bor1);

		f.setTitle("Req Mode List");
		f.setLocation((ss.width - fs.width) / 3, (ss.height - fs.height) / 4);
		f.setSize(250, 300);
		f.setVisible(true);
		showRecord();
	}

	public void showRecord() {
		String str1 = SessionUtil.getLoginInfo().getCompanyId();
		String str2 = SessionUtil.getLoginInfo().getBranchCode();
		System.out.println(str1 + "\t" + str2);
		arl.clear();

		model.setNumRows(0);
		try {
			con = DBConnectionUtil.getConnection();
			stat = con.createStatement();
			rs = stat.executeQuery("select REQ_MODES from HRCOMPANY_REQ_MODES where Company_Id='" + str1
					+ "' and br_code='" + str2 + "' ");
			rs.getMetaData().getColumnCount();
			rs.getRow();
			while (rs.next()) {
				String str3 = new String(rs.getString(1));
				model.addRow(new Object[] { str3 });
				arl.add(str3);
			}
		} catch (Exception localException) {
		}
	}

	static JTextField sktf = new JTextField();

	public void getReqMode(JTextField paramJTextField) {
		sktf = paramJTextField;
		reqmodes = String.valueOf(model.getValueAt(tb.getSelectedRow(), 0));
		paramJTextField.setText(reqmodes);
		paramJTextField = sktf;
		System.out.println(reqmodes);
	}

	public ArrayList<String> getModeList() {
		showRecord();
		return arl;
	}

	public void keyPressed(KeyEvent paramKeyEvent) {
	}

	public void keyReleased(KeyEvent paramKeyEvent) {
		int i = paramKeyEvent.getKeyCode();
		if (i == 27) {
			f.setVisible(false);
		}
	}

	public void keyTyped(KeyEvent paramKeyEvent) {
	}

	public void mousePressed(MouseEvent paramMouseEvent) {
	}

	public void mouseReleased(MouseEvent paramMouseEvent) {
		if (paramMouseEvent.getSource() == tb) {
			getReqMode(sktf);
			System.out.println("Clicked : " + sktf.getText());
			f.setVisible(false);
		}
	}

	public void mouseClicked(MouseEvent paramMouseEvent) {
	}

	public void mouseExited(MouseEvent paramMouseEvent) {
	}

	public void mouseEntered(MouseEvent paramMouseEvent) {
	}

	public void tableChanged(javax.swing.event.TableModelEvent paramTableModelEvent) {
		tb.setForeground(Color.blue);
	}

}
