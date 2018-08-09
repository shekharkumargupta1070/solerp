package com.sol.erp;

import java.sql.ResultSet;
import java.util.Hashtable;

public class HashTable {
	Hashtable<String, String> hashtable = new Hashtable<String, String>();
	String info = null;
	String city;
	String stdcode;
	String state;

	public String cityInfo(String paramString1, String paramString2, String paramString3) {
		this.city = paramString1;
		this.stdcode = paramString2;
		this.state = paramString3;

		this.info = (" " + paramString1 + " \t " + paramString2 + " \t " + paramString3);
		return this.info;
	}

	public String getCity() {
		return this.city;
	}

	public String getSTDCode() {
		return this.stdcode;
	}

	public String getState() {
		return this.state;
	}

	public void populateDB() {
		try {
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
			java.sql.Connection localConnection = java.sql.DriverManager.getConnection(
					"jdbc:odbc:Driver={MicroSoft Access Driver (*.mdb)};DBQ=d:/solgrouperp1/erp/database/db.mdb", "",
					"");
			java.sql.Statement localStatement = localConnection.createStatement();
			ResultSet localResultSet = localStatement.executeQuery("select * from Emp_Register ");
			int j = 0;
			while (localResultSet.next()) {
				j += 1;
				String str1 = new String(localResultSet.getString(1));
				String str2 = new String(localResultSet.getString(2));
				this.hashtable.put(str1, str2);
				System.out.print("\t" + j + " : " + str1);
			}
		} catch (Exception localException) {
			System.out.println(localException);
		}
	}

	public void check() {
		System.out.println("\n*******************************************************************************\n");
		System.out.print("\t\t\tEnter Your Emp Code : ");
		java.io.BufferedReader localBufferedReader = new java.io.BufferedReader(
				new java.io.InputStreamReader(System.in));
		try {
			String str1 = String.valueOf(localBufferedReader.readLine());
			String str2 = "";

			if (str1.length() == 0) {
				System.out.println("Enter a Valid EmpCode.");
				return;
			}
			str2 = (String) this.hashtable.get(str1);
			System.out.println("City is located in  : " + str2);
			check();
		} catch (Exception localException) {
			System.out.println("Check " + localException);
		}
	}

}