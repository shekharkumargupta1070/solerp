package com.sol.erp;

import java.io.BufferedReader;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Hashtable;

import com.sol.erp.util.DBConnectionUtil;

public class EmpTB {

	private EmpTB(){
		
	}
	
	private static Connection con;

	private static Statement stat;

	private static ResultSet rs;

	public static Hashtable<String, String> nametable = new Hashtable<String, String>();
	public static Hashtable<String, String> depttable = new Hashtable<String, String>();
	public static Hashtable<String, String> desigtable = new Hashtable<String, String>();
	public static Hashtable<String, String> jobtable = new Hashtable<String, String>();
	public static Hashtable<String, String> dojtable = new Hashtable<String, String>();
	public static Hashtable<String, String> probationtable = new Hashtable<String, String>();
	public static Hashtable<String, String> reqmodetable = new Hashtable<String, String>();
	public static Hashtable<String, String> salarytable = new Hashtable<String, String>();
	public static Hashtable<String, String> punchcardtable = new Hashtable<String, String>();
	public static Hashtable<String, String> coidtable = new Hashtable<String, String>();
	public static Hashtable<String, String> brcodetable = new Hashtable<String, String>();
	public static Hashtable<String, String> usertypetable = new Hashtable<String, String>();

	public static Hashtable<String, String> firstnametable = new Hashtable<String, String>();
	public static Hashtable<String, String> middlenametable = new Hashtable<String, String>();
	public static Hashtable<String, String> lastnametable = new Hashtable<String, String>();
	public static Hashtable<String, String> factortable = new Hashtable<String, String>();
	public static Hashtable<String, String> otpermissiontable = new Hashtable<String, String>();
	public static String code;
	public static String fullname;
	public static String firstname;
	public static String middlename;
	public static String lastname;
	public static String dept;
	public static String desig;
	public static String job;
	public static String doj;
	public static String prob;
	public static String reqmode;
	public static String salary;
	public static String punchcard;
	public static String coid;
	public static String brcode;
	public static String usertype;
	public static String factor;
	public static String otpermission;
	public static String info = null;
	public static String empcode = null;

	public static void populateDB() {
		System.out.println("Uploading EmpTB...");

		factortable.put("DETAILING", "0");
		factortable.put("CHECKING", "0");
		factortable.put("OTHER", "0");
		factortable.put("TOTAL", "0");
		factortable.put("0", "0");
		factortable.put("null", "0");

		jobtable.put("DETAILING", "Detailer");
		jobtable.put("CHECKING", "Checker");
		jobtable.put("OTHER", "Other");
		jobtable.put("TOTAL", "Other");
		jobtable.put("0", "Other");
		jobtable.put("null", "Other");

		try {
			con = DBConnectionUtil.getConnection();
			stat = con.createStatement();
			rs = stat.executeQuery(
					"select h.Emp_Code, h.full_name, h.Dept, h.Desig, e.job, h.Doj, h.req_mode, h.Basic_Salary, h.Punchcard_No, h.CO_ID, h.BR_CODE, p.User_Type, h.prob, e.factor, h.OT from HREMP_JOIN h, Password p, EMP_STATUS e where h.emp_code=p.user and e.emp_code=p.user ");
			rs.getMetaData().getColumnCount();
			int j = 0;
			while (rs.next()) {
				j += 1;
				code = new String(rs.getString(1));
				fullname = new String(rs.getString(2));

				dept = new String(rs.getString(3));
				desig = new String(rs.getString(4));
				job = new String(rs.getString(5));
				doj = new String(rs.getString(6));
				reqmode = new String(rs.getString(7));
				salary = new String(rs.getString(8));
				punchcard = new String(rs.getString(9));
				coid = new String(rs.getString(10));
				brcode = new String(rs.getString(11));
				usertype = new String(rs.getString(12));
				prob = new String(rs.getString(13));
				factor = new String(rs.getString(14));
				otpermission = new String(rs.getString(15));

				nametable.put(code, fullname);
				depttable.put(code, dept);
				desigtable.put(code, desig);
				jobtable.put(code, job);
				dojtable.put(code, doj);
				reqmodetable.put(code, reqmode);
				salarytable.put(code, salary);
				punchcardtable.put(code, punchcard);
				coidtable.put(code, coid);
				brcodetable.put(code, brcode);
				usertypetable.put(code, usertype);
				probationtable.put(code, prob);
				factortable.put(code, factor);
				otpermissiontable.put(code, otpermission);

				System.out.println(j + " " + code + "\t" + usertype + "\t" + desig + "\t" + factor);
			}
		} catch (Exception localException) {
			System.out.println("Populate DB : " + localException);
		}
	}

	public static String getEmpName(String paramString) {
		return String.valueOf(nametable.get(paramString));
	}

	public static String getDept(String paramString) {
		return String.valueOf(depttable.get(paramString));
	}

	public static String getDesig(String paramString) {
		return String.valueOf(desigtable.get(paramString));
	}

	public static String getJob(String paramString) {
		return String.valueOf(jobtable.get(paramString));
	}

	public static String getDOJ(String paramString) {
		return String.valueOf(dojtable.get(paramString));
	}

	public static String getProbationDate(String paramString) {
		return String.valueOf(probationtable.get(paramString));
	}

	public static String getReqMode(String paramString) {
		return String.valueOf(reqmodetable.get(paramString));
	}

	public static String getSalary(String paramString) {
		return String.valueOf(salarytable.get(paramString));
	}

	public static String getPunchcardNo(String paramString) {
		return String.valueOf(punchcardtable.get(paramString));
	}

	public static String getCompanyID(String paramString) {
		return String.valueOf(coidtable.get(paramString));
	}

	public static String getBranchCode(String paramString) {
		return String.valueOf(brcodetable.get(paramString));
	}

	public static String getUserType(String paramString) {
		return String.valueOf(usertypetable.get(paramString));
	}

	public static String getCurrentFactor(String paramString) {
		return String.valueOf(factortable.get(paramString));
	}

	public static String getOTPermission(String paramString) {
		return String.valueOf(otpermissiontable.get(paramString));
	}

	public static void check() {
		System.out.println("\n*******************************************************************************\n");
		System.out.print("\t\t\tEnter Your Emp Code : ");
		BufferedReader localBufferedReader = new BufferedReader(new java.io.InputStreamReader(System.in));
		try {
			empcode = String.valueOf(localBufferedReader.readLine());

			if (empcode.length() == 0) {
				System.out.println("Enter a Valid EmpCode.");
				return;
			}
			if (empcode != null) {
				System.out.println("DETAILS  : " + getEmpName(empcode).toUpperCase() + "\t"
						+ getDesig(empcode).toUpperCase() + "\t" + getSalary(empcode).toUpperCase() + "\t"
						+ getCompanyID(empcode).toUpperCase() + "\t" + getPunchcardNo(empcode).toUpperCase() + "\t"
						+ getUserType(empcode).toUpperCase() + "\t" + getCurrentFactor(empcode) + "\t"
						+ getOTPermission(empcode));
				check();
			} else {
				System.out.println("Not a Valid EmpCode.");
				System.exit(0);
			}
		} catch (Exception localException) {
			System.out.println("Check " + localException);
		}
	}

	public static void addRecord() {
		nametable.put("SUPER", "Shekhar");
		desigtable.put("SUPER", "Software Admin.");
	}

}
