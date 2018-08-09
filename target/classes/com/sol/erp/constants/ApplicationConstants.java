/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sol.erp.constants;

/**
 *
 * @author shekharkumar
 */
public class ApplicationConstants {
    
    
    public static final String DB_URL = "";
    public static final String DB_DRIVER = "com.mysql.jdbc.Driver";
    public static final String DB_NAME = "erpdb";
    public static final String DB_USER = "root";
    public static final String DB_PASSWORD = "adminadmin";
    
    public static final String IMAGE_PATH = "image/";
    public static final String USER_HOME = System.getProperty("user.home");
    public static final String CONFIG_FILE_NAME = "sol_config";
    
    public static final String SERVER_IP = "SERVER_IP";
    public static final String SERVER_PORT = "SERVER_PORT";
    
    public static final String PUNCHCARD_DB_LOCATION = "c:/savior60";
    
    public static final String DIGIT_1 = "0";
    public static final String DIGIT_2 = "00";
    public static final String DIGIT_3 = "000";
    public static final String DIGIT_4 = "0000";
    
    public static final String DIGIT_DECIMAL_2 = "00.00";
    public static final String DIGIT_DECIMAL_3 = "00.00";
    
    public static final String DD_MM_YYYY = "dd/MM/yyyy";
    public static final String MMM_YYYY = "MMM/yyyy";
    
    
    //Months constants
    public static final String MONTH_JANUARY = "January";
    public static final String MONTH_FEBRUARY = "February";
    public static final String MONTH_MARCH = "March";
    public static final String MONTH_APRIL = "April";
    public static final String MONTH_MAY = "May";
    public static final String MONTH_JUNE = "June";
    public static final String MONTH_JULY = "July";
    public static final String MONTH_AUGUST = "August";
    public static final String MONTH_SEPTEMBER = "September";
    public static final String MONTH_OCTOBER = "October";
    public static final String MONTH_NOVEMEBER = "November";
    public static final String MONTH_DECEMBER = "December";
    
    public static final String DAY_MONDAY = "MON";
    public static final String DAY_TUESDAY = "TUE";
    public static final String DAY_WEDNESDAY = "WED";
    public static final String DAY_THURSDAY = "THU";
    public static final String DAY_FRIDAY = "FRI";
    public static final String DAY_SATURDAY = "SAT";
    public static final String DAY_SUNDAY = "SUN";
    
    public static final String[] MONTHS = { 
            ApplicationConstants.MONTH_JANUARY,
            ApplicationConstants.MONTH_FEBRUARY,
            ApplicationConstants.MONTH_MARCH,
            ApplicationConstants.MONTH_APRIL,
            ApplicationConstants.MONTH_MAY,
            ApplicationConstants.MONTH_JUNE,
            ApplicationConstants.MONTH_JULY,
            ApplicationConstants.MONTH_AUGUST,
            ApplicationConstants.MONTH_SEPTEMBER,
            ApplicationConstants.MONTH_OCTOBER,
            ApplicationConstants.MONTH_NOVEMEBER,
            ApplicationConstants.MONTH_DECEMBER
    };
    
    public static final String[] DAYS = { 
            ApplicationConstants.DAY_MONDAY,
            ApplicationConstants.DAY_TUESDAY,
            ApplicationConstants.DAY_WEDNESDAY,
            ApplicationConstants.DAY_THURSDAY,
            ApplicationConstants.DAY_FRIDAY,
            ApplicationConstants.DAY_SATURDAY,
            ApplicationConstants.DAY_SUNDAY
    };
        
}
