package com.sol.erp.exports.util;

import com.sol.erp.dao.ProjectDAO;
import com.sol.erp.exports.dto.ProjectProgressDTO;

import java.util.*;

public class ReportUtil {

    private enum ACTVITIES {
        MODELING, EDITING, CHECKING, TOTAL
    };

    public static Map<Long, List<ProjectProgressDTO>> generateProjectProgressData(long fromProjectNo, long toProjectNo) {
        System.out.println(ReportUtil.class.getName() + " : generateProjectProgressData [START] ");
        List<Long> projectNumbers = ProjectDAO.getProjectNumber(fromProjectNo, toProjectNo);
        Map<Long, List<ProjectProgressDTO>> progressMap = new HashMap<>();

        for (long projectNo : projectNumbers) {
            List<ProjectProgressDTO> projectProgressDTOS = new ArrayList<>();

            ProjectProgressDTO detailingHrs = new ProjectProgressDTO(projectNo, "D");
            ProjectProgressDTO checkingHrs = new ProjectProgressDTO(projectNo, "C");
            ProjectProgressDTO tlHrs = new ProjectProgressDTO(projectNo, "E");
            ProjectProgressDTO totalHrs = new ProjectProgressDTO(projectNo, "TOTAL");//a means TOTAL hrs

            totalHrs.setTotalHrs(totalHrs.getSpentHrs());
            detailingHrs.setTotalHrs(totalHrs.getSpentHrs());
            checkingHrs.setTotalHrs(totalHrs.getSpentHrs());
            tlHrs.setTotalHrs((totalHrs.getSpentHrs()));


            projectProgressDTOS.add(detailingHrs); //Detailing Hrs
            projectProgressDTOS.add(checkingHrs); //Checking Hrs
            projectProgressDTOS.add(tlHrs); //TL Hrs
            projectProgressDTOS.add((totalHrs));//Total Hrs
            //CHANGE THE LOGIC OF TOTAL ELEMENT



            progressMap.put(projectNo, projectProgressDTOS);
        }

        return progressMap;
    }

}
