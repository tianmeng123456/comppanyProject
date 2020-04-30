package com.boyo.wuhang.entity;

import com.boyo.wuhang.entity.assess.WhDeliveryDetails;

public class WhDeliveryDetailsForm extends WhDeliveryDetails {

    private Long goodsId;

    private String goodsName;

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }
}
