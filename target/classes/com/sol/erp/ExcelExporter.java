package com.sol.erp;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.File;
import java.io.FileWriter;

public class ExcelExporter {

	public void exportTable(JTable paramJTable, DefaultTableModel paramDefaultTableModel, File paramFile)
			throws java.io.IOException {
		FileWriter localFileWriter = new FileWriter(paramFile);

		for (int i = 0; i < paramJTable.getColumnCount(); i++) {
			localFileWriter.write(paramJTable.getColumnName(i) + "\t");
		}
		localFileWriter.write("\n");

		for (int i = 0; i < paramJTable.getRowCount(); i++) {
			for (int j = 0; j < paramJTable.getColumnCount(); j++) {
				if (j == 0) {
					localFileWriter.write(String.valueOf(paramDefaultTableModel.getValueAt(i, j)) + "\t");
				} else {
					localFileWriter.write("\"" + String.valueOf(paramDefaultTableModel.getValueAt(i, j)) + "\t" + "\"");
				}
			}
			localFileWriter.write("\n");
		}
		localFileWriter.close();
		System.out.println("write to " + paramFile);
	}



}
