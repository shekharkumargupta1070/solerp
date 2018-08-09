package com.sol.erp;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.SoftBevelBorder;
import javax.swing.table.DefaultTableModel;

public class AplicantStatus extends JInternalFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JButton buttonSave;
	private JButton buttonSearch;

	public AplicantStatus() {
		initComponents();
	}

	private JButton buttonClose;
	private JPanel jPanel1;

	private void initComponents() {
		jPanel1 = new JPanel();
		panel1 = new JPanel();
		labelApplicantNumber = new JLabel();
		applicantName = new JLabel();
		labelFatherName = new JLabel();
		textFieldFatherName = new JTextField();
		tf2 = new JTextField();
		labelDateOfBirth = new JLabel();
		textFieldDateOfBirth = new JTextField();
		jTextField1 = new JTextField();
		labelHomeTown = new JLabel();
		textFieldHomeTown = new JTextField();
		labelScore = new JLabel();
		textFieldScore = new JTextField();
		panel2 = new JPanel();
		buttonSave = new JButton();
		buttonSearch = new JButton();
		buttonClose = new JButton();
		jScrollPane1 = new JScrollPane();
		jTable1 = new JTable();

		getContentPane().setLayout(null);

		setClosable(true);
		setIconifiable(true);
		setMaximizable(true);
		setResizable(true);
		setTitle("AplicantStatus");
		setFrameIcon(null);
		jPanel1.setLayout(null);

		jPanel1.setBackground(new java.awt.Color(204, 204, 204));
		panel1.setLayout(null);

		panel1.setBackground(new java.awt.Color(204, 204, 204));
		panel1.setBorder(BorderFactory.createEtchedBorder());
		panel1.setForeground(new java.awt.Color(255, 255, 255));
		labelApplicantNumber.setFont(new java.awt.Font("MS Sans Serif", 1, 11));
		labelApplicantNumber.setForeground(new java.awt.Color(102, 102, 102));
		labelApplicantNumber.setText("Aplicant No");
		panel1.add(labelApplicantNumber);
		labelApplicantNumber.setBounds(20, 30, 90, 20);

		applicantName.setFont(new java.awt.Font("MS Sans Serif", 1, 11));
		applicantName.setForeground(new java.awt.Color(102, 102, 102));
		applicantName.setText("Aplicant Name");
		panel1.add(applicantName);
		applicantName.setBounds(20, 60, 90, 20);

		labelFatherName.setFont(new java.awt.Font("MS Sans Serif", 1, 11));
		labelFatherName.setForeground(new java.awt.Color(102, 102, 102));
		labelFatherName.setText("Father's Name");
		panel1.add(labelFatherName);
		labelFatherName.setBounds(20, 90, 100, 20);

		panel1.add(textFieldFatherName);
		textFieldFatherName.setBounds(130, 60, 290, 21);

		panel1.add(tf2);
		tf2.setBounds(130, 90, 290, 21);

		labelDateOfBirth.setFont(new java.awt.Font("MS Sans Serif", 1, 11));
		labelDateOfBirth.setForeground(new java.awt.Color(102, 102, 102));
		labelDateOfBirth.setText("Date of Birth");
		panel1.add(labelDateOfBirth);
		labelDateOfBirth.setBounds(20, 120, 90, 20);

		panel1.add(textFieldDateOfBirth);
		textFieldDateOfBirth.setBounds(130, 120, 100, 21);

		panel1.add(jTextField1);
		jTextField1.setBounds(130, 30, 100, 21);

		labelHomeTown.setFont(new java.awt.Font("MS Sans Serif", 1, 11));
		labelHomeTown.setForeground(new java.awt.Color(102, 102, 102));
		labelHomeTown.setText("Home Town");
		panel1.add(labelHomeTown);
		labelHomeTown.setBounds(20, 150, 90, 20);

		panel1.add(textFieldHomeTown);
		textFieldHomeTown.setBounds(130, 150, 290, 21);

		labelScore.setFont(new java.awt.Font("MS Sans Serif", 1, 11));
		labelScore.setForeground(new java.awt.Color(102, 102, 102));
		labelScore.setText("Score");
		panel1.add(labelScore);
		labelScore.setBounds(20, 180, 90, 20);

		panel1.add(textFieldScore);
		textFieldScore.setBounds(130, 180, 50, 21);

		jPanel1.add(panel1);
		panel1.setBounds(30, 20, 450, 220);

		panel2.setLayout(null);

		panel2.setBackground(new java.awt.Color(204, 204, 204));
		panel2.setBorder(BorderFactory.createEtchedBorder());
		panel2.setAlignmentX(0.2F);
		panel2.setAlignmentY(0.2F);
		buttonSave.setText("Save");
		buttonSave.setBorder(new SoftBevelBorder(0));
		panel2.add(buttonSave);
		buttonSave.setBounds(80, 10, 100, 30);

		buttonSearch.setText("Search");
		panel2.add(buttonSearch);
		buttonSearch.setBounds(190, 10, 100, 30);

		buttonClose.setText("Close");
		panel2.add(buttonClose);
		buttonClose.setBounds(300, 10, 90, 30);

		jPanel1.add(panel2);
		panel2.setBounds(30, 250, 450, 50);

		jTable1.setModel(new DefaultTableModel(
				new Object[][] { { null, null, null, null, null, null }, { null, null, null, null, null, null },
						{ null, null, null, null, null, null }, { null, null, null, null, null, null } },
				new String[] { "Date", "Name", "Father", "Date of Birth", "Home Town", "Score" }));

		jScrollPane1.setViewportView(jTable1);

		jPanel1.add(jScrollPane1);
		jScrollPane1.setBounds(30, 310, 750, 220);

		getContentPane().add(jPanel1);
		jPanel1.setBounds(0, 0, 800, 570);

		java.awt.Dimension localDimension = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
		setBounds((localDimension.width - 809) / 2, (localDimension.height - 586) / 2, 809, 586);
	}

	private JScrollPane jScrollPane1;
	private JTable jTable1;
	private JTextField jTextField1;
	private JLabel labelApplicantNumber;
	private JLabel applicantName;
	private JLabel labelFatherName;
	private JLabel labelDateOfBirth;
	private JLabel labelHomeTown;
	private JLabel labelScore;
	private JPanel panel1;
	private JPanel panel2;
	private JTextField textFieldFatherName;
	private JTextField tf2;
	private JTextField textFieldDateOfBirth;
	private JTextField textFieldHomeTown;
	private JTextField textFieldScore;
}