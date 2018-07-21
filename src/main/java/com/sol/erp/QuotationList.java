package com.sol.erp;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.print.PrinterException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.MessageFormat;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingWorker;
import javax.swing.table.DefaultTableModel;

import com.sol.erp.constants.ApplicationConstants;
import com.sol.erp.dao.ProjectDAO;
import com.sol.erp.dao.QuotationDAO;
import com.sol.erp.dto.QuotationDTO;
import com.sol.erp.ui.custom.SolTableModel;
import com.sol.erp.util.DBConnectionUtil;
import com.sol.erp.util.UIUtil;

public class QuotationList implements java.awt.event.ActionListener, java.awt.event.MouseListener {

	private final Logger logger = Logger.getLogger(QuotationList.class.getName());

	String[] columnNames = new String[] { "Date", "Qta No", "Project Name", "Client Name", "Quoted Hrs", "Sheet Qty",
			"Project No", "Team Leader", "Quoted Hrs", "Sheet Qty" };

	Object[][] data = new Object[0][];

	DefaultTableModel quotationTableModel = new DefaultTableModel(data, columnNames);
	JTable quotationTable = new JTable(quotationTableModel);
	JScrollPane quotationScrollPane = new javax.swing.JScrollPane(quotationTable);

	DecimalFormat df = new DecimalFormat("00.00");
	DecimalFormat df3 = new DecimalFormat("0");
	DecimalFormat df2 = new DecimalFormat("00");
	DecimalFormat df4 = new DecimalFormat("000");

	JInternalFrame internalFrame = new JInternalFrame("Quotation List", true, true, true, true);
	JMenuBar mainMenubar = new JMenuBar();
	JMenu fileMenu = new JMenu("File");
	JMenuItem refreshMenuItem = new JMenuItem("Refresh");
	JMenuItem cleanMenuItem = new JMenuItem(
			"Clean This Project Entierly. (If Project is Assigned to any Team Leader).");
	JMenuItem printMenuItem = new JMenuItem("Print");
	JMenuItem closeMenuItem = new JMenuItem("Close");

	JPopupMenu quotationTableContextMenu = new JPopupMenu();
	JMenuItem startContextMenuItem = new JMenuItem("Start Project No.",
			new ImageIcon(ClassLoader.getSystemResource(ApplicationConstants.IMAGE_PATH + "check.gif")));
	JMenuItem undoContextMenuItem = new JMenuItem("Undo Project No. (If Project is Not Assigned to any Team Leader).",
			new ImageIcon(ClassLoader.getSystemResource(ApplicationConstants.IMAGE_PATH + "close.gif")));
	JMenuItem removeContextMenuItem = new JMenuItem("Remove Quote. (If Project is Not Assigned to any Team Leader).",
			new ImageIcon(ClassLoader.getSystemResource(ApplicationConstants.IMAGE_PATH + "close.gif")));

	JLabel yearLabel = new JLabel("Enter Year: ");
	JLabel messageLabel = new JLabel("");
	JTextField yearTextField = new JTextField(8);
	JButton showButton = new JButton("Show List");

	JLabel projectNumberLabel = new JLabel("Project No");
	JTextField projectNumberTextField = new JTextField(10);

	JButton updateButton = new JButton("Update");
	JButton refreshButton = new JButton("Refresh");
	JButton removeButton = new JButton("Remove Quote");
	JButton closeButton = new JButton("Close");
	JPanel northpanel = new JPanel();
	JPanel centerpanel = new JPanel();
	JPanel butpanel = new JPanel();

	String time;
	ColoredTableCellRenderer coloredTableCellRenderer;
	SolTableModel skt;
	Container container;
	String user;

	public QuotationList() {
		coloredTableCellRenderer = new QuotationList.ColoredTableCellRenderer();
		skt = new SolTableModel();

		user = "Project Manager";
	}

	class ColoredTableCellRenderer extends javax.swing.table.DefaultTableCellRenderer {
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
				setBackground(new Color(200, 220, 240));
				setForeground(Color.darkGray);

			} else {
				setBackground(Color.white);
				setForeground(Color.darkGray);
			}

			if (paramInt2 == 1) {
				setHorizontalAlignment(0);
			}
			if (paramInt2 == 2) {
				setHorizontalAlignment(2);
			}
			if (paramInt2 == 3) {
				setHorizontalAlignment(2);
			}
			if (paramInt2 >= 4) {
				setHorizontalAlignment(4);
			}

			super.getTableCellRendererComponent(paramJTable, paramObject, paramBoolean1, paramBoolean2, paramInt1,
					paramInt2);

			return this;
		}
	}

	public void designScreen() {
		container = internalFrame.getContentPane();
		container.setLayout(new BorderLayout());
		northpanel.setLayout(new FlowLayout(0));
		butpanel.setLayout(new FlowLayout(0));
		centerpanel.setLayout(new BorderLayout());

		for (int i = 0; i < quotationTable.getColumnCount(); i++) {
			quotationTable.getColumnModel().getColumn(i).setCellRenderer(coloredTableCellRenderer);
		}

		quotationTable.getColumnModel().getColumn(2).setPreferredWidth(220);
		quotationTable.getColumnModel().getColumn(3).setPreferredWidth(95);

		quotationTable.setShowGrid(false);
		quotationTable.setFont(new Font("Tahoma", 1, 11));
		quotationTable.setRowHeight(21);
		quotationTable.getTableHeader().setFont(new Font("Arial", 1, 11));
		quotationTable.getTableHeader().setPreferredSize(new Dimension(30, 30));

		fileMenu.add(refreshMenuItem);
		fileMenu.addSeparator();
		fileMenu.add(cleanMenuItem);
		cleanMenuItem.setEnabled(false);
		fileMenu.add(printMenuItem);
		fileMenu.addSeparator();
		fileMenu.add(closeMenuItem);
		mainMenubar.add(fileMenu);

		quotationTableContextMenu.add(startContextMenuItem);
		quotationTableContextMenu.add(undoContextMenuItem);
		quotationTableContextMenu.add(removeContextMenuItem);

		northpanel.add(yearLabel);
		northpanel.add(yearTextField);
		northpanel.add(showButton);
		northpanel.add(messageLabel);

		butpanel.add(projectNumberLabel);
		butpanel.add(projectNumberTextField);
		butpanel.add(updateButton);

		container.add(northpanel, "North");
		container.add(quotationScrollPane, "Center");
		container.add(butpanel, "South");

		showButton.addActionListener(this);

		refreshMenuItem.addActionListener(this);
		cleanMenuItem.addActionListener(this);
		printMenuItem.addActionListener(this);
		closeMenuItem.addActionListener(this);

		startContextMenuItem.addActionListener(this);
		undoContextMenuItem.addActionListener(this);
		removeContextMenuItem.addActionListener(this);

		quotationTable.addMouseListener(this);

		updateButton.addActionListener(this);
		refreshButton.addActionListener(this);
		removeButton.addActionListener(this);
		closeButton.addActionListener(this);

		internalFrame.setJMenuBar(mainMenubar);
		internalFrame.setSize(1024, 550);
		internalFrame.setResizable(true);
		UIUtil.centreToApplication(internalFrame);
		
		internalFrame.setVisible(true);

		// refresh();
	}

	public void refresh() {
		showQuotationList(yearTextField.getText().trim());
	}

	public void enableDisableRemoveButton() {
		user = String.valueOf(TechMainFrame.textFieldPost.getText());

		if (user.equalsIgnoreCase("Project Manager")) {
			removeButton.setEnabled(true);
		} else {
			removeButton.setEnabled(false);
		}
	}

	public void showQuotationList(final String dateString) {
		
		SwingWorker<Boolean, Integer> worker = new SwingWorker<Boolean, Integer>(){
			
			List<QuotationDTO> quotationList = null;
			
			@Override
			protected Boolean doInBackground() throws Exception {
				messageLabel.setText("Loading... ");
				quotationTableModel.setNumRows(0);
				
				quotationList = QuotationDAO.findAllByYear(dateString);
				System.out.println("quotationList: "+quotationList.size());
				return true;
			}
			
			
			@Override
			protected void process(List<Integer> chunks) {
				super.process(chunks);
				int mostRecentValue = chunks.get(chunks.size()-1);
				messageLabel.setText("Loading: "+mostRecentValue);
			}
			
			@Override
			protected void done() {
				super.done();
				
				int counter = 0;
				for (QuotationDTO quotation : quotationList) {
					String teamLeader = ProjectDAO.getTeamLeader(quotation.getProjectNumber());
					if(teamLeader == null){
						teamLeader = "";
					}
					quotation.setTeamLeader(teamLeader);
					quotationTableModel.addRow(new Object[] { quotation.getDate(), quotation.getQuotationNumber(),
							quotation.getProjectName(), quotation.getClientName(), quotation.getTotalQuotedHrs(),
							quotation.getTotalQuotedSheets(), quotation.getProjectNumber(), quotation.getTeamLeader(),
							quotation.getActualTotalHrs(), quotation.getActualTotalSheets() });

					counter++;
				}
				
				messageLabel.setText("Total: "+counter+" records found.");
			}
		};

		worker.execute();
	}

	

	public void updateProject() {
		
		int year = Integer.parseInt(yearTextField.getText().trim());
		int selectedRow = quotationTable.getSelectedRow();
		long newProjectNumber = Long.parseLong(ProjectDAO.generateNewProjectNumber(year));
		long quotationNumber = Long.parseLong(String.valueOf(quotationTableModel.getValueAt(selectedRow, 1)));
		String teamLeader = String.valueOf(quotationTableModel.getValueAt(selectedRow, 7));

		Connection con = null;
		Statement stat = null;
		String query1 = "update ESTIMATION_MP set PROJECT_NO = '" + newProjectNumber + "' where PROJECT_NO = '"
				+ quotationNumber + "' ";
		String query2 = "update QUOTATION_LIST set PROJECT_NO = '" + newProjectNumber + "' where QUOTE_NO = '"
				+ quotationNumber + "' ";
		String query3 = "update PROJECT_CO set PROJECT_NO = '" + newProjectNumber + "' where PROJECT_NO = '"
                + quotationNumber + "' ";
		try {
			con = DBConnectionUtil.getConnection();
			con.setAutoCommit(false);
			stat = con.createStatement();
			stat.addBatch(query1);
			stat.addBatch(query2);
			stat.addBatch(query3);
			int[] recordsAffected = stat.executeBatch();
			con.commit();
			if (recordsAffected.length > 0) {
				JOptionPane.showMessageDialog(internalFrame, "Project Number Updated.");
			}
			quotationTableModel.setValueAt(String.valueOf(df4.format(newProjectNumber)), selectedRow, 6);
		} catch (Exception localException) {
			System.out.println(localException);
			JOptionPane.showMessageDialog(internalFrame, localException.getMessage().toString());
			logger.log(Level.SEVERE, localException.getMessage());
		} finally {
			DBConnectionUtil.closeConnection(stat, con);
		}
		
		ProjectDAO.setTeamLeader(newProjectNumber, teamLeader);
	}

	public void undoProject() {
		int i = quotationTable.getSelectedRow();
		String str1 = String.valueOf(quotationTableModel.getValueAt(i, 1)).trim();
		String str2 = String.valueOf(quotationTableModel.getValueAt(i, 6)).trim();

		// String.valueOf(qta);

		Connection con = null;
		Statement stat = null;

		try {
			con = DBConnectionUtil.getConnection();
			stat = con.createStatement();
			int affected = stat.executeUpdate(
					"update estimation_mp set project_no='" + str1 + "' where project_no='" + str2 + "' ");

			if (affected > 0) {
				affected = stat
						.executeUpdate("update QUOTATION_LIST set project_no='0' where QUOTE_NO='" + str1 + "' ");
			}
			if (affected > 0) {
                affected = stat
                        .executeUpdate("update PROJECT_CO set project_no='" + str1 + "' where project_no='" + str2 + "' ");

                if (affected > 0) {
                    JOptionPane.showMessageDialog(internalFrame, "Project is Fresh Now.");
                    quotationTableModel.setValueAt("0", i, 6);
                }
            }
		} catch (Exception e) {
			JOptionPane.showMessageDialog(internalFrame, e.getMessage().toString());
			logger.log(Level.SEVERE, e.getMessage());
		} finally {
			DBConnectionUtil.closeConnection(stat, con);
		}
	}

	public void checkProject() {
		int i = quotationTable.getSelectedRow();
		String projectNumber = String.valueOf(quotationTableModel.getValueAt(i, 6));

		String teamLeader = ProjectDAO.getTeamLeader(projectNumber);

		if (teamLeader == null) {
			undoProject();
		} else {
			JOptionPane.showMessageDialog(internalFrame, "Cannot Undo. Project is Still assigned to " + teamLeader);
		}
	}

	public void removeProject() {
		int i = quotationTable.getSelectedRow();
		String str1 = String.valueOf(quotationTableModel.getValueAt(i, 1));
		String str2 = String.valueOf(quotationTableModel.getValueAt(i, 6));

		Connection con = null;
		Statement stat = null;

		try {
			con = DBConnectionUtil.getConnection();
			stat = con.createStatement();
			int affected = stat.executeUpdate("Delete from ESTIMATION_MP where (PROJECT_NO = '" + str1
					+ "' OR PROJECT_NO ='" + str2 + "') and PROJECT_NO not in (select PROJECT_NO from PROJECT_CO)");
			if (affected == 0) {
				JOptionPane.showMessageDialog(internalFrame,
						"This Project is alloted to Team Leader so\nThis Project or Quotation Cannot be Deleted.");
			}

			if (affected > 0) {

				affected = stat.executeUpdate("Delete from QUOTATION_LIST where QUOTE_NO ='" + str1 + "' ");
				affected = stat.executeUpdate("Delete from PROJECT_CO where PROJECT_NO ='" + str2 + "' ");

				if (affected > 0) {
					quotationTableModel.setValueAt("XXXXX", i, 2);
					quotationTableModel.setValueAt("XXXXX", i, 3);
					quotationTableModel.setValueAt("XXXXX", i, 4);
					quotationTableModel.setValueAt("XXXXX", i, 5);
					quotationTableModel.setValueAt("00000", i, 6);
				}

				JOptionPane.showMessageDialog(internalFrame, "Quotation Deleted");
				refresh();
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(internalFrame, e.getMessage().toString());
			logger.log(Level.SEVERE, e.getMessage());
		} finally {
			DBConnectionUtil.closeConnection(stat, con);
		}
	}

	public void cleanProjectEntierly() {
		int i = quotationTable.getSelectedRow();
		String.valueOf(quotationTableModel.getValueAt(i, 1));
		String str2 = String.valueOf(quotationTableModel.getValueAt(i, 6));

		Connection con = null;
		Statement stat = null;

		try {
			con = DBConnectionUtil.getConnection();
			stat = con.createStatement();
			int affected = stat.executeUpdate("UPDATE PROJECT_CO SET CO_CODE ='0' where PROJECT_NO ='" + str2 + "' ");

			if (affected > 0) {
				ProjectDAO.setTeamLeader(Long.parseLong(str2), "0");

				affected = stat.executeUpdate("DELETE from ESTIMATION where PROJECT_NO = '" + str2 + "' ");
				if (affected > 0) {
					JOptionPane.showMessageDialog(internalFrame, "Team Leader Estimation Deleted.");
				}
				if (affected == 0) {
					JOptionPane.showMessageDialog(internalFrame,
							"<html><font color 'red'>Critical !</font>Estimation Not Deleted.");
				}

				affected = stat.executeUpdate("Delete from DRAWINGNO where PROJECT_NO ='" + str2 + "' ");
				if (affected > 0) {
					JOptionPane.showMessageDialog(internalFrame, "All Drawing Numbers are Deleted of this Project.");
				}
				if (affected == 0) {
					JOptionPane.showMessageDialog(internalFrame,
							"<html><font color 'red'>Critical !</font>Drawing Numbers Not deleted of this Project.");
				}
				affected = stat.executeUpdate("Delete from DETAILING where PROJECT_NO ='" + str2 + "' ");
				if (affected > 0) {
					JOptionPane.showMessageDialog(internalFrame, "All Detailers are removed from this Project");
				}
				if (affected == 0) {
					JOptionPane.showMessageDialog(internalFrame,
							"<html><font color 'red'>Critical !</font>Detailers Not removed from this project.");
				}
				affected = stat.executeUpdate("Delete from CHECKING where PROJECT_NO ='" + str2 + "' ");
				if (affected > 0) {
					JOptionPane.showMessageDialog(internalFrame, "All Checkers are removed from this Project.");
				}
				if (affected == 0) {
					JOptionPane.showMessageDialog(internalFrame,
							"<html><font color 'red'>Critical !</font>Checkers Not removed from this Project.");
				}
				affected = stat.executeUpdate("Delete from PROJECTMANPOWER where PROJECT_NO ='" + str2 + "' ");
				if (affected > 0) {
					JOptionPane.showMessageDialog(internalFrame,
							"All the Dates assigned to the Detailer and Checkers are removed.");
				}
				if (affected == 0) {
					JOptionPane.showMessageDialog(internalFrame,
							"<html><font color 'red'>Critical !</font>All the Dates assigned to the Detailers and Checkers are not removed.");
				}
				affected = stat.executeUpdate(
						"Update HRTIMEMASTER set PROJECT_NO = 'DELETED " + str2 + "'where PROJECT_NO ='" + str2 + "' ");
				if (affected > 0) {
					JOptionPane.showMessageDialog(internalFrame,
							"Time spent by the associates of this Project is Managed.");
				}
				if (affected == 0) {
					JOptionPane.showMessageDialog(internalFrame,
							"<html><font color 'red'>Critical !</font>Time spent by the associates of this Project is not Managed.");
				}

				if (affected > 0) {
					quotationTableModel.setValueAt("00000", i, 6);
				}
			}
		} catch (Exception localException) {
			JOptionPane.showMessageDialog(internalFrame, localException.getMessage().toString());
		}
	}

	public void updateDynamicProject() {
		int i = quotationTable.getSelectedRow();
		String quotationNumber = String.valueOf(quotationTableModel.getValueAt(i, 1));
		String oldProjectNumber = String.valueOf(quotationTableModel.getValueAt(i, 6));

		String newProjectNumber = projectNumberTextField.getText();

		Connection con = null;
		Statement stat = null;

		try {
		    String query1 = "update estimation_mp set project_no= '"+newProjectNumber+"' where project_no= '"+oldProjectNumber+"' ";
		    String query2 = "update QUOTATION_LIST set project_no= '"+newProjectNumber+"' where QUOTE_NO = '"+quotationNumber+"' ";
		    String query3 = "update PROJECT_CO set project_no= '"+newProjectNumber+"' where PROJECT_NO = '"+oldProjectNumber+"' ";
			con = DBConnectionUtil.getConnection();
			con.setAutoCommit(false);
			stat = con.createStatement();
			stat.addBatch(query1);
			stat.addBatch(query2);
            stat.addBatch(query3);
            stat.executeBatch();
            con.commit();
            quotationTableModel.setValueAt(projectNumberTextField.getText(), i, 6);
            JOptionPane.showMessageDialog(internalFrame, "Project No Updated.");
            
		} catch (Exception localException) {
			JOptionPane.showMessageDialog(internalFrame, localException.getMessage().toString());
		}
	}

	public void actionPerformed(ActionEvent paramActionEvent) {
		if (paramActionEvent.getSource() == refreshMenuItem) {
			refresh();
		}
		if (paramActionEvent.getSource() == showButton) {
			refresh();
		}
		if (paramActionEvent.getSource() == cleanMenuItem) {
			Object localObject = new Object[] { "Yes", "No! Not at All" };
			int i = JOptionPane.showOptionDialog(internalFrame,
					"Please Think Again and Again.\nDo You Really Want to Delete this project", "Delete Entierly", 0, 3,
					null, (Object[]) localObject, localObject);

			if (i == 0) {
				cleanProjectEntierly();
			}
			if (i != 1) {
			}
		}

		if (paramActionEvent.getSource() == printMenuItem) {
			MyPrint print = new MyPrint();
			print.start();
		}
		if (paramActionEvent.getSource() == closeMenuItem) {
			internalFrame.setVisible(false);
		}
		if (paramActionEvent.getSource() == startContextMenuItem) {
			updateProject();
		}
		if (paramActionEvent.getSource() == undoContextMenuItem) {
			checkProject();
		}
		if (paramActionEvent.getSource() == removeContextMenuItem) {
			removeProject();
		}
		if (paramActionEvent.getSource() == updateButton) {
			updateDynamicProject();
		}
		if (paramActionEvent.getSource() == refreshButton) {
			refresh();
		}
		if (paramActionEvent.getSource() == removeButton) {
			removeProject();
		}
		if (paramActionEvent.getSource() == closeButton) {
			internalFrame.setVisible(false);
		}
	}

	public void mousePressed(MouseEvent paramMouseEvent) {
		if (paramMouseEvent.getSource() == quotationTable) {
			if (javax.swing.SwingUtilities.isRightMouseButton(paramMouseEvent)) {
				quotationTableContextMenu.show((javax.swing.JComponent) paramMouseEvent.getSource(),
						paramMouseEvent.getX(), paramMouseEvent.getY());

				int i = quotationTable.getSelectedRow();
				String str = String.valueOf(quotationTableModel.getValueAt(i, 6));

				if ((user.equalsIgnoreCase("Project Manager")) && (str.equalsIgnoreCase("0"))) {
					startContextMenuItem.setEnabled(true);
					undoContextMenuItem.setEnabled(false);
				}
				if ((user.equalsIgnoreCase("Project Manager")) && (str.length() >= 2)) {
					startContextMenuItem.setEnabled(false);
					undoContextMenuItem.setEnabled(true);
				}
				if (user.compareToIgnoreCase("Project Manager") != 0) {
					System.out.println(user.compareToIgnoreCase("Project Manager"));
					startContextMenuItem.setEnabled(false);
					undoContextMenuItem.setEnabled(false);
				}
			}

			int i = quotationTable.getSelectedRow();
			String str = String.valueOf(quotationTableModel.getValueAt(i, 6));

			if ((user.equalsIgnoreCase("Project Manager")) && (str.equalsIgnoreCase("0"))) {
				removeButton.setEnabled(true);
			}
			if ((user.equalsIgnoreCase("Project Manager")) && (str.length() >= 2)) {
				removeButton.setEnabled(false);
			}
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

	public class MyPrint extends Thread implements Runnable {
		public MyPrint() {
		}

		public void run() {
			String str = "Quotation List";

			try {
				MessageFormat localMessageFormat1 = new MessageFormat(str);
				MessageFormat localMessageFormat2 = new MessageFormat("- {0} -");
				quotationTable.print(javax.swing.JTable.PrintMode.FIT_WIDTH, localMessageFormat1, localMessageFormat2);
			} catch (PrinterException localPrinterException) {
				System.err.println("Error printing: " + localPrinterException.getMessage());
			}
		}
	}
}
