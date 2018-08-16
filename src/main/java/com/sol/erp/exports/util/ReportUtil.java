package com.sol.erp.exports.util;

import com.sol.erp.dao.ProjectDAO;
import com.sol.erp.exports.dto.ProjectProgressDTO;

import java.util.*;

public class ReportUtil {

    private enum ACTVITIES  {
        MODELING, EDITING, CHECKING
    };

    public static Map<String, List<ProjectProgressDTO>> exportProjectProgressReport(String fromProjectNo, String toProjectNo){
        System.out.println(ReportUtil.class.getName() + " : exportProjectProgressReport [START] ");
        List<String> projectNumbers = ProjectDAO.getProjectNumber(fromProjectNo, toProjectNo);
        Map<String, List<ProjectProgressDTO>> progressMap = new HashMap<>();

        for(String projectNo: projectNumbers){
            List<ProjectProgressDTO> projectProgressDTOS = new ArrayList<>();
            projectProgressDTOS.add(new ProjectProgressDTO(projectNo, "D")); //Detailing Hrs
            projectProgressDTOS.add(new ProjectProgressDTO(projectNo, "C")); //Checking Hrs
            projectProgressDTOS.add(new ProjectProgressDTO(projectNo, "T")); //TL Hrs

            progressMap.put(projectNo, projectProgressDTOS);
        }

        return progressMap;
    }
}
