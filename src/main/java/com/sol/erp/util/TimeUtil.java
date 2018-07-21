package com.sol.erp.util;

import java.text.DecimalFormat;

import com.sol.erp.constants.ApplicationConstants;

public class TimeUtil {

	private static DecimalFormat formatter4digit = new DecimalFormat(ApplicationConstants.DIGIT_4);
	private static DecimalFormat formatter2digit = new DecimalFormat(ApplicationConstants.DIGIT_2);

	public static String getTotalWorkingHrs(String inTime, String outTime) {
		String spliter = "";
		if (inTime.contains(".")) {
			spliter = "\\.";
		}
		if (inTime.contains("_")) {
			spliter = "_";
		}
		if (inTime.contains(":")) {
			spliter = ":";
		}

		String result = null;
		if (inTime.length() > 1) {
			String inTimeArray[] = inTime.split(spliter);
			String outTimeArray[] = outTime.split(spliter);

			String inTimeString = inTimeArray[0] + inTimeArray[1];
			String outTimeString = outTimeArray[0] + outTimeArray[1];

			int totalHrs = Integer.parseInt(outTimeString) - Integer.parseInt(inTimeString);

			String totalHrsString = formatter4digit.format(totalHrs);
			String minutesString = totalHrsString.substring(2, 4);
			int totalMinute = Integer.parseInt(minutesString);
			if (totalMinute > 60) {
				totalHrs -= 40;
			}

			System.out.println("totalHrs: " + totalHrs);
			result = formatter4digit.format(totalHrs);
			;
		}

		// Formating Result
		int resultValue = Integer.parseInt(result);
		if(spliter.equals("\\.")) {
			spliter = ".";
		}
		if (resultValue > 0) {
			String hrsString = result.substring(0, 2);
			String minutesString = result.substring(2, 4);
			result = hrsString + spliter + minutesString;
		} else {
			result = "00" + spliter + "00";
		}

		return result;
	}

	/*
	public static void main(String args[]) {
		System.out.println("Total Time: " + getTotalWorkingHrs("06:50", "07:30"));
	}
	*/
}
