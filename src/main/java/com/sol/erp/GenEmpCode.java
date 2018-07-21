package com.sol.erp;

import javax.swing.JLabel;

public class GenEmpCode extends javax.swing.JInternalFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private javax.swing.JButton jButton1;
	private javax.swing.JButton jButton2;
	private javax.swing.JButton jButton3;

	public GenEmpCode() {
		initComponents();
	}

	private JLabel jLabel1;

	private void initComponents() {
		jPanel1 = new javax.swing.JPanel();
		jLabel1 = new JLabel();
		jTextField1 = new javax.swing.JTextField();
		jLabel2 = new JLabel();
		jTextField2 = new javax.swing.JTextField();
		jLabel3 = new JLabel();
		jTextField3 = new javax.swing.JTextField();
		jLabel4 = new JLabel();
		jTextField4 = new javax.swing.JTextField();
		jPanel2 = new javax.swing.JPanel();
		jLabel5 = new JLabel();
		jButton1 = new javax.swing.JButton();
		jButton2 = new javax.swing.JButton();
		jButton3 = new javax.swing.JButton();

		getContentPane().setLayout(null);

		setClosable(true);
		setIconifiable(true);
		setMaximizable(true);
		setResizable(true);
		setTitle("Generate 3 Char Code");
		setVisible(true);
		jPanel1.setLayout(null);

		jPanel1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(102, 102, 102), 1, true));
		jLabel1.setText("Emp Code");
		jPanel1.add(jLabel1);
		jLabel1.setBounds(10, 10, 70, 20);

		jPanel1.add(jTextField1);
		jTextField1.setBounds(110, 10, 90, 21);

		jLabel2.setText("First Name");
		jPanel1.add(jLabel2);
		jLabel2.setBounds(10, 40, 70, 20);

		jTextField2.setEditable(false);
		jPanel1.add(jTextField2);
		jTextField2.setBounds(110, 40, 340, 21);

		jLabel3.setText("Middle Initial");
		jPanel1.add(jLabel3);
		jLabel3.setBounds(10, 70, 70, 20);

		jTextField3.setEditable(false);
		jPanel1.add(jTextField3);
		jTextField3.setBounds(110, 70, 30, 21);

		jLabel4.setText("Last Name");
		jPanel1.add(jLabel4);
		jLabel4.setBounds(10, 100, 70, 20);

		jTextField4.setEditable(false);
		jPanel1.add(jTextField4);
		jTextField4.setBounds(110, 100, 340, 21);

		getContentPane().add(jPanel1);
		jPanel1.setBounds(50, 30, 470, 140);

		jPanel2.setLayout(null);

		jPanel2.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 51, 51), 1, true));
		jLabel5.setFont(new java.awt.Font("MS Sans Serif", 1, 12));
		jLabel5.setHorizontalAlignment(0);
		jLabel5.setText("3 Char Code");
		jPanel2.add(jLabel5);
		jLabel5.setBounds(20, 30, 80, 30);

		getContentPane().add(jPanel2);
		jPanel2.setBounds(220, 180, 120, 90);

		jButton1.setText("Search code");
		getContentPane().add(jButton1);
		jButton1.setBounds(70, 285, 130, 30);

		jButton2.setText("Try Again");
		getContentPane().add(jButton2);
		jButton2.setBounds(220, 285, 130, 30);

		jButton3.setText("Close");
		getContentPane().add(jButton3);
		jButton3.setBounds(370, 285, 130, 30);

		java.awt.Dimension localDimension = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
		setBounds((localDimension.width - 569) / 2, (localDimension.height - 363) / 2, 569, 363);
	}

	private JLabel jLabel2;
	private JLabel jLabel3;
	private JLabel jLabel4;
	private JLabel jLabel5;
	private javax.swing.JPanel jPanel1;
	private javax.swing.JPanel jPanel2;
	private javax.swing.JTextField jTextField1;
	private javax.swing.JTextField jTextField2;
	private javax.swing.JTextField jTextField3;
	private javax.swing.JTextField jTextField4;
}
