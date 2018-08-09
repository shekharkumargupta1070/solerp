package com.sol.erp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.sol.erp.dto.QuotationDTO;
import com.sol.erp.util.DBConnectionUtil;

public class QuotationDAO {

	private static final Logger logger = Logger.getLogger(QuotationDAO.class.getName());

	public static List<QuotationDTO> findAllByYear(String year) {
		String query = "select"
				+ " q.DATE,"
				+ " q.QUOTE_NO,"
				+ " q.PROJECT_NAME,"
				+ " q.PROJECT_NO,"
				+ " q.CLIENT_NAME,"
				+ " sum(em.SHEET_QTY) as TOTAL_QUOTED_SHEETS,"
				+ " sum(em.TOTAL_TIME) as TOTAL_QUOTED_HRS," 
				+ " sum(e.SHEET_QTY) as TOTAL_ACTUAL_SHEETS,"
				+ " sum(e.TOTAL_TIME) as TOTAL_ACTUAL_HRS"
				+ " from QUOTATION_LIST q"
				+ " left join ESTIMATION_MP as em on q.PROJECT_NO = em.PROJECT_NO"
				+ " left join ESTIMATION as e on q.PROJECT_NO = e.PROJECT_NO" 
				+ " where q.DATE like ? and q.PROJECT_NAME not like ?"
				+ " GROUP BY q.QUOTE_NO";

		System.out.println("Query: "+query);
		
		List<QuotationDTO> quotationList = new ArrayList<QuotationDTO>();
		Connection con = null;
		PreparedStatement stat = null;
		ResultSet rs = null;
		try {
			con = DBConnectionUtil.getConnection();
			stat = con.prepareStatement(query);
			stat.setString(1, "%" + year);
			stat.setString(2, "%XXXXX");
			rs = stat.executeQuery();
			while (rs.next()) {
				QuotationDTO quotation = new QuotationDTO();
				quotation.setDate(rs.getString("q.DATE"));
				quotation.setQuotationNumber(rs.getLong("q.QUOTE_NO"));
				quotation.setProjectName(rs.getString("q.PROJECT_NAME"));
				quotation.setProjectNumber(rs.getString("PROJECT_NO"));
				quotation.setClientName(rs.getString("q.CLIENT_NAME"));
				quotation.setTotalQuotedSheets(rs.getInt("TOTAL_QUOTED_SHEETS"));
				quotation.setTotalQuotedHrs(rs.getInt("TOTAL_QUOTED_HRS"));
				quotation.setActualTotalSheets(rs.getInt("TOTAL_ACTUAL_SHEETS"));
				quotation.setActualTotalHrs(rs.getInt("TOTAL_ACTUAL_HRS"));

				quotationList.add(quotation);
				System.out.println(quotation);
			}
		} catch (SQLException e) {
			logger.log(Level.SEVERE, e.getMessage());
		} finally {
			DBConnectionUtil.closeConnection(stat, con);
		}
		return quotationList;
	}
}
