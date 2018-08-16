package com.sol.erp;

import com.sol.erp.exports.dto.ProjectProgressDTO;
import com.sol.erp.exports.util.ReportUtil;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Map;

public class ExportProjectReport {

    private JInternalFrame frame;
    private JLabel fromLabel;
    private JLabel toLabel;
    private JTextField fromText;
    private JTextField toText;
    private JButton submitButton;
    private JPanel northComponentPanel;

    Container container;

    public ExportProjectReport(){
        frame = new JInternalFrame("Export Project Report");
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

        submitButton.addActionListener((actionEvent) -> {
            System.out.println("ActionSource: "+actionEvent.getSource());
            String fromProjectNo = fromText.getText();
            String toProjectNo = toText.getText();
            Map<String, List<ProjectProgressDTO>> progressMap = ReportUtil.exportProjectProgressReport(fromProjectNo, toProjectNo);


            progressMap.forEach( (k, v) -> {
                //System.out.println(v);
                v.forEach((e) -> {
                    System.out.println(e.getProjectNo() + "\t" +e.getActivity() + "\t" + e.getEstimatedHrs() + "\t" + e.getSpentHrs() + "\t" + e.getUsedPercentage());
                });
            });
            System.out.println(ReportUtil.class.getName() + " : exportProjectProgressReport [END] ");
        });
    }

    public JInternalFrame getScreen(){
        frame.setSize(600, 200);
        frame.setVisible(true);
        return frame;
    }



}
