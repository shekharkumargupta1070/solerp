package com.sol.erp;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.sol.erp.util.DBConnectionUtil;

public class ItemCode implements ActionListener, MouseListener, KeyListener {

	private Estimation estimation;

	public ItemCode(Estimation estimation) {
		this.estimation = estimation;
	}

	String[] columnNames = { "Item Code", "Item Name" };
	Object[][] data = new Object[0][];

	DefaultTableModel itemDefaultTableModel = new DefaultTableModel(data, columnNames);
	JTable itemTable = new JTable(itemDefaultTableModel) {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public boolean isCellEditable(int paramAnonymousInt1, int paramAnonymousInt2) {
			convertColumnIndexToModel(paramAnonymousInt2);
			return paramAnonymousInt2 < 0;
		}
	};
	JScrollPane scrollPane = new JScrollPane(itemTable);

	JInternalFrame f = new JInternalFrame("Create Item Code", true, true, true, true);
	JLabel labelItemCode = new JLabel("Item Code");
	JLabel labelItemName = new JLabel("Item Name");
	JLabel labelSearch = new JLabel("Search");

	JTextField textFieldItemCode = new JTextField();
	JTextField textFieldItemName = new JTextField();
	JTextField textFieldSearch = new JTextField(30);

	JButton savebut = new JButton("Save ITEM");
	JButton updatebut = new JButton("Update");
	JButton showbut = new JButton("Show All");
	JButton nextbut = new JButton("Next >>");
	JButton removebut = new JButton("Remove");
	JButton closebut = new JButton("Close", new javax.swing.ImageIcon("image/close.gif"));

	JPanel northp1 = new JPanel();
	JPanel northp2 = new JPanel();
	JPanel northp3 = new JPanel();

	JPanel butpanel1 = new JPanel();
	JPanel butpanel2 = new JPanel();

	JPanel centerpanel = new JPanel();
	JPanel northpanel = new JPanel();

	Font fo = new Font("Verdana", 1, 9);
	javax.swing.border.Border line = javax.swing.BorderFactory.createLineBorder(Color.black);
	javax.swing.border.Border bor1 = javax.swing.BorderFactory.createTitledBorder(line, "Item Code Details", 0, 0, fo,
			Color.darkGray);
	javax.swing.border.Border bor2 = javax.swing.BorderFactory.createTitledBorder(line, "List of Items", 0, 2, fo,
			Color.darkGray);

	java.awt.Toolkit tk = java.awt.Toolkit.getDefaultToolkit();
	Dimension ss = tk.getScreenSize();
	Dimension fs = f.getSize();

	class Renderer extends javax.swing.table.DefaultTableCellRenderer {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		Renderer() {
		}

		public java.awt.Component getTableCellRendererComponent(JTable paramJTable, Object paramObject,
				boolean paramBoolean1, boolean paramBoolean2, int paramInt1, int paramInt2) {
			setEnabled((paramJTable == null) || (paramJTable.isEnabled()));

			if (paramInt2 == 0) {
				setHorizontalAlignment(0);
			}
			if (paramInt2 == 1) {
				setHorizontalAlignment(2);
			}

			if (paramInt1 % 2 == 0) {
				setBackground(new Color(240, 240, 240));
				setForeground(Color.darkGray);
			} else {
				setBackground(new Color(250, 250, 250));
				setForeground(Color.darkGray);
			}
			super.getTableCellRendererComponent(paramJTable, paramObject, paramBoolean1, paramBoolean2, paramInt1,
					paramInt2);

			return this;
		}
	}

	ItemCode.Renderer rnd = new ItemCode.Renderer();

	java.awt.Container c;

	public void designFrame() {
		c = f.getContentPane();
		c.setLayout(new java.awt.BorderLayout());
		northp1.setLayout(new java.awt.FlowLayout());
		northp2.setLayout(new java.awt.GridLayout(2, 2, 2, 2));
		northpanel.setLayout(new java.awt.BorderLayout());
		butpanel1.setLayout(new java.awt.FlowLayout(2));
		butpanel2.setLayout(new java.awt.FlowLayout(0));

		centerpanel.setLayout(new java.awt.BorderLayout());

		northpanel.setBorder(bor1);
		scrollPane.setBorder(bor2);

		northp2.setPreferredSize(new Dimension(50, 50));
		northp2.add(labelItemCode);
		northp2.add(textFieldItemCode);
		northp2.add(labelItemName);
		northp2.add(textFieldItemName);

		butpanel1.add(savebut);
		savebut.setMnemonic(83);
		butpanel1.add(showbut);
		showbut.setMnemonic(87);
		butpanel1.add(removebut);
		removebut.setMnemonic(82);

		butpanel2.add(labelSearch);
		butpanel2.add(textFieldSearch);
		butpanel2.add(closebut);
		textFieldSearch.setFont(new Font("Verdana", 0, 9));
		labelSearch.setFont(new Font("Verdana", 0, 9));

		butpanel2.setPreferredSize(new Dimension(50, 50));
		textFieldSearch.setPreferredSize(new Dimension(22, 22));

		textFieldItemCode.setFont(new Font("Verdana", 0, 9));
		textFieldItemName.setFont(new Font("Verdana", 0, 9));

		itemTable.setRowHeight(20);
		itemTable.setFont(new Font("Verdana", 0, 9));
		itemTable.getTableHeader().setPreferredSize(new Dimension(50, 25));
		itemTable.getTableHeader().setFont(fo);
		itemTable.setGridColor(Color.white);
		itemTable.setIntercellSpacing(new Dimension(1, 1));

		itemTable.getColumnModel().getColumn(0).setPreferredWidth(50);
		itemTable.getColumnModel().getColumn(1).setPreferredWidth(320);

		itemTable.getColumnModel().getColumn(0).setCellRenderer(rnd);
		itemTable.getColumnModel().getColumn(1).setCellRenderer(rnd);

		northpanel.add(northp2, "North");

		centerpanel.add(butpanel1, "North");
		centerpanel.add(scrollPane, "Center");
		centerpanel.add(butpanel2, "South");

		c.add(northpanel, "North");
		c.add(centerpanel, "Center");

		savebut.addActionListener(this);
		showbut.addActionListener(this);
		updatebut.addActionListener(this);
		nextbut.addActionListener(this);
		removebut.addActionListener(this);
		closebut.addActionListener(this);

		textFieldItemCode.addActionListener(this);
		textFieldItemName.addActionListener(this);
		textFieldSearch.addActionListener(this);

		textFieldItemCode.addKeyListener(this);
		textFieldItemName.addKeyListener(this);
		textFieldSearch.addKeyListener(this);
		savebut.addKeyListener(this);
		removebut.addKeyListener(this);
		showbut.addKeyListener(this);
		closebut.addKeyListener(this);
		itemTable.addKeyListener(this);
		itemTable.addMouseListener(this);

		f.setLocation((ss.width - fs.width) / 4, (ss.height - fs.height) / 10);

		f.setSize(480, 500);
		f.setVisible(true);
		textFieldSearch.requestFocus();
	}

	public void showAll() {
		itemDefaultTableModel.setNumRows(0);
		String str1 = textFieldSearch.getText() + "%";
		Connection con = null;
		Statement stat = null;
		ResultSet rs = null;
		try {
			con = DBConnectionUtil.getConnection();
			stat = con.createStatement();
			rs = stat.executeQuery("select * from ItemCode where item_code like '" + str1 + "' or item_name like '"
					+ str1 + "' order by item_code");
			while (rs.next()) {
				String str2 = new String(rs.getString(1));
				String str3 = new String(rs.getString(2));

				itemDefaultTableModel.addRow(new Object[] { str2, str3 });
			}
		} catch (Exception localException) {
			System.out.println(localException);
		} finally {
			DBConnectionUtil.closeConnection(rs, stat, con);
		}
	}

	public void saveRecord() {
		Connection con = null;
		Statement stat = null;
		try {
			con = DBConnectionUtil.getConnection();
			stat = con.createStatement();
			int affected = stat.executeUpdate("insert into ItemCode values('" + textFieldItemCode.getText() + "','"
					+ textFieldItemName.getText() + "')  ");

			if (affected > 0) {
				itemDefaultTableModel.addRow(new Object[] { textFieldItemCode.getText(), textFieldItemName.getText() });
				textFieldItemCode.setText("");
				textFieldItemName.setText("");
				textFieldItemCode.requestFocus();
			}
		} catch (Exception localException) {
			if (localException.getMessage().toString().equalsIgnoreCase("General Error")) {
				javax.swing.JOptionPane.showMessageDialog(f, "Item Code Allready Exists.");
			} else {
				javax.swing.JOptionPane.showMessageDialog(f, localException.getMessage().toString());
			}
		} finally {
			DBConnectionUtil.closeConnection(stat, con);
		}
	}

	public void deleteRecord() {
		int i = itemTable.getSelectedRow();
		String str = String.valueOf(itemDefaultTableModel.getValueAt(i, 1));

		Connection con = null;
		Statement stat = null;
		try {
			con = DBConnectionUtil.getConnection();
			stat = con.createStatement();
			int j = stat.executeUpdate("delete from ItemCode where ITEM_NAME = '" + str + "'  ");
			if (j > 0) {
				javax.swing.JOptionPane.showMessageDialog(f, "OK. Item Deleted.");
				showAll();
			}
		} catch (Exception localException) {
			javax.swing.JOptionPane.showMessageDialog(f, localException.getMessage().toString());
		} finally {
			DBConnectionUtil.closeConnection(stat, con);
		}
	}

	public String getItemCode() {
		int i = itemTable.getSelectedRow();
		return String.valueOf(itemDefaultTableModel.getValueAt(i, 0));
	}

	public String getItemName() {
		int i = itemTable.getSelectedRow();
		return String.valueOf(itemDefaultTableModel.getValueAt(i, 1));
	}

	public void selectedRecord() {
		int i = itemTable.getSelectedRow();
		String codestr = String.valueOf(itemDefaultTableModel.getValueAt(i, 0));
		String namestr = String.valueOf(itemDefaultTableModel.getValueAt(i, 1));

		textFieldItemCode.setText(codestr);
		textFieldItemName.setText(namestr);
		textFieldItemName.requestFocus();
		textFieldItemName.selectAll();

		if (this.estimation != null) {
			this.estimation.comboBoxItemCode.setSelectedItem(codestr);
			this.estimation.textFieldItemName.setText(namestr);
		}
	}

	public void mousePressed(MouseEvent paramMouseEvent) {
	}

	public void mouseReleased(MouseEvent paramMouseEvent) {
		// selectedRecord();
	}

	public void mouseClicked(MouseEvent paramMouseEvent) {
		if (paramMouseEvent.getClickCount() == 2) {
			selectedRecord();
			f.setVisible(false);
		}
	}

	public void mouseEntered(MouseEvent paramMouseEvent) {
	}

	public void mouseExited(MouseEvent paramMouseEvent) {
	}

	public void keyPressed(KeyEvent paramKeyEvent) {
	}

	public void keyReleased(KeyEvent paramKeyEvent) {
		if (paramKeyEvent.getSource() == textFieldSearch) {
			showAll();
		}
	}

	public void keyTyped(KeyEvent paramKeyEvent) {
	}

	public void actionPerformed(java.awt.event.ActionEvent paramActionEvent) {
		if (paramActionEvent.getSource() == textFieldItemCode) {
			textFieldItemName.requestFocus();
		}
		if (paramActionEvent.getSource() == savebut) {
			saveRecord();
		}
		if (paramActionEvent.getSource() == showbut) {
			showAll();
		}
		if (paramActionEvent.getSource() == removebut) {
			deleteRecord();
		}
		if (paramActionEvent.getSource() == closebut) {
			f.setVisible(false);
		}
	}

}
