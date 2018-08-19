package com.sol.erp.dao;

import com.sol.erp.EmpTB;
import com.sol.erp.constants.ApplicationConstants;
import com.sol.erp.dto.ProjectDTO;
import com.sol.erp.util.DBConnectionUtil;
import com.sol.erp.util.formater.SolDateFormatter;

import java.sql.*;
import java.sql.Date;
import java.text.DecimalFormat;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProjectDAO {

    private static Logger logger = Logger.getLogger(ProjectDAO.class.getName());

    public static Map<Long, ProjectDTO> projectMap = new HashMap<Long, ProjectDTO>();

    public static void chacheInMemory() {
        logger.log(Level.INFO, "Connecting...");

        Connection con = null;
        Statement stat = null;
        ResultSet rs = null;
        try {
            con = DBConnectionUtil.getConnection();
            stat = con.createStatement();
            rs = stat.executeQuery(
                    "select PROJECT_NO, PROJECT_NAME, CO_CODE, CLIENT_NAME, START, NEW_FINAL_DATE from PROJECT_CO");

            logger.log(Level.INFO, "Connected!");
            logger.log(Level.INFO, "Chaching Projects in memory...");
            while (rs.next()) {
                ProjectDTO projectDTO = new ProjectDTO();
                projectDTO.setProjectNumber(rs.getLong("PROJECT_NO"));
                projectDTO.setProjectName(rs.getString("PROJECT_NAME"));
                projectDTO.setTeamLeader(rs.getString("CO_CODE"));
                projectDTO.setClientName(rs.getString("CLIENT_NAME"));
                projectDTO.setStartDate(rs.getString("START"));
                projectDTO.setFinalDate(rs.getString("NEW_FINAL_DATE"));

                projectMap.put(rs.getLong("PROJECT_NO"), projectDTO);
                // System.out.println(projectDTO);
            }
            logger.log(Level.INFO, "Chaching Projects in memory done!");
            logger.log(Level.INFO, "Total Project: " + projectMap.size());
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage());
        } finally {
            DBConnectionUtil.closeConnection(rs, stat, con);
        }
    }

    public static ProjectDTO findByProjectNumber(long projectNumber) {
        Connection con = null;
        PreparedStatement stat = null;
        ResultSet rs = null;
        ProjectDTO projectDTO = null;
        try {
            String query = "select PROJECT_NO, PROJECT_NAME, CO_CODE, CLIENT_NAME, START, NEW_FINAL_DATE from PROJECT_CO"
                    + " where PROJECT_NO = ? ";
            con = DBConnectionUtil.getConnection();
            stat = con.prepareStatement(query);
            stat.setLong(1, projectNumber);
            rs = stat.executeQuery();

            logger.log(Level.INFO, "Connected!");
            logger.log(Level.INFO, "Chaching Projects in memory...");
            while (rs.next()) {
                projectDTO = new ProjectDTO();
                projectDTO.setProjectNumber(rs.getLong("PROJECT_NO"));
                projectDTO.setProjectName(rs.getString("PROJECT_NAME"));
                projectDTO.setTeamLeader(rs.getString("CO_CODE"));
                projectDTO.setClientName(rs.getString("CLIENT_NAME"));
                projectDTO.setStartDate(rs.getString("START"));
                projectDTO.setFinalDate(rs.getString("NEW_FINAL_DATE"));
                System.out.println("findByProjectNumber: " + projectDTO);
            }
            logger.log(Level.INFO, "Chaching Projects in memory done!");
            logger.log(Level.INFO, "Total Project: " + projectMap.size());
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage());
        } finally {
            DBConnectionUtil.closeConnection(rs, stat, con);
        }

        return projectDTO;
    }

    public static float getEmpFactorByProject(String employeeCode, String projectNumber) {
        float factor = 0.0F;

        Connection con = null;
        PreparedStatement stat = null;
        ResultSet rs = null;
        try {
            con = DBConnectionUtil.getConnection();
            stat = con.prepareStatement("select FACTOR from PROJECTMANPOWER where EMP_CODE = ? AND PROJECT_NO = ?");
            stat.setString(1, employeeCode);
            stat.setString(2, projectNumber);
            rs = stat.executeQuery();

            while (rs.next()) {
                factor = Float.parseFloat(String.valueOf(rs.getString(1)));
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage());
        } finally {
            DBConnectionUtil.closeConnection(rs, stat, con);
        }
        return factor;
    }

    public static int getCompletedDrgBetweenPeriod(String paramString, Date paramDate1, Date paramDate2) {
        int i = 0;

        Connection con = null;
        PreparedStatement stat = null;
        ResultSet rs = null;
        try {
            con = DBConnectionUtil.getConnection();
            stat = con.prepareStatement(
                    "select count(drg_no) from timesheet where project_no like ? and drgstatus1 like ? and DATE2 between ? and ? ");
            stat.setString(1, paramString);
            stat.setString(2, "%check%");
            stat.setDate(3, paramDate1);
            stat.setDate(4, paramDate2);
            rs = stat.executeQuery();

            while (rs.next()) {
                i = rs.getInt(1);
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage());
        } finally {
            DBConnectionUtil.closeConnection(rs, stat, con);
        }
        return i;
    }

    public static Date getProjectMinDate(String paramString) {
        paramString = "%" + paramString + "%";
        Date localDate = null;

        Connection con = null;
        PreparedStatement stat = null;
        ResultSet rs = null;
        try {
            con = DBConnectionUtil.getConnection();
            stat = con.prepareStatement("select min(date) from hrtimemaster where PROJECT_NO like ? ");
            stat.setString(1, paramString);
            rs = stat.executeQuery();

            while (rs.next()) {
                localDate = rs.getDate(1);
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage());
        } finally {
            DBConnectionUtil.closeConnection(rs, stat, con);
        }
        return localDate;
    }

    public static Date getProjectMaxDate(String paramString) {
        paramString = "%" + paramString + "%";

        Date localDate = null;

        Connection con = null;
        PreparedStatement stat = null;
        ResultSet rs = null;
        try {
            con = DBConnectionUtil.getConnection();
            stat = con.prepareStatement("select max(date) from hrtimemaster where PROJECT_NO like ? ");
            stat.setString(1, paramString);
            rs = stat.executeQuery();

            while (rs.next()) {
                localDate = rs.getDate(1);
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage());
        } finally {
            DBConnectionUtil.closeConnection(rs, stat, con);
        }

        return localDate;
    }

    public static ProjectDTO getProject(String projectNumber) {
        return projectMap.get(Long.parseLong(projectNumber.trim()));
    }

    public static String getProjectName(String projectNumber) {
        return projectMap.get(Long.parseLong(projectNumber.trim())).getProjectName();
    }

    public static String getClientName(String projectNumber) {
        return projectMap.get(Long.parseLong(projectNumber.trim())).getClientName();
    }

    public static String getTeamLeader(String projectNumber) {
        if (projectMap.containsKey(Long.parseLong(projectNumber.trim()))) {
            return projectMap.get(Long.parseLong(projectNumber.trim())).getTeamLeader();
        } else {
            return null;
        }
    }

    public static void setTeamLeader(long projectNumber, String teamLeader) {
        if (projectMap.containsKey(projectNumber)) {
            projectMap.get(projectNumber).setTeamLeader(teamLeader);
        } else {
            ProjectDTO dto = new ProjectDTO();
            dto.setProjectNumber(projectNumber);
            dto.setTeamLeader(teamLeader);
            projectMap.put(projectNumber, dto);
        }
    }

    public static String getStartDate(String projectNumber) {
        return projectMap.get(Long.parseLong(projectNumber.trim())).getStartDate();
    }

    public static String getFinalDate(String projectNumber) {
        return projectMap.get(Long.parseLong(projectNumber.trim())).getFinalDate();
    }

    public static int getCountSheet(String paramString1, String paramString2) {
        paramString1 = "%" + paramString1 + "%";
        paramString2 = "%" + paramString2 + "%";

        String str = "0";

        Connection con = null;
        PreparedStatement stat = null;
        ResultSet rs = null;
        try {
            con = DBConnectionUtil.getConnection();
            stat = con.prepareStatement(
                    "select SUM(SHEET_QTY) from ESTIMATION where PROJECT_NO like ? and ITEM_CODE like ? ");
            stat.setString(1, paramString1);
            stat.setString(2, paramString2);
            rs = stat.executeQuery();

            while (rs.next()) {
                str = new String(rs.getString(1));
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage());
        } finally {
            DBConnectionUtil.closeConnection(rs, stat, con);
        }
        int i = Integer.parseInt(str);

        return i;
    }

    public static float getProjectEstimatedHrs(String paramString) {
        paramString = "%" + paramString;

        float f1 = 0.0F;

        Connection con = null;
        PreparedStatement stat = null;
        ResultSet rs = null;
        try {
            con = DBConnectionUtil.getConnection();
            stat = con.prepareStatement("select TOTAL_TIME,ITEM_CODE from ESTIMATION where PROJECT_NO like ? ");
            stat.setString(1, paramString);

            rs = stat.executeQuery();

            while (rs.next()) {
                float f2 = rs.getFloat(1);
                String str2 = new String(rs.getString(2));

                float f3 = SolDateFormatter.convertHrsToMinute2(String.valueOf(f2));
                f1 += f3;
                System.out.println(str2 + "\t" + f2 + "\t" + f3 + "\t" + f1);
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage());
        } finally {
            DBConnectionUtil.closeConnection(rs, stat, con);
        }
        return Float.parseFloat(SolDateFormatter.convertMinuteToHRS2(f1));
    }

    public static Float getProjectCompletedHrsByTeamLeader(String paramString, Date paramDate1, Date paramDate2) {
        paramString = "%" + paramString + "%";

        float f1 = 0.0F;

        Connection con = null;
        PreparedStatement stat = null;
        ResultSet rs = null;

        try {
            String str1 = "SELECT  distinct(drawing_no) FROM checking where project_no like ? and  DATE2 between ? and ? ";

            f1 = 0.0F;
            con = DBConnectionUtil.getConnection();
            stat = con.prepareStatement(str1);
            stat.setString(1, paramString);
            stat.setDate(2, paramDate1);
            stat.setDate(3, paramDate2);
            rs = stat.executeQuery();

            while (rs.next()) {
                String str2 = rs.getString(1);

                float f2 = getDrawingEstimatedHrs(paramString, str2, "TOTAL_TIME");
                float f3 = SolDateFormatter.convertHrsToMinute2(String.valueOf(f2));
                f1 += f3;
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage());
        } finally {
            DBConnectionUtil.closeConnection(rs, stat, con);
        }
        return Float.valueOf(Float.parseFloat(SolDateFormatter.convertMinuteToHRS2(f1)));
    }

    public static void updateDDMMYYtoSQL() {
        Date localDate1 = null;
        Date localDate2 = SolDateFormatter.DDMMYYtoSQL("01/01/2003");

        Connection con = null;
        PreparedStatement stat = null;
        ResultSet rs = null;
        try {
            con = DBConnectionUtil.getConnection();
            String str1 = "SELECT project_no, drg_no, date, emp_code FROM TIMESHEET where DATE2 = ? ";

            stat = con.prepareStatement(str1);
            stat.setDate(1, localDate2);

            rs = stat.executeQuery();

            while (rs.next()) {
                String str2 = rs.getString(1);
                String str3 = rs.getString(2);
                String str4 = rs.getString(3);
                String str5 = rs.getString(4);

                localDate1 = SolDateFormatter.DDMMYYtoSQL(str4);

                stat = con.prepareStatement(
                        "Update timesheet set DATE2 = ? where PROJECT_NO LIKE ? and DRG_NO LIKE ? and DATE = ? and EMP_CODE = ? ");
                stat.setDate(1, localDate1);
                stat.setString(2, str2);
                stat.setString(3, str3);
                stat.setString(4, str4);
                stat.setString(5, str5);
                int i = stat.executeUpdate();

                if (i > 0) {
                    System.out.println(
                            "Updated : " + str2 + " " + str3 + " " + str4 + " " + str5 + " as \t" + localDate1);
                }
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage());
        } finally {
            DBConnectionUtil.closeConnection(rs, stat, con);
        }
    }

    public static float getDrawingEstimatedHrs(String paramString1, String paramString2, String paramString3) {
        paramString1 = "%" + paramString1 + "%";

        String str = "0";

        Connection con = null;
        PreparedStatement stat = null;
        ResultSet rs = null;
        try {
            con = DBConnectionUtil.getConnection();
            stat = con.prepareStatement(
                    "select " + paramString3 + " from DRAWINGNO where PROJECT_NO like ? and DRAWING_NO like ? ");
            stat.setString(1, paramString1);
            stat.setString(2, paramString2);
            rs = stat.executeQuery();

            while (rs.next()) {
                str = new String(rs.getString(1));
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage());
        } finally {
            DBConnectionUtil.closeConnection(rs, stat, con);
        }

        return Float.parseFloat(str);
    }

    public static float getDrawingHrs(String paramString1, String paramString2) {
        paramString1 = "%" + paramString1 + "%";

        String str = "0";

        Connection con = null;
        PreparedStatement stat = null;
        ResultSet rs = null;
        try {
            con = DBConnectionUtil.getConnection();
            stat = con.prepareStatement(
                    "select TOTAL_TIME from DRAWINGNO where PROJECT_NO like ? and DRAWING_NO like ? ");
            stat.setString(1, paramString1);
            stat.setString(2, paramString2);
            rs = stat.executeQuery();

            while (rs.next()) {
                str = new String(rs.getString(1));
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage());
        } finally {
            DBConnectionUtil.closeConnection(rs, stat, con);
        }

        return Float.parseFloat(str);
    }

    public static String getDCofDRG(String paramString1, String paramString2, String paramString3) {
        paramString1 = "%" + paramString1 + "%";
        paramString2 = "%" + paramString2 + "%";

        String str = "0";

        Connection con = null;
        PreparedStatement stat = null;
        ResultSet rs = null;
        try {
            con = DBConnectionUtil.getConnection();
            stat = con.prepareStatement(
                    "select " + paramString3 + " from drawingno where PROJECT_NO like ? and drawing_no like ? ");
            stat.setString(1, paramString1);
            stat.setString(2, paramString2);
            rs = stat.executeQuery();

            while (rs.next()) {
                str = new String(rs.getString(1));
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage());
        } finally {
            DBConnectionUtil.closeConnection(rs, stat, con);
        }
        return str;
    }

    public static String getItemCode(String paramString1, String paramString2) {
        paramString1 = "%" + paramString1 + "%";
        paramString2 = "%" + paramString2 + "%";

        String str = "0";

        Connection con = null;
        PreparedStatement stat = null;
        ResultSet rs = null;
        try {
            con = DBConnectionUtil.getConnection();
            stat = con
                    .prepareStatement("select ITEM_CODE from DRAWINGNO where PROJECT_NO like ? and drawing_no like ? ");
            stat.setString(1, paramString1);
            stat.setString(2, paramString2);
            rs = stat.executeQuery();

            while (rs.next()) {
                str = new String(rs.getString(1));
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage());
        } finally {
            DBConnectionUtil.closeConnection(rs, stat, con);
        }
        return str;
    }

    public static List<String> getDCList(String paramString) {

    	Set<String> dcList = new HashSet<String>();
    	
    	
        Connection con = null;
        PreparedStatement stat = null;
        ResultSet rs = null;
        String sql = "select EMP_CODE from HRTIMEMASTER where PROJECT_NO = ?";
        System.out.println(sql);
        try {
            con = DBConnectionUtil.getConnection();
            stat = con.prepareStatement(sql);
            stat.setString(1, paramString);
            rs = stat.executeQuery();

            while (rs.next()) {
                String str = new String(rs.getString(1));
                dcList.add(str);
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage());
        } finally {
            DBConnectionUtil.closeConnection(rs, stat, con);
        }
        List<String> dcArrayList = new ArrayList<String>(dcList);
        return dcArrayList;
    }
    
    public static Float getUsedHrs(String paramString1, String paramString2, String paramString3) {
        paramString1 = "%" + paramString1 + "%";
        paramString2 = "%" + paramString2 + "%";

        if (paramString3.equalsIgnoreCase("Detailing")) {
            paramString3 = "%D%";
        }
        if (paramString3.equalsIgnoreCase("Checking")) {
            paramString3 = "%C%";
        }
        if (paramString3.equalsIgnoreCase("")) {
            paramString3 = "%%";
        }

        int i = 0;

        Connection con = null;
        PreparedStatement stat = null;
        ResultSet rs = null;
        try {
            con = DBConnectionUtil.getConnection();
            stat = con.prepareStatement(
                    "select sum(TOTAL_TIME) from HRTIMEMASTER where EMP_CODE like ? and PROJECT_NO like ? and D_C like ? ");
            stat.setString(1, paramString1);
            stat.setString(2, paramString2);
            stat.setString(3, paramString3);
            rs = stat.executeQuery();

            while (rs.next()) {
                float f = Float.parseFloat(rs.getString(1));

                System.out.println("Used Hrs : " + f);

                int j = SolDateFormatter.convertHrsToMinute(String.valueOf(f));
                i += j;
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage());
        } finally {
            DBConnectionUtil.closeConnection(rs, stat, con);
        }
        return Float.valueOf(Float.parseFloat(SolDateFormatter.convertMinuteToHRS(i)));
    }

    public static Float getUsedHrsBetweenPeriod(String paramString1, String paramString2, String paramString3,
            Date paramDate1, Date paramDate2) {

        paramString1 = "%" + paramString1 + "%";
        paramString2 = "%" + paramString2 + "%";

        if (paramString3.equalsIgnoreCase("Detailing")) {
            paramString3 = "%D%";
        }
        if (paramString3.equalsIgnoreCase("Checking")) {
            paramString3 = "%C%";
        }
        if (paramString3.equalsIgnoreCase("")) {
            paramString3 = "%%";
        }

        int i = 0;

        Connection con = null;
        PreparedStatement stat = null;
        ResultSet rs = null;
        try {
            con = DBConnectionUtil.getConnection();
            stat = con.prepareStatement(
                    "select sum(TOTAL_TIME) from HRTIMEMASTER where EMP_CODE like ? and PROJECT_NO like ? and D_C like ? and date between ? and ? ");
            stat.setString(1, paramString1);
            stat.setString(2, paramString2);
            stat.setString(3, paramString3);
            stat.setDate(4, paramDate1);
            stat.setDate(5, paramDate2);
            rs = stat.executeQuery();

            while (rs.next()) {
                float f = Float.parseFloat(rs.getString(1));

                System.out.println("Used Hrs : " + f);

                int j = SolDateFormatter.convertHrsToMinute(String.valueOf(f));
                i += j;
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage());
        } finally {
            DBConnectionUtil.closeConnection(rs, stat, con);
        }
        return Float.valueOf(Float.parseFloat(SolDateFormatter.convertMinuteToHRS(i)));
    }

    public static float getEffectiveUsedHrs(String paramString1, String paramString2) {
        paramString1 = "%" + paramString1 + "%";
        paramString2 = "%" + paramString2 + "%";

        List<String> localArrayList = new ArrayList<String>();
        localArrayList = getDCList(paramString1);

        float f1 = 0.0F;

        for (int i = 0; i < localArrayList.size(); i++) {
            String str = String.valueOf(localArrayList.get(i));
            float f2 = getUsedHrs(str, paramString1, paramString2).floatValue();

            float f3 = Float.parseFloat(EmpTB.getCurrentFactor(str));
            float f4 = f2 * f3;

            f1 += f4;

            System.out.println("Emp Code : " + str + "\t" + f2 + "\t" + f1 + "\t" + f3);
        }

        return f1;
    }

    public static float getEffectiveUsedHrsBetweenPeriod(String paramString1, String paramString2, Date paramDate1,
            Date paramDate2) {
        paramString1 = "%" + paramString1 + "%";
        paramString2 = "%" + paramString2 + "%";

        List<String> localArrayList = new ArrayList<String>();
        localArrayList = getDCList(paramString1);

        float f1 = 0.0F;

        for (int i = 0; i < localArrayList.size(); i++) {
            String str = String.valueOf(localArrayList.get(i));
            float f2 = getUsedHrsBetweenPeriod(str, paramString1, paramString2, paramDate1, paramDate2).floatValue();

            float f3 = Float.parseFloat(EmpTB.getCurrentFactor(str));
            float f4 = f2 * f3;

            f1 += f4;

            System.out.println("Emp Code : " + str + "\t" + f2 + "\t" + f1 + "\t" + f3);
        }

        return f1;
    }

    public static Float getBFAHrs(String paramString1, String paramString2) {
        paramString1 = "%" + paramString1 + "%";
        paramString2 = "%" + paramString2 + "%";

        int i = 0;

        Connection con = null;
        PreparedStatement stat = null;
        ResultSet rs = null;
        try {
            con = DBConnectionUtil.getConnection();
            stat = con.prepareStatement(
                    "select sum(TOTAL_TIME) from BFA_TIME where EMP_CODE like ? and PROJECT_NO like ? ");
            stat.setString(1, paramString1);
            stat.setString(2, paramString2);
            rs = stat.executeQuery();

            while (rs.next()) {
                float f = Float.parseFloat(rs.getString(1));

                int j = SolDateFormatter.convertHrsToMinute(String.valueOf(f));
                i += j;
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage());
        } finally {
            DBConnectionUtil.closeConnection(rs, stat, con);
        }
        return Float.valueOf(Float.parseFloat(SolDateFormatter.convertMinuteToHRS(i)));
    }

    public static Float getBFAHrs(String paramString1, String paramString2, Date paramDate1, Date paramDate2) {
        paramString1 = "%" + paramString1 + "%";
        paramString2 = "%" + paramString2 + "%";

        int i = 0;

        Connection con = null;
        PreparedStatement stat = null;
        ResultSet rs = null;
        try {
            con = DBConnectionUtil.getConnection();
            stat = con.prepareStatement(
                    "select sum(TOTAL_TIME) from BFA_TIME where EMP_CODE like ? and PROJECT_NO like ? AND DATE_MONTH between ? and ? ");
            stat.setString(1, paramString1);
            stat.setString(2, paramString2);
            stat.setDate(3, paramDate1);
            stat.setDate(4, paramDate2);
            rs = stat.executeQuery();

            while (rs.next()) {
                float f = Float.parseFloat(rs.getString(1));

                int j = SolDateFormatter.convertHrsToMinute(String.valueOf(f));
                i += j;
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage());
        } finally {
            DBConnectionUtil.closeConnection(rs, stat, con);
        }
        return Float.valueOf(Float.parseFloat(SolDateFormatter.convertMinuteToHRS(i)));
    }

    public static float getEffectiveBFAHrs(String paramString) {
        //paramString = "%" + paramString + "%";

        List<String> localArrayList = new ArrayList<String>();
        localArrayList = getDCList(paramString);

        float f1 = 0.0F;

        for (int i = 0; i < localArrayList.size(); i++) {
            String str = String.valueOf(localArrayList.get(i));
            float f2 = getBFAHrs(str, paramString).floatValue();
            float f3 = Float.parseFloat(EmpTB.getCurrentFactor(str));
            float f4 = f2 * f3;

            f1 += f4;

            System.out.println("Emp Code : " + str + "\t" + f2 + "\t" + f1);
        }

        return f1;
    }

    public static float getEffectiveBFAHrs(String paramString, Date paramDate1, Date paramDate2) {
        paramString = "%" + paramString + "%";

        List<String> localArrayList = new ArrayList<String>();
        localArrayList = getDCList(paramString);

        float f1 = 0.0F;

        for (int i = 0; i < localArrayList.size(); i++) {
            String str = String.valueOf(localArrayList.get(i));
            float f2 = getBFAHrs(str, paramString, paramDate1, paramDate2).floatValue();
            float f3 = Float.parseFloat(EmpTB.getCurrentFactor(str));
            float f4 = f2 * f3;

            f1 += f4;

            System.out.println("Emp Code : " + str + "\t" + f2 + "\t" + f1);
        }

        return f1;
    }

    public static Float getRevisionHrs(String paramString1, String paramString2) {
        paramString1 = "%" + paramString1 + "%";
        paramString2 = "%" + paramString2 + "%";

        int i = 0;

        Connection con = null;
        PreparedStatement stat = null;
        ResultSet rs = null;
        try {
            con = DBConnectionUtil.getConnection();
            stat = con.prepareStatement(
                    "select sum(TOTAL_TIME) from REVISION_TIME where EMP_CODE like ? and PROJECT_NO like ? ");
            stat.setString(1, paramString1);
            stat.setString(2, paramString2);
            rs = stat.executeQuery();

            while (rs.next()) {
                float f = Float.parseFloat(rs.getString(1));

                int j = SolDateFormatter.convertHrsToMinute(String.valueOf(f));
                i += j;
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage());
        } finally {
            DBConnectionUtil.closeConnection(rs, stat, con);
        }
        return Float.valueOf(Float.parseFloat(SolDateFormatter.convertMinuteToHRS(i)));
    }

    public static Float getRevisionHrs(String paramString1, String paramString2, Date paramDate1, Date paramDate2) {
        paramString1 = "%" + paramString1 + "%";
        paramString2 = "%" + paramString2 + "%";

        int i = 0;

        Connection con = null;
        PreparedStatement stat = null;
        ResultSet rs = null;
        try {
            con = DBConnectionUtil.getConnection();
            stat = con.prepareStatement(
                    "select sum(TOTAL_TIME) from REVISION_TIME where EMP_CODE like ? and PROJECT_NO like ? and DATE_MONTH between ? and ? ");
            stat.setString(1, paramString1);
            stat.setString(2, paramString2);
            stat.setDate(3, paramDate1);
            stat.setDate(4, paramDate2);
            rs = stat.executeQuery();

            while (rs.next()) {
                float f = Float.parseFloat(rs.getString(1));

                int j = SolDateFormatter.convertHrsToMinute(String.valueOf(f));
                i += j;
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage());
        } finally {
            DBConnectionUtil.closeConnection(rs, stat, con);
        }
        return Float.valueOf(Float.parseFloat(SolDateFormatter.convertMinuteToHRS(i)));
    }

    public static float getEffectiveRevisionHrs(String paramString) {
        paramString = "%" + paramString + "%";

        List<String> localArrayList = new ArrayList<String>();
        localArrayList = getDCList(paramString);

        float f1 = 0.0F;

        for (int i = 0; i < localArrayList.size(); i++) {
            String str = String.valueOf(localArrayList.get(i));
            float f2 = getRevisionHrs(str, paramString).floatValue();
            float f3 = Float.parseFloat(EmpTB.getCurrentFactor(str));
            float f4 = f2 * f3;

            f1 += f4;

            System.out.println("Emp Code : " + str + "\t" + f2 + "\t" + f1);
        }

        return f1;
    }

    public static float getEffectiveRevisionHrs(String paramString, Date paramDate1, Date paramDate2) {
        paramString = "%" + paramString + "%";

        List<String> localArrayList = new ArrayList<String>();
        localArrayList = getDCList(paramString);

        float f1 = 0.0F;

        for (int i = 0; i < localArrayList.size(); i++) {
            String str = String.valueOf(localArrayList.get(i));
            float f2 = getRevisionHrs(str, paramString, paramDate1, paramDate2).floatValue();
            float f3 = Float.parseFloat(EmpTB.getCurrentFactor(str));
            float f4 = f2 * f3;

            f1 += f4;

            System.out.println("Emp Code : " + str + "\t" + f2 + "\t" + f1);
        }

        return f1;
    }

    public static Float getProjectRevisionHrs(String paramString) {
        paramString = "%" + paramString + "%";
        int i = 0;

        Connection con = null;
        PreparedStatement stat = null;
        ResultSet rs = null;
        try {
            con = DBConnectionUtil.getConnection();
            stat = con.prepareStatement("select sum(EXTRA_HRS) from REV_LOG where PROJECT_NO like ? ");
            stat.setString(1, paramString);
            rs = stat.executeQuery();

            while (rs.next()) {
                float f = Float.parseFloat(rs.getString(1));

                int j = SolDateFormatter.convertHrsToMinute(String.valueOf(f));
                i += j;
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage());
        } finally {
            DBConnectionUtil.closeConnection(rs, stat, con);
        }

        return Float.valueOf(Float.parseFloat(SolDateFormatter.convertMinuteToHRS(i)));
    }

    public static Float getProjectBFAHrs(String paramString) {
        paramString = "%" + paramString + "%";
        int i = 0;

        Connection con = null;
        PreparedStatement stat = null;
        ResultSet rs = null;
        try {
            con = DBConnectionUtil.getConnection();
            stat = con.prepareStatement("select sum(EXTRA_HRS) from RFI_LOG where PROJECT_NO like ? ");
            stat.setString(1, paramString);
            rs = stat.executeQuery();

            while (rs.next()) {
                float f = Float.parseFloat(rs.getString(1));

                int j = SolDateFormatter.convertHrsToMinute(String.valueOf(f));
                i += j;
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage());
        } finally {
            DBConnectionUtil.closeConnection(rs, stat, con);
        }
        return Float.valueOf(Float.parseFloat(SolDateFormatter.convertMinuteToHRS(i)));
    }

    public static Float getCompletedHrs(String paramString1, String paramString2) {
        paramString1 = "%" + paramString1 + "%";

        String str1 = EmpTB.getJob(paramString2);
        String str2 = "0";
        String str3 = "DTL";

        if (str1.equalsIgnoreCase("Detailing")) {
            str2 = "0";
            str3 = "DTL";
        }
        if (str1.equalsIgnoreCase("Checking")) {
            str2 = "Check";
            str3 = "CHK";
        }

        str3 = str3 + "_TIME";

        float f1 = 0.0F;

        Connection con = null;
        PreparedStatement stat = null;
        ResultSet rs = null;
        try {
            f1 = 0.0F;
            con = DBConnectionUtil.getConnection();
            stat = con.prepareStatement(
                    "select DRG_NO from TIMESHEET where PROJECT_NO like ? and EMP_CODE like ? and DRGSTATUS1 =? ");
            stat.setString(1, paramString1);
            stat.setString(2, paramString2);
            stat.setString(3, str2);

            rs = stat.executeQuery();

            int i = 0;
            while (rs.next()) {
                i += 1;
                String str4 = rs.getString(1);

                float f2 = getDrawingEstimatedHrs(paramString1, str4, str3);
                float f3 = SolDateFormatter.convertHrsToMinute2(String.valueOf(f2));
                f1 += f3;

                System.out.println(i + "\t" + paramString1 + "\t" + str4 + "\t" + f2 + "\t" + f3 + "\t" + f1);
            }
            i = 0;
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage());
        } finally {
            DBConnectionUtil.closeConnection(rs, stat, con);
        }
        return Float.valueOf(Float.parseFloat(SolDateFormatter.convertMinuteToHRS2(f1)));
    }

    public static Float getCompletedHrsBetweenPeriod(String paramString, Date paramDate1, Date paramDate2) {
        float f1 = 0.0F;

        Connection con = null;
        PreparedStatement stat = null;
        ResultSet rs = null;
        try {
            f1 = 0.0F;
            con = DBConnectionUtil.getConnection();
            stat = con.prepareStatement(
                    "select DRG_NO from TIMESHEET where PROJECT_NO like ? and DRGSTATUS1 like ? and DATE2 between ? and ? ");
            stat.setString(1, "%" + paramString + "%");
            stat.setString(2, "%Check%");
            stat.setDate(3, paramDate1);
            stat.setDate(4, paramDate2);

            rs = stat.executeQuery();

            while (rs.next()) {
                String str = rs.getString(1);

                float f2 = getDrawingHrs(paramString, str);
                float f3 = SolDateFormatter.convertHrsToMinute2(String.valueOf(f2));
                f1 += f3;
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage());
        } finally {
            DBConnectionUtil.closeConnection(rs, stat, con);
        }
        return Float.valueOf(Float.parseFloat(SolDateFormatter.convertMinuteToHRS2(f1)));
    }

    public static Float getCompletedHrs(String paramString1, String paramString2, String paramString3) {
        paramString1 = "%" + paramString1 + "%";

        String str1 = "0";
        String str2 = "DTL";

        if (paramString3.equalsIgnoreCase("Detailing")) {
            str1 = "0";
            str2 = "DTL";
        }
        if (paramString3.equalsIgnoreCase("Checking")) {
            str1 = "Check";
            str2 = "CHK";
        }

        str2 = str2 + "_TIME";

        float f1 = 0.0F;

        Connection con = null;
        PreparedStatement stat = null;
        ResultSet rs = null;
        try {
            f1 = 0.0F;
            con = DBConnectionUtil.getConnection();
            stat = con.prepareStatement(
                    "select DRG_NO from TIMESHEET where PROJECT_NO like ? and EMP_CODE like ? and DRGSTATUS1 =? ");
            stat.setString(1, paramString1);
            stat.setString(2, paramString2);
            stat.setString(3, str1);

            rs = stat.executeQuery();

            int i = 0;
            while (rs.next()) {
                i += 1;
                String str3 = rs.getString(1);

                float f2 = getDrawingEstimatedHrs(paramString1, str3, str2);
                float f3 = SolDateFormatter.convertHrsToMinute2(String.valueOf(f2));
                f1 += f3;

                System.out.println(i + "\t" + paramString1 + "\t" + str3 + "\t" + f2 + "\t" + f3 + "\t" + f1);
            }
            i = 0;
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage());
        } finally {
            DBConnectionUtil.closeConnection(rs, stat, con);
        }
        return Float.valueOf(Float.parseFloat(SolDateFormatter.convertMinuteToHRS2(f1)));
    }

    public static Float getCompletedHrs(String paramString) {
        paramString = "%" + paramString + "%";

        float f1 = 0.0F;

        Connection con = null;
        PreparedStatement stat = null;
        ResultSet rs = null;
        try {
            f1 = 0.0F;
            con = DBConnectionUtil.getConnection();
            stat = con.prepareStatement("select DRG_NO from TIMESHEET where PROJECT_NO like ? and DRGSTATUS1 =? ");
            stat.setString(1, paramString);
            stat.setString(2, "Check");
            rs = stat.executeQuery();

            int i = 0;
            while (rs.next()) {
                i += 1;
                String str = rs.getString(1);

                float f2 = getDrawingEstimatedHrs(paramString, str, "TOTAL_TIME");
                float f3 = SolDateFormatter.convertHrsToMinute2(String.valueOf(f2));
                f1 += f3;

                System.out.println(i + "\t" + paramString + "\t" + str + "\t" + f2 + "\t" + f3 + "\t" + f1);
            }
            i = 0;
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage());
        } finally {
            DBConnectionUtil.closeConnection(rs, stat, con);
        }
        return Float.valueOf(Float.parseFloat(SolDateFormatter.convertMinuteToHRS2(f1)));
    }

    public static Float getAllotedHrs(String paramString1, String paramString2, String paramString3) {
        paramString1 = "%" + paramString1 + "%";

        String str1 = null;
        if (paramString3.equalsIgnoreCase("Detailing")) {
            str1 = "DTL_TIME";
        }
        if (paramString3.equalsIgnoreCase("Checking")) {
            str1 = "CHK_TIME";
        }
        if (paramString3.equalsIgnoreCase("Total")) {
            str1 = "TOTAL_TIME";
        }

        float f1 = 0.0F;

        Connection con = null;
        PreparedStatement stat = null;
        ResultSet rs = null;
        try {
            f1 = 0.0F;
            con = DBConnectionUtil.getConnection();
            stat = con.prepareStatement(
                    "select " + str1 + " from DRAWINGNO where PROJECT_NO like ? and " + paramString3 + " =? ");
            stat.setString(1, paramString1);
            stat.setString(2, paramString2);
            rs = stat.executeQuery();

            while (rs.next()) {
                String str2 = rs.getString(1);

                float f2 = SolDateFormatter.convertHrsToMinute2(str2);
                f1 += f2;
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage());
        } finally {
            DBConnectionUtil.closeConnection(rs, stat, con);
        }
        return Float.valueOf(Float.parseFloat(SolDateFormatter.convertMinuteToHRS2(f1)));
    }

    public static int getCountAllotedDrg(String paramString1, String paramString2, String paramString3) {
        paramString1 = "%" + paramString1 + "%";
        int i = 0;

        Connection con = null;
        PreparedStatement stat = null;
        ResultSet rs = null;
        try {
            con = DBConnectionUtil.getConnection();
            stat = con.prepareStatement(
                    "select COUNT(DRAWING_NO) from DRAWINGNO where PROJECT_NO like ? and " + paramString3 + " =? ");
            stat.setString(1, paramString1);
            stat.setString(2, paramString2);
            rs = stat.executeQuery();

            while (rs.next()) {
                i = rs.getInt(1);
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage());
        } finally {
            DBConnectionUtil.closeConnection(rs, stat, con);
        }
        return i;
    }

    public static int getCountCompletedDrg(String paramString1, String paramString2) {
        paramString1 = "%" + paramString1 + "%";
        int i = 0;

        Connection con = null;
        PreparedStatement stat = null;
        ResultSet rs = null;
        try {
            con = DBConnectionUtil.getConnection();
            stat = con.prepareStatement("select COUNT(DRG_NO) from TIMESHEET where PROJECT_NO like ? and EMP_CODE =? ");
            stat.setString(1, paramString1);
            stat.setString(2, paramString2);
            rs = stat.executeQuery();

            while (rs.next()) {
                i = rs.getInt(1);
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage());
        } finally {
            DBConnectionUtil.closeConnection(rs, stat, con);
        }
        return i;
    }

    public static int getCountCompletedDrg(String paramString1, String paramString2, String paramString3) {
        paramString1 = "%" + paramString1 + "%";

        String str = null;

        if (paramString3.equalsIgnoreCase("Detailing")) {
            str = "0";
        }
        if (paramString3.equalsIgnoreCase("Checking")) {
            str = "Check";
        }

        int i = 0;

        Connection con = null;
        PreparedStatement stat = null;
        ResultSet rs = null;
        try {
            con = DBConnectionUtil.getConnection();
            stat = con.prepareStatement(
                    "select COUNT(DRG_NO) from TIMESHEET where PROJECT_NO like ? and EMP_CODE =? and DRGSTATUS1 = ? ");
            stat.setString(1, paramString1);
            stat.setString(2, paramString2);
            stat.setString(3, str);
            rs = stat.executeQuery();

            while (rs.next()) {
                i = rs.getInt(1);
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage());
        } finally {
            DBConnectionUtil.closeConnection(rs, stat, con);
        }
        return i;
    }

    public static int isQuoted(String paramString) {
        int i = 0;

        Connection con = null;
        PreparedStatement stat = null;
        ResultSet rs = null;
        try {
            con = DBConnectionUtil.getConnection();
            stat = con.prepareStatement("select COUNT(PROJECT_NO) from QUOTATION_LIST where PROJECT_NO = ? ");
            stat.setString(1, paramString);
            rs = stat.executeQuery();

            while (rs.next()) {
                i = rs.getInt(1);
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage());
        } finally {
            DBConnectionUtil.closeConnection(rs, stat, con);
        }
        return i;
    }

    public static String generateNewProjectNumber(int year) {

        long generatedProjectNumber = 0;

        Connection con = null;
        PreparedStatement stat = null;
        ResultSet rs = null;
        try {
            con = DBConnectionUtil.getConnection();
            stat = con.prepareStatement("select max(PROJECT_NO) from QUOTATION_LIST where project_no like ? ");
            stat.setString(1, year + "%");
            rs = stat.executeQuery();

            while (rs.next()) {
                generatedProjectNumber = rs.getLong(1);
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage());
        } finally {
            DBConnectionUtil.closeConnection(rs, stat, con);
        }

        DecimalFormat formater = new DecimalFormat(ApplicationConstants.DIGIT_3);
        String result = "0";
        if (generatedProjectNumber == 0) {
            result = year + formater.format(generatedProjectNumber + 1);
        } else {

            result = formater.format(generatedProjectNumber + 1);
        }
        return result;
    }

    public static List<Long> getProjectNumber(long fromProject, long toProject){
        Connection con = null;
        PreparedStatement stat = null;
        ResultSet rs = null;

        String query = "select distinct(PROJECT_NO) from estimation_mp where project_no between ? and ?";
        List<Long> projectNumberList = new ArrayList<>();
        try {
            con = DBConnectionUtil.getConnection();
            stat = con.prepareStatement(query);
            stat.setLong(1, fromProject);
            stat.setLong(2, toProject);
            //System.out.println("Query: "+stat.toString());
            rs = stat.executeQuery();

            while (rs.next()) {
                projectNumberList.add(rs.getLong(1));
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage());
        } finally {
            DBConnectionUtil.closeConnection(rs, stat, con);
        }

        return projectNumberList;
    }

    //get hrs of details, checking or tl. d,c,t.
    public static float getProjectEstimatedHrs(long projectNumber, String hrsOf){
        float totalHrs = 0;

        String hrsOfString = "";
        if(hrsOf.equalsIgnoreCase("d") ){
            hrsOfString = "DTL_TIME";
        }
        if(hrsOf.equalsIgnoreCase("c")){
            hrsOfString = "CHK_TIME";
        }
        if (hrsOf.equalsIgnoreCase("t")){
            hrsOfString = "TL_TIME";
        }

        Connection con = null;
        PreparedStatement stat = null;
        ResultSet rs = null;

        String query = "SELECT sum("+hrsOfString+" * SHEET_QTY) FROM estimation_mp where PROJECT_NO = ?";
        //System.out.println("Query: "+query);
        try {
            con = DBConnectionUtil.getConnection();
            stat = con.prepareStatement(query);
            stat.setLong(1, projectNumber);
            rs = stat.executeQuery();
            //System.out.println(stat);

            while (rs.next()) {
                totalHrs = rs.getFloat(1);
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage());
        } finally {
            DBConnectionUtil.closeConnection(rs, stat, con);
        }

        return totalHrs;
    }

    public static float getProjectUsedHrs(long projectNumber, String hrsOf){
        float totalHrs = 0;

        String hrsOfString = "";
        if(hrsOf.equalsIgnoreCase("d") ){
            hrsOfString = "D_C = 'D' and";
        }
        if(hrsOf.equalsIgnoreCase("c")){
            hrsOfString = "D_C = 'C' and";
        }
        if (hrsOf.equalsIgnoreCase("t")){
            hrsOfString = "D_C = 'J' and";
        }
        if (hrsOf.equalsIgnoreCase("a")){
            hrsOfString = "";
        }

        Connection con = null;
        PreparedStatement stat = null;
        ResultSet rs = null;

        String query = "select sum(TOTAL_TIME) from hrtimemaster where "+hrsOfString+"  PROJECT_NO = ?";
        //System.out.println("Query: "+query);
        try {
            con = DBConnectionUtil.getConnection();
            stat = con.prepareStatement(query);
            stat.setLong(1, projectNumber);
            rs = stat.executeQuery();
            //System.out.println(stat);

            while (rs.next()) {
                totalHrs = rs.getFloat(1);
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage());
        } finally {
            DBConnectionUtil.closeConnection(rs, stat, con);
        }

        return totalHrs;
    }
}