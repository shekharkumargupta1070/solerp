package com.sol.erp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sol.erp.dto.AttendanceDTO;
import com.sol.erp.dto.ShiftDefinitionDTO;
import com.sol.erp.util.DBConnectionUtil;

public class AttendanceDAO {

	public static ShiftDefinitionDTO getShiftDefinition(String companyId, String branchCode, String shiftCode) {

		ShiftDefinitionDTO shiftDefinitionDTO = null;
		Connection con = null;
		PreparedStatement stat = null;
		ResultSet rs = null;
		String query = "select COMPANY_ID, BRANCH_CODE, SHIFT, INTIME, OUTTIME,"
				+ " TOTALTIME, BREAK_OUT, BREAK_IN, TOTAL_BREAK, LATE_COUNT, OT_NOT_BEFORE,"
				+ " MIN_OT_VALUE, ABSENT_COUNT from HRSHIFT where COMPANY_ID = ? and BRANCH_CODE = ? ";

		try {
			con = DBConnectionUtil.getConnection();
			stat = con.prepareStatement(query);
			stat.setString(1, companyId);
			stat.setString(2, branchCode);
			rs = stat.executeQuery();

			while (rs.next()) {
				shiftDefinitionDTO = new ShiftDefinitionDTO();
				shiftDefinitionDTO.setCompanyId(rs.getString("COMPANY_ID"));
				shiftDefinitionDTO.setBranchId(rs.getString("BRANCH_CODE"));
				shiftDefinitionDTO.setShiftCode(rs.getString("SHIFT"));
				shiftDefinitionDTO.setInTime(rs.getString("INTIME"));
				shiftDefinitionDTO.setOutTime(rs.getString("OUTTIME"));
				shiftDefinitionDTO.setTotalTime(rs.getString("TOTALTIME"));
				shiftDefinitionDTO.setBreakOutTime(rs.getString("BREAK_OUT"));
				shiftDefinitionDTO.setBreakInTime(rs.getString("BREAK_IN"));
				shiftDefinitionDTO.setTotalBreakTime(rs.getString("TOTAL_BREAK"));
				shiftDefinitionDTO.setLateCount(rs.getString("LATE_COUNT"));
				shiftDefinitionDTO.setOtNotBefore(rs.getString("OT_NOT_BEFORE"));
				shiftDefinitionDTO.setMinimumOtValue(rs.getString("MIN_OT_VALUE"));
				shiftDefinitionDTO.setAbsentCount(rs.getString("ABSENT_COUNT"));
			}
		} catch (Exception localException) {
			System.out.println("showStandardTime : " + localException);
		} finally {
			DBConnectionUtil.closeConnection(rs, stat, con);
		}

		return shiftDefinitionDTO;
	}

	public static Map<String, List<AttendanceDTO>> getCompleteAttendance(String dateString) {
		Map<String, List<AttendanceDTO>> attendanceMap = new HashMap<String, List<AttendanceDTO>>();

		Connection con = null;
		PreparedStatement stat = null;
		ResultSet rs = null;
		String query = " SELECT DATE, PUNCHCARD, EMP_CODE, EMP_NAME, IN_TIME, OUT_TIME,"
				+ " TOTAL_TIME, PROJECT_NO, D_C, BREAK_HRS, OT_HRS, OT_REMARKS, DEDUCT_OT, DEDUCT_REMARKS, OT2"
				+ " FROM HRTIMEMASTER WHERE DATE = '" + dateString + "'"
				+ " AND EMP_CODE NOT IN (SELECT EMP_CODE FROM HREMP_JOIN WHERE STATUS='X')"
				+ " ORDER BY PUNCHCARD";
		
		System.out.println("Export Query: "+query);

		try {
			con = DBConnectionUtil.getConnection();
			stat = con.prepareStatement(query);
			rs = stat.executeQuery();

			while (rs.next()) {
				AttendanceDTO dto = new AttendanceDTO();
				dto.setDate(rs.getString("DATE"));
				dto.setPunchcard(rs.getString("PUNCHCARD"));
				dto.setEmpCode(rs.getString("EMP_CODE").trim());
				dto.setName(rs.getString("EMP_NAME"));
				dto.setInTime(rs.getString("IN_TIME"));
				dto.setOutTime(rs.getString("OUT_TIME"));
				dto.setTotal(rs.getString("TOTAL_TIME"));
				dto.setProjectNumber(rs.getString("PROJECT_NO"));
				dto.setDetailingChecking(rs.getString("D_C"));
				dto.setBreakHrs(rs.getString("BREAK_HRS"));
				dto.setOtHrs(rs.getString("OT_HRS"));
				dto.setOtYesNo(rs.getString("OT_REMARKS"));
				dto.setDeductOt(rs.getString("DEDUCT_OT"));
				dto.setDeductOtYesNo(rs.getString("DEDUCT_REMARKS"));
				dto.setOT2(rs.getString("OT2"));

				List<AttendanceDTO> list = new ArrayList<AttendanceDTO>();
				list.add(dto);
				if(attendanceMap.get(dto.getEmpCode()) == null) {
					attendanceMap.put(rs.getString("EMP_CODE").trim(), list);
				}else {
					attendanceMap.get(dto.getEmpCode()).add(dto);
				}
			}
		} catch (Exception localException) {
			System.out.println("showStandardTime : " + localException);
		}

		try {
			String bfaQuery = " SELECT DATE_MONTH, EMP_CODE, PROJECT_NO, DRG_NO, ITEM_CODE, TOTAL_TIME, REASON, REMARKS, REQ_NO"
					+ " FROM BFA_TIME WHERE DATE_MONTH = '" + dateString + "'";

			stat = con.prepareStatement(bfaQuery);
			rs = stat.executeQuery();

			while (rs.next()) {
				AttendanceDTO dto = new AttendanceDTO();
				dto.setDate(rs.getString("DATE_MONTH"));
				dto.setPunchcard("");
				dto.setEmpCode(rs.getString("EMP_CODE"));
				dto.setName("");
				dto.setInTime("");
				dto.setOutTime("");
				dto.setTotal(rs.getString("TOTAL_TIME"));
				dto.setProjectNumber(rs.getString("PROJECT_NO"));
				dto.setDetailingChecking(rs.getString("REASON"));
				dto.setBreakHrs("");
				dto.setOtHrs("");
				dto.setOtYesNo("");
				dto.setDeductOt("");
				dto.setDeductOtYesNo("");
				dto.setOT2("");

				attendanceMap.get(dto.getEmpCode().trim()).add(dto);
			}
		} catch (Exception localException) {
			System.out.println("showStandardTime : " + localException);
		} 

		try {
			String revisionQuery = " SELECT DATE_MONTH, EMP_CODE, PROJECT_NO, DRG_NO, ITEM_CODE, TOTAL_TIME, REASON, REMARKS, REQ_NO"
					+ " FROM REVISION_TIME WHERE DATE_MONTH = '" + dateString + "'";

			stat = con.prepareStatement(revisionQuery);
			rs = stat.executeQuery();

			while (rs.next()) {
				AttendanceDTO dto = new AttendanceDTO();
				dto.setDate(rs.getString("DATE_MONTH"));
				dto.setPunchcard("");
				dto.setEmpCode(rs.getString("EMP_CODE"));
				dto.setName("");
				dto.setInTime("");
				dto.setOutTime("");
				dto.setTotal(rs.getString("TOTAL_TIME"));
				dto.setProjectNumber(rs.getString("PROJECT_NO"));
				dto.setDetailingChecking(rs.getString("REASON"));
				dto.setBreakHrs("");
				dto.setOtHrs("");
				dto.setOtYesNo("");
				dto.setDeductOt("");
				dto.setDeductOtYesNo("");
				dto.setOT2("");

				attendanceMap.get(dto.getEmpCode().trim()).add(dto);
			}
		} catch (Exception localException) {
			System.out.println("showStandardTime : " + localException);
		} 
		finally {
			DBConnectionUtil.closeConnection(rs, stat, con);
		}
		return attendanceMap;
	}
}
