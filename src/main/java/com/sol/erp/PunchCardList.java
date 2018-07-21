package com.sol.erp;

import java.awt.Color;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JComboBox;

import com.sol.erp.util.DBConnectionUtil;

public class PunchCardList extends JComboBox {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	

	Connection con;

	Statement stat;

	ResultSet rs;

	public PunchCardList(String paramString1, String paramString2, String paramString3) {
		setBackground(new Color(120, 120, 200));
		setForeground(Color.white);
		removeAllItems();
		String str1 = "%" + paramString3;
		String str2 = "%" + paramString1;
		String str3 = "%" + paramString2;
		try {
			con = DBConnectionUtil.getConnection();
			stat = con.createStatement();
			rs = stat.executeQuery("select PUNCHCARD_NO from HREMP_JOIN where CO_ID like '" + str2
					+ "' and BR_CODE like '" + str3 + "' and EMP_CODE like '" + str1 + "' order by PUNCHCARD_NO");
			rs.getMetaData().getColumnCount();
			rs.getRow();
			while (rs.next()) {
				String str4 = new String(rs.getString(1));
				addItem(str4);
			}
		} catch (Exception localException) {
			System.out.println(localException);
		}
	}

	public void refreshList(String paramString1, String paramString2, String paramString3) {
		removeAllItems();
		String str1 = "%" + paramString3;
		String str2 = "%" + paramString1;
		String str3 = "%" + paramString2;
		try {
			con = DBConnectionUtil.getConnection();
			stat = con.createStatement();
			rs = stat.executeQuery("select PUNCHCARD_NO from HREMP_JOIN where CO_ID like '" + str2
					+ "' and BR_CODE like '" + str3 + "' and EMP_CODE like '" + str1 + "'  order by PUNCHCARD_NO ");
			rs.getMetaData().getColumnCount();
			rs.getRow();
			while (rs.next()) {
				String str4 = new String(rs.getString(1));
				addItem(str4);
			}
		} catch (Exception localException) {
			System.out.println(localException);
		}
	}

	public String getText() {
		return String.valueOf(getSelectedItem());
	}

	public void setText(String paramString) {
		setSelectedItem(paramString);
	}
}
