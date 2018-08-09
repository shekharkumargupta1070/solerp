package com.sol.erp;

import java.awt.Dimension;
import java.awt.event.KeyEvent;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.sol.erp.util.DBConnectionUtil;

public class ChangePassword implements java.awt.event.ActionListener, java.awt.event.KeyListener {
	java.sql.Connection con;
	java.sql.Statement stat;
	java.sql.ResultSet rs;
	int affected = 0;

	JInternalFrame f = new JInternalFrame("Update Password", true, true, true, true);

	JLabel l1 = new JLabel("User Name");
	JLabel l2 = new JLabel("Old Password");
	JLabel l3 = new JLabel("New Password");

	JTextField tf1 = new JTextField(15);
	JPasswordField tf2 = new JPasswordField();
	JPasswordField tf3 = new JPasswordField();

	JButton b1 = new JButton("Update Password");
	JButton b2 = new JButton("Close");

	JPanel p1 = new JPanel();
	JPanel p2 = new JPanel();

	java.awt.Font fo = new java.awt.Font("Tahoma", 1, 12);

	javax.swing.border.Border bor1 = javax.swing.BorderFactory.createTitledBorder("Set Password");

	java.awt.Toolkit tk = java.awt.Toolkit.getDefaultToolkit();
	java.awt.Image ic = tk.getImage("/main.jpg");

	Dimension ss = tk.getScreenSize();
	Dimension fs = f.getSize();
	java.awt.Container c;

	public void px() {
		c = f.getContentPane();
		c.setLayout(new java.awt.BorderLayout());
		p1.setLayout(new java.awt.GridLayout(4, 2, 2, 2));
		p2.setLayout(new java.awt.BorderLayout());

		tf1.setFont(fo);
		tf2.setFont(fo);
		tf3.setFont(fo);

		p1.add(l1);
		p1.add(tf1);
		tf1.setEditable(false);
		p1.add(l2);
		p1.add(tf2);
		p1.add(l3);
		p1.add(tf3);

		p1.add(b1);
		b1.setMnemonic('U');
		b1.addActionListener(this);
		p1.add(b2);
		b2.setMnemonic('C');
		b2.addActionListener(this);

		tf1.addKeyListener(this);
		tf1.addActionListener(this);
		tf3.addActionListener(this);
		tf2.addKeyListener(this);
		tf3.addKeyListener(this);

		b1.addKeyListener(this);
		b2.addKeyListener(this);

		p1.setBorder(bor1);

		c.add(p1, "Center");

		f.setLocation((ss.width - fs.width) / 4, (ss.height - fs.height) / 8);

		f.setSize(360, 170);
		f.setVisible(true);
		f.setResizable(false);
		tf2.requestFocus();
	}

	public void setUserID(String paramString) {
		tf1.setText(paramString);
	}

	public void keyPressed(KeyEvent paramKeyEvent) {
		int i = paramKeyEvent.getKeyCode();
		if (i == 27) {
			f.setVisible(false);
		}
	}

	public void keyReleased(KeyEvent paramKeyEvent) {
	}

	public void keyTyped(KeyEvent paramKeyEvent) {
	}

	@SuppressWarnings("deprecation")
	public void actionPerformed(java.awt.event.ActionEvent paramActionEvent) {
		if ((paramActionEvent.getSource() == b1) || (paramActionEvent.getSource() == tf3)) {
			if (tf1.getText() != null) {

				try {

					con =  DBConnectionUtil.getConnection();
					stat = con.createStatement();
					affected = stat.executeUpdate("update password set password='" + tf3.getText()
							+ "' where user='" + tf1.getText() + "' and password='" + tf2.getText() + "'  ");

					if (affected > 0) {
						javax.swing.JOptionPane.showMessageDialog(f,
								"Password Changed For " + tf1.getText() + ".");
						tf1.setText("");
						tf2.setText("");
						tf3.setText("");
						tf1.requestFocus();
						tf1.selectAll();
					}
				} catch (Exception localException) {
					javax.swing.JOptionPane.showMessageDialog(f, localException.getMessage().toString());
				}
			}
		}
		if (paramActionEvent.getSource() == b2) {
			f.setVisible(false);
		}
	}
}
