package com.sol.erp;

import java.io.IOException;

public class ReportServer {
	public void executeReport(String paramString) throws IOException {
		String str = paramString;
		Runtime localRuntime = Runtime.getRuntime();
		localRuntime.exec(str);
	}

}
