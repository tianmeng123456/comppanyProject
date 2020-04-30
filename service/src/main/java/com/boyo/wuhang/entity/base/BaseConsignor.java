package com.boyo.wuhang.entity.base;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "供应商下货主")
public class BaseConsignor {
    @ApiModelProperty(value = "实体主键",dataType="Long")
    private Long id;

    @ApiModelProperty(value = "货主名称",dataType="String")
    private String consignorName;

    @ApiModelProperty(value = "银行",dataType="String")
    private String bank;

    @ApiModelProperty(value = "银行账户",dataType="String")
    private String bankAccount;

    @ApiModelProperty(value = "对应供应商",dataType="Long")
    private Long supplierId;

    private String identityCard;

    private String remark;

    private Integer sort;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getConsignorName() {
        return consignorName;
    }

    public void setConsignorName(String consignorName) {
        this.consignorName = consignorName == null ? null : consignorName.trim();
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank == null ? null : bank.trim();
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount == null ? null : bankAccount.trim();
    }

    public Long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
    }

    public String getIdentityCard() {
        return identityCard;
    }

    public void setIdentityCard(String identityCard) {
        this.identityCard = identityCard;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }
}