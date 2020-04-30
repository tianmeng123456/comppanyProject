package com.boyo.wuhang.entity.assess;

import java.math.BigDecimal;
import java.util.Date;

public class WhDeliverCarWeighRecord {
    private Long id;

    private Long deliverId;

    private Long carId;

    private BigDecimal grossWeigh;

    private Date weighTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDeliverId() {
        return deliverId;
    }

    public void setDeliverId(Long deliverId) {
        this.deliverId = deliverId;
    }

    public Long getCarId() {
        return carId;
    }

    public void setCarId(Long carId) {
        this.carId = carId;
    }

    public BigDecimal getGrossWeigh() {
        return grossWeigh;
    }

    public void setGrossWeigh(BigDecimal grossWeigh) {
        this.grossWeigh = grossWeigh;
    }

    public Date getWeighTime() {
        return weighTime;
    }

    public void setWeighTime(Date weighTime) {
        this.weighTime = weighTime;
    }
}