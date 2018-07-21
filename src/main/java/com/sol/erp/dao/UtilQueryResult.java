package com.sol.erp.dao;

import com.sol.erp.util.DBConnectionUtil;
import java.sql.*;

public class UtilQueryResult {

    static Connection con;
    static Statement stat;
    static ResultSet rs;

    static String message = "Not Ok";
    static Object data[][] = null;

    static int row = 0, col = 0;

    public Object getResult(String querystr) {
        String query = querystr;
        int i = 0, j = 0;

        try {
            con = DBConnectionUtil.getConnection();
            stat = con.createStatement();
            rs = stat.executeQuery(query);

            row = 0;
            col = rs.getMetaData().getColumnCount();

            for (; rs.next();) {
                row++;
            }
            System.out.println(row + "\t" + col);

            data = new Object[row][col];
            rs = stat.executeQuery(query);
            for (i = 0; rs.next(); i++) {
                for (j = 0; j < col; j++) {
                    data[i][j] = rs.getString(j + 1);

                    //System.out.print(companyData[i][j]+"\t");
                }
                //System.out.println();
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }

        return data[i - 1][j - 1];
        //return companyData;
    }

    public String Query(String querystr) {
        String query = querystr;
        try {
            con = DBConnectionUtil.getConnection();
            stat = con.createStatement();
            stat.executeQuery(query);
        } catch (Exception ex) {
            message = ex.getMessage().toString();
        }
        return message;
    }

    public String getMessage() {
        if (message.equalsIgnoreCase("No ResultSet was produced")) {
            message = "Succeed";
        }
        return message;
    }
}
