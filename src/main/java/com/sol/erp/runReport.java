package com.sol.erp;

import java.io.IOException;

public class runReport {
	public void px() throws IOException {
		Runtime localRuntime = Runtime.getRuntime();
		localRuntime.exec("//solsrv08/solgrouperp/tech/DRGLOGREPORT.bat");
	}
}
