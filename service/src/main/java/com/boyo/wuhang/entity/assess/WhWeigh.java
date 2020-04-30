package com.boyo.wuhang.entity.assess;

import java.math.BigDecimal;
import java.util.Date;

public class WhWeigh {
    private Long id;

    private String weighNo;

    private Long deliverId;

    private BigDecimal arriveWeigh;

    private Date arriveWeighTime;

    private BigDecimal leaveWeigh;

    private Date leaveWeighTime;

    private BigDecimal netWeigh;

    private BigDecimal impurities;

    private String weighPerson;

    private Long employeeId;

    private Integer flag;

    private Integer mark;

    private String gpsPicture;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getWeighNo() {
        return weighNo;
    }

    public void setWeighNo(String weighNo) {
        this.weighNo = weighNo == null ? null : weighNo.trim();
    }

    public Long getDeliverId() {
        return deliverId;
    }

    public void setDeliverId(Long deliverId) {
        this.deliverId = deliverId;
    }

    public BigDecimal getArriveWeigh() {
        return arriveWeigh;
    }

    public void setArriveWeigh(BigDecimal arriveWeigh) {
        this.arriveWeigh = arriveWeigh;
    }

    public Date getArriveWeighTime() {
        return arriveWeighTime;
    }

    public void setArriveWeighTime(Date arriveWeighTime) {
        this.arriveWeighTime = arriveWeighTime;
    }

    public BigDecimal getLeaveWeigh() {
        return leaveWeigh;
    }

    public void setLeaveWeigh(BigDecimal leaveWeigh) {
        this.leaveWeigh = leaveWeigh;
    }

    public Date getLeaveWeighTime() {
        return leaveWeighTime;
    }

    public void setLeaveWeighTime(Date leaveWeighTime) {
        this.leaveWeighTime = leaveWeighTime;
    }

    public BigDecimal getNetWeigh() {
        return netWeigh;
    }

    public void setNetWeigh(BigDecimal netWeigh) {
        this.netWeigh = netWeigh;
    }

    public BigDecimal getImpurities() {
        return impurities;
    }

    public void setImpurities(BigDecimal impurities) {
        this.impurities = impurities;
    }

    public String getWeighPerson() {
        return weighPerson;
    }

    public void setWeighPerson(String weighPerson) {
        this.weighPerson = weighPerson == null ? null : weighPerson.trim();
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public Integer getMark() {
        return mark;
    }

    public void setMark(Integer mark) {
        this.mark = mark;
    }

	public String getGpsPicture() {
		return gpsPicture;
	}

	public void setGpsPicture(String gpsPicture) {
		this.gpsPicture = gpsPicture;
	}
}