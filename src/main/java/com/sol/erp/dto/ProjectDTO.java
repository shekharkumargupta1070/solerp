package com.sol.erp.dto;

public class ProjectDTO {

	private long quotationNumber;
	private long projectNumber;
	private String projectName;
	private String name;
	private String teamLeader;
	private String clientName;
	private String estimatedHrs;
	private String numberOfSheets;
	private String startDate;
	private String finalDate;
	private String usedHrs;

	
	
	public long getQuotationNumber() {
		return quotationNumber;
	}

	public void setQuotationNumber(long quotationNumber) {
		this.quotationNumber = quotationNumber;
	}

	public long getProjectNumber() {
		return projectNumber;
	}

	public void setProjectNumber(long projectNumber) {
		this.projectNumber = projectNumber;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTeamLeader() {
		return teamLeader;
	}

	public void setTeamLeader(String teamLeader) {
		this.teamLeader = teamLeader;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public String getEstimatedHrs() {
		return estimatedHrs;
	}

	public void setEstimatedHrs(String estimatedHrs) {
		this.estimatedHrs = estimatedHrs;
	}

	public String getNumberOfSheets() {
		return numberOfSheets;
	}

	public void setNumberOfSheets(String numberOfSheets) {
		this.numberOfSheets = numberOfSheets;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getFinalDate() {
		return finalDate;
	}

	public void setFinalDate(String finalDate) {
		this.finalDate = finalDate;
	}

	public String getUsedHrs() {
		return usedHrs;
	}

	public void setUsedHrs(String usedHrs) {
		this.usedHrs = usedHrs;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (quotationNumber ^ (quotationNumber >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProjectDTO other = (ProjectDTO) obj;
		if (quotationNumber != other.quotationNumber)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ProjectDTO [quotationNumber=" + quotationNumber + ", projectNumber=" + projectNumber + ", projectName="
				+ projectName + ", name=" + name + ", teamLeader=" + teamLeader + ", clientName=" + clientName
				+ ", estimatedHrs=" + estimatedHrs + ", numberOfSheets=" + numberOfSheets + ", startDate=" + startDate
				+ ", finalDate=" + finalDate + ", usedHrs=" + usedHrs + "]";
	}

	

}
