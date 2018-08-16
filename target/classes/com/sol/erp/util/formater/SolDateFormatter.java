package com.sol.erp.util.formater;

import com.sol.erp.constants.ApplicationConstants;

import java.sql.Date;
import java.sql.Time;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

public class SolDateFormatter {

	public static DateFormat getDateFormater(String formaterStyle) {
		DateFormat formater = null;
		if (formaterStyle.equalsIgnoreCase(ApplicationConstants.DD_MM_YYYY)) {
			formater = new SimpleDateFormat(ApplicationConstants.DD_MM_YYYY);
		}
		if (formaterStyle.equalsIgnoreCase(ApplicationConstants.MMM_YYYY)) {
			formater = new SimpleDateFormat(ApplicationConstants.MMM_YYYY);
		} else {
			formater = new SimpleDateFormat(ApplicationConstants.DD_MM_YYYY);
		}

		return formater;
	}

	public static String SQLtoDDMMYY(Date sqlDate) {
		SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
		String dateString = null;
		try {
			dateString = localSimpleDateFormat.format(sqlDate);
		} catch (IllegalArgumentException localIllegalArgumentException) {
			System.out.println(localIllegalArgumentException.getMessage());
		}
		return dateString;
	}

	public static java.sql.Date DDMMYYtoSQL(String ddmmyyString) {
		String str1 = ddmmyyString;
		String str2 = str1.substring(0, str1.indexOf('/'));
		String str3 = str1.substring(str1.indexOf('/') + 1, str1.lastIndexOf('/'));
		String str4 = str1.substring(str1.lastIndexOf('/') + 1, ddmmyyString.length());

		@SuppressWarnings("deprecation")
		Date localDate = new Date(Integer.parseInt(str4) - 1900, Integer.parseInt(str3) - 1, Integer.parseInt(str2));

		return localDate;
	}

	public static String SQLTIMEtoSimpleTime(Time paramTime) {
		SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("hh.mm");
		String timeString = null;
		try {
			timeString = localSimpleDateFormat.format(paramTime);
		} catch (IllegalArgumentException localIllegalArgumentException) {
			System.out.println(localIllegalArgumentException.getMessage());
		}
		return timeString;
	}

	public static Time SimpleTimetoSQLTIME(String decimalTimeString) {
		String hrs = "0";
		String minutes = "0";

		hrs = decimalTimeString.substring(0, decimalTimeString.indexOf('.'));
		minutes = decimalTimeString.substring(decimalTimeString.indexOf('.') + 1, decimalTimeString.length());

		@SuppressWarnings("deprecation")
		Time localTime = new Time(Integer.parseInt(hrs), Integer.parseInt(minutes), 0);

		return localTime;
	}

	public static int getHRS(String decimalTimeString) {
		DecimalFormat formater2decimal = new DecimalFormat("00.00");
		String timeString = String.valueOf(formater2decimal.format(Float.parseFloat(decimalTimeString)));

		int decimalIndex = timeString.indexOf(".");
		String hrsString = timeString.substring(0, decimalIndex);
		int hrs = Integer.parseInt(hrsString);

		return hrs;
	}

	public static int getMinutes(String paramString) {
		DecimalFormat formater2decimal = new DecimalFormat("00.00");
		String str1 = String.valueOf(formater2decimal.format(Float.parseFloat(paramString)));

		int i = str1.indexOf(".");
		int j = str1.length();

		String str2 = str1.substring(i + 1, j);

		int k = Integer.parseInt(str2);

		return k;
	}

	public static int convertHrsToMinute(String paramString) {
		DecimalFormat formater2decimal = new DecimalFormat("00.00");
		String str1 = String.valueOf(formater2decimal.format(Float.parseFloat(paramString)));

		int i = str1.indexOf(".");
		int j = str1.length();

		String str2 = str1.substring(0, i);
		String str3 = str1.substring(i + 1, j);

		int k = Integer.parseInt(str2);
		int m = Integer.parseInt(str3);

		int n = k * 60 + m;

		return n;
	}

	public static String convertMinuteToHRS(int paramInt) {
		DecimalFormat fomater2digit = new DecimalFormat("00");
		int i = paramInt / 60;
		int j = paramInt % 60;

		String str = "00.00";

		str = String.valueOf(fomater2digit.format(i)) + "." + String.valueOf(fomater2digit.format(j));

		if (i < 0) {
			str = String.valueOf(fomater2digit.format(i)) + "." + String.valueOf(fomater2digit.format(j - j * 2));
		}

		return str;
	}

	public static float convertHrsToMinute2(String paramString) {
		DecimalFormat formater2decimal = new DecimalFormat("00.00");
		String str1 = String.valueOf(formater2decimal.format(Float.parseFloat(paramString)));

		int i = str1.indexOf(".");
		int j = str1.length();

		String str2 = str1.substring(0, i);
		String str3 = str1.substring(i + 1, j);

		int k = Integer.parseInt(str2);
		int m = Integer.parseInt(str3);

		float f = k * 60 + m;

		return f;
	}

	public static String convertMinuteToHRS2(float paramFloat) {
		DecimalFormat formater2digit = new DecimalFormat("00");
		float f1 = paramFloat / 60.0F;
		float f2 = paramFloat % 60.0F;

		String str = String.valueOf(formater2digit.format(f1)) + "." + String.valueOf(formater2digit.format(f2));

		return str;
	}

	public static String setClockFormat(String paramString) {
		DecimalFormat formater2digit = new DecimalFormat("00");
		DecimalFormat formater2decimal = new DecimalFormat("00.00");
		String str1 = String.valueOf(formater2decimal.format(Float.parseFloat(paramString)));

		int i = str1.indexOf(".");
		int j = str1.length();

		String str2 = str1.substring(0, i);
		String str3 = str1.substring(i + 1, j);

		int k = Integer.parseInt(str2);
		int m = Integer.parseInt(str3);

		if (k >= 23) {
			k = 23;
			m -= 60;
			str1 = String.valueOf(formater2digit.format(k)) + "." + String.valueOf(formater2digit.format(m));
		} else {
			if (m > 59) {
				m -= 60;
				k += 1;
			}
			str1 = String.valueOf(formater2digit.format(k)) + "." + String.valueOf(formater2digit.format(m));
		}

		return str1;
	}

	public static String setMinuteFormat(String paramString) {
		DecimalFormat formater2digit = new DecimalFormat("00");
		DecimalFormat formater2decimal = new DecimalFormat("00.00");
		String str1 = String.valueOf(formater2decimal.format(Float.parseFloat(paramString)));

		int i = str1.indexOf(".");
		int j = str1.length();

		String str2 = str1.substring(0, i);
		String str3 = str1.substring(i + 1, j);

		int k = Integer.parseInt(str2);
		int m = Integer.parseInt(str3);

		int n = 60 * m / 100;

		str1 = String.valueOf(formater2digit.format(k)) + "." + String.valueOf(formater2digit.format(n));

		return str1;
	}

	public static String manageMinute(String paramString) {
		DecimalFormat formater2decimal = new DecimalFormat("00.00");
		String str1 = String.valueOf(formater2decimal.format(Float.parseFloat(paramString)));

		int i = str1.indexOf(".");
		int j = str1.length();

		String str2 = str1.substring(0, i);
		String str3 = str1.substring(i + 1, j);

		int k = Integer.parseInt(str2);
		int m = Integer.parseInt(str3);

		int n = k * 60 + m;

		str1 = convertMinuteToHRS(n);

		return str1;
	}

	public static String getTimeMultipliedBy(String paramString, int paramInt) {
		int i = convertHrsToMinute(paramString);
		int j = i * paramInt;

		return convertMinuteToHRS(j);
	}

	public static String getTimeDividedBy(String paramString, int paramInt) {
		int i = convertHrsToMinute(paramString);
		int j = i / paramInt;

		return convertMinuteToHRS(j);
	}

	public static String getPercentTime(String paramString, int paramInt) {
		int i = convertHrsToMinute(paramString);

		int j = i * paramInt / 100;
		return convertMinuteToHRS(j);
	}
}
