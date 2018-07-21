package com.sol.erp.dto;

public class AttendanceDTO {

	private String date;
	private String punchcard;
	private String empCode;
	private String name;
	private String inTime;
	private String outTime;
	private String total;
	private String projectNumber;
	private String detailingChecking;
	private String breakHrs;
	private String otHrs;
	private String otYesNo;
	private String deductOt;
	private String deductOtYesNo;
	private String applicable;
	private String OT2;

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getPunchcard() {
		return punchcard;
	}

	public void setPunchcard(String punchcard) {
		this.punchcard = punchcard;
	}

	public String getEmpCode() {
		return empCode;
	}

	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getInTime() {
		return inTime;
	}

	public void setInTime(String inTime) {
		this.inTime = inTime;
	}

	public String getOutTime() {
		return outTime;
	}

	public void setOutTime(String outTime) {
		this.outTime = outTime;
	}

	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}

	public String getProjectNumber() {
		return projectNumber;
	}

	public void setProjectNumber(String projectNumber) {
		this.projectNumber = projectNumber;
	}

	public String getDetailingChecking() {
		return detailingChecking;
	}

	public void setDetailingChecking(String detailingChecking) {
		this.detailingChecking = detailingChecking;
	}

	public String getBreakHrs() {
		return breakHrs;
	}

	public void setBreakHrs(String breakHrs) {
		this.breakHrs = breakHrs;
	}

	public String getOtHrs() {
		return otHrs;
	}

	public void setOtHrs(String otHrs) {
		this.otHrs = otHrs;
	}

	public String getOtYesNo() {
		return otYesNo;
	}

	public void setOtYesNo(String otYesNo) {
		this.otYesNo = otYesNo;
	}

	public String getDeductOt() {
		return deductOt;
	}

	public void setDeductOt(String deductOt) {
		this.deductOt = deductOt;
	}

	public String getDeductOtYesNo() {
		return deductOtYesNo;
	}

	public void setDeductOtYesNo(String deductOtYesNo) {
		this.deductOtYesNo = deductOtYesNo;
	}

	public String getApplicable() {
		return applicable;
	}

	public void setApplicable(String applicable) {
		this.applicable = applicable;
	}

	public String getOT2() {
		return OT2;
	}

	public void setOT2(String oT2) {
		OT2 = oT2;
	}

	@Override
	public String toString() {
		return "AttendanceDTO [date=" + date + ", punchcard=" + punchcard + ", empCode=" + empCode + ", name=" + name
				+ ", inTime=" + inTime + ", outTime=" + outTime + ", total=" + total + ", projectNumber="
				+ projectNumber + ", detailingChecking=" + detailingChecking + ", breakHrs=" + breakHrs + ", otHrs="
				+ otHrs + ", otYesNo=" + otYesNo + ", deductOt=" + deductOt + ", deductOtYesNo=" + deductOtYesNo
				+ ", applicable=" + applicable + ", OT2=" + OT2 + "]";
	}

}
