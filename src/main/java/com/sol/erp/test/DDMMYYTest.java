package com.sol.erp.test;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DDMMYYTest {
	
	static String day = " ";
	static int date = 0;
	static int month = 0;
	static int year = 0;

	DecimalFormat df = new DecimalFormat("00");
	DecimalFormat df1 = new DecimalFormat("0000");

	String[] months = { "January", "February", "March", "April", "May", "June", "July", "August", "September",
			"October", "Novemeber", "December" };

	String[] days = { "MON", "TUE", "WED", "THU", "FRI", "SAT", "SUN" };
	Calendar gcal;

	public String getCurrentDate(String paramString) {
		java.util.Date localDate = new java.util.Date();
		SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat(paramString);

		String str;
		try {
			str = localSimpleDateFormat.format(localDate);
		} catch (IllegalArgumentException localIllegalArgumentException) {
			str = localIllegalArgumentException.getMessage();
		}

		return str;
	}

	public int getDate(String paramString) {
		String str1 = paramString;

		String str2 = str1.substring(0, 2);

		date = Integer.parseInt(str2);

		return date;
	}

	public int getMonth(String paramString) {
		String str1 = paramString;

		String str2 = str1.substring(3, 5);

		month = Integer.parseInt(str2);

		return month;
	}

	public String getNameofMonth(int paramInt) {
		return this.months[paramInt];
	}

	public int getYear(String paramString) {
		String str1 = paramString;

		String str2 = str1.substring(6, 10);

		year = Integer.parseInt(str2);

		return year;
	}

	public String NameOfDay(String paramString) {
		String str1 = paramString;
		String str2 = str1.substring(0, 2);
		String str3 = str1.substring(3, 5);
		String str4 = str1.substring(7, 10);

		int i = Integer.parseInt(str2);
		int j = Integer.parseInt(str3);
		int k = Integer.parseInt(str4);

		this.gcal = new java.util.GregorianCalendar(k, j - 1, i);
		this.gcal.get(6);
		this.gcal.get(3);
		int i1 = this.gcal.get(7);

		if (i1 >= 7) {
			i1 = 0;
		}

		day = this.days[i1];

		return day;
	}

	public int countDay(int paramInt1, int paramInt2, String paramString) {
		int i = 0;

		String.valueOf(this.df.format(paramInt1));
		String.valueOf(paramInt2);
		String.valueOf(this.df.format(paramInt1));
		String.valueOf(paramInt2);

		for (int j = 1; j <= 31; j++) {
			String str3 = NameOfDay(String.valueOf(this.df.format(j)) + "/" + String.valueOf(this.df.format(paramInt1))
					+ "/" + String.valueOf(this.df1.format(paramInt2)));

			if (str3.equalsIgnoreCase(paramString)) {
				i += 1;
			}
		}

		return i;
	}

	public int countDayTillDate(int paramInt1, int paramInt2, int paramInt3, String paramString) {
		int i = 0;

		String.valueOf(this.df.format(paramInt2));
		String.valueOf(paramInt3);
		String.valueOf(this.df.format(paramInt1));
		String.valueOf(this.df.format(paramInt2));
		String.valueOf(paramInt3);

		for (int j = 1; j <= paramInt1; j++) {
			String str3 = NameOfDay(String.valueOf(this.df.format(j)) + "/" + String.valueOf(this.df.format(paramInt2))
					+ "/" + String.valueOf(this.df1.format(paramInt3)));
			if (str3.equalsIgnoreCase(paramString)) {
				i += 1;
			}
		}

		return i;
	}

	public int getNumberOfDays(String paramString) {
		int i = 0;

		int j = getMonth(paramString);

		if ((j == 4) || (j == 6) || (j == 9) || (j == 11)) {
			i = 30;
		}

		if ((j == 1) || (j == 3) || (j == 5) || (j == 7) || (j == 8) || (j == 10) || (j == 12)) {
			i = 31;
		}

		int k = getYear(paramString);
		int m = k % 4;

		if (j == 2) {
			if (m == 0) {
				i = 29;
			} else {
				i = 28;
			}
		}

		System.out.println(i);

		return i;
	}

	public boolean isCurrentDate(String paramString1, String paramString2) {
		int i = getDate(paramString1);
		int j = getMonth(paramString1);
		int k = getYear(paramString1);

		int m = getDate(paramString2);
		int n = getMonth(paramString2);
		int i1 = getYear(paramString2);

		int i2 = i + j + k;
		int i3 = m + n + i1;

		int i4 = k - i1;

		if (i4 > 0) {
			i2 += i4 * 365;
			System.out.println(i2 + "\t" + i4);
		}

		if (i2 > i3) {
			return true;
		}

		return false;
	}

	public java.sql.Timestamp getCurrentDateTime() {
		java.sql.Timestamp localTimestamp = null;
		try {
			SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
			localTimestamp = new java.sql.Timestamp(
					localSimpleDateFormat.parse(getCurrentDate("dd/MM/yyyy")).getTime());
		} catch (Exception localException) {
			System.out.println(localException);
		}
		return localTimestamp;
	}

	public static void main(String[] paramArrayOfString) {
		DDMMYYTest localDDMMYY = new DDMMYYTest();

		System.out.println(localDDMMYY.isCurrentDate("12/01/2009", "31/12/2008"));

		System.out.println(localDDMMYY.getCurrentDateTime());
	}
}
