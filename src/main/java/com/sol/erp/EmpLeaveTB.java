package com.sol.erp;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Hashtable;

import com.sol.erp.util.DBConnectionUtil;

public class EmpLeaveTB {
	
	String splstr = null;
	String[] splitedArray;
	private static Connection con;

	private static Statement stat;
	private static ResultSet rs;

	public static String SQLtoDDMMYY(Date paramDate) {
		java.text.SimpleDateFormat localSimpleDateFormat = new java.text.SimpleDateFormat("dd/MM/yyyy");
		String dateString = null;
		try {
			dateString = localSimpleDateFormat.format(paramDate);
		} catch (IllegalArgumentException localIllegalArgumentException) {
			System.out.println(localIllegalArgumentException.getMessage());
		}
		return dateString;
	}

	public static Hashtable<String, String> leavetable = new Hashtable<String, String>();

	public static void populateDB() {
		System.out.println("Uploading EmpLeave DB...");

		try {
			con = DBConnectionUtil.getConnection();
			stat = con.createStatement();

			rs = stat
					.executeQuery("select emp_code, date, category, reason, approve_tl, approve_hr from HREmp_Leaves ");
			while (rs.next()) {
				String str1 = new String(rs.getString(1));
				Date localDate = rs.getDate(2);
				String str2 = String.valueOf(SQLtoDDMMYY(localDate));
				String str3 = new String(rs.getString(3));
				String str4 = new String(rs.getString(4));
				String str5 = new String(rs.getString(5));
				String str6 = new String(rs.getString(6));

				leavetable.put(str1, str2 + "," + str3 + "," + str4 + "," + str5 + "," + str6);

				System.out.println(str1 + "\t" + str2 + "\t" + str3 + "\t" + str4 + "\t" + str5 + "\t" + str6);
			}
		} catch (Exception localException) {
			System.out.println(localException);
		}
	}

	public void AdjustInfo(String paramString) {
		splitedArray = null;
		splstr = String.valueOf(leavetable.get(paramString));
		splitedArray = splstr.split(",");
	}

	public ArrayList<String> getDate(String paramString) {
		AdjustInfo(paramString);

		ArrayList<String> localArrayList = new ArrayList<String>();

		for (int i = 0; i < splitedArray.length; i++) {
			localArrayList.add(splitedArray[0]);
		}

		return localArrayList;
	}

	public ArrayList<String> getCategory() {
		ArrayList<String> localArrayList = new ArrayList<String>();

		for (int i = 0; i < splitedArray.length; i++) {
			localArrayList.add(splitedArray[1]);
		}

		return localArrayList;
	}

	public ArrayList<String> getReason() {
		ArrayList<String> localArrayList = new ArrayList<String>();

		for (int i = 0; i < splitedArray.length; i++) {
			localArrayList.add(splitedArray[2]);
		}

		return localArrayList;
	}

	public ArrayList<String> getApproved() {
		ArrayList<String> localArrayList = new ArrayList<String>();

		for (int i = 0; i < splitedArray.length; i++) {
			localArrayList.add(splitedArray[3]);
		}

		return localArrayList;
	}

	public int getRowSize() {
		return splitedArray.length;
	}
}
