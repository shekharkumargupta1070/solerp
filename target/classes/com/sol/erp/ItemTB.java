package com.sol.erp;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Hashtable;

import com.sol.erp.util.DBConnectionUtil;

public class ItemTB {
	

	private static Connection con;
	private static Statement stat;
	private static ResultSet rs;

	public static Hashtable<String, String> nametable = new Hashtable<String, String>();

	public static String code;
	public static String name;

	public static void populateDB() {
		System.out.println("Uploading EmpTB...");

		nametable.put("Shekhar", "kumar");

		try {
			con = DBConnectionUtil.getConnection();
			stat = con.createStatement();
			rs = stat.executeQuery("select item_code, item_name from itemcode");
			rs.getMetaData().getColumnCount();

			while (rs.next()) {
				code = rs.getString(1);
				name = rs.getString(2);

				nametable.put(code.trim(), name.trim());
			}
		} catch (Exception localException) {
			System.out.println("Populate DB : " + localException);
		}
	}

	public static String getItemName(String paramString) {
		return String.valueOf(nametable.get(paramString));
	}

}
