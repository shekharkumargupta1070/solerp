package com.sol.erp;

import java.awt.Color;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JComboBox;

import com.sol.erp.util.DBConnectionUtil;

public class SalaryModeList extends JComboBox {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	

	Connection con;

	Statement stat;

	ResultSet rs;

	public SalaryModeList(String paramString1, String paramString2) {
		setBackground(Color.white);
		removeAllItems();
		try {
			con = DBConnectionUtil.getConnection();
			stat = con.createStatement();
			rs = stat.executeQuery("select REQ_MODES from HRCOMPANY_REQ_MODES where Company_Id='"
					+ paramString1 + "' and br_code='" + paramString2 + "' ");
			rs.getMetaData().getColumnCount();
			rs.getRow();
			while (rs.next()) {
				String str = new String(rs.getString(1));
				addItem(str);
			}
		} catch (Exception localException) {
			System.out.println(localException);
		}
	}

	public void refreshList(String paramString1, String paramString2) {
		removeAllItems();
		try {
			con = DBConnectionUtil.getConnection();
			stat = con.createStatement();
			rs = stat.executeQuery("select REQ_MODES from HRCOMPANY_REQ_MODES where Company_Id='"
					+ paramString1 + "' and br_code='" + paramString2 + "' ");
			rs.getMetaData().getColumnCount();
			rs.getRow();
			while (rs.next()) {
				String str = new String(rs.getString(1));
				addItem(str);
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
