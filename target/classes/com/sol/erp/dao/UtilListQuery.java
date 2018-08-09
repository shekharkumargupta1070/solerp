package com.sol.erp.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import com.sol.erp.util.DBConnectionUtil;

public class UtilListQuery {

    private static Connection con;
    private static Statement stat;
    private static ResultSet rs;

    private static String message;
    

    public static ArrayList<String> skList(String column, String table) {
    	ArrayList<String> ar = new ArrayList<String>();
        String tablestr = table;
        String columnstr = column;
        String query = "select distinct(" + columnstr + ") from " + tablestr + " order by " + columnstr;
        try {
            con = DBConnectionUtil.getConnection();
            stat = con.createStatement();
            rs = stat.executeQuery(query);
            while (rs.next()) {
                String str = new String(rs.getString(1));
                ar.add(str);
                System.out.println(ar);
            }
        } catch (Exception ex) {
            message = ex.getMessage().toString();
        }

        return ar;

    }

    public static ArrayList<String> skDynamicList(String column, String table, String clauseColumn, String clauseArg) {
    	ArrayList<String> ar = new ArrayList<String>();
        String tablestr = table;
        String columnstr = column;
        String clsclmstr = clauseColumn;
        String clsargstr = clauseArg;

        String query = "select distinct(" + columnstr + ") from " + tablestr + " where " + clsclmstr + " like '" + clsargstr + "'  order by " + columnstr;
        try {
            stat = con.createStatement();
            rs = stat.executeQuery(query);
            while (rs.next()) {
                String str = new String(rs.getString(1));
                ar.add(str);
                System.out.println(ar);
            }
        } catch (Exception ex) {
            message = ex.getMessage().toString();
        }

        return ar;

    }

    public String getMessage() {
        return message;
    }
}
