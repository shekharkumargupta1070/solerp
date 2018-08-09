package com.sol.erp.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.sol.erp.constants.ApplicationConstants;

public class SaviorConnectionUtil {

    private static final Logger logger = Logger.getLogger(SaviorConnectionUtil.class.getName());
    
    
    private static final String SAVIOR_CONNECTION_URL = "jdbc:odbc:Driver={Microsoft Visual FoxPro Driver};UID=;PWD=; SourceDB="
            + ApplicationConstants.PUNCHCARD_DB_LOCATION
            + ";SourceType=DBF;Exclusive=No;BackgroundFetch=Yes;Collate=Machine;Null=Yes;Deleted=Yes";

    
    /*
    public static Connection getConnection() {
        Connection con = null;
        try {
            Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
            con = DriverManager.getConnection(SAVIOR_CONNECTION_URL);
            System.out.println("Connect to Savior...");
        } catch (Exception localException) {
            System.out.println("Savior Connect Problem :" + localException);
        }
        return con;
    }*/
    
    
    
    public static Connection getConnection(){
    	Connection con = null;
        try {
            //Class.forName("com.caigen.sql.dbf.DBFDriver");
        	Class.forName("com.hxtt.sql.dbf.DBFDriver");
            con = DriverManager.getConnection("jdbc:dbf:/"+ApplicationConstants.PUNCHCARD_DB_LOCATION);
            System.out.println("Connected to Savior...");
        } catch (Exception localException) {
            System.out.println("Savior Connect Problem :" + localException);
        }
        return con;
    }
    
    public static void closeConnection(ResultSet resultSet, Statement statement, Connection connection) {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                logger.log(Level.SEVERE, e.getLocalizedMessage());
            }
        }
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                logger.log(Level.SEVERE, e.getLocalizedMessage());
            }
        }
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                logger.log(Level.SEVERE, e.getLocalizedMessage());
            }
        }
    }
    
    public static void closeConnection(Statement statement, Connection connection) {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                logger.log(Level.SEVERE, e.getLocalizedMessage());
            }
        }
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                logger.log(Level.SEVERE, e.getLocalizedMessage());
            }
        }
    }
}
