package com.sol.erp.dto;

public class QuotationDTO {

	private String date;
	private long quotationNumber;
	private String projectNumber;
	private String projectName;
	private String clientName;
	private int totalQuotedHrs;
	private int totalQuotedSheets;
	private int actualTotalHrs;
	private int actualTotalSheets;
	private String teamLeader;

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public long getQuotationNumber() {
		return quotationNumber;
	}

	public void setQuotationNumber(long quotationNumber) {
		this.quotationNumber = quotationNumber;
	}

	public String getProjectNumber() {
		return projectNumber;
	}

	public void setProjectNumber(String projectNumber) {
		this.projectNumber = projectNumber;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public int getTotalQuotedHrs() {
		return totalQuotedHrs;
	}

	public void setTotalQuotedHrs(int totalQuotedHrs) {
		this.totalQuotedHrs = totalQuotedHrs;
	}

	public int getTotalQuotedSheets() {
		return totalQuotedSheets;
	}

	public void setTotalQuotedSheets(int totalQuotedSheets) {
		this.totalQuotedSheets = totalQuotedSheets;
	}

	public int getActualTotalHrs() {
		return actualTotalHrs;
	}

	public void setActualTotalHrs(int actualTotalHrs) {
		this.actualTotalHrs = actualTotalHrs;
	}

	public int getActualTotalSheets() {
		return actualTotalSheets;
	}

	public void setActualTotalSheets(int actualTotalSheets) {
		this.actualTotalSheets = actualTotalSheets;
	}

	public String getTeamLeader() {
		return teamLeader;
	}

	public void setTeamLeader(String teamLeader) {
		this.teamLeader = teamLeader;
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
		QuotationDTO other = (QuotationDTO) obj;
		if (quotationNumber != other.quotationNumber)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "QuotationDTO [date=" + date + ", quotationNumber=" + quotationNumber + ", projectNumber="
				+ projectNumber + ", projectName=" + projectName + ", clientName=" + clientName + ", totalQuotedHrs="
				+ totalQuotedHrs + ", totalQuotedSheets=" + totalQuotedSheets + ", actualTotalHrs=" + actualTotalHrs
				+ ", actualTotalSheets=" + actualTotalSheets + ", teamLeader=" + teamLeader + "]";
	}

}
