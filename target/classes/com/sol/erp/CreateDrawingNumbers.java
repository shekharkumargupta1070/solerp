package com.sol.erp;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import com.sol.erp.constants.ApplicationConstants;
import com.sol.erp.util.DBConnectionUtil;
import com.sol.erp.util.UIUtil;

public class CreateDrawingNumbers implements ActionListener, KeyListener, MouseListener {

	JInternalFrame internalFrame = new JInternalFrame("Create Drawing No", true, true, true, true);
	JLabel labelNumberFormat = new JLabel("Number Format");
	JLabel labelProjectNumber = new JLabel("Project No");
	JLabel labelProjectName = new JLabel("Project Name");
	JLabel labelClientName = new JLabel("Client Name");
	JLabel labelItemCode = new JLabel("Item Code");
	JLabel labelRemarks = new JLabel("Remarks");
	JLabel labelItemQty = new JLabel("Item Qty");
	JLabel labelSheetQty = new JLabel("Sheet Qty");
	JLabel labelDetailingTime = new JLabel("Detailing Time");
	JLabel labelCheckingTime = new JLabel("Checking Time");
	JLabel labelEstimatedTime = new JLabel("Estimated Time");
	JLabel labelNumberOfDrawing = new JLabel("Number Drg");
	JLabel labelDrawingInitial = new JLabel("Char Drg");
	JLabel labelTotalSheet = new JLabel("Total Sheet");

	JComboBox comboBoxNumberFormat = new JComboBox();
	JComboBox comboBoxProjectNumber = new JComboBox();
	JTextField textFieldProjectName = new JTextField();
	JTextField textFieldClientName = new JTextField();
	JTextField textFieldItemCode = new JTextField();
	JTextField textFieldRemarks = new JTextField();
	JTextField textFieldItemQty = new JTextField();
	JTextField textFieldSheetQty = new JTextField();
	JTextField textFieldDetailingTime = new JTextField();
	JTextField textFieldCheckingTime = new JTextField();
	JTextField textFieldEstimatedTime = new JTextField();
	JTextField textFieldNumberOfDrawing = new JTextField(5);
	JTextField textFieldDrawingInitial = new JTextField(5);
	JTextField textFieldTotalSheet = new JTextField();

	JButton buttonShow = new JButton("Show Max Drawing No");
	JButton buttonSave = new JButton("Save Drawing No");
	JButton buttonUpdate = new JButton("Update");
	JButton buttonNext = new JButton("Next >>");
	JButton buttonRemove = new JButton("Remove From List");
	JButton buttonClose = new JButton("Close X");

	JPopupMenu drawingTableContextMenu = new JPopupMenu();
	JMenuItem removeMenuItem = new JMenuItem("Remove", new ImageIcon("image/close.gif"));
	JMenuItem removeFromEstimationMenuItem = new JMenuItem("Remove from Estimation", new ImageIcon("image/close.gif"));

	JPopupMenu itemTableContextMenu = new JPopupMenu();
	JMenuItem makeItFrameMenuItem = new JMenuItem("Make It Fresh", new ImageIcon("image/ok.gif"));

	JPanel formPanel = new JPanel();
	JPanel buttonPanel = new JPanel();

	JPanel centerPanel = new JPanel();
	JPanel northPanel = new JPanel();
	JPanel mainPanel = new JPanel();

	Container container;

	String[] itemColumns = { "Item Code", "Remarks", "Remaing", "Count", "Range" };
	Object[][] itemData = new Object[0][];

	String[] drawingColumns = { "DRG NO", "Status" };
	Object[][] drawingData = new Object[0][];

	DefaultTableModel itemTableModel = new DefaultTableModel(itemData, itemColumns);
	JTable itemTable = new JTable(itemTableModel);
	JScrollPane itemScrollPane = new JScrollPane(itemTable);

	DefaultTableModel drawingTableModel = new DefaultTableModel(drawingData, drawingColumns);
	JTable drawingTable = new JTable(drawingTableModel);
	JScrollPane drawingScrollPane = new JScrollPane(drawingTable);

	JSplitPane splitPanel = new JSplitPane(1, itemScrollPane, formPanel);
	GridLayout formGridLayout = new GridLayout(12, 2);

	/**
	 * Screen Designing
	 */
	public void designFrame() {
		// Setting up root container
		container = internalFrame.getContentPane();
		container.setLayout(new BorderLayout());

		northPanel.setLayout(new BorderLayout());
		buttonPanel.setLayout(new FlowLayout());
		centerPanel.setLayout(new BorderLayout());

		formPanel.setLayout(formGridLayout);

		comboBoxProjectNumber.setEditable(true);
		textFieldProjectName.setEditable(false);
		textFieldClientName.setEditable(false);
		textFieldItemCode.setEditable(false);
		textFieldRemarks.setEditable(false);
		textFieldItemQty.setEditable(false);
		textFieldSheetQty.setEditable(false);
		textFieldDetailingTime.setEditable(false);
		textFieldCheckingTime.setEditable(false);
		textFieldEstimatedTime.setEditable(false);
		textFieldTotalSheet.setEditable(false);

		comboBoxNumberFormat.addItem(ApplicationConstants.DIGIT_1);
		comboBoxNumberFormat.addItem(ApplicationConstants.DIGIT_2);
		comboBoxNumberFormat.addItem(ApplicationConstants.DIGIT_3);
		comboBoxNumberFormat.addItem(ApplicationConstants.DIGIT_4);

		drawingTableContextMenu.add(removeMenuItem);
		drawingTableContextMenu.add(removeFromEstimationMenuItem);

		itemTableContextMenu.add(makeItFrameMenuItem);

		formPanel.add(labelProjectNumber);
		formPanel.add(comboBoxProjectNumber);
		formPanel.add(labelProjectName);
		formPanel.add(textFieldProjectName);
		formPanel.add(labelClientName);
		formPanel.add(textFieldClientName);
		formPanel.add(labelItemCode);
		formPanel.add(textFieldItemCode);
		formPanel.add(labelRemarks);
		formPanel.add(textFieldRemarks);
		formPanel.add(labelItemQty);
		formPanel.add(textFieldItemQty);
		formPanel.add(labelSheetQty);
		formPanel.add(textFieldSheetQty);
		formPanel.add(labelDetailingTime);
		formPanel.add(textFieldDetailingTime);
		formPanel.add(labelCheckingTime);
		formPanel.add(textFieldCheckingTime);
		formPanel.add(labelEstimatedTime);
		formPanel.add(textFieldEstimatedTime);
		formPanel.add(labelTotalSheet);
		formPanel.add(textFieldTotalSheet);

		buttonPanel.add(labelNumberFormat);
		buttonPanel.add(comboBoxNumberFormat);
		buttonPanel.add(labelDrawingInitial);
		buttonPanel.add(textFieldDrawingInitial);
		buttonPanel.add(labelNumberOfDrawing);
		buttonPanel.add(textFieldNumberOfDrawing);
		buttonPanel.add(buttonSave);
		buttonSave.setMnemonic(83);
		buttonPanel.add(buttonClose);
		buttonClose.setMnemonic(67);

		centerPanel.add(splitPanel, "Center");
		centerPanel.add(drawingScrollPane, "East");

		container.add(northPanel, "North");
		container.add(centerPanel, "Center");
		container.add(buttonPanel, "South");

		drawingScrollPane.setPreferredSize(new Dimension(160, 160));

		buttonShow.addActionListener(this);
		buttonSave.addActionListener(this);
		buttonUpdate.addActionListener(this);
		buttonNext.addActionListener(this);
		buttonRemove.addActionListener(this);
		buttonClose.addActionListener(this);

		removeMenuItem.addActionListener(this);
		removeFromEstimationMenuItem.addActionListener(this);

		makeItFrameMenuItem.addActionListener(this);

		comboBoxProjectNumber.addActionListener(this);
		textFieldNumberOfDrawing.addActionListener(this);
		itemTable.addKeyListener(this);
		itemTable.addMouseListener(this);
		drawingTable.addMouseListener(this);

		splitPanel.setDividerLocation(530);
		internalFrame.setSize(1000, 450);
		internalFrame.setResizable(true);
		internalFrame.setVisible(true);
		UIUtil.centreToApplication(internalFrame);
		UIUtil.customTableDecoration(drawingTable);
		UIUtil.customTableDecoration(itemTable);

		comboBoxProjectNumber.requestFocus();
	}

	String empstr = "";

	public void paramUser() {
		String str = new String(TechMainFrame.textFieldPost.getText());
		empstr = TechMainFrame.textFieldLoggedBy.getText();

		if (str.equalsIgnoreCase("Team Leader")) {
			projectList();
		}
	}

	public JPanel getCenterPanel() {
		designFrame();
		return centerPanel;
	}

	public void projectList() {
		comboBoxProjectNumber.removeAllItems();
		Connection con = null;
		Statement stat = null;
		ResultSet rs = null;
		try {
			con = DBConnectionUtil.getConnection();
			stat = con.createStatement();
			rs = stat.executeQuery("select project_no from project_co where co_code='" + empstr + "' ");

			while (rs.next()) {
				String str = new String(rs.getString(1));
				comboBoxProjectNumber.addItem(String.valueOf(str));
			}
		} catch (Exception localException) {
			System.out.println(localException);
		} finally {
			DBConnectionUtil.closeConnection(rs, stat, con);
		}
	}

	public void showDetails() {
		int i = itemTable.getSelectedRow();
		String str1 = String.valueOf(itemTableModel.getValueAt(i, 0));
		String str2 = String.valueOf(itemTableModel.getValueAt(i, 1));

		Connection con = null;
		Statement stat = null;
		ResultSet rs = null;

		try {
			con = DBConnectionUtil.getConnection();
			stat = con.createStatement();
			rs = stat.executeQuery(
					"select item_qty,remaining,dtl_time,chk_time,persheet_time,sheet_qty from Estimation where project_no='"
							+ comboBoxProjectNumber.getSelectedItem() + "' and item_code='" + str1 + "' ");
			while (rs.next()) {
				String str3 = new String(rs.getString(1));
				String str4 = new String(rs.getString(2));
				String str5 = new String(rs.getString(3));
				String str6 = new String(rs.getString(4));
				String str7 = new String(rs.getString(5));
				String str8 = new String(rs.getString(6));

				textFieldItemCode.setText(str1);
				textFieldRemarks.setText(str2);
				textFieldItemQty.setText(str3);
				textFieldSheetQty.setText(str4);
				textFieldDetailingTime.setText(str5);
				textFieldCheckingTime.setText(str6);
				textFieldEstimatedTime.setText(str7);
				textFieldTotalSheet.setText(str8);
				textFieldNumberOfDrawing.requestFocus();
			}
		} catch (Exception localException) {
			System.out.println(localException);
		} finally {
			DBConnectionUtil.closeConnection(rs, stat, con);
		}
	}

	int createddrg = 0;

	public void drgList() {
		drawingTableModel.setNumRows(0);
		int i = itemTable.getSelectedRow();
		String str1 = String.valueOf(itemTableModel.getValueAt(i, 0));

		Connection con = null;
		Statement stat = null;
		ResultSet rs = null;

		try {
			con = DBConnectionUtil.getConnection();
			stat = con.createStatement();
			rs = stat.executeQuery(
					"select drawing_no, DETAILING, CHECKING, count(drawing_no) from drawingno where project_no='"
							+ comboBoxProjectNumber.getSelectedItem() + "' and item_code='" + str1
							+ "' GROUP BY DRAWING_NO, DETAILING, CHECKING order by drawing_no");
			while (rs.next()) {
				String str2 = new String(rs.getString(1));
				String str3 = new String(rs.getString(2));
				String str4 = new String(rs.getString(3));
				String str5 = new String(rs.getString(4));
				drawingTableModel.addRow(new Object[] { str2, str3 + "/" + str4 });
				createddrg = Integer.parseInt(str5);
			}
		} catch (Exception localException) {
			System.out.println(localException);
		} finally {
			DBConnectionUtil.closeConnection(rs, stat, con);
		}
	}

	public void maxDrg() {
		Connection con = null;
		Statement stat = null;
		ResultSet rs = null;

		try {
			con = DBConnectionUtil.getConnection();
			for (int row = 0; row < itemTable.getRowCount(); row++) {
				String str1 = String.valueOf(itemTableModel.getValueAt(row, 0));

				stat = con.createStatement();
				rs = stat.executeQuery(
						"select  count(Drawing_no), min(Drawing_no), max(Drawing_no) from Drawingno where project_no='"
								+ comboBoxProjectNumber.getSelectedItem() + "' and item_code='" + str1 + "'  ");
				rs.getMetaData().getColumnCount();
				while (rs.next()) {
					String str2 = new String(rs.getString(1));
					String str3 = new String(rs.getString(2));
					String str4 = new String(rs.getString(3));

					itemTableModel.setValueAt(str2, row, 3);
					itemTableModel.setValueAt(str3 + "-" + str4, row, 4);
				}

			}
		} catch (Exception localException) {
			System.out.println(localException);
		} finally {
			DBConnectionUtil.closeConnection(rs, stat, con);
		}
		textFieldNumberOfDrawing.requestFocus();
	}

	public void removeDrg() {
		int selectedItemRow = itemTable.getSelectedRow();
		String itemCode = String.valueOf(itemTableModel.getValueAt(selectedItemRow, 0));
		String remainingCount = String.valueOf(itemTableModel.getValueAt(selectedItemRow, 2));

		int[] selectedDrawingRows = drawingTable.getSelectedRows();
		int affected = 0;

		Connection con = null;
		Statement stat = null;

		try {
			con = DBConnectionUtil.getConnection();
			stat = con.createStatement();
			for (int row = 0; row < selectedDrawingRows.length; row++) {
				String drawingNumber = String.valueOf(drawingTableModel.getValueAt(selectedDrawingRows[row], 0));
				String status = String.valueOf(drawingTableModel.getValueAt(selectedDrawingRows[row], 1));

				if (status.length() >= 4) {
					JOptionPane.showMessageDialog(internalFrame,
							"Drawing is alloted to " + status + ". So You Cannot Remove It.");
				} else {
					affected = stat.executeUpdate(
							"delete from drawingno where project_no='" + comboBoxProjectNumber.getSelectedItem()
									+ "' and item_code='" + itemCode + "' and drawing_no='" + drawingNumber + "'  ");

					if (affected > 0) {
						drawingTableModel.removeRow(selectedDrawingRows[row]);
						int k = Integer.parseInt(remainingCount) + 1;
						String str5 = String.valueOf(k);
						
						affected = stat.executeUpdate("update estimation set remarks='Remaining', REMAINING='" + str5
								+ "' WHERE project_no='" + comboBoxProjectNumber.getSelectedItem() + "' and item_code='"
								+ itemCode + "' ");
						if (affected > 0) {
							itemTableModel.setValueAt("Remaining", selectedItemRow, 1);
							itemTableModel.setValueAt(str5, selectedItemRow, 2);
						}

					} else {
						JOptionPane.showMessageDialog(internalFrame, "Possibly Drawing is Assigned to SomeOne.");
					}
				}
			}
		} catch (Exception localException1) {
			JOptionPane.showMessageDialog(internalFrame, localException1.getMessage().toString());
		} finally {
			DBConnectionUtil.closeConnection(stat, con);
		}
	}

	public void removeFromEstimation() {
		int selectedItemRow = itemTable.getSelectedRow();
		String itemCode = String.valueOf(itemTableModel.getValueAt(selectedItemRow, 0));
		int remainingItemCount = Integer.parseInt((String) itemTableModel.getValueAt(selectedItemRow, 2));

		Connection con = null;
		Statement stat = null;
		int affected = 0;
		int[] selectedDrawingRows = drawingTable.getSelectedRows();

		try {
			con = DBConnectionUtil.getConnection();
			stat = con.createStatement();
			for (int row = 0; row < selectedDrawingRows.length; row++) {
				String drawingNumber = String.valueOf(drawingTableModel.getValueAt(selectedDrawingRows[row], 0));
				String status = String.valueOf(drawingTableModel.getValueAt(selectedDrawingRows[row], 1));

				if (status.length() >= 4) {
					JOptionPane.showMessageDialog(internalFrame,
							"Drawing is alloted to " + status + ". So You Cannot Remove It.");
				} else {
					int totalSheetCount = Integer.parseInt(textFieldTotalSheet.getText());
					affected = stat.executeUpdate("delete from drawingno where project_no='"
							+ comboBoxProjectNumber.getSelectedItem() + "' and item_code='" + itemCode
							+ "' and drawing_no='" + drawingNumber + "' and status='Fresh'  ");

					if (affected > 0) {
						drawingTableModel.removeRow(selectedDrawingRows[row]);
						totalSheetCount -= 1;
						textFieldTotalSheet.setText(String.valueOf(totalSheetCount));

						affected = stat.executeUpdate("update estimation set remarks='Remaining', REMAINING='"
								+ String.valueOf(remainingItemCount) + "', sheet_Qty='" + textFieldTotalSheet.getText()
								+ "' WHERE project_no='" + comboBoxProjectNumber.getSelectedItem() + "' and item_code='"
								+ itemCode + "' ");

						if (affected > 0) {
							itemTableModel.setValueAt("Remaining", selectedItemRow, 1);
							itemTableModel.setValueAt(String.valueOf(remainingItemCount), selectedItemRow, 2);
						}

					} else {
						JOptionPane.showMessageDialog(internalFrame, "Possibly Drawing is Assigned to SomeOne.");
					}
				}
			}
		} catch (SQLException localException2) {
			JOptionPane.showMessageDialog(internalFrame, localException2.getMessage().toString());
		} catch (Exception ex) {
			System.out.println(ex);
		} finally {
			DBConnectionUtil.closeConnection(stat, con);
		}

	}

	public void makeItFresh() {
		int[] selectedItemRows = itemTable.getSelectedRows();

		Connection con = null;
		Statement stat = null;
		ResultSet rs = null;
		int affected = 0;

		try {
			con = DBConnectionUtil.getConnection();
			for (int i = 0; i < selectedItemRows.length; i++) {
				String str1 = String.valueOf(itemTableModel.getValueAt(selectedItemRows[i], 0));

				stat = con.createStatement();
				affected = stat.executeUpdate("delete from drawingno where project_no='"
						+ comboBoxProjectNumber.getSelectedItem() + "' and item_code='" + str1 + "' ");
				if (affected > 0) {
					drawingTableModel.setNumRows(0);
					String str2 = "0";

					rs = stat.executeQuery("select sheet_qty from estimation WHERE project_no='"
							+ comboBoxProjectNumber.getSelectedItem() + "' and item_code='" + str1 + "' ");
					while (rs.next()) {
						str2 = new String(rs.getString(1));
					}

					try {
						affected = stat.executeUpdate(
								"update estimation set remarks='Ready', REMAINING = '" + str2 + "' WHERE project_no='"
										+ comboBoxProjectNumber.getSelectedItem() + "' and item_code='" + str1 + "' ");
						if (affected > 0) {
							itemTableModel.setValueAt("Ready", selectedItemRows[i], 1);
							itemTableModel.setValueAt(str2, selectedItemRows[i], 2);
							itemTableModel.setValueAt(" ", selectedItemRows[i], 3);
						}
					} catch (Exception localException2) {
						JOptionPane.showMessageDialog(internalFrame, localException2.getMessage().toString());
					}
				}
			}
		} catch (Exception localException1) {
			JOptionPane.showMessageDialog(internalFrame, localException1.getMessage().toString());
		} finally {
			DBConnectionUtil.closeConnection(rs, stat, con);
		}
	}

	public void itemList(String paramString) {
		String str1 = "%" + paramString;

		itemTableModel.setNumRows(0);

		Connection con = null;
		Statement stat = null;
		ResultSet rs = null;

		try {
			con = DBConnectionUtil.getConnection();
			stat = con.createStatement();
			rs = stat.executeQuery(
					"select project_name, client_name, item_code,remarks,remaining from Estimation where project_no='"
							+ comboBoxProjectNumber.getSelectedItem() + "' and item_code like '" + str1 + "'  ");
			rs.getMetaData().getColumnCount();
			while (rs.next()) {
				String str2 = new String(rs.getString(1));
				String str3 = new String(rs.getString(2));
				String str4 = new String(rs.getString(3));
				String str5 = new String(rs.getString(4));
				String str6 = new String(rs.getString(5));

				textFieldProjectName.setText(str2);
				textFieldClientName.setText(str3);

				itemTableModel.addRow(new Object[] { str4, str5, str6 });

				itemTable.requestFocus();
			}
		} catch (Exception localException) {
			System.out.println(localException);
		} finally {
			DBConnectionUtil.closeConnection(rs, stat, con);
		}
		maxDrg();
	}

	public void actionPerformed(ActionEvent paramActionEvent) {
		if (paramActionEvent.getSource() == comboBoxProjectNumber) {
			itemList("");
		}

		if ((paramActionEvent.getSource() == textFieldNumberOfDrawing)
				|| (paramActionEvent.getSource() == buttonSave)) {
			DecimalFormat selectedNumberFormat = new java.text.DecimalFormat(
					String.valueOf(comboBoxNumberFormat.getSelectedItem()));

			int i = itemTable.getSelectedRow();

			String str1 = textFieldDrawingInitial.getText();
			String str2 = textFieldNumberOfDrawing.getText();
			int j = Integer.parseInt(str2);

			String str3 = str1 + "" + String.valueOf(selectedNumberFormat.format(j));

			int k = Integer.parseInt(textFieldSheetQty.getText());

			if (k < 1) {
				JOptionPane.showMessageDialog(internalFrame, "Sheet Qauntity is Zero. Can Not Accept ");
				maxDrg();
			}

			if (k >= 1) {
				int m = Integer.parseInt(String.valueOf(textFieldTotalSheet.getText()));
				if (createddrg >= m) {
					JOptionPane.showMessageDialog(internalFrame, "Sheet Qauntity is Zero. Can Not Accept ");
				}

				if (createddrg <= m) {
					Connection con = null;
					Statement stat = null;
					int affected = 0;

					try {
						con = DBConnectionUtil.getConnection();
						stat = con.createStatement();
						affected = stat.executeUpdate("insert into DrawingNo values('"
								+ comboBoxProjectNumber.getSelectedItem() + "','" + textFieldProjectName.getText()
								+ "','" + textFieldClientName.getText() + "','" + textFieldItemCode.getText() + "','"
								+ textFieldRemarks.getText() + "','" + textFieldDetailingTime.getText() + "','"
								+ textFieldCheckingTime.getText() + "','" + textFieldEstimatedTime.getText() + "','"
								+ str3 + "','Fresh','0','0') ");

						String str4 = "SK";
						if (affected > 0) {
							createddrg += 1;
							k -= 1;
							textFieldSheetQty.setText(String.valueOf(k));

							j += 1;

							textFieldNumberOfDrawing.setText(String.valueOf(selectedNumberFormat.format(j)));
							itemTableModel.setValueAt(String.valueOf(textFieldSheetQty.getText()), i, 2);
							drawingTableModel.addRow(new Object[] {
									String.valueOf(
											textFieldDrawingInitial.getText() + selectedNumberFormat.format(j - 1)),
									"0/0" });

							str4 = "Ready";
							if (k > 0) {
								str4 = "Remaining";
							}
							if (k == 0) {
								str4 = "Done";
							}
							stat.executeUpdate("update ESTIMATION set remarks='" + str4 + "', remaining='"
									+ textFieldSheetQty.getText() + "' WHERE project_no='"
									+ comboBoxProjectNumber.getSelectedItem() + "' and item_code='"
									+ textFieldItemCode.getText() + "'  ");
						}
					} catch (Exception localException) {
						if (localException.getMessage().toString().equalsIgnoreCase("General Error")) {
							JOptionPane.showMessageDialog(internalFrame,
									"This Drawing No is Allready created for This Project.");
						} else {
							JOptionPane.showMessageDialog(internalFrame, localException.getMessage().toString());
						}
					} finally {
						DBConnectionUtil.closeConnection(stat, con);
					}
				}
			}
		}

		if (paramActionEvent.getSource() == removeMenuItem) {
			removeDrg();
		}
		if (paramActionEvent.getSource() == removeFromEstimationMenuItem) {
			removeFromEstimation();
		}

		if (paramActionEvent.getSource() == makeItFrameMenuItem) {
			makeItFresh();
		}

		if (paramActionEvent.getSource() == buttonClose) {
			internalFrame.setVisible(false);
		}
	}

	public void keyPressed(java.awt.event.KeyEvent paramKeyEvent) {
		if (paramKeyEvent.getSource() == itemTable) {
			int i = paramKeyEvent.getKeyCode();
			if (i == 10) {
				showDetails();
			}
		}
	}

	public void keyReleased(java.awt.event.KeyEvent paramKeyEvent) {
	}

	public void keyTyped(java.awt.event.KeyEvent paramKeyEvent) {
	}

	public void mousePressed(MouseEvent paramMouseEvent) {
		if (paramMouseEvent.getSource() == drawingTable) {
			if (SwingUtilities.isRightMouseButton(paramMouseEvent)) {
				drawingTableContextMenu.show((JComponent) paramMouseEvent.getSource(), paramMouseEvent.getX(),
						paramMouseEvent.getY());
			}
		}

		if (paramMouseEvent.getSource() == itemTable) {
			if (SwingUtilities.isRightMouseButton(paramMouseEvent)) {
				itemTableContextMenu.show((JComponent) paramMouseEvent.getSource(), paramMouseEvent.getX(),
						paramMouseEvent.getY());
			}
		}

		if (paramMouseEvent.getSource() == itemTable) {
			showDetails();

			drgList();
		}
	}

	public void mouseReleased(MouseEvent paramMouseEvent) {
	}

	public void mouseClicked(MouseEvent paramMouseEvent) {
	}

	public void mouseEntered(MouseEvent paramMouseEvent) {
	}

	public void mouseExited(MouseEvent paramMouseEvent) {
	}

}
