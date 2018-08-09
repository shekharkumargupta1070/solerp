package com.sol.erp;

import java.awt.Color;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JComboBox;

import com.sol.erp.util.DBConnectionUtil;

public class EmpCodeList extends JComboBox {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

	Connection con;

	Statement stat;

	ResultSet rs;

	public EmpCodeList(String paramString1, String paramString2, String paramString3) {
		setBackground(new Color(120, 120, 200));
		setForeground(Color.white);
		removeAllItems();
		String str1 = "%" + paramString1;
		String str2 = "%" + paramString2;
		String str3 = "%" + paramString3;

		try {
			con = DBConnectionUtil.getConnection();
			stat = con.createStatement();
			rs = stat.executeQuery("select emp_code, FULL_NAME from HREMP_JOIN where CO_ID LIKE '" + str1
					+ "' and BR_CODE LIKE '" + str2 + "' and PUNCHCARD_NO LIKE '" + str3 + "' order by EMP_CODE");
			rs.getMetaData().getColumnCount();
			rs.getRow();
			while (rs.next()) {
				String str4 = new String(rs.getString(1));
				String str5 = new String(rs.getString(2));
				addItem(str4.toUpperCase() + " > " + str5.toUpperCase());
			}
		} catch (Exception localException) {
			System.out.println(localException);
		}
	}

	public void refreshList(String paramString1, String paramString2, String paramString3) {
		removeAllItems();
		String str1 = "%" + paramString1;
		String str2 = "%" + paramString2;
		String str3 = "%" + paramString3;

		try {
			con = DBConnectionUtil.getConnection();
			stat = con.createStatement();
			rs = stat.executeQuery("select emp_code, FULL_NAME from HREMP_JOIN where CO_ID LIKE '" + str1
					+ "' and BR_CODE LIKE '" + str2 + "' and PUNCHCARD_NO LIKE '" + str3 + "' order by EMP_CODE ");
			rs.getMetaData().getColumnCount();
			rs.getRow();
			while (rs.next()) {
				String str4 = new String(rs.getString(1));
				String str5 = new String(rs.getString(2));
				addItem(str4.toUpperCase() + " > " + str5.toUpperCase());
			}
		} catch (Exception localException) {
			System.out.println(localException);
		}
	}

	public String getText() {
		String str1 = String.valueOf(getSelectedItem());
		String str2 = str1.substring(0, 3);
		return str2.trim();
	}

	public void setText(String paramString) {
		addItem(paramString);
		setSelectedItem(paramString);
	}
}