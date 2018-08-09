package com.sol.erp;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;

import com.sol.erp.util.DBConnectionUtil;
import com.sol.erp.util.SessionUtil;

import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JRViewer;

public class AppointLetter implements ActionListener, FocusListener {

    JFrame f = new JFrame();

    JTabbedPane tabbedPane = new JTabbedPane();
    JPanel applicationPanel = new JPanel();
    JPanel previewPanel = new JPanel();

    JLabel northLabel = new JLabel("<HTML><FONT COLOR=RED SIZE=12>Offer Letter</FONT></HTML>");
    JLabel labelApplicantNumber = new JLabel("Aplicant No");
    JLabel labelName = new JLabel("Name");
    JLabel labelAddress = new JLabel("Address");
    JLabel labelPost = new JLabel("Post");
    JLabel labelDateOfJoining = new JLabel("Date of Joining");
    JLabel labelCtc = new JLabel("CTC");
    JLabel labelMonthlySalary = new JLabel("Monthly Salary");

    JTextField textFieldApplicantNumber = new JTextField();
    JTextField textFieldName = new JTextField();
    JTextPane textFieldAddress = new JTextPane();
    JScrollPane scrollPaneAddress = new JScrollPane(textFieldAddress);
    JTextField textFieldPost = new JTextField();
    JTextField textFieldDateOfJoining = new JTextField();
    JTextField textFieldCtc = new JTextField();
    JTextField textFieldMonthlySalary = new JTextField();

    JButton buttonOk = new JButton("OK");
    JButton buttonCancel = new JButton("Cancel");

    JPanel mainPanel = new JPanel();
    JPanel formPanel = new JPanel();
    JPanel southPanel = new JPanel();

    public JPanel DesignFrame() {
        Container container = f.getContentPane();
        container.setLayout(new BorderLayout());
        mainPanel.setLayout(new BorderLayout());
        formPanel.setLayout(new GridLayout(8, 2, 2, 2));
        southPanel.setLayout(new FlowLayout(2));
        applicationPanel.setLayout(new BorderLayout());
        previewPanel.setLayout(new BorderLayout());

        tabbedPane.add(applicationPanel, "Application");
        tabbedPane.add(previewPanel, "Preview");

        labelApplicantNumber.setHorizontalAlignment(4);
        labelName.setHorizontalAlignment(4);
        labelAddress.setHorizontalAlignment(4);
        labelPost.setHorizontalAlignment(4);
        labelDateOfJoining.setHorizontalAlignment(4);
        labelCtc.setHorizontalAlignment(4);
        labelMonthlySalary.setHorizontalAlignment(4);

        formPanel.add(labelApplicantNumber);
        formPanel.add(textFieldApplicantNumber);
        formPanel.add(labelName);
        formPanel.add(textFieldName);
        formPanel.add(labelAddress);
        formPanel.add(scrollPaneAddress);
        formPanel.add(labelPost);
        formPanel.add(textFieldPost);
        formPanel.add(labelDateOfJoining);
        formPanel.add(textFieldDateOfJoining);
        formPanel.add(labelCtc);
        formPanel.add(textFieldCtc);
        formPanel.add(labelMonthlySalary);
        formPanel.add(textFieldMonthlySalary);

        southPanel.add(buttonOk);
        southPanel.add(buttonCancel);

        northLabel.setHorizontalAlignment(0);
        applicationPanel.add(formPanel, "North");
        applicationPanel.add(southPanel, "Center");

        applicationPanel.setBorder(BorderFactory.createTitledBorder("Offer Letter"));
        southPanel.setBorder(BorderFactory.createEtchedBorder());

        mainPanel.add(tabbedPane, "Center");

        container.add(mainPanel, "Center");

        buttonOk.addActionListener(this);
        buttonCancel.addActionListener(this);
        textFieldApplicantNumber.addActionListener(this);
        textFieldApplicantNumber.addFocusListener(this);

        return mainPanel;
    }

    public void showOfferLetter() {
        DesignFrame();
        f.setSize(550, 250);
        f.setLocationRelativeTo(null);
        f.setVisible(true);
    }

    public void details() {
        String companyId = SessionUtil.getLoginInfo().getCompanyId();
        String branchCode = SessionUtil.getLoginInfo().getBranchCode();

        Connection con = null;
        Statement stat = null;
        ResultSet rs = null;
        if (textFieldApplicantNumber.getText().length() > 0) {
            try {
                con = DBConnectionUtil.getConnection();
                stat = con.createStatement();
                rs = stat.executeQuery("select Name,Rec_Post,Date,Rec_Salary from HR_CEO_ROUNDS WHERE Aplicant_no='"
                        + textFieldApplicantNumber.getText() + "' and company_id='" + companyId + "' and Branch_Code='"
                        + branchCode + "' ");

                while (rs.next()) {
                    String str1 = new String(rs.getString(1));
                    String str2 = new String(rs.getString(2));
                    String str3 = new String(rs.getString(3));
                    String str4 = new String(rs.getString(4));

                    textFieldName.setText(str1);
                    textFieldPost.setText(str2);
                    textFieldDateOfJoining.setText(str3);
                    textFieldMonthlySalary.setText(str4);
                }
            } catch (Exception localException) {
                JOptionPane.showMessageDialog(f, localException.getMessage().toString());
            } finally{
                DBConnectionUtil.closeConnection(rs, stat, con);
            }
        }
    }

    public void appointmentLetterReport() {
        String str = null;
        Connection con = null;
        try {
            HashMap<String, Object> localHashMap = new HashMap<String, Object>();
            localHashMap.put("AplicantNo", String.valueOf(textFieldApplicantNumber.getText()));
            localHashMap.put("Name", String.valueOf(textFieldName.getText()));
            localHashMap.put("Address", String.valueOf(textFieldAddress.getText()));
            localHashMap.put("Post", String.valueOf(textFieldPost.getText()));
            localHashMap.put("DOJ", String.valueOf(textFieldDateOfJoining.getText()));
            localHashMap.put("CTCSalary", String.valueOf(textFieldCtc.getText()));
            localHashMap.put("MonthlySalary", String.valueOf(textFieldMonthlySalary.getText()));

            JasperDesign localObject = JRXmlLoader.load(new File(str));
            JasperReport localJasperReport = net.sf.jasperreports.engine.JasperCompileManager
                    .compileReport((JasperDesign) localObject);

            con = DBConnectionUtil.getConnection();

            JasperPrint localJasperPrint = net.sf.jasperreports.engine.JasperFillManager.fillReport(localJasperReport,
                    localHashMap, con);

            JRViewer localJRViewer = new JRViewer(localJasperPrint);
            previewPanel.removeAll();
            previewPanel.add(new JScrollPane(localJRViewer), "Center");
        } catch (Exception localException) {
            Object localObject = "Could not create the report " + localException.getMessage() + " "
                    + localException.getLocalizedMessage();

            System.out.println((String) localObject);
        } finally {
            DBConnectionUtil.closeConnection(null, con);
        }
    }

    public void actionPerformed(ActionEvent paramActionEvent) {
        if (paramActionEvent.getSource() == buttonOk) {
            appointmentLetterReport();
            tabbedPane.setSelectedIndex(1);
        }
        if (paramActionEvent.getSource() == buttonCancel) {
        }
    }

    public void focusGained(FocusEvent paramFocusEvent) {
    }

    public void focusLost(FocusEvent paramFocusEvent) {
        details();
    }
}
