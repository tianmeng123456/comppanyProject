package com.boyo.wuhang.entity.assess;

import java.math.BigDecimal;
import java.util.Date;

public class WhAccess {
    private Long id;

    private String accessNo;

    private BigDecimal impurities;

    private String remark;

    private String accessPerson;

    private Long employeeId;

    private String editPerson;

    private Long managerId;

    private Date checkTime;

    private Integer flag;

    private Integer mark;

    private Long deliverId;

    private BigDecimal priceAdjust;

    private Date createTime;

    private BigDecimal sumAdjust;

    private Long loadPlaceId;

    private Integer over60;

    private Long employeeId2;

    private String accessPerson2;

    private Date submitTime;

    private Date adoptTime;

    private BigDecimal custom;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccessNo() {
        return accessNo;
    }

    public void setAccessNo(String accessNo) {
        this.accessNo = accessNo == null ? null : accessNo.trim();
    }

    public BigDecimal getImpurities() {
        return impurities;
    }

    public void setImpurities(BigDecimal impurities) {
        this.impurities = impurities;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getAccessPerson() {
        return accessPerson;
    }

    public void setAccessPerson(String accessPerson) {
        this.accessPerson = accessPerson == null ? null : accessPerson.trim();
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public String getEditPerson() {
        return editPerson;
    }

    public void setEditPerson(String editPerson) {
        this.editPerson = editPerson == null ? null : editPerson.trim();
    }

    public Long getManagerId() {
        return managerId;
    }

    public void setManagerId(Long managerId) {
        this.managerId = managerId;
    }

    public Date getCheckTime() {
        return checkTime;
    }

    public void setCheckTime(Date checkTime) {
        this.checkTime = checkTime;
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

    public Long getDeliverId() {
        return deliverId;
    }

    public void setDeliverId(Long deliverId) {
        this.deliverId = deliverId;
    }

    public BigDecimal getPriceAdjust() {
        return priceAdjust;
    }

    public void setPriceAdjust(BigDecimal priceAdjust) {
        this.priceAdjust = priceAdjust;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

	public BigDecimal getSumAdjust() {
		return sumAdjust;
	}

	public void setSumAdjust(BigDecimal sumAdjust) {
		this.sumAdjust = sumAdjust;
	}

	public Long getLoadPlaceId() {
		return loadPlaceId;
	}

	public void setLoadPlaceId(Long loadPlaceId) {
		this.loadPlaceId = loadPlaceId;
	}

	public Integer getOver60() {
		return over60;
	}

	public void setOver60(Integer over60) {
		this.over60 = over60;
	}

	public Long getEmployeeId2() {
		return employeeId2;
	}

	public void setEmployeeId2(Long employeeId2) {
		this.employeeId2 = employeeId2;
	}

	public String getAccessPerson2() {
		return accessPerson2;
	}

	public void setAccessPerson2(String accessPerson2) {
		this.accessPerson2 = accessPerson2;
	}

    public Date getSubmitTime() {
        return submitTime;
    }

    public void setSubmitTime(Date submitTime) {
        this.submitTime = submitTime;
    }

    public Date getAdoptTime() {
        return adoptTime;
    }

    public void setAdoptTime(Date adoptTime) {
        this.adoptTime = adoptTime;
    }

    public BigDecimal getCustom() {
        return custom;
    }

    public void setCustom(BigDecimal custom) {
        this.custom = custom;
    }
}