package com.sol.erp.util;

import java.util.ArrayList;
import java.util.List;

import com.sol.erp.constants.ApplicationConstants;

public class ApplicationUtil {

	
	
	
	private static List<String> monthList = new ArrayList<String>();
	
	static {
		monthList.add(ApplicationConstants.MONTH_JANUARY);
		monthList.add(ApplicationConstants.MONTH_FEBRUARY);
		monthList.add(ApplicationConstants.MONTH_MARCH);
		monthList.add(ApplicationConstants.MONTH_APRIL);
		monthList.add(ApplicationConstants.MONTH_MAY);
		monthList.add(ApplicationConstants.MONTH_JUNE);
		monthList.add(ApplicationConstants.MONTH_JULY);
		monthList.add(ApplicationConstants.MONTH_AUGUST);
		monthList.add(ApplicationConstants.MONTH_SEPTEMBER);
		monthList.add(ApplicationConstants.MONTH_OCTOBER);
		monthList.add(ApplicationConstants.MONTH_NOVEMEBER);
		monthList.add(ApplicationConstants.MONTH_DECEMBER);
	}
	
	public static List<String> getMonthList(){
		return monthList;
	}
}
