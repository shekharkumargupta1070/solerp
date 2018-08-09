package com.sol.erp;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;

import com.sol.erp.util.DBConnectionUtil;

public class TLCode {
	
	Connection con;
	Statement stat;
	ResultSet rs;
	HashMap<String, Integer> hs = new HashMap<String, Integer>();

	public void query() {
		try {
			con = DBConnectionUtil.getConnection();
			stat = con.createStatement();
			rs = stat.executeQuery(
					"select distinct(Emp_code) From HREMP_JOIN where Desig ='Team Leader' OR Desig='Project Manager' ");
			while (rs.next()) {
				String str = new String(rs.getString(1));
				hs.put(str, new Integer(20));
				System.out.println(hs);
			}
		} catch (Exception localException) {
			System.out.println(localException);
		}
	}
}
