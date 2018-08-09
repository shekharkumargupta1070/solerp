package com.sol.erp.util;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import com.sol.erp.constants.ApplicationConstants;

public class DateDifferencesUtil {

    //private static String fulldatestr = " ";
    private static String totaltimestr = "";

    // static DecimalFormat df2 = new DecimalFormat("00.00");

    static DecimalFormat dfmin3 = new DecimalFormat("000");
    static DecimalFormat dfd3 = new DecimalFormat("00.000");

    static String[] days = { "SUN", "MON", "TUE", "WED", "THU", "FRI", "SAT" };

    static Calendar gcal;
    static ArrayList<String> dateList = new ArrayList<String>();

    public static int getDayCount(String fromDateParam, String toDateParam) {
        String fromDateString = fromDateParam.substring(0, 2);
        String fromMonthString = fromDateParam.substring(3, 5);
        String fromYearString = fromDateParam.substring(7, 10);

        String toDateString = toDateParam.substring(0, 2);
        String toMonthString = toDateParam.substring(3, 5);
        String toYearString = toDateParam.substring(7, 10);

        int fromDate = Integer.parseInt(fromDateString);
        int fromMonth = Integer.parseInt(fromMonthString);
        int fromYear = Integer.parseInt(fromYearString);

        int toDate = Integer.parseInt(toDateString);
        int toMonth = Integer.parseInt(toMonthString);
        int toYear = Integer.parseInt(toYearString);

        GregorianCalendar fromDateCalendar = new GregorianCalendar(fromYear, fromMonth, fromDate);
        GregorianCalendar toDateCalendar = new GregorianCalendar(toYear, toMonth, toDate);

        int differenceCount = 0;
        while (toDateCalendar.get(1) != fromDateCalendar.get(1)) {
            differenceCount += 365;
            fromDateCalendar.add(6, 365);
        }
        differenceCount += toDateCalendar.get(6) - fromDateCalendar.get(6);

        return differenceCount + 1;
    }

    public static ArrayList<String> getDate(String fromDateParam, String toDateParam, int paramInt) {
        DecimalFormat formater2digit = new DecimalFormat(ApplicationConstants.DIGIT_2);

        String fromDateString = fromDateParam.substring(0, 2);
        String fromMonthString = fromDateParam.substring(3, 5);
        String fromYearString = fromDateParam.substring(6, 10);

        int date = Integer.parseInt(fromDateString);
        int month = Integer.parseInt(fromMonthString);
        int year = Integer.parseInt(fromYearString);

        int februaryDayCount = 0;

        int leapYear = year % 4;

        String monthAndYear = "/" + String.valueOf(formater2digit.format(month)) + "/" + String.valueOf(year);
        
        String fullDateString = "";
        for (int i2 = 0; i2 <= paramInt; i2++) {
            fullDateString = formater2digit.format(date) + monthAndYear;
            gcal = new GregorianCalendar(year, month - 1, date);
            String.valueOf(days[(gcal.get(7) - 1)]);

            if (month == 2) {
                if (leapYear == 0) {
                    februaryDayCount = 29;
                } else {
                    februaryDayCount = 28;
                }
            }

            if ((month == 4) || (month == 6) || (month == 9) || (month == 11)) {
                februaryDayCount = 30;
            }
            if ((month == 1) || (month == 3) || (month == 5) || (month == 7) || (month == 8) || (month == 10) || (month == 12)) {
                februaryDayCount = 31;
            }

            if (date == februaryDayCount) {
                date = 0;
                if (month == 12) {
                    month = 1;
                    year += 1;
                    monthAndYear = "/" + String.valueOf(formater2digit.format(month)) + "/" + String.valueOf(year);
                } else {
                    month += 1;
                    monthAndYear = "/" + String.valueOf(formater2digit.format(month)) + "/" + String.valueOf(year);
                }
            }
            date += 1;

            dateList.add(fullDateString);
        }

        return dateList;
    }

    public static String getTimeDiff(String fromTimeParam, String toTimeParam) {
        DecimalFormat formater2digit = new DecimalFormat(ApplicationConstants.DIGIT_2);
        DecimalFormat formater2decimal = new DecimalFormat(ApplicationConstants.DIGIT_DECIMAL_2);

        String fromTimeString = String.valueOf(formater2decimal.format(Float.parseFloat(fromTimeParam)));
        String toTimeString = String.valueOf(formater2decimal.format(Float.parseFloat(toTimeParam)));

        int fromHrsIndex = fromTimeString.indexOf(".");
        int fromMinutesIndex = fromTimeString.length();

        int toHrsIndex = toTimeString.indexOf(".");
        int toMinutsIndex = toTimeString.length();

        String fromTime = fromTimeString.substring(0, fromHrsIndex) + "." + fromTimeString.substring(fromHrsIndex + 1, fromMinutesIndex);
        String toTime = toTimeString.substring(0, toHrsIndex) + "." + toTimeString.substring(toHrsIndex + 1, toMinutsIndex);

        int fromHrs = Integer.parseInt(fromTimeString.substring(0, fromHrsIndex));
        int toHrs = Integer.parseInt(toTimeString.substring(0, toHrsIndex));

        float fromMinutes = Float.parseFloat(fromTimeString.substring(fromHrsIndex + 1, fromMinutesIndex));
        float toMinutes = Float.parseFloat(toTimeString.substring(toHrsIndex + 1, toMinutsIndex));

        float timDifferences = Float.parseFloat(toTime) - Float.parseFloat(fromTime);
        float totalHrs = Float.parseFloat(totaltimestr.substring(totaltimestr.length() - 5, totaltimestr.length() - 3));
        float totalMinutes = Float.parseFloat(totaltimestr.substring(totaltimestr.length() - 2, totaltimestr.length()));

        if (totalMinutes >= 60.0F) {
            totalMinutes *= 0.6F;
        }

        if (toHrs >= fromHrs) {
            String totalTimeDifferanceString = String.valueOf(formater2decimal.format(timDifferences));

            int totalTimeHrsIndex = totalTimeDifferanceString.indexOf(".");

            String totalHrsString = totalTimeDifferanceString.substring(0, totalTimeHrsIndex);
            String totalMinutesIndex = totalTimeDifferanceString.substring(totalTimeHrsIndex + 1, totalTimeDifferanceString.length());
            float actualTotalTime;
            if (fromMinutes <= toMinutes) {
                totalHrs = Float.parseFloat(totalHrsString) / 60.0F + Float.parseFloat(totalHrsString);

                totalMinutes = Float.parseFloat(totalMinutesIndex) % 60.0F;

                totaltimestr = String.valueOf(formater2digit.format(totalHrs)) + "."
                        + String.valueOf(formater2digit.format(totalMinutes));
                actualTotalTime = Float.parseFloat(totaltimestr);
                totaltimestr = String.valueOf(formater2decimal.format(actualTotalTime));
            }

            if (fromMinutes > toMinutes) {
                totalHrs = Float.parseFloat(totalHrsString);
                totalMinutes = 60.0F - fromMinutes + toMinutes;

                totaltimestr = String.valueOf(formater2digit.format(totalHrs)) + "."
                        + String.valueOf(formater2digit.format(totalMinutes));
                actualTotalTime = Float.parseFloat(totaltimestr);
                totaltimestr = String.valueOf(formater2decimal.format(actualTotalTime));
            }

            totaltimestr = String.valueOf(formater2digit.format(totalHrs)) + "." + String.valueOf(formater2digit.format(totalMinutes));
        }

        if (toHrs < fromHrs) {
            totaltimestr = "00.00";
        }
        return totaltimestr;
    }

    public static String getTimeDiff3(String paramString1, String paramString2) {
        DecimalFormat df = new DecimalFormat(ApplicationConstants.DIGIT_2);
        DecimalFormat formater2decimal = new DecimalFormat(ApplicationConstants.DIGIT_DECIMAL_2);
        String str1 = String.valueOf(dfd3.format(Float.parseFloat(paramString1)));
        String str2 = String.valueOf(dfd3.format(Float.parseFloat(paramString2)));

        int i = str1.indexOf(".");
        int j = str1.length();

        int k = str2.indexOf(".");
        int m = str2.length();

        String str3 = str1.substring(0, i) + "." + str1.substring(i + 1, j);
        String str4 = str2.substring(0, k) + "." + str2.substring(k + 1, m);

        int n = Integer.parseInt(str1.substring(0, i));
        int i1 = Integer.parseInt(str2.substring(0, k));

        String str5 = str1.substring(i + 1, j);
        String str6 = str2.substring(k + 1, m);

        float f1 = Float.parseFloat(str5);
        float f2 = Float.parseFloat(str6);

        float f3 = Float.parseFloat(str4) - Float.parseFloat(str3);
        float f4 = Float.parseFloat(totaltimestr.substring(totaltimestr.length() - 5, totaltimestr.length() - 3));
        float f5 = Float.parseFloat(totaltimestr.substring(totaltimestr.length() - 2, totaltimestr.length()));

        if (f5 >= 60.0F) {
            f5 *= 0.6F;
        }

        if (i1 >= n) {
            String str7 = String.valueOf(dfd3.format(f3));

            int i2 = str7.indexOf(".");
            str7.length();

            String str8 = str7.substring(0, i2);
            String str9 = str7.substring(i2 + 1, str7.length());
            float f6;
            if (f1 <= f2) {
                f4 = Float.parseFloat(str8) / 60.0F + Float.parseFloat(str8);
                f5 = Float.parseFloat(str9) % 60.0F;

                totaltimestr = String.valueOf(df.format(f4)) + "." + String.valueOf(dfmin3.format(f5));
                f6 = Float.parseFloat(totaltimestr);
                totaltimestr = String.valueOf(formater2decimal.format(f6));
            }

            if (f1 > f2) {
                f4 = Float.parseFloat(str8);
                f5 = 60.0F - f1 + f2;

                totaltimestr = String.valueOf(df.format(f4)) + "." + String.valueOf(dfmin3.format(f5));
                f6 = Float.parseFloat(totaltimestr);
                totaltimestr = String.valueOf(formater2decimal.format(f6));
            }

            totaltimestr = String.valueOf(df.format(f4)) + "." + String.valueOf(dfmin3.format(f5));
        }

        if (i1 < n) {
            totaltimestr = "00.000";
        }
        return totaltimestr;
    }

    public static void setTotalTime(String totalTimeString) {
        totaltimestr = totalTimeString;
    }
}