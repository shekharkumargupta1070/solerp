package com.sol.erp;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.ButtonGroup;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.sol.erp.util.DBConnectionUtil;

public class CompanyList implements java.awt.event.KeyListener, java.awt.event.MouseListener {
	Connection con = null;
	Statement stat = null;
	ResultSet rs = null;

	public static String[] head = { "Name", "Id", "Branch", "Branch Add" };
	public static Object[][] data = new Object[0][];

	JDialog f = new JDialog();

	public static DefaultTableModel model = new DefaultTableModel(data, head);
	public static JTable tb = new JTable(model) {
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
			return (i > 3) && ((i <= 12) || (i > 15)) && (i <= 16);
		}
	};
	javax.swing.JScrollPane sp = new javax.swing.JScrollPane(tb);

	JLabel l1 = new JLabel("Name");
	JTextField tf1 = new JTextField(16);

	JPanel northpanel = new JPanel();
	JPanel centerpanel = new JPanel();
	JPanel mainpanel = new JPanel();

	JPanel northp1 = new JPanel();
	JPanel northp2 = new JPanel();

	JRadioButton rb1 = new JRadioButton("Name", true);
	JRadioButton rb2 = new JRadioButton("Company Id", false);

	ButtonGroup group = new ButtonGroup();

	javax.swing.border.Border bor1 = javax.swing.BorderFactory.createTitledBorder("Search By");

	public void DesignFrame() {
		Container localContainer = f.getContentPane();
		localContainer.setLayout(new BorderLayout());
		centerpanel.setLayout(new BorderLayout());
		northpanel.setLayout(new BorderLayout());
		northp1.setLayout(new FlowLayout(0));
		northp2.setLayout(new FlowLayout(2));
		mainpanel.setLayout(new BorderLayout());

		tf1.setFont(new Font("Verdana", 0, 10));

		group.add(rb1);
		group.add(rb2);

		northp1.add(l1);
		northp1.add(tf1);

		northp2.add(rb1);
		northp2.add(rb2);

		northp2.setBorder(bor1);

		tb.getColumnModel().getColumn(0).setPreferredWidth(250);
		tb.getColumnModel().getColumn(1).setPreferredWidth(30);
		tb.setRowHeight(18);
		tb.setFont(new Font("Verdana", 0, 10));
		tb.getTableHeader().setPreferredSize(new java.awt.Dimension(50, 25));
		tb.getTableHeader().setFont(new Font("Verdana", 1, 10));

		northpanel.add(northp1, "West");
		northpanel.add(northp2, "Center");

		centerpanel.add(sp, "Center");
		mainpanel.add(northpanel, "North");
		mainpanel.add(centerpanel, "Center");

		localContainer.add(mainpanel, "Center");

		tf1.addKeyListener(this);
		tb.addKeyListener(this);
		tb.addMouseListener(this);

		f.setUndecorated(true);
	}

	public void showFrame() {
		DesignFrame();
		f.setSize(600, 320);
		f.setLocationRelativeTo(null);
		f.setVisible(true);
	}

	public void getRecord() {
		model.setNumRows(0);
		try {
			String str1 = tf1.getText() + "%";

			String str2 = "FULL_NAME";
			if (rb2.isSelected()) {
				str2 = "Company_ID";
			}
			if (rb1.isSelected()) {
				str2 = "Name";
			}

			con =  DBConnectionUtil.getConnection();
			stat = con.createStatement();
			rs = stat.executeQuery("Select name, Company_id, br_code, address from HRCOMPANY_ID where  "
					+ str2 + " like '" + str1 + "' ");
			while (rs.next()) {
				String str3 = new String(rs.getString(1));
				String str4 = new String(rs.getString(2));
				String str5 = new String(rs.getString(3));
				String str6 = new String(rs.getString(4));

				model.addRow(new Object[] { str3, str4, str5, str6 });
			}
		} catch (Exception localException) {
			javax.swing.JOptionPane.showMessageDialog(f,
					" CompanyTable, getRecord : " + localException.getMessage().toString());
		}
	}

	static JTextField conametf = new JTextField();

	public void getCompanyName(JTextField paramJTextField) {
		conametf = paramJTextField;
		if (tb.getRowCount() > 0) {
			int i = tb.getSelectedRow();
			String str = String.valueOf(model.getValueAt(i, 0));
			conametf.setText(str);
		}
		paramJTextField = conametf;
	}

	static JTextField coidtf = new JTextField();

	public void getCompanyId(JTextField paramJTextField) {
		coidtf = paramJTextField;
		if (tb.getRowCount() > 0) {
			int i = tb.getSelectedRow();
			String str = String.valueOf(model.getValueAt(i, 1));
			coidtf.setText(str);
		}
		paramJTextField = coidtf;
	}

	static JTextField brcodetf = new JTextField();

	public void getBranchCode(JTextField paramJTextField) {
		brcodetf = paramJTextField;
		if (tb.getRowCount() > 0) {
			int i = tb.getSelectedRow();
			String str = String.valueOf(model.getValueAt(i, 2));
			brcodetf.setText(str);
		}
		paramJTextField = brcodetf;
	}

	static JTextField citytf = new JTextField();

	public void getBranchAddress(JTextField paramJTextField) {
		citytf = paramJTextField;
		if (tb.getRowCount() > 0) {
			int i = tb.getSelectedRow();
			String str = String.valueOf(model.getValueAt(i, 3));
			citytf.setText(str);
		}
		paramJTextField = citytf;
	}

	static JTextField modetf = new JTextField();

	public void getSalaryMode(JTextField paramJTextField) {
		modetf = paramJTextField;
		if (tb.getRowCount() > 0) {
			int i = tb.getSelectedRow();
			String str = String.valueOf(model.getValueAt(i, 4));
			modetf.setText(str);
		}
		paramJTextField = modetf;
	}

	public void keyPressed(KeyEvent paramKeyEvent) {
	}

	public void keyReleased(KeyEvent paramKeyEvent) {
		int i = paramKeyEvent.getKeyCode();

		if (i == 27) {
			f.setVisible(false);
		}

		if (paramKeyEvent.getSource() == tf1) {
			getRecord();

			if (i == 10) {
				tb.setRowSelectionInterval(0, 0);
				tb.requestFocus();
			}
		}

		if (paramKeyEvent.getSource() == tb) {
			getCompanyId(coidtf);
			getCompanyName(conametf);
			getBranchCode(brcodetf);
			getBranchAddress(citytf);
			getSalaryMode(modetf);

			if (i == 27) {
				f.setVisible(false);
			}
		}
	}

	public void keyTyped(KeyEvent paramKeyEvent) {
	}

	public void mousePressed(MouseEvent paramMouseEvent) {
	}

	public void mouseReleased(MouseEvent paramMouseEvent) {
	}

	public void mouseClicked(MouseEvent paramMouseEvent) {
		getCompanyId(coidtf);
		getCompanyName(conametf);
		getBranchCode(brcodetf);
		getBranchAddress(citytf);
		getSalaryMode(modetf);
	}

	public void mouseEntered(MouseEvent paramMouseEvent) {
	}

	public void mouseExited(MouseEvent paramMouseEvent) {
	}
}
