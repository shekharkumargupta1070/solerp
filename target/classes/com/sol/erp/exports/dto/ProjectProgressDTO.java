package com.sol.erp.exports.dto;

import com.sol.erp.dao.ProjectDAO;

import java.util.Objects;

public class ProjectProgressDTO {

    private static final String ACTIVITY_MODELING = "Modeling";
    private static final String ACTIVITY_EDITING = "Editing";
    private static final String ACTIVITY_CHECKING = "Checking";
    private static final String ACTIVITY_TOTAL = "Total";

    private long projectNo;
    private String activity;
    private float totalHrs;
    private float spentHrs;
    private float usedPercentage;

    public ProjectProgressDTO(){}

    public ProjectProgressDTO(long projectNo, String activity) {
        this.projectNo = projectNo;
        this.activity = activity;
        //this.totalHrs = ProjectDAO.getProjectEstimatedHrs(projectNo, activity);
        this.spentHrs = ProjectDAO.getProjectUsedHrs(projectNo, activity);
    }

    public long getProjectNo() {
        return projectNo;
    }

    public void setProjectNo(long projectNo) {
        this.projectNo = projectNo;
    }

    public String getActivity() {
        switch (activity){
            case "D": return ACTIVITY_MODELING;
            case "M": return ACTIVITY_MODELING;
            case "RM": return ACTIVITY_MODELING;

            case "C": return ACTIVITY_CHECKING;
            case "J": return ACTIVITY_CHECKING;
            case "RC": return ACTIVITY_CHECKING;

            case "E": return ACTIVITY_EDITING;
            case "RE": return ACTIVITY_EDITING;

            default: return ACTIVITY_TOTAL;
        }
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public float getTotalHrs() {
        return totalHrs;
    }

    public void setTotalHrs(float totalHrs) {
        this.totalHrs = totalHrs;
    }

    public float getSpentHrs() {
        return spentHrs;
    }

    public void setSpentHrs(float spentHrs) {
        this.spentHrs = spentHrs;
    }

    public float getUsedPercentage() {
        if(totalHrs > 0) {
            this.usedPercentage = (spentHrs / totalHrs) * 100;
        }
        return usedPercentage;
    }

    public void setUsedPercentage(float usedPercentage) {
        this.usedPercentage = usedPercentage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProjectProgressDTO that = (ProjectProgressDTO) o;
        return Objects.equals(projectNo, that.projectNo);
    }

    @Override
    public int hashCode() {

        return Objects.hash(projectNo);
    }

    @Override
    public String toString() {
        return "ProjectProgressDTO{" +
                "projectNo='" + projectNo + '\'' +
                ", activity='" + activity + '\'' +
                ", totalHrs=" + totalHrs +
                ", spentHrs=" + spentHrs +
                ", usedPercentage=" + usedPercentage +
                '}';
    }
}
