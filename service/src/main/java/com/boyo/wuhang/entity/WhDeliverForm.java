package com.boyo.wuhang.entity;

import com.boyo.wuhang.entity.assess.WhDeliver;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class WhDeliverForm extends WhDeliver {

    private String supplierName;

    private String startDate;

    private String endDate;

    private Integer pageNumber;

    private Integer pageSize;
    //入场称重时间
    private String arriveWeighStartDate;

    private String arriveWeighEndDate;
    //出场称重时间
    private String leaveWeighStartTime;

    private String leaveWeighEndTime;

    private String accessNo;

    private String gpsPicture;

    private Long weighId;

    private Long deliverId;

    private String carNo;

    private Integer flagStart;

    private Integer flagEnd;

    private Integer editFlag1;

    private Integer editFlag2;

    private Integer detailFlag;

    private Integer shipFlag;

    private Integer shipAccessFlag;

    private List<WhDeliver> deliverList;

    public String getCarNo() {
        return carNo;
    }

    public void setCarNo(String carNo) {
        this.carNo = carNo;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

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

    public String getAccessNo() {
        return accessNo;
    }

    public void setAccessNo(String accessNo) {
        this.accessNo = accessNo;
    }

    public String getGpsPicture() {
        return gpsPicture;
    }

    public void setGpsPicture(String gpsPicture) {
        this.gpsPicture = gpsPicture;
    }

    public Long getWeighId() {
        return weighId;
    }

    public void setWeighId(Long weighId) {
        this.weighId = weighId;
    }

    public Long getDeliverId() {
        return deliverId;
    }

    public void setDeliverId(Long deliverId) {
        this.deliverId = deliverId;
    }

    public Integer getFlagStart() {
        return flagStart;
    }

    public void setFlagStart(Integer flagStart) {
        this.flagStart = flagStart;
    }

    public Integer getFlagEnd() {
        return flagEnd;
    }

    public void setFlagEnd(Integer flagEnd) {
        this.flagEnd = flagEnd;
    }

    public Integer getEditFlag1() {
        return editFlag1;
    }

    public void setEditFlag1(Integer editFlag1) {
        this.editFlag1 = editFlag1;
    }

    public Integer getEditFlag2() {
        return editFlag2;
    }

    public void setEditFlag2(Integer editFlag2) {
        this.editFlag2 = editFlag2;
    }

    public Integer getDetailFlag() {
        return detailFlag;
    }

    public void setDetailFlag(Integer detailFlag) {
        this.detailFlag = detailFlag;
    }

    public List<WhDeliver> getDeliverList() {
        return deliverList;
    }

    public void setDeliverList(List<WhDeliver> deliverList) {
        this.deliverList = deliverList;
    }

    public Integer getShipFlag() {
        return shipFlag;
    }

    public void setShipFlag(Integer shipFlag) {
        this.shipFlag = shipFlag;
    }

    public Integer getShipAccessFlag() {
        return shipAccessFlag;
    }

    public void setShipAccessFlag(Integer shipAccessFlag) {
        this.shipAccessFlag = shipAccessFlag;
    }
}
