package com.boyo.wuhang.entity.base;

public class BaseGoods {
    private Long id;

    private String goodsName;

    private String goodsSpec;

    private Integer flag;

    private Integer mark;

    private String goodsCode;

    private String goodsSpec2;

    private String model1;

    private String model2;

    private String goodsName2;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName == null ? null : goodsName.trim();
    }

    public String getGoodsSpec() {
        return goodsSpec;
    }

    public void setGoodsSpec(String goodsSpec) {
        this.goodsSpec = goodsSpec == null ? null : goodsSpec.trim();
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

    public String getGoodsCode() {
        return goodsCode;
    }

    public void setGoodsCode(String goodsCode) {
        this.goodsCode = goodsCode == null ? null : goodsCode.trim();
    }

    public String getGoodsSpec2() {
        return goodsSpec2;
    }

    public void setGoodsSpec2(String goodsSpec2) {
        this.goodsSpec2 = goodsSpec2;
    }

    public String getModel1() {
        return model1;
    }

    public void setModel1(String model1) {
        this.model1 = model1;
    }

    public String getModel2() {
        return model2;
    }

    public void setModel2(String model2) {
        this.model2 = model2;
    }

    public String getGoodsName2() {
        return goodsName2;
    }

    public void setGoodsName2(String goodsName2) {
        this.goodsName2 = goodsName2;
    }
}