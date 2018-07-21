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
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.sol.erp.util.DBConnectionUtil;

public class CompanyTable implements java.awt.event.KeyListener, java.awt.event.MouseListener {
	Connection con = null;
	Statement stat = null;
	ResultSet rs = null;

	public static String[] head = { "Name", "Id", "Branch", "Branch Add", "Salary Mode" };
	public static Object[][] data = new Object[0][];

	JFrame frame = new JFrame("Company Table");
	JDialog f = new JDialog();

	public static DefaultTableModel model = new DefaultTableModel(data, head);
	public static JTable tb = new JTable(model) {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public Class<? extends Object> getColumnClass(int paramAnonymousInt) {
			return getValueAt(0, paramAnonymousInt).getClass();
		}

		public boolean isCellEditable(int paramAnonymousInt1, int paramAnonymousInt2) {
			int i = convertColumnIndexToModel(paramAnonymousInt2);
			return (i > 3) && ((i <= 12) || (i > 15)) && (i <= 16);
		}
	};
	javax.swing.JScrollPane sp = new javax.swing.JScrollPane(tb);

	JLabel coidlabel = new JLabel("Company ID");
	JLabel conamelabel = new JLabel("Name");
	JLabel brcodelabel = new JLabel("Branch Code");

	JTextField coidtf1 = new JTextField("C01", 3);
	JTextField conametf1 = new JTextField("Structures Online", 16);
	JTextField brcodetf1 = new JTextField("0", 3);

	JPanel contextpanel = new JPanel();

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

	Font fo = new Font("Verdana", 0, 10);

	public JPanel showContextFrame() {
		Container localContainer = frame.getContentPane();
		localContainer.setLayout(new BorderLayout());
		contextpanel.setLayout(new FlowLayout(0));

		contextpanel.add(coidlabel);
		contextpanel.add(coidtf1);
		contextpanel.add(conamelabel);
		contextpanel.add(conametf1);
		contextpanel.add(brcodelabel);
		contextpanel.add(brcodetf1);

		coidtf1.setFont(fo);
		conametf1.setFont(fo);
		brcodetf1.setFont(fo);

		conametf1.setEditable(false);
		brcodetf1.setEditable(false);

		localContainer.add(contextpanel, "Center");

		coidtf1.addKeyListener(this);
		tb.addKeyListener(this);

		return contextpanel;
	}

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
			rs = stat
					.executeQuery("Select name, Company_id, br_code, city, req_modes from HRCOMPANY_REQ_MODES where  "
							+ str2 + " like '" + str1 + "' ");
			while (rs.next()) {
				String str3 = new String(rs.getString(1));
				String str4 = new String(rs.getString(2));
				String str5 = new String(rs.getString(3));
				String str6 = new String(rs.getString(4));
				String str7 = new String(rs.getString(5));

				model.addRow(new Object[] { str3, str4, str5, str6, str7 });
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

	public String getMyCompanyId() {
		return coidtf1.getText();
	}

	public String getMyCompanyName() {
		return conametf1.getText();
	}

	public String getMyBranchCode() {
		return brcodetf1.getText();
	}

	public void keyPressed(KeyEvent paramKeyEvent) {
	}

	public void keyReleased(KeyEvent paramKeyEvent) {
		int i;
		if (paramKeyEvent.getSource() == coidtf1) {
			i = paramKeyEvent.getKeyCode();
			if ((i == 10) || (i == 112)) {
				CompanyTable localCompanyTable = new CompanyTable();
				localCompanyTable.showFrame();
				localCompanyTable.getCompanyId(coidtf1);
				localCompanyTable.getCompanyName(conametf1);
				localCompanyTable.getBranchCode(brcodetf1);
			}
			if (i == 27) {
				f.setVisible(false);
			}
		}

		if (paramKeyEvent.getSource() == tf1) {

			i = paramKeyEvent.getKeyCode();
			getRecord();

			if (tb.getRowCount() > 0) {
				tb.setRowSelectionInterval(0, 0);
				tb.requestFocus();
			}

			if ((i != 10) ||

					(i == 27)) {
				f.setVisible(false);
			}
		}

		if (paramKeyEvent.getSource() == tb) {
			getCompanyId(coidtf);
			getCompanyName(conametf);
			getBranchCode(brcodetf);
			getBranchAddress(citytf);
			getSalaryMode(modetf);

			i = paramKeyEvent.getKeyCode();
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
