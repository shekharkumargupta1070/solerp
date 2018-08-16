package com.sol.erp.exports.dto;

import com.sol.erp.dao.ProjectDAO;

import java.util.Objects;

public class ProjectProgressDTO {

    private String projectNo;
    private String activity;
    private float estimatedHrs;
    private float spentHrs;
    private float usedPercentage;

    public ProjectProgressDTO(){}

    public ProjectProgressDTO(String projectNo, String activity) {
        this.projectNo = projectNo;
        this.activity = activity;
        this.estimatedHrs = ProjectDAO.getProjectEstimatedHrs(projectNo, activity);
        this.spentHrs = ProjectDAO.getProjectUsedHrs(projectNo, activity);

        this.usedPercentage = (spentHrs/estimatedHrs) * 100;
    }

    public String getProjectNo() {
        return projectNo;
    }

    public void setProjectNo(String projectNo) {
        this.projectNo = projectNo;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public float getEstimatedHrs() {
        return estimatedHrs;
    }

    public void setEstimatedHrs(float estimatedHrs) {
        this.estimatedHrs = estimatedHrs;
    }

    public float getSpentHrs() {
        return spentHrs;
    }

    public void setSpentHrs(float spentHrs) {
        this.spentHrs = spentHrs;
    }

    public float getUsedPercentage() {
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
                ", estimatedHrs=" + estimatedHrs +
                ", spentHrs=" + spentHrs +
                ", usedPercentage=" + usedPercentage +
                '}';
    }
}