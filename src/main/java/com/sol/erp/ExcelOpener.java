package com.sol.erp;

import java.io.File;
import java.io.IOException;

public class ExcelOpener {
	public void openTable(File paramFile) throws IOException {
		Runtime localRuntime = Runtime.getRuntime();

		String str = System.getProperty("os.name").toLowerCase();
		boolean bool = str.startsWith("mac os x");

		if (bool) {
			localRuntime.exec("open " + paramFile);
		} else {
			localRuntime.exec("cmd.exe /mainContainer start " + paramFile);
		}
		System.out.println(paramFile + " opened");
	}
}
