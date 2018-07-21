package com.sol.erp;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import com.sol.erp.util.DBConnectionUtil;

public class ITMainFrame implements ActionListener {
	

	JFrame f = new JFrame("Technichal Dept");
	static JDesktopPane desktop = new JDesktopPane();

	JMenuBar menubar = new JMenuBar();
	JMenu ma = new JMenu("Master SetUPs");
	JMenu mb = new JMenu("IT Power");

	JMenuItem ma1 = new JMenuItem("Exit");

	JMenuItem mb1 = new JMenuItem("ReCycle Password");

	Toolkit tk = Toolkit.getDefaultToolkit();

	public void px() {
		Container localContainer = f.getContentPane();

		localContainer.setLayout(new BorderLayout());
		localContainer.setBackground(Color.darkGray);
		localContainer.add(desktop, "Center");
		desktop.setBackground(Color.darkGray);

		ma.add(ma1);

		mb.add(mb1);

		menubar.add(ma);
		menubar.add(mb);

		ma1.addActionListener(this);
		mb1.addActionListener(this);

		f.setJMenuBar(menubar);
		f.setSize(tk.getScreenSize());
		f.setVisible(true);
	}

	Connection con = null;
	PreparedStatement prsm = null;
	ResultSet rs = null;

	public void RecyclePassword() {
		String str1 = "0";
		str1 = JOptionPane.showInputDialog(f, "Enter EmpCode To ReCycle the Password");

		String str2 = EmpTB.getUserType(str1);
		String str3 = null;

		if (str2.equalsIgnoreCase("User")) {
			str3 = "DEFAULT";
		}
		if (str2.equalsIgnoreCase("Admin")) {
			str3 = "SUPER";
		}

		try {
			con = DBConnectionUtil.getConnection();
			prsm = con.prepareStatement("Update PASSWORD set password=? where user =?");
			prsm.setString(1, str3);
			prsm.setString(2, str1);
			int i = prsm.executeUpdate();

			if (i > 0) {
				JOptionPane.showMessageDialog(f, "Password ReCycled Successfully.");
			}
		} catch (Exception localException) {
			JOptionPane.showMessageDialog(f, localException.getMessage().toString());
		}
	}

	public void actionPerformed(ActionEvent paramActionEvent) {
		if (paramActionEvent.getSource() == ma1) {
			System.exit(0);
		}
		if (paramActionEvent.getSource() == mb1) {
			RecyclePassword();
		}
	}
}
