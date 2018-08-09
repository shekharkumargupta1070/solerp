package com.sol.erp;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import com.sol.erp.util.DBConnectionUtil;

public class query {
	

	Connection con;

	Statement stat;
	ResultSet rs;
	String message = "Not Ok";
	Object[][] data = (Object[][]) null;

	int row = 0;
	int col = 0;

	public void checkConnect() {
	}

	public void getResult() {
		try {
			con = DBConnectionUtil.getConnection();
			stat = con.createStatement();
			rs = stat.executeQuery(
					"Select project_no,drawing_no, COUNT(drawing_no) from DRAWINGNO group by project_no , drawing_no HAVING ( COUNT(drawing_no) > 1 )");

			while (rs.next()) {
				String str1 = new String(rs.getString(1));
				String str2 = new String(rs.getString(2));
				String str3 = new String(rs.getString(3));

				System.out.println(str1 + "\t" + str2 + "\t" + str3);
			}
		} catch (Exception localException) {
			System.out.println(localException);
		}
	}

	public void updateQuery() {
		try {
			con = DBConnectionUtil.getConnection();
			stat = con.createStatement();
			stat.executeUpdate("update HRTIMEMASTER set IN_TIME='0', out_time='0' WHERE TOTAL_TIME like '0'   ");
		} catch (Exception localException) {
			System.out.println(localException);
		}
	}

}
