package com.boyo.wuhang.entity.assess;

import java.math.BigDecimal;
import java.util.Date;

public class WhDeliver {
    private Long id;

    private String deliverNo;

    private Date arriveTime;

    private Long supplierId;

    private String consignorName;

    private Long consignorId;

    private String carNumber;

    private BigDecimal weigh;

    private String goodsName;

    private Long goodsId;

    private String driverName;

    private String phone;

    private Date createTime;

    private Integer flag;

    private Integer mark;

    private Long departureId;

    private String departureName;

    private Long personId;

    private String personName;

    private String contractNo;

    private Integer valueMethod;

    private String placeName;

    private Integer placeId;

    private BigDecimal contractPrice;

    private BigDecimal contractAmount;

    private String remark;

    private Integer checkFlag;

    private String settleNo;

    private Date settleTime;

    private Long settleId;

    private String settleName;

    private Integer settleFlag;

    private Integer editFlag;

    private Long priceEditId;

    private String priceEditName;

    private Date priceEditTime;

    private Long priceCheckId;

    private String priceCheckName;

    private Date priceCheckTime;

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    public BigDecimal getContractAmount() {
        return contractAmount;
    }

    public void setContractAmount(BigDecimal contractAmount) {
        this.contractAmount = contractAmount;
    }

    public BigDecimal getContractPrice() {
        return contractPrice;
    }

    public void setContractPrice(BigDecimal contractPrice) {
        this.contractPrice = contractPrice;
    }

    public Integer getValueMethod() {
        return valueMethod;
    }

    public void setValueMethod(Integer valueMethod) {
        this.valueMethod = valueMethod;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDeliverNo() {
        return deliverNo;
    }

    public void setDeliverNo(String deliverNo) {
        this.deliverNo = deliverNo == null ? null : deliverNo.trim();
    }

    public Date getArriveTime() {
        return arriveTime;
    }

    public void setArriveTime(Date arriveTime) {
        this.arriveTime = arriveTime;
    }

    public Long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
    }

    public String getConsignorName() {
        return consignorName;
    }

    public void setConsignorName(String consignorName) {
        this.consignorName = consignorName == null ? null : consignorName.trim();
    }

    public Long getConsignorId() {
        return consignorId;
    }

    public void setConsignorId(Long consignorId) {
        this.consignorId = consignorId;
    }

    public String getCarNumber() {
        return carNumber;
    }

    public void setCarNumber(String carNumber) {
        this.carNumber = carNumber == null ? null : carNumber.trim();
    }

    public BigDecimal getWeigh() {
        return weigh;
    }

    public void setWeigh(BigDecimal weigh) {
        this.weigh = weigh;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName == null ? null : goodsName.trim();
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName == null ? null : driverName.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
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

    public Long getDepartureId() {
        return departureId;
    }

    public void setDepartureId(Long departureId) {
        this.departureId = departureId;
    }

    public String getDepartureName() {
        return departureName;
    }

    public void setDepartureName(String departureName) {
        this.departureName = departureName == null ? null : departureName.trim();
    }

    public Long getPersonId() {
        return personId;
    }

    public void setPersonId(Long personId) {
        this.personId = personId;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public String getContractNo() {
        return contractNo;
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public Integer getPlaceId() {
        return placeId;
    }

    public void setPlaceId(Integer placeId) {
        this.placeId = placeId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getCheckFlag() {
        return checkFlag;
    }

    public void setCheckFlag(Integer checkFlag) {
        this.checkFlag = checkFlag;
    }

    public String getSettleNo() {
        return settleNo;
    }

    public void setSettleNo(String settleNo) {
        this.settleNo = settleNo;
    }

    public Date getSettleTime() {
        return settleTime;
    }

    public void setSettleTime(Date settleTime) {
        this.settleTime = settleTime;
    }

    public Long getSettleId() {
        return settleId;
    }

    public void setSettleId(Long settleId) {
        this.settleId = settleId;
    }

    public String getSettleName() {
        return settleName;
    }

    public void setSettleName(String settleName) {
        this.settleName = settleName;
    }

    public Integer getSettleFlag() {
        return settleFlag;
    }

    public void setSettleFlag(Integer settleFlag) {
        this.settleFlag = settleFlag;
    }

    public Integer getEditFlag() {
        return editFlag;
    }

    public void setEditFlag(Integer editFlag) {
        this.editFlag = editFlag;
    }

    public Long getPriceEditId() {
        return priceEditId;
    }

    public void setPriceEditId(Long priceEditId) {
        this.priceEditId = priceEditId;
    }

    public String getPriceEditName() {
        return priceEditName;
    }

    public void setPriceEditName(String priceEditName) {
        this.priceEditName = priceEditName;
    }

    public Date getPriceEditTime() {
        return priceEditTime;
    }

    public void setPriceEditTime(Date priceEditTime) {
        this.priceEditTime = priceEditTime;
    }

    public Long getPriceCheckId() {
        return priceCheckId;
    }

    public void setPriceCheckId(Long priceCheckId) {
        this.priceCheckId = priceCheckId;
    }

    public String getPriceCheckName() {
        return priceCheckName;
    }

    public void setPriceCheckName(String priceCheckName) {
        this.priceCheckName = priceCheckName;
    }

    public Date getPriceCheckTime() {
        return priceCheckTime;
    }

    public void setPriceCheckTime(Date priceCheckTime) {
        this.priceCheckTime = priceCheckTime;
    }
}