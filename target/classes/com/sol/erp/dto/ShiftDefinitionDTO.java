package com.sol.erp.dto;

public class ShiftDefinitionDTO {

    private String companyId;
    private String branchId;
    private String shiftCode;
    private String inTime;
    private String outTime;
    private String totalTime;
    private String breakOutTime;
    private String breakInTime;
    private String totalBreakTime;
    private String lateCount;
    private String otNotBefore;
    private String minimumOtValue;
    private String absentCount;
    public String getCompanyId() {
        return companyId;
    }
    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }
    public String getBranchId() {
        return branchId;
    }
    public void setBranchId(String branchId) {
        this.branchId = branchId;
    }
    public String getShiftCode() {
        return shiftCode;
    }
    public void setShiftCode(String shiftCode) {
        this.shiftCode = shiftCode;
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
    public String getTotalTime() {
        return totalTime;
    }
    public void setTotalTime(String totalTime) {
        this.totalTime = totalTime;
    }
    public String getBreakOutTime() {
        return breakOutTime;
    }
    public void setBreakOutTime(String breakOutTime) {
        this.breakOutTime = breakOutTime;
    }
    public String getBreakInTime() {
        return breakInTime;
    }
    public void setBreakInTime(String breakInTime) {
        this.breakInTime = breakInTime;
    }
    public String getTotalBreakTime() {
        return totalBreakTime;
    }
    public void setTotalBreakTime(String totalBreakTime) {
        this.totalBreakTime = totalBreakTime;
    }
    public String getLateCount() {
        return lateCount;
    }
    public void setLateCount(String lateCount) {
        this.lateCount = lateCount;
    }
    public String getOtNotBefore() {
        return otNotBefore;
    }
    public void setOtNotBefore(String otNotBefore) {
        this.otNotBefore = otNotBefore;
    }
    public String getMinimumOtValue() {
        return minimumOtValue;
    }
    public void setMinimumOtValue(String minimumOtValue) {
        this.minimumOtValue = minimumOtValue;
    }
    public String getAbsentCount() {
        return absentCount;
    }
    public void setAbsentCount(String absentCount) {
        this.absentCount = absentCount;
    }
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((branchId == null) ? 0 : branchId.hashCode());
        result = prime * result + ((companyId == null) ? 0 : companyId.hashCode());
        result = prime * result + ((shiftCode == null) ? 0 : shiftCode.hashCode());
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
        ShiftDefinitionDTO other = (ShiftDefinitionDTO) obj;
        if (branchId == null) {
            if (other.branchId != null)
                return false;
        } else if (!branchId.equals(other.branchId))
            return false;
        if (companyId == null) {
            if (other.companyId != null)
                return false;
        } else if (!companyId.equals(other.companyId))
            return false;
        if (shiftCode == null) {
            if (other.shiftCode != null)
                return false;
        } else if (!shiftCode.equals(other.shiftCode))
            return false;
        return true;
    }
    @Override
    public String toString() {
        return "ShiftDefinitionDTO [companyId=" + companyId + ", branchId=" + branchId + ", shiftCode=" + shiftCode
                + ", inTime=" + inTime + ", outTime=" + outTime + ", totalTime=" + totalTime + ", breakOutTime="
                + breakOutTime + ", breakInTime=" + breakInTime + ", totalBreakTime=" + totalBreakTime + ", lateCount="
                + lateCount + ", otNotBefore=" + otNotBefore + ", minimumOtValue=" + minimumOtValue + ", absentCount="
                + absentCount + "]";
    }

    

}
