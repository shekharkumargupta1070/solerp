package com.sol.erp;

import java.sql.ResultSet;
import java.util.Hashtable;

public class Hash2 {
	Hashtable<String, String> hashtable = new Hashtable<String, String>();
	String empcode = null;
	String splstr = null;

	String[] splitedArray;

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
				String str3 = new String(localResultSet.getString(3));
				String str4 = new String(localResultSet.getString(4));
				this.hashtable.put(str1, str2 + "," + str3 + "," + str4);
				System.out.println(j + ". " + str1);
			}
		} catch (Exception localException) {
			System.out.println(localException);
		}
	}

	public void skStringSplit() {
		this.splstr = String.valueOf(this.hashtable.get(this.empcode));
		this.splitedArray = this.splstr.split(",");
		System.out.println("Split To : " + this.splstr);
		System.out.println("Splited : " + this.splitedArray[0]);
	}

	public String getName() {
		System.out.println(this.splitedArray[1]);
		return this.splitedArray[1];
	}

	public void check() {
		System.out.println("\n*******************************************************************************\n");
		System.out.print("\t\t\tEnter Your Emp Code : ");
		java.io.BufferedReader localBufferedReader = new java.io.BufferedReader(
				new java.io.InputStreamReader(System.in));
		try {
			this.empcode = String.valueOf(localBufferedReader.readLine());
			String str = "";

			if (this.empcode.length() == 0) {
				System.out.println("Enter a Valid EmpCode.");
				return;
			}
			if (this.empcode != null) {
				str = (String) this.hashtable.get(this.empcode);
				System.out.println("City is located in  : " + str);
				skStringSplit();
				check();
			} else {
				System.out.println("Not a Valid EmpCode.");
				System.exit(0);
			}
		} catch (Exception localException) {
			System.out.println("Check " + localException);
		}
	}

}
