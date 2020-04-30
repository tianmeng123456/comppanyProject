package com.boyo.wuhang.entity;

import com.boyo.wuhang.entity.assess.WhWeigh;

public class WhWeighForm extends WhWeigh {

    private String carNumber;

    private String startDate;

    private String endDate;

    private String supplierName;

    private String consignorName;

    //入场称重时间
    private String arriveWeighStartDate;

    private String arriveWeighEndDate;
    //出场称重时间
    private String leaveWeighStartTime;

    private String leaveWeighEndTime;

    private Integer pageNumber;

    private Integer pageSize;

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getArriveWeighStartDate() {
        return arriveWeighStartDate;
    }

    public void setArriveWeighStartDate(String arriveWeighStartDate) {
        this.arriveWeighStartDate = arriveWeighStartDate;
    }

    public String getArriveWeighEndDate() {
        return arriveWeighEndDate;
    }

    public void setArriveWeighEndDate(String arriveWeighEndDate) {
        this.arriveWeighEndDate = arriveWeighEndDate;
    }

    public String getLeaveWeighStartTime() {
        return leaveWeighStartTime;
    }

    public void setLeaveWeighStartTime(String leaveWeighStartTime) {
        this.leaveWeighStartTime = leaveWeighStartTime;
    }

    public String getLeaveWeighEndTime() {
        return leaveWeighEndTime;
    }

    public void setLeaveWeighEndTime(String leaveWeighEndTime) {
        this.leaveWeighEndTime = leaveWeighEndTime;
    }

    public String getCarNumber() {
        return carNumber;
    }

    public void setCarNumber(String carNumber) {
        this.carNumber = carNumber;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getConsignorName() {
        return consignorName;
    }

    public void setConsignorName(String consignorName) {
        this.consignorName = consignorName;
    }
}
