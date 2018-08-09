package com.sol.erp;

import java.sql.ResultSet;
import java.util.Hashtable;

import com.sol.erp.util.DBConnectionUtil;

public class EmpDetailsTable {
	
	
	java.sql.Connection con;
	java.sql.Statement stat;

	ResultSet rs;

	Hashtable<String, String> nametable = new Hashtable<String, String>();
	Hashtable<String, String> posttable = new Hashtable<String, String>();

	String info = null;
	String code;
	String name;
	String post;
	String empcode = null;

	public void populateDB() {
		try {
			con = DBConnectionUtil.getConnection();
			stat = con.createStatement();
			rs = stat.executeQuery("select * from Emp_Register ");
			int j = 0;
			while (rs.next()) {
				j += 1;
				code = new String(rs.getString(1));
				name = new String(rs.getString(2));
				post = new String(rs.getString(3));

				nametable.put(code, name);
				posttable.put(code, post);

				System.out.print("\t" + j + " : " + code);
			}
		} catch (Exception localException) {
			System.out.println(localException);
		}
	}

	public String getEmpName() {
		return String.valueOf(nametable.get(empcode));
	}

	public String getPost() {
		return String.valueOf(posttable.get(empcode));
	}

	public void check() {
		System.out.println("\n*******************************************************************************\n");
		System.out.print("\t\t\tEnter Your Emp Code : ");
		java.io.BufferedReader localBufferedReader = new java.io.BufferedReader(
				new java.io.InputStreamReader(System.in));
		try {
			empcode = String.valueOf(localBufferedReader.readLine());

			if (empcode.length() == 0) {
				System.out.println("Enter a Valid EmpCode.");
				return;
			}
			if (empcode != null) {
				System.out.println("Name/Post  : " + getEmpName() + "\t" + getPost().toUpperCase());
				check();
			} else {
				System.out.println("Not a Valid EmpCode.");
				System.exit(0);
			}
		} catch (Exception localException) {
			System.out.println("Check " + localException);
		}
	}

	public void addRecord() {
		nametable.put("SUPER", "Shekhar");
		posttable.put("SUPER", "Software Admin.");
	}
}
