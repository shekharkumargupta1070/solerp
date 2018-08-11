package com.sol.erp;

import javax.swing.*;
import java.awt.*;

public class ExportProjectReport {

    private JFrame frame;
    private JLabel fromLabel;
    private JLabel toLabel;
    private JTextField fromText;
    private JTextField toText;
    private JButton submitButton;
    private JPanel northComponentPanel;

    Container container;

    public ExportProjectReport(){
        frame = new JFrame("Export Project Report");
        northComponentPanel = new JPanel();
        fromLabel = new JLabel("From Project: ");
        toLabel = new JLabel("To Project: ");
        fromText = new JTextField(10);
        toText = new JTextField(10);
        submitButton = new JButton("Submit");

        northComponentPanel.setLayout(new FlowLayout());
        northComponentPanel.add(fromLabel);
        northComponentPanel.add(fromText);
        northComponentPanel.add(toLabel);
        northComponentPanel.add(toText);
        northComponentPanel.add(submitButton);

        container = frame.getContentPane();
        container.setLayout(new BorderLayout());
        container.add(northComponentPanel, BorderLayout.NORTH);
    }

    public void showForm(){
        frame.setSize(600, 200);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }



}
