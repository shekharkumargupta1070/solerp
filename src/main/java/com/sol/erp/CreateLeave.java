package com.sol.erp;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.sql.ResultSet;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.sol.erp.dao.UtilQueryResult;
import com.sol.erp.ui.custom.SolCellModel;
import com.sol.erp.ui.custom.SolTableModel;
import com.sol.erp.util.DBConnectionUtil;

public class CreateLeave extends javax.swing.JInternalFrame implements java.awt.event.ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	java.sql.Connection con = null;
	java.sql.Statement stat = null;
	ResultSet rs = null;


	UtilQueryResult qr = new UtilQueryResult();
	SolCellModel skc = new SolCellModel();

	String[] head = { "Leave Type", "Max Allowed", "Penalty" };
	Object[][] data = { new Object[0] };

	private JButton b1;
	private JButton b2;
	private JButton b3;
	private JButton b4;

	public CreateLeave() {
		initComponents();
		con = DBConnectionUtil.getConnection();
	}

	private JLabel jLabel1;
	private JLabel jLabel2;
	private JLabel jLabel3;
	private JPanel jPanel1;

	private void initComponents() {
		jPanel1 = new JPanel();
		jLabel1 = new JLabel();
		tf1 = new JTextField();
		jLabel2 = new JLabel();
		tf2 = new JTextField();
		jLabel3 = new JLabel();
		tf3 = new JTextField();
		b3 = new JButton();
		jPanel3 = new JPanel();
		b1 = new JButton();
		b2 = new JButton();
		b4 = new JButton();
		jPanel2 = new JPanel();
		jScrollPane1 = new javax.swing.JScrollPane();
		model = new DefaultTableModel(data, head);
		tb1 = new JTable(model);

		SolTableModel.decorateTable(tb1);

		for (int i = 0; i < tb1.getColumnCount(); i++) {
			tb1.getColumnModel().getColumn(i).setCellRenderer(skc);
		}

		getContentPane().setLayout(null);

		setClosable(true);
		setIconifiable(true);
		setMaximizable(true);
		setResizable(true);
		setTitle("Create Leaves");
		setAutoscrolls(true);
		setFrameIcon(null);
		try {
			setSelected(true);
		} catch (java.beans.PropertyVetoException localPropertyVetoException) {
			localPropertyVetoException.printStackTrace();
		}
		setVisible(true);
		getAccessibleContext().setAccessibleName("");
		jPanel1.setLayout(null);

		jPanel1.setBorder(new javax.swing.border.LineBorder(new Color(102, 102, 102), 1, true));
		jLabel1.setText("Leave Type");
		jPanel1.add(jLabel1);
		jLabel1.setBounds(40, 20, 90, 15);

		tf1.setText("");
		jPanel1.add(tf1);
		tf1.setBounds(150, 20, 140, 21);

		jLabel2.setText("Max Allowed");
		jPanel1.add(jLabel2);
		jLabel2.setBounds(40, 50, 90, 15);

		tf2.setText("");
		jPanel1.add(tf2);
		tf2.setBounds(150, 50, 40, 21);

		jLabel3.setText("Penalty");
		jPanel1.add(jLabel3);
		jLabel3.setBounds(40, 80, 90, 15);

		tf3.setText("");
		jPanel1.add(tf3);
		tf3.setBounds(150, 80, 40, 21);

		getContentPane().add(jPanel1);
		jPanel1.setBounds(130, 30, 320, 130);

		b3.setText("Close");
		getContentPane().add(b3);
		b3.setBounds(480, 420, 73, 25);

		jPanel3.setBorder(new javax.swing.border.LineBorder(new Color(102, 102, 102), 1, true));
		b1.setText("Create");
		jPanel3.add(b1);

		b2.setText("Update");
		jPanel3.add(b2);

		b4.setText("Show");
		jPanel3.add(b4);

		getContentPane().add(jPanel3);
		jPanel3.setBounds(40, 180, 490, 40);

		jPanel2.setLayout(new java.awt.BorderLayout());

		jPanel2.setBorder(new javax.swing.border.LineBorder(new Color(102, 102, 102), 1, true));

		jScrollPane1.setViewportView(tb1);

		jPanel2.add(jScrollPane1, "Center");

		getContentPane().add(jPanel2);
		jPanel2.setBounds(40, 230, 490, 180);

		java.awt.Dimension localDimension = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
		setBounds((localDimension.width - 576) / 2, (localDimension.height - 489) / 2, 576, 489);

		b1.addActionListener(this);
		b2.addActionListener(this);
		b3.addActionListener(this);
		b4.addActionListener(this);
	}

	private JPanel jPanel2;
	private JPanel jPanel3;
	private javax.swing.JScrollPane jScrollPane1;
	private JTable tb1;

	public void showRecord() {
		model.setNumRows(0);
		try {
			rs = stat.executeQuery("select * from HRLEAVE_Type");
			while (rs.next()) {
				String str1 = new String(rs.getString(1));
				String str2 = new String(rs.getString(2));
				String str3 = new String(rs.getString(3));

				model.addRow(new Object[] { str1, str2, str3 });
			}
		} catch (Exception localException) {
			javax.swing.JOptionPane.showMessageDialog(this, qr.getMessage());
		}
	}

	private DefaultTableModel model;
	private JTextField tf1;
	private JTextField tf2;
	private JTextField tf3;

	public void actionPerformed(ActionEvent paramActionEvent) {
		if (paramActionEvent.getSource() == b1) {
			qr.Query("insert into HRLEAVE_TYPE values('" + tf1.getText() + "','" + tf2.getText() + "','"
					+ tf3.getText() + "') ");

			if (qr.getMessage().equalsIgnoreCase("Succeed")) {
				model.addRow(new Object[] { tf1.getText(), tf2.getText(), tf3.getText() });
			} else {
				javax.swing.JOptionPane.showMessageDialog(this, qr.getMessage());
			}
		}
		if (paramActionEvent.getSource() == b2) {
			qr.Query("update HRLEAVE_type set max='" + tf2.getText() + "' , penalty='" + tf3.getText()
					+ "' where leave_type='" + tf2.getText() + "' ");

			if (qr.getMessage().equalsIgnoreCase("Succeed")) {
				model.addRow(new Object[] { tf1.getText(), tf2.getText(), tf3.getText() });
			} else {
				javax.swing.JOptionPane.showMessageDialog(this, qr.getMessage());
			}
		}

		if (paramActionEvent.getSource() == b4) {
			showRecord();
		}
		if (paramActionEvent.getSource() == b3) {
			setVisible(false);
		}
	}
}
