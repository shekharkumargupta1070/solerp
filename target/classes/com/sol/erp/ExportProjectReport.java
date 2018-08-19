package com.sol.erp;

import com.sol.erp.exports.dto.ProjectProgressDTO;
import com.sol.erp.exports.util.ReportUtil;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ExportProjectReport {

    private JInternalFrame frame;
    private JLabel fromLabel;
    private JLabel toLabel;
    private JTextField fromText;
    private JTextField toText;
    private JButton submitButton;
    private JPanel northComponentPanel;
    private JProgressBar progressBar;


    Container container;


    public ExportProjectReport(){
        frame = new JInternalFrame("Export Project Report");
        northComponentPanel = new JPanel();
        fromLabel = new JLabel("From Project: ");
        toLabel = new JLabel("To Project: ");
        fromText = new JTextField(10);
        toText = new JTextField(10);
        submitButton = new JButton("Submit");
        progressBar = new JProgressBar(0, 100);

        progressBar.setStringPainted(true);

        northComponentPanel.setLayout(new FlowLayout());
        northComponentPanel.add(fromLabel);
        northComponentPanel.add(fromText);
        northComponentPanel.add(toLabel);
        northComponentPanel.add(toText);
        northComponentPanel.add(submitButton);

        container = frame.getContentPane();
        container.setLayout(new BorderLayout());
        container.add(northComponentPanel, BorderLayout.NORTH);
        container.add(progressBar, BorderLayout.SOUTH);

        submitButton.addActionListener((actionEvent) -> {


            if (isFormValidated()) {
                JFileChooser fileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
                //int returnValue = fileChooser.showSaveDialog(null);
                //returnValue == JFileChooser.APPROVE_OPTION

                fileChooser.showSaveDialog(null);
                File file = fileChooser.getSelectedFile();
                final String fileName = file.getAbsolutePath();


                long fromProjectNo = Long.parseLong(fromText.getText().trim());
                long toProjectNo = Long.parseLong(toText.getText().trim());

                SwingWorker worker = new SwingWorker<Boolean, Integer>() {

                    @Override
                    protected Boolean doInBackground() throws Exception {
                        System.out.println("Background...");
                        this.setProgress(1);
                        this.publish(1);

                        Map<Long, List<ProjectProgressDTO>> progressMap = ReportUtil.generateProjectProgressData(fromProjectNo, toProjectNo);
                        this.setProgress(50);
                        this.publish(50);


                        progressMap.forEach( (k, v) -> {
                            //System.out.println(v);
                            v.forEach((e) -> {
                                System.out.println(e.getProjectNo() + "\t" +e.getActivity() + "\t" + e.getSpentHrs() + "\t" + e.getUsedPercentage());
                            });
                        });

                        this.setProgress(80);
                        this.publish(80);


                        if(fileName == null || fileName.isEmpty()){
                            String defaultFileName = FileSystemView.getFileSystemView().getHomeDirectory() + "/"+fromText.getText()+"-"+toText.getText();
                            createExcelFile(progressMap, defaultFileName);
                        }else{
                            createExcelFile(progressMap, fileName);
                        }



                        setProgress(100);
                        publish(100);
                        return true;
                    }

                    @Override
                    protected void process(List<Integer> chunks) {
                        super.process(chunks);
                        int currentProgress = chunks.get(chunks.size() - 1);
                        progressBar.setValue(currentProgress);
                        switch (currentProgress){
                            case 1 :
                                progressBar.setString(currentProgress+"% Process started and working...");
                                break;
                            case 50 :
                                progressBar.setString(currentProgress+"% Project data populated...");
                                break;
                            case 100 :
                                progressBar.setString(currentProgress+"% Generated excel file...");
                                break;
                            default:
                                progressBar.setString("Working...");
                        }
                    }

                    @Override
                    protected void done() {
                        super.done();
                        setProgress(100);
                        System.out.println("Done!");
                        submitButton.setEnabled(true);
                    }
                };

                worker.execute();
            }
        });
    }

    public JInternalFrame getScreen(){
        Toolkit toolKit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolKit.getScreenSize();
        Dimension frameSize = frame.getSize();

        frame.setSize(600, 200);
        frame.setLocation((screenSize.width - frameSize.width) / 2,
                (screenSize.height - frameSize.height)/2);
        frame.setVisible(true);
        return frame;
    }

    private boolean isFormValidated(){
        String fromProject = fromText.getText();
        String toProject = toText.getText();
        if(fromProject == null || fromProject.isEmpty()){
            JOptionPane.showMessageDialog(frame, "Please enter From Project");
            return false;
        }
        if(toProject == null || toProject.isEmpty()){
            JOptionPane.showMessageDialog(frame, "Please enter To Project");
            return false;
        }
        else{
            return true;
        }
    }



    public void createExcelFile(Map<Long, List<ProjectProgressDTO>> dataMap, String fileName) {

        String sheetName = "SOL Project Report";

        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet();

        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("Project No");
        header.createCell(1).setCellValue("Activities");
        header.createCell(2).setCellValue("Taken Hrs");
        header.createCell(3).setCellValue("Percentage");

        Set<Long> keySet = dataMap.keySet();

        int rowCounter = 1;
        for (Long key : keySet) {
            List<ProjectProgressDTO> projectProgressData = dataMap.get(key);
            for (ProjectProgressDTO dto : projectProgressData) {
                Row row = sheet.createRow(rowCounter);
                row.createCell(0).setCellValue(dto.getProjectNo());
                row.createCell(1).setCellValue(dto.getActivity());
                row.createCell(2).setCellValue(dto.getSpentHrs());
                row.createCell(3).setCellValue(dto.getUsedPercentage());
                rowCounter++;
            }
        }

        try {
            FileOutputStream outputStream = new FileOutputStream(fileName + ".xls");
            workbook.write(outputStream);
            workbook.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void exportReportAsExcel(Map<Long, List<ProjectProgressDTO>> dataMap) {
        JFileChooser fileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        int returnValue = fileChooser.showSaveDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            createExcelFile(dataMap, file.getAbsolutePath());
        }
    }


}
