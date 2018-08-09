package com.sol.erp;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;

import com.sol.erp.util.DBConnectionUtil;
import com.sol.erp.util.SessionUtil;

import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.swing.JRViewer;

public class OfferLetter implements ActionListener, java.awt.event.FocusListener {
	
	
	

	Connection con;

	Statement stat;
	ResultSet rs;
	String coidstr = SessionUtil.getLoginInfo().getCompanyId();
	String brcodestr = SessionUtil.getLoginInfo().getBranchCode();

	JFrame f = new JFrame();

	JTabbedPane tabbedpane = new JTabbedPane();
	JPanel application = new JPanel();
	JPanel preview = new JPanel();

	JLabel northLabel = new JLabel("<HTML><FONT COLOR=RED SIZE=12>Offer Letter</FONT></HTML>");
	JLabel l1 = new JLabel("Aplicant No");
	JLabel l2 = new JLabel("Name");
	JLabel l3 = new JLabel("Address");
	JLabel l4 = new JLabel("Post");
	JLabel l5 = new JLabel("Date of Joining");
	JLabel l6 = new JLabel("CTC");
	JLabel l7 = new JLabel("Monthly Salary");

	JTextField tf1 = new JTextField();
	JTextField tf2 = new JTextField();
	JTextPane tf3 = new JTextPane();
	JScrollPane sp3 = new JScrollPane(tf3);
	JTextField tf4 = new JTextField();
	JTextField tf5 = new JTextField();
	JTextField tf6 = new JTextField();
	JTextField tf7 = new JTextField();

	JButton b1 = new JButton("OK");
	JButton b2 = new JButton("Cancel");

	JPanel mainpanel = new JPanel();
	JPanel p1 = new JPanel();
	JPanel southpanel = new JPanel();

	public JPanel DesignFrame() {
		Container localContainer = f.getContentPane();
		localContainer.setLayout(new BorderLayout());
		mainpanel.setLayout(new BorderLayout());
		p1.setLayout(new GridLayout(8, 2, 2, 2));
		southpanel.setLayout(new java.awt.FlowLayout(2));
		application.setLayout(new BorderLayout());
		preview.setLayout(new BorderLayout());

		tabbedpane.add(application, "Application");
		tabbedpane.add(preview, "Preview");

		l1.setHorizontalAlignment(4);
		l2.setHorizontalAlignment(4);
		l3.setHorizontalAlignment(4);
		l4.setHorizontalAlignment(4);
		l5.setHorizontalAlignment(4);
		l6.setHorizontalAlignment(4);
		l7.setHorizontalAlignment(4);

		p1.add(l1);
		p1.add(tf1);
		p1.add(l2);
		p1.add(tf2);
		p1.add(l3);
		p1.add(sp3);
		p1.add(l4);
		p1.add(tf4);
		p1.add(l5);
		p1.add(tf5);
		p1.add(l6);
		p1.add(tf6);
		p1.add(l7);
		p1.add(tf7);

		southpanel.add(b1);
		southpanel.add(b2);

		northLabel.setHorizontalAlignment(0);
		application.add(p1, "North");
		application.add(southpanel, "Center");

		application.setBorder(BorderFactory.createTitledBorder("Offer Letter"));
		southpanel.setBorder(BorderFactory.createEtchedBorder());

		mainpanel.add(tabbedpane, "Center");

		localContainer.add(mainpanel, "Center");

		b1.addActionListener(this);
		b2.addActionListener(this);
		tf1.addActionListener(this);
		tf1.addFocusListener(this);

		return mainpanel;
	}

	public void showOfferLetter() {
		DesignFrame();
		f.setSize(550, 250);
		f.setLocationRelativeTo(null);
		f.setVisible(true);
	}


	public void details() {
		if (tf1.getText().length() > 0) {


			try {
				con = DBConnectionUtil.getConnection();
				stat = con.createStatement();
				rs = stat
						.executeQuery("select Name,Rec_Post,Date,Rec_Salary from HR_CEO_ROUNDS WHERE Aplicant_no='"
								+ tf1.getText() + "' and company_id='" + coidstr + "' and Branch_Code='"
								+ brcodestr + "' ");

				while (rs.next()) {
					String str1 = new String(rs.getString(1));
					String str2 = new String(rs.getString(2));
					String str3 = new String(rs.getString(3));
					String str4 = new String(rs.getString(4));

					tf2.setText(str1);
					tf4.setText(str2);
					tf5.setText(str3);
					tf7.setText(str4);
				}
			} catch (Exception localException) {
				javax.swing.JOptionPane.showMessageDialog(f, localException.getMessage().toString());
			}
		}
	}

	public void offerLetterReport() {
		String str = "//Printserver/d$/mapp/SOLGROUPERP/ERP/jrxml/offerletter2.jrxml";

		try {
			Map<String, Object> localHashMap = new HashMap<String, Object>();

			localHashMap.put("AplicantNo", String.valueOf(tf1.getText()));
			localHashMap.put("Name", String.valueOf(tf2.getText()));
			localHashMap.put("Address", String.valueOf(tf3.getText()));
			localHashMap.put("Post", String.valueOf(tf4.getText()));
			localHashMap.put("DOJ", String.valueOf(tf5.getText()));
			localHashMap.put("CTCSalary", String.valueOf(tf6.getText()));
			localHashMap.put("MonthlySalary", String.valueOf(tf7.getText()));

			JasperDesign localObject = JRXmlLoader.load(new File(str));
			net.sf.jasperreports.engine.JasperReport localJasperReport = net.sf.jasperreports.engine.JasperCompileManager
					.compileReport((JasperDesign) localObject);

			net.sf.jasperreports.engine.JasperPrint localJasperPrint = net.sf.jasperreports.engine.JasperFillManager
					.fillReport(localJasperReport, localHashMap, con);

			JRViewer localJRViewer = new JRViewer(localJasperPrint);
			preview.removeAll();
			preview.add(new JScrollPane(localJRViewer), "Center");
		} catch (Exception localException) {
			Object localObject = "Could not create the report " + localException.getMessage() + " "
					+ localException.getLocalizedMessage();

			System.out.println((String) localObject);
		}
	}

	public void actionPerformed(ActionEvent paramActionEvent) {
		if (paramActionEvent.getSource() == b1) {
			offerLetterReport();
			tabbedpane.setSelectedIndex(1);
		}
		if (paramActionEvent.getSource() == b2) {
		}
	}

	public void focusGained(FocusEvent paramFocusEvent) {
	}

	public void focusLost(FocusEvent paramFocusEvent) {
		details();
	}

}
