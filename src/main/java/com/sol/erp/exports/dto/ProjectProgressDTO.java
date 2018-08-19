package com.sol.erp.exports.dto;

import com.sol.erp.dao.ProjectDAO;

import java.util.Objects;

public class ProjectProgressDTO {

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
        return activity;
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
