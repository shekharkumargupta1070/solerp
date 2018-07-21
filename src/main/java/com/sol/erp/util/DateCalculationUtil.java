package com.sol.erp.util;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.sol.erp.constants.ApplicationConstants;

public class DateCalculationUtil {

    public static String getCurrentDate(String paramString) {
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

    public static int getDate(String paramString) {
        int date = 0;
        String str1 = paramString;
        String str2 = str1.substring(0, 2);
        date = Integer.parseInt(str2);

        return date;
    }

    public static int getMonth(String paramString) {
        int month = 0;
        String str1 = paramString;
        String str2 = str1.substring(3, 5);
        month = Integer.parseInt(str2);

        return month;
    }

    public static String getNameofMonth(int paramInt) {
        return ApplicationConstants.MONTHS[paramInt];
    }

    public static int getYear(String paramString) {
        int year = 0;
        String str1 = paramString;
        String str2 = str1.substring(6, 10);
        year = Integer.parseInt(str2);

        return year;
    }

    public static String NameOfDay(String paramString) {
        Calendar calendar;
        String day = " ";
        String str1 = paramString;
        String str2 = str1.substring(0, 2);
        String str3 = str1.substring(3, 5);
        String str4 = str1.substring(7, 10);

        int i = Integer.parseInt(str2);
        int j = Integer.parseInt(str3);
        int k = Integer.parseInt(str4);

        calendar = new java.util.GregorianCalendar(k, j - 1, i);
        calendar.get(6);
        calendar.get(3);
        int i1 = calendar.get(7);

        if (i1 >= 7) {
            i1 = 0;
        }

        day = ApplicationConstants.DAYS[i1];

        return day;
    }

    public static int countDay(int paramInt1, int paramInt2, String paramString) {
        DecimalFormat formater2digit = new DecimalFormat("00");
        DecimalFormat formater4digit = new DecimalFormat("0000");
        int i = 0;

        String.valueOf(formater2digit.format(paramInt1));
        String.valueOf(paramInt2);
        String.valueOf(formater2digit.format(paramInt1));
        String.valueOf(paramInt2);

        for (int j = 1; j <= 31; j++) {
            String str3 = NameOfDay(
                    String.valueOf(formater2digit.format(j)) + "/" + String.valueOf(formater2digit.format(paramInt1))
                            + "/" + String.valueOf(formater4digit.format(paramInt2)));

            if (str3.equalsIgnoreCase(paramString)) {
                i += 1;
            }
        }

        return i;
    }

    public static int countDayTillDate(int paramInt1, int paramInt2, int paramInt3, String paramString) {
        DecimalFormat formater2digit = new DecimalFormat("00");
        DecimalFormat formater4digit = new DecimalFormat("0000");
        int i = 0;

        String.valueOf(formater2digit.format(paramInt2));
        String.valueOf(paramInt3);
        String.valueOf(formater2digit.format(paramInt1));
        String.valueOf(formater2digit.format(paramInt2));
        String.valueOf(paramInt3);

        for (int j = 1; j <= paramInt1; j++) {
            String str3 = NameOfDay(
                    String.valueOf(formater2digit.format(j)) + "/" + String.valueOf(formater2digit.format(paramInt2))
                            + "/" + String.valueOf(formater4digit.format(paramInt3)));
            if (str3.equalsIgnoreCase(paramString)) {
                i += 1;
            }
        }

        return i;
    }

    public static int getNumberOfDays(String paramString) {
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

    public static boolean isCurrentDate(String paramString1, String paramString2) {
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

    public static java.sql.Timestamp getCurrentDateTime() {
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
}
